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

import static com.restfb.util.UrlUtils.extractParametersFromQueryString;
import static com.restfb.util.UrlUtils.extractParametersFromUrl;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit tests that exercise {@link com.restfb.util.UrlUtils}.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
class UrlUtilsTest {

  @Test
  void urlEncode_null() {
    assertThat(UrlUtils.urlEncode(null)).isNull();
  }

  @Test
  void urlEncode_string() {
    assertThat(UrlUtils.urlEncode("This is a string")).isEqualTo("This+is+a+string");
  }

  @Test
  void urlDecode_null() {
    assertThat(UrlUtils.urlDecode(null)).isNull();
  }

  @Test
  void urlDecode_string() {
    assertThat(UrlUtils.urlDecode("This+is+a+string")).isEqualTo("This is a string");
  }

  @Test
  void queryString() {
    assertThat(extractParametersFromQueryString(null)).isEmpty();
    assertThat(extractParametersFromQueryString("")).isEmpty();
    assertThat(extractParametersFromQueryString("access_token=123")).hasSize(1);
    assertThat(extractParametersFromQueryString("?access_token=123")).hasSize(1);
  }

  @Test
  void urlParameters() {
    assertThat(extractParametersFromUrl(null)).isEmpty();
    assertThat(extractParametersFromUrl("")).isEmpty();
    assertThat(extractParametersFromUrl("access_token=123")).isEmpty();
    assertThat(extractParametersFromUrl("?access_token=123")).hasSize(1);
    assertThat(extractParametersFromUrl("http://whatever?access_token=123")).hasSize(1);
  }

  @ParameterizedTest(name = "{index} {0}")
  @MethodSource("provideRemoveUrls")
  void removeParameter(String exampleURL, String key, String expectedURL) {
    String resultURL = UrlUtils.removeQueryParameter(exampleURL, key);
    assertThat(resultURL).isEqualTo(expectedURL);
  }

  private static Stream<Arguments> provideRemoveUrls() {
    return Stream.of(
      Arguments.of(Named.of("lastParameter", "http://www.example.com?access_token=123&before=1234"), "before",
        "http://www.example.com?access_token=123"),
      Arguments.of(Named.of("firstParameter", "http://www.example.com?access_token=123&before=1234"), "access_token",
        "http://www.example.com?before=1234"),
      Arguments.of(Named.of("middleParameter", "http://www.example.com?access_token=123&first=6543&before=1234"),
        "first", "http://www.example.com?access_token=123&before=1234"));
  }

  @ParameterizedTest(name = "{index} {0}")
  @MethodSource("provideReplaceUrls")
  void replaceOrAddParameter(String exampleURL, String expectedURL) {
    String resultURL = UrlUtils.replaceOrAddQueryParameter(exampleURL, "before", "56789");
    assertThat(resultURL).isEqualTo(expectedURL);
  }

  private static Stream<Arguments> provideReplaceUrls() {
    return Stream.of(
      Arguments.of(Named.of("replace", "http://www.example.com?access_token=123&before=1234"),
        "http://www.example.com?access_token=123&before=56789"),
      Arguments.of(Named.of("addParameterNoParameter", "http://www.example.com"),
        "http://www.example.com?before=56789"),
      Arguments.of(Named.of("addParameter", "http://www.example.com?access_token=123"),
        "http://www.example.com?access_token=123&before=56789"));
  }
}