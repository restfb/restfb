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

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Campaign extends NamedAdsObject {

  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  @Getter
  @Setter
  @Facebook("adlabels")
  private List<AdLabel> adlabels;

  @Getter
  @Setter
  @Facebook("buying_type")
  private String buyingType;

  @Getter
  @Setter
  @Facebook("can_use_spend_cap")
  private Boolean canUseSpendCap;

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
  @Facebook("effective_status")
  private String effectiveStatus;

  @Getter
  @Setter
  @Facebook
  private String objective;

  @Getter
  @Setter
  @Facebook
  private List<AdRecommendation> recommendations;

  @Getter
  @Setter
  @Facebook("spend_cap")
  private String spendCap;

  @Getter
  @Setter
  private Date startTime;

  @Facebook("start_time")
  private String rawStartTime;

  @Getter
  @Setter
  @Facebook
  private String status;

  @Getter
  @Setter
  private Date stopTime;

  @Facebook("stop_time")
  private String rawStopTime;

  @Getter
  @Setter
  private Date updatedTime;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    startTime = toDateFromLongFormat(rawStartTime);
    stopTime = toDateFromLongFormat(rawStopTime);
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }
}
