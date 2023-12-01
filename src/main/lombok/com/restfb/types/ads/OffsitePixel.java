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

import java.util.Date;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/offsite-pixel">Offsite Pixel</a>
 * Marketing API Type
 */
public class OffsitePixel extends BaseAdsObject {

  /**
   * ID and name of the ad account that first created the pixel
   */
  @Getter
  @Setter
  @Facebook
  private String creator;

  /**
   * JavaScript code for the pixel that you should place in the head of the conversion page
   */
  @Getter
  @Setter
  @Facebook("js_pixel")
  private String jsPixel;

  /**
   * Last time the pixel was fired
   */
  @Getter
  @Setter
  @Facebook("last_firing_time")
  private Date lastFiringTime;

  /**
   * One of: {@code checkout}, {@code registration}, {@code lead}, {@code key_page_view}, {@code add_to_cart},
   * {@code other}
   */
  @Getter
  @Setter
  @Facebook
  private String tag;

}
