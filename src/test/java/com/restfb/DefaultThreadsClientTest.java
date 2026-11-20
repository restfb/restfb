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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.restfb.WebRequestor.Request;
import com.restfb.WebRequestor.Response;
import com.restfb.exception.FacebookGraphException;
import com.restfb.exception.FacebookNetworkException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.exception.ThreadsApiException;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;

class DefaultThreadsClientTest {

  private static final String THREADS_URL = "https://graph.threads.net/v1.0/";
  private static final List<String> IDS = List.of(" 123 ", "456");
  private static final Parameter FIELDS = Parameter.withFields("id,media_type");
  private static final String FIRST_OBJECT = "{\"id\":\"123\",\"media_type\":\"IMAGE\"}";
  private static final String SECOND_OBJECT = "{\"id\":\"456\",\"media_type\":\"IMAGE\"}";
  private static final String BOTH_OBJECTS = "{\"123\":" + FIRST_OBJECT + ",\"456\":" + SECOND_OBJECT + "}";

  @Test
  void fetchObjectsUsesSingleIdsGetWithMetadata() {
    RecordingRequestor requestor =
        new RecordingRequestor(new Response(200, BOTH_OBJECTS, null, Map.of("x-fb-trace-id", List.of("trace"))));
    FacebookClient client = threadsClient(requestor);

    ApiResult<JsonObject> result = client.fetchObjectsWithResult(IDS, JsonObject.class, FIELDS);

    assertThat(result.getResult()).isEqualTo(Json.parse(BOTH_OBJECTS));
    assertThat(result.getHttpMethod()).isEqualTo("GET");
    assertThat(result.getRequestUrl()).startsWith(THREADS_URL).doesNotContain("test-token", "test-secret");
    assertThat(result.getResponseHeaders()).containsEntry("x-fb-trace-id", List.of("trace"));
    assertThat(requestor.gets).hasSize(1);
    assertThat(requestor.gets.get(0).getUrl()).isEqualTo(THREADS_URL);
    assertThat(decodedParameters(requestor.gets.get(0))).contains("ids=[\"123\",\"456\"]", "fields=id,media_type",
      "access_token=test-token", "appsecret_proof=" + client.obtainAppSecretProof("test-token", "test-secret"))
      .doesNotContain("batch=");
  }

  @Test
  void fetchObjectsPropagatesInvalidIdErrorWithoutFallback() {
    // Threads rejects the whole ids request if one object is unavailable (issue #1709).
    String invalidId = "9999999999999999";
    String message = "Unsupported get request. Object with ID '" + invalidId
        + "' does not exist, cannot be loaded due to missing permissions, or does not support this operation";
    String body = new JsonObject().add("error", new JsonObject().add("message", message).add("type", "THApiException")
      .add("code", 100).add("error_subcode", 33).add("fbtrace_id", "test-trace")).toString();
    RecordingRequestor requestor = new RecordingRequestor(new Response(400, body));
    FacebookClient client = threadsClient(requestor);

    ThreadsApiException exception = assertThrows(ThreadsApiException.class,
      () -> client.fetchObjectsWithResult(List.of("123", invalidId), JsonObject.class, FIELDS));

    assertThat(exception.getHttpStatusCode()).isEqualTo(400);
    assertThat(exception.getErrorCode()).isEqualTo(100);
    assertThat(exception.getErrorSubcode()).isEqualTo(33);
    assertThat(exception.getErrorMessage()).isEqualTo(message);
    assertThat(exception.getFbtraceId()).isEqualTo("test-trace");
    assertThat(requestor.gets).extracting(Request::getUrl).containsExactly(THREADS_URL);
    assertThat(decodedParameters(requestor.gets.get(0))).contains("ids=[\"123\",\"" + invalidId + "\"]",
      "fields=id,media_type");
  }

  @ParameterizedTest
  @ValueSource(strings = { "Unknown parameter: ids", "(#100) The 'ids' parameter is no longer supported." })
  void fetchObjectsFallsBackToIndividualGetsWhenIdsIsRejected(String message) {
    RecordingRequestor requestor = new RecordingRequestor(error(400, 100, "THApiException", message),
      new Response(200, FIRST_OBJECT), new Response(200, SECOND_OBJECT));
    FacebookClient client = threadsClient(requestor);

    ApiResult<JsonObject> result = client.fetchObjectsWithResult(IDS, JsonObject.class, FIELDS);

    assertThat(result.getResult()).isEqualTo(Json.parse(BOTH_OBJECTS));
    assertThat(result.getResponseMetadata()).isNull();
    assertThat(requestor.gets).extracting(Request::getUrl).containsExactly(THREADS_URL, THREADS_URL + "123",
      THREADS_URL + "456");
    assertThat(requestor.gets.subList(1, 3)).allSatisfy(request -> assertThat(decodedParameters(request))
      .contains("fields=id,media_type", "access_token=test-token",
        "appsecret_proof=" + client.obtainAppSecretProof("test-token", "test-secret"))
      .doesNotContain("ids=", "batch="));
  }

