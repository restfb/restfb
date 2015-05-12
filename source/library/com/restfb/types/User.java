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

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.JsonObject;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static com.restfb.util.DateUtils.toDateFromShortFormat;
import com.restfb.util.ReflectionUtils;
import static com.restfb.util.StringUtils.isBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/user">User Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author Patrick Alberts
 * @since 1.5
 */
public class User extends NamedFacebookType {

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
   * The user's last name.
   * 
   * @return The user's last name.
   */
  @Getter
  @Setter
  @Facebook("last_name")
  private String lastName;

  /**
   * A link to the user's profile.
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
  @Getter
  @Setter
  @Facebook
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
   * The user's blurb that appears under their profile picture.
   * 
   * @return The user's blurb that appears under their profile picture.
   */
  @Getter
  @Setter
  @Facebook
  private String about;

  /**
   * The user's relationship status.
   * 
   * @return The user's relationship status.
   */
  @Getter
  @Setter
  @Facebook("relationship_status")
  private String relationshipStatus;

  /**
   * The user's religion.
   * 
   * @return The user's religion.
   */
  @Getter
  @Setter
  @Facebook
  private String religion;

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
   * A link to the user's personal website.
   * 
   * @return A link to the user's personal website.
   */
  @Getter
  @Setter
  @Facebook
  private String website;

  /**
   * The user's birthday as a {@code String}.
   * <p>
   * Will always succeed, even if the user has specified month/year format only. If you'd like to use a typed version of
   * this accessor, call {@link #getBirthdayAsDate()} instead.
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
   * Is the user verified?
   * 
   * @return User verification status.
   */
  @Getter
  @Setter
  @Facebook
  private Boolean verified;

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
   * The user's political affiliation.
   * 
   * @return The user's political affiliation.
   */
  @Getter
  @Setter
  @Facebook
  private String political;

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
   * The user's Facebook username.
   * 
   * @return The user's Facebook username.
   * @since 1.6.5
   * @deprecated since graph api 2.0
   */
  @Deprecated
  @Getter
  @Setter
  @Facebook
  private String username;

  @Facebook("picture")
  private JsonObject rawPicture;

  /**
   * The user's picture, if provided.
   * 
   * To force Facebook to fill the <code>picture</code> field you 
   * have to fetch the user with the <code>fields=picture</code>
   * parameter, otherwise the picture is <code>null</code>.
   * 
   * @return the user's picture as picture object
   * @since 1.6.16
   */
  @Getter
  @Setter
  private Picture picture;

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
   * The user's current location.
   * 
   * @return The user's current location.
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType location;

  /**
   * The user's significant other.
   * 
   * @return The user's significant other.
   */
  @Getter
  @Setter
  @Facebook("significant_other")
  private NamedFacebookType significantOther;

  @Facebook("updated_time")
  private String rawUpdatedTime;

  /**
   * Date the user's profile was updated.
   * 
   * @return Date the user's profile was updated.
   */
  @Getter
  @Setter
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
   * Further documentation available on Facebook's <a
   * href="https://developers.facebook.com/docs/payments/user_currency">Displaying prices in user's currency</a> page.
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

  @Facebook("interested_in")
  private List<String> interestedIn = new ArrayList<String>();

  @Facebook("meeting_for")
  private List<String> meetingFor = new ArrayList<String>();

  @Facebook
  private List<Work> work = new ArrayList<Work>();

  @Facebook
  private List<Education> education = new ArrayList<Education>();

  @Facebook
  private List<Sport> sports = new ArrayList<Sport>();

  @Facebook("favorite_teams")
  private List<NamedFacebookType> favoriteTeams = new ArrayList<NamedFacebookType>();

  @Facebook("favorite_athletes")
  private List<NamedFacebookType> favoriteAthletes = new ArrayList<NamedFacebookType>();

  @Facebook
  private List<NamedFacebookType> languages = new ArrayList<NamedFacebookType>();

  private static final long serialVersionUID = 1L;

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/user">Work Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @author Patrick Alberts
   */
  public static class Work implements Serializable {

    /**
     * The employer for this job.
     * 
     * @return The employer for this job.
     */
    @Getter
    @Setter
    @Facebook
    private NamedFacebookType employer;

    /**
     * The location of this job.
     * 
     * @return The location of this job.
     */
    @Getter
    @Setter
    @Facebook
    private NamedFacebookType location;

    /**
     * Position held at this job.
     * 
     * @return Position held at this job.
     */
    @Getter
    @Setter
    @Facebook
    private NamedFacebookType position;

