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

import static com.restfb.util.DateUtils.toDateFromLongFormat;

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-campaign-group">Campaign</a>
 * Marketing API type
 */
public class Campaign extends NamedAdsObject {

  /**
   * ID of the ad account that owns this campaign
   *
   * -- GETTER --
   *
   * @return ID of the ad account that owns this campaign
   */
  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  /**
   * Ad Labels associated with this campaign
   *
   * -- GETTER --
   *
   * @return Ad Labels associated with this campaign
   */
  @Getter
  @Setter
  @Facebook("adlabels")
  private List<AdLabel> adlabels;

  /**
   * Buying type, possible values are:
   * <ul>
   * <li>AUCTION: default</li>
   * <li>RESERVED: for reach and frequency ads</li>
   * </ul>
   *
   * -- GETTER --
   *
   * @return Buying type
   */
  @Getter
  @Setter
  @Facebook("buying_type")
  private String buyingType;

  /**
   * Whether the campaign can set the spend cap
   *
   * -- GETTER --
   *
   * @return Whether the campaign can set the spend cap
   */
  @Getter
  @Setter
  @Facebook("can_use_spend_cap")
  private Boolean canUseSpendCap;

  /**
   * If this status is PAUSED, all its active ad sets and ads will be paused and have an effective status
   * CAMPAIGN_PAUSED. Prefer using 'status' instead of this.
   *
   * possible values: ACTIVE, PAUSED, DELETED, ARCHIVED
   *
   * -- GETTER --
   *
   * @return the configured status
   */
  @Getter
  @Setter
  @Facebook("configured_status")
  private String configuredStatus;

  /**
   * Created Time
   *
   * -- GETTER --
   *
   * @return Created Time
   */
  @Getter
  @Setter
  private Date createdTime;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * The effective status of this campaign.
   *
   * possible values: ACTIVE, PAUSED, DELETED, PENDING_REVIEW, DISAPPROVED, PREAPPROVED, PENDING_BILLING_INFO,
   * CAMPAIGN_PAUSED, ARCHIVED, ADSET_PAUSED
   *
   * -- GETTER --
   *
   * @return The effective status of this campaign
   */
  @Getter
  @Setter
  @Facebook("effective_status")
  private String effectiveStatus;

  /**
   * Campaign's objective
   *
   * -- GETTER --
   *
   * @return Campaign's objective
   */
  @Getter
  @Setter
  @Facebook
  private String objective;

  /**
   * If there are recommendations for this campaign, this field includes them.
   *
   * -- GETTER --
   *
   * @return If there are recommendations for this campaign, this field includes them.
   */
  @Getter
  @Setter
  @Facebook
  private List<AdRecommendation> recommendations = new ArrayList<AdRecommendation>();

  /**
   * A spend cap for the campaign, such that it will not spend more than this cap. Expressed as integer value of the
   * subunit in your currency.
   *
   * -- GETTER --
   *
   * @return A spend cap for the campaign, such that it will not spend more than this cap.
   */
  @Getter
  @Setter
  @Facebook("spend_cap")
  private String spendCap;

  /**
   * Start Time
   *
   * -- GETTER --
   *
   * @return Start Time
   */
  @Getter
  @Setter
  private Date startTime;

  @Facebook("start_time")
  private String rawStartTime;

  /**
   * If this status is PAUSED, all its active ad sets and ads will be paused and have an effective status
   * CAMPAIGN_PAUSED. The field returns the same value as 'configured_status', and is the suggested one to use.
   *
   * -- GETTER --
   *
   * @return the status
   */
  @Getter
  @Setter
  @Facebook
  private String status;

  /**
   * Stop Time
   *
   * -- GETTER --
   *
   * @return Stop Time
   */
  @Getter
  @Setter
  private Date stopTime;

  @Facebook("stop_time")
  private String rawStopTime;

  /**
   * Updated Time
   *
   * -- GETTER --
   *
   * @return Updated Time
   */
  @Getter
  @Setter
  private Date updatedTime;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    startTime = toDateFromLongFormat(rawStartTime);
    stopTime = toDateFromLongFormat(rawStopTime);
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }
}
