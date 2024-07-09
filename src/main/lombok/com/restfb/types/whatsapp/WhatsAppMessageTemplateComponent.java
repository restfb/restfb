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
package com.restfb.types.whatsapp;

import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class WhatsAppMessageTemplateComponent extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook
  private Type type;

  @Getter
  @Setter
  @Facebook
  private Format format;

  @Getter
  @Setter
  @Facebook
  private String text;

  @Getter
  @Setter
  @Facebook
  private List<Button> buttons;

  public enum Type {
    HEADER, BODY, FOOTER, BUTTONS
  }

  public enum Format {
    TEXT, IMAGE, DOCUMENT, VIDEO, LOCATION
  }

  public enum ButtonType {
    QUICK_REPLY, URL, PHONE_NUMBER
  }

  public static class Button {

    @Getter
    @Setter
    @Facebook
    private ButtonType type;

    @Getter
    @Setter
    @Facebook
    private String text;

    @Getter
    @Setter
    @Facebook
    private String url;

    @Getter
    @Setter
    @Facebook("phone_number")
    private String phoneNumber;
  }
}
