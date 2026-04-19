/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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
package com.restfb.util;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.restfb.types.MessageTag;

class MessageTagUtilsTest {

  @Test
  void keepsOriginalWhenNoTagsPresent() {
    String message = "Hello World";
    assertSame(message, MessageTagUtils.normalizeMessage(message, null));
    assertSame(message, MessageTagUtils.normalizeMessage(message, Collections.emptyList()));
  }

  @Test
  void replacesTaggedSegmentsWithMentions() {
    String message = "Hello Foo Bar";
    MessageTag tag = createTag(6, 3, "123456", "Foo");

    assertEquals("Hello @[123456] Bar", MessageTagUtils.normalizeMessage(message, asList(tag)));
  }

  @Test
  void fallsBackWhenOffsetsAreInvalid() {
    String message = "Hello";
    MessageTag tag = createTag(10, 2, "123", "zz");

    assertSame(message, MessageTagUtils.normalizeMessage(message, asList(tag)));
  }

  @Test
  void usesNameWhenIdMissing() {
    String message = "Hello Foo";
    MessageTag tag = createTag(6, 3, null, "Foo");

    assertEquals("Hello Foo", MessageTagUtils.normalizeMessage(message, asList(tag)));
  }

  private MessageTag createTag(int offset, int length, String id, String name) {
    MessageTag tag = new MessageTag();
    tag.setOffset(offset);
    tag.setLength(length);
    tag.setId(id);
    tag.setName(name);
    return tag;
  }
}
