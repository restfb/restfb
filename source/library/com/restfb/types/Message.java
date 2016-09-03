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

import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static java.util.Collections.unmodifiableList;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/message/">Message Graph API type</a>
 * .
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
   * The subject of the message.
   *
   * @return The subject of the message.
   */
  @Getter
  @Setter
  @Facebook
  private String subject;

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

  @Facebook
  private List<Share> shares = new ArrayList<Share>();

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

  @Facebook("tags")
  private String rawTags;

  private List<String> tags = new ArrayList<String>();

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

    @Getter
    @Setter
    @Facebook("file_url")
    private String fileUrl;

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
     * When the attached file is a video, Facebook will also send information about it's width, height and url.
     *
     * @return The attachment's video data.
     */
    @Getter
    @Setter
    @Facebook("video_data")
    private VideoData videoData;

    /**
     * returns if the attachment is a image
     * 
     * @return true if the attachment is a image, false otherwise
     */
    public boolean isImage() {
      return null != imageData && null == videoData;
    }

    /**
     * returns if the attachment is a video
     * 
     * @return true if the attachment is a video, false otherwise
     */
    public boolean isVideo() {
      return null != videoData;
    }

  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/message/shares/">Message Share
   * Graph API type</a>.
   */
  public static class Share extends NamedFacebookType {

    /**
     * The URL to the shared item.
     *
     * @return The URL to the shared item.
     */
    @Getter
    @Setter
    @Facebook
    private String link;

    /**
     * The description of the shared item.
     *
     * @return The description of the shared item.
     */
    @Getter
    @Setter
    @Facebook
    private String description;

    @Getter
    @Setter
    @Facebook
    private String picture;
  }

  /**
   * Additional attachment information, only present when an attached file is an image.
   *
   * @author Felipe Kurkowski
   */
  public static class ImageData extends AttachmentData {
    private static final long serialVersionUID = 1L;

    /**
     * The image's max width.
     *
     * @return The image's max width.
     */
    @Getter
    @Setter
    @Facebook("max_width")
    private int maxWidth;

    /**
     * The image's max height.
     *
     * @return The image's max height.
     */
    @Getter
    @Setter
    @Facebook("max_height")
    private int maxHeight;

    @Getter
    @Setter
    @Facebook("render_as_sticker")
    private Boolean renderAsSticker;

    /**
     * @deprecated use {@see Message#getRenderAsSticker()} instead
     * @return
     */
    @Deprecated
    public boolean isRenderAsSticker() {
      if (renderAsSticker != null) {
        return renderAsSticker.booleanValue();
      }
      return false;
    }

    @Getter
    @Setter
    @Facebook("image_type")
    private int imageType;

    @Getter
    @Setter
    @Facebook("raw_gif_image")
    private String rawGifImage;

    @Getter
    @Setter
    @Facebook("raw_webp_image")
    private String rawWebpImage;

    @Getter
    @Setter
    @Facebook("animated_gif_url")
    private String animatedGifUrl;

    @Getter
    @Setter
    @Facebook("animated_gif_preview_url")
    private String animatedGifPreviewUrl;

    @Getter
    @Setter
    @Facebook("animated_webp_url")
    private String animatedWebpUrl;

    @Getter
    @Setter
    @Facebook("animated_webp_preview_url")
    private String animatedWebpPreviewUrl;

  }

  /**
   * Additional attachment information, only present when an attached file is an video.
   */
  public static class VideoData extends AttachmentData {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook
    private int length;

    @Getter
    @Setter
    @Facebook("video_type")
    private int videoType;

    @Getter
    @Setter
    @Facebook
    private int rotation;
  }

  public static abstract class AttachmentData extends AbstractFacebookType {

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

  }

  @JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }

  @JsonMappingCompleted
  void convertTags() {
    if (rawTags != null) {
      JsonObject parsedObject = new JsonObject(rawTags);
      JsonArray tagsArray = parsedObject.getJsonArray("data");
      for (int i = 0; i < tagsArray.length(); i++) {
        tags.add(tagsArray.getJsonObject(i).getString("name"));
      }
    }
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
   * The shares associated with the message.
   *
   * This is page only.
   *
   * @return The shares associated with the message.
   */
  public List<Share> getShares() {
    return (shares != null ? unmodifiableList(shares) : null);
  }

  public boolean addShare(Share share) {
    return shares.add(share);
  }

  public boolean removeShare(Share share) {
    return shares.remove(share);
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

  /**
   * A set of tags indicating the message folder and source of the message.
   *
   * @return A set of tags indicating the message folder and source of the message.
   */
  public List<String> getTags() {
    return unmodifiableList(tags);
  }

  public boolean addTag(String tag) {
    return tags.add(tag);
  }

  public boolean removeTag(String tag) {
    return tags.remove(tag);
  }
}