  @ParameterizedTest
  @ValueSource(strings = { "{}", "{\"123\":{\"id\":\"123\"}}" })
  void fetchObjectsDoesNotRetryEmptyOrPartialIdsResults(String body) {
    RecordingRequestor requestor = new RecordingRequestor(new Response(200, body));

    JsonObject result = threadsClient(requestor).fetchObjects(IDS, JsonObject.class);

    assertThat(result).isEqualTo(Json.parse(body));
    assertThat(requestor.gets).hasSize(1);
  }

  @ParameterizedTest
  @CsvSource({ "190, Invalid access token", "4, Application request limit reached", "10, Permission denied",
      "100, Invalid parameter" })
  void fetchObjectsDoesNotRetryOtherGraphErrors(int code, String message) {
    RecordingRequestor requestor = new RecordingRequestor(error(400, code, "OAuthException", message));

    FacebookGraphException exception =
        assertThrows(FacebookGraphException.class, () -> threadsClient(requestor).fetchObjects(IDS, JsonObject.class));

    assertThat(exception.getErrorCode()).isEqualTo(code);
    assertThat(requestor.gets).hasSize(1);
  }

  @Test
  void fetchObjectsDoesNotRetryEmptyHttp400() {
    RecordingRequestor requestor = new RecordingRequestor(new Response(400, ""));

    FacebookNetworkException exception = assertThrows(FacebookNetworkException.class,
      () -> threadsClient(requestor).fetchObjects(IDS, JsonObject.class));

    assertThat(exception.getHttpStatusCode()).isEqualTo(400);
    assertThat(requestor.gets).hasSize(1);
  }

  @Test
  void fetchObjectsStopsAtFirstErrorFromIndividualGets() {
    RecordingRequestor requestor =
        new RecordingRequestor(error(400, 100, "THApiException", "Unsupported parameter: ids"),
          new Response(200, FIRST_OBJECT), error(400, 190, "OAuthException", "Invalid access token"));

    FacebookOAuthException exception = assertThrows(FacebookOAuthException.class,
      () -> threadsClient(requestor).fetchObjects(List.of("123", "456", "789"), JsonObject.class));

    assertThat(exception.getErrorCode()).isEqualTo(190);
    assertThat(requestor.gets).extracting(Request::getUrl).containsExactly(THREADS_URL, THREADS_URL + "123",
      THREADS_URL + "456");
  }

  @Test
  void fetchObjectsValidatesInputBeforeFirstRequest() {
    RecordingRequestor requestor = new RecordingRequestor();
    FacebookClient client = threadsClient(requestor);

    assertThrows(IllegalArgumentException.class, () -> client.fetchObjects(List.of("123", " "), JsonObject.class));
    assertThrows(IllegalArgumentException.class,
      () -> client.fetchObjects(IDS, JsonObject.class, Parameter.with("ids", "another-id")));

    assertThat(requestor.gets).isEmpty();
  }

  private static FacebookClient threadsClient(RecordingRequestor requestor) {
    return new DefaultThreadsClient("test-token", "test-secret", requestor, new DefaultJsonMapper(),
      Version.THREADS_LATEST);
  }

  private static String decodedParameters(Request request) {
    return URLDecoder.decode(request.getParameters(), StandardCharsets.UTF_8);
  }

  private static Response error(int status, int code, String type, String message) {
    return new Response(status, new JsonObject()
      .add("error", new JsonObject().add("code", code).add("type", type).add("message", message)).toString());
  }

  private static class RecordingRequestor extends FakeWebRequestor {
    private final List<Request> gets = new ArrayList<>();
    private final Queue<Response> responses;

    RecordingRequestor(Response... responses) {
      this.responses = new ArrayDeque<>(Arrays.asList(responses));
    }

    @Override
    public Response executeGet(Request request) {
      gets.add(request);
      return responses.remove();
    }
  }
}
