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

import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static java.util.Collections.unmodifiableList;

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PageLabel extends NamedFacebookType {

  @Getter
  @Setter
  private Date creationTime;

  @Facebook("creation_time")
  private String rawCreationTime;

  @Getter
  @Setter
  @Facebook("creator_id")
  private NamedFacebookType creatorId;

  @Getter
  @Setter
  @Facebook
  private Page from;

  @Facebook
  private List<User> users = new ArrayList<User>();

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    creationTime = toDateFromLongFormat(rawCreationTime);
  }

  public List<User> getUsers() {
    return unmodifiableList(users);
  }

  public boolean addUser(User user) {
    return users.add(user);
  }

  public boolean removeUser(User user) {
    return users.remove(user);
  }
}
