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
package com.restfb.types.ads;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.json.JsonObject;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-asset-feed-spec">Ad Asset
 * Feed Spec Type</a>.
 */
public class AdAssetFeedSpec extends BaseAdsObject {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook("ad_formats")
  private List<String> adFormats = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("additional_data")
  private AdAssetFeedAdditionalData additionalData;

  @Getter
  @Setter
  @Facebook("asset_customization_rules")
  private List<JsonObject> assetCustomizationRules = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private List<String> autotranslate = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private List<AdAssetFeedSpecBody> bodies = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("call_to_action_types")
  private List<String> callToActionTypes = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private List<AdAssetFeedSpecCaption> captions = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private List<JsonObject> carousels = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private List<AdAssetFeedSpecDescription> descriptions = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private List<AdAssetFeedSpecGroupRule> groups = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private List<AdAssetFeedSpecImage> images = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("link_urls")
  private List<AdAssetFeedSpecLinkURL> linkUrls = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("optimization_type")
  private String optimizationType;

  @Getter
  @Setter
  @Facebook
  private List<AdAssetFeedSpecTitle> titles = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private List<AdAssetFeedSpecVideo> videos = new ArrayList<>();
}
