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

import static com.restfb.util.DateUtils.toDateFromShortFormat;
import static com.restfb.util.StringUtils.isBlank;
import static java.util.Collections.unmodifiableList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.annotation.GraphAPI;
import com.restfb.types.features.HasCover;
import com.restfb.types.features.HasProfilePicture;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/user">User Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author Patrick Alberts
 * @since 1.5
 */
public class User extends NamedFacebookType implements HasProfilePicture, HasCover {

  /**
   * Social context for this person
   *
   * @return Social context for this person
   */
  @Getter
  @Setter
  @Facebook
  private FacebookType context;

  /**
   * The user's first name.
   * 
   * @return The user's first name.
   */
  @Getter
  @Setter
  @Facebook("first_name")
  private String firstName;

  /**
   * The user's middle name.
   * 
   * @return The user's middle name.
   */
  @Getter
  @Setter
  @Facebook("middle_name")
  private String middleName;

  /**
   * The person's name formatted to correctly handle Chinese, Japanese, or Korean ordering.
   *
   * @return The person's name formatted to correctly handle Chinese, Japanese, or Korean ordering
   */
  @Getter
  @Setter
  @Facebook("name_format")
  private String nameFormat;

  /**
   * The person's payment pricepoints
   *
   * @return The person's payment pricepoints
   */
  @Getter
  @Setter
  @Facebook("payment_pricepoints")
  private PaymentPricepoints paymentPricepoints;

  /**
   * The profile picture URL of the Messenger user. The URL will expire.
   */
  @Facebook("profile_pic")
  private String profilePic;

  /**
   * The user's last name.
   * 
   * @return The user's last name.
   */
  @Getter
  @Setter
  @Facebook("last_name")
  private String lastName;

  /**
   * Shortened, locale-aware name for the person.
   *
   * @return Shortened, locale-aware name for the person
   */
  @Getter(onMethod_ = {@GraphAPI(since = "2.9")})
  @Setter
  @Facebook("short_name")
  @GraphAPI(since = "2.9")
  private String shortName;

  /**
   * The person's cover photo
   *
   * @return The person's cover photo
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook
  private CoverPhoto cover;

  /**
   * A link to the person's Timeline.
   *
   * The link will only resolve if the person clicking the link is logged
   * into Facebook and is a friend of the person whose profile is being viewed.
   * 
   * @return A link to the user's profile.
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * The user's biographical snippet.
   *
   * @return The user's biographical snippet.
   */
  @Getter(onMethod_ = {@GraphAPI(until = "2.7")})
  @Setter
  @Facebook
  @GraphAPI(until = "2.7")
  private String bio;

  /**
   * The user's favorite quotes.
   * 
   * @return The user's favorite quotes.
   */
  @Getter
  @Setter
  @Facebook
  private String quotes;

  /**
   * Unspecific age range that this person's age fits into.
   * 
   * @return The user's age range
   */
  @Getter
  @Setter
  @Facebook("age_range")
  private AgeRange ageRange;

  /**
   * The user's birthday as a {@code String}.
   * <p>
   * Will always succeed, even if the user has specified month/year format only.
   * If you'd like to use a typed version of this accessor,
   * call {@link #getBirthdayAsDate()} instead.
   *
   * "The person's birthday. This is a fixed format string, like `MM/DD/YYYY`.
   * However, people can control who can see the year they were
   * born separately from the month and day so this string can be
   * only the year (YYYY) or the month + day (MM/DD)",
   * 
   * @return The user's birthday as a {@code String}.
   */
  @Getter
  @Setter
  @Facebook
  private String birthday;

  /**
   * The proxied or contact email address granted by the user.
   * 
   * @return The proxied or contact email address granted by the user.
   */
  @Getter
  @Setter
  @Facebook
  private String email;

  /**
   * The user's timezone offset.
   * 
   * @return The user's timezone offset.
   */
  @Getter
  @Setter
  @Facebook
  private Double timezone;

  /**
   * Indicates whether the account has been verified. This is distinct from the <code>is_verified</code> field. Someone
   * is considered verified if they take any of the following actions:
   * <ul>
   * <li>Register for mobile</li>
   * <li>Confirm their account via SMS</li>
   * <li>Enter a valid credit card</li>
   * </ul>
   * 
   * @return User verification status.
   */
  @Getter
  @Setter
  @Facebook
  private Boolean verified;

