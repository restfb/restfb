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

import com.restfb.types.webhook.messaging.*;

import java.util.Date;

/**
 * interface to provide methods a listener can implement to handle the different messaging items
 */
public interface WebhookMessagingListener {

  @Deprecated
  void accountLinking(final AccountLinkingItem item, MessagingParticipant recipient, MessagingParticipant sender);

  void accountLinking(final AccountLinkingItem item, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  @Deprecated
  void appRoles(final AppRoles appRoles, MessagingParticipant recipient, MessagingParticipant sender);

  void appRoles(final AppRoles appRoles, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  @Deprecated
  void checkoutUpdate(final CheckoutUpdateItem checkoutUpdate, MessagingParticipant recipient,
      MessagingParticipant sender);

  void checkoutUpdate(final CheckoutUpdateItem checkoutUpdate, MessagingParticipant recipient,
                      MessagingParticipant sender, Date timestamp);

  @Deprecated
  void delivery(final DeliveryItem delivery, MessagingParticipant recipient, MessagingParticipant sender);

  void delivery(final DeliveryItem delivery, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  @Deprecated
  void message(final MessageItem message, MessagingParticipant recipient, MessagingParticipant sender);

  void message(final MessageItem message, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  @Deprecated
  void optin(final OptinItem optin, MessagingParticipant recipient, MessagingParticipant sender);

  void optin(final OptinItem optin, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  @Deprecated
  void passThreadControl(final PassThreadControlItem passThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender);

  void passThreadControl(final PassThreadControlItem passThreadControl, MessagingParticipant recipient,
                         MessagingParticipant sender, Date timestamp);

  @Deprecated
  void payment(final PaymentItem payment, MessagingParticipant recipient, MessagingParticipant sender);

  void payment(final PaymentItem payment, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  @Deprecated
  void policyEnforcement(final PolicyEnforcementItem policyEnforcement, MessagingParticipant recipient,
      MessagingParticipant sender);

  void policyEnforcement(final PolicyEnforcementItem policyEnforcement, MessagingParticipant recipient,
                         MessagingParticipant sender, Date timestamp);

  @Deprecated
  void postback(final PostbackItem postback, MessagingParticipant recipient, MessagingParticipant sender);

  void postback(final PostbackItem postback, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  @Deprecated
  void reaction(final MessageReaction reaction, MessagingParticipant recipient, MessagingParticipant sender);

  void reaction(final MessageReaction reaction, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  @Deprecated
  void read(final ReadItem read, MessagingParticipant recipient, MessagingParticipant sender);

  void read(final ReadItem read, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  @Deprecated
  void referral(final ReferralItem referral, MessagingParticipant recipient, MessagingParticipant sender);

  void referral(final ReferralItem referral, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  @Deprecated
  void requestThreadControl(final RequestThreadControlItem requestThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender);

  void requestThreadControl(final RequestThreadControlItem requestThreadControl, MessagingParticipant recipient,
                            MessagingParticipant sender, Date timestamp);

  @Deprecated
  void takeThreadControl(final TakeThreadControlItem takeThreadControl, MessagingParticipant recipient,
      MessagingParticipant sender);

  void takeThreadControl(final TakeThreadControlItem takeThreadControl, MessagingParticipant recipient,
                         MessagingParticipant sender, Date timestamp);
}
