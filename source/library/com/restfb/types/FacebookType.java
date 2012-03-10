/*
 * Copyright (c) 2010-2012 Mark Allen.
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

import static com.restfb.util.StringUtils.isBlank;

import java.io.Serializable;

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;

/**
 * Base class which encapsulates behavior and properties common to most <a
 * href="http://developers.facebook.com/docs/reference/api/">Graph API
 * types</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class FacebookType implements Serializable {
  @Facebook
  private String id;

  @Facebook
  private Metadata metadata;

  @Facebook
  private String type;

  private static final long serialVersionUID = 1L;

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
   * @return This object's metadata, available by including the
   *         {@code metadata=1} URL parameter in an API request.
   */
  public Metadata getMetadata() {
    return metadata;
  }

  /**
   * This object's type metadata, available by including the {@code metadata=1}
   * URL parameter in an API request.
   * 
   * @return This object's type metadata, available by including the
   *         {@code metadata=1} URL parameter in an API request.
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
  public static class Metadata implements Serializable {
    @Facebook
    private Connections connections;

    private static final long serialVersionUID = 1L;

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
     * Object connections metadata</a>, available by including the
     * {@code metadata=1} URL parameter in an API request.
     * 
     * @author <a href="http://restfb.com">Mark Allen</a>
     * @since 1.5
     */
    public static class Connections implements Serializable {
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

      private static final long serialVersionUID = 1L;

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
       * @return {@code true} if this object has a 'home' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasHome() {
        return !isBlank(home);
      }

      /**
       * Does this object have a 'feed' connection?
       * 
       * @return {@code true} if this object has a 'feed' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasFeed() {
        return !isBlank(feed);
      }

      /**
       * Does this object have a 'friends' connection?
       * 
       * @return {@code true} if this object has a 'friends' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasFriends() {
        return !isBlank(friends);
      }

      /**
       * Does this object have a 'family' connection?
       * 
       * @return {@code true} if this object has a 'family' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasFamily() {
        return !isBlank(family);
      }

      /**
       * Does this object have an 'activities' connection?
       * 
       * @return {@code true} if this object has an 'activities' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasActivities() {
        return !isBlank(activities);
      }

      /**
       * Does this object have an 'interests' connection?
       * 
       * @return {@code true} if this object has an 'interests' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasInterests() {
        return !isBlank(interests);
      }

      /**
       * Does this object have a 'music' connection?
       * 
       * @return {@code true} if this object has a 'music' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasMusic() {
        return !isBlank(music);
      }

      /**
       * Does this object have a 'books' connection?
       * 
       * @return {@code true} if this object has a 'books' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasBooks() {
        return !isBlank(books);
      }

      /**
       * Does this object have a 'movies' connection?
       * 
       * @return {@code true} if this object has a 'movies' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasMovies() {
        return !isBlank(movies);
      }

      /**
       * Does this object have a 'television' connection?
       * 
       * @return {@code true} if this object has a 'television' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasTelevision() {
        return !isBlank(television);
      }

      /**
       * Does this object have a 'likes' connection?
       * 
       * @return {@code true} if this object has a 'likes' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasLikes() {
        return !isBlank(likes);
      }

      /**
       * Does this object have a 'posts' connection?
       * 
       * @return {@code true} if this object has a 'posts' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasPosts() {
        return !isBlank(posts);
      }

      /**
       * Does this object have a 'tagged' connection?
       * 
       * @return {@code true} if this object has a 'tagged' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasTagged() {
        return !isBlank(tagged);
      }

      /**
       * Does this object have a 'statuses' connection?
       * 
       * @return {@code true} if this object has a 'statuses' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasStatuses() {
        return !isBlank(statuses);
      }

      /**
       * Does this object have a 'links' connection?
       * 
       * @return {@code true} if this object has a 'links' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasLinks() {
        return !isBlank(links);
      }

      /**
       * Does this object have a 'notes' connection?
       * 
       * @return {@code true} if this object has a 'notes' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasNotes() {
        return !isBlank(notes);
      }

      /**
       * Does this object have a 'photos' connection?
       * 
       * @return {@code true} if this object has a 'photos' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasPhotos() {
        return !isBlank(photos);
      }

      /**
       * Does this object have an 'albums' connection?
       * 
       * @return {@code true} if this object has an 'albums' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasAlbums() {
        return !isBlank(albums);
      }

      /**
       * Does this object have an 'events' connection?
       * 
       * @return {@code true} if this object has an 'events' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasEvents() {
        return !isBlank(events);
      }

      /**
       * Does this object have a 'groups' connection?
       * 
       * @return {@code true} if this object has a 'groups' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasGroups() {
        return !isBlank(groups);
      }

      /**
       * Does this object have a 'videos' connection?
       * 
       * @return {@code true} if this object has a 'videos' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasVideos() {
        return !isBlank(videos);
      }

      /**
       * Does this object have a 'picture' connection?
       * 
       * @return {@code true} if this object has a 'picture' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasPicture() {
        return !isBlank(picture);
      }

      /**
       * Does this object have an 'inbox' connection?
       * 
       * @return {@code true} if this object has an 'inbox' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasInbox() {
        return !isBlank(inbox);
      }

      /**
       * Does this object have an 'outbox' connection?
       * 
       * @return {@code true} if this object has an 'outbox' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasOutbox() {
        return !isBlank(outbox);
      }

      /**
       * Does this object have an 'updates' connection?
       * 
       * @return {@code true} if this object has an 'updates' connection,
       *         {@code false} otherwise.
       */
      public Boolean hasUpdates() {
        return !isBlank(updates);
      }

      /**
       * This object's 'home' connection URL.
       * 
       * @return This object's 'home' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getHome() {
        return home;
      }

      /**
       * This object's 'feed' connection URL.
       * 
       * @return This object's 'feed' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getFeed() {
        return feed;
      }

      /**
       * This object's 'friends' connection URL.
       * 
       * @return This object's 'friends' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getFriends() {
        return friends;
      }

      /**
       * This object's 'family' connection URL.
       * 
       * @return This object's 'family' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getFamily() {
        return family;
      }

      /**
       * This object's 'activities' connection URL.
       * 
       * @return This object's 'activities' connection URL, or {@code null} if
       *         it doesn't have one.
       */
      public String getActivities() {
        return activities;
      }

      /**
       * This object's 'interests' connection URL.
       * 
       * @return This object's 'interests' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getInterests() {
        return interests;
      }

      /**
       * This object's 'music' connection URL.
       * 
       * @return This object's 'music' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getMusic() {
        return music;
      }

      /**
       * This object's 'books' connection URL.
       * 
       * @return This object's 'books' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getBooks() {
        return books;
      }

      /**
       * This object's 'movies' connection URL.
       * 
       * @return This object's 'movies' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getMovies() {
        return movies;
      }

      /**
       * This object's 'television' connection URL.
       * 
       * @return This object's 'television' connection URL, or {@code null} if
       *         it doesn't have one.
       */
      public String getTelevision() {
        return television;
      }

      /**
       * This object's 'likes' connection URL.
       * 
       * @return This object's 'likes' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getLikes() {
        return likes;
      }

      /**
       * This object's 'posts' connection URL.
       * 
       * @return This object's 'posts' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getPosts() {
        return posts;
      }

      /**
       * This object's 'tagged' connection URL.
       * 
       * @return This object's 'tagged' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getTagged() {
        return tagged;
      }

      /**
       * This object's 'statuses' connection URL.
       * 
       * @return This object's 'statuses' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getStatuses() {
        return statuses;
      }

      /**
       * This object's 'links' connection URL.
       * 
       * @return This object's 'links' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getLinks() {
        return links;
      }

      /**
       * This object's 'notes' connection URL.
       * 
       * @return This object's 'notes' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getNotes() {
        return notes;
      }

      /**
       * This object's 'photos' connection URL.
       * 
       * @return This object's 'photos' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getPhotos() {
        return photos;
      }

      /**
       * This object's 'albums' connection URL.
       * 
       * @return This object's 'albums' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getAlbums() {
        return albums;
      }

      /**
       * This object's 'events' connection URL.
       * 
       * @return This object's 'events' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getEvents() {
        return events;
      }

      /**
       * This object's 'groups' connection URL.
       * 
       * @return This object's 'groups' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getGroups() {
        return groups;
      }

      /**
       * This object's 'videos' connection URL.
       * 
       * @return This object's 'videos' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getVideos() {
        return videos;
      }

      /**
       * This object's 'picture' connection URL.
       * 
       * @return This object's 'picture' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getPicture() {
        return picture;
      }

      /**
       * This object's 'inbox' connection URL.
       * 
       * @return This object's 'inbox' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getInbox() {
        return inbox;
      }

      /**
       * This object's 'outbox' connection URL.
       * 
       * @return This object's 'outbox' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getOutbox() {
        return outbox;
      }

      /**
       * This object's 'updates' connection URL.
       * 
       * @return This object's 'updates' connection URL, or {@code null} if it
       *         doesn't have one.
       */
      public String getUpdates() {
        return updates;
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
}