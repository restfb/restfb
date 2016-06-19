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
import com.restfb.JsonMapper;
import com.restfb.json.JsonObject;
import com.restfb.types.AbstractFacebookType;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class TargetingResponseGeographicMetadata extends AbstractFacebookType {

  @Facebook
  private JsonObject data;

  private MetaData metaData;

  public Map<String, TargetingResponseGeographic> getCountries() {
    return metaData.getCountries();
  }

  public Map<String, TargetingResponseGeographic> getRegions() {
    return metaData.getRegions();
  }

  public Map<String, TargetingResponseGeographic> getCities() {
    return metaData.getCities();
  }

  public Map<String, TargetingResponseGeographic> getZips() {
    return metaData.getZips();
  }

  public Map<String, TargetingResponseGeographic> getPlaces() {
    return metaData.getPlaces();
  }

  public Map<String, TargetingResponseGeographic> getCustomLocations() {
    return metaData.getCustomLocations();
  }

  public Map<String, TargetingResponseGeographic> getGeoMarkets() {
    return metaData.getGeoMarkets();
  }

  public Map<String, TargetingResponseGeographic> getElectoralDistricts() {
    return metaData.getElectoralDistricts();
  }

  @JsonMapper.JsonMappingCompleted
  void convertData(JsonMapper mapper) {
    metaData = mapper.toJavaObject(data.toString(), MetaData.class);
  }

  private static class MetaData {

    @Getter
    @Facebook
    private Map<String, TargetingResponseGeographic> countries = new HashMap<String, TargetingResponseGeographic>();

    @Getter
    @Facebook
    private Map<String, TargetingResponseGeographic> regions = new HashMap<String, TargetingResponseGeographic>();

    @Getter
    @Facebook
    private Map<String, TargetingResponseGeographic> cities = new HashMap<String, TargetingResponseGeographic>();

    @Getter
    @Facebook
    private Map<String, TargetingResponseGeographic> zips = new HashMap<String, TargetingResponseGeographic>();

    @Getter
    @Facebook
    private Map<String, TargetingResponseGeographic> places = new HashMap<String, TargetingResponseGeographic>();

    @Getter
    @Facebook("custom_locations")
    private Map<String, TargetingResponseGeographic> customLocations =
        new HashMap<String, TargetingResponseGeographic>();

    @Getter
    @Facebook("geo_markets")
    private Map<String, TargetingResponseGeographic> geoMarkets = new HashMap<String, TargetingResponseGeographic>();

    @Getter
    @Facebook("electoral_districts")
    private Map<String, TargetingResponseGeographic> electoralDistricts =
        new HashMap<String, TargetingResponseGeographic>();
  }
}
