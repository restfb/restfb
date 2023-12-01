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

import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class TargetingGeoLocation extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook
  private List<TargetingGeoLocationCity> cities;

  @Getter
  @Setter
  @Facebook
  private List<String> countries;

  @Getter
  @Setter
  @Facebook("country_groups")
  private List<String> countryGroups;

  @Getter
  @Setter
  @Facebook("custom_locations")
  private List<TargetingGeoLocationCustomLocation> customLocations;

  @Getter
  @Setter
  @Facebook("electoral_districts")
  private List<TargetingGeoLocationElectoralDistrict> electoralDistricts;

  @Getter
  @Setter
  @Facebook("geo_markets")
  private List<TargetingGeoLocationMarket> geoMarkets;

  @Getter
  @Setter
  @Facebook("location_types")
  private List<String> locationTypes;

  @Getter
  @Setter
  @Facebook
  private List<TargetingGeoLocationPlace> places;

  @Getter
  @Setter
  @Facebook
  private List<TargetingGeoLocationRegion> regions;

  @Getter
  @Setter
  @Facebook
  private List<TargetingGeoLocationZip> zips;
}
