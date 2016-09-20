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
package com.restfb.types.webhook.messaging.payment;

import com.restfb.Facebook;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ShippingAddress implements Serializable {

  /**
   * ID of shipping address
   */
  @Getter
  @Setter
  @Facebook
  private String id;

  /**
   * Shipping address country
   */
  @Getter
  @Setter
  @Facebook
  private String country;

  /**
   * Shipping address city
   */
  @Getter
  @Setter
  @Facebook
  private String city;

  /**
   * Shipping address street, first line
   */
  @Getter
  @Setter
  @Facebook
  private String street1;

  /**
   * Shipping address street, second line
   */
  @Getter
  @Setter
  @Facebook
  private String street2;

  /**
   * Shipping address state
   */
  @Getter
  @Setter
  @Facebook
  private String state;

  /**
   * Shipping address postal code
   */
  @Getter
  @Setter
  @Facebook("postal_code")
  private String postalCode;
}
