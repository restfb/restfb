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
package com.restfb.util;

import static com.restfb.util.UrlUtils.extractParametersFromQueryString;
import static com.restfb.util.UrlUtils.extractParametersFromUrl;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.restfb.Security;

/**
 * Unit tests that exercise {@link com.restfb.util.UrlUtils}.
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
class UrlUtilsTest {

  @ParameterizedTest
  @ValueSource(strings = { "appsecret_proof", "code", "signed_request", "sessions", "batch", "%63ode", "CODE" })
  void redactSensitiveParameters(String name) {
    assertThat(UrlUtils.redactSensitiveParameters("fields=id&" + name + "=credential-value&format=json"))
      .isEqualTo("fields=id&" + name + "=[REDACTED]&format=json");
  }

  @ParameterizedTest
  @ValueSource(strings = { "access_token", "client_secret", "app_secret", "input_token", "fb_exchange_token",
      "refresh_token", "ACCESS_TOKEN", "access%5Ftoken", "%63lient_secret", "APP_SECRET" })
  void tokensAndAppSecretsIncludeFingerprint(String name) {
    assertThat(UrlUtils.redactSensitiveParameters("fields=id&" + name + "=abc&format=json"))
      .isEqualTo("fields=id&" + name + "=[REDACTED; sha256=ba7816bf8f01cfea]&format=json");
  }

  @Test
  void credentialFingerprintUsesCompleteUtf8Value() {
    assertThat(UrlUtils.redactSensitiveCredential("abc")).isEqualTo("[REDACTED; sha256=ba7816bf8f01cfea]");
    assertThat(UrlUtils.redactSensitiveCredential("abcd")).isEqualTo("[REDACTED; sha256=88d4266fd4e6338d]");
    assertThat(UrlUtils.redactSensitiveCredential("ä")).isEqualTo("[REDACTED; sha256=33e6d73fee82904c]");
    assertThat(UrlUtils.redactSensitiveCredential(null)).isNull();
    assertThat(UrlUtils.redactSensitiveCredential("")).isEqualTo("[REDACTED]");
  }

  @Test
  void encodedCredentialMatchesUnencodedHeaderFingerprint() {
    String credential = "app|secret+space ä&=end";
    String expected = "access_token=" + UrlUtils.redactSensitiveCredential(credential);
    assertThat(UrlUtils.redactSensitiveParameters("access_token=" + UrlUtils.urlEncode(credential)))
      .isEqualTo(expected);
    assertThat(UrlUtils.redactSensitiveParameters("access_token=abc&input_token=%61bc"))
      .isEqualTo("access_token=[REDACTED; sha256=ba7816bf8f01cfea]&input_token=[REDACTED; sha256=ba7816bf8f01cfea]");
  }

  @Test
  void guessableCodesHaveNoFingerprint() {
    assertThat(UrlUtils.redactSensitiveParameters("code=123456&signed_request=abc&sessions=abc&appsecret_proof=abc"))
      .isEqualTo("code=[REDACTED]&signed_request=[REDACTED]&sessions=[REDACTED]&appsecret_proof=[REDACTED]");
  }

  @Test
  void redactRepeatedEmptyAndEncodedCredentials() {
    assertThat(UrlUtils.redactSensitiveParameters("access_token=app%7Csecret&access_token=&access_token=second"))
      .isEqualTo("access_token=" + UrlUtils.redactSensitiveCredential("app|secret")
          + "&access_token=[REDACTED]&access_token=" + UrlUtils.redactSensitiveCredential("second"));
  }

  @Test
  void mediumKeepsOnlyNonemptyOrdinaryAccessTokensInTheirOriginalEncoding() {
    String parameters = "ACCESS%5ftoken=first%2Btoken&access_token=second&access_token="
        + "&appsecret%5fproof=proof&client_secret=secret&input_token=other&refresh_token=refresh&batch=payload";

    assertThat(UrlUtils.redactSensitiveParameters(parameters, Security.MEDIUM))
      .isEqualTo("ACCESS%5ftoken=first%2Btoken&access_token=second&access_token=[REDACTED]"
          + "&appsecret%5fproof=[REDACTED]&client_secret=" + UrlUtils.redactSensitiveCredential("secret")
          + "&input_token=" + UrlUtils.redactSensitiveCredential("other") + "&refresh_token="
          + UrlUtils.redactSensitiveCredential("refresh") + "&batch=[REDACTED]");
  }

  @ParameterizedTest
  @ValueSource(strings = { "123|private-secret", "123%7Cprivate-secret", "123%7cprivate-secret" })
  void mediumDoesNotRevealTheSecretInAnAppAccessToken(String token) {
    String url = "https://example.com/me?access_token=" + token + "#access_token=fragment-token";

    assertThat(UrlUtils.redactSensitiveUrl(url, Security.MEDIUM)).isEqualTo(UrlUtils.redactSensitiveUrl(url));
    assertThat(UrlUtils.redactSensitiveUrl(url, Security.LOW)).isEqualTo(url);
  }

  @Test
  void preserveOrdinaryEncodedParameterBoundaries() {
    String parameters = "fields=id%2Cname&message=one%26two%3Dthree&format=json&&";
    assertThat(UrlUtils.redactSensitiveParameters(parameters)).isEqualTo(parameters);
    assertThat(UrlUtils.redactSensitiveParameters(null)).isNull();
    assertThat(UrlUtils.redactSensitiveParameters("")).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(strings = { "access%_token=credential-value", "fields=credential-value%", "credential-value",
      "=credential-value", "access_token=credential-value%" })
  void malformedParametersDoNotFallBackToRawData(String parameter) {
    assertThat(UrlUtils.redactSensitiveParameters("fields=id&" + parameter + "&format=json")).contains("[REDACTED]")
      .doesNotContain("credential-value").startsWith("fields=id&").endsWith("&format=json");
    assertThat(UrlUtils.redactSensitiveParameters("fields=id&" + parameter + "&format=json", Security.MEDIUM))
      .isEqualTo("fields=id&[REDACTED]&format=json");
  }

  @Test
  void redactNestedBatchCredentialsWithoutParsingPayload() {
    String batch = "[{\"relative_url\":\"me?access_token=nested-token\","
        + "\"body\":\"client_secret=nested-secret\",\"headers\":[{\"name\":\"Authorization\",\"value\":\"Bearer nested-header\"}]}]";
    assertThat(UrlUtils.redactSensitiveParameters("batch=" + UrlUtils.urlEncode(batch) + "&fields=id"))
      .isEqualTo("batch=[REDACTED]&fields=id");
  }

  @Test
  void redactUrlQueriesAndOAuthFragments() {
    assertThat(UrlUtils.redactSensitiveUrl("https://graph.facebook.com/me?fields=id&access_token=url-token")).isEqualTo(
      "https://graph.facebook.com/me?fields=id&access_token=" + UrlUtils.redactSensitiveCredential("url-token"));
    assertThat(UrlUtils.redactSensitiveUrl("https://example.com/callback#access_token=fragment-token"))
      .isEqualTo("https://example.com/callback#[REDACTED]");
    assertThat(UrlUtils.redactSensitiveUrl("https://example.com/callback?code=query-code#fragment-token"))
      .isEqualTo("https://example.com/callback?code=[REDACTED]#[REDACTED]");
    assertThat(UrlUtils.redactSensitiveUrl("https://graph.facebook.com/me")).isEqualTo("https://graph.facebook.com/me");
    assertThat(UrlUtils.redactSensitiveUrl(null)).isNull();
  }

  @Test
  void redactionIsIdempotent() {
    String redacted = UrlUtils.redactSensitiveUrl(
      "https://graph.facebook.com/me?access_token=token&client_secret=secret" + "&code=123456&fields=id#fragment");
    assertThat(UrlUtils.redactSensitiveUrl(redacted)).isEqualTo(redacted);
    String fingerprint = UrlUtils.redactSensitiveCredential("abc");
    assertThat(UrlUtils.redactSensitiveCredential(fingerprint)).isEqualTo(fingerprint);
    assertThat(UrlUtils.redactSensitiveCredential("[REDACTED]")).isEqualTo("[REDACTED]");
    assertThat(UrlUtils.redactSensitiveParameters("access_token=" + UrlUtils.urlEncode(fingerprint)))
      .isEqualTo("access_token=" + fingerprint);
    assertThat(UrlUtils.redactSensitiveParameters("access_token=[REDACTED]")).isEqualTo("access_token=[REDACTED]");
  }

  @Test
  void incompleteMarkersAreNotReturnedAsCredentials() {
    assertThat(UrlUtils.redactSensitiveCredential("[REDACTED; sha256=private-credential]"))
      .matches("\\[REDACTED; sha256=[0-9a-f]{16}\\]").doesNotContain("private-credential");
  }

  @Test
  void urlEncode_null() {
    assertThat(UrlUtils.urlEncode(null)).isNull();
  }

  @Test
  void urlEncode_string() {
    assertThat(UrlUtils.urlEncode("This is a string")).isEqualTo("This+is+a+string");
  }

  @Test
  void urlDecode_null() {
    assertThat(UrlUtils.urlDecode(null)).isNull();
  }

  @Test
  void urlDecode_string() {
    assertThat(UrlUtils.urlDecode("This+is+a+string")).isEqualTo("This is a string");
  }

  @Test
  void queryString() {
    assertThat(extractParametersFromQueryString(null)).isEmpty();
    assertThat(extractParametersFromQueryString("")).isEmpty();
    assertThat(extractParametersFromQueryString("access_token=123")).hasSize(1);
    assertThat(extractParametersFromQueryString("?access_token=123")).hasSize(1);
  }

  @Test
  void urlParameters() {
    assertThat(extractParametersFromUrl(null)).isEmpty();
    assertThat(extractParametersFromUrl("")).isEmpty();
    assertThat(extractParametersFromUrl("access_token=123")).isEmpty();
    assertThat(extractParametersFromUrl("?access_token=123")).hasSize(1);
    assertThat(extractParametersFromUrl("http://whatever?access_token=123")).hasSize(1);
  }

  @ParameterizedTest(name = "{index} {0}")
  @MethodSource("provideRemoveUrls")
  void removeParameter(String exampleURL, String key, String expectedURL) {
    String resultURL = UrlUtils.removeQueryParameter(exampleURL, key);
    assertThat(resultURL).isEqualTo(expectedURL);
  }

  private static Stream<Arguments> provideRemoveUrls() {
    return Stream.of(
      Arguments.of(Named.of("lastParameter", "http://www.example.com?access_token=123&before=1234"), "before",
        "http://www.example.com?access_token=123"),
      Arguments.of(Named.of("firstParameter", "http://www.example.com?access_token=123&before=1234"), "access_token",
        "http://www.example.com?before=1234"),
      Arguments.of(Named.of("middleParameter", "http://www.example.com?access_token=123&first=6543&before=1234"),
        "first", "http://www.example.com?access_token=123&before=1234"));
  }

  @ParameterizedTest(name = "{index} {0}")
  @MethodSource("provideReplaceUrls")
  void replaceOrAddParameter(String exampleURL, String expectedURL) {
    String resultURL = UrlUtils.replaceOrAddQueryParameter(exampleURL, "before", "56789");
    assertThat(resultURL).isEqualTo(expectedURL);
  }

  private static Stream<Arguments> provideReplaceUrls() {
    return Stream.of(
      Arguments.of(Named.of("replace", "http://www.example.com?access_token=123&before=1234"),
        "http://www.example.com?access_token=123&before=56789"),
      Arguments.of(Named.of("addParameterNoParameter", "http://www.example.com"),
        "http://www.example.com?before=56789"),
      Arguments.of(Named.of("addParameter", "http://www.example.com?access_token=123"),
        "http://www.example.com?access_token=123&before=56789"));
  }
}
