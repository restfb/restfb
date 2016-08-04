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

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-campaign">AdGroup Set
 * type</a>.
 * 
 * Note: AdGroup Set vs AdGroup Campaign Prior to July 2014 ad sets were referred to as 'campaigns'. When using ad sets
 * in API calls the parameter may be referred to as 'adcampaign'. A campaign contains one or more ad sets.
 */
public class AdSet extends NamedAdsObject {

  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  @Getter
  @Setter
  @Facebook("adcampaign_group")
  private AdCampaignGroup adcampaignGroup;

  @Facebook("adlabels")
  private List<AdLabel> adLabels = new ArrayList<AdLabel>();

  @Getter
  @Setter
  @Facebook("adset_schedule")
  private List<DayPart> mAdsetSchedule = new ArrayList<DayPart>();

  @Getter
  @Setter
  @Facebook("bid_amount")
  private Long bidAmount;

  @Getter
  @Setter
  @Facebook("bid_info")
  private Map<String, Long> mBidInfo;

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

  @Getter
  @Setter
  private Date createdTime;

  @Facebook("created_time")
  private String rawCreatedTime;

  @Getter
  @Setter
  @Facebook("creative_sequence")
  private List<String> creativeSequence;

  @Getter
  @Setter
  @Facebook("daily_budget")
  private String dailyBudget;

  @Getter
  @Setter
  @Facebook("effective_status")
  private String effectiveStatus;

  @Getter
  @Setter
  private Date endTime;

  @Facebook("end_time")
  private String rawEndTime;

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
  private List<AdCampaignFrequencyControlSpecs> frequencyControlSpecs;

  @Getter
  @Setter
  @Facebook("is_autobid")
  private Boolean isAutobid;

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
  private List<String> pacingType;

  @Getter
  @Setter
  @Facebook("promoted_object")
  private AdPromotedObject promotedObject;

  @Getter
  @Setter
  @Facebook("recommendations")
  private List<AdRecommendation> recommendations;

  @Getter
  @Setter
  @Facebook("rf_prediction_id")
  private String rfPredictionId;

  @Getter
  @Setter
  @Facebook("rtb_flag")
  private Boolean rtbFlag;

  @Facebook("start_time")
  private String rawStartTime;

  @Getter
  @Setter
  private Date startTime;

  @Getter
  @Setter
  @Facebook("status")
  private String mStatus;

  @Getter
  @Setter
  @Facebook("targeting")
  private Targeting mTargeting;

  @Getter
  @Setter
  private Date updatedTime;

  @Facebook("updated_time")
  private String rawUpdatedTime;

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

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    startTime = toDateFromLongFormat(rawStartTime);
    endTime = toDateFromLongFormat(rawEndTime);
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }
}
