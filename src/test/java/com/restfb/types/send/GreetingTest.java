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
package com.restfb.types.send;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.testutils.AssertJson;

class GreetingTest {

  @Test
  void checkDefaultLocale() {
    Greeting greeting = new Greeting("Test Greeting");
    JsonMapper mapper = new DefaultJsonMapper();

    AssertJson.assertEquals("{\"text\":\"Test Greeting\",\"locale\":\"default\"}", mapper.toJson(greeting));
  }

  @Test
  void checkGermanLocale() {
    Greeting greeting = new Greeting("de_de", "Test Greeting");
    JsonMapper mapper = new DefaultJsonMapper();

    AssertJson.assertEquals("{\"text\":\"Test Greeting\",\"locale\":\"de_de\"}", mapper.toJson(greeting));
  }

  @Test
  void checkUsLocale() {
    Greeting greeting = new Greeting("en_us", "Test Greeting");
    JsonMapper mapper = new DefaultJsonMapper();

    AssertJson.assertEquals("{\"text\":\"Test Greeting\",\"locale\":\"en_us\"}", mapper.toJson(greeting));
  }

  @Test
  void testJavaLocale() {
    Locale loc = Locale.CHINA;

    Greeting greeting = new Greeting(loc, "Test Greeting");
    JsonMapper mapper = new DefaultJsonMapper();

    AssertJson.assertEquals("{\"text\":\"Test Greeting\",\"locale\":\"zh_cn\"}", mapper.toJson(greeting));
  }
}
