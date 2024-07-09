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
package com.restfb.types.whatsapp.platform.send;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;
import com.restfb.types.whatsapp.platform.send.contact.*;
import com.restfb.util.DateUtils;

import lombok.Setter;

public class Contact extends AbstractFacebookType {

  @Facebook
  private List<Address> addresses;

  @Facebook
  private String birthday;

  @Facebook
  private List<Email> emails;

  @Setter
  @Facebook
  private Name name;

  @Setter
  @Facebook
  private Organisation org;

  @Facebook
  private List<Phone> phones;

  @Facebook
  private List<Url> urls;

  public void setBirthday(Date birthday) {
    this.birthday = DateUtils.toShortFormatFromDate(birthday);
  }

  public void addAddress(Address address) {
    if (addresses == null) {
      this.addresses = new ArrayList<>();
    }

    this.addresses.add(address);
  }

  public void addEmail(Email email) {
    if (emails == null) {
      this.emails = new ArrayList<>();
    }

    this.emails.add(email);
  }

  public void addPhone(Phone phone) {
    if (phones == null) {
      this.phones = new ArrayList<>();
    }

    this.phones.add(phone);
  }

  public void addUrl(Url url) {
    if (urls == null) {
      this.urls = new ArrayList<>();
    }

    this.urls.add(url);
  }

}
