/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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

import static com.restfb.testutils.RestfbAssertions.assertThat;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.restfb.json.JsonObject;
import com.restfb.types.FacebookReelAttachment;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class DefaultWebRequestorTest {

  private final DefaultWebRequestor requestor = new DefaultWebRequestor();

  @Test
  void checkGet_withAccessToken() throws IOException {
    RequestCapture capture = new RequestCapture();
    try (TestServer server = TestServer.start("/auth", exchange -> {
      capture.capture(exchange);
      respond(exchange, 200, "pong");
    })) {
      WebRequestor.Response response = requestor.executeGet(new WebRequestor.Request(server.url("/auth"), "accesstoken"));

      assertThat(response.getStatusCode()).isEqualTo(200);
      assertThat(capture.getHeaders().getFirst("Authorization")).isEqualTo("Bearer accesstoken");
      assertThat(capture.getMethod()).isEqualTo("GET");
    }
  }

  @Test
  void checkGet_withoutAccessToken() throws IOException {
    RequestCapture capture = new RequestCapture();
    try (TestServer server = TestServer.start("/get", exchange -> {
      capture.capture(exchange);
      respond(exchange, 200, "pong");
    })) {
      requestor.executeGet(new WebRequestor.Request(server.url("/get"), null));

      assertThat(capture.getHeaders().getFirst("Authorization")).isNull();
      assertThat(capture.getMethod()).isEqualTo("GET");
    }
  }

  @Test
  void checkPost_NoBinary() throws IOException {
    RequestCapture capture = new RequestCapture();
    try (TestServer server = TestServer.start("/post", exchange -> {
      capture.capture(exchange);
      respond(exchange, 200, "ok");
    })) {
      WebRequestor.Request request = new WebRequestor.Request(server.url("/post"), null, "foo=bar");
      WebRequestor.Response response = requestor.executePost(request);

      assertThat(response.getStatusCode()).isEqualTo(200);
      assertThat(capture.getMethod()).isEqualTo("POST");
      assertThat(capture.bodyAsString()).isEqualTo("foo=bar");
      assertThat(capture.getUri().getQuery()).isNull();
    }
  }

  @Test
  void checkPost_WithBody() throws IOException {
    RequestCapture capture = new RequestCapture();
    try (TestServer server = TestServer.start("/body", exchange -> {
      capture.capture(exchange);
      respond(exchange, 200, "ok");
    })) {
      WebRequestor.Request request = new WebRequestor.Request(server.url("/body"), null, "");
      request.setBody(Body.withData(new JsonObject().add("message", "hi")));
      requestor.executePost(request);

      assertThat(capture.getHeaders().getFirst("Content-Type")).isEqualTo("application/json");
      assertThat(capture.bodyAsString()).contains("message");
    }
  }

  @Test
  void checkPost_WithBinaryAttachment() throws IOException {
    RequestCapture capture = new RequestCapture();
    try (TestServer server = TestServer.start("/upload", exchange -> {
      capture.capture(exchange);
      respond(exchange, 200, "ok");
    })) {
      BinaryAttachment attachment = BinaryAttachment.with("photo", "test.txt",
        () -> new CloseTrackingInputStream("hello".getBytes(StandardCharsets.UTF_8)), "text/plain");
      WebRequestor.Request request = new WebRequestor.Request(server.url("/upload"), null, "",
        Collections.singletonList(attachment));
      requestor.executePost(request);

      assertThat(capture.getHeaders().getFirst("Content-Type")).contains("multipart/form-data");
      assertThat(capture.bodyAsString()).contains("Content-Disposition: form-data; name=\"photo\"");
      assertThat(capture.bodyAsString()).contains("hello");
    }
  }

  @Test
  void checkPost_WithReel_Binary() throws IOException {
    RequestCapture capture = new RequestCapture();
    try (TestServer server = TestServer.start("/reel", exchange -> {
      capture.capture(exchange);
      respond(exchange, 200, "ok");
    })) {
      FacebookReelAttachment reel = FacebookReelAttachment.withByteContent("reel-data".getBytes(StandardCharsets.UTF_8));
      WebRequestor.Request request = new WebRequestor.Request(server.url("/reel"), "token", "",
        Collections.singletonList(reel));
      WebRequestor.Response response = requestor.executePost(request);

      assertThat(response.getStatusCode()).isEqualTo(200);
      assertThat(capture.getHeaders().getFirst("Authorization")).isEqualTo("OAuth token");
      assertThat(capture.getHeaders().getFirst("offset")).isEqualTo("0");
      assertThat(capture.getHeaders().getFirst("file_size")).isEqualTo(String.valueOf("reel-data".getBytes(StandardCharsets.UTF_8).length));
      assertThat(new String(capture.getBody(), StandardCharsets.UTF_8)).isEqualTo("reel-data");
    }
  }

  @Test
  void checkPost_WithReel_Url() throws IOException {
    RequestCapture capture = new RequestCapture();
    try (TestServer server = TestServer.start("/reel-url", exchange -> {
      capture.capture(exchange);
      respond(exchange, 200, "ok");
    })) {
      FacebookReelAttachment reel = FacebookReelAttachment.withUrl("https://example.com/reel.mp4");
      WebRequestor.Request request = new WebRequestor.Request(server.url("/reel-url"), "token", "",
        Collections.singletonList(reel));
      requestor.executePost(request);

      assertThat(capture.getHeaders().getFirst("file_url")).isEqualTo("https://example.com/reel.mp4");
      assertThat(capture.getBody().length).isZero();
    }
  }

  private static void respond(HttpExchange exchange, int statusCode, String body) throws IOException {
    byte[] payload = body.getBytes(StandardCharsets.UTF_8);
    exchange.getResponseHeaders().add("facebook-api-version", "v19.0");
    exchange.sendResponseHeaders(statusCode, payload.length);
    exchange.getResponseBody().write(payload);
    exchange.close();
  }

  private static final class RequestCapture {

    private String method;
    private URI uri;
    private Headers headers = new Headers();
    private byte[] body = new byte[0];

    void capture(HttpExchange exchange) throws IOException {
      method = exchange.getRequestMethod();
      uri = exchange.getRequestURI();
      headers = exchange.getRequestHeaders();
      body = exchange.getRequestBody().readAllBytes();
    }

    String bodyAsString() {
      return new String(body, StandardCharsets.UTF_8);
    }

    String getMethod() {
      return method;
    }

    URI getUri() {
      return uri;
    }

    Headers getHeaders() {
      return headers;
    }

    byte[] getBody() {
      return body;
    }
  }

  private static final class TestServer implements Closeable {

    private final HttpServer server;
    private final String baseUrl;

    private TestServer(HttpServer server) {
      this.server = server;
      this.baseUrl = "http://localhost:" + server.getAddress().getPort();
    }

    static TestServer start(String path, HttpHandler handler) throws IOException {
      HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
      server.createContext(path, handler);
      server.start();
      return new TestServer(server);
    }

    String url(String path) {
      if (!path.startsWith("/")) {
        return baseUrl + "/" + path;
      }
      return baseUrl + path;
    }

    @Override
    public void close() {
      server.stop(0);
    }
  }

  private static final class CloseTrackingInputStream extends InputStream {

    private final byte[] data;
    private int index;

    CloseTrackingInputStream(byte[] data) {
      this.data = data;
    }

    @Override
    public int read() {
      if (index >= data.length) {
        return -1;
      }
      return data[index++];
    }
  }
}
