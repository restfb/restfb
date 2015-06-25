package com.restfb.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;
import static java.util.Collections.unmodifiableList;
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
   * Returns type of the attachment.
   *
   * @return Type of the attachment.
   */
  @Getter
  @Setter
  @Facebook
  private String type;

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
   * Returns list of subattachments that are associated with this attachment.
   *
   * @return List of subattachments that are associated with this attachment.
   */
  @Getter
  @Setter
  @Facebook("subattachments")
  private Attachments subAttachments;

  private static final long serialVersionUID = 1L;

  /**
   * Represents the list of subattachments that are associated with this attachment. This will have data if the parent
   * attachment is a container for multiple other attachments. For example, a multi-photo story will have a parent
   * attachment representing an upload of multiple photos to an album where each subattachment will contain the actual
   * photos that were uploaded.
   *
   * @author <a href="https://github.com/kevinleturc/">Kevin Leturc</a>
   */
  public static class Attachments implements Serializable {

    @Facebook
    private List<StoryAttachment> data = new ArrayList<StoryAttachment>();

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

    @Getter
    @Setter
    @Facebook
    private String url;

  }

}
