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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.exception.FacebookJsonMappingException;

class IgAudioTest extends AbstractJsonMapperTests {

  @Test
  void checkSearchResponseJson() {
    IgAudioResponse response =
        createJsonMapper().toJavaObject(jsonFromClasspath("instagram/audio-response"), IgAudioResponse.class);

    assertNotNull(response);
    assertEquals(2, response.getAudio().size());

    IgAudio musicAudio = response.getAudio().get(0);
    assertEquals("587784541076604", musicAudio.getAudioId());
    assertEquals("https://scontent.example/cover.jpg", musicAudio.getCoverArtworkThumbnailUrl());
    assertEquals("https://scontent.example/cover.jpg", musicAudio.getCoverArtworkThumbnailUri());
    assertEquals("Shuba", musicAudio.getDisplayArtist());
    assertEquals(153760, musicAudio.getDurationInMs());
    assertEquals("music", musicAudio.getAudioType());
    assertEquals("Birthday Wish", musicAudio.getTitle());
    assertEquals("https://scontent.example/audio.mp3", musicAudio.getDownloadUrl());
    assertEquals(Boolean.FALSE, musicAudio.getIsAdsEligible());
    assertEquals("https://www.instagram.com/reels/audio/587784541076604/", musicAudio.getOnPlatformAudioPreviewLink());
    assertNull(musicAudio.getIgUsername());
    assertNull(musicAudio.getProfilePictureUrl());

    IgAudio originalSoundAudio = response.getAudio().get(1);
    assertEquals("1234567890", originalSoundAudio.getAudioId());
    assertEquals("original_sound", originalSoundAudio.getAudioType());
    assertEquals("restfb", originalSoundAudio.getIgUsername());
    assertEquals("https://scontent.example/profile.jpg", originalSoundAudio.getProfilePictureUrl());
    assertNull(originalSoundAudio.getDisplayArtist());
    assertNull(originalSoundAudio.getCoverArtworkThumbnailUrl());
    assertNull(originalSoundAudio.getCoverArtworkThumbnailUri());
    assertNull(originalSoundAudio.getIsAdsEligible());
    assertNull(originalSoundAudio.getOnPlatformAudioPreviewLink());
  }

  @Test
  void checkMetadataJson() {
    IgAudio igAudio = createJsonMapper().toJavaObject(jsonFromClasspath("instagram/audio-metadata"), IgAudio.class);

    assertNotNull(igAudio);
    assertEquals("587784541076604", igAudio.getAudioId());
    assertEquals("https://scontent.example/cover-metadata.jpg", igAudio.getCoverArtworkThumbnailUrl());
    assertEquals("https://scontent.example/cover-metadata.jpg", igAudio.getCoverArtworkThumbnailUri());
    assertEquals("Shuba", igAudio.getDisplayArtist());
    assertEquals(153760, igAudio.getDurationInMs());
    assertEquals("music", igAudio.getAudioType());
    assertEquals("Birthday Wish", igAudio.getTitle());
    assertEquals("https://scontent.example/audio-metadata.mp3", igAudio.getDownloadUrl());
    assertEquals(Boolean.TRUE, igAudio.getIsAdsEligible());
    assertEquals("https://www.instagram.com/reels/audio/587784541076604/", igAudio.getOnPlatformAudioPreviewLink());
  }

  @Test
  void checkExplicitNullFields() {
    IgAudio igAudio = createJsonMapper().toJavaObject(
      "{\"is_ads_eligible\":null,\"on_platform_audio_preview_link\":null,"
          + "\"cover_artwork_thumbnail_url\":null,\"cover_artwork_thumbnail_uri\":null,\"download_url\":null}",
      IgAudio.class);

    assertNull(igAudio.getIsAdsEligible());
    assertNull(igAudio.getOnPlatformAudioPreviewLink());
    assertNull(igAudio.getCoverArtworkThumbnailUrl());
    assertNull(igAudio.getCoverArtworkThumbnailUri());
    assertNull(igAudio.getDownloadUrl());
  }

