/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/object/comments">Comments Graph API
 * type</a>.
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
   * The count of comments on this node.
   * 
   * <p>
   * It is important to note that this value is changed depending on the {@code filter} modifier being used (where
   * comment replies are available):
   * </p>
   * <ul>
   * <li>if filter is {@code stream} then total_count will be a count of all comments (including replies) on the node.
   * </li>
   * <li>if filter is {@code toplevel} then total_count will be a count of all top-level comments on the node.</li>
   * </ul>
   * <p>
   * {@code total_count} can be greater than or equal to the actual number of comments returned due to privacy or
   * deletion
   * </p>
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

  /**
   * Order in which comments were returned.
   * 
   * <p>
   * <code>ranked</code> indicates the most interesting comments are sorted first.<br>
   * <code>chronological</code> indicates comments are sorted by the oldest comments first.
   * </p>
   *
   * @return the order of the comments
   */
  @Getter
  @Setter
  private String order;

  @Getter
  @Setter
  private Boolean canComment;

  @Facebook("comment_order")
  private String openGraphCommentOrder;

  @Facebook("can_comment")
  private Boolean openGraphCanComment;

  @Facebook
  private Long count = 0L;

  @Facebook
  private String summary = null;

  @Facebook
  private List<Comment> data = new ArrayList<>();

  private static final long serialVersionUID = 1L;

  /**
   * The comments.
   *
   * @return The comments.
   */
  public List<Comment> getData() {
    return unmodifiableList(data);
  }

  /**
   * Adds a comment
   *
   * @param comment
   *          the comment that should be added
   * @return true if the comment was added, false otherwise
   */
  public boolean addData(Comment comment) {
    return data.add(comment);
  }

  /**
   * remove a comment
   *
   * @param comment
   *          the comment that should be removed
   * @return true if the comment was removed, false otherwise
   */
  public boolean removeData(Comment comment) {
    return data.remove(comment);
  }

  @JsonMappingCompleted
  private void fillFields() {
    JsonObject summaryObject = null;
    if (summary != null) {
      summaryObject = Json.parse(summary).asObject();
    }
    fillTotalCount(summaryObject);
    fillOrder(summaryObject);
    fillCanComment(summaryObject);
  }

  /**
   * set total count if summary is present
   */
  private void fillTotalCount(JsonObject summary) {
    if (totalCount == 0 && summary != null && summary.get("total_count") != null) {
      totalCount = summary.getLong("total_count", totalCount);
    }

    if (totalCount == 0 && count != 0) {
      totalCount = count;
    }
  }

  /**
   * set the order the comments are sorted
   */
  private void fillOrder(JsonObject summary) {
    if (summary != null) {
      order = summary.getString("order", order);
    }

    if (order == null && openGraphCommentOrder != null) {
      order = openGraphCommentOrder;
    }
  }

  /**
   * set the can_comment
   */
  private void fillCanComment(JsonObject summary) {
    if (summary != null && summary.get("can_comment") != null) {
      canComment = summary.get("can_comment").asBoolean();
    }

    if (canComment == null && openGraphCanComment != null) {
      canComment = openGraphCanComment;
    }
  }
}
