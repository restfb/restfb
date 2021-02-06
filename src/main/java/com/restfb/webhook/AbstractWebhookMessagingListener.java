/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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
package com.restfb.webhook;

import java.util.Date;

import com.restfb.types.webhook.messaging.*;

/**
 * abstract class as base for custom webhook messaging listener, with this abstract class it is possible to implement
 * only a subset of the need methods and ignore the other ones.
 */
public abstract class AbstractWebhookMessagingListener implements WebhookMessagingListener {

  /**
   * @deprecated use {@link #accountLinking(AccountLinkingItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param item
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void accountLinking(final AccountLinkingItem item, MessagingParticipant recipient,
      MessagingParticipant sender) {}

  @Override
  public void accountLinking(final AccountLinkingItem item, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  /**
   * @deprecated use {@link #appRoles(AppRoles, MessagingParticipant, MessagingParticipant, Date)}
   * @param appRoles
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void appRoles(final AppRoles appRoles, MessagingParticipant recipient, MessagingParticipant sender) {}

  @Override
  public void appRoles(final AppRoles appRoles, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  /**
   * @deprecated use {@link #checkoutUpdate(CheckoutUpdateItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param checkoutUpdate
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void checkoutUpdate(final CheckoutUpdateItem checkoutUpdate, MessagingParticipant recipient,
      MessagingParticipant sender) {}

  @Override
  public void checkoutUpdate(final CheckoutUpdateItem checkoutUpdate, MessagingParticipant recipient,
      MessagingParticipant sender, Date timestamp) {}

  /**
   * @deprecated use {@link #delivery(DeliveryItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param delivery
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void delivery(final DeliveryItem delivery, MessagingParticipant recipient, MessagingParticipant sender) {}

  @Override
  public void delivery(final DeliveryItem delivery, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  /**
   * @deprecated use {@link #message(MessageItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param message
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void message(final MessageItem message, MessagingParticipant recipient, MessagingParticipant sender) {}

  @Override
  public void message(final MessageItem message, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  /**
   * @deprecated use {@link #optin(OptinItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param optin
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void optin(final OptinItem optin, MessagingParticipant recipient, MessagingParticipant sender) {}

  @Override
  public void optin(final OptinItem optin, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  /**
   * @deprecated use {@link #passThreadControl(PassThreadControlItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param passThreadControl
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void passThreadControl(final PassThreadControlItem passThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender) {}

  @Override
  public void passThreadControl(final PassThreadControlItem passThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender, Date timestamp) {}

  /**
   * @deprecated use {@link #payment(PaymentItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param payment
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void payment(final PaymentItem payment, MessagingParticipant recipient, MessagingParticipant sender) {}

  @Override
  public void payment(final PaymentItem payment, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  /**
   * @deprecated use {@link #policyEnforcement(PolicyEnforcementItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param policyEnforcement
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void policyEnforcement(final PolicyEnforcementItem policyEnforcement, MessagingParticipant recipient,
      MessagingParticipant sender) {}

  @Override
  public void policyEnforcement(final PolicyEnforcementItem policyEnforcement, MessagingParticipant recipient,
      MessagingParticipant sender, Date timestamp) {}

  /**
   * @deprecated use {@link #postback(PostbackItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param postback
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void postback(final PostbackItem postback, MessagingParticipant recipient, MessagingParticipant sender) {}

  @Override
  public void postback(final PostbackItem postback, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  /**
   * @deprecated use {@link #reaction(MessageReaction, MessagingParticipant, MessagingParticipant, Date)}
   * @param reaction
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void reaction(final MessageReaction reaction, MessagingParticipant recipient, MessagingParticipant sender) {}

  @Override
  public void reaction(final MessageReaction reaction, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  /**
   * @deprecated use {@link #read(ReadItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param read
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void read(final ReadItem read, MessagingParticipant recipient, MessagingParticipant sender) {}

  @Override
  public void read(final ReadItem read, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp) {}

  /**
   * @deprecated use {@link #referral(ReferralItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param referral
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void referral(final ReferralItem referral, MessagingParticipant recipient, MessagingParticipant sender) {}

  @Override
  public void referral(final ReferralItem referral, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  /**
   * @deprecated use
   *             {@link #requestThreadControl(RequestThreadControlItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param requestThreadControl
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void requestThreadControl(final RequestThreadControlItem requestThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender) {}

  @Override
  public void requestThreadControl(final RequestThreadControlItem requestThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender, Date timestamp) {}

  /**
   * @deprecated use {@link #takeThreadControl(TakeThreadControlItem, MessagingParticipant, MessagingParticipant, Date)}
   * @param takeThreadControl
   * @param recipient
   * @param sender
   */
  @Deprecated
  @Override
  public void takeThreadControl(final TakeThreadControlItem takeThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender) {}

  @Override
  public void takeThreadControl(final TakeThreadControlItem takeThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender, Date timestamp) {}
}
