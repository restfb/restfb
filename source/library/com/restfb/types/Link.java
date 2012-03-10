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
 * href="http://developers.facebook.com/docs/reference/api/link">Link Graph API
 * type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author Patrick Alberts
 * @since 1.5
 */
public class Link extends NamedFacebookType {
  @Facebook
  private NamedFacebookType from;

  @Facebook
  private String message;

  @Facebook
  private String picture;

  @Facebook
  private String link;

  @Facebook
  private String description;

  @Facebook
  private String icon;

  @Facebook("created_time")
  private String createdTime;
  
  private static final long serialVersionUID = 1L;

  /**
   * An object containing the name and ID of the user who posted the link.
   * 
   * @return An object containing the name and ID of the user who posted the
   *         link.
   */
  public NamedFacebookType getFrom() {
    return from;
  }

  /**
   * The link message content.
   * 
   * @return The link message content.
   */
  public String getMessage() {
    return message;
  }

  /**
   * The picture associated with the link.
   * 
   * @return The picture associated with the link.
   */
  public String getPicture() {
    return picture;
  }

  /**
   * The actual URL that was shared.
   * 
   * @return The actual URL that was shared.
   */
  public String getLink() {
    return link;
  }

  /**
   * The link description.
   * 
   * @return The link description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * The link icon.
   * 
   * @return The link icon.
   */
  public String getIcon() {
    return icon;
  }

  /**
   * The time at which this object was created, if available.
   * 
   * @return The time at which this object was created.
   * @since 1.6.3
   */
  public Date getCreatedTime() {
    return toDateFromLongFormat(createdTime);
  }
}