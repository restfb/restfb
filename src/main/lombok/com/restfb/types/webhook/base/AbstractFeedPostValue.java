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
package com.restfb.types.webhook.base;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

abstract public class AbstractFeedPostValue extends BaseChangeValue {

  @Getter
  @Setter
  @Facebook("post_id")
  private String postId;

  @Setter
  @Facebook("sender_id")
  private String senderId;

  @Setter
  @Facebook("sender_name")
  private String senderName;

  @Facebook
  private From from;

  /**
   * returns the {@code sender_id}.
   *
   * Use {@see AbstractFeedPostValue#getFrom} instead, because with Graph API 2.11 sender_id is deprecated
   * 
   * @return the sender id
   */
  public String getSenderId() {
    if (from != null) {
      return from.getId();
    }
    return senderId;
  }

  /**
   * returns the {@code sender_name}.
   *
   * Use {@see AbstractFeedPostValue#getFrom} instead, because with Graph API 2.11 sender_name is deprecated
   *
   * @return the sender name
   */
  public String getSenderName() {
    if (from != null) {
      return from.getName();
    }
    return senderName;
  }

  /**
   * returns the sender (from) of the feed value
   * 
   * @return from of the feed value
   */
  public From getFrom() {
    if (from != null) {
      return from;
    } else {
      return new From(senderId, senderName);
    }

  }

}
