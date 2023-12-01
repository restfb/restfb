/*
 * Copyright (c) 2010-2024 Mark Allen, Norbert Bartels.
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
package com.restfb.types.instagram;

import java.util.Date;

import com.restfb.Facebook;
import com.restfb.types.FacebookType;

import lombok.Getter;
import lombok.Setter;

public class IgMediaChild extends FacebookType {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook("ig_id")
  private String igId;

  /**
   * Media type. Can be CAROUSEL_ALBUM, IMAGE, or VIDEO.
   */
  @Getter
  @Setter
  @Facebook("media_type")
  private String mediaType;

  /**
   * Media URL. Will be omitted from responses if the media contains copyrighted material, or has been flagged for a
   * copyright violation.
   */
  @Getter
  @Setter
  @Facebook("media_url")
  private String mediaUrl;

  /**
   * Surface where the media is published. Can be AD, FEED, or STORY.
   */
  @Getter
  @Setter
  @Facebook("media_product_type")
  private String mediaProductType;

  /**
   * ID of Instagram user who created the media. Only returned if the app user making the query also created the media,
   * otherwise <code>username</code> field will be returned instead.
   *
   * @deprecated with Graph API 12 or December 13, 2021 for all versions
   */
  @Getter
  @Setter
  @Facebook
  @Deprecated
  private IgUser owner;

  /**
   * Permanent URL to the media.
   */
  @Getter
  @Setter
  @Facebook
  private String permalink;

  /**
   * Shortcode to the media.
   */
  @Getter
  @Setter
  @Facebook
  private String shortcode;

  /**
   * Media thumbnail URL. Only available on VIDEO media.
   */
  @Getter
  @Setter
  @Facebook("thumbnail_url")
  private String thumbnailUrl;

  /**
   * ISO 8601 formatted creation date in UTC (default is UTC ±00:00)
   */
  @Getter
  @Setter
  @Facebook
  private Date timestamp;

  /**
   * Username of user who created the media.
   */
  @Getter
  @Setter
  @Facebook
  private String username;
}
