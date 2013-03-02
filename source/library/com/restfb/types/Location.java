/*
 * Copyright (c) 2010-2013 Mark Allen.
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

import java.io.Serializable;

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;

/**
 * Represents a location (address and latitude/longitude).
 * <p>
 * This is used by several Graph API types, e.g. <tt>{@link Post}</tt> and <tt>{@link Page}</tt>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.8
 */
public class Location implements Serializable {
  @Facebook
  private String street;

  @Facebook
  private String city;

  @Facebook
  private String state;

  @Facebook
  private String country;

  @Facebook
  private String zip;

  @Facebook
  private Double latitude;

  @Facebook
  private Double longitude;

  private static final long serialVersionUID = 1L;

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ReflectionUtils.hashCode(this);
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object that) {
    return ReflectionUtils.equals(this, that);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ReflectionUtils.toString(this);
  }

  /**
   * The street address of this location.
   * 
   * @return The street address of this location.
   */
  public String getStreet() {
    return street;
  }

  /**
   * The city name of this location.
   * 
   * @return The city name of this location.
   */
  public String getCity() {
    return city;
  }

  /**
   * The state name of this location.
   * 
   * @return The state name of this location.
   */
  public String getState() {
    return state;
  }

  /**
   * The country name of this location.
   * 
   * @return The country name of this location.
   */
  public String getCountry() {
    return country;
  }

  /**
   * The postal code of this location.
   * 
   * @return The postal code of this location.
   */
  public String getZip() {
    return zip;
  }

  /**
   * The latitude of this location.
   * 
   * @return The latitude of this location.
   */
  public Double getLatitude() {
    return latitude;
  }

  /**
   * The longitude of this location.
   * 
   * @return The longitude of this location.
   */
  public Double getLongitude() {
    return longitude;
  }
}