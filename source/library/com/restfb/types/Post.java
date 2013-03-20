/*
 * Copyright (c) 2010-2013 Mark Allen.
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

import java.io.Serializable;
import java.util.*;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.JsonObject;
import com.restfb.types.Checkin.Place.Location;
import com.restfb.util.ReflectionUtils;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Post Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
@SuppressWarnings("deprecation")
public class Post extends NamedFacebookType {
  @Facebook
  protected CategorizedFacebookType from;

  @Facebook
  protected String message;

  @Facebook
  protected String picture;

  @Facebook
  protected String link;

  @Facebook
  protected String caption;

  @Facebook
  protected String description;

  @Facebook
  protected String source;

  @Facebook
  protected String type;

  @Facebook
  protected NamedFacebookType application;

  @Facebook
  protected String icon;

  @Facebook
  protected String attribution;

  @Facebook
  protected Privacy privacy;

  /**
   * Duplicate mapping for "likes" since FB can return it differently in different situations.
   */
  @Facebook("likes")
  protected Long likesCount;

  /**
   * Duplicate mapping for "likes" since FB can return it differently in different situations.
   */
  @Facebook
  protected Likes likes;

  @Facebook("created_time")
  protected String createdTime;

  @Facebook("updated_time")
  protected String updatedTime;

  @Facebook("object_id")
  protected String objectId;

  @Facebook("status_type")
  protected String statusType;

  @Facebook
  protected Comments comments;

  @Facebook
  protected com.restfb.types.Place place;

  @Facebook
  protected List<NamedFacebookType> to = new ArrayList<NamedFacebookType>();

  @Facebook
  protected List<Action> actions = new ArrayList<Action>();

  @Facebook
  protected List<Property> properties = new ArrayList<Property>();

  @Facebook("with_tags")
  protected List<NamedFacebookType> withTags = new ArrayList<NamedFacebookType>();

  @Facebook("message_tags")
  protected JsonObject rawMessageTags;

  protected Map<String, List<MessageTag>> messageTags = new HashMap<String, List<MessageTag>>();

  @Facebook
  protected Shares shares;

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
      String messageTagJson = rawMessageTags.getString(key).toString();
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
    @Facebook
    protected Location location;

    private static final long serialVersionUID = 1L;

    /**
     * The location of this place.
     * 
     * @return The location of this place.
     */
    public Location getLocation() {
      return location;
    }
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Message Tag Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.10
   */
  public static class MessageTag extends NamedFacebookType {
    @Facebook
    protected Integer offset;

    @Facebook
    protected Integer length;

    private static final long serialVersionUID = 1L;

    /**
     * The offset, within the message field, of the object mentioned.
     * 
     * @return The offset, within the message field, of the object mentioned.
     */
    public Integer getOffset() {
      return offset;
    }

    /**
     * The length, within the message field, of the object mentioned.
     * 
     * @return The length, within the message field, of the object mentioned.
     */
    public Integer getLength() {
      return length;
    }
  }

  /**
   * Represents the undocumented {@code Property} type.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.4
   */
  public static class Property implements Serializable {
    @Facebook
    protected String name;

    @Facebook
    protected String text;

    @Facebook
    protected String href;

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
     * The name of the property.
     * 
     * @return The name of the property.
     */
    public String getName() {
      return name;
    }

    /**
     * The text of the property.
     * 
     * @return The text of the property.
     */
    public String getText() {
      return text;
    }

    /**
     * The URL of the property.
     * 
     * @return The URL of the property.
     */
    public String getHref() {
      return href;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setText(String text) {
      this.text = text;
    }

    public void setHref(String href) {
      this.href = href;
    }
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Likes Graph API type</a>
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6
   */
  public static class Likes implements Serializable {
    @Facebook
    protected Long count;

    @Facebook
    protected List<NamedFacebookType> data = new ArrayList<NamedFacebookType>();

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
     * The number of likes.
     * 
     * @return The number of likes.
     */
    public Long getCount() {
      return count;
    }

    /**
     * The likes.
     * 
     * @return The likes.
     */
    public List<NamedFacebookType> getData() {
      return data;
    }

    public void setCount(Long count) {
      this.count = count;
    }

    public void setData(List<NamedFacebookType> data) {
      this.data = data;
    }
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Comments Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5.3
   */
  public static class Comments implements Serializable {
    @Facebook
    protected Long count;

    @Facebook
    protected List<Comment> data = new ArrayList<Comment>();

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
      return data;
    }

    public void setCount(Long count) {
      this.count = count;
    }

    public void setData(List<Comment> data) {
      this.data = data;
    }
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Privacy Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5
   */
  public static class Privacy implements Serializable {
    @Facebook
    protected String value;

    @Facebook
    protected String description;

    @Facebook
    protected String friends;

    @Facebook
    protected String networks;

    @Facebook
    protected String deny;

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
     * The description of the privacy value.
     * 
     * @return The description of the privacy value.
     */
    public String getValue() {
      return value;
    }

    /**
     * The privacy description.
     * 
     * @return The privacy description.
     */
    public String getDescription() {
      return description;
    }

    /**
     * The privacy friends restriction.
     * 
     * @return The privacy friends restriction.
     */
    public String getFriends() {
      return friends;
    }

    /**
     * The privacy networks restriction.
     * 
     * @return The privacy networks restriction.
     */
    public String getNetworks() {
      return networks;
    }

    /**
     * The privacy "deny" restriction.
     * 
     * @return The privacy "deny" restriction.
     */
    public String getDeny() {
      return deny;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public void setFriends(String friends) {
      this.friends = friends;
    }

    public void setNetworks(String networks) {
      this.networks = networks;
    }

    public void setDeny(String deny) {
      this.deny = deny;
    }
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Action Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5
   */
  public static class Action implements Serializable {
    @Facebook
    protected String name;

    @Facebook
    protected String link;

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
     * Gets the name of the action.
     * 
     * @return Gets the name of the action.
     */
    public String getName() {
      return name;
    }

    /**
     * The link for the action.
     * 
     * @return The link for the action.
     */
    public String getLink() {
      return link;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setLink(String link) {
      this.link = link;
    }
  }

  /**
   * Represents the Shares included the <a href="http://developers.facebook.com/docs/reference/api/post">Post</a>
   * response. Presently only supports count.
   * 
   * @since 1.6.11
   */
  public static class Shares implements Serializable {
    @Facebook
    protected Long count;

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
     * The number of shares.
     * 
     * @return The number of shares.
     */
    public Long getCount() {
      return count;
    }

    public void setCount(Long count) {
      this.count = count;
    }
  }

  /**
   * An object containing the ID and name of the user who posted the message.
   * 
   * @return An object containing the ID and name of the user who posted the message.
   */
  public CategorizedFacebookType getFrom() {
    return from;
  }

  /**
   * The message.
   * 
   * @return The message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * If available, a link to the picture included with this post.
   * 
   * @return If available, a link to the picture included with this post.
   */
  public String getPicture() {
    return picture;
  }

  /**
   * The link attached to this post.
   * 
   * @return The link attached to this post.
   */
  public String getLink() {
    return link;
  }

  /**
   * The caption of the link (appears beneath the link name).
   * 
   * @return The caption of the link (appears beneath the link name).
   */
  public String getCaption() {
    return caption;
  }

  /**
   * A description of the link (appears beneath the link caption).
   * 
   * @return A description of the link (appears beneath the link caption).
   */
  public String getDescription() {
    return description;
  }

  /**
   * If available, the source link attached to this post (for example, a flash or video file).
   * 
   * @return If available, the source link attached to this post (for example, a flash or video file).
   */
  public String getSource() {
    return source;
  }

  /**
   * A link to an icon representing the type of this post.
   * 
   * @return A link to an icon representing the type of this post.
   */
  public String getIcon() {
    return icon;
  }

  /**
   * A string indicating which application was used to create this post.
   * 
   * @return A string indicating which application was used to create this post.
   */
  public String getAttribution() {
    return attribution;
  }

  /**
   * The privacy settings for this post.
   * 
   * @return The privacy settings for this post.
   */
  public Privacy getPrivacy() {
    return privacy;
  }

  /**
   * The type of post this is, for example {@code "link"}.
   * 
   * @return The type of post this is, for example {@code "link"}.
   */
  public String getType() {
    return type;
  }

  /**
   * The application used to create this post.
   * 
   * @return The application used to create this post.
   */
  public NamedFacebookType getApplication() {
    return application;
  }

  /**
   * The number of likes on this post.
   * 
   * @return The number of likes on this post.
   */
  public Long getLikesCount() {
    if (getLikes() != null)
      return getLikes().getCount();

    return likesCount;
  }

  /**
   * The likes on this post.
   * <p>
   * Sometimes this can be {@code null} - check {@link #getLikesCount()} instead in that case.
   * 
   * @return The likes on this post.
   */
  public Likes getLikes() {
    return likes;
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
   * The time the post was initially published.
   * 
   * @return The time the post was initially published.
   */
  public Date getCreatedTime() {
    return toDateFromLongFormat(createdTime);
  }

  /**
   * The time of the last comment on this post.
   * 
   * @return The time of the last comment on this post.
   */
  public Date getUpdatedTime() {
    return toDateFromLongFormat(updatedTime);
  }

  /**
   * The Facebook object id for an uploaded photo or video.
   * 
   * @return The Facebook object id for an uploaded photo or video.
   * @since 1.6.5
   */
  public String getObjectId() {
    return objectId;
  }

  /**
   * The {@code status_type} of post this is, for example {@code "approved_friend"}.
   * 
   * @return The {@code status_type} of post this is, for example {@code "approved_friend"}.
   * @since 1.6.12
   */
  public String getStatusType() {
    return statusType;
  }

  /**
   * The comments for this post.
   * 
   * @return The comments for this post.
   */
  public Comments getComments() {
    return comments;
  }

  /**
   * The place where this post occurred.
   * 
   * @return The place where this post occurred.
   * @since 1.6.8
   */
  public com.restfb.types.Place getPlace() {
    return place;
  }

  /**
   * A list of the profiles mentioned or targeted in this post.
   * 
   * @return A list of the profiles mentioned or targeted in this post.
   */
  public List<NamedFacebookType> getTo() {
    return to;
  }

  /**
   * A list of available action names and links (including commenting, liking, and an optional app-specified action).
   * 
   * @return A list of available action names and links (including commenting, liking, and an optional app-specified
   *         action).
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * A list of properties for this post.
   * <p>
   * This field is undocumented.
   * 
   * @return A list of properties for this post.
   */
  public List<Property> getProperties() {
    return properties;
  }

  /**
   * Objects (Users, Pages, etc) tagged as being with the publisher of the post ("Who are you with?" on Facebook).
   * 
   * @return Objects (Users, Pages, etc) tagged as being with the publisher of the post ("Who are you with?" on
   *         Facebook).
   * @since 1.6.10
   */
  public List<NamedFacebookType> getWithTags() {
    return withTags;
  }

  /**
   * Objects tagged in the message (Users, Pages, etc).
   * 
   * @return Objects tagged in the message (Users, Pages, etc).
   * @since 1.6.10
   */
  public Map<String, List<MessageTag>> getMessageTags() {
    return messageTags;
  }

  public void setFrom(CategorizedFacebookType from) {
    this.from = from;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setApplication(NamedFacebookType application) {
    this.application = application;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public void setAttribution(String attribution) {
    this.attribution = attribution;
  }

  public void setPrivacy(Privacy privacy) {
    this.privacy = privacy;
  }

  public void setLikesCount(Long likesCount) {
    this.likesCount = likesCount;
  }

  public void setLikes(Likes likes) {
    this.likes = likes;
  }

  public void setCreatedTime(String createdTime) {
    this.createdTime = createdTime;
  }

  public void setUpdatedTime(String updatedTime) {
    this.updatedTime = updatedTime;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public void setStatusType(String statusType) {
    this.statusType = statusType;
  }

  public void setComments(Comments comments) {
    this.comments = comments;
  }

  public void setPlace(com.restfb.types.Place place) {
    this.place = place;
  }

  public void setTo(List<NamedFacebookType> to) {
    this.to = to;
  }

  public void setActions(List<Action> actions) {
    this.actions = actions;
  }

  public void setProperties(List<Property> properties) {
    this.properties = properties;
  }

  public void setWithTags(List<NamedFacebookType> withTags) {
    this.withTags = withTags;
  }

  public void setRawMessageTags(JsonObject rawMessageTags) {
    this.rawMessageTags = rawMessageTags;
  }

  public void setMessageTags(Map<String, List<MessageTag>> messageTags) {
    this.messageTags = messageTags;
  }

  public void setShares(Shares shares) {
    this.shares = shares;
  }
}
