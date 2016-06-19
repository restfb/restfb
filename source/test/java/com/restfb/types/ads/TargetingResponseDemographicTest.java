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

public class TargetingResponseDemographicTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_6_interest1() {
    TargetingResponseDemographic demographic = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsedemographic_interest1"), TargetingResponseDemographic.class);
    assertNotNull(demographic);
    assertEquals("6003283849579", demographic.getId());
    assertEquals("Cooking Light", demographic.getName());
    assertEquals(6975690L, demographic.getAudienceSize().longValue());
    assertEquals(0L, demographic.getPath().size());
    assertEquals("News and entertainment", demographic.getTopic());
    assertEquals("Magazine", demographic.getDisambiguationCategory());
    assertNull(demographic.getDescription());
  }

  @Test
  public void checkV2_6_interest2() {
    TargetingResponseDemographic demographic = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsedemographic_interest2"), TargetingResponseDemographic.class);
    assertNotNull(demographic);
    assertEquals("6003659420716", demographic.getId());
    assertEquals("Cooking", demographic.getName());
    assertEquals(522874160L, demographic.getAudienceSize().longValue());
    assertEquals(2, demographic.getPath().size());
    assertEquals("Food and drink", demographic.getTopic());
    assertNull(demographic.getDisambiguationCategory());
    assertEquals("", demographic.getDescription());
  }

  @Test
  public void checkV2_6_category1() {
    TargetingResponseDemographic demographic = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsedemographic_category1"), TargetingResponseDemographic.class);
    assertNotNull(demographic);
    assertEquals("6003424404140", demographic.getId());
    assertEquals("Marathons", demographic.getName());
    assertEquals(104636550L, demographic.getAudienceSize().longValue());
    assertEquals(3, demographic.getPath().size());
    assertNull(demographic.getTopic());
    assertNull(demographic.getDisambiguationCategory());
    assertNull(demographic.getDescription());
  }

  @Test
  public void checkV2_6_category2() {
    TargetingResponseDemographic demographic = createJsonMapper()
      .toJavaObject(jsonFromClasspath("ads/v2_6/responsedemographic_category2"), TargetingResponseDemographic.class);
    assertNotNull(demographic);
    assertEquals("6003512053894", demographic.getId());
    assertEquals("Snowboarding", demographic.getName());
    assertEquals(171864070L, demographic.getAudienceSize().longValue());
    assertEquals(3, demographic.getPath().size());
    assertNull(demographic.getTopic());
    assertNull(demographic.getDisambiguationCategory());
    assertNull(demographic.getDescription());
  }
}
