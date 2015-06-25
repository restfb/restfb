/*
 * Copyright (c) 2010-2015 Mark Allen.
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
import com.restfb.util.ReflectionUtils;
import static java.util.Collections.unmodifiableList;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/event">Comment Graph API type</a>.
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Comment extends FacebookType {

  /**
   * User who posted the comment.
   *
   * @return User who posted the comment.
   */
  @Getter
  @Setter
  @Facebook
  private CategorizedFacebookType from;

  /**
   * Text contents of the comment.
   *
   * @return Text contents of the comment.
   */
  @Getter
  @Setter
  @Facebook
  private String message;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * Date on which the comment was created.
   *
   * @return Date on which the comment was created.
   */
  @Getter
  @Setter
  private Date createdTime;

  /**
   * The number of likes on this comment.
   *
   * @return The number of likes on this comment.
   * @deprecated As of September 5, 2012, Facebook is changing over to {@code like_count}, so this method will be
   *             replaced by {@link #likeCount}.
   */
  @Getter
  @Setter
  @Facebook
  private Long likes;

  /**
   * The number of likes on this comment.
   *
   * @return The number of likes on this comment.
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook("like_count")
  private Long likeCount;

  /**
   * Number of replies to this comment.
   *
   * @return Number of replies to this comment
   */
  @Getter
  @Setter
  @Facebook("comment_count")
  private long commentCount;

  /**
   * This field is returned only if the authenticated user can remove this comment.
   *
   * @return This field is returned only if the authenticated user can remove this comment.
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook("can_remove")
  private Boolean canRemove;

  /**
   * This field is returned only if the authenticated user likes this comment
   *
   * @return This field is returned only if the authenticated user likes this comment.
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook("user_likes")
  private Boolean userLikes;

  /**
   * If this comment is a reply, this field returns the parent comment, otherwise no value
   *
   * @return the parent Comment
   * @since 1.6.13
   */
  @Getter
  @Setter
  @Facebook
  private Comment parent;

  /**
   * Specifies whether you can reply to this comment
   *
   * @return can_comment
   * @since 1.6.13
   */
  @Getter
  @Setter
  @Facebook("can_comment")
  private boolean canComment;

  /**
   * Whether the viewer can hide this comment
   *
   * @return can_hide
   * @since 1.7.1
   */
  @Getter
  @Setter
  @Facebook("can_hide")
  private boolean canHide;

  /**
   * Whether this comment is hidden. The original poster can still see the comment, along with the page admin and anyone
   * else tagged in the comment
   *
   * @return is_hidden
   * @since 1.7.1
   */
  @Getter
  @Setter
  @Facebook("is_hidden")
  private Boolean isHidden;

  /**
   * Parent object this comment was made on.
   *
   * @return object
   * @since 1.7.1
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType object;

  /**
   * The replies to this comment
   *
   * @return replies
   */
  @Getter
  @Setter
  @Facebook("comments")
  private Comments comments;

  /**
   * Attachment (image) added to a comment.
   *
   * To force Facebook to fill the <code>attachment</code> field you have to fetch the comment with the
   * <code>fields=attachment</code> parameter, otherwise the attachments are <code>null</code>.
   *
   * @return Attachment on the comment
   */
  @Getter
  @Setter
  @Facebook
  private Attachment attachment;

  private static final long serialVersionUID = 2L;

  @JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
  }

  /**
   * Represents the Replies to a Comment</a>.
   *
   * @author <a href="http://ityx.de">Jan Schweizer</a>
   */
  public static class Comments implements Serializable {

    /**
     * The number of comments.
     *
     * @return The number of comments.
     */
    @Getter
    @Setter
    @Facebook
    private Long count = 0L;

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
  }

  /**
   * Media data as applicable for the attachment.
   *
   * @author <a href="http://ityx.de">Jan Schweizer</a>
   */
  public static class Media extends FacebookType {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook
    private Image image;
  }

  /**
   * Contains the attachment data for a specific comment (like an image or such)
   *
   * @author <a href="http://ityx.de">Jan Schweizer</a>
   */
  public static class Attachment extends FacebookType {

    @Getter
    @Setter
    @Facebook
    private String url;

    @Getter
    @Setter
    @Facebook
    private String title;

    @Getter
    @Setter
    @Facebook
    private String description;

    @Getter
    @Setter
    @Facebook
    private String type;

    @Getter
    @Setter
    @Facebook("media")
    private Media media;

  }

  /**
   * Image data as applicable for the attachment
   *
   * @author <a href="http://ityx.de">Jan Schweizer</a>
   */
  public static class Image extends FacebookType {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook
    private Integer height;

    @Getter
    @Setter
    @Facebook
    private Integer width;

    @Getter
    @Setter
    @Facebook
    private String src;

  }
}