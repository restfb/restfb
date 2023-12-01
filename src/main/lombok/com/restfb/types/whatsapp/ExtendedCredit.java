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
import com.restfb.types.FacebookType;
import com.restfb.types.ads.Business;

import lombok.Getter;
import lombok.Setter;

public class ExtendedCredit extends FacebookType {

  /**
   * The total amount of credit that has been granted to other businesses.
   *
   * Going forward, credentials are measured in USD at the top level, and attachable
   * to ad accounts of multiple currencies.
   */
  @Getter
  @Setter
  @Facebook("allocated_amount")
  private CurrencyAmount allocatedAmount;

  /**
   * The total amount spent.
   *
   * This is calculated by adding parent ad account and all child ad accounts.
   * Going forward, credentials are measured in USD at the top level, and attachable
   * to ad accounts of multiple currencies.
   */
  @Getter
  @Setter
  @Facebook
  private CurrencyAmount balance;

  /**
   * The credit available to this business
   */
  @Getter
  @Setter
  @Facebook("credit_available")
  private CurrencyAmount creditAvailable;

  /**
   * The type of extended credit used.
   *
   * The most common are ADS_BUSINESS and ADS_SEQUENTIAL
   */
  @Getter
  @Setter
  @Facebook("credit_type")
  private String creditType;

  /**
   * Returns true if credit owner removed access for previously shared credit line
   */
  @Getter
  @Setter
  @Facebook("is_access_revoked")
  private Boolean isAccessRevoked;

  /**
   * Whether this credential is using the fully automated experience
   */
  @Getter
  @Setter
  @Facebook("is_automated_experience")
  private Boolean isAutomatedExperience;

  /**
   * The legal entity name of the owner of a line
   */
  @Getter
  @Setter
  @Facebook("legal_entity_name")
  private String legalEntityName;

  /**
   * When this credit is chosen as the payment method,
   * the business name of the liable_party
   */
  @Getter
  @Setter
  @Facebook("liable_biz_name")
  private String liableBizName;

  /**
   * The amount of credit available to a specific business.
   *
   * Going forward, credentials are measured in USD at the top level, and
   * attachable to ad accounts of multiple currencies.
   */
  @Getter
  @Setter
  @Facebook("max_balance")
  private CurrencyAmount maxBalance;

  /**
   * The raw credit limit for an entire business.
   *
   * Going forward, credentials are measured in USD at the top level, and
   * attachable to ad accounts of multiple currencies.
   */
  @Getter
  @Setter
  @Facebook("online_max_balance")
  private CurrencyAmount onlineMaxBalance;

  /**
   * The business account responsible for extended credit payment
   */
  @Getter
  @Setter
  @Facebook("owner_business")
  private Business ownerBusiness;

  /**
   * The name of the business account responsible for extended credit payment
   */
  @Getter
  @Setter
  @Facebook("owner_business_name")
  private String ownerBusinessName;

  /**
   * The name of the business that granted the credit line. For ADS_SEQUENTIAL credit types
   */
  @Getter
  @Setter
  @Facebook("partition_from")
  private String partitionFrom;

  /**
   * When this credit is chosen as the payment method, the business name of the bill_to_party
   */
  @Getter
  @Setter
  @Facebook("send_bill_to_biz_name")
  private String sendBillToBizNname;
}
