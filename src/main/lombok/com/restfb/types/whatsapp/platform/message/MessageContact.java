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

import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Contact type used in the Message object
 */
public class MessageContact extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook
  private ContactName name;

  @Getter
  @Setter
  @Facebook
  private List<ContactPhone> phones;

  public static class ContactName extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook("first_name")
    private String firstName;

    @Getter
    @Setter
    @Facebook("last_name")
    private String lastName;

    @Getter
    @Setter
    @Facebook("formatted_name")
    private String formattedName;
  }

  public static class ContactPhone extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook("phone")
    private String phone;

    @Getter
    @Setter
    @Facebook("type")
    private String type;

    @Getter
    @Setter
    @Facebook("wa_id")
    private String waId;
  }
}
