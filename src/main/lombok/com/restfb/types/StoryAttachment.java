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
import java.util.Collections;
import java.util.List;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/v2.3/story_attachment">Story
 * Attachment Graph API type</a>.
 *
 * @author <a href="https://github.com/kevinleturc/">Kevin Leturc</a>
 * @since 1.12.0
 */
public class StoryAttachment extends FacebookType {

  /**
   * Returns text accompanying the attachment.
   *
   * @return Text accompanying the attachment.
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * Returns media object (photo, link etc.) contained in the attachment.
   *
   * @return Media object (photo, link etc.) contained in the attachment.
   */
  @Getter
  @Setter
  @Facebook
  private Media media;

  /**
   * Type of the media such as (photo, video, link etc)
   *
   * @return Type of the media such as (photo, video, link etc)
   */
  @Getter
  @Setter
  @Facebook("media_type")
  private String mediaType;

  /**
   * Returns object that the attachment links to.
   *
   * @return Object that the attachment links to.
   */
  @Getter
  @Setter
  @Facebook
  private Target target;

  /**
   * Returns title of the attachment.
   *
   * @return Title of the attachment.
   */
  @Getter
  @Setter
  @Facebook
  private String title;

  /**
   * Returns URL of the attachment.
   *
   * @return URL of the attachment.
   */
  @Getter
  @Setter
  @Facebook
  private String url;

  /**
   * Unshimmed URL of the attachment.
   *
   * @return Unshimmed URL of the attachment
   */
  @Getter
  @Setter
  @Facebook("unshimmed_url")
  private String unshimmedUrl;

  /**
   * Returns list of subattachments that are associated with this attachment.
   *
   * @return List of subattachments that are associated with this attachment.
   */
  @Getter
  @Setter
  @Facebook("subattachments")
  private Attachments subAttachments;

  @Facebook("description_tags")
  private List<EntityAtTextRange> descriptionTags = new ArrayList<>();

  private static final long serialVersionUID = 1L;

  /**
   * Profiles tagged in the text accompanying the attachment
   *
   * @return Profiles tagged in the text accompanying the attachment
   */
  public List<EntityAtTextRange> getDescriptionTags() {
    return Collections.unmodifiableList(descriptionTags);
  }

  public boolean addDescriptionTag(EntityAtTextRange entityAtTextRange) {
    return descriptionTags.add(entityAtTextRange);
  }

  public boolean removeDescriptionTag(EntityAtTextRange entityAtTextRange) {
    return descriptionTags.remove(entityAtTextRange);
  }

  /**
   * Represents the list of subattachments that are associated with this attachment. This will have data if the parent
   * attachment is a container for multiple other attachments. For example, a multi-photo story will have a parent
   * attachment representing an upload of multiple photos to an album where each subattachment will contain the actual
   * photos that were uploaded.
   *
   * @author <a href="https://github.com/kevinleturc/">Kevin Leturc</a>
   */
  public static class Attachments extends AbstractFacebookType {

    @Facebook
    private List<StoryAttachment> data = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    /**
     * The attachments.
     *
     * @return The attachments.
     */
    public List<StoryAttachment> getData() {
      return unmodifiableList(data);
    }

    public boolean addData(StoryAttachment attachment) {
      return data.add(attachment);
    }

    public boolean removeData(StoryAttachment attachment) {
      return data.remove(attachment);
    }
  }

  /**
   * Media data as applicable for the attachment.
   *
   * @author <a href="https://github.com/kevinleturc/">Kevin Leturc</a>
   */
  public static class Media extends FacebookType {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook
    private Image image;

    @Getter
    @Setter
    @Facebook
    private String source;

  }

  /**
   * Image data as applicable for the attachment
   *
   * @author <a href="https://github.com/kevinleturc/">Kevin Leturc</a>
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

  /**
   * Target data as applicable for the attachment
   *
   * @author <a href="https://github.com/kevinleturc/">Kevin Leturc</a>
   */
  public static class Target extends FacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook
    private String url;

    @Getter
    @Setter
    @Facebook("unshimmed_url")
    private String unshimmedUrl;

  }

}
