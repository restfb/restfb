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

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/threads/threads-profiles">Threads profile</a>
 */
public class TdProfile extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * Instagram user ID. This is returned by default.
   */
  @Getter
  @Setter
  @Facebook
  private String id;

  /**
   * Handle or unique username on Threads. This is the same as the username on Instagram.
   */
  @Getter
  @Setter
  @Facebook
  private String username;

  /**
   * name of the Threads profile
   */
  @Getter
  @Setter
  @Facebook
  private String name;

  /**
   * URL of the user's profile picture on Threads.
   */
  @Getter
  @Setter
  @Facebook("threads_profile_picture_url")
  private String threadsProfilePictureUrl;

  /**
   * Biography text on Threads profile.
   */
  @Getter
  @Setter
  @Facebook("threads_biography")
  private String threadsBiography;
}
