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

import static com.restfb.util.UrlUtils.extractParametersFromQueryString;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;
import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.exception.FacebookSignedRequestParsingException;
import com.restfb.exception.FacebookSignedRequestVerificationException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenCodeExpiredException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenDeclinedException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenPendingException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenSlowdownException;
import com.restfb.json.JsonObject;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.DeviceCode;
import com.restfb.util.ReflectionUtils;
import java.io.Serializable;

/**
 * Specifies how a <a href="http://developers.facebook.com/docs/api">Facebook Graph API</a> client must operate.
 * <p>
 * Projects that need to access the <a href="http://developers.facebook.com/docs/reference/rest/">old REST API</a>
 * should use {@link LegacyFacebookClient} instead. You might choose to do this because you have a legacy codebase or
 * you need functionality that is not yet available in the Graph API.
 * <p>
 * If you'd like to...
 * 
 * <ul>
 * <li>Fetch an object: use {@link #fetchObject(String, Class, Parameter...)} or
 * {@link #fetchObjects(List, Class, Parameter...)}</li>
 * <li>Fetch a connection: use {@link #fetchConnection(String, Class, Parameter...)}</li>
 * <li>Execute an FQL query: use {@link #executeFqlQuery(String, Class, Parameter...)} or
 * {@link #executeFqlMultiquery(Map, Class, Parameter...)}</li>
 * <li>Execute operations in batch: use {@link #executeBatch(BatchRequest...)} or {@link #executeBatch(List, List)}</li>
 * <li>Publish data: use {@link #publish(String, Class, Parameter...)} or
 * {@link #publish(String, Class, BinaryAttachment, Parameter...)}</li>
 * <li>Delete an object: use {@link #deleteObject(String, Parameter...)}</li>
 * </ul>
 * 
 * <p>
 * You may also perform some common access token operations. If you'd like to...
 * 
 * <ul>
 * <li>Extend the life of an access token: use {@link #obtainExtendedAccessToken(String, String, String)}</li>
 * <li>Obtain an access token for use on behalf of an application instead of a user, use
 * {@link #obtainAppAccessToken(String, String)}.</li>
 * <li>Convert old-style session keys to OAuth access tokens: use
 * {@link #convertSessionKeysToAccessTokens(String, String, String...)}</li>
 * <li>Verify and extract data from a signed request: use {@link #parseSignedRequest(String, String, Class)}</li>
 * </ul>
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author Scott Hernandez
 * @author Mattia Tommasone
 * @author <a href="http://ex-nerd.com">Chris Petersen</a>
 * @author Josef Gierbl
 * @author Broc Seib
 */
public interface FacebookClient {
  /**
   * Fetches a single <a href="http://developers.facebook.com/docs/reference/api/">Graph API object</a>, mapping the
   * result to an instance of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param object
   *          ID of the object to fetch, e.g. {@code "me"}.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code objectType} which contains the requested object's data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters);

  /**
   * Fetches multiple <a href="http://developers.facebook.com/docs/reference/api/">Graph API objects</a> in a single
   * call, mapping the results to an instance of {@code objectType}.
   * <p>
   * You'll need to write your own container type ({@code objectType}) to hold the results. See <a
   * href="http://restfb.com">http://restfb.com</a> for an example of how to do this.
   * 
   * @param <T>
   *          Java type to map to.
   * @param ids
   *          IDs of the objects to fetch, e.g. {@code "me", "arjun"}.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code objectType} which contains the requested objects' data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T fetchObjects(List<String> ids, Class<T> objectType, Parameter... parameters);

  /**
   * Fetches a Graph API {@code Connection} type, mapping the result to an instance of {@code connectionType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The name of the connection, e.g. {@code "me/feed"}.
   * @param connectionType
   *          Connection type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code connectionType} which contains the requested Connection's data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> Connection<T> fetchConnection(String connection, Class<T> connectionType, Parameter... parameters);

  /**
   * Fetches a previous/next page of a Graph API {@code Connection} type, mapping the result to an instance of
   * {@code connectionType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connectionPageUrl
   *          The URL of the connection page to fetch, usually retrieved via {@link Connection#getPreviousPageUrl()} or
   *          {@link Connection#getNextPageUrl()}.
   * @param connectionType
   *          Connection type token.
   * @return An instance of type {@code connectionType} which contains the requested Connection's data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> Connection<T> fetchConnectionPage(String connectionPageUrl, Class<T> connectionType);

  /**
   * Executes an <a href="http://developers.facebook.com/docs/reference/fql/">FQL query</a>, mapping the resultset to a
   * {@code List} of instances of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param query
   *          The FQL query to execute, e.g. {@code "SELECT name FROM user WHERE uid=220439 or uid=7901103"}.
   * @param objectType
   *          Resultset object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return A list of instances of {@code objectType} which map to the query results.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   * @deprecated As of 1.6.12, prefer {@link #executeFqlQuery(String, Class, Parameter...)} because it connects to the
   *             Graph API FQL endpoint instead of the legacy FQL endpoint.
   */
  @Deprecated
  <T> List<T> executeQuery(String query, Class<T> objectType, Parameter... parameters);

