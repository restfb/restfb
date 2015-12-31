/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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
 * change value of the feed like
 */
public class FeedLikeValue extends AbstractFeedPostValue {

  @Getter
  @Setter
  @Facebook("user_id")
  private String userId;

  @Getter
  @Setter
  @Facebook("parent_id")
  private String parentId;

  /**
   * returns if the page is liked or a post.
   * 
   * if this is a page like you can only fetch the {@code userId} of the liking user. Otherwise you can fetch fields
   * like {@code parentId}, {@code postId}, {@code senderId} and so on
   * 
   * @return if this is a page like
   */
  public boolean isPageLike() {
    return userId != null;
  }
}
