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
package com.restfb.types;

import static org.junit.Assert.*;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

import java.util.List;

public class PageTest extends AbstractJsonMapperTests {

  @Test
  public void checkV1_0() {
    Page examplePage = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/page"), Page.class);
    assertEquals(examplePage.getWebsite(), "http://www.coca-cola.com");
    assertEquals(1, examplePage.getCategoryList().size());
    Category cat = examplePage.getCategoryList().get(0);
    assertEquals("Company", cat.getName());
    assertEquals("2200", cat.getId());
  }

  @Test
  public void checkV2_0() {
    Page examplePage = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/page"), Page.class);
    assertEquals(examplePage.getWebsite(), "http://www.coca-cola.com");
    assertEquals(1, examplePage.getCategoryList().size());
    Category cat = examplePage.getCategoryList().get(0);
    assertEquals("Company", cat.getName());
    assertEquals("2200", cat.getId());
  }

  @Test
  public void checkV2_1() {
    Page examplePage = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/page"), Page.class);
    assertEquals(examplePage.getWebsite(), "http://www.coca-cola.com");
    assertEquals(1, examplePage.getCategoryList().size());
    Category cat = examplePage.getCategoryList().get(0);
    assertEquals("Company", cat.getName());
    assertEquals("2200", cat.getId());
  }

  @Test
  public void checkV2_3_settings() {
    List<Page.Settings> pageSettingList =
        createJsonMapper().toJavaList(jsonFromClasspath("v2_3/page-settings"), Page.Settings.class);
    assertEquals(11, pageSettingList.size());
    for (Page.Settings pageSetting : pageSettingList) {
      assertNotNull(pageSetting.getSetting());
      assertNotNull(pageSetting.getValue());
    }
  }

  @Test
  public void checkV2_3_engagement() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/page-engagement"), Page.class);
    assertNotNull(page);
    assertEquals("1234567890", page.getId());
    assertNotNull(page.getEngagement());
    assertEquals(37, page.getEngagement().getCount().intValue());
    assertEquals("37 people like this.", page.getEngagement().getSocialSentence());
  }

  @Test
  public void checkV2_3_voipinfo() {
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
  public void checkV2_4_picture() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/page-picture"), Page.class);
    assertNotNull(page);
    assertNotNull(page.getPicture());
    assertFalse(page.getPicture().getIsSilhouette());
    assertEquals("https://fbcdn-profile-a.akamaihd.net/testpicture.jpg", page.getPicture().getUrl());
  }

  @Test
  public void checkV2_5_lastusedtime() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/page-lastusedtime"), Page.class);
    assertNotNull(page);
    assertEquals(1413059309000L, page.getLastUsedTime().getTime());
  }

  @Test
  public void checkV2_5_moreFields() {
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
  public void checkV2_6_fanCount() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/page-with-fancount"), Page.class);
    assertNotNull(page);
    assertEquals(3L, page.getFanCount().longValue());
    assertEquals(3L, page.getLikes().longValue());
  }

  @Test
  public void checkV2_5_fanCount() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/page-with-likes"), Page.class);
    assertNotNull(page);
    assertEquals(3L, page.getFanCount().longValue());
    assertEquals(3L, page.getLikes().longValue());
  }

  @Test
  public void checkV2_6_adminNotes() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/page-with-admin_notes"), Page.class);
    assertNotNull(page);
    assertNotNull(page.getAdminNotes());
    assertEquals(1L, page.getAdminNotes().size());
  }

  @Test
  public void checkV2_6_label() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/page-with-labels"), Page.class);
    assertNotNull(page);
    assertNotNull(page.getLabels());
    assertEquals(4L, page.getLabels().size());
  }

  @Test
  public void checkV2_6_likes() {
    Page page = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/page-with-likes"), Page.class);
    assertNotNull(page);
    assertNotNull(page.getLikesList());
    assertEquals(5, page.getLikesList().getData().size());
    NamedFacebookType item = page.getLikesList().getData().get(0);
    assertEquals("10582587785", item.getId());
    assertEquals("Damien Rice", item.getName());
  }

  @Test
  public void checkV2_7_webhook_subscribed() {
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

}
