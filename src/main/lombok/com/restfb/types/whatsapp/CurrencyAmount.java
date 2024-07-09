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
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class CurrencyAmount extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;
  /**
   * Value of the amount in whole currency units (USD 123.45 = "123.45")
   */
  @Getter
  @Setter
  @Facebook
  private String amount;

  /**
   * Value of the amount in hundredths, i.e. (USD 123.45 = "12345", JYP 1 = "100")
   */
  @Getter
  @Setter
  @Facebook("amount_in_hundredths")
  private String amountInHundredths;

  /**
   * Currency in which amount is given
   */
  @Getter
  @Setter
  @Facebook
  private String currency;

  /**
   * Value of the amount in cents (USD 123.45 = "12345")
   */
  @Getter
  @Setter
  @Facebook("offsetted_amount")
  private String offsettedAmount;
}
