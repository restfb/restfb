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

import static com.restfb.util.DateUtils.toDateFromLongFormat;

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-image">AdGroup Image type</a>
 */
public class AdImage extends NamedAdsObject {

  /**
   * The ad account that owns the image
   */
  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  /**
   * The hash which uniquely identifies the image.
   */
  @Getter
  @Setter
  @Facebook
  private String hash;

  /**
   * A temporary URL which the image can be retrieved at. NOTE: do not use this URL in ad creative creation
   */
  @Getter
  @Setter
  @Facebook
  private String url;

  /**
   * A temporary URL pointing to a version of the image resized to fit withing a 128x128 pixel box
   */
  @Getter
  @Setter
  @Facebook("url_128")
  private String url128;

  /**
   * A permanent URL of the image to use in story creatives.
   */
  @Getter
  @Setter
  @Facebook("permalink_url")
  private String permalinkUrl;

  /**
   * The width of the image.
   */
  @Getter
  @Setter
  @Facebook
  private String width;

  /**
   * The height of the image.
   */
  @Getter
  @Setter
  @Facebook
  private String height;

  /**
   * The width of the image that was originally uploaded.
   */
  @Getter
  @Setter
  @Facebook
  private String original_width;

  /**
   * The height of the image that was originally uploaded.
   */
  @Getter
  @Setter
  @Facebook
  private String original_height;

  /**
   * Status of the image
   */
  @Getter
  @Setter
  @Facebook
  private String status;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * Time the image was created
   */
  @Getter
  @Setter
  private Date createdTime;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  /**
   * Time the image was updated
   */
  @Getter
  @Setter
  private Date updatedTime;

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }

  private final List<AdCreative> creatives = new ArrayList<AdCreative>();

  public boolean addCreative(AdCreative creative) {
    return creatives.add(creative);
  }

  public boolean removeCreative(AdCreative creative) {
    return creatives.remove(creative);
  }

  /**
   * A list of ad creative IDs that this ad image is being used in.
   *
   * @return A list of ad creative IDs that this ad image is being used in
   */
  public List<AdCreative> getCreatives() {
    return Collections.unmodifiableList(creatives);
  }

}
