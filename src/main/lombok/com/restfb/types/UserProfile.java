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
package com.restfb.types;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Respresents the User Profile object as defined
 * <a href="https://developers.facebook.com/docs/messenger-platform/identity/user-profile/">here</a>
 */
public class UserProfile extends NamedFacebookType {

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
  @Facebook("profile_pic")
  private String profilePic;

  @Getter
  @Setter
  @Facebook
  private String locale;

  @Getter
  @Setter
  @Facebook
  private String timezone;

  @Getter
  @Setter
  @Facebook
  private String gender;

  @Getter
  @Setter
  @Facebook("is_payment_enabled")
  private Boolean isPaymentEnabled;

  @Getter
  @Setter
  @Facebook("last_ad_referral")
  private LastAdReferral lastAdReferral;

  public static class LastAdReferral extends AbstractFacebookType {

    @Getter
    @Setter
    @Facebook
    private String source;

    @Getter
    @Setter
    @Facebook
    private String type;

    @Getter
    @Setter
    @Facebook("ad_id")
    private String adId;
  }
}
