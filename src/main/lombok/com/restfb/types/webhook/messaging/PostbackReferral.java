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
package com.restfb.types.webhook.messaging;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the referral sub object described here:
 * https://developers.facebook.com/docs/messenger-platform/referral-params
 */
@ToString
public class PostbackReferral {

  /**
   * The arbitrary data that was originally passed in the ref param added to the m.me link.
   */
  @Getter
  @Setter
  @Facebook
  private String ref;

  /**
   *
   */
  @Getter
  @Setter
  @Facebook("ad_id")
  private String adId;

  /**
   * The source of this referral. Currently, the only possible value is “SHORTLINK”.
   */
  @Getter
  @Setter
  @Facebook
  private String source;

  /**
   * The identifier for the referral. For referrals coming from m.me links, it will always be "OPEN_THREAD".
   */
  @Getter
  @Setter
  @Facebook
  private String type;

  /**
   * The URI of the site where the message was sent in the Facebook chat plugin.
   */
  @Getter
  @Setter
  @Facebook("referer_uri")
  private String refererUri;

  /**
   * A flag indicating whether the user is a guest user from Facebook Chat Plugin
   */
  @Getter
  @Setter
  @Facebook("is_guest_user")
  private Boolean isGuestUser;

  /**
   * The data contaning information about the CTM ad, the user initiated the thread from.
   */
  @Getter
  @Setter
  @Facebook("ads_context_data")
  private AdsContextData adsContextData;

  public static class AdsContextData {

    /**
     * Title of the Ad.
     */
    @Getter
    @Setter
    @Facebook("ad_title")
    private String adTitle;

    /**
     * [Optional] Url of the image from the Ad the user is interested.
     */
    @Getter
    @Setter
    @Facebook("photo_url")
    private String photoUrl;

    /**
     * [Optional] Thumbnail url of the the video from the ad.
     */
    @Getter
    @Setter
    @Facebook("video_url")
    private String videoUrl;

    /**
     * ID of the post.
     */
    @Getter
    @Setter
    @Facebook("post_id")
    private String postId;
  }
}