    /**
     * Description of this job.
     * 
     * @return Description of this job.
     * @since 1.6.3
     */
    @Getter
    @Setter
    @Facebook
    private String description;

    @Facebook("start_date")
    private String rawStartDate;

    /**
     * Date this job was started.
     * 
     * @return Date this job was started.
     */
    @Getter
    @Setter
    private Date startDate;

    @Facebook("end_date")
    private String rawEndDate;

    /**
     * Date this job ended.
     * 
     * @return Date this job ended.
     */
    @Getter
    @Setter
    private Date endDate;

    @Facebook
    private List<NamedFacebookType> with = new ArrayList<NamedFacebookType>();

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
     * Friends associated with this job.
     * 
     * @return Friends associated with this job.
     * @since 1.6.3
     */
    public List<NamedFacebookType> getWith() {
      return unmodifiableList(with);
    }

    public boolean addWith(NamedFacebookType friend) {
      return with.add(friend);
    }

    public boolean removeWith(NamedFacebookType friend) {
      return with.remove(friend);
    }

    @JsonMappingCompleted
    void convertTime() {
      startDate = toDateFromLongFormat(rawStartDate);
      endDate = toDateFromLongFormat(rawEndDate);
    }
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/user">Education Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @author Patrick Alberts
   */
  public static class Education implements Serializable {

    /**
     * The school name and ID.
     * 
     * @return The school name and ID.
     */
    @Getter
    @Setter
    @Facebook
    private NamedFacebookType school;

    /**
     * Graduation year.
     * 
     * @return Graduation year.
     */
    @Getter
    @Setter
    @Facebook
    private NamedFacebookType year;

    /**
     * Description for this year.
     * 
     * It is possible for Facebook to return either this value or {@link #getYear()}. If {@link #getYear()} returns
     * {@code null}, then check this method to see if it has data, e.g. {@code "1997"}.
     * 
     * @return Description for this year.
     * @since 1.7.1
     */
    @Getter
    @Setter
    @Facebook("year")
    private String yearAsString;

    /**
     * Degree acquired.
     * 
     * @return Degree acquired.
     */
    @Getter
    @Setter
    @Facebook
    private NamedFacebookType degree;

    /**
     * Type of school, e.g. {@code College}.
     * 
     * @return Type of school.
     */
    @Getter
    @Setter
    @Facebook
    private String type;

    @Facebook
    private List<NamedFacebookType> concentration = new ArrayList<NamedFacebookType>();

    @Facebook
    private List<NamedFacebookType> with = new ArrayList<NamedFacebookType>();

    @Facebook
    private List<EducationClass> classes = new ArrayList<EducationClass>();

    private static final long serialVersionUID = 2L;

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
     * Concentrations/minors.
     * 
     * @return Concentrations/minors.
     */
    public List<NamedFacebookType> getConcentration() {
      return unmodifiableList(concentration);
    }

    public boolean addConcentration(NamedFacebookType minor) {
      return concentration.add(minor);
    }

    public boolean removeConcentration(NamedFacebookType minor) {
      return concentration.remove(minor);
    }

    /**
     * Friends associated with this school.
     * 
     * @return Friends associated with this school.
     * @since 1.6.3
     */
    public List<NamedFacebookType> getWith() {
      return unmodifiableList(with);
    }

    public boolean addWith(NamedFacebookType friend) {
      return with.add(friend);
    }

    public boolean removeWith(NamedFacebookType friend) {
      return with.remove(friend);
    }

    /**
     * Classes taken at this school.
     * 
     * @return Classes taken at this school.
     * @since 1.6.8
     */
    public List<EducationClass> getClasses() {
      return unmodifiableList(classes);
    }

    public boolean addClasses(EducationClass eduClass) {
      return classes.add(eduClass);
    }

    public boolean removeClasses(EducationClass eduClass) {
      return classes.remove(eduClass);
    }
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/user">Class Graph API type</a>.
   * 
   * @author Mark Allen
   * @since 1.6.8
   */
  public static class EducationClass extends NamedFacebookType {
    @Facebook
    private List<NamedFacebookType> with = new ArrayList<NamedFacebookType>();

    /**
     * The description of this class.
     * 
     * @return The description of this class.
     */
    @Getter
    @Setter
    @Facebook
    private String description;

    private static final long serialVersionUID = 1L;

    /**
     * Friends associated with this class.
     * 
     * @return Friends associated with this class.
     */
    public List<NamedFacebookType> getWith() {
      return unmodifiableList(with);
    }

