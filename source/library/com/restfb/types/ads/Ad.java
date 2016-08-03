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

import java.util.*;

import lombok.Getter;
import lombok.Setter;

public class Ad extends NamedAdsObject {

  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  @Getter
  @Setter
  @Facebook("ad_review_feedback")
  private AdgroupReviewFeedback adReviewFeedback;

  @Facebook("adlabels")
  private List<AdLabel> adlabels = new ArrayList<AdLabel>();

  @Getter
  @Setter
  @Facebook("adset")
  private AdSet adset;

  @Getter
  @Setter
  @Facebook("adset_id")
  private String adsetId;

  @Getter
  @Setter
  @Facebook("bid_amount")
  private Long bidAmount;

  @Getter
  @Setter
  @Facebook("bid_info")
  private Map<String, Long> bidInfo;

  @Getter
  @Setter
  @Facebook("bid_type")
  private String bidType;

  @Getter
  @Setter
  @Facebook("campaign")
  private Campaign campaign;

  @Getter
  @Setter
  @Facebook("campaign_id")
  private String campaignId;

  @Getter
  @Setter
  @Facebook("configured_status")
  private String configuredStatus;

  @Facebook("conversion_specs")
  private List<ConversionActionQuery> conversionSpecs = new ArrayList<ConversionActionQuery>();

  @Getter
  @Setter
  private Date createdTime;

  @Facebook("created_time")
  private String rawCreatedTime;

  @Getter
  @Setter
  @Facebook("creative")
  private AdCreative creative;

  @Getter
  @Setter
  @Facebook("effective_status")
  private String effectiveStatus;

  @Getter
  @Setter
  @Facebook("last_updated_by_app_id")
  private String lastUpdatedByAppId;

  @Facebook("recommendations")
  private List<AdRecommendation> recommendations = new ArrayList<AdRecommendation>();

  @Getter
  @Setter
  @Facebook("status")
  private String status;

  @Facebook("tracking_specs")
  private List<ConversionActionQuery> trackingSpecs = new ArrayList<ConversionActionQuery>();

  @Getter
  @Setter
  private Date updatedTime;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  public boolean addConversionSpec(ConversionActionQuery conversionSpec) {
    return conversionSpecs.add(conversionSpec);
  }

  public boolean removeConversionSpec(ConversionActionQuery conversionSpec) {
    return conversionSpecs.remove(conversionSpec);
  }

  public List<ConversionActionQuery> getConversionSpecs() {
    return Collections.unmodifiableList(conversionSpecs);
  }

  public boolean addAdlabel(AdLabel adLabel) {
    return adlabels.add(adLabel);
  }

  public boolean removeAdlabel(AdLabel adLabel) {
    return adlabels.remove(adLabel);
  }

  public List<AdLabel> getAdlabels() {
    return Collections.unmodifiableList(adlabels);
  }

  public boolean addRecommendation(AdRecommendation adRecommendation) {
    return recommendations.add(adRecommendation);
  }

  public boolean removeRecommendation(AdRecommendation adRecommendation) {
    return recommendations.remove(adRecommendation);
  }

  public List<AdRecommendation> getRecommendations() {
    return Collections.unmodifiableList(recommendations);
  }

  public boolean addTrackingSpec(ConversionActionQuery trackingSpec) {
    return trackingSpecs.add(trackingSpec);
  }

  public boolean removeTrackingSpec(ConversionActionQuery trackingSpec) {
    return trackingSpecs.remove(trackingSpec);
  }

  public List<ConversionActionQuery> getTrackingSpecs() {
    return Collections.unmodifiableList(trackingSpecs);
  }

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }
}
