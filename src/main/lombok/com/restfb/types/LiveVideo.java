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

import java.util.Date;

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/live-video/">Live Video API
 * type</a>.
 */
public class LiveVideo extends FacebookType {

  @Getter
  @Setter
  @Facebook("broadcast_start_time")
  private Date broadcastStartTime;

  @Getter
  @Setter
  @Facebook("creation_time")
  private Date creationTime;

  @Facebook
  @Getter
  @Setter
  private String description;

  @Getter
  @Setter
  @Facebook("embed_html")
  private String embedHtml;

  @Getter
  @Setter
  @Facebook("is_manual_mode")
  private Boolean isManualMode;

  @Getter
  @Setter
  @Facebook("is_reference_only")
  private Boolean isReferenceOnly;

  @Getter
  @Setter
  @Facebook("live_views")
  private Long liveViews;

  @Getter
  @Setter
  @Facebook("permalink_url")
  private String permalinkUrl;

  @Getter
  @Setter
  @Facebook("planned_start_time")
  private Date plannedStartTime;

  @Getter
  @Setter
  @Facebook("preview_url")
  private String previewUrl;

  @Getter
  @Setter
  @Facebook("seconds_left")
  private Integer secondsLeft;

  @Getter
  @Setter
  @Facebook("secure_stream_url")
  private String secureStreamUrl;

  @Getter
  @Setter
  @Facebook
  private String source;

  @Getter
  @Setter
  @Facebook
  private String status;

  @Getter
  @Setter
  @Facebook("stream_url")
  private String streamUrl;

  @Getter
  @Setter
  @Facebook
  private String title;

  @Getter
  @Setter
  @Facebook
  private Video video;

  @JsonMapper.JsonMappingCompleted
  private void provideFallback() {
    if (getType() == null && source != null) {
      setType(source);
    }

    if (getType() != null && source == null) {
      setSource(getType());
    }
  }

}
