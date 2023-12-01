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

import com.restfb.Facebook;
import com.restfb.types.FacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href=
 * "https://developers.facebook.com/docs/graph-api/reference/whats-app-business-account-to-number-current-status/">WhatsApp
 * Business Account To Number Current Status type </a>
 */
public class WhatsAppBusinessAccountToNumberCurrentStatus extends FacebookType {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook("account_mode")
  private String accountMode;

  @Getter
  @Setter
  @Facebook
  private String certificate;

  @Deprecated
  @Getter
  @Setter
  @Facebook("country_code")
  private String countryCode;

  @Deprecated
  @Getter
  @Setter
  @Facebook("country_dial_code")
  private String countryDialCode;

  @Getter
  @Setter
  @Facebook("display_phone_number")
  private String displayPhoneNumber;

  @Getter
  @Setter
  @Facebook("name_status")
  private String nameStatus;

  @Getter
  @Setter
  @Facebook("new_certificate")
  private String newCertificate;

  @Getter
  @Setter
  @Facebook("new_name_status")
  private String newNameStatus;

  @Getter
  @Setter
  @Facebook("quality_score")
  private WhatsAppPhoneQualityScoreShape qualityScore;

  @Deprecated
  @Getter
  @Setter
  @Facebook("quality_rating")
  private String qualityRating;

  @Getter
  @Setter
  @Facebook
  private String status;

  @Deprecated
  @Getter
  @Setter
  @Facebook("verified_name")
  private String verifiedName;
}
