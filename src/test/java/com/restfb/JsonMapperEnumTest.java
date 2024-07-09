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

import static com.restfb.testutils.AssertJson.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class JsonMapperEnumTest {

  private final DefaultJsonMapper mapper = new DefaultJsonMapper();

  @Test
  void createWithEnum() {
    String simpleJson = "{\"id\": 12345, \"test_enum\": \"FOO\"}";
    EnumTestType testType = mapper.toJavaObject(simpleJson, EnumTestType.class);
    assertThat(testType.id).isEqualTo("12345");
    assertThat(testType.testEnum).isEqualTo(EnumTestEnum.FOO);
    assertThat(testType.testEnumString).isEqualTo("FOO");
  }

  @Test
  void createWithNonExistingEnumValue() {
    String simpleJson = "{\"id\": 12345, \"test_enum\": \"BAZ\"}";
    EnumTestType testType = mapper.toJavaObject(simpleJson, EnumTestType.class);
    assertThat(testType.id).isEqualTo("12345");
    assertThat(testType.testEnum).isNull();
    assertThat(testType.testEnumString).isEqualTo("BAZ");
  }

  @Test
  void createJsonFromObject() {
    EnumTestTypeSimple testType = new EnumTestTypeSimple();
    testType.testEnum = EnumTestEnum.BAR;
    testType.id = "1234";
    String jsonString = mapper.toJson(testType);
    assertEquals("{\"id\":\"1234\",\"test_enum\":\"BAR\"}", jsonString);
  }

  @Test
  void checkAnnotationEnumWithoutAnnotation() {
    String json = "{\"id\": 12345, \"test_enum\": \"FOO\"}";
    EnumTestTypeAnnotation testType = mapper.toJavaObject(json, EnumTestTypeAnnotation.class);
    assertThat(testType.testEnum).isEqualTo(EnumTestAnnotationEnum.FOO);
  }

  @Test
  void checkAnnotationEnumWithAnnotation() {
    String json = "{\"id\": 12345, \"test_enum\": \"BAR\"}";
    EnumTestTypeAnnotation testType = mapper.toJavaObject(json, EnumTestTypeAnnotation.class);
    assertThat(testType.testEnum).isEqualTo(EnumTestAnnotationEnum.BAR);
  }

  @Test
  void checkAnnotationEnumWithAnnotationValue() {
    String json = "{\"id\": 12345, \"test_enum\": \"foo_bar\"}";
    EnumTestTypeAnnotation testType = mapper.toJavaObject(json, EnumTestTypeAnnotation.class);
    assertThat(testType.testEnum).isEqualTo(EnumTestAnnotationEnum.BAZ);
  }

  @Test
  void checkAnnotationEnumToJsonAltValue() {
    EnumTestTypeAnnotation testType = new EnumTestTypeAnnotation();
    testType.id = "12345";
    testType.testEnum = EnumTestAnnotationEnum.BAZ;
    String json = mapper.toJson(testType);
    assertEquals(json, "{\"id\":\"12345\",\"test_enum\":\"foo_bar\"}");
  }

  @Test
  void checkAnnotationEnumToJsonWithAnnotation() {
    EnumTestTypeAnnotation testType = new EnumTestTypeAnnotation();
    testType.id = "12345";
    testType.testEnum = EnumTestAnnotationEnum.BAR;
    String json = mapper.toJson(testType);
    assertEquals(json, "{\"id\":\"12345\",\"test_enum\":\"BAR\"}");
  }

  @Test
  void checkAnnotationEnumToJsonWithoutAnnotation() {
    EnumTestTypeAnnotation testType = new EnumTestTypeAnnotation();
    testType.id = "12345";
    testType.testEnum = EnumTestAnnotationEnum.FOO;
    String json = mapper.toJson(testType);
    assertEquals(json, "{\"id\":\"12345\",\"test_enum\":\"FOO\"}");
  }
}
