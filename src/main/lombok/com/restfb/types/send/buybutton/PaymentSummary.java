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
package com.restfb.types.send.buybutton;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class PaymentSummary extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook
  private String currency;

  @Getter
  @Setter
  @Facebook("payment_type")
  private PaymentTypeEnum paymentType;

  @Getter
  @Setter
  @Facebook("is_test_payment")
  private boolean testPayment = false;

  @Getter
  @Setter
  @Facebook("merchant_name")
  private String merchantName;

  @Getter
  @Facebook("requested_user_info")
  private List<RequestedUserInfoEnum> requestedUserInfo;

  @Getter
  @Facebook("price_list")
  private List<PriceListItem> priceList;

  public boolean addRequestedUserInfo(RequestedUserInfoEnum requestedUserInfoItem) {
    if (requestedUserInfo == null) {
      requestedUserInfo = new ArrayList<>();
    }

    return requestedUserInfo.add(requestedUserInfoItem);
  }

  public boolean addPriceListItem(PriceListItem item) {
    if (priceList == null) {
      priceList = new ArrayList<>();
    }

    return priceList.add(item);
  }
}
