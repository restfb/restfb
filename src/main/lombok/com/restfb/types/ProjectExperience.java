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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * Represets the <a href="https://developers.facebook.com/docs/graph-api/reference/project-experience/">Project
 * Experience</a> type
 */
public class ProjectExperience extends NamedFacebookType {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook
  private String description;

  @Getter
  @Setter
  private Date endDate;

  @Facebook("end_date")
  private transient String rawEndDate;

  @Getter
  @Setter
  private Date startDate;

  @Facebook("start_date")
  private transient String rawStartDate;

  @Getter
  @Setter
  @Facebook
  private User from;

  @Facebook
  private List<User> with = new ArrayList<>();

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
}
