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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class SearchPlaceTest extends AbstractJsonMapperTests {

  @Test
  void checkPhotos() {
    SearchPlace exampleSearch =
        createJsonMapper().toJavaObject(jsonFromClasspath("v3_1/searchplace-photos"), SearchPlace.class);
    assertNotNull(exampleSearch);
    assertNotNull(exampleSearch.getPhotos());
    assertEquals(4, exampleSearch.getPhotos().size());
    assertEquals(33597L, exampleSearch.getCheckins().longValue());
    assertNotNull(exampleSearch.getPicture());
  }

  @Test
  void checkDefault() {
    SearchPlace examplesearch =
        createJsonMapper().toJavaObject(jsonFromClasspath("v3_1/searchplace-default"), SearchPlace.class);
    assertNotNull(examplesearch);
    assertNotNull(examplesearch.getPhotos());
    assertEquals(4, examplesearch.getPhotos().size());
    assertEquals(6345L, examplesearch.getCheckins().longValue());
    assertNotNull(examplesearch.getPicture());
    assertEquals("(212) 253-1046", examplesearch.getPhone());
    assertEquals(4.3, examplesearch.getOverallStarRating().doubleValue());
    assertEquals("$$", examplesearch.getPriceRange());
    assertNotNull(examplesearch.getRestaurantServices());
  }
}
