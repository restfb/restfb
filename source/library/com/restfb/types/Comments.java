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

import static java.util.Collections.unmodifiableList;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.JsonObject;
import com.restfb.util.ReflectionUtils;

import java.io.Serializable;
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
public class Comments implements Serializable {

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

  @Getter
  @Setter
  private Boolean canComment;

  @Facebook
  private JsonObject summary;

  @Facebook
  private List<Comment> data = new ArrayList<Comment>();

  private static final long serialVersionUID = 1L;

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ReflectionUtils.hashCode(this);
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object that) {
    return ReflectionUtils.equals(this, that);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ReflectionUtils.toString(this);
  }

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
    if (totalCount == 0 && summary != null && summary.get("total_count") != null) {
      totalCount = summary.getLong("total_count", totalCount);
    }
  }

  /**
   * set the order the comments are sorted
   */
  @JsonMappingCompleted
  private void fillOrder() {
    if (summary != null) {
      order = summary.getString("order", order);
    }
  }

  /**
   * set the can_comment
   */
  @JsonMappingCompleted
  private void fillCanComment() {
    if (summary != null && summary.get("can_comment") != null) {
      canComment = summary.get("can_comment").asBoolean();
    }
  }
}
