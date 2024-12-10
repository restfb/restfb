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
