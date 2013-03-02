/*
 * Copyright (c) 2010-2013 Mark Allen.
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

package com.restfb;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.restfb.types.FacebookType;
import com.restfb.types.User;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class JsonMapperErrorSwallowingTest extends AbstractJsonMapperTests {
  /**
   * Does the mapper return null for empty strings?
   */
  @Test
  public void emptyStrings() {
    assertTrue(null == createErrorSwallowingJsonMapper().toJavaObject("", Integer.class));
    assertTrue(null == createErrorSwallowingJsonMapper().toJavaObject("", String.class));
  }

  /**
   * Does the mapper return null for only those fields that are mis-mapped?
   */
  @Test
  public void objectWithIncorrectFields() {
    MostlyIncorrectUser mostlyIncorrectUser =
        createErrorSwallowingJsonMapper()
          .toJavaObject(jsonFromClasspath("user-with-photos"), MostlyIncorrectUser.class);

    assertTrue(null == mostlyIncorrectUser.uid);
    assertTrue(null == mostlyIncorrectUser.name);
    assertTrue("1".equals(mostlyIncorrectUser.photos.get(0).getId()));
  }

  /**
   * Does the mapper return null for only those list elements that are mis-mapped?
   */
  @Test
  public void listWithIncorrectObject() {
    List<User> users =
        createErrorSwallowingJsonMapper().toJavaList(jsonFromClasspath("incorrect-user-list"), User.class);

    assertTrue("123".equals(users.get(0).getId()));
    assertTrue(null == users.get(1));
    assertTrue("456".equals(users.get(2).getId()));
    assertTrue(users.size() == 3);
  }

  static class MostlyIncorrectUser {
    // Incorrect mapping
    @Facebook
    Boolean uid;

    // Incorrect mapping
    @Facebook
    Double name;

    // Correct mapping
    @Facebook
    List<FacebookType> photos;
  }
}