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

package com.restfb.types;

import com.restfb.Facebook;

/**
 * Represents the <a href="https://developers.facebook.com/docs/test_users">Test User type</a>.
 * 
 * @author <a href="http://ex-nerd.com">Chris Petersen</a>
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.10
 */
public class TestUser extends FacebookType {
  @Facebook("access_token")
  private String accessToken;

  @Facebook("login_url")
  private String loginUrl;

  @Facebook
  private String email;

  @Facebook
  private String password;

  private static final long serialVersionUID = 1L;

  /**
   * You can use this access token to make API calls on behalf of the test user. This is available only if your app has
   * been installed by the test user.
   * 
   * @return The test user's access token.
   */
  public String getAccessToken() {
    return accessToken;
  }

  /**
   * You can login as the test user by going to this url.
   * 
   * @return The test user's login url.
   */
  public String getLoginUrl() {
    return loginUrl;
  }

  /**
   * If logging in manually (that is, not using the login_url), you can use this as the user's email address.
   * 
   * @return The test user's email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * If logging in manually (that is, not using the login_url), you can use this as the user's password.
   * 
   * @return The test user's password.
   */
  public String getPassword() {
    return password;
  }
}