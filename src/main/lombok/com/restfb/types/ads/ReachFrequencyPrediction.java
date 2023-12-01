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

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/marketing-api/reference/reach-frequency-prediction">Reach Frequency
 * Prediction type</a>.
 */
public class ReachFrequencyPrediction extends NamedAdsObject {

  /**
   * The ID of the Ad Account this reach frequency prediction belongs to
   */
  @Getter
  @Setter
  @Facebook("account_id")
  private Integer accountId;

  /**
   * The id of the campaign which this prediction belongs to
   */
  @Getter
  @Setter
  @Facebook("campaign_group_id")
  private Integer campaignGroupId;

  /**
   * The ID of the ad set to which this reach frequency prediction is assigned
   */
  @Getter
  @Setter
  @Facebook("campaign_id")
  private String campaignId;

  /**
   * Unix timestamp of the ad set start time
   */
  @Getter
  @Setter
  @Facebook("campaign_time_start")
  private Date campaignTimeStart;

  /**
   * Unix timestamp of the ad set stop time
   */
  @Getter
  @Setter
  @Facebook("campaign_time_stop")
  private Date campaignTimeStop;

  /**
   * The curve for budget and reach.
   *
   * It is a string in JSON format representing a JSON object with these fields.<br>
   * <code>num_points</code>: the number of data points within the object.<br>
   * <code></code>reach</code>: Data contained at corresponding indices of each array form a single data point. The
   * "reach" values are presented in ascending order with the final value containing the maximum available reach.<br>
   * <code>budget</code>: Data contained at corresponding indices of each array form a single data point. Cent of
   * accounts currency.<br>
   * <code>impression</code>: Data contained at corresponding indices of each array form a single data point. In video
   * view buying, this is the number of view throughs (aka impression by conversion)raw_impression: Data contained at
   * corresponding indices of each array form a single data point. In video view buying,impressions represents view
   * throughs and raw_impressions representstotal number of views
   */
  @Getter
  @Setter
  @Facebook("curve_budget_reach")
  private String curveBudgetReach;

  /**
   * Daily Impression field represents a vector of predicted daily impressions for every single day. Measured from
   * midnight to midnight in the advertiser timezone during the campaign duration.
   */
  @Getter
  @Setter
  @Facebook("daily_impression_curve")
  private List<Double> dailyImpressionCurve = new ArrayList<>();

  /**
   * The ID of the Page or the ID of the app which the ad promotes.
   */
  @Getter
  @Setter
  @Facebook("destination_id")
  private String destinationId;

  /**
   * Unix timestamp of the expiration time of prediction, if applicable
   */
  @Getter
  @Setter
  @Facebook("expiration_time")
  private Date expirationTime;

  /**
   * Predicted budget in cents for the ad set, relevant if prediction mode is 0
   */
  @Getter
  @Setter
  @Facebook("external_budget")
  private Integer externalBudget;

  /**
   * Predicted impressions for the ad set
   */
  @Getter
  @Setter
  @Facebook("external_impression")
  private Long externalImpression;

  /**
   * Maximum budget given the target, in cents
   */
  @Getter
  @Setter
  @Facebook("external_maximum_budget")
  private Long externalMaximumBudget;

  /**
   * Maximum number of impressions given the target
   */
  @Getter
  @Setter
  @Facebook("external_maximum_impression")
  private String externalMaximumImpression;

  /**
   * Maximum reach given the target
   */
  @Getter
  @Setter
  @Facebook("external_maximum_reach")
  private Long externalMaximumReach;

  /**
   * Minimum budget given the target, in cents
   */
  @Getter
  @Setter
  @Facebook("external_minimum_budget")
  private Long externalMinimumBudget;

  /**
   * Minimum impressions given the target
   */
  @Getter
  @Setter
  @Facebook("external_minimum_impression")
  private Long externalMinimumImpression;

  /**
   * Minimum reach given the target
   */
  @Getter
  @Setter
  @Facebook("external_minimum_reach")
  private Long externalMinimumReach;

