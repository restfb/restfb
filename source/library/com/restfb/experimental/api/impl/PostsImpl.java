/*
 * Copyright (c) 2010-2015 Norbert Bartels.
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

package com.restfb.experimental.api.impl;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.experimental.api.Posts;
import com.restfb.types.Comment;
import com.restfb.types.Post;
import java.util.ArrayList;
import java.util.List;

public class PostsImpl implements Posts {
  private final FacebookClient facebookClient;

  PostsImpl(FacebookClient facebookClient) {
    this.facebookClient = facebookClient;
  }

  @Override
  public List<Comment> fetchCommentList(Post post) {

    List<Comment> postCommentList = new ArrayList<Comment>();
    Connection<Comment> connection = facebookClient.fetchConnection(post.getId() + "/comments", Comment.class);
    for (List<Comment> page : connection) {
      for (Comment comment : page) {
        postCommentList.add(comment);
      }
    }

    return postCommentList;
  }

  @Override
  public Post get(String postId) {
    return facebookClient.fetchObject(postId, Post.class);
  }

}
