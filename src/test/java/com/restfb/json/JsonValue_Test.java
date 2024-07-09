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

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
class JsonValue_Test {

  @Test
  void writeTo() throws IOException {
    JsonValue value = new JsonObject();
    Writer writer = new StringWriter();

    value.writeTo(writer);

    assertEquals("{}", writer.toString());
  }

  @Test
  void writeTo_failsWithNullWriter() {
    final JsonValue value = new JsonObject();
    assertThrows(NullPointerException.class, () -> value.writeTo(null, WriterConfig.MINIMAL), "writer is null");
  }

  @Test
  void writeTo_failsWithNullConfig() {
    final JsonValue value = new JsonObject();
    assertThrows(NullPointerException.class, () -> value.writeTo(new StringWriter(), null), "config is null");
  }

  @Test
  void toString_failsWithNullConfig() {
    final JsonValue value = new JsonObject();
    assertThrows(NullPointerException.class, () -> value.toString(null), "config is null");
  }

  @Test
  void writeTo_doesNotCloseWriter() throws IOException {
    JsonValue value = new JsonObject();
    StringWriter strWriter = new StringWriter() {
      @Override
      public void close() {
        fail("close must not be called here");
      }
    };

    value.writeTo(strWriter);
  }

  @Test
  void asObject_failsOnIncompatibleType() {
    assertThrows(UnsupportedOperationException.class, Json.NULL::asObject, "Not an object: null");
  }

  @Test
  void asArray_failsOnIncompatibleType() {
    assertThrows(UnsupportedOperationException.class, Json.NULL::asArray, "Not an array: null");
  }

  @Test
  void asString_failsOnIncompatibleType() {
    assertThrows(UnsupportedOperationException.class, Json.NULL::asString, "Not a string: null");
  }

  @Test
  void asInt_failsOnIncompatibleType() {
    assertThrows(UnsupportedOperationException.class, Json.NULL::asInt, "Not a number: null");
  }

  @Test
  void asLong_failsOnIncompatibleType() {
    assertThrows(UnsupportedOperationException.class, Json.NULL::asLong, "Not a number: null");
  }

  @Test
  void asFloat_failsOnIncompatibleType() {
    assertThrows(UnsupportedOperationException.class, Json.NULL::asFloat, "Not a number: null");
  }

  @Test
  void asDouble_failsOnIncompatibleType() {
    assertThrows(UnsupportedOperationException.class, Json.NULL::asDouble, "Not a number: null");
  }

  @Test
  void asBoolean_failsOnIncompatibleType() {
    assertThrows(UnsupportedOperationException.class, Json.NULL::asBoolean, "Not a boolean: null");
  }

  @Test
  void isXxx_returnsFalseForIncompatibleType() {
    JsonValue jsonValue = new JsonValue() {
      @Override
      void write(JsonWriter writer) throws IOException {}
    };

    assertFalse(jsonValue.isArray());
    assertFalse(jsonValue.isObject());
    assertFalse(jsonValue.isString());
    assertFalse(jsonValue.isNumber());
    assertFalse(jsonValue.isBoolean());
    assertFalse(jsonValue.isNull());
    assertFalse(jsonValue.isTrue());
    assertFalse(jsonValue.isFalse());
  }

}
