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

  @Getter
  @Facebook("audio_id")
  private String audioId;

  // Meta's Audio API documentation uses "uri" for search responses and "url" for metadata responses.
  @Facebook("cover_artwork_thumbnail_url")
  private String coverArtworkThumbnailUrl;

  @Facebook("cover_artwork_thumbnail_uri")
  private String coverArtworkThumbnailUri;

  @Getter
  @Facebook("display_artist")
  private String displayArtist;

  @Getter
  @Facebook("duration_in_ms")
  private Integer durationInMs;

  @Getter
  @Facebook("audio_type")
  private String audioType;

  @Getter
  @Facebook
  private String title;

  @Getter
  @Facebook("download_url")
  private String downloadUrl;

  @Getter
  @Facebook("ig_username")
  private String igUsername;

  @Getter
  @Facebook("profile_picture_url")
  private String profilePictureUrl;

  public String getCoverArtworkThumbnailUrl() {
    return coverArtworkThumbnailUrl != null ? coverArtworkThumbnailUrl : coverArtworkThumbnailUri;
  }

  public String getCoverArtworkThumbnailUri() {
    return coverArtworkThumbnailUri != null ? coverArtworkThumbnailUri : coverArtworkThumbnailUrl;
  }
}
