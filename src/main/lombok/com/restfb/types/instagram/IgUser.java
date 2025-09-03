/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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
package com.restfb.types.instagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.restfb.Connection;
import com.restfb.Facebook;
import com.restfb.types.Insight;
import com.restfb.types.NamedFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/instagram-api/reference/user/">instagram user</a> type
 * used in the Graph API
 * and contains additional fields for the instagram API with instagram login User object
 */
public class IgUser extends NamedFacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * Descriptive text for images, for accessibility.
   */
  @Getter
  @Setter
  @Facebook("alt_text")
  private String altText;

  /**
   * Biography of the user
   */
  @Getter
  @Setter
  @Facebook
  private String biography;

  /**
   * Loads business discovery information
   */
  @Getter
  @Setter
  @Facebook("business_discovery")
  private IgUser businessDiscovery;

  /**
   * Active follower count of the user
   */
  @Getter
  @Setter
  @Facebook("followers_count")
  private Long followersCount;

  /**
   * Active follows count of the user
   */
  @Getter
  @Setter
  @Facebook("follows_count")
  private Long followsCount;

  /**
   * Indicates whether your app user's Instagram professional
   * account has a profile picture.
   */
  @Getter
  @Setter
  @Facebook("has_profile_pic")
  private Boolean hasProfilePic;

  /**
   * Ig Id of the user
   */
  @Getter
  @Setter
  @Facebook("ig_id")
  private Long igId;

  /**
   * Indicates whether your app user's Instagram account is published.
   * Available for Page-backed Instagram accounts.
   */
  @Getter
  @Setter
  @Facebook("is_published")
  private Boolean isPublished;

  /**
   * Instagram Id of the Instagram API with Instagram login user
   */
  @Getter
  @Setter
  @Facebook("user_id")
  private Long userId;

  /**
   * Filtered media count of the user
   */
  @Getter
  @Setter
  @Facebook("media_count")
  private Long mediaCount;

  /**
   * The app user's account type.
   * Can be Business or Media_Creator
   */
  @Getter
  @Setter
  @Facebook("account_type")
  private String accountType;

  /**
   * Your app user's Instagram ID that was created for Marketing API
   * endpoints for v21.0 and older. Available for Page-backed Instagram accounts.
   */
  @Getter
  @Setter
  @Facebook("legacy_instagram_user_id")
  private String legacyInstagramUserId;

  /**
   * The cdn url to query the raw profile picture of the user
   */
  @Getter
  @Setter
  @Facebook("profile_picture_url")
  private String profilePictureUrl;

  /**
   * Returns true if your app user has set up an Instagram
   * Shop and is therefore eligible for product tagging, otherwise returns false.
   */
  @Getter
  @Setter
  @Facebook("shopping_product_tag_eligibility")
  private Boolean shoppingProductTagEligibility;

  /**
   * Username handle of the user
   */
  @Getter
  @Setter
  @Facebook
  private String username;

  /**
   * Url in the profile
   */
  @Getter
  @Setter
  @Facebook
  private String website;

  @Facebook
  private List<Insight> insights = new ArrayList<>();

  @Deprecated
  @Facebook
  private List<IgMedia> media = new ArrayList<>();

  @Facebook("media")
  private Connection<IgMedia> mediaConnection;

  public Connection<IgMedia> getMediaConnection() {
    return mediaConnection;
  }

  @Getter
  @Facebook("connected_threads_user")
  private Connection<IgUserConnectedThreadsUser> connectedThreadsUser;

  public List<Insight> getInsights() {
    return Collections.unmodifiableList(insights);
  }

  public boolean addChild(Insight insight) {
    return insights.add(insight);
  }

  public boolean removeChild(Insight insight) {
    return insights.remove(insight);
  }

  @Deprecated
  public List<IgMedia> getMedia() {
    return Collections.unmodifiableList(media);
  }

  @Deprecated
  public boolean addMedia(IgMedia media) {
    return this.media.add(media);
  }

  @Deprecated
  public boolean removeMedia(IgMedia media) {
    return this.media.remove(media);
  }

  /**
   * will be removed in 2026. the ID structure between
   * Instagram user fetched with Instagram Login and Facebook API are too different
   * @return some ID
   */
  @Deprecated
  public Long getInstagramId() {
    if (userId != null) {
      return userId;
    }

    if (igId != null) {
      return igId;
    }

    return null;
  }

}
