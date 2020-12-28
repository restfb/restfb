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

  void accountLinking(final AccountLinkingItem item, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  void appRoles(final AppRoles appRoles, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  void checkoutUpdate(final CheckoutUpdateItem checkoutUpdate, MessagingParticipant recipient,
                      MessagingParticipant sender, Date timestamp);

  void delivery(final DeliveryItem delivery, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  void message(final MessageItem message, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  void optin(final OptinItem optin, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  void passThreadControl(final PassThreadControlItem passThreadControl, MessagingParticipant recipient,
                         MessagingParticipant sender, Date timestamp);

  void payment(final PaymentItem payment, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  void policyEnforcement(final PolicyEnforcementItem policyEnforcement, MessagingParticipant recipient,
                         MessagingParticipant sender, Date timestamp);

  void postback(final PostbackItem postback, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  void reaction(final MessageReaction reaction, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  void read(final ReadItem read, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  void referral(final ReferralItem referral, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp);

  void requestThreadControl(final RequestThreadControlItem requestThreadControl, MessagingParticipant recipient,
                            MessagingParticipant sender, Date timestamp);

  void takeThreadControl(final TakeThreadControlItem takeThreadControl, MessagingParticipant recipient,
                         MessagingParticipant sender, Date timestamp);
}
