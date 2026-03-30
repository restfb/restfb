/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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
package com.restfb.types.webhook.whatsapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.whatsapp.platform.Metadata;
import com.restfb.types.whatsapp.platform.message.Contact;

import lombok.Getter;

public class UserIdUpdateValue extends AbstractWhatsappBaseChangeValue {

  @Getter
  @Facebook("messaging_product")
  private String messagingProduct;

  @Getter
  @Facebook
  private Metadata metadata;

  @Getter
  @Facebook
  private List<Contact> contacts = new ArrayList<>();

  @Getter
  @Facebook("user_id_update")
  private List<UserIdUpdateItem> userIdUpdate = new ArrayList<>();

  public static class UserIdUpdateItem {

    @Getter
    @Facebook("wa_id")
    private String waId;

    @Getter
    @Facebook
    private String detail;

    @Getter
    @Facebook("user_id")
    private ChangedId userId;

    @Getter
    @Facebook("parent_user_id")
    private ChangedId parentUserId;

    @Getter
    @Facebook
    private Date timestamp;
  }

  public static class ChangedId {

    @Getter
    @Facebook
    private String previous;

    @Getter
    @Facebook
    private String current;
  }
}
