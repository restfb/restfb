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

import com.restfb.exception.FacebookOAuthException;
import com.restfb.exception.ThreadsApiException;
import com.restfb.exception.generator.DefaultFacebookExceptionGenerator;
import org.junit.jupiter.api.Test;

import static com.restfb.testutils.RestfbAssertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

class ThreadsExceptionTest extends AbstractJsonMapperTests {

    @Test
    void threadsException() {
        String jsonErrorString = jsonFromClasspath("threads/exception");
        DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
        try {
            generator.throwFacebookResponseStatusExceptionIfNecessary(jsonErrorString, 400);
            failBecauseExceptionWasNotThrown(FacebookOAuthException.class);
        } catch (ThreadsApiException fe) {
            assertThat(fe.getErrorCode()).isEqualTo(100);
            assertThat(fe.getMessage()).contains("missing permission");
        } catch (Exception ex) {
            failBecauseExceptionWasNotThrown(FacebookOAuthException.class);
        }
    }
}
