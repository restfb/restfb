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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Post extends FacebookType {
  @Facebook
  private From from;

  @Facebook
  private List<Owner> to = new ArrayList<Owner>();

  @Facebook
  private String message;

  @Facebook
  private String picture;

  @Facebook
  private String link;

  @Facebook
  private String name;

  @Facebook
  private String caption;

  @Facebook
  private String description;

  @Facebook
  private String source;

  @Facebook
  private String icon;

  @Facebook
  private String attribution;

  @Facebook
  private Privacy privacy;

  @Facebook(value = "comments", contains = Comment.class)
  private List<Comment> comments = new ArrayList<Comment>();

  @Facebook(value = "actions", contains = Action.class)
  private List<Action> actions = new ArrayList<Action>();

  @Facebook
  private Long likes;

  @Facebook("created_time")
  private String createdTime;

  @Facebook("updated_time")
  private String updatedTime;

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("%s[id=%s from=%s to=%s message=%s "
        + "picture=%s link=%s name=%s caption=%s "
        + "description=%s source=%s icon=%s attribution=%s "
        + "privacy=%s actions=%s comments=%s "
        + "likes=%d createdTime=%s updatedTime=%s]",
      getClass().getSimpleName(), getId(), getFrom(), getTo(), getMessage(),
      getPicture(), getLink(), getName(), getCaption(), getDescription(),
      getSource(), getIcon(), getAttribution(), getPrivacy(), getActions(),
      getComments(), getLikes(), getCreatedTime(), getUpdatedTime());
  }

  public static class Privacy {
    @Facebook
    private String value;

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return String.format("%s[value=%s]", getClass().getSimpleName(),
        getValue());
    }

    public String getValue() {
      return value;
    }
  }

  public static class Action {
    @Facebook
    private String name;

    @Facebook
    private String link;

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return String.format("%s[name=%s link=%s]", getClass().getSimpleName(),
        getName(), getLink());
    }

    public String getName() {
      return name;
    }

    public String getLink() {
      return link;
    }
  }

  public From getFrom() {
    return from;
  }

  public List<Owner> getTo() {
    return to;
  }

  public String getMessage() {
    return message;
  }

  public String getPicture() {
    return picture;
  }

  public String getLink() {
    return link;
  }

  public String getName() {
    return name;
  }

  public String getCaption() {
    return caption;
  }

  public String getDescription() {
    return description;
  }

  public String getSource() {
    return source;
  }

  public String getIcon() {
    return icon;
  }

  public String getAttribution() {
    return attribution;
  }

  public Privacy getPrivacy() {
    return privacy;
  }

  public List<Comment> getComments() {
    return Collections.unmodifiableList(comments);
  }

  public List<Action> getActions() {
    return Collections.unmodifiableList(actions);
  }

  public Long getLikes() {
    return likes;
  }

  public Date getCreatedTime() {
    return toDate(createdTime);
  }

  public Date getUpdatedTime() {
    return toDate(updatedTime);
  }
}