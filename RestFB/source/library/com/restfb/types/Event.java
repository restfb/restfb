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
public class Event extends FacebookType {
  @Facebook
  private Owner owner;
  @Facebook
  private String name;
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

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("%s[id=%s name=%s owner=%s description=%s "
        + "startTime=%s endTime=%s location=%s "
        + "privacy=%s updatedTime=%s venue=%s]", getClass().getSimpleName(),
      getId(), getName(), getOwner(), getDescription(), getStartTime(),
      getEndTime(), getLocation(), getPrivacy(), getUpdatedTime(), getVenue());
  }

  public Owner getOwner() {
    return owner;
  }

  public String getName() {
    return name;
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

  public static class Venue {
    @Facebook
    private String street;

    @Facebook
    private String city;

    @Facebook
    private String state;

    @Facebook
    private String country;

    @Facebook
    private Double latitude;

    @Facebook
    private Double longitude;

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return String.format("%s[street=%s city=%s state=%s "
          + "country=%s latitude=%d longitude=%d]", getClass().getSimpleName(),
        getStreet(), getCity(), getState(), getCountry(), getLatitude(),
        getLongitude());
    }

    public String getStreet() {
      return street;
    }

    public String getCity() {
      return city;
    }

    public String getState() {
      return state;
    }

    public String getCountry() {
      return country;
    }

    public Double getLatitude() {
      return latitude;
    }

    public Double getLongitude() {
      return longitude;
    }
  }
}