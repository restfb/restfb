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
import static com.restfb.util.UrlUtils.redactSensitiveCredential;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.restfb.json.JsonObject;
import com.restfb.logging.JulLogger;
import com.restfb.types.FacebookReelAttachment;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class DefaultWebRequestorTest {

  private final DefaultWebRequestor requestor = new DefaultWebRequestor();

  @Test
  void requestToStringRedactsCredentialsWithoutChangingRequest() {
    String url = "https://graph.facebook.com/me?access_token=url-credential";
    String parameters = "fields=id&client_secret=secret-credential&batch=payload-credential";
    WebRequestor.Request request = new WebRequestor.Request(url, "header-credential", parameters);

    assertThat(request.toString()).contains("access_token=" + redactSensitiveCredential("url-credential"),
      "client_secret=" + redactSensitiveCredential("secret-credential"), "batch=[REDACTED]", "fields=id",
      "Header access token: " + redactSensitiveCredential("header-credential")).doesNotContain("credential");
    assertThat(request.getUrl()).isEqualTo(url);
    assertThat(request.getParameters()).isEqualTo(parameters);
    assertThat(request.getHeaderAccessToken()).isEqualTo("header-credential");
  }

  @ParameterizedTest
  @EnumSource(Security.class)
  void authenticationParametersAreSentUnchanged(Security security) throws IOException {
    RequestCapture capture = new RequestCapture();
    try (TestServer server = TestServer.start("/credentials", exchange -> {
      capture.capture(exchange);
      respond(exchange, 200, "ok");
    })) {
      String parameters = "access_token=query-credential&appsecret_proof=proof-credential";
      WebRequestor.Request request =
          new WebRequestor.Request(server.url("/credentials"), "header-credential", parameters, null, security);
      requestor.executeGet(request);
      assertThat(capture.getUri().getRawQuery()).isEqualTo(parameters);
      assertThat(capture.getHeaders().getFirst("Authorization")).isEqualTo("Bearer header-credential");

      requestor.executePost(request);
      assertThat(capture.bodyAsString()).isEqualTo(parameters);
      assertThat(capture.getHeaders().getFirst("Authorization")).isEqualTo("Bearer header-credential");
    }
  }

  @Test
  void legacyRequestDiagnosticsMatchThePreviousFormats() throws IOException {
    try (TestServer server = TestServer.start("/credentials", exchange -> respond(exchange, 200, "ok"));
        RequestLogs logs = new RequestLogs()) {
      DefaultWebRequestor sharedRequestor = logs.requestor();
      String endpoint = server.url("/credentials");
      String url = endpoint + "?access_token=url-credential";
      String parameters = "client_secret=form%2Bsecret&fields=id%2Cname&appsecret_proof=proof-credential";
      WebRequestor.Request low = new WebRequestor.Request(url, "header-credential", parameters, null, Security.LOW);
      WebRequestor.Request medium =
          new WebRequestor.Request(url, "header-credential", parameters, null, Security.MEDIUM);
      WebRequestor.Request high = new WebRequestor.Request(url, "header-credential", parameters);

      assertThat(low.toString())
        .isEqualTo("Request to url " + url + " with parameters " + parameters + ". Header access token: true");
      sharedRequestor.executeGet(low);
      sharedRequestor.executeGet(high);
      sharedRequestor.executeGet(medium);
      sharedRequestor.executeDelete(low);
      sharedRequestor.executePost(low);
      sharedRequestor.executePost(high);
      sharedRequestor.executePost(medium);

      String redactedUrl = endpoint + "?access_token=" + redactSensitiveCredential("url-credential");
      String redactedParameters =
          "client_secret=" + redactSensitiveCredential("form+secret") + "&fields=id%2Cname&appsecret_proof=[REDACTED]";
      String redactedHeader = "Header access token: " + redactSensitiveCredential("header-credential");
      assertThat(medium.toString())
        .isEqualTo("Request to url " + url + " with parameters " + redactedParameters + ". " + redactedHeader);
      assertThat(logs.messages).containsExactly("Making a GET request to " + url + " with parameters " + parameters,
        "Making a GET request to " + redactedUrl + " with parameters " + redactedParameters + ". " + redactedHeader,
        "Making a GET request to " + url + " with parameters " + redactedParameters + ". " + redactedHeader,
        "Making a DELETE request to " + url + " with parameters " + parameters,
        "Executing a POST to " + url
            + " with parameters (sent in request body): client_secret=form+secret&fields=id,name&appsecret_proof=proof-credential",
        "Executing a POST to " + redactedUrl + " with parameters (sent in request body): " + redactedParameters + " "
            + redactedHeader,
        "Executing a POST to " + url + " with parameters (sent in request body): " + redactedParameters + " "
            + redactedHeader);
    }
  }

  @Test
  void legacyPostLogsDecodedValuesLiterallyOnOneLineWithoutChangingTheBody() throws IOException {
    RequestCapture capture = new RequestCapture();
    try (TestServer server = TestServer.start("/post", exchange -> {
      capture.capture(exchange);
      respond(exchange, 200, "ok");
    }); RequestLogs logs = new RequestLogs()) {
      String url = server.url("/post");
      String parameters = "message=%7B%7D%0D%0Asecond%20line%0A100%25";
      WebRequestor.Request request = new WebRequestor.Request(url, null, parameters, null, Security.LOW);

      assertThat(logs.requestor().executePost(request).getStatusCode()).isEqualTo(200);
      assertThat(capture.bodyAsString()).isEqualTo(parameters);
      assertThat(logs.messages).containsExactly(
        "Executing a POST to " + url + " with parameters (sent in request body): message={}\\r\\nsecond line\\n100%");
    }
  }

  @Test
  void legacyGetAndPagingDiagnosticsCanBeReplayed() throws IOException, InterruptedException {
    List<URI> received = new CopyOnWriteArrayList<>();
    try (TestServer server = TestServer.start("/feed", exchange -> {
      received.add(exchange.getRequestURI());
      respond(exchange, 200, "{\"data\":[]}");
    }); RequestLogs logs = new RequestLogs()) {
      String feedUrl = server.url("/feed");
      DefaultWebRequestor sharedRequestor = logs.requestor();
      DefaultFacebookClient low = new DefaultFacebookClient("test|token", null, sharedRequestor,
        new DefaultJsonMapper(), Version.UNVERSIONED, Security.LOW);
      low.setFacebookEndpointUrls(new FacebookEndpoints() {
        @Override
        public String getGraphEndpoint() {
          return server.baseUrl;
        }
      });

      ApiResult<JsonObject> result = low.fetchObjectWithResult("feed", JsonObject.class, Parameter.withFields("id"));
      String pageUrl = feedUrl + "?after=cursor%2B1&access_token=paging%7Ctoken";
      Connection<JsonObject> page = low.fetchConnectionPage(pageUrl, JsonObject.class);

      assertThat(logs.messages).hasSize(2);
      assertThat(result.getRequestUrl()).isEqualTo(replayUrl(logs.messages.get(0)))
        .contains("access_token=test%7Ctoken");
      assertThat(page.getResponseMetadata().getRequestUrl()).isEqualTo(pageUrl)
        .isEqualTo(replayUrl(logs.messages.get(1)));

      HttpClient replayClient = HttpClient.newHttpClient();
      for (String message : logs.messages) {
        HttpRequest replay = HttpRequest.newBuilder(URI.create(replayUrl(message))).GET().build();
        assertThat(replayClient.send(replay, HttpResponse.BodyHandlers.discarding()).statusCode()).isEqualTo(200);
      }
      assertThat(received).hasSize(4);
      assertThat(received.subList(2, 4)).containsExactlyElementsOf(received.subList(0, 2));
    }
  }

  @Test
  void mediumGetCanBeReplayedAfterRestoringTheProof() throws IOException, InterruptedException {
    List<URI> received = new CopyOnWriteArrayList<>();
    try (TestServer server = TestServer.start("/me", exchange -> {
      received.add(exchange.getRequestURI());
      respond(exchange, 200, "{\"id\":\"123\"}");
    }); RequestLogs logs = new RequestLogs()) {
      String accessToken = "test+token";
      String appSecret = "test-app-secret";
      DefaultFacebookClient medium = new DefaultFacebookClient(accessToken, appSecret, logs.requestor(),
        new DefaultJsonMapper(), Version.UNVERSIONED, Security.MEDIUM);
      medium.setFacebookEndpointUrls(new FacebookEndpoints() {
        @Override
        public String getGraphEndpoint() {
          return server.baseUrl;
        }
      });

      String proof = medium.obtainAppSecretProof(accessToken, appSecret);
      String url = medium.fetchObjectWithResult("me", JsonObject.class).getRequestUrl();
      assertThat(url).contains("access_token=test%2Btoken", "appsecret_proof=[REDACTED]").doesNotContain(proof);
      assertThat(logs.messages).hasSize(1);
      String loggedRequest = logs.messages.get(0);
      assertThat(loggedRequest).endsWith(". Header access token: null");
      String loggedUrl = replayUrl(loggedRequest.substring(0, loggedRequest.lastIndexOf(". Header access token: ")));
      assertThat(loggedUrl).isEqualTo(url);

      URI replayUri = URI.create(loggedUrl.replace("appsecret_proof=[REDACTED]", "appsecret_proof=" + proof));
      HttpRequest replay = HttpRequest.newBuilder(replayUri).GET().build();
      assertThat(HttpClient.newHttpClient().send(replay, HttpResponse.BodyHandlers.discarding()).statusCode())
        .isEqualTo(200);
      assertThat(received).hasSize(2);
      assertThat(received.get(0).getRawQuery()).contains("appsecret_proof=" + proof);
      assertThat(received.get(1)).isEqualTo(received.get(0));
    }
  }

  private static String replayUrl(String logMessage) {
    String prefix = "Making a GET request to ";
    assertThat(logMessage).startsWith(prefix);
    String[] parts = logMessage.substring(prefix.length()).split(" with parameters ", 2);
    assertThat(parts).hasSize(2);
    return "null".equals(parts[1]) ? parts[0] : parts[0] + "?" + parts[1];
  }

  @Test
  void checkGet_withAccessToken() throws IOException {
    RequestCapture capture = new RequestCapture();
    try (TestServer server = TestServer.start("/auth", exchange -> {
      capture.capture(exchange);
      respond(exchange, 200, "pong");
    })) {
      WebRequestor.Response response =
          requestor.executeGet(new WebRequestor.Request(server.url("/auth"), "accesstoken"));

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
      WebRequestor.Request request =
          new WebRequestor.Request(server.url("/upload"), null, "", Collections.singletonList(attachment));
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
      FacebookReelAttachment reel =
          FacebookReelAttachment.withByteContent("reel-data".getBytes(StandardCharsets.UTF_8));
      WebRequestor.Request request =
          new WebRequestor.Request(server.url("/reel"), "token", "", Collections.singletonList(reel));
      WebRequestor.Response response = requestor.executePost(request);

      assertThat(response.getStatusCode()).isEqualTo(200);
      assertThat(capture.getHeaders().getFirst("Authorization")).isEqualTo("OAuth token");
      assertThat(capture.getHeaders().getFirst("offset")).isEqualTo("0");
      assertThat(capture.getHeaders().getFirst("file_size"))
        .isEqualTo(String.valueOf("reel-data".getBytes(StandardCharsets.UTF_8).length));
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
      WebRequestor.Request request =
          new WebRequestor.Request(server.url("/reel-url"), "token", "", Collections.singletonList(reel));
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

  private static final class RequestLogs extends Handler implements Closeable {

    private final Logger logger = Logger.getLogger(DefaultWebRequestorTest.class.getName() + "." + UUID.randomUUID());
    private final List<String> messages = new ArrayList<>();

    RequestLogs() {
      logger.setUseParentHandlers(false);
      logger.setLevel(Level.FINE);
      logger.addHandler(this);
    }

    DefaultWebRequestor requestor() {
      return new DefaultWebRequestor(HttpClient.newHttpClient(), new JulLogger(logger.getName()));
    }

    @Override
    public void publish(LogRecord record) {
      String message = record.getMessage();
      if (message.startsWith("Making a ") || message.startsWith("Executing a POST to ")) {
        messages.add(message);
      }
    }

    @Override
    public void flush() {
      // No buffering.
    }

    @Override
    public void close() {
      logger.removeHandler(this);
    }
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
