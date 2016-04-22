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

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

import com.restfb.json.JsonObject;

import org.junit.Test;

public class JsonMapperMultiFieldTest {

  @Test
  public void check1_object() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();

    TestClass1 test1 = mapper.toJavaObject("{\"mydata\":{}}", TestClass1.class);

    assertEquals("{}", test1.dataString);
    assertNotNull(test1.dataObject);
    assertEquals("{}", test1.dataObject.toString());
  }

  @Test
  public void check1_string() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();

    TestClass1 test1 = mapper.toJavaObject("{\"mydata\":123}", TestClass1.class);

    assertEquals("123", test1.dataString);
    assertNull(test1.dataObject);
  }

  @Test
  public void check2_object() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();

    TestClass2 test2 = mapper.toJavaObject("{\"mydata\":{}}", TestClass2.class);

    assertEquals("{}", test2.dataString);
    assertNotNull(test2.dataObject);
    assertEquals("{}", test2.dataObject.toString());
  }

  @Test
  public void check2_string() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();

    TestClass2 test2 = mapper.toJavaObject("{\"mydata\":123}", TestClass2.class);

    assertEquals("123", test2.dataString);
    assertNull(test2.dataObject);
  }

  @Test
  public void check3_object() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();

    TestClass3 test1 = mapper.toJavaObject("{\"mydata\":{}}", TestClass3.class);

    assertNotNull(test1.dataObject1);
    assertNotNull(test1.dataObject2);
    assertEquals("{}", test1.dataObject1.toString());
    assertEquals("{}", test1.dataObject2.toString());
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
