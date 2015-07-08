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
import static com.restfb.json.JsonObject.getNames;
import com.restfb.types.Checkin.Place.Location;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
import com.restfb.util.ReflectionUtils;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
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
   * Object that controls news feed targeting for this post.
   *
   * To force Facebook to fill the <code>feed_targeting</code> field you have to fetch the post with the
   * <code>fields=feed_targeting</code> parameter, otherwise the feedTargeting is <code>null</code>.
   *
   *
   * @since 1.11.0
   */
  @Getter
  @Setter
  @Facebook("feed_targeting")
  private FeedTargeting feedTargeting;

  @Getter
  @Setter
  @Facebook
  private Targeting targeting;

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
   * Indicates whether a scheduled post was published. (applies to scheduled Page Post only, for users post and
   * instantly published posts this value is always <code>true</code>)
   *
   * @since 1.10.0
   * @return indicates whether a scheduled post was published
   */
  @Getter
  @Setter
  @Facebook("is_published")
  private Boolean isPublished;

  /**
   * Attachments added to a post.
   *
   * To force Facebook to fill the <code>attachments</code> field you have to fetch the post with the
   * <code>fields=attachments</code> parameter, otherwise the attachments are <code>null</code>.
   *
   * @return Attachment on the post
   */
  @Getter
  @Setter
  @Facebook
  private Attachments attachments;

  /**
   * Full picture URL.
   * 
   * you get the url to this posts full picture.
   * 
   * To force Facebook to fill the <code>full_picture</code> field you have to fetch the post with the
   * <code>fields=full_picture</code> parameter, otherwise the full_picture is <code>null</code>.
   * 
   * @return String representing the url to the post's full picture
   */
  @Getter
  @Setter
  @Facebook("full_picture")
  private String fullPicture;

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

    if (rawMessageTags == null) {
      return;
    }

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

  public static class Targeting implements Serializable {

    @Facebook
    protected List<NamedFacebookType> cities = new ArrayList<NamedFacebookType>();
    @Facebook
    protected List<String> countries = new ArrayList<String>();
    @Facebook
    protected List<Integer> regions = new ArrayList<Integer>();
    @Facebook
    protected List<Integer> locales = new ArrayList<Integer>();

    public boolean addCity(NamedFacebookType city) {
      return cities.add(city);
    }

    public boolean addCountry(String country) {
      return countries.add(country);
    }

    public boolean addLocale(Integer locale) {
      return locales.add(locale);
    }

    public boolean addRegion(Integer region) {
      return regions.add(region);
    }

    /**
     * Values of targeting cities.
     *
     * Use type of adcity to find Targeting Options and use the returned key to specify.
     *
     * @return list of cities
     */
    public List<NamedFacebookType> getCities() {
      return unmodifiableList(cities);
    }

    /**
     * Values of targeting countries.
     *
     * You can specify up to 25 countries. Use ISO 3166 format codes.
     *
     * @return list of targeting countries.
     */
    public List<String> getCountries() {
      return unmodifiableList(countries);
    }

    /**
     * Targeted locales.
     *
     * Use type of adlocale to find Targeting Options and use the returned key to specify.
     *
     * @return list of locales
     */
    public List<Integer> getLocales() {
      return unmodifiableList(locales);
    }

    /**
     * Values of targeting regions.
     *
     * Use type of adregion to find Targeting Options and use the returned key to specify.
     *
     * @return list of regions
     */
    public List<Integer> getRegions() {
      return unmodifiableList(regions);
    }

    public boolean removeCity(NamedFacebookType city) {
      return cities.remove(city);
    }

    public boolean removeCountry(String country) {
      return countries.remove(country);
    }

    public boolean removeLocale(Integer locale) {
      return locales.remove(locale);
    }

    public boolean removeRegion(Integer region) {
      return regions.remove(region);
    }

    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }

  }

  /**
   * Object that controls news feed targeting for this post.
   *
   * Anyone in these groups will be more likely to see this post, others will be less likely, but may still see it
   * anyway. Any of the targeting fields shown here can be used, none are required (applies to Pages only).
   *
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post#fields">Feed Targeting API type</a>.
   */
  public static class FeedTargeting extends Targeting {

    /**
     * Maximum age.
     */
    @Getter
    @Setter
    @Facebook("age_max")
    private Integer ageMax;

    /**
     * Must be 13 or higher. Default is 0
     */
    @Getter
    @Setter
    @Facebook("age_min")
    private Integer ageMin;

    @Facebook("college_majors")
    private List<String> collegeMajors = new ArrayList<String>();

    @Facebook("college_networks")
    private List<FacebookType> collegeNetworks = new ArrayList<FacebookType>();

    @Facebook("college_years")
    private List<Integer> collegeYears = new ArrayList<Integer>();

    @Facebook("education_statuses")
    private List<Integer> educationStatuses = new ArrayList<Integer>();

    @Facebook("fan_of")
    private List<String> fanOf = new ArrayList<String>();

    @Facebook
    private List<Integer> genders = new ArrayList<Integer>();

    @Facebook("interested_in")
    private List<Integer> interestedIn = new ArrayList<Integer>();

    @Facebook("relationship_statuses")
    private List<Integer> relationshipStatuses = new ArrayList<Integer>();

    @Facebook("work_networks")
    private List<NamedFacebookType> workNetworks = new ArrayList<NamedFacebookType>();

    /**
     * Target people who majored in these college subjects.
     *
     * Limited to 200 college major values. Use type of adcollegemajor to find Targeting Options and use the returned
     * name to specify.
     *
     * @return list of college majors
     */
    public List<String> getCollegeMajors() {
      return unmodifiableList(collegeMajors);
    }

    public boolean addCollegeMajor(String collegeMajor) {
      return collegeMajors.add(collegeMajor);
    }

    public boolean removeCollegeMajor(String collegeMajor) {
      return collegeMajors.remove(collegeMajor);
    }

    /**
     * Colleges, for college graduates.
     *
     * Limit is 200 values.
     *
     * @return list of colleges
     */
    public List<FacebookType> getCollegeNetworks() {
      return unmodifiableList(collegeNetworks);
    }

    public boolean addCollegeNetwork(FacebookType collegeNetwork) {
      return collegeNetworks.add(collegeNetwork);
    }

    public boolean removeCollegeNetwork(FacebookType collegeNetwork) {
      return collegeNetworks.remove(collegeNetwork);
    }

    /**
     * Array of integers for graduation year from college.
     *
     * @return graduation year list
     */
    public List<Integer> getCollegeYears() {
      return unmodifiableList(collegeYears);
    }

    public boolean addCollegeYear(Integer collegeYear) {
      return collegeYears.add(collegeYear);
    }

    public boolean removeCollegeYear(Integer collegeYear) {
      return collegeYears.remove(collegeYear);
    }

    /**
     * Array of integers for targeting based on education level.
     *
     * Use 1 for high school, 2 for undergraduate, and 3 for alum (or localized equivalents).
     *
     * @return list of education levels
     */
    public List<Integer> getEducationStatuses() {
      return unmodifiableList(educationStatuses);
    }

    public boolean addEducationStatus(Integer educationStatus) {
      return educationStatuses.add(educationStatus);
    }

    public boolean removeEducationStatus(Integer educationStatus) {
      return educationStatuses.remove(educationStatus);
    }

    /**
     * List of object ids.
     *
     * the user should be fan of these objects (interests).
     *
     * @return list of object ids
     */
    public List<String> getFanOf() {
      return unmodifiableList(fanOf);
    }

    public boolean addFanOf(String interestId) {
      return fanOf.add(interestId);
    }

    public boolean removeFanOf(String interestId) {
      return fanOf.remove(interestId);
    }

    /**
     * Target specific genders.
     *
     * 1 targets all male viewers and 2 females. Default is to target both.
     *
     * @return list of genders
     */
    public List<Integer> getGenders() {
      return unmodifiableList(genders);
    }

    public boolean addGender(Integer gender) {
      return genders.add(gender);
    }

    public boolean removeGender(Integer gender) {
      return genders.remove(gender);
    }

    /**
     * Indicates targeting based on the 'interested in' field of the user profile.
     *
     * You can specify an integer of 1 to indicate male, 2 indicates female. Default is all types.
     *
     * Please note 'interested in' targeting is not available in France due to local laws.
     *
     * @return list of 'interested in' types
     */
    public List<Integer> getInterestedIn() {
      return unmodifiableList(interestedIn);
    }

    public boolean addInterestedIn(Integer interest) {
      return interestedIn.add(interest);
    }

    public boolean removeInterestedIn(Integer interest) {
      return interestedIn.remove(interest);
    }

    /**
     * Array of integers for targeting based on relationship status.
     *
     * Use 1 for single, 2 for 'in a relationship', 3 for married, and 4 for engaged. Default is all types.
     *
     * @return list of relationship statuses
     */
    public List<Integer> getRelationshipStatuses() {
      return unmodifiableList(relationshipStatuses);
    }

    public boolean addRelationshipStatus(Integer relationshipStatus) {
      return relationshipStatuses.add(relationshipStatus);
    }

    public boolean removeRelationshipStatus(Integer relationshipStatus) {
      return relationshipStatuses.remove(relationshipStatus);
    }

    /**
     * Company, organization, or other workplace.
     *
     * <b>Name</b>: Name of targeted workplace. Use type of adworkplace to find Targeting Options and use the returned
     * name to specify this.
     *
     * <b>Id</b>: Unique ID of targeted workplace. Use type of adworkplace to find Targeting Options and use the
     * returned key to specify this (must match paired name value).
     *
     * @return list of work networks
     */
    public List<NamedFacebookType> getWorkNetworks() {
      return unmodifiableList(workNetworks);
    }

    public boolean addWorkNetwork(NamedFacebookType workNetwork) {
      return workNetworks.add(workNetwork);
    }

    public boolean removeWorkNetwork(NamedFacebookType workNetwork) {
      return workNetworks.remove(workNetwork);
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
   * Represents the attachments included in<a href="http://developers.facebook.com/docs/reference/api/post">Graph API
   * Post type</a>.
   *
   * @author <a href="https://github.com/kevinleturc/">Kevin Leturc</a>
   * @since 1.12.0
   */
  public static class Attachments implements Serializable {

    /**
     * All media attachments associated with this post.
     *
     * @return All media attachments associated with this post.
     */
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
   * The number of likes on this post.
   *
   * @return The number of likes on this post.
   */
  public Long getLikesCount() {
    if (getLikes() != null) {
      return getLikes().getTotalCount();
    }

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