    public boolean addWith(NamedFacebookType friend) {
      return with.add(friend);
    }

    public boolean removeWith(NamedFacebookType friend) {
      return with.remove(friend);
    }

  }

  public static class Picture implements Serializable {

    /**
     * The URL of the profile photo
     * 
     * @return The URL of the profile photo
     * @since 1.6.16
     */
    @Getter
    @Setter
    @Facebook
    private String url;

    /**
     * Indicates whether the profile photo is the default 'silhouette' picture, or has been replaced
     * 
     * @return is the photo the default or has been replaced
     * @since 1.6.16
     */
    @Getter
    @Setter
    @Facebook("is_silhouette")
    private Boolean isSilhouette;

    /**
     * Picture height in pixels
     * 
     * @return Picture height in pixels
     * @since 1.6.16
     */
    @Getter
    @Setter
    @Facebook
    private Integer height;

    /**
     * Picture width in pixels
     * 
     * @return Picture width in pixels
     * @since 1.6.16
     */
    @Getter
    @Setter
    @Facebook
    private Integer width;

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
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/user">Sport Graph API type</a>.
   * 
   * @author Patrick Alberts
   * @since 1.6.3
   */
  public static class Sport extends NamedFacebookType {

    @Facebook
    private List<NamedFacebookType> with = new ArrayList<NamedFacebookType>();

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
  public static class Currency implements Serializable {

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

  public static class AgeRange implements Serializable {

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
    if (isBlank(getBirthday()) || getBirthday().split("/").length < 2)
      return null;

    return toDateFromShortFormat(birthday);
  }

  @JsonMappingCompleted
  void convertTime() {
    updatedTime = toDateFromLongFormat(rawUpdatedTime);
  }

  @JsonMappingCompleted
  protected void jsonMappingCompleted(JsonMapper jsonMapper) {
    picture = null;

    if (rawPicture == null)
      return;

    String picJson = rawPicture.getJsonObject("data").toString();
    picture = jsonMapper.toJavaObject(picJson, User.Picture.class);
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
   * The genders the user is interested in.
   * 
   * @return The genders the user is interested in.
   */
  public List<String> getInterestedIn() {
    return unmodifiableList(interestedIn);
  }

  public boolean addInterestedIn(String gender) {
    return interestedIn.add(gender);
  }

  public boolean removeInterestedIn(String gender) {
    return interestedIn.remove(gender);
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
   * A list of the work history from the user's profile.
   * 
   * @return A list of the work history from the user's profile.
   */
  public List<Work> getWork() {
    return unmodifiableList(work);
  }

  public boolean addWork(Work workHistoryItem) {
    return work.add(workHistoryItem);
  }

  public boolean removeWork(Work workHistoryItem) {
    return work.remove(workHistoryItem);
  }

  /**
   * A list of the education history from the user's profile.
   * 
   * @return A list of the education history from the user's profile.
   */
  public List<Education> getEducation() {
    return unmodifiableList(education);
  }

  public boolean addEducation(Education educationHistoryItem) {
    return education.add(educationHistoryItem);
  }

  public boolean removeEducation(Education educationHistoryItem) {
    return education.remove(educationHistoryItem);
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
  public List<NamedFacebookType> getFavoriteTeams() {
    return unmodifiableList(favoriteTeams);
  }

  public boolean addFavoriteTeam(NamedFacebookType team) {
    return favoriteTeams.add(team);
  }

  public boolean removeFavoriteTeam(NamedFacebookType team) {
    return favoriteTeams.remove(team);
  }

  /**
   * A list of the favorite athletes from the user's profile.
   * 
   * @return A list of the favorite athletes from the user's profile.
   */
  public List<NamedFacebookType> getFavoriteAthletes() {
    return unmodifiableList(favoriteAthletes);
  }

  public boolean addFavoriteAthlete(NamedFacebookType athlet) {
    return favoriteAthletes.add(athlet);
  }

  public boolean removeFavoriteAthlete(NamedFacebookType athlet) {
    return favoriteAthletes.remove(athlet);
  }

  /**
   * A list of the languages from the user's profile.
   * 
   * @return A list of the languages from the user's profile.
   */
  public List<NamedFacebookType> getLanguages() {
    return unmodifiableList(languages);
  }

  public boolean addLanguage(NamedFacebookType language) {
    return languages.add(language);
  }

  public boolean removeLanguage(NamedFacebookType language) {
    return languages.remove(language);
  }
}