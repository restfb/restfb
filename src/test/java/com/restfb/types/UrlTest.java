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

import static com.restfb.testutils.RestfbAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class UrlTest extends AbstractJsonMapperTests {

  @Test
  void checkURL_v2_1() {
    Url exampleUrl = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/url-gotg"), Url.class);
    assertNotNull(exampleUrl);
    assertEquals("http://www.imdb.com/title/tt2015381/", exampleUrl.getId());
    assertNotNull(exampleUrl.getOgObject());
    assertEquals("10150298925420108", exampleUrl.getOgObject().getId());
    assertEquals("video.movie", exampleUrl.getOgObject().getType());
    assertEquals(1411924963000L, exampleUrl.getOgObject().getUpdatedTime().getTime());
    assertThat(exampleUrl.getOgObject().getImage()).contains("screencap.png");
    assertThat(exampleUrl.getOgObject().getTitle()).contains("Guardians");
    assertEquals(0, exampleUrl.getCommentCount());
    assertEquals(59573, exampleUrl.getShareCount());
  }

  @Test
  void checkURL_v2_8() {
    Url exampleUrl = createJsonMapper().toJavaObject(jsonFromClasspath("v2_8/url-with-share"), Url.class);
    assertNotNull(exampleUrl);
    assertEquals(5871, exampleUrl.getCommentCount());
    assertEquals(0, exampleUrl.getCommentPluginCount());
    assertEquals(0, exampleUrl.getReactionCount());
    assertEquals(172673672, exampleUrl.getShareCount());
    assertNotNull(exampleUrl.getAppLinks());
    assertEquals(1, exampleUrl.getAppLinks().getAndroid().size());
    assertEquals(1, exampleUrl.getAppLinks().getIos().size());
    assertEquals("website", exampleUrl.getOgObject().getType());
  }

  @Test
  void checkURL_v2_9() {
    Url exampleUrl = createJsonMapper().toJavaObject(jsonFromClasspath("v2_9/url-with-engagement"), Url.class);
    assertNotNull(exampleUrl);
    assertEquals(13809962, exampleUrl.getCommentCount());
    assertEquals(5871, exampleUrl.getCommentPluginCount());
    assertEquals(94605429, exampleUrl.getReactionCount());
    assertEquals(64258281, exampleUrl.getShareCount());
    assertNotNull(exampleUrl.getAppLinks());
    assertEquals(1, exampleUrl.getAppLinks().getAndroid().size());
    assertEquals(1, exampleUrl.getAppLinks().getIos().size());
    assertEquals("website", exampleUrl.getOgObject().getType());
  }
}
