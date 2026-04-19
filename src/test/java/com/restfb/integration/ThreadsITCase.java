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
package com.restfb.integration;

import java.util.ArrayList;
import java.util.List;

import com.restfb.integration.base.NeedFacebookWriteAccess;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import org.junit.jupiter.api.Test;

import com.restfb.DefaultThreadsClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.GraphResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@NeedFacebookWriteAccess
class ThreadsITCase extends RestFbIntegrationTestBase {

    @Test
    void publishImagePerUrl() {
        String thAccessToken = getTestSettings().getThreadsAccessToken();
        String clientSecret = getTestSettings().getThreadsClientSecret();
        Version version = Version.THREADS_LATEST;
        DefaultThreadsClient threadsClient = new DefaultThreadsClient(thAccessToken, clientSecret, version);

        String profileId = getTestSettings().getThreadsProfileId();
        String imageUrl = "https://placehold.co/600x400";

        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(Parameter.with("media_type","IMAGE"));
        parameterList.add(Parameter.with("image_url", imageUrl));
        parameterList.add(Parameter.with("text", "Test"));

        GraphResponse publish = threadsClient.publish(profileId + "/threads", GraphResponse.class, parameterList.toArray(Parameter[]::new));
        assertNotNull(publish);
        System.out.println(publish);
    }
}
