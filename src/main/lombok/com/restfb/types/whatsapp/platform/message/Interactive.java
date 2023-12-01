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
package com.restfb.types.whatsapp.platform.message;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class Interactive extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook("list_reply")
  private Reply listReply;

  @Getter
  @Setter
  @Facebook("button_reply")
  private Reply buttonReply;

  @Getter
  @Setter
  @Facebook
  private Type type;

  public Reply getReply() {
    if (buttonReply != null) {
      return buttonReply;
    }

    if (listReply != null) {
      return listReply;
    }

    return null;
  }

  public boolean isButtonReply() {
    return buttonReply != null;
  }

  public boolean isListReply() {
    return listReply != null;
  }

  public static class Reply extends AbstractFacebookType {

    @Getter
    @Setter
    @Facebook
    private String id;

    @Getter
    @Setter
    @Facebook
    private String title;

    @Getter
    @Setter
    @Facebook
    private String description;
  }

  public enum Type {
    list_reply, button_reply
  }
}
