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
package com.restfb;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.restfb.types.ads.AdsInsights;

class ConnectionTest extends AbstractJsonMapperTests {

  private void createConnectionNull() {
    new Connection<>(new DefaultFacebookClient(Version.LATEST), null, User.class);
  }

  private void createConnectionJson() {
    new Connection<>(new DefaultFacebookClient(Version.LATEST), "{", User.class);
  }

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
    assertThrows(FacebookJsonMappingException.class, this::createConnectionNull);
  }

  @Test
  void checkInvalidJson() {
    assertThrows(FacebookJsonMappingException.class, this::createConnectionJson);
  }

  @Test
  void checkIterator_reachAllElements() {
    Connection<FacebookType> connection = create3PageConnection();

    assertThat(connection).isNotNull();
    long counter = StreamSupport.stream(connection.spliterator(), false).mapToLong(List::size).sum();
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
    assertThat(connection.hasNext()).isFalse();
    assertThat(connection.hasPrevious()).isFalse();
    assertThat(connection.getNextPageUrl()).isNull();
    assertThat(connection.getPreviousPageUrl()).isNull();
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
  void checkTypedSummary() {
    Connection<AdsInsights> con = new Connection<>(new DefaultFacebookClient(Version.LATEST), jsonFromClasspath("connection-typed-summary"), AdsInsights.class);
    assertThat(con.getTypedSummary()).isNotNull();
    AdsInsights insightsSummary = con.getTypedSummary();
    assertThat(insightsSummary.getActions()).hasSize(29);
    assertThat(insightsSummary.getClicks()).isEqualTo("223");
    assertThat(insightsSummary.getDateStart()).hasTime(1698796800000L);
  }

  @Test
  void checkIteration_withSameCursorAndClient() {
    FakeWebRequestor fakeWebRequestor = new FakeWebRequestor() {
      @Override
      public Response executeGet(Request request) {
        if (request.getFullUrl().equals("https://graph.facebook.com/v9.0/me/adaccounts?access_token=token&format=json")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-same-cursor"));
        }

        return new Response(HTTP_OK, request.getUrl());
      }
    };
    DefaultFacebookClient facebookClient =
        new DefaultFacebookClient("token", fakeWebRequestor, new DefaultJsonMapper(), Version.VERSION_9_0);

    ConnectionIterator<JsonObject> it = facebookClient.fetchConnection("/me/adaccounts", JsonObject.class).iterator();
    assertThat(it).isNotNull();
    assertThat(it.hasNext()).isTrue();
    it.next();
    assertThat(it.hasNext()).isFalse();
  }

  @Test
  void checkConnectionWithObject() {
    assertThrows(FacebookJsonMappingException.class, () -> new Connection<Post>(null, "{}", Post.class));
  }

  private Connection<FacebookType> createCursorConnection(boolean cursorOnly) {
    String connectionWithCursorTestFile = "connection-with-cursor";
    if (cursorOnly) {
      connectionWithCursorTestFile += "-only";
    }
    WebRequestor.Response dummyResponse =
        new WebRequestor.Response(HTTP_OK, jsonFromClasspath(connectionWithCursorTestFile));

    DefaultFacebookClient facebookClient = new DefaultFacebookClient("token", new FakeWebRequestor(dummyResponse),
      new DefaultJsonMapper(), Version.VERSION_9_0);
    return facebookClient.fetchConnection("/cursor", FacebookType.class);
  }

  private Connection<FacebookType> create3PageConnection() {
    FakeWebRequestor fakeWebRequestor = new FakeWebRequestor() {
      @Override
      public Response executeGet(Request request) {

        String url = request.getFullUrl();

        if (url.equals("https://graph.facebook.com/v9.0/page1?access_token=token&format=json")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p1"));
        }

        if (url.equals("https://graph.facebook.com/v9.0/page2?access_token=token&format=json")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p2"));
        }

        if (url.equals("https://graph.facebook.com/v9.0/page3?access_token=token&format=json")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p3"));
        }

        return new Response(HTTP_OK, url);
      }
    };
    DefaultFacebookClient facebookClient =
        new DefaultFacebookClient("token", fakeWebRequestor, new DefaultJsonMapper(), Version.VERSION_9_0);
    return facebookClient.fetchConnection("/page1", FacebookType.class);
  }

  private Connection<FacebookType> create3PageConnectionWithCursorOnly() {
    FakeWebRequestor fakeWebRequestor = new FakeWebRequestor() {
      @Override
      public Response executeGet(Request request) {

        String url = request.getFullUrl();

        if (url.equals("https://graph.facebook.com/v2.12/page1?access_token=token&format=json")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p1-cursor-only"));
        }

        if (url.equals("https://graph.facebook.com/v2.12/page1?access_token=token&format=json&after=cursor-p1-after")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p2-cursor-only"));
        }

        if (url.equals("https://graph.facebook.com/v2.12/page1?access_token=token&format=json&after=cursor-p2-after")) {
          return new Response(HTTP_OK, jsonFromClasspath("connection-p3-cursor-only"));
        }

        return new Response(HTTP_OK, url);

      }

    };
    DefaultFacebookClient facebookClient =
        new DefaultFacebookClient("token", fakeWebRequestor, new DefaultJsonMapper(), Version.VERSION_9_0);
    return facebookClient.fetchConnection("/page1", FacebookType.class);
  }
}
