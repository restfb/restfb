/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StringUtilsTest {

  @Test
  public void integerParseTest_null() {
    assertThat(StringUtils.toInteger(null)).isNull();
  }

  @Test
  public void integerParseTest_number() {
    assertThat(StringUtils.toInteger("23")).isEqualTo(23);
  }

  @Test
  public void integerParseTest_String() {
    assertThat(StringUtils.toInteger("bla")).isNull();
  }

  @Test
  public void joinArrayTest() {
    assertThat(StringUtils.join(new String[] { "foo", "bar" })).isEqualTo("foo,bar");
  }

  @Test
  public void joinListTest_null() {
    assertThat(StringUtils.join((List) null)).isNull();
  }

  @Test
  public void joinListTest_List() {
    List<String> myStrings = new ArrayList<>();
    myStrings.add("foo");
    myStrings.add("bar");
    assertThat(StringUtils.join(myStrings)).isEqualTo("foo,bar");
  }

  @Test
  public void toString_null() {
    try {
      StringUtils.toString(null);
      fail("NullPointerException expected");
    } catch (NullPointerException npe) {
      assertThat(npe).hasMessage("The 'data' parameter cannot be null.");
    }
  }

  @Test
  public void toString_array() {
    assertThat(StringUtils.toString("abc".getBytes())).contains("abc");
  }

  @Test
  public void toBytes_null() {
    try {
      StringUtils.toBytes(null);
      fail("NullPointerException expected");
    } catch (NullPointerException npe) {
      assertThat(npe).hasMessage("The 'string' parameter cannot be null.");
    }
  }

  @Test
  public void toBytes_String() {
    assertThat(StringUtils.toBytes("abc")).contains('a', 'b', 'c');
  }

  @Test
  public void fromInputStream_null() throws IOException {
    assertThat(StringUtils.fromInputStream(null)).isNull();
  }
}
