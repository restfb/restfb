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
import java.util.function.Supplier;

import com.restfb.Facebook;
import com.restfb.json.JsonObject;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdsInsights extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  @Facebook("account_currency")
  private String accountCurrency;
  @Facebook("account_id")
  private String accountId;
  @Facebook("account_name")
  private String accountName;
  @Facebook("action_values")
  private List<AdsActionStats> actionValues = new ArrayList<>();
  @Facebook("actions")
  private List<AdsActionStats> actions = new ArrayList<>();
  @Facebook("ad_click_actions")
  private List<AdsActionStats> adClickActions = new ArrayList<>();
  @Facebook("ad_id")
  private String adId;
  @Facebook("ad_impression_actions")
  private List<AdsActionStats> adImpressionActions = new ArrayList<>();
  @Facebook("ad_name")
  private String adName;
  @Facebook("adset_end")
  private String adsetEnd;
  @Facebook("adset_id")
  private String adsetId;
  @Facebook("adset_name")
  private String adsetName;
  @Facebook("adset_start")
  private String adsetStart;
  @Facebook("age_targeting")
  private String ageTargeting;
  @Facebook("attribution_setting")
  private String attributionSetting;
  @Facebook("auction_bid")
  private String auctionBid;
  @Facebook("auction_competitiveness")
  private String auctionCompetitiveness;
  @Facebook("auction_max_competitor_bid")
  private String auctionMaxCompetitorBid;
  @Facebook("buying_type")
  private String buyingType;
  @Facebook("campaign_id")
  private String campaignId;
  @Facebook("campaign_name")
  private String campaignName;
  @Facebook("canvas_avg_view_percent")
  private String canvasAvgViewPercent;
  @Facebook("canvas_avg_view_time")
  private String canvasAvgViewTime;
  @Facebook("catalog_segment_actions")
  private List<AdsActionStats> catalogSegmentActions = new ArrayList<>();
  @Facebook("catalog_segment_value")
  private List<AdsActionStats> catalogSegmentValue = new ArrayList<>();
  @Facebook("catalog_segment_value_mobile_purchase_roas")
  private List<AdsActionStats> catalogSegmentValueMobilePurchaseRoas = new ArrayList<>();
  @Facebook("catalog_segment_value_omni_purchase_roas")
  private List<AdsActionStats> catalogSegmentValueOmniPurchaseRoas = new ArrayList<>();
  @Facebook("catalog_segment_value_website_purchase_roas")
  private List<AdsActionStats> catalogSegmentValueWebsitePurchaseRoas = new ArrayList<>();
  @Facebook("clicks")
  private String clicks;
  @Facebook("conversion_lead_rate")
  private String conversionLeadRate;
  @Facebook("conversion_rate_ranking")
  private String conversionRateRanking;
  @Facebook("conversion_values")
  private List<AdsActionStats> conversionValues = new ArrayList<>();
  @Facebook("conversions")
  private List<AdsActionStats> conversions = new ArrayList<>();
  @Facebook("converted_product_quantity")
  private List<AdsActionStats> convertedProductQuantity = new ArrayList<>();
  @Facebook("converted_product_value")
  private List<AdsActionStats> convertedProductValue = new ArrayList<>();
  @Facebook("cost_per_15_sec_video_view")
  private List<AdsActionStats> costPer15SecVideoView = new ArrayList<>();
  @Facebook("cost_per_2_sec_continuous_video_view")
  private List<AdsActionStats> costPer2SecContinuousVideoView = new ArrayList<>();
  @Facebook("cost_per_action_type")
  private List<AdsActionStats> costPerActionType = new ArrayList<>();
  @Facebook("cost_per_ad_click")
  private List<AdsActionStats> costPerAdClick = new ArrayList<>();
  @Facebook("cost_per_conversion")
  private List<AdsActionStats> costPerConversion = new ArrayList<>();
  @Facebook("cost_per_conversion_lead")
  private String costPerConversionLead;
  @Facebook("cost_per_dda_countby_convs")
  private String costPerDdaCountbyConvs;
  @Facebook("cost_per_estimated_ad_recallers")
  private String costPerEstimatedAdRecallers;
  @Facebook("cost_per_inline_link_click")
  private String costPerInlineLinkClick;
  @Facebook("cost_per_inline_post_engagement")
  private String costPerInlinePostEngagement;
  @Facebook("cost_per_one_thousand_ad_impression")
  private List<AdsActionStats> costPerOneThousandAdImpression = new ArrayList<>();
  @Facebook("cost_per_outbound_click")
  private List<AdsActionStats> costPerOutboundClick = new ArrayList<>();
  @Facebook("cost_per_thruplay")
  private List<AdsActionStats> costPerThruplay = new ArrayList<>();
  @Facebook("cost_per_unique_action_type")
  private List<AdsActionStats> costPerUniqueActionType = new ArrayList<>();
  @Facebook("cost_per_unique_click")
  private String costPerUniqueClick;
  @Facebook("cost_per_unique_conversion")
  private List<AdsActionStats> costPerUniqueConversion = new ArrayList<>();
  @Facebook("cost_per_unique_inline_link_click")
  private String costPerUniqueInlineLinkClick;
  @Facebook("cost_per_unique_outbound_click")
  private List<AdsActionStats> costPerUniqueOutboundClick = new ArrayList<>();
  @Facebook("cpc")
  private String cpc;
  @Facebook("cpm")
  private String cpm;
  @Facebook("cpp")
  private String cpp;
  @Facebook("created_time")
  private String createdTime;
  @Facebook("creative_media_type")
  private String creativeMediaType;
  @Facebook("ctr")
  private String ctr;
  @Facebook("date_start")
  private Date dateStart;
  @Facebook("date_stop")
  private Date dateStop;
  @Facebook("dda_countby_convs")
  private String ddaCountbyConvs;
  @Facebook("dda_results")
  private List<JsonObject> ddaResults = new ArrayList<>();
  @Facebook("engagement_rate_ranking")
  private String engagementRateRanking;
  @Facebook("estimated_ad_recall_rate")
  private String estimatedAdRecallRate;
  @Facebook("estimated_ad_recall_rate_lower_bound")
  private String estimatedAdRecallRateLowerBound;
  @Facebook("estimated_ad_recall_rate_upper_bound")
  private String estimatedAdRecallRateUpperBound;
  @Facebook("estimated_ad_recallers")
  private String estimatedAdRecallers;
  @Facebook("estimated_ad_recallers_lower_bound")
  private String estimatedAdRecallersLowerBound;
  @Facebook("estimated_ad_recallers_upper_bound")
  private String estimatedAdRecallersUpperBound;
  @Facebook("frequency")
  private String frequency;
  @Facebook("full_view_impressions")
  private String fullViewImpressions;
  @Facebook("full_view_reach")
  private String fullViewReach;
  @Facebook("gender_targeting")
  private String genderTargeting;
  @Facebook("impressions")
  private String impressions;
  @Facebook("inline_link_click_ctr")
  private String inlineLinkClickCtr;
  @Facebook("inline_link_clicks")
  private String inlineLinkClicks;
  @Facebook("inline_post_engagement")
  private String inlinePostEngagement;
  @Facebook("instagram_upcoming_event_reminders_set")
  private String instagramUpcomingEventRemindersSet;
  @Facebook("instant_experience_clicks_to_open")
  private String instantExperienceClicksToOpen;
  @Facebook("instant_experience_clicks_to_start")
  private String instantExperienceClicksToStart;
  @Facebook("instant_experience_outbound_clicks")
  private List<AdsActionStats> instantExperienceOutboundClicks = new ArrayList<>();
  @Facebook("interactive_component_tap")
  private List<AdsActionStats> interactiveComponentTap = new ArrayList<>();
  @Facebook("labels")
  private String labels;
  @Facebook("location")
  private String location;
  @Facebook("marketing_messages_cost_per_delivered")
  private String marketingMessagesCostPerDelivered;
  @Facebook("marketing_messages_cost_per_link_btn_click")
  private String marketingMessagesCostPerLinkBtnClick;
  @Facebook("marketing_messages_spend")
  private String marketingMessagesSpend;
  @Facebook("mobile_app_purchase_roas")
  private List<AdsActionStats> mobileAppPurchaseRoas = new ArrayList<>();
  @Facebook("objective")
  private String objective;
  @Facebook("optimization_goal")
  private String optimizationGoal;
  @Facebook("outbound_clicks")
  private List<AdsActionStats> outboundClicks = new ArrayList<>();
  @Facebook("outbound_clicks_ctr")
  private List<AdsActionStats> outboundClicksCtr = new ArrayList<>();
  @Facebook("place_page_name")
  private String placePageName;
  @Facebook("purchase_roas")
  private List<AdsActionStats> purchaseRoas = new ArrayList<>();
  @Facebook("qualifying_question_qualify_answer_rate")
  private String qualifyingQuestionQualifyAnswerRate;
  @Facebook("quality_ranking")
  private String qualityRanking;
  @Facebook("reach")
  private String reach;
  @Facebook("social_spend")
  private String socialSpend;
  @Facebook("spend")
  private String spend;
  @Facebook("total_postbacks")
  private String totalPostbacks;
  @Facebook("total_postbacks_detailed")
  private List<AdsActionStats> totalPostbacksDetailed = new ArrayList<>();
  @Facebook("total_postbacks_detailed_v4")
  private List<AdsActionStats> totalPostbacksDetailedV4 = new ArrayList<>();
  @Facebook("unique_actions")
  private List<AdsActionStats> uniqueActions = new ArrayList<>();
  @Facebook("unique_clicks")
  private String uniqueClicks;
  @Facebook("unique_conversions")
  private List<AdsActionStats> uniqueConversions = new ArrayList<>();
  @Facebook("unique_ctr")
  private String uniqueCtr;
  @Facebook("unique_inline_link_click_ctr")
  private String uniqueInlineLinkClickCtr;
  @Facebook("unique_inline_link_clicks")
  private String uniqueInlineLinkClicks;
  @Facebook("unique_link_clicks_ctr")
  private String uniqueLinkClicksCtr;
  @Facebook("unique_outbound_clicks")
  private List<AdsActionStats> uniqueOutboundClicks = new ArrayList<>();
  @Facebook("unique_outbound_clicks_ctr")
  private List<AdsActionStats> uniqueOutboundClicksCtr = new ArrayList<>();
  @Facebook("unique_video_continuous_2_sec_watched_actions")
  private List<AdsActionStats> uniqueVideoContinuous2SecWatchedActions = new ArrayList<>();
  @Facebook("unique_video_view_15_sec")
  private List<AdsActionStats> uniqueVideoView15Sec = new ArrayList<>();
  @Facebook("updated_time")
  private String updatedTime;
  @Facebook("video_15_sec_watched_actions")
  private List<AdsActionStats> video15SecWatchedActions = new ArrayList<>();
  @Facebook("video_30_sec_watched_actions")
  private List<AdsActionStats> video30SecWatchedActions = new ArrayList<>();
  @Facebook("video_avg_time_watched_actions")
  private List<AdsActionStats> videoAvgTimeWatchedActions = new ArrayList<>();
  @Facebook("video_continuous_2_sec_watched_actions")
  private List<AdsActionStats> videoContinuous2SecWatchedActions = new ArrayList<>();
  @Facebook("video_p100_watched_actions")
  private List<AdsActionStats> videoP100WatchedActions = new ArrayList<>();
  @Facebook("video_p25_watched_actions")
  private List<AdsActionStats> videoP25WatchedActions = new ArrayList<>();
  @Facebook("video_p50_watched_actions")
  private List<AdsActionStats> videoP50WatchedActions = new ArrayList<>();
  @Facebook("video_p75_watched_actions")
  private List<AdsActionStats> videoP75WatchedActions = new ArrayList<>();
  @Facebook("video_p95_watched_actions")
  private List<AdsActionStats> videoP95WatchedActions = new ArrayList<>();
  @Facebook("video_play_actions")
  private List<AdsActionStats> videoPlayActions = new ArrayList<>();
  @Facebook("video_play_curve_actions")
  private List<AdsHistogramStats> videoPlayCurveActions = new ArrayList<>();
  @Facebook("video_play_retention_0_to_15s_actions")
  private List<AdsHistogramStats> videoPlayRetention0To15sActions = new ArrayList<>();
  @Facebook("video_play_retention_20_to_60s_actions")
  private List<AdsHistogramStats> videoPlayRetention20To60sActions = new ArrayList<>();
  @Facebook("video_play_retention_graph_actions")
  private List<AdsHistogramStats> videoPlayRetentionGraphActions = new ArrayList<>();
  @Facebook("video_thruplay_watched_actions")
  private List<AdsActionStats> videoThruplayWatchedActions = new ArrayList<>();
  @Facebook("video_time_watched_actions")
  private List<AdsActionStats> videoTimeWatchedActions = new ArrayList<>();
  @Facebook("website_ctr")
  private List<AdsActionStats> websiteCtr = new ArrayList<>();
  @Facebook("website_purchase_roas")
  private List<AdsActionStats> websitePurchaseRoas = new ArrayList<>();
  @Facebook("wish_bid")
  private String wishBid;
  // Breakdowns
  @Facebook("ad_format_asset")
  private String adFormatAsset;
  @Facebook("age")
  private String age;
  @Facebook("app_id")
  private String appId;
  @Facebook("body_asset")
  private String bodyAsset;
  @Facebook("call_to_action_asset")
  private String callToActionAsset;
  @Facebook("coarse_conversion_value")
  private String coarseConversionValue;
  @Facebook("country")
  private String country;
  @Facebook("description_asset")
  private String descriptionAsset;
  @Facebook("device_platform")
  private String devicePlatform;
  @Facebook("dma")
  private String dma;
  @Facebook("fidelity_type")
  private String fidelityType;
  @Facebook("frequency_value")
  private String frequencyValue;
  @Facebook("gender")
  private String gender;
  @Facebook("hourly_stats_aggregated_by_advertiser_time_zone")
  private String hourlyStatsAggregatedByAdvertiserTimeZone;
  @Facebook("hourly_stats_aggregated_by_audience_time_zone")
  private String hourlyStatsAggregatedByAudienceTimeZone;
  @Facebook("hsid")
  private String hsid;
  @Facebook("image_asset")
  private String imageAsset;
  @Facebook("impression_device")
  private String impressionDevice;
  @Facebook("is_conversion_id_modeled")
  private String isConversionIdModeled;
  @Facebook("link_url_asset")
  private String linkUrlAsset;
  @Facebook("marketing_messages_btn_name")
  private String marketingMessagesBtnName;
  @Facebook("mdsa_landing_destination")
  private String mdsaLandingDestination;
  @Facebook("media_asset_url")
  private String mediaAssetUrl;
  @Facebook("media_creator")
  private String mediaCreator;
  @Facebook("media_destination_url")
  private String mediaDestinationUrl;
  @Facebook("media_format")
  private String mediaFormat;
  @Facebook("media_origin_url")
  private String mediaOriginUrl;
  @Facebook("media_text_content")
  private String mediaTextContent;
  @Facebook("mmm")
  private String mmm;
  @Facebook("place_page_id")
  private String placePageId;
  @Facebook("platform_position")
  private String platformPosition;
  @Facebook("postback_sequence_index")
  private String postbackSequenceIndex;
  @Facebook("product_id")
  private String productId;
  @Facebook("publisher_platform")
  private String publisherPlatform;
  @Facebook("redownload")
  private String redownload;
  @Facebook("region")
  private String region;
  @Facebook("skan_campaign_id")
  private String skanCampaignId;
  @Facebook("skan_conversion_id")
  private String skanConversionId;
  @Facebook("skan_version")
  private String skanVersion;
  @Facebook("standard_event_content_type")
  private String standardEventContentType;
  @Facebook("title_asset")
  private String titleAsset;
  @Facebook("video_asset")
  private String videoAsset;

  public List<AdsActionStats> getActionStats(String actionStatsField) {
    Supplier<List<AdsActionStats>> supplier = initAdActionsStatsListMap().get(actionStatsField);
    if (supplier != null) {
      return supplier.get();
    }

    return Collections.emptyList();
  }

  private Map<String, Supplier<List<AdsActionStats>>> initAdActionsStatsListMap() {
    Map<String, Supplier<List<AdsActionStats>>> map = new HashMap<>();
    map.put("action_values", this::getActionValues);
    map.put("actions", this::getActions);
    map.put("ad_click_actions", this::getAdClickActions);
    map.put("ad_impression_actions", this::getAdImpressionActions);
    map.put("catalog_segment_actions", this::getCatalogSegmentActions);
    map.put("catalog_segment_value", this::getCatalogSegmentValue);
    map.put("catalog_segment_value_mobile_purchase_roas", this::getCatalogSegmentValueMobilePurchaseRoas);
    map.put("catalog_segment_value_omni_purchase_roas", this::getCatalogSegmentValueOmniPurchaseRoas);
    map.put("catalog_segment_value_website_purchase_roas", this::getCatalogSegmentValueWebsitePurchaseRoas);
    map.put("conversion_values", this::getConversionValues);
    map.put("conversions", this::getConversions);
    map.put("converted_product_quantity", this::getConvertedProductQuantity);
    map.put("converted_product_value", this::getConvertedProductValue);
    map.put("cost_per_15_sec_video_view", this::getCostPer15SecVideoView);
    map.put("cost_per_2_sec_continuous_video_view", this::getCostPer2SecContinuousVideoView);
    map.put("cost_per_action_type", this::getCostPerActionType);
    map.put("cost_per_ad_click", this::getCostPerAdClick);
    map.put("cost_per_conversion", this::getCostPerConversion);
    map.put("cost_per_unique_conversion", this::getCostPerUniqueConversion);
    map.put("cost_per_one_thousand_ad_impression", this::getCostPerOneThousandAdImpression);
    map.put("cost_per_outbound_click", this::getCostPerOutboundClick);
    map.put("cost_per_thruplay", this::getCostPerThruplay);
    map.put("cost_per_unique_action_type", this::getCostPerUniqueActionType);
    map.put("cost_per_unique_outbound_click", this::getCostPerUniqueOutboundClick);
    map.put("instant_experience_outbound_clicks", this::getInstantExperienceOutboundClicks);
    map.put("interactive_component_tap", this::getInteractiveComponentTap);
    map.put("mobile_app_purchase_roas", this::getMobileAppPurchaseRoas);
    map.put("outbound_clicks", this::getOutboundClicks);
    map.put("outbound_clicks_ctr", this::getOutboundClicksCtr);
    map.put("purchase_roas", this::getPurchaseRoas);
    map.put("total_postbacks_detailed", this::getTotalPostbacksDetailed);
    map.put("total_postbacks_detailed_v4", this::getTotalPostbacksDetailedV4);
    map.put("unique_actions", this::getUniqueActions);
    map.put("unique_conversions", this::getUniqueConversions);
    map.put("unique_outbound_clicks", this::getUniqueOutboundClicks);
    map.put("unique_outbound_clicks_ctr", this::getUniqueOutboundClicksCtr);
    map.put("unique_video_continuous_2_sec_watched_actions", this::getUniqueVideoContinuous2SecWatchedActions);
    map.put("unique_video_view_15_sec", this::getUniqueVideoView15Sec);
    map.put("video_15_sec_watched_actions", this::getVideo15SecWatchedActions);
    map.put("video_30_sec_watched_actions", this::getVideo30SecWatchedActions);
    map.put("video_avg_time_watched_actions", this::getVideoAvgTimeWatchedActions);
    map.put("video_continuous_2_sec_watched_actions", this::getVideoContinuous2SecWatchedActions);
    map.put("video_p100_watched_actions", this::getVideoP100WatchedActions);
    map.put("video_p25_watched_actions", this::getVideoP25WatchedActions);
    map.put("video_p50_watched_actions", this::getVideoP50WatchedActions);
    map.put("video_p75_watched_actions", this::getVideoP75WatchedActions);
    map.put("video_p95_watched_actions", this::getVideoP95WatchedActions);
    map.put("video_play_actions", this::getVideoPlayActions);
    map.put("video_thruplay_watched_actions", this::getVideoThruplayWatchedActions);
    map.put("video_time_watched_actions", this::getVideoTimeWatchedActions);
    map.put("website_ctr", this::getWebsiteCtr);
    map.put("website_purchase_roas", this::getWebsitePurchaseRoas);
    return map;
  }
}
