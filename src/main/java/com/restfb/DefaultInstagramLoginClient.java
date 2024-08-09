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

import com.restfb.exception.FacebookResponseContentException;
import com.restfb.scope.ScopeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.restfb.util.ObjectUtil.verifyParameterPresence;

/**
 * InstagramLoginClient is the default implementation of the
 * <a href="https://developers.facebook.com/docs/instagram-platform/instagram-api-with-instagram-login/business-login">
 * Instagram API with Instagram Login</a>.
 * <p>
 * This client is used to use a instagram Login to get a access token, extended access token and so on for the Instagram
 * API.
 * <p>
 * Don't use the client if you are planning to use the Facebbok Login to work with the Instagram API.
 */
public class DefaultInstagramLoginClient extends DefaultFacebookClient {

  public DefaultInstagramLoginClient(Version version) {
    super(version);
  }

  public DefaultInstagramLoginClient(String accessToken, Version apiVersion) {
    super(accessToken, apiVersion);
  }

  public DefaultInstagramLoginClient(String accessToken, String appSecret, Version apiVersion) {
    super(accessToken, appSecret, apiVersion);
  }

  public DefaultInstagramLoginClient(String accessToken, WebRequestor webRequestor, JsonMapper jsonMapper,
      Version apiVersion) {
    super(accessToken, webRequestor, jsonMapper, apiVersion);
  }

  public DefaultInstagramLoginClient(String accessToken, String appSecret, WebRequestor webRequestor,
      JsonMapper jsonMapper, Version apiVersion) {
    super(accessToken, appSecret, webRequestor, jsonMapper, apiVersion);
  }

  @Override
  public String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope, String state,
      Parameter... parameters) {
    List<Parameter> parameterList = new ArrayList<>();
    Collections.addAll(parameterList, parameters);
    parameterList.add(Parameter.with("response_type", CODE));
    return getGenericLoginDialogUrl(appId, redirectUri, scope,
      () -> getFacebookEndpointUrls().getInstagramOAuthEndpoint() + "/oauth/authorize", state, parameterList);
  }

  @Override
  public String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope, Parameter... parameters) {
    return this.getLoginDialogUrl(appId, redirectUri, scope, null, parameters);
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
  public AccessToken obtainExtendedAccessToken(String appId, String appSecret) {
    verifyParameterPresence(APP_SECRET, appSecret);
    verifyParameterPresence("accessToken", accessToken);

    String response = makeRequest("access_token", false, false, null, //
      Parameter.with(PARAM_CLIENT_SECRET, appSecret), //
      Parameter.with(GRANT_TYPE, "ig_exchange_token"), //
      Parameter.withFields("access_token,expires_in,token_type"));
    try {
      return getAccessTokenFromResponse(response);
    } catch (Exception t) {
      throw new FacebookResponseContentException(CANNOT_EXTRACT_ACCESS_TOKEN_MESSAGE, t);
    }
  }

  @Override
  public FacebookClient createClientWithAccessToken(String accessToken) {
    return new DefaultInstagramLoginClient(accessToken, this.appSecret, getWebRequestor(), getJsonMapper(),
      this.apiVersion);
  }

  @Override
  public AccessToken obtainRefreshedExtendedAccessToken() {
    String response = makeRequest("refresh_access_token", false, false, null, //
      Parameter.with(GRANT_TYPE, "ig_refresh_token"), //
      Parameter.withFields("access_token,expires_in,token_type"));
    try {
      return getAccessTokenFromResponse(response);
    } catch (Exception t) {
      throw new FacebookResponseContentException(CANNOT_EXTRACT_ACCESS_TOKEN_MESSAGE, t);
    }
  }

  @Override
  protected String createBaseUrlForEndpoint(String apiCall, boolean hasAttachment, boolean hasReel) {
    if (apiCall.startsWith(PATH_OAUTH_ACCESS_TOKEN)) {
      return getInstagramApiEndpointUrl();
    }
    return getInstagramGraphEndpointUrl();
  }

  private String getInstagramApiEndpointUrl() {
    return getFacebookEndpointUrls().getInstagramApiEndpoint();
  }

  private String getInstagramGraphEndpointUrl() {
    if (apiVersion.isUrlElementRequired()) {
      return getFacebookEndpointUrls().getInstagramEndpoint() + '/' + apiVersion.getUrlElement();
    } else {
      return getFacebookEndpointUrls().getInstagramEndpoint();
    }
  }
}
