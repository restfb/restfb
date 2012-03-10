/*
 * Copyright (c) 2010-2012 Mark Allen.
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

import static com.restfb.util.StringUtils.isBlank;
import static com.restfb.util.StringUtils.toBytes;
import static com.restfb.util.StringUtils.trimToEmpty;
import static com.restfb.util.StringUtils.urlEncode;
import static java.net.HttpURLConnection.HTTP_OK;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.restfb.WebRequestor.Response;
import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.exception.FacebookNetworkException;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;

/**
 * Default implementation of a <a
 * href="http://developers.facebook.com/docs/reference/rest/">Legacy Facebook
 * API</a> client.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class DefaultLegacyFacebookClient extends BaseFacebookClient implements LegacyFacebookClient {
  /**
   * Facebook API key.
   */
  protected String apiKey;

  /**
   * Facebook application secret key.
   */
  protected String secretKey;

  /**
   * OAuth Access token.
   */
  protected String accessToken;

  /**
   * API endpoint URL.
   */
  protected static final String FACEBOOK_REST_ENDPOINT_URL = "https://api.facebook.com/restserver.php";

  /**
   * Read-only API endpoint URL.
   */
  protected static final String FACEBOOK_READ_ONLY_ENDPOINT_URL = "https://api-read.facebook.com/restserver.php";

  // Common parameter names/values that must be included in all API requests
  protected static final String METHOD_PARAM_NAME = "method";
  protected static final String FORMAT_PARAM_NAME = "format";
  protected static final String FORMAT_PARAM_VALUE = "json";

  // Common parameter names/values that must be included in all API requests
  // that do not use the new OAuth scheme.
  protected static final String API_KEY_PARAM_NAME = "api_key";
  protected static final String CALL_ID_PARAM_NAME = "call_id";
  protected static final String SIG_PARAM_NAME = "sig";
  protected static final String SESSION_KEY_PARAM_NAME = "session_key";
  protected static final String VERSION_PARAM_NAME = "v";
  protected static final String VERSION_PARAM_VALUE = "1.0";

  /**
   * Creates a Facebook API client with the given <a
   * href="http://developers.facebook.com/docs/guides/upgrade#oauth">OAuth
   * access token</a>.
   * 
   * @param accessToken
   *          An OAuth access token.
   * @throws NullPointerException
   *           If {@code accessToken} is {@code null}.
   * @throws IllegalArgumentException
   *           If {@code accessToken} is a blank string.
   * @since 1.5
   */
  public DefaultLegacyFacebookClient(String accessToken) {
    this(accessToken, new DefaultWebRequestor(), new DefaultJsonMapper());
  }

  /**
   * Creates a Facebook API client with the given API key and secret key (Legacy
   * authentication).
   * 
   * @param apiKey
   *          A Facebook API key.
   * @param secretKey
   *          A Facebook application secret key.
   * @throws NullPointerException
   *           If either parameter is {@code null}.
   * @throws IllegalArgumentException
   *           If either parameter is a blank string.
   * @deprecated You should use {@link #DefaultLegacyFacebookClient(String)}
   *             instead. Facebook is moving to OAuth and will stop supporting
   *             the old authentication scheme soon.
   */
  @Deprecated
  public DefaultLegacyFacebookClient(String apiKey, String secretKey) {
    this(apiKey, secretKey, new DefaultWebRequestor(), new DefaultJsonMapper());
  }

  /**
   * Creates a Facebook API client with the given API key, secret key,
   * {@code webRequestor}, and {@code jsonMapper} (Legacy authentication).
   * 
   * @param apiKey
   *          A Facebook API key.
   * @param secretKey
   *          A Facebook application secret key.
   * @param webRequestor
   *          The {@link WebRequestor} implementation to use for {@code POST}ing
   *          to the API endpoint.
   * @param jsonMapper
   *          The {@link JsonMapper} implementation to use for mapping API
   *          response JSON to Java objects.
   * @throws NullPointerException
   *           If any parameter is {@code null}.
   * @throws IllegalArgumentException
   *           If either {@code apiKey} or {@code secretKey} is a blank string.
   * @deprecated You should use
   *             {@link #DefaultLegacyFacebookClient(String, WebRequestor, JsonMapper)}
   *             instead. Facebook is moving to OAuth and will stop supporting
   *             the old authentication scheme soon.
   */
  @Deprecated
  public DefaultLegacyFacebookClient(String apiKey, String secretKey, WebRequestor webRequestor, JsonMapper jsonMapper) {
    super();

    verifyParameterPresence("apiKey", apiKey);
    verifyParameterPresence("secretKey", secretKey);
    verifyParameterPresence("webRequestor", webRequestor);
    verifyParameterPresence("jsonMapper", jsonMapper);

    this.apiKey = apiKey.trim();
    this.secretKey = secretKey.trim();
    this.webRequestor = webRequestor;
    this.jsonMapper = jsonMapper;

    initializeIllegalParamNames();
  }

  /**
   * Creates a Facebook API client with the given <a
   * href="http://developers.facebook.com/docs/guides/upgrade#oauth">OAuth
   * access token</a>.
   * 
   * @param accessToken
   *          An OAuth access token.
   * @param webRequestor
   *          The {@link WebRequestor} implementation to use for {@code POST}ing
   *          to the API endpoint.
   * @param jsonMapper
   *          The {@link JsonMapper} implementation to use for mapping API
   *          response JSON to Java objects.
   * @throws NullPointerException
   *           If any parameter is {@code null}.
   * @throws IllegalArgumentException
   *           If {@code accessToken} is a blank string.
   * @since 1.5
   */
  public DefaultLegacyFacebookClient(String accessToken, WebRequestor webRequestor, JsonMapper jsonMapper) {
    super();

    verifyParameterPresence("accessToken", accessToken);
    verifyParameterPresence("webRequestor", webRequestor);
    verifyParameterPresence("jsonMapper", jsonMapper);

    this.accessToken = accessToken.trim();
    this.webRequestor = webRequestor;
    this.jsonMapper = jsonMapper;

    initializeIllegalParamNames();
  }

  /**
   * @see com.restfb.LegacyFacebookClient#execute(java.lang.String,
   *      com.restfb.Parameter[])
   */
  @Override
  public void execute(String method, Parameter... parameters) {
    execute(method, (String) null, parameters);
  }

  /**
   * @see com.restfb.LegacyFacebookClient#execute(java.lang.String,
   *      java.lang.String, com.restfb.Parameter[])
   */
  @Override
  public void execute(String method, String sessionKey, Parameter... parameters) {
    makeRequest(method, sessionKey, parameters);
  }

  /**
   * @see com.restfb.LegacyFacebookClient#execute(java.lang.String,
   *      java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> T execute(String method, Class<T> resultType, Parameter... parameters) {
    return execute(method, null, resultType, parameters);
  }

  /**
   * @see com.restfb.LegacyFacebookClient#execute(java.lang.String,
   *      java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> T execute(String method, String sessionKey, Class<T> resultType, Parameter... parameters) {
    return jsonMapper.toJavaObject(makeRequest(method, sessionKey, parameters), resultType);
  }

  /**
   * @see com.restfb.LegacyFacebookClient#executeForList(java.lang.String,
   *      java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> List<T> executeForList(String method, Class<T> resultType, Parameter... parameters) {
    return executeForList(method, null, resultType, parameters);
  }

  /**
   * @see com.restfb.LegacyFacebookClient#executeForList(java.lang.String,
   *      java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> List<T> executeForList(String method, String sessionKey, Class<T> resultType, Parameter... parameters) {
    return jsonMapper.toJavaList(makeRequest(method, sessionKey, parameters), resultType);
  }

  /**
   * @see com.restfb.LegacyFacebookClient#executeMultiquery(java.util.Map,
   *      java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> T executeMultiquery(Map<String, String> queries, Class<T> resultType, Parameter... additionalParameters) {
    return executeMultiquery(queries, resultType, additionalParameters);
  }

  /**
   * @see com.restfb.LegacyFacebookClient#executeMultiquery(java.util.Map,
   *      java.lang.String, java.lang.Class, com.restfb.Parameter[])
   */
  @Override
  public <T> T executeMultiquery(Map<String, String> queries, String sessionKey, Class<T> resultType,
      Parameter... additionalParameters) {
    List<Parameter> parameters = new ArrayList<Parameter>();
    parameters.add(Parameter.with("queries", queriesToJson(queries)));

    for (Parameter additionalParameter : additionalParameters) {
      if (additionalParameter.name.equals("queries"))
        throw new IllegalArgumentException("You cannot specify a parameter named 'queries' "
            + "because it's reserved for use by RestFB for this call. "
            + "Specify your queries in the Map that gets passed to this method.");

      parameters.add(additionalParameter);
    }

    JsonObject normalizedJson = new JsonObject();

    try {
      JsonArray jsonArray =
          new JsonArray(makeRequest("fql.multiquery", sessionKey, parameters.toArray(new Parameter[0])));

      for (int i = 0; i < jsonArray.length(); i++) {
        JsonObject jsonObject = jsonArray.getJsonObject(i);

        // For empty resultsets, Facebook will return an empty object instead of
        // an empty list. Hack around that here.
        JsonArray resultsArray =
            jsonObject.get("fql_result_set") instanceof JsonArray ? jsonObject.getJsonArray("fql_result_set")
                : new JsonArray();

        normalizedJson.put(jsonObject.getString("name"), resultsArray);
      }
    } catch (JsonException e) {
      throw new FacebookJsonMappingException("Unable to process fql.multiquery JSON response", e);
    }

    return jsonMapper.toJavaObject(normalizedJson.toString(), resultType);
  }

  /**
   * Coordinates the process of verifying and transforming API parameters,
   * executing the API POST, and processing the response we receive from the
   * endpoint.
   * 
   * @param method
   *          Facebook API method name.
   * @param sessionKey
   *          Facebook API session key (can be {@code null} or a blank string).
   * @param parameters
   *          Arbitrary number of parameters to send along to Facebook as part
   *          of the API call.
   * @return The JSON returned by Facebook for the API call.
   * @throws FacebookException
   *           If an error occurs while making the Facebook API POST or
   *           processing the response.
   */
  protected String makeRequest(String method, String sessionKey, Parameter... parameters) {
    // Make sure we're not provided with any params that conflict with what
    // we're passing to FB internally
    verifyParameterLegality(parameters);

    // Turn the set of parameters into a string per the API spec
    String parametersAsString = toParameterString(method, sessionKey, parameters);

    Response response = null;

    // Perform a POST to the API endpoint
    try {
      response = webRequestor.executePost(createEndpointForApiCall(method, false), parametersAsString);
    } catch (Throwable t) {
      throw new FacebookNetworkException("Facebook POST failed", t);
    }

    // If we get any HTTP response code other than a 200 OK, throw an exception
    if (HTTP_OK != response.getStatusCode())
      throw new FacebookNetworkException("Facebook POST failed", response.getStatusCode());

    String json = response.getBody();

    // If the response contained an error code, throw an exception
    throwLegacyFacebookResponseStatusExceptionIfNecessary(json);

    return json;
  }

  /**
   * Given basic request information, generate the parameter string to be
   * included in the Facebook API POST.
   * 
   * @param method
   *          Facebook API method name.
   * @param sessionKey
   *          Facebook API session key (can be {@code null} or a blank string).
   * @param parameters
   *          Arbitrary number of extra parameters to include in the request.
   * @return The parameter string to include in the Facebook API POST.
   * @throws IllegalArgumentException
   *           If a session key is provided but we're using OAuth authentication
   *           instead.
   */
  protected String toParameterString(String method, String sessionKey, Parameter... parameters) {
    Map<String, String> sortedParameters = new TreeMap<String, String>();

    for (Parameter param : parameters)
      sortedParameters.put(param.name, param.value);

    sortedParameters.put(FORMAT_PARAM_NAME, FORMAT_PARAM_VALUE);
    sortedParameters.put(METHOD_PARAM_NAME, method);

    // New OAuth access token parameter is simple: just one value.
    // The legacy stuff requires a bunch more work.
    if (usesAccessTokenAuthentication()) {
      if (sessionKey != null)
        throw new IllegalArgumentException("If you're using the OAuth access token "
            + "for authentication, you cannot " + "specify a session key.");

      sortedParameters.put(ACCESS_TOKEN_PARAM_NAME, accessToken);
    } else {
      sortedParameters.put(API_KEY_PARAM_NAME, apiKey);
      sortedParameters.put(VERSION_PARAM_NAME, VERSION_PARAM_VALUE);
      sortedParameters.put(CALL_ID_PARAM_NAME, String.valueOf(System.currentTimeMillis()));

      if (!isBlank(sessionKey))
        sortedParameters.put(SESSION_KEY_PARAM_NAME, sessionKey);

      sortedParameters.put(SIG_PARAM_NAME, generateSignature(sortedParameters));
    }

    StringBuilder parameterStringBuilder = new StringBuilder();
    boolean first = true;

    for (Entry<String, String> entry : sortedParameters.entrySet()) {
      if (first)
        first = false;
      else
        parameterStringBuilder.append("&");

      parameterStringBuilder.append(urlEncode(entry.getKey()));
      parameterStringBuilder.append("=");
      parameterStringBuilder.append(usesAccessTokenAuthentication() ? urlEncodedValueForParameterName(entry.getKey(),
        entry.getValue()) : urlEncode(entry.getValue()));
    }

    return parameterStringBuilder.toString();
  }

  /**
   * Given a sorted map of parameter names to values, calculate and return the
   * Facebook API signature as defined by
   * http://wiki.developers.facebook.com/index.php/Verifying_The_Signature.
   * 
   * @param sortedParameters
   *          Parameter name/value mappings, sorted alphabetically.
   * @return The Facebook API signature which matches the given parameter map.
   */
  protected String generateSignature(Map<String, String> sortedParameters) {
    StringBuilder parameterString = new StringBuilder();

    for (Entry<String, String> entry : sortedParameters.entrySet()) {
      parameterString.append(entry.getKey());
      parameterString.append("=");
      parameterString.append(entry.getValue());
    }

    parameterString.append(secretKey);
    return generateMd5(parameterString.toString());
  }

  /**
   * Generate an MD5 hash of the given {@code string}.
   * 
   * @param string
   *          The string for which an MD5 hash is calculated.
   * @return The MD5 hash of the given {@code string}.
   * @throws IllegalStateException
   *           If MD5 hashing isn't supported on this platform (should never
   *           occur).
   */
  protected String generateMd5(String string) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      byte[] bytes = toBytes(string);

      StringBuilder result = new StringBuilder();
      for (byte b : messageDigest.digest(bytes)) {
        result.append(Integer.toHexString((b & 0xf0) >>> 4));
        result.append(Integer.toHexString(b & 0x0f));
      }
      return result.toString();
    } catch (NoSuchAlgorithmException e) {
      // Should never happen
      throw new IllegalStateException("MD5 isn't available on this JVM", e);
    }
  }

  /**
   * Are we using OAuth access token authentication?
   * 
   * @return {@code true} if we are, {@code false} if we're using the legacy
   *         authentication scheme.
   */
  protected boolean usesAccessTokenAuthentication() {
    return !isBlank(accessToken);
  }

  /**
   * @see com.restfb.BaseFacebookClient#createEndpointForApiCall(java.lang.String,boolean)
   */
  @Override
  protected String createEndpointForApiCall(String apiCall, boolean hasAttachment) {
    apiCall = trimToEmpty(apiCall).toLowerCase();
    return readOnlyApiCalls.contains(apiCall) ? getFacebookReadOnlyEndpointUrl() : getFacebookRestEndpointUrl();
  }

  /**
   * Initializes the set of illegal URL parameter names.
   */
  protected void initializeIllegalParamNames() {
    illegalParamNames.addAll(Arrays.asList(new String[] { API_KEY_PARAM_NAME, CALL_ID_PARAM_NAME, SIG_PARAM_NAME,
        METHOD_PARAM_NAME, SESSION_KEY_PARAM_NAME, FORMAT_PARAM_NAME, VERSION_PARAM_NAME, ACCESS_TOKEN_PARAM_NAME }));
  }

  /**
   * Returns the base endpoint URL for the Old REST API.
   * 
   * @return The base endpoint URL for the Old REST API.
   */
  protected String getFacebookRestEndpointUrl() {
    return FACEBOOK_REST_ENDPOINT_URL;
  }

  /**
   * @see com.restfb.BaseFacebookClient#getFacebookReadOnlyEndpointUrl()
   */
  @Override
  protected String getFacebookReadOnlyEndpointUrl() {
    return FACEBOOK_READ_ONLY_ENDPOINT_URL;
  }
}