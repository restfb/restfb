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
package com.restfb.types.whatsapp.platform.status;

public enum CategoryType {

  @Deprecated
  business_initiated, //
  /**
   * Indicates a <a href="https://developers.facebook.com/docs/whatsapp/pricing#free-entry-point-conversations">free
   * entry point conversation</a>.
   */
  referral_conversion, //
  @Deprecated
  user_initiated, //
  /**
   * Indicates the conversation was opened by a business sending template categorized as AUTHENTICATION to the customer.
   * This applies any time it has been more than 24 hours since the last customer message.
   */
  authentication, //
  /**
   * Indicates the conversation was opened by a business sending template categorized as MARKETING to the customer. This
   * applies any time it has been more than 24 hours since the last customer message.
   */
  marketing, //
  /**
   * Indicates the conversation was opened by a business sending template categorized as UTILITY to the customer. This
   * applies any time it has been more than 24 hours since the last customer message.
   */
  utility, //
  /**
   * Indicates that the conversation opened by a business replying to a customer within a customer service window.
   */
  service
}
