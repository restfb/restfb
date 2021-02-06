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
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import com.restfb.json.JsonObject.HashIndexTable;
import com.restfb.json.JsonObject.Member;

class JsonObject_Test {

  private JsonObject object;

  @BeforeEach
  void setUp() {
    object = new JsonObject();
  }

  @Test
  void copyConstructor_failsWithNull() {
    assertThrows(NullPointerException.class, () -> new JsonObject(null), "object is null");
  }

  @Test
  void copyConstructor_hasSameValues() {
    object.add("foo", 23);
    JsonObject copy = new JsonObject(object);

    assertEquals(object.names(), copy.names());
    assertSame(object.get("foo"), copy.get("foo"));
  }

  @Test
  void copyConstructor_worksOnSafeCopy() {
    JsonObject copy = new JsonObject(object);
    object.add("foo", 23);

    assertTrue(copy.isEmpty());
  }

  @Test
  void unmodifiableObject_hasSameValues() {
    object.add("foo", 23);
    JsonObject unmodifiableObject = JsonObject.unmodifiableObject(object);

    assertEquals(object.names(), unmodifiableObject.names());
    assertSame(object.get("foo"), unmodifiableObject.get("foo"));
  }

  @Test
  void unmodifiableObject_reflectsChanges() {
    JsonObject unmodifiableObject = JsonObject.unmodifiableObject(object);
    object.add("foo", 23);

    assertEquals(object.names(), unmodifiableObject.names());
    assertSame(object.get("foo"), unmodifiableObject.get("foo"));
  }

  @Test
  void unmodifiableObject_preventsModification() {
    JsonObject unmodifiableObject = JsonObject.unmodifiableObject(object);

    assertThrows(UnsupportedOperationException.class, () -> unmodifiableObject.add("foo", 23));
  }

  @Test
  void isEmpty_trueAfterCreation() {
    assertTrue(object.isEmpty());
  }

  @Test
  void isEmpty_falseAfterAdd() {
    object.add("a", true);

    assertFalse(object.isEmpty());
  }

  @Test
  void size_zeroAfterCreation() {
    assertEquals(0, object.size());
  }

  @Test
  void size_oneAfterAdd() {
    object.add("a", true);

    assertEquals(1, object.size());
  }

  @Test
  void keyRepetition_allowsMultipleEntries() {
    object.add("a", true);
    object.add("a", "value");

    assertEquals(2, object.size());
  }

  @Test
  void keyRepetition_getsLastEntry() {
    object.add("a", true);
    object.add("a", "value");

    assertEquals("value", object.getString("a", "missing"));
  }

  @Test
  void keyRepetition_equalityConsidersRepetitions() {
    object.add("a", true);
    object.add("a", "value");

    JsonObject onlyFirstProperty = new JsonObject();
    onlyFirstProperty.add("a", true);
    assertNotEquals(onlyFirstProperty, object);

    JsonObject bothProperties = new JsonObject();
    bothProperties.add("a", true);
    bothProperties.add("a", "value");
    assertEquals(bothProperties, object);
  }

  @Test
  void names_emptyAfterCreation() {
    assertTrue(object.names().isEmpty());
  }

  @Test
  void names_containsNameAfterAdd() {
    object.add("foo", true);

    List<String> names = object.names();
    assertEquals(1, names.size());
    assertEquals("foo", names.get(0));
  }

  @Test
  void names_reflectsChanges() {
    List<String> names = object.names();

    object.add("foo", true);

    assertEquals(1, names.size());
    assertEquals("foo", names.get(0));
  }

  @Test
  void names_preventsModification() {
    List<String> names = object.names();

    assertThrows(UnsupportedOperationException.class, () -> names.add("foo"));
  }

  @Test
  void iterator_isEmptyAfterCreation() {
    assertFalse(object.iterator().hasNext());
  }

  @Test
  void iterator_hasNextAfterAdd() {
    object.add("a", true);
    Iterator<Member> iterator = object.iterator();

    assertTrue(iterator.hasNext());
  }

  @Test
  void iterator_nextReturnsActualValue() {
    object.add("a", true);
    Iterator<Member> iterator = object.iterator();

    assertEquals(new Member("a", Json.TRUE), iterator.next());
  }

