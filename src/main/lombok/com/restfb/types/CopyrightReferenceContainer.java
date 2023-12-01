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
package com.restfb.types;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Ported from <a href="https://github.com/facebook/facebook-java-business-sdk/blob/189b22281f75681939750fc71ae1429651865867/src/main/java/com/facebook/ads/sdk/CopyrightReferenceContainer.java">
 * Facebook Business Ads SDK</a>
 */
public class CopyrightReferenceContainer extends FacebookType {

  @Getter
  @Setter
  @Facebook("content_type")
  private String contentType;

  @Getter
  @Setter
  @Facebook("copyright_creation_time")
  private String copyrightCreationTime;

  @Getter
  @Setter
  @Facebook("download_hd_url")
  private String downloadHdUrl;

  @Getter
  @Setter
  @Facebook("duration_in_sec")
  private Double durationInSec;

  @Getter
  @Setter
  @Facebook("fingerprint_validity")
  private String fingerprintValidity;

  @Getter
  @Setter
  @Facebook("iswc")
  private String iswc;

  @Getter
  @Setter
  @Facebook("published_time")
  private Date publishedTime;

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
  @Facebook("universal_content_id")
  private String universalContentId;

  @Facebook("writer_names")
  private List<String> writerNames = new ArrayList<>();

  public List<String> getWriterNames() {
    return unmodifiableList(writerNames);
  }

  public boolean addWriterName(String writerName) {
    return writerNames.add(writerName);
  }

  public boolean removeWriterName(String writerName) {
    return writerNames.remove(writerName);
  }
}
