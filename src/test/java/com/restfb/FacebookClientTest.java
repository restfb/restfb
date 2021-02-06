/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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

import static com.restfb.testutils.RestfbAssertions.assertThat;
import static com.restfb.util.StringUtils.fromInputStream;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.restfb.WebRequestor.Response;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.exception.FacebookResponseContentException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenCodeExpiredException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenDeclinedException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenPendingException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenSlowdownException;
import com.restfb.json.JsonArray;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.Message;
import com.restfb.types.send.SendResponse;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class FacebookClientTest {

  /**
   * Is the <code>appsecret_proof</code> hash function correct
   */
  @Test
  public void testMakeAppSecretProof() {
    DefaultFacebookClient facebookClient = new DefaultFacebookClient(Version.LATEST);
    String test = facebookClient.obtainAppSecretProof("test", "test");
    // obtained from running hash_hmac("sha256","test","test");
    String php_result = "88cd2108b5347d973cf39cdf9053d7dd42704876d8c9a9bd8e2d168259d3ddf7";
    assertThat(test).isEqualTo(php_result);
    // obtained from running hash_hmac("sha256","helloWorld",'PRIE7$oG2uS-Yf17kEnUEpi5hvW/#AFo');
    String php_result2 = "cb064987988fcd658470d6a24f1c68f6d7982c80ab9efb08cb8c84ef88fd03e1";
    DefaultFacebookClient facebookClient2 = new DefaultFacebookClient(Version.LATEST);
    String test2 = facebookClient2.obtainAppSecretProof("helloWorld", "PRIE7$oG2uS-Yf17kEnUEpi5hvW/#AFo");
    assertThat(test2).isEqualTo(php_result2);
  }

  /**
   * Do we correctly handle the case where FB returns an OAuthException with an error code?
   */
  @Test
  public void oauthExceptionWithErrorCode() {
    FacebookClient facebookClient = facebookClientWithResponse(new Response(403,
      "{\"error\":{\"message\":\"(#210) User not visible\",\"type\":\"OAuthException\",\"code\":210}}"));

    try {
      facebookClient.fetchObject("me", User.class);
      failBecauseExceptionWasNotThrown(FacebookOAuthException.class);
    } catch (FacebookOAuthException e) {
      assertThat(e.getErrorMessage()).isEqualTo("(#210) User not visible");
      assertThat(e.getErrorType()).isEqualTo("OAuthException");
      assertThat(e.getErrorCode()).isEqualTo(210);
    }
  }

  /**
   * Do we correctly handle the case where FB returns an OAuthException with an error code and subcode?
   */
  @Test
  public void oauthExceptionWithErrorSubcode() {
    FacebookClient facebookClient = facebookClientWithResponse(new Response(403,
      "{\"error\":{\"message\":\"App Not Installed\",\"type\":\"OAuthException\",\"code\":190,\"error_subcode\":458}}"));

    try {
      facebookClient.fetchObject("me", User.class);
      failBecauseExceptionWasNotThrown(FacebookOAuthException.class);
    } catch (FacebookOAuthException e) {
      assertThat(e.getErrorMessage()).isEqualTo("App Not Installed");
      assertThat(e.getErrorType()).isEqualTo("OAuthException");
      assertThat(e.getErrorCode()).isEqualTo(190);
      assertThat(e.getErrorSubcode()).isEqualTo(458);
    }
  }

  /**
   * Do we correctly handle the case where FB returns an OAuthException without an error code?
   */
  @Test
  public void oauthExceptionWithoutErrorCode() {
    FacebookClient facebookClient = facebookClientWithResponse(
      new Response(403, "{\"error\":{\"message\":\"(#210) User not visible\",\"type\":\"OAuthException\"}}"));

    try {
      facebookClient.fetchObject("me", User.class);
      failBecauseExceptionWasNotThrown(FacebookOAuthException.class);
    } catch (FacebookOAuthException e) {
      assertThat(e.getErrorMessage()).isEqualTo("(#210) User not visible");
      assertThat(e.getErrorType()).isEqualTo("OAuthException");
      assertThat(e.getErrorCode()).isNull();
    }
  }

  @Test
  public void obtainExtendedAccessTokenV23() {
    FacebookClient fbc = facebookClientWithResponse(
      new Response(200, "{\"access_token\": \"accesstoken\", \"token_type\":\"tokentype\", \"expires_in\":132363}"));
    FacebookClient.AccessToken at = fbc.obtainExtendedAccessToken("a", "b", "c");
    assertThat(at.getAccessToken()).isEqualTo("accesstoken");
    assertThat(at.getTokenType()).isEqualTo("tokentype");
    assertThat(at.getExpires()).isAfter(new Date());
  }

  @Test
  public void obtainExtendedAccessTokenV23WithoutExpiresIn() {
    FacebookClient fbc = facebookClientWithResponse(
      new Response(200, "{\"access_token\": \"accesstoken\", \"token_type\":\"tokentype\"}"));
    FacebookClient.AccessToken at = fbc.obtainExtendedAccessToken("a", "b", "c");
    assertThat(at.getAccessToken()).isEqualTo("accesstoken");
    assertThat(at.getTokenType()).isEqualTo("tokentype");
    assertThat(at.getExpires()).isNull();
  }

  @Test
  public void obtainExtendedAccessTokenV22() {
    FacebookClient fbc =
        facebookClientWithResponse(new Response(200, "access_token=accesstoken&token_type=tokentype&expires=132363"));
    FacebookClient.AccessToken at = fbc.obtainExtendedAccessToken("a", "b", "c");
    assertThat(at.getAccessToken()).isEqualTo("accesstoken");
    assertThat(at.getTokenType()).isEqualTo("tokentype");
    assertThat(at.getExpires()).isAfter(new Date());
  }

  @Test
  public void obtainExtendedAccessTokenV22WithoutTokenType() {
    FacebookClient fbc = facebookClientWithResponse(new Response(200, "access_token=accesstoken&expires=132363"));
    FacebookClient.AccessToken at = fbc.obtainExtendedAccessToken("a", "b", "c");
    assertThat(at.getAccessToken()).isEqualTo("accesstoken");
    assertThat(at.getTokenType()).isNull();
    assertThat(at.getExpires()).isAfter(new Date());
  }

  @Test
  public void fetchDeviceCodeLatest() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_3_0);
    try {
      fbc.fetchDeviceCode(new ScopeBuilder());
    } catch (FacebookJsonMappingException je) {

    }
    assertThat(requestor).isSavedUrlEqualTo("https://graph.facebook.com/v3.0/device/login");
    assertThat(requestor)
      .isParametersEqualTo("type=device_code&scope=public_profile&access_token=accesstoken&format=json");
  }

  @Test
  public void sendTextMessage() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_3_0);
    try {
      Message simpleTextMessage = new Message("That's funny \uD83D\uDE03");
      IdMessageRecipient recipient = new IdMessageRecipient("968155906638513");

      SendResponse response = fbc.publish("me/messages", SendResponse.class, Parameter.with("recipient", recipient),
        Parameter.with("message", simpleTextMessage));
    } catch (FacebookJsonMappingException je) {

    }
    assertThat(requestor).isSavedUrlEqualTo("https://graph.facebook.com/v3.0/me/messages");
    assertThat(requestor).isParametersEqualTo(
      "recipient=%7B%22id%22%3A%22968155906638513%22%7D&message=%7B%22text%22%3A%22That%27s+funny+%5Cud83d%5Cude03%22%7D&access_token=accesstoken&format=json");
  }

  @Test
  public void checkfetchObjects() throws URISyntaxException {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_3_0);

    List<String> idList = new ArrayList<>();
    idList.add("123456789  ");
    idList.add("abcdefghijkl");
    idList.add("m_mid:35723r72$bfehZFDEBDET");

    fbc.fetchObjects(idList, String.class);

    assertThat(requestor.getSavedUrl()).contains("123456789", "abcdefghijkl", "m_mid%3A35723r72%24bfehZFDEBDET");
    assertThat(requestor.getSavedUrl()).contains("ids=%5B", "%5D&");
    URI savedURL = new URI(requestor.getSavedUrl());
    for (String id : idList) {
      assertThat(savedURL.getQuery()).contains('"' + id.trim() + '"');
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkfetchObjects_withEmptyId() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_3_0);

    List<String> idList = new ArrayList<>();
    idList.add("  ");
    idList.add("abcdefghijkl");
    idList.add("m_mid:35723r72$bfehZFDEBDET");

    fbc.fetchObjects(idList, String.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkfetchObjects_idsAsParameter() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_3_0);

    List<String> idList = new ArrayList<>();
    idList.add("abcdefghijkl");
    idList.add("m_mid:35723r72$bfehZFDEBDET");

    fbc.fetchObjects(idList, String.class, Parameter.with("ids", "something"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkfetchObjects_emptyList() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_3_0);

    fbc.fetchObjects(Collections.EMPTY_LIST, String.class);
  }

  @Test
  public void fetchDeviceCodeV26() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_3_0);
    try {
      fbc.fetchDeviceCode(new ScopeBuilder());
    } catch (FacebookJsonMappingException je) {

    }
    assertThat(requestor).isSavedUrlEqualTo("https://graph.facebook.com/v3.0/device/login");
    assertThat(requestor)
      .isParametersEqualTo("type=device_code&scope=public_profile&access_token=accesstoken&format=json");
  }

  @Test
  public void obtainDeviceAccessTokenCodeV26() {
    FakeWebRequestor requestor = createFbClientAndObtainAccessToken(Version.VERSION_3_0);
    assertThat(requestor).isSavedUrlEqualTo("https://graph.facebook.com/v3.0/device/login_status");
    assertThat(requestor)
      .isParametersEqualTo("type=device_token&code=DevCode1234&access_token=accesstoken&format=json");
  }

  private FakeWebRequestor createFbClientAndObtainAccessToken(Version version) {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc = new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), version);
    try {
      fbc.obtainDeviceAccessToken("DevCode1234");
    } catch (IllegalArgumentException je) {
      // exception can be ignored, the url is important
    } catch (FacebookDeviceTokenCodeExpiredException e) {
      // never reached
    } catch (FacebookDeviceTokenPendingException e) {
      // never reached
    } catch (FacebookDeviceTokenSlowdownException e) {
      // never reached
    } catch (FacebookDeviceTokenDeclinedException e) {
      // never reached
    }
    return requestor;
  }

  @Test
  public void testDebugToken() throws IOException {
    final String returnJson = fromInputStream(getClass().getResourceAsStream("/json/data-debug-token-info.json"));

    FakeWebRequestor requestor = new FakeWebRequestor(new Response(200, returnJson));

    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_3_0);
    FacebookClient.DebugTokenInfo debugTokenInfo = fbc.debugToken("myToken");
    assertThat(requestor).isSavedUrlEqualTo(
      "https://graph.facebook.com/v3.0/debug_token?input_token=myToken&access_token=accesstoken&format=json");
    assertThat(debugTokenInfo).isNotNull();
  }

  @Test(expected = FacebookResponseContentException.class)
  public void testDebugTokenException() throws IOException {
    FakeWebRequestor requestor = new FakeWebRequestor(new Response(200, null));
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_3_0);
    fbc.debugToken("myToken");
  }

  @Test
  public void deleteObjectReturnsJson() {
    FacebookClient facebookClient = facebookClientWithResponse(new Response(200, "{\"success\":true}"));
    assertThat(facebookClient.deleteObject("12345")).isTrue();
  }

  @Test
  public void deleteObjectReturnsJsonGreetingMessengerPlatform() {
    FacebookClient facebookClient =
        facebookClientWithResponse(new Response(200, "{\"result\":\"Successfully deleted greeting\"}"));
    assertThat(facebookClient.deleteObject("12345")).isTrue();
  }

  @Test
  public void deleteObjectReturnsText() {
    FacebookClient facebookClient = facebookClientWithResponse(new Response(200, "true"));
    assertThat(facebookClient.deleteObject("12345")).isTrue();
  }

  @Test
  public void checkLogoutUrl() {
    FacebookClient client = new DefaultFacebookClient("123456", Version.VERSION_3_0);
    String logoutUrl = client.getLogoutUrl(null);
    assertThat(logoutUrl).isEqualTo("https://www.facebook.com/logout.php?access_token=123456");
  }

  @Test
  public void checkLogoutUrlWithNext() {
    FacebookClient client = new DefaultFacebookClient("123456", Version.VERSION_3_0);
    String logoutUrl = client.getLogoutUrl("http://www.example.com");
    assertThat(logoutUrl)
      .isEqualTo("https://www.facebook.com/logout.php?next=http%3A%2F%2Fwww.example.com&access_token=123456");
  }

  @Test
  public void checkLoginDialogURL() {
    FacebookClient client = new DefaultFacebookClient(Version.VERSION_3_0);
    String loginDialogUrlString = client.getLoginDialogUrl("123456", "http://www.example.com", new ScopeBuilder());
    assertThat(loginDialogUrlString).isEqualTo(
      "https://www.facebook.com/dialog/oauth?client_id=123456&redirect_uri=http%3A%2F%2Fwww.example.com&scope=public_profile");
  }

  @Test
  public void checkLoginDialogURLAdditionalParameters() {
    FacebookClient client = new DefaultFacebookClient(Version.VERSION_3_0);
    String loginDialogUrlString = client.getLoginDialogUrl("123456", "http://www.example.com", new ScopeBuilder(),
      Parameter.with("auth_type", "reauthenticate"));
    assertThat(loginDialogUrlString).isEqualTo(
      "https://www.facebook.com/dialog/oauth?client_id=123456&redirect_uri=http%3A%2F%2Fwww.example.com&scope=public_profile&auth_type=reauthenticate");
  }

  @Test
  public void checkUrlWithExpiresIn() {
    String queryString = "access_token=<access-token>&expires_in=5184000";
    FacebookClient.AccessToken token = FacebookClient.AccessToken.fromQueryString(queryString);
    assertThat(token.getExpires()).isAfter(new Date(1468096359099L));
  }

  @Test
  public void checkUrlWithExpires() {
    String queryString = "access_token=<access-token>&expires=5184000";
    FacebookClient.AccessToken token = FacebookClient.AccessToken.fromQueryString(queryString);
    assertThat(token.getExpires()).isAfter(new Date(1468096359099L));
  }

  @Test
  public void createJsonArray() {
    JsonArray array = new JsonArray();
    array.add("123");
    array.add("456");

    assertThat(array.toString()).contains("\"123\"").contains("\"456\"");
  }

  /**
   * Simple way to create a {@code FacebookClient} whose web requests always return the provided synthetic
   * {@code response}.
   * 
   * @param response
   *          The synthetic response to return.
   * @return A {@code FacebookClient} for testing.
   */
  protected FacebookClient facebookClientWithResponse(final Response response) {
    return new DefaultFacebookClient(null, new FakeWebRequestor(response), new DefaultJsonMapper(), Version.LATEST);
  }
}