  @Test
  void iterator_nextProgressesToNextValue() {
    object.add("a", true);
    object.add("b", false);
    Iterator<Member> iterator = object.iterator();

    iterator.next();
    assertTrue(iterator.hasNext());
    assertEquals(new Member("b", Json.FALSE), iterator.next());
  }

  @Test
  void iterator_nextFailsAtEnd() {
    Iterator<Member> iterator = object.iterator();

    assertThrows(NoSuchElementException.class, iterator::next);
  }

  @Test
  void iterator_doesNotAllowModification() {
    object.add("a", 23);
    Iterator<Member> iterator = object.iterator();
    iterator.next();

    assertThrows(UnsupportedOperationException.class, iterator::remove);
  }

  @Test
  void iterator_detectsConcurrentModification() {
    Iterator<Member> iterator = object.iterator();
    object.add("a", 23);
    assertThrows(ConcurrentModificationException.class, iterator::next);
  }

  @Test
  void get_failsWithNullName() {
    assertThrows(NullPointerException.class, () -> object.get(null), "name is null");
  }

  @Test
  void get_returnsNullForNonExistingMember() {
    assertNull(object.get("foo"));
  }

  @Test
  void get_returnsValueForName() {
    object.add("foo", true);

    assertEquals(Json.TRUE, object.get("foo"));
  }

  @Test
  void get_returnsLastValueForName() {
    object.add("foo", false).add("foo", true);

    assertEquals(Json.TRUE, object.get("foo"));
  }

  @Test
  void get_int_returnsValueFromMember() {
    object.add("foo", 23);

    assertEquals(23, object.getInt("foo", 42));
  }

  @Test
  void get_int_returnsDefaultForMissingMember() {
    assertEquals(23, object.getInt("foo", 23));
  }

  @Test
  void get_long_returnsValueFromMember() {
    object.add("foo", 23L);

    assertEquals(23L, object.getLong("foo", 42L));
  }

  @Test
  void get_long_returnsDefaultForMissingMember() {
    assertEquals(23L, object.getLong("foo", 23L));
  }

  @Test
  void get_float_returnsValueFromMember() {
    object.add("foo", 3.14f);

    assertEquals(3.14f, object.getFloat("foo", 1.41f));
  }

  @Test
  void get_float_returnsDefaultForMissingMember() {
    assertEquals(3.14f, object.getFloat("foo", 3.14f));
  }

  @Test
  void get_double_returnsValueFromMember() {
    object.add("foo", 3.14);

    assertEquals(3.14, object.getDouble("foo", 1.41));
  }

  @Test
  void get_double_returnsDefaultForMissingMember() {
    assertEquals(3.14, object.getDouble("foo", 3.14));
  }

  @Test
  void get_boolean_returnsValueFromMember() {
    object.add("foo", true);

    assertTrue(object.getBoolean("foo", false));
  }

  @Test
  void get_boolean_returnsDefaultForMissingMember() {
    assertFalse(object.getBoolean("foo", false));
  }

  @Test
  void get_string_returnsValueFromMember() {
    object.add("foo", "bar");

    assertEquals("bar", object.getString("foo", "default"));
  }

  @Test
  void get_string_returnsDefaultForMissingMember() {
    assertEquals("default", object.getString("foo", "default"));
  }

  @Test
  void add_failsWithNullName() {
    assertThrows(NullPointerException.class, () -> object.add(null, 23), "name is null");
  }

  @Test
  void add_int() {
    object.add("a", 23);

    assertEquals("{\"a\":23}", object.toString());
  }

  @Test
  void add_int_enablesChaining() {
    assertSame(object, object.add("a", 23));
  }

  @Test
  void add_long() {
    object.add("a", 23L);

    assertEquals("{\"a\":23}", object.toString());
  }

  @Test
  void add_long_enablesChaining() {
    assertSame(object, object.add("a", 23L));
  }

  @Test
  void add_float() {
    object.add("a", 3.14f);

    assertEquals("{\"a\":3.14}", object.toString());
  }

  @Test
  void add_float_enablesChaining() {
    assertSame(object, object.add("a", 3.14f));
  }

  @Test
  void add_double() {
    object.add("a", 3.14d);

    assertEquals("{\"a\":3.14}", object.toString());
  }

