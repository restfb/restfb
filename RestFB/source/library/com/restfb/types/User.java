/*
 * Copyright (c) 2010 Mark Allen.
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

/**
 * Represents the <a
 * href="http://developers.facebook.com/docs/reference/api/user">User Graph API
 * type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class User extends NamedFacebookType {
  @Facebook("first_name")
  private String firstName;

  @Facebook("last_name")
  private String lastName;

  @Facebook
  private String link;

  @Facebook
  private String about;

  @Facebook("relationship_status")
  private String relationshipStatus;

  @Facebook
  private String religion;

  @Facebook
  private String website;

  @Facebook
  private String birthday;

  @Facebook
  private String email;

  @Facebook
  private Integer timezone;

  @Facebook
  private Boolean verified;

  @Facebook("updated_time")
  private String updatedTime;

  @Facebook(value = "interested_in", contains = String.class)
  private List<String> interestedIn = new ArrayList<String>();

  @Facebook(value = "work", contains = Work.class)
  private List<Work> work = new ArrayList<Work>();

  @Facebook(value = "education", contains = Education.class)
  private List<Education> education = new ArrayList<Education>();

  /**
   * Represents the <a
   * href="http://developers.facebook.com/docs/reference/api/user">Work Graph
   * API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   */
  public static class Work {
    // Facebook month-year only date format.
    // Example: 2007-03
    private static final String FACEBOOK_MONTH_YEAR_DATE_FORMAT = "yyyy-MM";

    @Facebook
    NamedFacebookType employer;

    @Facebook
    NamedFacebookType location;

    @Facebook
    NamedFacebookType position;

    @Facebook("start_date")
    private String startDate;

    @Facebook("end_date")
    private String endDate;

    /**
     * Returns a Java representation of a Facebook month-year {@code date}
     * string.
     * 
     * @param date
     *          Facebook month-year {@code date} string.
     * @return Java date representation of the given Facebook {@code date}
     *         string.
     * @throws IllegalArgumentException
     *           If the provided {@code date} isn't in the Facebook month-year
     *           date format.
     */
    protected Date toMonthYearDate(String date) throws IllegalArgumentException {
      if (date == null)
        return null;

      try {
        return new SimpleDateFormat(FACEBOOK_MONTH_YEAR_DATE_FORMAT)
          .parse(date);
      } catch (ParseException e) {
        throw new IllegalArgumentException(
          "Unable to parse date '" + date + "' using format string '"
              + FACEBOOK_MONTH_YEAR_DATE_FORMAT + "'", e);
      }
    }

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
     * The employer for this job.
     * 
     * @return The employer for this job.
     */
    public NamedFacebookType getEmployer() {
      return employer;
    }

    /**
     * The location of this job.
     * 
     * @return The location of this job.
     */
    public NamedFacebookType getLocation() {
      return location;
    }

    /**
     * Position held at this job.
     * 
     * @return Position held at this job.
     */
    public NamedFacebookType getPosition() {
      return position;
    }

    /**
     * Date this job was started.
     * 
     * @return Date this job was started.
     */
    public Date getStartDate() {
      return toMonthYearDate(startDate);
    }

    /**
     * Date this job ended.
     * 
     * @return Date this job ended.
     */
    public Date getEndDate() {
      return toMonthYearDate(endDate);
    }
  }

  /**
   * Represents the <a
   * href="http://developers.facebook.com/docs/reference/api/user">Education
   * Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   */
  public static class Education {
    @Facebook
    NamedFacebookType school;

    @Facebook
    NamedFacebookType year;

    @Facebook(value = "concentration", contains = NamedFacebookType.class)
    private List<NamedFacebookType> concentration =
        new ArrayList<NamedFacebookType>();

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
     * The school name and ID.
     * 
     * @return The school name and ID.
     */
    public NamedFacebookType getSchool() {
      return school;
    }

    /**
     * Graduation year.
     * 
     * @return Graduation year.
     */
    public NamedFacebookType getYear() {
      return year;
    }

    /**
     * Concentrations/minors.
     * 
     * @return Concentrations/minors.
     */
    public List<NamedFacebookType> getConcentration() {
      return Collections.unmodifiableList(concentration);
    }
  }

  /**
   * The user's first name
   * 
   * @return The user's first name.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * The user's last name.
   * 
   * @return The user's last name.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * A link to the user's profile.
   * 
   * @return A link to the user's profile.
   */
  public String getLink() {
    return link;
  }

  /**
   * The user's blurb that appears under their profile picture.
   * 
   * @return The user's blurb that appears under their profile picture.
   */
  public String getAbout() {
    return about;
  }

  /**
   * The user's relationship status.
   * 
   * @return The user's relationship status.
   */
  public String getRelationshipStatus() {
    return relationshipStatus;
  }

  /**
   * The user's birthday.
   * 
   * @return The user's birthday.
   */
  public String getBirthday() {
    return birthday;
  }

  /**
   * The user's religion.
   * 
   * @return The user's religion.
   */
  public String getReligion() {
    return religion;
  }

  /**
   * A link to the user's personal website.
   * 
   * @return A link to the user's personal website.
   */
  public String getWebsite() {
    return website;
  }

  /**
   * The proxied or contact email address granted by the user.
   * 
   * @return The proxied or contact email address granted by the user.
   */
  public String getEmail() {
    return email;
  }

  /**
   * The user's timezone offset.
   * 
   * @return The user's timezone offset.
   */
  public Integer getTimezone() {
    return timezone;
  }

  /**
   * Is the user verified?
   * 
   * @return User verification status.
   */
  public Boolean getVerified() {
    return verified;
  }

  /**
   * Date the user's profile was updated.
   * 
   * @return Date the user's profile was updated.
   */
  public Date getUpdatedTime() {
    return StringUtils.toDate(updatedTime);
  }

  /**
   * The user's interests.
   * 
   * @return The user's interests.
   */
  public List<String> getInterestedIn() {
    return Collections.unmodifiableList(interestedIn);
  }

  /**
   * A list of the work history from the user's profile
   * 
   * @return A list of the work history from the user's profile
   */
  public List<Work> getWork() {
    return Collections.unmodifiableList(work);
  }

  /**
   * A list of the education history from the user's profile
   * 
   * @return A list of the education history from the user's profile
   */
  public List<Education> getEducation() {
    return Collections.unmodifiableList(education);
  }
}