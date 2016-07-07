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

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-creative-link-data/">AdCreativeLinkData</a>
 * Marketing API type
 */
public class AdCreativeLinkData extends AbstractFacebookType {

  /**
   * The index (zero based) of the image from the additionalimages array to use as the ad image for a dynamic product ad
   *
   * -- GETTER --
   *
   * @return The index (zero based) of the image from the additionalimages array to use as the ad image for a dynamic
   *         product ad
   */
  @Getter
  @Setter
  @Facebook("additional_image_index")
  private Long additionalImageIndex;

  /**
   * Native deeplinks attached to the post
   *
   * -- GETTER --
   *
   * @return Native deeplinks attached to the post
   */
  @Getter
  @Setter
  @Facebook("app_link_spec")
  private AdCreativeLinkDataAppLinkSpec appLinkSpec;

  /**
   * The style of the attachment.
   *
   * -- GETTER --
   *
   * @return The style of the attachment.
   */
  @Getter
  @Setter
  @Facebook("attachment_style")
  private String attachmentStyle;

  /**
   * The branded content sponsor page id.
   *
   * -- GETTER --
   *
   * @return The branded content sponsor page id.
   */
  @Getter
  @Setter
  @Facebook("branded_content_sponsor_page_id")
  private String brandedContentSponsorPageId;

  /**
   * An optional call to action button. If not specified, on Instagram, a default CTA would be used,
   * <code>{"type":"LEARN_MORE","value": {"link":<LINK VALUE OF LINK_DATA>,}}</code>.
   *
   * -- GETTER --
   *
   * @return An optional call to action button
   */
  @Getter
  @Setter
  @Facebook("call_to_action")
  private AdCreativeLinkDataCallToAction callToAction;

  /**
   * If canvas experience is enabled post click
   *
   * -- GETTER --
   *
   * @return If canvas experience is enabled post click
   */
  @Getter
  @Setter
  @Facebook("canvas_enabled")
  private Boolean canvasEnabled;

  /**
   * Link caption. Overwrites the caption under the title in the link on Facebook. See post for more info. This setting
   * is not used on Instagram.
   *
   * -- GETTER --
   *
   * @return Link caption.
   */
  @Getter
  @Setter
  @Facebook
  private String caption;
  @Facebook("child_attachments")

  /**
   *
   */
  @Getter
  @Setter
  private List<AdCreativeLinkDataChildAttachment> childAttachments;

  /**
   * Link description. Overwrites the description in the link on Facebook. See post for more info. This setting is not
   * used on Instagram.
   *
   * -- GETTER --
   *
   * @return Link description.
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * The id of a Facebook event. This is only to be used if this creative is for a Website Clicks campaign, the Call To
   * Action is Buy Tickets, and the link points to the ticketing website of this Facebook event.
   *
   * -- GETTER --
   *
   * @return The id of a Facebook event.
   */
  @Getter
  @Setter
  @Facebook("event_id")
  private String eventId;

  /**
   * Whether to force the post to render in a single link format
   *
   * -- GETTER --
   *
   * @return Whether to force the post to render in a single link format
   */
  @Getter
  @Setter
  @Facebook("force_single_link")
  private Boolean forceSingleLink;

  /**
   * How to the image should be cropped. Different placements use different crop specs. For example, Facebook News Feed
   * uses the crop spec with 191x100 key, and Instagram uses 100x100 crop spec.
   *
   * -- GETTER --
   *
   * @return How to the image should be cropped.
   */
  @Getter
  @Setter
  @Facebook("image_crops")
  private AdsImageCrops imageCrops;

  /**
   * Hash of an image in your image library with Facebook. Specify this field or picture but not both
   *
   * -- GETTER --
   *
   * @return Hash of an image in your image library with Facebook.
   */
  @Getter
  @Setter
  @Facebook("image_hash")
  private String imageHash;

  /**
   * Link url. See post for more info. This field is required for a carousel ad .
   *
   * -- GETTER --
   *
   * @return Link url
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * The main body of the post. See post for more info. This field is required for a carousel ad .
   *
   * -- GETTER --
   *
   * @return The main body of the post
   */
  @Getter
  @Setter
  @Facebook
  private String message;

  /**
   * If set to false, removes the end card which displays the page icon. Default is true. Used by carousel ads.
   *
   * -- GETTER --
   *
   * @return If set to false, removes the end card which displays the page icon.
   */
  @Getter
  @Setter
  @Facebook("multi_share_end_card")
  private Boolean multiShareEndCard;

  /**
   * If set to true, automatically select and order images and links. Default is true. Used by carousel ads.
   *
   * -- GETTER --
   *
   * @return If set to true, automatically select and order images and links.
   */
  @Getter
  @Setter
  @Facebook("multi_share_optimized")
  private Boolean multiShareOptimized;

  /**
   * Name of the link. Overwrites the title of the link preview. See post for more info.
   *
   * -- GETTER --
   *
   * @return Name of the link.
   */
  @Getter
  @Setter
  @Facebook
  private String name;

  /**
   * URL of a picture to use in the post. Specify this field or image_hash but not both. See post for more info. The
   * image specified at the URL will be saved into the ad accounts image library
   *
   * -- GETTER --
   *
   * @return URL of a picture to use in the post.
   */
  @Getter
  @Setter
  @Facebook
  private String picture;
}
