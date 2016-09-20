/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class ParameterTest {

  @Test
  public void emptyFacebookList() throws JSONException {
    JsonMapperToJsonTest.ListObject obj = new JsonMapperToJsonTest.ListObject();
    String val = Parameter.with("key", obj).value;
    JSONAssert.assertEquals("{id: 12345}", val, true);
  }

  @Test
  public void correctKeyCheck() {
    String key = "tHiSAtEsT";
    String val = "sOmEVaLue";

    Parameter testParam = Parameter.with(key, val);

    assertEquals(key, testParam.name);
    assertEquals(val, testParam.value);
  }

  @Test
  public void useEnumAsValue() {
    String val = Parameter.with("key", EnumTestEnum.FOO).value;
    assertEquals("FOO", val);
  }

  @Test
  public void correctKeyWithWsCheck() {
    String key = "\n\ntHiSAtEsT\n\t";
    String val = "sOmEVaLue";

    Parameter testParam = Parameter.with(key, val);

    assertEquals(key.trim(), testParam.name);
    assertEquals(val, testParam.value);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullKeyCheck() {
    Parameter.with(null, "val");
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyKeyCheck() {
    Parameter.with("", "val");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullValueCheck() {
    Parameter.with("key", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullJsonMapper() {
    Parameter.with("key", "value", null);
  }

  @Test
  public void equalsCheck_null() {
    Parameter obj = Parameter.with("name", "value");
    assertFalse(obj.equals(null));
  }

  @Test
  public void equalsCheck_differentClass() {
    Parameter obj = Parameter.with("name", "value");
    assertFalse(obj.equals(new Object()));
  }

  @Test
  public void equalsCheck_differentName() {
    Parameter obj1 = Parameter.with("name", "value");
    Parameter obj2 = Parameter.with("name1", "value");
    assertFalse(obj1.equals(obj2));
  }

  @Test
  public void equalsCheck_differentValue() {
    Parameter obj1 = Parameter.with("name", "value");
    Parameter obj2 = Parameter.with("name", "value1");
    assertFalse(obj1.equals(obj2));
  }

  @Test
  public void equalsCheck_equals() {
    Parameter obj1 = Parameter.with("name", "value");
    Parameter obj2 = Parameter.with("name", "value");
    assertTrue(obj1.equals(obj2));
  }

}
