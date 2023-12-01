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
package com.restfb.types.webhook.messaging.payment;

import java.io.Serializable;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PaymentCredential implements Serializable {

  /**
   * The "stripe" provider type.
   */
  public static final String STRIPE = "stripe";

  /**
   * The "paypal" provider type.
   */
  public static final String PAYPAL = "paypal";

  /**
   * The "token" provider type.
   */
  public static final String TOKEN = "token";

  /**
   * Payment provider type
   */
  @Getter
  @Setter
  @Facebook("provider_type")
  private String providerType;

  /**
   * Payment provider charge id
   */
  @Getter
  @Setter
  @Facebook("charge_id")
  private String chargeId;

  /**
   * convenience method to check if the provider type is stripe
   *
   * @return {@code true} if stripe, {@code false} if not stripe
   */
  public boolean isStripe() {
    return STRIPE.equals(providerType);
  }

  /**
   * convenience method to check if the provider type is paypal
   *
   * @return {@code true} if paypal, {@code false} if not paypal
   */
  public boolean isPaypal() {
    return PAYPAL.equals(providerType);
  }

  /**
   * convenience method to check if the provider type is token
   *
   * @return {@code true} if token, {@code false} if not token
   */
  public boolean isToken() {
    return TOKEN.equals(providerType);
  }
}
