/*
 * Copyright (c) 2010-2014 Mark Allen.
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

import static com.restfb.util.UrlUtils.extractParametersFromQueryString;
import static com.restfb.util.UrlUtils.extractParametersFromUrl;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests that exercise {@link com.restfb.util.UrlUtils}.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class UrlUtilsTest {
  @Test
  public void queryString() {
    assertTrue(extractParametersFromQueryString(null).size() == 0);
    assertTrue(extractParametersFromQueryString("").size() == 0);
    assertTrue(extractParametersFromQueryString("access_token=123").size() == 1);
    assertTrue(extractParametersFromQueryString("?access_token=123").size() == 1);
  }

  @Test
  public void urlParameters() {
    assertTrue(extractParametersFromUrl(null).size() == 0);
    assertTrue(extractParametersFromUrl("").size() == 0);
    assertTrue(extractParametersFromUrl("access_token=123").size() == 0);
    assertTrue(extractParametersFromUrl("?access_token=123").size() == 1);
    assertTrue(extractParametersFromUrl("http://whatever?access_token=123").size() == 1);
  }
}