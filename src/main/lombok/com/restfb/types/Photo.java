/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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
import com.restfb.JsonMapper;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.annotation.GraphAPI;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.JsonObject;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/photo/">Photo Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Photo extends NamedFacebookType {

  /**
   * An object containing the name and ID of the user who posted the photo.
   * 
   * @return An object containing the name and ID of the user who posted the photo.
   */
  @Getter
  @Setter
  @Facebook
  private CategorizedFacebookType from;

  /**
   * The album-sized view of the photo.
   * 
   * @return The album-sized view of the photo.
   */
  @Getter
  @Setter
  @Facebook
  private String picture;

  /**
   * ID of the page story this corresponds to.
   *
   * May not be on all photos. Applies only to published photos
   *
   * @return ID of the page story this corresponds to.
   */
  @Getter
  @Setter
  @Facebook("page_story_id")
  private String pageStoryId;

  /**
   * The reactions for this photo.
   *
   * @return The reactions for this photo.
   */
  @Getter
  @Setter
  @Facebook
  private Reactions reactions;

  /**
   * The full-sized source of the photo.
   * 
   * @return The full-sized source of the photo.
   * @deprecated Use <code>images</code> field instead
   */
  @Getter
  @Setter
  @Facebook
  @Deprecated
  private String source;

  /**
   * The height of the photo, in pixels.
   * 
   * @return The height of the photo, in pixels.
   */
  @Getter
  @Setter
  @Facebook
  private Integer height;

  /**
   * The width of the photo, in pixels.
   * 
   * @return The width of the photo, in pixels.
   */
  @Getter
  @Setter
  @Facebook
  private Integer width;

  /**
   * A link to the photo on Facebook.
   * 
   * @return A link to the photo on Facebook.
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * The icon-sized source of the photo.
   * 
   * @return The icon-sized source of the photo.
   */
  @Getter
  @Setter
  @Facebook
  private String icon;

  /**
   * The album this photo is in
   *
   * @return The album this photo is in
   */
  @Getter
  @Setter
  @Facebook
  private Album album;

  /**
   * A boolean indicating if the viewer can delete the photo
   *
   * @return A boolean indicating if the viewer can delete the photo
   */
  @Getter
  @Setter
  @Facebook("can_delete")
  private Boolean canDelete;

  /**
   * A boolean indicating if the viewer can tag the photo
   *
   * @return A boolean indicating if the viewer can tag the photo
   */
  @Getter
  @Setter
  @Facebook("can_tag")
  private Boolean canTag;

  /**
   * The position of this photo in the album.
   * 
   * @return The position of this photo in the album.
   * @since 1.6.5
   * @deprecated Facebook will start returning 0 for this field starting on October 3, 2012.
   */
  @Deprecated
  @Getter
  @Setter
  @Facebook
  private Integer position;

  /**
   * If this object has a place, the event associated with the place
   *
   * @return If this object has a place, the event associated with the place
   */
  @Getter(onMethod = @__(@GraphAPI(since = "2.3")))
  @Setter
  @Facebook
  @GraphAPI(since = "2.3")
  private Event event;

  /**
   * The last time the photo or its caption was updated.
   * 
   * @return The last time the photo or its caption was updated.
   */
  @Getter
  @Setter
  @Facebook("updated_time")
  private Date updatedTime;

  /**
   * The time the photo was initially published.
   * 
   * @return The time the photo was initially published.
   */
  @Getter
  @Setter
  @Facebook("created_time")
  private Date createdTime;

  /**
   * All of the comments on this photo.
   *
   * @return All of the comments on this photo.
   * @since 1.6.5
   */
  @Getter
  @Setter
  @Facebook
  private Comments comments;

  @Facebook
  private List<Tag> tags = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private Likes likes;

  @Facebook
  private List<Image> images = new ArrayList<>();

  @Facebook("name_tags")
  private transient String rawNameTags;

  private List<EntityAtTextRange> nameTags = new ArrayList<>();

  /**
   * The location associated with this photo, if any.
   * 
   * @return The place this photo was taken.
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook
  private Place place;

  /**
   * Back dated time
   * 
   * @return the back dated time
   * @since 1.6.15
   */
  @Getter
  @Setter
  @Facebook("backdated_time")
  private Date backdatedTime;

  /**
   * String that represents the back dated time granularity
   * 
   * @return the back dated time granularity
   * @since 1.6.15
   */
  @Getter
  @Setter
  @Facebook("backdated_time_granularity")
  private String backdatedTimeGranularity;

  private static final long serialVersionUID = 1L;

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/photo">Tag Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5
   */
  public static class Tag extends NamedFacebookType {

    /**
     * X coordinate (as a percentage of distance from left vs. width).
     * 
     * @return X coordinate (as a percentage of distance from left vs. width).
     */
    @Getter
    @Setter
    @Facebook
    private Double x;

    /**
     * Y coordinate (as a percentage of distance from top vs. height).
     * 
     * @return Y coordinate (as a percentage of distance from top vs. height).
     */
    @Getter
    @Setter
    @Facebook
    private Double y;

    /**
     * Date this tag was created.
     * 
     * @return Date this tag was created.
     */
    @Getter
    @Setter
    @Facebook("created_time")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/photo">Image Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.5
   */
  public static class Image extends AbstractFacebookType {

    /**
     * The height of the image in pixels.
     * 
     * @return The height of the image in pixels.
     */
    @Getter
    @Setter
    @Facebook
    private Integer height;

    /**
     * The width of the image in pixels.
     * 
     * @return The width of the image in pixels.
     */
    @Getter
    @Setter
    @Facebook
    private Integer width;

    /**
     * The source URL of the image.
     * 
     * @return The source URL of the image.
     */
    @Getter
    @Setter
    @Facebook
    private String source;

    private static final long serialVersionUID = 1L;
  }

  /**
   * An array containing the users and their positions in this photo. The x and y coordinates are percentages from the
   * left and top edges of the photo, respectively.
   * 
   * @return An array containing the users and their positions in this photo. The x and y coordinates are percentages
   *         from the left and top edges of the photo, respectively.
   */
  public List<Tag> getTags() {
    return unmodifiableList(tags);
  }

  public boolean addTag(Tag tag) {
    return tags.add(tag);
  }

  public boolean removeTag(Tag tag) {
    return tags.remove(tag);
  }

  public List<EntityAtTextRange> getNameTags() {
    return unmodifiableList(nameTags);
  }

  public boolean addNameTag(EntityAtTextRange nameTag) {
    return nameTags.add(nameTag);
  }

  public boolean removeNameTag(EntityAtTextRange nameTag) {
    return nameTags.remove(nameTag);
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

  public boolean addImage(Image image) {
    return images.add(image);
  }

  public boolean removeImage(Image image) {
    return images.remove(image);
  }

  /**
   * Post-JSON-mapping operation that populates the {@code messageTags} field "by hand".
   *
   * @param jsonMapper
   *          The {@code JsonMapper} that was used to map to this type.
   */
  @JsonMappingCompleted
  protected void jsonMappingCompleted(JsonMapper jsonMapper) {

    if (rawNameTags == null) {
      return;
    }

    try {
      nameTags = jsonMapper.toJavaList(rawNameTags, EntityAtTextRange.class);
      return;
    } catch (FacebookJsonMappingException je) {
      // message tags not in Graph API 2.5 format, ignore this exception and try another way
    }

    try {
      JsonObject rawMessageTagsObject = jsonMapper.toJavaObject(rawNameTags, JsonObject.class);
      for (String key : rawMessageTagsObject.names()) {
        String tagArrayString = rawMessageTagsObject.get(key).toString();
        nameTags.addAll(jsonMapper.toJavaList(tagArrayString, EntityAtTextRange.class));
      }
    } catch (FacebookJsonMappingException je) {
      // cannot parse message tags, but don't break the flow here
    }
  }
}