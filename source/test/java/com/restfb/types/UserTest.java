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
import com.restfb.types.User.Picture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class UserTest extends AbstractJsonMapperTests {

  @Test
  public void checkUserPicture() {
    User exampleUser = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/user-picture"), User.class);
    assertEquals("Tester", exampleUser.getName());
    assertEquals("123456789", exampleUser.getId());
    Picture pic = exampleUser.getPicture();
    assertFalse(pic.getIsSilhouette());
    assertNotNull(pic.getUrl());
    assertEquals("https://fbcdn-profile-a.akamaihd.net/profilepic", pic.getUrl());
  }

  @Test
  public void checkUserNoPicture() {
    User exampleUser = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/user-no-picture"), User.class);
    assertEquals("Tester", exampleUser.getName());
    assertEquals("123456789", exampleUser.getId());
    assertTrue(null == exampleUser.getPicture());
  }

  @Test
  public void checkAgeRangeNoUpper() {
    User exampleUser = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/age_range_no_upper"), User.class);
    assertEquals("Test Benutzer", exampleUser.getName());
    assertEquals("123456789", exampleUser.getId());
    assertEquals(21, exampleUser.getAgeRange().getMin().intValue());
    assertNull(exampleUser.getAgeRange().getMax());
  }

  @Test
  public void checkAgeRange() {
    User exampleUser = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/age_range"), User.class);
    assertEquals("Test Benutzer", exampleUser.getName());
    assertEquals("123456789", exampleUser.getId());
    assertEquals(13, exampleUser.getAgeRange().getMin().intValue());
    assertEquals(17, exampleUser.getAgeRange().getMax().intValue());
  }
}
