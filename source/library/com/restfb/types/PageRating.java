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
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.JsonObject;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/v1.0/page/ratings">Cover Graph API
 * type</a>.
 * 
 * @author Anand Hemmige
 * @author Venil Noronha
 * @since 1.6.16
 */
public class PageRating extends FacebookType {

  @Facebook("start_time")
  private String rawStartTime;

  /**
   * Time the rating took place
   * 
   * @return Time the rating took place
   */
  @Getter
  @Setter
  private Date startTime;

  @Facebook("publish_time")
  private String rawPublishTime;

  /**
   * Time the rating took place
   *
   * @return Time the rating took place
   */
  @Getter
  @Setter
  private Date publishTime;

  /**
   * Person who rated the page
   * 
   * @return Person who rated the page
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType from;

  @Getter
  @Setter
  @Facebook
  private String message;

  @Getter
  @Setter
  @Facebook
  private Application application;

  @Getter
  @Setter
  @Facebook("no_feed_story")
  private Boolean noFeedStory;

  @Getter
  @Setter
  @Facebook
  private Comments comments;

  @Getter
  @Setter
  @Facebook
  private Likes likes;

  @Facebook
  private JsonObject data;

  @Getter
  @Setter
  private Double ratingValue;

  @Getter
  @Setter
  private Long ratingScale;

  @Getter
  @Setter
  private String reviewText;

  @Getter
  @Setter
  private Boolean isDraft;

  @Getter
  @Setter
  private String language;

  @Getter
  @Setter
  private Place place;

  @JsonMappingCompleted
  void convertTime() {
    publishTime = toDateFromLongFormat(rawPublishTime);
    startTime = toDateFromLongFormat(rawStartTime);
  }

  @JsonMappingCompleted
  void fillAdditionalValues(JsonMapper mapper) {
    if (data != null) {
      if (data.has("rating")) {
        JsonObject rating = data.getJsonObject("rating");
        ratingValue = rating.getDouble("value");
        ratingScale = rating.getLong("scale");
      }
      if (data.has("review_text")) {
        reviewText = data.getString("review_text");
      }
      if (data.has("is_draft")) {
        isDraft = data.getBoolean("is_draft");
      }
      if (data.has("language")) {
        language = data.getString("language");
      }
      if (data.has("generic_place")) {
        place = mapper.toJavaObject(data.get("generic_place").toString(), Place.class);
      } else if ( data.has("seller") ) {
        place = mapper.toJavaObject(data.get("seller").toString(), Place.class);
      }
    }
  }

}
