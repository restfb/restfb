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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.annotation.GraphAPI;
import com.restfb.types.features.HasCreatedTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-campaign-group">Campaign</a>
 * Marketing API type
 */
public class Campaign extends NamedAdsObject implements HasCreatedTime {

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
   * Bid strategy for this campaign
   */
  @Getter
  @Setter
  @Facebook("bid_strategy")
  private String bidStrategy;

  /**
   * The Boosted Object this campaign has associated, if any
   */
  @Getter
  @Setter
  @Facebook("boosted_object_id")
  private String boostedObjectId;

  /**
   * Automated Brand Lift V2 studies for this ad set.
   */
  @Getter
  @Setter
  @Facebook("brand_lift_studies")
  private List<AdStudy> brandLiftStudies = new ArrayList<>();

  /**
   * Whether to automatically rebalance budgets daily for all the adsets under this campaign.
   */
  @Getter
  @Setter
  @Facebook("budget_rebalance_flag")
  private Boolean budgetRebalanceFlag;

  /**
   * Remaining budget
   */
  @Getter
  @Setter
  @Facebook("budget_remaining")
  private String budgetRemaining;

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
   * If we can create a new automated brand lift study for the ad set.
   */
  @Getter
  @Setter
  @Facebook("can_create_brand_lift_study")
  private Boolean canCreateBrandLiftStudy;

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
  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook("created_time")
  private Date createdTime;

  /**
   * The daily budget of the campaign
   */
  @Getter
  @Setter
  @Facebook("daily_budget")
  private String dailyBudget;

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
   * Issues for this campaign that prevented it from deliverying.
   *
   * -- GETTER --
   *
   * @return List of issues for this campaign that prevented it from deliverying.
   */
  @Getter(onMethod_ = @GraphAPI(since = "3.2"))
  @Setter
  @Facebook("issues_info")
  @GraphAPI(since = "3.2")
  private List<AdCampaignIssuesInfo> issuesInfo = new ArrayList<>();

  /**
   * Last budget toggling time
   */
  @Getter
  @Setter
  @Facebook("last_budget_toggling_time")
  private Date lastBudgetTogglingTime;

  /**
   * The lifetime budget of the campaign
   */
  @Getter
  @Setter
  @Facebook("lifetime_budget")
  private String lifetimeBudget;

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
  private List<AdRecommendation> recommendations = new ArrayList<>();

  /**
   * The source campaign that this campaign is copied from
   */
  @Getter
  @Setter
  @Facebook("source_campaign")
  private Campaign sourceCampaign;

  /**
   * The source campaign id that this campaign is copied from
   */
  @Getter
  @Setter
  @Facebook("source_campaign_id")
  private String sourceCampaignId;

  /**
   * The campaign's Special Ad Category. One of {@code HOUSING}, {@code EMPLOYMENT}, {@code CREDIT}, or {@code NONE}.
   */
  @Deprecated
  @Getter
  @Setter
  @GraphAPI(until = "7.0")
  @Facebook("special_ad_category")
  private String specialAdCategory;

  @Getter
  @Setter
  @GraphAPI(since = "7.0")
  @Facebook("special_ad_categories")
  private List<String> specialAdCategories = new ArrayList<>();

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
  @Facebook("start_time")
  private Date startTime;

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
  @Facebook("stop_time")
  private Date stopTime;

  /**
   * Topline ID
   */
  @Getter
  @Setter
  @Facebook("topline_id")
  private String toplineId;

  /**
   * Updated Time
   *
   * -- GETTER --
   *
   * @return Updated Time
   */
  @Getter
  @Setter
  @Facebook("updated_time")
  private Date updatedTime;

}