  @Test
  void add_double_enablesChaining() {
    assertSame(object, object.add("a", 3.14d));
  }

  @Test
  void add_boolean() {
    object.add("a", true);

    assertEquals("{\"a\":true}", object.toString());
  }

  @Test
  void add_boolean_enablesChaining() {
    assertSame(object, object.add("a", true));
  }

  @Test
  void add_string() {
    object.add("a", "foo");

    assertEquals("{\"a\":\"foo\"}", object.toString());
  }

  @Test
  void add_string_toleratesNull() {
    object.add("a", (String) null);

    assertEquals("{\"a\":null}", object.toString());
  }

  @Test
  void add_string_enablesChaining() {
    assertSame(object, object.add("a", "foo"));
  }

  @Test
  void add_jsonNull() {
    object.add("a", Json.NULL);

    assertEquals("{\"a\":null}", object.toString());
  }

  @Test
  void add_jsonArray() {
    object.add("a", new JsonArray());

    assertEquals("{\"a\":[]}", object.toString());
  }

  @Test
  void add_jsonObject() {
    object.add("a", new JsonObject());

    assertEquals("{\"a\":{}}", object.toString());
  }

  @Test
  void add_json_enablesChaining() {
    assertSame(object, object.add("a", Json.NULL));
  }

  @Test
  void add_json_failsWithNull() {
    assertThrows(NullPointerException.class, () -> object.add("a", (JsonValue) null), "value is null");
  }

  @Test
  void add_json_nestedArray() {
    JsonArray innerArray = new JsonArray();
    innerArray.add(23);

    object.add("a", innerArray);

    assertEquals("{\"a\":[23]}", object.toString());
  }

  @Test
  void add_json_nestedArray_modifiedAfterAdd() {
    JsonArray innerArray = new JsonArray();
    object.add("a", innerArray);

    innerArray.add(23);

    assertEquals("{\"a\":[23]}", object.toString());
  }

  @Test
  void add_json_nestedObject() {
    JsonObject innerObject = new JsonObject();
    innerObject.add("a", 23);

    object.add("a", innerObject);

    assertEquals("{\"a\":{\"a\":23}}", object.toString());
  }

  @Test
  void add_json_nestedObject_modifiedAfterAdd() {
    JsonObject innerObject = new JsonObject();
    object.add("a", innerObject);

    innerObject.add("a", 23);

    assertEquals("{\"a\":{\"a\":23}}", object.toString());
  }

  @Test
  void set_int() {
    object.set("a", 23);

    assertEquals("{\"a\":23}", object.toString());
  }

  @Test
  void set_int_enablesChaining() {
    assertSame(object, object.set("a", 23));
  }

  @Test
  void set_long() {
    object.set("a", 23L);

    assertEquals("{\"a\":23}", object.toString());
  }

  @Test
  void set_long_enablesChaining() {
    assertSame(object, object.set("a", 23L));
  }

  @Test
  void set_float() {
    object.set("a", 3.14f);

    assertEquals("{\"a\":3.14}", object.toString());
  }

  @Test
  void set_float_enablesChaining() {
    assertSame(object, object.set("a", 3.14f));
  }

  @Test
  void set_double() {
    object.set("a", 3.14d);

    assertEquals("{\"a\":3.14}", object.toString());
  }

  @Test
  void set_double_enablesChaining() {
    assertSame(object, object.set("a", 3.14d));
  }

  @Test
  void set_boolean() {
    object.set("a", true);

    assertEquals("{\"a\":true}", object.toString());
  }

  @Test
  void set_boolean_enablesChaining() {
    assertSame(object, object.set("a", true));
  }

  @Test
  void set_string() {
    object.set("a", "foo");

    assertEquals("{\"a\":\"foo\"}", object.toString());
  }

  @Test
  void set_string_enablesChaining() {
    assertSame(object, object.set("a", "foo"));
  }

  @Test
  void set_jsonNull() {
    object.set("a", Json.NULL);

    assertEquals("{\"a\":null}", object.toString());
  }

  @Test
  void set_jsonArray() {
    object.set("a", new JsonArray());

    assertEquals("{\"a\":[]}", object.toString());
  }

  @Test
  void set_jsonObject() {
    object.set("a", new JsonObject());

    assertEquals("{\"a\":{}}", object.toString());
  }

