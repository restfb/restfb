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

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;
import com.restfb.types.features.HasCreatedTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/video/likes/">Video Likes Graph API
 * type</a> and the <a href="https://developers.facebook.com/docs/graph-api/reference/post/likes/">Post Likes Graph API
 * type</a>
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Likes extends AbstractFacebookType {

  /**
   * The number of likes.
   *
   * @return The number of likes.
   */
  @Getter
  @Setter
  @Facebook
  private Long totalCount = 0L;

  /**
   * returns if the user can like the object
   *
   * @return if the user can like the object
   */
  @Getter
  @Setter
  private Boolean canLike;

  /**
   * returns if the user has liked the object
   *
   * @return if the user has liked the object
   */
  @Getter
  @Setter
  private Boolean hasLiked;

  @Facebook("can_like")
  private Boolean openGraphCanLike;

  @Facebook("user_likes")
  private Boolean openGraphUserLikes;

  @Facebook("count")
  private Long openGraphCount = 0L;

  @Facebook
  private String summary;

  @Facebook
  private List<LikeItem> data = new ArrayList<>();

  private static final long serialVersionUID = 1L;

  /**
   * The likes.
   *
   * @return The likes.
   */
  public List<LikeItem> getData() {
    return unmodifiableList(data);
  }

  public boolean addData(LikeItem like) {
    return data.add(like);
  }

  public boolean removeData(LikeItem like) {
    return data.remove(like);
  }

  @JsonMappingCompleted
  private void fillFields() {
    JsonObject summaryObject = null;
    if (summary != null) {
      summaryObject = Json.parse(summary).asObject();
    }
    fillTotalCount(summaryObject);
    fillHasLiked(summaryObject);
    fillCanLike(summaryObject);
  }

  /**
   * add change count value, if summary is set and count is empty
   */
  private void fillTotalCount(JsonObject summary) {
    if (totalCount == 0 && summary != null && summary.get("total_count") != null) {
      totalCount = summary.getLong("total_count", totalCount);
    }

    if (openGraphCount != 0) {
      totalCount = openGraphCount;
    }
  }

  /**
   * fill <code>has_liked</code> from summary, in case of open graph object use user_likes instead
   */
  private void fillHasLiked(JsonObject summary) {
    if (summary != null && summary.get("has_liked") != null) {
      hasLiked = summary.get("has_liked").asBoolean();
    }

    if (hasLiked == null && openGraphUserLikes != null) {
      hasLiked = openGraphUserLikes;
    }
  }

  private void fillCanLike(JsonObject summary) {
    if (summary != null && summary.get("can_like") != null) {
      canLike = summary.get("can_like").asBoolean();
    }

    if (canLike == null && openGraphCanLike != null) {
      canLike = openGraphCanLike;
    }
  }

  public static class LikeItem extends NamedFacebookType implements HasCreatedTime {

    /**
     * created time is the date the Like was created.
     * <p>
     * may be null if Facebook does not provide this information
     */
    @Getter(onMethod_ = {@Override})
    @Setter
    @Facebook("created_time")
    private Date createdTime;

  }
}
