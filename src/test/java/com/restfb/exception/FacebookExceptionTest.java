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
package com.restfb.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.restfb.exception.FacebookException.InfoData;

class FacebookExceptionTest {

  @Test
  void getMessageAppendsInfoData() {
    TestFacebookException exception = new TestFacebookException("boom");
    exception.withInfoData("GET", "https://graph.facebook.com/me", "fields=id", "token", 1000L);

    assertThat(exception.getMessage()).contains("boom").contains("GET").contains("headerAccessToken");
    assertThat(exception.getMessage()).contains("headerAccessToken: [REDACTED]").doesNotContain("token");
    assertThat(exception.getBasicMessage()).isEqualTo("boom");
  }

  @Test
  void getMessageWithoutInfoDataMatchesBasicMessage() {
    TestFacebookException exception = new TestFacebookException("just-basic");

    assertThat(exception.getMessage()).isEqualTo("just-basic");
    assertThat(exception.getBasicMessage()).isEqualTo("just-basic");
  }

  @ParameterizedTest
  @ValueSource(strings = { "access_token", "appsecret_proof", "client_secret", "input_token", "fb_exchange_token",
      "code", "sessions", "batch" })
  void credentialsAreAbsentFromAllMetadataOutputsAndSerialization(String name) throws Exception {
    TestFacebookException exception = new TestFacebookException("boom");
    exception.withInfoData("POST", "https://graph.facebook.com/oauth/access_token?" + name + "=endpoint-credential",
      "fields=id&" + name + "=parameter-credential", "header-credential", null);
    InfoData info = exception.getInfoData().orElseThrow();

    assertThat(info.getFullEndpoint()).contains(name + "=[REDACTED]");
    assertThat(info.getParameterString()).isEqualTo("fields=id&" + name + "=[REDACTED]");
    assertThat(info.getHeaderAccessToken()).isEqualTo("[REDACTED]");
    assertThat(info.getUrl()).contains("&fields=id&").doesNotContain("credential");
    assertThat(info.toString()).doesNotContain("credential");
    assertThat(exception.getMessage()).contains("boom").doesNotContain("credential");
    assertThat(exception.toString()).doesNotContain("credential");

    StringWriter stackTrace = new StringWriter();
    exception.printStackTrace(new PrintWriter(stackTrace));
    assertThat(stackTrace.toString()).doesNotContain("endpoint-credential", "parameter-credential",
      "header-credential");

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (ObjectOutputStream output = new ObjectOutputStream(bytes)) {
      output.writeObject(exception);
    }
    assertThat(bytes.toString(StandardCharsets.ISO_8859_1)).doesNotContain("endpoint-credential",
      "parameter-credential", "header-credential");
    try (ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
      FacebookException restored = (FacebookException) input.readObject();
      assertThat(restored.getMessage()).isEqualTo(exception.getMessage());
      assertThat(restored.getInfoData().orElseThrow().getHeaderAccessToken()).isEqualTo("[REDACTED]");
    }
  }

  @Test
  void diagnosticUrlRemainsReadableWithoutDuplicateQuerySeparators() {
    InfoData info = new InfoData("GET", "https://graph.facebook.com/me?after=cursor&access_token=app%7Csecret",
      "fields=id%2Cname&client_secret=private-value", null, null);

    assertThat(info.getUrl()).isEqualTo("https://graph.facebook.com/me?after=cursor&access_token=[REDACTED]"
        + "&fields=id,name&client_secret=[REDACTED]");
    assertThat(info.getHeaderAccessToken()).isNull();
    assertThat(info.getDuration()).isEqualTo(Duration.ZERO);
    assertThat(info.getDurationAsString()).isEqualTo("unknown");
  }

  @ParameterizedTest
  @ValueSource(strings = { "https://graph.facebook.com/me", "https://graph.facebook.com/me?",
      "https://graph.facebook.com/me?fields=id&" })
  void urlJoinsParametersWithoutExtraSeparators(String endpoint) {
    InfoData info = new InfoData("GET", endpoint, "access_token=private-value", null, null);

    assertThat(info.getUrl()).endsWith("access_token=[REDACTED]").doesNotContain("??", "?&", "&&", "private-value");
    assertThat(info.getUrl().chars().filter(c -> c == '?').count()).isEqualTo(1);
  }

  @Test
  void missingParametersAndMalformedEncodingDoNotExposeCredentials() {
    assertThat(new InfoData("GET", "https://graph.facebook.com/me", "", null, null).getUrl())
      .isEqualTo("https://graph.facebook.com/me");
    assertThat(new InfoData("GET", null, null, null, null).getUrl()).isEqualTo("null");
    InfoData info = new InfoData("GET", "https://graph.facebook.com/bad%path?access_token=private-value",
      "access%_token=other-private-value", null, null);
    assertThat(info.getUrl()).contains("[REDACTED]").doesNotContain("private-value");
  }

  @Test
  void additionalParametersPrecedeRedactedFragment() {
    InfoData info = new InfoData("GET", "https://graph.facebook.com/me?fields=id#access_token=private-value",
      "format=json", null, null);
    assertThat(info.getUrl()).isEqualTo("https://graph.facebook.com/me?fields=id&format=json#[REDACTED]");
  }

  private static class TestFacebookException extends FacebookException {
    TestFacebookException(String message) {
      super(message);
    }
  }
}