  /**
   * Video upload limits
   *
   * @return Video upload limits
   */
  @Getter
  @Setter
  @Facebook("video_upload_limits")
  private VideoUploadLimits videoUploadLimits;

  /**
   * The user's gender.
   * 
   * @return The user's gender.
   */
  @Getter
  @Setter
  @Facebook
  private String gender;

  /**
   * The user's locale.
   * 
   * @return The user's locale.
   */
  @Getter
  @Setter
  @Facebook
  private String locale;

  /**
   * The person's PGP public key
   *
   * @return The person's PGP public key
   */
  @Getter
  @Setter
  @Facebook("public_key")
  private String publicKey;

  /**
   * The user's Likes
   *
   * @return the user's Likes
   */
  @Getter
  @Setter
  @Facebook
  private Likes likes;

  @Facebook("picture")
  private transient String rawPicture;

  /**
   * The user's picture, if provided.
   * 
   * To force Facebook to fill the <code>picture</code> field you have to fetch the user with the
   * <code>fields=picture</code> parameter, otherwise the picture is <code>null</code>.
   * 
   * @return the user's picture as ProfilePictureSource object
   * @since 1.6.16
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  private ProfilePictureSource picture;

  /**
   * Duplicate mapping for "hometown" since FB can return it differently in different situations.
   * 
   * -- GETTER -- The user's hometown.
   * <p>
   * Sometimes this can be {@code null} - check {@link #getHometownName()} instead in that case.
   * 
   * @return The user's hometown.
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType hometown;

  /**
   * Duplicate mapping for "hometown" since FB can return it differently in different situations.
   */
  @Facebook("hometown")
  private String hometownAsString;

  /**
   * A profile based app scoped ID. It is used to query avatars
   */
  @Getter
  @Setter
  @Facebook("id_for_avatars")
  private String idForAvatars;

  /**
   * he person's current location as entered by them on their profile.
   *
   * This field requires the `user_location` permission.
   *
   * @return The user's current location.
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType location;

  /**
   * The time that the shared loginneeds to be upgraded to Business Manager by
   *
   * @return The time that the shared loginneeds to be upgraded to Business Manager by
   */
  @Getter
  @Setter
  @Facebook("shared_login_upgrade_required_by")
  private Date sharedLoginUpgradeRequiredBy;

  /**
   * The user's significant other.
   * 
   * @return The user's significant other.
   */
  @Getter
  @Setter
  @Facebook("significant_other")
  private User significantOther;

  /**
   * Whether the user can add a Donate Button to their Live Videos
   */
  @Getter
  @Setter
  @Facebook("supports_donate_button_in_live_video")
  private Boolean supportsDonateButtonInLiveVideo;

  /**
   * Date the user's profile was updated.
   * 
   * @return Date the user's profile was updated.
   */
  @Getter
  @Setter
  @Facebook("updated_time")
  private Date updatedTime;

  /**
   * An anonymous, but unique identifier for the user.
   * 
   * @return An anonymous, but unique identifier for the user.
   */
  @Getter
  @Setter
  @Facebook("third_party_id")
  private String thirdPartyId;

  /**
   * The user's currency preferences.
   * <p>
   * Further documentation available on Facebook's
   * <a href="https://developers.facebook.com/docs/payments/user_currency">Displaying prices in user's currency</a>
   * page.
   * 
   * @return The user's currency preferences.
   * @since 1.6.12
   */
  @Getter
  @Setter
  @Facebook
  private Currency currency;

  /**
   * This returns a string which is the same for this person across all the apps managed by the same Business Manager.
   * 
   * @return string which is the same for a person across all apps managed by one company
   * @since 1.7.0
   */
  @Getter
  @Setter
  @Facebook("token_for_business")
  private String tokenForBusiness;

  /**
   * Install type.
   *
   * @return Install type.
   */
  @Getter
  @Setter
  @Facebook("install_type")
  private String installType;

  /**
   * Is the app making the request installed?
   *
   * @return Is the app making the request installed?
   */
  @Getter
  @Setter
  @Facebook
  private Boolean installed;

