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
package com.restfb;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

class ConnectionTest extends AbstractJsonMapperTests {

  @Test
  void checkV1_0() {
    Connection<User> con = new Connection<>(new DefaultFacebookClient(Version.LATEST),
      jsonFromClasspath("v1_0/connection-user-friends"), User.class);
    assertThat(con.getTotalCount()).isNull();
    assertThat(con.getNextPageUrl()).isNotNull();
    assertThat(con.hasNext()).isTrue();
  }

  @Test
  void checkV2_0() {
    Connection<User> con = new Connection<>(new DefaultFacebookClient(Version.LATEST),
      jsonFromClasspath("v2_0/connection-user-friends"), User.class);
    assertThat(con.getTotalCount()).isEqualTo(99);
  }

  @Test
  void checkV2_1() {
    Connection<User> con = new Connection<>(new DefaultFacebookClient(Version.LATEST),
      jsonFromClasspath("v2_1/connection-user-friends"), User.class);
    assertThat(con.getTotalCount()).isEqualTo(99);
  }

  @Test
  void checkNoData() {
    Connection<JsonObject> con = new Connection<>(new DefaultFacebookClient(Version.LATEST),
      jsonFromClasspath("connection-nodata"), JsonObject.class);
    assertThat(con.getData()).isEmpty();
    assertThat(con.hasNext()).isFalse();

    for (List<JsonObject> objects : con) {
      assertThat(objects).isEmpty();
    }
  }

  @Test
  void checkNullJson() {
    assertThrows(FacebookJsonMappingException.class,
      () -> new Connection<>(new DefaultFacebookClient(Version.LATEST), null, User.class));
  }

  @Test
  void checkInvalidJson() {
    assertThrows(FacebookJsonMappingException.class,
      () -> new Connection<>(new DefaultFacebookClient(Version.LATEST), "{", User.class));
  }

  @Test
  void checkIterator_reachAllElements() {
    Connection<FacebookType> connection = create3PageConnection();

    assertThat(connection).isNotNull();

    int counter = 0;
    for (List<FacebookType> page : connection) {
      for (FacebookType type : page) {
        counter++;
      }
    }

    assertThat(counter).isEqualTo(18);
  }

  @Test
  void checkIterator_snapshot() {
    Connection<FacebookType> connection = create3PageConnection();
    assertThat(connection).isNotNull();

    ConnectionIterator<FacebookType> it = connection.iterator();
    assertThat(it).isNotNull();

    assertThat(connection).isEqualTo(it.snapshot());
  }

  @Test
  void checkIterator_compatibility() {
    Connection<FacebookType> connection = create3PageConnection();
    assertThat(connection).isNotNull();

    Iterator<List<FacebookType>> it = connection.iterator();
    assertThat(it).isNotNull();
  }

  @Test
  void checkIterator_snapshotNext() {
    Connection<FacebookType> connection = create3PageConnection();
    assertThat(connection).isNotNull();

    ConnectionIterator<FacebookType> it = connection.iterator();
    assertThat(it).isNotNull();

    it.next(); // first page
    it.next(); // second page

    assertThat(connection).isNotEqualTo(it.snapshot());

    assertThat(it.snapshot().getPreviousPageUrl()).isNotNull().contains("page1");
    assertThat(it.snapshot().getNextPageUrl()).isNotNull().contains("page3");
  }

  @Test
  void checkIterator_lastThrowsException() {
    Connection<FacebookType> connection = create3PageConnection();
    ConnectionIterator<FacebookType> it = connection.iterator();
    it.next(); // first page
    it.next(); // second page
    it.next(); // third and last page

    assertThat(it.hasNext()).isFalse();
    assertThrows(NoSuchElementException.class, it::next);
  }

  @Test
  void checkIterator_withCursor() {
    Connection<FacebookType> connection = createCursorConnection(false);
    assertThat(connection.getAfterCursor()).isEqualTo("NzU1MjI1MjU0");
    assertThat(connection.getBeforeCursor()).isEqualTo("MTY1MTAyNjUxNg==");
  }

