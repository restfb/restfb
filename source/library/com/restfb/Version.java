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
package com.restfb;

public enum Version {

  /**
   * unversiond api
   */
  UNVERSIONED(null, false),

  /**
   * <tt>Graph API 1.0</tt>, available until April 30, 2015
   * 
   * @since April 21, 2010
   * @deprecated use {@link Version#VERSION_2_2} instead
   */
  @Deprecated
  VERSION_1_0("v1.0", false),

  /**
   * <tt>Graph API 2.0</tt>, available until August 7, 2016
   *
   * @since April 30, 2014
   * @deprecated use {@link Version#VERSION_2_2} instead
   */
  @Deprecated
  VERSION_2_0("v2.0", false),

  /**
   * <tt>Graph API 2.1</tt>, available until October 30, 2016
   *
   * @since August 7, 2014
   * @deprecated use {@link Version#VERSION_2_2} instead
   */
  @Deprecated
  VERSION_2_1("v2.1", false),

  /**
   * <tt>Graph API 2.2</tt>, available until March 25, 2017
   *
   * @since October 30, 2014
   */
  VERSION_2_2("v2.2", false),

  /**
   * <tt>Graph API 2.3</tt>, available until July 8, 2017
   *
   * @since March 25, 2015
   */
  VERSION_2_3("v2.3", false),

  /**
   * <tt>Graph API 2.4</tt>, available until October 7, 2017
   *
   * @since July 8, 2015
   */
  VERSION_2_4("v2.4", false),

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
   * <tt>Graph API 2.8</tt>, available at least until October 2018
   *
   * @since October 5th, 2016
   */
  VERSION_2_8("v2.8", true),

  /**
   * convenience enum to provide simple access to the latest supported Graph API Version.
   * <p>
   * the current version is <tt>Graph API 2.8</tt>
   * </p>
   */
  LATEST("v2.8", true);

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
