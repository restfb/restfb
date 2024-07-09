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

import com.restfb.Facebook;
import com.restfb.types.FacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/threads/reply-moderation">Reply or Conversation type</a>
 */
public class TdReply extends FacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * Represents text for a Threads reply. This is optional on image, video, and carousel replies.
   */
  @Getter
  @Setter
  @Facebook
  private String text;

  /**
   * Instagram username who created the post. Note: This only works for public users or your own user.
   */
  @Getter
  @Setter
  @Facebook
  private String username;

  /**
   * Permanent link to the post. Will be omitted if the media contains copyrighted material or has been flagged for a
   * copyright violation. Note: This only works for public users or your own user.
   */
  @Getter
  @Setter
  @Facebook
  private String permalink;

  /**
   * The publish date and time of the post in ISO 8601 format.
   */
  @Getter
  @Setter
  @Facebook
  private Date timestamp;

  /**
   * Surface where the media is published. In the case of Threads, the value is THREADS.
   */
  @Getter
  @Setter
  @Facebook("media_product_type")
  private String mediaProductType;

  /**
   * The media type for a Threads reply. Values: TEXT_POST, IMAGE, VIDEO, CAROUSEL_ALBUM
   */
  @Getter
  @Setter
  @Facebook("media_type")
  private TdMediaType mediaType;

  /**
   * The post’s media URL. This only shows for image, video, and carousel replies.
   */
  @Getter
  @Setter
  @Facebook("media_url")
  private String mediaUrl;

  /**
   * Shortcode of the media.
   */
  @Getter
  @Setter
  @Facebook
  private String shortcode;

  /**
   * URL of thumbnail. This only shows for Threads replies with video.
   */
  @Getter
  @Setter
  @Facebook("thumbnail_url")
  private String thumbnailUrl;

  /**
   * List of child posts. This only shows for carousel replies.
   */
  @Getter
  @Setter
  @Facebook
  private List<TdReply> children = new ArrayList<>();

  /**
   * Indicates if the media is a quoted reply made by another user.
   */
  @Getter
  @Setter
  @Facebook("is_quote_post")
  private Boolean isQuotePost;

  /**
   * true if the Threads post or reply has replies that you can see.
   */
  @Getter
  @Setter
  @Facebook("has_replies")
  private Boolean hasReplies;

  /**
   * Media ID of the top-level post or original thread in the reply tree. Note: This only appears on replies.
   */
  @Getter
  @Setter
  @Facebook("root_post")
  private TdReply rootPost;

  /**
   * Media ID of the immediate parent of the reply. Note: This only appears on replies.
   */
  @Getter
  @Setter
  @Facebook("replied_to")
  private TdReply repliedTo;

  /**
   * true if the Threads media is a reply. false if the Threads media is a top-level post.
   */
  @Getter
  @Setter
  @Facebook("is_reply")
  private Boolean isReply;

  /**
   * {@code true} if your user is the owner of the Threads reply.
   * <br>
   * {@code false} if another user is the owner of the
   * Threads reply.
   */
  @Getter
  @Setter
  @Facebook("is_reply_owned_by_me")
  private Boolean isReplyOwnedByMe;

  /**
   * Whether or not the reply is hidden. Values: NOT_HUSHED, UNHUSHED, HIDDEN, COVERED, BLOCKED, RESTRICTED
   */
  @Getter
  @Setter
  @Facebook("hide_status")
  private TdHideStatus hideStatus;

  /**
   * Who can reply to your post.
   */
  @Getter
  @Setter
  @Facebook("reply_audience")
  private TdReplyAudience replyAudience;

  public enum TdHideStatus {
    NOT_HUSHED, UNHUSHED, HIDDEN, COVERED, BLOCKED, RESTRICTED
  }

  public enum TdReplyAudience {
    EVERYONE, ACCOUNTS_YOU_FOLLOW, MENTIONED_ONLY
  }
}
