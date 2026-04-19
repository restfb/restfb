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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;

import com.restfb.Body;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookGoawayNetworkException;
import com.restfb.exception.FacebookNetworkException;
import org.junit.jupiter.api.Test;

import com.restfb.exception.FacebookRstStreamNetworkException;
import com.restfb.json.JsonObject;

class DefaultFacebookClientTest {

  @Test
  void throwsStreamResetExceptionOnRstStream() {
    TestableFacebookClient client = new TestableFacebookClient();

    DefaultFacebookClient.Requestor failingRequestor = () -> {
      throw new IOException("Received RST_STREAM: Internal error");
    };

    assertThatThrownBy(() -> client.invokeMakeRequest(failingRequestor))
      .isInstanceOf(FacebookRstStreamNetworkException.class).hasMessageContaining("RST_STREAM");
  }

  @Test
  void throwsGoawyExceptionOnRstStream() {
    TestableFacebookClient client = new TestableFacebookClient();

    DefaultFacebookClient.Requestor failingRequestor = () -> {
      throw new IOException("Received GOAWAY from upstream");
    };

    assertThatThrownBy(() -> client.invokeMakeRequest(failingRequestor))
      .isInstanceOf(FacebookGoawayNetworkException.class).hasMessageContaining("GOAWAY");
  }

  @Test
  void makeRequestEnrichesFacebookExceptionWithInfoData() {
    InfoTrackingFacebookClient client = new InfoTrackingFacebookClient(new ThrowingWebRequestor());
    client.setHeaderAuthorization(true);

    assertThatThrownBy(client::invokeFailingMakeRequest).isInstanceOf(FacebookNetworkException.class)
      .satisfies(throwable -> {
        FacebookException exception = (FacebookException) throwable;
        assertThat(exception.getInfoData()).isPresent();
        FacebookException.InfoData info = exception.getInfoData().orElseThrow();
        assertThat(info.getHttpMethod()).isEqualTo("GET");
        assertThat(info.getFullEndpoint()).contains("/me");
        assertThat(info.getParameterString()).contains("fields=id").contains("format=json");
        assertThat(info.getHeaderAccessToken()).isEqualTo("token");
        assertThat(info.getDuration()).isNotNull();
      });
  }

  @Test
  void makeRequestExceptionContainsSingleParameterSet() {
    InfoTrackingFacebookClient client = new InfoTrackingFacebookClient(new ThrowingWebRequestor());
    client.setHeaderAuthorization(true);

    assertThatThrownBy(client::invokeFailingMakeRequest).isInstanceOf(FacebookNetworkException.class)
      .satisfies(throwable -> {
        FacebookException exception = (FacebookException) throwable;
        FacebookException.InfoData info = exception.getInfoData().orElseThrow();
        assertThat(countOccurrences(info.getUrl(), "?")).isEqualTo(1);
        assertThat(countOccurrences(info.getUrl(), "fields=id")).isEqualTo(1);
        assertThat(countOccurrences(info.getUrl(), "format=json")).isEqualTo(1);
      });
  }

  @Test
  void fetchConnectionPageEnrichesFacebookExceptionWithInfoData() {
    InfoTrackingFacebookClient client = new InfoTrackingFacebookClient(new ThrowingWebRequestor());
    client.setHeaderAuthorization(true);

    String connectionUrl = "https://graph.facebook.com/me/feed?after=cursor";

    assertThatThrownBy(() -> client.fetchConnectionPage(connectionUrl, JsonObject.class))
      .isInstanceOf(FacebookNetworkException.class).satisfies(throwable -> {
        FacebookException exception = (FacebookException) throwable;
        assertThat(exception.getInfoData()).isPresent();
        FacebookException.InfoData info = exception.getInfoData().orElseThrow();
        assertThat(info.getHttpMethod()).isEqualTo("GET");
        assertThat(info.getFullEndpoint()).isEqualTo(connectionUrl);
        assertThat(info.getParameterString()).isNull();
        assertThat(info.getHeaderAccessToken()).isEqualTo("token");
        assertThat(info.getDuration()).isNotNull();
      });
  }

  private static class TestableFacebookClient extends DefaultFacebookClient {
    TestableFacebookClient() {
      super("token", new DefaultWebRequestor(), new DefaultJsonMapper(), Version.LATEST);
    }

    void invokeMakeRequest(Requestor requestor) {
      executeRequestWithMetadata(null, null, requestor);
    }
  }

  private static class InfoTrackingFacebookClient extends DefaultFacebookClient {
    InfoTrackingFacebookClient(WebRequestor webRequestor) {
      super("token", webRequestor, new DefaultJsonMapper(), Version.LATEST);
    }

    void invokeFailingMakeRequest() {
      makeRequestWithMetadata("me", false, false, null, (Body) null, Parameter.with("fields", "id"));
    }
  }

  private static class ThrowingWebRequestor implements WebRequestor {

    @Override
    public Response executeGet(Request request) throws IOException {
      throw new IOException("network broken");
    }

    @Override
    public Response executePost(Request request) throws IOException {
      throw new IOException("network broken");
    }

    @Override
    public Response executeDelete(Request request) throws IOException {
      throw new IOException("network broken");
    }
  }

  private static int countOccurrences(String value, String substring) {
    int count = 0;
    int index = 0;
    while (index != -1) {
      index = value.indexOf(substring, index);
      if (index != -1) {
        count++;
        index += substring.length();
      }
    }
    return count;
  }
}
