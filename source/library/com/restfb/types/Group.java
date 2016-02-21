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

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/group">Group Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Group extends NamedFacebookType {

  /**
   * Information about the group's cover photo.
   *
   * @return Information about the group's cover photo.
   */
  @Getter
  @Setter
  @Facebook
  private CoverPhoto cover;

  /**
   * The email address to upload content to the group.
   * <p>
   * Only current members of the group can use this.
   * </p>
   * 
   * @return The email address to upload content to the group.
   */
  @Getter
  @Setter
  @Facebook
  private String email;

  /**
   * An object containing the name and ID of the user who owns the group.
   * 
   * @return An object containing the name and ID of the user who owns the group.
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType owner;

  /**
   * The group description.
   * 
   * @return The group description.
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * The URL for the group's website.
   * 
   * @return The URL for the group's website.
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * The number of pending member requests.
   * <p>
   * If the token is for an administrator, this is the total number of outstanding requests. If the token is for a group
   * member, this will return the number of friends who have requested membership and also use the same app that is
   * making the request.
   * </p>
   *
   * @return The number of pending member requests
   */
  @Getter
  @Setter
  @Facebook("member_request_count")
  private Long memberRequestCount;

  /**
   * The parent of this group, if it exists.
   *
   * @return The parent of this group, if it exists
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType parent;

  /**
   * The location of this group, a structured address object.
   * 
   * @return The location of this group, a structured address object.
   */
  @Getter
  @Setter
  @Facebook
  private Venue venue;

  /**
   * The privacy setting of the group, either 'OPEN', 'CLOSED', or 'SECRET'.
   * 
   * @return The privacy setting of the group, either 'OPEN', 'CLOSED', or 'SECRET'.
   */
  @Getter
  @Setter
  @Facebook
  private String privacy;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  /**
   * The last time the group was updated.
   * 
   * @return The last time the group was updated.
   */
  @Getter
  @Setter
  private Date updatedTime;

  /**
   * The URL of the group's icon
   * 
   * @return The group's icon url
   * @since 1.6.16
   */
  @Getter
  @Setter
  @Facebook
  private String icon;

  private static final long serialVersionUID = 1L;

  @JsonMappingCompleted
  void convertTime() {
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }

}