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

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/adcreative/">AdGroup Creative type</a>.
 */
public class AdCreative extends NamedAdsObject {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook("actor_id")
  private String actorId;

  @Getter
  @Setter
  @Facebook("actor_image_hash")
  private String actorImageHash;

  @Getter
  @Setter
  @Facebook("actor_image_url")
  private String actorImageUrl;

  @Getter
  @Setter
  @Facebook("actor_name")
  private String actorName;

  @Getter
  @Setter
  @Facebook("adlabels")
  private List<AdLabel> adlabels;

  @Getter
  @Setter
  @Facebook("applink_treatment")
  private String applinkTreatment;

  @Getter
  @Setter
  @Facebook
  private String body;

  @Getter
  @Setter
  @Facebook("call_to_action_type")
  private String callToActionType;

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
  @Facebook("image_url")
  private String imageUrl;

  @Getter
  @Setter
  @Facebook("instagram_actor_id")
  private String instagramActorId;

  @Getter
  @Setter
  @Facebook("instagram_permalink_url")
  private String instagramPermalinkUrl;

  @Getter
  @Setter
  @Facebook("instagram_story_id")
  private String instagramStoryId;

  @Getter
  @Setter
  @Facebook("link_og_id")
  private String linkOgId;

  @Getter
  @Setter
  @Facebook("link_url")
  private String linkUrl;

  @Getter
  @Setter
  @Facebook("object_id")
  private String objectId;

  @Getter
  @Setter
  @Facebook("object_story_id")
  private String objectStoryId;

  @Getter
  @Setter
  @Facebook("object_story_spec")
  private AdCreativeObjectStorySpec objectStorySpec;

  @Getter
  @Setter
  @Facebook("object_type")
  private String objectType;

  @Getter
  @Setter
  @Facebook("object_url")
  private String objectUrl;

  @Getter
  @Setter
  @Facebook("platform_customizations")
  private String platformCustomizations;

  @Getter
  @Setter
  @Facebook("product_set_id")
  private String productSetId;

  @Getter
  @Setter
  @Facebook("run_status")
  private String runStatus;

  @Getter
  @Setter
  @Facebook("template_url")
  private String templateUrl;

  @Getter
  @Setter
  @Facebook("thumbnail_url")
  private String thumbnailUrl;

  @Getter
  @Setter
  @Facebook("title")
  private String title;

  @Getter
  @Setter
  @Facebook("url_tags")
  private String urlTags;

  @Getter
  @Setter
  @Facebook("creative_id")
  private String creativeId;

}
