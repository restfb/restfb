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
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;
import com.restfb.types.features.HasComments;
import com.restfb.types.features.HasFrom;
import com.restfb.types.features.HasMessage;

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
public class PageRating extends FacebookType implements HasComments, HasFrom, HasMessage {

  private static final long serialVersionUID = 1L;

  /**
   * Time the rating took place
   * 
   * @return Time the rating took place
   */
  @Getter
  @Setter
  @Facebook("start_time")
  private Date startTime;

  /**
   * Time the rating took place
   *
   * @return Time the rating took place
   */
  @Getter
  @Setter
  @Facebook("publish_time")
  private Date publishTime;

  /**
   * Person who rated the page
   * 
   * @return Person who rated the page
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook
  private NamedFacebookType from;

  @Getter(onMethod_ = {@Override})
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

  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook
  private Comments comments;

  @Getter
  @Setter
  @Facebook
  private Likes likes;

  @Facebook
  private String data;

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

  @Getter
  @Setter
  private RecommendationType recommendationType;

  public boolean isRecommendation() {
    return ratingValue == null;
  }

  @JsonMappingCompleted
  void fillAdditionalValues(JsonMapper mapper) {
    if (this.data != null) {
      JsonObject dataObject = Json.parse(this.data).asObject();
      if (dataObject.get("rating") != null) {
        JsonObject rating = dataObject.get("rating").asObject();
        if (rating != null) {
          ratingValue = rating.get("value").asDouble();
          ratingScale = rating.get("scale").asLong();
        }
      }

      if (dataObject.get("language") != null) {
        language = dataObject.get("language").asString();
      }
      if (dataObject.get("is_draft") != null) {
        isDraft = dataObject.get("is_draft").asBoolean();
      }
      if (dataObject.get("review_text") != null) {
        reviewText = dataObject.get("review_text").asString();
      }
      if (dataObject.get("generic_place") != null) {
        place = mapper.toJavaObject(dataObject.get("generic_place").toString(), Place.class);
      } else if (dataObject.get("seller") != null) {
        place = mapper.toJavaObject(dataObject.get("seller").toString(), Place.class);
      }
      if (dataObject.get("recommendation_type") != null) {
        try {
          String typeString = dataObject.get("recommendation_type").asString();
          recommendationType = RecommendationType.valueOf(typeString.toUpperCase());
        } catch (IllegalArgumentException iae) {
          // no enum value found ignore this
        }
      }
    }
  }

}
