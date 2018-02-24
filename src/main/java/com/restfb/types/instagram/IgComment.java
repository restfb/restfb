/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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
package com.restfb.types.instagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.types.FacebookType;
import com.restfb.util.DateUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a <a href="https://developers.facebook.com/docs/instagram-api/reference/comment">Instagram Comment</a>
 * object
 */
public class IgComment extends FacebookType {

  private static final long serialVersionUID = 1L;
  /**
   * Whether the comment is hidden
   */
  @Getter
  @Setter
  @Facebook
  private Boolean hidden;

  /**
   * Number of likes on the comment
   */
  @Getter
  @Setter
  @Facebook("like_count")
  private Long likeCount;

  /**
   * Media on which the comment is made
   */
  @Getter
  @Setter
  @Facebook
  private IgMedia media;

  /**
   * Text of the comment
   */
  @Getter
  @Setter
  @Facebook
  private String text;

  @Facebook("timestamp")
  private String rawTimestamp;

  /**
   * Timestamp of comment
   */
  @Getter
  @Setter
  private Date timestamp;

  /**
   * User who made the comment
   */
  @Getter
  @Setter
  @Facebook
  private IgUser user;

  @JsonMapper.JsonMappingCompleted
  private void convertTimestamp() {
    timestamp = DateUtils.toDateFromLongFormat(rawTimestamp);
  }

  @Facebook
  private List<IgComment> replies = new ArrayList<IgComment>();

  public List<IgComment> getReplies() {
    return Collections.unmodifiableList(replies);
  }

  public boolean addReply(IgComment reply) {
    return replies.add(reply);
  }

  public boolean removeReply(IgComment reply) {
    return replies.remove(reply);
  }
}
