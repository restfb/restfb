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
package com.restfb.types.send;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href=
 * "https://developers.facebook.com/docs/messenger-platform/reference/messenger-profile-api/payment-settings">Messenger
 * Profile Payment Settings</a>
 */
public class PaymentSettings extends AbstractFacebookType {

  /**
   * The URL of the privacy policy for your bot. Required for buy button payments.
   */
  @Getter
  @Setter
  @Facebook("privacy_url")
  private String privacyUrl;

  /**
   * Your public key. Used to encrypt all webview payments, and buy button implementations that use tokenized payments.
   */
  @Getter
  @Setter
  @Facebook("public_key")
  private String publicKey;

  /**
   * A list of IDs for people that will test payments in your bot. These people will send a mock payment when they tap
   * the buy button.
   */
  @Getter
  @Setter
  @Facebook
  private List<String> testers = new ArrayList<>();
}
