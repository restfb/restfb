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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import com.restfb.types.Url;

class FetchIDsITCase extends RestFbIntegrationTestBase {

  @Test
  void fetchWithEmail() {
    DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.LATEST);
    FetchObjectsResult me = client.fetchObjects(Arrays.asList("http://cnn.com", "http://abc.com"),
      FetchObjectsResult.class, Parameter.with("fields", "engagement"));
    assertTrue(me.abc.getReactionCount() > 0);
    assertTrue(me.abc.getCommentCount() > 0);
    assertTrue(me.abc.getShareCount() > 0);
    assertEquals(0, me.abc.getCommentPluginCount());
    assertTrue(me.cnn.getReactionCount() > 0);
    assertTrue(me.cnn.getCommentCount() > 0);
    assertTrue(me.cnn.getShareCount() > 0);
    assertEquals(0, me.cnn.getCommentPluginCount());
  }

  static class FetchObjectsResult {

    @Facebook("http://cnn.com")
    Url cnn;

    @Facebook("http://abc.com")
    Url abc;
  }

}
