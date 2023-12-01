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

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-asset-feed-spec-group-rule/">Ad Asset Feed
 * Spec Group Rule Type</a>.
 */
public class AdAssetFeedSpecGroupRule extends BaseAdsObject {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook("body_label")
  private AdAssetFeedSpecAssetLabel bodyLabel;

  @Getter
  @Setter
  @Facebook("caption_label")
  private AdAssetFeedSpecAssetLabel captionLabel;

  @Getter
  @Setter
  @Facebook("description_label")
  private AdAssetFeedSpecAssetLabel descriptionLabel;

  @Getter
  @Setter
  @Facebook("image_label")
  private AdAssetFeedSpecAssetLabel imageLabel;

  @Getter
  @Setter
  @Facebook("link_url_label")
  private AdAssetFeedSpecAssetLabel linkUrlLabel;

  @Getter
  @Setter
  @Facebook("title_label")
  private AdAssetFeedSpecAssetLabel titleLabel;

  @Getter
  @Setter
  @Facebook("video_label")
  private AdAssetFeedSpecAssetLabel videoLabel;
}
