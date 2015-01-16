/*
 * Copyright (c) 2010-2015 Mark Allen.
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

package com.restfb.json;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class SerializationTests {
  @Test
  public void jsonArray() throws Exception {
    JsonArray arrayToSerialize = new JsonArray();
    arrayToSerialize.put(123);
    arrayToSerialize.put("test");

    JsonArray deserializedArray = (JsonArray) serializeAndDeserialize(arrayToSerialize);

    assertEquals(123, deserializedArray.getInt(0));
    assertEquals("test", deserializedArray.getString(1));
  }

  @Test
  public void jsonObject() throws Exception {
    JsonObject objectToSerialize = new JsonObject();
    objectToSerialize.put("one", 123);
    objectToSerialize.put("two", "test");

    JsonObject deserializedObject = (JsonObject) serializeAndDeserialize(objectToSerialize);

    assertEquals(123, deserializedObject.getInt("one"));
    assertEquals("test", deserializedObject.getString("two"));
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