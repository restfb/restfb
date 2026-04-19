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
import static com.restfb.testutils.RestfbAssertions.assertThatThrownBy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.HttpServer;

class DefaultWebRequestorHttpClientIntegrationTest {

  private static HttpServer server;
  private static String baseUrl;
  private static final AtomicReference<String> lastPostBody = new AtomicReference<>("");

  @BeforeAll
  static void startServer() throws IOException {
    server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
    server.createContext("/ok", exchange -> {
      byte[] body = "pong".getBytes(StandardCharsets.UTF_8);
      exchange.getResponseHeaders().add("facebook-api-version", "v19.0");
      exchange.sendResponseHeaders(200, body.length);
      exchange.getResponseBody().write(body);
      exchange.close();
    });
    server.createContext("/mirror", exchange -> {
      byte[] requestBody = exchange.getRequestBody().readAllBytes();
      lastPostBody.set(new String(requestBody, StandardCharsets.UTF_8));
      exchange.sendResponseHeaders(200, requestBody.length);
      exchange.getResponseBody().write(requestBody);
      exchange.close();
    });
    server.start();
    baseUrl = "http://localhost:" + server.getAddress().getPort();
  }

  @AfterAll
  static void stopServer() {
    if (server != null) {
      server.stop(0);
    }
  }

  @Test
  void executeGetUsesHttpClient() throws IOException {
    DefaultWebRequestor requestor = new DefaultWebRequestor();
    WebRequestor.Response response = requestor.executeGet(new WebRequestor.Request(baseUrl + "/ok", null));

    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo("pong");
    assertThat(response.getHeaders().get("facebook-api-version")).contains("v19.0");
  }

  @Test
  void executePostStreamsBody() throws IOException {
    DefaultWebRequestor requestor = new DefaultWebRequestor();
    WebRequestor.Request request = new WebRequestor.Request(baseUrl + "/mirror", null, "echo=1");
    request.setBody(Body.withData(Collections.singletonMap("message", "hi")));

    WebRequestor.Response response = requestor.executePost(request);

    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getBody()).contains("message");
    assertThat(lastPostBody.get()).contains("message");
  }

  @Test
  void executeGetFailsOnIncompleteBody() throws Exception {
    IncompleteResponseServer incompleteServer = new IncompleteResponseServer();
    incompleteServer.start();

    try {
      DefaultWebRequestor requestor = new DefaultWebRequestor();
      WebRequestor.Request request = new WebRequestor.Request(incompleteServer.url(), null);

      assertThatThrownBy(() -> requestor.executeGet(request)).isInstanceOf(IOException.class)
        .hasMessageContaining("Incomplete response body");
    } finally {
      incompleteServer.stop();
    }
  }

  private static final class IncompleteResponseServer {
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private IncompleteResponseServer() throws IOException {
      serverSocket = new ServerSocket(0);
    }

    private void start() {
      executor.submit(this::handleConnection);
    }

    private String url() {
      return "http://localhost:" + serverSocket.getLocalPort() + "/incomplete";
    }

    private void stop() throws Exception {
      serverSocket.close();
      executor.shutdownNow();
      executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    private void handleConnection() {
      try (Socket socket = serverSocket.accept()) {
        consumeRequest(socket.getInputStream());

        byte[] fullBody = "{\"message\":\"hi\"}".getBytes(StandardCharsets.UTF_8);
        byte[] truncatedBody = Arrays.copyOf(fullBody, fullBody.length - 2);

        OutputStream outputStream = socket.getOutputStream();
        writeString(outputStream, "HTTP/1.1 200 OK\r\n");
        writeString(outputStream, "Content-Type: application/json\r\n");
        writeString(outputStream, "Content-Length: " + fullBody.length + "\r\n");
        writeString(outputStream, "Connection: close\r\n\r\n");
        outputStream.write(truncatedBody);
        outputStream.flush();
      } catch (IOException ignored) {
        // ignore errors from shutting down the socket early
      }
    }

    private void consumeRequest(InputStream inputStream) throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.US_ASCII));
      String line;
      while ((line = reader.readLine()) != null && !line.isEmpty()) {
        // consume request headers
      }
    }

    private void writeString(OutputStream outputStream, String value) throws IOException {
      outputStream.write(value.getBytes(StandardCharsets.US_ASCII));
    }
  }
}