  @Test
  void set_json_enablesChaining() {
    assertSame(object, object.set("a", Json.NULL));
  }

  @Test
  void set_addsElementIfMissing() {
    object.set("a", Json.TRUE);

    assertEquals("{\"a\":true}", object.toString());
  }

  @Test
  void set_modifiesElementIfExisting() {
    object.add("a", Json.TRUE);

    object.set("a", Json.FALSE);

    assertEquals("{\"a\":false}", object.toString());
  }

  @Test
  void set_modifiesLastElementIfMultipleExisting() {
    object.add("a", 1);
    object.add("a", 2);

    object.set("a", Json.TRUE);

    assertEquals("{\"a\":1,\"a\":true}", object.toString());
  }

  @Test
  void set_json_failsWithNull() {
    assertThrows(NullPointerException.class, () -> object.set("a", (JsonValue) null), "value is null");
  }

  @Test
  void set_failsWithNullName() {
    assertThrows(NullPointerException.class, () -> object.set(null, 23), "name is null");
  }

  @Test
  void remove_failsWithNullName() {
    assertThrows(NullPointerException.class, () -> object.remove(null), "name is null");
  }

  @Test
  void remove_removesMatchingMember() {
    object.add("a", 23);

    object.remove("a");

    assertEquals("{}", object.toString());
  }

  @Test
  void remove_removesOnlyMatchingMember() {
    object.add("a", 23);
    object.add("b", 42);
    object.add("c", true);

    object.remove("b");

    assertEquals("{\"a\":23,\"c\":true}", object.toString());
  }

  @Test
  void remove_removesOnlyLastMatchingMember() {
    object.add("a", 23);
    object.add("a", 42);

    object.remove("a");

    assertEquals("{\"a\":23}", object.toString());
  }

  @Test
  void remove_removesOnlyLastMatchingMember_afterRemove() {
    object.add("a", 23);
    object.remove("a");
    object.add("a", 42);
    object.add("a", 47);

    object.remove("a");

    assertEquals("{\"a\":42}", object.toString());
  }

  @Test
  void remove_doesNotModifyObjectWithoutMatchingMember() {
    object.add("a", 23);

    object.remove("b");

    assertEquals("{\"a\":23}", object.toString());
  }

  @Test
  void contains_findsAddedMembers() {
    object.add("f", 15f);

    assertTrue(object.contains("f"));
  }

  @Test
  void contains_doesNotFindAbsentMembers() {
    assertFalse(object.contains("a"));
  }

  @Test
  void contains_doesNotFindDeletedMembers() {
    object.add("a", "foo");

    object.remove("a");

    assertFalse(object.contains("a"));
  }

  @Test
  void merge_failsWithNull() {
    assertThrows(NullPointerException.class, () -> object.merge(null), "object is null");
  }

  @Test
  void merge_appendsMembers() {
    object.add("a", 1).add("b", 1);
    object.merge(Json.object().add("c", 2).add("d", 2));

    assertEquals(Json.object().add("a", 1).add("b", 1).add("c", 2).add("d", 2), object);
  }

  @Test
  void merge_replacesMembers() {
    object.add("a", 1).add("b", 1).add("c", 1);
    object.merge(Json.object().add("b", 2).add("d", 2));

    assertEquals(Json.object().add("a", 1).add("b", 2).add("c", 1).add("d", 2), object);
  }

  @Test
  void write_empty() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    object.write(writer);

    InOrder inOrder = inOrder(writer);
    inOrder.verify(writer).writeObjectOpen();
    inOrder.verify(writer).writeObjectClose();
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  void write_withSingleValue() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    object.add("a", 23);

    object.write(writer);

    InOrder inOrder = inOrder(writer);
    inOrder.verify(writer).writeObjectOpen();
    inOrder.verify(writer).writeMemberName("a");
    inOrder.verify(writer).writeMemberSeparator();
    inOrder.verify(writer).writeNumber("23");
    inOrder.verify(writer).writeObjectClose();
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  void write_withMultipleValues() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    object.add("a", 23);
    object.add("b", 3.14f);
    object.add("c", "foo");
    object.add("d", true);
    object.add("e", (String) null);

    object.write(writer);

