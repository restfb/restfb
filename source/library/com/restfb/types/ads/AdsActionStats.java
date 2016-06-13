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
package com.restfb.types.ads;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/ads-action-stats/">Ads action
 * stats type</a>
 */
public class AdsActionStats extends AbstractFacebookType {

  /**
   * The ID of the specific carousel card that people engaged with when they saw your ad.
   */
  @Getter
  @Setter
  @Facebook("action_carousel_card_id")
  private String actionCarouselCardId;

  /**
   * The specific carousel card that people engaged with when they saw your ad. The cards are identified by their
   * headlines.
   */
  @Getter
  @Setter
  @Facebook("action_carousel_card_name")
  private String actionCarouselCardName;

  /**
   * The destination where people go after clicking on your ad. This could be your Facebook Page, an external URL for
   * your conversion pixel or an app configured with the software development kit (SDK).
   */
  @Getter
  @Setter
  @Facebook("action_destination")
  private String actionDestination;

  /**
   * The device on which the conversion event you are tracking occurred. For example, "Desktop" if someone converted on
   * a desktop computer.
   */
  @Getter
  @Setter
  @Facebook("action_device")
  private String actionDevice;

  /**
   * The destination where people go after clicking on your ad. This could be your Facebook Page, an external URL for
   * your conversion pixel or an app configured with the software development kit (SDK).
   */
  @Getter
  @Setter
  @Facebook("action_target_id")
  private String actionTargetId;

  /**
   * The kind of actions taken on your ad, Page, app or event after your ad was served to someone, even if they didn't
   * click on it. Action types include Page likes, app installs, conversions, event responses and more.
   */
  @Getter
  @Setter
  @Facebook("action_type")
  private String actionType;

  /**
   * Video metrics breakdown.
   */
  @Getter
  @Setter
  @Facebook("action_video_type")
  private String actionVideoType;

  /**
   * Metric value of default attribution window
   */
  @Getter
  @Setter
  @Facebook
  private Double value;

  /**
   * Metric value of attribution window "1d_view"
   */
  @Getter
  @Setter
  @Facebook("1d_view")
  private Double view1d;

  /**
   * Metric value of attribution window "7d_view"
   */
  @Getter
  @Setter
  @Facebook("7d_view")
  private Double view7d;

  /**
   * Metric value of attribution window "28d_view"
   */
  @Getter
  @Setter
  @Facebook("28d_view")
  private Double view28d;

  /**
   * Metric value of attribution window "1d_click"
   */
  @Getter
  @Setter
  @Facebook("1d_click")
  private Double click1d;

  /**
   * Metric value of attribution window "7d_click"
   */
  @Getter
  @Setter
  @Facebook("7d_click")
  private Double click7d;

  /**
   * Metric value of attribution window "28d_click"
   */
  @Getter
  @Setter
  @Facebook("28d_click")
  private Double click28d;
}
