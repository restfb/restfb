/*
 * Copyright (c) 2010-2012 Mark Allen.
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

import java.util.Date;
import java.util.List;

import static com.restfb.util.DateUtils.toDateFromLongFormat;

/**
 * Represents the <a
 * href="http://developers.facebook.com/docs/reference/api/message/">Message
 * Graph API type</a>.
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author Felipe Kurkowski
 */
public class Message extends FacebookType {
  @Facebook("created_time")
  private String createdTime;

  @Facebook
  private NamedFacebookType from;

  @Facebook
  private List<NamedFacebookType> to;

  @Facebook
  private String message;

  private static final long serialVersionUID = 1L;

  /**
   * The time the message was initially created.
   *
   * @return The time the message was initially created.
   */
  public Date getCreatedTime() {
    return toDateFromLongFormat(createdTime);
  }

  /**
   * The sender of this message
   *
   * @return The sender of this message
   */
  public NamedFacebookType getFrom() {
    return from;
  }

  /**
   * A list of the message recipients
   *
   * @return A list of the message recipients
   */
  public List<NamedFacebookType> getTo() {
    return to;
  }

  /**
   * The text of the message
   *
   * @return The text of the message
   */
  public String getMessage() {
    return message;
  }
}
