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

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

import java.util.List;

public class AlbumTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_5_comments() {
    Album exampleAlbum = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/album-with-comments"), Album.class);
    assertNotNull(exampleAlbum);
    assertNotNull(exampleAlbum.getComments());
    List<Comment> comments = exampleAlbum.getComments().getData();
    assertEquals(1, comments.size());
    Comment exampleComment = comments.get(0);
    assertEquals("521525131216804",exampleComment.getFrom().getId());
    assertNotNull(exampleComment.getMessage());
  }

  @Test
  public void checkV2_5_type() {
    Album exampleAlbum = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/album-with-comments"), Album.class);
    assertNotNull(exampleAlbum);
    assertEquals("wall", exampleAlbum.getType());
  }

  @Test
  public void checkV2_3_cover() {
    Album exampleAlbum = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/album-coverphoto"), Album.class);
    assertNotNull(exampleAlbum);
    assertNotNull(exampleAlbum.getCoverPhoto());
    Photo coverPhoto = exampleAlbum.getCoverPhoto();
    assertEquals("1234567890123456", coverPhoto.getId());
  }

  @Test
  public void checkV2_4_cover() {
    Album exampleAlbum = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/album-coverphoto"), Album.class);
    assertNotNull(exampleAlbum);
    assertNotNull(exampleAlbum.getCoverPhoto());
    Photo coverPhoto = exampleAlbum.getCoverPhoto();
    assertEquals("1234567890123456", coverPhoto.getId());
    assertEquals("A name!", coverPhoto.getName());
  }

}
