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

public class AdCreativeLinkData extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook("additional_image_index")
  private Long additionalImageIndex;

  @Getter
  @Setter
  @Facebook("app_link_spec")
  private AdCreativeLinkDataAppLinkSpec appLinkSpec;

  @Getter
  @Setter
  @Facebook("attachment_style")
  private String attachmentStyle;

  @Getter
  @Setter
  @Facebook("branded_content_sponsor_page_id")
  private String brandedContentSponsorPageId;

  @Getter
  @Setter
  @Facebook("call_to_action")
  private AdCreativeLinkDataCallToAction callToAction;

  @Getter
  @Setter
  @Facebook("canvas_enabled")
  private Boolean canvasEnabled;

  @Getter
  @Setter
  @Facebook
  private String caption;
  @Facebook("child_attachments")

  @Getter
  @Setter
  private List<AdCreativeLinkDataChildAttachment> childAttachments;

  @Getter
  @Setter
  @Facebook
  private String description;

  @Getter
  @Setter
  @Facebook("event_id")
  private String eventId;

  @Getter
  @Setter
  @Facebook("force_single_link")
  private Boolean forceSingleLink;

  @Getter
  @Setter
  @Facebook("image_crops")
  private AdsImageCrops imageCrops;

  @Getter
  @Setter
  @Facebook("image_hash")
  private String imageHash;

  @Getter
  @Setter
  @Facebook
  private String link;

  @Getter
  @Setter
  @Facebook
  private String message;

  @Getter
  @Setter
  @Facebook("multi_share_end_card")
  private Boolean multiShareEndCard;

  @Getter
  @Setter
  @Facebook("multi_share_optimized")
  private Boolean multiShareOptimized;

  @Getter
  @Setter
  @Facebook
  private String name;

  @Getter
  @Setter
  @Facebook
  private String picture;
}
