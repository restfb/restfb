/*
 * Copyright (c) 2010-2011 Mark Allen.
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

import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;

/**
 * Represents the <a
 * href="http://developers.facebook.com/docs/reference/api/checkin">Checkin
 * Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6
 */
public class Checkin extends FacebookType {
  @Facebook
  private String message;

  @Facebook
  private NamedFacebookType from;

  @Facebook
  private NamedFacebookType application;

  @Facebook
  private Place place;

  @Facebook("created_time")
  private String createdTime;

  @Facebook
  private List<Comment> comments = new ArrayList<Comment>();

  /**
   * Represents the <a
   * href="http://developers.facebook.com/docs/reference/api/checkin">Place
   * Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6
   */
  public static class Place extends CategorizedFacebookType {
    @Facebook
    private Location location;

    /**
     * Represents the <a
     * href="http://developers.facebook.com/docs/reference/api/checkin">Location
     * Graph API type</a>.
     * 
     * @author <a href="http://restfb.com">Mark Allen</a>
     * @since 1.6
     */
    public static class Location {
      @Facebook
      private Double latitude;

      @Facebook
      private Double longitude;

      /**
       * @see java.lang.Object#hashCode()
       */
      @Override
      public int hashCode() {
        return ReflectionUtils.hashCode(this);
      }

      /**
       * @see java.lang.Object#equals(java.lang.Object)
       */
      @Override
      public boolean equals(Object that) {
        return ReflectionUtils.equals(this, that);
      }

      /**
       * @see java.lang.Object#toString()
       */
      @Override
      public String toString() {
        return ReflectionUtils.toString(this);
      }

      /**
       * The latitude of the check-in.
       * 
       * @return The latitude of the check-in.
       */
      public Double getLatitude() {
        return latitude;
      }

      /**
       * The longitude of the check-in.
       * 
       * @return The longitude of the check-in.
       */
      public Double getLongitude() {
        return longitude;
      }
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }

    /**
     * The latitude/longitude of the check-in.
     * 
     * @return The latitude/longitude of the check-in.
     */
    public Location getLocation() {
      return location;
    }
  }

  /**
   * The ID, name, and location of the Facebook Page that represents the
   * location of the check-in.
   * 
   * @return The ID, name, and location of the Facebook Page that represents the
   *         location of the check-in.
   */
  public Place getPlace() {
    return place;
  }

  /**
   * The ID and name of the application that made the check-in.
   * 
   * @return The ID and name of the application that made the check-in.
   */
  public NamedFacebookType getApplication() {
    return application;
  }

  /**
   * The ID and name of the user who made the check-in.
   * 
   * @return The ID and name of the user who made the check-in.
   */
  public NamedFacebookType getFrom() {
    return from;
  }

  /**
   * The message the user added to the check-in.
   * 
   * @return The message the user added to the check-in.
   */
  public String getMessage() {
    return message;
  }

  /**
   * The time the check-in was created.
   * 
   * @return The time the check-in was created.
   */
  public Date getCreatedTime() {
    return toDateFromLongFormat(createdTime);
  }

  /**
   * The comments for the check-in.
   * 
   * @return The comments for the check-in.
   */
  public List<Comment> getComments() {
    return unmodifiableList(comments);
  }
}