  /**
   * Executes an <a href="http://developers.facebook.com/docs/reference/fql/">FQL multiquery</a>, which allows you to
   * batch multiple queries into a single request.
   * <p>
   * You'll need to write your own container type ({@code objectType}) to hold the results. See <a
   * href="http://restfb.com">http://restfb.com</a> for an example of how to do this.
   * 
   * @param <T>
   *          Java type to map to.
   * @param queries
   *          A mapping of query names to queries. This is marshaled to JSON and sent over the wire to the Facebook API
   *          endpoint as the {@code queries} parameter.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code objectType} which contains the requested objects' data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   * @deprecated As of 1.6.12, prefer {@link #executeFqlMultiquery(Map, Class, Parameter...)} because it connects to the
   *             Graph API FQL endpoint instead of the legacy FQL endpoint.
   */
  @Deprecated
  <T> T executeMultiquery(Map<String, String> queries, Class<T> objectType, Parameter... parameters);

  /**
   * Executes an <a href="http://developers.facebook.com/docs/reference/fql/">FQL query</a>, mapping the resultset to a
   * {@code List} of instances of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param query
   *          The FQL query to execute, e.g. {@code "SELECT name FROM user WHERE uid=220439 or uid=7901103"}.
   * @param objectType
   *          Resultset object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return A list of instances of {@code objectType} which map to the query results.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   * @since 1.6.12
   */
  <T> List<T> executeFqlQuery(String query, Class<T> objectType, Parameter... parameters);

  /**
   * Executes an <a href="http://developers.facebook.com/docs/reference/fql/">FQL multiquery</a>, which allows you to
   * batch multiple queries into a single request.
   * <p>
   * You'll need to write your own container type ({@code objectType}) to hold the results. See <a
   * href="http://restfb.com">http://restfb.com</a> for an example of how to do this.
   * 
   * @param <T>
   *          Java type to map to.
   * @param queries
   *          A mapping of query names to queries. This is marshaled to JSON and sent over the wire to the Facebook API
   *          endpoint as the {@code q} parameter.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code objectType} which contains the requested objects' data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T executeFqlMultiquery(Map<String, String> queries, Class<T> objectType, Parameter... parameters);

  /**
   * Executes operations as a batch using the <a href="https://developers.facebook.com/docs/reference/api/batch/">Batch
   * API</a>.
   * 
   * @param batchRequests
   *          The operations to execute.
   * @return The execution results in the order in which the requests were specified.
   */
  List<BatchResponse> executeBatch(BatchRequest... batchRequests);

