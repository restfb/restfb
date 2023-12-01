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
package com.restfb.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.integration.base.NeedFacebookWriteAccess;
import com.restfb.integration.base.RestFbImageIntegrationTestBase;
import com.restfb.types.GraphResponse;

@NeedFacebookWriteAccess
class PostTwoBinaryITCase extends RestFbImageIntegrationTestBase {

  @Test
  void postTwoBinarysAtOnce() {
    InputStream thumbnailIs = getClass().getResourceAsStream("/binary/video/thumb.png");
    InputStream videoIs = getClass().getResourceAsStream("/binary/video/small.mp4");

    byte[] imageAsBytes = getBytesFromInputStream(thumbnailIs);
    byte[] videoAsBytes = getBytesFromInputStream(videoIs);

    DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getPageAccessToken(), Version.LATEST);
    GraphResponse response = client.publish("me/videos", //
      GraphResponse.class, // response
      Arrays.asList( // array list
        BinaryAttachment.with("source", "test.mp4", videoAsBytes), // video
        BinaryAttachment.with("thumb", "thumbnail.png", imageAsBytes)), // picture
      Parameter.with("description", "Test video with Thumb"));

    assertNotNull(response);
  }
}
