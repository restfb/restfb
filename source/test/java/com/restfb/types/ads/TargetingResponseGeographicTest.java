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

public class TargetingResponseGeographicTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_6_city() {
    TargetingResponseGeographic geographic = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsegeographic_city"), TargetingResponseGeographic.class);
    assertNotNull(geographic);
    assertEquals("790255", geographic.getKey());
    assertEquals("Sarreguemines", geographic.getName());
    assertEquals("city", geographic.getType());
    assertEquals("FR", geographic.getCountryCode());
    assertEquals("France", geographic.getCountryName());
    assertEquals("Lorraine", geographic.getRegion());
    assertEquals(1081L, geographic.getRegionId().longValue());
    assertTrue(geographic.getSupportsRegion().booleanValue());
    assertTrue(geographic.getSupportsCity().booleanValue());
    assertNull(geographic.getPrimaryCity());
    assertNull(geographic.getPrimaryCityId());
  }

  @Test
  public void checkV2_6_region() {
    TargetingResponseGeographic geographic = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsegeographic_region"), TargetingResponseGeographic.class);
    assertNotNull(geographic);
    assertEquals("7", geographic.getKey());
    assertEquals("Escaldes-Engordany", geographic.getName());
    assertEquals("region", geographic.getType());
    assertEquals("AD", geographic.getCountryCode());
    assertEquals("Andorra", geographic.getCountryName());
    assertNull(geographic.getRegion());
    assertNull(geographic.getRegionId());
    assertTrue(geographic.getSupportsRegion().booleanValue());
    assertTrue(geographic.getSupportsCity().booleanValue());
    assertNull(geographic.getPrimaryCity());
    assertNull(geographic.getPrimaryCityId());
  }

  @Test
  public void checkV2_6_country() {
    TargetingResponseGeographic geographic = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsegeographic_country"), TargetingResponseGeographic.class);
    assertNotNull(geographic);
    assertEquals("AU", geographic.getKey());
    assertEquals("Australia", geographic.getName());
    assertEquals("country", geographic.getType());
    assertEquals("AU", geographic.getCountryCode());
    assertNull(geographic.getCountryName());
    assertNull(geographic.getRegion());
    assertNull(geographic.getRegionId());
    assertTrue(geographic.getSupportsRegion().booleanValue());
    assertTrue(geographic.getSupportsCity().booleanValue());
    assertNull(geographic.getPrimaryCity());
    assertNull(geographic.getPrimaryCityId());
  }

  @Test
  public void checkV2_6_zip() {
    TargetingResponseGeographic geographic = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsegeographic_zip"), TargetingResponseGeographic.class);
    assertNotNull(geographic);
    assertEquals("FR:66740", geographic.getKey());
    assertEquals("66740", geographic.getName());
    assertEquals("zip", geographic.getType());
    assertEquals("FR", geographic.getCountryCode());
    assertEquals("France", geographic.getCountryName());
    assertEquals("Languedoc-Roussillon", geographic.getRegion());
    assertEquals(1079L, geographic.getRegionId().longValue());
    assertTrue(geographic.getSupportsRegion().booleanValue());
    assertTrue(geographic.getSupportsCity().booleanValue());
    assertEquals("Villelongue-dels-Monts", geographic.getPrimaryCity());
    assertEquals(796803L, geographic.getPrimaryCityId().longValue());
  }

  @Test
  public void checkV2_6_market() {
    TargetingResponseGeographic geographic = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsegeographic_market"), TargetingResponseGeographic.class);
    assertNotNull(geographic);
    assertEquals("DMA:501", geographic.getKey());
    assertEquals("New York", geographic.getName());
    assertEquals("geo_market", geographic.getType());
    assertEquals("US", geographic.getCountryCode());
    assertNull(geographic.getCountryName());
    assertNull(geographic.getRegion());
    assertNull(geographic.getRegionId());
    assertTrue(geographic.getSupportsRegion().booleanValue());
    assertTrue(geographic.getSupportsCity().booleanValue());
    assertNull(geographic.getPrimaryCity());
    assertNull(geographic.getPrimaryCityId());
  }

  @Test
  public void checkV2_6_electoral_district() {
    TargetingResponseGeographic geographic = createJsonMapper().toJavaObject(
      jsonFromClasspath("ads/v2_6/responsegeographic_electoraldistrict"), TargetingResponseGeographic.class);
    assertNotNull(geographic);
    assertEquals("US:CA02", geographic.getKey());
    assertEquals("California's 2nd Congressional District", geographic.getName());
    assertEquals("electoral_district", geographic.getType());
    assertEquals("US", geographic.getCountryCode());
    assertNull(geographic.getCountryName());
    assertNull(geographic.getRegion());
    assertNull(geographic.getRegionId());
    assertTrue(geographic.getSupportsRegion().booleanValue());
    assertTrue(geographic.getSupportsCity().booleanValue());
    assertNull(geographic.getPrimaryCity());
    assertNull(geographic.getPrimaryCityId());
  }
}
