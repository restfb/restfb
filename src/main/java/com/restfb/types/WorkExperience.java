/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/work-experience/">Work
 * Experience</a> type
 * @deprecated since breaking change on 4 April, 2018
 */
@Deprecated
public class WorkExperience extends FacebookType {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook
  private String description;

  @Getter
  @Setter
  @Facebook
  private Page employer;

  @Getter
  @Setter
  private Date endDate;

  @Facebook("end_date")
  private String rawEndDate;

  @Getter
  @Setter
  @Facebook
  private User from;

  @Getter
  @Setter
  @Facebook
  private Page location;

  @Getter
  @Setter
  @Facebook
  private Page position;

  @Facebook
  private List<ProjectExperience> projects = new ArrayList<ProjectExperience>();

  @Getter
  @Setter
  private Date startDate;

  @Facebook("start_date")
  private String rawStartDate;

  @Facebook
  private List<User> with = new ArrayList<User>();

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    startDate = toDateFromShortFormat(rawStartDate);
    endDate = toDateFromShortFormat(rawEndDate);
  }

  public List<User> getWith() {
    return Collections.unmodifiableList(with);
  }

  public boolean addWith(User withUser) {
    return with.add(withUser);
  }

  public boolean removeWith(User withUser) {
    return with.remove(withUser);
  }

  public List<ProjectExperience> getProjects() {
    return Collections.unmodifiableList(projects);
  }

  public boolean addProject(ProjectExperience projectExperience) {
    return projects.add(projectExperience);
  }

  public boolean removeProject(ProjectExperience projectExperience) {
    return projects.remove(projectExperience);
  }
}
