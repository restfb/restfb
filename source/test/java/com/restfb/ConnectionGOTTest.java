/*
 * Copyright (c) 2010-2015 Norbert Bartels
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

import com.restfb.types.Post;
import com.restfb.types.Post.Comments;
import com.restfb.types.Post.Likes;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ConnectionGOTTest extends AbstractJsonMapperTests {

  /**
   * we check the likes and the comments count if some likes or comments are present, we fill the count/total count
   * (summary needs to be set to true)
   */
  @Test
  public void check_2_1_comments_likes_count() {
    Connection<Post> con =
        new Connection<Post>(new DefaultFacebookClient(), jsonFromClasspath("v2_1/feed-got"), Post.class);
    List<Post> postPage = con.getData();
    for (Post post : postPage) {
      Comments cs = post.getComments();
      if (null != cs && !cs.getData().isEmpty()) {
        Assert.assertTrue(cs.getTotalCount() > 0);
      }
      Likes ls = post.getLikes();
      if (null != ls && !ls.getData().isEmpty()) {
        Assert.assertTrue(ls.getTotalCount() > 0);
      }
    }
    assertEquals(25, postPage.size());
  }
}