    InOrder inOrder = inOrder(writer);
    inOrder.verify(writer).writeObjectOpen();
    inOrder.verify(writer).writeMemberName("a");
    inOrder.verify(writer).writeMemberSeparator();
    inOrder.verify(writer).writeNumber("23");
    inOrder.verify(writer).writeObjectSeparator();
    inOrder.verify(writer).writeMemberName("b");
    inOrder.verify(writer).writeMemberSeparator();
    inOrder.verify(writer).writeNumber("3.14");
    inOrder.verify(writer).writeObjectSeparator();
    inOrder.verify(writer).writeMemberName("c");
    inOrder.verify(writer).writeMemberSeparator();
    inOrder.verify(writer).writeString("foo");
    inOrder.verify(writer).writeObjectSeparator();
    inOrder.verify(writer).writeMemberName("d");
    inOrder.verify(writer).writeMemberSeparator();
    inOrder.verify(writer).writeLiteral("true");
    inOrder.verify(writer).writeObjectSeparator();
    inOrder.verify(writer).writeMemberName("e");
    inOrder.verify(writer).writeMemberSeparator();
    inOrder.verify(writer).writeLiteral("null");
    inOrder.verify(writer).writeObjectClose();
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  void isObject() {
    assertTrue(object.isObject());
  }

  @Test
  void asObject() {
    assertSame(object, object.asObject());
  }

  @Test
  void equals_trueForSameInstance() {
    assertTrue(object.equals(object));
  }

  @Test
  void equals_trueForEqualObjects() {
    assertTrue(object().equals(object()));
    assertTrue(object("a", "1", "b", "2").equals(object("a", "1", "b", "2")));
  }

  @Test
  void equals_falseForDifferentObjects() {
    assertFalse(object("a", "1").equals(object("a", "2")));
    assertFalse(object("a", "1").equals(object("b", "1")));
    assertFalse(object("a", "1", "b", "2").equals(object("b", "2", "a", "1")));
  }

  @Test
  void equals_falseForNull() {
    assertFalse(new JsonObject().equals(null));
  }

  @Test
  void equals_falseForSubclass() {
    JsonObject jsonObject = new JsonObject();

    assertFalse(jsonObject.equals(new JsonObject(jsonObject) {}));
  }

  @Test
  void hashCode_equalsForEqualObjects() {
    assertTrue(object().hashCode() == object().hashCode());
    assertTrue(object("a", "1").hashCode() == object("a", "1").hashCode());
  }

  @Test
  void hashCode_differsForDifferentObjects() {
    assertFalse(object().hashCode() == object("a", "1").hashCode());
    assertFalse(object("a", "1").hashCode() == object("a", "2").hashCode());
    assertFalse(object("a", "1").hashCode() == object("b", "1").hashCode());
  }

  @Test
  void indexOf_returnsNoIndexIfEmpty() {
    assertEquals(-1, object.indexOf("a"));
  }

  @Test
  void indexOf_returnsIndexOfMember() {
    object.add("a", true);

    assertEquals(0, object.indexOf("a"));
  }

  @Test
  void indexOf_returnsIndexOfLastMember() {
    object.add("a", true);
    object.add("a", true);

    assertEquals(1, object.indexOf("a"));
  }

  @Test
  void indexOf_returnsIndexOfLastMember_afterRemove() {
    object.add("a", true);
    object.add("a", true);
    object.remove("a");

    assertEquals(0, object.indexOf("a"));
  }

  @Test
  void indexOf_returnsUpdatedIndexAfterRemove() {
    // See issue #16
    object.add("a", true);
    object.add("b", true);
    object.remove("a");

    assertEquals(0, object.indexOf("b"));
  }

  @Test
  void indexOf_returnsIndexOfLastMember_forBigObject() {
    object.add("a", true);
    // for indexes above 255, the hash index table does not return a value
    for (int i = 0; i < 256; i++) {
      object.add("x-" + i, 0);
    }
    object.add("a", true);

    assertEquals(257, object.indexOf("a"));
  }

  @Test
  void hashIndexTable_copyConstructor() {
    HashIndexTable original = new HashIndexTable();
    original.add("name", 23);

    HashIndexTable copy = new HashIndexTable(original);

    assertEquals(23, copy.get("name"));
  }

