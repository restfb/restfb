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

import static com.restfb.json.JsonObject.getNames;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.JsonObject;
import com.restfb.types.Checkin.Place.Location;
import com.restfb.util.ReflectionUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Post Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
@SuppressWarnings("deprecation")
public class Post extends NamedFacebookType {

  /**
   * An object containing the ID and name of the user who posted the message.
   * 
   * @return An object containing the ID and name of the user who posted the message.
   */
  @Getter
  @Setter
  @Facebook
  private CategorizedFacebookType from;

  /**
   * The message.
   * 
   * @return The message.
   */
  @Getter
  @Setter
  @Facebook
  private String message;

  /**
   * If available, a link to the picture included with this post.
   * 
   * @return If available, a link to the picture included with this post.
   */
  @Getter
  @Setter
  @Facebook
  private String picture;

  /**
   * The link attached to this post.
   * 
   * @return The link attached to this post.
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * The caption of the link (appears beneath the link name).
   * 
   * @return The caption of the link (appears beneath the link name).
   */
  @Getter
  @Setter
  @Facebook
  private String caption;

  /**
   * A description of the link (appears beneath the link caption).
   * 
   * @return A description of the link (appears beneath the link caption).
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * If available, the source link attached to this post (for example, a flash or video file).
   * 
   * @return If available, the source link attached to this post (for example, a flash or video file).
   */
  @Getter
  @Setter
  @Facebook
  private String source;

  /**
   * The type of post this is, for example {@code "link"}.
   * 
   * @return The type of post this is, for example {@code "link"}.
   */
  @Getter
  @Setter
  @Facebook
  private String type;

  /**
   * The application used to create this post.
   * 
   * @return The application used to create this post.
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType application;

  /**
   * A link to an icon representing the type of this post.
   * 
   * @return A link to an icon representing the type of this post.
   */
  @Getter
  @Setter
  @Facebook
  private String icon;

  /**
   * A string indicating which application was used to create this post.
   * 
   * @return A string indicating which application was used to create this post.
   */
  @Getter
  @Setter
  @Facebook
  private String attribution;

  /**
   * The privacy settings for this post.
   * 
   * @return The privacy settings for this post.
   */
  @Getter
  @Setter
  @Facebook
  private Privacy privacy;

  /**
   * Duplicate mapping for "likes" since FB can return it differently in different situations.
   */
  @Facebook("likes")
  private Long likesCount;

  /**
   * Duplicate mapping for "likes" since FB can return it differently in different situations.
   * 
   * -- GETTER -- The likes on this post.
   * <p>
   * Sometimes this can be {@code null} - check {@link #getLikesCount()} instead in that case.
   * 
   * @return The likes on this post.
   * 
   */
  @Getter
  @Setter
  @Facebook
  private Likes likes;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * The time the post was initially published.
   * 
   * @return The time the post was initially published.
   */
  @Getter
  @Setter
  private Date createdTime;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  /**
   * The time of the last comment on this post.
   * 
   * @return The time of the last comment on this post.
   */
  @Getter
  @Setter
  private Date updatedTime;

  /**
   * The Facebook object id for an uploaded photo or video.
   * 
   * @return The Facebook object id for an uploaded photo or video.
   * @since 1.6.5
   */
  @Getter
  @Setter
  @Facebook("object_id")
  private String objectId;

  /**
   * The {@code status_type} of post this is, for example {@code "approved_friend"}.
   * 
   * @return The {@code status_type} of post this is, for example {@code "approved_friend"}.
   * @since 1.6.12
   */
  @Getter
  @Setter
  @Facebook("status_type")
  private String statusType;

  /**
   * Text from stories not intentionally generated by users
   * 
   * @return Text from stories not intentionally generated by users
   * @since 1.6.16
   */
  @Getter
  @Setter
  @Facebook
  private String story;

  /**
   * The comments for this post.
   * 
   * @return The comments for this post.
   */
  @Getter
  @Setter
  @Facebook
  private Comments comments;

  /**
   * The place where this post occurred.
   * 
   * @return The place where this post occurred.
   * @since 1.6.8
   */
  @Getter
  @Setter
  @Facebook
  private com.restfb.types.Place place;

  @Facebook
  private List<NamedFacebookType> to = new ArrayList<NamedFacebookType>();

  @Facebook
  private List<Action> actions = new ArrayList<Action>();

  @Facebook
  private List<Property> properties = new ArrayList<Property>();

  @Facebook("with_tags")
  private List<NamedFacebookType> withTags = new ArrayList<NamedFacebookType>();

  @Facebook("message_tags")
  private JsonObject rawMessageTags;

  private Map<String, List<MessageTag>> messageTags = new HashMap<String, List<MessageTag>>();

  @Getter
  @Setter
  @Facebook
  private Shares shares;
  
  /**
   * ID of admin who created the post. 
   * 
   * Applies to pages only
   * 
   * @return ID of admin who created the post.
   * @since 1.10.0
   */
  @Getter
  @Setter
  @Facebook("admin_creator")
  private NamedFacebookType adminCreator;

