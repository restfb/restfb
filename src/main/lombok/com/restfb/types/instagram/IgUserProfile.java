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

import com.restfb.Facebook;
import com.restfb.annotation.GraphAPI;
import com.restfb.types.NamedFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Respresents the Instagram User Profile object as defined
 * <a href="https://developers.facebook.com/docs/messenger-platform/instagram/features/user-profile">here</a>
 */
public class IgUserProfile extends NamedFacebookType {

  /**
   * URL to the Profile picture.
   *
   * The URL will expire in a few days.
   */
  @Getter
  @Setter
  @Facebook("profile_pic")
  private String profilePic;

  /**
   * Verification status of the user
   */
  @GraphAPI(since = "12.0")
  @Getter
  @Setter
  @Facebook("is_verified_user")
  private Boolean isVerifiedUser;

  /**
   * Follower count of the user
   */
  @GraphAPI(since = "12.0")
  @Getter
  @Setter
  @Facebook("follower_count")
  private Integer followerCount;

  /**
   * A flag indicating whether the user follow the business or not
   */
  @GraphAPI(since = "12.0")
  @Getter
  @Setter
  @Facebook("is_user_follow_business")
  private Boolean isUserFollowBusiness;

  /**
   * A flag indicating whether the business follow the user or not
   */
  @GraphAPI(since = "12.0")
  @Getter
  @Setter
  @Facebook("is_business_follow_user")
  private Boolean isBusinessFollowUser;
  
  /**
   * The username for the customer's Instagram account
   */
  @GraphAPI(since = "14.0")
  @Getter
  @Setter
  @Facebook
  private String username;
}
