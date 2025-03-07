/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class AdTest extends AbstractJsonMapperTests {

  @Test
  void test() {
    Ad adObj = createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/ad"), Ad.class);
    assertNotNull(adObj);
    assertEquals("123123", adObj.getId());
    assertEquals("123123", adObj.getAccountId());
    assertEquals("123123", adObj.getAdsetId());
    assertEquals("ACTIVE", adObj.getConfiguredStatus());
    assertEquals("ABSOLUTE_OCPM", adObj.getBidType());
    assertEquals("ACTIVE", adObj.getEffectiveStatus());
    assertEquals("123123", adObj.getCampaignId());
    assertEquals(1465379762000L, adObj.getUpdatedTime().getTime());
    assertEquals(1465379757000L, adObj.getCreatedTime().getTime());
    assertEquals("123123", adObj.getCreative().getId());
    assertEquals("123123", adObj.getAdset().getId());
    assertEquals("123123", adObj.getCampaign().getId());

    List<ConversionActionQuery> trackingSpecs = adObj.getTrackingSpecs();
    assertEquals(1, trackingSpecs.size());
    ConversionActionQuery trackingSpec = trackingSpecs.get(0);
    assertEquals("page_engagement", trackingSpec.getActionType().get(0));
    assertEquals("123123", trackingSpec.getPage().get(0));

    List<ConversionActionQuery> conversionSpecs = adObj.getConversionSpecs();
    assertEquals(1, conversionSpecs.size());
    ConversionActionQuery conversionSpec = conversionSpecs.get(0);
    assertEquals("like", conversionSpec.getActionType().get(0));
    assertEquals("123123", conversionSpec.getPage().get(0));
  }
}