  /**
   * Predicted reach for the ad set, relevant if prediction mode is 1
   */
  @Getter
  @Setter
  @Facebook("external_reach")
  private Long externalReach;

  /**
   * Lifetime frequency cap per user, always relevant, 0 means no frequncy cap
   */
  @Getter
  @Setter
  @Facebook("frequency_cap")
  private Long frequencyCap;

  /**
   * GRP: Audience size within DMAs based on Nielsen definition
   */
  @Getter
  @Setter
  @Facebook("grp_dmas_audience_size")
  private Double grpDmasAudienceSize;

  /**
   * Percent of users in holdout
   */
  @Getter
  @Setter
  @Facebook("holdout_percentage")
  private Long holdoutPercentage;

  /**
   * The Instagram account id if instagramstream placement is used, except in the case of Mobile App Installs ads.
   */
  @Getter
  @Setter
  @Facebook("instagram_destination_id")
  private String instagramDestinationId;

  /**
   * Interval frequency cap which is set for a custom period
   */
  @Getter
  @Setter
  @Facebook("interval_frequency_cap")
  private Long intervalFrequencyCap;

  /**
   * Custom reset period (hours) for interval frequency cap
   */
  @Getter
  @Setter
  @Facebook("interval_frequency_cap_reset_period")
  private Long intervalFrequencyCapResetPeriod;

  /**
   * A list of time periods the associated campaign has been paused.
   */
  @Getter
  @Setter
  @Facebook("pause_periods")
  private String pausePeriods;

  /**
   * Predicted impression distribution on different placements, including:
   *
   * <code>msite</code>: Facebook mobile sites<br>
   * <code>android</code>: Facebook android<br>
   * <code>ios</code>: Facebook ios<br>
   * <code>desktop</code>: Facebook desktop news feed and right hand column<br>
   * <code>ig_android</code>: Instagram android<br>
   * <code>ig_ios</code>: Instagram ios<br>
   * <code>ig_others</code>: Other Instagram placements
   *
   * ## ReachFrequencyEstimatesPlacementBreakdown
   */
  @Getter
  @Setter
  @Facebook("placement_breakdown")
  private String placementBreakdown;

  /**
   * The prediction mode,
   *
   * <code>0</code> = given reach, predict budget,<br>
   * <code>1</code> = given budget, predict reach
   */
  @Getter
  @Setter
  @Facebook("prediction_mode")
  private Long predictionMode;

  /**
   * Represents percentage value indicating the prediction progress (values 0-100). When 100 check status to indicate
   * whether the prediction was successful.
   */
  @Getter
  @Setter
  @Facebook("prediction_progress")
  private Long predictionProgress;

  /**
   * Reservation status.
   *
   * <code>0</code> = Cancelled prediction,<br>
   * <code>1</code> = Reserved prediction,<br>
   * <code>2</code> = Prediction has been attached to a campaign
   */
  @Getter
  @Setter
  @Facebook("reservation_status")
  private Long reservationStatus;

  /**
   * Represents the status of the prediction, refer to Response Status
   */
  @Getter
  @Setter
  @Facebook
  private Long status;

  /**
   * Used to indicated the prediction is for video ads or not. If it is for video, the prediction will not include
   * devices that cannot play video
   */
  @Getter
  @Setter
  @Facebook("story_event_type")
  private Long storyEventType;

  /**
   * Unique 30-day active users for given targetting specs. Used as tip to indicate the maximum possible audience size
   * if campaign length is increased
   */
  @Getter
  @Setter
  @Facebook("target_audience_size")
  private Long targetAudienceSize;

  /**
   * A string in JSON format representing the targeting specs specified on creation.
   */
  @Getter
  @Setter
  @Facebook("target_spec")
  private Targeting targetSpec;

  /**
   * The time when this reach frequency prediction was created
   */
  @Getter
  @Setter
  @Facebook("time_created")
  private Date timeCreated;

  /**
   * Unix timestamp when the row is updated
   */
  @Getter
  @Setter
  @Facebook("time_updated")
  private Date timeUpdated;

}
