/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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
import com.restfb.types.User;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class AdStudy extends NamedAdsObject {

  @Getter
  @Setter
  @Facebook
  private Business business;

  @Facebook("canceled_time")
  private String rawCanceledTime;

  @Getter
  @Setter
  private Date canceledTime;

  @Getter
  @Setter
  @Facebook("created_by")
  private User createdBy;

  @Facebook("created_time")
  private String rawCreatedTime;

  @Getter
  @Setter
  private Date createdTime;

  @Getter
  @Setter
  @Facebook
  private String description;

  @Facebook("end_time")
  private String rawEndTime;

  @Getter
  @Setter
  private Date endTime;

  @Facebook("cooldown_start_time")
  private String rawCooldownStartTime;

  @Getter
  @Setter
  private Date cooldownStartTime;

  @Getter
  @Setter
  @Facebook
  private String type;

  @Facebook("observation_end_time")
  private String rawObservationEndTime;

  @Getter
  @Setter
  private Date observationEndTime;

  @Facebook("start_time")
  private String rawStartTime;

  @Getter
  @Setter
  private Date startTime;

  @Getter
  @Setter
  @Facebook("updated_by")
  private User updatedBy;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  @Getter
  @Setter
  private Date updatedTime;

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    startTime = toDateFromLongFormat(rawStartTime);
    endTime = toDateFromLongFormat(rawEndTime);
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
    observationEndTime = toDateFromLongFormat(rawObservationEndTime);
    cooldownStartTime = toDateFromLongFormat(rawCooldownStartTime);
  }

}
