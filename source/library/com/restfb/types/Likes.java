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

import static java.util.Collections.unmodifiableList;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

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
  private JsonObject summary;

  @Facebook
  private List<NamedFacebookType> data = new ArrayList<NamedFacebookType>();

  private static final long serialVersionUID = 1L;

  /**
   * The likes.
   *
   * @return The likes.
   */
  public List<NamedFacebookType> getData() {
    return unmodifiableList(data);
  }

  public boolean addData(NamedFacebookType like) {
    return data.add(like);
  }

  public boolean removeData(NamedFacebookType like) {
    return data.remove(like);
  }

  /**
   * add change count value, if summary is set and count is empty
   */
  @JsonMappingCompleted
  private void fillTotalCount() {
    if (totalCount == 0 && summary != null && summary.has("total_count")) {
      totalCount = summary.getLong("total_count");
    }

    if (openGraphCount != 0) {
      totalCount = openGraphCount;
    }
  }

  @JsonMappingCompleted
  private void fillHasLiked() {
    if (summary != null && summary.has("has_liked")) {
      hasLiked = summary.getBoolean("has_liked");
    }

    if (hasLiked == null && openGraphUserLikes != null) {
      hasLiked = openGraphUserLikes;
    }
  }

  @JsonMappingCompleted
  private void fillCanLike() {
    if (summary != null && summary.has("can_like")) {
      canLike = summary.getBoolean("can_like");
    }

    if (canLike == null && openGraphCanLike != null) {
      canLike = openGraphCanLike;
    }
  }
}
