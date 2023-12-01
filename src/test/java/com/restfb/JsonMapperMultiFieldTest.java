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

import org.junit.jupiter.api.Test;

import com.restfb.json.JsonObject;

class JsonMapperMultiFieldTest {

  @Test
  void check1_object() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();

    TestClass1 test1 = mapper.toJavaObject("{\"mydata\":{}}", TestClass1.class);

    assertThat(test1.dataString).isEqualTo("{}");
    assertThat(test1.dataObject).isNotNull().hasToString("{}");
  }

  @Test
  void check1_string() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();

    TestClass1 test1 = mapper.toJavaObject("{\"mydata\":123}", TestClass1.class);

    assertThat(test1.dataString).isEqualTo("123");
    assertThat(test1.dataObject).isNull();
  }

  @Test
  void check2_object() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();

    TestClass2 test2 = mapper.toJavaObject("{\"mydata\":{}}", TestClass2.class);

    assertThat(test2.dataString).isEqualTo("{}");
    assertThat(test2.dataObject).isNotNull().hasToString("{}");
  }

  @Test
  void check2_string() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();

    TestClass2 test2 = mapper.toJavaObject("{\"mydata\":123}", TestClass2.class);

    assertThat(test2.dataString).isEqualTo("123");
    assertThat(test2.dataObject).isNull();
  }

  @Test
  void check3_object() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();

    TestClass3 test1 = mapper.toJavaObject("{\"mydata\":{}}", TestClass3.class);

    assertThat(test1.dataObject1).isNotNull().hasToString("{}");
    assertThat(test1.dataObject2).isNotNull().hasToString("{}");
  }

  private static class TestClass3 {

    @Facebook("mydata")
    public JsonObject dataObject1;

    @Facebook("mydata")
    public JsonObject dataObject2;

  }

  private static class TestClass1 {

    @Facebook("mydata")
    public String dataString;

    @Facebook("mydata")
    public JsonObject dataObject;

  }

  private static class TestClass2 {

    @Facebook("mydata")
    public JsonObject dataObject;

    @Facebook("mydata")
    public String dataString;

  }
}
