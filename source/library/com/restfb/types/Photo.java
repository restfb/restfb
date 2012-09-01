/*
 * Copyright (c) 2010-2012 Mark Allen.
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
 * Represents the <a
 * href="http://developers.facebook.com/docs/reference/api/photo">Photo Graph
 * API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Photo extends NamedFacebookType {
  @Facebook
  private CategorizedFacebookType from;

  @Facebook
  private String picture;

  @Facebook
  private String source;

  @Facebook
  private Integer height;

  @Facebook
  private Integer width;

  @Facebook
  private String link;

  @Facebook
  private String icon;

  @Facebook
  private Integer position;

  @Facebook("created_time")
  private String createdTime;

  @Facebook("updated_time")
  private String updatedTime;

  @Facebook
  private List<Tag> tags = new ArrayList<Tag>();

  @Facebook
  private List<Comment> comments = new ArrayList<Comment>();

  @Facebook
  private List<NamedFacebookType> likes = new ArrayList<NamedFacebookType>();

  @Facebook
  private List<Image> images = new ArrayList<Image>();

  @Facebook
  private Place place;

  private static final long serialVersionUID = 1L;

  /**
   * Represents the <a
   * href="http://developers.facebook.com/docs/reference/api/photo">Tag Graph
   * API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5
   */
  public static class Tag extends NamedFacebookType {
    @Facebook
    private Integer x;

    @Facebook
    private Integer y;

    @Facebook("created_time")
    private String createdTime;

    private static final long serialVersionUID = 1L;

    /**
     * X coordinate (as a percentage of distance from left vs. width).
     * 
     * @return X coordinate (as a percentage of distance from left vs. width).
     */
    public Integer getX() {
      return x;
    }

    /**
     * Y coordinate (as a percentage of distance from top vs. height).
     * 
     * @return Y coordinate (as a percentage of distance from top vs. height).
     */
    public Integer getY() {
      return y;
    }

    /**
     * Date this tag was created.
     * 
     * @return Date this tag was created.
     */
    public Date getCreatedTime() {
      return toDateFromLongFormat(createdTime);
    }
  }

  /**
   * Represents the <a
   * href="http://developers.facebook.com/docs/reference/api/photo">Image Graph
   * API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.5
   */
  public static class Image implements Serializable {
    @Facebook
    private Integer height;

    @Facebook
    private Integer width;

    @Facebook
    private String source;

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
     * The height of the image in pixels.
     * 
     * @return The height of the image in pixels.
     */
    public Integer getHeight() {
      return height;
    }

    /**
     * The width of the image in pixels.
     * 
     * @return The width of the image in pixels.
     */
    public Integer getWidth() {
      return width;
    }

    /**
     * The source URL of the image.
     * 
     * @return The source URL of the image.
     */
    public String getSource() {
      return source;
    }
  }

  /**
   * An object containing the name and ID of the user who posted the photo.
   * 
   * @return An object containing the name and ID of the user who posted the
   *         photo.
   */
  public CategorizedFacebookType getFrom() {
    return from;
  }

  /**
   * The album-sized view of the photo.
   * 
   * @return The album-sized view of the photo.
   */
  public String getPicture() {
    return picture;
  }

  /**
   * The full-sized source of the photo.
   * 
   * @return The full-sized source of the photo.
   */
  public String getSource() {
    return source;
  }

  /**
   * The height of the photo, in pixels.
   * 
   * @return The height of the photo, in pixels.
   */
  public Integer getHeight() {
    return height;
  }

  /**
   * The width of the photo, in pixels.
   * 
   * @return The width of the photo, in pixels.
   */
  public Integer getWidth() {
    return width;
  }

  /**
   * A link to the photo on Facebook.
   * 
   * @return A link to the photo on Facebook.
   */
  public String getLink() {
    return link;
  }

  /**
   * The icon-sized source of the photo.
   * 
   * @return The icon-sized source of the photo.
   */
  public String getIcon() {
    return icon;
  }

  /**
   * The position of this photo in the album.
   * 
   * @return The position of this photo in the album.
   * @since 1.6.5
   * @deprecated Facebook will start returning 0 for this field starting on
   *             October 3, 2012.
   */
  @Deprecated
  public Integer getPosition() {
    return position;
  }

  /**
   * The location associated with this photo, if any.
   * 
   * @return The place this photo was taken.
   * @since 1.6.10
   */
  public Place getPlace() {
    return place;
  }

  /**
   * The time the photo was initially published.
   * 
   * @return The time the photo was initially published.
   */
  public Date getCreatedTime() {
    return toDateFromLongFormat(createdTime);
  }

  /**
   * The last time the photo or its caption was updated.
   * 
   * @return The last time the photo or its caption was updated.
   */
  public Date getUpdatedTime() {
    return toDateFromLongFormat(updatedTime);
  }

  /**
   * An array containing the users and their positions in this photo. The x and
   * y coordinates are percentages from the left and top edges of the photo,
   * respectively.
   * 
   * @return An array containing the users and their positions in this photo.
   *         The x and y coordinates are percentages from the left and top edges
   *         of the photo, respectively.
   */
  public List<Tag> getTags() {
    return unmodifiableList(tags);
  }

  /**
   * All of the comments on this photo.
   * 
   * @return All of the comments on this photo.
   * @since 1.6.5
   */
  public List<Comment> getComments() {
    return unmodifiableList(comments);
  }

  /**
   * Users who like the photo.
   * 
   * @return Users who like the photo.
   * @since 1.6.5
   */
  public List<NamedFacebookType> getLikes() {
    return unmodifiableList(likes);
  }

  /**
   * The 4 different stored representations of the photo.
   * 
   * @return The 4 different stored representations of the photo.
   * @since 1.6.5
   */
  public List<Image> getImages() {
    return unmodifiableList(images);
  }
}