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

import static com.restfb.util.DateUtils.toDateFromLongFormat;

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/product-feed-upload/">Product
 * Feed Upload type</a>
 */
public class ProductFeedUpload extends BaseAdsObject {

  @Facebook("end_time")
  private String rawEndTime;

  /**
   * The time the upload was completed
   *
   * -- GETTER --
   *
   * @return The time the upload was completed
   */
  @Getter
  @Setter
  private Date endTime;

  /**
   * The input method the product feed was obtained with
   *
   * possible values: Manual Upload, Server Fetch
   *
   * -- GETTER --
   *
   * @return The input method the product feed was obtained with
   */
  @Getter
  @Setter
  @Facebook("input_method")
  private String inputMethod;

  /**
   * The time the upload process started
   *
   * -- GETTER --
   *
   * @return The time the upload process started
   */
  @Getter
  @Setter
  private Date startTime;

  @Facebook("start_time")
  private String rawStartTime;

  /**
   * The url to fetch the products from
   *
   * -- GETTER --
   *
   * @return The url to fetch the products from
   */
  @Getter
  @Setter
  @Facebook("url")
  private String url;

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    startTime = toDateFromLongFormat(rawStartTime);
    endTime = toDateFromLongFormat(rawEndTime);
  }

}
