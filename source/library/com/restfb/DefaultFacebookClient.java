/*
 * Copyright (c) 2010-2015 Mark Allen.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.restfb;

import static com.restfb.util.EncodingUtils.decodeBase64;
import static com.restfb.util.EncodingUtils.encodeHex;
import static com.restfb.util.StringUtils.isBlank;
import static com.restfb.util.StringUtils.join;
import static com.restfb.util.StringUtils.toBytes;
import static com.restfb.util.StringUtils.toInteger;
import static com.restfb.util.StringUtils.trimToNull;
import static com.restfb.util.UrlUtils.urlEncode;
import static java.lang.String.format;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.restfb.WebRequestor.Response;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;
import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookExceptionMapper;
import com.restfb.exception.FacebookGraphException;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.exception.FacebookNetworkException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.exception.FacebookQueryParseException;
import com.restfb.exception.FacebookResponseContentException;
import com.restfb.exception.FacebookResponseStatusException;
import com.restfb.exception.FacebookSignedRequestParsingException;
import com.restfb.exception.FacebookSignedRequestVerificationException;
import com.restfb.exception.devicetoken.DeviceTokenExceptionFactory;
import com.restfb.exception.devicetoken.FacebookDeviceTokenCodeExpiredException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenDeclinedException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenPendingException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenSlowdownException;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.DeviceCode;
import com.restfb.util.StringUtils;

/**
 * Default implementation of a <a href="http://developers.facebook.com/docs/api">Facebook Graph API</a> client.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class DefaultFacebookClient extends BaseFacebookClient implements FacebookClient {
  /**
   * Graph API access token.
   */
  protected String accessToken;

  /**
   * Graph API app secret.
   */
  private String appSecret;

  /**
   * Knows how to map Graph API exceptions to formal Java exception types.
   */
  protected FacebookExceptionMapper graphFacebookExceptionMapper;

  protected static final String FACEBOOK_ENDPOINT_URL = "https://www.facebook.com";

  /**
   * API endpoint URL.
   */
  protected static final String FACEBOOK_GRAPH_ENDPOINT_URL = "https://graph.facebook.com";

  /**
   * Read-only API endpoint URL.
   */
  protected static final String FACEBOOK_READ_ONLY_ENDPOINT_URL = "https://api-read.facebook.com/method";

  /**
   * Video Upload API endpoint URL.
   * 
   * @since 1.6.5
   */
  protected static final String FACEBOOK_GRAPH_VIDEO_ENDPOINT_URL = "https://graph-video.facebook.com";

  /**
   * Reserved method override parameter name.
   */
  protected static final String METHOD_PARAM_NAME = "method";

  /**
   * Reserved "multiple IDs" parameter name.
   */
  protected static final String IDS_PARAM_NAME = "ids";

  /**
   * Reserved legacy FQL query parameter name.
   */
  protected static final String QUERY_PARAM_NAME = "query";

  /**
   * Reserved legacy FQL multiquery parameter name.
   */
  protected static final String QUERIES_PARAM_NAME = "queries";

  /**
   * Reserved FQL query parameter name.
   */
  protected static final String FQL_QUERY_PARAM_NAME = "q";

  /**
   * Reserved "result format" parameter name.
   */
  protected static final String FORMAT_PARAM_NAME = "format";

  /**
   * API error response 'error' attribute name.
   */
  protected static final String ERROR_ATTRIBUTE_NAME = "error";

  /**
   * API error response 'type' attribute name.
   */
  protected static final String ERROR_TYPE_ATTRIBUTE_NAME = "type";

  /**
   * API error response 'error_user_title' attribute name.
   */
  protected static final String ERROR_USER_TITLE_ATTRIBUTE_NAME = "error_user_title";

  /**
   * API error response 'error_user_msg' attribute name.
   */
  protected static final String ERROR_USER_MSG_ATTRIBUTE_NAME = "error_user_msg";

  /**
   * API error response 'message' attribute name.
   */
  protected static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "message";

  /**
   * API error response 'code' attribute name.
   */
  protected static final String ERROR_CODE_ATTRIBUTE_NAME = "code";

  /**
   * API error response 'error_subcode' attribute name.
   */
  protected static final String ERROR_SUBCODE_ATTRIBUTE_NAME = "error_subcode";

  /**
   * Batch API error response 'error' attribute name.
   */
  protected static final String BATCH_ERROR_ATTRIBUTE_NAME = "error";

  /**
   * Batch API error response 'error_description' attribute name.
   */
  protected static final String BATCH_ERROR_DESCRIPTION_ATTRIBUTE_NAME = "error_description";

  /**
   * Version of API endpoint.
   */
  protected Version apiVersion = Version.UNVERSIONED;

  /**
   * By default this is <code>false</code>, so real http DELETE is used
   */
  protected boolean httpDeleteFallback = false;

  /**
   * Creates a Facebook Graph API client with no access token.
   * <p>
   * Without an access token, you can view and search public graph data but can't do much else.
   * 
   * @deprecated As of release 1.7.1, replaced by {@link #DefaultFacebookClient(com.restfb.Version) }
   */
  @Deprecated
  public DefaultFacebookClient() {
    this((String) null);
  }

  /**
   * Creates a Facebook Graph API client with the given {@code accessToken}.
   * 
   * @param accessToken
   *          A Facebook OAuth access token.
   * @deprecated As of release 1.7.1 replaced by {@link #DefaultFacebookClient(java.lang.String, com.restfb.Version) }
   */
  @Deprecated
  public DefaultFacebookClient(String accessToken) {
    this(accessToken, null, new DefaultWebRequestor(), new DefaultJsonMapper(), null);
  }

  /**
   * Creates a Facebook Graph API client with the given {@code apiVersion}.
   * 
   * @param apiVersion
   *          Version of the api endpoint
   */
  public DefaultFacebookClient(Version apiVersion) {
    this(null, null, new DefaultWebRequestor(), new DefaultJsonMapper(), apiVersion);
  }

  /**
   * Creates a Facebook Graph API client with the given {@code accessToken}.
   * 
   * @param accessToken
   *          A Facebook OAuth access token.
   * @param apiVersion
   *          Version of the api endpoint
   * @since 1.6.14
   */
  public DefaultFacebookClient(String accessToken, Version apiVersion) {
    this(accessToken, null, new DefaultWebRequestor(), new DefaultJsonMapper(), apiVersion);
  }

  /**
   * Creates a Facebook Graph API client with the given {@code accessToken}.
   * 
   * @param accessToken
   *          A Facebook OAuth access token.
   * @param appSecret
   *          A Facebook application secret.
   * @since 1.6.13
   * @deprecated As of release 1.7.1, replaced by
   *             {@link #DefaultFacebookClient(java.lang.String, java.lang.String, com.restfb.Version) }
   */
  @Deprecated
  public DefaultFacebookClient(String accessToken, String appSecret) {
    this(accessToken, appSecret, new DefaultWebRequestor(), new DefaultJsonMapper(), null);
  }

  /**
   * Creates a Facebook Graph API client with the given {@code accessToken}.
   * 
   * @param accessToken
   *          A Facebook OAuth access token.
   * @param appSecret
   *          A Facebook application secret.
   * @param apiVersion
   *          Version of the api endpoint
   * @since 1.6.14
   */
  public DefaultFacebookClient(String accessToken, String appSecret, Version apiVersion) {
    this(accessToken, appSecret, new DefaultWebRequestor(), new DefaultJsonMapper(), apiVersion);
  }

  /**
   * Creates a Facebook Graph API client with the given {@code accessToken}.
   * 
   * @param accessToken
   *          A Facebook OAuth access token.
   * @param webRequestor
   *          The {@link WebRequestor} implementation to use for sending requests to the API endpoint.
   * @param jsonMapper
   *          The {@link JsonMapper} implementation to use for mapping API response JSON to Java objects.
   * @throws NullPointerException
   *           If {@code jsonMapper} or {@code webRequestor} is {@code null}.
   * @deprecated As of release 1.7.1, replaced by
   *             {@link #DefaultFacebookClient(java.lang.String, com.restfb.WebRequestor, com.restfb.JsonMapper, com.restfb.Version) }
   */
  @Deprecated
  public DefaultFacebookClient(String accessToken, WebRequestor webRequestor, JsonMapper jsonMapper) {
    this(accessToken, null, webRequestor, jsonMapper, null);
  }

  /**
   * Creates a Facebook Graph API client with the given {@code accessToken}.
   * 
   * @param accessToken
   *          A Facebook OAuth access token.
   * @param webRequestor
   *          The {@link WebRequestor} implementation to use for sending requests to the API endpoint.
   * @param jsonMapper
   *          The {@link JsonMapper} implementation to use for mapping API response JSON to Java objects.
   * @param apiVersion
   *          Version of the api endpoint
   * @throws NullPointerException
   *           If {@code jsonMapper} or {@code webRequestor} is {@code null}.
   * @since 1.6.14
   */
  public DefaultFacebookClient(String accessToken, WebRequestor webRequestor, JsonMapper jsonMapper, Version apiVersion) {
    this(accessToken, null, webRequestor, jsonMapper, apiVersion);
  }

  /**
   * Creates a Facebook Graph API client with the given {@code accessToken}, {@code webRequestor}, and
   * {@code jsonMapper}.
   * 
   * @param accessToken
   *          A Facebook OAuth access token.
   * @param appSecret
   *          A Facebook application secret.
   * @param webRequestor
   *          The {@link WebRequestor} implementation to use for sending requests to the API endpoint.
   * @param jsonMapper
   *          The {@link JsonMapper} implementation to use for mapping API response JSON to Java objects.
   * @param apiVersion
   *          Version of the api endpoint
   * @throws NullPointerException
   *           If {@code jsonMapper} or {@code webRequestor} is {@code null}.
   */
  public DefaultFacebookClient(String accessToken, String appSecret, WebRequestor webRequestor, JsonMapper jsonMapper,
      Version apiVersion) {
    super();

    verifyParameterPresence("jsonMapper", jsonMapper);
    verifyParameterPresence("webRequestor", webRequestor);

    this.accessToken = trimToNull(accessToken);
    this.appSecret = trimToNull(appSecret);

    this.webRequestor = webRequestor;
    this.jsonMapper = jsonMapper;
    this.apiVersion = (null == apiVersion) ? Version.UNVERSIONED : apiVersion;
    graphFacebookExceptionMapper = createGraphFacebookExceptionMapper();

    illegalParamNames.addAll(Arrays.asList(ACCESS_TOKEN_PARAM_NAME, METHOD_PARAM_NAME, FORMAT_PARAM_NAME));
  }

  /**
   * @see com.restfb.FacebookClient#deleteObject(java.lang.String, com.restfb.Parameter[])
   */
  @Override
  public boolean deleteObject(String object, Parameter... parameters) {
    verifyParameterPresence("object", object);

    String responseString = makeRequest(object, true, true, null, parameters);
    String cmpString;

    try {
      JsonObject jObj = new JsonObject(responseString);
      cmpString = jObj.getString("success");
    } catch (JsonException jex) {
      cmpString = responseString;
    }

    return "true".equals(cmpString);
  }

  /**
   * @see com.restfb.FacebookClient#fetchConnection(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> Connection<T> fetchConnection(String connection, Class<T> connectionType, Parameter... parameters) {
    verifyParameterPresence("connection", connection);
    verifyParameterPresence("connectionType", connectionType);
    return new Connection<T>(this, makeRequest(connection, parameters), connectionType);
  }

  /**
   * @see com.restfb.FacebookClient#fetchConnectionPage(java.lang.String, java.lang.Class)
   */
  @Override
  public <T> Connection<T> fetchConnectionPage(final String connectionPageUrl, Class<T> connectionType) {
    String connectionJson;
    if (!isBlank(accessToken) && !isBlank(appSecret)) {
      connectionJson = makeRequestAndProcessResponse(new Requestor() {
        @Override
        public Response makeRequest() throws IOException {
          return webRequestor.executeGet(String.format("%s&%s=%s", connectionPageUrl,
            urlEncode(APP_SECRET_PROOF_PARAM_NAME), obtainAppSecretProof(accessToken, appSecret)));
        }
      });
    } else {
      connectionJson = makeRequestAndProcessResponse(new Requestor() {
        @Override
        public Response makeRequest() throws IOException {
          return webRequestor.executeGet(connectionPageUrl);
        }
      });
    }

    return new Connection<T>(this, connectionJson, connectionType);
  }

  /**
   * @see com.restfb.FacebookClient#fetchObject(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("object", object);
    verifyParameterPresence("objectType", objectType);
    return jsonMapper.toJavaObject(makeRequest(object, parameters), objectType);
  }

  /**
   * @see com.restfb.FacebookClient#fetchObjects(java.util.List, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T> T fetchObjects(List<String> ids, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("ids", ids);
    verifyParameterPresence("connectionType", objectType);

    if (ids.isEmpty()) {
      throw new IllegalArgumentException("The list of IDs cannot be empty.");
    }

    for (Parameter parameter : parameters) {
      if (IDS_PARAM_NAME.equals(parameter.name)) {
        throw new IllegalArgumentException("You cannot specify the '" + IDS_PARAM_NAME + "' URL parameter yourself - "
            + "RestFB will populate this for you with " + "the list of IDs you passed to this method.");
      }
    }

    // Normalize the IDs
    for (int i = 0; i < ids.size(); i++) {
      String id = ids.get(i).trim().toLowerCase();
      if ("".equals(id)) {
        throw new IllegalArgumentException("The list of IDs cannot contain blank strings.");
      }

      ids.set(i, id);
    }

    try {
      String jsonString =
          makeRequest("", parametersWithAdditionalParameter(Parameter.with(IDS_PARAM_NAME, join(ids)), parameters));

      return jsonMapper.toJavaObject(jsonString, objectType);
    } catch (JsonException e) {
      throw new FacebookJsonMappingException("Unable to map connection JSON to Java objects", e);
    }
  }

  /**
   * @see com.restfb.FacebookClient#publish(java.lang.String, java.lang.Class, com.restfb.BinaryAttachment,
   *      com.restfb.Parameter[])
   */
  @Override
  public <T> T publish(String connection, Class<T> objectType, List<BinaryAttachment> binaryAttachments,
      Parameter... parameters) {
    verifyParameterPresence("connection", connection);

    return jsonMapper.toJavaObject(makeRequest(connection, true, false, binaryAttachments, parameters), objectType);
  }

  /**
   * @see com.restfb.FacebookClient#publish(java.lang.String, java.lang.Class, com.restfb.BinaryAttachment,
   *      com.restfb.Parameter[])
   */
  @Override
  public <T> T publish(String connection, Class<T> objectType, BinaryAttachment binaryAttachment,
      Parameter... parameters) {

    List<BinaryAttachment> attachments = null;
    if (binaryAttachment != null) {
      attachments = new ArrayList<BinaryAttachment>();
      attachments.add(binaryAttachment);
    }

    return publish(connection, objectType, attachments, parameters);
  }

  /**
   * @see com.restfb.FacebookClient#publish(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> T publish(String connection, Class<T> objectType, Parameter... parameters) {
    return publish(connection, objectType, (List<BinaryAttachment>) null, parameters);
  }

  @Override
  @Deprecated
  @SuppressWarnings("unchecked")
  public <T> T executeMultiquery(Map<String, String> queries, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("objectType", objectType);

    for (Parameter parameter : parameters) {
      if (QUERIES_PARAM_NAME.equals(parameter.name)) {
        throw new IllegalArgumentException("You cannot specify the '" + QUERIES_PARAM_NAME
            + "' URL parameter yourself - " + "RestFB will populate this for you with "
            + "the queries you passed to this method.");
      }
    }

    try {
      JsonArray jsonArray =
          new JsonArray(makeRequest("fql.multiquery", false, false, null,
            parametersWithAdditionalParameter(Parameter.with(QUERIES_PARAM_NAME, queriesToJson(queries)), parameters)));

      JsonObject normalizedJson = new JsonObject();

      for (int i = 0; i < jsonArray.length(); i++) {
        JsonObject jsonObject = jsonArray.getJsonObject(i);

        // For empty resultsets, Facebook will return an empty object instead of
        // an empty list. Hack around that here.
        JsonArray resultsArray =
            jsonObject.get("fql_result_set") instanceof JsonArray ? jsonObject.getJsonArray("fql_result_set")
                : new JsonArray();

        normalizedJson.put(jsonObject.getString("name"), resultsArray);
      }

      return objectType.equals(JsonObject.class) ? (T) normalizedJson : jsonMapper.toJavaObject(
        normalizedJson.toString(), objectType);
    } catch (JsonException e) {
      throw new FacebookJsonMappingException("Unable to process fql.multiquery JSON response", e);
    }
  }

  /**
   * @see com.restfb.FacebookClient#executeQuery(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  @Deprecated
  public <T> List<T> executeQuery(String query, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("query", query);
    verifyParameterPresence("objectType", objectType);

    for (Parameter parameter : parameters) {
      if (QUERY_PARAM_NAME.equals(parameter.name)) {
        throw new IllegalArgumentException("You cannot specify the '" + QUERY_PARAM_NAME
            + "' URL parameter yourself - " + "RestFB will populate this for you with "
            + "the query you passed to this method.");
      }
    }

    return jsonMapper.toJavaList(
      makeRequest("fql.query", false, false, null,
        parametersWithAdditionalParameter(Parameter.with(QUERY_PARAM_NAME, query), parameters)), objectType);
  }

  /**
   * @see com.restfb.FacebookClient#executeFqlQuery(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> List<T> executeFqlQuery(String query, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("query", query);
    verifyParameterPresence("objectType", objectType);

    for (Parameter parameter : parameters) {
      if (FQL_QUERY_PARAM_NAME.equals(parameter.name)) {
        throw new IllegalArgumentException("You cannot specify the '" + FQL_QUERY_PARAM_NAME
            + "' URL parameter yourself - " + "RestFB will populate this for you with "
            + "the query you passed to this method.");
      }
    }

    return jsonMapper.toJavaList(
      makeRequest("fql", false, false, null,
        parametersWithAdditionalParameter(Parameter.with(FQL_QUERY_PARAM_NAME, query), parameters)), objectType);
  }

  @Override
  public String getLogoutUrl(String next) {
    Parameter p = null;
    String parameterString;
    if (next != null) {
      p = Parameter.with("next", next);
      parameterString = toParameterString(false, p);
    } else {
      parameterString = toParameterString(false);
    }

    final String fullEndPoint = createEndpointForApiCall("logout.php", false);
    return fullEndPoint + "?" + parameterString;
  }

  /**
   * @see com.restfb.FacebookClient#executeFqlMultiquery(java.util.Map, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T> T executeFqlMultiquery(Map<String, String> queries, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("objectType", objectType);

    for (Parameter parameter : parameters) {
      if (FQL_QUERY_PARAM_NAME.equals(parameter.name)) {
        throw new IllegalArgumentException("You cannot specify the '" + FQL_QUERY_PARAM_NAME
            + "' URL parameter yourself - " + "RestFB will populate this for you with "
            + "the queries you passed to this method.");
      }
    }

    try {
      List<JsonObject> jsonObjects =
          jsonMapper.toJavaList(
            makeRequest(
              "fql",
              false,
              false,
              null,
              parametersWithAdditionalParameter(Parameter.with(FQL_QUERY_PARAM_NAME, queriesToJson(queries)),
                parameters)), JsonObject.class);

      JsonObject normalizedJson = new JsonObject();

      for (int i = 0; i < jsonObjects.size(); i++) {
        JsonObject jsonObject = jsonObjects.get(i);

        // For empty resultsets, Facebook will return an empty object instead of
        // an empty list. Hack around that here.
        JsonArray resultsArray =
            jsonObject.get("fql_result_set") instanceof JsonArray ? jsonObject.getJsonArray("fql_result_set")
                : new JsonArray();

        normalizedJson.put(jsonObject.getString("name"), resultsArray);
      }

      return objectType.equals(JsonObject.class) ? (T) normalizedJson : jsonMapper.toJavaObject(
        normalizedJson.toString(), objectType);
    } catch (JsonException e) {
      throw new FacebookJsonMappingException("Unable to process fql.multiquery JSON response", e);
    }
  }

  /**
   * @see com.restfb.FacebookClient#executeBatch(com.restfb.batch.BatchRequest[])
   */
  @Override
  public List<BatchResponse> executeBatch(BatchRequest... batchRequests) {
    return executeBatch(asList(batchRequests), Collections.<BinaryAttachment> emptyList());
  }

  /**
   * @see com.restfb.FacebookClient#executeBatch(java.util.List)
   */
  @Override
  public List<BatchResponse> executeBatch(List<BatchRequest> batchRequests) {
    return executeBatch(batchRequests, Collections.<BinaryAttachment> emptyList());
  }

  /**
   * @see com.restfb.FacebookClient#executeBatch(java.util.List, java.util.List)
   */
  @Override
  public List<BatchResponse> executeBatch(List<BatchRequest> batchRequests, List<BinaryAttachment> binaryAttachments) {
    verifyParameterPresence("binaryAttachments", binaryAttachments);

    if (batchRequests == null || batchRequests.isEmpty()) {
      throw new IllegalArgumentException("You must specify at least one batch request.");
    }

    return jsonMapper.toJavaList(
      makeRequest("", true, false, binaryAttachments, Parameter.with("batch", jsonMapper.toJson(batchRequests, true))),
      BatchResponse.class);
  }

  /**
   * @see com.restfb.FacebookClient#convertSessionKeysToAccessTokens(java.lang.String, java.lang.String,
   *      java.lang.String[])
   */
  @Override
  public List<AccessToken> convertSessionKeysToAccessTokens(String appId, String secretKey, String... sessionKeys) {
    verifyParameterPresence("appId", appId);
    verifyParameterPresence("secretKey", secretKey);

    if (sessionKeys == null || sessionKeys.length == 0) {
      return emptyList();
    }

    String json =
        makeRequest("/oauth/exchange_sessions", true, false, null, Parameter.with("client_id", appId),
          Parameter.with("client_secret", secretKey), Parameter.with("sessions", join(sessionKeys)));

    return jsonMapper.toJavaList(json, AccessToken.class);
  }

  /**
   * @see com.restfb.FacebookClient#obtainAppAccessToken(java.lang.String, java.lang.String)
   */
  @Override
  public AccessToken obtainAppAccessToken(String appId, String appSecret) {
    verifyParameterPresence("appId", appId);
    verifyParameterPresence("appSecret", appSecret);

    String response =
        makeRequest("oauth/access_token", Parameter.with("grant_type", "client_credentials"),
          Parameter.with("client_id", appId), Parameter.with("client_secret", appSecret));

    try {
      return getAccessTokenFromResponse(response);
    } catch (Throwable t) {
      throw new FacebookResponseContentException("Unable to extract access token from response.", t);
    }
  }

  @Override
  public DeviceCode fetchDeviceCode(String appId, ScopeBuilder scope) {
    verifyParameterPresence("appId", appId);
    verifyParameterPresence("scope", scope);

    String response =
        makeRequest("oauth/device", true, false, null, Parameter.with("type", "device_code"),
          Parameter.with("client_id", appId), Parameter.with("scope", scope.toString()));
    return jsonMapper.toJavaObject(response, DeviceCode.class);
  }

  @Override
  public AccessToken obtainDeviceAccessToken(String appId, String code) throws FacebookDeviceTokenCodeExpiredException,
      FacebookDeviceTokenPendingException, FacebookDeviceTokenDeclinedException, FacebookDeviceTokenSlowdownException {
    verifyParameterPresence("appId", appId);
    verifyParameterPresence("code", code);

    try {
      String response =
          makeRequest("oauth/device", true, false, null, Parameter.with("type", "device_token"),
            Parameter.with("client_id", appId), Parameter.with("code", code));
      return getAccessTokenFromResponse(response);
    } catch (FacebookOAuthException foae) {
      DeviceTokenExceptionFactory.createFrom(foae);
      return null;
    }
  }

  /**
   * @see com.restfb.FacebookClient#obtainUserAccessToken(java.lang.String, java.lang.String, java.lang.String,
   *      java.lang.String)
   */
  @Override
  public AccessToken obtainUserAccessToken(String appId, String appSecret, String redirectUri, String verificationCode) {
    verifyParameterPresence("appId", appId);
    verifyParameterPresence("appSecret", appSecret);
    verifyParameterPresence("verificationCode", verificationCode);

    String response =
        makeRequest("oauth/access_token", Parameter.with("client_id", appId),
          Parameter.with("client_secret", appSecret), Parameter.with("code", verificationCode),
          Parameter.with("redirect_uri", redirectUri));

    try {
      return getAccessTokenFromResponse(response);
    } catch (Throwable t) {
      throw new FacebookResponseContentException("Unable to extract access token from response.", t);
    }
  }

  /**
   * @see com.restfb.FacebookClient#obtainExtendedAccessToken(java.lang.String, java.lang.String)
   */
  @Override
  public AccessToken obtainExtendedAccessToken(String appId, String appSecret) {
    if (accessToken == null) {
      throw new IllegalStateException(format(
        "You cannot call this method because you did not construct this instance of %s with an access token.",
        getClass().getSimpleName()));
    }

    return obtainExtendedAccessToken(appId, appSecret, accessToken);
  }

  /**
   * @see com.restfb.FacebookClient#obtainExtendedAccessToken(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public AccessToken obtainExtendedAccessToken(String appId, String appSecret, String accessToken) {
    verifyParameterPresence("appId", appId);
    verifyParameterPresence("appSecret", appSecret);
    verifyParameterPresence("accessToken", accessToken);

    String response =
        makeRequest("/oauth/access_token", false, false, null, Parameter.with("client_id", appId),
          Parameter.with("client_secret", appSecret), Parameter.with("grant_type", "fb_exchange_token"),
          Parameter.with("fb_exchange_token", accessToken));

    try {
      return getAccessTokenFromResponse(response);
    } catch (Throwable t) {
      throw new FacebookResponseContentException("Unable to extract access token from response.", t);
    }
  }

  private AccessToken getAccessTokenFromResponse(String response) {
    try {
      return getJsonMapper().toJavaObject(response, AccessToken.class);
    } catch (FacebookJsonMappingException fjme) {
      return AccessToken.fromQueryString(response);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T parseSignedRequest(String signedRequest, String appSecret, Class<T> objectType) {
    verifyParameterPresence("signedRequest", signedRequest);
    verifyParameterPresence("appSecret", appSecret);
    verifyParameterPresence("objectType", objectType);

    String[] signedRequestTokens = signedRequest.split("[.]");

    if (signedRequestTokens.length != 2) {
      throw new FacebookSignedRequestParsingException(format(
        "Signed request '%s' is expected to be signature and payload strings separated by a '.'", signedRequest));
    }

    String encodedSignature = signedRequestTokens[0];
    String urlDecodedSignature = urlDecodeSignedRequestToken(encodedSignature);
    byte[] signature = decodeBase64(urlDecodedSignature);

    String encodedPayload = signedRequestTokens[1];
    String urlDecodedPayload = urlDecodeSignedRequestToken(encodedPayload);
    String payload = StringUtils.toString(decodeBase64(urlDecodedPayload));

    // Convert payload to a JsonObject so we can pull algorithm data out of it
    JsonObject payloadObject = getJsonMapper().toJavaObject(payload, JsonObject.class);

    if (!payloadObject.has("algorithm")) {
      throw new FacebookSignedRequestParsingException("Unable to detect algorithm used for signed request");
    }

    String algorithm = payloadObject.getString("algorithm");

    if (!verifySignedRequest(appSecret, algorithm, encodedPayload, signature)) {
      throw new FacebookSignedRequestVerificationException(
        "Signed request verification failed. Are you sure the request was made for the app identified by the app secret you provided?");
    }

    // Marshal to the user's preferred type.
    // If the user asked for a JsonObject, send back the one we already parsed.
    return objectType.equals(JsonObject.class) ? (T) payloadObject : getJsonMapper().toJavaObject(payload, objectType);
  }

  /**
   * Decodes a component of a signed request received from Facebook using FB's special URL-encoding strategy.
   * 
   * @param signedRequestToken
   *          Token to decode.
   * @return The decoded token.
   */
  protected String urlDecodeSignedRequestToken(String signedRequestToken) {
    verifyParameterPresence("signedRequestToken", signedRequestToken);
    return signedRequestToken.replace("-", "+").replace("_", "/").trim();
  }

  @Override
  public String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope) {
    verifyParameterPresence("appId", appId);
    verifyParameterPresence("redirectUri", redirectUri);
    verifyParameterPresence("scope", scope);

    String dialogUrl = FACEBOOK_ENDPOINT_URL + "/dialog/oauth";

    Parameter clientId = Parameter.with("client_id", appId);
    Parameter redirectUriParameter = Parameter.with("redirect_uri", redirectUri);
    Parameter scopeParam = Parameter.with("scope", scope.toString());

    return dialogUrl + "?" + toParameterString(false, clientId, redirectUriParameter, scopeParam);
  }

  /**
   * Verifies that the signed request is really from Facebook.
   * 
   * @param appSecret
   *          The secret for the app that can verify this signed request.
   * @param algorithm
   *          Signature algorithm specified by FB in the decoded payload.
   * @param encodedPayload
   *          The encoded payload used to generate a signature for comparison against the provided {@code signature}.
   * @param signature
   *          The decoded signature extracted from the signed request. Compared against a signature generated from
   *          {@code encodedPayload}.
   * @return {@code true} if the signed request is verified, {@code false} if not.
   */
  protected boolean verifySignedRequest(String appSecret, String algorithm, String encodedPayload, byte[] signature) {
    verifyParameterPresence("appSecret", appSecret);
    verifyParameterPresence("algorithm", algorithm);
    verifyParameterPresence("encodedPayload", encodedPayload);
    verifyParameterPresence("signature", signature);

    // Normalize algorithm name...FB calls it differently than Java does
    if ("HMAC-SHA256".equalsIgnoreCase(algorithm)) {
      algorithm = "HMACSHA256";
    }

    try {
      Mac mac = Mac.getInstance(algorithm);
      mac.init(new SecretKeySpec(toBytes(appSecret), algorithm));
      byte[] payloadSignature = mac.doFinal(toBytes(encodedPayload));
      return Arrays.equals(signature, payloadSignature);
    } catch (Exception e) {
      throw new FacebookSignedRequestVerificationException("Unable to perform signed request verification", e);
    }
  }

  /**
   * @see com.restfb.FacebookClient#debugToken(java.lang.String)
   */
  @Override
  public DebugTokenInfo debugToken(String inputToken) {
    verifyParameterPresence("inputToken", inputToken);

    String response = makeRequest("/debug_token", Parameter.with("input_token", inputToken));
    JsonObject json = new JsonObject(response);
    JsonObject data = json.getJsonObject("data");

    try {
      return getJsonMapper().toJavaObject(data.toString(), DebugTokenInfo.class);
    } catch (Throwable t) {
      throw new FacebookResponseContentException("Unable to parse JSON from response.", t);
    }
  }

  /**
   * @see com.restfb.FacebookClient#getJsonMapper()
   */
  @Override
  public JsonMapper getJsonMapper() {
    return jsonMapper;
  }

  /**
   * @see com.restfb.FacebookClient#getWebRequestor()
   */
  @Override
  public WebRequestor getWebRequestor() {
    return webRequestor;
  }

  /**
   * Coordinates the process of executing the API request GET/POST and processing the response we receive from the
   * endpoint.
   * 
   * @param endpoint
   *          Facebook Graph API endpoint.
   * @param parameters
   *          Arbitrary number of parameters to send along to Facebook as part of the API call.
   * @return The JSON returned by Facebook for the API call.
   * @throws FacebookException
   *           If an error occurs while making the Facebook API POST or processing the response.
   */
  protected String makeRequest(String endpoint, Parameter... parameters) {
    return makeRequest(endpoint, false, false, null, parameters);
  }

  /**
   * Coordinates the process of executing the API request GET/POST and processing the response we receive from the
   * endpoint.
   * 
   * @param endpoint
   *          Facebook Graph API endpoint.
   * @param executeAsPost
   *          {@code true} to execute the web request as a {@code POST}, {@code false} to execute as a {@code GET}.
   * @param executeAsDelete
   *          {@code true} to add a special 'treat this request as a {@code DELETE}' parameter.
   * @param binaryAttachments
   *          A list of binary files to include in a {@code POST} request. Pass {@code null} if no attachment should be
   *          sent.
   * @param parameters
   *          Arbitrary number of parameters to send along to Facebook as part of the API call.
   * @return The JSON returned by Facebook for the API call.
   * @throws FacebookException
   *           If an error occurs while making the Facebook API POST or processing the response.
   */
  protected String makeRequest(String endpoint, final boolean executeAsPost, final boolean executeAsDelete,
      final List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    verifyParameterLegality(parameters);

    if (executeAsDelete && isHttpDeleteFallback()) {
      parameters = parametersWithAdditionalParameter(Parameter.with(METHOD_PARAM_NAME, "delete"), parameters);
    }

    if (!endpoint.startsWith("/")) {
      endpoint = "/" + endpoint;
    }

    final String fullEndpoint =
        createEndpointForApiCall(endpoint, binaryAttachments != null && binaryAttachments.size() > 0);
    final String parameterString = toParameterString(parameters);

    return makeRequestAndProcessResponse(new Requestor() {
      /**
       * @see com.restfb.DefaultFacebookClient.Requestor#makeRequest()
       */
      @Override
      public Response makeRequest() throws IOException {
        if (executeAsDelete && !isHttpDeleteFallback()) {
          return webRequestor.executeDelete(fullEndpoint + "?" + parameterString);
        } else {
          return executeAsPost ? webRequestor.executePost(
            fullEndpoint,
            parameterString,
            binaryAttachments == null ? null
                : binaryAttachments.toArray(new BinaryAttachment[binaryAttachments.size()])) : webRequestor
            .executeGet(fullEndpoint + "?" + parameterString);
        }
      }
    });
  }

  /**
   * @see com.restfb.FacebookClient#obtainAppSecretProof(java.lang.String, java.lang.String)
   */
  @Override
  public String obtainAppSecretProof(String accessToken, String appSecret) {
    verifyParameterPresence("accessToken", accessToken);
    verifyParameterPresence("appSecret", appSecret);

    try {
      byte[] key = appSecret.getBytes(Charset.forName("UTF-8"));
      SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(signingKey);
      byte[] raw = mac.doFinal(accessToken.getBytes());
      byte[] hex = encodeHex(raw);
      return new String(hex, "UTF-8");
    } catch (Exception e) {
      throw new IllegalStateException("Creation of appsecret_proof has failed", e);
    }
  }

  /**
   * returns if the fallback post method (<code>true</code>) is used or the http delete (<code>false</code>)
   * 
   * @return
   */
  public boolean isHttpDeleteFallback() {
    return httpDeleteFallback;
  }

  /**
   * Set to <code>true</code> if the facebook http delete fallback should be used. Facebook allows to use the http POST
   * with the parameter "method=delete" to override the post and use delete instead. This feature allow http client that
   * do not support the whole http method set, to delete objects from facebook
   * 
   * @param httpDeleteFallback
   */
  public void setHttpDeleteFallback(boolean httpDeleteFallback) {
    this.httpDeleteFallback = httpDeleteFallback;
  }

  protected static interface Requestor {
    Response makeRequest() throws IOException;
  }

  protected String makeRequestAndProcessResponse(Requestor requestor) {
    Response response = null;

    // Perform a GET or POST to the API endpoint
    try {
      response = requestor.makeRequest();
    } catch (Throwable t) {
      throw new FacebookNetworkException("Facebook request failed", t);
    }

    // If we get any HTTP response code other than a 200 OK or 400 Bad Request
    // or 401 Not Authorized or 403 Forbidden or 404 Not Found or 500 Internal
    // Server Error or 302 Not Modified
    // throw an exception.
    if (HTTP_OK != response.getStatusCode() && HTTP_BAD_REQUEST != response.getStatusCode()
        && HTTP_UNAUTHORIZED != response.getStatusCode() && HTTP_NOT_FOUND != response.getStatusCode()
        && HTTP_INTERNAL_ERROR != response.getStatusCode() && HTTP_FORBIDDEN != response.getStatusCode()
        && HTTP_NOT_MODIFIED != response.getStatusCode()) {
      throw new FacebookNetworkException("Facebook request failed", response.getStatusCode());
    }

    String json = response.getBody();

    // If the response contained an error code, throw an exception.
    throwFacebookResponseStatusExceptionIfNecessary(json, response.getStatusCode());

    // If there was no response error information and this was a 500 or 401
    // error, something weird happened on Facebook's end. Bail.
    if (HTTP_INTERNAL_ERROR == response.getStatusCode() || HTTP_UNAUTHORIZED == response.getStatusCode()) {
      throw new FacebookNetworkException("Facebook request failed", response.getStatusCode());
    }

    return json;
  }

  /**
   * Throws an exception if Facebook returned an error response. Using the Graph API, it's possible to see both the new
   * Graph API-style errors as well as Legacy API-style errors, so we have to handle both here. This method extracts
   * relevant information from the error JSON and throws an exception which encapsulates it for end-user consumption.
   * <p>
   * For Graph API errors:
   * <p>
   * If the {@code error} JSON field is present, we've got a response status error for this API call.
   * <p>
   * For Legacy errors (e.g. FQL):
   * <p>
   * If the {@code error_code} JSON field is present, we've got a response status error for this API call.
   * 
   * @param json
   *          The JSON returned by Facebook in response to an API call.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @throws FacebookGraphException
   *           If the JSON contains a Graph API error response.
   * @throws FacebookResponseStatusException
   *           If the JSON contains an Legacy API error response.
   * @throws FacebookJsonMappingException
   *           If an error occurs while processing the JSON.
   */
  protected void throwFacebookResponseStatusExceptionIfNecessary(String json, Integer httpStatusCode) {
    // If we have a legacy exception, throw it.
    throwLegacyFacebookResponseStatusExceptionIfNecessary(json, httpStatusCode);

    // If we have a batch API exception, throw it.
    throwBatchFacebookResponseStatusExceptionIfNecessary(json, httpStatusCode);

    try {
      // If the result is not an object, bail immediately.
      if (!json.startsWith("{"))
        return;

      int subStrEnd = Math.min(50, json.length());
      if (!json.substring(0, subStrEnd).contains("\"error\"")) {
        return; // no need to parse json
      }

      JsonObject errorObject = new JsonObject(json);

      if (!errorObject.has(ERROR_ATTRIBUTE_NAME))
        return;

      JsonObject innerErrorObject = errorObject.getJsonObject(ERROR_ATTRIBUTE_NAME);

      // If there's an Integer error code, pluck it out.
      Integer errorCode =
          innerErrorObject.has(ERROR_CODE_ATTRIBUTE_NAME) ? toInteger(innerErrorObject
            .getString(ERROR_CODE_ATTRIBUTE_NAME)) : null;
      Integer errorSubcode =
          innerErrorObject.has(ERROR_SUBCODE_ATTRIBUTE_NAME) ? toInteger(innerErrorObject
            .getString(ERROR_SUBCODE_ATTRIBUTE_NAME)) : null;

      throw graphFacebookExceptionMapper.exceptionForTypeAndMessage(errorCode, errorSubcode, httpStatusCode,
        innerErrorObject.optString(ERROR_TYPE_ATTRIBUTE_NAME),
        innerErrorObject.getString(ERROR_MESSAGE_ATTRIBUTE_NAME),
        innerErrorObject.optString(ERROR_USER_TITLE_ATTRIBUTE_NAME),
        innerErrorObject.optString(ERROR_USER_MSG_ATTRIBUTE_NAME));
    } catch (JsonException e) {
      throw new FacebookJsonMappingException("Unable to process the Facebook API response", e);
    }
  }

  /**
   * If the {@code error} and {@code error_description} JSON fields are present, we've got a response status error for
   * this batch API call. Extracts relevant information from the JSON and throws an exception which encapsulates it for
   * end-user consumption.
   * 
   * @param json
   *          The JSON returned by Facebook in response to a batch API call.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @throws FacebookResponseStatusException
   *           If the JSON contains an error code.
   * @throws FacebookJsonMappingException
   *           If an error occurs while processing the JSON.
   * @since 1.6.5
   */
  protected void throwBatchFacebookResponseStatusExceptionIfNecessary(String json, Integer httpStatusCode) {
    try {
      // If this is not an object, it's not an error response.
      if (!json.startsWith("{"))
        return;

      int subStrEnd = Math.min(50, json.length());
      if (!json.substring(0, subStrEnd).contains("\"error\"")) {
        return; // no need to parse json
      }

      JsonObject errorObject = null;

      // We need to swallow exceptions here because it's possible to get a legit
      // Facebook response that contains illegal JSON (e.g.
      // users.getLoggedInUser returning 1240077) - we're only interested in
      // whether or not there's an error_code field present.
      try {
        errorObject = new JsonObject(json);
      } catch (JsonException e) {}

      if (errorObject == null || !errorObject.has(BATCH_ERROR_ATTRIBUTE_NAME)
          || !errorObject.has(BATCH_ERROR_DESCRIPTION_ATTRIBUTE_NAME))
        return;

      throw legacyFacebookExceptionMapper.exceptionForTypeAndMessage(errorObject.getInt(BATCH_ERROR_ATTRIBUTE_NAME),
        null, httpStatusCode, null, errorObject.getString(BATCH_ERROR_DESCRIPTION_ATTRIBUTE_NAME), null, null);
    } catch (JsonException e) {
      throw new FacebookJsonMappingException("Unable to process the Facebook API response", e);
    }
  }

  /**
   * Specifies how we map Graph API exception types/messages to real Java exceptions.
   * <p>
   * Uses an instance of {@link DefaultGraphFacebookExceptionMapper} by default.
   * 
   * @return An instance of the exception mapper we should use.
   * @since 1.6
   */
  protected FacebookExceptionMapper createGraphFacebookExceptionMapper() {
    return new DefaultGraphFacebookExceptionMapper();
  }

  /**
   * A canned implementation of {@code FacebookExceptionMapper} that maps Graph API exceptions.
   * <p>
   * Thanks to BatchFB's Jeff Schnitzer for doing some of the legwork to find these exception type names.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.3
   */
  protected static class DefaultGraphFacebookExceptionMapper implements FacebookExceptionMapper {
    /**
     * @see com.restfb.exception.FacebookExceptionMapper#exceptionForTypeAndMessage(java.lang.Integer,
     *      java.lang.Integer, java.lang.String, java.lang.String)
     */
    public FacebookException exceptionForTypeAndMessage(Integer errorCode, Integer errorSubcode,
        Integer httpStatusCode, String type, String message, String errorUserTitle, String errorUserMessage) {
      if ("OAuthException".equals(type) || "OAuthAccessTokenException".equals(type)) {
        return new FacebookOAuthException(type, message, errorCode, errorSubcode, httpStatusCode, errorUserTitle,
          errorUserMessage);
      }

      if ("QueryParseException".equals(type)) {
        return new FacebookQueryParseException(type, message, errorCode, errorSubcode, httpStatusCode, errorUserTitle,
          errorUserMessage);
      }

      // Don't recognize this exception type? Just go with the standard
      // FacebookGraphException.
      return new FacebookGraphException(type, message, errorCode, errorSubcode, httpStatusCode, errorUserTitle,
        errorUserMessage);
    }
  }

  /**
   * Generate the parameter string to be included in the Facebook API request.
   * 
   * @param parameters
   *          Arbitrary number of extra parameters to include in the request.
   * @return The parameter string to include in the Facebook API request.
   * @throws FacebookJsonMappingException
   *           If an error occurs when building the parameter string.
   */
  protected String toParameterString(Parameter... parameters) {
    return toParameterString(true, parameters);
  }

  /**
   * Generate the parameter string to be included in the Facebook API request.
   * 
   * @param withJsonParameter
   *          add additional parameter format with type json
   * @param parameters
   *          Arbitrary number of extra parameters to include in the request.
   * @return The parameter string to include in the Facebook API request.
   * @throws FacebookJsonMappingException
   *           If an error occurs when building the parameter string.
   */
  protected String toParameterString(boolean withJsonParameter, Parameter... parameters) {
    if (!isBlank(accessToken)) {
      parameters = parametersWithAdditionalParameter(Parameter.with(ACCESS_TOKEN_PARAM_NAME, accessToken), parameters);
    }

    if (!isBlank(accessToken) && !isBlank(appSecret)) {
      parameters =
          parametersWithAdditionalParameter(
            Parameter.with(APP_SECRET_PROOF_PARAM_NAME, obtainAppSecretProof(accessToken, appSecret)), parameters);
    }

    if (withJsonParameter) {
      parameters = parametersWithAdditionalParameter(Parameter.with(FORMAT_PARAM_NAME, "json"), parameters);
    }

    StringBuilder parameterStringBuilder = new StringBuilder();
    boolean first = true;

    for (Parameter parameter : parameters) {
      if (first) {
        first = false;
      } else {
        parameterStringBuilder.append("&");
      }

      parameterStringBuilder.append(urlEncode(parameter.name));
      parameterStringBuilder.append("=");
      parameterStringBuilder.append(urlEncodedValueForParameterName(parameter.name, parameter.value));
    }

    return parameterStringBuilder.toString();
  }

  /**
   * @see com.restfb.BaseFacebookClient#createEndpointForApiCall(java.lang.String,boolean)
   */
  @Override
  protected String createEndpointForApiCall(String apiCall, boolean hasAttachment) {
    while (apiCall.startsWith("/"))
      apiCall = apiCall.substring(1);

    String baseUrl = getFacebookGraphEndpointUrl();

    if (readOnlyApiCalls.contains(apiCall)) {
      baseUrl = getFacebookReadOnlyEndpointUrl();
    } else if (hasAttachment && (apiCall.endsWith("/videos") || apiCall.endsWith("/advideos"))) {
      baseUrl = getFacebookGraphVideoEndpointUrl();
    } else if (apiCall.endsWith("logout.php")) {
      baseUrl = FACEBOOK_ENDPOINT_URL;
    }

    return format("%s/%s", baseUrl, apiCall);
  }

  /**
   * Returns the base endpoint URL for the Graph API.
   * 
   * @return The base endpoint URL for the Graph API.
   */
  protected String getFacebookGraphEndpointUrl() {
    if (apiVersion.isUrlElementRequired()) {
      return FACEBOOK_GRAPH_ENDPOINT_URL + '/' + apiVersion.getUrlElement();
    } else {
      return FACEBOOK_GRAPH_ENDPOINT_URL;
    }
  }

  /**
   * Returns the base endpoint URL for the Graph API's video upload functionality.
   * 
   * @return The base endpoint URL for the Graph API's video upload functionality.
   * @since 1.6.5
   */
  protected String getFacebookGraphVideoEndpointUrl() {
    if (apiVersion.isUrlElementRequired()) {
      return FACEBOOK_GRAPH_VIDEO_ENDPOINT_URL + '/' + apiVersion.getUrlElement();
    } else {
      return FACEBOOK_GRAPH_VIDEO_ENDPOINT_URL;
    }
  }

  @Override
  protected String getFacebookReadOnlyEndpointUrl() {
    if (apiVersion.isUrlElementRequired()) {
      return FACEBOOK_READ_ONLY_ENDPOINT_URL + '/' + apiVersion.getUrlElement();
    } else {
      return FACEBOOK_READ_ONLY_ENDPOINT_URL;
    }
  }
}
