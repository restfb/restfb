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
package com.restfb.types.oembed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class OembedTest extends AbstractJsonMapperTests {

  @Test
  void oembedPage() {
    OEmbedPage page = createJsonMapper().toJavaObject(jsonFromClasspath("v8_0/oembed_page"), OEmbedPage.class);
    baseOEmbedChecks(page);
    assertEquals(340, page.getWidth());
    assertEquals(500, page.getHeight());
  }

  @Test
  void oembedPost() {
    OEmbedPost post = createJsonMapper().toJavaObject(jsonFromClasspath("v8_0/oembed_post"), OEmbedPost.class);
    baseOEmbedChecks(post);
    assertEquals(552, post.getWidth());
    assertEquals("Test Page", post.getAuthorName());
    assertEquals("https://www.facebook.com/123456789", post.getAuthorUrl());
  }

  @Test
  void oembedVideo() {
    OEmbedVideo video = createJsonMapper().toJavaObject(jsonFromClasspath("v8_0/oembed_video"), OEmbedVideo.class);
    baseOEmbedChecks(video);
    assertEquals(552, video.getWidth());
    assertEquals(893, video.getHeight());
    assertEquals("Test Page", video.getAuthorName());
    assertEquals("https://www.facebook.com/123456789", video.getAuthorUrl());
  }

  private void baseOEmbedChecks(BaseOEmbed embed) {
    assertNotNull(embed);
    assertEquals("Facebook", embed.getProviderName());
    assertEquals("https://www.facebook.com", embed.getProviderUrl());
    assertEquals("rich", embed.getType());
    assertEquals("1.0", embed.getVersion());
    assertEquals("<div id=\"fb-root\">SOME HTML STUFF</div>", embed.getHtml());
  }
}
