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
import com.restfb.types.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Lead extends BaseAdsObject {

  @Getter
  @Setter
  @Facebook("ad_id")
  private String adId;

  @Getter
  @Setter
  @Facebook("ad_name")
  private String adName;

  @Getter
  @Setter
  @Facebook("adset_id")
  private String adsetId;

  @Getter
  @Setter
  @Facebook("adset_name")
  private String adsetName;

  @Getter
  @Setter
  @Facebook("campaign_id")
  private String campaignId;

  @Getter
  @Setter
  @Facebook("campaign_name")
  private String campaignName;

  @Facebook("created_time")
  private String rawCreatedTime;

  @Getter
  @Setter
  private Date createdTime;

  @Getter
  @Setter
  @Facebook("custom_disclaimer_responses")
  private List<String> customDisclaimerResponses = new ArrayList<String>();

  @Getter
  @Setter
  @Facebook("field_data")
  private List<FieldData> fieldData = new ArrayList<FieldData>();

  /**
   * The ID of the form.
   *
   * -- GETTER --
   *
   * @return The ID of the form.
   */
  @Getter
  @Setter
  @Facebook("form_id")
  private String formId;

  @Getter
  @Setter
  @Facebook("is_organic")
  private Boolean isOrganic;

  @Getter
  @Setter
  @Facebook
  private Post post;

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
  }
}
