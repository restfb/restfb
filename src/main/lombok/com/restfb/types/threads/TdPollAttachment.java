/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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
package com.restfb.types.threads;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Poll attachment for threads.
 * As defined here: https://developers.facebook.com/docs/threads/create-posts/polls
 */
public class TdPollAttachment extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook("option_a")
  private String optionA;

  @Getter
  @Setter
  @Facebook("option_b")
  private String optionB;

  @Getter
  @Setter
  @Facebook("option_c")
  private String optionC;

  @Getter
  @Setter
  @Facebook("option_d")
  private String optionD;

  @Getter
  @Setter
  @Facebook("option_a_votes_percentage")
  private Double optionAVotesPercentage;

  @Getter
  @Setter
  @Facebook("option_b_votes_percentage")
  private Double optionBVotesPercentage;

  @Getter
  @Setter
  @Facebook("option_c_votes_percentage")
  private Double optionCVotesPercentage;

  @Getter
  @Setter
  @Facebook("option_d_votes_percentage")
  private Double optionDVotesPercentage;

  @Getter
  @Setter
  @Facebook("expiration_timestamp")
  private Date expirationTimestamp;
}
