/*
 * Copyright (c) 2010-2024 Mark Allen, Norbert Bartels.
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

import static com.restfb.util.ObjectUtil.verifyParameterPresence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.restfb.exception.FacebookResponseContentException;
import com.restfb.scope.ScopeBuilder;

/**
 * A class representing a client for interacting with Facebook's Threads API. This class extends the
 * DefaultFacebookClient and provides additional methods for accessing Threads-specific functionalities.
 */
public class DefaultThreadsClient extends DefaultFacebookClient {

  public DefaultThreadsClient(Version version) {
    super(version);
  }

  public DefaultThreadsClient(String accessToken, Version apiVersion) {
    super(accessToken, apiVersion);
  }

  public DefaultThreadsClient(String accessToken, String appSecret, Version apiVersion) {
    super(accessToken, appSecret, apiVersion);
  }

  public DefaultThreadsClient(String accessToken, WebRequestor webRequestor, JsonMapper jsonMapper,
      Version apiVersion) {
    super(accessToken, webRequestor, jsonMapper, apiVersion);
  }

  public DefaultThreadsClient(String accessToken, String appSecret, WebRequestor webRequestor, JsonMapper jsonMapper,
      Version apiVersion) {
    super(accessToken, appSecret, webRequestor, jsonMapper, apiVersion);
  }

  @Override
  public String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope, String state,
      Parameter... parameters) {
    List<Parameter> parameterList = new ArrayList<>();
    Collections.addAll(parameterList, parameters);
    parameterList.add(Parameter.with("response_type", CODE));
    return getGenericLoginDialogUrl(appId, redirectUri, scope,
      () -> getFacebookEndpointUrls().getThreadsBaseEndpoint() + "/oauth/authorize", state, parameterList);
  }

  @Override
  public String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope, Parameter... parameters) {
    return getLoginDialogUrl(appId, redirectUri, scope, null, parameters);
  }

  @Override
  public AccessToken obtainUserAccessToken(String clientId, String clientSecret, String redirectUri, String code) {
    verifyParameterPresence(CLIENT_ID, clientId);
    verifyParameterPresence(PARAM_CLIENT_SECRET, clientSecret);
    verifyParameterPresence(CODE, code);
    verifyParameterPresence(REDIRECT_URI, redirectUri);

    return publish(PATH_OAUTH_ACCESS_TOKEN, AccessToken.class, //
      Parameter.with(CLIENT_ID, clientId), //
      Parameter.with(PARAM_CLIENT_SECRET, clientSecret), //
      Parameter.with(CODE, code), //
      Parameter.with(GRANT_TYPE, "authorization_code"), //
      Parameter.with(REDIRECT_URI, redirectUri));
  }

  @Override
  public AccessToken obtainExtendedAccessToken(String appId, String appSecret, String accessToken) {
    throw new UnsupportedOperationException("Not supported, use the other obtainExtendedAccessToken instead");
  }

  @Override
  public AccessToken obtainExtendedAccessToken(String appId, String appSecret) {
    verifyParameterPresence(APP_SECRET, appSecret);
    verifyParameterPresence("accessToken", accessToken);

    String response = makeRequest("access_token", false, false, null, //
      Parameter.with(PARAM_CLIENT_SECRET, appSecret), //
      Parameter.with(GRANT_TYPE, "th_exchange_token"), //
      Parameter.withFields("access_token,expires_in,token_type"));

    try {
      return getAccessTokenFromResponse(response);
    } catch (Exception t) {
      throw new FacebookResponseContentException(CANNOT_EXTRACT_ACCESS_TOKEN_MESSAGE, t);
    }
  }

  /**
   * Obtain a refreshed Threads extended access token.
   *
   * <p>
   * This method is used to refresh an existing Threads extended access token. Extended access tokens expire after a
   * certain period of time, and this method allows you to obtain a new one using the refresh token provided with the
   * original extended access token.
   *
   * @return A new {@link AccessToken} object containing the refreshed access token, expiration time, and token type.
   * @throws FacebookResponseContentException
   *           If the response from the Facebook API cannot be parsed or if the access token cannot be extracted from
   *           the response.
   */
  @Override
  public AccessToken obtainRefreshedExtendedAccessToken() {
    String response = makeRequest("refresh_access_token", false, false, null, //
      Parameter.with(GRANT_TYPE, "th_refresh_token"), //
      Parameter.withFields("access_token,expires_in,token_type"));
    try {
      return getAccessTokenFromResponse(response);
    } catch (Exception t) {
      throw new FacebookResponseContentException(CANNOT_EXTRACT_ACCESS_TOKEN_MESSAGE, t);
    }
  }

  @Override
  public FacebookClient createClientWithAccessToken(String accessToken) {
    return new DefaultThreadsClient(accessToken, this.appSecret, getWebRequestor(), getJsonMapper(), this.apiVersion);
  }

  @Override
  protected String createBaseUrlForEndpoint(String apiCall, boolean hasAttachment, boolean hasReel) {
    return getThreadsGraphEndpointUrl();
  }

  private String getThreadsGraphEndpointUrl() {
    if (apiVersion.isUrlElementRequired()) {
      return getFacebookEndpointUrls().getThreadsApiEndpoint() + '/' + apiVersion.getUrlElement();
    } else {
      return getFacebookEndpointUrls().getThreadsApiEndpoint();
    }
  }
}
