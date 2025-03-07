/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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
package com.restfb.types.webhook;

import com.restfb.Facebook;
import com.restfb.types.webhook.base.AbstractFeedPostValue;

import lombok.Getter;
import lombok.Setter;

/**
 * Change value describing a reaction to a post/comment/reply.
 */
public class FeedReactionValue extends AbstractFeedPostValue {

  @Getter
  @Setter
  @Facebook("parent_id")
  private String parentId;

  @Getter
  @Setter
  @Facebook("reaction_type")
  private String reactionType;

  @Getter
  @Setter
  @Facebook("comment_id")
  private String commentId;

  /**
   * returns <code>true</code> if the reaction was made on a Post
   * 
   * @return <code>true</code> if the reaction was made on a Post
   */
  public boolean isPostReaction() {
    return commentId == null;
  }

  /**
   * returns <code>true</code> if the reaction was made on a Comment, <code>false</code> if the reaction was made on a
   * post
   * 
   * @return <code>true</code> if the reaction was made on a Comment
   */
  public boolean isCommentReaction() {
    return commentId != null;
  }

  /**
   * returns <code>true</code> if the reaction was made on a Reply (comment of a comment),
   * 
   * @return <code>true</code> if the reaction was made on a Reply
   */
  public boolean isReplyReaction() {
    return commentId != null && !getPostId().equals(parentId);
  }

}
