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
package com.restfb.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.restfb.AbstractJsonMapperTests;

public class UserProfileTest extends AbstractJsonMapperTests {

  @Test
  public void check() {
    UserProfile exampleUserProfile =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_9/userprofile"), UserProfile.class);
    assertNotNull(exampleUserProfile);
    assertEquals("Peter", exampleUserProfile.getFirstName());
    assertEquals("Chang", exampleUserProfile.getLastName());
    assertNotNull(exampleUserProfile.getLastAdReferral());
    assertEquals("6045246247433", exampleUserProfile.getLastAdReferral().getAdId());
    assertEquals("ADS", exampleUserProfile.getLastAdReferral().getSource());
    assertEquals("OPEN_THREAD", exampleUserProfile.getLastAdReferral().getType());
  }
}
