/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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
package com.restfb.integration.base;

import java.util.Properties;

import org.junit.jupiter.api.Assumptions;

/**
 * Settings holder for integration testing.
 */
public class RestFbIntegrationTestSettings {

  private final boolean writeToFacebook;

  private final String userAccessToken;

  private final String userId;

  private final String pageAccessToken;

  private final String pageId;

  private final String userGroupId;

  private final String appSecret;

  private final String appId;

  private final String appAccessToken;

  private final String recipientId;

  private final String pageAlbumId;

  private final String threadsAccessToken;

  private final String threadsClientSecret;

  private final String threadsProfileId;

  RestFbIntegrationTestSettings(Properties settings) {
    writeToFacebook = Boolean.parseBoolean(settings.getProperty("writeToFacebook", "false"));
    userAccessToken = settings.getProperty("user.accessToken", "");
    pageAccessToken = settings.getProperty("page.accessToken", "");
    appAccessToken = settings.getProperty("app.accessToken", "");
    userId = settings.getProperty("user.id", "");
    pageId = settings.getProperty("page.id", "");
    userGroupId = settings.getProperty("user.group.id", "");
    appSecret = settings.getProperty("app.secret", "");
    appId = settings.getProperty("app.id", "");
    recipientId = settings.getProperty("messenger.recipient", "");
    pageAlbumId = settings.getProperty("page.album.id", "");
    threadsAccessToken = settings.getProperty("threads.accessToken", "");
    threadsClientSecret = settings.getProperty("threads.clientSecret", "");
    threadsProfileId = settings.getProperty("threads.profileId", "");
  }

  public boolean writeAccessAllowed() {
    return writeToFacebook;
  }

  public String getUserAccessToken() {
    Assumptions.assumeFalse(userAccessToken.isEmpty());
    return userAccessToken;
  }

  public String getUserId() {
    Assumptions.assumeFalse(userId.isEmpty());
    return userId;
  }

  public String getPageAccessToken() {
    Assumptions.assumeFalse(pageAccessToken.isEmpty());
    return pageAccessToken;
  }

  public String getPageId() {
    Assumptions.assumeFalse(pageId.isEmpty());
    return pageId;
  }

  public String getGroupId() {
    Assumptions.assumeFalse(userGroupId.isEmpty());
    return userGroupId;
  }

  public String getAppSecret() {
    Assumptions.assumeFalse(appSecret.isEmpty());
    return appSecret;
  }

  public String getAppId() {
    Assumptions.assumeFalse(appId.isEmpty());
    return appId;
  }

  public String getRecipientId() {
    Assumptions.assumeFalse(recipientId.isEmpty());
    return recipientId;
  }

  public String getPageAlbumId() {
    Assumptions.assumeFalse(pageAlbumId.isEmpty());
    return pageAlbumId;
  }

  public String getAppAccessToken() {
    Assumptions.assumeFalse(appAccessToken.isEmpty());
    return appAccessToken;
  }

  public String getThreadsAccessToken() {
    Assumptions.assumeFalse(threadsAccessToken.isEmpty());
    return threadsAccessToken;
  }

  public String getThreadsClientSecret() {
    Assumptions.assumeFalse(threadsClientSecret.isEmpty());
    return threadsClientSecret;
  }

  public String getThreadsProfileId() {
    Assumptions.assumeFalse(threadsProfileId.isEmpty());
    return threadsProfileId;
  }
}
