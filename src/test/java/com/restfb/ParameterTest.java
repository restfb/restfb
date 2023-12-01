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

import static com.restfb.testutils.RestfbAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.restfb.testutils.AssertJson;

class ParameterTest {

  @Test
  void emptyFacebookList() {
    JsonMapperToJsonTest.ListObject obj = new JsonMapperToJsonTest.ListObject();
    String val = Parameter.with("key", obj).value;
    AssertJson.assertEquals("{\"id\": 12345}", val);
  }

  @Test
  void correctKeyCheck() {
    String key = "tHiSAtEsT";
    String val = "sOmEVaLue";

    Parameter testParam = Parameter.with(key, val);

    assertThat(testParam).hasName(key).hasValue(val);
  }

  @Test
  void useEnumAsValue() {
    assertThat(Parameter.with("key", EnumTestEnum.FOO)).hasValue("FOO");
  }

  @Test
  void correctKeyWithWsCheck() {
    String key = "\n\ntHiSAtEsT\n\t";
    String val = "sOmEVaLue";

    Parameter testParam = Parameter.with(key, val);

    assertThat(testParam).hasName(key.trim()).hasValue(val);
  }

  @Test
  void nullKeyCheck() {
    assertThrows(IllegalArgumentException.class, () -> Parameter.with(null, "val"));
  }

  @Test
  void emptyKeyCheck() {
    assertThrows(IllegalArgumentException.class, () -> Parameter.with("", "val"));
  }

  @Test
  void nullValueCheck() {
    assertThrows(IllegalArgumentException.class, () -> Parameter.with("key", null));
  }

  @Test
  void nullJsonMapper() {
    assertThrows(IllegalArgumentException.class, () -> Parameter.with("key", "value", null));
  }

  @Test
  void equalsCheck_null() {
    assertThat(Parameter.with("name", "value")).isNotNull();
  }

  @Test
  void equalsCheck_differentClass() {
    Parameter obj = Parameter.with("name", "value");
    assertThat(obj).isNotEqualTo(new Object());
  }

  @Test
  void equalsCheck_differentName() {
    Parameter obj1 = Parameter.with("name", "value");
    Parameter obj2 = Parameter.with("name1", "value");
    assertThat(obj1).isNotEqualTo(obj2);
  }

  @Test
  void equalsCheck_differentValue() {
    Parameter obj1 = Parameter.with("name", "value");
    Parameter obj2 = Parameter.with("name", "value1");
    assertThat(obj1).isNotEqualTo(obj2);
  }

  @Test
  void equalsCheck_equals() {
    Parameter obj1 = Parameter.with("name", "value");
    Parameter obj2 = Parameter.with("name", "value");
    assertThat(obj1).isEqualTo(obj2);
  }

  @Test
  void checkLocaleParameter() {
    assertThat(Parameter.withLocale(Locale.GERMAN)).hasName("locale").hasValue("de");
  }

  @Test
  void checkFieldsParameter() {
    assertThat(Parameter.withFields("id,name")).hasName("fields").hasValue("id,name");
  }

  @Test
  void checkMetadataParameter() {
    assertThat(Parameter.withMetadata()).hasValue("1").hasName("metadata");
  }

  @Test
  void checkLimitParameter() {
    assertThat(Parameter.withLimit(10)).hasValue("10").hasName("limit");
  }

  @Test
  void checkMessageParameter() {
    assertThat(Parameter.withMessage("test")).hasValue("test").hasName("message");
  }

  @Test
  void checkQueryParameter() {
    assertThat(Parameter.withQuery("test")).hasValue("test").hasName("q");
  }

}