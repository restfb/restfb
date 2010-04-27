/*
 * Copyright (c) 2010 Mark Allen.
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

package com.restfb.types;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@code String}-related utility methods.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
abstract class StringUtils {
  /**
   * Prevents instantiation.
   */
  private StringUtils() {}

  /**
   * Facebook date format (ISO 8601). Example: 2010-02-28T16:11:08+0000
   */
  private static final String FACEBOOK_DATE_FORMAT = "yyyy-MM-dd'T'kk:mm:ssZ";

  /**
   * Returns a Java representation of a Facebook {@code date} string.
   * 
   * @param date
   *          Facebook {@code date} string.
   * @return Java date representation of the given Facebook {@code date} string.
   * @throws IllegalArgumentException
   *           If the provided {@code date} isn't in the Facebook date format
   *           (ISO 8601).
   */
  static Date toDate(String date) throws IllegalArgumentException {
    if (date == null)
      return null;

    try {
      return new SimpleDateFormat(FACEBOOK_DATE_FORMAT).parse(date);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Unable to parse date '" + date
          + "' using format string '" + FACEBOOK_DATE_FORMAT + "'", e);
    }
  }
}