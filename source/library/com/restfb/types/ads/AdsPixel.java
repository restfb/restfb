/**
 * Copyright (c) 2010-2015 Mark Allen, Norbert Bartels.
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

import lombok.Getter;
import lombok.Setter;

public class AdsPixel extends NamedAdsObject {

  private static final long serialVersionUID = 1L;
  
  @Facebook
  @Getter
  @Setter
  private String code;

  @Facebook
  @Getter
  @Setter
  private String rulevalidation;

  @Facebook
  @Getter
  @Setter
  private String rules;

  @Facebook("creation_time")
  private String rawCreationTime;

  @Getter
  @Setter
  private Date creationTime;

  @Facebook("last_fired_time")
  private String rawLastFiredTime;

  @Getter
  @Setter
  private Date lastFiredTime;

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    creationTime = toDateFromLongFormat(rawCreationTime);
    lastFiredTime = toDateFromLongFormat(rawLastFiredTime);
  }
}
