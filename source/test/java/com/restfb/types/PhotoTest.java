/*
 * Copyright (c) 2010-2015 Norbert Bartels
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

import com.restfb.AbstractJsonMapperTests;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PhotoTest extends AbstractJsonMapperTests {

    @Test
    public void checkDayV1_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-day"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "day");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkYearV1_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-year"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "year");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkMonthV1_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-month"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "month");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkHourV1_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-hour"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "hour");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkMinuteV1_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-min"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "min");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302720000L));
    }

    @Test
    public void checkNoBackdateV1_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/photo-nobackdate"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), null);
        assertEquals(examplePhoto.getBackdatedTime(), null);
    }

    @Test
    public void checkDayV2_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-day"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "day");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkYearV2_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-year"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "year");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkMonthV2_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-month"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "month");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkHourV2_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-hour"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "hour");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkMinuteV2_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-min"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "min");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302720000L));
    }

    @Test
    public void checkNoBackdateV2_0() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/photo-nobackdate"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), null);
        assertEquals(examplePhoto.getBackdatedTime(), null);
    }

    @Test
    public void checkDayV2_1() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-day"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "day");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkYearV2_1() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-year"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "year");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkMonthV2_1() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-month"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "month");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkHourV2_1() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-hour"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "hour");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302000000L));
    }

    @Test
    public void checkMinuteV2_1() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-min"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), "min");
        assertEquals(examplePhoto.getBackdatedTime(), new Date(1408302720000L));
    }

    @Test
    public void checkNoBackdateV2_1() {
        Photo examplePhoto
                = createJsonMapper().toJavaObject(jsonFromClasspath("v2_1/photo-nobackdate"), Photo.class);
        assertEquals(examplePhoto.getBackdatedTimeGranularity(), null);
        assertEquals(examplePhoto.getBackdatedTime(), null);
    }
}
