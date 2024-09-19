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
package com.restfb.types.threads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.restfb.Facebook;
import com.restfb.types.FacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/threads/threads-media">Threads Media type</a>
 */
public class TdMedia extends FacebookType {

  private static final long serialVersionUID = -1L;
  /**
   * Surface where the media is published. In the case of Threads, the value is THREADS.
   */
  @Getter
  @Setter
  @Facebook("media_product_type")
  private String mediaProductType;

  /**
   * The media type for a Threads post will be one of these values: TEXT_POST, IMAGE, VIDEO, CAROUSEL_ALBUM, or
   * REPOST_FACADE.
   */
  @Getter
  @Setter
  @Facebook("media_type")
  private TdMediaType mediaType;

  /**
   * The post’s media URL.
   */
  @Getter
  @Setter
  @Facebook("media_url")
  private String mediaUrl;

  /**
   * Permanent link to the post. Will be omitted if the media contains copyrighted material or has been flagged for a
   * copyright violation.
   */
  @Getter
  @Setter
  @Facebook
  private String permalink;

  /**
   * Instagram user ID who created the post.
   */
  @Getter
  @Setter
  @Facebook
  private TdProfile owner;

  /**
   * Instagram username who created the post.
   */
  @Getter
  @Setter
  @Facebook
  private String username;

  /**
   * Represents text for a Threads post.
   */
  @Getter
  @Setter
  @Facebook
  private String text;

  /**
   * Post time. The publish date in ISO 8601 format.
   */
  @Getter
  @Setter
  @Facebook
  private Date timestamp;

  /**
   * Shortcode of the media.
   */
  @Getter
  @Setter
  @Facebook
  private String shortcode;

  /**
   * URL of thumbnail. This only shows up for Threads media with video.
   */
  @Getter
  @Setter
  @Facebook("thumbnail_url")
  private String thumbnailUrl;

  /**
   * List of child posts. This only shows up for carousel posts.
   */
  @Getter
  @Setter
  @Facebook
  private List<TdMedia> children = new ArrayList<>();

  /**
   * Indicates if the media is a quoted post made by another user
   */
  @Getter
  @Setter
  @Facebook("is_quote_post")
  private Boolean isQuotePost;

  /**
   * The accessibility text label or description for an image or video in a Threads post
   */
  @Getter
  @Setter
  @Facebook("alt_text")
  private String altText;

  @Getter
  @Setter
  @Facebook("allowlisted_country_codes")
  private List<String> allowlistedCountryCodes = new ArrayList<>();

  /**
   * link attachment URL of the URL that is attachment with highlight.
   * See here: https://developers.facebook.com/docs/threads/posts#tags-and-links-in-posts
   */
  @Getter
  @Setter
  @Facebook("link_attachment_url")
  private String linkAttachmentUrl;

  public List<Locale> getAllowlistedCountryCodesAsLocales() {
    List<Locale> locales = new ArrayList<>();
    for (String code : allowlistedCountryCodes) {
      locales.add(new Locale("", code));
    }
    return locales;
  }

}
