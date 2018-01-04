/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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

import static com.restfb.util.DateUtils.toDateFromLongFormat;

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/live-video/">Live Video API type</a>.
 */
public class LiveVideo extends FacebookType {

  @Facebook("broadcast_start_time")
  private String rawBroadcastStartTime;

  @Getter
  @Setter
  private Date broadcastStartTime;

  @Facebook("creation_time")
  private String rawCreationTime;

  @Getter
  @Setter
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

  @Facebook("planned_start_time")
  private String rawPlannedStartTime;

  @Getter
  @Setter
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
  void convertTime() {
    broadcastStartTime = toDateFromLongFormat(rawBroadcastStartTime);
    creationTime = toDateFromLongFormat(rawCreationTime);
    plannedStartTime = toDateFromLongFormat(rawPlannedStartTime);
  }
}
