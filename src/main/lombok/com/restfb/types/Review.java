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

import com.restfb.types.features.HasCreatedTime;
import com.restfb.types.features.HasFrom;
import com.restfb.types.features.HasMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/review">Review Graph API Type</a>
 * 
 * @author Norbert Bartels
 * @since 1.6.16
 */
public class Review extends FacebookType implements HasCreatedTime, HasFrom, HasMessage {

  private static final long serialVersionUID = 1L;

  /**
   * When the review was created.
   * 
   * @return when the review was created
   * @since 1.6.16
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook("created_time")
  private Date createdTime;

  /**
   * The user that created the review.
   * 
   * @return The user that created the review
   * @since 1.6.16
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook
  private NamedFacebookType from;

  /**
   * The review text, if any.
   * 
   * @return The review text, if any
   * @since 1.6.16
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook
  private String message;

  /**
   * The review rating.
   * 
   * @return The review rating
   * @since 1.6.16
   */
  @Getter
  @Setter
  @Facebook
  private int rating;

  /**
   * The app to which this review applies.
   * 
   * @return The app to which this review applies
   * @since 1.6.16
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType to;

}
