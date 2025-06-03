/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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

public enum Version {

  /**
   * unversiond api
   */
  UNVERSIONED(null),

  /**
   * <tt>Graph API 12.0</tt>, available until February 8th, 2024
   *
   * @since September 14th, 2021
   */
  @Deprecated
  VERSION_12_0("v12.0"),

  /**
   * <tt>Graph API 13.0</tt>, available until May 28th, 2024
   *
   * @since February 8th, 2022
   */
  @Deprecated
  VERSION_13_0("v13.0"),

  /**
   * <tt>Graph API 14.0</tt>, available until September 17th, 2024
   *
   * @since May 25th, 2022
   */
  @Deprecated
  VERSION_14_0("v14.0"),

  /**
   * <tt>Graph API 15.0</tt>, available until February 2nd, 2025
   *
   * @since September 17th, 2022
   */
  @Deprecated
  VERSION_15_0("v15.0"),

  /**
   * <tt>Graph API 16.0</tt>, available until May 23, 2025
   *
   * @since February 2nd, 2023
   */
  @Deprecated
  VERSION_16_0("v16.0"),

  /**
   * <tt>Graph API 17.0</tt>, available until September 12, 2025
   *
   * @since May 23, 2023
   */
  VERSION_17_0("v17.0"),

  /**
   * <tt>Graph API 18.0</tt>, available at least until January 23, 2026
   *
   * @since September 12, 2023
   */
  VERSION_18_0("v18.0"),

  /**
   * <tt>Graph API 19.0</tt>, available at least until May 21, 2026
   *
   * @since January 23, 2024
   */
  VERSION_19_0("v19.0"),

  /**
   * <tt>Graph API 20.0</tt>, available at least until September 24, 2026
   *
   * @since May 21, 2024
   */
  VERSION_20_0("v20.0"),

  /**
   * <tt>Graph API 21.0</tt>, available at least until January 21, 2027
   *
   * @since October 2, 2024
   */
  VERSION_21_0("v21.0"),

  /**
   * <tt>Graph API 22.0</tt>, available at least until May 29, 2027
   *
   * @since January 21, 2025
   */
  VERSION_22_0("v22.0"),

  /**
   * <tt>Graph API 23.0</tt>, available at least until May 2027
   *
   * @since May 29, 2025
   */
  VERSION_23_0("v23.0"),

  /**
   * convenience enum to provide simple access to the latest supported Graph API Version.
   * <p>
   * the current version is <tt>Graph API 23.0</tt>
   * </p>
   */
  LATEST("v23.0"),

  /**
   * <tt>Threads API 1.0</tt>, according to the documentation, this is the first Threads API Version
   */
  THREADS_1_0("v1.0"),

  /**
   * convenience enum to provide simple access to the latest supported Threads API Version.
   */
  THREADS_LATEST("v1.0");

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

    return Arrays.stream(Version.values()).filter(v -> urlElementStr.equals(v.getUrlElement())).findFirst()
      .orElse(UNVERSIONED);
  }
}
