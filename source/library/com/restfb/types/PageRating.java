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
