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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.integration.base.NeedFacebookWriteAccess;
import com.restfb.integration.base.RestFbImageIntegrationTestBase;
import com.restfb.types.FacebookReelAttachment;
import com.restfb.types.GraphResponse;
import com.restfb.types.ResumableUploadStartResponse;

@NeedFacebookWriteAccess
class PostFacebookStoryITCase extends RestFbImageIntegrationTestBase {

  @Test
  void publishStoryTest() {
    InputStream videoIs = getClass().getResourceAsStream("/binary/video/small.mp4");
    byte[] videoAsBytes = getBytesFromInputStream(videoIs);

    FacebookClient fbClient = new DefaultFacebookClient(getTestSettings().getPageAccessToken(), Version.LATEST);
    ResumableUploadStartResponse videoResponse = fbClient.publish(getTestSettings().getPageId() + "/video_stories",
      ResumableUploadStartResponse.class, Parameter.with("upload_phase", "start"));
    String videoId = videoResponse.getVideoId();
    assertNotNull(videoId);

    DefaultFacebookClient upClient = new DefaultFacebookClient(getTestSettings().getPageAccessToken(), Version.LATEST);

    GraphResponse uploadResponse =
        upClient.publish(videoId, GraphResponse.class, FacebookReelAttachment.withByteContent(videoAsBytes));

    assertTrue(uploadResponse.isSuccess());

    GraphResponse publishResponse = fbClient.publish(getTestSettings().getPageId() + "/video_stories",
      GraphResponse.class, Parameter.with("video_id", videoId), Parameter.with("upload_phase", "finish"));

    assertTrue(publishResponse.isSuccess());
    System.out.println("Post ID: " + publishResponse.getPostId());
  }

}
