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

import java.util.List;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-report-run">AdReportRun
 * type</a>
 */
public class AdReportRun extends BaseAdsObject {

  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  @Getter
  @Setter
  @Facebook("async_percent_completion")
  private Long asyncPercentCompletion;

  @Getter
  @Setter
  @Facebook("async_status")
  private String asyncStatus;

  @Getter
  @Setter
  @Facebook("date_start")
  private String dateStart;

  @Getter
  @Setter
  @Facebook("date_stop")
  private String dateStop;

  @Getter
  @Setter
  @Facebook
  private List<String> emails;

  @Getter
  @Setter
  @Facebook("friendly_name")
  private String friendlyName;

  @Getter
  @Setter
  @Facebook("is_bookmarked")
  private Boolean isBookmarked;

  @Getter
  @Setter
  @Facebook("is_running")
  private Boolean isRunning;

  @Getter
  @Setter
  @Facebook("schedule_id")
  private String scheduleId;

  @Getter
  @Setter
  @Facebook("time_completed")
  private Long timeCompleted;

  @Getter
  @Setter
  @Facebook("time_ref")
  private Long timeRef;

}
