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

import static org.assertj.core.api.Assertions.assertThat;

import com.restfb.testutils.AssertJson;

import org.junit.Test;

public class JsonMapperEnumTest {

  @Test
  public void createWithEnum() {
    String simpleJson = "{\"id\": 12345, \"test_enum\": \"FOO\"}";
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    EnumTestType testType = mapper.toJavaObject(simpleJson, EnumTestType.class);
    assertThat(testType.id).isEqualTo("12345");
    assertThat(testType.testEnum).isEqualTo(EnumTestEnum.FOO);
    assertThat(testType.testEnumString).isEqualTo("FOO");
  }

  @Test
  public void createWithNonExistingEnumValue() {
    String simpleJson = "{\"id\": 12345, \"test_enum\": \"BAZ\"}";
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    EnumTestType testType = mapper.toJavaObject(simpleJson, EnumTestType.class);
    assertThat(testType.id).isEqualTo("12345");
    assertThat(testType.testEnum).isNull();
    assertThat(testType.testEnumString).isEqualTo("BAZ");
  }

  @Test
  public void createJsonFromObject() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    EnumTestTypeSimple testType = new EnumTestTypeSimple();
    testType.testEnum = EnumTestEnum.BAR;
    testType.id = "1234";
    String jsonString = mapper.toJson(testType);
    AssertJson.assertEquals("{\"id\":\"1234\",\"test_enum\":\"BAR\"}", jsonString);
  }
}
