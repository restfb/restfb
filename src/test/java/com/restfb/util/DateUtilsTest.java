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

import static com.restfb.util.DateUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit tests that exercise {@link com.restfb.util.DateUtils}.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
class DateUtilsTest {

  private static Stream<DateFormatStrategy> strategies() {
    return Stream.of(new CachedDateFormatStrategy(), new SimpleDateFormatStrategy());
  }

  /**
   * Tests the "short" date format.
   */
  @ParameterizedTest
  @MethodSource("strategies")
  void shortDatesSimple(DateFormatStrategy expectedStrategy) {
    DateUtils.setDateFormatStrategy(expectedStrategy);
    assertThat(toDateFromShortFormat("04/15/1984")).isNotNull();
    assertThat(toDateFromShortFormat("01/01/1970")).isNotNull();
    assertThat(toDateFromShortFormat("1970-09-15")).isNotNull();
    assertThat(toDateFromShortFormat("junk")).isNull();
    assertThat(DateUtils.getDateFormatStrategy()).isEqualTo(expectedStrategy);
  }

  /**
   * FB uses "long" date formats with and without timezones. Make sure we handle both gracefully.
   */
  @ParameterizedTest
  @MethodSource("strategies")
  void longDatesSimple(DateFormatStrategy expectedStrategy) {
    DateUtils.setDateFormatStrategy(expectedStrategy);
    assertThat(toDateFromLongFormat("2011-12-22T21:00:00+0000")).isNotNull();
    assertThat(toDateFromLongFormat("2011-12-22T21:00:00")).isNotNull();
    assertThat(toDateFromLongFormat("1331784257")).isNotNull();
    assertThat(toDateFromLongFormat("junk")).isNull();
    assertThat(DateUtils.getDateFormatStrategy()).isEqualTo(expectedStrategy);
  }

  /**
   * Tests the "month and year" date format.
   */
  @ParameterizedTest
  @MethodSource("strategies")
  void monthYearDates(DateFormatStrategy expectedStrategy) {
    DateUtils.setDateFormatStrategy(expectedStrategy);
    assertThat(toDateFromMonthYearFormat("2007-03")).isNotNull();
    assertThat(toDateFromMonthYearFormat("2011-12")).isNotNull();
    assertThat(toDateFromMonthYearFormat("0000-00")).isNull();
    assertThat(toDateFromMonthYearFormat("junk")).isNull();
    assertThat(DateUtils.getDateFormatStrategy()).isEqualTo(expectedStrategy);
  }

  @ParameterizedTest
  @MethodSource("strategies")
  void dateToString(DateFormatStrategy expectedStrategy) {
    DateUtils.setDateFormatStrategy(expectedStrategy);
    assertThat(toLongFormatFromDate(null)).isNull();
    assertThat(toLongFormatFromDate(new Date())).isNotNull();
    assertThat(toLongFormatFromDate(new Date(1474559324000L))).isEqualTo("2016-09-22T15:48:44");
    assertThat(DateUtils.getDateFormatStrategy()).isEqualTo(expectedStrategy);
  }

  @ParameterizedTest
  @MethodSource("strategies")
  void dateToShortString(DateFormatStrategy expectedStrategy) {
    DateUtils.setDateFormatStrategy(expectedStrategy);
    assertThat(toShortFormatFromDate(null)).isNull();
    assertThat(toShortFormatFromDate(new Date())).isNotNull();
    assertThat(toShortFormatFromDate(new Date(1474559324000L))).isEqualTo("2016-09-22");
    assertThat(DateUtils.getDateFormatStrategy()).isEqualTo(expectedStrategy);
  }
}