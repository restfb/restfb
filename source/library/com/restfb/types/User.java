/*
 * Copyright (c) 2010-2014 Mark Allen.
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
import static com.restfb.util.DateUtils.toDateFromMonthYearFormat;
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
  @Facebook("first_name")
  private String firstName;

  /**
   * The user's middle name.
   * 
   * @return The user's middle name.
   */
  @Getter
  @Facebook("middle_name")
  private String middleName;

  /**
   * The user's last name.
   * 
   * @return The user's last name.
   */
  @Getter
  @Facebook("last_name")
  private String lastName;

  /**
   * A link to the user's profile.
   * 
   * @return A link to the user's profile.
   */
  @Getter
  @Facebook
  private String link;

  /**
   * The user's biographical snippet.
   * 
   * @return The user's biographical snippet.
   */
  @Getter
  @Facebook
  private String bio;

  /**
   * The user's favorite quotes.
   * 
   * @return The user's favorite quotes.
   */
  @Getter
  @Facebook
  private String quotes;

  /**
   * The user's blurb that appears under their profile picture.
   * 
   * @return The user's blurb that appears under their profile picture.
   */
  @Getter
  @Facebook
  private String about;

  /**
   * The user's relationship status.
   * 
   * @return The user's relationship status.
   */
  @Getter
  @Facebook("relationship_status")
  private String relationshipStatus;

  /**
   * The user's religion.
   * 
   * @return The user's religion.
   */
  @Getter
  @Facebook
  private String religion;

  /**
   * A link to the user's personal website.
   * 
   * @return A link to the user's personal website.
   */
  @Getter
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
  @Facebook
  private String birthday;

  /**
   * The proxied or contact email address granted by the user.
   * 
   * @return The proxied or contact email address granted by the user.
   */
  @Getter
  @Facebook
  private String email;

  /**
   * The user's timezone offset.
   * 
   * @return The user's timezone offset.
   */
  @Getter
  @Facebook
  private Double timezone;

  /**
   * Is the user verified?
   * 
   * @return User verification status.
   */
  @Getter
  @Facebook
  private Boolean verified;

  /**
   * The user's gender.
   * 
   * @return The user's gender.
   */
  @Getter
  @Facebook
  private String gender;

  /**
   * The user's political affiliation.
   * 
   * @return The user's political affiliation.
   */
  @Getter
  @Facebook
  private String political;

  /**
   * The user's locale.
   * 
   * @return The user's locale.
   */
  @Getter
  @Facebook
  private String locale;

  /**
   * The user's Facebook username.
   * 
   * @return The user's Facebook username.
   * @since 1.6.5
   */
  @Getter
  @Facebook
  private String username;

  @Facebook("picture")
  private JsonObject rawPicture;

  /**
   * The user's picture, if provided
   * 
   * @return the user's picture as picture object
   * @since 1.6.16
   */
  @Getter
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
  @Facebook
  private NamedFacebookType location;

  /**
   * The user's significant other.
   * 
   * @return The user's significant other.
   */
  @Getter
  @Facebook("significant_other")
  private NamedFacebookType significantOther;

  @Facebook("updated_time")
  private String updatedTime;

  /**
   * An anonymous, but unique identifier for the user.
   * 
   * @return An anonymous, but unique identifier for the user.
   */
  @Getter
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
  @Facebook
  private Currency currency;

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
    @Facebook
    private NamedFacebookType employer;

    /**
     * The location of this job.
     * 
     * @return The location of this job.
     */
    @Getter
    @Facebook
    private NamedFacebookType location;

    /**
     * Position held at this job.
     * 
     * @return Position held at this job.
     */
    @Getter
    @Facebook
    private NamedFacebookType position;

    /**
     * Description of this job.
     * 
     * @return Description of this job.
     * @since 1.6.3
     */
    @Getter
    @Facebook
    private String description;

    @Facebook("start_date")
    private String startDate;

    @Facebook("end_date")
    private String endDate;

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
     * Date this job was started.
     * 
     * @return Date this job was started.
     */
    public Date getStartDate() {
      return toDateFromMonthYearFormat(startDate);
    }

    /**
     * Date this job ended.
     * 
     * @return Date this job ended.
     */
    public Date getEndDate() {
      return toDateFromMonthYearFormat(endDate);
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
    @Facebook
    private NamedFacebookType school;

    /**
     * Graduation year.
     * 
     * @return Graduation year.
     */
    @Getter
    @Facebook
    private NamedFacebookType year;

    /**
     * Degree acquired.
     * 
     * @return Degree acquired.
     */
    @Getter
    @Facebook
    private NamedFacebookType degree;

    /**
     * Type of school, e.g. {@code College}.
     * 
     * @return Type of school.
     */
    @Getter
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

    /**
     * Friends associated with this school.
     * 
     * @return Friends associated with this school.
     * @since 1.6.3
     */
    public List<NamedFacebookType> getWith() {
      return unmodifiableList(with);
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

  }

  public static class Picture implements Serializable {

    /**
     * The URL of the profile photo
     * 
     * @return The URL of the profile photo
     * @since 1.6.16
     */
    @Getter
    @Facebook
    private String url;

    /**
     * Indicates whether the profile photo is the default 'silhouette' picture, or has been replaced
     * 
     * @return is the photo the default or has been replaced
     * @since 1.6.16
     */
    @Getter
    @Facebook("is_silhouette")
    private Boolean isSilhouette;

    /**
     * Picture height in pixels
     * 
     * @return Picture height in pixels
     * @since 1.6.16
     */
    @Getter
    @Facebook
    private Integer height;

    /**
     * Picture width in pixels
     * 
     * @return Picture width in pixels
     * @since 1.6.16
     */
    @Getter
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
    @Facebook("user_currency")
    private String userCurrency;

    /**
     * The number of Facebook Credits that equate in value to one unit of {@code user_currency}.
     * 
     * @return The number of Facebook Credits that equate in value to one unit of {@code user_currency}.
     */
    @Getter
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

  /**
   * Date the user's profile was updated.
   * 
   * @return Date the user's profile was updated.
   */
  public Date getUpdatedTime() {
    return toDateFromLongFormat(updatedTime);
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

  /**
   * What genders the user is interested in meeting.
   * 
   * @return What genders the user is interested in meeting.
   */
  public List<String> getMeetingFor() {
    return unmodifiableList(meetingFor);
  }

  /**
   * A list of the work history from the user's profile.
   * 
   * @return A list of the work history from the user's profile.
   */
  public List<Work> getWork() {
    return unmodifiableList(work);
  }

  /**
   * A list of the education history from the user's profile.
   * 
   * @return A list of the education history from the user's profile.
   */
  public List<Education> getEducation() {
    return unmodifiableList(education);
  }

  /**
   * A list of the sports from the user's profile.
   * 
   * @return A list of the sports from this user's profile.
   */
  public List<Sport> getSports() {
    return unmodifiableList(sports);
  }

  /**
   * A list of the favorite sports teams from the user's profile.
   * 
   * @return A list of the favorite sports teams from the user's profile.
   */
  public List<NamedFacebookType> getFavoriteTeams() {
    return unmodifiableList(favoriteTeams);
  }

  /**
   * A list of the favorite athletes from the user's profile.
   * 
   * @return A list of the favorite athletes from the user's profile.
   */
  public List<NamedFacebookType> getFavoriteAthletes() {
    return unmodifiableList(favoriteAthletes);
  }

  /**
   * A list of the languages from the user's profile.
   * 
   * @return A list of the languages from the user's profile.
   */
  public List<NamedFacebookType> getLanguages() {
    return unmodifiableList(languages);
  }
}