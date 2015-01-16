/*
 * Copyright (c) 2010-2015 Mark Allen.
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
import com.restfb.JsonMapper.JsonMappingCompleted;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
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