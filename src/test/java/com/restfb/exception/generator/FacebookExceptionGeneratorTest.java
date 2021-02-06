/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import com.restfb.exception.FacebookOAuthException;
import com.restfb.exception.ResponseErrorJsonParsingException;

import org.junit.Test;

public class FacebookExceptionGeneratorTest {

  @Test
  public void checkSkipErrorResponseParsing_NoJsonObject() {
    String json = "testString";
    parseJson(json, false);
  }

  @Test
  public void checkSkipErrorResponseParsing_JsonObjectWithoutError() {
    String json = "{ \"testString\": \"example\" }";
    parseJson(json, false);
  }

  @Test
  public void checkSkipErrorResponseParsing_JsonObjectWithError() {
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
  public void checkSilentJsonObjectGeneration_WithValidJson() {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    String json = "{ \"error\": \"exampleError\" }";
    assertThat(generator.silentlyCreateObjectFromString(json)).isNotNull();
  }

  @Test
  public void checkSilentJsonObjectGeneration_WithInvalidJson() {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    String json = "{ \"error\"}";
    assertThat(generator.silentlyCreateObjectFromString(json)).isNull();
  }

  @Test
  public void checkIsTransient() {
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

}
