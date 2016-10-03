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
package com.restfb.types;

import com.restfb.Facebook;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/video-copyright/">Video
 * Copyright</a> type
 */
public class VideoCopyright extends FacebookType {

  /**
   * The copyright content ID
   */
  @Getter
  @Setter
  @Facebook("copyright_content_id")
  private String copyrightContentId;

  /**
   * The account that created the copyright
   */
  @Getter
  @Setter
  @Facebook
  private User creator;

  /**
   * Whether the video is monitored successfully for copyright.
   *
   * The status could be {@code NOT_EXAMED}, {@code COPYRIGHTED} and {@code ERROR}.
   *
   * @RestFB.GraphApi.Since 2.5
   */
  @Getter
  @Setter
  @Facebook("monitoring_status")
  private String monitoringStatus;

  /**
   * Whether the video is monitored for video, audio, or both
   */
  @Getter
  @Setter
  @Facebook("monitoring_type")
  private String monitoringType;

  /**
   * A string array of ISO 3166 format country codes, where the owner owns the rights of the content
   */
  @Getter
  @Setter
  @Facebook("ownership_countries")
  private List<String> ownershipCountries;

  /**
   * The ID of the reference video owner
   */
  @Getter
  @Setter
  @Facebook("reference_owner_id")
  private String referenceOwnerId;

  /**
   * A list of matching rules applied to the copyrighted content
   */
  @Getter
  @Setter
  @Facebook("rule_ids")
  private List<VideoCopyrightRule> ruleIds;

  /**
   * A list of page IDs or user IDs, who are allowed to use the copyrighted content.
   */
  @Getter
  @Setter
  @Facebook("whitelisted_ids")
  private List<String> whitelistedIds;

}
