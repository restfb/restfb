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
package com.restfb.scope;

public enum FacebookPermissions {
  /**
   * Provides access to a subset of items that are part of a person's public profile.
   * 
   * A person's public profile refers to the following properties on the user object by default:
   * <ul>
   * <li>id</li>
   * <li>name</li>
   * <li>first_name</li>
   * <li>last_name</li>
   * <li>link</li>
   * <li>gender</li>
   * <li>locale</li>
   * <li>timezone</li>
   * <li>updated_time</li>
   * <li>verified</li>
   * </ul>
   * 
   * On the web, public_profile is implied with every request and isn't required, although the best practice is to
   * declare it. On iOS and Android, you must manually request it as part of your login flow.<br />
   * <br />
   * 
   * gender & locale can only be accessed if:
   * 
   * <ul>
   * <li>The person queried is the person using the app.</li>
   * <li>The person queried is using the app, and is a friend of the person using the app.</li>
   * <li>The person queried is using the app, is not a friend of the person using the app, but the app includes either
   * an app access token or an appsecret_proof argument with the call.</li> <br />
   * <strong>Review</strong> Your app may use this permission without review from Facebook.
   */
  PUBLIC_PROFILE("public_profile", Category.PUBLIC), //

  /**
   * Provides access to a person's personal description (the 'About Me' section on their Profile) through the bio
   * property on the User object.
   *
   * <p>
   * This permission does not give access to a person's public profile data. A person's name, profile picture, locale,
   * age range and gender are included by default with the public_profile permission.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_ABOUT_ME("user_about_me", Category.USER_DATA), //

  /**
   * Provides access to all common books actions published by any app the person has used. This includes books they've
   * read, want to read, rated or quoted.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_ACTIONS_BOOKS("user_actions.books", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to all common Open Graph fitness actions published by any app the person has used. This includes
   * runs, walks and bikes actions.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_ACTIONS_FITNESS("user_actions.fitness", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to all common Open Graph music actions published by any app the person has used. This includes
   * songs they've listened to, and playlists they've created.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_ACTIONS_MUSIC("user_actions.music", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to all common Open Graph news actions published by any app the person has used which publishes
   * these actions. This includes news articles they've read or news articles they've published.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_ACTIONS_NEWS("user_actions.news", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to all common Open Graph video actions published by any app the person has used which publishes
   * these actions. This includes videos they've watched, videos they've rated and videos they want to watch.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_ACTIONS_VIDEO("user_actions.video", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to a person's list of activities as listed on their Profile. This is a subset of the pages they
   * have liked, where those pages represent particular interests. This information is accessed through the activities
   * connection on the user node.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Until 2.2
   *
   * @Deprecated removed Not usable since Graph API 2.3. If you use the Graph API before 2.3 ignore the deprecation
   *             warning
   */
  @Deprecated
  USER_ACTIVITIES("user_activities", Category.USER_DATA), //

  /**
   * Access the date and month of a person's birthday. This may or may not include the person's year of birth, dependent
   * upon their privacy settings and the access token being used to query this field.
   *
   * <p>
   * Please note most integrations will only need age_range which comes as part of the public_profile permission.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_BIRTHDAY("user_birthday", Category.USER_DATA), //

  /**
   * Provides access to a person's education history through the education field on the User object.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_EDUCATION_HISTORY("user_education_history", Category.USER_DATA), //

  /**
   * Provides read-only access to the Events a person is hosting or has RSVP'd to.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_EVENTS("user_events", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access the list of friends that also use your app. These friends can be found on the friends edge on the
   * user object.
   *
   * <p>
   * In order for a person to show up in one person's friend list, both people must have decided to share their list of
   * friends with your app and not disabled that permission during login. Also both friends must have been asked for
   * <code>user_friends</code> during the login process.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * Your app may use this permission without review from Facebook.
   */
  USER_FRIENDS("user_friends", Category.USER_DATA), //

