/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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

import com.restfb.json.Json;
import com.restfb.json.JsonValue;
import com.restfb.testutils.AssertJson;

import org.junit.Test;

public class AssertJsonTest {

  @Test
  public void check_Boolean_bothFalse() {
    JsonValue expectedFalseValue = Json.FALSE;
    JsonValue actualFalseValue = Json.FALSE;

    AssertJson.assertEquals(expectedFalseValue.toString(), actualFalseValue.toString());
  }

  @Test(expected = AssertionError.class)
  public void check_Boolean_differentBoolean() {
    JsonValue expectedFalseValue = Json.FALSE;
    JsonValue actualFalseValue = Json.TRUE;

    AssertJson.assertEquals(expectedFalseValue.toString(), actualFalseValue.toString());
  }

  @Test
  public void check_Double_both() {
    JsonValue expectedFalseValue = Json.parse("1.0");
    JsonValue actualFalseValue = Json.parse("1.0");

    AssertJson.assertEquals(expectedFalseValue.toString(), actualFalseValue.toString());
  }

  @Test
  public void check_String_both() {
    JsonValue expectedFalseValue = Json.value("Test");
    JsonValue actualFalseValue = Json.value("Test");

    AssertJson.assertEquals(expectedFalseValue.toString(), actualFalseValue.toString());
  }

  @Test
  public void check_Array_both() {
    String expectedString = "[\"String1\",\"String2\",\"String3\"]";
    String actualString = "[\"String1\",\"String2\",\"String3\"]";

    AssertJson.assertEquals(expectedString, actualString);
  }

  @Test(expected = AssertionError.class)
  public void check_Array_differentOrder() {
    String expectedString = "[\"String1\",\"String2\",\"String3\"]";
    String actualString = "[\"String2\",\"String3\",\"String1\"]";

    AssertJson.assertEquals(expectedString, actualString);
  }

  @Test
  public void check_Object_both() {
    String expectedString = "{\"name\":\"test\",\"id\":345,\"blub\":\"bla\"}";
    String actualString = "{\"name\":\"test\",\"id\":345,\"blub\":\"bla\"}";

    AssertJson.assertEquals(expectedString, actualString);
  }

  @Test
  public void check_Object_differentOrder() {
    String expectedString = "{\"name\":\"test\",\"id\":345,\"blub\":\"bla\"}";
    String actualString = "{\"id\":345,\"name\":\"test\",\"blub\":\"bla\"}";

    AssertJson.assertEquals(expectedString, actualString);
  }

  @Test(expected = AssertionError.class)
  public void check_Object_differentFields() {
    String expectedString = "{}";
    String actualString = "{\"id\":345}";

    AssertJson.assertEquals(expectedString, actualString);
  }

  @Test(expected = AssertionError.class)
  public void check_Object_Array() {
    String expectedString = "{}";
    String actualString = "[]";

    AssertJson.assertEquals(expectedString, actualString);
  }
}
