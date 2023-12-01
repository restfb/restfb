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
package com.restfb.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringJsonUtilsTest {

  @Test
  void checkIsEmptyList() {
    assertTrue(StringJsonUtils.isEmptyList("[]"));
    assertFalse(StringJsonUtils.isEmptyList("["));
    assertFalse(StringJsonUtils.isEmptyList("test"));
    assertFalse(StringJsonUtils.isEmptyList(null));
  }

  @Test
  void checkIsList() {
    assertTrue(StringJsonUtils.isList("[]"));
    assertTrue(StringJsonUtils.isList("["));
    assertFalse(StringJsonUtils.isList("test"));
    assertFalse(StringJsonUtils.isList(null));
  }

  @Test
  void checkIsEmptyObject() {
    assertTrue(StringJsonUtils.isEmptyObject("{}"));
    assertFalse(StringJsonUtils.isEmptyObject("{"));
    assertFalse(StringJsonUtils.isEmptyObject("test"));
    assertFalse(StringJsonUtils.isEmptyObject(null));
  }

  @Test
  void checkIsObject() {
    assertTrue(StringJsonUtils.isObject("{}"));
    assertTrue(StringJsonUtils.isObject("{"));
    assertFalse(StringJsonUtils.isObject("test"));
    assertFalse(StringJsonUtils.isObject(null));
  }

  @Test
  void checkIsNull() {
    assertTrue(StringJsonUtils.isNull("null"));
    assertFalse(StringJsonUtils.isNull("test"));
    assertFalse(StringJsonUtils.isNull(null));
  }

  @Test
  void checkIsFalse() {
    assertTrue(StringJsonUtils.isFalse("false"));
    assertFalse(StringJsonUtils.isFalse("test"));
    assertFalse(StringJsonUtils.isFalse(null));
  }
}
