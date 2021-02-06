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
package com.restfb.types.webhook;

import com.restfb.Facebook;
import com.restfb.types.webhook.base.AbstractFeedPostValue;

import lombok.Getter;
import lombok.Setter;

/**
 * change value of the ratings comment
 */
public class RatingsCommentValue extends AbstractFeedPostValue {

  @Getter
  @Setter
  @Facebook("parent_id")
  private String parentId;

  @Getter
  @Setter
  @Facebook("comment_id")
  private String commentId;

  @Getter
  @Setter
  @Facebook("open_graph_story_id")
  private String openGraphStoryId;

  /**
   * returns {@code true} if the added comment is a reply (a comment to a comment), {@code false} otherwise
   *
   * may be {@code null}, if it is not possible to check
   *
   * @return {@code true} if it is a Reply, {@code false}, may be {@code null}
   */
  public Boolean isReply() {
    if (getPostId() != null && getParentId() != null) {
      return !getPostId().equals(getParentId());
    } else {
      return null;
    }
  }
}
