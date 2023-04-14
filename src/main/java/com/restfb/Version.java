/*
 * Copyright (c) 2010-2023 Mark Allen, Norbert Bartels.
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

import java.util.Arrays;
import java.util.stream.Stream;

public enum Version {

  /**
   * unversiond api
   */
  UNVERSIONED(null),

  /**
   * <tt>Graph API 9.0</tt>, available until February 23, 2023
   *
   * @since November 10th, 2020
   */
  VERSION_9_0("v9.0"),

  /**
   * <tt>Graph API 10.0</tt>, available until June 8th, 2023
   *
   * @since February 23th, 2021
   */
  VERSION_10_0("v10.0"),

  /**
   * <tt>Graph API 11.0</tt>, available until September 14th, 2023
   *
   * @since June 8th, 2021
   */
  VERSION_11_0("v11.0"),

  /**
   * <tt>Graph API 12.0</tt>, available until February 8th, 2024
   *
   * @since September 14th, 2021
   */
  VERSION_12_0("v12.0"),

  /**
   * <tt>Graph API 13.0</tt>, available until May 28th, 2024
   *
   * @since February 8th, 2022
   */
  VERSION_13_0("v13.0"),

  /**
   * <tt>Graph API 14.0</tt>, available until September 17th, 2024
   *
   * @since May 25th, 2022
   */
  VERSION_14_0("v14.0"),

  /**
   * <tt>Graph API 15.0</tt>, available at least until February 2nd, 2024
   *
   * @since September 17th, 2022
   */
  VERSION_15_0("v15.0"),

  /**
   * <tt>Graph API 16.0</tt>, available at least until February 2026
   *
   * @since February 2nd, 2023
   */
  VERSION_16_0("v16.0"),

  /**
   * convenience enum to provide simple access to the latest supported Graph API Version.
   * <p>
   * the current version is <tt>Graph API 14.0</tt>
   * </p>
   */
  LATEST("v16.0");

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
   * converts a String (for example the url parameter) into a Version object
   * 
   * @param urlElementStr
   *          String that should
   * @return the generated version
   */
  public static Version getVersionFromString(String urlElementStr) {
    if (urlElementStr == null) {
      return UNVERSIONED;
    }

    return Arrays.stream(Version.values())
            .filter(v -> urlElementStr.equals(v.getUrlElement()))
            .findFirst()
            .orElse(UNVERSIONED);
  }
}
