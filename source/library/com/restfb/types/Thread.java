/*
 * Copyright (c) 2010-2014 Norbert Bartels.
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

import com.restfb.Facebook;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static java.util.Collections.unmodifiableList;
import java.util.Date;
import java.util.List;
import lombok.Getter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/thread">Thread Graph API type</a>.
 * 
 * @since 1.7.0
 */
public class Thread extends FacebookType {

  @Facebook
  private List<NamedFacebookType> to;

  @Facebook
  private List<Message> comments;

  /**
   * The amount of messages that are unread by the session profile.
   * 
   * @return the amount of messages that are unread
   */
  @Getter
  @Facebook
  private Integer unread;

  /**
   * The amount of messages that are unseen by the session profile.
   * 
   * @return the amount of messages that are unseen
   */
  @Getter
  @Facebook
  private Integer unseen;

  @Facebook("updated_time")
  private String updatedTime;

  /**
   * The time of the last comment on this post.
   * 
   * @return The time of the last comment on this post.
   */
  public Date getUpdatedTime() {
    return toDateFromLongFormat(updatedTime);
  }

  /**
   * The messages in this thread.
   * 
   * @return the messages in the thread
   */
  public List<Message> getComments() {
    return unmodifiableList(comments);
  }

  /**
   * A list of the Thread subscriber
   * 
   * @return A list of the thread subsscriber
   */
  public List<NamedFacebookType> getTo() {
    return unmodifiableList(to);
  }

}