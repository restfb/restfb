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
 */
public class IgUser extends NamedFacebookType {

  private static final long serialVersionUID = 1L;

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
   * Ig Id of the user
   */
  @Getter
  @Setter
  @Facebook("ig_id")
  private Long igId;

  /**
   * Filtered media count of the user
   */
  @Getter
  @Setter
  @Facebook("media_count")
  private Long mediaCount;

  /**
   * The cdn url to query the raw profile picture of the user
   */
  @Getter
  @Setter
  @Facebook("profile_picture_url")
  private String profilePictureUrl;

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
}
