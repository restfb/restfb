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
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/product-feed/">Product Feed
 * type</a>
 */
public class ProductFeed extends BaseAdsObject {

  /**
   * An ISO 3166-1 Alpha 2 country code
   *
   * -- GETTER --
   *
   * @return An ISO 3166-1 Alpha 2 country code
   */
  @Getter
  @Setter
  @Facebook
  private String country;

  /**
   * Creation time of the product feed
   *
   * -- GETTER --
   *
   * @return Creation time of the product feed
   */
  @Getter
  @Setter
  private Date createdTime;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * Default currency for products.
   *
   * If no currency is specified for items in the feed file, this value would be used
   *
   * -- GETTER --
   *
   * @return Default currency for products
   */
  @Getter
  @Setter
  @Facebook("default_currency")
  private String defaultCurrency;

  /**
   * Allows products to be deleted if they are no longer included in a product feed
   *
   * -- GETTER --
   *
   * @return Allows products to be deleted if they are no longer included in a product feed
   */
  @Getter
  @Setter
  @Facebook("deletion_enabled")
  private Boolean deletionEnabled;

  /**
   * The delimiter used in product feed file
   *
   * possible values: AUTODETECT, BAR, COMMA, TAB, TILDE, SEMICOLON
   *
   * -- GETTER --
   *
   * @return The delimiter used in product feed file
   */
  @Getter
  @Setter
  @Facebook
  private String delimiter;

  /**
   * The character encoding used by provided feed
   *
   * possible values: AUTODETECT, LATIN1, UTF8, UTF16LE, UTF16BE, UTF32LE, UTF32BE
   *
   * -- GETTER --
   *
   * @return The character encoding used by provided feed
   */
  @Getter
  @Setter
  @Facebook
  private String encoding;

  /**
   * File name of the product feed. Will be overridden by {@code name} if present
   *
   * -- GETTER --
   *
   * @return File name of the product feed. Will be overridden by {@code name} if present
   */
  @Getter
  @Setter
  @Facebook("file_name")
  private String fileName;

  /**
   * The latest upload session of the product feed
   *
   * -- GETTER --
   *
   * @return The latest upload session of the product feed
   */
  @Getter
  @Setter
  @Facebook("latest_upload")
  private ProductFeedUpload latestUpload;

  /**
   * The total products of this product catalog
   *
   * -- GETTER --
   *
   * @return The total products of this product catalog
   */
  @Getter
  @Setter
  @Facebook("product_count")
  private Long productCount;

  /**
   * Allows tabs and newlines within fields
   *
   * possible values: AUTODETECT, ON, OFF
   *
   * -- GETTER --
   *
   * @return Allows tabs and newlines within fields
   */
  @Getter
  @Setter
  @Facebook("quoted_fields_mode")
  private String quotedFieldsMode;

  /**
   * The configuration for fetching a feed in a recurrant manner
   *
   * -- GETTER --
   *
   * @return The configuration for fetching a feed in a recurrant manner
   */
  @Getter
  @Setter
  @Facebook
  private ProductFeedSchedule schedule;

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
  }
}