  /**
   * Executes operations as a batch using the <a href="https://developers.facebook.com/docs/reference/api/batch/">Batch
   * API</a>.
   * 
   * @param batchRequests
   *          The operations to execute.
   * @return The execution results in the order in which the requests were specified.
   */
  List<BatchResponse> executeBatch(List<BatchRequest> batchRequests);

  /**
   * Executes operations as a batch with binary attachments using the <a
   * href="https://developers.facebook.com/docs/reference/api/batch/">Batch API</a>.
   * 
   * @param batchRequests
   *          The operations to execute.
   * @param binaryAttachments
   *          Binary attachments referenced by the batch requests.
   * @return The execution results in the order in which the requests were specified.
   * @since 1.6.5
   */
  List<BatchResponse> executeBatch(List<BatchRequest> batchRequests, List<BinaryAttachment> binaryAttachments);

  /**
   * Performs a <a href="http://developers.facebook.com/docs/api#publishing">Graph API publish</a> operation on the
   * given {@code connection}, mapping the result to an instance of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The Connection to publish to.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return An instance of type {@code objectType} which contains the Facebook response to your publish request.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T publish(String connection, Class<T> objectType, Parameter... parameters);

  /**
   * Performs a <a href="http://developers.facebook.com/docs/api#publishing">Graph API publish</a> operation on the
   * given {@code connection} and includes some files - photos, for example - in the publish request, and mapping the
   * result to an instance of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The Connection to publish to.
   * @param objectType
   *          Object type token.
   * @param binaryAttachments
   *          The files to include in the publish request.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return An instance of type {@code objectType} which contains the Facebook response to your publish request.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T publish(String connection, Class<T> objectType, List<BinaryAttachment> binaryAttachments,
      Parameter... parameters);

  /**
   * Performs a <a href="http://developers.facebook.com/docs/api#publishing">Graph API publish</a> operation on the
   * given {@code connection} and includes a file - a photo, for example - in the publish request, and mapping the
   * result to an instance of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The Connection to publish to.
   * @param objectType
   *          Object type token.
   * @param binaryAttachment
   *          The file to include in the publish request.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return An instance of type {@code objectType} which contains the Facebook response to your publish request.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T publish(String connection, Class<T> objectType, BinaryAttachment binaryAttachment, Parameter... parameters);

  /**
   * Performs a <a href="http://developers.facebook.com/docs/api#deleting">Graph API delete</a> operation on the given
   * {@code object}.
   * 
   * @param object
   *          The ID of the object to delete.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return {@code true} if Facebook indicated that the object was successfully deleted, {@code false} otherwise.
   * @throws FacebookException
   *           If an error occurred while attempting to delete the object.
   */
  boolean deleteObject(String object, Parameter... parameters);

  /**
   * Converts an arbitrary number of {@code sessionKeys} to OAuth access tokens.
   * <p>
   * See the <a href="http://developers.facebook.com/docs/guides/upgrade">Facebook Platform Upgrade Guide</a> for
   * details on how this process works and why you should convert your application's session keys if you haven't
   * already.
   * 
   * @param appId
   *          A Facebook application ID.
   * @param secretKey
   *          A Facebook application secret key.
   * @param sessionKeys
   *          The Old REST API session keys to be converted to OAuth access tokens.
   * @return A list of access tokens ordered to correspond to the {@code sessionKeys} argument list.
   * @throws FacebookException
   *           If an error occurs while attempting to convert the session keys to API keys.
   * @since 1.6
   */
  List<AccessToken> convertSessionKeysToAccessTokens(String appId, String secretKey, String... sessionKeys);

