/*
 * Copyright (c) 2010-2013 Mark Allen.
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

import com.restfb.Facebook;

import java.util.Date;

import static com.restfb.util.DateUtils.toDateFromLongFormat;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/album">Album Graph API type</a>.
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Album extends NamedFacebookType {
  private static final long serialVersionUID = 1L;
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
  @Facebook("can_upload")
  private Boolean canUpload;
  @Facebook("created_time")
  private String createdTime;
  @Facebook("updated_time")
  private String updatedTime;

  /**
   * An object containing the ID and name of the profile who posted this album.
   *
   * @return An object containing the ID and name of the profile who posted this album.
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
   * Whether or not the user has permission to upload to this album.
   *
   * @return The {@code can_upload} setting for this album.
   */
  public Boolean getCanUpload() {
    return canUpload;
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

  public void setFrom(CategorizedFacebookType from) {
    this.from = from;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public void setCoverPhoto(String coverPhoto) {
    this.coverPhoto = coverPhoto;
  }

  public void setPrivacy(String privacy) {
    this.privacy = privacy;
  }

  public void setCanUpload(Boolean canUpload) {
    this.canUpload = canUpload;
  }

  public void setCreatedTime(String createdTime) {
    this.createdTime = createdTime;
  }

  public void setUpdatedTime(String updatedTime) {
    this.updatedTime = updatedTime;
  }
}