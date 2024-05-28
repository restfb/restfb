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
package com.restfb.types.webhook.messaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.json.JsonObject;
import com.restfb.json.JsonValue;

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
  @Facebook("message_edit")
  private MessageEditItem messageEdit;

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

  @Getter
  @Setter
  @Facebook("policy-enforcement")
  private PolicyEnforcementItem policyEnforcement;

  @Getter
  @Setter
  @Facebook("pass_thread_control")
  private PassThreadControlItem passThreadControl;

  @Getter
  @Setter
  @Facebook("take_thread_control")
  private TakeThreadControlItem takeThreadControl;

  @Getter
  @Setter
  @Facebook("request_thread_control")
  private RequestThreadControlItem requestThreadControl;

  @Getter
  @Setter
  private AppRoles appRoles;

  @Getter
  @Setter
  @Facebook("prior_message")
  private PriorMessage priorMessage;

  @Getter
  @Setter
  @Facebook
  private MessageReaction reaction;

  @Facebook("app_roles")
  private JsonObject rawAppRoles;

  /**
   * generic access to the inner item.
   * 
   * depending on the inner elements the corresponding element is returned. So you can get an {@link OptinItem},
   * {@link PostbackItem}, {@link DeliveryItem}, {@link AccountLinkingItem} or {@link MessageItem}
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

    if (policyEnforcement != null) {
      return policyEnforcement;
    }

    if (passThreadControl != null) {
      return passThreadControl;
    }

    if (takeThreadControl != null) {
      return takeThreadControl;
    }

    if (requestThreadControl != null) {
      return requestThreadControl;
    }

    if (appRoles != null) {
      return appRoles;
    }

    if (reaction != null) {
      return reaction;
    }

    return null;
  }

  public boolean isDelivery() {
    return getItem() instanceof DeliveryItem;
  }

  public boolean isRead() {
    return getItem() instanceof ReadItem;
  }

  public boolean isMessage() {
    return getItem() instanceof MessageItem;
  }

  public boolean isPostback() {
    return getItem() instanceof PostbackItem;
  }

  public boolean isOptin() {
    return getItem() instanceof OptinItem;
  }

  public boolean isAccountLinking() {
    return getItem() instanceof AccountLinkingItem;
  }

  public boolean isCheckoutUpdate() {
    return getItem() instanceof CheckoutUpdateItem;
  }

  public boolean isPayment() {
    return getItem() instanceof PaymentItem;
  }

  public boolean isReferral() {
    return getItem() instanceof ReferralItem;
  }

  public boolean isPolicyEnforcement() {
    return getItem() instanceof PolicyEnforcementItem;
  }

  public boolean isPassThreadControl() {
    return getItem() instanceof PassThreadControlItem;
  }

  public boolean isTakeThreadControl() {
    return getItem() instanceof TakeThreadControlItem;
  }

  public boolean isRequestThreadControl() {
    return getItem() instanceof RequestThreadControlItem;
  }

  public boolean isAppRoles() {
    return getItem() instanceof AppRoles;
  }

  public boolean hasPriorMessage() {
    return priorMessage != null;
  }

  public boolean isReaction() {
    return getItem() instanceof MessageReaction;
  }

  @JsonMapper.JsonMappingCompleted
  private void convertDate() {
    if (rawTimestamp != null) {
      timestamp = new Date(rawTimestamp);
    }
  }

  @JsonMapper.JsonMappingCompleted
  private void convertAppRoles() {
    if (rawAppRoles != null) {
      AppRoles appRoles = new AppRoles();
      for (String appId : rawAppRoles.names()) {
        List<JsonValue> rawRoles = rawAppRoles.get(appId).asArray().values();
        List<String> roles = new ArrayList<>();
        for (JsonValue rawValue : rawRoles) {
          roles.add(rawValue.asString());
        }
        appRoles.addRoles(appId, roles);
      }
      this.appRoles = appRoles;
    }
  }
}
