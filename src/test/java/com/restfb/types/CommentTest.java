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

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class CommentTest extends AbstractJsonMapperTests {

  @Test
  void checkV2_2_NoHide() {
    Comment exampleComment =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/comment_noCanHide"), Comment.class);
    assertNull(exampleComment.getCanHide());
    assertTrue(exampleComment.getIsHidden());
  }

  @Test
  void checkV2_2_canHide() {
    Comment exampleComment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/comment_canHide"), Comment.class);
    assertTrue(exampleComment.getCanHide());
    assertFalse(exampleComment.getIsHidden());
  }

  @Test
  void checkV2_2_object() {
    Comment exampleComment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/comment_object"), Comment.class);
    assertNotNull(exampleComment.getObject());
    assertEquals("1559830900918160", exampleComment.getObject().getId());
    assertEquals("picture time", exampleComment.getObject().getName());
  }

  @Test
  void checkV2_2_issue286() {
    Comment exampleComment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/comment-issue286"), Comment.class);
    assertNotNull(exampleComment.getObject());
    assertEquals("520586098107541", exampleComment.getObject().getId());
    assertEquals("new comment", exampleComment.getMessage());
  }

  @Test
  void checkV2_4_messageTags() {
    Comment exampleComment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/message-tags"), Comment.class);
    assertNotNull(exampleComment);
    assertNotNull(exampleComment.getMessageTags());
    assertFalse(exampleComment.getMessageTags().isEmpty());
    MessageTag exampleTag = exampleComment.getMessageTags().get(0);
    assertEquals("88388366982", exampleTag.getId());
    assertEquals("Público", exampleTag.getName());
    assertEquals("page", exampleTag.getType());
    assertEquals(7, exampleTag.getLength().intValue());
    assertEquals(94, exampleTag.getOffset().intValue());
  }

  @Test
  void checkV2_5_messageTags() {
    Comment exampleComment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/message-tags"), Comment.class);
    assertNotNull(exampleComment);
    assertNotNull(exampleComment.getMessageTags());
    assertFalse(exampleComment.getMessageTags().isEmpty());
    MessageTag exampleTag = exampleComment.getMessageTags().get(0);
    assertEquals("88388366982", exampleTag.getId());
    assertEquals("Público", exampleTag.getName());
    assertEquals("page", exampleTag.getType());
    assertEquals(7, exampleTag.getLength().intValue());
    assertEquals(94, exampleTag.getOffset().intValue());
  }

  @Test
  void checkV2_9_reactions() {
    Comment exampleComment =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_9/comment-reactions"), Comment.class);
    assertNotNull(exampleComment);
    assertNotNull(exampleComment.getReactions());
    Reactions reactions = exampleComment.getReactions();
    assertEquals(1214, reactions.getTotalCount().intValue());
    assertEquals("NONE", reactions.getViewerReaction());
    Reactions.ReactionItem rItem = reactions.getData().get(0);
    assertEquals("LIKE", rItem.getType());
    assertEquals("10204061807492438", rItem.getId());
  }

  @Test
  void checkV2_9_permalink() {
    Comment exampleComment =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_9/comment-permalink"), Comment.class);
    assertNotNull(exampleComment);
    assertNotNull(exampleComment.getPermalinkUrl());
  }

  @Test
  void checkV2_9_Issue768() {
    Comment exampleComment =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_9/comment-messagetags"), Comment.class);
    assertNotNull(exampleComment);
    assertNotNull(exampleComment.getMessageTags());
    assertEquals(1, exampleComment.getMessageTags().size());
    MessageTag tag = exampleComment.getMessageTags().get(0);
    assertEquals(13, tag.getLength().intValue());
  }

}