  @Test
  void hashIndexTable_add() {
    HashIndexTable indexTable = new HashIndexTable();

    indexTable.add("name-0", 0);
    indexTable.add("name-1", 1);
    indexTable.add("name-fe", 0xfe);
    indexTable.add("name-ff", 0xff);

    assertEquals(0, indexTable.get("name-0"));
    assertEquals(1, indexTable.get("name-1"));
    assertEquals(0xfe, indexTable.get("name-fe"));
    assertEquals(-1, indexTable.get("name-ff"));
  }

  @Test
  void hashIndexTable_add_overwritesPreviousValue() {
    HashIndexTable indexTable = new HashIndexTable();

    indexTable.add("name", 23);
    indexTable.add("name", 42);

    assertEquals(42, indexTable.get("name"));
  }

  @Test
  void hashIndexTable_add_clearsPreviousValueIfIndexExceeds0xff() {
    HashIndexTable indexTable = new HashIndexTable();

    indexTable.add("name", 23);
    indexTable.add("name", 300);

    assertEquals(-1, indexTable.get("name"));
  }

  @Test
  void hashIndexTable_remove() {
    HashIndexTable indexTable = new HashIndexTable();

    indexTable.add("name", 23);
    indexTable.remove(23);

    assertEquals(-1, indexTable.get("name"));
  }

  @Test
  void hashIndexTable_remove_updatesSubsequentElements() {
    HashIndexTable indexTable = new HashIndexTable();

    indexTable.add("foo", 23);
    indexTable.add("bar", 42);
    indexTable.remove(23);

    assertEquals(41, indexTable.get("bar"));
  }

  @Test
  void hashIndexTable_remove_doesNotChangePrecedingElements() {
    HashIndexTable indexTable = new HashIndexTable();

    indexTable.add("foo", 23);
    indexTable.add("bar", 42);
    indexTable.remove(42);

    assertEquals(23, indexTable.get("foo"));
  }

  @Test
  void canBeSerializedAndDeserialized() throws Exception {
    object.add("foo", 23).add("bar", new JsonObject().add("a", 3.14d).add("b", true));

    assertEquals(object, serializeAndDeserialize(object));
  }

  @Test
  void deserializedObjectCanBeAccessed() throws Exception {
    object.add("foo", 23);

    JsonObject deserializedObject = serializeAndDeserialize(object);

    assertEquals(23, deserializedObject.get("foo").asInt());
  }

  @Test
  void member_returnsNameAndValue() {
    Member member = new Member("a", Json.TRUE);

    assertEquals("a", member.getName());
    assertEquals(Json.TRUE, member.getValue());
  }

  @Test
  void member_equals_trueForSameInstance() {
    Member member = new Member("a", Json.TRUE);

    assertTrue(member.equals(member));
  }

  @Test
  void member_equals_trueForEqualObjects() {
    Member member = new Member("a", Json.TRUE);

    assertTrue(member.equals(new Member("a", Json.TRUE)));
  }

  @Test
  void member_equals_falseForDifferingObjects() {
    Member member = new Member("a", Json.TRUE);

    assertFalse(member.equals(new Member("b", Json.TRUE)));
    assertFalse(member.equals(new Member("a", Json.FALSE)));
  }

  @Test
  void member_equals_falseForNull() {
    Member member = new Member("a", Json.TRUE);

    assertFalse(member.equals(null));
  }

  @Test
  void member_equals_falseForSubclass() {
    Member member = new Member("a", Json.TRUE);

    assertFalse(member.equals(new Member("a", Json.TRUE) {}));
  }

  @Test
  void member_hashCode_equalsForEqualObjects() {
    Member member = new Member("a", Json.TRUE);

    assertTrue(member.hashCode() == new Member("a", Json.TRUE).hashCode());
  }

  @Test
  void member_hashCode_differsForDifferingobjects() {
    Member member = new Member("a", Json.TRUE);

    assertFalse(member.hashCode() == new Member("b", Json.TRUE).hashCode());
    assertFalse(member.hashCode() == new Member("a", Json.FALSE).hashCode());
  }

  private static JsonObject object(String... namesAndValues) {
    JsonObject object = new JsonObject();
    for (int i = 0; i < namesAndValues.length; i += 2) {
      object.add(namesAndValues[i], namesAndValues[i + 1]);
    }
    return object;
  }

}
