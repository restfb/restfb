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

import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.experimental.api.Comments;
import com.restfb.types.Comment;
import com.restfb.types.GraphResponse;

public class CommentsImpl implements Comments {

  private final FacebookClient facebookClient;

  CommentsImpl(FacebookClient facebookClient) {
    this.facebookClient = facebookClient;
  }

  @Override
  public boolean hide(String commentId) {

    Comment comment = facebookClient.fetchObject(commentId, Comment.class, Parameter.with("fields", "can_hide"));
    if (comment.isCanHide()) {
      GraphResponse response =
          facebookClient.publish(commentId, GraphResponse.class, Parameter.with("is_hidden", "true"));

      return response.isSuccess();
    }
    return false;
  }

  @Override
  public boolean unhide(String commentId) {
    Comment comment = facebookClient.fetchObject(commentId, Comment.class, Parameter.with("fields", "can_hide"));
    if (comment.isCanHide()) {
      GraphResponse response =
          facebookClient.publish(commentId, GraphResponse.class, Parameter.with("is_hidden", "false"));

      return response.isSuccess();
    }
    return false;
  }

  @Override
  public Comment get(String commentId) {
    return facebookClient.fetchObject(commentId, Comment.class);
  }

  @Override
  public boolean delete(String commentId) {
    return facebookClient.deleteObject(commentId);
  }

  @Override
  public boolean update(String commentId, String updatedMessage) {
    GraphResponse response =
        facebookClient.publish(commentId, GraphResponse.class, Parameter.with("message", updatedMessage));
    return response.isSuccess();
  }

  @Override
  public boolean like(String commentId) {
    GraphResponse response = facebookClient.publish(commentId + "/likes", GraphResponse.class);
    return response.isSuccess();
  }

  @Override
  public boolean unLike(String commentId) {
    return facebookClient.deleteObject(commentId + "/likes");
  }

}
