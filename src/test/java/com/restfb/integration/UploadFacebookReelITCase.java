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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.integration.base.BinaryAsBytes;
import com.restfb.integration.base.NeedFacebookWriteAccess;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import com.restfb.types.FacebookReelAttachment;
import com.restfb.types.GraphResponse;
import com.restfb.types.ReelsUploadStartResponse;
import com.restfb.types.Video;

@NeedFacebookWriteAccess
class UploadFacebookReelITCase extends RestFbIntegrationTestBase implements BinaryAsBytes {

  @Test
  void uploadReelVideoBinary() {
    FacebookClient client = new DefaultFacebookClient(getTestSettings().getPageAccessToken(), Version.LATEST);
    ReelsUploadStartResponse startResponse = client.publish(getTestSettings().getPageId() + "/video_reels", //
      ReelsUploadStartResponse.class, //
      Parameter.with("upload_phase", "start") //
    );

    String videoUploadID = startResponse.getVideoId();

    InputStream videoIs = getClass().getResourceAsStream("/binary/reels/example1.mp4");
    FacebookReelAttachment reelAttachment = FacebookReelAttachment.withByteContent(getBytesFromInputStream(videoIs));

    GraphResponse response = client.publish(videoUploadID, GraphResponse.class, reelAttachment);

    Video videoStatus = client.fetchObject(videoUploadID, Video.class, Parameter.withFields("status"));
    System.out.println("VideoStatus: " + videoStatus.getStatus());

    if (response.isSuccess()) {
      GraphResponse publishResponse =
          client.publish(getTestSettings().getPageId() + "/video_reels", GraphResponse.class, //
            Parameter.with("video_id", videoUploadID), //
            Parameter.with("upload_phase", "finish"), //
            Parameter.with("video_state", "PUBLISHED"), //
            Parameter.with("description", "Just a litte test"));
      assertTrue(publishResponse.isSuccess());
    } else {
      fail();
    }
  }

  @Test
  void uploadReelVideoUrl() {
    FacebookClient client = new DefaultFacebookClient(getTestSettings().getPageAccessToken(), Version.LATEST);
    ReelsUploadStartResponse startResponse = client.publish(getTestSettings().getPageId() + "/video_reels", //
      ReelsUploadStartResponse.class, //
      Parameter.with("upload_phase", "start") //
    );

    String videoUploadID = startResponse.getVideoId();

    String fileUrl =
        "https://player.vimeo.com/external/660616894.hd.mp4?s=77da3a14917189c1c943d1d0d8a594a45445f327&profile_id=174&oauth_token_id=57447761";
    FacebookReelAttachment reelAttachment = FacebookReelAttachment.withUrl(fileUrl);

    GraphResponse response = client.publish(videoUploadID, GraphResponse.class, reelAttachment);

    Video videoStatus = client.fetchObject(videoUploadID, Video.class, Parameter.withFields("status"));
    System.out.println("VideoStatus: " + videoStatus.getStatus());

    if (response.isSuccess()) {
      GraphResponse publishResponse =
          client.publish(getTestSettings().getPageId() + "/video_reels", GraphResponse.class, //
            Parameter.with("video_id", videoUploadID), //
            Parameter.with("upload_phase", "finish"), //
            Parameter.with("video_state", "PUBLISHED"), //
            Parameter.with("description", "Just a litte test")); //
      assertTrue(publishResponse.isSuccess());
    } else {
      fail();
    }
  }

}
