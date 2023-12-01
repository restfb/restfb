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

import org.junit.jupiter.api.Test;

import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.integration.base.NeedFacebookWriteAccess;
import com.restfb.integration.base.RestFbImageIntegrationTestBase;
import com.restfb.json.JsonObject;
import com.restfb.types.GraphResponse;

@NeedFacebookWriteAccess
class PostMultiPhotoPostITCase extends RestFbImageIntegrationTestBase {

  @Test
  void publishMultiImagePost() {
    byte[] image1AsBytes = fetchBytesFromImage();
    byte[] image2AsBytes = fetchBytesFromImage();
    byte[] image3AsBytes = fetchBytesFromImage();

    // Create a new Facebook Client with a page access token
    DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getPageAccessToken(), Version.LATEST);

    // upload the images and save the returned id in a GraphResponse object
    GraphResponse response1 = client.publish(getTestSettings().getPageId() + "/photos", //
      GraphResponse.class, //
      BinaryAttachment.with("test1.png", image1AsBytes, "image/png"), //
      Parameter.with("published", "false"));
    GraphResponse response2 = client.publish(getTestSettings().getPageId() + "/photos", //
      GraphResponse.class, //
      BinaryAttachment.with("test2.png", image2AsBytes, "image/png"), //
      Parameter.with("published", "false"));
    GraphResponse response3 = client.publish(getTestSettings().getPageId() + "/photos", //
      GraphResponse.class, //
      BinaryAttachment.with("test3.png", image3AsBytes, "image/png"), //
      Parameter.with("published", "false"));

    // Image JSON, the "media_fbid" key is needed here
    JsonObject image1 = new JsonObject();
    image1.add("media_fbid", response1.getId());

    JsonObject image2 = new JsonObject();
    image2.add("media_fbid", response2.getId());

    JsonObject image3 = new JsonObject();
    image3.add("media_fbid", response3.getId());

    // publish the images with a message to the page feed
    GraphResponse postResponse = //
        client.publish("me/feed", //
          GraphResponse.class, //
          Parameter.with("message", "Multiphoto Test"), // the message for the post
          Parameter.with("attached_media[0]", image1), // image 1
          Parameter.with("attached_media[1]", image2), // image 2
          Parameter.with("attached_media[2]", image3)); // image 3

    assertNotNull(postResponse);
  }
}
