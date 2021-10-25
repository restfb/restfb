/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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

import com.restfb.annotation.GraphAPI;

/**
 * Please check the permission dependencies
 * <a href="https://developers.facebook.com/docs/pages/overview-1#permission-dependencies">here</a>
 */
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
   * gender &amp; locale can only be accessed if:
   * 
   * <ul>
   * <li>The person queried is the person using the app.</li>
   * <li>The person queried is using the app, and is a friend of the person using the app.</li>
   * <li>The person queried is using the app, is not a friend of the person using the app, but the app includes either
   * an app access token or an <code>appsecret_proof</code> argument with the call.</li> <br />
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
   * Provides access to a person's age range.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "3.0")
  USER_AGE_RANGE("user_age_range", Category.USER_DATA), //

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
   * Provides access to a person's gender.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "3.0")
  USER_GENDER("user_gender", Category.USER_DATA), //

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
   * Provides access to the Facebook profile URL for another user of the app.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "3.0")
  USER_LINK("user_link", Category.USER_DATA), //

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
   * This permission is being deprecated as Groups is moving to a new permission model for apps. Please continue to use
   * {@code user_managed_groups} for testing your apps in dev mode. However, when submitting for review, please select
   * the reviewable feature Groups API, and do not submit this {@code user_managed_groups} in your review.
   *
   * <p>
   * <b>Limited use: for testing only</b>
   */
  USER_MANAGED_GROUPS("user_managed_groups", Category.EVENTS_GROUPS_PAGES), //

  /**
   * The user_messenger_contact permission allows a business to contact a person via Messenger upon their approval or
   * initiation of a chat thread with the business's Page.
   *
   * <p>
   * The allowed usage for this permission is for a Page to send a person an initial message,
   * post—purchase updates and account updates.
   *
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_MESSENGER_CONTACT("user_messenger_contact", Category.EVENTS_GROUPS_PAGES), //

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
   * Enables your app to retrieve Page Access Tokens for the Pages and Apps that the person administrates.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it. You can grant this
   * permission on behalf of people listed within the Roles section of your App's Dashboard without review by Facebook.
   */
  @Deprecated
  MANAGE_PAGES("manage_pages", Category.EVENTS_GROUPS_PAGES), //

  /**
   * The {@code pages_manage_ads} permission allows your app the ability to manage ads associated with the Page.
   * 
   * <p>
   * You can use this permission to create and manage ads for the Page.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  PAGES_MANAGE_ADS("pages_manage_ads", Category.EVENTS_GROUPS_PAGES), //

  /**
   * The {@code pages_manage_metadata} permission allows you to subscribe and receive webhooks about activity on the
   * Page, and to update settings on the Page.
   * 
   * <p>
   * You can use this permission if you need it to help the Page Admin administer and manage the Page.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  PAGES_MANAGE_METADATA("pages_manage_metadata", Category.EVENTS_GROUPS_PAGES), //

  /**
   * The {@code pages_read_engagement} permission allows your app the ability to read content (posts, photos, videos,
   * events) posted by the Page, read followers data including name, PSID, and profile picture, and read metadata and
   * other insights about the Page.
   * 
   * <p>
   * You can use this permission if you need it to help the Page Admin administer and manage the Page.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  PAGES_READ_ENGAGEMENT("pages_read_engagement", Category.EVENTS_GROUPS_PAGES), //

  /**
   * The {@code pages_read_user_content} permission allows your app the ability to read User generated content on the
   * Page, such as posts, comments, and ratings by Users or other Pages, and to delete User comments on Page posts.
   *
   * <p>
   * It also allows your app to read posts that the Page is tagged in.
   *
   * <p>
   * You can use this permission to read Users and other Page’s content posted on the Page if you need it to help manage
   * the Page.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  PAGES_READ_USER_CONTENT("pages_read_user_content", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides the access to manage call to actions of the Pages that you manage.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "2.5")
  PAGES_MANAGE_CTA("pages_manage_cta", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Lets your app manage Instant Articles on behalf of Facebook Pages administered by people using your app.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "2.5")
  PAGES_MANAGE_INSTANT_ARTICLES("pages_manage_instant_articles", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides access to manage leads retrieved from Lead Ads of the Pages that you manage.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "2.3")
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
   */
  @GraphAPI(since = "2.6")
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
   */
  @GraphAPI(since = "2.6")
  PAGES_MESSAGING_PHONE_NUMBER("pages_messaging_phone_number", Category.MESSAGING), //

  /**
   * Provides the access to show the list of the Pages that you manage.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "2.5")
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
   *
   * @deprecated this permission is deprecated and it is not possible to publish anything to the user feed via Graph API
   *             since April 2018
   */
  @Deprecated
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
   */
  @GraphAPI(since = "2.3")
  @Deprecated
  PUBLISH_PAGES("publish_pages", Category.EVENTS_GROUPS_PAGES), //

  /**
   * The {@code pages_manage_posts} permission allows your app the ability to create, edit, and delete your Page posts.
   *
   * <p>
   * If you have access to {@code pages_read_user_content}, you can also use {@code pages_manage_posts} to delete Page
   * posts created by a User.
   *
   * <p>
   * You can use this permission to create and delete content on the Page.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  PAGES_MANAGE_POSTS("pages_manage_posts", Category.EVENTS_GROUPS_PAGES), //

  /**
   * The {@code pages_manage_engagement} permission allows your app the ability to create, edit, and delete comments
   * posted on the Page.
   *
   * <p>
   * If you have access to {@code pages_read_user_content}, you can also use {@code pages_manage_engagement} to delete
   * comments posted by other Pages.
   *
   * <p>
   * It also allows your app the ability to create and delete your own Page's likes to Page content.
   *
   * <p>
   * You can use this permission if you need it to help manage and moderate content on the Page.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  PAGES_MANAGE_ENGAGEMENT("pages_manage_engagement", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Gives an app the ability to post content into a group on behalf of a user who has granted the app this permission.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "3.0")
  PUBLISH_TO_GROUPS("publish_to_groups", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Grants an app permission to publish live videos to the app User's timeline.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "3.1")
  PUBLISH_VIDEO("publish_video", Category.LIVE_VIDEO), //

  /**
   * Gives your app the ability to receive member-related data on group content when a member has granted the app
   * permission to do so.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "3.0")
  GROUPS_ACCESS_MEMBER_INFO("groups_access_member_info", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Provides read-only access to the Audience Network Insights data for Apps the person owns.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "2.4")
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
   */
  @Deprecated
  @GraphAPI(until = "2.2")
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
   * @deprecated since breaking change on 4 April, 2018
   */
  @Deprecated
  @GraphAPI(since = "2.3")
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
   * Grants your app the ability to create, read, update, and delete business owned product catalogs that the user is an
   * admin of. This permission grants access to related endpoints. By default, your app may only access product catalogs
   * that are owned by admins of the app when in developer mode.
   * 
   * <p>
   * <strong>Review</strong>
   * 
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  CATALOG_MANAGEMENT("catalog_management", Category.EVENTS_GROUPS_PAGES), //

  /**
   * Grants your app permission to retrieve all the information captured within a
   * <a href="https://developers.facebook.com/docs/marketing-api/guides/lead-ads/">lead.</a>
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  @GraphAPI(since = "3.1")
  LEADS_RETRIEVAL("leads_retrieval", Category.EVENTS_GROUPS_PAGES), //

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
   */
  @GraphAPI(since = "2.6")
  PAGES_MESSAGING_SUBSCRIPTIONS("pages_messaging_subscriptions", Category.MESSAGING), //

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
   */
  @GraphAPI(since = "2.6")
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
   */
  @GraphAPI(since = "2.5")
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
   */
  @GraphAPI(since = "2.5")
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
   */
  @GraphAPI(since = "2.5")
  INSTAGRAM_MANAGE_INSIGHTS("instagram_manage_insights", Category.INSTAGRAM),

  /**
   * The instagram_manage_messages permission allows business users to read and respond to Instagram Direct messages.
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
   */
  INSTAGRAM_MANAGE_MESSAGES("instagram_manage_messages", Category.INSTAGRAM),

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
   */
  @GraphAPI(since = "2.5")
  INSTAGRAM_CONTENT_PUBLISH("instagram_content_publish", Category.INSTAGRAM),

  /**
   * Provides the ability to read and/or manage WhatsApp business assets you own or have been granted access to by other
   * businesses through this permission.
   *
   * These business assets include WhatsApp business accounts, phone numbers, and message templates.
   *
   * <p>
   * Please see <a href=
   * "https://developers.facebook.com/docs/facebook-login/permissions/#reference-whatsapp_business_management">Whatsapps's
   * reference</a> for details.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  WHATSAPP_BUSINESS_MANAGEMENT("whatsapp_business_management", Category.WHATSAPP);

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
    PUBLIC, USER_DATA, EVENTS_GROUPS_PAGES, OTHER, MESSAGING, INSTAGRAM, LIVE_VIDEO, WHATSAPP
  }
}
