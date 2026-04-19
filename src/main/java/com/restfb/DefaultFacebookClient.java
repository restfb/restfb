/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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

import static com.restfb.logging.RestFBLogger.CLIENT_LOGGER;
import static com.restfb.util.EncodingUtils.decodeBase64;
import static com.restfb.util.ObjectUtil.requireNotEmpty;
import static com.restfb.util.ObjectUtil.verifyParameterPresence;
import static com.restfb.util.StringUtils.*;
import static com.restfb.util.UrlUtils.urlEncode;
import static java.lang.String.format;
import static java.net.HttpURLConnection.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.restfb.WebRequestor.Response;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;
import com.restfb.exception.*;
import com.restfb.exception.devicetoken.*;
import com.restfb.exception.generator.DefaultFacebookExceptionGenerator;
import com.restfb.exception.generator.FacebookExceptionGenerator;
import com.restfb.json.*;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.DebugTokenInfo;
import com.restfb.types.DeviceCode;
import com.restfb.util.EncodingUtils;
import com.restfb.util.ObjectUtil;
import com.restfb.util.StringUtils;

/**
 * Default implementation of a <a href="http://developers.facebook.com/docs/api">Facebook Graph API</a> client.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class DefaultFacebookClient extends BaseFacebookClient implements FacebookClient {
  public static final String CLIENT_ID = "client_id";
  public static final String APP_ID = "appId";
  public static final String APP_SECRET = "appSecret";
  public static final String SCOPE = "scope";
  public static final String CANNOT_EXTRACT_ACCESS_TOKEN_MESSAGE = "Unable to extract access token from response.";
  public static final String PARAM_CLIENT_SECRET = "client_secret";

  public static final String CONNECTION = "connection";
  public static final String CONNECTION_TYPE = "connectionType";
  public static final String ALGORITHM = "algorithm";
  public static final String PATH_OAUTH_ACCESS_TOKEN = "oauth/access_token";
  public static final String REDIRECT_URI = "redirect_uri";
  public static final String GRANT_TYPE = "grant_type";
  public static final String CODE = "code";
  /**
   * Graph API access token.
   */
  protected String accessToken;

  /**
   * Graph API app secret.
   */
  protected String appSecret;

  /**
   * facebook exception generator to convert Facebook error json into java exceptions
   */
  private FacebookExceptionGenerator graphFacebookExceptionGenerator;

  /**
   * holds the Facebook endpoint urls
   */
  private FacebookEndpoints facebookEndpointUrls = new FacebookEndpoints() {};

  /**
   * Reserved "multiple IDs" parameter name.
   */
  protected static final String IDS_PARAM_NAME = "ids";

  /**
   * Version of API endpoint.
   */
  protected Version apiVersion;

  /**
   * By default, this is <code>false</code>, so real http DELETE is used
   */
  protected boolean httpDeleteFallback;

  protected boolean accessTokenInHeader;


  protected DefaultFacebookClient() {
    this(Version.LATEST);
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
   * @param apiVersion
   *          Version of the api endpoint
   * @throws NullPointerException
   *           If {@code jsonMapper} or {@code webRequestor} is {@code null}.
   * @since 1.6.14
   */
  public DefaultFacebookClient(String accessToken, WebRequestor webRequestor, JsonMapper jsonMapper,
      Version apiVersion) {
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
    this.jsonMapper.setFacebookClient(this);
    this.apiVersion = Optional.ofNullable(apiVersion).orElse(Version.UNVERSIONED);
    graphFacebookExceptionGenerator = new DefaultFacebookExceptionGenerator();
  }

  /**
   * Switch between access token in header and access token in query parameters (default)
   * 
   * @param accessTokenInHttpHeader
   *          <code>true</code> use access token as header field, <code>false</code> use access token as query parameter
   *          (default)
   */
  public void setHeaderAuthorization(boolean accessTokenInHttpHeader) {
    this.accessTokenInHeader = accessTokenInHttpHeader;
  }

  /**
   * override the default facebook exception generator to provide a custom handling for the facebook error objects
   * 
   * @param exceptionGenerator
   *          the custom exception generator implementing the {@link FacebookExceptionGenerator} interface
   */
  public void setFacebookExceptionGenerator(FacebookExceptionGenerator exceptionGenerator) {
    graphFacebookExceptionGenerator = exceptionGenerator;
  }

  /**
   * fetch the current facebook exception generator implementing the {@link FacebookExceptionGenerator} interface
   * 
   * @return the current facebook exception generator
   */
  public FacebookExceptionGenerator getFacebookExceptionGenerator() {
    return graphFacebookExceptionGenerator;
  }

  @Override
  public boolean deleteObject(String object, Parameter... parameters) {
    return deleteObjectWithResult(object, parameters).getResult();
  }

  @Override
  public ApiResult<Boolean> deleteObjectWithResult(String object, Parameter... parameters) {
    verifyParameterPresence("object", object);

    RequestExecutionResult executionResult = makeRequestWithMetadata(object, true, true, null, parameters);
    Response response = executionResult.getResponse();
    String responseString = response.getBody();

    try {
      JsonValue jObj = Json.parse(responseString);
      boolean success = false;
      if (jObj.isObject()) {
        if (jObj.asObject().contains("success")) {
          success = jObj.asObject().get("success").asBoolean();
        }
        if (jObj.asObject().contains("result")) {
          success = jObj.asObject().get("result").asString().contains("Successfully deleted");
        }
      } else {
        success = jObj.asBoolean();
      }
      return toApiResult(success, executionResult);
    } catch (ParseException jex) {
      CLIENT_LOGGER.trace("no valid JSON returned while deleting a object, using returned String instead", jex);
      boolean fallbackResult = "true".equals(responseString);
      return toApiResult(fallbackResult, executionResult);
    }
  }

  /**
   * @see com.restfb.FacebookClient#fetchConnection(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> Connection<T> fetchConnection(String connection, Class<T> connectionType, Parameter... parameters) {
    verifyParameterPresence(CONNECTION, connection);
    verifyParameterPresence(CONNECTION_TYPE, connectionType);
    RequestExecutionResult executionResult = makeRequestWithMetadata(connection, parameters);
    Response response = executionResult.getResponse();
    Connection<T> connectionResult = createConnection(response.getBody(), connectionType);
    connectionResult.setResponseMetadata(toResponseMetadata(executionResult));
    return connectionResult;
  }

  /**
   * @see com.restfb.FacebookClient#fetchConnectionPage(java.lang.String, java.lang.Class)
   */
  @Override
  public <T> Connection<T> fetchConnectionPage(final String connectionPageUrl, Class<T> connectionType) {
    verifyParameterPresence("connectionPageUrl", connectionPageUrl);
    verifyParameterPresence(CONNECTION_TYPE, connectionType);

    RequestExecutionResult executionResult = fetchConnectionPageResponse(connectionPageUrl);
    String connectionJson = executionResult.getResponse().getBody();
    Connection<T> connection = createConnection(connectionJson, connectionType);
    connection.setResponseMetadata(toResponseMetadata(executionResult));
    return connection;
  }

  private RequestExecutionResult fetchConnectionPageResponse(String connectionPageUrl) {
    WebRequestor.Request request;
    if (!isBlank(accessToken) && !isBlank(appSecret)) {
      if (isAppSecretProofWithTime()) {
        long now = System.currentTimeMillis() / 1000;
        request = new WebRequestor.Request(
          String.format("%s&%s=%s&%s=%s", connectionPageUrl, urlEncode(APP_SECRET_PROOF_TIME_PARAM_NAME), now,
            urlEncode(APP_SECRET_PROOF_PARAM_NAME), obtainAppSecretProof(accessToken + "|" + now, appSecret)),
          null);
      } else {
        request = new WebRequestor.Request(String.format("%s&%s=%s", connectionPageUrl,
          urlEncode(APP_SECRET_PROOF_PARAM_NAME), obtainAppSecretProof(accessToken, appSecret)), null);
      }
    } else {
      request = new WebRequestor.Request(connectionPageUrl, getHeaderAccessToken());
    }

    return executeGetRequestWithInfo(request);
  }

  /**
   * @see com.restfb.FacebookClient#fetchObject(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters) {
    return fetchObjectWithResult(object, objectType, parameters).getResult();
  }

  @Override
  public <T> ApiResult<T> fetchObjectWithResult(String object, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("object", object);
    verifyParameterPresence("objectType", objectType);
    RequestExecutionResult executionResult = makeRequestWithMetadata(object, parameters);
    T mapped = jsonMapper.toJavaObject(executionResult.getResponse().getBody(), objectType);
    return toApiResult(mapped, executionResult);
  }

  @Override
  public FacebookClient createClientWithAccessToken(String accessToken) {
    return new DefaultFacebookClient(accessToken, this.appSecret, getWebRequestor(), getJsonMapper(), this.apiVersion);
  }

  /**
   * @see com.restfb.FacebookClient#fetchObjects(java.util.List, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> T fetchObjects(List<String> ids, Class<T> objectType, Parameter... parameters) {
    return fetchObjectsWithResult(ids, objectType, parameters).getResult();
  }

  @Override
  public <T> ApiResult<T> fetchObjectsWithResult(List<String> ids, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("ids", ids);
    verifyParameterPresence(CONNECTION_TYPE, objectType);
    requireNotEmpty(ids, "The list of IDs cannot be empty.");

    if (Stream.of(parameters).anyMatch(p -> IDS_PARAM_NAME.equals(p.name))) {
      throw new IllegalArgumentException("You cannot specify the '" + IDS_PARAM_NAME + "' URL parameter yourself - "
          + "RestFB will populate this for you with the list of IDs you passed to this method.");
    }

    JsonArray idArray = new JsonArray();

    // Normalize the IDs
    for (String id : ids) {
      throwIAEonBlankId(id);
      idArray.add(id.trim());
    }

    try {
      RequestExecutionResult executionResult = makeRequestWithMetadata("",
        parametersWithAdditionalParameter(Parameter.with(IDS_PARAM_NAME, idArray.toString()), parameters));

      T mapped = jsonMapper.toJavaObject(executionResult.getResponse().getBody(), objectType);
      return toApiResult(mapped, executionResult);
    } catch (ParseException e) {
      throw new FacebookJsonMappingException("Unable to map connection JSON to Java objects", e);
    }
  }

  private void throwIAEonBlankId(String id) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException("The list of IDs cannot contain blank strings.");
    }
  }

  private <T> ApiResult<T> toApiResult(T result, RequestExecutionResult executionResult) {
    return ApiResult.withMetadata(result, toResponseMetadata(executionResult));
  }

  private ResponseMetadata toResponseMetadata(RequestExecutionResult executionResult) {
    if (executionResult == null) {
      return null;
    }
    Response response = executionResult.getResponse();
    DebugHeaderInfo debugHeaderInfo = Optional.ofNullable(response).map(Response::getDebugHeaderInfo).orElse(null);
    Map<String, List<String>> headers = Optional.ofNullable(response).map(Response::getHeaders).orElse(null);
    Duration duration = Optional.ofNullable(executionResult).map(RequestExecutionResult::getDuration).orElse(null);
    String httpMethod = Optional.ofNullable(executionResult).map(RequestExecutionResult::getHttpMethod).orElse(null);
    String requestUrl = Optional.ofNullable(executionResult).map(RequestExecutionResult::getRequestUrl).orElse(null);
    return ResponseMetadata.of(debugHeaderInfo, headers, duration, httpMethod, requestUrl);
  }

  /**
   * @see com.restfb.FacebookClient#publish(java.lang.String, java.lang.Class, com.restfb.BinaryAttachment,
   *      com.restfb.Parameter[])
   */
  @Override
  public <T> T publish(String connection, Class<T> objectType, List<BinaryAttachment> binaryAttachments,
      Parameter... parameters) {
    return publishWithResult(connection, objectType, binaryAttachments, parameters).getResult();
  }

  @Override
  public <T> ApiResult<T> publishWithResult(String connection, Class<T> objectType,
      List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    verifyParameterPresence(CONNECTION, connection);
    RequestExecutionResult executionResult =
        makeRequestWithMetadata(connection, true, false, binaryAttachments, parameters);
    T mapped = jsonMapper.toJavaObject(executionResult.getResponse().getBody(), objectType);
    return toApiResult(mapped, executionResult);
  }

  /**
   * @see com.restfb.FacebookClient#publish(java.lang.String, java.lang.Class, com.restfb.BinaryAttachment,
   *      com.restfb.Parameter[])
   */
  @Override
  public <T> T publish(String connection, Class<T> objectType, BinaryAttachment binaryAttachment,
      Parameter... parameters) {
    return publishWithResult(connection, objectType, binaryAttachment, parameters).getResult();
  }

  @Override
  public <T> ApiResult<T> publishWithResult(String connection, Class<T> objectType, BinaryAttachment binaryAttachment,
      Parameter... parameters) {
    List<BinaryAttachment> attachments =
        Optional.ofNullable(binaryAttachment).map(Collections::singletonList).orElse(null);
    return publishWithResult(connection, objectType, attachments, parameters);
  }

  /**
   * @see com.restfb.FacebookClient#publish(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> T publish(String connection, Class<T> objectType, Parameter... parameters) {
    return publishWithResult(connection, objectType, parameters).getResult();
  }

  @Override
  public <T> ApiResult<T> publishWithResult(String connection, Class<T> objectType, Parameter... parameters) {
    return publishWithResult(connection, objectType, (List<BinaryAttachment>) null, parameters);
  }

  @Override
  public <T> T publish(String connection, Class<T> objectType, Body body, Parameter... parameters) {
    return publishWithResult(connection, objectType, body, parameters).getResult();
  }

  @Override
  public <T> ApiResult<T> publishWithResult(String connection, Class<T> objectType, Body body,
      Parameter... parameters) {
    verifyParameterPresence(CONNECTION, connection);
    RequestExecutionResult executionResult = makeRequestWithMetadata(connection, true, false, null, body, parameters);
    T mapped = jsonMapper.toJavaObject(executionResult.getResponse().getBody(), objectType);
    return toApiResult(mapped, executionResult);
  }

  @Override
  public String getLogoutUrl(String next) {
    String parameterString;
    if (next != null) {
      Parameter p = Parameter.with("next", next);
      parameterString = toParameterString(false, p);
    } else {
      parameterString = toParameterString(false);
    }

    final String fullEndPoint = createEndpointForApiCall("logout.php", false, false);
    return fullEndPoint + "?" + parameterString;
  }

  /**
   * @see com.restfb.FacebookClient#executeBatch(com.restfb.batch.BatchRequest[])
   */
  @Override
  public List<BatchResponse> executeBatch(BatchRequest... batchRequests) {
    return executeBatch(asList(batchRequests), Collections.emptyList());
  }

  /**
   * @see com.restfb.FacebookClient#executeBatch(java.util.List)
   */
  @Override
  public List<BatchResponse> executeBatch(List<BatchRequest> batchRequests) {
    return executeBatch(batchRequests, Collections.emptyList());
  }

  /**
   * @see com.restfb.FacebookClient#executeBatch(java.util.List, java.util.List)
   */
  @Override
  public List<BatchResponse> executeBatch(List<BatchRequest> batchRequests, List<BinaryAttachment> binaryAttachments) {
    verifyParameterPresence("binaryAttachments", binaryAttachments);
    requireNotEmpty(batchRequests, "You must specify at least one batch request.");

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
    verifyParameterPresence(APP_ID, appId);
    verifyParameterPresence("secretKey", secretKey);

    if (sessionKeys == null || sessionKeys.length == 0) {
      return emptyList();
    }

    String json = makeRequest("/oauth/exchange_sessions", true, false, null, Parameter.with(CLIENT_ID, appId),
      Parameter.with(PARAM_CLIENT_SECRET, secretKey), Parameter.with("sessions", String.join(",", sessionKeys)));

    return jsonMapper.toJavaList(json, AccessToken.class);
  }

  /**
   * @see com.restfb.FacebookClient#obtainAppAccessToken(java.lang.String, java.lang.String)
   */
  @Override
  public AccessToken obtainAppAccessToken(String appId, String appSecret) {
    verifyParameterPresence(APP_ID, appId);
    verifyParameterPresence(APP_SECRET, appSecret);

    String response = makeRequest(PATH_OAUTH_ACCESS_TOKEN, Parameter.with(GRANT_TYPE, "client_credentials"),
      Parameter.with(CLIENT_ID, appId), Parameter.with(PARAM_CLIENT_SECRET, appSecret));

    try {
      return getAccessTokenFromResponse(response);
    } catch (Exception t) {
      throw new FacebookResponseContentException(CANNOT_EXTRACT_ACCESS_TOKEN_MESSAGE, t);
    }
  }

  @Override
  public DeviceCode fetchDeviceCode(ScopeBuilder scope) {
    verifyParameterPresence(SCOPE, scope);
    ObjectUtil.requireNotNull(accessToken,
      () -> new IllegalStateException("access token is required to fetch a device access token"));

    String response = makeRequest("device/login", true, false, null, Parameter.with("type", "device_code"),
      Parameter.with(SCOPE, scope.toString()));
    return jsonMapper.toJavaObject(response, DeviceCode.class);
  }

  @Override
  public AccessToken obtainDeviceAccessToken(String code) throws FacebookDeviceTokenCodeExpiredException,
      FacebookDeviceTokenPendingException, FacebookDeviceTokenDeclinedException, FacebookDeviceTokenSlowdownException {
    verifyParameterPresence(CODE, code);

    ObjectUtil.requireNotNull(accessToken,
      () -> new IllegalStateException("access token is required to fetch a device access token"));

    try {
      String response = makeRequest("device/login_status", true, false, null, Parameter.with("type", "device_token"),
        Parameter.with(CODE, code));
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
  public AccessToken obtainUserAccessToken(String appId, String appSecret, String redirectUri,
      String verificationCode) {
    verifyParameterPresence(APP_ID, appId);
    verifyParameterPresence(APP_SECRET, appSecret);
    verifyParameterPresence("verificationCode", verificationCode);

    String response = makeRequest(PATH_OAUTH_ACCESS_TOKEN, Parameter.with(CLIENT_ID, appId),
      Parameter.with(PARAM_CLIENT_SECRET, appSecret), Parameter.with(CODE, verificationCode),
      Parameter.with(REDIRECT_URI, redirectUri));

    try {
      return getAccessTokenFromResponse(response);
    } catch (Exception t) {
      throw new FacebookResponseContentException(CANNOT_EXTRACT_ACCESS_TOKEN_MESSAGE, t);
    }
  }

  /**
   * @see com.restfb.FacebookClient#obtainExtendedAccessToken(java.lang.String, java.lang.String)
   */
  @Override
  public AccessToken obtainExtendedAccessToken(String appId, String appSecret) {
    ObjectUtil.requireNotNull(accessToken,
      () -> new IllegalStateException(
        format("You cannot call this method because you did not construct this instance of %s with an access token.",
          getClass().getSimpleName())));

    return obtainExtendedAccessToken(appId, appSecret, accessToken);
  }

  @Override
  public AccessToken obtainRefreshedExtendedAccessToken() {
    throw new UnsupportedOperationException(
      "obtaining a refreshed extended access token is not supported by this client");
  }

  /**
   * @see com.restfb.FacebookClient#obtainExtendedAccessToken(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public AccessToken obtainExtendedAccessToken(String appId, String appSecret, String accessToken) {
    verifyParameterPresence(APP_ID, appId);
    verifyParameterPresence(APP_SECRET, appSecret);
    verifyParameterPresence("accessToken", accessToken);

    String response = makeRequest("/oauth/access_token", false, false, null, //
      Parameter.with(CLIENT_ID, appId), //
      Parameter.with(PARAM_CLIENT_SECRET, appSecret), //
      Parameter.with(GRANT_TYPE, "fb_exchange_token"), //
      Parameter.with("fb_exchange_token", accessToken), //
      Parameter.withFields("access_token,expires_in,token_type"));

    try {
      return getAccessTokenFromResponse(response);
    } catch (Exception t) {
      throw new FacebookResponseContentException(CANNOT_EXTRACT_ACCESS_TOKEN_MESSAGE, t);
    }
  }

  protected AccessToken getAccessTokenFromResponse(String response) {
    AccessToken token;
    try {
      token = getJsonMapper().toJavaObject(response, AccessToken.class);
    } catch (FacebookJsonMappingException fjme) {
      CLIENT_LOGGER.trace("could not map response to access token class try to fetch directly from String", fjme);
      token = AccessToken.fromQueryString(response);
    }
    token.setClient(createClientWithAccessToken(token.getAccessToken()));
    return token;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T parseSignedRequest(String signedRequest, String appSecret, Class<T> objectType) {
    verifyParameterPresence("signedRequest", signedRequest);
    verifyParameterPresence(APP_SECRET, appSecret);
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

    // Convert payload to a JsonObject, so we can pull algorithm data out of it
    JsonObject payloadObject = getJsonMapper().toJavaObject(payload, JsonObject.class);

    if (!payloadObject.contains(ALGORITHM)) {
      throw new FacebookSignedRequestParsingException("Unable to detect algorithm used for signed request");
    }

    String algorithm = payloadObject.getString(ALGORITHM, null);

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
  public String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope, String state,
      Parameter... parameters) {
    List<Parameter> parameterList = asList(parameters);
    return getGenericLoginDialogUrl(appId, redirectUri, scope,
      () -> getFacebookEndpointUrls().getFacebookEndpoint() + "/dialog/oauth", state, parameterList);
  }

  @Override
  public String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope, Parameter... parameters) {
    return getLoginDialogUrl(appId, redirectUri, scope, null, parameters);
  }

  @Override
  public String getBusinessLoginDialogUrl(String appId, String redirectUri, String configId, String state,
      Parameter... parameters) {
    verifyParameterPresence("configId", configId);

    List<Parameter> parameterList = new ArrayList<>(asList(parameters));
    parameterList.add(Parameter.with("config_id", configId));
    parameterList.add(Parameter.with("response_type", "code"));
    parameterList.add(Parameter.with("override_default_response_type", true));

    return getGenericLoginDialogUrl(appId, redirectUri, new ScopeBuilder(true),
      () -> getFacebookEndpointUrls().getFacebookEndpoint() + "/dialog/oauth", state, parameterList);
  }

  protected String getGenericLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope,
      Supplier<String> endpointSupplier, String state, List<Parameter> parameters) {
    verifyParameterPresence(APP_ID, appId);
    verifyParameterPresence("redirectUri", redirectUri);
    verifyParameterPresence(SCOPE, scope);

    String dialogUrl = endpointSupplier.get();

    List<Parameter> parameterList = new ArrayList<>();
    parameterList.add(Parameter.with(CLIENT_ID, appId));
    parameterList.add(Parameter.with(REDIRECT_URI, redirectUri));
    if (!scope.toString().isEmpty()) {
      parameterList.add(Parameter.with(SCOPE, scope.toString()));
    }

    if (StringUtils.isNotBlank(state)) {
      parameterList.add(Parameter.with("state", state));
    }

    // add optional parameters
    parameterList.addAll(parameters);
    return dialogUrl + "?" + toParameterString(false, parameterList.toArray(new Parameter[0]));
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
    verifyParameterPresence(APP_SECRET, appSecret);
    verifyParameterPresence(ALGORITHM, algorithm);
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

    try {
      JsonObject json = Json.parse(response).asObject();

      // FB sometimes returns an empty DebugTokenInfo and then the 'data' field is an array
      if (json.contains("data") && json.get("data").isArray()) {
        return null;
      }

      JsonObject data = json.get("data").asObject();
      return getJsonMapper().toJavaObject(data.toString(), DebugTokenInfo.class);
    } catch (Exception t) {
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
    return makeRequestForResponse(endpoint, parameters).getBody();
  }

  protected String makeRequest(String endpoint, final boolean executeAsPost, final boolean executeAsDelete,
      final List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    return makeRequestForResponse(endpoint, executeAsPost, executeAsDelete, binaryAttachments, parameters).getBody();
  }

  protected String makeRequest(String endpoint, final boolean executeAsPost, final boolean executeAsDelete,
      final List<BinaryAttachment> binaryAttachments, Body body, Parameter... parameters) {
    return makeRequestForResponse(endpoint, executeAsPost, executeAsDelete, binaryAttachments, body, parameters)
      .getBody();
  }

  protected Response makeRequestForResponse(String endpoint, Parameter... parameters) {
    return makeRequestWithMetadata(endpoint, parameters).getResponse();
  }

  protected Response makeRequestForResponse(String endpoint, final boolean executeAsPost, final boolean executeAsDelete,
      final List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    return makeRequestWithMetadata(endpoint, executeAsPost, executeAsDelete, binaryAttachments, null, parameters)
      .getResponse();
  }

  /**
   * Coordinates the process of executing the API request GET/POST and returning the raw response.
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
   * @param body
   *          Optional body used for POST requests.
   * @param parameters
   *          Arbitrary number of parameters to send along to Facebook as part of the API call.
   * @return The raw response returned by Facebook for the API call.
   * @throws FacebookException
   *           If an error occurs while making the Facebook API POST or processing the response.
   */
  protected RequestExecutionResult makeRequestWithMetadata(String endpoint, final boolean executeAsPost,
      final boolean executeAsDelete, final List<BinaryAttachment> binaryAttachments, Body body,
      Parameter... parameters) {
    verifyParameterLegality(parameters);

    if (executeAsDelete && isHttpDeleteFallback()) {
      parameters = parametersWithAdditionalParameter(Parameter.with(METHOD_PARAM_NAME, "delete"), parameters);
    }

    if (!endpoint.startsWith("/")) {
      endpoint = "/" + endpoint;
    }

    boolean hasAttachment = binaryAttachments != null && !binaryAttachments.isEmpty();
    boolean hasReel = hasAttachment && binaryAttachments.get(0).isFacebookReel();

    final String fullEndpoint = createEndpointForApiCall(endpoint, hasAttachment, hasReel);
    final String parameterString = toParameterString(parameters);

    String headerAccessToken = (hasReel) ? accessToken : getHeaderAccessToken();

    WebRequestor.Request request = new WebRequestor.Request(fullEndpoint, headerAccessToken, parameterString);
    request.setBinaryAttachments(binaryAttachments);
    request.setBody(body);

    String httpMethod;
    Requestor requestor;

    if (executeAsDelete && !isHttpDeleteFallback()) {
      httpMethod = "DELETE";
      requestor = () -> webRequestor.executeDelete(request);
    } else if (executeAsPost) {
      httpMethod = "POST";
      requestor = () -> webRequestor.executePost(request);
    } else {
      httpMethod = "GET";
      requestor = () -> webRequestor.executeGet(request);
    }

    long requestStartTime = System.currentTimeMillis();
    try {
      return executeRequestWithMetadata(httpMethod, request.getFullUrl(), requestor);
    } catch (FacebookException facebookException) {
      facebookException.withInfoData(httpMethod, request.getUrl(), parameterString, headerAccessToken,
        requestStartTime);
      throw facebookException;
    }
  }

  protected Response makeRequestForResponse(String endpoint, final boolean executeAsPost,
      final boolean executeAsDelete, final List<BinaryAttachment> binaryAttachments, Body body,
      Parameter... parameters) {
    return makeRequestWithMetadata(endpoint, executeAsPost, executeAsDelete, binaryAttachments, body, parameters)
      .getResponse();
  }

  protected RequestExecutionResult makeRequestWithMetadata(String endpoint, Parameter... parameters) {
    return makeRequestWithMetadata(endpoint, false, false, null, null, parameters);
  }

  protected RequestExecutionResult makeRequestWithMetadata(String endpoint, final boolean executeAsPost,
      final boolean executeAsDelete, final List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    return makeRequestWithMetadata(endpoint, executeAsPost, executeAsDelete, binaryAttachments, null, parameters);
  }

  private RequestExecutionResult executeGetRequest(WebRequestor.Request request) {
    return executeRequestWithMetadata("GET", request.getFullUrl(), () -> webRequestor.executeGet(request));
  }

  private RequestExecutionResult executeGetRequestWithInfo(WebRequestor.Request request) {
    long startTime = System.currentTimeMillis();
    try {
      return executeGetRequest(request);
    } catch (FacebookException facebookException) {
      facebookException.withInfoData("GET", request.getUrl(), request.getParameters(),
        request.getHeaderAccessToken(), startTime);
      throw facebookException;
    }
  }

  private String getHeaderAccessToken() {
    if (accessTokenInHeader) {
      return this.accessToken;
    }

    return null;
  }

  /**
   * @see com.restfb.FacebookClient#obtainAppSecretProof(java.lang.String, java.lang.String)
   */
  @Override
  public String obtainAppSecretProof(String accessToken, String appSecret) {
    verifyParameterPresence("accessToken", accessToken);
    verifyParameterPresence(APP_SECRET, appSecret);
    return EncodingUtils.encodeAppSecretProof(appSecret, accessToken);
  }

  /**
   * returns if the fallback post method (<code>true</code>) is used or the http delete (<code>false</code>)
   * 
   * @return {@code true} if POST is used instead of HTTP DELETE (default)
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
   *          <code>true</code> if the http Delete Fallback is used
   */
  public void setHttpDeleteFallback(boolean httpDeleteFallback) {
    this.httpDeleteFallback = httpDeleteFallback;
  }

  protected interface Requestor {
    Response makeRequest() throws IOException;
  }

  protected RequestExecutionResult executeRequestWithMetadata(String httpMethod, String requestUrl,
      Requestor requestor) {
    Response response;
    long start = System.nanoTime();

    // Perform a GET or POST to the API endpoint
    try {
      response = requestor.makeRequest();
    } catch (IOException ioe) {
      if (ioe.getMessage() != null && ioe.getMessage().contains("RST_STREAM")) {
        throw new FacebookRstStreamNetworkException(ioe.getMessage(), ioe);
      }
      if (ioe.getMessage() != null && ioe.getMessage().contains("GOAWAY")) {
        throw new FacebookGoawayNetworkException(ioe.getMessage(), ioe);
      }
      throw new FacebookNetworkException(ioe);
    } catch (Exception t) {
      throw new FacebookNetworkException(t);
    }

    // If we get any HTTP response code other than a 200 OK or 400 Bad Request
    // or 401 Not Authorized or 403 Forbidden or 404 Not Found or 500 Internal
    // Server Error or 302 Not Modified
    // throw an exception.
    if (HTTP_OK != response.getStatusCode() && HTTP_BAD_REQUEST != response.getStatusCode()
        && HTTP_UNAUTHORIZED != response.getStatusCode() && HTTP_NOT_FOUND != response.getStatusCode()
        && HTTP_INTERNAL_ERROR != response.getStatusCode() && HTTP_FORBIDDEN != response.getStatusCode()
        && HTTP_NOT_MODIFIED != response.getStatusCode()) {
      throw new FacebookNetworkException(response.getStatusCode());
    }

    try {
      // If the response contained an error code, throw an exception.
      getFacebookExceptionGenerator().throwFacebookResponseStatusExceptionIfNecessary(response.getBody(),
        response.getStatusCode());
    } catch (FacebookErrorMessageException feme) {
      Optional.ofNullable(response).map(Response::getDebugHeaderInfo).ifPresent(feme::setDebugHeaderInfo);
      throw feme;
    }

    // If there was no response error information and this was a 500 or 401
    // error, something weird happened on Facebook's end. Bail.
    if (HTTP_INTERNAL_ERROR == response.getStatusCode() || HTTP_UNAUTHORIZED == response.getStatusCode()) {
      throw new FacebookNetworkException(response.getStatusCode());
    }

    Duration duration = Duration.ofNanos(System.nanoTime() - start);
    return new RequestExecutionResult(response, duration, httpMethod, requestUrl);
  }

  protected static class RequestExecutionResult {
    private final Response response;
    private final Duration duration;
    private final String httpMethod;
    private final String requestUrl;

    RequestExecutionResult(Response response, Duration duration, String httpMethod, String requestUrl) {
      this.response = response;
      this.duration = duration;
      this.httpMethod = httpMethod;
      this.requestUrl = requestUrl;
    }

    public Response getResponse() {
      return response;
    }

    public Duration getDuration() {
      return duration;
    }

    public String getHttpMethod() {
      return httpMethod;
    }

    public String getRequestUrl() {
      return requestUrl;
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
    if (!isBlank(accessToken) && !accessTokenInHeader) {
      parameters = parametersWithAdditionalParameter(Parameter.with(ACCESS_TOKEN_PARAM_NAME, accessToken), parameters);
    }

    if (!isBlank(accessToken) && !isBlank(appSecret)) {
      if (isAppSecretProofWithTime()) {
        long now = System.currentTimeMillis() / 1000;
        parameters = parametersWithAdditionalParameter(
          Parameter.with(APP_SECRET_PROOF_TIME_PARAM_NAME, String.valueOf(now)), parameters);
        parameters = parametersWithAdditionalParameter(
          Parameter.with(APP_SECRET_PROOF_PARAM_NAME, obtainAppSecretProof(accessToken + "|" + now, appSecret)),
          parameters);
      } else {
        parameters = parametersWithAdditionalParameter(
          Parameter.with(APP_SECRET_PROOF_PARAM_NAME, obtainAppSecretProof(accessToken, appSecret)), parameters);
      }
    }

    if (withJsonParameter) {
      parameters = parametersWithAdditionalParameter(Parameter.with(FORMAT_PARAM_NAME, "json"), parameters);
    }

    return Stream.of(parameters).map(p -> urlEncode(p.name) + "=" + urlEncodedValueForParameterName(p.name, p.value))
      .collect(Collectors.joining("&"));
  }

  /**
   * @see com.restfb.BaseFacebookClient#createEndpointForApiCall(java.lang.String,boolean,boolean)
   */
  @Override
  protected String createEndpointForApiCall(String apiCall, boolean hasAttachment, boolean hasReel) {
    while (apiCall.startsWith("/")) {
      apiCall = apiCall.substring(1);
    }

    String baseUrl = createBaseUrlForEndpoint(apiCall, hasAttachment, hasReel);

    return format("%s/%s", baseUrl, apiCall);
  }

  protected String createBaseUrlForEndpoint(String apiCall, boolean hasAttachment, boolean hasReel) {
    String baseUrl = getFacebookGraphEndpointUrl();
    if (hasAttachment && hasReel) {
      baseUrl = getFacebookReelsUploadEndpointUrl();
    } else if (hasAttachment && (apiCall.endsWith("/videos") || apiCall.endsWith("/advideos"))) {
      baseUrl = getFacebookGraphVideoEndpointUrl();
    } else if (apiCall.endsWith("logout.php")) {
      baseUrl = getFacebookEndpointUrls().getFacebookEndpoint();
    }
    return baseUrl;
  }

  /**
   * Returns the base endpoint URL for the Graph API.
   * 
   * @return The base endpoint URL for the Graph API.
   */
  protected String getFacebookGraphEndpointUrl() {
    if (apiVersion.isUrlElementRequired()) {
      return getFacebookEndpointUrls().getGraphEndpoint() + '/' + apiVersion.getUrlElement();
    } else {
      return getFacebookEndpointUrls().getGraphEndpoint();
    }
  }

  /**
   * Returns the base endpoint URL for the Graph APIs video upload functionality.
   * 
   * @return The base endpoint URL for the Graph APIs video upload functionality.
   * @since 1.6.5
   */
  protected String getFacebookGraphVideoEndpointUrl() {
    if (apiVersion.isUrlElementRequired()) {
      return getFacebookEndpointUrls().getGraphVideoEndpoint() + '/' + apiVersion.getUrlElement();
    } else {
      return getFacebookEndpointUrls().getGraphVideoEndpoint();
    }
  }

  /**
   * Returns the Facebook Reels Upload endpoint URL for handling the Reels Upload
   * 
   * @return the Facebook Reels Upload endpoint URL
   */
  protected String getFacebookReelsUploadEndpointUrl() {
    if (apiVersion.isUrlElementRequired()) {
      return getFacebookEndpointUrls().getReelUploadEndpoint() + "/" + apiVersion.getUrlElement();
    }

    return getFacebookEndpointUrls().getReelUploadEndpoint();
  }

  public FacebookEndpoints getFacebookEndpointUrls() {
    return facebookEndpointUrls;
  }

  public void setFacebookEndpointUrls(FacebookEndpoints facebookEndpointUrls) {
    this.facebookEndpointUrls = facebookEndpointUrls;
  }
}
