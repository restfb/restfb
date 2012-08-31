/*
 * Copyright (c) 2010-2012 Mark Allen.
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
 * Represents a Facebook URL.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @deprecated As of 1.6.10, this type is deprecated and will be removed in a
 *             future release.
 */
@Deprecated
public class Url extends NamedFacebookType {
  @Facebook
  private Long shares;

  @Facebook
  private String picture;

  @Facebook
  private String link;

  @Facebook
  private String category;

  @Facebook("fan_count")
  private Long fanCount;

  private static final long serialVersionUID = 1L;

  public Long getShares() {
    return shares;
  }

  public String getPicture() {
    return picture;
  }

  public String getLink() {
    return link;
  }

  public String getCategory() {
    return category;
  }

  public Long getFanCount() {
    return fanCount;
  }
}