  private static final long serialVersionUID = 3L;

  /**
   * Post-JSON-mapping operation that populates the {@code messageTags} field "by hand".
   * <p>
   * This is a temporary hack until we have formal public support for it/improved {@code JsonMapper} capabilities so it
   * can handle arbitrary Map types.
   * 
   * @param jsonMapper
   *          The {@code JsonMapper} that was used to map to this type.
   * @since 1.6.11
   */
  @JsonMappingCompleted
  protected void jsonMappingCompleted(JsonMapper jsonMapper) {
    messageTags = new HashMap<String, List<MessageTag>>();

    if (rawMessageTags == null)
      return;

    for (String key : getNames(rawMessageTags)) {
      String messageTagJson = rawMessageTags.getString(key);
      messageTags.put(key, jsonMapper.toJavaList(messageTagJson, MessageTag.class));
    }
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Place Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.8
   * @deprecated As of release 1.6.10, replaced by {@link Location}.
   */
  @Deprecated
  public static class Place extends NamedFacebookType {

    /**
     * The location of this place.
     * 
     * @return The location of this place.
     */
    @Getter
    @Setter
    @Facebook
    private Location location;

    private static final long serialVersionUID = 1L;

  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Message Tag Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.10
   */
  public static class MessageTag extends NamedFacebookType {

    /**
     * The offset, within the message field, of the object mentioned.
     * 
     * @return The offset, within the message field, of the object mentioned.
     */
    @Getter
    @Setter
    @Facebook
    private Integer offset;

    /**
     * The length, within the message field, of the object mentioned.
     * 
     * @return The length, within the message field, of the object mentioned.
     */
    @Getter
    @Setter
    @Facebook
    private Integer length;

    private static final long serialVersionUID = 1L;

  }

  /**
   * Represents the undocumented {@code Property} type.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.4
   */
  public static class Property implements Serializable {

    /**
     * The name of the property.
     * 
     * @return The name of the property.
     */
    @Getter
    @Setter
    @Facebook
    private String name;

    /**
     * The text of the property.
     * 
     * @return The text of the property.
     */
    @Getter
    @Setter
    @Facebook
    private String text;

    /**
     * The URL of the property.
     * 
     * @return The URL of the property.
     */
    @Getter
    @Setter
    @Facebook
    private String href;
    
    /**
     * If this post is marked as hidden (applies to Pages only).
     * 
     * @since 1.10.0
     * @return if this post is marked as hidden
     */
    @Getter
    @Setter
    @Facebook("is_hidden")
    private Boolean isHidden;
    
    /**
     * Indicates whether a scheduled post was published. 
     * (applies to scheduled Page Post only, for users post and 
     * instanlty published posts this value is always <code>true</code>)
     * 
     * @since 1.10.0
     * @return indicates whether a scheduled post was published
     */
    @Getter
    @Setter
    @Facebook("is_published")
    private Boolean isPublished;

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

  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Likes Graph API type</a>
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6
   */
  public static class Likes implements Serializable {

    /**
     * The number of likes.
     * 
     * @return The number of likes.
     */
    @Getter
    @Setter
    @Facebook
    private Long totalCount = 0L;

    @Facebook
    private JsonObject summary;

    @Facebook
    private List<NamedFacebookType> data = new ArrayList<NamedFacebookType>();

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
     * The likes.
     * 
     * @return The likes.
     */
    public List<NamedFacebookType> getData() {
      return unmodifiableList(data);
    }

    public boolean addData(NamedFacebookType like) {
      return data.add(like);
    }

    public boolean removeData(NamedFacebookType like) {
      return data.remove(like);
    }

    /**
     * add change count value, if summary is set and count is empty
     */
    @JsonMappingCompleted
    private void fillTotalCount() {
      if (totalCount == 0 && summary != null && summary.has("total_count")) {
        totalCount = summary.getLong("total_count");
      }
    }
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Comments Graph API type</a>.
   * 
   * <p>
   * Please request '{id}/comments?summary=true' explicitly if you would like the summary field which contains the count
   * (now called 'total_count')
   * </p>
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5.3
   */
  public static class Comments implements Serializable {

    /**
     * The number of comments.
     * 
     * <p>
     * Please request '{id}/comments?summary=true' explicitly if you would like the summary field which contains the
     * count (now called 'total_count')
     * </p>
     * 
     * @return The number of comments.
     */
    @Getter
    @Setter
    @Facebook("total_count")
    private Long totalCount = 0L;

    @Facebook
    private JsonObject summary = null;

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

    /**
     * set total count if summary is present
     */
    @JsonMappingCompleted
    private void fillTotalCount() {
      if (totalCount == 0 && summary != null && summary.has("total_count")) {
        totalCount = summary.getLong("total_count");
      }
    }
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/reference/api/privacy-parameter/">Privacy Graph API
   * type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5
   */
  public static class Privacy implements Serializable {

    /**
     * The description of the privacy value.
     * 
     * @return The description of the privacy value.
     */
    @Getter
    @Setter
    @Facebook
    private String value;

    /**
     * The privacy description.
     * 
     * @return The privacy description.
     */
    @Getter
    @Setter
    @Facebook
    private String description;

    /**
     * The privacy friends restriction.
     * 
     * @return The privacy friends restriction.
     */
    @Getter
    @Setter
    @Facebook
    private String friends;

    /**
     * The privacy networks restriction.
     * 
     * @return The privacy networks restriction.
     */
    @Getter
    @Setter
    @Facebook
    private String networks;

    /**
     * For CUSTOM settings, a comma-separated list of user IDs and friend list IDs that "cannot" see the post.
     * 
     * @return The privacy "deny" restriction.
     */
    @Getter
    @Setter
    @Facebook
    private String deny;

    /**
     * For CUSTOM settings, a comma-separated list of user IDs and friend list IDs that "can" see the post. This can
     * also be ALL_FRIENDS or FRIENDS_OF_FRIENDS to include all members of those sets.
     * 
     * @return The privacy "allow" restriction.
     */
    @Getter
    @Setter
    @Facebook
    private String allow;

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

  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Action Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5
   */
  public static class Action implements Serializable {

    /**
     * Gets the name of the action.
     * 
     * @return Gets the name of the action.
     */
    @Getter
    @Setter
    @Facebook
    private String name;

    /**
     * The link for the action.
     * 
     * @return The link for the action.
     */
    @Getter
    @Setter
    @Facebook
    private String link;

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

  }

  /**
   * Represents the Shares included the <a href="http://developers.facebook.com/docs/reference/api/post">Post</a>
   * response. Presently only supports count.
   * 
   * @since 1.6.11
   */
  public static class Shares implements Serializable {

    /**
     * The number of shares.
     * 
     * @return The number of shares.
     */
    @Getter
    @Setter
    @Facebook
    private Long count = 0L;

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

  }

  /**
   * The number of likes on this post.
   * 
   * @return The number of likes on this post.
   */
  public Long getLikesCount() {
    if (getLikes() != null)
      return getLikes().getTotalCount();

    return likesCount;
  }

  /**
   * The number of shares of this post.
   * 
   * @return The number of shares of this post.
   */
  public Long getSharesCount() {
    if (shares != null) {
      return shares.getCount();
    }
    return 0L;
  }

  /**
   * The number of comments of this post.
   * 
   * @return The number of comments of this post.
   */
  public Long getCommentsCount() {
    if (comments != null) {
      return comments.getTotalCount();
    }
    return 0L;
  }

  @JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }

  /**
   * A list of the profiles mentioned or targeted in this post.
   * 
   * @return A list of the profiles mentioned or targeted in this post.
   */
  public List<NamedFacebookType> getTo() {
    return unmodifiableList(to);
  }

  public boolean addTo(NamedFacebookType profile) {
    return to.add(profile);
  }

  public boolean removeTo(NamedFacebookType profile) {
    return to.remove(profile);
  }

  /**
   * A list of available action names and links (including commenting, liking, and an optional app-specified action).
   * 
   * @return A list of available action names and links (including commenting, liking, and an optional app-specified
   *         action).
   */
  public List<Action> getActions() {
    return unmodifiableList(actions);
  }

  public boolean addAction(Action action) {
    return actions.add(action);
  }

  public boolean removeAction(Action action) {
    return actions.remove(action);
  }

  /**
   * A list of properties for this post.
   * <p>
   * This field is undocumented.
   * 
   * @return A list of properties for this post.
   */
  public List<Property> getProperties() {
    return unmodifiableList(properties);
  }

  public boolean addProperty(Property property) {
    return properties.add(property);
  }

  public boolean removeProperty(Property property) {
    return properties.remove(property);
  }

  /**
   * Objects (Users, Pages, etc) tagged as being with the publisher of the post ("Who are you with?" on Facebook).
   * 
   * @return Objects (Users, Pages, etc) tagged as being with the publisher of the post ("Who are you with?" on
   *         Facebook).
   * @since 1.6.10
   */
  public List<NamedFacebookType> getWithTags() {
    return unmodifiableList(withTags);
  }

  public boolean addWithTag(NamedFacebookType withTag) {
    return withTags.add(withTag);
  }

  public boolean removeWithTag(NamedFacebookType withTag) {
    return withTags.remove(withTag);
  }

  /**
   * Objects tagged in the message (Users, Pages, etc).
   * 
   * @return Objects tagged in the message (Users, Pages, etc).
   * @since 1.6.10
   */
  public Map<String, List<MessageTag>> getMessageTags() {
    return unmodifiableMap(messageTags);
  }

  public void addMessageTag(String key, List<MessageTag> tagList) {
    messageTags.put(key, tagList);
  }

  public void removeMessageTag(String key) {
    messageTags.remove(key);
  }
}