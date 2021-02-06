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
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class TargetingGeoLocationCustomLocation extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook("address_string")
  private String addressString;

  @Getter
  @Setter
  @Facebook
  private Double latitude;

  @Getter
  @Setter
  @Facebook
  private Double longitude;

  @Getter
  @Setter
  @Facebook("primary_city_id")
  private Long primaryCityId;

  @Getter
  @Setter
  @Facebook("max_population")
  private Long maxPopulation;

  @Getter
  @Setter
  @Facebook("min_population")
  private Long minPopulation;

  @Getter
  @Setter
  @Facebook
  private Double radius;

  @Getter
  @Setter
  @Facebook("region_id")
  private Long regionId;
}