  /**
   * Obtains an access token which can be used to perform Graph API operations on behalf of a user.
   * <p>
   * See <a href="https://developers.facebook.com/docs/facebook-login/access-tokens">Access Tokens</a>.
   *
   * @param appId
   *          The ID of the app for which you'd like to obtain an access token.
   * @param appSecret
   *          The secret for the app for which you'd like to obtain an access token.
   * @param redirectUri
   *          The redirect URI which was used to obtain the {@code verificationCode}.
   * @param verificationCode
   *          The verification code in the Graph API callback to the redirect URI.
   * @return The access token for the user identified by {@code appId}, {@code appSecret}, {@code redirectUri} and
   *         {@code verificationCode}.
   * @throws FacebookException
   *           If an error occurs while attempting to obtain an access token.
   * @since 1.8.0
   */
  AccessToken obtainUserAccessToken(String appId, String appSecret, String redirectUri, String verificationCode);

  /**
   * Obtains an access token which can be used to perform Graph API operations on behalf of an application instead of a
   * user.
   * <p>
   * See <a href="https://developers.facebook.com/docs/authentication/applications/" >Facebook's authenticating as an
   * app documentation</a>.
   * 
   * @param appId
   *          The ID of the app for which you'd like to obtain an access token.
   * @param appSecret
   *          The secret for the app for which you'd like to obtain an access token.
   * @return The access token for the application identified by {@code appId} and {@code appSecret}.
   * @throws FacebookException
   *           If an error occurs while attempting to obtain an access token.
   * @since 1.6.10
   */
  AccessToken obtainAppAccessToken(String appId, String appSecret);

  /**
   * Obtains an extended access token for the given existing, non-expired, short-lived access_token.
   * <p>
   * See <a href="https://developers.facebook.com/roadmap/offline-access-removal/#extend_token">Facebook's extend access
   * token documentation</a>.
   * 
   * @param appId
   *          The ID of the app for which you'd like to obtain an extended access token.
   * @param appSecret
   *          The secret for the app for which you'd like to obtain an extended access token.
   * @param accessToken
   *          The non-expired, short-lived access token to extend.
   * @return An extended access token for the given {@code accessToken}.
   * @throws FacebookException
   *           If an error occurs while attempting to obtain an extended access token.
   * @since 1.6.10
   */
  AccessToken obtainExtendedAccessToken(String appId, String appSecret, String accessToken);

  /**
   * Generates an {@code appsecret_proof} value.
   * <p>
   * See <a href="https://developers.facebook.com/docs/graph-api/securing-requests">Facebook's 'securing requests'
   * documentation</a> for more info.
   * 
   * @param accessToken
   *          The access token required to generate the {@code appsecret_proof} value.
   * @param appSecret
   *          The secret for the app for which you'd like to generate the {@code appsecret_proof} value.
   * @return A hex-encoded SHA256 hash as a {@code String}.
   * @throws IllegalStateException
   *           If creating the {@code appsecret_proof} fails.
   * @since 1.6.13
   */
  String obtainAppSecretProof(String accessToken, String appSecret);

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
  AccessToken obtainExtendedAccessToken(String appId, String appSecret);

  /**
   * Parses a signed request and verifies it against your App Secret.
   * <p>
   * See <a href="http://developers.facebook.com/docs/howtos/login/signed-request/">Facebook's signed request
   * documentation</a>.
   * 
   * @param signedRequest
   *          The signed request to parse.
   * @param appSecret
   *          The secret for the app that can read this signed request.
   * @param objectType
   *          Object type token.
   * @param <T>
   *          class of objectType
   * @return An instance of type {@code objectType} which contains the decoded object embedded within
   *         {@code signedRequest}.
   * @throws FacebookSignedRequestParsingException
   *           If an error occurs while trying to process {@code signedRequest}.
   * @throws FacebookSignedRequestVerificationException
   *           If {@code signedRequest} fails verification against {@code appSecret}.
   * @since 1.6.13
   */
  <T> T parseSignedRequest(String signedRequest, String appSecret, Class<T> objectType);

