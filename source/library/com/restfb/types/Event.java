/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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
import static java.util.Collections.unmodifiableList;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
  @Setter
  @Facebook
  private Owner owner;

  /**
   * The category of the event.
   *
   * @since Graph API 2.4
   * @return The category of the event
   */
  @Getter
  @Setter
  @Facebook
  private String category;

  /**
   * Can guests invite friends.
   *
   * @return Can guests invite friends
   */
  @Getter
  @Setter
  @Facebook("can_guests_invite")
  private Boolean canGuestsInvite;

  /**
   * Can see guest list.
   *
   * @return Can see guest list
   */
  @Getter
  @Setter
  @Facebook("guest_list_enabled")
  private Boolean guestListEnabled;

  /**
   * The long-form HTML description of the event.
   * 
   * @return The long-form HTML description of the event.
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  @Facebook("start_time")
  private String rawStartTime;

  @Facebook("end_time")
  private String rawEndTime;

  /**
   * The start time of the event.
   * 
   * @return The start time of the event.
   */
  @Getter
  @Setter
  private Date startTime;

  /**
   * The end time of the event.
   * 
   * @return The end time of the event.
   */
  @Getter
  @Setter
  private Date endTime;

  /**
   * The location for this event, a string name.
   * 
   * Deprecated with API version 2.3, use <code>place</code> field instead
   * 
   * @return The location for this event, a string name.
   */
  @Getter
  @Setter
  @Facebook
  private String location;

  /**
   * The RSVP status of this event.
   * 
   * @return The RSVP status of this event.
   */
  @Getter
  @Setter
  @Facebook("rsvp_status")
  private String rsvpStatus;

  /**
   * The location of this event, a structured address object.
   * 
   * Deprecated with API version 2.3, use <code>place</code> field instead
   * 
   * @return The location of this event, a structured address object.
   */
  @Getter
  @Setter
  @Facebook
  private Venue venue;

  /**
   * Number of people interested in the event.
   *
   * @since Graph API 2.1
   * @return Number of people interested in the event
   */
  @Getter
  @Setter
  @Facebook("interested_count")
  private Long interestedCount;

  /**
   * Whether the event is created by page or not.
   *
   * @return Whether the event is created by page or not
   */
  @Getter
  @Setter
  @Facebook("is_page_owned")
  private Boolean isPageOwned;

  /**
   * Whether the viewer is admin or not.
   *
   * @return Whether the viewer is admin or not
   */
  @Getter
  @Setter
  @Facebook("is_viewer_admin")
  private Boolean isViewerAdmin;

  /**
   * The visibility of this event. Can be 'OPEN', 'CLOSED', or 'SECRET'.
   * 
   * @return The visibility of this event. Can be 'OPEN', 'CLOSED', or 'SECRET'.
   */
  @Getter
  @Setter
  @Facebook
  private String privacy;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  /**
   * The last time the event was updated.
   * 
   * @return The last time the event was updated.
   */
  @Getter
  @Setter
  private Date updatedTime;

  /**
   * The URL to a location to buy tickets for this event (on Events for Pages only).
   * 
   * @return The URL to a location to buy tickets for this event (on Events for Pages only).
   * @since 1.6.13
   */
  @Getter
  @Setter
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
  @Setter
  private String picture;

  @Facebook("picture")
  private JsonObject rawPicture;

  /**
   * The group the event belongs to, if any.
   * 
   * @return The group the event belongs to, if any.
   */
  @Getter
  @Setter
  @Facebook("parent_group")
  private Group parentGroup;

  /**
   * Location associated with the event, if any.
   *
   * @since Graph API 2.3
   * @return Location associated with the event, if any
   */
  @Getter
  @Setter
  @Facebook
  private Place place;

  /**
   * Should the time information be ignored in the dates for this event?
   * 
   * @return <tt>true</tt> if the time information be ignored in the dates for this event, <tt>false</tt> otherwise.
   * @since 1.6.13
   */
  @Getter
  @Setter
  @Facebook("is_date_only")
  private Boolean isDateOnly;

  /**
   * Cover picture
   *
   * @return Cover picture
   */
  @Getter
  @Setter
  @Facebook
  private CoverPhoto cover;

  /**
   * Number of people attending the event
   *
   * @since Graph API 2.1
   * @return Number of people attending the event
   */
  @Getter
  @Setter
  @Facebook("attending_count")
  private Integer attendingCount;

  /**
   * Number of people who declined the event
   *
   * @since Graph API 2.1
   * @return Number of people who declined the event
   */
  @Getter
  @Setter
  @Facebook("declined_count")
  private Integer declinedCount;

  /**
   * Number of people who maybe going to the event
   *
   * @since Graph API 2.1
   * @return Number of people who maybe going to the event
   */
  @Getter
  @Setter
  @Facebook("maybe_count")
  private Integer maybeCount;

  /**
   * Number of people who did not reply to the event
   *
   * @since Graph API 2.1
   * @return Number of people who did not reply to the event
   */
  @Getter
  @Setter
  @Facebook("noreply_count")
  private Integer noreplyCount;

  private static final long serialVersionUID = 2L;

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/event">Event Owner Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.13
   */
  public static class Owner extends AbstractFacebookType {

    /**
     * The unique identifier for this owner.
     * 
     * @return The unique identifier for this owner.
     */
    @Getter
    @Setter
    @Facebook
    private String id;

    /**
     * The name of this owner.
     * 
     * @return The name of this owner.
     */
    @Getter
    @Setter
    @Facebook
    private String name;

    /**
     * The category for this owner.
     * 
     * @return The category for this owner.
     */
    @Getter
    @Setter
    @Facebook
    private String category;

    @Facebook("category_list")
    private List<Category> categoryList = new ArrayList<Category>();

    private static final long serialVersionUID = 1L;

    public boolean addCategory(Category category) {
      return categoryList.add(category);
    }

    public boolean removeCategory(Category category) {
      return categoryList.remove(category);
    }

    /**
     * List of other categories for this owner.
     * 
     * @return List of other categories for this owner.
     */
    public List<Category> getCategoryList() {
      return unmodifiableList(categoryList);
    }
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/event">Event Owner Category Graph API
   * type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.13
   */
  public static class Category extends AbstractFacebookType {

    /**
     * The unique identifier for this category.
     * 
     * @return The unique identifier for this category.
     */
    @Getter
    @Setter
    @Facebook
    private String id;

    /**
     * The name of this category.
     * 
     * @return The name of this category.
     */
    @Getter
    @Setter
    @Facebook
    private String name;

    private static final long serialVersionUID = 1L;

  }

  @JsonMappingCompleted
  void convertTime() {
    updatedTime = toDateFromLongFormat(rawUpdatedTime);

    // Sometimes the date comes back in short form - if long form parsing
    // failed, try short instead
    Date dateEnd = toDateFromLongFormat(rawEndTime);
    endTime = dateEnd == null ? toDateFromShortFormat(rawEndTime) : dateEnd;

    Date dateStart = toDateFromLongFormat(rawStartTime);
    startTime = dateStart == null ? toDateFromShortFormat(rawStartTime) : dateStart;
  }

  @JsonMappingCompleted
  protected void jsonMappingCompleted() {
    picture = null;

    if (rawPicture == null)
      return;

    JsonObject picData = rawPicture.getJsonObject("data");
    if (picData != null) {
      picture = picData.getString("url");
    }
  }
}