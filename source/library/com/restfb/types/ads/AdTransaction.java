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
package com.restfb.types.ads;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/transaction/">Ad transaction
 * type</a>
 */
public class AdTransaction extends BaseAdsObject {

  /**
   * Time at which the transaction was created
   */
  @Getter
  @Setter
  @Facebook
  private Integer time;

  /**
   * ID of the source Ad Account
   */
  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  /**
   * ID for the Brazilian invoice (if transaction was made in Brazil) else 0
   */
  @Getter
  @Setter
  @Facebook("fatura_id")
  private Integer faturaId;

  /**
   * Type of charge involved in the transaction
   */
  @Getter
  @Setter
  @Facebook("charge_type")
  private String chargeType;

  /**
   * Current status of the transaction
   */
  @Getter
  @Setter
  @Facebook
  private String status;

  /**
   * Start time of the transaction's billing period
   */
  @Getter
  @Setter
  @Facebook("billing_start_time")
  private Integer billingStartTime;

  /**
   * End time of the transaction's billing period
   */
  @Getter
  @Setter
  @Facebook("billing_end_time")
  private Integer billingEndTime;

  /**
   * Method used to pay for the transaction
   */
  @Getter
  @Setter
  @Facebook("payment_option")
  private String paymentOption;

  /**
   * Monetary amount given to the app for this transaction
   */
  @Getter
  @Setter
  @Facebook("app_amount")
  private Integer appAmount;

  /**
   * The type of ads included in this transaction
   */
  @Getter
  @Setter
  @Facebook("product_type")
  private String productType;

  /**
   * Monetary amount charged to the user for this transaction
   */
  @Getter
  @Setter
  @Facebook("provider_amount")
  private Integer providerAmount;
}
