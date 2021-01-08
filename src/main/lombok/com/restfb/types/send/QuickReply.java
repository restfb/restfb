/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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
package com.restfb.types.send;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/messenger-platform/send-api-reference/quick-replies">Quick Reply</a>
 */
public class QuickReply extends AbstractFacebookType {

  @Getter
  @Facebook("content_type")
  private String contentType;

  @Getter
  @Facebook
  private String title;

  @Getter
  @Facebook
  private String payload;

  @Getter
  @Setter
  @Facebook("image_url")
  private String imageUrl;

  /**
   *
   * @deprecated use constructor without contenttype instead. If using content type 'text' use
   *             {@link QuickReply#QuickReply(String,String)} for 'location' use {@link QuickReply#QuickReply()}
   */
  @Deprecated
  public QuickReply(String contentType, String title, String payload) {
    this(title, payload);
    this.contentType = contentType;
  }

  public QuickReply(String title, String payload) {
    this.contentType = "text";
    this.title = title;
    this.payload = payload;
  }

  public QuickReply(QuickReplyType quickReplyType) {
    this.contentType = quickReplyType.getContentType();
  }

  public enum QuickReplyType {
    USER_EMAIL, USER_PHONE_NUMBER;

    public String getContentType() {
      return name().toLowerCase();
    }
  }
}
