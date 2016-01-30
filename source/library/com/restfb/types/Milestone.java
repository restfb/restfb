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

import static com.restfb.util.DateUtils.toDateFromLongFormat;

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/milestone">Milestone Graph API
 * type</a>.
 */
public class Milestone extends FacebookType {

  /**
   * The title of the milestone
   *
   * @return The title of the milestone
   */
  @Getter
  @Setter
  @Facebook
  private String title;

  /**
   * The Page that posted the milestone.
   *
   * @return The Page that posted the milestone.
   */
  @Getter
  @Setter
  @Facebook
  private Page from;

  /**
   * The description of the milestone.
   *
   * @return The description of the milestone
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * The creation time of the milestone.
   *
   * @return The creation time of the milestone
   */
  @Getter
  @Setter
  private Date createdTime;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * The update time of the milestone.
   *
   * @return The update time of the milestone
   */
  @Getter
  @Setter
  private Date updatedTime;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  /**
   * The start time of the milestone.
   *
   * @return The start time of the milestone
   */
  @Getter
  @Setter
  private Date startTime;

  @Facebook("start_time")
  private String rawStartTime;

  /**
   * The end time of the milestone. Page milestones have the same start and end time.
   *
   * @return The end time of the milestone. Page milestones have the same start and end time
   */
  @Getter
  @Setter
  private Date endTime;

  @Facebook("end_time")
  private String rawEndTime;

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
    startTime = toDateFromLongFormat(rawStartTime);
    endTime = toDateFromLongFormat(rawEndTime);
  }

}
