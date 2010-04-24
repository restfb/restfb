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
 * TODO: document
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Event extends NamedFacebookType {
  @Facebook
  private NamedFacebookType owner;

  @Facebook
  private String description;

  @Facebook("start_time")
  private String startTime;

  @Facebook("end_time")
  private String endTime;

  @Facebook
  private String location;

  @Facebook
  private Venue venue;

  @Facebook
  private String privacy;

  @Facebook("updated_time")
  private String updatedTime;

  public NamedFacebookType getOwner() {
    return owner;
  }

  public String getDescription() {
    return description;
  }

  public Date getStartTime() {
    return toDate(startTime);
  }

  public Date getEndTime() {
    return toDate(endTime);
  }

  public String getLocation() {
    return location;
  }

  public Venue getVenue() {
    return venue;
  }

  public String getPrivacy() {
    return privacy;
  }

  public Date getUpdatedTime() {
    return toDate(updatedTime);
  }
}