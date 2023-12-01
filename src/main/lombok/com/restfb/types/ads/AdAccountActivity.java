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
package com.restfb.types.ads;

import com.restfb.Facebook;
import com.restfb.json.JsonObject;

import lombok.Getter;
import lombok.Setter;

public class AdAccountActivity extends BaseAdsObject {

  @Getter
  @Setter
  @Facebook("billing_address_new")
  private String billingAddressNew;

  @Getter
  @Setter
  @Facebook("billing_address_old")
  private String billingAddressOld;

  @Getter
  @Setter
  @Facebook("created_by")
  private String createdBy;

  @Getter
  @Setter
  @Facebook("created_time")
  private String createdTime;

  @Getter
  @Setter
  @Facebook("credit_new")
  private JsonObject creditNew;

  @Getter
  @Setter
  @Facebook("credit_old")
  private JsonObject creditOld;

  @Getter
  @Setter
  @Facebook("credit_status_new")
  private String creditStatusNew;

  @Getter
  @Setter
  @Facebook("credit_status_old")
  private String creditStatusOld;

  @Getter
  @Setter
  @Facebook("currency_new")
  private String currencyNew;

  @Getter
  @Setter
  @Facebook("currency_old")
  private String currencyOld;

  @Getter
  @Setter
  @Facebook("daily_spend_limit_new")
  private JsonObject dailySpendLimitNew;

  @Getter
  @Setter
  @Facebook("daily_spend_limit_old")
  private JsonObject dailySpendLimitOld;

  @Getter
  @Setter
  @Facebook("event_time")
  private String eventTime;

  @Getter
  @Setter
  @Facebook("event_type")
  private String eventType;

  @Getter
  @Setter
  @Facebook("funding_id_new")
  private String fundingIdNew;

  @Getter
  @Setter
  @Facebook("funding_id_old")
  private String fundingIdOld;

  @Getter
  @Setter
  @Facebook("grace_period_time_new")
  private Long gracePeriodTimeNew;

  @Getter
  @Setter
  @Facebook("grace_period_time_old")
  private Long gracePeriodTimeOld;

  @Getter
  @Setter
  @Facebook("manager_id_new")
  private String managerIdNew;

  @Getter
  @Setter
  @Facebook("manager_id_old")
  private String managerIdOld;

  @Getter
  @Setter
  @Facebook("name_new")
  private String nameNew;

  @Getter
  @Setter
  @Facebook("name_old")
  private String nameOld;

  @Getter
  @Setter
  @Facebook("spend_cap_new")
  private JsonObject spendCapNew;

  @Getter
  @Setter
  @Facebook("spend_cap_old")
  private JsonObject spendCapOld;

  @Getter
  @Setter
  @Facebook("status_new")
  private String statusNew;

  @Getter
  @Setter
  @Facebook("status_old")
  private String statusOld;

  @Getter
  @Setter
  @Facebook("terms_new")
  private Long termsNew;

  @Getter
  @Setter
  @Facebook("terms_old")
  private Long termsOld;

  @Getter
  @Setter
  @Facebook("tier_new")
  private String tierNew;

  @Getter
  @Setter
  @Facebook("tier_old")
  private String tierOld;

  @Getter
  @Setter
  @Facebook("time_updated_new")
  private String timeUpdatedNew;

  @Getter
  @Setter
  @Facebook("time_updated_old")
  private String timeUpdatedOld;
}
