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

import java.util.*;

import com.restfb.Facebook;
import com.restfb.types.features.HasCreatedTime;

import lombok.Getter;
import lombok.Setter;

public class Ad extends NamedAdsObject implements HasCreatedTime {

  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  @Getter
  @Setter
  @Facebook("ad_review_feedback")
  private AdgroupReviewFeedback adReviewFeedback;

  @Facebook("adlabels")
  private List<AdLabel> adlabels = new ArrayList<>();

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
  private List<ConversionActionQuery> conversionSpecs = new ArrayList<>();

  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook("created_time")
  private Date createdTime;

  @Getter
  @Setter
  @Facebook("creative")
  private AdCreative creative;

  @Getter
  @Setter
  @Facebook("demolink_hash")
  private String demolinkHash;

  @Getter
  @Setter
  @Facebook("display_sequence")
  private Long displaySequence;

  @Getter
  @Setter
  @Facebook("effective_status")
  private String effectiveStatus;

  @Getter
  @Setter
  @Facebook("engagement_audience")
  private Boolean engagementAudience;

  @Getter
  @Setter
  @Facebook("failed_delivery_checks")
  private List<DeliveryCheck> failedDeliveryChecks = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("issues_info")
  private List<AdgroupIssuesInfo> issuesInfo = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private Long priority;

  @Getter
  @Setter
  @Facebook("source_ad")
  private Ad sourceAd;

  @Getter
  @Setter
  @Facebook("source_ad_id")
  private String sourceAdId;

  @Getter
  @Setter
  @Facebook
  private Targeting targeting;

  @Getter
  @Setter
  @Facebook("last_updated_by_app_id")
  private String lastUpdatedByAppId;

  @Facebook("recommendations")
  private List<AdRecommendation> recommendations = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("status")
  private String status;

  @Getter
  @Setter
  @Facebook("tracking_and_conversion_with_defaults")
  private TrackingAndConversionWithDefaults trackingAndConversionWithDefaults;

  @Facebook("tracking_specs")
  private List<ConversionActionQuery> trackingSpecs = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("updated_time")
  private Date updatedTime;

  @Facebook
  private List<TargetingSentenceLine> targetingsentencelines = new ArrayList<>();

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

  public boolean addTargetingsentenceline(TargetingSentenceLine targetingsentenceline) {
    return targetingsentencelines.add(targetingsentenceline);
  }

  public boolean removeTargetingsentenceline(TargetingSentenceLine targetingsentenceline) {
    return targetingsentencelines.remove(targetingsentenceline);
  }

  public List<TargetingSentenceLine> getTargetingsentencelines() {
    return Collections.unmodifiableList(targetingsentencelines);
  }

}
