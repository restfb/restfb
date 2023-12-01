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
package com.restfb.types.whatsapp;

import com.restfb.Facebook;
import com.restfb.types.NamedFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/graph-api/reference/whats-app-business-account/">WhatsApp Business
 * Account type </a>
 */
public class WhatsAppBusinessAccount extends NamedFacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * Status from account review process
   */
  @Getter
  @Setter
  @Facebook("account_review_status")
  private String accountReviewStatus;

  /**
   * Analytics data of the WhatsApp Business Account
   */
  @Getter
  @Setter
  @Facebook
  private WABAAnalytics analytics;

  /**
   * The currency in which the payment transactions for the WhatsApp Business Account will be processed
   */
  @Getter
  @Setter
  @Facebook
  private String currency;

  /**
   * Whether or not the WhatsApp Business Account is eligible for sending notifications
   */
  @Getter
  @Setter
  @Facebook("eligible_for_sending_notifications")
  private Boolean eligibleForSendingNotifications;

  /**
   * The reason why the WhatsApp Business Account is ineligible for delivery This field will be null when the
   * <code>eligible_for_sending_notifications</code> field is false
   */
  @Getter
  @Setter
  @Facebook("ineligible_for_sending_notifications_reason")
  private String ineligibleForSendingNotificationsReason;

  /**
   * Namespace string for the message templates that belong to the WhatsApp Business Account
   */
  @Getter
  @Setter
  @Facebook("message_template_namespace")
  private String messageTemplateNamespace;

  /**
   * The "on behalf of" information for the WhatsApp Business Account
   */
  @Getter
  @Setter
  @Facebook("on_behalf_of_business_info")
  private WABAOnBehalfOfComputedInfo onBehalfOfBusinessInfo;

  /**
   * Primary funding ID for the WhatsApp Business Account paid service
   */
  @Getter
  @Setter
  @Facebook("primary_funding_id")
  private String primaryFundingId;

  /**
   * The purchase order number supplied by the business for payment management purposes
   */
  @Getter
  @Setter
  @Facebook("purchase_order_number")
  private String purchaseOrderNumber;

  /**
   * Status of the WhatsApp Business Account.
   *
   * Default: ACTIVE
   */
  @Getter
  @Setter
  @Facebook
  private String status;

  /**
   * The timezone of the WhatsApp Business Account
   */
  @Getter
  @Setter
  @Facebook("timezone_id")
  private String timezoneId;


}
