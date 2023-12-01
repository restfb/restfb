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
package com.restfb;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
class SerializationTest {

  @Test
  void jsonArray() throws Exception {
    JsonArray arrayToSerialize = new JsonArray();
    arrayToSerialize.add(123);
    arrayToSerialize.add("test");

    JsonArray deserializedArray = (JsonArray) serializeAndDeserialize(arrayToSerialize);

    assertThat(deserializedArray.get(0).asInt()).isEqualTo(123);
    assertThat(deserializedArray.get(1).asString()).isEqualTo("test");
  }

  @Test
  void jsonObject() throws Exception {
    JsonObject objectToSerialize = new JsonObject();
    objectToSerialize.add("one", 123);
    objectToSerialize.add("two", "test");

    JsonObject deserializedObject = (JsonObject) serializeAndDeserialize(objectToSerialize);

    assertThat(deserializedObject.get("one").asInt()).isEqualTo(123);
    assertThat(deserializedObject.get("two").asString()).isEqualTo("test");
  }

  protected Object serializeAndDeserialize(Object objectToSerialize) throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(baos);
    out.writeObject(objectToSerialize);
    out.flush();
    out.close();

    ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
    Object deserializedObject = in.readObject();
    in.close();

    return deserializedObject;
  }
}