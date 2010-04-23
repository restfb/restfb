/*
 * Copyright (c) 2010 Mark Allen.
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
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Page extends FacebookType {
  @Facebook
  private String name;

  @Facebook
  private String picture;

  @Facebook
  private String link;

  @Facebook
  private String category;

  @Facebook
  private String username;

  @Facebook
  private String founded;

  @Facebook("company_overview")
  private String companyOverview;

  @Facebook
  private String mission;

  @Facebook
  private String products;

  @Facebook("fan_count")
  private Long fanCount;

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("%s[id=%s name=%s picture=%s link=%s "
        + "category=%s username=%s founded=%s "
        + "companyOverview=%s mission=%s products=%s fanCount=%d]", getClass()
      .getSimpleName(), getId(), getName(), getPicture(), getLink(),
      getCategory(), getUsername(), getFounded(), getCompanyOverview(),
      getMission(), getProducts(), getFanCount());
  }

  public String getName() {
    return name;
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

  public String getUsername() {
    return username;
  }

  public String getFounded() {
    return founded;
  }

  public String getCompanyOverview() {
    return companyOverview;
  }

  public String getMission() {
    return mission;
  }

  public String getProducts() {
    return products;
  }

  public Long getFanCount() {
    return fanCount;
  }
}