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
 * TODO: document
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Post extends NamedFacebookType {
  @Facebook
  private CategorizedFacebookType from;

  @Facebook
  private String message;

  @Facebook
  private String picture;

  @Facebook
  private String link;

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

  @Facebook
  private Long likes;

  @Facebook("created_time")
  private String createdTime;

  @Facebook("updated_time")
  private String updatedTime;

  @Facebook
  private List<NamedFacebookType> to = new ArrayList<NamedFacebookType>();

  @Facebook(value = "comments", contains = Comment.class)
  private List<Comment> comments = new ArrayList<Comment>();

  @Facebook(value = "actions", contains = Action.class)
  private List<Action> actions = new ArrayList<Action>();

  /**
   * TODO: document, equals, hashcode
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   */
  public static class Privacy {
    @Facebook
    private String value;

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }

    public String getValue() {
      return value;
    }
  }

  /**
   * TODO: document, equals, hashcode
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   */
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
      return ReflectionUtils.toString(this);
    }

    public String getName() {
      return name;
    }

    public String getLink() {
      return link;
    }
  }

  public CategorizedFacebookType getFrom() {
    return from;
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

  public Long getLikes() {
    return likes;
  }

  public Date getCreatedTime() {
    return toDate(createdTime);
  }

  public Date getUpdatedTime() {
    return toDate(updatedTime);
  }

  public List<NamedFacebookType> getTo() {
    return Collections.unmodifiableList(to);
  }

  public List<Comment> getComments() {
    return Collections.unmodifiableList(comments);
  }

  public List<Action> getActions() {
    return Collections.unmodifiableList(actions);
  }

}