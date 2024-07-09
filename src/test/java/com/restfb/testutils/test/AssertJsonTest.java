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
package com.restfb.testutils.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.restfb.json.Json;
import com.restfb.json.JsonValue;
import com.restfb.testutils.AssertJson;

class AssertJsonTest {

  @Test
  void check_Boolean_bothFalse() {
    JsonValue expectedFalseValue = Json.FALSE;
    JsonValue actualFalseValue = Json.FALSE;

    AssertJson.assertEquals(expectedFalseValue.toString(), actualFalseValue.toString());
  }

  @Test
  void check_Boolean_differentBoolean() {
    JsonValue expectedFalseValue = Json.FALSE;
    JsonValue actualFalseValue = Json.TRUE;

    assertThrows(AssertionError.class,
      () -> AssertJson.assertEquals(expectedFalseValue.toString(), actualFalseValue.toString()));
  }

  @Test
  void check_Double_both() {
    JsonValue expectedFalseValue = Json.parse("1.0");
    JsonValue actualFalseValue = Json.parse("1.0");

    AssertJson.assertEquals(expectedFalseValue.toString(), actualFalseValue.toString());
  }

  @Test
  void check_String_both() {
    JsonValue expectedFalseValue = Json.value("Test");
    JsonValue actualFalseValue = Json.value("Test");

    AssertJson.assertEquals(expectedFalseValue.toString(), actualFalseValue.toString());
  }

  @ParameterizedTest(name = "[{index}] {0}")
  @MethodSource("provideJsonWithExc")
  void check_Object_checkExc(String expectedString, String actualString) {
    assertThrows(AssertionError.class, () -> AssertJson.assertEquals(expectedString, actualString));
  }

  private static Stream<Arguments> provideJsonWithExc() {
    return Stream.of(
      Arguments.of(Named.of("check_Array_differentOrder", "[\"String1\",\"String2\",\"String3\"]"),
        "[\"String2\",\"String3\",\"String1\"]"),
      Arguments.of(Named.of("check_Object_differentFields", "{}"), "{\"id\":345}"),
      Arguments.of(Named.of("check_Object_Array", "{}"), "[]"));
  }

  @ParameterizedTest(name = "[{index}] {0}")
  @MethodSource("provideJson")
  void check_Object_param(String expectedString, String actualString) {
    AssertJson.assertEquals(expectedString, actualString);
  }

  private static Stream<Arguments> provideJson() {
    return Stream.of(
      Arguments.of(Named.of("check_Object_both", "{\"name\":\"test\",\"id\":345,\"blub\":\"bla\"}"),
        "{\"name\":\"test\",\"id\":345,\"blub\":\"bla\"}"),
      Arguments.of(Named.of("check_Object_differentOrder", "{\"name\":\"test\",\"id\":345,\"blub\":\"bla\"}"),
        "{\"id\":345,\"name\":\"test\",\"blub\":\"bla\"}"),
      Arguments.of(Named.of("check_Array_both", "[\"String1\",\"String2\",\"String3\"]"),
        "[\"String1\",\"String2\",\"String3\"]"));
  }
}