  /**
   * Method to initialize the device access token generation.
   * 
   * You receive a {@link DeviceCode} instance and have to show the user the {@link DeviceCode#getVerificationUri()} and
   * the {@link DeviceCode#getUserCode()}. The user have to enter the user code at the verification url.
   * 
   * Save the {@link DeviceCode#getCode()} to use it later, when polling Facebook with the
   * {@link #obtainDeviceAccessToken(java.lang.String, java.lang.String)} method.
   * 
   * @param appId
   *          The ID of your app, found in your app's dashboard.
   * @param scope
   *          List of Permissions to request from the person using your app.
   * @return Instance of {@code DeviceCode} including the information to obtain the Device access token
   * @since 1.12.0
   */
  DeviceCode fetchDeviceCode(String appId, ScopeBuilder scope);

  /**
   * Method to poll Facebook and fetch the Device Access Token.
   * 
   * You have to use this method to check if the user confirms the authorization.
   * 
   * {@link FacebookOAuthException} can be thrown if the authorization is declined or still pending.
   * 
   * @param appId
   *          The ID of your app, found in your app's dashboard.
   * @param code
   *          The device
   * @return An extended access token for the given {@link AccessToken}.
   * @throws com.restfb.exception.devicetoken.FacebookDeviceTokenCodeExpiredException
   *           the {@link DeviceCode#getCode()} is expired, please fetch a new {@link DeviceCode}. Call
   *           {@link #fetchDeviceCode(java.lang.String, com.restfb.scope.ScopeBuilder) }
   * @throws com.restfb.exception.devicetoken.FacebookDeviceTokenPendingException
   *           the user has not finished the authorisation process, yet. Please poll again later.
   * @throws com.restfb.exception.devicetoken.FacebookDeviceTokenDeclinedException
   *           the user declined the authorisation. You have to handle this problem.
   * @throws com.restfb.exception.devicetoken.FacebookDeviceTokenSlowdownException
   *           you tried too often to fetch the device access token. You have to use a larger interval
   * @since 1.12.0
   */
  AccessToken obtainDeviceAccessToken(String appId, String code) throws FacebookDeviceTokenCodeExpiredException,
      FacebookDeviceTokenPendingException, FacebookDeviceTokenDeclinedException, FacebookDeviceTokenSlowdownException;

  /**
   * <p>
   * When working with access tokens, you may need to check what information is associated with them, such as its user
   * or expiry. To get this information you can use the debug tool in the developer site, or you can use this function.
   * </p>
   * 
   * <p>
   * You must instantiate your FacebookClient using your App Access Token, or a valid User Access Token from a developer
   * of the app.
   * </p>
   * 
   * <p>
   * Note that if your app is set to Native/Desktop in the Advanced settings of your App Dashboard, the underlying
   * GraphAPI endpoint will not work with your app token unless you change the "App Secret in Client" setting to NO. If
   * you do not see this setting, make sure your "App Type" is set to Native/Desktop and then press the save button at
   * the bottom of the page. This will not affect apps set to Web.
   * </p>
   * 
   * <p>
   * The response of the API call is a JSON array containing data and a map of fields. For example:
   * </p>
   * 
   * <pre>
   * {@code
   * {
   *     "data": {
   *         "app_id": 138483919580948, 
   *         "application": "Social Cafe", 
   *         "expires_at": 1352419328, 
   *         "is_valid": true, 
   *         "issued_at": 1347235328, 
   *         "metadata": {
   *             "sso": "iphone-safari"
   *         }, 
   *         "scopes": [
   *             "email", 
   *             "publish_actions"
   *         ], 
   *         "user_id": 1207059
   *     }
   * }
   * }
   * </pre>
   * 
   * <p>
   * Note that the {@code issued_at} field is not returned for short-lived access tokens.
   * </p>
   * 
   * <p>
   * See <a href="https://developers.facebook.com/docs/howtos/login/debugging-access-tokens/"> Debugging an Access
   * Token</a>
   * </p>
   * 
   * @param inputToken
   *          The Access Token to debug.
   * 
   * @return A JsonObject containing the debug information for the accessToken.
   * @since 1.6.13
   */
  DebugTokenInfo debugToken(String inputToken);

