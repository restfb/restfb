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
package com.restfb.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EncodingUtilsRfcTest {

  private static Stream<Arguments> testData() {
    return Stream.of(Arguments.of("", ""), //
      Arguments.of("Zg==", "f"), //
      Arguments.of("Zg", "f"), //
      Arguments.of("Zg=", "f"), //
      Arguments.of("Zm8=", "fo"), //
      Arguments.of("Zm9v", "foo"), //
      Arguments.of("Zm9vYg==", "foob"), //
      Arguments.of("Zm9vYmE=", "fooba"), //
      Arguments.of("Zm9vYmFy", "foobar"));
  }

  @ParameterizedTest
  @MethodSource("testData")
  void base64Decode(String base64EncodedString, String decodedString) {
    try {
      byte[] decoded = EncodingUtils.decodeBase64(base64EncodedString);
      assertThat(decoded).isEqualTo(decodedString.getBytes());
    } catch (IllegalArgumentException iae) {
      fail("Cannot decode string: " + base64EncodedString);
    }
  }

}
