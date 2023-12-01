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
package com.restfb.types;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.restfb.AbstractJsonMapperTests;

class PageTest extends AbstractJsonMapperTests {

  @DisplayName("check Page")
  @ParameterizedTest(name = "{index} ==> version ''{0}''")
  @MethodSource("jsonSupplier")
  void checkV1_0(String version) {
    Page examplePage = createJsonMapper().toJavaObject(jsonFromClasspath(version + "/page"), Page.class);
    assertEquals("http://www.coca-cola.com", examplePage.getWebsite());
    assertEquals(1, examplePage.getCategoryList().size());
    Category cat = examplePage.getCategoryList().get(0);
    assertEquals("Company", cat.getName());
    assertEquals("2200", cat.getId());
  }

  private static Stream<String> jsonSupplier() {
    return Stream.of("v1_0", "v2_0", "v2_1");
  }

  @Test
  void checkV2_3_settings() {
    List<Page.Settings> pageSettingList =
        createJsonMapper().toJavaList(jsonFromClasspath("v2_3/page-settings"), Page.Settings.class);
    assertEquals(11, pageSettingList.size());
    for (Page.Settings pageSetting : pageSettingList) {
      assertNotNull(pageSetting.getSetting());
      assertNotNull(pageSetting.getValue());
    }
  }

