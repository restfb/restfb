/**
 * Copyright (c) 2010-2017 Mark Allen, Norbert Bartels.
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

import java.util.Date;
import java.util.List;

public class PhotoTest extends AbstractJsonMapperTests {

  @Test
  public void checkDay_V1_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-day"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "day");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkYear_V1_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-year"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "year");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkMonth_V1_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-month"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "month");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkHour_V1_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-hour"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "hour");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkMinute_V1_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-min"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "min");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302720000L));
  }

  @Test
  public void checkNoBackdate_V1_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-nobackdate"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), null);
    assertEquals(examplePhoto.getBackdatedTime(), null);
  }

  @Test
  public void checkDay_V2_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-day"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "day");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkYear_V2_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-year"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "year");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkMonth_V2_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-month"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "month");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkHour_V2_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-hour"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "hour");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkMinute_V2_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-min"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "min");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302720000L));
  }

  @Test
  public void checkNoBackdate_V2_0() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-nobackdate"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), null);
    assertEquals(examplePhoto.getBackdatedTime(), null);
  }

  @Test
  public void checkDay_V2_1() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-day"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "day");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkYear_V2_1() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-year"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "year");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkMonth_V2_1() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-month"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "month");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkHour_V2_1() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-hour"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "hour");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
  }

  @Test
  public void checkMinute_V2_1() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-min"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), "min");
    assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302720000L));
  }

  @Test
  public void checkNoBackdate_V2_1() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-nobackdate"), Photo.class);
    assertEquals(examplePhoto.getBackdatedTimeGranularity(), null);
    assertEquals(examplePhoto.getBackdatedTime(), null);
  }

  @Test
  public void checkWithAlbum_V2_5() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/photo-withAlbum"), Photo.class);
    assertNotNull(examplePhoto.getAlbum());
    Album album = examplePhoto.getAlbum();
    assertEquals("Profile Pictures", album.getName());
    assertEquals("1234567890987", album.getId());
    assertEquals(1359978007000L, album.getCreatedTime().getTime());
  }

  @Test
  public void checkWithFlag_V2_5() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/photo-withAlbum"), Photo.class);
    assertNotNull(examplePhoto.getAlbum());
    assertTrue(examplePhoto.getCanDelete().booleanValue());
    assertTrue(examplePhoto.getCanTag().booleanValue());
  }

  @Test
  public void checkImages_V2_5() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/photo-withAlbum"), Photo.class);
    assertNotNull(examplePhoto.getAlbum());
    assertNotNull(examplePhoto.getImages());
    assertEquals(3, examplePhoto.getImages().size());
    Photo.Image image0 = examplePhoto.getImages().get(0);
    assertEquals(246, image0.getHeight().intValue());
    assertEquals(246, image0.getWidth().intValue());
    Photo.Image image1 = examplePhoto.getImages().get(1);
    assertEquals(130, image1.getHeight().intValue());
    assertEquals(130, image1.getWidth().intValue());
    Photo.Image image2 = examplePhoto.getImages().get(2);
    assertEquals(225, image2.getHeight().intValue());
    assertEquals(225, image2.getWidth().intValue());
  }

  @Test
  public void checkFrom_V2_5() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/photo-withAlbum"), Photo.class);
    assertNotNull(examplePhoto.getAlbum());
    assertEquals("1234567890", examplePhoto.getFrom().getId());
  }

  @Test
  public void checkNameTags_V2_5() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/photo-withAlbum"), Photo.class);
    assertNotNull(examplePhoto.getNameTags());
    List<EntityAtTextRange> nameTags = examplePhoto.getNameTags();
    assertEquals("88388366982", nameTags.get(0).getId());
    assertEquals("Público", nameTags.get(0).getName());
  }

  @Test
  public void photoComments_V2_5() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/photo-comments"), Photo.class);
    assertNotNull(examplePhoto.getNameTags());
    Comments comments = examplePhoto.getComments();
    assertNotNull(comments);
    assertEquals(1L, comments.getTotalCount().longValue());
    assertTrue(comments.getCanComment());
    Comment c = comments.getData().get(0);
    assertEquals("Whooo, great Picture", c.getMessage());
  }

  @Test
  public void photoLikes_V2_5() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/photo-comments"), Photo.class);
    assertNotNull(examplePhoto.getLikes());
    Likes likes = examplePhoto.getLikes();
    assertTrue(likes.getCanLike());
    assertFalse(likes.getHasLiked());
    assertEquals(0L, likes.getTotalCount().longValue());
  }
}
