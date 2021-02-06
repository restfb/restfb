/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.restfb.AbstractJsonMapperTests;

public class IgUserTest extends AbstractJsonMapperTests {

  @Test
  public void checkUser() {
    IgUser igUser = createJsonMapper().toJavaObject(jsonFromClasspath("instagram/user"), IgUser.class);
    assertNull(igUser.getBiography());
    assertEquals("tester2018", igUser.getUsername());
    assertNull(igUser.getWebsite());
    assertEquals(9876543L, igUser.getIgId().longValue());
    assertEquals(10l, igUser.getFollowersCount().longValue());
    assertEquals(15l, igUser.getFollowsCount().longValue());
    assertEquals(2l, igUser.getMediaCount().longValue());
    assertEquals("Tester Test", igUser.getName());
  }
}
