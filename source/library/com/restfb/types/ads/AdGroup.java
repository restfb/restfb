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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Deprecated use {@link Ad} instead
 */
@Deprecated
public class AdGroup extends NamedAdsObject {

  /**
   * The ID of the ad account that this ad belongs to.
   */
  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  /**
   * AdGroup set that contains this ad
   */
  @Getter
  @Setter
  @Facebook
  private AdSet adset;

  /**
   * AdGroup campaign that contains this ad
   */
  @Getter
  @Setter
  @Facebook
  private AdCampaignGroup campaign;

  /**
   * ID of the ad set that contains the ad
   */
  @Getter
  @Setter
  @Facebook("adset_id")
  private String adsetId;

  /**
   * Bid amount for this ad
   */
  @Getter
  @Setter
  @Facebook("bid_amount")
  private Integer bidAmount;

  /**
   * Bid type
   */
  @Getter
  @Setter
  @Facebook("bid_type")
  private String bidType;

  /**
   * The configured status of the ad. Possible values are ACTIVE, PAUSED, ARCHIVED, DELETED.
   */
  @Getter
  @Setter
  @Facebook("configured_status")
  private String configuredStatus;

  /**
   * Created time
   */
  @Setter
  @Getter
  private Date createdTime;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * This field is required for create. The ID of the ad creative to be used by this ad.
   */
  @Getter
  @Setter
  @Facebook
  private AdCreative creative;

  /**
   * The effective status of the ad. Possible values are ACTIVE, PAUSED, ARCHIVED, DELETED, CAMPAIGN_PAUSED,
   * ADSET_PAUSED, PENDING_REVIEW, DISAPPROVED, PREAPPROVED, PENDING_BILLING_INFO.
   */
  @Getter
  @Setter
  @Facebook("effective_status")
  private String effectiveStatus;

  /**
   * Last Updated By App ID
   */
  @Getter
  @Setter
  @Facebook("last_updated_by_app_id")
  private String lastUpdatedByAppId;

  /**
   * ID of the ad campaign that contains this ad
   */
  @Getter
  @Setter
  @Facebook("campaign_id")
  private String campaignId;

  /**
   * Updated time
   */
  @Getter
  @Setter
  private Date updatedTime;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  /**
   * Review feedback for this ad. Look here for more information:
   * https://developers.facebook.com/docs/marketing-api/adgroup/feedback
   */
  @Getter
  @Setter
  @Facebook("ad_review_feedback")
  private String adReviewFeedback;

  @Facebook("tracking_specs")
  private List<ConversionActionQuery> trackingSpecs = new ArrayList<ConversionActionQuery>();

  public List<ConversionActionQuery> getTrackingSpecs() {
    return Collections.unmodifiableList(trackingSpecs);
  }

  public boolean addTrackingSpec(ConversionActionQuery action) {
    return trackingSpecs.add(action);
  }

  public boolean removeTrackingSpec(ConversionActionQuery action) {
    return trackingSpecs.remove(action);
  }

  @Facebook("conversion_specs")
  private List<ConversionActionQuery> conversionSpecs = new ArrayList<ConversionActionQuery>();

  public List<ConversionActionQuery> getConversionSpecs() {
    return Collections.unmodifiableList(conversionSpecs);
  }

  public boolean addConversionSpec(ConversionActionQuery action) {
    return conversionSpecs.add(action);
  }

  public boolean removeConversionSpec(ConversionActionQuery action) {
    return conversionSpecs.remove(action);
  }

  @Facebook
  private List<AdLabel> adlabels = new ArrayList<AdLabel>();

  public List<AdLabel> getAdLabels() {
    return Collections.unmodifiableList(adlabels);
  }

  public boolean addAdLabel(AdLabel action) {
    return adlabels.add(action);
  }

  public boolean removeAdLabel(AdLabel action) {
    return adlabels.remove(action);
  }

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }
}
