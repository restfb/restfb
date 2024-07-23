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
package com.restfb.types.threads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

import java.util.Locale;

class TdMediaTest extends AbstractJsonMapperTests {

  @Test
  void checkBasic() {
    TdMedia threadsMedis = createJsonMapper().toJavaObject(jsonFromClasspath("threads/basic-media"), TdMedia.class);
    assertNotNull(threadsMedis);
    assertNotNull(threadsMedis.getOwner());
    assertEquals("1234567", threadsMedis.getOwner().getId());
    assertEquals("1234567", threadsMedis.getId());
    assertEquals(1697521323000L, threadsMedis.getTimestamp().getTime());
    assertEquals("abcdefg", threadsMedis.getShortcode());
    assertEquals(TdMediaType.TEXT_POST, threadsMedis.getMediaType());
    assertEquals(2, threadsMedis.getAllowlistedCountryCodesAsLocales().size());
    Locale locale1 = threadsMedis.getAllowlistedCountryCodesAsLocales().get(0);
    Locale locale2 = threadsMedis.getAllowlistedCountryCodesAsLocales().get(1);
    assertEquals("US", locale1.getCountry());
    assertEquals("CA", locale2.getCountry());
    assertEquals(2, threadsMedis.getAllowlistedCountryCodes().size());
  }
}
