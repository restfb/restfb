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
package com.restfb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import com.restfb.exception.FacebookGraphException;
import com.restfb.exception.generator.DefaultFacebookExceptionGenerator;
import com.restfb.testutils.AssertJson;

import org.junit.Test;

public class StatusExceptionTest extends AbstractJsonMapperTests {

  @Test
  public void statusException() {
    String jsonErrorString = jsonFromClasspath("exampleError");
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    try {
      generator.throwFacebookResponseStatusExceptionIfNecessary(jsonErrorString, 400);
      failBecauseExceptionWasNotThrown(FacebookGraphException.class);
    } catch (FacebookGraphException fe) {
      assertThat(fe.getErrorUserTitle()).isEqualTo("A title");
      assertThat(fe.getErrorUserMessage()).isEqualTo("A message");
      assertThat(fe.getErrorCode()).isEqualTo(190);
      assertThat(fe.getErrorSubcode()).isEqualTo(460);
      assertThat(fe.getErrorMessage()).isEqualTo("Message describing the error");
      assertThat(fe.getRawErrorJson()).isNotNull();
      AssertJson.assertEquals(jsonErrorString, fe.getRawErrorJson().toString());
      assertThat(fe.getFbtraceId()).isEqualTo("");
    }
  }

  @Test
  public void statusExceptionWithFbtraceId() {
    String jsonErrorString = jsonFromClasspath("exampleErrorNew");
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    try {
      generator.throwFacebookResponseStatusExceptionIfNecessary(jsonErrorString, 400);
      failBecauseExceptionWasNotThrown(FacebookGraphException.class);
    } catch (FacebookGraphException fe) {
      assertThat(fe.getErrorUserTitle()).isEqualTo("A title");
      assertThat(fe.getErrorUserMessage()).isEqualTo("A message");
      assertThat(fe.getErrorCode()).isEqualTo(190);
      assertThat(fe.getErrorSubcode()).isEqualTo(460);
      assertThat(fe.getErrorMessage()).isEqualTo("Message describing the error");
      assertThat(fe.getRawErrorJson()).isNotNull();
      AssertJson.assertEquals(jsonErrorString, fe.getRawErrorJson().toString());
      assertThat(fe.getFbtraceId()).isEqualTo("EJplcsCHuLu");
    }
  }

}
