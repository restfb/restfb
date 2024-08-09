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

import com.restfb.exception.FacebookOAuthException;
import org.junit.jupiter.api.Test;

import com.restfb.exception.FacebookGraphException;
import com.restfb.exception.generator.DefaultFacebookExceptionGenerator;
import com.restfb.testutils.AssertJson;

class OAuthExceptionTest extends AbstractJsonMapperTests {

  @Test
  void exception() {
    String jsonErrorString = jsonFromClasspath("oauthExampleError");
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    try {
      generator.throwFacebookResponseStatusExceptionIfNecessary(jsonErrorString, 400);
      failBecauseExceptionWasNotThrown(FacebookGraphException.class);
    } catch (FacebookGraphException fe) {
      assertThat(fe.getErrorUserTitle()).isEqualTo("Fundraisers Have a 90 Day Limit");
      assertThat(fe.getErrorUserMessage()).isEqualTo("Please choose another end date.");
      assertThat(fe.getErrorCode()).isEqualTo(1);
      assertThat(fe.getErrorSubcode()).isEqualTo(1965014);
      assertThat(fe.getErrorMessage()).isEqualTo("An unknown error occurred");
      assertThat(fe.getMessage()).isEqualTo(
        "Received Facebook error response of type OAuthException: An unknown error occurred (code 1, subcode 1965014) 'Fundraisers Have a 90 Day Limit - Please choose another end date.'");
      assertThat(fe.getRawErrorJson()).isNotNull();
      AssertJson.assertEquals(jsonErrorString, fe.getRawErrorJson().toString());
      assertThat(fe.getFbtraceId()).isEqualTo("EmOAvxDPlF2");
    }
  }

  @Test
  void loginOauthException() {
    String jsonErrorString = jsonFromClasspath("exampleOauthError");
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    try {
      generator.throwFacebookResponseStatusExceptionIfNecessary(jsonErrorString, 400);
      failBecauseExceptionWasNotThrown(FacebookOAuthException.class);
    } catch (FacebookOAuthException fe) {
      assertThat(fe.getErrorCode()).isEqualTo(400);
      assertThat(fe.getMessage()).contains("verification");
    } catch (Exception ex) {
      failBecauseExceptionWasNotThrown(FacebookOAuthException.class);
    }
  }

}
