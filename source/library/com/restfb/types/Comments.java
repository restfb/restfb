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
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Comments Graph API type</a>.
 *
 * <p>
 * Please request '{id}/comments?summary=true' explicitly if you would like the summary field which contains the count
 * (now called 'total_count')
 * </p>
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Comments extends AbstractFacebookType {

  /**
   * The number of comments.
   *
   * <p>
   * Please request '{id}/comments?summary=true' explicitly if you would like the summary field which contains the count
   * (now called 'total_count')
   * </p>
   *
   * @return The number of comments.
   */
  @Getter
  @Setter
  @Facebook("total_count")
  private Long totalCount = 0L;

  @Getter
  @Setter
  private String order;

  @Facebook("comment_order")
  private String openGraphCommentOrder;

  @Facebook("can_comment")
  private Boolean openGraphCanComment;

  @Facebook
  private Long count = 0L;

  /**
   * returns if the user can comment the object
   *
   * @return if the user can comment the object
   */
  @Getter
  @Setter
  private Boolean canComment;

  @Facebook
  private JsonObject summary = null;

  @Facebook
  private List<Comment> data = new ArrayList<Comment>();

  private static final long serialVersionUID = 1L;

  /**
   * @see java.lang.Object#hashCode()
   */
  /**
   * The comments.
   *
   * @return The comments.
   */
  public List<Comment> getData() {
    return unmodifiableList(data);
  }

  public boolean addData(Comment comment) {
    return data.add(comment);
  }

  public boolean removeData(Comment comment) {
    return data.remove(comment);
  }

  /**
   * set total count if summary is present
   */
  @JsonMappingCompleted
  private void fillTotalCount() {
    if (totalCount == 0 && summary != null && summary.has("total_count")) {
      totalCount = summary.getLong("total_count");
    }

    if (totalCount == 0 && count != 0) {
      totalCount = count;
    }
  }

  /**
   * set the order the comments are sorted
   */
  @JsonMappingCompleted
  private void fillOrder() {
    if (summary != null && summary.has("order")) {
      order = summary.getString("order");
    }

    if (order == null && openGraphCommentOrder != null) {
      order = openGraphCommentOrder;
    }
  }

  /**
   * set the can_comment
   */
  @JsonMappingCompleted
  private void fillCanComment() {
    if (summary != null && summary.has("can_comment")) {
      canComment = summary.getBoolean("can_comment");
    }

    if (canComment == null && openGraphCanComment != null) {
      canComment = openGraphCanComment;
    }
  }
}
