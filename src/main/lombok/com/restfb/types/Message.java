/*
 * Copyright (c) 2010-2024 Mark Allen, Norbert Bartels.
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
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.Json;
import com.restfb.json.JsonValue;
import com.restfb.types.features.HasCreatedTime;
import com.restfb.types.features.HasFrom;
import com.restfb.types.features.HasMessage;

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
public class Message extends FacebookType implements HasCreatedTime, HasFrom, HasMessage {

  /**
   * The time the message was initially created.
   *
   * @return The time the message was initially created.
   */
  @Getter(onMethod_ = { @Override })
  @Setter
  @Facebook("created_time")
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
  @Getter(onMethod_ = { @Override })
  @Setter
  @Facebook
  private ExtendedReferenceType from;

  @Facebook
  private List<ExtendedReferenceType> to = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("is_unsupported")
  private boolean isUnsupported = false;

  /**
   * The text of the message
   *
   * @return The text of the message
   */
  @Getter(onMethod_ = { @Override })
  @Setter
  @Facebook
  private String message;

  /**
   * Sticker contained in the message.
   *
   * @return The Sticker in that message
   */
  @Getter
  @Setter
  @Facebook
  private String sticker;

  @Facebook
  private List<Reaction> reactions = new ArrayList<>();

  @Facebook
  private List<Attachment> attachments = new ArrayList<>();

  @Facebook
  private List<Share> shares = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private Story story;

  /**
   * The time of the last update to this message.
   *
   * @return The time of the last update to this message.
   */
  @Getter
  @Setter
  @Facebook("updated_time")
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
  private transient String rawTags;

  private List<String> tags = new ArrayList<>();

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
     * When the attached file is an image, Facebook will also send information about its width, height and url.
     *
     * @return The attachment's image data.
     */
    @Getter
    @Setter
    @Facebook("image_data")
    private ImageData imageData;

    /**
     * When the attached file is a video, Facebook will also send information about its width, height and url.
     *
     * @return The attachment's video data.
     */
    @Getter
    @Setter
    @Facebook("video_data")
    private VideoData videoData;

    /**
     * returns if the attachment is an image
     * 
     * @return true if the attachment is an image, false otherwise
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

    /**
     * returns if the attachment is a placeholder for the real attachment, that is not accessible via API due to privacy
     * rules in Europe
     *
     * check <a href="https://developers.facebook.com/docs/messenger-platform/europe-updates">at Facebook</a>
     *
     * @return true if not accessible in the EU
     */
    public boolean isRemovedInEurope() {
      return "1234".equals(getId());
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
   * Additional attachment information, only present when an attached file is a video.
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

  public abstract static class AttachmentData extends AbstractFacebookType {

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

  public static class Reaction extends AbstractFacebookType {

    @Getter
    @Setter
    @Facebook
    private String reaction;

    @Facebook
    private List<ExtendedReferenceType> users = new ArrayList<>();

    public List<ExtendedReferenceType> getUsers() {
      return users;
    }

    public boolean addUser(ExtendedReferenceType user) {
      return users.add(user);
    }

    public boolean removeUser(ExtendedReferenceType user) {
      return users.remove(user);
    }
  }

  public static class Story extends AbstractFacebookType {

    @Getter
    @Setter
    @Facebook("reply_to")
    StoryReply replyTo;

    @Getter
    @Setter
    @Facebook
    StoryMention mention;
  }

  public static class StoryReply extends FacebookType {
    @Getter
    @Setter
    @Facebook
    private String link;
  }

  public static class StoryMention extends FacebookType {
    @Getter
    @Setter
    @Facebook
    private String link;
  }

  @JsonMappingCompleted
  void convertTags() {
    if (rawTags != null) {
      JsonValue parsedObject = Json.parse(rawTags);
      if (parsedObject.isObject() && parsedObject.asObject().get("data").isArray()) {
        for (JsonValue tagObject : parsedObject.asObject().get("data").asArray()) {
          tags.add(tagObject.asObject().get("name").asString());
        }
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
   * <p>
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
   * A list of recipients of the message.
   *
   * @return A list of the message recipients
   */
  public List<ExtendedReferenceType> getTo() {
    return unmodifiableList(to);
  }

  public boolean addTo(ExtendedReferenceType receiver) {
    return to.add(receiver);
  }

  public boolean removeTo(ExtendedReferenceType receiver) {
    return to.remove(receiver);
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

  /**
   * Reaction (like/love) on the existing message
   *
   * @return list of reactions
   */
  public List<Reaction> getReactions() {
    return unmodifiableList(reactions);
  }

  public boolean addReaction(Reaction reaction) {
    return reactions.add(reaction);
  }

  public boolean removeReaction(Reaction reaction) {
    return reactions.remove(reaction);
  }

}
