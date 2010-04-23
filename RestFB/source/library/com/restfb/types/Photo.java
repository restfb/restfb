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

import java.util.Date;

import com.restfb.Facebook;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Photo extends FacebookType {
  @Facebook
  private From from;

  @Facebook
  private String name;

  @Facebook
  private String picture;

  @Facebook
  private String source;

  @Facebook
  private Integer height;

  @Facebook
  private Integer width;

  @Facebook
  private String link;

  @Facebook
  private String icon;

  @Facebook("created_time")
  private String createdTime;

  @Facebook("updated_time")
  private String updatedTime;

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("%s[id=%s name=%s from=%s picture=%s "
        + "source=%s height=%d width=%d link=%s "
        + "icon=%s createdTime=%s updatedTime=%s", getClass().getSimpleName(),
      getId(), getName(), getFrom(), getPicture(), getSource(), getHeight(),
      getWidth(), getLink(), getIcon(), getCreatedTime(), getUpdatedTime());
  }

  public From getFrom() {
    return from;
  }

  public String getName() {
    return name;
  }

  public String getPicture() {
    return picture;
  }

  public String getSource() {
    return source;
  }

  public Integer getHeight() {
    return height;
  }

  public Integer getWidth() {
    return width;
  }

  public String getLink() {
    return link;
  }

  public String getIcon() {
    return icon;
  }

  public Date getCreatedTime() {
    return toDate(createdTime);
  }

  public Date getUpdatedTime() {
    return toDate(updatedTime);
  }
}