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
package com.restfb.types.instagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.FacebookType;

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
   * An object containing:
   *
   * id — IGSID of the Instagram user who created the IG Comment.
   * username — Username of the Instagram user who created the IG Comment.
   */
  @Getter
  @Setter
  @Facebook
  private IgFrom from;

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

  /**
   * Timestamp of comment
   */
  @Getter
  @Setter
  @Facebook
  private Date timestamp;

  /**
   * User who made the comment
   *
   * @deprecated with Graph API 12 or December 13, 2021 for all versions
   */
  @Getter
  @Setter
  @Facebook
  @Deprecated
  private IgUser user;

  /**
   * User will only be returned when queried by owner of comment. Otherwise, username is the only field that will be
   * returned.
   */
  @Getter
  @Setter
  @Facebook
  private String username;

  /**
   * ID of the parent IG Comment if this comment was created on another IG Comment (i.e. a reply to another comment.
   */
  @Getter
  @Setter
  @Facebook("parent_id")
  private String parentId;

  @Facebook
  private List<IgComment> replies = new ArrayList<>();

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
