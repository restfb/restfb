/**
 * Copyright (c) 2010-2017 Mark Allen, Norbert Bartels.
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

import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class AdCreativeVideoData extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook("additional_image_index")
  private Long additionalImageIndex;

  @Getter
  @Setter
  @Facebook("branded_content_sponsor_page_id")
  private String brandedContentSponsorPageId;

  @Getter
  @Setter
  @Facebook("branded_content_sponsor_relationship")
  private String brandedContentSponsorRelationship;

  @Getter
  @Setter
  @Facebook("call_to_action")
  private AdCreativeLinkDataCallToAction callToAction;

  @Getter
  @Setter
  @Facebook
  private String description;

  @Getter
  @Setter
  @Facebook("link_description")
  private String linkDescription;

  @Getter
  @Setter
  @Facebook
  private String message;

  @Getter
  @Setter
  @Facebook("image_hash")
  private String imageHash;

  @Getter
  @Setter
  @Facebook("image_url")
  private String imageUrl;

  @Getter
  @Setter
  @Facebook("offer_id")
  private String offerId;

  @Getter
  @Setter
  @Facebook("page_welcome_message")
  private String pageWelcomeMessage;

  @Getter
  @Setter
  @Facebook("post_click_configuration")
  private AdCreativePostClickConfiguration postClickConfiguration;

  @Getter
  @Setter
  @Facebook("retailer_item_ids")
  private List<String> retailerItemIds;

  @Getter
  @Setter
  @Facebook
  private Targeting targeting;

  @Getter
  @Setter
  @Facebook
  private String title;

  @Getter
  @Setter
  @Facebook("video_id")
  private String videoId;
}
