/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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

import static org.junit.Assert.*;

import org.junit.Test;

import com.restfb.AbstractJsonMapperTests;

public class IgCommentTest extends AbstractJsonMapperTests {

  @Test
  public void checkComment() {
    IgComment igComment = createJsonMapper().toJavaObject(jsonFromClasspath("instagram/comment"), IgComment.class);
    assertNotNull(igComment);
    assertNotNull(igComment.getMedia());
    assertNotNull(igComment.getUser());
    assertEquals("123456789", igComment.getId());
    assertEquals("Great!", igComment.getText());
    assertFalse(igComment.getHidden().booleanValue());
    assertEquals(1519421536000l, igComment.getTimestamp().getTime());
    assertEquals(0l, igComment.getLikeCount().longValue());

    assertEquals(1, igComment.getReplies().size());
    int i = 0;
    for (IgComment reply : igComment.getReplies()) {
      i++;
      assertEquals(igComment.getId() + i, reply.getId());
      assertEquals("yeah", reply.getText());
    }
  }
}
