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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringJsonUtilsTest {

  @Test
  public void checkIsEmptyList() {
    assertTrue(StringJsonUtils.isEmptyList("[]"));
    assertFalse(StringJsonUtils.isEmptyList("["));
    assertFalse(StringJsonUtils.isEmptyList("test"));
    assertFalse(StringJsonUtils.isEmptyList(null));
  }

  @Test
  public void checkIsList() {
    assertTrue(StringJsonUtils.isList("[]"));
    assertTrue(StringJsonUtils.isList("["));
    assertFalse(StringJsonUtils.isList("test"));
    assertFalse(StringJsonUtils.isList(null));
  }

  @Test
  public void checkIsEmptyObject() {
    assertTrue(StringJsonUtils.isEmptyObject("{}"));
    assertFalse(StringJsonUtils.isEmptyObject("{"));
    assertFalse(StringJsonUtils.isEmptyObject("test"));
    assertFalse(StringJsonUtils.isEmptyObject(null));
  }

  @Test
  public void checkIsObject() {
    assertTrue(StringJsonUtils.isObject("{}"));
    assertTrue(StringJsonUtils.isObject("{"));
    assertFalse(StringJsonUtils.isObject("test"));
    assertFalse(StringJsonUtils.isObject(null));
  }

  @Test
  public void checkIsNull() {
    assertTrue(StringJsonUtils.isNull("null"));
    assertFalse(StringJsonUtils.isNull("test"));
    assertFalse(StringJsonUtils.isNull(null));
  }

  @Test
  public void checkIsFalse() {
    assertTrue(StringJsonUtils.isFalse("false"));
    assertFalse(StringJsonUtils.isFalse("test"));
    assertFalse(StringJsonUtils.isFalse(null));
  }
}
