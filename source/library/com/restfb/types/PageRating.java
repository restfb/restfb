/**
 * Copyright (c) 2010-2015 Mark Allen, Norbert Bartels.
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

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/v1.0/page/ratings">Cover Graph API
 * type</a>.
 * 
 * @author Anand Hemmige
 * @since 1.6.16
 */
public class PageRating extends FacebookType {

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * Time the rating took place
   * 
   * @return Time the rating took place
   */
  @Getter
  @Setter
  private Date createdTime;

  /**
   * Person who rated the page
   * 
   * @return Person who rated the page
   */
  @Getter
  @Setter
  @Facebook("reviewer")
  private NamedFacebookType from;

  /**
   * The open graph story that generated this rating.
   * 
   * This also contains the likes and comments that attached to the story
   * 
   * @return the open graph story that generated this rating
   */
  @Getter
  @Setter
  @Facebook("open_graph_story")
  private Post story;

  /**
   * true if the person specified a rating.
   * 
   * only visible if the field is set in the request
   * 
   * @return true if the person specified a rating
   */
  @Getter
  @Setter
  @Facebook("has_rating")
  private boolean hasRating;

  /**
   * true if the person added a text review to the rating.
   * 
   * only visible if the field is set in the request
   * 
   * @return true if the person added a text review to the rating
   */
  @Getter
  @Setter
  @Facebook("has_review")
  private boolean hasReview;

  /**
   * Rating value of the review.
   * 
   * Value can be 1-5.
   * 
   * @return rating value of this review
   */
  @Getter
  @Setter
  @Facebook
  private int rating;

  /**
   * Review text included with the rating
   * 
   * @return Review text included with the rating
   */
  @Getter
  @Setter
  @Facebook("review_text")
  private String review;

  @JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
  }

}
