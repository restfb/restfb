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

import static org.junit.Assert.*;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

public class TargetingResponseGeographicMetadataTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_6() {
    TargetingResponseGeographicMetadata geographicMetadata = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsegeographic_meta"), TargetingResponseGeographicMetadata.class);
    assertNotNull(geographicMetadata);
    assertNotNull(geographicMetadata.getCities());
    assertEquals(2L, geographicMetadata.getCities().size());
    assertNotNull(geographicMetadata.getCountries());
    assertEquals(2L, geographicMetadata.getCountries().size());
    assertNotNull(geographicMetadata.getCustomLocations());
    assertEquals(0L, geographicMetadata.getCustomLocations().size());
    assertNotNull(geographicMetadata.getElectoralDistricts());
    assertEquals(1L, geographicMetadata.getElectoralDistricts().size());
    assertNotNull(geographicMetadata.getGeoMarkets());
    assertEquals(2L, geographicMetadata.getGeoMarkets().size());
    assertNotNull(geographicMetadata.getPlaces());
    assertEquals(1L, geographicMetadata.getPlaces().size());
    assertNotNull(geographicMetadata.getRegions());
    assertEquals(2L, geographicMetadata.getRegions().size());
    assertNotNull(geographicMetadata.getZips());
    assertEquals(2L, geographicMetadata.getZips().size());
  }

  @Test
  public void checkV2_6_cities() {
    TargetingResponseGeographicMetadata geographicMetadata = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsegeographic_meta"), TargetingResponseGeographicMetadata.class);
    assertNotNull(geographicMetadata);
    assertNotNull(geographicMetadata.getCities());
    assertEquals(2L, geographicMetadata.getCities().size());
    TargetingResponseGeographic city = geographicMetadata.getCities().get("2418779");
    assertEquals("2418779", city.getKey());
    assertEquals("Danville", city.getName());
    assertEquals("city", city.getType());
    assertEquals("US", city.getCountryCode());
    assertEquals("United States", city.getCountryName());
    assertEquals("California", city.getRegion());
    assertEquals(3847L, city.getRegionId().longValue());
    assertTrue(city.getSupportsRegion().booleanValue());
    assertTrue(city.getSupportsCity().booleanValue());
    assertNull(city.getPrimaryCity());
    assertNull(city.getPrimaryCityId());
  }

  @Test
  public void checkV2_6_zips() {
    TargetingResponseGeographicMetadata geographicMetadata = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsegeographic_meta"), TargetingResponseGeographicMetadata.class);
    assertNotNull(geographicMetadata);
    assertNotNull(geographicMetadata.getZips());
    assertEquals(2L, geographicMetadata.getZips().size());
    TargetingResponseGeographic city = geographicMetadata.getZips().get("US:90210");
    assertEquals("US:90210", city.getKey());
    assertEquals("90210", city.getName());
    assertEquals("zip", city.getType());
    assertEquals("US", city.getCountryCode());
    assertEquals("United States", city.getCountryName());
    assertEquals("California", city.getRegion());
    assertEquals(3847L, city.getRegionId().longValue());
    assertTrue(city.getSupportsRegion().booleanValue());
    assertTrue(city.getSupportsCity().booleanValue());
    assertEquals("Beverly Hills", city.getPrimaryCity());
    assertEquals(2417987L, city.getPrimaryCityId().longValue());
  }

  @Test
  public void checkV2_6_places() {
    TargetingResponseGeographicMetadata geographicMetadata = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsegeographic_meta"), TargetingResponseGeographicMetadata.class);
    assertNotNull(geographicMetadata);
    assertNotNull(geographicMetadata.getPlaces());
    assertEquals(1L, geographicMetadata.getPlaces().size());
    TargetingResponseGeographic city = geographicMetadata.getPlaces().get("111745868854244");
    assertEquals("111745868854244", city.getKey());
    assertEquals("Four Barrel Coffee", city.getName());
    assertEquals("place", city.getType());
    assertEquals("US", city.getCountryCode());
    assertEquals("United States", city.getCountryName());
    assertEquals("California", city.getRegion());
    assertEquals(3847L, city.getRegionId().longValue());
    assertNull(city.getSupportsRegion());
    assertNull(city.getSupportsCity());
    assertEquals("San Francisco", city.getPrimaryCity());
    assertEquals(2421836L, city.getPrimaryCityId().longValue());
    assertEquals("37.767010", city.getLatitude());
    assertEquals("-122.421770", city.getLongitude());
    assertEquals("375 Valencia St, San Francisco, California 94103", city.getAddressString());
  }

  @Test
  public void checkV2_6_countries() {
    TargetingResponseGeographicMetadata geographicMetadata = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsegeographic_meta"), TargetingResponseGeographicMetadata.class);
    assertNotNull(geographicMetadata);
    assertNotNull(geographicMetadata.getCountries());
    assertEquals(2L, geographicMetadata.getCountries().size());
    TargetingResponseGeographic city = geographicMetadata.getCountries().get("JP");
    assertEquals("JP", city.getKey());
    assertEquals("Japan", city.getName());
    assertEquals("country", city.getType());
    assertNull(city.getCountryCode());
    assertNull(city.getCountryName());
    assertNull(city.getRegion());
    assertNull(city.getRegionId());
    assertTrue(city.getSupportsRegion().booleanValue());
    assertTrue(city.getSupportsCity().booleanValue());
    assertNull(city.getPrimaryCity());
    assertNull(city.getPrimaryCityId());
  }
}
