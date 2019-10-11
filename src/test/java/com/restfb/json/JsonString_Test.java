/*******************************************************************************
 * Copyright (c) 2013, 2015 EclipseSource.
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

import static com.restfb.json.TestUtil.serializeAndDeserialize;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonString_Test {

  private StringWriter stringWriter;
  private JsonWriter jsonWriter;

  @BeforeEach
  void setUp() {
    stringWriter = new StringWriter();
    jsonWriter = new JsonWriter(stringWriter);
  }

  @Test
  void constructor_failsWithNull() {
    assertThrows(NullPointerException.class, () -> new JsonString(null), "string is null");
  }

  @Test
  void write() throws IOException {
    new JsonString("foo").write(jsonWriter);

    assertEquals("\"foo\"", stringWriter.toString());
  }

  @Test
  void write_escapesStrings() throws IOException {
    new JsonString("foo\\bar").write(jsonWriter);

    assertEquals("\"foo\\\\bar\"", stringWriter.toString());
  }

  @Test
  void isString() {
    assertTrue(new JsonString("foo").isString());
  }

  @Test
  void asString() {
    assertEquals("foo", new JsonString("foo").asString());
  }

  @Test
  void equals_trueForSameInstance() {
    JsonString string = new JsonString("foo");

    assertTrue(string.equals(string));
  }

  @Test
  void equals_trueForEqualStrings() {
    assertTrue(new JsonString("foo").equals(new JsonString("foo")));
  }

  @Test
  void equals_falseForDifferentStrings() {
    assertFalse(new JsonString("").equals(new JsonString("foo")));
    assertFalse(new JsonString("foo").equals(new JsonString("bar")));
  }

  @Test
  void equals_falseForNull() {
    assertFalse(new JsonString("foo").equals(null));
  }

  @Test
  void equals_falseForSubclass() {
    assertFalse(new JsonString("foo").equals(new JsonString("foo") {}));
  }

  @Test
  void hashCode_equalsForEqualStrings() {
    assertTrue(new JsonString("foo").hashCode() == new JsonString("foo").hashCode());
  }

  @Test
  void hashCode_differsForDifferentStrings() {
    assertFalse(new JsonString("").hashCode() == new JsonString("foo").hashCode());
    assertFalse(new JsonString("foo").hashCode() == new JsonString("bar").hashCode());
  }

  @Test
  void canBeSerializedAndDeserialized() throws Exception {
    JsonString string = new JsonString("foo");

    assertEquals(string, serializeAndDeserialize(string));
  }

}
