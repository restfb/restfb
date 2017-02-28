/**
 * Copyright (c) 2010-2017 Mark Allen, Norbert Bartels.
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

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.*;

import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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

  @Test
  public void checkIterator_reachAllElements() {
    Connection<FacebookType> connection = create3PageConnection();
    assertNotNull(connection);

    int counter = 0;
    for (List<FacebookType> page : connection) {
      for (FacebookType type : page) {
        counter++;
      }
    }

    assertEquals(18, counter);
  }

  @Test
  public void checkIterator_snapshot() {
    Connection<FacebookType> connection = create3PageConnection();
    assertNotNull(connection);

    ConnectionIterator<FacebookType> it = connection.iterator();
    assertNotNull(it);

    assertEquals(connection, it.snapshot());
  }

  @Test
  public void checkIterator_compatibility() {
    Connection<FacebookType> connection = create3PageConnection();
    assertNotNull(connection);

    Iterator<List<FacebookType>> it = connection.iterator();
    assertNotNull(it);
  }

  @Test
  public void checkIterator_snapshotNext() {
    Connection<FacebookType> connection = create3PageConnection();
    assertNotNull(connection);

    ConnectionIterator<FacebookType> it = connection.iterator();
    assertNotNull(it);

    it.next(); // first page
    it.next(); // second page

    assertNotEquals(connection, it.snapshot());

    assertNotNull(it.snapshot().getPreviousPageUrl());
    assertTrue(it.snapshot().getPreviousPageUrl().contains("page1"));

    assertNotNull(it.snapshot().getNextPageUrl());
    assertTrue(it.snapshot().getNextPageUrl().contains("page3"));
  }

  @Test(expected = NoSuchElementException.class)
  public void checkIterator_lastThrowsException() {
    Connection<FacebookType> connection = create3PageConnection();
    ConnectionIterator<FacebookType> it = connection.iterator();
    it.next(); // first page
    it.next(); // second page
    it.next(); // third and last page

    assertFalse(it.hasNext());
    it.next();
    fail(NoSuchElementException.class.getName());
  }

  @Test
  public void checkIterator_withCursor() {
    Connection<FacebookType> connection = createCursorConnection();
    assertEquals("NzU1MjI1MjU0", connection.getAfterCursor());
    assertEquals("MTY1MTAyNjUxNg==", connection.getBeforeCursor());
  }

  private Connection<FacebookType> createCursorConnection() {
    FakeWebRequestor fakeWebRequestor = new FakeWebRequestor() {
      @Override
      public Response executeGet(String url) throws IOException {
        return new Response(HTTP_OK, jsonFromClasspath("connection-with-cursor"));
      }
    };
    DefaultFacebookClient facebookClient =
        new DefaultFacebookClient("token", fakeWebRequestor, new DefaultJsonMapper(), Version.VERSION_2_8);
    return facebookClient.fetchConnection("/cursor", FacebookType.class);
  }

  private Connection<FacebookType> create3PageConnection() {
    FakeWebRequestor fakeWebRequestor = new FakeWebRequestor() {
      @Override
      public Response executeGet(String url) throws IOException {

        if (url.equals("https://graph.facebook.com/v2.8/page1?access_token=token&format=json")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p1"));
        }

        if (url.equals("https://graph.facebook.com/v2.8/page2?access_token=token&format=json")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p2"));
        }

        if (url.equals("https://graph.facebook.com/v2.8/page3?access_token=token&format=json")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p3"));
        }

        return new Response(HTTP_OK, url);

      }
    };
    DefaultFacebookClient facebookClient =
        new DefaultFacebookClient("token", fakeWebRequestor, new DefaultJsonMapper(), Version.VERSION_2_8);
    return facebookClient.fetchConnection("/page1", FacebookType.class);
  }
}
