/*
 * Copyright (c) 2010-2015 Mark Allen.
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

package com.restfb.scope;

public enum UserDataPermissions implements FacebookPermissions {

  /**
   * Provides access to a person's personal description (the 'About Me' section on their Profile) through the bio
   * property on the User object.
   * 
   * This permission does not give access to a person's public profile data. A person's name, profile picture, locale,
   * age range and gender are included by default with the public_profile permission.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_ABOUT_ME("user_about_me"), //

  /**
   * Provides access to all common books actions published by any app the person has used. This includes books they've
   * read, want to read, rated or quoted.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_ACTIONS_BOOKS("user_actions.books"), //

  /**
   * Provides access to all common Open Graph fitness actions published by any app the person has used. This includes
   * runs, walks and bikes actions.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_ACTIONS_FITNESS("user_actions.fitness"), //

  /**
   * Provides access to all common Open Graph music actions published by any app the person has used. This includes
   * songs they've listened to, and playlists they've created.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_ACTIONS_MUSIC("user_actions.music"), //

  /**
   * Provides access to all common Open Graph news actions published by any app the person has used which publishes
   * these actions. This includes news articles they've read or news articles they've published.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_ACTIONS_NEWS("user_actions.news"), //

  /**
   * Provides access to all common Open Graph video actions published by any app the person has used which publishes
   * these actions. This includes videos they've watched, videos they've rated and videos they want to watch.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_ACTIONS_VIDEO("user_actions.video"), //

  /**
   * Provides access to a person's list of activities as listed on their Profile. This is a subset of the pages they
   * have liked, where those pages represent particular interests. This information is accessed through the activities
   * connection on the user node.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_ACTIVITIES("user_activities"), //

  /**
   * Access the date and month of a person's birthday. This may or may not include the person's year of birth, dependent
   * upon their privacy settings and the access token being used to query this field.
   * 
   * Please note most integrations will only need age_range which comes as part of the public_profile permission.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_BIRTHDAY("user_birthday"), //

  /**
   * Provides access to a person's education history through the education field on the User object.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_EDUCATION_HISTORY("user_education_history"), //

  /**
   * Provides read-only access to the Events a person is hosting or has RSVP'd to.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_EVENTS("user_events"), //

  /**
   * Provides access the list of friends that also use your app. These friends can be found on the friends edge on the
   * user object.
   * 
   * In order for a person to show up in one person's friend list, both people must have decided to share their list of
   * friends with your app and not disabled that permission during login. Also both friends must have been asked for
   * <code>user_friends</code> during the login process.
   * 
   * <strong>Review</strong>
   * 
   * Your app may use this permission without review from Facebook.
   */
  USER_FRIENDS("user_friends"), //

  /**
   * Provides access to read a person's game activity (scores, achievements) in any game the person has played.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_GAMES_ACTIVITY("user_games_activity"), //

  /**
   * Enables your app to read the Groups a person is a member of through the groups edge on the User object.
   * 
   * This permission does not allow you to create groups on behalf of a person. It is not possible to create groups via
   * the Graph API.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_GROUPS("user_groups"), //

  /**
   * Provides access to a person's hometown location through the hometown field on the User object. This is set by the
   * user on the Profile.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_HOMETOWN("user_hometown"), //

  /**
   * Provides access to the list of interests in a person's Profile. This is a subset of the pages they have liked which
   * represent particular interests.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_INTERESTS("user_interests"), //

  /**
   * Provides access to the list of all Facebook Pages and Open Graph objects that a person has liked. This list is
   * available through the likes edge on the User object.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_LIKES("user_likes"), //

  /**
   * Provides access to a person's current city through the location field on the User object. The current city is set
   * by a person on their Profile.
   * 
   * The current city is not necessarily the same as a person's hometown.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_LOCATION("user_location"), //

  /**
   * Provides access to the photos a person has uploaded or been tagged in. This is available through the photos edge on
   * the User object.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_PHOTOS("user_photos"), //

  /**
   * Provides access to the posts on a person's Timeline.
   * 
   * Includes their own posts, posts they are tagged in, and posts other people make on their Timeline.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   * 
   * @since 1.10.0
   */
  USER_POSTS("user_posts"), //

  /**
   * Provides access to a person's relationship interests as the <code>interested_in</code> field on the User object.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_RELATIONSHIP_DETAILS("user_relationship_details"), //

  /**
   * Provides access to a person's relationship status, significant other and family members as fields on the User
   * object.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_RELATIONSHIPS("user_relationships"), //

  /**
   * Provides access to a person's religious and political affiliations.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_RELIGION_POLITICS("user_religion_politics"), //

  /**
   * Provides access to a person's statuses. These are posts on Facebook which don't include links, videos or photos.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_STATUS("user_status"), //

  /**
   * Provides access to the Places a person has been tagged at in photos, videos, statuses and links.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_TAGGED_PLACES("user_tagged_places"), //

  /**
   * Provides access to the videos a person has uploaded or been tagged in.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_VIDEOS("user_videos"), //

  /**
   * Provides access to the person's personal website URL via the website field on the User object.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_WEBSITE("user_website"), //

  /**
   * Provides access to a person's work history and list of employers via the <code>work</code> field on the User
   * object.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_WORK_HISTORY("user_work_history");

  UserDataPermissions(String facebookPermissionString) {
    this.permissionString = facebookPermissionString;
  }

  String permissionString;

  @Override
  public String getPermissionString() {
    return this.permissionString;
  }

}
