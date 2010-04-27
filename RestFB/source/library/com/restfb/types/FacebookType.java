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

import com.restfb.Facebook;

/**
 * Base class which encapsulates behavior and properties common to most <a
 * href="http://developers.facebook.com/docs/reference/api/">Graph API
 * types</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class FacebookType {
  @Facebook
  private String id;

  @Facebook
  Metadata metadata;

  @Facebook
  String type;

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
   * This object's unique Facebook ID.
   * 
   * @return This object's unique Facebook ID.
   */
  public String getId() {
    return id;
  }

  /**
   * This object's metadata, available by including the {@code metadata=1} URL
   * parameter in an API request.
   * 
   * @return This object's metadata, available by including the {@code
   *         metadata=1} URL parameter in an API request.
   */
  public Metadata getMetadata() {
    return metadata;
  }

  /**
   * This object's type metadata, available by including the {@code metadata=1}
   * URL parameter in an API request.
   * 
   * @return This object's type metadata, available by including the {@code
   *         metadata=1} URL parameter in an API request.
   */
  public String getType() {
    return type;
  }

  /**
   * Represents <a
   * href="http://developers.facebook.com/docs/api#introspection">Facebook
   * Object metadata</a>, available by including the {@code metadata=1} URL
   * parameter in an API request.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5
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
     * Represents <a
     * href="http://developers.facebook.com/docs/api#introspection">Facebook
     * Object connections metadata</a>, available by including the {@code
     * metadata=1} URL parameter in an API request.
     * 
     * @author <a href="http://restfb.com">Mark Allen</a>
     * @since 1.5
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

      /**
       * Does this object have a 'home' connection?
       * 
       * @return {@code true} if this object has a 'home' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasHome() {
        return toBoolean(home);
      }

      /**
       * Does this object have a 'feed' connection?
       * 
       * @return {@code true} if this object has a 'feed' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasFeed() {
        return toBoolean(feed);
      }

      /**
       * Does this object have a 'friends' connection?
       * 
       * @return {@code true} if this object has a 'friends' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasFriends() {
        return toBoolean(friends);
      }

      /**
       * Does this object have a 'family' connection?
       * 
       * @return {@code true} if this object has a 'family' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasFamily() {
        return toBoolean(family);
      }

      /**
       * Does this object have an 'activities' connection?
       * 
       * @return {@code true} if this object has an 'activities' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasActivities() {
        return toBoolean(activities);
      }

      /**
       * Does this object have an 'interests' connection?
       * 
       * @return {@code true} if this object has an 'interests' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasInterests() {
        return toBoolean(interests);
      }

      /**
       * Does this object have a 'music' connection?
       * 
       * @return {@code true} if this object has a 'music' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasMusic() {
        return toBoolean(music);
      }

      /**
       * Does this object have a 'books' connection?
       * 
       * @return {@code true} if this object has a 'books' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasBooks() {
        return toBoolean(books);
      }

      /**
       * Does this object have a 'movies' connection?
       * 
       * @return {@code true} if this object has a 'movies' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasMovies() {
        return toBoolean(movies);
      }

      /**
       * Does this object have a 'television' connection?
       * 
       * @return {@code true} if this object has a 'television' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasTelevision() {
        return toBoolean(television);
      }

      /**
       * Does this object have a 'likes' connection?
       * 
       * @return {@code true} if this object has a 'likes' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasLikes() {
        return toBoolean(likes);
      }

      /**
       * Does this object have a 'posts' connection?
       * 
       * @return {@code true} if this object has a 'posts' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasPosts() {
        return toBoolean(posts);
      }

      /**
       * Does this object have a 'tagged' connection?
       * 
       * @return {@code true} if this object has a 'tagged' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasTagged() {
        return toBoolean(tagged);
      }

      /**
       * Does this object have a 'statuses' connection?
       * 
       * @return {@code true} if this object has a 'statuses' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasStatuses() {
        return toBoolean(statuses);
      }

      /**
       * Does this object have a 'links' connection?
       * 
       * @return {@code true} if this object has a 'links' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasLinks() {
        return toBoolean(links);
      }

      /**
       * Does this object have a 'notes' connection?
       * 
       * @return {@code true} if this object has a 'notes' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasNotes() {
        return toBoolean(notes);
      }

      /**
       * Does this object have a 'photos' connection?
       * 
       * @return {@code true} if this object has a 'photos' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasPhotos() {
        return toBoolean(photos);
      }

      /**
       * Does this object have an 'albums' connection?
       * 
       * @return {@code true} if this object has an 'albums' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasAlbums() {
        return toBoolean(albums);
      }

      /**
       * Does this object have an 'events' connection?
       * 
       * @return {@code true} if this object has an 'events' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasEvents() {
        return toBoolean(events);
      }

      /**
       * Does this object have a 'groups' connection?
       * 
       * @return {@code true} if this object has a 'groups' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasGroups() {
        return toBoolean(groups);
      }

      /**
       * Does this object have a 'videos' connection?
       * 
       * @return {@code true} if this object has a 'videos' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasVideos() {
        return toBoolean(videos);
      }

      /**
       * Does this object have a 'picture' connection?
       * 
       * @return {@code true} if this object has a 'picture' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasPicture() {
        return toBoolean(picture);
      }

      /**
       * Does this object have an 'inbox' connection?
       * 
       * @return {@code true} if this object has an 'inbox' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasInbox() {
        return toBoolean(inbox);
      }

      /**
       * Does this object have an 'outbox' connection?
       * 
       * @return {@code true} if this object has an 'outbox' connection, {@code
       *         false} otherwise.
       */
      public Boolean hasOutbox() {
        return toBoolean(outbox);
      }

      /**
       * Does this object have an 'updates' connection?
       * 
       * @return {@code true} if this object has an 'updates' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasUpdates() {
        return toBoolean(updates);
      }
    }

    /**
     * The available connections for this object.
     * 
     * @return The available connections for this object.
     */
    public Connections getConnections() {
      return connections;
    }
  }

  /**
   * Returns {@code true} if {@code string} is not blank, {@code false}
   * otherwise.
   * 
   * @param string
   *          The string to check.
   * @return {@code true} if {@code string} is not blank, {@code false}
   *         otherwise.
   */
  private static Boolean toBoolean(String string) {
    return string != null && !"".equals(string.trim());
  }
}