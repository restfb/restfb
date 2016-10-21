/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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
package com.restfb.types.webhook.messaging;

import com.restfb.Facebook;
import com.restfb.types.webhook.messaging.payment.Amount;
import com.restfb.types.webhook.messaging.payment.PaymentCredential;
import com.restfb.types.webhook.messaging.payment.ReuqestedUserInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/messenger-platform/webhook-reference/payment">Payment</a> Callback
 */
@ToString
public class PaymentItem implements InnerMessagingItem {

  /**
   * Metadata defined in the Buy Button.
   */
  @Getter
  @Setter
  @Facebook
  private String payload;

  /**
   * Information that was requested from the user by the Buy Button.
   */
  @Getter
  @Setter
  @Facebook("requested_user_info")
  private ReuqestedUserInfo requestedUserInfo;

  /**
   * Payment credentials.
   */
  @Getter
  @Setter
  @Facebook("payment_credential")
  private PaymentCredential paymentCredential;

  /**
   * Total amount of transaction.
   */
  @Getter
  @Setter
  @Facebook
  private Amount amount;

}
