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
 * TODO: document
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
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
   * TODO: document, equals, hashcode
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return String.format("%s[employer=%s endDate=%s location=%s "
          + "position=%s startDate=%s]", getClass().getSimpleName(),
        getEmployer(), getEndDate(), getLocation(), getPosition(),
        getStartDate());
    }

    public NamedFacebookType getEmployer() {
      return employer;
    }

    public NamedFacebookType getLocation() {
      return location;
    }

    public NamedFacebookType getPosition() {
      return position;
    }

    public Date getStartDate() {
      return toMonthYearDate(startDate);
    }

    public Date getEndDate() {
      return toMonthYearDate(endDate);
    }
  }

  /**
   * TODO: document, equals, hashcode
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return String.format("%s[concentration=%s school=%s year=%s]", getClass()
        .getSimpleName(), getConcentration(), getSchool(), getYear());
    }

    public NamedFacebookType getSchool() {
      return school;
    }

    public NamedFacebookType getYear() {
      return year;
    }

    public List<NamedFacebookType> getConcentration() {
      return Collections.unmodifiableList(concentration);
    }
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getLink() {
    return link;
  }

  public String getAbout() {
    return about;
  }

  public String getRelationshipStatus() {
    return relationshipStatus;
  }

  public String getReligion() {
    return religion;
  }

  public String getWebsite() {
    return website;
  }

  public Integer getTimezone() {
    return timezone;
  }

  public Boolean getVerified() {
    return verified;
  }

  public Date getUpdatedTime() {
    return toDate(updatedTime);
  }

  public List<String> getInterestedIn() {
    return Collections.unmodifiableList(interestedIn);
  }

  public List<Work> getWork() {
    return Collections.unmodifiableList(work);
  }

  public List<Education> getEducation() {
    return Collections.unmodifiableList(education);
  }
}