  /**
   * People with large numbers of followers can have the authenticity of their identity manually verified by Facebook.
   * This field indicates whether the person's profile is verified in this way.
   *
   * This is distinct from the <code>verified</code> field
   *
   * @return Is the user verified
   */
  @Getter
  @Setter
  @Facebook("is_verified")
  private Boolean isVerified;

  @Getter
  @Setter
  @Facebook("is_work_account")
  private Boolean isWorkAccount;

  /**
   * if the current user is a guest user. should always return false.
   */
  @Getter
  @Setter
  @Facebook("is_guest_user")
  private Boolean isGuestUser;

  @Facebook("meeting_for")
  private List<String> meetingFor = new ArrayList<>();

  @Facebook
  private List<UserDevice> devices = new ArrayList<>();

  @Facebook
  private List<Sport> sports = new ArrayList<>();

  @Facebook("favorite_teams")
  private List<Experience> favoriteTeams = new ArrayList<>();

  @Facebook("favorite_athletes")
  private List<Experience> favoriteAthletes = new ArrayList<>();

  @Facebook("inspirational_people")
  private List<Experience> inspirationalPeople = new ArrayList<>();

  @Facebook
  private List<Experience> languages = new ArrayList<>();

  @Facebook
  private List<PageLabel> labels = new ArrayList<>();

  @Facebook("ids_for_apps")
  private List<UserIDForApp> idsForApps = new ArrayList<>();

  @Facebook("ids_for_business")
  private List<UserIDForApp> idsForBusiness = new ArrayList<>();

  @Facebook("ids_for_pages")
  private List<UserIDForPage> idsForPages = new ArrayList<>();

  @Facebook("invitable_friends")
  private List<UserInvitableFriend> invitableFriends = new ArrayList<>();

  private static final long serialVersionUID = 1L;

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/user">Sport Graph API type</a>.
   * 
   * @author Patrick Alberts
   * @since 1.6.3
   */
  public static class Sport extends NamedFacebookType {

    @Facebook
    private List<NamedFacebookType> with = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    /**
     * Friends associated with this sport.
     * 
     * @return Friends associated with this sport.
     */
    public List<NamedFacebookType> getWith() {
      return unmodifiableList(with);
    }

    public boolean addWith(NamedFacebookType withSport) {
      return with.add(withSport);
    }

    public boolean removeWith(NamedFacebookType withSport) {
      return with.remove(withSport);
    }
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/payments/user_currency">Currency Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.12
   */
  public static class Currency extends AbstractFacebookType {

    /**
     * The ISO-4217-3 code for the user's preferred currency (defaulting to USD if the user hasn't set one).
     * 
     * @return The ISO-4217-3 code for the user's preferred currency (defaulting to USD if the user hasn't set one).
     */
    @Getter
    @Setter
    @Facebook("user_currency")
    private String userCurrency;

    /**
     * The number of Facebook Credits that equate in value to one unit of {@code user_currency}.
     * 
     * @return The number of Facebook Credits that equate in value to one unit of {@code user_currency}.
     */
    @Getter
    @Setter
    @Facebook("currency_exchange")
    private BigDecimal currencyExchange;

    /**
     * The number of units of {@code user_currency} that equate in value to one Credit.
     * <p>
     * To calculate the local currency amount based on the credits price, compute
     * {@code credits_price * currency_exchange_inverse}.
     * 
     * @return The number of units of {@code user_currency} that equate in value to one Credit.
     */
    @Getter
    @Setter
    @Facebook("currency_exchange_inverse")
    private BigDecimal currencyExchangeInverse;

    /**
     * The number by which a price should be divided for display in {@code user_currency} units.
     * <p>
     * For example, a price of $1.20 will be represented by the Facebook API as "120", which you should divide by the
     * USD {@code currency_offset} of 100 to arrive back at 1.20.
     * 
     * @return The number by which a price should be divided for display in {@code user_currency} units.
     */
    @Getter
    @Setter
    @Facebook("currency_offset")
    private BigDecimal currencyOffset;

    private static final long serialVersionUID = 1L;

  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/user-device/">User Device
   * Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Norbert Bartels</a>
   */
  public static class UserDevice extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook
    private String hardware;

    @Getter
    @Setter
    @Facebook
    private String os;

  }

  public static class AgeRange extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    /**
     * The lower bounds of the range for this person's age.
     * 
     * @return The lower bounds of the range for this person's age.
     */
    @Getter
    @Setter
    @Facebook
    private Integer min;

