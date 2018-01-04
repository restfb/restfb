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
package com.restfb;

import static com.restfb.testutils.RestfbAssertions.assertThat;

import com.restfb.testutils.AssertJson;

import org.junit.Test;

public class ParameterTest {

  @Test
  public void emptyFacebookList() {
    JsonMapperToJsonTest.ListObject obj = new JsonMapperToJsonTest.ListObject();
    String val = Parameter.with("key", obj).value;
    AssertJson.assertEquals("{\"id\": 12345}", val);
  }

  @Test
  public void correctKeyCheck() {
    String key = "tHiSAtEsT";
    String val = "sOmEVaLue";

    Parameter testParam = Parameter.with(key, val);

    assertThat(testParam).hasName(key);
    assertThat(testParam).hasValue(val);
  }

  @Test
  public void useEnumAsValue() {
    assertThat(Parameter.with("key", EnumTestEnum.FOO)).hasValue("FOO");
  }

  @Test
  public void correctKeyWithWsCheck() {
    String key = "\n\ntHiSAtEsT\n\t";
    String val = "sOmEVaLue";

    Parameter testParam = Parameter.with(key, val);

    assertThat(testParam).hasName(key.trim());
    assertThat(testParam).hasValue(val);
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
    assertThat(Parameter.with("name", "value")).isNotNull();
  }

  @Test
  public void equalsCheck_differentClass() {
    Parameter obj = Parameter.with("name", "value");
    assertThat(obj).isNotEqualTo(new Object());
  }

  @Test
  public void equalsCheck_differentName() {
    Parameter obj1 = Parameter.with("name", "value");
    Parameter obj2 = Parameter.with("name1", "value");
    assertThat(obj1).isNotEqualTo(obj2);
  }

  @Test
  public void equalsCheck_differentValue() {
    Parameter obj1 = Parameter.with("name", "value");
    Parameter obj2 = Parameter.with("name", "value1");
    assertThat(obj1).isNotEqualTo(obj2);
  }

  @Test
  public void equalsCheck_equals() {
    Parameter obj1 = Parameter.with("name", "value");
    Parameter obj2 = Parameter.with("name", "value");
    assertThat(obj1).isEqualTo(obj2);
  }

}