  @Test
  void checkV2_3_engagement() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/page-engagement"), Page.class);
    assertNotNull(page);
    assertEquals("1234567890", page.getId());
    assertNotNull(page.getEngagement());
    assertEquals(37, page.getEngagement().getCount().intValue());
    assertEquals("37 people like this.", page.getEngagement().getSocialSentence());
  }

  @Test
  void checkV2_3_voipinfo() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/page-voipinfo"), Page.class);
    assertNotNull(page);
    assertEquals("1234567890", page.getId());
    assertNotNull(page.getVoipInfo());
    assertTrue(page.getVoipInfo().getHasPermission());
    assertTrue(page.getVoipInfo().getHasMobileApp());
    assertTrue(page.getVoipInfo().getIsPushable());
    assertTrue(page.getVoipInfo().getIsCallable());
    assertTrue(page.getVoipInfo().getIsCallableWebrtc());
    assertEquals(1356044, page.getVoipInfo().getReasonCode().intValue());
    assertEquals("This person can't be called right now.", page.getVoipInfo().getReasonDescription());
  }

  @Test
  void checkV2_4_picture() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/page-picture"), Page.class);
    assertNotNull(page);
    assertNotNull(page.getPicture());
    assertFalse(page.getPicture().getIsSilhouette());
    assertEquals("https://fbcdn-profile-a.akamaihd.net/testpicture.jpg", page.getPicture().getUrl());
  }

  @Test
  void checkV2_5_lastusedtime() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/page-lastusedtime"), Page.class);
    assertNotNull(page);
    assertEquals(1413059309000L, page.getLastUsedTime().getTime());
  }

  @Test
  void checkV2_5_moreFields() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/page-morefields"), Page.class);
    assertNotNull(page);
    assertEquals(0.12, page.getAssetScore(), 0.001);
    assertEquals(2, page.getCountryPageLikes().intValue());
    assertEquals("PLACE", page.getPlaceType());
    assertEquals("test street 10, 12345 test town", page.getSingleLineAddress());
    assertEquals("not_verified", page.getVerificationStatus());
    assertFalse(page.getIsAlwaysOpen());
    assertTrue(page.getCanCheckin());
  }

  @Test
  void checkV2_6_fanCount() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/page-with-fancount"), Page.class);
    assertNotNull(page);
    assertEquals(3L, page.getFanCount().longValue());
    assertEquals(3L, page.getLikesCount().longValue());
  }

  @Test
  void checkV2_5_fanCount() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/page-with-likes"), Page.class);
    assertNotNull(page);
    assertEquals(3L, page.getFanCount().longValue());
    assertEquals(3L, page.getLikesCount().longValue());
  }

  @Test
  void checkV2_6_adminNotes() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/page-with-admin_notes"), Page.class);
    assertNotNull(page);
    assertNotNull(page.getAdminNotes());
    assertEquals(1L, page.getAdminNotes().size());
  }

  @Test
  void checkV2_6_label() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/page-with-labels"), Page.class);
    assertNotNull(page);
    assertNotNull(page.getLabels());
    assertEquals(4L, page.getLabels().size());
  }

  @Test
  void checkV2_6_likes() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/page-with-likes"), Page.class);
    assertNotNull(page);
    assertNotNull(page.getLikes());
    assertEquals(5, page.getLikes().getData().size());
    Likes.LikeItem item = page.getLikes().getData().get(0);
    assertEquals("10582587785", item.getId());
    assertEquals("Damien Rice", item.getName());
  }

  @Test
  void checkV2_7_webhook_subscribed() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_7/page-with-whsub"), Page.class);
    assertNotNull(page);
    assertTrue(page.getIsWebhooksSubscribed());
    assertNotNull(page.getEngagement());
    Page.Engagement engagement = page.getEngagement();
    assertEquals("3", engagement.getCountString());
    assertEquals(3L, engagement.getCount().longValue());
    assertEquals("4", engagement.getCountStringWithLike());
    assertEquals("3", engagement.getCountStringWithoutLike());
    assertEquals("3 people like this.", engagement.getSocialSentence());
    assertEquals("You and 3 others like this.", engagement.getSocialSentenceWithLike());
    assertEquals("3 people like this.", engagement.getSocialSentenceWithoutLike());
  }

  @Test
  void checkV2_11_screennames() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_11/page-with-screennames"), Page.class);
    assertNotNull(page);
    assertNotNull(page.getScreenNames());
    assertEquals(2, page.getScreenNames().size());
  }

  @Test
  void checkV2_11_instagram() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_11/page-with-instagram"), Page.class);
    assertNotNull(page);
    assertNotNull(page.getConnectedInstagramAccount());
    assertEquals("987654321", page.getConnectedInstagramAccount().getId());
    assertNotNull(page.getInstagramBusinessAccount());
    assertEquals("987654321", page.getInstagramBusinessAccount().getId());
  }

  @Test
  void checkV3_2_hours() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v3_2/page-with-hours"), Page.class);
    assertNotNull(page);
    Hours hours = page.getHours();
    assertEquals(2, hours.getHours(Hours.DayOfWeek.MON).size());
    assertEquals(7, hours.getHours().size());
  }

  @Test
  void checkV3_2_hours_alternative() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v3_2/page-with-hours2"), Page.class);
    assertNotNull(page);
    Hours hours = page.getHours();
    assertEquals(1, hours.getHours(Hours.DayOfWeek.MON).size());
    assertEquals(5, hours.getHours().size());
  }

  @Test
  void checkV3_2_rating() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v3_2/page-with-ratings"), Page.class);
    assertNotNull(page);
    assertEquals(2, page.getRatingCount().intValue());
    assertEquals(2.5, page.getOverallStarRating().doubleValue());
  }

  @Test
  void checkv16_insights() {
    JsonMapper mapper = createConnectionJsonMapper();
    Page page = mapper.toJavaObject(jsonFromClasspath("v16_0/page-with-insights"), Page.class);
    assertNotNull(page);
    assertNotNull(page.getInsights());
    assertEquals(3, page.getInsights().getData().size());
    Insight insight0 = page.getInsights().getData().get(0);
    assertEquals("day", insight0.getPeriod());
    assertEquals("page_engaged_users", insight0.getName());
    assertEquals("123456/insights/page_engaged_users/day", insight0.getId());
    assertEquals(2, insight0.getValues().size());
    assertEquals(0, insight0.getValues().get(0).get("value").asInt());
  }

}