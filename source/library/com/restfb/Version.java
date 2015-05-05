/*
 * Copyright (c) 2010-2015 Norbert Bartels.
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
   * graph api 1.0, available until April 30, 2015
   * 
   * @since April 21, 2010
   * @deprecated use VERSION_2_0 instead
   */
  @Deprecated
  VERSION_1_0("v1.0"),
  /**
   * graph api 2.0, available until August 7, 2016
   * 
   * @since April 30, 2014
   */
  VERSION_2_0("v2.0"),
  /**
   * graph api 2.1, available until October 30, 2016
   * 
   * @since August 7, 2014
   */
  VERSION_2_1("v2.1"),
  /**
   * graph api 2.2, available until March 25, 2017
   * 
   * @since October 30, 2014
   */
  VERSION_2_2("v2.2"),
  /**
   * graph api 2.3, available at least until March 25, 2017
   * 
   * @since March 25, 2015
   */
  VERSION_2_3("v2.3");

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
}
