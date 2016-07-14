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

import org.junit.Test;

import java.util.List;

public class VideoTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_3_ThumbnailList() {
    List<Video.Thumbnail> thumbnailList =
        createJsonMapper().toJavaList(jsonFromClasspath("v2_3/video-thumbnails"), Video.Thumbnail.class);
    assertEquals(10, thumbnailList.size());
    for (Video.Thumbnail thumbnail : thumbnailList) {
      assertEquals(null, thumbnail.getName());
      assertEquals(302, thumbnail.getHeight().intValue());
      assertEquals(403, thumbnail.getWidth().intValue());
      assertEquals(1, thumbnail.getScale().intValue());
      assertTrue(thumbnail.getUri().contains("akamaihd.net"));
      assertNotNull(thumbnail.getIsPreferred());
    }
  }

  @Test
  public void checkV2_4_Privacy() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-newfields"), Video.class);
    assertNotNull(exampleVideo.getPrivacy());
    Privacy privacy = exampleVideo.getPrivacy();
    assertEquals("", privacy.getAllow());
    assertEquals("EVERYONE", privacy.getValue());
    assertEquals("Public", privacy.getDescription());
  }

  @Test
  public void checkV2_4_Formats() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-newfields"), Video.class);
    assertNotNull(exampleVideo.getFormat());
    assertEquals(3, exampleVideo.getFormat().size());
    Video.VideoFormat format0 = exampleVideo.getFormat().get(0);
    assertEquals("130x130", format0.getFilter());
    assertEquals(73, format0.getHeight().intValue());
    assertEquals(130, format0.getWidth().intValue());
    Video.VideoFormat format1 = exampleVideo.getFormat().get(1);
    assertEquals("480x480", format1.getFilter());
    assertEquals(270, format1.getHeight().intValue());
    assertEquals(480, format1.getWidth().intValue());
    Video.VideoFormat format2 = exampleVideo.getFormat().get(2);
    assertEquals("native", format2.getFilter());
    assertEquals(360, format2.getHeight().intValue());
    assertEquals(640, format2.getWidth().intValue());
  }

  @Test
  public void checkV2_4_Embeddable() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-newfields"), Video.class);
    assertTrue(exampleVideo.getEmbeddable());
  }

  @Test
  public void checkV2_4_Status() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-newfields"), Video.class);
    assertNotNull(exampleVideo.getStatus());
    assertEquals("ready", exampleVideo.getStatus().getVideoStatus());
  }

  @Test
  public void checkV2_4_LikesCount() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-likescount"), Video.class);
    assertEquals(10566L, exampleVideo.getLikesCount().longValue());
  }

  @Test
  public void checkV2_4_Likes() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-likescount"), Video.class);
    assertNotNull(exampleVideo.getLikes());
    Likes likes = exampleVideo.getLikes();
    assertNotNull(likes.getData());
    NamedFacebookType like = likes.getData().get(0);
    assertEquals("Princess Hayiria", like.getName());
    assertEquals("1466760573639983", like.getId());
    assertEquals(10566L, likes.getTotalCount().longValue());
  }

  @Test
  public void checkV2_4_CommentsCount() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-commentcount"), Video.class);
    assertEquals(231L, exampleVideo.getCommentsCount().longValue());
  }

  @Test
  public void checkV2_4_Comments() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-commentcount"), Video.class);
    assertNotNull(exampleVideo.getComments());
    Comments comments = exampleVideo.getComments();
    assertNotNull(comments.getData());
    Comment c = comments.getData().get(0);
    assertEquals("Anas Umar", c.getFrom().getName());
    assertEquals("Goood nd fn", c.getMessage());
    assertEquals(231L, comments.getTotalCount().longValue());
  }

  @Test
  public void checkV2_7_feedType() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_7/video-feedtype"), Video.class);
    assertNotNull(exampleVideo);
    assertEquals("NOT_HIGHLIGHTED", exampleVideo.getFeedType());
    assertEquals("/987654321/videos/977667602329064/", exampleVideo.getPermalinkUrl());
    assertEquals("https://scontent.xx.fbcdn.net/example_picture.jpg", exampleVideo.getPicture());
    assertEquals("https://static.xx.fbcdn.net/rsrc.php/icon.gif", exampleVideo.getIcon());
    assertEquals(
      "<iframe src=\"https://www.facebook.com/video/embed?video_id=977667602329064\" width=\"400\" height=\"224\" frameborder=\"0\"></iframe>",
      exampleVideo.getEmbedHtml());
    assertTrue(exampleVideo.getIsCrosspostingEligible());
    assertTrue(exampleVideo.getIsInstagramEligible());
    assertTrue(exampleVideo.getEmbeddable());
    assertEquals(5.461D, exampleVideo.getLength().doubleValue(), 0.01);
    assertEquals("ready", exampleVideo.getStatus().getVideoStatus());
    assertEquals("Test Sender", exampleVideo.getFrom().getName());
    assertEquals("987654321", exampleVideo.getFrom().getId());
  }

}