    /**
     * The upper bounds of the range for this person's age.
     * 
     * @return The upper bounds of the range for this person's age.
     */
    @Getter
    @Setter
    @Facebook
    private Integer max;

  }

  /**
   * The user's birthday, typed to {@code java.util.Date} if possible.
   * 
   * @return The user's birthday, or {@code null} if unavailable or only available in month/year format.
   */
  public Date getBirthdayAsDate() {
    if (isBlank(getBirthday()) || getBirthday().split("/").length < 2) {
      return null;
    }

    return toDateFromShortFormat(birthday);
  }

  @JsonMappingCompleted
  protected void jsonMappingCompleted(JsonMapper jsonMapper) {
    picture = convertPicture(jsonMapper, rawPicture);
  }

  /**
   * The user's hometown name.
   * 
   * @return The user's hometown name.
   */
  public String getHometownName() {
    if (getHometown() != null)
      return getHometown().getName();

    return hometownAsString;
  }

  /**
   * Businesses can claim ownership of multiple apps using Business Manager.
   *
   * This method returns the list of IDs that this user has in any of those other apps
   * 
   * @return list of ids a user has in other apps
   */
  public List<UserIDForApp> getIdsForApps() {
    return unmodifiableList(idsForApps);
  }

  public boolean addIdsForApps(UserIDForApp userId) {
    return idsForApps.add(userId);
  }

  public boolean removeIdsForApps(UserIDForApp userId) {
    return idsForApps.remove(userId);
  }

  /**
   * Businesses can claim ownership of multiple apps using Business Manager.
   *
   * This edge returns the list of IDs that this user has in any of those other apps
   *
   * @return list of ids a user has in other apps
   */
  public List<UserIDForApp> getIdsForBusiness() {
    return unmodifiableList(idsForBusiness);
  }

  public boolean addIdsForBusiness(UserIDForApp userId) {
    return idsForBusiness.add(userId);
  }

  public boolean removeIdsForBusiness(UserIDForApp userId) {
    return idsForBusiness.remove(userId);
  }

  /**
   * Businesses can claim ownership of apps and pages using Business Manager.
   *
   * This edge returns the list of IDs that this user has in any of the pages owned by this business.
   *
   * @return list of ids a user has in other pages
   */
  public List<UserIDForPage> getIdsForPages() {
    return unmodifiableList(idsForPages);
  }

  public boolean addIdsForPages(UserIDForPage userId) {
    return idsForPages.add(userId);
  }

  public boolean removeIdsForPages(UserIDForPage userId) {
    return idsForPages.remove(userId);
  }

  /**
   * What genders the user is interested in meeting.
   * 
   * @return What genders the user is interested in meeting.
   */
  public List<String> getMeetingFor() {
    return unmodifiableList(meetingFor);
  }

  public boolean addMeetingFor(String gender) {
    return meetingFor.add(gender);
  }

  public boolean removeMeetingFor(String gender) {
    return meetingFor.remove(gender);
  }

  /**
   * The list of devices the person is using.
   * 
   * This will return only iOS and Android devices
   * 
   * @return The list of devices the person is using.
   */
  public List<UserDevice> getDevices() {
    return unmodifiableList(devices);
  }

  public boolean addDevice(UserDevice device) {
    return devices.add(device);
  }

  public boolean removeDevice(UserDevice device) {
    return devices.remove(device);
  }

  /**
   * A list of the sports from the user's profile.
   * 
   * @return A list of the sports from this user's profile.
   */
  public List<Sport> getSports() {
    return unmodifiableList(sports);
  }

  public boolean addSport(Sport sportItem) {
    return sports.add(sportItem);
  }

  public boolean removeSport(Sport sportItem) {
    return sports.remove(sportItem);
  }

  /**
   * A list of the favorite sports teams from the user's profile.
   * 
   * @return A list of the favorite sports teams from the user's profile.
   */
  public List<Experience> getFavoriteTeams() {
    return unmodifiableList(favoriteTeams);
  }

  public boolean addFavoriteTeam(Experience team) {
    return favoriteTeams.add(team);
  }

  public boolean removeFavoriteTeam(Experience team) {
    return favoriteTeams.remove(team);
  }

