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
package com.restfb.types.instagram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class IgUserTest extends AbstractJsonMapperTests {

  @Test
  void checkUser() {
    IgUser igUser = createJsonMapper().toJavaObject(jsonFromClasspath("instagram/user"), IgUser.class);
    assertNull(igUser.getBiography());
    assertEquals("tester2018", igUser.getUsername());
    assertNull(igUser.getWebsite());
    assertEquals(9876543L, igUser.getIgId().longValue());
    assertEquals(10L, igUser.getFollowersCount().longValue());
    assertEquals(15L, igUser.getFollowsCount().longValue());
    assertEquals(2L, igUser.getMediaCount().longValue());
    assertEquals("Tester Test", igUser.getName());
  }
}
