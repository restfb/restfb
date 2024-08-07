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
package com.restfb.types.instagram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.restfb.types.ads.IgBoostMediaAd;
import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class IgMediaTest extends AbstractJsonMapperTests {

  @Test
  void checkMediaImage() {
    IgMedia igMedia = createJsonMapper().toJavaObject(jsonFromClasspath("instagram/media-image"), IgMedia.class);
    assertNotNull(igMedia);
    assertEquals("123456789", igMedia.getId());
    assertEquals("123456789777", igMedia.getIgId());
    assertEquals("a great picture", igMedia.getCaption());
    assertEquals("IMAGE", igMedia.getMediaType());
    assertEquals("<image url>", igMedia.getMediaUrl());
    assertNotNull(igMedia.getUsername());
    assertEquals("https://www.instagram.com/permalink", igMedia.getPermalink());
    assertEquals("shortcode", igMedia.getShortcode());
    assertEquals(1519293629000L, igMedia.getTimestamp().getTime());
    assertEquals(1L, igMedia.getCommentsCount().longValue());
    assertEquals(0L, igMedia.getLikeCount().longValue());
    assertEquals("fake_username", igMedia.getUsername());
  }

  @Test
  void checkMediaCarousel() {
    IgMedia igMedia = createJsonMapper().toJavaObject(jsonFromClasspath("instagram/media-carousel"), IgMedia.class);
    assertNotNull(igMedia);
    assertEquals("123456789", igMedia.getId());
    assertEquals("123456789777", igMedia.getIgId());
    assertEquals("Great carousel", igMedia.getCaption());
    assertEquals("CAROUSEL_ALBUM", igMedia.getMediaType());
    assertEquals("<image url>", igMedia.getMediaUrl());
    assertNotNull(igMedia.getUsername());
    assertEquals("https://www.instagram.com/permalink", igMedia.getPermalink());
    assertEquals("shortcode", igMedia.getShortcode());
    assertEquals(1519296045000L, igMedia.getTimestamp().getTime());
    assertEquals(0L, igMedia.getCommentsCount().longValue());
    assertEquals(0L, igMedia.getLikeCount().longValue());
    assertEquals(2, igMedia.getChildren().size());
    int i = 0;
    for (IgMediaChild child : igMedia.getChildren()) {
      i++;
      assertNotNull(child.getUsername());
      assertEquals(igMedia.getId() + i, child.getId());
      assertEquals(igMedia.getIgId() + i, child.getIgId());
      assertEquals("IMAGE", child.getMediaType());
      assertEquals(igMedia.getPermalink() + i, child.getPermalink());
      assertEquals(igMedia.getShortcode() + i, child.getShortcode());
    }
  }

  @Test
  void checkMediaCopyright() {
    IgMedia igMedia = createJsonMapper().toJavaObject(jsonFromClasspath("instagram/igmedia-cci"), IgMedia.class);
    assertNotNull(igMedia);
    assertNotNull(igMedia.getCopyrightCheckInformation());
    assertNotNull(igMedia.getCopyrightCheckInformation().getStatus());
    assertEquals("error", igMedia.getCopyrightCheckInformation().getStatus().getStatus());
  }

  @Test
  void checkMediaWithBoostAdsList() {
    IgMedia igMedia = createJsonMapper().toJavaObject(jsonFromClasspath("instagram/media-with-boost-ads"), IgMedia.class);
    assertNotNull(igMedia);
    assertEquals(1, igMedia.getBoostAdsList().size());
    IgBoostMediaAd boostAd = igMedia.getBoostAdsList().get(0);
    assertEquals("ad123", boostAd.getAdId());
    assertEquals("active", boostAd.getAdStatus());
  }
}
