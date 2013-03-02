/*
 * Copyright (c) 2010-2013 Mark Allen.
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

package com.restfb.types;

import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static java.util.Collections.unmodifiableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/status">Status Message Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6
 */
public class StatusMessage extends NamedFacebookType {
  @Facebook
  private NamedFacebookType from;

  @Facebook
  private String message;

  @Facebook
  private String type;

  @Facebook("updated_time")
  private String updatedTime;

  @Facebook
  private List<NamedFacebookType> likes = new ArrayList<NamedFacebookType>();

  /**
   * Hack so JSON mapping won't fail when FB returns inconsistent JSON when there are 0 likes.
   */
  @Facebook("likes")
  @SuppressWarnings("unused")
  private EmptyLikes emptyLikes;

  @Facebook
  private List<Comment> comments = new ArrayList<Comment>();

  /**
   * Hack so JSON mapping won't fail when FB returns inconsistent JSON when there are 0 comments.
   */
  @Facebook("comments")
  @SuppressWarnings("unused")
  private EmptyComments emptyComments;

  private static final long serialVersionUID = 2L;

  /**
   * Sometimes Facebook will return <tt>"likes":{"count":0}</tt> instead of the connection-formatted likes object that's
   * documented - this class handles that so JSON mapping won't fail.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.8
   */
  private static class EmptyLikes implements Serializable {
    @Facebook
    @SuppressWarnings("unused")
    private Long count;

    private static final long serialVersionUID = 1L;
  }

  /**
   * Sometimes Facebook will return <tt>"comments":{"count":0}</tt> instead of the connection-formatted comments object
   * that's documented - this class handles that so JSON mapping won't fail.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.8
   */
  private static class EmptyComments implements Serializable {
    @Facebook
    @SuppressWarnings("unused")
    private Long count;

    private static final long serialVersionUID = 1L;
  }

  /**
   * The user who posted the message.
   * 
   * @return The user who posted the message.
   */
  public NamedFacebookType getFrom() {
    return from;
  }

  /**
   * The status message content.
   * 
   * @return The status message content.
   */
  public String getMessage() {
    return message;
  }

  /**
   * The object type which is set to status.
   * 
   * @return The object type which is set to status.
   */
  public String getType() {
    return type;
  }

  /**
   * The time the message was published.
   * 
   * @return The time the message was published.
   */
  public Date getUpdatedTime() {
    return toDateFromLongFormat(updatedTime);
  }

  /**
   * The users that have liked this message.
   * 
   * @return The users that have liked this message.
   */
  public List<NamedFacebookType> getLikes() {
    return unmodifiableList(likes);
  }

  /**
   * All of the comments on this message.
   * 
   * @return All of the comments on this message.
   */
  public List<Comment> getComments() {
    return unmodifiableList(comments);
  }
}