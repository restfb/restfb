/*
 * Copyright (c) 2010-2011 Mark Allen.
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

import java.util.Date;

import com.restfb.Facebook;

/**
 * Represents the <a
 * href="http://developers.facebook.com/docs/reference/api/status">Status
 * Message Graph API type</a>.
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

  private static final long serialVersionUID = 1L;

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
}