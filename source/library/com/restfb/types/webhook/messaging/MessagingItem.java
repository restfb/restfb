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
import com.restfb.JsonMapper;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MessagingItem {

  @Getter
  @Setter
  @Facebook
  private MessagingParticipant sender;

  @Getter
  @Setter
  @Facebook
  private MessagingParticipant recipient;

  @Facebook("timestamp")
  private Long rawTimestamp;

  @Getter
  @Setter
  private Date timestamp;

  @Getter
  @Setter
  @Facebook
  private DeliveryItem delivery;

  @Getter
  @Setter
  @Facebook
  private ReadItem read;

  @Getter
  @Setter
  @Facebook
  private MessageItem message;

  @Getter
  @Setter
  @Facebook
  private PostbackItem postback;

  @Getter
  @Setter
  @Facebook
  private OptinItem optin;

  @Getter
  @Setter
  @Facebook("account_linking")
  private AccountLinkingItem accountLinking;

  @Getter
  @Setter
  @Facebook("checkout_update")
  private CheckoutUpdateItem checkoutUpdate;

  @Getter
  @Setter
  @Facebook
  private PaymentItem payment;

  @Getter
  @Setter
  @Facebook
  private ReferralItem referral;

  /**
   * generic access to the inner item.
   * 
   * depending on the inner elements the corresponding element is returned. So you can get an {@see OptinItem},
   * {@see PostbackItem}, {@see DeliveryItem}, {@see AccountLinkingItem} or {@see MessageItem}
   * 
   * @return the inner item.
   */
  public InnerMessagingItem getItem() {
    if (optin != null) {
      return optin;
    }

    if (postback != null) {
      return postback;
    }

    if (delivery != null) {
      return delivery;
    }

    if (read != null) {
      return read;
    }

    if (accountLinking != null) {
      return accountLinking;
    }

    if (message != null) {
      return message;
    }

    if (checkoutUpdate != null) {
      return checkoutUpdate;
    }

    if (payment != null) {
      return payment;
    }

    if (referral != null) {
      return referral;
    }

    return null;
  }

  @JsonMapper.JsonMappingCompleted
  private void convertDate() {
    if (rawTimestamp != null) {
      timestamp = new Date(rawTimestamp);
    }
  }
}
