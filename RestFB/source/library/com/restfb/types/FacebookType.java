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
import java.util.Date;

import com.restfb.Facebook;

/**
 * TODO: document
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class FacebookType {
  @Facebook
  private String id;

  @Facebook
  Metadata metadata;

  @Facebook
  String type;

  // Facebook date format (ISO 8601).
  // Example: 2010-02-28T16:11:08+0000
  private static final String FACEBOOK_DATE_FORMAT = "yyyy-MM-dd'T'kk:mm:ssZ";

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

  public String getId() {
    return id;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public String getType() {
    return type;
  }

  /**
   * Returns a Java representation of a Facebook {@code date} string.
   * 
   * @param date
   *          Facebook {@code date} string.
   * @return Java date representation of the given Facebook {@code date} string.
   * @throws IllegalArgumentException
   *           If the provided {@code date} isn't in the Facebook date format
   *           (ISO 8601).
   */
  protected Date toDate(String date) throws IllegalArgumentException {
    if (date == null)
      return null;

    try {
      return new SimpleDateFormat(FACEBOOK_DATE_FORMAT).parse(date);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Unable to parse date '" + date
          + "' using format string '" + FACEBOOK_DATE_FORMAT + "'", e);
    }
  }

  /**
   * TODO: document
   * 
   * @param string
   * @return
   */
  private static Boolean toBoolean(String string) {
    return string != null && !"".equals(string.trim());
  }

  /**
   * TODO: documentation, equals, hashcode
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   */
  public static class Metadata {
    @Facebook
    private Connections connections;

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
     * TODO: documentation, equals, hashcode
     * 
     * @author <a href="http://restfb.com">Mark Allen</a>
     */
    public static class Connections {
      @Facebook
      private String home;

      @Facebook
      private String feed;

      @Facebook
      private String friends;

      @Facebook
      private String family;

      @Facebook
      private String activities;

      @Facebook
      private String interests;

      @Facebook
      private String music;

      @Facebook
      private String books;

      @Facebook
      private String movies;

      @Facebook
      private String television;

      @Facebook
      private String likes;

      @Facebook
      private String posts;

      @Facebook
      private String tagged;

      @Facebook
      private String statuses;

      @Facebook
      private String links;

      @Facebook
      private String notes;

      @Facebook
      private String photos;

      @Facebook
      private String albums;

      @Facebook
      private String events;

      @Facebook
      private String groups;

      @Facebook
      private String videos;

      @Facebook
      private String picture;

      @Facebook
      private String inbox;

      @Facebook
      private String outbox;

      @Facebook
      private String updates;

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

      public Boolean hasHome() {
        return toBoolean(home);
      }

      public Boolean hasFeed() {
        return toBoolean(feed);
      }

      public Boolean hasFriends() {
        return toBoolean(friends);
      }

      public Boolean hasFamily() {
        return toBoolean(family);
      }

      public Boolean hasActivities() {
        return toBoolean(activities);
      }

      public Boolean hasInterests() {
        return toBoolean(interests);
      }

      public Boolean hasMusic() {
        return toBoolean(music);
      }

      public Boolean hasBooks() {
        return toBoolean(books);
      }

      public Boolean hasMovies() {
        return toBoolean(movies);
      }

      public Boolean hasTelevision() {
        return toBoolean(television);
      }

      public Boolean hasLikes() {
        return toBoolean(likes);
      }

      public Boolean hasPosts() {
        return toBoolean(posts);
      }

      public Boolean hasTagged() {
        return toBoolean(tagged);
      }

      public Boolean hasStatuses() {
        return toBoolean(statuses);
      }

      public Boolean hasLinks() {
        return toBoolean(links);
      }

      public Boolean hasNotes() {
        return toBoolean(notes);
      }

      public Boolean hasPhotos() {
        return toBoolean(photos);
      }

      public Boolean hasAlbums() {
        return toBoolean(albums);
      }

      public Boolean hasEvents() {
        return toBoolean(events);
      }

      public Boolean hasGroups() {
        return toBoolean(groups);
      }

      public Boolean hasVideos() {
        return toBoolean(videos);
      }

      public Boolean hasPicture() {
        return toBoolean(picture);
      }

      public Boolean hasInbox() {
        return toBoolean(inbox);
      }

      public Boolean hasOutbox() {
        return toBoolean(outbox);
      }

      public Boolean hasUpdates() {
        return toBoolean(updates);
      }
    }

    public Connections getConnections() {
      return connections;
    }
  }
}