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
  UNVERSIONED(null),

  /**
   * <tt>Graph API 2.7</tt>, available until October 5, 2018
   *
   * @since July 13, 2016
   */
  @Deprecated
  VERSION_2_7("v2.7"),

  /**
   * <tt>Graph API 2.8</tt>, available until April 18th, 2019
   *
   * @since October 5th, 2016
   */
  VERSION_2_8("v2.8"),

  /**
   * <tt>Graph API 2.9</tt>, available until July 18th, 2019
   *
   * @since April 18th, 2017
   */
  VERSION_2_9("v2.9"),

  /**
   * <tt>Graph API 2.10</tt>, available until November 7, 2019
   *
   * @since July 18th, 2017
   */
  VERSION_2_10("v2.10"),

  /**
   * <tt>Graph API 2.11</tt>, available until January 30, 2020
   *
   * @since November 7, 2017
   */
  VERSION_2_11("v2.11"),

  /**
   * <tt>Graph API 2.12</tt>, available at least until May 1, 2020
   *
   * @since January 30, 2018
   */
  VERSION_2_12("v2.12"),

  /**
   * <tt>Graph API 3.0</tt>, available at least until July 26, 2020
   *
   * @since May 1, 2018
   */
  VERSION_3_0("v3.0"),

  /**
   * <tt>Graph API 3.1</tt>, available at least until October 23, 2020
   *
   * @since July 26, 2018
   */
  VERSION_3_1("v3.1"),

  /**
   * <tt>Graph API 3.2</tt>, available at least until October, 2020
   *
   * @since October 23, 2018
   */
  VERSION_3_2("v3.2"),

  /**
   * convenience enum to provide simple access to the latest supported Graph API Version.
   * <p>
   * the current version is <tt>Graph API 3.2</tt>
   * </p>
   */
  LATEST("v3.2");

  private final String urlElement;

  Version(String urlElement) {
    this.urlElement = urlElement;
  }

  public String getUrlElement() {
    return this.urlElement;
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
