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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookGoawayNetworkException;
import com.restfb.exception.FacebookNetworkException;
import com.restfb.exception.FacebookOAuthException;
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
        assertThat(info.getHeaderAccessToken()).isEqualTo("[REDACTED]");
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
        assertThat(info.getHeaderAccessToken()).isEqualTo("[REDACTED]");
        assertThat(info.getDuration()).isNotNull();
      });
  }

  @ParameterizedTest
  @ValueSource(strings = { "GET", "POST", "DELETE" })
  void requestCredentialsAreUnchangedButExceptionMetadataIsRedacted(String method) {
    CapturingWebRequestor requestor = new CapturingWebRequestor();
    DefaultFacebookClient client = credentialClient(requestor);
    client.setHttpDeleteFallback(false);
    String proof = client.obtainAppSecretProof("request-token", "request-secret");

    assertThatThrownBy(() -> client.makeRequestWithMetadata("me", "POST".equals(method), "DELETE".equals(method), null,
      Parameter.with("fields", "id"))).isInstanceOf(FacebookNetworkException.class).satisfies(throwable -> {
        FacebookException exception = (FacebookException) throwable;
        assertThat(exception.getMessage())
          .contains(method, "fields=id", "access_token=[REDACTED]", "appsecret_proof=[REDACTED]")
          .doesNotContain("request-token", "request-secret", proof);
        assertThat(exception.getInfoData().orElseThrow().getParameterString()).doesNotContain("request-token",
          "request-secret", proof);
      });

    assertThat(requestor.method).isEqualTo(method);
    assertThat(requestor.request.getParameters()).contains("access_token=request-token", "appsecret_proof=" + proof);
  }

  @Test
  void headerTokenIsOnlyRedactedInDiagnostics() {
    CapturingWebRequestor requestor = new CapturingWebRequestor();
    DefaultFacebookClient client = credentialClient(requestor);
    client.setHeaderAuthorization(true);

    assertThatThrownBy(() -> client.fetchObject("me", JsonObject.class)).isInstanceOf(FacebookNetworkException.class)
      .satisfies(throwable -> {
        FacebookException exception = (FacebookException) throwable;
        assertThat(exception.getInfoData().orElseThrow().getHeaderAccessToken()).isEqualTo("[REDACTED]");
        assertThat(exception.getMessage()).doesNotContain("request-token");
      });
    assertThat(requestor.request.getHeaderAccessToken()).isEqualTo("request-token");
  }

  @Test
  void pagingUrlAndAddedProofAreRedactedWithoutChangingRequest() {
    CapturingWebRequestor requestor = new CapturingWebRequestor();
    DefaultFacebookClient client = credentialClient(requestor);
    String connectionUrl = "https://graph.facebook.com/me/feed?after=cursor&access_token=paging-token";
    String proof = client.obtainAppSecretProof("request-token", "request-secret");

    assertThatThrownBy(() -> client.fetchConnectionPage(connectionUrl, JsonObject.class))
      .isInstanceOf(FacebookNetworkException.class).satisfies(throwable -> {
        FacebookException exception = (FacebookException) throwable;
        assertThat(exception.getInfoData().orElseThrow().getFullEndpoint())
          .contains("after=cursor", "access_token=[REDACTED]", "appsecret_proof=[REDACTED]")
          .doesNotContain("paging-token", proof);
        assertThat(exception.getMessage()).doesNotContain("paging-token", proof);
      });
    assertThat(requestor.request.getUrl()).startsWith(connectionUrl).endsWith("appsecret_proof=" + proof);
  }

  @ParameterizedTest
  @ValueSource(strings = { "app", "extended", "user", "debug", "device" })
  void oauthAndDebugCredentialsAreNotAddedToExceptions(String operation) {
    CapturingWebRequestor requestor = new CapturingWebRequestor();
    DefaultFacebookClient client = credentialClient(requestor);

    assertThatThrownBy(() -> {
      switch (operation) {
      case "app":
        client.obtainAppAccessToken("app-id", "oauth-secret");
        break;
      case "extended":
        client.obtainExtendedAccessToken("app-id", "oauth-secret", "exchange-token");
        break;
      case "user":
        client.obtainUserAccessToken("app-id", "oauth-secret", "https://example.com/callback", "verification-code");
        break;
      case "debug":
        client.debugToken("debug-token");
        break;
      default:
        client.obtainDeviceAccessToken("device-code");
      }
    }).isInstanceOf(FacebookNetworkException.class).satisfies(throwable -> {
      FacebookException exception = (FacebookException) throwable;
      assertThat(exception.getMessage()).doesNotContain("request-token", "oauth-secret", "exchange-token",
        "verification-code", "debug-token", "device-code");
    });
    String parameters = requestor.request.getParameters();
    assertThat(parameters).contains("access_token=request-token");
    switch (operation) {
    case "app":
      assertThat(parameters).contains("client_secret=oauth-secret");
      break;
    case "extended":
      assertThat(parameters).contains("client_secret=oauth-secret", "fb_exchange_token=exchange-token");
      break;
    case "user":
      assertThat(parameters).contains("client_secret=oauth-secret", "code=verification-code");
      break;
    case "debug":
      assertThat(parameters).contains("input_token=debug-token");
      break;
    default:
      assertThat(parameters).contains("code=device-code");
    }
  }

  @Test
  void apiErrorsAlsoCarryOnlyRedactedMetadata() {
    CapturingWebRequestor requestor = new CapturingWebRequestor();
    requestor.response = new WebRequestor.Response(400,
      "{\"error\":{\"message\":\"Invalid token\",\"type\":\"OAuthException\",\"code\":190}}");
    DefaultFacebookClient client = credentialClient(requestor);

    assertThatThrownBy(() -> client.fetchObject("me", JsonObject.class)).isInstanceOf(FacebookOAuthException.class)
      .satisfies(throwable -> assertThat(throwable.getMessage()).contains("Invalid token", "access_token=[REDACTED]")
        .doesNotContain("request-token"));
  }

  @Test
  void successfulResponseMetadataRedactsCredentialsWithoutChangingRequest() {
    CapturingWebRequestor requestor = new CapturingWebRequestor();
    requestor.response = new WebRequestor.Response(200, "{\"id\":\"123\"}");
    DefaultFacebookClient client = credentialClient(requestor);

    ApiResult<JsonObject> result = client.fetchObjectWithResult("me", JsonObject.class, Parameter.with("fields", "id"));

    assertThat(result.getRequestUrl()).contains("fields=id", "access_token=[REDACTED]", "appsecret_proof=[REDACTED]")
      .doesNotContain("request-token", client.obtainAppSecretProof("request-token", "request-secret"));
    assertThat(requestor.request.getParameters()).contains("access_token=request-token");
  }

  private DefaultFacebookClient credentialClient(WebRequestor requestor) {
    return new DefaultFacebookClient("request-token", "request-secret", requestor, new DefaultJsonMapper(),
      Version.LATEST);
  }

  private static class CapturingWebRequestor implements WebRequestor {
    private Request request;
    private String method;
    private Response response;

    private Response execute(String method, Request request) throws IOException {
      this.method = method;
      this.request = request;
      if (response != null) {
        return response;
      }
      throw new IOException("network broken");
    }

    @Override
    public Response executeGet(Request request) throws IOException {
      return execute("GET", request);
    }

    @Override
    public Response executePost(Request request) throws IOException {
      return execute("POST", request);
    }

    @Override
    public Response executeDelete(Request request) throws IOException {
      return execute("DELETE", request);
    }
  }

  @Test
  void videoUploadUsesGraphEndpoint() {
    TestableFacebookClient client = new TestableFacebookClient();

    assertThat(client.createEndpoint("me/videos", true, false)).isEqualTo("https://graph.facebook.com/v18.0/me/videos");
    assertThat(client.createEndpoint("act_123/advideos", true, false))
      .isEqualTo("https://graph.facebook.com/v18.0/act_123/advideos");
  }

  @Test
  void reelUploadStillUsesReelUploadEndpoint() {
    TestableFacebookClient client = new TestableFacebookClient();

    assertThat(client.createEndpoint("me/video_reels", true, true))
      .isEqualTo("https://rupload.facebook.com/video-upload/v18.0/me/video_reels");
  }

  @Test
  @SuppressWarnings("deprecation")
  void graphVideoEndpointMethodIsRetainedForCompatibility() {
    TestableFacebookClient client = new TestableFacebookClient();

    assertThat(client.graphVideoEndpoint()).isEqualTo("https://graph-video.facebook.com/v18.0");
    assertThat(client.getFacebookEndpointUrls().getGraphVideoEndpoint()).isEqualTo("https://graph-video.facebook.com");
  }

  @Test
  void logoutStillUsesFacebookEndpoint() {
    TestableFacebookClient client = new TestableFacebookClient();

    assertThat(client.createEndpoint("logout.php", false, false)).isEqualTo("https://www.facebook.com/logout.php");
  }

  private static class TestableFacebookClient extends DefaultFacebookClient {
    TestableFacebookClient() {
      super("token", new DefaultWebRequestor(), new DefaultJsonMapper(), Version.VERSION_18_0);
    }

    void invokeMakeRequest(Requestor requestor) {
      executeRequestWithMetadata(null, null, requestor);
    }

    String createEndpoint(String apiCall, boolean hasAttachment, boolean hasReel) {
      return createEndpointForApiCall(apiCall, hasAttachment, hasReel);
    }

    @SuppressWarnings("deprecation")
    String graphVideoEndpoint() {
      return getFacebookGraphVideoEndpointUrl();
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
