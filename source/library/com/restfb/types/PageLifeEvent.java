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
import com.restfb.JsonMapper.JsonMappingCompleted;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/life-event">Life Event Graph API</a>
 * type.
 * 
 * @author <a href="http://restfb.com">Quang Pham Le Duy</a>
 * @since 2.0.0
 */
public class PageLifeEvent extends FacebookType {

  private static final long serialVersionUID = 1L;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * The time when this milestone was published
   * 
   * @return The time when this milestone was published
   */
  @Getter
  @Setter
  private Date createdTime;

  /**
   * Description of the milestone.
   * 
   * @return Description of the milestone.
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  @Facebook("end_time")
  private String rawEndTime;

  /**
   * The time when this milestone came to an end.
   * 
   * @return The time when this milestone came to an end.
   */
  @Getter
  @Setter
  private Date endTime;

  /**
   * The information of the Page that owns the milestone.
   * 
   * @return The information of the Page that owns the milestone.
   */
  @Getter
  @Setter
  @Facebook("from")
  private Page fromPage;

  /**
   * Whether the milestone is hidden or not.
   *
   * @return true if the post is hidden, otherwise return false.
   */
  @Getter
  @Setter
  @Facebook("is_hidden")
  private Boolean isHidden;

  @Facebook("start_time")
  private String rawStartTime;

  /**
   * The time when this milestone was started.
   * 
   * @return The time when this milestone was started.
   */
  @Getter
  @Setter
  private Date startTime;

  /**
   * The title of the milestone.
   * 
   * @return The title of the milestone.
   */
  @Getter
  @Setter
  @Facebook
  private String title;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  /**
   * The time when this milestone was updated.
   * 
   * @return The time when this milestone was updated.
   */
  @Getter
  @Setter
  private Date updatedTime;

  @JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
    endTime = toDateFromLongFormat(rawEndTime);
    startTime = toDateFromLongFormat(rawStartTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }

}
