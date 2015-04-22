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

import com.restfb.WebRequestor.Response;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;
import java.io.IOException;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class FacebookClientTest {

  /**
   * Is the appsecret_proof hash function correct
   */
  @Test
  public void testMakeAppSecretProof() {
    System.out.println("Testing make App Secret Proof");
    DefaultFacebookClient facebookClient = new DefaultFacebookClient();
    String test = facebookClient.obtainAppSecretProof("test", "test");
    // obtained from running hash_hmac("sha256","test","test");
    String php_result = "88cd2108b5347d973cf39cdf9053d7dd42704876d8c9a9bd8e2d168259d3ddf7";
    assertEquals(php_result, test);
    // obtained from running hash_hmac("sha256","helloWorld",'PRIE7$oG2uS-Yf17kEnUEpi5hvW/#AFo');
    String php_result2 = "cb064987988fcd658470d6a24f1c68f6d7982c80ab9efb08cb8c84ef88fd03e1";
    DefaultFacebookClient facebookClient2 = new DefaultFacebookClient();
    String test2 = facebookClient2.obtainAppSecretProof("helloWorld", "PRIE7$oG2uS-Yf17kEnUEpi5hvW/#AFo");
    assertEquals(php_result2, test2);
  }

  /**
   * Do we correctly handle the case where FB returns an OAuthException with an error code?
   */
  @Test
  public void oauthExceptionWithErrorCode() {
    FacebookClient facebookClient =
        facebookClientWithResponse(new Response(403,
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
    FacebookClient facebookClient =
        facebookClientWithResponse(new Response(403,
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
    FacebookClient facebookClient =
        facebookClientWithResponse(new Response(403,
          "{\"error\":{\"message\":\"(#210) User not visible\",\"type\":\"OAuthException\"}}"));

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
    FacebookClient fbc =
        facebookClientWithResponse(new Response(200,
          "{\"access_token\": \"accesstoken\", \"token_type\":\"tokentype\", \"expires_in\":132363}"));
    FacebookClient.AccessToken at = fbc.obtainExtendedAccessToken("a", "b", "c");
    assertEquals("accesstoken", at.getAccessToken());
    assertEquals("tokentype", at.getTokenType());
    assertTrue(at.getExpires().getTime() > new Date().getTime());
  }

  @Test
  public void obtainExtendedAccessTokenV23WithoutExpiresIn() {
    FacebookClient fbc =
        facebookClientWithResponse(new Response(200,
          "{\"access_token\": \"accesstoken\", \"token_type\":\"tokentype\"}"));
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
  public void deleteObjectReturnsJson() {
    FacebookClient facebookClient = facebookClientWithResponse(new Response(200, "{\"success\":true}"));
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
    assertEquals("https://www.facebook.com/logout.php?next=http%3A%2F%2Fwww.example.com&access_token=123456", logoutUrl);
  }

  @Test
  public void checkLoginDialogURL() {
    FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_0);
    String loginDialogUrlString = client.getLoginDialogUrl("123456", "http://www.example.com", new ScopeBuilder());
    assertEquals(
      "https://www.facebook.com/dialog/oauth?client_id=123456&redirect_uri=http%3A%2F%2Fwww.example.com&scope=public_profile",
      loginDialogUrlString);
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

    }, new DefaultJsonMapper());
  }
}