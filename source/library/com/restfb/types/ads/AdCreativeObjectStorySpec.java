/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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
package com.restfb.types.ads;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-creative-object-story-spec/">
 * AdCreativeObjectStorySpec</a> Marketing API type.
 */
public class AdCreativeObjectStorySpec extends AbstractFacebookType {

  /**
   * The Instagram user account that the story will be posted to
   *
   * -- GETTER --
   *
   * @return The Instagram user account that the story will be posted to
   */
  @Getter
  @Setter
  @Facebook("instagram_actor_id")
  private String instagramActorId;

  /**
   * The spec for a link page post or carousel ad
   *
   * -- GETTER --
   *
   * @return The spec for a link page post or carousel ad
   */
  @Getter
  @Setter
  @Facebook("link_data")
  private AdCreativeLinkData linkData;

  /**
   * The spec for an offer page post.
   *
   * -- GETTER --
   *
   * @return The spec for an offer page post.
   */
  @Getter
  @Setter
  @Facebook("offer_data")
  private AdCreativeOfferData offerData;

  /**
   * ID of a Facebook page.
   *
   * An unpublished page post will be created on this page. User must have Admin or Editor role for this page.
   *
   * -- GETTER --
   *
   * @return ID of a Facebook page.
   */
  @Getter
  @Setter
  @Facebook("page_id")
  private String pageId;

  /**
   * The spec for a photo page post.
   *
   * -- GETTER --
   *
   * @return The spec for a photo page post.
   */
  @Getter
  @Setter
  @Facebook("photo_data")
  private AdCreativePhotoData photoData;

  /**
   * The spec for a template link page post as used in Dynamic Product Ads.
   *
   * -- GETTER --
   *
   * @return The spec for a template link page post as used in Dynamic Product Ads.
   */
  @Getter
  @Setter
  @Facebook("template_data")
  private AdCreativeLinkData templateData;

  /**
   * The spec for a text page post.
   *
   * -- GETTER --
   *
   * @return The spec for a text page post.
   */
  @Getter
  @Setter
  @Facebook("text_data")
  private AdCreativeTextData textData;

  /**
   * The spec for a video page post.
   *
   * -- GETTER --
   *
   * @return The spec for a video page post.
   */
  @Getter
  @Setter
  @Facebook("video_data")
  private AdCreativeVideoData videoData;
}
