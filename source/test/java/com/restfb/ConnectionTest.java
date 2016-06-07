/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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

import static org.junit.Assert.*;

import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.types.User;

import org.junit.Test;

public class ConnectionTest extends AbstractJsonMapperTests {

  @Test
  public void checkV1_0() {
    Connection<User> con = new Connection<User>(new DefaultFacebookClient(Version.LATEST),
      jsonFromClasspath("v1_0/connection-user-friends"), User.class);
    assertEquals(null, con.getTotalCount());
    assertNotNull(con.getNextPageUrl());
    assertTrue(con.hasNext());
  }

  @Test
  public void checkV2_0() {
    Connection<User> con = new Connection<User>(new DefaultFacebookClient(Version.LATEST),
      jsonFromClasspath("v2_0/connection-user-friends"), User.class);
    assertEquals(99L, con.getTotalCount().longValue());
  }

  @Test
  public void checkV2_1() {
    Connection<User> con = new Connection<User>(new DefaultFacebookClient(Version.LATEST),
      jsonFromClasspath("v2_1/connection-user-friends"), User.class);
    assertEquals(99L, con.getTotalCount().longValue());
  }

  @Test(expected = FacebookJsonMappingException.class)
  public void checkNullJson() {
    new Connection<User>(new DefaultFacebookClient(Version.LATEST), null, User.class);
  }

  @Test(expected = FacebookJsonMappingException.class)
  public void checkInvalidJson() {
    new Connection<User>(new DefaultFacebookClient(Version.LATEST), "{", User.class);
  }
}
