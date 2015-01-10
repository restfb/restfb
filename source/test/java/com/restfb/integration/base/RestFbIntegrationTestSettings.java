/*
 * Copyright (c) 2010-2015 Norbert Bartels
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

package com.restfb.integration.base;

import java.util.Properties;

/**
 * Settings holder for integration testing.
 */
public class RestFbIntegrationTestSettings {

  private boolean writeToFacebook = false;

  private String userAccessToken = "";

  private String userId = "";

  private String pageAccessToken = "";

  private String pageId = "";

  private String userGroupId = "";

  public RestFbIntegrationTestSettings(Properties settings) {
    writeToFacebook = Boolean.getBoolean(settings.getProperty("writeToFacebook", "false"));
    userAccessToken = settings.getProperty("user.accessToken", "");
    pageAccessToken = settings.getProperty("page.accessToken", "");
    userId = settings.getProperty("user.id", "");
    pageId = settings.getProperty("page.id", "");
    userGroupId = settings.getProperty("user.group.id", "");

    checkSettings();
  }

  public boolean writeAccessAllowed() {
    return writeToFacebook;
  }

  public String getUserAccessToken() {
    return userAccessToken;
  }

  public String getUserId() {
    return userId;
  }

  public String getPageAccessToken() {
    return pageAccessToken;
  }

  public String getPageId() {
    return pageId;
  }

  public String getGroupId() {
    return userGroupId;
  }

  private void checkSettings() {
    if (getUserId().isEmpty()) {
      throw new IllegalArgumentException("user id not set");
    }
    if (getUserAccessToken().isEmpty()) {
      throw new IllegalArgumentException("access token not set");
    }
    if (getPageId().isEmpty()) {
      throw new IllegalArgumentException("page id not set");
    }
    if (getPageAccessToken().isEmpty()) {
      throw new IllegalArgumentException("page access token not set");
    }
  }

}