  @Test
  void checkIterator_withCursorOnly() {
    Connection<FacebookType> connection = createCursorConnection(true);
    assertThat(connection.getAfterCursor()).isEqualTo("NzU1MjI1MjU0");
    assertThat(connection.getBeforeCursor()).isEqualTo("MTY1MTAyNjUxNg==");
    assertThat(connection.hasNext()).isTrue();
    assertThat(connection.hasPrevious()).isTrue();
    assertThat(connection.getNextPageUrl()).isNotNull().contains("cursor").contains("&after=NzU1MjI1MjU0");
    assertThat(connection.getPreviousPageUrl()).isNotNull().contains("cursor").contains("&before=MTY1MTAyNjUxNg==");
  }

  @Test
  void checkIteration_withCursorOnly() {
    Connection<FacebookType> connection = create3PageConnectionWithCursorOnly();

    assertThat(connection.getAfterCursor()).isEqualTo("cursor-p1-after");
    assertThat(connection.getBeforeCursor()).isNull();
    assertThat(connection.getNextPageUrl()).isNotNull().contains("page1");
    assertThat(connection.getPreviousPageUrl()).isNull();

    connection = connection.fetchNextPage();

    assertThat(connection.getAfterCursor()).isEqualTo("cursor-p2-after");
    assertThat(connection.getBeforeCursor()).isEqualTo("cursor-p2-before");
    assertThat(connection.getNextPageUrl()).isNotNull().contains("page1");
    assertThat(connection.getPreviousPageUrl()).isNotNull().contains("page1");

    connection = connection.fetchNextPage();

    assertThat(connection.getAfterCursor()).isNull();
    assertThat(connection.getBeforeCursor()).isEqualTo("cursor-p3-before");
    assertThat(connection.getNextPageUrl()).isNull();
    assertThat(connection.getPreviousPageUrl()).isNotNull().contains("page1");

    assertThat(connection.hasNext()).isFalse();
  }

  @Test
  void checkIteration_withSameCursor() {
    ConnectionIterator<JsonObject> con = new Connection<>(new DefaultFacebookClient(Version.LATEST),
      jsonFromClasspath("connection-same-cursor"), JsonObject.class).iterator();
    assertThat(con.hasNext()).isTrue();
    con.next();
    assertThat(con.hasNext()).isFalse();
  }

  @Test
  void checkIteration_withSameCursorAndClient() {
    FakeWebRequestor fakeWebRequestor = new FakeWebRequestor() {
      @Override
      public Response executeGet(String url) throws IOException {
        if (url.equals("https://graph.facebook.com/v3.2/me/adaccounts?access_token=token&format=json")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-same-cursor"));
        }

        return new Response(HTTP_OK, url);
      }
    };
    DefaultFacebookClient facebookClient = new DefaultFacebookClient("token", fakeWebRequestor, new DefaultJsonMapper(), Version.VERSION_3_2);

    ConnectionIterator<JsonObject> it = facebookClient.fetchConnection("/me/adaccounts", JsonObject.class).iterator();
    assertThat(it).isNotNull();
    assertThat(it.hasNext()).isTrue();
    it.next();
    assertThat(it.hasNext()).isFalse();
  }

  private Connection<FacebookType> createCursorConnection(boolean cursorOnly) {
    String connectionWithCursorTestFile = "connection-with-cursor";
    if (cursorOnly) {
      connectionWithCursorTestFile += "-only";
    }
    WebRequestor.Response dummyResponse =
        new WebRequestor.Response(HTTP_OK, jsonFromClasspath(connectionWithCursorTestFile));

    DefaultFacebookClient facebookClient = new DefaultFacebookClient("token", new FakeWebRequestor(dummyResponse),
      new DefaultJsonMapper(), Version.VERSION_2_8);
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

  private Connection<FacebookType> create3PageConnectionWithCursorOnly() {
    FakeWebRequestor fakeWebRequestor = new FakeWebRequestor() {
      @Override
      public Response executeGet(String url) throws IOException {

        if (url.equals("https://graph.facebook.com/v2.8/page1?access_token=token&format=json")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p1-cursor-only"));
        }

        if (url.equals("https://graph.facebook.com/v2.8/page1?access_token=token&format=json&after=cursor-p1-after")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p2-cursor-only"));
        }

        if (url.equals("https://graph.facebook.com/v2.8/page1?access_token=token&format=json&after=cursor-p2-after")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p3-cursor-only"));
        }

        return new Response(HTTP_OK, url);

      }
    };
    DefaultFacebookClient facebookClient =
        new DefaultFacebookClient("token", fakeWebRequestor, new DefaultJsonMapper(), Version.VERSION_2_8);
    return facebookClient.fetchConnection("/page1", FacebookType.class);
  }
}
