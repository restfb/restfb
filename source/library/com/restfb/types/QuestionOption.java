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
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/question_option" >QuestionOption Graph API
 * type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.10
 * @Deprecated removed after Graph API 1.0
 */
@Deprecated
public class QuestionOption extends NamedFacebookType {

  /**
   * User who asked the question.
   * 
   * @return User who asked the question.
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType from;

  /**
   * Number of votes this option has received.
   * 
   * @return Number of votes this option has received.
   */
  @Getter
  @Setter
  @Facebook
  private Integer votes;

  /**
   * Optional page associated with this option.
   * 
   * @return Optional page associated with this option.
   */
  @Getter
  @Setter
  @Facebook
  private CategorizedFacebookType object;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * Time when option was created.
   * 
   * @return Time when option was created.
   */
  @Getter
  @Setter
  private Date createdTime;

  private static final long serialVersionUID = 1L;

  @JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
  }
}