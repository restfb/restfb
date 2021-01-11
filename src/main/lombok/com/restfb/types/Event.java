/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.annotation.GraphAPI;

import com.restfb.types.features.HasCover;
import com.restfb.types.features.HasProfilePicture;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/event">Event Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Event extends NamedFacebookType implements HasProfilePicture, HasCover {

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
   * @return The category of the event
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.4"))
  @Setter
  @Facebook
  @GraphAPI(since = "2.4")
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
  private transient String rawStartTime;

  @Facebook("end_time")
  private transient String rawEndTime;

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
   * Whether or not the event has been marked as canceled
   *
   * @return Whether or not the event has been marked as canceled
   */
  @Getter
  @Setter
  @Facebook("is_canceled")
  private Boolean isCanceled;

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
   * Number of people interested in the event.
   *
   * @return Number of people interested in the event
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.1"))
  @Setter
  @Facebook("interested_count")
  @GraphAPI(since = "2.1")
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
   * The visibility of this event. Can be 'OPEN', 'CLOSED', or 'SECRET'.
   * 
   * @return The visibility of this event. Can be 'OPEN', 'CLOSED', or 'SECRET'.
   */
  @Getter
  @Setter
  @Facebook
  private String privacy;

  /**
   * The last time the event was updated.
   * 
   * @return The last time the event was updated.
   */
  @Getter
  @Setter
  @Facebook("updated_time")
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
   * The event's picture.
   * 
   * @return The event's picture (only returned if you explicitly include picture in the fields param; example:
   *         ?fields=id,name,picture)
   * @since 1.6.13
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  private ProfilePictureSource picture;

  @Facebook("picture")
  private transient String rawPicture;

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
   * @return Location associated with the event, if any
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.3"))
  @Setter
  @Facebook
  @GraphAPI(since = "2.3")
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
   * the timezone of the event
   */
  @Getter
  @Setter
  @Facebook
  private String timezone;

  /**
   * Cover picture
   *
   * @return Cover picture
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook
  private CoverPhoto cover;

  /**
   * Number of people attending the event
   *
   * @return Number of people attending the event
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.1"))
  @Setter
  @Facebook("attending_count")
  @GraphAPI(since = "2.1")
  private Integer attendingCount;

  /**
   * Number of people who declined the event
   *
   * @return Number of people who declined the event
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.1"))
  @Setter
  @Facebook("declined_count")
  @GraphAPI(since = "2.1")
  private Integer declinedCount;

  /**
   * Number of people who maybe going to the event
   *
   * @return Number of people who maybe going to the event
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.1"))
  @Setter
  @Facebook("maybe_count")
  @GraphAPI(since = "2.1")
  private Integer maybeCount;

  /**
   * Number of people who did not reply to the event
   *
   * @return Number of people who did not reply to the event
   */
  @Getter(onMethod_ = @GraphAPI(since = "2.1"))
  @Setter
  @Facebook("noreply_count")
  @GraphAPI(since = "2.1")
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
    private List<Category> categoryList = new ArrayList<>();

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
    // Sometimes the date comes back in short form - if long form parsing
    // failed, try short instead
    Date dateEnd = toDateFromLongFormat(rawEndTime);
    endTime = dateEnd == null ? toDateFromShortFormat(rawEndTime) : dateEnd;

    Date dateStart = toDateFromLongFormat(rawStartTime);
    startTime = dateStart == null ? toDateFromShortFormat(rawStartTime) : dateStart;
  }

  @JsonMappingCompleted
  protected void fillProfilePicture(JsonMapper jsonMapper) {
    picture = convertPicture(jsonMapper, rawPicture);
  }

}