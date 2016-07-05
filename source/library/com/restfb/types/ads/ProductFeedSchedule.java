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
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/product-feed-schedule/">Product
 * Feed Schedule type</a>
 */
public class ProductFeedSchedule extends AbstractFacebookType {

  /**
   * The day of month to fetch feed, for monthly schedules e.g., 1 for first of month
   *
   * -- GETTER --
   * 
   * @return The day of month to fetch feed, for monthly schedules e.g., 1 for first of month
   */
  @Getter
  @Setter
  @Facebook("day_of_month")
  private Long mDayOfMonth;

  /**
   * The day of week to fetch feed, for weekly schedules.
   *
   * Allowed values: SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
   *
   * -- GETTER --
   * 
   * @return The day of week to fetch feed, for weekly schedules.
   */
  @Getter
  @Setter
  @Facebook("day_of_week")
  private String dayOfWeek;

  /**
   * Hour of the day to fetch the product feed, 0-23 PST
   *
   * -- GETTER --
   * 
   * @return Hour of the day to fetch the product feed, 0-23 PST
   */
  @Getter
  @Setter
  @Facebook
  private Long hour;

  /**
   * The interval at which the product feed gets fetched
   *
   * -- GETTER --
   * 
   * @return The interval at which the product feed gets fetched
   */
  @Getter
  @Setter
  @Facebook
  private String interval;

  /**
   * Minute of the hour to fetch the product feed, 0-59
   *
   * -- GETTER --
   * 
   * @return Minute of the hour to fetch the product feed, 0-59
   */
  @Getter
  @Setter
  @Facebook
  private Long minute;

  /**
   * The location of the product feed to fetch
   *
   * -- GETTER --
   * 
   * @return The location of the product feed to fetch
   */
  @Getter
  @Setter
  @Facebook
  private String url;

  /**
   * The username that is needed to access the url
   *
   * -- GETTER --
   * 
   * @return The username that is needed to access the url
   */
  @Getter
  @Setter
  @Facebook
  private String username;

  /**
   * undocumented field according to GitHub issue #497
   */
  @Getter
  @Setter
  @Facebook
  private String password;
}
