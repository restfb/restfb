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
package com.restfb.exception.generator;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.restfb.exception.FacebookOAuthException;
import com.restfb.exception.ResponseErrorJsonParsingException;

class FacebookExceptionGeneratorTest {

  @Test
  void checkSkipErrorResponseParsing_NoJsonObject() {
    String json = "testString";
    parseJson(json, false);
  }

  @Test
  void checkSkipErrorResponseParsing_JsonObjectWithoutError() {
    String json = "{ \"testString\": \"example\" }";
    parseJson(json, false);
  }

  @Test
  void checkSkipErrorResponseParsing_JsonObjectWithError() {
    String json = "{ \"error\": \"exampleError\" }";
    parseJson(json, true);
  }

  private void parseJson(String json, boolean failOnException) {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    try {
      generator.skipResponseStatusExceptionParsing(json);
      if (!failOnException) {
        fail("Exception not thrown");
      }
      // every thing fine;
    } catch (ResponseErrorJsonParsingException e) {
      if (failOnException) {
        fail("Exception thrown");
      }
    }
  }

  @Test
  void checkSilentJsonObjectGeneration_WithValidJson() {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    String json = "{ \"error\": \"exampleError\" }";
    assertThat(generator.silentlyCreateObjectFromString(json)).isNotNull();
  }

  @Test
  void checkSilentJsonObjectGeneration_WithInvalidJson() {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    String json = "{ \"error\"}";
    assertThat(generator.silentlyCreateObjectFromString(json)).isNull();
  }

  @Test
  void checkIsTransient() {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    String json = "{\n" + //
        "  \"error\": {\n" + //
        "    \"message\": \"(#2) Service temporarily unavailable\",\n" + //
        "    \"type\": \"OAuthException\",\n" + //
        "    \"is_transient\": true,\n" + //
        "    \"code\": 2,\n" + //
        "    \"fbtrace_id\": \"abcdefgh\"\n" + //
        "  }\n" + //
        "}";

    try {
      generator.throwFacebookResponseStatusExceptionIfNecessary(json, 500);
      failBecauseExceptionWasNotThrown(FacebookOAuthException.class);
    } catch (FacebookOAuthException fex) {
      assertThat(fex.getIsTransient()).isTrue();
      assertThat(fex.getErrorCode().intValue()).isEqualTo(2);
    }

  }

  @Test
  void checkErrorData() {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    String json = "{\n" +
            "    \"error\": {\n" +
            "        \"message\": \"(#131009) Parameter value is not valid\",\n" +
            "        \"type\": \"OAuthException\",\n" +
            "        \"code\": 131009,\n" +
            "        \"error_data\": {\n" +
            "            \"messaging_product\": \"whatsapp\",\n" +
            "            \"details\": \"Duplicate button id\"\n" +
            "        },\n" +
            "        \"fbtrace_id\": \"A7YysJRhDLLwyNk-RXuVBTM\"\n" +
            "    }\n" +
            "}";

    try {
      generator.throwFacebookResponseStatusExceptionIfNecessary(json, 500);
      failBecauseExceptionWasNotThrown(FacebookOAuthException.class);
    } catch (FacebookOAuthException fex) {
      assertThat(fex.getErrorCode().intValue()).isEqualTo(131009);
      assertThat(fex.getErrorData()).isNotNull();
      assertThat(fex.getErrorData("messaging_product")).isEqualTo("whatsapp");
      assertThat(fex.getErrorData("details")).isEqualTo("Duplicate button id");
    }
  }

}
