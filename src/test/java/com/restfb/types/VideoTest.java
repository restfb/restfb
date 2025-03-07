/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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

import java.util.List;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.JsonMapper;

class VideoTest extends AbstractJsonMapperTests {

  @Test
  void checkV2_3_ThumbnailList() {
    List<Video.Thumbnail> thumbnailList =
        createJsonMapper().toJavaList(jsonFromClasspath("v2_3/video-thumbnails"), Video.Thumbnail.class);
    assertEquals(10, thumbnailList.size());
    thumbnailList.forEach(thumbnail -> {
      assertNull(thumbnail.getName());
      assertEquals(302, thumbnail.getHeight().intValue());
      assertEquals(403, thumbnail.getWidth().intValue());
      assertEquals(1, thumbnail.getScale().intValue());
      assertTrue(thumbnail.getUri().contains("akamaihd.net"));
      assertNotNull(thumbnail.getIsPreferred());
    });
  }

  @Test
  void checkV2_4_Privacy() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-newfields"), Video.class);
    assertNotNull(exampleVideo.getPrivacy());
    Privacy privacy = exampleVideo.getPrivacy();
    assertEquals("", privacy.getAllow());
    assertEquals("EVERYONE", privacy.getValue());
    assertEquals("Public", privacy.getDescription());
  }

  @Test
  void checkV2_4_Formats() {
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
  void checkV2_4_Embeddable() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-newfields"), Video.class);
    assertTrue(exampleVideo.getEmbeddable());
  }

  @Test
  void checkV2_4_Status() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-newfields"), Video.class);
    assertNotNull(exampleVideo.getStatus());
    assertEquals("ready", exampleVideo.getStatus().getVideoStatus());
  }

  @Test
  void checkV2_4_LikesCount() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-likescount"), Video.class);
    assertEquals(10566L, exampleVideo.getLikesCount().longValue());
  }

  @Test
  void checkV2_4_Likes() {
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
  void checkV2_4_CommentsCount() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/video-commentcount"), Video.class);
    assertEquals(231L, exampleVideo.getCommentsCount().longValue());
  }

  @Test
  void checkV2_4_Comments() {
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
  void checkV2_7_feedType() {
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
    assertEquals(5.461D, exampleVideo.getLength().doubleValue());
    assertEquals("ready", exampleVideo.getStatus().getVideoStatus());
    assertEquals("Test Sender", exampleVideo.getFrom().getName());
    assertEquals("987654321", exampleVideo.getFrom().getId());
  }

  @Test
  void checkV2_9_captions() {
    Video exampleVideo = createJsonMapper().toJavaObject(jsonFromClasspath("v2_9/video-captions"), Video.class);
    assertNotNull(exampleVideo);
    assertNotNull(exampleVideo.getCaptions());
    List<VideoCaption> captions = exampleVideo.getCaptions();
    assertNotNull(captions.get(0));
    VideoCaption firstCaption = captions.get(0);
    assertEquals("en_US", firstCaption.getLocale());
    assertEquals(1501879203000L, firstCaption.getCreateTime().getTime());
    assertTrue(firstCaption.getUri().contains("example1"));
    assertTrue(firstCaption.getIsDefault());
    VideoCaption secondCaption = captions.get(1);
    assertEquals("de_DE", secondCaption.getLocale());
    assertEquals(1501875685000L, secondCaption.getCreateTime().getTime());
    assertTrue(secondCaption.getUri().contains("example2"));
    assertFalse(secondCaption.getIsDefault());
  }

  @Test
  void checkv16_insights() {
    JsonMapper mapper = createConnectionJsonMapper();
    Video page = mapper.toJavaObject(jsonFromClasspath("v16_0/video-with-insights"), Video.class);
    assertNotNull(page);
    assertNotNull(page.getVideoInsights());
    assertEquals(3, page.getVideoInsights().getData().size());
    Insight insight0 = page.getVideoInsights().getData().get(0);
    assertEquals("lifetime", insight0.getPeriod());
    assertEquals("total_video_views", insight0.getName());
    assertEquals("987654321/video_insights/total_video_views/lifetime", insight0.getId());
    assertEquals(1, insight0.getValues().size());
    assertEquals(6, insight0.getValues().get(0).get("value").asInt());
  }

  @Test
  void checkv16_withThumbnails() {
    Video page = createConnectionJsonMapper().toJavaObject(jsonFromClasspath("v16_0/video-with-thumbnails"), Video.class);
    assertNotNull(page);
    assertNotNull(page.getThumbnails());
    List<Video.Thumbnail> thumbnailList = page.getThumbnails().getData();
    assertEquals(2, thumbnailList.size());
    Video.Thumbnail thumb = thumbnailList.get(0);
    assertEquals("thumbnail_url1", thumb.getUri());
    assertEquals(1.0F, thumb.getScale());
    assertEquals(1280, thumb.getWidth());
    assertEquals(1280, thumb.getHeight());
    assertFalse(thumb.getIsPreferred());
  }
}