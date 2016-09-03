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

import org.hamcrest.core.StringContains;
import org.junit.Test;

import java.util.List;

public class MessageTest extends AbstractJsonMapperTests {

  @Test
  public void photoTags_V2_5() {
    Message exampleMessage = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/message"), Message.class);
    assertNotNull(exampleMessage);
    List<String> tags = exampleMessage.getTags();
    assertEquals(4, tags.size());
    assertEquals("Test", exampleMessage.getMessage());
    assertEquals("Tester Page", exampleMessage.getFrom().getName());
    assertEquals("Test User", exampleMessage.getTo().get(0).getName());
  }

  @Test
  public void messagesWithAttachment() {
    List<Message> messageList =
        createJsonMapper().toJavaList(jsonFromClasspath("v2_5/messages-with-attachments"), Message.class);
    for (Message message : messageList) {
      assertNotNull(message);
      Message.Attachment attachment = message.getAttachments().get(0);
      if (attachment.isImage()) {
        assertNotNull(attachment.getImageData());
        Message.ImageData imageData = attachment.getImageData();
        assertThat(imageData.getPreviewUrl(), new StringContains("preview"));
      } else if (attachment.isVideo()) {
        assertNotNull(attachment.getVideoData());
        Message.VideoData videoData = attachment.getVideoData();
        assertThat(videoData.getUrl(), new StringContains("examplevideo.mp4"));
        assertThat(videoData.getPreviewUrl(), new StringContains("example_preview.jpg"));
        assertEquals(0,videoData.getRotation());
        Message.ImageData imageData = attachment.getImageData();
        assertEquals(1280, imageData.getWidth());
        assertEquals(720, imageData.getHeight());
      } else {
        assertNotNull(attachment.getFileUrl());
      }
    }
  }

  @Test
  public void messagesWithShares() {
    Message exampleMessage =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/message-with-share"), Message.class);
    assertNotNull(exampleMessage);
    assertEquals(1, exampleMessage.getShares().size());
    Message.Share exampleShare = exampleMessage.getShares().get(0);
    assertEquals("https://example.org/sample.png", exampleShare.getLink());
  }
}
