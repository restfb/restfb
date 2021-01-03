/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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
import com.restfb.annotation.GraphAPI;
import com.restfb.json.JsonObject;

import com.restfb.types.features.HasCreatedTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-campaign">AdGroup Set
 * type</a>.
 * 
 * Note: AdGroup Set vs AdGroup Campaign Prior to July 2014 ad sets were referred to as 'campaigns'. When using ad sets
 * in API calls the parameter may be referred to as 'adcampaign'. A campaign contains one or more ad sets.
 */
public class AdSet extends NamedAdsObject implements HasCreatedTime {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  @Getter
  @Setter
  @Facebook("ad_keywords")
  private AdKeywords adKeywords;

  @Getter
  @Setter
  @Facebook("adcampaign_group")
  private Campaign adcampaignGroup;

  @Facebook("adlabels")
  private List<AdLabel> adLabels = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("adset_schedule")
  private List<DayPart> adsetSchedule = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("asset_feed_id")
  private String assetFeedId;

  @Getter
  @Setter
  @Facebook("attribution_spec")
  private List<JsonObject> attributionSpec = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("best_creative")
  private AdDynamicCreative bestCreative;

  @Getter
  @Setter
  @Facebook("bid_adjustments")
  private AdBidAdjustments bidAdjustments;

  @Getter
  @Setter
  @Facebook("bid_amount")
  private Long bidAmount;

  @Getter
  @Setter
  @Facebook("bid_info")
  private Map<String, Long> bidInfo = new HashMap<>();

  @Getter
  @Setter
  @Facebook("bid_constraints")
  private AdCampaignBidConstraint bidConstraints;

  @Getter
  @Setter
  @Facebook("bid_info")
  private Map<String, Long> mBidInfo = new HashMap<>();

  @Getter
  @Setter
  @Facebook("bid_strategy")
  private BidStrategyEnum bidStrategy;

  @Getter
  @Setter
  @Facebook("billing_event")
  private String billingEvent;

  @Getter
  @Setter
  @Facebook("budget_remaining")
  private String budgetRemaining;

  @Getter
  @Setter
  @Facebook
  private Campaign campaign;

  @Getter
  @Setter
  @Facebook("campaign_id")
  private String campaignId;

  @Getter
  @Setter
  @Facebook("configured_status")
  private String configuredStatus;

  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook("created_time")
  private Date createdTime;

  @Getter
  @Setter
  @Facebook("creative_sequence")
  private List<String> creativeSequence = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("daily_budget")
  private String dailyBudget;

  @Getter
  @Setter
  @Facebook("daily_min_spend_target")
  private String dailyMinSpendTarget;

  @Getter
  @Setter
  @Facebook("daily_spend_cap")
  private String dailySpendCap;
  @Getter
  @Setter
  @Facebook("destination_type")
  private String destinationType;

  @Getter
  @Setter
  @Facebook("effective_status")
  private String effectiveStatus;

  @Getter
  @Setter
  @Facebook("end_time")
  private Date endTime;

  @Getter
  @Setter
  @Facebook("frequency_cap")
  private Long frequencyCap;

  @Getter
  @Setter
  @Facebook("frequency_cap_reset_period")
  private Long frequencyCapResetPeriod;

  @Getter
  @Setter
  @Facebook("frequency_control_specs")
  private List<AdCampaignFrequencyControlSpecs> frequencyControlSpecs = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("is_autobid")
  private Boolean isAutobid;

  @Getter(onMethod_ = @GraphAPI(since = "3.2"))
  @Setter
  @Facebook("issues_info")
  @GraphAPI(since = "3.2")
  private List<AdCampaignIssuesInfo> issuesInfo = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("lifetime_budget")
  private String lifetimeBudget;

  @Getter
  @Setter
  @Facebook("lifetime_frequency_cap")
  private Long lifetimeFrequencyCap;

  @Getter
  @Setter
  @Facebook("lifetime_imps")
  private Long lifetimeImps;

  @Getter
  @Setter
  @Facebook("optimization_goal")
  private String optimizationGoal;

  @Getter
  @Setter
  @Facebook("pacing_type")
  private List<String> pacingType = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("promoted_object")
  private AdPromotedObject promotedObject;

  @Getter
  @Setter
  @Facebook("recommendations")
  private List<AdRecommendation> recommendations = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("rf_prediction_id")
  private String rfPredictionId;

  @Getter
  @Setter
  @Facebook("rtb_flag")
  private Boolean rtbFlag;

  @Getter
  @Setter
  @Facebook("start_time")
  private Date startTime;

  @Getter
  @Setter
  @Facebook("status")
  private String mStatus;

  @Getter
  @Setter
  @Facebook("targeting")
  private Targeting targeting;

  @Getter
  @Setter
  @Facebook("updated_time")
  private Date updatedTime;

  @Getter
  @Setter
  @Facebook("use_new_app_click")
  private Boolean useNewAppClick;

  public boolean addAdLabel(AdLabel adLabel) {
    return adLabels.add(adLabel);
  }

  public boolean removeAdLabel(AdLabel adLabel) {
    return adLabels.remove(adLabel);
  }

  public List<AdLabel> getAdlabels() {
    return Collections.unmodifiableList(adLabels);
  }

}
