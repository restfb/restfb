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

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/custom-audience">Custom
 * Audience</a> Marketing API type
 */
public class CustomAudience extends NamedAdsObject {

  /**
   * Ad Account ID
   *
   * -- GETTER --
   *
   * @return Ad Account ID
   */
  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  /**
   * Approximate number of people in this audience
   *
   * -- GETTER --
   *
   * @return Approximate number of people in this audience
   */
  @Getter
  @Setter
  @Facebook("approximate_count")
  private Long approximateCount;

  /**
   * JSON dictionary of type, sub_type to indicate by which method the custom audience was created.
   *
   * -- GETTER --
   *
   * @return JSON dictionary of type, sub_type to indicate by which method the custom audience was created.
   */
  @Getter
  @Setter
  @Facebook("data_source")
  private CustomAudienceDataSource dataSource;

  /**
   * JSON dictionary of code and description.
   *
   * Indicates whether or not an audience can be used in ads. There are two situations that an audience will make ads
   * not deliverable. First, if the size is smaller than 20 people, the audience can't be delivered. Second, if for some
   * reason the audience is disabled (such as violation of policy, expired), validation will fail when it is used in
   * ads.
   *
   * -- GETTER --
   *
   * @return dictionary of code and description
   */
  @Getter
  @Setter
  @Facebook("delivery_status")
  private CustomAudienceStatus deliveryStatus;

  /**
   * Custom audience description
   *
   * -- GETTER --
   *
   * @return Custom audience description
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  @Getter
  @Setter
  @Facebook("excluded_custom_audiences")
  private List<CustomAudience> excludedCustomAudiences = new ArrayList<CustomAudience>();

  /**
   * Read-only JSON dictionary with key id containing the pixel id whose traffic generated this custom audience
   *
   * -- GETTER --
   *
   * @return Read-only JSON dictionary with key id containing the pixel id whose traffic generated this custom audience
   */
  @Getter
  @Setter
  @Facebook("external_event_source")
  private AdsPixel externalEventSource;

  @Getter
  @Setter
  @Facebook("included_custom_audiences")
  private List<CustomAudience> includedCustomAudiences = new ArrayList<CustomAudience>();

  /**
   * last used time of this object by the current viewer
   *
   * -- GETTER --
   *
   * @return last used time of this object by the current viewer
   */
  @Getter
  @Setter
  @Facebook("last_used_time")
  private String lastUsedTime;

  /**
   * The IDs of the lookalike audiences generated from this audience
   *
   * -- GETTER --
   *
   * @return The IDs of the lookalike audiences generated from this audience
   */
  @Getter
  @Setter
  @Facebook("lookalike_audience_ids")
  private List<String> lookalikeAudienceIds = new ArrayList<String>();

  /**
   * Generated only when the subtype is LOOKALIKE.
   *
   * -- GETTER --
   *
   * @return Generated only when the subtype is LOOKALIKE.
   */
  @Getter
  @Setter
  @Facebook("lookalike_spec")
  private LookalikeSpec lookalikeSpec;

  /**
   * JSON dictionary of code to int value and description to a description string.
   *
   * The operation status represents the status of the last operation performed on an audience. In general, it will have
   * following states:
   * <ul>
   * <li>0: Status not available</li>
   * <li>200: Normal: there is no updating or issues found</li>
   * <li>400: Warning: there is some message we would like advertisers to know</li>
   * <li>410: No upload: no file has been uploaded</li>
   * <li>411: Low match rate: low rate of matched people</li>
   * <li>412: High invalid rate: high rate of invalid people</li>
   * <li>421: No pixel: Your Facebook pixel hasn't been installed on your website yet</li>
   * <li>422: Pixel not firing: Your Facebook pixel isn't firing</li>
   * <li>423: Invalid pixel: Your Facebook pixel is invalid</li>
   * <li>431: Lookalike Audience refresh failed</li>
   * <li>432: Lookalike Audience build failed</li>
   * <li>433: Lookalike Audience build failed</li>
   * <li>434: Lookalike Audience build retrying</li>
   * <li>500: Error: there is some error and advertisers need to take action items to fix the error</li>
   * </ul>
   *
   * -- GETTER --
   *
   * @return dictionary of code to int value and description to a description string
   */
  @Getter
  @Setter
  @Facebook("operation_status")
  private CustomAudienceStatus operationStatus;

  /**
   * Your opt-out URL so people can choose not to be targeted
   *
   * -- GETTER --
   *
   * @return Your opt-out URL so people can choose not to be targeted
   */
  @Getter
  @Setter
  @Facebook("opt_out_link")
  private String optOutLink;

  /**
   * The ID of origin Custom Audience. The origin audience you create must have a minimum size of 100.
   *
   * -- GETTER --
   *
   * @return The ID of origin Custom Audience. The origin audience you create must have a minimum size of 100.
   */
  @Getter
  @Setter
  @Facebook("origin_audience_id")
  private Long originAudienceId;

  /**
   * owner business of this object
   *
   * -- GETTER --
   *
   * @return owner business of this object
   */
  @Getter
  @Setter
  @Facebook("owner_business")
  private Business ownerBusiness;

  /**
   * JSON dictionary of permissions (string) to boolean value if the custom audience has that permission
   *
   * -- GETTER --
   *
   * @return JSON dictionary of permissions (string) to boolean value if the custom audience has that permission
   */
  @Getter
  @Setter
  @Facebook("permission_for_actions")
  private CustomAudiencePermission permissionForActions;

  /**
   * ID of the pixel which is collecting events for this Website Custom audience
   *
   * -- GETTER --
   *
   * @return ID of the pixel which is collecting events for this Website Custom audience
   */
  @Getter
  @Setter
  @Facebook("pixel_id")
  private String pixelId;

  /**
   * Number of days to keep the user in this cluster. You can use any value between 1 and 180 days. Defaults to 14 days
   * if not specified
   *
   * -- GETTER --
   *
   * @return Number of days to keep the user in this cluster.
   */
  @Getter
  @Setter
  @Facebook("retention_days")
  private Long retentionDays;

  /**
   * Audience rules to be applied on the referrer URL
   *
   * -- GETTER --
   *
   * @return Audience rules to be applied on the referrer URL
   */
  @Getter
  @Setter
  @Facebook
  private String rule;

  /**
   * Type of custom audience, derived from original data source
   *
   * -- GETTER --
   *
   * @return Type of custom audience, derived from original data source
   */
  @Getter
  @Setter
  @Facebook
  private String subtype;

  /**
   * Last update of people in this custom audience
   *
   * -- GETTER --
   *
   * @return Last update of people in this custom audience
   */
  @Getter
  @Setter
  @Facebook("time_content_updated")
  private Long timeContentUpdated;

  /**
   * Creation time
   *
   * -- GETTER --
   *
   * @return Creation time
   */
  @Getter
  @Setter
  @Facebook("time_created")
  private Long timeCreated;

  /**
   * Last time this audience metadata was updated
   *
   * -- GETTER --
   *
   * @return Last time this audience metadata was updated
   */
  @Getter
  @Setter
  @Facebook("time_updated")
  private Long timeUpdated;
}
