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
package com.restfb.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.integration.base.NeedFacebookWriteAccess;
import com.restfb.integration.base.RestFbImageIntegrationTestBase;
import com.restfb.json.JsonObject;

@NeedFacebookWriteAccess
public class PostByteImageITCase extends RestFbImageIntegrationTestBase {

  @Test
  public void postImageToPagePhotosWithMessage() {
    byte[] imageAsBytes = fetchBytesFromImage();
    DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getPageAccessToken(), Version.LATEST);
    JsonObject obj = client.publish(getTestSettings().getPageId() + "/photos", JsonObject.class,
      BinaryAttachment.with("test.png", imageAsBytes, "image/png"), Parameter.with("message", "TestImage"),
      Parameter.with("no_story", "true"));
    assertNotNull(obj);
  }

  @Test
  public void postImageToUserFeedWithMessageAndPrivacy() {
    byte[] imageAsBytes = fetchBytesFromImage();
    JsonObject privacy = new JsonObject();
    privacy.add("value", "SELF");
    DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.LATEST);
    JsonObject obj = client.publish("me/photos", JsonObject.class,
      BinaryAttachment.with("test.png", imageAsBytes, "image/png"), Parameter.with("privacy", privacy));
    assertNotNull(obj);
  }

  @Test
  public void postLinkToPageFeedWithMessage() {
    DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getPageAccessToken(), Version.LATEST);
    JsonObject obj = client.publish(getTestSettings().getPageId() + "/feed", JsonObject.class,
      Parameter.with("message", "Just a test url"), Parameter.with("link", "http://www.restfb.com"));
    assertNotNull(obj);
  }

  @Test
  public void postImageToPhotosAndFeedWithMessage() {
    byte[] imageAsBytes = fetchBytesFromImage();
    DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getPageAccessToken(), Version.LATEST);
    JsonObject obj = client.publish(getTestSettings().getPageId() + "/photos", JsonObject.class,
      BinaryAttachment.with("test.png", imageAsBytes, "image/png"), Parameter.with("message", "TestImage"));
    assertNotNull(obj);
  }

  @Test
  public void postImageToPhotos() {
    byte[] imageAsBytes = fetchBytesFromImage();
    DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getPageAccessToken(), Version.LATEST);
    JsonObject obj = client.publish(getTestSettings().getPageId() + "/photos", JsonObject.class,
      BinaryAttachment.with("test.png", imageAsBytes, "image/png"));
    assertNotNull(obj);
  }

  @Test
  public void postImageToAlbumPhotos() {
    byte[] imageAsBytes = fetchBytesFromImage();
    DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getPageAccessToken(), Version.LATEST);
    JsonObject obj = client.publish(getTestSettings().getPageAlbumId() + "/photos", JsonObject.class,
            BinaryAttachment.with("test.png", imageAsBytes, "image/png"));
    assertNotNull(obj);
  }

}