  /**
   * Provides access to read a person's game activity (scores, achievements) in any game the person has played.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_GAMES_ACTIVITY("user_games_activity", Category.USER_DATA), //

  /**
   * Enables your app to read the Groups a person is a member of through the groups edge on the User object.
   *
   * <p>
   * This permission does not allow you to create groups on behalf of a person. It is not possible to create groups via
   * the Graph API.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated Not usable since Graph API 2.4. If you use the Graph API before 2.4 ignore the deprecation warning
   */
  @Deprecated
  USER_GROUPS("user_groups", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to a person's hometown location through the hometown field on the User object. This is set by the
   * user on the Profile.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_HOMETOWN("user_hometown", Category.USER_DATA), //

  /**
   * Provides access to the list of interests in a person's Profile. This is a subset of the pages they have liked which
   * represent particular interests.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Until 2.2
   *
   * @Deprecated removed Not usable since Graph API 2.3. If you use the Graph API before 2.3 ignore the deprecation
   *             warning
   */
  @Deprecated
  USER_INTERESTS("user_interests", Category.USER_DATA), //

  /**
   * Provides access to the list of all Facebook Pages and Open Graph objects that a person has liked. This list is
   * available through the likes edge on the User object.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_LIKES("user_likes", Category.USER_DATA), //

  /**
   * Provides access to a person's current city through the location field on the User object. The current city is set
   * by a person on their Profile.
   *
   * <p>
   * The current city is not necessarily the same as a person's hometown.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_LOCATION("user_location", Category.USER_DATA), //

  /**
   * Enables your app to read the Groups a person is an admin of through the groups edge on the User object.
   *
   * <p>
   * This permission does not allow you to create groups on behalf of a person. It is not possible to create groups via
   * the Graph API. This does not let you read the groups a user is just a member of.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_MANAGED_GROUPS("user_managed_groups", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to the photos a person has uploaded or been tagged in. This is available through the photos edge on
   * the User object.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_PHOTOS("user_photos", Category.USER_DATA), //

  /**
   * Provides access to the posts on a person's Timeline.
   *
   * <p>
   * Includes their own posts, posts they are tagged in, and posts other people make on their Timeline.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @since 1.10.0
   */
  USER_POSTS("user_posts", Category.USER_DATA), //

  /**
   * Provides access to a person's relationship interests as the <code>interested_in</code> field on the User object.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_RELATIONSHIP_DETAILS("user_relationship_details", Category.USER_DATA), //

  /**
   * Provides access to a person's relationship status, significant other and family members as fields on the User
   * object.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_RELATIONSHIPS("user_relationships", Category.USER_DATA), //

  /**
   * Provides access to a person's religious and political affiliations.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_RELIGION_POLITICS("user_religion_politics", Category.USER_DATA), //

  /**
   * Provides access to a person's statuses. These are posts on Facebook which don't include links, videos or photos.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Until 2.3
   *
   * @deprecated Not usable since Graph API 2.4. If you use the Graph API before 2.4 ignore the deprecation warning. If
   *             you are calling the endpoint <code>/{user-id}/posts</code> or <code>/{user-id}/feed</code>, ask for the
   *             <code>user_posts</code> permission instead (only v2.3 or older).
   */
  @Deprecated
  USER_STATUS("user_status", Category.USER_DATA), //

  /**
   * Provides access to the Places a person has been tagged at in photos, videos, statuses and links.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_TAGGED_PLACES("user_tagged_places", Category.USER_DATA), //

  /**
   * Provides access to the videos a person has uploaded or been tagged in.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_VIDEOS("user_videos", Category.USER_DATA), //

  /**
   * Provides access to the person's personal website URL via the website field on the User object.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_WEBSITE("user_website", Category.USER_DATA), //

  /**
   * Provides access to a person's work history and list of employers via the <code>work</code> field on the User
   * object.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  USER_WORK_HISTORY("user_work_history", Category.USER_DATA), //

  /**
   * Provides the ability to both read and manage the ads for ad accounts you have access to. Please see
   * <a href="https://developers.facebook.com/docs/marketing-api/buying-api">Ads Management</a> for details.
   */
  ADS_MANAGEMENT("ads_management", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides the access to <a href="https://developers.facebook.com/docs/marketing-api/read-access-onboarding">Ads
   * Insights API</a> to pull ads report information for ad accounts you have access to.
   */
  ADS_READ("ads_read", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to the person's primary email address via the email property on the user object.
   *
   * <p>
   * Do not spam users. Your use of email must comply with both Facebook policies and with the CAN-SPAM Act.
   *
   * <p>
   * Note, even if you request the email permission it is not guaranteed you will get an email address. For example, if
   * someone signed up for Facebook with a phone number instead of an email address, the email field may be empty.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * Your app may use this permission without review from Facebook.
   */
  EMAIL("email", Category.USER_DATA), //

  /**
   * Enables your app to read a person's notifications and mark them as read.
   *
   * <p>
   * This permission does not let you send notifications to a person.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Since 2.3
   * @deprecated Not usable since Graph API 2.4. If you use the Graph API before 2.4 ignore the deprecation warning
   */
  @Deprecated
  MANAGE_NOTIFICATIONS("manage_notifications", Category.OTHER), //

  /**
   * Enables your app to retrieve Page Access Tokens for the Pages and Apps that the person administrates.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it. You can grant this
   * permission on behalf of people listed within the Roles section of your App's Dashboard without review by Facebook.
   */
  MANAGE_PAGES("manage_pages", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides the access to manage call to actions of the Pages that you manage.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @since Graph API 2.5
   * @RestFB.GraphApi.Since 2.5
   */
  PAGES_MANAGE_CTA("pages_manage_cta", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Lets your app manage Instant Articles on behalf of Facebook Pages administered by people using your app.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Since 2.5
   * @since Graph API 2.5
   */
  PAGES_MANAGE_INSTANT_ARTICLES("pages_manage_instant_articles", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to manage leads retrieved from Lead Ads of the Pages that you manage.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Since 2.3
   * @since Graph API 2.3
   */
  PAGES_MANAGE_LEADS("pages_manage_leads", Category.EVENTS_GROUPS_PAGES), //

  /**
   * This allows you to send and receive messages through a Facebook Page.
   *
   * <p>
   * This permission cannot be used to send promotional or advertising content. Conversations through this API can only
   * begin when someone indicates—through a Messenger plugin or directly messaging you—that they want to receive
   * messages from you.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Since 2.6
   * @since Graph API 2.6
   */
  PAGES_MESSAGING("pages_messaging", Category.MESSAGING), //

  /**
   * This allows you to send and receive messages through a Facebook Page.
   *
   * <p>
   * This permission cannot be used to send promotional or advertising content. Conversations through this API can only
   * begin when someone indicates—through a Messenger plugin or directly messaging you—that they want to receive
   * messages from you.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Since 2.6
   * @since Graph API 2.6
   */
  PAGES_MESSAGING_PHONE_NUMBER("pages_messaging_phone_number", Category.MESSAGING), //

  /**
   * Provides the access to show the list of the Pages that you manage.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @since Graph API 2.5
   * @RestFB.GraphApi.Since 2.5
   */
  PAGES_SHOW_LIST("pages_show_list", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to publish Posts, Open Graph actions, achievements, scores and other activity on behalf of a person
   * using your app.
   *
   * <p>
   * Because this permission lets you publish on behalf of a user please read the Platform Policies to ensure you
   * understand how to properly use this permission.
   *
   * <p>
   * Your app does not need to request the publish_actions permission in order to use the Feed Dialog, the Requests
   * Dialog or the Send Dialog
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  PUBLISH_ACTIONS("publish_actions", Category.USER_DATA), //

  /**
   * Gives your app the ability to post, comment and like as any of the Pages managed by a person using your app.
   *
   * <p>
   * Publishing as an individual personal account is not possible with this permission. To post as an individual, please
   * see the <code>publish_actions</code> permission.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @since Graph API 2.3
   * @RestFB.GraphApi.Since 2.3
   */
  PUBLISH_PAGES("publish_pages", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides read-only access to the Audience Network Insights data for Apps the person owns.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @since Graph API 2.4
   * @RestFB.GraphApi.Since 2.4
   */
  READ_AUDIENCE_NETWORK_INSIGHTS("read_audience_network_insights", Category.OTHER), //

  /**
   * Provides access to the names of custom lists a person has created to organize their friends. This is useful for
   * rendering an audience selector when someone is publishing stories to Facebook from your app.
   *
   * <p>
   * This permission does not give access to a list of person's friends. If you want to access a person's friends who
   * also use your app, you should use the user_friends permission.
   *
   * <p>
   * This permission will also not help you invite a person's friends to use your app.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated not usable since Graph API 2.3, use <code>READ_CUSTOM_FRIENDLISTS</code> instead. If you use the Graph
   *             API before 2.3 ignore the deprecation warning
   * @RestFB.GraphApi.Until 2.2
   */
  @Deprecated
  READ_FRIENDLISTS("read_friendlists", Category.OTHER), //

  /**
   * Provides access to the names of custom lists a person has created to organize their friends.
   *
   * <p>
   * This is useful for rendering an audience selector when someone is publishing stories to Facebook from your app.
   *
   * <p>
   * This permission does not give access to a list of person's friends. If you want to access a person's friends who
   * also use your app, you should use the user_friends permission.
   *
   * <p>
   * This permission will also not help you invite a person's friends to use your app.
   *
   * <p>
   * This permission was called <code>read_friendlists</code> before v2.3.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @since Graph API 2.3
   * @RestFB.GraphApi.Since 2.3
   *
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  READ_CUSTOM_FRIENDLISTS("read_custom_friendlists", Category.OTHER), //

  /**
   * Provides read-only access to the Insights data for Pages, Apps and web domains the person owns.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  READ_INSIGHTS("read_insights", Category.OTHER), //

  /**
   * Facebook has not yet officially documented this permission, but it is necessary for certain integrations with the
   * new Messenger Platform.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * Your app will need to be approved and whitelisted by Facebook to use of this permission.
   *
   * @since Graph API 2.5
   * @RestFB.GraphApi.Until 2.5
   */
  XMPP_LOGIN("xmpp_login", Category.OTHER), //

  /**
   * Provides the ability to read the messages in a person's Facebook Inbox through the inbox edge and the thread node.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @deprecated Not usable since Graph API 2.4. If you use the Graph API before 2.4 ignore the deprecation warning
   * @RestFB.GraphApi.Until 2.3
   */
  @Deprecated
  READ_MAILBOX("read_mailbox", Category.OTHER), //

  /**
   * Provides the ability to read from the Page Inboxes of the Pages managed by a person. This permission is often used
   * alongside the manage_pages permission.
   *
   * <p>
   * This permission does not let your app read the page owner's mailbox. It only applies to the page's mailbox.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  READ_PAGE_MAILBOXES("read_page_mailboxes", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to read the posts in a person's News Feed, or the posts on their Profile.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * <p>
   *
   * @deprecated Not usable since Graph API 2.4. If you use the Graph API before 2.4 ignore the deprecation warning
   * @RestFB.GraphApi.Until 2.3
   */
  @Deprecated
  READ_STREAM("read_stream", Category.OTHER), //

  /**
   * Provides the ability to set a person's attendee status on Facebook Events (eg attending, maybe, or declined).
   *
   * <p>
   * This permission does not let you invite people to an event.
   *
   * <p>
   * This permission does not let you update an event's details.
   *
   * <p>
   * This permission does not let you create an event. There is no way to create an event via the API as of Graph API
   * v2.0.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  RSVP_EVENT("rsvp_event", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Read and write with Business Management API
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  BUSINESS_MANAGEMENT("business_management", Category.EVENTS_GROUPS_PAGES), //

  /**
   * This allows you to send and receive messages through a Facebook Page out of the 24h window opened by a user action.
   *
   * <p>
   * This permission cannot be used to send promotional or advertising content.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Since 2.6
   * @since Graph API 2.6
   */
  PAGES_MESSAGING_SUBSCRPTIONS("pages_messaging_subscriptions", Category.MESSAGING), //

  /**
   * This allows you to charge users in Messenger conversations on behalf of pages.
   *
   * <p>
   * Intended for tangible goods only, not virtual or subscriptions.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Since 2.6
   * @since Graph API 2.6
   */
  PAGES_MESSAGING_PAYMENTS("pages_messaging_payments", Category.MESSAGING),

  // Instagram Platform permissions

  /**
   * Provides the ability to read Instagram accounts you have access to.
   *
   * <p>
   * Please see <a href="https://developers.facebook.com/docs/instagram-api/getting-started">Instagram's Getting Started
   * Guide</a> for details.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Since 2.5
   * @since Graph API 2.5
   */
  INSTAGRAM_BASIC("instagram_basic", Category.INSTAGRAM),

  /**
   * Provides the ability to read Instagram accounts you have access to.
   *
   * <p>
   * Please see <a href="https://developers.facebook.com/docs/instagram-api/getting-started">Instagram's Getting Started
   * Guide</a> for details.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Since 2.5
   * @since Graph API 2.5
   */
  INSTAGRAM_MANAGE_COMMENTS("instagram_manage_comments", Category.INSTAGRAM),

  /**
   * Provides the ability to read insights of Instagram account you have access to.
   *
   * <p>
   * Please see <a href="https://developers.facebook.com/docs/instagram-api/getting-started">Instagram's Getting Started
   * Guide</a> for details.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Since 2.5
   * @since Graph API 2.5
   */
  INSTAGRAM_MANAGE_INSIGHTS("instagram_manage_insights", Category.INSTAGRAM),

  /**
   * Provides the ability to publish content to Instagram account you have access to.
   *
   * <p>
   * Please see <a href="https://developers.facebook.com/docs/instagram-api/reference/user/media_publish">Instagram's
   * reference</a> for details.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   *
   * @RestFB.GraphApi.Since 2.5
   * @since Graph API 2.5
   */
  INSTAGRAM_CONTENT_PUBLISH("instagram_content_publish", Category.INSTAGRAM);

  private final String permissionString;

  private final Category category;

  FacebookPermissions(String facebookPermissionString, Category category) {
    this.permissionString = facebookPermissionString;
    this.category = category;
  }

  public String getPermissionString() {
    return this.permissionString;
  }

  public Category getCategory() {
    return this.category;
  }

  public enum Category {
    PUBLIC, USER_DATA, EVENTS_GROUPS_PAGES, OTHER, MESSAGING, INSTAGRAM;
  }
}
