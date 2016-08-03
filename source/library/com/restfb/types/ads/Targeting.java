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

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Targeting extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook("adgroup_id")
  private String adgroupId;

  @Getter
  @Setter
  @Facebook("age_max")
  private Long ageMax;

  @Getter
  @Setter
  @Facebook("age_min")
  private Long ageMin;

  @Getter
  @Setter
  @Facebook("app_install_state")
  private String appInstallState;

  @Getter
  @Setter
  @Facebook("audience_network_positions")
  private List<String> audienceNetworkPositions;

  @Getter
  @Setter
  @Facebook
  private List<IDName> behaviors;

  @Getter
  @Setter
  @Facebook
  private List<IDName> cities;

  @Getter
  @Setter
  @Facebook("college_years")
  private List<Long> collegeYears;

  @Getter
  @Setter
  @Facebook
  private List<IDName> connections;

  @Getter
  @Setter
  @Facebook
  private List<String> countries;

  @Getter
  @Setter
  @Facebook
  private List<String> country;

  @Getter
  @Setter
  @Facebook("country_groups")
  private List<String> countryGroups;

  @Getter
  @Setter
  @Facebook("custom_audiences")
  private List<IDName> customAudiences;

  @Getter
  @Setter
  @Facebook("device_platforms")
  private List<String> devicePlatforms;

  @Getter
  @Setter
  @Facebook("dynamic_audience_ids")
  private List<String> dynamicAudienceIds;

  @Getter
  @Setter
  @Facebook("education_majors")
  private List<IDName> educationMajors;

  @Getter
  @Setter
  @Facebook("education_schools")
  private List<IDName> educationSchools;

  @Getter
  @Setter
  @Facebook("education_statuses")
  private List<Long> educationStatuses;

  @Getter
  @Setter
  @Facebook("effective_device_platforms")
  private List<String> effectiveDevicePlatforms;

  @Getter
  @Setter
  @Facebook("effective_facebook_positions")
  private List<String> effectiveFacebookPositions;

  @Getter
  @Setter
  @Facebook("engagement_specs")
  private List<TargetingDynamicRule> engagementSpecs;

  @Getter
  @Setter
  @Facebook("ethnic_affinity")
  private List<IDName> ethnicAffinity;

  @Getter
  @Setter
  @Facebook("exclude_reached_since")
  private List<String> excludeReachedSince;

  @Getter
  @Setter
  @Facebook("excluded_connections")
  private List<IDName> excludedConnections;

  @Getter
  @Setter
  @Facebook("excluded_custom_audiences")
  private List<IDName> excludedCustomAudiences;

  @Getter
  @Setter
  @Facebook("excluded_dynamic_audience_ids")
  private List<String> excludedDynamicAudienceIds;

  @Getter
  @Setter
  @Facebook("excluded_engagement_specs")
  private List<TargetingDynamicRule> excludedEngagementSpecs;

  @Getter
  @Setter
  @Facebook("excluded_geo_locations")
  private TargetingGeoLocation excludedGeoLocations;

  @Getter
  @Setter
  @Facebook("excluded_product_audience_specs")
  private List<TargetingProductAudienceSpec> excludedProductAudienceSpecs;

  @Getter
  @Setter
  @Facebook("excluded_publisher_categories")
  private List<String> excludedPublisherCategories;

  @Getter
  @Setter
  @Facebook("excluded_publisher_list_ids")
  private List<String> excludedPublisherListIds;

  @Getter
  @Setter
  @Facebook("exclusions")
  private FlexibleTargeting exclusions;

  @Getter
  @Setter
  @Facebook("facebook_positions")
  private List<String> facebookPositions;

  @Getter
  @Setter
  @Facebook("family_statuses")
  private List<IDName> familyStatuses;

  @Getter
  @Setter
  @Facebook("fb_deal_id")
  private Long fbDealId;

  @Getter
  @Setter
  @Facebook("flexible_spec")
  private List<FlexibleTargeting> flexibleSpec;

  @Getter
  @Setter
  @Facebook("friends_of_connections")
  private List<IDName> friendsOfConnections;

  @Getter
  @Setter
  @Facebook
  private List<Long> genders;

  @Getter
  @Setter
  @Facebook
  private List<IDName> generation;

  @Getter
  @Setter
  @Facebook("geo_locations")
  private TargetingGeoLocation geoLocations;

  @Getter
  @Setter
  @Facebook("home_ownership")
  private List<IDName> homeOwnership;

  @Getter
  @Setter
  @Facebook("home_type")
  private List<IDName> homeType;

  @Getter
  @Setter
  @Facebook("home_value")
  private List<IDName> homeValue;

  @Getter
  @Setter
  @Facebook("household_composition")
  private List<IDName> householdComposition;

  @Getter
  @Setter
  @Facebook
  private List<IDName> income;

  @Getter
  @Setter
  @Facebook
  private List<IDName> industries;

  @Getter
  @Setter
  @Facebook("interested_in")
  private List<Long> interestedIn;

  @Getter
  @Setter
  @Facebook
  private List<IDName> interests;

  @Getter
  @Setter
  @Facebook
  private List<String> keywords;

  @Getter
  @Setter
  @Facebook("life_events")
  private List<IDName> lifeEvents;

  @Getter
  @Setter
  @Facebook
  private List<Long> locales;

  @Getter
  @Setter
  @Facebook
  private List<IDName> moms;

  @Getter
  @Setter
  @Facebook("net_worth")
  private List<IDName> netWorth;

  @Getter
  @Setter
  @Facebook("office_type")
  private List<IDName> officeType;

  /**
   * @RestFB.GraphApi.Until 2.7
   */
  @Getter
  @Setter
  @Facebook("page_types")
  private List<String> pageTypes;

  @Getter
  @Setter
  @Facebook
  private List<String> platforms;

  @Getter
  @Setter
  @Facebook("political_views")
  private List<Long> politicalViews;

  @Getter
  @Setter
  @Facebook
  private List<IDName> politics;

  @Getter
  @Setter
  @Facebook("product_audience_specs")
  private List<TargetingProductAudienceSpec> productAudienceSpecs = new ArrayList<TargetingProductAudienceSpec>();

  @Getter
  @Setter
  @Facebook("publisher_platforms")
  private List<String> publisherPlatforms;

  @Getter
  @Setter
  @Facebook
  private String radius;

  @Getter
  @Setter
  @Facebook
  private List<IDName> regions;

  @Getter
  @Setter
  @Facebook("relationship_statuses")
  private List<Long> relationshipStatuses;

  @Getter
  @Setter
  @Facebook("rtb_flag")
  private Boolean rtbFlag;

  @Getter
  @Setter
  @Facebook("site_category")
  private List<String> siteCategory;

  @Getter
  @Setter
  @Facebook("targeting_optimization")
  private String targetingOptimization;

  @Getter
  @Setter
  @Facebook("user_adclusters")
  private List<IDName> userAdclusters;

  @Getter
  @Setter
  @Facebook("user_device")
  private List<String> userDevice;

  @Getter
  @Setter
  @Facebook("user_event")
  private List<Long> userEvent;

  @Getter
  @Setter
  @Facebook("user_os")
  private List<String> userOs;

  @Getter
  @Setter
  @Facebook("wireless_carrier")
  private List<String> wirelessCarrier;

  @Getter
  @Setter
  @Facebook("work_employers")
  private List<IDName> workEmployers;

  @Getter
  @Setter
  @Facebook("work_positions")
  private List<IDName> workPositions;

  @Getter
  @Setter
  @Facebook
  private List<String> zips;
}
