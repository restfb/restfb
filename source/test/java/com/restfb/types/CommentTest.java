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
import com.restfb.types.StoryAttachment.Image;
import com.restfb.types.StoryAttachment.Media;
import com.restfb.types.StoryAttachment.Target;

import org.junit.Test;

public class CommentTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_2_NoHide() {
    Comment exampleComment =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/comment_noCanHide"), Comment.class);
    assertFalse(exampleComment.isCanHide());
    assertTrue(exampleComment.getIsHidden());
  }

  @Test
  public void checkV2_2_canHide() {
    Comment exampleComment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/comment_canHide"), Comment.class);
    assertTrue(exampleComment.isCanHide());
    assertFalse(exampleComment.getIsHidden());
  }

  @Test
  public void checkV2_2_object() {
    Comment exampleComment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/comment_object"), Comment.class);
    assertNotNull(exampleComment.getObject());
    assertEquals("1559830900918160", exampleComment.getObject().getId());
    assertEquals("picture time", exampleComment.getObject().getName());
  }

  @Test
  public void checkV2_2_attachment() {
    Comment comment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/comment_attachment"), Comment.class);
    assertNotNull(comment.getObject());
    assertEquals("229204807192138", comment.getFrom().getId());
    assertEquals(null, comment.getParent());
    assertEquals("test attachment", comment.getMessage());
    assertEquals(0, comment.getCommentCount());
    assertEquals(0L, comment.getLikeCount().longValue());
    assertEquals(true, comment.getCanComment());
    assertEquals(false, comment.getCanHide());
    assertEquals(true, comment.getCanRemove());
    assertEquals(true, comment.getCanLike());
    assertEquals(false, comment.getIsHidden());
    StoryAttachment attachment = comment.getAttachment();
    assertEquals("https://www.facebook.com/229204807192138/photos/p.972460529533225/972460529533225/?type=3", attachment.getUrl());
    assertEquals("photo", attachment.getType());
    assertEquals(null, attachment.getTitle());
    assertEquals(null, attachment.getDescription());
    Media media = attachment.getMedia();
    assertNotNull(media);
    Image image = media.getImage();
    assertNotNull(image);
    assertEquals("https://scontent.xx.fbcdn.net/v/t1.0-9/14067576_972460529533225_2677071112954696357_n.jpg?oh=73826da052167394cd8ae9924cc2faee&oe=584DBFBA", image.getSrc());
    assertEquals(199, image.getWidth().intValue());
    assertEquals(60, image.getHeight().intValue());
    Target target = attachment.getTarget();
    assertNotNull(target);
    assertEquals("972460529533225", target.getId());
    assertEquals("https://www.facebook.com/229204807192138/photos/p.972460529533225/972460529533225/?type=3", target.getUrl());
  }

  @Test
  public void checkV2_2_issue286() {
    Comment exampleComment = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/comment-issue286"), Comment.class);
    assertNotNull(exampleComment.getObject());
    assertEquals("520586098107541", exampleComment.getObject().getId());
    assertEquals("new comment", exampleComment.getMessage());
  }

  @Test
  public void checkV2_4_messageTags() {
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
  public void checkV2_5_messageTags() {
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
}
