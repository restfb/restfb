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

import static com.restfb.json.JsonObject.getNames;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static java.util.Collections.unmodifiableList;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.JsonObject;
import com.restfb.types.Checkin.Place.Location;
import com.restfb.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/post/">Post Graph API type</a>.
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
   * ID of the parent post
   *
   * @return ID of the parent post
   */
  @Getter
  @Setter
  @Facebook("parent_id")
  private String parentId;

  /**
   * The permanent static URL to the post on www.facebook.com.
   * <p>
   * Example: https://www.facebook.com/FacebookforDevelopers/posts/10153449196353553
   * </p>
   * 
   * @return The permanent static URL to the post on www.facebook.com.
   */
  @Getter
  @Setter
  @Facebook("permalink_url")
  private String permalinkUrl;

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
   * Status of the promotion, if the post was promoted.
   *
   * @return Status of the promotion, if the post was promoted
   */
  @Getter
  @Setter
  @Facebook("promotion_status")
  private String promotionStatus;

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

  /**
   * The profile this was posted on if different from the author.
   *
   * @RestFB.GraphApi.Since 2.5
   * @return The profile this was posted on if different from the author
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType target;

  /**
   * Ads targeting information of the post.
   *
   * @return Ads targeting information of the post
   */
  @Getter
  @Setter
  @Facebook
  private Targeting targeting;

  /**
   * Timeline visibility information of the post.
   *
   * @return imeline visibility information of the post
   */
  @Getter
  @Setter
  @Facebook("timeline_visibility")
  private String timelineVisibility;

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
   * The reactions for this post.
   *
   * @return The reactions for this post.
   */
  @Getter
  @Setter
  @Facebook
  private Reactions reactions;

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
  private String rawMessageTags;

  private List<MessageTag> messageTags = new ArrayList<MessageTag>();

  @Facebook("story_tags")
  private String rawStoryTags;

  private List<MessageTag> storyTags = new ArrayList<MessageTag>();

  /**
   * UNIX timestamp of the scheduled publish time for the post.
   *
   * @return UNIX timestamp of the scheduled publish time for the post
   */
  @Facebook("scheduled_publish_time")
  private String rawScheduledPublishTime;

  /**
   * UNIX timestamp of the scheduled publish time for the post.
   *
   * @return UNIX timestamp of the scheduled publish time for the post
   */
  @Getter
  @Setter
  private Date scheduledPublishTime;

  /**
   * Number of times the post has been shared.
   *
   * @return Number of times the post has been shared
   */
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
   * Whether the post can be promoted on Instagram.
   * <p>
   * It returns the enum <code>eligible</code> if it can be promoted. Otherwise it returns an enum for why it cannot be
   * promoted
   * </p>
   * 
   * @return Whether the post can be promoted on Instagram
   */
  @Getter
  @Setter
  @Facebook("instagram_eligibility")
  private String instagramEligibility;

  /**
   * Whether or not the post references an app.
   *
   * @return Whether or not the post references an app
   */
  @Getter
  @Setter
  @Facebook("is_app_share")
  private Boolean isAppShare;

  /**
   * Whether the post has expiration time that has passed
   *
   * @return Whether the post has expiration time that has passed
   */
  @Getter
  @Setter
  @Facebook("is_expired")
  private Boolean isExpired;

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
   * Whether this post can be promoted in Instagram.
   *
   * @return Whether this post can be promoted in Instagram
   */
  @Getter
  @Setter
  @Facebook("is_instagram_eligible")
  private Boolean isInstagramEligible;

  /**
   * Whether the post is currently popular.
   *
   * Based on whether the total actions as a percentage of reach exceeds a certain threshold
   *
   * @return Whether the post is currently popular
   */
  @Getter
  @Setter
  @Facebook("is_popular")
  private Boolean isPopular;

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
   * Whether the post is a spherical video post.
   *
   * @return Whether the post is a spherical video post
   */
  @Getter
  @Setter
  @Facebook("is_spherical")
  private Boolean isSpherical;

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

    if (rawMessageTags != null) {
      messageTags = createTags(rawMessageTags, jsonMapper);
    }

    if (rawStoryTags != null) {
      storyTags = createTags(rawStoryTags, jsonMapper);
    }

  }

  private List<MessageTag> createTags(String rawTags, JsonMapper jsonMapper) {

    try {
      return jsonMapper.toJavaList(rawTags, MessageTag.class);
    } catch (FacebookJsonMappingException je) {
      // message tags not in Graph API 2.5 format, ignore this exception and try another way
    }

    try {
      List<MessageTag> resultList = new ArrayList<MessageTag>();
      JsonObject rawMessageTagsObject = jsonMapper.toJavaObject(rawTags, JsonObject.class);
      for (String key : getNames(rawMessageTagsObject)) {
        String tagArrayString = rawMessageTagsObject.get(key).toString();
        resultList.addAll(jsonMapper.toJavaList(tagArrayString, MessageTag.class));
      }
      return resultList;
    } catch (FacebookJsonMappingException je) {
      return new ArrayList<MessageTag>();
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
  public static class MessageTag extends com.restfb.types.MessageTag {
    // remove this on next major release
  }

  /**
   * Represents the undocumented {@code Property} type.
   *
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.4
   */
  public static class Property extends AbstractFacebookType {

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

  }

  /**
   * @TODO: remove on type refactoring
   */
  public static class Likes extends com.restfb.types.Likes {

  }

  /**
   * @TODO: remove on type refactoring
   */
  public static class Comments extends com.restfb.types.Comments {

  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/privacy/">Privacy Graph API
   * type</a>.
   * 
   * @TODO remove with next major version
   * @deprecated use {@link com.restfb.types.Privacy} instead
   */
  @Deprecated
  public static class Privacy extends com.restfb.types.Privacy {
    // only here due to API reason
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/post">Action Graph API type</a>.
   *
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5
   */
  public static class Action extends AbstractFacebookType {

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

  }

  public static class KeyedType extends NamedFacebookType {

    @Getter
    @Setter
    @Facebook
    private String key;

    @JsonMappingCompleted
    private void replaceId() {
      if (getId() == null && key != null) {
        setId(key);
      }

      if (key == null && getId() != null) {
        setKey(getId());
      }
    }
  }

  public static class Targeting extends AbstractFacebookType {

    /**
     * @RestFB.GraphApi.Since 2.8
     */
    @Getter
    @Setter
    @Facebook("geo_locations")
    FeedTargeting geoLocations;

    @Facebook
    protected List<KeyedType> cities = new ArrayList<KeyedType>();
    @Facebook
    protected List<String> countries = new ArrayList<String>();

    @Facebook
    protected List<KeyedType> regions = new ArrayList<KeyedType>();

    @Facebook("regions")
    private JsonObject rawRegions;

    @JsonMappingCompleted
    private void convertList(JsonMapper mapper) {
      if (rawRegions != null) {
        Iterator<String> it = rawRegions.keys();
        while (it.hasNext()) {
          String region = rawRegions.getString(it.next());
          regions.add(mapper.toJavaObject(region, KeyedType.class));
        }
      }
    }

    @Facebook
    protected List<Integer> locales = new ArrayList<Integer>();

    public boolean addCity(KeyedType city) {
      if (geoLocations != null) {
        return geoLocations.addCity(city);
      }
      return cities.add(city);
    }

    public boolean addCountry(String country) {
      if (geoLocations != null) {
        return geoLocations.addCountry(country);
      }
      return countries.add(country);
    }

    public boolean addLocale(Integer locale) {
      if (geoLocations != null) {
        return geoLocations.addLocale(locale);
      }
      return locales.add(locale);
    }

    public boolean addRegion(KeyedType region) {
      if (geoLocations != null) {
        return geoLocations.addRegion(region);
      }
      return regions.add(region);
    }

    /**
     * Values of targeting cities.
     *
     * Use type of adcity to find Targeting Options and use the returned key to specify.
     *
     * @return list of cities
     */
    public List<KeyedType> getCities() {
      if (geoLocations != null) {
        return geoLocations.getCities();
      }
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
      if (geoLocations != null) {
        return geoLocations.getCountries();
      }
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
      if (geoLocations != null && !geoLocations.getLocales().isEmpty()) {
        return geoLocations.getLocales();
      }
      return unmodifiableList(locales);
    }

    /**
     * Values of targeting regions.
     *
     * Use type of adregion to find Targeting Options and use the returned key to specify.
     *
     * @return list of regions
     */
    public List<KeyedType> getRegions() {
      if (geoLocations != null) {
        return geoLocations.getRegions();
      }
      return unmodifiableList(regions);
    }

    public boolean removeCity(NamedFacebookType city) {
      if (geoLocations != null) {
        return geoLocations.removeCity(city);
      }
      return cities.remove(city);
    }

    public boolean removeCountry(String country) {
      if (geoLocations != null) {
        return geoLocations.removeCountry(country);
      }
      return countries.remove(country);
    }

    public boolean removeLocale(Integer locale) {
      if (geoLocations != null) {
        return geoLocations.removeLocale(locale);
      }
      return locales.remove(locale);
    }

    public boolean removeRegion(NamedFacebookType region) {
      if (geoLocations != null) {
        return geoLocations.removeRegion(region);
      }
      return regions.remove(region);
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

    @Getter
    @Setter
    private Date relevantUntilTs;

    @Facebook("relevant_until_ts")
    private String rawRelevantUntilTs;

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

    @Facebook
    private List<String> interests = new ArrayList<String>();

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
     * Indicates targeting based on the 'interests' field of the user profile.
     *
     * @return list of 'interests' types
     */
    public List<String> getInterests() {
      return unmodifiableList(interests);
    }

    public boolean addInterests(String interest) {
      return interests.add(interest);
    }

    public boolean removeInterests(String interest) {
      return interests.remove(interest);
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

    @JsonMappingCompleted
    private void createTimeStamp() {
      relevantUntilTs = DateUtils.toDateFromLongFormat(rawRelevantUntilTs);
    }
  }

  /**
   * Represents the Shares included the <a href="http://developers.facebook.com/docs/reference/api/post">Post</a>
   * response. Presently only supports count.
   *
   * @since 1.6.11
   */
  public static class Shares extends AbstractFacebookType {

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

  }

  /**
   * Represents the attachments included in<a href="http://developers.facebook.com/docs/reference/api/post">Graph API
   * Post type</a>.
   *
   * @author <a href="https://github.com/kevinleturc/">Kevin Leturc</a>
   * @since 1.12.0
   */
  public static class Attachments extends AbstractFacebookType {

    /**
     * All media attachments associated with this post.
     *
     * @return All media attachments associated with this post.
     */
    @Facebook
    private List<StoryAttachment> data = new ArrayList<StoryAttachment>();

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
   * The number of reactions on this post.
   *
   * @return The number of reactions on this post.
   */
  public Long getReactionsCount() {
    if (getReactions() != null) {
      return getReactions().getTotalCount();
    }

    return 0L;
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
    scheduledPublishTime = toDateFromLongFormat(rawScheduledPublishTime);
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
  public List<MessageTag> getMessageTags() {
    return unmodifiableList(messageTags);
  }

  public void addMessageTag(MessageTag messageTag) {
    messageTags.add(messageTag);
  }

  public void removeMessageTag(MessageTag messageTag) {
    messageTags.remove(messageTag);
  }

  /**
   * Objects tagged in the story (Users, Pages, etc).
   *
   * @return Objects tagged in the story (Users, Pages, etc).
   */
  public List<MessageTag> getStoryTags() {
    return unmodifiableList(storyTags);
  }

  public void addStoryTag(MessageTag messageTag) {
    storyTags.add(messageTag);
  }

  public void removeStoryTag(MessageTag messageTag) {
    storyTags.remove(messageTag);
  }
}