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

  @Override
  public void accountLinking(final AccountLinkingItem item, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  @Override
  public void appRoles(final AppRoles appRoles, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  @Override
  public void checkoutUpdate(final CheckoutUpdateItem checkoutUpdate, MessagingParticipant recipient,
      MessagingParticipant sender, Date timestamp) {}

  @Override
  public void delivery(final DeliveryItem delivery, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  @Override
  public void message(final MessageItem message, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  @Override
  public void optin(final OptinItem optin, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  @Override
  public void passThreadControl(final PassThreadControlItem passThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender, Date timestamp) {}

  @Override
  public void payment(final PaymentItem payment, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  @Override
  public void policyEnforcement(final PolicyEnforcementItem policyEnforcement, MessagingParticipant recipient,
      MessagingParticipant sender, Date timestamp) {}

  @Override
  public void postback(final PostbackItem postback, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  @Override
  public void reaction(final MessageReaction reaction, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  @Override
  public void read(final ReadItem read, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp) {}

  @Override
  public void referral(final ReferralItem referral, MessagingParticipant recipient, MessagingParticipant sender,
      Date timestamp) {}

  @Override
  public void requestThreadControl(final RequestThreadControlItem requestThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender, Date timestamp) {}

  @Override
  public void takeThreadControl(final TakeThreadControlItem takeThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender, Date timestamp) {}
}
