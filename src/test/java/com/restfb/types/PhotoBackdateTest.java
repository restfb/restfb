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
package com.restfb.types;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.restfb.AbstractJsonMapperTests;

class PhotoBackdateTest extends AbstractJsonMapperTests {

  static Stream<Arguments> backdateData() {
    return Stream.of(Arguments.of("v1_0/photo-day", "day", 1408302000000L), //
      Arguments.of("v1_0/photo-year", "year", 1408302000000L), //
      Arguments.of("v1_0/photo-month", "month", 1408302000000L), //
      Arguments.of("v1_0/photo-hour", "hour", 1408302000000L), //
      Arguments.of("v1_0/photo-min", "min", 1408302720000L), //
      Arguments.of("v1_0/photo-nobackdate", null, null), //
      Arguments.of("v2_0/photo-day", "day", 1408302000000L), //
      Arguments.of("v2_0/photo-year", "year", 1408302000000L), //
      Arguments.of("v2_0/photo-month", "month", 1408302000000L), //
      Arguments.of("v2_0/photo-hour", "hour", 1408302000000L), //
      Arguments.of("v2_0/photo-min", "min", 1408302720000L), //
      Arguments.of("v2_0/photo-nobackdate", null, null), //
      Arguments.of("v2_1/photo-day", "day", 1408302000000L), //
      Arguments.of("v2_1/photo-year", "year", 1408302000000L), //
      Arguments.of("v2_1/photo-month", "month", 1408302000000L), //
      Arguments.of("v2_1/photo-hour", "hour", 1408302000000L), //
      Arguments.of("v2_1/photo-min", "min", 1408302720000L), //
      Arguments.of("v2_1/photo-nobackdate", null, null) //
    );
  }

  @ParameterizedTest
  @MethodSource("backdateData")
  void checkBackDate(String fileName, String expectedGranularity,
      @AggregateWith(DateAggregator.class) Date expectedTime) {

    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath(fileName), Photo.class);
    assertEquals(expectedGranularity, examplePhoto.getBackdatedTimeGranularity());
    assertEquals(expectedTime, examplePhoto.getBackdatedTime());
  }

  static class DateAggregator implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(ArgumentsAccessor arguments, ParameterContext context)
        throws ArgumentsAggregationException {
      if (arguments.getLong(2) != null) {
        return new Date(arguments.getLong(2));
      } else {
        return null;
      }
    }
  }

}
