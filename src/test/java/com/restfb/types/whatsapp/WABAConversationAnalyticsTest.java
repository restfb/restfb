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
package com.restfb.types.whatsapp;

import static com.restfb.testutils.AssertJson.assertEquals;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class WABAConversationAnalyticsTest extends AbstractJsonMapperTests {

  @Test
  void simpleTest() {
    WABAConversationAnalytics conversationAnalytics = new WABAConversationAnalytics();
    assertEquals("{}", createJsonMapper().toJson(conversationAnalytics, true));
  }

  @Test
  void withConversionDirection() {
    WABAConversationAnalytics conversationAnalytics = new WABAConversationAnalytics();
    conversationAnalytics.addConversationDirection(WABAConversationDirection.USER_INITIATED);
    assertEquals("{\"conversation_directions\":[\"USER_INITIATED\"]}",
      createJsonMapper().toJson(conversationAnalytics, true));
  }

  @Test
  void withConversionTypes() {
    WABAConversationAnalytics conversationAnalytics = new WABAConversationAnalytics();
    conversationAnalytics.addConversationType(WABAConversationType.FREE_TIER);
    assertEquals("{\"conversation_types\":[\"FREE_TIER\"]}", createJsonMapper().toJson(conversationAnalytics, true));
  }

  @Test
  void withCountryCode() {
    WABAConversationAnalytics conversationAnalytics = new WABAConversationAnalytics();
    conversationAnalytics.addCountryCode("de");
    assertEquals("{\"country_codes\":[\"de\"]}", createJsonMapper().toJson(conversationAnalytics, true));
  }

  @Test
  void withDimensions() {
    WABAConversationAnalytics conversationAnalytics = new WABAConversationAnalytics();
    conversationAnalytics.addDimension(WABADimension.COUNTRY);
    assertEquals("{\"dimensions\":[\"COUNTRY\"]}", createJsonMapper().toJson(conversationAnalytics, true));
  }

  @Test
  void withMetricTypes() {
    WABAConversationAnalytics conversationAnalytics = new WABAConversationAnalytics();
    conversationAnalytics.addMetricType(WABAMetricType.CONVERSATION);
    assertEquals("{\"metric_types\":[\"CONVERSATION\"]}", createJsonMapper().toJson(conversationAnalytics, true));
  }

  @Test
  void full() {
    WABAConversationAnalytics conversationAnalytics = new WABAConversationAnalytics();
    conversationAnalytics.addMetricType(WABAMetricType.CONVERSATION);
    conversationAnalytics.addConversationDirection(WABAConversationDirection.BUSINESS_INITIATED);
    conversationAnalytics.addConversationType(WABAConversationType.REGULAR);
    conversationAnalytics.addDimension(WABADimension.COUNTRY);
    conversationAnalytics.addCountryCode("de");
    conversationAnalytics.addCountryCode("en");
    conversationAnalytics.addPhoneNumber("12345");
    conversationAnalytics.setGranularity(WABAGranularity.DAILY);
    String expectedObj = "{\"conversation_directions\":[\"BUSINESS_INITIATED\"]," //
        + "\"conversation_types\":[\"REGULAR\"]," //
        + "\"country_codes\":[\"de\",\"en\"]," //
        + "\"dimensions\":[\"COUNTRY\"]," //
        + "\"granularity\":\"DAILY\"," //
        + "\"metric_types\":[\"CONVERSATION\"]," //
        + "\"phone_numbers\":[\"12345\"]}";
    assertEquals(expectedObj, createJsonMapper().toJson(conversationAnalytics, true));
  }

}
