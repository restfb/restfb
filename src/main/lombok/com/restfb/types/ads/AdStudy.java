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

import java.util.Date;

import com.restfb.Facebook;
import com.restfb.types.User;
import com.restfb.types.features.HasCreatedTime;

import lombok.Getter;
import lombok.Setter;

public class AdStudy extends NamedAdsObject implements HasCreatedTime {

  @Getter
  @Setter
  @Facebook
  private Business business;

  @Getter
  @Setter
  @Facebook("canceled_time")
  private Date canceledTime;

  @Getter
  @Setter
  @Facebook("created_by")
  private User createdBy;

  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook("created_time")
  private Date createdTime;

  @Getter
  @Setter
  @Facebook
  private String description;

  @Getter
  @Setter
  @Facebook("end_time")
  private Date endTime;

  @Getter
  @Setter
  @Facebook("cooldown_start_time")
  private Date cooldownStartTime;

  @Getter
  @Setter
  @Facebook
  private String type;

  @Getter
  @Setter
  @Facebook("observation_end_time")
  private Date observationEndTime;

  @Getter
  @Setter
  @Facebook("start_time")
  private Date startTime;

  @Getter
  @Setter
  @Facebook("updated_by")
  private User updatedBy;

  @Getter
  @Setter
  @Facebook("updated_time")
  private Date updatedTime;

}
