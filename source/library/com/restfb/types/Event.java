/*
 * Copyright (c) 2010-2014 Mark Allen.
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
import static com.restfb.util.DateUtils.toDateFromShortFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;
import lombok.Getter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/event">Event Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Event extends NamedFacebookType {

  /**
   * The user who owns the event.
   * 
   * @return The user who owns the event.
   */
  @Getter
  @Facebook
  private Owner owner;

  /**
   * The long-form HTML description of the event.
   * 
   * @return The long-form HTML description of the event.
   */
  @Getter
  @Facebook
  private String description;

  @Facebook("start_time")
  private String startTime;

  @Facebook("end_time")
  private String endTime;

  /**
   * The location for this event, a string name.
   * 
   * @return The location for this event, a string name.
   */
  @Getter
  @Facebook
  private String location;

  /**
   * The RSVP status of this event.
   * 
   * @return The RSVP status of this event.
   */
  @Getter
  @Facebook("rsvp_status")
  private String rsvpStatus;

  /**
   * The location of this event, a structured address object.
   * 
   * @return The location of this event, a structured address object.
   */
  @Getter
  @Facebook
  private Venue venue;

  /**
   * The visibility of this event. Can be 'OPEN', 'CLOSED', or 'SECRET'.
   * 
   * @return The visibility of this event. Can be 'OPEN', 'CLOSED', or 'SECRET'.
   */
  @Getter
  @Facebook
  private String privacy;

  @Facebook("updated_time")
  private String updatedTime;

  /**
   * The URL to a location to buy tickets for this event (on Events for Pages only).
   * 
   * @return The URL to a location to buy tickets for this event (on Events for Pages only).
   * @since 1.6.13
   */
  @Getter
  @Facebook("ticket_uri")
  private String ticketUri;

  /**
   * The URL of the event's picture.
   * 
   * @return The URL of the event's picture (only returned if you explicitly include picture in the fields param;
   *         example: ?fields=id,name,picture)
   * @since 1.6.13
   */
  @Getter
  @Facebook
  private String picture;

  /**
   * Should the time information be ignored in the dates for this event?
   * 
   * @return <tt>true</tt> if the time information be ignored in the dates for this event, <tt>false</tt> otherwise.
   * @since 1.6.13
   */
  @Getter
  @Facebook("is_date_only")
  private Boolean isDateOnly;

  private static final long serialVersionUID = 2L;

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/event">Event Owner Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.13
   */
  public static class Owner implements Serializable {

    /**
     * The unique identifier for this owner.
     * 
     * @return The unique identifier for this owner.
     */
    @Getter
    @Facebook
    private String id;

    /**
     * The name of this owner.
     * 
     * @return The name of this owner.
     */
    @Getter
    @Facebook
    private String name;

    /**
     * The category for this owner.
     * 
     * @return The category for this owner.
     */
    @Getter
    @Facebook
    private String category;

    /**
     * List of other categories for this owner.
     * 
     * @return List of other categories for this owner.
     */
    @Getter
    @Facebook("category_list")
    private List<Category> categoryList;

    private static final long serialVersionUID = 1L;

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

  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/event">Event Owner Category Graph API
   * type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.13
   */
  public static class Category implements Serializable {

    /**
     * The unique identifier for this category.
     * 
     * @return The unique identifier for this category.
     */
    @Getter
    @Facebook
    private String id;

    /**
     * The name of this category.
     * 
     * @return The name of this category.
     */
    @Getter
    @Facebook
    private String name;

    private static final long serialVersionUID = 1L;

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

  }

  /**
   * The start time of the event.
   * 
   * @return The start time of the event.
   */
  public Date getStartTime() {
    Date date = toDateFromLongFormat(startTime);

    // Sometimes the date comes back in short form - if long form parsing
    // failed, try short instead
    return date == null ? toDateFromShortFormat(startTime) : date;
  }

  /**
   * The end time of the event.
   * 
   * @return The end time of the event.
   */
  public Date getEndTime() {
    Date date = toDateFromLongFormat(endTime);

    // Sometimes the date comes back in short form - if long form parsing
    // failed, try short instead
    return date == null ? toDateFromShortFormat(endTime) : date;
  }

  /**
   * The last time the event was updated.
   * 
   * @return The last time the event was updated.
   */
  public Date getUpdatedTime() {
    return toDateFromLongFormat(updatedTime);
  }

}