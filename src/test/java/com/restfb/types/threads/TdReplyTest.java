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
package com.restfb.types.threads;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.restfb.AbstractJsonMapperTests;

class TdReplyTest extends AbstractJsonMapperTests {

  @Test
  void checkAdditionalFields() {
    // Synthetic field-coverage fixture, not a captured API response.
    TdReply reply =
        createJsonMapper().toJavaObject(jsonFromClasspath("threads/reply-with-additional-fields"), TdReply.class);

    assertEquals("https://example.org/avatar.jpg", reply.getProfilePictureUrl());
    assertTrue(reply.getIsVerified());
    assertEquals("400", reply.getQuotedPost());
    assertEquals("https://example.org/animation.gif", reply.getGifUrl());
    assertEquals("ThreadsAPI", reply.getTopicTag());
    assertNotNull(reply.getPollAttachment());
    assertEquals("First", reply.getPollAttachment().getOptionA());
    assertEquals("Second", reply.getPollAttachment().getOptionB());
    assertEquals(0.25, reply.getPollAttachment().getOptionAVotesPercentage(), 0.001);
    assertEquals(0.75, reply.getPollAttachment().getOptionBVotesPercentage(), 0.001);
    assertEquals(1735772400000L, reply.getPollAttachment().getExpirationTimestamp().getTime());
    assertEquals("200", reply.getRootPost().getId());
    assertEquals("300", reply.getRepliedTo().getId());
  }

  @ParameterizedTest
  @ValueSource(strings = { "true", "false", "null" })
  void checkOptionalBooleanValues(String rawValue) {
    String json = String.format(
      "{\"is_verified\":%1$s,\"has_replies\":%1$s,\"is_reply\":%1$s,\"is_reply_owned_by_me\":%1$s,\"is_quote_post\":%1$s}",
      rawValue);
    TdReply reply = createJsonMapper().toJavaObject(json, TdReply.class);
    Boolean expected = "null".equals(rawValue) ? null : Boolean.valueOf(rawValue);

    assertEquals(expected, reply.getIsVerified());
    assertEquals(expected, reply.getHasReplies());
    assertEquals(expected, reply.getIsReply());
    assertEquals(expected, reply.getIsReplyOwnedByMe());
    assertEquals(expected, reply.getIsQuotePost());
  }

  @ParameterizedTest
  @ValueSource(strings = { "threads/basic-reply", "threads/additional-fields-null" })
  void checkMissingOrNullAdditionalFields(String fixture) {
    TdReply reply = createJsonMapper().toJavaObject(jsonFromClasspath(fixture), TdReply.class);

    assertNull(reply.getProfilePictureUrl());
    assertNull(reply.getIsVerified());
    assertNull(reply.getQuotedPost());
    assertNull(reply.getGifUrl());
    assertNull(reply.getPollAttachment());
    assertNull(reply.getTopicTag());
  }

  @Test
  void checkMissingBooleanFields() {
    TdReply reply = createJsonMapper().toJavaObject("{\"id\":\"100\"}", TdReply.class);

    assertNull(reply.getIsVerified());
    assertNull(reply.getHasReplies());
    assertNull(reply.getIsReply());
    assertNull(reply.getIsReplyOwnedByMe());
    assertNull(reply.getIsQuotePost());
  }

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
