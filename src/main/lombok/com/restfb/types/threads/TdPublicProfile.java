/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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

import com.restfb.Facebook;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the Threads public profile.
 * <p>
 * <a href="https://developers.facebook.com/docs/threads/threads-profiles#retrieve-a-threads-user-s-public-profile-information">Threads reference</a>
 */
public class TdPublicProfile extends TdProfileBase {

  private static final long serialVersionUID = 1L;

  /**
   * URL of the user's profile picture on Threads.
   */
  @Getter
  @Setter
  @Facebook("profile_picture_url")
  private String profilePictureUrl;

  /**
   * Biography text on Threads profile.
   */
  @Getter
  @Setter
  @Facebook
  private String biography;

  /**
   * Returns true if the user is verified on Threads.
   */
  @Getter
  @Setter
  @Facebook("is_verified")
  private Boolean isVerified;

  /**
   * Returns the number of followers on Threads.
   */
  @Getter
  @Setter
  @Facebook("follower_count")
  private Integer followerCount;

  /**
   * Returns the number of likes on Threads.
   */
  @Getter
  @Setter
  @Facebook("likes_count")
  private Integer likesCount;

  /**
   * Returns the number of quotes on Threads.
   */
  @Getter
  @Setter
  @Facebook("quotes_count")
  private Integer quotesCount;

  /**
   * Returns the number of reposts on Threads.
   */
  @Getter
  @Setter
  @Facebook("reposts_count")
  private Integer repostsCount;

  /**
   * Returns the number of views on Threads.
   */
  @Getter
  @Setter
  @Facebook("views_count")
  private Integer viewsCount;

}
