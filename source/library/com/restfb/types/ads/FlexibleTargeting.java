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
package com.restfb.types.ads;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class FlexibleTargeting extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook
  private List<IDName> behaviors;

  @Getter
  @Setter
  @Facebook("college_years")
  private List<Long> collegeYears;

  @Getter
  @Setter
  @Facebook
  private List<IDName> connections;

  @Getter
  @Setter
  @Facebook("custom_audiences")
  private List<IDName> customAudiences;

  @Getter
  @Setter
  @Facebook("education_majors")
  private List<IDName> educationMajors;

  @Getter
  @Setter
  @Facebook("education_schools")
  private List<IDName> educationSchools;

  @Getter
  @Setter
  @Facebook("education_statuses")
  private List<Long> educationStatuses;

  @Getter
  @Setter
  @Facebook("ethnic_affinity")
  private List<IDName> ethnicAffinity;

  @Getter
  @Setter
  @Facebook("family_statuses")
  private List<IDName> familyStatuses;

  @Getter
  @Setter
  @Facebook("friends_of_connections")
  private List<IDName> friendsOfConnections;

  @Getter
  @Setter
  @Facebook
  private List<IDName> generation;

  @Getter
  @Setter
  @Facebook("home_ownership")
  private List<IDName> homeOwnership;

  @Getter
  @Setter
  @Facebook("home_type")
  private List<IDName> homeType;

  @Getter
  @Setter
  @Facebook("home_value")
  private List<IDName> homeValue;

  @Getter
  @Setter
  @Facebook("household_composition")
  private List<IDName> householdComposition;

  @Getter
  @Setter
  @Facebook
  private List<IDName> income;

  @Getter
  @Setter
  @Facebook
  private List<IDName> industries;

  @Getter
  @Setter
  @Facebook("interested_in")
  private List<Long> interestedIn;

  @Getter
  @Setter
  @Facebook
  private List<IDName> interests;

  @Getter
  @Setter
  @Facebook("life_events")
  private List<IDName> lifeEvents;

  @Getter
  @Setter
  @Facebook
  private List<IDName> moms;

  @Getter
  @Setter
  @Facebook("net_worth")
  private List<IDName> netWorth;

  @Getter
  @Setter
  @Facebook("office_type")
  private List<IDName> officeType;

  @Getter
  @Setter
  @Facebook
  private List<IDName> politics;

  @Getter
  @Setter
  @Facebook("relationship_statuses")
  private List<Long> relationshipStatuses;

  @Getter
  @Setter
  @Facebook("user_adclusters")
  private List<IDName> userAdclusters;

  @Getter
  @Setter
  @Facebook("work_employers")
  private List<IDName> workEmployers;

  @Getter
  @Setter
  @Facebook("work_positions")
  private List<IDName> workPositions;
}
