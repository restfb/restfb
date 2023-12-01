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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.restfb.types.Comments;
import com.restfb.types.Likes;
import com.restfb.types.Post;

class ConnectionGOTTest extends AbstractJsonMapperTests {

  /**
   * we check the likes and the comments count if some likes or comments are present, we fill the count/total count
   * (summary needs to be set to true)
   */
  @Test
  void check_2_1_comments_likes_count() {
    Connection<Post> con =
        new Connection<>(new DefaultFacebookClient(Version.LATEST), jsonFromClasspath("v2_1/feed-got"), Post.class);
    List<Post> postPage = con.getData();
    postPage.forEach(this::checkPost);
    assertThat(postPage).hasSize(25);
  }

  private void checkPost(Post post) {
    Comments cs = post.getComments();
    if (null != cs && !cs.getData().isEmpty()) {
      assertThat(cs.getTotalCount()).isPositive();
    }
    Likes ls = post.getLikes();
    if (null != ls && !ls.getData().isEmpty()) {
      assertThat(ls.getTotalCount()).isPositive();
    }
  }
}
