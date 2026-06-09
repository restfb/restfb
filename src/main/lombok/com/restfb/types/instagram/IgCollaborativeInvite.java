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
package com.restfb.types.instagram;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an Instagram collaboration invite.
 *
 * @see <a href="https://developers.facebook.com/docs/instagram-platform/instagram-api-with-facebook-login/collaboration#fetch-collaboration-invites">Fetch Collaboration Invites</a>
 */
public class IgCollaborativeInvite extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * The ID of the Instagram Media object tagged for collaboration.
   */
  @Getter
  @Setter
  @Facebook("media_id")
  private String mediaId;

  /**
   * Instagram username of the media owner who invited the app user's Instagram account for collaboration.
   */
  @Getter
  @Setter
  @Facebook("media_owner_username")
  private String mediaOwnerUsername;

  /**
   * Caption of the tagged Instagram Media.
   */
  @Getter
  @Setter
  @Facebook
  private String caption;

  /**
   * Viewable CDN URL of the tagged Instagram Media.
   */
  @Getter
  @Setter
  @Facebook("media_url")
  private String mediaUrl;
}
