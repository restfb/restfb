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

import static com.restfb.json.Json.*;
import static com.restfb.json.TestUtil.serializeAndDeserialize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;


class JsonLiteral_Test {

  @Test
  void isNull() {
    assertTrue(NULL.isNull());

    assertFalse(TRUE.isNull());
    assertFalse(FALSE.isNull());
  }

  @Test
  void isTrue() {
    assertTrue(TRUE.isTrue());

    assertFalse(NULL.isTrue());
    assertFalse(FALSE.isTrue());
  }

  @Test
  void isFalse() {
    assertTrue(FALSE.isFalse());

    assertFalse(NULL.isFalse());
    assertFalse(TRUE.isFalse());
  }

  @Test
  void isBoolean() {
    assertTrue(TRUE.isBoolean());
    assertTrue(FALSE.isBoolean());

    assertFalse(NULL.isBoolean());
  }

  @Test
  void NULL_write() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);

    NULL.write(writer);

    verify(writer).writeLiteral("null");
    verifyNoMoreInteractions(writer);
  }

  @Test
  void TRUE_write() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);

    TRUE.write(writer);

    verify(writer).writeLiteral("true");
    verifyNoMoreInteractions(writer);
  }

  @Test
  void FALSE_write() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);

    FALSE.write(writer);

    verify(writer).writeLiteral("false");
    verifyNoMoreInteractions(writer);
  }

  @Test
  void NULL_toString() {
    assertEquals("null", NULL.toString());
  }

  @Test
  void TRUE_toString() {
    assertEquals("true", TRUE.toString());
  }

  @Test
  void FALSE_toString() {
    assertEquals("false", FALSE.toString());
  }

  @Test
  void NULL_equals() {
    assertTrue(NULL.equals(NULL));

    assertFalse(NULL.equals(null));
    assertFalse(NULL.equals(TRUE));
    assertFalse(NULL.equals(FALSE));
    assertFalse(NULL.equals(Json.value("null")));
  }

  @Test
  void TRUE_equals() {
    assertTrue(TRUE.equals(TRUE));

    assertFalse(TRUE.equals(null));
    assertFalse(TRUE.equals(FALSE));
    assertFalse(TRUE.equals(Boolean.TRUE));
    assertFalse(NULL.equals(Json.value("true")));
  }

  @Test
  void FALSE_equals() {
    assertTrue(FALSE.equals(FALSE));

    assertFalse(FALSE.equals(null));
    assertFalse(FALSE.equals(TRUE));
    assertFalse(FALSE.equals(Boolean.FALSE));
    assertFalse(NULL.equals(Json.value("false")));
  }

  @Test
  void NULL_isSerializable() throws Exception {
    assertEquals(NULL, serializeAndDeserialize(NULL));
    assertTrue(serializeAndDeserialize(NULL).isNull());
  }

  @Test
  void TRUE_isSerializable() throws Exception {
    assertEquals(TRUE, serializeAndDeserialize(TRUE));
    assertTrue(serializeAndDeserialize(TRUE).isBoolean());
    assertTrue(serializeAndDeserialize(TRUE).isTrue());
  }

  @Test
  void FALSE_isSerializable() throws Exception {
    assertEquals(FALSE, serializeAndDeserialize(FALSE));
    assertTrue(serializeAndDeserialize(FALSE).isBoolean());
    assertTrue(serializeAndDeserialize(FALSE).isFalse());
  }

  @Test
  void sameAfterDeserialization() throws Exception {
    JsonArray array = new JsonArray().add(NULL).add(NULL);

    JsonArray deserialized = serializeAndDeserialize(array);

    assertNotSame(NULL, deserialized.get(0));
    assertSame(deserialized.get(0), deserialized.get(1));
  }

}