  /**
   * Gets the {@code JsonMapper} used to convert Facebook JSON to Java objects.
   * 
   * @return The {@code JsonMapper} used to convert Facebook JSON to Java objects.
   * @since 1.6.7
   */
  JsonMapper getJsonMapper();

  /**
   * Gets the {@code WebRequestor} used to talk to the Facebook API endpoints.
   * 
   * @return The {@code WebRequestor} used to talk to the Facebook API endpoints.
   * @since 1.6.7
   */
  WebRequestor getWebRequestor();

  /**
   * generates an logout url
   * 
   * @param next
   *          may be null, url the webpage should redirect after logout
   * @return the logout url
   * @since 1.9.0
   */
  String getLogoutUrl(String next);

  /**
   * generates the login dialog url
   * 
   * @param appId
   *          The ID of your app, found in your app's dashboard.
   * @param redirectUri
   *          The URL that you want to redirect the person logging in back to. This URL will capture the response from
   *          the Login Dialog. If you are using this in a webview within a desktop app, this must be set to
   *          <code>https://www.facebook.com/connect/login_success.html</code>.
   * @param scope
   *          List of Permissions to request from the person using your app.
   * @since 1.9.0
   * @return
   */
  String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope);

  /**
   * Represents an access token/expiration date pair.
   * <p>
   * Facebook returns these types when performing access token-related operations - see
   * {@link com.restfb.FacebookClient#convertSessionKeysToAccessTokens(String, String, String...)},
   * {@link com.restfb.FacebookClient#obtainAppAccessToken(String, String)}, and
   * {@link com.restfb.FacebookClient#obtainExtendedAccessToken(String, String, String)} for details.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   */
  public static class AccessToken {
    @Facebook("access_token")
    private String accessToken;

    @Facebook("expires_in")
    private Long rawExpires;

    private Long expires;

    @Facebook("token_type")
    private String tokenType;

    /**
     * Given a query string of the form {@code access_token=XXX} or {@code access_token=XXX&expires=YYY}, return an
     * {@code AccessToken} instance.
     * <p>
     * The {@code queryString} is required to contain an {@code access_token} parameter with a non-{@code null} value.
     * The {@code expires} value is optional and should be the number of seconds since the epoch. If the {@code expires}
     * value cannot be parsed, the returned {@code AccessToken} will have a {@code null} {@code expires} value.
     * 
     * @param queryString
     *          The Facebook query string out of which to parse an {@code AccessToken} instance.
     * @return An {@code AccessToken} instance which corresponds to the given {@code queryString}.
     * @throws IllegalArgumentException
     *           If no {@code access_token} parameter is present in the query string.
     * @since 1.6.10
     */
    public static AccessToken fromQueryString(String queryString) {
      // Query string can be of the form 'access_token=XXX' or
      // 'access_token=XXX&expires=YYY'
      Map<String, List<String>> urlParameters = extractParametersFromQueryString(queryString);

      String extendedAccessToken = null;
      String tokenType = null;

      if (urlParameters.containsKey("access_token")) {
        extendedAccessToken = urlParameters.get("access_token").get(0);
      }

      if (urlParameters.containsKey("token_type")) {
        tokenType = urlParameters.get("token_type").get(0);
      }

      if (extendedAccessToken == null) {
        throw new IllegalArgumentException(format(
          "Was expecting a query string of the form 'access_token=XXX' or 'access_token=XXX&expires=YYY'. "
              + "Instead, the query string was '%s'", queryString));
      }

      Long expires = null;

      // If an expires value was provided and it's a valid long, great - use it.
      // Otherwise ignore it.
      if (urlParameters.containsKey("expires")) {
        try {
          expires = Long.valueOf(urlParameters.get("expires").get(0));
        } catch (NumberFormatException e) {}
        if (expires != null) {
          expires = new Date().getTime() + 1000L * expires;
        }
      }

      AccessToken accessToken = new AccessToken();
      accessToken.accessToken = extendedAccessToken;
      accessToken.expires = expires;
      accessToken.tokenType = tokenType;
      return accessToken;
    }

    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }

    /**
     * The access token's value.
     * 
     * @return The access token's value.
     */
    public String getAccessToken() {
      return accessToken;
    }

    /**
     * The date on which the access token expires.
     * 
     * @return The date on which the access token expires.
     */
    public Date getExpires() {
      return expires == null ? null : new Date(expires);
    }

    /**
     * The token type of this access token provided by Facebook
     * 
     * @return the access token type
     */
    public String getTokenType() {
      return tokenType;
    }

    @JsonMapper.JsonMappingCompleted
    void convertExpires() {
      if (rawExpires != null) {
        expires = new Date().getTime() + 1000L * rawExpires;
      }
    }

  }

  /**
   * <p>
   * Represents the result of a {@link FacebookClient#debugToken(String)} inquiry.
   * </p>
   * 
   * FIXME does this class belong here?
   * 
   * <p>
   * See <a href="https://developers.facebook.com/docs/howtos/login/debugging-access-tokens/">Debug access tokens</a>
   * 
   * @author Broc Seib
   */
  public static class DebugTokenInfo implements Serializable {
    @Facebook("app_id")
    private String appId;

    @Facebook
    private String application;

    @Facebook("expires_at")
    private Long expiresAt;

    @Facebook("issued_at")
    private Long issuedAt;

    @Facebook("is_valid")
    private Boolean isValid;

    @Facebook("user_id")
    private String userId;

    @Facebook("profile_id")
    private String profileId;

    @Facebook
    private JsonObject metadata;

    @Facebook
    private DebugTokenError error;

    @Facebook
    private List<String> scopes = new ArrayList<String>();

    // FIXME let's read 'metadata' if it exist. It's a nested structure...

    /**
     * The application id.
     * 
     * @return The id of the application.
     */
    public String getAppId() {
      return appId;
    }

    /**
     * The application name.
     * 
     * @return The name of the application.
     */
    public String getApplication() {
      return application;
    }

    /**
     * The date on which the access token expires.
     * 
     * @return The date on which the access token expires.
     */
    public Date getExpiresAt() {
      // note that the expire timestamp is in *seconds*, not milliseconds
      return expiresAt == null ? null : new Date(expiresAt * 1000L);
    }

    /**
     * The date on which the access token was issued.
     * 
     * @return The date on which the access token was issued.
     */
    public Date getIssuedAt() {
      // note that the issue timestamp is in *seconds*, not milliseconds
      return issuedAt == null ? null : new Date(issuedAt * 1000L);
    }

    /**
     * Whether or not the token is valid.
     * 
     * @return Whether or not the token is valid.
     */
    public Boolean isValid() {
      return isValid;
    }

    /**
     * The user id.
     * 
     * @return The user id.
     */
    public String getUserId() {
      return userId;
    }

    /**
     * List of scopes the access token 'contains'
     * 
     * @return list of scopes
     */
    public List<String> getScopes() {
      return unmodifiableList(scopes);
    }

    /**
     * General metadata associated with the access token. Can contain data like 'sso', 'auth_type', 'auth_nonce'
     * 
     * @return General metadata associated with the access token
     */
    public JsonObject getMetaData() {
      return metadata;
    }

    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }
  }

  public static class DebugTokenError implements Serializable {

    @Facebook
    private Integer code;

    @Facebook
    private String message;

    @Facebook
    private Integer subcode;

    /**
     * The error code for the error.
     * 
     * @return The error code for the error.
     */
    public Integer getCode() {
      return code;
    }

    /**
     * The error message for the error.
     * 
     * @return The error message for the error.
     */
    public String getMessage() {
      return message;
    }

    /**
     * The error subcode for the error.
     * 
     * @return The error subcode for the error.
     */
    public Integer getSubcode() {
      return subcode;
    }

    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }
  }
}