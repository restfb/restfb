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

  /**
   * The actor ID (Page ID) of this creative.
   *
   * This field is available only for mobile app ads created before 2015
   *
   * -- GETTER --
   *
   * @return The actor ID (Page ID) of this creative.
   */
  @Getter
  @Setter
  @Facebook("actor_id")
  private String actorId;

  /**
   * The image used for actor's icon.
   *
   * This field is available only for mobile app ads created before 2015
   *
   * -- GETTER --
   *
   * @return The image used for actor's icon.
   */
  @Getter
  @Setter
  @Facebook("actor_image_hash")
  private String actorImageHash;

  /**
   * The URL of the icon for the actor (Page ID) of this creative.
   *
   * This field is only available for mobile app ads created before 2015
   *
   * -- GETTER --
   *
   * @return The URL of the icon for the actor (Page ID) of this creative.
   */
  @Getter
  @Setter
  @Facebook("actor_image_url")
  private String actorImageUrl;

  /**
   * The title text used for actor.
   *
   * This field is available only for mobile app ads created before 2015
   *
   * -- GETTER --
   *
   * @return The title text used for actor.
   */
  @Getter
  @Setter
  @Facebook("actor_name")
  private String actorName;

  /**
   * Ad Labels that are associated with this creative
   *
   * -- GETTER --
   *
   * @return Ad Labels that are associated with this creative
   */
  @Getter
  @Setter
  @Facebook("adlabels")
  private List<AdLabel> adlabels;

  /**
   * Deep link fallback behavior for dynamic product ads if the app is not installed.
   *
   * -- GETTER --
   *
   * Deep link fallback behavior for dynamic product ads if the app is not installed.
   */
  @Getter
  @Setter
  @Facebook("applink_treatment")
  private String applinkTreatment;

  /**
   * The body of the ad
   *
   * -- GETTER --
   *
   * @return The body of the ad
   */
  @Getter
  @Setter
  @Facebook
  private String body;

  /**
   * The call to action button text and header text of legacy ads.
   *
   * -- GETTER --
   *
   * @return The call to action button text and header text of legacy ads.
   */
  @Getter
  @Setter
  @Facebook("call_to_action_type")
  private String callToActionType;

  /**
   * A JSON object defining crop dimensions for the image specified.
   *
   * See image crop reference for more details
   *
   * -- GETTER --
   *
   * @return A JSON object defining crop dimensions for the image specified.
   */
  @Getter
  @Setter
  @Facebook("image_crops")
  private AdsImageCrops imageCrops;

  /**
   * Image hash for an image you can use in creatives.
   *
   * See image library for more details
   *
   * -- GETTER --
   *
   * @return Image hash for an image you can use in creatives.
   */
  @Getter
  @Setter
  @Facebook("image_hash")
  private String imageHash;

  /**
   * A URL for the image for this creative.
   *
   * The image specified at this URL will be saved into the ad account's image library
   *
   * -- GETTER --
   *
   * @return A URL for the image for this creative.
   */
  @Getter
  @Setter
  @Facebook("image_url")
  private String imageUrl;

  /**
   * Instagram actor ID
   *
   * -- GETTER --
   *
   * @return Instagram actor ID
   */
  @Getter
  @Setter
  @Facebook("instagram_actor_id")
  private String instagramActorId;

  /**
   * Instagram permalink
   *
   * -- GETTER --
   *
   * @return Instagram permalink
   */
  @Getter
  @Setter
  @Facebook("instagram_permalink_url")
  private String instagramPermalinkUrl;

  /**
   * Instagram Story ID
   *
   * -- GETTER
   *
   * @return Instagram Story ID
   */
  @Getter
  @Setter
  @Facebook("instagram_story_id")
  private String instagramStoryId;

  /**
   * The Open Graph (OG) ID for the link in this creative if the landing page has OG tags
   *
   * -- GETTER --
   *
   * @return The Open Graph (OG) ID for the link in this creative if the landing page has OG tags
   */
  @Getter
  @Setter
  @Facebook("link_og_id")
  private String linkOgId;

  /**
   * Used to identify a specific landing tab on the Page (e.g. a Page tab app) by the Page tab's URL.
   *
   * See connection objects for retrieving Page tabs' URLs. app_data parameters may be added to the url to pass data to
   * a tab app
   *
   * -- GETTER --
   *
   * @return Used to identify a specific landing tab on the Page (e.g. a Page tab app) by the Page tab's URL.
   */
  @Getter
  @Setter
  @Facebook("link_url")
  private String linkUrl;

  /**
   * The ID of the promoted_object or object that is relevant to the ad and ad type
   *
   * -- GETTER --
   *
   * @return The ID of the promoted_object or object that is relevant to the ad and ad type
   */
  @Getter
  @Setter
  @Facebook("object_id")
  private String objectId;

  /**
   * The ID of a page post to use in an ad.
   *
   * This ID can be retrieved by using the graph API to query the posts of the page. If an image is used in the post, it
   * will be downloaded and available in your account's image library
   *
   * -- GETTER --
   *
   * @return The ID of a page post to use in an ad.
   */
  @Getter
  @Setter
  @Facebook("object_story_id")
  private String objectStoryId;

  /**
   * The page id and the content to create a new unpublished page post specified using one of link_data, photo_data,
   * video_data, offer_data, text_data or template_data
   *
   * -- GETTER --
   *
   * @return The page id and the content to create a new unpublished page post
   */
  @Getter
  @Setter
  @Facebook("object_story_spec")
  private AdCreativeObjectStorySpec objectStorySpec;

  /**
   * The type of object that is being advertised.
   *
   * Allowed values are:
   * <ul>
   * <li>PAGE</li>
   * <li>DOMAIN</li>
   * <li>EVENT</li>
   * <li>STORE_ITEM: refers to an iTunes or Google Play store destination</li>
   * <li>OFFER</li>
   * <li>SHARE: from a page</li>
   * <li>PHOTO</li>
   * <li>STATUS: of a page</li>
   * <li>VIDEO</li>
   * <li>APPLICATION: app on Facebook</li>
   * <li>INVALID: when an invalid object_id was specified such as a deleted object or if you do not have permission to
   * see the object. In very few cases, this field may be empty if Facebook is unable to identify the type of advertised
   * object</li>
   * </ul>
   *
   * -- GETTER --
   *
   * @return The type of object that is being advertised.
   */
  @Getter
  @Setter
  @Facebook("object_type")
  private String objectType;

  /**
   * Destination URL for a link ads not connected to a page
   *
   * -- GETTER --
   *
   * @return Destination URL for a link ads not connected to a page
   */
  @Getter
  @Setter
  @Facebook("object_url")
  private String objectUrl;

  /**
   * Use this field to customize the media for different Facebook placements.
   *
   * Currently you can use this field for customizing images only. The media specified here replaces the original media
   * defined in the ad creative when the ad displays on those placements. For example, if you define a media here for
   * the instagram key, Facebook uses that media instead of the media defined in the ad creative when showing the ad on
   * Instagram.
   *
   * -- GETTER --
   *
   * @return Use this field to customize the media for different Facebook placements.
   */
  @Getter
  @Setter
  @Facebook("platform_customizations")
  private String platformCustomizations;

  /**
   * The ID of the product set for this creative. See dynamic product ads for more detail
   *
   * -- GETTER --
   *
   * @return The ID of the product set for this creative. See dynamic product ads for more detail
   */
  @Getter
  @Setter
  @Facebook("product_set_id")
  private String productSetId;

  /**
   * The run status of this creative.
   *
   * Allowed values are:
   * <ul>
   * <li>ACTIVE</li>
   * <li>DELETED</li>
   * </ul>
   *
   * -- GETTER --
   *
   * @return The run status of this creative.
   */
  @Getter
  @Setter
  @Facebook("run_status")
  private String runStatus;

  /**
   * The Tracking URL for dynamic product ads.
   *
   * See dynamic product ads for more detail
   *
   * -- GETTER --
   *
   * @return The Tracking URL for dynamic product ads.
   */
  @Getter
  @Setter
  @Facebook("template_url")
  private String templateUrl;

  /**
   * The URL to a thumbnail for this creative.
   *
   * You can optionally request dimensions of this thumbnail by providing the thumbnail_width and thumbnail_height
   * parameters. See example for more detail
   *
   * -- GETTER --
   *
   * @return The URL to a thumbnail for this creative.
   */
  @Getter
  @Setter
  @Facebook("thumbnail_url")
  private String thumbnailUrl;

  /**
   * Title for a link ad (not connected to a Page)
   *
   * -- GETTER --
   *
   * @return Title for a link ad (not connected to a Page)
   */
  @Getter
  @Setter
  @Facebook("title")
  private String title;

  /**
   * A set of query string parameters which will replace or be appended to urls clicked from page post ads, and canvas
   * app install creatives only
   *
   * -- GETTER --
   *
   * @return A set of query string parameters
   */
  @Getter
  @Setter
  @Facebook("url_tags")
  private String urlTags;

}
