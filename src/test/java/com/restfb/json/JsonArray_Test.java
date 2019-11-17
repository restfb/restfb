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
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class JsonArray_Test {

  private JsonArray array;

  @BeforeEach
  void setUp() {
    array = new JsonArray();
  }

  @Test
  void copyConstructor_failsWithNull() {
    assertThrows(NullPointerException.class, () -> new JsonArray(null), "array is null");
  }

  @Test
  void copyConstructor_hasSameValues() {
    array.add(23);
    JsonArray copy = new JsonArray(array);

    assertEquals(array.values(), copy.values());
  }

  @Test
  void copyConstructor_worksOnSafeCopy() {
    JsonArray copy = new JsonArray(array);
    array.add(23);

    assertTrue(copy.isEmpty());
  }

  @Test
  void unmodifiableArray_hasSameValues() {
    array.add(23);
    JsonArray unmodifiableArray = JsonArray.unmodifiableArray(array);

    assertEquals(array.values(), unmodifiableArray.values());
  }

  @Test
  void unmodifiableArray_reflectsChanges() {
    JsonArray unmodifiableArray = JsonArray.unmodifiableArray(array);
    array.add(23);

    assertEquals(array.values(), unmodifiableArray.values());
  }

  @Test
  void unmodifiableArray_preventsModification() {
    JsonArray unmodifiableArray = JsonArray.unmodifiableArray(array);

    assertThrows(UnsupportedOperationException.class, () -> {
      unmodifiableArray.add(23);
    });
  }

  @Test
  void isEmpty_isTrueAfterCreation() {
    assertTrue(array.isEmpty());
  }

  @Test
  void isEmpty_isFalseAfterAdd() {
    array.add(true);

    assertFalse(array.isEmpty());
  }

  @Test
  void size_isZeroAfterCreation() {
    assertEquals(0, array.size());
  }

  @Test
  void size_isOneAfterAdd() {
    array.add(true);

    assertEquals(1, array.size());
  }

  @Test
  void iterator_isEmptyAfterCreation() {
    assertFalse(array.iterator().hasNext());
  }

  @Test
  void iterator_hasNextAfterAdd() {
    array.add(true);

    Iterator<JsonValue> iterator = array.iterator();
    assertTrue(iterator.hasNext());
    assertEquals(Json.TRUE, iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  void iterator_doesNotAllowModification() {
    assertThrows(UnsupportedOperationException.class, () -> {
      array.add(23);
      Iterator<JsonValue> iterator = array.iterator();
      iterator.next();
      iterator.remove();
    });
  }

  @Test
  void iterator_detectsConcurrentModification() {
    assertThrows(ConcurrentModificationException.class, () -> {
      Iterator<JsonValue> iterator = array.iterator();
      array.add(23);
      iterator.next();
    });
  }

  @Test
  void values_isEmptyAfterCreation() {
    assertTrue(array.values().isEmpty());
  }

  @Test
  void values_containsValueAfterAdd() {
    array.add(true);

    assertEquals(1, array.values().size());
    assertEquals(Json.TRUE, array.values().get(0));
  }

  @Test
  void values_reflectsChanges() {
    List<JsonValue> values = array.values();

    array.add(true);

    assertEquals(array.values(), values);
  }

  @Test
  void values_preventsModification() {
    List<JsonValue> values = array.values();

    assertThrows(UnsupportedOperationException.class, () -> {
      values.add(Json.TRUE);
    });
  }

  @Test
  void get_returnsValue() {
    array.add(23);

    JsonValue value = array.get(0);

    assertEquals(Json.value(23), value);
  }

  @Test
  void get_failsWithInvalidIndex() {
    assertThrows(IndexOutOfBoundsException.class, () -> {
      array.get(0);
    });
  }

  @Test
  void add_int() {
    array.add(23);

    assertEquals("[23]", array.toString());
  }

  @Test
  void add_int_enablesChaining() {
    assertSame(array, array.add(23));
  }

  @Test
  void add_long() {
    array.add(23L);

    assertEquals("[23]", array.toString());
  }

  @Test
  void add_long_enablesChaining() {
    assertSame(array, array.add(23L));
  }

  @Test
  void add_float() {
    array.add(3.14f);

    assertEquals("[3.14]", array.toString());
  }

  @Test
  void add_float_enablesChaining() {
    assertSame(array, array.add(3.14f));
  }

  @Test
  void add_double() {
    array.add(3.14d);

    assertEquals("[3.14]", array.toString());
  }

  @Test
  void add_double_enablesChaining() {
    assertSame(array, array.add(3.14d));
  }

  @Test
  void add_boolean() {
    array.add(true);

    assertEquals("[true]", array.toString());
  }

  @Test
  void add_boolean_enablesChaining() {
    assertSame(array, array.add(true));
  }

  @Test
  void add_string() {
    array.add("foo");

    assertEquals("[\"foo\"]", array.toString());
  }

  @Test
  void add_string_enablesChaining() {
    assertSame(array, array.add("foo"));
  }

  @Test
  void add_string_toleratesNull() {
    array.add((String) null);

    assertEquals("[null]", array.toString());
  }

  @Test
  void add_jsonNull() {
    array.add(Json.NULL);

    assertEquals("[null]", array.toString());
  }

  @Test
  void add_jsonArray() {
    array.add(new JsonArray());

    assertEquals("[[]]", array.toString());
  }

  @Test
  void add_jsonObject() {
    array.add(new JsonObject());

    assertEquals("[{}]", array.toString());
  }

  @Test
  void add_json_enablesChaining() {
    assertSame(array, array.add(Json.NULL));
  }

  @Test
  void add_json_failsWithNull() {
    assertThrows(NullPointerException.class, () -> array.add((JsonValue) null), "value is null");
  }

  @Test
  void add_json_nestedArray() {
    JsonArray innerArray = new JsonArray();
    innerArray.add(23);

    array.add(innerArray);

    assertEquals("[[23]]", array.toString());
  }

  @Test
  void add_json_nestedArray_modifiedAfterAdd() {
    JsonArray innerArray = new JsonArray();
    array.add(innerArray);

    innerArray.add(23);

    assertEquals("[[23]]", array.toString());
  }

  @Test
  void add_json_nestedObject() {
    JsonObject innerObject = new JsonObject();
    innerObject.add("a", 23);

    array.add(innerObject);

    assertEquals("[{\"a\":23}]", array.toString());
  }

  @Test
  void add_json_nestedObject_modifiedAfterAdd() {
    JsonObject innerObject = new JsonObject();
    array.add(innerObject);

    innerObject.add("a", 23);

    assertEquals("[{\"a\":23}]", array.toString());
  }

  @Test
  void set_int() {
    array.add(false);
    array.set(0, 23);

    assertEquals("[23]", array.toString());
  }

  @Test
  void set_int_enablesChaining() {
    array.add(false);

    assertSame(array, array.set(0, 23));
  }

  @Test
  void set_long() {
    array.add(false);

    array.set(0, 23L);

    assertEquals("[23]", array.toString());
  }

  @Test
  void set_long_enablesChaining() {
    array.add(false);

    assertSame(array, array.set(0, 23L));
  }

  @Test
  void set_float() {
    array.add(false);

    array.set(0, 3.14f);

    assertEquals("[3.14]", array.toString());
  }

  @Test
  void set_float_enablesChaining() {
    array.add(false);

    assertSame(array, array.set(0, 3.14f));
  }

  @Test
  void set_double() {
    array.add(false);

    array.set(0, 3.14d);

    assertEquals("[3.14]", array.toString());
  }

  @Test
  void set_double_enablesChaining() {
    array.add(false);

    assertSame(array, array.set(0, 3.14d));
  }

  @Test
  void set_boolean() {
    array.add(false);

    array.set(0, true);

    assertEquals("[true]", array.toString());
  }

  @Test
  void set_boolean_enablesChaining() {
    array.add(false);

    assertSame(array, array.set(0, true));
  }

  @Test
  void set_string() {
    array.add(false);

    array.set(0, "foo");

    assertEquals("[\"foo\"]", array.toString());
  }

  @Test
  void set_string_enablesChaining() {
    array.add(false);

    assertSame(array, array.set(0, "foo"));
  }

  @Test
  void set_jsonNull() {
    array.add(false);

    array.set(0, Json.NULL);

    assertEquals("[null]", array.toString());
  }

  @Test
  void set_jsonArray() {
    array.add(false);

    array.set(0, new JsonArray());

    assertEquals("[[]]", array.toString());
  }

  @Test
  void set_jsonObject() {
    array.add(false);

    array.set(0, new JsonObject());

    assertEquals("[{}]", array.toString());
  }

  @Test
  void set_json_failsWithNull() {
    array.add(false);

    assertThrows(NullPointerException.class, () -> {
      array.set(0, (JsonValue) null);
    }, "value is null");
  }

  @Test
  void set_json_failsWithInvalidIndex() {
    assertThrows(IndexOutOfBoundsException.class, () -> {
      array.set(0, Json.NULL);
    });
  }

  @Test
  void set_json_enablesChaining() {
    array.add(false);

    assertSame(array, array.set(0, Json.NULL));
  }

  @Test
  void set_json_replacesDifferentArrayElements() {
    array.add(3).add(6).add(9);

    array.set(1, 4).set(2, 5);

    assertEquals("[3,4,5]", array.toString());
  }

  @Test
  void remove_failsWithInvalidIndex() {
    assertThrows(IndexOutOfBoundsException.class, () -> {
      array.remove(0);
    });
  }

  @Test
  void remove_removesElement() {
    array.add(23);

    array.remove(0);

    assertEquals("[]", array.toString());
  }

  @Test
  void remove_keepsOtherElements() {
    array.add("a").add("b").add("c");

    array.remove(1);

    assertEquals("[\"a\",\"c\"]", array.toString());
  }

  @Test
  void write_empty() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    array.write(writer);

    InOrder inOrder = inOrder(writer);
    inOrder.verify(writer).writeArrayOpen();
    inOrder.verify(writer).writeArrayClose();
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  void write_withSingleValue() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    array.add(23);

    array.write(writer);

    InOrder inOrder = inOrder(writer);
    inOrder.verify(writer).writeArrayOpen();
    inOrder.verify(writer).writeNumber("23");
    inOrder.verify(writer).writeArrayClose();
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  void write_withMultipleValues() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    array.add(23).add("foo").add(false);

    array.write(writer);

    InOrder inOrder = inOrder(writer);
    inOrder.verify(writer).writeArrayOpen();
    inOrder.verify(writer).writeNumber("23");
    inOrder.verify(writer).writeArraySeparator();
    inOrder.verify(writer).writeString("foo");
    inOrder.verify(writer).writeArraySeparator();
    inOrder.verify(writer).writeLiteral("false");
    inOrder.verify(writer).writeArrayClose();
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  void isArray() {
    assertTrue(array.isArray());
  }

  @Test
  void asArray() {
    assertSame(array, array.asArray());
  }

  @Test
  void equals_trueForSameInstance() {
    assertTrue(array.equals(array));
  }

  @Test
  void equals_trueForEqualArrays() {
    assertTrue(array().equals(array()));
    assertTrue(array("foo", "bar").equals(array("foo", "bar")));
  }

  @Test
  void equals_falseForDifferentArrays() {
    assertFalse(array("foo", "bar").equals(array("foo", "bar", "baz")));
    assertFalse(array("foo", "bar").equals(array("bar", "foo")));
  }

  @Test
  void equals_falseForNull() {
    assertFalse(array.equals(null));
  }

  @Test
  void equals_falseForSubclass() {
    assertFalse(array.equals(new JsonArray(array) {}));
  }

  @Test
  void hashCode_equalsForEqualArrays() {
    assertTrue(array().hashCode() == array().hashCode());
    assertTrue(array("foo").hashCode() == array("foo").hashCode());
  }

  @Test
  void hashCode_differsForDifferentArrays() {
    assertFalse(array().hashCode() == array("bar").hashCode());
    assertFalse(array("foo").hashCode() == array("bar").hashCode());
  }

  @Test
  void canBeSerializedAndDeserialized() throws Exception {
    array.add(true).add(3.14d).add(23).add("foo").add(new JsonArray().add(false));

    assertEquals(array, serializeAndDeserialize(array));
  }

  @Test
  void deserializedArrayCanBeAccessed() throws Exception {
    array.add(23);

    JsonArray deserializedArray = serializeAndDeserialize(array);

    assertEquals(23, deserializedArray.get(0).asInt());
  }

  private static JsonArray array(String... values) {
    JsonArray array = new JsonArray();
    for (String value : values) {
      array.add(value);
    }
    return array;
  }

}
