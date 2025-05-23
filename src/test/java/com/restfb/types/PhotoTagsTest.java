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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.restfb.AbstractJsonMapperTests;

class PhotoTagsTest extends AbstractJsonMapperTests {

  @DisplayName("check no backdate on PhotoTags")
  @ParameterizedTest(name = "{index} ==> version ''{0}''")
  @MethodSource("jsonSupplier")
  void checkNoBackdate(String version) {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath(version + "/photo-nobackdate"), Photo.class);
    assertEquals(1, examplePhoto.getTags().size());
    assertEquals(46.428571428571, examplePhoto.getTags().get(0).getX().doubleValue());
    assertEquals(53.571428571429, examplePhoto.getTags().get(0).getY().doubleValue());
  }

  private static Stream<String> jsonSupplier() {
    return Stream.of("v1_0", "v2_0", "v2_1");
  }

}