  @Test
  void checkCoverArtworkGettersPreferMatchingField() {
    IgAudio igAudio =
        createJsonMapper().toJavaObject("{\"cover_artwork_thumbnail_url\":\"https://scontent.example/metadata.jpg\","
            + "\"cover_artwork_thumbnail_uri\":\"https://scontent.example/search.jpg\"}",
          IgAudio.class);

    assertEquals("https://scontent.example/metadata.jpg", igAudio.getCoverArtworkThumbnailUrl());
    assertEquals("https://scontent.example/search.jpg", igAudio.getCoverArtworkThumbnailUri());
  }

  @Test
  void checkCoverArtworkFallbackWithExplicitNull() {
    IgAudio metadataAudio =
        createJsonMapper().toJavaObject("{\"cover_artwork_thumbnail_url\":\"https://scontent.example/metadata.jpg\","
            + "\"cover_artwork_thumbnail_uri\":null}",
          IgAudio.class);
    IgAudio searchAudio = createJsonMapper().toJavaObject("{\"cover_artwork_thumbnail_url\":null,"
        + "\"cover_artwork_thumbnail_uri\":\"https://scontent.example/search.jpg\"}",
      IgAudio.class);

    assertEquals("https://scontent.example/metadata.jpg", metadataAudio.getCoverArtworkThumbnailUri());
    assertEquals("https://scontent.example/search.jpg", searchAudio.getCoverArtworkThumbnailUrl());
  }

  @Test
  void checkAdsResponseWithEmptyDownloadUrl() {
    IgAudioResponse response = createJsonMapper().toJavaObject("{\"audio\":[{\"audio_id\":\"587784541076604\","
        + "\"cover_artwork_thumbnail_uri\":\"https://scontent.example/cover.jpg\","
        + "\"display_artist\":\"Shuba\",\"duration_in_ms\":153760,\"title\":\"Birthday Wish\",\"download_url\":\"\"}]}",
      IgAudioResponse.class);

    assertEquals(1, response.getAudio().size());
    IgAudio igAudio = response.getAudio().get(0);
    assertEquals("587784541076604", igAudio.getAudioId());
    assertEquals("https://scontent.example/cover.jpg", igAudio.getCoverArtworkThumbnailUrl());
    assertEquals("", igAudio.getDownloadUrl());
    assertNull(igAudio.getAudioType());
    assertNull(igAudio.getIsAdsEligible());
    assertNull(igAudio.getOnPlatformAudioPreviewLink());
  }

  @Test
  void checkEmptyJsonThrows() {
    assertThrows(FacebookJsonMappingException.class, () -> createJsonMapper().toJavaObject("", IgAudioResponse.class));
    assertThrows(FacebookJsonMappingException.class, () -> createJsonMapper().toJavaObject("", IgAudio.class));
  }

  @Test
  void checkNullJsonThrows() {
    assertThrows(FacebookJsonMappingException.class,
      () -> createJsonMapper().toJavaObject(null, IgAudioResponse.class));
    assertThrows(FacebookJsonMappingException.class, () -> createJsonMapper().toJavaObject(null, IgAudio.class));
  }

  @Test
  void checkMalformedJsonHandling() {
    assertThrows(FacebookJsonMappingException.class,
      () -> createJsonMapper().toJavaObject("{\"audio\": [", IgAudioResponse.class));
    assertThrows(FacebookJsonMappingException.class,
      () -> createJsonMapper().toJavaObject("{\"audio_id\":", IgAudio.class));
  }

  @Test
  void checkMissingFieldsDefaults() {
    IgAudioResponse response = createJsonMapper().toJavaObject("{}", IgAudioResponse.class);

    assertNotNull(response);
    assertNotNull(response.getAudio());
    assertEquals(0, response.getAudio().size());

    IgAudio igAudio = createJsonMapper().toJavaObject("{}", IgAudio.class);

    assertNotNull(igAudio);
    assertNull(igAudio.getAudioId());
    assertNull(igAudio.getTitle());
    assertNull(igAudio.getDurationInMs());
    assertNull(igAudio.getCoverArtworkThumbnailUrl());
    assertNull(igAudio.getCoverArtworkThumbnailUri());
    assertNull(igAudio.getIsAdsEligible());
    assertNull(igAudio.getOnPlatformAudioPreviewLink());
  }
}
