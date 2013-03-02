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

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.restfb.WebRequestor.Response;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class FacebookClientTest {
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

    }, new DefaultJsonMapper());
  }
}