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
public class Album extends FacebookType {
  @Facebook
  private From from;

  @Facebook
  private String name;

  @Facebook
  private String description;

  @Facebook
  private String location;

  @Facebook
  private String link;

  @Facebook
  private Long count;

  @Facebook("created_time")
  private String createdTime;

  @Facebook("updated_time")
  private String updatedTime;

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format(
      "%s[id=%s name=%s from=%s description=%s location=%s link=%s "
          + "count=%d createdTime=%s updatedTime=%s]", getClass()
        .getSimpleName(), getId(), getName(), getFrom(), getDescription(),
      getLocation(), getLink(), getCount(), getCreatedTime(), getUpdatedTime());
  }

  public From getFrom() {
    return from;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getLocation() {
    return location;
  }

  public String getLink() {
    return link;
  }

  public Long getCount() {
    return count;
  }

  public Date getCreatedTime() {
    return toDate(createdTime);
  }

  public Date getUpdatedTime() {
    return toDate(updatedTime);
  }
}