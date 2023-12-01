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
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class MessageTest extends AbstractJsonMapperTests {

  @Test
  void photoTags_V2_5() {
    Message exampleMessage = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/message"), Message.class);
    assertNotNull(exampleMessage);
    List<String> tags = exampleMessage.getTags();
    assertEquals(4, tags.size());
    assertEquals("Test", exampleMessage.getMessage());
    assertEquals("Tester Page", exampleMessage.getFrom().getName());
    assertEquals("Test User", exampleMessage.getTo().get(0).getName());
  }

  @Test
  void messagesWithAttachment() {
    List<Message> messageList =
        createJsonMapper().toJavaList(jsonFromClasspath("v2_5/messages-with-attachments"), Message.class);
    for (Message message : messageList) {
      assertNotNull(message);
      Message.Attachment attachment = message.getAttachments().get(0);
      if (attachment.isImage()) {
        assertNotNull(attachment.getImageData());
        Message.ImageData imageData = attachment.getImageData();
        assertThat(imageData.getPreviewUrl()).contains("preview");
      } else if (attachment.isVideo()) {
        assertNotNull(attachment.getVideoData());
        Message.VideoData videoData = attachment.getVideoData();
        assertThat(videoData.getUrl()).contains("examplevideo.mp4");
        assertThat(videoData.getPreviewUrl()).contains("example_preview.jpg");
        assertEquals(0, videoData.getRotation());
        Message.ImageData imageData = attachment.getImageData();
        assertEquals(1280, imageData.getWidth());
        assertEquals(720, imageData.getHeight());
      } else {
        assertNotNull(attachment.getFileUrl());
      }
    }
  }

  @Test
  void messagesWithShares() {
    Message exampleMessage =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/message-with-share"), Message.class);
    assertNotNull(exampleMessage);
    assertEquals(1, exampleMessage.getShares().size());
    Message.Share exampleShare = exampleMessage.getShares().get(0);
    assertEquals("https://example.org/sample.png", exampleShare.getLink());
  }

  @Test
  void messagesWithSticker() {
    Message exampleMessage =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_11/message-with-sticker"), Message.class);
    assertNotNull(exampleMessage);
    assertEquals("https://scontent.xx.fbcdn.net/sticker.png", exampleMessage.getSticker());
  }

  @Test
  void messageWithRemovedAttachment() {
    Message exampleMessage = createJsonMapper().toJavaObject(jsonFromClasspath("v7_0/message-eu"), Message.class);
    assertNotNull(exampleMessage);
    Message.Attachment attachment = exampleMessage.getAttachments().get(0);
    assertTrue(attachment.isRemovedInEurope());
  }

  @Test
  void v10_instagram() {
    Message exampleMessage = createJsonMapper().toJavaObject(jsonFromClasspath("v10_0/instagram-message"), Message.class);
    assertThat(exampleMessage).isNotNull();
    assertThat(exampleMessage.getReactions()).isNotEmpty();
    assertThat(exampleMessage.getReactions().get(0).getReaction()).isEqualTo("love");
    assertThat(exampleMessage.getReactions().get(0).getUsers().get(0).getUsername()).isEqualTo("<IG_USERNAME>");
    assertThat(exampleMessage.getAttachments()).isNotEmpty();
    assertThat(exampleMessage.getShares()).isNotEmpty();
    assertThat(exampleMessage.getTo()).isNotNull();
    assertThat(exampleMessage.getTo().get(0).isInstagram()).isTrue();
    ExtendedReferenceType to = exampleMessage.getTo().get(0);
    assertThat(to.getUserId()).isEqualTo("<IGID>");
    assertThat(to.getUsername()).isEqualTo("<IG_USERNAME>");
    assertThat(to.getId()).isEqualTo("<IGSID|IGID>");

    assertThat(exampleMessage.getFrom()).isNotNull();
    assertThat(exampleMessage.getFrom().isInstagram()).isTrue();
    ExtendedReferenceType from = exampleMessage.getFrom();
    assertThat(from.getUserId()).isEqualTo("<IGID>");
    assertThat(from.getUsername()).isEqualTo("<IG_USERNAME>");
    assertThat(from.getId()).isEqualTo("<IGSID|IGID>");

  }

  @Test
  void v10_instagram_story() {
    Message exampleMessage = createJsonMapper().toJavaObject(jsonFromClasspath("v10_0/instagram-message-story"), Message.class);
    assertThat(exampleMessage).isNotNull();
    assertThat(exampleMessage.getStory()).isNotNull();
    assertThat(exampleMessage.getStory().getReplyTo()).isNotNull();
    assertThat(exampleMessage.getStory().getReplyTo().getLink()).isNotNull();
    assertThat(exampleMessage.getStory().getReplyTo().getLink()).contains("lookaside");
    assertThat(exampleMessage.getStory().getReplyTo().getId()).isNotNull();
    assertThat(exampleMessage.getStory().getReplyTo().getId()).contains("1807");
  }
}
