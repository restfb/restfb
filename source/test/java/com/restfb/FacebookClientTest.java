/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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

import static com.restfb.util.StringUtils.fromInputStream;
import static org.junit.Assert.*;

import com.restfb.WebRequestor.Response;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.exception.FacebookResponseContentException;
import com.restfb.exception.ResponseErrorJsonParsingException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenCodeExpiredException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenDeclinedException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenPendingException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenSlowdownException;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.Message;
import com.restfb.types.send.SendResponse;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class FacebookClientTest {

  /**
   * Is the <code>appsecret_proof</code> hash function correct
   */
  @Test
  public void testMakeAppSecretProof() {
    System.out.println("Testing make App Secret Proof");
    DefaultFacebookClient facebookClient = new DefaultFacebookClient(Version.LATEST);
    String test = facebookClient.obtainAppSecretProof("test", "test");
    // obtained from running hash_hmac("sha256","test","test");
    String php_result = "88cd2108b5347d973cf39cdf9053d7dd42704876d8c9a9bd8e2d168259d3ddf7";
    assertEquals(php_result, test);
    // obtained from running hash_hmac("sha256","helloWorld",'PRIE7$oG2uS-Yf17kEnUEpi5hvW/#AFo');
    String php_result2 = "cb064987988fcd658470d6a24f1c68f6d7982c80ab9efb08cb8c84ef88fd03e1";
    DefaultFacebookClient facebookClient2 = new DefaultFacebookClient(Version.LATEST);
    String test2 = facebookClient2.obtainAppSecretProof("helloWorld", "PRIE7$oG2uS-Yf17kEnUEpi5hvW/#AFo");
    assertEquals(php_result2, test2);
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
    } catch (FacebookOAuthException e) {
      assertEquals("(#210) User not visible", e.getErrorMessage());
      assertEquals("OAuthException", e.getErrorType());
      assertEquals(Integer.valueOf(210), e.getErrorCode());
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
    } catch (FacebookOAuthException e) {
      assertEquals("App Not Installed", e.getErrorMessage());
      assertEquals("OAuthException", e.getErrorType());
      assertEquals(Integer.valueOf(190), e.getErrorCode());
      assertEquals(Integer.valueOf(458), e.getErrorSubcode());
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
    } catch (FacebookOAuthException e) {
      assertEquals("(#210) User not visible", e.getErrorMessage());
      assertEquals("OAuthException", e.getErrorType());
      assertEquals(null, e.getErrorCode());
    }
  }

  @Test
  public void obtainExtendedAccessTokenV23() {
    FacebookClient fbc = facebookClientWithResponse(
      new Response(200, "{\"access_token\": \"accesstoken\", \"token_type\":\"tokentype\", \"expires_in\":132363}"));
    FacebookClient.AccessToken at = fbc.obtainExtendedAccessToken("a", "b", "c");
    assertEquals("accesstoken", at.getAccessToken());
    assertEquals("tokentype", at.getTokenType());
    assertTrue(at.getExpires().getTime() > new Date().getTime());
  }

  @Test
  public void obtainExtendedAccessTokenV23WithoutExpiresIn() {
    FacebookClient fbc = facebookClientWithResponse(
      new Response(200, "{\"access_token\": \"accesstoken\", \"token_type\":\"tokentype\"}"));
    FacebookClient.AccessToken at = fbc.obtainExtendedAccessToken("a", "b", "c");
    assertEquals("accesstoken", at.getAccessToken());
    assertEquals("tokentype", at.getTokenType());
    assertNull(at.getExpires());
  }

  @Test
  public void obtainExtendedAccessTokenV22() {
    FacebookClient fbc =
        facebookClientWithResponse(new Response(200, "access_token=accesstoken&token_type=tokentype&expires=132363"));
    FacebookClient.AccessToken at = fbc.obtainExtendedAccessToken("a", "b", "c");
    assertEquals("accesstoken", at.getAccessToken());
    assertEquals("tokentype", at.getTokenType());
    assertTrue(at.getExpires().getTime() > new Date().getTime());
  }

  @Test
  public void obtainExtendedAccessTokenV22WithoutTokenType() {
    FacebookClient fbc = facebookClientWithResponse(new Response(200, "access_token=accesstoken&expires=132363"));
    FacebookClient.AccessToken at = fbc.obtainExtendedAccessToken("a", "b", "c");
    assertEquals("accesstoken", at.getAccessToken());
    assertNull(at.getTokenType());
    assertTrue(at.getExpires().getTime() > new Date().getTime());
  }

  @Test
  public void fetchDeviceCodeLatest() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_2_6);
    try {
      fbc.fetchDeviceCode("123456283", new ScopeBuilder());
    } catch (FacebookJsonMappingException je) {

    }
    assertEquals("https://graph.facebook.com/v2.6/device/login", requestor.getSavedUrl());
    assertEquals("type=device_code&client_id=123456283&scope=public_profile&access_token=accesstoken&format=json",
      requestor.getParameters());
  }

  @Test
  public void sendTextMessage() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
            new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_2_6);
    try {
      Message simpleTextMessage = new Message("That's funny \uD83D\uDE03");
      IdMessageRecipient recipient = new IdMessageRecipient("968155906638513");

      SendResponse response = fbc.publish("me/messages", SendResponse.class,
              Parameter.with("recipient", recipient), Parameter.with("message", simpleTextMessage));
    } catch (FacebookJsonMappingException je) {

    }
    assertEquals("https://graph.facebook.com/v2.6/me/messages", requestor.getSavedUrl());
    assertEquals("recipient=%7B%22id%22%3A%22968155906638513%22%7D&message=%7B%22text%22%3A%22That%27s+funny+%5Cud83d%5Cude03%22%7D&access_token=accesstoken&format=json",
            requestor.getParameters());
  }

  @Test
  public void obtainDeviceAccessTokenCodeLatest() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_2_6);
    try {
      fbc.obtainDeviceAccessToken("12345678", "DevCode1234");
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
    assertEquals("https://graph.facebook.com/v2.6/device/login_status", requestor.getSavedUrl());
    assertEquals("type=device_token&client_id=12345678&code=DevCode1234&access_token=accesstoken&format=json",
      requestor.getParameters());
  }

  @Test
  public void fetchDeviceCodeV25() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_2_5);
    try {
      fbc.fetchDeviceCode("123456283", new ScopeBuilder());
    } catch (FacebookJsonMappingException je) {

    }
    assertEquals("https://graph.facebook.com/v2.5/oauth/device", requestor.getSavedUrl());
    assertEquals("type=device_code&client_id=123456283&scope=public_profile&access_token=accesstoken&format=json",
      requestor.getParameters());
  }

  @Test
  public void fetchDeviceCodeV26() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
            new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_2_6);
    try {
      fbc.fetchDeviceCode("123456283", new ScopeBuilder());
    } catch (FacebookJsonMappingException je) {

    }
    assertEquals("https://graph.facebook.com/v2.6/device/login", requestor.getSavedUrl());
    assertEquals("type=device_code&client_id=123456283&scope=public_profile&access_token=accesstoken&format=json",
            requestor.getParameters());
  }

  @Test
  public void obtainDeviceAccessTokenCodeV25() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_2_5);
    try {
      fbc.obtainDeviceAccessToken("12345678", "DevCode1234");
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
    assertEquals("https://graph.facebook.com/v2.5/oauth/device", requestor.getSavedUrl());
    assertEquals("type=device_token&client_id=12345678&code=DevCode1234&access_token=accesstoken&format=json",
      requestor.getParameters());
  }

  @Test
  public void obtainDeviceAccessTokenCodeV26() {
    FakeWebRequestor requestor = new FakeWebRequestor();
    FacebookClient fbc =
            new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_2_6);
    try {
      fbc.obtainDeviceAccessToken("12345678", "DevCode1234");
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
    assertEquals("https://graph.facebook.com/v2.6/device/login_status", requestor.getSavedUrl());
    assertEquals("type=device_token&client_id=12345678&code=DevCode1234&access_token=accesstoken&format=json",
            requestor.getParameters());
  }

  @Test
  public void testDebugToken() throws IOException {
    final String returnJson = fromInputStream(getClass().getResourceAsStream("/json/data-debug-token-info.json"));

    FakeWebRequestor requestor = new FakeWebRequestor() {
      @Override
      public Response executeGet(String url) throws IOException {
        super.executeGet(url);
        return new Response(200, returnJson);
      }
    };

    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_2_6);
    FacebookClient.DebugTokenInfo debugTokenInfo = fbc.debugToken("myToken");
    assertEquals("https://graph.facebook.com/v2.6/debug_token?input_token=myToken&access_token=accesstoken&format=json",
      requestor.getSavedUrl());
    assertNotNull(debugTokenInfo);
  }

  @Test(expected = FacebookResponseContentException.class)
  public void testDebugTokenException() throws IOException {
    FakeWebRequestor requestor = new FakeWebRequestor() {
      @Override
      public Response executeGet(String url) throws IOException {
        super.executeGet(url);
        return new Response(200, null);
      }
    };

    FacebookClient fbc =
        new DefaultFacebookClient("accesstoken", requestor, new DefaultJsonMapper(), Version.VERSION_2_6);
    fbc.debugToken("myToken");
  }

  @Test
  public void deleteObjectReturnsJson() {
    FacebookClient facebookClient = facebookClientWithResponse(new Response(200, "{\"success\":true}"));
    assertTrue(facebookClient.deleteObject("12345"));
  }

  @Test
  public void deleteObjectReturnsJsonGreetingMessengerPlatform() {
    FacebookClient facebookClient = facebookClientWithResponse(new Response(200, "{\"result\":\"Successfully deleted greeting\"}"));
    assertTrue(facebookClient.deleteObject("12345"));
  }

  @Test
  public void deleteObjectReturnsText() {
    FacebookClient facebookClient = facebookClientWithResponse(new Response(200, "true"));
    assertTrue(facebookClient.deleteObject("12345"));
  }

  @Test
  public void checkLogoutUrl() {
    FacebookClient client = new DefaultFacebookClient("123456", Version.VERSION_2_2);
    String logoutUrl = client.getLogoutUrl(null);
    assertEquals("https://www.facebook.com/logout.php?access_token=123456", logoutUrl);
  }

  @Test
  public void checkLogoutUrlWithNext() {
    FacebookClient client = new DefaultFacebookClient("123456", Version.VERSION_2_2);
    String logoutUrl = client.getLogoutUrl("http://www.example.com");
    assertEquals("https://www.facebook.com/logout.php?next=http%3A%2F%2Fwww.example.com&access_token=123456",
      logoutUrl);
  }

  @Test
  public void checkLoginDialogURL() {
    FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_0);
    String loginDialogUrlString = client.getLoginDialogUrl("123456", "http://www.example.com", new ScopeBuilder());
    assertEquals(
      "https://www.facebook.com/dialog/oauth?client_id=123456&redirect_uri=http%3A%2F%2Fwww.example.com&scope=public_profile",
      loginDialogUrlString);
  }

  @Test
  public void checkLoginDialogURLAdditionalParameters() {
    FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_0);
    String loginDialogUrlString = client.getLoginDialogUrl("123456", "http://www.example.com", new ScopeBuilder(), Parameter.with("auth_type", "reauthenticate"));
    assertEquals(
            "https://www.facebook.com/dialog/oauth?client_id=123456&redirect_uri=http%3A%2F%2Fwww.example.com&scope=public_profile&auth_type=reauthenticate",
            loginDialogUrlString);
  }

  @Test
  public void checkSkipErrorResponseParsing_NoJsonObject() {
    DefaultFacebookClient dfc = new DefaultFacebookClient(Version.VERSION_2_0);
    String json = "testString";
    try {
      dfc.skipResponseStatusExceptionParsing(json);
      fail("Exception not thrown");
    } catch (ResponseErrorJsonParsingException e) {
      // every thing fine;
    }
  }

  @Test
  public void checkSkipErrorResponseParsing_JsonObjectWithoutError() {
    DefaultFacebookClient dfc = new DefaultFacebookClient(Version.VERSION_2_0);
    String json = "{ \"testString\": \"example\" }";
    try {
      dfc.skipResponseStatusExceptionParsing(json);
      fail("Exception not thrown");
    } catch (ResponseErrorJsonParsingException e) {
      // every thing fine;
    }
  }

  @Test
  public void checkSkipErrorResponseParsing_JsonObjectWithError() {
    DefaultFacebookClient dfc = new DefaultFacebookClient(Version.VERSION_2_0);
    String json = "{ \"error\": \"exampleError\" }";
    try {
      dfc.skipResponseStatusExceptionParsing(json);
      // every thing fine;
    } catch (ResponseErrorJsonParsingException e) {
      fail("Exception not thrown");
    }
  }

  @Test
  public void checkSilentJsonObjectGeneration_WithValidJson() {
    DefaultFacebookClient dfc = new DefaultFacebookClient(Version.VERSION_2_0);
    String json = "{ \"error\": \"exampleError\" }";
    assertNotNull(dfc.silentlyCreateObjectFromString(json));
  }

  @Test
  public void checkSilentJsonObjectGeneration_WithInvalidJson() {
    DefaultFacebookClient dfc = new DefaultFacebookClient(Version.VERSION_2_0);
    String json = "{ \"error\"}";
    assertNull(dfc.silentlyCreateObjectFromString(json));
  }

  @Test
  public void checkUrlWithExpiresIn() {
    String queryString = "access_token=<access-token>&expires_in=5184000";
    FacebookClient.AccessToken token = FacebookClient.AccessToken.fromQueryString(queryString);
    assertTrue(1468096359099L < token.getExpires().getTime());
  }

  @Test
  public void checkUrlWithExpires() {
    String queryString = "access_token=<access-token>&expires=5184000";
    FacebookClient.AccessToken token = FacebookClient.AccessToken.fromQueryString(queryString);
    assertTrue(1468096359099L < token.getExpires().getTime());
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
    return new DefaultFacebookClient(null, new DefaultWebRequestor() {
      @Override
      public Response executeGet(String url) throws IOException {
        return response;
      }

      @Override
      public Response executePost(String url, String parameters) throws IOException {
        return response;
      }

      @Override
      public Response executePost(String url, String parameters, BinaryAttachment... binaryAttachments)
          throws IOException {
        return response;
      }

      @Override
      public Response executeDelete(String url) throws IOException {
        return response;
      }

    }, new DefaultJsonMapper(), Version.LATEST);
  }
}