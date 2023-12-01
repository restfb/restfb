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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restfb.exception.FacebookSignedRequestParsingException;
import com.restfb.exception.FacebookSignedRequestVerificationException;

@ExtendWith(MockitoExtension.class)
class SignedRequestTest {

  @Spy
  DefaultFacebookClient facebookClient;

  @Test
  void checkSignedRequest_signedRequest() {
    String signature = "uLQw26KrDdDhZA+Cbvhjm1Y5SB1LiUej9tAXWWXhb9s=";
    String payload =
        "ewogICAib2F1dGhfdG9rZW4iOiAie3VzZXItYWNjZXNzLXRva2VufSIsCiAgICJhbGdvcml0aG0iOiAiSE1BQy1TSEEyNTYiLAogICAiZXhwaXJlcyI6IDEyOTE4NDA0MDAsCiAgICJpc3N1ZWRfYXQiOiAxMjkxODM2ODAwLAogICAidXNlcl9pZCI6ICIyMTg0NzEiCn0=";

    Payload payloadObject = facebookClient.parseSignedRequest(signature + "." + payload, "secret", Payload.class);
    assertEquals("218471", payloadObject.userId);
    assertEquals("{user-access-token}", payloadObject.oauthToken);
    assertEquals(1_291_840_400, payloadObject.expires.longValue());
    assertEquals(1_291_836_800, payloadObject.issuedAt.longValue());
  }

  @DisplayName("Check signed request exception handling")
  @ParameterizedTest(name = "{index} ==> check for signedRequest and secret ''{1}''")
  @MethodSource("requestProvider")
  void checkSignedRequest(String signedRequest, String appSecret, String message, Class<? extends Throwable> clazz) {
    try {
      facebookClient.parseSignedRequest(signedRequest, appSecret, Payload.class);
      failBecauseExceptionWasNotThrown(clazz);
    } catch (Throwable t) {
      if (clazz.isInstance(t.getClass()))
        assertThat(t).hasMessageContaining(message);
    }
  }

  private static Stream<Arguments> requestProvider() {
    String signature = "uLQw26KrDdDhZA+Cbvhjm1Y5SB1LiUej9tAXWWXhb9s=";
    String payload =
        "ewogICAib2F1dGhfdG9rZW4iOiAie3VzZXItYWNjZXNzLXRva2VufSIsCiAgICJhbGdvcml0aG0iOiAiSE1BQy1TSEEyNTYiLAogICAiZXhwaXJlcyI6IDEyOTE4NDA0MDAsCiAgICJpc3N1ZWRfYXQiOiAxMjkxODM2ODAwLAogICAidXNlcl9pZCI6ICIyMTg0NzEiCn0=";

    return Stream.of(
      Arguments.of(signature + "." + payload, "wrong_secret", "was made for the app identified by the app secret you",
        FacebookSignedRequestVerificationException.class),
      Arguments.of("wrongSignedRequest", "secret", "signature and payload strings separated by a '.'",
        FacebookSignedRequestParsingException.class),
      Arguments.of("", "", "cannot be an empty string", IllegalArgumentException.class),
      Arguments.of(null, null, "parameter cannot be null", NullPointerException.class));
  }

  public static class Payload {
    @Facebook("user_id")
    String userId;

    @Facebook("oauth_token")
    String oauthToken;

    @Facebook
    Long expires;

    @Facebook("issued_at")
    Long issuedAt;

    Date getExpires() {
      return expires == null ? null : new Date(expires * 1000L);
    }

    Date getIssuedAt() {
      return issuedAt == null ? null : new Date(issuedAt * 1000L);
    }

    // Add whatever other fields you might have
  }

}
