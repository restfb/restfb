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
 * Represents an Instagram audio asset returned by the
 * <a href="https://developers.facebook.com/docs/instagram-platform/content-publishing/audio-api/">Instagram Audio
 * API</a>.
 */
@Setter
public class IgAudio extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * The unique identifier for the audio asset.
   */
  @Getter
  @Facebook("audio_id")
  private String audioId;

  /**
   * URL of the cover artwork thumbnail, returned for music only. May be {@code null} when no URL is available. Metadata
   * responses use {@code cover_artwork_thumbnail_url}.
   */
  @Facebook("cover_artwork_thumbnail_url")
  private String coverArtworkThumbnailUrl;

  /**
   * URL of the cover artwork thumbnail, returned for music only. May be {@code null} when no URL is available. Search
   * responses, including ads audio responses, use {@code cover_artwork_thumbnail_uri}.
   */
  @Facebook("cover_artwork_thumbnail_uri")
  private String coverArtworkThumbnailUri;

  /**
   * The display name of the artist, returned for music only.
   */
  @Getter
  @Facebook("display_artist")
  private String displayArtist;

  /**
   * Duration of the audio asset in milliseconds.
   */
  @Getter
  @Facebook("duration_in_ms")
  private Integer durationInMs;

  /**
   * The type of audio: {@code music} or {@code original_sound}.
   */
  @Getter
  @Facebook("audio_type")
  private String audioType;

  /**
   * The title of the audio asset.
   */
  @Getter
  @Facebook
  private String title;

  /**
   * A temporary URL to preview the audio file, which expires after approximately 1.5 days. May be {@code null} when no
   * preview URL is available, or empty in ads audio responses.
   */
  @Getter
  @Facebook("download_url")
  private String downloadUrl;

  /**
   * The Instagram username of the creator, returned for {@code original_sound} only.
   */
  @Getter
  @Facebook("ig_username")
  private String igUsername;

  /**
   * URL of the creator's profile picture, returned for {@code original_sound} only.
   */
  @Getter
  @Facebook("profile_picture_url")
  private String profilePictureUrl;

  /**
   * Whether the audio asset can be used in ads. May be {@code null} if eligibility is unknown or not returned.
   */
  @Getter
  @Facebook("is_ads_eligible")
  private Boolean isAdsEligible;

  /**
   * URL to preview the audio on Instagram. May be {@code null} when not available.
   */
  @Getter
  @Facebook("on_platform_audio_preview_link")
  private String onPlatformAudioPreviewLink;

  /**
   * Returns the cover artwork thumbnail URL from metadata responses, falling back to the URI from search responses.
   *
   * @return the cover artwork thumbnail URL, or {@code null} if neither field is available
   */
  public String getCoverArtworkThumbnailUrl() {
    return coverArtworkThumbnailUrl != null ? coverArtworkThumbnailUrl : coverArtworkThumbnailUri;
  }

  /**
   * Returns the cover artwork thumbnail URI from search responses, falling back to the URL from metadata responses.
   *
   * @return the cover artwork thumbnail URI, or {@code null} if neither field is available
   */
  public String getCoverArtworkThumbnailUri() {
    return coverArtworkThumbnailUri != null ? coverArtworkThumbnailUri : coverArtworkThumbnailUrl;
  }
}
