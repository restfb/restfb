/*
 * Copyright (c) 2010-2013 Mark Allen.
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
import static com.restfb.util.StringUtils.isBlank;
import static com.restfb.util.StringUtils.join;
import static com.restfb.util.StringUtils.toBytes;
import static com.restfb.util.StringUtils.toInteger;
import static com.restfb.util.StringUtils.trimToEmpty;
import static com.restfb.util.StringUtils.trimToNull;
import static com.restfb.util.UrlUtils.urlEncode;
import static java.lang.String.format;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.IOException;
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
import com.restfb.json.JsonArray;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;
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
   * Knows how to map Graph API exceptions to formal Java exception types.
   */
  protected FacebookExceptionMapper graphFacebookExceptionMapper;

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
   * Creates a Facebook Graph API client with no access token.
   * <p>
   * Without an access token, you can view and search public graph data but can't do much else.
   */
  public DefaultFacebookClient() {
    this(null);
  }

  /**
   * Creates a Facebook Graph API client with the given {@code accessToken}.
   * 
   * @param accessToken
   *          A Facebook OAuth access token.
   */
  public DefaultFacebookClient(String accessToken) {
    this(accessToken, new DefaultWebRequestor(), new DefaultJsonMapper());
  }

  /**
   * Creates a Facebook Graph API client with the given {@code accessToken}, {@code webRequestor}, and
   * {@code jsonMapper}.
   * 
   * @param accessToken
   *          A Facebook OAuth access token.
   * @param webRequestor
   *          The {@link WebRequestor} implementation to use for sending requests to the API endpoint.
   * @param jsonMapper
   *          The {@link JsonMapper} implementation to use for mapping API response JSON to Java objects.
   * @throws NullPointerException
   *           If {@code jsonMapper} or {@code webRequestor} is {@code null}.
   */
  public DefaultFacebookClient(String accessToken, WebRequestor webRequestor, JsonMapper jsonMapper) {
    super();

    verifyParameterPresence("jsonMapper", jsonMapper);
    verifyParameterPresence("webRequestor", webRequestor);

    this.accessToken = trimToNull(accessToken);
    this.webRequestor = webRequestor;
    this.jsonMapper = jsonMapper;
    graphFacebookExceptionMapper = createGraphFacebookExceptionMapper();

    illegalParamNames.addAll(Arrays
      .asList(new String[] { ACCESS_TOKEN_PARAM_NAME, METHOD_PARAM_NAME, FORMAT_PARAM_NAME }));
  }

  /**
   * @see com.restfb.FacebookClient#deleteObject(java.lang.String, com.restfb.Parameter[])
   */
  @Override
  public boolean deleteObject(String object, Parameter... parameters) {
    verifyParameterPresence("object", object);
    return "true".equals(makeRequest(object, true, true, null, parameters));
  }

  /**
   * @see com.restfb.FacebookClient#fetchConnection(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  public <T> Connection<T> fetchConnection(String connection, Class<T> connectionType, Parameter... parameters) {
    verifyParameterPresence("connection", connection);
    verifyParameterPresence("connectionType", connectionType);
    return new Connection<T>(this, makeRequest(connection, parameters), connectionType);
  }

  /**
   * @see com.restfb.FacebookClient#fetchConnectionPage(java.lang.String, java.lang.Class)
   */
  public <T> Connection<T> fetchConnectionPage(final String connectionPageUrl, Class<T> connectionType) {
    String connectionJson = makeRequestAndProcessResponse(new Requestor() {
      /**
       * @see com.restfb.DefaultFacebookClient.Requestor#makeRequest()
       */
      public Response makeRequest() throws IOException {
        return webRequestor.executeGet(connectionPageUrl);
      }
    });

    return new Connection<T>(this, connectionJson, connectionType);
  }

  /**
   * @see com.restfb.FacebookClient#fetchObject(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  public <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("object", object);
    verifyParameterPresence("objectType", objectType);
    return jsonMapper.toJavaObject(makeRequest(object, parameters), objectType);
  }

  /**
   * @see com.restfb.FacebookClient#fetchObjects(java.util.List, java.lang.Class, com.restfb.Parameter[])
   */
  @SuppressWarnings("unchecked")
  public <T> T fetchObjects(List<String> ids, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("ids", ids);
    verifyParameterPresence("connectionType", objectType);

    if (ids.size() == 0)
      throw new IllegalArgumentException("The list of IDs cannot be empty.");

    for (Parameter parameter : parameters)
      if (IDS_PARAM_NAME.equals(parameter.name))
        throw new IllegalArgumentException("You cannot specify the '" + IDS_PARAM_NAME + "' URL parameter yourself - "
            + "RestFB will populate this for you with " + "the list of IDs you passed to this method.");

    // Normalize the IDs
    for (int i = 0; i < ids.size(); i++) {
      String id = ids.get(i).trim().toLowerCase();
      if ("".equals(id))
        throw new IllegalArgumentException("The list of IDs cannot contain blank strings.");
      ids.set(i, id);
    }

    try {
      JsonObject jsonObject =
          new JsonObject(makeRequest("",
            parametersWithAdditionalParameter(Parameter.with(IDS_PARAM_NAME, join(ids)), parameters)));

      return objectType.equals(JsonObject.class) ? (T) jsonObject : jsonMapper.toJavaObject(jsonObject.toString(),
        objectType);
    } catch (JsonException e) {
      throw new FacebookJsonMappingException("Unable to map connection JSON to Java objects", e);
    }
  }

  /**
   * @see com.restfb.FacebookClient#publish(java.lang.String, java.lang.Class, com.restfb.BinaryAttachment,
   *      com.restfb.Parameter[])
   */
  public <T> T publish(String connection, Class<T> objectType, BinaryAttachment binaryAttachment,
      Parameter... parameters) {
    verifyParameterPresence("connection", connection);

    List<BinaryAttachment> binaryAttachments = new ArrayList<BinaryAttachment>();
    if (binaryAttachment != null)
      binaryAttachments.add(binaryAttachment);

    return jsonMapper.toJavaObject(makeRequest(connection, true, false, binaryAttachments, parameters), objectType);
  }

  /**
   * @see com.restfb.FacebookClient#publish(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  public <T> T publish(String connection, Class<T> objectType, Parameter... parameters) {
    return publish(connection, objectType, null, parameters);
  }

  /**
   * @see com.restfb.FacebookClient#executeMultiquery(java.util.Map, java.lang.Class, com.restfb.Parameter[])
   */
  @SuppressWarnings("unchecked")
  public <T> T executeMultiquery(Map<String, String> queries, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("objectType", objectType);

    for (Parameter parameter : parameters)
      if (QUERIES_PARAM_NAME.equals(parameter.name))
        throw new IllegalArgumentException("You cannot specify the '" + QUERIES_PARAM_NAME
            + "' URL parameter yourself - " + "RestFB will populate this for you with "
            + "the queries you passed to this method.");

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
  public <T> List<T> executeQuery(String query, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("query", query);
    verifyParameterPresence("objectType", objectType);

    for (Parameter parameter : parameters)
      if (QUERY_PARAM_NAME.equals(parameter.name))
        throw new IllegalArgumentException("You cannot specify the '" + QUERY_PARAM_NAME
            + "' URL parameter yourself - " + "RestFB will populate this for you with "
            + "the query you passed to this method.");

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

    for (Parameter parameter : parameters)
      if (FQL_QUERY_PARAM_NAME.equals(parameter.name))
        throw new IllegalArgumentException("You cannot specify the '" + FQL_QUERY_PARAM_NAME
            + "' URL parameter yourself - " + "RestFB will populate this for you with "
            + "the query you passed to this method.");

    return jsonMapper.toJavaList(
      makeRequest("fql", false, false, null,
        parametersWithAdditionalParameter(Parameter.with(FQL_QUERY_PARAM_NAME, query), parameters)), objectType);
  }

  /**
   * @see com.restfb.FacebookClient#executeFqlMultiquery(java.util.Map, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T> T executeFqlMultiquery(Map<String, String> queries, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("objectType", objectType);

    for (Parameter parameter : parameters)
      if (FQL_QUERY_PARAM_NAME.equals(parameter.name))
        throw new IllegalArgumentException("You cannot specify the '" + FQL_QUERY_PARAM_NAME
            + "' URL parameter yourself - " + "RestFB will populate this for you with "
            + "the queries you passed to this method.");

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
  public List<BatchResponse> executeBatch(BatchRequest... batchRequests) {
    return executeBatch(asList(batchRequests), Collections.<BinaryAttachment> emptyList());
  }

  /**
   * @see com.restfb.FacebookClient#executeBatch(java.util.List, java.util.List)
   */
  public List<BatchResponse> executeBatch(List<BatchRequest> batchRequests, List<BinaryAttachment> binaryAttachments) {
    verifyParameterPresence("binaryAttachments", binaryAttachments);

    if (batchRequests == null || batchRequests.size() == 0)
      throw new IllegalArgumentException("You must specify at least one batch request.");

    return jsonMapper.toJavaList(
      makeRequest("", true, false, binaryAttachments, Parameter.with("batch", jsonMapper.toJson(batchRequests, true))),
      BatchResponse.class);
  }

  /**
   * @see com.restfb.FacebookClient#convertSessionKeysToAccessTokens(java.lang.String, java.lang.String,
   *      java.lang.String[])
   */
  public List<AccessToken> convertSessionKeysToAccessTokens(String appId, String secretKey, String... sessionKeys) {
    verifyParameterPresence("appId", appId);
    verifyParameterPresence("secretKey", secretKey);

    if (sessionKeys == null || sessionKeys.length == 0)
      return emptyList();

    String json =
        makeRequest("/oauth/exchange_sessions", true, false, null, Parameter.with("client_id", appId),
          Parameter.with("client_secret", secretKey), Parameter.with("sessions", join(sessionKeys)));

    return jsonMapper.toJavaList(json, AccessToken.class);
  }

  /**
   * @see com.restfb.FacebookClient#obtainAppAccessToken(java.lang.String, java.lang.String)
   */
  public AccessToken obtainAppAccessToken(String appId, String appSecret) {
    verifyParameterPresence("appId", appId);
    verifyParameterPresence("appSecret", appSecret);

    String response =
        makeRequest("oauth/access_token", Parameter.with("grant_type", "client_credentials"),
          Parameter.with("client_id", appId), Parameter.with("client_secret", appSecret));

    try {
      return AccessToken.fromQueryString(response);
    } catch (Throwable t) {
      throw new FacebookResponseContentException("Unable to extract access token from response.", t);
    }
  }

  /**
   * Convenience method which invokes {@link #obtainExtendedAccessToken(String, String, String)} with the current access
   * token.
   * 
   * @param appId
   *          The ID of the app for which you'd like to obtain an extended access token.
   * @param appSecret
   *          The secret for the app for which you'd like to obtain an extended access token.
   * @return An extended access token for the given {@code accessToken}.
   * @throws FacebookException
   *           If an error occurs while attempting to obtain an extended access token.
   * @throws IllegalStateException
   *           If this instance was not constructed with an access token.
   * @since 1.6.10
   */
  public AccessToken obtainExtendedAccessToken(String appId, String appSecret) {
    if (accessToken == null)
      throw new IllegalStateException(format(
        "You cannot call this method because you did not construct this instance of %s with an access token.",
        getClass().getSimpleName()));

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
        makeRequest("/oauth/access_token", true, false, null, Parameter.with("client_id", appId),
          Parameter.with("client_secret", appSecret), Parameter.with("grant_type", "fb_exchange_token"),
          Parameter.with("fb_exchange_token", accessToken));

    try {
      return AccessToken.fromQueryString(response);
    } catch (Throwable t) {
      throw new FacebookResponseContentException("Unable to extract access token from response.", t);
    }
  }

  /**
   * @see com.restfb.FacebookClient#parseSignedRequest(java.lang.String, java.lang.String, java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T> T parseSignedRequest(String signedRequest, String appSecret, Class<T> objectType) {
    verifyParameterPresence("signedRequest", signedRequest);
    verifyParameterPresence("appSecret", appSecret);
    verifyParameterPresence("objectType", objectType);

    String[] signedRequestTokens = signedRequest.split("[.]");

    if (signedRequestTokens.length != 2)
      throw new FacebookSignedRequestParsingException(format(
        "Signed request '%s' is expected to be signature and payload strings separated by a '.'", signedRequest));

    String encodedSignature = signedRequestTokens[0];
    String urlDecodedSignature = urlDecodeSignedRequestToken(encodedSignature);
    byte[] signature = decodeBase64(urlDecodedSignature);

    String encodedPayload = signedRequestTokens[1];
    String urlDecodedPayload = urlDecodeSignedRequestToken(encodedPayload);
    String payload = StringUtils.toString(decodeBase64(urlDecodedPayload));

    // Convert payload to a JsonObject so we can pull algorithm data out of it
    JsonObject payloadObject = getJsonMapper().toJavaObject(payload, JsonObject.class);

    if (!payloadObject.has("algorithm"))
      throw new FacebookSignedRequestParsingException("Unable to detect algorithm used for signed request");

    String algorithm = payloadObject.getString("algorithm");

    if (!verifySignedRequest(appSecret, algorithm, encodedPayload, signature))
      throw new FacebookSignedRequestVerificationException(
        "Signed request verification failed. Are you sure the request was made for the app identified by the app secret you provided?");

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
    if ("HMAC-SHA256".equalsIgnoreCase(algorithm))
      algorithm = "HMACSHA256";

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
  public JsonMapper getJsonMapper() {
    return jsonMapper;
  }

  /**
   * @see com.restfb.FacebookClient#getWebRequestor()
   */
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
   * @param binaryAttachment
   *          A binary file to include in a {@code POST} request. Pass {@code null} if no attachment should be sent.
   * @param parameters
   *          Arbitrary number of parameters to send along to Facebook as part of the API call.
   * @return The JSON returned by Facebook for the API call.
   * @throws FacebookException
   *           If an error occurs while making the Facebook API POST or processing the response.
   */
  protected String makeRequest(String endpoint, final boolean executeAsPost, boolean executeAsDelete,
      final List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    verifyParameterLegality(parameters);

    if (executeAsDelete)
      parameters = parametersWithAdditionalParameter(Parameter.with(METHOD_PARAM_NAME, "delete"), parameters);

    trimToEmpty(endpoint).toLowerCase();
    if (!endpoint.startsWith("/"))
      endpoint = "/" + endpoint;

    final String fullEndpoint =
        createEndpointForApiCall(endpoint, binaryAttachments != null && binaryAttachments.size() > 0);
    final String parameterString = toParameterString(parameters);

    return makeRequestAndProcessResponse(new Requestor() {
      /**
       * @see com.restfb.DefaultFacebookClient.Requestor#makeRequest()
       */
      public Response makeRequest() throws IOException {
        return executeAsPost ? webRequestor.executePost(fullEndpoint, parameterString, binaryAttachments == null ? null
            : binaryAttachments.toArray(new BinaryAttachment[] {})) : webRequestor.executeGet(fullEndpoint + "?"
            + parameterString);
      }
    });
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
    // Server Error,
    // throw an exception.
    if (HTTP_OK != response.getStatusCode() && HTTP_BAD_REQUEST != response.getStatusCode()
        && HTTP_UNAUTHORIZED != response.getStatusCode() && HTTP_NOT_FOUND != response.getStatusCode()
        && HTTP_INTERNAL_ERROR != response.getStatusCode() && HTTP_FORBIDDEN != response.getStatusCode())
      throw new FacebookNetworkException("Facebook request failed", response.getStatusCode());

    String json = response.getBody();

    // If the response contained an error code, throw an exception.
    throwFacebookResponseStatusExceptionIfNecessary(json, response.getStatusCode());

    // If there was no response error information and this was a 500 or 401
    // error, something weird happened on Facebook's end. Bail.
    if (HTTP_INTERNAL_ERROR == response.getStatusCode() || HTTP_UNAUTHORIZED == response.getStatusCode())
      throw new FacebookNetworkException("Facebook request failed", response.getStatusCode());

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

      JsonObject errorObject = new JsonObject(json);

      if (errorObject == null || !errorObject.has(ERROR_ATTRIBUTE_NAME))
        return;

      JsonObject innerErrorObject = errorObject.getJsonObject(ERROR_ATTRIBUTE_NAME);

      // If there's an Integer error code, pluck it out.
      Integer errorCode =
          innerErrorObject.has(ERROR_CODE_ATTRIBUTE_NAME) ? toInteger(innerErrorObject
            .getString(ERROR_CODE_ATTRIBUTE_NAME)) : null;
      Integer errorSubcode =
          innerErrorObject.has(ERROR_SUBCODE_ATTRIBUTE_NAME) ? toInteger(innerErrorObject
            .getString(ERROR_SUBCODE_ATTRIBUTE_NAME)) : null;          

      throw graphFacebookExceptionMapper
        .exceptionForTypeAndMessage(errorCode, errorSubcode, httpStatusCode,
          innerErrorObject.getString(ERROR_TYPE_ATTRIBUTE_NAME),
          innerErrorObject.getString(ERROR_MESSAGE_ATTRIBUTE_NAME));
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
        null, httpStatusCode, null, errorObject.getString(BATCH_ERROR_DESCRIPTION_ATTRIBUTE_NAME));
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
        Integer httpStatusCode, String type, String message) {
      if ("OAuthException".equals(type) || "OAuthAccessTokenException".equals(type))
        return new FacebookOAuthException(type, message, errorCode, errorSubcode, httpStatusCode);

      if ("QueryParseException".equals(type))
        return new FacebookQueryParseException(type, message, httpStatusCode);

      // Don't recognize this exception type? Just go with the standard
      // FacebookGraphException.
      return new FacebookGraphException(type, message, httpStatusCode);
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
    if (!isBlank(accessToken))
      parameters = parametersWithAdditionalParameter(Parameter.with(ACCESS_TOKEN_PARAM_NAME, accessToken), parameters);

    parameters = parametersWithAdditionalParameter(Parameter.with(FORMAT_PARAM_NAME, "json"), parameters);

    StringBuilder parameterStringBuilder = new StringBuilder();
    boolean first = true;

    for (Parameter parameter : parameters) {
      if (first)
        first = false;
      else
        parameterStringBuilder.append("&");

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
    trimToEmpty(apiCall).toLowerCase();
    while (apiCall.startsWith("/"))
      apiCall = apiCall.substring(1);

    String baseUrl = getFacebookGraphEndpointUrl();

    if (readOnlyApiCalls.contains(apiCall))
      baseUrl = getFacebookReadOnlyEndpointUrl();
    else if (hasAttachment && apiCall.endsWith("/videos"))
      baseUrl = getFacebookGraphVideoEndpointUrl();

    return format("%s/%s", baseUrl, apiCall);
  }

  /**
   * Returns the base endpoint URL for the Graph API.
   * 
   * @return The base endpoint URL for the Graph API.
   */
  protected String getFacebookGraphEndpointUrl() {
    return FACEBOOK_GRAPH_ENDPOINT_URL;
  }

  /**
   * Returns the base endpoint URL for the Graph API's video upload functionality.
   * 
   * @return The base endpoint URL for the Graph API's video upload functionality.
   * @since 1.6.5
   */
  protected String getFacebookGraphVideoEndpointUrl() {
    return FACEBOOK_GRAPH_VIDEO_ENDPOINT_URL;
  }

  /**
   * @see com.restfb.BaseFacebookClient#getFacebookReadOnlyEndpointUrl()
   */
  @Override
  protected String getFacebookReadOnlyEndpointUrl() {
    return FACEBOOK_READ_ONLY_ENDPOINT_URL;
  }
}