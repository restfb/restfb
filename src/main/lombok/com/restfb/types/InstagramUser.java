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
package com.restfb.types;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/instagram-user/">Instagram User</a>
 * API type.
 */
public class InstagramUser extends NamedFacebookType {

  /**
   * Number of Instagram users that this Instagram user follows
   */
  @Getter
  @Setter
  @Facebook("follow_count")
  private Long followCount;

  /**
   * Number of Instagram users that follow this Instagram user
   */
  @Getter
  @Setter
  @Facebook("followed_by_count")
  private Long followedByCount;

  /**
   * Indicates whether Instagram Account has a profile picture
   */
  @Getter
  @Setter
  @Facebook("has_profile_picture")
  private Boolean hasProfilePicture;

  /**
   * Whether the Instagram user is private
   */
  @Getter
  @Setter
  @Facebook("is_private")
  private Boolean isPrivate;

  /**
   * Whether the Instagram user is published
   */
  @Getter
  @Setter
  @Facebook("is_published")
  private Boolean isPublished;

  /**
   * Number of active media posted by this Instagram user
   */
  @Getter
  @Setter
  @Facebook("media_count")
  private Long mediaCount;

  /**
   * URI to user's Instagram profile picture
   */
  @Getter
  @Setter
  @Facebook("profile_pic")
  private String profilePic;

  /**
   * Instagram username
   */
  @Getter
  @Setter
  @Facebook
  private String username;

}
