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

import static com.restfb.logging.RestFBLogger.UTILS_LOGGER;
import static java.lang.String.format;

import java.text.ParseException;
import java.util.Date;

/**
 * A collection of date-handling utility methods.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6
 */
public final class DateUtils {
  /**
   * Facebook "long" date format (IETF RFC 3339). Example: {@code 2010-02-28T16:11:08+0000}
   */
  public static final String FACEBOOK_LONG_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

  /**
   * Facebook "long" date format (IETF RFC 3339) without a timezone component. Example: {@code 2010-02-28T16:11:08}
   */
  public static final String FACEBOOK_LONG_DATE_FORMAT_WITHOUT_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss";

  /**
   * Facebook "long" date format (IETF RFC 3339) without a timezone or seconds component. Example: {@code 2010-02-28T16:11}
   */
  public static final String FACEBOOK_LONG_DATE_FORMAT_WITHOUT_TIMEZONE_OR_SECONDS = "yyyy-MM-dd'T'HH:mm";

  /**
   * Facebook short date format. Example: {@code 04/15/1984}
   */
  public static final String FACEBOOK_SHORT_DATE_FORMAT = "MM/dd/yyyy";

  /**
   * Facebook alternate short date format. Example: {@code 2012-09-15}
   */
  public static final String FACEBOOK_ALTERNATE_SHORT_DATE_FORMAT = "yyyy-MM-dd";

  /**
   * Facebook month-year only date format. Example: {@code Example: 2007-03}
   */
  public static final String FACEBOOK_MONTH_YEAR_DATE_FORMAT = "yyyy-MM";

  /**
   * DateFormatStrategy (default: SimpleDateFormat).
   */
  private static DateFormatStrategy strategy = new SimpleDateFormatStrategy();

  /**
   * Prevents instantiation.
   */
  private DateUtils() {
    // Prevents instantiation
  }

  /**
   * Returns a Java representation of a Facebook "long" {@code date} string, or the number of seconds since the epoch.
   * <p>
   * Supports dates with or without timezone information.
   * 
   * @param date
   *          Facebook {@code date} string.
   * @return Java date representation of the given Facebook "long" {@code date} string or {@code null} if {@code date}
   *         is {@code null} or invalid.
   */
  public static Date toDateFromLongFormat(String date) {
    if (date == null) {
      return null;
    }

    // Is this an all-digit date? Then assume it's the "seconds since epoch"
    // variant
    if (date.trim().matches("\\d+")) {
      return new Date(Long.parseLong(date) * 1000L);
    }

    Date parsedDate = toDateWithFormatString(date, FACEBOOK_LONG_DATE_FORMAT);

    // Fall back to variant without timezone if the initial parse fails
    if (parsedDate == null) {
      parsedDate = toDateWithFormatString(date, FACEBOOK_LONG_DATE_FORMAT_WITHOUT_TIMEZONE);
    }

    // Fall back to variant without seconds if secondary parse fails
    if (parsedDate == null) {
      parsedDate = toDateWithFormatString(date, FACEBOOK_LONG_DATE_FORMAT_WITHOUT_TIMEZONE_OR_SECONDS);
    }

    return parsedDate;
  }

  /**
   * Returns a Java representation of a Facebook "short" {@code date} string.
   * 
   * @param date
   *          Facebook {@code date} string.
   * @return Java date representation of the given Facebook "short" {@code date} string or {@code null} if {@code date}
   *         is {@code null} or invalid.
   */
  public static Date toDateFromShortFormat(String date) {
    if (date == null) {
      return null;
    }

    Date parsedDate = toDateWithFormatString(date, FACEBOOK_SHORT_DATE_FORMAT);

    // Fall back to variant if initial parse fails
    if (parsedDate == null) {
      parsedDate = toDateWithFormatString(date, FACEBOOK_ALTERNATE_SHORT_DATE_FORMAT);
    }

    return parsedDate;
  }

  /**
   * Returns a Java representation of a Facebook "month-year" {@code date} string.
   * 
   * @param date
   *          Facebook {@code date} string.
   * @return Java date representation of the given Facebook "month-year" {@code date} string or {@code null} if
   *         {@code date} is {@code null} or invalid.
   */
  public static Date toDateFromMonthYearFormat(String date) {
    if (date == null) {
      return null;
    }

    if ("0000-00".equals(date)) {
      return null;
    }

    return toDateWithFormatString(date, FACEBOOK_MONTH_YEAR_DATE_FORMAT);
  }

  /**
   * Returns a String representation of a {@code date} object
   * 
   * @param date
   *          as Date
   * @return String representation of a {@code date} object. The String is in the form {@code 2010-02-28T16:11:08}
   */
  public static String toLongFormatFromDate(Date date) {
    if (date == null) {
      return null;
    }

    return strategy.formatFor(FACEBOOK_LONG_DATE_FORMAT_WITHOUT_TIMEZONE).format(date);
  }

  /**
   * Returns a Java representation of a {@code date} string.
   * 
   * @param date
   *          Date in string format.
   * @return Java date representation of the given {@code date} string or {@code null} if {@code date} is {@code null}
   *         or invalid.
   */
  private static Date toDateWithFormatString(String date, String format) {
    if (date == null) {
      return null;
    }

    try {
      return strategy.formatFor(format).parse(date);
    } catch (ParseException e) {
      if (UTILS_LOGGER.isTraceEnabled()) {
        UTILS_LOGGER.trace(format("Unable to parse date '%s' using format string '%s': %s", date, format, e));
      }

      return null;
    }
  }

  /**
   * get the current DateFormatStrategy.
   * 
   * @return the current DateFormatStrategy
   */
  public static DateFormatStrategy getDateFormatStrategy() {
    return strategy;
  }

  /**
   * set the {@link DateFormatStrategy}.
   * 
   * default value: {@link SimpleDateFormatStrategy}
   * 
   * @param dateFormatStrategy
   *          the used {@see DateFormatStrategy}
   * 
   */
  public static void setDateFormatStrategy(DateFormatStrategy dateFormatStrategy) {
    strategy = dateFormatStrategy;
  }
}