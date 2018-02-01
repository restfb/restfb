/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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
package com.restfb;

public enum Version {

  /**
   * unversiond api
   */
  UNVERSIONED(null, false),

  /**
   * <tt>Graph API 2.5</tt>, available until April 12, 2018
   *
   * @since October 7, 2015
   */
  VERSION_2_5("v2.5", false),

  /**
   * <tt>Graph API 2.6</tt>, available until July 13, 2018
   *
   * @since April 12, 2016
   */
  VERSION_2_6("v2.6", true),

  /**
   * <tt>Graph API 2.7</tt>, available until October 5, 2018
   *
   * @since July 13, 2016
   */
  VERSION_2_7("v2.7", true),

  /**
   * <tt>Graph API 2.8</tt>, available until April 18th, 2019
   *
   * @since October 5th, 2016
   */
  VERSION_2_8("v2.8", true),

  /**
   * <tt>Graph API 2.9</tt>, available until July 18th, 2019
   *
   * @since April 18th, 2017
   */
  VERSION_2_9("v2.9", true),

  /**
   * <tt>Graph API 2.10</tt>, available until November 7, 2019
   *
   * @since July 18th, 2017
   */
  VERSION_2_10("v2.10", true),

  /**
   * <tt>Graph API 2.11</tt>, available until January 30, 2020
   *
   * @since November 7, 2017
   */
  VERSION_2_11("v2.11", true),

  /**
   * <tt>Graph API 2.12</tt>, available at least until January, 2020
   *
   * @since January 30, 2018
   */
  VERSION_2_12("v2.12", true),

  /**
   * convenience enum to provide simple access to the latest supported Graph API Version.
   * <p>
   * the current version is <tt>Graph API 2.12</tt>
   * </p>
   */
  LATEST("v2.12", true);

  private final String urlElement;

  private final boolean newDeviceTokenMethod;

  Version(String urlElement, boolean newDeviceTokenMethod) {
    this.urlElement = urlElement;
    this.newDeviceTokenMethod = newDeviceTokenMethod;
  }

  public String getUrlElement() {
    return this.urlElement;
  }

  public boolean isNewDeviceTokenMethod() {
    return this.newDeviceTokenMethod;
  }

  public boolean isUrlElementRequired() {
    return null != this.urlElement;
  }

  /**
   * converts a String (for example the url paramter) into a Version object
   * 
   * @param urlElement
   *          String that should
   * @return the generated version
   */
  public static Version getVersionFromString(String urlElement) {
    if (urlElement != null) {
      for (Version v : Version.values()) {
        if (urlElement.equals(v.getUrlElement())) {
          return v;
        }
      }
    }
    return UNVERSIONED;
  }
}
