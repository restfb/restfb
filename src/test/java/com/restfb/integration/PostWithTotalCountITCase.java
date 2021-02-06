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

import static org.junit.Assert.*;

import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import com.restfb.types.Post;

import org.junit.Test;

public class PostWithTotalCountITCase extends RestFbIntegrationTestBase {

  @Test
  public void checkPostWithCommentsAndLikes() {
    DefaultFacebookClient client =
        new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.LATEST);
    Post gotPost = client.fetchObject("74133697733_10152424266332734", Post.class,
      Parameter.with("fields", "from,to,likes.summary(true),comments.summary(true)"));
    assertNotNull(gotPost);
    assertTrue(gotPost.getLikesCount() > 0);
    assertTrue(gotPost.getLikes().getTotalCount() > 0);
    assertEquals(gotPost.getLikesCount(), gotPost.getLikes().getTotalCount());
    assertTrue(gotPost.getComments().getTotalCount() > 0);
    assertEquals(gotPost.getComments().getTotalCount(), gotPost.getCommentsCount());
  }
}
