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
package com.restfb.types.webhook;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * root object to fetch information provided by a webhook call
 */
@ToString
public class WebhookObject {

  /**
   * top object that receives the changes, possible values are {@code page}, {@code permissions} and {@code user}
   */
  @Facebook
  @Getter
  @Setter
  private String object;

  @Facebook("entry")
  @Getter
  @Setter
  private List<WebhookEntry> entryList = new ArrayList<>();

  public boolean isAdAccount() {
    return "ad_account".equals(object);
  }

  public boolean isApplication() {
    return "application".equals(object);
  }

  public boolean isCertificateTransparency() {
    return "certificate_transparency".equals(object);
  }

  public boolean isGroup() {
    return "group".equals(object);
  }

  public boolean isInstagram() {
    return "instagram".equals(object);
  }

  public boolean isInstantWorkflow() {
    return "instant_workflow".equals(object);
  }

  public boolean isPage() {
    return "page".equals(object);
  }

  public boolean isPermissions() {
    return "permissions".equals(object);
  }

  public boolean isUser() {
    return "user".equals(object);
  }

  public boolean isWorkplaceSecurity() {
    return "workplace_security".equals(object);
  }

  public boolean isWhatsAppBusinessAccount() { return "whatsapp_business_account".equals(object); }

}
