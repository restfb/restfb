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

import static java.util.Collections.unmodifiableList;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
import com.restfb.util.ReflectionUtils;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/message/">Message Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author Felipe Kurkowski
 * @author alockhart
 */
public class Message extends FacebookType {

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * The time the message was initially created.
   * 
   * @return The time the message was initially created.
   */
  @Getter
  @Setter
  private Date createdTime;

  /**
   * The sender of this message
   * 
   * @return The sender of this message
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType from;

  @Facebook
  private List<NamedFacebookType> to = new ArrayList<NamedFacebookType>();

  /**
   * The text of the message
   * 
   * @return The text of the message
   */
  @Getter
  @Setter
  @Facebook
  private String message;

  @Facebook
  private List<Attachment> attachments = new ArrayList<Attachment>();

  @Facebook("updated_time")
  transient private String rawUpdatedTime;

  /**
   * The time of the last update to this message.
   * 
   * @return The time of the last update to this message.
   */
  @Getter
  @Setter
  private Date updatedTime;

  /**
   * The "unread" count for this message.
   * 
   * @return The "unread" count for this message.
   */
  @Getter
  @Setter
  @Facebook
  private Integer unread;

  /**
   * Whether this message has been seen.
   * 
   * @return Whether this message has been seen.
   */
  @Getter
  @Setter
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
    @Setter
    @Facebook
    private String name;

    /**
     * The attachment's mime type, for example image/jpeg.
     * 
     * @return The attachment's mime type.
     */
    @Getter
    @Setter
    @Facebook("mime_type")
    private String mimeType;

    /**
     * The size of the attachment in bytes.
     * 
     * @return The size of the attachment in bytes.
     */
    @Getter
    @Setter
    @Facebook
    private Long size;

    /**
     * When the attached file is an image, Facebook will also send information about it's width, height and url.
     * 
     * @return The attachment's image data.
     */
    @Getter
    @Setter
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
   * Additional attachment information, only present when an attached file is an image.
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
    @Setter
    @Facebook
    private int width;

    /**
     * The image's height.
     * 
     * @return The image's height.
     */
    @Getter
    @Setter
    @Facebook
    private int height;

    /**
     * The image's url.
     * 
     * @return The image's url.
     */
    @Getter
    @Setter
    @Facebook
    private String url;

    /**
     * The image's preview url.
     * 
     * @return The image's preview url.
     */
    @Getter
    @Setter
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

  @JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }

  /**
   * The attachments associated with the message.
   * 
   * @return The attachments associated with the message.
   */
  public List<Attachment> getAttachments() {
    return (attachments != null ? unmodifiableList(attachments) : null);
  }

  public boolean addAttachment(Attachment attachment) {
    return attachments.add(attachment);
  }

  public boolean removeAttachment(Attachment attachment) {
    return attachments.remove(attachment);
  }

  /**
   * A list of the message recipients
   * 
   * @return A list of the message recipients
   */
  public List<NamedFacebookType> getTo() {
    return unmodifiableList(to);
  }

  public boolean addTo(NamedFacebookType receiver) {
    return to.add(from);
  }

  public boolean removeTo(NamedFacebookType receiver) {
    return to.remove(from);
  }
}
