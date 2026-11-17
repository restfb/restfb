/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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
 * Represents the
 * <a href="https://developers.facebook.com/documentation/threads/retrieve-and-discover-posts/retrieve-posts">Threads
 * Media type</a>. Field availability depends on the endpoint, permissions, and post type.
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
   * The media type for a Threads post will be one of these values: TEXT_POST, IMAGE, VIDEO, CAROUSEL_ALBUM, AUDIO, or
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
   * Threads user who created the post. Only available on top-level posts that you own.
   */
  @Getter
  @Setter
  @Facebook
  private TdProfile owner;

  /**
   * Threads username who created the post.
   */
  @Getter
  @Setter
  @Facebook
  private String username;

  /**
   * URL of the post author's profile picture on Threads.
   */
  @Getter
  @Setter
  @Facebook("profile_picture_url")
  private String profilePictureUrl;

  /**
   * Whether the post author's profile is verified on Threads.
   */
  @Getter
  @Setter
  @Facebook("is_verified")
  private Boolean isVerified;

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
   * Whether the post or reply has replies that you can see. Supported by media retrieval and keyword search; not listed
   * in the Retrieve User Posts field table.
   */
  @Getter
  @Setter
  @Facebook("has_replies")
  private Boolean hasReplies;

  /**
   * Whether the media is a reply rather than a top-level post.
   */
  @Getter
  @Setter
  @Facebook("is_reply")
  private Boolean isReply;

  /**
   * Whether your user owns the reply. Only appears on replies.
   */
  @Getter
  @Setter
  @Facebook("is_reply_owned_by_me")
  private Boolean isReplyOwnedByMe;

  /**
   * The top-level post or original thread in the reply tree. Only appears on replies.
   */
  @Getter
  @Setter
  @Facebook("root_post")
  private TdReply rootPost;

  /**
   * The immediate parent of the reply. Only appears on replies.
   */
  @Getter
  @Setter
  @Facebook("replied_to")
  private TdReply repliedTo;

  /**
   * Whether the reply is hidden. Only appears on replies.
   */
  @Getter
  @Setter
  @Facebook("hide_status")
  private TdReply.TdHideStatus hideStatus;

  /**
   * Who can reply to the post. Only appears on top-level posts and replies that you own.
   */
  @Getter
  @Setter
  @Facebook("reply_audience")
  private TdReply.TdReplyAudience replyAudience;

  /**
   * Media ID of the post that was quoted. Note: This only appears on quote posts.
   */
  @Getter
  @Setter
  @Facebook("quoted_post")
  private String quotedPost;

  /**
   * Media ID of the post that was reposted. Note: This only appears on reposts.
   */
  @Getter
  @Setter
  @Facebook("reposted_post")
  private String repostedPost;

  /**
   * The accessibility text label or description for an image or video in a Threads post
   */
  @Getter
  @Setter
  @Facebook("alt_text")
  private String altText;

  /**
   * The ID of the location tagged to the media.
   */
  @Getter
  @Setter
  @Facebook("location_id")
  private String locationId;

  /**
   * The location tagged to the media.
   */
  @Getter
  @Setter
  @Facebook
  private TdLocation location;

  @Getter
  @Setter
  @Facebook("allowlisted_country_codes")
  private List<String> allowlistedCountryCodes = new ArrayList<>();

  /**
   * link attachment URL of the URL that is attachment with highlight. See here:
   * https://developers.facebook.com/docs/threads/posts#tags-and-links-in-posts
   */
  @Getter
  @Setter
  @Facebook("link_attachment_url")
  private String linkAttachmentUrl;

  /**
   * URL of the GIF attached to the post. Only appears on posts with a GIF attachment.
   */
  @Getter
  @Setter
  @Facebook("gif_url")
  private String gifUrl;

  /**
   * Topic tag in the post's header. Only appears on posts with a topic tag.
   */
  @Getter
  @Setter
  @Facebook("topic_tag")
  private String topicTag;

  /**
   * Whether the media was posted as a spoiler.
   */
  @Getter
  @Setter
  @Facebook("is_spoiler_media")
  private Boolean isSpoilerMedia;

  /**
   * Status of a ghost post, currently ACTIVE or ARCHIVED. Only appears on ghost posts.
   */
  @Getter
  @Setter
  @Facebook("ghost_post_status")
  private String ghostPostStatus;

  /**
   * When the ghost post expires or expired. Only appears on ghost posts.
   */
  @Getter
  @Setter
  @Facebook("ghost_post_expiration_timestamp")
  private Date ghostPostExpirationTimestamp;

  @Getter
  @Setter
  @Facebook("poll_attachment")
  private TdPollAttachment pollAttachment;

  @Getter
  @Setter
  @Facebook("text_attachment")
  private TdTextAttachment textAttachment;

  public List<Locale> getAllowlistedCountryCodesAsLocales() {
    List<Locale> locales = new ArrayList<>();
    for (String code : allowlistedCountryCodes) {
      locales.add(new Locale("", code));
    }
    return locales;
  }

}
