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
package com.restfb.types.threads;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class TdReplyTest extends AbstractJsonMapperTests {

  @Test
  void checkSingleBasicReply() {
    TdReply threadsReply = createJsonMapper().toJavaObject(jsonFromClasspath("threads/basic-reply"), TdReply.class);
    assertNotNull(threadsReply);
    assertEquals("1234567890", threadsReply.getId());
    assertEquals(TdMediaType.TEXT_POST, threadsReply.getMediaType());
    assertTrue(threadsReply.getHasReplies());
    assertNotNull(threadsReply.getRootPost());
    assertEquals("1234567890", threadsReply.getRootPost().getId());
    assertNotNull(threadsReply.getRepliedTo());
    assertEquals("1234567890", threadsReply.getRepliedTo().getId());
    assertEquals(TdReply.TdHideStatus.NOT_HUSHED, threadsReply.getHideStatus());
    assertEquals(1704133200000L, threadsReply.getTimestamp().getTime());
    assertTrue(threadsReply.getIsReply());
  }

  @Test
  void checkBasicReplyList() {
    List<TdReply> threadsReplyList =
        createJsonMapper().toJavaList(jsonFromClasspath("threads/basic-list-reply"), TdReply.class);
    assertNotNull(threadsReplyList);
    assertEquals(3, threadsReplyList.size());

    for (TdReply reply : threadsReplyList) {
      assertEquals("1234567890", reply.getId());
    }

  }
}
