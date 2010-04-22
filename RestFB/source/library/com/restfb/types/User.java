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

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class User {
  @Facebook
  private Long id;

  @Facebook("first_name")
  private String firstName;

  @Facebook("last_name")
  private String lastName;

  @Facebook
  private String name;

  @Facebook
  private String link;

  @Facebook
  private String about;

  @Facebook(value = "interested_in", contains = String.class)
  private List<String> interestedIn = new ArrayList<String>();

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

  // Example: 2010-02-28T16:11:08+0000
  @Facebook("updated_time")
  private String updatedTime;

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("(%d) %s", getId(), getName());
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getName() {
    return name;
  }

  public String getLink() {
    return link;
  }

  public String getAbout() {
    return about;
  }

  public List<String> getInterestedIn() {
    return interestedIn;
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

  public String getUpdatedTime() {
    return updatedTime;
  }
}