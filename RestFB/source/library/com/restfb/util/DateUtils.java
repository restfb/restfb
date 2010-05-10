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

package com.restfb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A collection of date-handling utility methods.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6
 */
public final class DateUtils {
  /**
   * Facebook "long" date format (ISO 8601). Example: {@code
   * 2010-02-28T16:11:08+0000}
   */
  public static final String FACEBOOK_LONG_DATE_FORMAT =
      "yyyy-MM-dd'T'kk:mm:ssZ";

  /**
   * Facebook short date format. Example: {@code 04/15/1984}
   */
  public static final String FACEBOOK_SHORT_DATE_FORMAT = "MM/dd/yyyy";

  /**
   * Prevents instantiation.
   */
  private DateUtils() {}

  /**
   * Returns a Java representation of a Facebook "long" {@code date} string.
   * 
   * @param date
   *          Facebook {@code date} string.
   * @return Java date representation of the given Facebook "long" {@code date}
   *         string or {@code null} if {@code date} is {@code null}.
   * @throws IllegalArgumentException
   *           If the provided {@code date} isn't in the Facebook "long" date
   *           format (ISO 8601).
   */
  public static Date toDateFromLongFormat(String date)
      throws IllegalArgumentException {
    return toDateWithFormatString(date, FACEBOOK_LONG_DATE_FORMAT);
  }

  /**
   * Returns a Java representation of a Facebook "short" {@code date} string.
   * 
   * @param date
   *          Facebook {@code date} string.
   * @return Java date representation of the given Facebook "short" {@code date}
   *         string or {@code null} if {@code date} is {@code null}.
   * @throws IllegalArgumentException
   *           If the provided {@code date} isn't in the Facebook "short" date
   *           format.
   */
  public static Date toDateFromShortFormat(String date)
      throws IllegalArgumentException {
    return toDateWithFormatString(date, FACEBOOK_SHORT_DATE_FORMAT);
  }

  /**
   * Returns a Java representation of a {@code date} string.
   * 
   * @param date
   *          Date in string format.
   * @return Java date representation of the given {@code date} string or
   *         {@code null} if {@code date} is {@code null}.
   * @throws IllegalArgumentException
   *           If the provided {@code date} isn't in the given {@code format}.
   */
  private static Date toDateWithFormatString(String date, String format) {
    if (date == null)
      return null;

    try {
      return new SimpleDateFormat(format).parse(date);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Unable to parse date '" + date
          + "' using format string '" + format + "'", e);
    }
  }
}