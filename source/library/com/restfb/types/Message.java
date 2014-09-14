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
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;
import lombok.Getter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/message/">Message Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author Felipe Kurkowski
 * @author alockhart
 */
public class Message extends FacebookType {
  @Facebook("created_time")
  private String createdTime;

  /**
   * The sender of this message
   * 
   * @return The sender of this message
   */
  @Getter
  @Facebook
  private NamedFacebookType from;

  /**
   * A list of the message recipients
   * 
   * @return A list of the message recipients
   */
  @Getter
  @Facebook
  private List<NamedFacebookType> to;

  /**
   * The text of the message
   * 
   * @return The text of the message
   */
  @Getter
  @Facebook
  private String message;

  @Facebook
  private List<Attachment> attachments;

  @Facebook("updated_time")
  private String updatedTime;

  /**
   * The "unread" count for this message.
   * 
   * @return The "unread" count for this message.
   */
  @Getter
  @Facebook
  private Integer unread;

  /**
   * Whether this message has been seen.
   * 
   * @return Whether this message has been seen.
   */
  @Getter
  @Facebook
  private Boolean unseen;

  private static final long serialVersionUID = 1L;

  /**
   * Represents an attached file that you may find on a private message.
   * 
   * @author alockhart
   * @since 1.6.12
   */
  public static class Attachment extends FacebookType {

    private static final long serialVersionUID = 1L;

    /**
     * The attachment's filename, for example 121423423.jpg.
     * 
     * @return The attachment's filename.
     */
    @Getter
    @Facebook
    private String name;

    /**
     * The attachment's mime type, for example image/jpeg.
     * 
     * @return The attachment's mime type.
     */
    @Getter
    @Facebook("mime_type")
    private String mimeType;

    /**
     * The size of the attachment in bytes.
     * 
     * @return The size of the attachment in bytes.
     */
    @Getter
    @Facebook
    private Long size;

    /**
     * When the attached file is an image, Facebook will also send information
     * about it's width, height and url.
     *
     * @return The attachment's image data.
     */
    @Getter
    @Facebook("image_data")
    private ImageData imageData;

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
  }

  /**
  * Additional attachment information, only present when an attached file is
  * an image.
  *
  * @author Felipe Kurkowski
  */
  public static class ImageData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The image's width.
     *
     * @return The image's width.
     */
    @Getter
    @Facebook
    private int width;

    /**
     * The image's height.
     *
     * @return The image's height.
     */
    @Getter
    @Facebook
    private int height;

    /**
     * The image's url.
     *
     * @return The image's url.
     */
    @Getter
    @Facebook
    private String url;

    /**
     * The image's preview url.
     *
     * @return The image's preview url.
     */
    @Getter
    @Facebook("preview_url")
    private String previewUrl;

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
  }

  /**
   * The time the message was initially created.
   * 
   * @return The time the message was initially created.
   */
  public Date getCreatedTime() {
    return toDateFromLongFormat(createdTime);
  }

  /**
   * The time of the last update to this message.
   * 
   * @return The time of the last update to this message.
   */
  public Date getUpdatedTime() {
    return toDateFromLongFormat(updatedTime);
  }

  /**
   * The attachments associated with the message.
   * 
   * @return The attachments associated with the message.
   */
  public List<Attachment> getAttachments() {
    return (attachments != null ? unmodifiableList(attachments) : null);
  }
}
