/*
 * Copyright (c) 2010-2013 Mark Allen.
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

import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static com.restfb.util.DateUtils.toDateFromMonthYearFormat;
import static com.restfb.util.DateUtils.toDateFromShortFormat;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests that exercise {@link com.restfb.util.DateUtils}.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class DateUtilsTest {
  /**
   * Tests the "short" date format.
   */
  @Test
  public void shortDates() {
    assertTrue(toDateFromShortFormat("04/15/1984") != null);
    assertTrue(toDateFromShortFormat("01/01/1970") != null);
    assertTrue(toDateFromShortFormat("1970-09-15") != null);
    assertTrue(toDateFromShortFormat("junk") == null);
  }

  /**
   * FB uses "long" date formats with and without timezones. Make sure we handle both gracefully.
   */
  @Test
  public void longDates() {
    assertTrue(toDateFromLongFormat("2011-12-22T21:00:00+0000") != null);
    assertTrue(toDateFromLongFormat("2011-12-22T21:00:00") != null);
    assertTrue(toDateFromLongFormat("1331784257") != null);
    assertTrue(toDateFromLongFormat("junk") == null);
  }

  /**
   * Tests the "month and year" date format.
   */
  @Test
  public void monthYearDates() {
    assertTrue(toDateFromMonthYearFormat("2007-03") != null);
    assertTrue(toDateFromMonthYearFormat("2011-12") != null);
    assertTrue(toDateFromMonthYearFormat("0000-00") == null);
    assertTrue(toDateFromMonthYearFormat("junk") == null);
  }
}