/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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

import lombok.Getter;
import lombok.Setter;

public class TargetingResponseGeographic extends NamedAdsObject {

  @Getter
  @Setter
  @Facebook
  private String key;

  @Getter
  @Setter
  @Facebook
  private String type;

  @Getter
  @Setter
  @Facebook("address_string")
  private String addressString;

  @Getter
  @Setter
  @Facebook
  private String latitude;

  @Getter
  @Setter
  @Facebook
  private String longitude;

  @Getter
  @Setter
  @Facebook("country_code")
  private String countryCode;

  @Getter
  @Setter
  @Facebook("country_name")
  private String countryName;

  @Getter
  @Setter
  @Facebook
  private String region;

  @Getter
  @Setter
  @Facebook("region_id")
  private Long regionId;

  @Getter
  @Setter
  @Facebook("supports_region")
  private Boolean supportsRegion;

  @Getter
  @Setter
  @Facebook("supports_city")
  private Boolean supportsCity;

  @Getter
  @Setter
  @Facebook("primary_city")
  private String primaryCity;

  @Getter
  @Setter
  @Facebook("primary_city_id")
  private Long primaryCityId;
}
