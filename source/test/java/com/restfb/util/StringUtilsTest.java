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
package com.restfb.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StringUtilsTest {

  @Test
  public void integerParseTest_null() {
    assertNull(StringUtils.toInteger(null));
  }

  @Test
  public void integerParseTest_number() {
    assertEquals(23L, StringUtils.toInteger("23").longValue());
  }

  @Test
  public void integerParseTest_String() {
    assertNull(StringUtils.toInteger("bla"));
  }

  @Test
  public void joinArrayTest() {
    assertEquals("foo,bar", StringUtils.join(new String[] { "foo", "bar" }));
  }

  @Test
  public void joinListTest_null() {
    assertNull(StringUtils.join((List) null));
  }

  @Test
  public void joinListTest_List() {
    ArrayList<String> myStrings = new ArrayList<String>();
    myStrings.add("foo");
    myStrings.add("bar");
    assertEquals("foo,bar", StringUtils.join(myStrings));
  }
}
