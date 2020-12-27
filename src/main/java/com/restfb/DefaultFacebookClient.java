/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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
import java.util.*;
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
  /**
   * Graph API access token.
   */
  protected String accessToken;

  /**
   * Graph API app secret.
   */
  private String appSecret;

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
   * By default this is <code>false</code>, so real http DELETE is used
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
    verifyParameterPresence("object", object);

    String responseString = makeRequest(object, true, true, null, parameters);

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
      return success;
    } catch (ParseException jex) {
      CLIENT_LOGGER.trace("no valid JSON returned while deleting a object, using returned String instead", jex);
      return "true".equals(responseString);
    }
  }

  /**
   * @see com.restfb.FacebookClient#fetchConnection(java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> Connection<T> fetchConnection(String connection, Class<T> connectionType, Parameter... parameters) {
    verifyParameterPresence("connection", connection);
    verifyParameterPresence("connectionType", connectionType);
    return new Connection<>(this, makeRequest(connection, parameters), connectionType);
  }

  /**
   * @see com.restfb.FacebookClient#fetchConnectionPage(java.lang.String, java.lang.Class)
   */
  @Override
  public <T> Connection<T> fetchConnectionPage(final String connectionPageUrl, Class<T> connectionType) {
    String connectionJson;
    if (!isBlank(accessToken) && !isBlank(appSecret)) {
      WebRequestor.Request request = new WebRequestor.Request(String.format("%s&%s=%s", connectionPageUrl,
        urlEncode(APP_SECRET_PROOF_PARAM_NAME), obtainAppSecretProof(accessToken, appSecret)), null);
      connectionJson = makeRequestAndProcessResponse(() -> webRequestor.executeGet(request));
    } else {
      connectionJson = makeRequestAndProcessResponse(
        () -> webRequestor.executeGet(new WebRequestor.Request(connectionPageUrl, getHeaderAccessToken())));
    }

    return new Connection<>(this, connectionJson, connectionType);
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

  @Override
  public FacebookClient createClientWithAccessToken(String accessToken) {
    return new DefaultFacebookClient(accessToken, this.appSecret, this.apiVersion);
  }

  /**
   * @see com.restfb.FacebookClient#fetchObjects(java.util.List, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T> T fetchObjects(List<String> ids, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("ids", ids);
    verifyParameterPresence("connectionType", objectType);
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
      String jsonString = makeRequest("",
        parametersWithAdditionalParameter(Parameter.with(IDS_PARAM_NAME, idArray.toString()), parameters));

      return jsonMapper.toJavaObject(jsonString, objectType);
    } catch (ParseException e) {
      throw new FacebookJsonMappingException("Unable to map connection JSON to Java objects", e);
    }
  }

  private void throwIAEonBlankId(String id) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException("The list of IDs cannot contain blank strings.");
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
    List<BinaryAttachment> attachments =
        Optional.ofNullable(binaryAttachment).map(Collections::singletonList).orElse(null);
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
  public String getLogoutUrl(String next) {
    String parameterString;
    if (next != null) {
      Parameter p = Parameter.with("next", next);
      parameterString = toParameterString(false, p);
    } else {
      parameterString = toParameterString(false);
    }

    final String fullEndPoint = createEndpointForApiCall("logout.php", false);
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

    String response = makeRequest("oauth/access_token", Parameter.with("grant_type", "client_credentials"),
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
    verifyParameterPresence("code", code);

    ObjectUtil.requireNotNull(accessToken,
      () -> new IllegalStateException("access token is required to fetch a device access token"));

    try {
      String response = makeRequest("device/login_status", true, false, null, Parameter.with("type", "device_token"),
        Parameter.with("code", code));
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

    String response = makeRequest("oauth/access_token", Parameter.with(CLIENT_ID, appId),
      Parameter.with(PARAM_CLIENT_SECRET, appSecret), Parameter.with("code", verificationCode),
      Parameter.with("redirect_uri", redirectUri));

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

  /**
   * @see com.restfb.FacebookClient#obtainExtendedAccessToken(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public AccessToken obtainExtendedAccessToken(String appId, String appSecret, String accessToken) {
    verifyParameterPresence(APP_ID, appId);
    verifyParameterPresence(APP_SECRET, appSecret);
    verifyParameterPresence("accessToken", accessToken);

    String response = makeRequest("/oauth/access_token", false, false, null, Parameter.with(CLIENT_ID, appId),
      Parameter.with(PARAM_CLIENT_SECRET, appSecret), Parameter.with("grant_type", "fb_exchange_token"),
      Parameter.with("fb_exchange_token", accessToken));

    try {
      return getAccessTokenFromResponse(response);
    } catch (Exception t) {
      throw new FacebookResponseContentException(CANNOT_EXTRACT_ACCESS_TOKEN_MESSAGE, t);
    }
  }

  private AccessToken getAccessTokenFromResponse(String response) {
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

    // Convert payload to a JsonObject so we can pull algorithm data out of it
    JsonObject payloadObject = getJsonMapper().toJavaObject(payload, JsonObject.class);

    if (!payloadObject.contains("algorithm")) {
      throw new FacebookSignedRequestParsingException("Unable to detect algorithm used for signed request");
    }

    String algorithm = payloadObject.getString("algorithm", null);

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
  public String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope, Parameter... parameters) {
    verifyParameterPresence(APP_ID, appId);
    verifyParameterPresence("redirectUri", redirectUri);
    verifyParameterPresence(SCOPE, scope);

    String dialogUrl = getFacebookEndpointUrls().getFacebookEndpoint() + "/dialog/oauth";

    List<Parameter> parameterList = new ArrayList<>();
    parameterList.add(Parameter.with(CLIENT_ID, appId));
    parameterList.add(Parameter.with("redirect_uri", redirectUri));
    parameterList.add(Parameter.with(SCOPE, scope.toString()));

    // add optional parameters
    Collections.addAll(parameterList, parameters);

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

    try {
      JsonObject json = Json.parse(response).asObject();
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
        createEndpointForApiCall(endpoint, binaryAttachments != null && !binaryAttachments.isEmpty());
    final String parameterString = toParameterString(parameters);

    return makeRequestAndProcessResponse(() -> {
      WebRequestor.Request request = new WebRequestor.Request(fullEndpoint, getHeaderAccessToken(), parameterString);
      if (executeAsDelete && !isHttpDeleteFallback()) {
        return webRequestor.executeDelete(request);
      }

      if (executeAsPost) {
        request.setBinaryAttachments(binaryAttachments);
        return webRequestor.executePost(request);
      }

      return webRequestor.executeGet(request);
    });
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
   *          <code>true</code> if the the http Delete Fallback is used
   */
  public void setHttpDeleteFallback(boolean httpDeleteFallback) {
    this.httpDeleteFallback = httpDeleteFallback;
  }

  protected interface Requestor {
    Response makeRequest() throws IOException;
  }

  protected String makeRequestAndProcessResponse(Requestor requestor) {
    Response response;

    // Perform a GET or POST to the API endpoint
    try {
      response = requestor.makeRequest();
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

    String json = response.getBody();

    try {
      // If the response contained an error code, throw an exception.
      getFacebookExceptionGenerator().throwFacebookResponseStatusExceptionIfNecessary(json, response.getStatusCode());
    } catch (FacebookErrorMessageException feme) {
      Optional.ofNullable(getWebRequestor()).map(WebRequestor::getDebugHeaderInfo).ifPresent(feme::setDebugHeaderInfo);
      throw feme;
    }

    // If there was no response error information and this was a 500 or 401
    // error, something weird happened on Facebook's end. Bail.
    if (HTTP_INTERNAL_ERROR == response.getStatusCode() || HTTP_UNAUTHORIZED == response.getStatusCode()) {
      throw new FacebookNetworkException(response.getStatusCode());
    }

    return json;
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
      parameters = parametersWithAdditionalParameter(
        Parameter.with(APP_SECRET_PROOF_PARAM_NAME, obtainAppSecretProof(accessToken, appSecret)), parameters);
    }

    if (withJsonParameter) {
      parameters = parametersWithAdditionalParameter(Parameter.with(FORMAT_PARAM_NAME, "json"), parameters);
    }

    return Stream.of(parameters).map(p -> urlEncode(p.name) + "=" + urlEncodedValueForParameterName(p.name, p.value))
      .collect(Collectors.joining("&"));
  }

  /**
   * @see com.restfb.BaseFacebookClient#createEndpointForApiCall(java.lang.String,boolean)
   */
  @Override
  protected String createEndpointForApiCall(String apiCall, boolean hasAttachment) {
    while (apiCall.startsWith("/"))
      apiCall = apiCall.substring(1);

    String baseUrl = getFacebookGraphEndpointUrl();

    if (hasAttachment && (apiCall.endsWith("/videos") || apiCall.endsWith("/advideos"))) {
      baseUrl = getFacebookGraphVideoEndpointUrl();
    } else if (apiCall.endsWith("logout.php")) {
      baseUrl = getFacebookEndpointUrls().getFacebookEndpoint();
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
      return getFacebookEndpointUrls().getGraphEndpoint() + '/' + apiVersion.getUrlElement();
    } else {
      return getFacebookEndpointUrls().getGraphEndpoint();
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
      return getFacebookEndpointUrls().getGraphVideoEndpoint() + '/' + apiVersion.getUrlElement();
    } else {
      return getFacebookEndpointUrls().getGraphVideoEndpoint();
    }
  }

  public FacebookEndpoints getFacebookEndpointUrls() {
    return facebookEndpointUrls;
  }

  public void setFacebookEndpointUrls(FacebookEndpoints facebookEndpointUrls) {
    this.facebookEndpointUrls = facebookEndpointUrls;
  }
}
