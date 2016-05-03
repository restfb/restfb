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

public class PageRatingTest extends AbstractJsonMapperTests {

  @Test
  public void check_2_2() {
    PageRating exampleRating =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/page-rating-example1"), PageRating.class);
    assertEquals(1448795001000L, exampleRating.getStartTime().getTime());
    assertEquals("123456789", exampleRating.getFrom().getId());
    assertEquals("Example Reviewer", exampleRating.getFrom().getName());
    assertEquals("Everything is nice here!", exampleRating.getMessage());

    assertNotNull(exampleRating.getComments());
    Comments comments = exampleRating.getComments();
    assertEquals("chronological", comments.getOrder());
    assertEquals(1L, comments.getTotalCount().longValue());

    assertNotNull(exampleRating.getLikes());
    Likes likes = exampleRating.getLikes();
    assertEquals(0L, likes.getTotalCount().longValue());
    assertTrue(likes.getCanLike());
    assertFalse(likes.getHasLiked());

    assertNotNull(exampleRating.getPlace());
    Place place = exampleRating.getPlace();
    assertEquals("349489628480201", place.getId());
    assertEquals("place", place.getType());

    assertNotNull(exampleRating.getApplication());
    Application appl = exampleRating.getApplication();
    assertEquals("Places", appl.getName());
    assertEquals("302324425790", appl.getId());
    assertEquals("https://www.facebook.com/games/?app_id=302324425790", appl.getLink());

    assertFalse(exampleRating.getIsDraft());
    assertEquals(3D, exampleRating.getRatingValue().doubleValue(), 00.1);
    assertEquals(5L, exampleRating.getRatingScale().intValue());
  }

  @Test
  public void check_2_5() {
    OpenGraphRating exampleOGRating =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/page-rating-example1"), OpenGraphRating.class);
    assertNotNull(exampleOGRating.getOpenGraphStory().getPlace());
    Place seller = exampleOGRating.getOpenGraphStory().getPlace();
    assertEquals("seller", seller.getType());
    assertEquals("149235290050", seller.getId());
  }
}
