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
 * Represents the
 * <a href="https://developers.facebook.com/docs/messenger-platform/reference/messenger-profile-api">Messenger
 * Profile</a>
 */
public class MessengerProfileProperties extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook("account_linking_url")
  private String accountLinkingUrl;

  @Getter
  @Setter
  @Facebook("persistent_menu")
  private PersistentMenu persistentMenu;

  @Getter
  @Setter
  @Facebook("get_started")
  private CallToAction getStarted;

  @Getter
  @Setter
  @Facebook("greeting")
  private List<Greeting> greeting = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("whitelisted_domains")
  private List<String> whitelistedDomains = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("payment_settings")
  private PaymentSettings paymentSettings;

  @Getter
  @Setter
  @Facebook("target_audience")
  private TargetAudience targetAudience;

  @Getter
  @Setter
  @Facebook("home_url")
  private HomeUrl homeUrl;
}
