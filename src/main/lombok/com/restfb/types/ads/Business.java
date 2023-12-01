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
package com.restfb.types.ads;

import java.util.Date;

import com.restfb.Facebook;
import com.restfb.types.Page;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/businessmanager">Business type</a>.
 */
public class Business extends NamedAdsObject {

  private static final long serialVersionUID = 1L;

  /**
   * The creation time of this business.
   */
  @Getter
  @Setter
  @Facebook("creation_time")
  private Date creationTime;

  /**
   * The creation time of this business.
   */
  @Getter
  @Setter
  @Facebook("created_time")
  private Date createdTime;

  /**
   * The creator of this business.
   */
  @Getter
  @Setter
  @Facebook("created_by")
  private NamedAdsObject createdBy;

  /**
   * Specifies whether offline analytics for business is blocked.
   */
  @Getter
  @Setter
  @Facebook("block_offline_analytics")
  private Boolean blockOfflineAnalytics;

  /**
   * The update time of the extended credits for this business.
   */
  @Getter
  @Setter
  @Facebook("extended_updated_time")
  private Date extendedUpdatedTime;

  /**
   * If true, indicates the business is hidden.
   */
  @Getter
  @Setter
  @Facebook("is_hidden")
  private Boolean isHidden;

  /**
   * URI for business profile page.
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * The ID for the payment account of this business.
   */
  @Getter
  @Setter
  @Facebook("payment_account_id")
  private String paymentAccountId;

  /**
   * The primary Facebook Page for this business.
   */
  @Getter
  @Setter
  @Facebook("primary_page")
  private Page primaryPage;

  /**
   * The profile picture URI of the business.
   */
  @Getter
  @Setter
  @Facebook("profile_picture_uri")
  private String profilePictureUri;

  /**
   * This business's timezone.
   */
  @Getter
  @Setter
  @Facebook("timezone_id")
  private Integer timezoneId;

  @Getter
  @Setter
  @Facebook("two_factor_type")
  private String twoFactorType;

  /**
   * The time when this business was last updated.
   */
  @Getter
  @Setter
  @Facebook("update_time")
  private Date updateTime;

  /**
   * The person's name who last updated this business.
   */
  @Getter
  @Setter
  @Facebook("updated_by")
  private NamedAdsObject updatedBy;

  @Getter
  @Setter
  @Facebook("user_access_expire_time")
  private Date userAccessExpireTime;

  /**
   * Verification status for this business.
   */
  @Getter
  @Setter
  @Facebook("verification_status")
  private String verificationStatus;

  /**
   * The vertical industry that this business associates with, or belongs to.
   */
  @Getter
  @Setter
  @Facebook
  private String vertical;

  /**
   * The ID for the vertical industry.
   */
  @Getter
  @Setter
  @Facebook("vertical_id")
  private Integer verticalId;

}
