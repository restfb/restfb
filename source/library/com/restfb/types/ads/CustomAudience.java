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

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class CustomAudience extends NamedAdsObject {

  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId = null;

  @Getter
  @Setter
  @Facebook("approximate_count")
  private Long approximateCount = null;

  @Getter
  @Setter
  @Facebook("data_source")
  private CustomAudienceDataSource dataSource = null;

  @Getter
  @Setter
  @Facebook("delivery_status")
  private CustomAudienceStatus deliveryStatus = null;

  @Getter
  @Setter
  @Facebook
  private String description = null;

  @Getter
  @Setter
  @Facebook("excluded_custom_audiences")
  private List<CustomAudience> excludedCustomAudiences = null;

  @Getter
  @Setter
  @Facebook("external_event_source")
  private AdsPixel externalEventSource = null;

  @Getter
  @Setter
  @Facebook("included_custom_audiences")
  private List<CustomAudience> includedCustomAudiences = null;

  @Getter
  @Setter
  @Facebook("last_used_time")
  private String lastUsedTime = null;

  @Getter
  @Setter
  @Facebook("lookalike_audience_ids")
  private List<String> lookalikeAudienceIds = null;

  @Getter
  @Setter
  @Facebook("lookalike_spec")
  private LookalikeSpec lookalikeSpec = null;

  @Getter
  @Setter
  @Facebook("operation_status")
  private CustomAudienceStatus operationStatus = null;

  @Getter
  @Setter
  @Facebook("opt_out_link")
  private String optOutLink = null;

  @Getter
  @Setter
  @Facebook("owner_business")
  private Business ownerBusiness = null;

  @Getter
  @Setter
  @Facebook("permission_for_actions")
  private CustomAudiencePermission permissionForActions = null;

  @Getter
  @Setter
  @Facebook("pixel_id")
  private String pixelId = null;

  @Getter
  @Setter
  @Facebook("retention_days")
  private Long retentionDays = null;

  @Getter
  @Setter
  @Facebook
  private String rule = null;

  @Getter
  @Setter
  @Facebook
  private String subtype = null;

  @Getter
  @Setter
  @Facebook("time_content_updated")
  private Long timeContentUpdated = null;

  @Getter
  @Setter
  @Facebook("time_created")
  private Long timeCreated = null;

  @Getter
  @Setter
  @Facebook("time_updated")
  private Long timeUpdated = null;
}
