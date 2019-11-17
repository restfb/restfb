/*******************************************************************************
 * Copyright (c) 2015 EclipseSource.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.restfb.json;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

class Json_Test {

  @Test
  void literalConstants() {
    assertTrue(Json.NULL.isNull());
    assertTrue(Json.TRUE.isTrue());
    assertTrue(Json.FALSE.isFalse());
  }

  @Test
  void value_int() {
    assertEquals("0", Json.value(0).toString());
    assertEquals("23", Json.value(23).toString());
    assertEquals("-1", Json.value(-1).toString());
    assertEquals("2147483647", Json.value(Integer.MAX_VALUE).toString());
    assertEquals("-2147483648", Json.value(Integer.MIN_VALUE).toString());
  }

  @Test
  void value_long() {
    assertEquals("0", Json.value(0L).toString());
    assertEquals("9223372036854775807", Json.value(Long.MAX_VALUE).toString());
    assertEquals("-9223372036854775808", Json.value(Long.MIN_VALUE).toString());
  }

  @Test
  void value_float() {
    assertEquals("23.5", Json.value(23.5f).toString());
    assertEquals("-3.1416", Json.value(-3.1416f).toString());
    assertEquals("1.23E-6", Json.value(0.00000123f).toString());
    assertEquals("-1.23E7", Json.value(-12300000f).toString());
  }

  @Test
  void value_float_cutsOffPointZero() {
    assertEquals("0", Json.value(0f).toString());
    assertEquals("-1", Json.value(-1f).toString());
    assertEquals("10", Json.value(10f).toString());
  }

  @Test
  void value_float_failsWithInfinity() {
    String message = "Infinite and NaN values not permitted in JSON";
    assertThrows(IllegalArgumentException.class, () -> Json.value(Float.POSITIVE_INFINITY), message);
  }

  @Test
  void value_float_failsWithNaN() {
    String message = "Infinite and NaN values not permitted in JSON";
    assertThrows(IllegalArgumentException.class, () -> Json.value(Float.NaN), message);
  }

  @Test
  void value_double() {
    assertEquals("23.5", Json.value(23.5d).toString());
    assertEquals("3.1416", Json.value(3.1416d).toString());
    assertEquals("1.23E-6", Json.value(0.00000123d).toString());
    assertEquals("1.7976931348623157E308", Json.value(1.7976931348623157E308d).toString());
  }

  @Test
  void value_double_cutsOffPointZero() {
    assertEquals("0", Json.value(0d).toString());
    assertEquals("-1", Json.value(-1d).toString());
    assertEquals("10", Json.value(10d).toString());
  }

  @Test
  void value_double_failsWithInfinity() {
    String message = "Infinite and NaN values not permitted in JSON";
    assertThrows(IllegalArgumentException.class, () -> Json.value(Double.POSITIVE_INFINITY), message);
  }

  @Test
  void value_double_failsWithNaN() {
    String message = "Infinite and NaN values not permitted in JSON";
    assertThrows(IllegalArgumentException.class, () -> Json.value(Double.NaN), message);
  }

  @Test
  void value_boolean() {
    assertSame(Json.TRUE, Json.value(true));
    assertSame(Json.FALSE, Json.value(false));
  }

  @Test
  void value_string() {
    assertEquals("", Json.value("").asString());
    assertEquals("Hello", Json.value("Hello").asString());
    assertEquals("\"Hello\"", Json.value("\"Hello\"").asString());
  }

  @Test
  void value_string_toleratesNull() {
    assertSame(Json.NULL, Json.value(null));
  }

  @Test
  void array() {
    assertEquals(new JsonArray(), Json.array());
  }

  @Test
  void array_int() {
    assertEquals(new JsonArray().add(23), Json.array(23));
    assertEquals(new JsonArray().add(23).add(42), Json.array(23, 42));
  }

  @Test
  void array_int_failsWithNull() {
    assertThrows(NullPointerException.class, () -> Json.array((int[]) null), "values is null");
  }

  @Test
  void array_long() {
    assertEquals(new JsonArray().add(23L), Json.array(23L));
    assertEquals(new JsonArray().add(23L).add(42L), Json.array(23L, 42L));
  }

  @Test
  void array_long_failsWithNull() {
    assertThrows(NullPointerException.class, () -> Json.array((long[]) null), "values is null");
  }

  @Test
  void array_float() {
    assertEquals(new JsonArray().add(3.14f), Json.array(3.14f));
    assertEquals(new JsonArray().add(3.14f).add(1.41f), Json.array(3.14f, 1.41f));
  }

  @Test
  void array_float_failsWithNull() {
    assertThrows(NullPointerException.class, () -> Json.array((float[]) null), "values is null");
  }

  @Test
  void array_double() {
    assertEquals(new JsonArray().add(3.14d), Json.array(3.14d));
    assertEquals(new JsonArray().add(3.14d).add(1.41d), Json.array(3.14d, 1.41d));
  }

  @Test
  void array_double_failsWithNull() {
    assertThrows(NullPointerException.class, () -> Json.array((double[]) null), "values is null");
  }

  @Test
  void array_boolean() {
    assertEquals(new JsonArray().add(true), Json.array(true));
    assertEquals(new JsonArray().add(true).add(false), Json.array(true, false));
  }

  @Test
  void array_boolean_failsWithNull() {
    assertThrows(NullPointerException.class, () -> Json.array((boolean[]) null), "values is null");
  }

  @Test
  void array_string() {
    assertEquals(new JsonArray().add("foo"), Json.array("foo"));
    assertEquals(new JsonArray().add("foo").add("bar"), Json.array("foo", "bar"));
  }

  @Test
  void array_string_failsWithNull() {
    assertThrows(NullPointerException.class, () -> Json.array((String[]) null), "values is null");
  }

  @Test
  void object() {
    assertEquals(new JsonObject(), Json.object());
  }

  @Test
  void parse_string() {
    assertEquals(Json.value(23), Json.parse("23"));
  }

  @Test
  void parse_string_failsWithNull() {
    assertThrows(NullPointerException.class, () -> Json.parse((String) null), "string is null");
  }

  @Test
  void parse_reader() throws IOException {
    Reader reader = new StringReader("23");

    assertEquals(Json.value(23), Json.parse(reader));
  }

  @Test
  void parse_reader_failsWithNull() {
    assertThrows(NullPointerException.class, () -> Json.parse((Reader) null), "reader is null");
  }

}
