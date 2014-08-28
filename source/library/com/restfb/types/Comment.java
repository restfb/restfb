/*
 * Copyright (c) 2010-2014 Mark Allen.
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
import static java.util.Collections.unmodifiableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/event">Comment Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Comment extends FacebookType {
  @Facebook
  private CategorizedFacebookType from;

  @Facebook
  private String message;

  @Facebook("created_time")
  private String createdTime;

  @Facebook
  private Long likes;

  @Facebook("like_count")
  private Long likeCount;

  @Facebook("can_remove")
  private Boolean canRemove;

  @Facebook("user_likes")
  private Boolean userLikes;

  @Facebook
  private Comment parent;

  @Facebook("can_comment")
  private boolean canComment;

  @Facebook("comments")
  private Comments comments;

  @Facebook
  private Attachment attachment;

  private static final long serialVersionUID = 2L;

  /**
   * User who posted the comment.
   * 
   * @return User who posted the comment.
   */
  public CategorizedFacebookType getFrom() {
    return from;
  }

  /**
   * Attachment (image) added to a comment.
   * 
   * @return Attachment on the comment
   */
  public Attachment getAttachment() {
    return attachment;
  }

  /**
   * Text contents of the comment.
   * 
   * @return Text contents of the comment.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Date on which the comment was created.
   * 
   * @return Date on which the comment was created.
   */
  public Date getCreatedTime() {
    return toDateFromLongFormat(createdTime);
  }

  /**
   * The number of likes on this comment.
   * 
   * @return The number of likes on this comment.
   * @deprecated As of September 5, 2012, Facebook is changing over to {@code like_count}, so this method will be
   *             replaced by {@link #likeCount}.
   */
  @Deprecated
  public Long getLikes() {
    return likes;
  }

  /**
   * The number of likes on this comment.
   * 
   * @return The number of likes on this comment.
   * @since 1.6.10
   */
  public Long getLikeCount() {
    return likeCount;
  }

  /**
   * This field is returned only if the authenticated user can remove this comment.
   * 
   * @return This field is returned only if the authenticated user can remove this comment.
   * @since 1.6.10
   */
  public Boolean getCanRemove() {
    return canRemove;
  }

  /**
   * This field is returned only if the authenticated user likes this comment
   * 
   * @return This field is returned only if the authenticated user likes this comment.
   * @since 1.6.10
   */
  public Boolean getUserLikes() {
    return userLikes;
  }

  /**
   * If this comment is a reply, this field returns the parent comment, otherwise no value
   * 
   * @return the parent Comment
   * @since 1.6.13
   */
  public Comment getParent() {
    return parent;
  }

  /**
   * Specifies whether you can reply to this comment
   * 
   * @return can_comment
   * @since 1.6.13
   */
  public boolean getCanComment() {
    return canComment;
  }

  /**
   * The replies to this comment
   * 
   * @return replies
   */
  public Comments getComments() {
    return comments;
  }

  /**
   * Represents the Replies to a Comment</a>.
   * 
   * @author <a href="http://ityx.de">Jan Schweizer</a>
   */
  public static class Comments implements Serializable {
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
     * The number of comments.
     * 
     * @return The number of comments.
     */
    public Long getCount() {
      return count;
    }

    /**
     * The comments.
     * 
     * @return The comments.
     */
    public List<Comment> getData() {
      return unmodifiableList(data);
    }
  }

  /**
   * Media data as applicable for the attachment.
   * 
   * @author <a href="http://ityx.de">Jan Schweizer</a>
   */
  public static class Media extends FacebookType {
    private static final long serialVersionUID = 1L;

    @Facebook
    private Image image;

    public Image getImage() {
      return image;
    }

    public void setImage(Image image) {
      this.image = image;
    }
  }

  /**
   * Contains the attachment data for a specific comment (like an image or such)
   * 
   * @author <a href="http://ityx.de">Jan Schweizer</a>
   */
  public static class Attachment extends FacebookType {
    @Facebook
    private String url;

    @Facebook
    private String type;

    @Facebook("media")
    private Media media;

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

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public Media getMedia() {
      return media;
    }
  }

  /**
   * Image data as applicable for the attachment
   * 
   * @author <a href="http://ityx.de">Jan Schweizer</a>
   */
  public static class Image extends FacebookType {
    private static final long serialVersionUID = 1L;

    @Facebook
    private Integer height;

    @Facebook
    private Integer width;

    @Facebook
    private String src;

    public int getHeight() {
      return height;
    }

    public int getWidth() {
      return width;
    }

    public String getSrc() {
      return src;
    }
  }
}