  /**
   * A list of the favorite athletes from the user's profile.
   * 
   * @return A list of the favorite athletes from the user's profile.
   */
  public List<Experience> getFavoriteAthletes() {
    return unmodifiableList(favoriteAthletes);
  }

  public boolean addFavoriteAthlete(Experience athlet) {
    return favoriteAthletes.add(athlet);
  }

  public boolean removeFavoriteAthlete(Experience athlet) {
    return favoriteAthletes.remove(athlet);
  }

  /**
   * The person's inspirational people.
   *
   * @return The person's inspirational people.
   */
  public List<Experience> getInspirationalPeople() {
    return unmodifiableList(inspirationalPeople);
  }

  public boolean addInspirationalPeople(Experience person) {
    return inspirationalPeople.add(person);
  }

  public boolean removeInspirationalPeople(Experience person) {
    return inspirationalPeople.remove(person);
  }

  /**
   * A list of the languages from the user's profile.
   * 
   * @return A list of the languages from the user's profile.
   */
  public List<Experience> getLanguages() {
    return unmodifiableList(languages);
  }

  public boolean addLanguage(Experience language) {
    return languages.add(language);
  }

  public boolean removeLanguage(Experience language) {
    return languages.remove(language);
  }

  /**
   * Page labels of this user
   *
   * @return labels of this user
   */
  @GraphAPI(since = "2.6")
  public List<PageLabel> getLabels() {
    return unmodifiableList(labels);
  }

  public boolean addLabel(PageLabel label) {
    return labels.add(label);
  }

  public boolean removeLabels(PageLabel label) {
    return labels.remove(label);
  }

  /**
   * A list of friends that can be invited to install a Facebook Canvas app
   * 
   * @return A list of friends that can be invited to install a Facebook Canvas app
   */
  public List<UserInvitableFriend> getInvitableFriends() {
    return unmodifiableList(invitableFriends);
  }

  public boolean addInvitableFriend(UserInvitableFriend friend) {
    return invitableFriends.add(friend);
  }

  public boolean removeInvitableFriend(UserInvitableFriend friend) {
    return invitableFriends.remove(friend);
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/secure-browsing/">Secure Browsing
   * Graph API type</a>.
   */
  public static class SecureBrowsing extends AbstractFacebookType {

    /**
     * Enabled
     *
     * @return Enabled
     */
    @Getter
    @Setter
    @Facebook
    private Boolean enabled;

  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/payment-pricepoints/">Payment
   * Pricepoints Graph API type</a>.
   */
  public static class PaymentPricepoints extends AbstractFacebookType {

    @Facebook
    private List<PaymentPricepoint> mobile = new ArrayList<>();

    /**
     * Mobile payment pricepoints
     * 
     * @return Mobile payment pricepoints
     */
    public List<PaymentPricepoint> getMobile() {
      return unmodifiableList(mobile);
    }

    public boolean addMobile(PaymentPricepoint language) {
      return mobile.add(language);
    }

    public boolean removeMobile(PaymentPricepoint language) {
      return mobile.remove(language);
    }
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/payment-pricepoint/">Payment
   * Pricepoint Graph API type</a>.
   */
  public static class PaymentPricepoint extends AbstractFacebookType {

    @Getter
    @Setter
    @Facebook
    private Double credits;

    @Getter
    @Setter
    @Facebook("local_currency")
    private String localCurrency;

    @Getter
    @Setter
    @Facebook("user_price")
    private String userPrice;

  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/video-upload-limits/">Video Upload
   * Limits Graph API type</a>.
   */
  public static class VideoUploadLimits extends AbstractFacebookType {

    @Getter
    @Setter
    @Facebook
    private Long length;

    @Getter
    @Setter
    @Facebook
    private Long size;
  }

  public static class Experience extends NamedFacebookType {

    @Getter
    @Setter
    @Facebook
    private String description;

    @Getter
    @Setter
    @Facebook
    private User from;

    @Facebook
    private List<User> with = new ArrayList<>();

    /**
     * Tagged users
     * 
     * @return Tagged users
     */
    public List<User> getWith() {
      return unmodifiableList(with);
    }

    public boolean addWith(User with) {
      return this.with.add(with);
    }

    public boolean removeWith(User with) {
      return this.with.remove(with);
    }
  }
}