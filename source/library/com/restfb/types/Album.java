/*
 * Copyright (c) 2010-2012 Mark Allen.
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

import java.util.Date;

import com.restfb.Facebook;

/**
 * Represents the <a
 * href="http://developers.facebook.com/docs/reference/api/album">Album Graph
 * API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Album extends NamedFacebookType {
  @Facebook
  private CategorizedFacebookType from;

  @Facebook
  private String description;

  @Facebook
  private String location;

  @Facebook
  private String link;

  @Facebook
  private Long count;

  @Facebook("cover_photo")
  private String coverPhoto;

  @Facebook
  private String privacy;

  @Facebook("created_time")
  private String createdTime;

  @Facebook("updated_time")
  private String updatedTime;

  private static final long serialVersionUID = 1L;

  /**
   * An object containing the ID and name of the profile who posted this album.
   * 
   * @return An object containing the ID and name of the profile who posted this
   *         album.
   */
  public CategorizedFacebookType getFrom() {
    return from;
  }

  /**
   * The description of the album.
   * 
   * @return The description of the album.
   */
  public String getDescription() {
    return description;
  }

  /**
   * The location of the album.
   * 
   * @return The location of the album.
   */
  public String getLocation() {
    return location;
  }

  /**
   * A link to this album on Facebook.
   * 
   * @return A link to this album on Facebook.
   */
  public String getLink() {
    return link;
  }

  /**
   * The number of photos in this album.
   * 
   * @return The number of photos in this album.
   */
  public Long getCount() {
    return count;
  }

  /**
   * The album cover photo ID.
   * 
   * @return The album cover photo ID
   */
  public String getCoverPhoto() {
    return coverPhoto;
  }

  /**
   * The privacy settings for the album.
   * 
   * @return The privacy settings for the album.
   */
  public String getPrivacy() {
    return privacy;
  }

  /**
   * The time the photo album was initially created.
   * 
   * @return The time the photo album was initially created.
   */
  public Date getCreatedTime() {
    return toDateFromLongFormat(createdTime);
  }

  /**
   * The last time the photo album was updated.
   * 
   * @return The last time the photo album was updated.
   */
  public Date getUpdatedTime() {
    return toDateFromLongFormat(updatedTime);
  }
}