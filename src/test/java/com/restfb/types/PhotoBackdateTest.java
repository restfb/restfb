/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.restfb.AbstractJsonMapperTests;

@RunWith(Parameterized.class)
public class PhotoBackdateTest extends AbstractJsonMapperTests {

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] { //
        { "v1_0/photo-day", "day", 1408302000000L }, //
        { "v1_0/photo-year", "year", 1408302000000L }, //
        { "v1_0/photo-month", "month", 1408302000000L }, //
        { "v1_0/photo-hour", "hour", 1408302000000L }, //
        { "v1_0/photo-min", "min", 1408302720000L }, //
        { "v1_0/photo-nobackdate", null, null }, //
        { "v2_0/photo-day", "day", 1408302000000L }, //
        { "v2_0/photo-year", "year", 1408302000000L }, //
        { "v2_0/photo-month", "month", 1408302000000L }, //
        { "v2_0/photo-hour", "hour", 1408302000000L }, //
        { "v2_0/photo-min", "min", 1408302720000L }, //
        { "v2_0/photo-nobackdate", null, null }, //
        { "v2_1/photo-day", "day", 1408302000000L }, //
        { "v2_1/photo-year", "year", 1408302000000L }, //
        { "v2_1/photo-month", "month", 1408302000000L }, //
        { "v2_1/photo-hour", "hour", 1408302000000L }, //
        { "v2_1/photo-min", "min", 1408302720000L }, //
        { "v2_1/photo-nobackdate", null, null }, //

    });
  }

  private String fileName;

  private String expectedGranularity;

  private Date expectedTime;

  public PhotoBackdateTest(String fileName, String granularity, Long time) {
    this.fileName = fileName;
    this.expectedGranularity = granularity;
    if (time != null) {
      expectedTime = new Date(time);
    } else {
      expectedTime = null;
    }

  }

  @Test
  public void checkBackDate() {
    Photo examplePhoto = createJsonMapper().toJavaObject(jsonFromClasspath(fileName), Photo.class);
    assertEquals(expectedGranularity, examplePhoto.getBackdatedTimeGranularity());
    assertEquals(expectedTime, examplePhoto.getBackdatedTime());
  }

}
