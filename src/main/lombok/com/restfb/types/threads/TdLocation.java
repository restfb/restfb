/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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
package com.restfb.types.threads;

import com.restfb.Facebook;
import com.restfb.types.FacebookType;
import com.restfb.types.NamedFacebookType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a
 * <a href="https://developers.facebook.com/docs/threads/create-posts/location-tagging#location-retrieval">Location</a>
 * in Threads.
 */
public class TdLocation extends NamedFacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * The address of the location.
   */
  @Getter
  @Setter
  @Facebook
  private String address;

  /**
   * The city of the location.
   */
  @Getter
  @Setter
  @Facebook
  private String city;

  /**
   * The country code of the location.
   */
  @Getter
  @Setter
  @Facebook
  private String country;

  /**
   * The latitude of the location.
   */
  @Getter
  @Setter
  @Facebook
  private Double latitude;

  /**
   * The longitude of the location.
   */
  @Getter
  @Setter
  @Facebook
  private Double longitude;

  /**
   * The postal code of the location.
   */
  @Getter
  @Setter
  @Facebook("postal_code")
  private String postalCode;
}
