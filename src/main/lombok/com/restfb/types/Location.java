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
package com.restfb.types;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a location (address and latitude/longitude).
 * <p>
 * This is used by several Graph API types, e.g. <tt>{@link Post}</tt> and <tt>{@link Page}</tt>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.8
 */
public class Location extends FacebookType {

  /**
   * The street address of this location.
   * 
   * @return The street address of this location.
   */
  @Getter
  @Setter
  @Facebook
  private String street;

  /**
   * The city name of this location.
   * 
   * @return The city name of this location.
   */
  @Getter
  @Setter
  @Facebook
  private String city;

  /**
   * City ID. Any city identified is also a city you can use for targeting ads.
   */
  @Getter
  @Setter
  @Facebook("city_id")
  private String cityId;


  /**
   * The state name of this location.
   * 
   * @return The state name of this location.
   */
  @Getter
  @Setter
  @Facebook
  private String state;

  /**
   * The country name of this location.
   * 
   * @return The country name of this location.
   */
  @Getter
  @Setter
  @Facebook
  private String country;

  /**
   * Country code
   *
   * @return the country code
   */
  @Getter
  @Setter
  @Facebook("country_code")
  private String countryCode;

  /**
   * The postal code of this location.
   * 
   * @return The postal code of this location.
   */
  @Getter
  @Setter
  @Facebook
  private String zip;

  /**
   * The latitude of this location.
   * 
   * @return The latitude of this location.
   */
  @Getter
  @Setter
  @Facebook
  private Double latitude;

  /**
   * The longitude of this location.
   * 
   * @return The longitude of this location.
   */
  @Getter
  @Setter
  @Facebook
  private Double longitude;

  /**
   * The region of this location.
   *
   * @return The region of this location
   */
  @Getter
  @Setter
  @Facebook
  private String region;

  /**
   * Region ID. Specifies a geographic region, such as California.
   * An identified region is the same as one you can use to target ads.
   *
   * @return the region id
   */
  @Getter
  @Setter
  @Facebook("region_id")
  private String regionId;

  /**
   * The name of this location.
   *
   * @return The name of this location
   */
  @Getter
  @Setter
  @Facebook
  private String name;

  /**
   * The parent location if this location is located within another location.
   *
   * @return The parent location if this location is located within another location
   */
  @Getter
  @Setter
  @Facebook("located_in")
  private String locatedIn;

  private static final long serialVersionUID = 1L;

}