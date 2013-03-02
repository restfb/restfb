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

import com.restfb.Facebook;

/**
 * Represents information about the place where an event occurred, for example a {@link Checkin} or {@link Photo}.
 * 
 * @author <a href="http://ex-nerd.com">Chris Petersen</a>
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.10
 */
public class Place extends NamedFacebookType {
  @Facebook
  private Location location;

  @Facebook("location")
  private String locationAsString;

  private static final long serialVersionUID = 1L;

  /**
   * Location containing geographic information such as latitude, longitude, country, and other fields (fields will vary
   * based on geography and availability of information).
   * <p>
   * It is possible for Facebook to return either this value or {@link #getLocationAsString()}.
   * 
   * @return Location containing geographic information such as latitude, longitude, country, and other fields.
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Description for this location.
   * <p>
   * It is possible for Facebook to return either this value or {@link #getLocation()}. If {@link #getLocation()}
   * returns {@code null}, then check this method to see if it has data, e.g. {@code "Philadelphia, PA"}.
   * 
   * @return Description for this location.
   * @since 1.6.12
   */
  public String getLocationAsString() {
    return locationAsString;
  }
}