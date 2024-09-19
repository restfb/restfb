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
package com.restfb.scope;

import com.restfb.annotation.GraphAPI;

/**
 * Please check the permission dependencies
 * <a href="https://developers.facebook.com/docs/pages/overview-1#permission-dependencies">here</a>
 */
public enum FacebookPermissions {
  /**
   * Provides access to a subset of items that are part of a person's public profile.
   * <p>
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
   * <b>Limited use: for testing only</strong>
   */
  USER_MANAGED_GROUPS("user_managed_groups", Category.EVENTS_GROUPS_PAGES), //

  /**
   * The user_messenger_contact permission allows a business to contact a person via Messenger upon their approval or
   * initiation of a chat thread with the business's Page.
   *
   * <p>
   * The allowed usage for this permission is for a Page to send a person an initial message, post—purchase updates and
   * account updates.
   * <p>
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
   * The attribution_read permission grants your app access to the Attribution API to pull attribution report data for
   * lines of business you own or have been granted access to by the owner or owners of other lines of business.
   * <p>
   * Allowed Usage
   * <p>
   * Provides the ability for your app to access ads performance data from Attribution for use in custom dashboards and
   * data analytics.
   */
  ATTRIBUTION_READ("attribution_read", Category.EVENTS_GROUPS_PAGES), //

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
   * The page_events permissions allows your app permission to log events on behalf of Facebook Pages administered by
   * people using your app and to send those events to Facebook for ads targeting, optimization and reporting.
   * <p>
   * <strong>Allowed Usage</strong>
   * <p>
   * Send businesses related activities (for example purchase, add-to-cart, lead) on behalf of Pages owned by the people
   * who use your app.
   */
  PAGES_EVENTS("pages_events", Category.EVENTS_GROUPS_PAGES), //

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
   *
   * @since June 1, 2020
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
   *
   * @since June 1, 2020
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
   *
   * @since June 1, 2020
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
   *
   * @since June 1, 2020
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
   * The pages_user_gender permission allows your app to access a user's gender through the Page your app is connected
   * to.
   * <p>
   * Allowed Usage
   *
   * <ul>
   * <li>Personalize experiences or recommendations based on gender.</li>
   * <li>Use gendered language such as correct pronouns and titles.</li>
   * </ul>
   */
  PAGES_USER_GENDER("pages_user_gender", Category.EVENTS_GROUPS_PAGES), //

  /**
   * The pages_user_locale permission allows your to app to a user's locale through the Page your app is connected to.
   * <p>
   * Allowed Usage
   *
   * <ul>
   * <li>Personalize experiences based on the locale of a person by surfacing locale specific content.</li>
   * <li>Send responses in the preferred language of the person.</li>
   * <li>Display numbers, times, and dates correctly for the locale of the person.</li>
   * </ul>
   */
  PAGES_USER_LOCALE("pages_user_locale", Category.EVENTS_GROUPS_PAGES), //

  /**
   * The pages_user_timezone permission grants your app access to a user's time zone through the Page your app is
   * connected to.
   * <p>
   * Allowed Usage
   *
   * <ul>
   * <li>Prevent messages from being sent at an inconvenient time.</li>
   * <li>Send time sensitive content or recurring news at a specific time.</li>
   * <li>Provide tailored content based on time.</li>
   * <li>Send time appropriate greetings.</li>
   * </ul>
   */
  PAGES_USER_TIMEZONE("pages_user_timezone", Category.EVENTS_GROUPS_PAGES), //

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
   *
   * @since June 1, 2020
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
   *
   * @since June 1, 2020
   */
  PAGES_MANAGE_ENGAGEMENT("pages_manage_engagement", Category.EVENTS_GROUPS_PAGES), //

  /**
   * The private_computation_access permission allows an app to access the Meta Private Computation products.
   * <p>
   * <strong>Allowed Usage</strong>
   * <ul>
   * <li>Monitor private attribution datasets for a business.</li>
   * <li>Monitor instances for private attribution datasets for a business.</li>
   * <li>Create and manage instances for private attribution datasets for a business.</li>
   * </ul>
   */
  PRIVATE_COMPUTATION_ACCESS("private_computation_access", Category.OTHER), //

  /**
   * Gives an app the ability to post content into a group on behalf of a user who has granted the app this permission.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   * 
   * @deprecated no longer supported with Graph API 19.0 or after 22. April 2024
   */
  @GraphAPI(since = "3.0", until = "19.0")
  @Deprecated
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
   * 
   * @deprecated no longer supported with Graph API 19.0 or after 22. April 2024
   */
  @GraphAPI(since = "3.0", until = "19.0")
  @Deprecated
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
   * The research_apis permission allows your app to access data on public Facebook Pages, Groups, and Events within the
   * Facebook Open Research and Transparency Tool.
   * <p>
   * <strong>Allowed Usage</strong>
   * <p>
   * Utilize public Facebook data for academic research.
   */
  RESEARCH_APIS("research_apis", Category.OTHER), //

  /**
   * The read_insights permission allows your app to read the Insights data for Pages, apps and web domains the person
   * owns.
   * <p>
   * <strong>Allowed Usage</strong>
   * <ul>
   * <li>Integrate Facebook's app, page or domain insights into your own analytics tools..</li>
   * </ul>
   */
  READ_INSIGHTS("read_insights", Category.OTHER), //

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
   * The gaming_user_locale permission allows your app to get a user's preferred language while the user plays a game on
   * Facebook (for example, Instant Games or Cloud Gaming).
   * <p>
   * <strong>Allowed Usage</strong>
   * <p>
   * Display a game interface in the user's preferred language.
   */
  GAMING_USER_LOCALE("gaming_user_locale", Category.EVENTS_GROUPS_PAGES), //

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
   * The instagram_manage_events permission allows an app permission to log events (e.g., purchase, add-to-cart, leads)
   * on behalf of Instagram accounts administered by the app’s users. The allowed usage for this permission is to log
   * events on Instagram accounts and send this activity data to Meta for ads targeting, optimization and reporting; and
   * to provide marketing and advertising analytics insights. You may also use this permission to request analytics
   * insights to improve your app and for marketing or advertising purposes, through the use of aggregated and
   * de-identified or anonymized information (provided such data cannot be re-identified).
   * <p>
   * <strong>Allowed Usage</strong>
   * <ul>
   * <li>Get metadata of an Instagram Business account.</li>
   * <li>Get data insights of an Instagram Business account.</li>
   * <li>Get story insights of an Instagram Business account.</li>
   * </ul>
   */
  INSTAGRAM_MANAGE_EVENTS("instagram_manage_events", Category.INSTAGRAM), //

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
   * The instagram_shopping_tag_products permission allows an app to tag Instagram media with product tags and appeal
   * product rejections.
   * <p>
   * <strong>Allowed Usage</strong>
   * <ul>
   * <li>Check eligibility for product tagging</li>
   * <li>Get catalogs and products</li>
   * <li>Tag media with product tags</li>
   * <li>Manage existing product tags</li>
   * <li>Appeal product rejections</li>
   * </ul>
   */
  INSTAGRAM_SHOPPING_TAG_PRODUCTS("instagram_shopping_tag_products", Category.INSTAGRAM), //

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
  INSTAGRAM_CONTENT_PUBLISH("instagram_content_publish", Category.INSTAGRAM), //

  /**
   * The instagram_branded_content_ads_brand permission allows an app to read Instagram posts where the app user's
   * Instagram account is tagged as a paid partner, and an app user to read, request, and revoke permissions to run
   * Partnership Ads.
   * <p>
   * <strong>Allowed Usage</strong>
   * <ul>
   * <li>Read Instagram posts where the account is tagged as a paid partner</li>
   * <li>Manage permissions to run Partnership Ads without needing a pre-existing post</li>
   * </ul>
   */
  INSTAGRAM_BRANDED_CONTENT_ADS_BRAND("instagram_branded_content_ads_brand", Category.INSTAGRAM), //

  /**
   * The instagram_branded_content_brand permission allows your app to add, remove and view creators from a specific
   * brand’s approved creators list. The allowed usage for this permission is to manage a specific brand’s Instagram
   * creator content settings. You may also use this permission to request analytics insights to improve your app and
   * for marketing or advertising purposes, through the use of aggregated and de-identified or anonymized information
   * (provided such data cannot be re-identified).
   * <p>
   * <strong>Allowed Usage</strong>
   * <ul>
   * <li>Manage branded creator content settings on a business’ Instagram account.</li>
   * </ul>
   */
  INSTAGRAM_BRANDED_CONTENT_BRAND("instagram_branded_content_brand", Category.INSTAGRAM), //

  /**
   * The instagram_branded_content_creator permission allows your app to read and change the boost status of a creator’s
   * specific piece of content. The allowed usage for this permission is to manage Instagram creator content settings.
   * You may also use this permission to request analytics insights to improve your app and for marketing or advertising
   * purposes, through the use of aggregated and de-identified or anonymized information (provided such data cannot be
   * re-identified).
   * <p>
   * <strong>Allowed Usage</strong>
   * <ul>
   * <li>Read and change an Instagram creator’s content settings.</li>
   * </ul>
   */
  INSTAGRAM_BRANDED_CONTENT_CREATOR("instagram_branded_content_creator", Category.INSTAGRAM), //

  /**
   * The instagram_graph_user_media permission allows your app to read the Media node, which represents an image, video,
   * or album and the node’s edges.
   * 
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  INSTAGRAM_GRAPH_USER_MEDIA("instagram_graph_user_media", Category.INSTAGRAM), //

  /**
   * The instagram_graph_user_profile permission allows your app to read the app user's profile.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  INSTAGRAM_GRAPH_USER_PROFILE("instagram_graph_user_profile", Category.INSTAGRAM), //

  INSTAGRAM_BUSINESS_BASIC("instagram_business_basic", Category.INSTAGRAM_BUSINESS), //

  INSTAGRAM_BUSINESS_MANAGE_COMMENTS("instagram_business_manage_comments", Category.INSTAGRAM_BUSINESS), //

  INSTAGRAM_BUSINESS_CONTENT_PUBLISH("instagram_business_content_publishing", Category.INSTAGRAM_BUSINESS), //

  INSTAGRAM_BUSINESS_MANAGE_MESSAGES("instagram_business_manage_messages", Category.INSTAGRAM_BUSINESS), //
  /**
   * The user_media permission allows your app to read the Media node, which represents an image, video, or album and
   * the node’s edges using the Instagram Basic Display API.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_MEDIA("user_media", Category.INSTAGRAM_BASIC_DISPLAY), //

  /**
   * The user_profile permission allows your app to read the app user's profile using the Instagram Basic Display API.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  USER_PROFILE("user_profile", Category.INSTAGRAM_BASIC_DISPLAY), //

  /**
   * Provides the ability to read and/or manage WhatsApp business assets you own or have been granted access to by other
   * businesses through this permission.
   * <p>
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
  WHATSAPP_BUSINESS_MANAGEMENT("whatsapp_business_management", Category.WHATSAPP),

  /**
   * The `whatsapp_business_messaging` permission allows an app to send WhatsApp messages to a specific phone number,
   * upload and retrieve media from messages, manage and get WhatsApp business profile information, and to register
   * those phone numbers with Meta.
   *
   * <p>
   * Please see
   * <a href= "https://developers.facebook.com/docs/permissions/reference/whatsapp_business_messaging">Whatsapps's
   * reference</a> for details.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  WHATSAPP_BUSINESS_MESSAGING("whatsapp_business_messaging", Category.WHATSAPP),

  /**
   * Required for making any calls to all Threads API endpoints
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  THREADS_BASIC("threads_basic", Category.THREADS),

  /**
   * Required for Threads publishing endpoints only
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  THREADS_CONTENT_PUBLISH("threads_content_publish", Category.THREADS),

  /**
   * Required for making GET calls to insights endpoints
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  THREADS_MANAGE_INSIGHTS("threads_manage_insights", Category.THREADS),

  /**
   * Required for making POST calls to reply endpoints
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  THREADS_MANAGE_REPLIES("threads_manage_replies", Category.THREADS),

  /**
   * Required for making GET calls to reply endpoints
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  THREADS_READ_REPLIES("threads_read_replies", Category.THREADS),

  /**
   * The commerce_account manage_orders permission allows your app to read and update commerce account orders.
   *
   * <p>
   * <strong>Allowed Usage</strong>
   * <ul>
   * <li>Read orders in your commerce account.</li>
   * <li>Tech providers manage orders on behalf of their customers.</li>
   * <li>Access to Webhook notifications.</li>
   * </ul>
   */
  COMMERCE_ACCOUNT_MANAGE_ORDERS("commerce_account_manage_orders", Category.COMMERCE),

  /**
   * The commerce_account_read_orders permission allows your app to read commerce account orders.
   * <p>
   * <strong>Allowed Usage</strong>
   * <ul>
   * <li>Read orders in your commerce account.</li>
   * <li>Use the buyer's email address for marketing purposes only if a buyer has opted-in at checkout.</li>
   * </ul>
   */
  COMMERCE_ACCOUNT_READ_ORDERS("commerce_account_read_orders", Category.COMMERCE),

  /**
   * The commerce_account_read_reports permission allows your app to read finance reporting data to build custom tax,
   * cash reconciliation and reimbursement reports for a commerce account.
   * <p>
   * <strong>Allowed Usage</strong>
   * <ul>
   * <li>Read finance reporting data in your commerce account for building custom tax cash reconciliation and
   * reimbursement reports.</li>
   * <li>Tech providers can run finance reports on behalf of their customers.</li>
   * </ul>
   */
  COMMERCE_ACCOUNT_READ_REPORTS("commerce_account_read_reports", Category.COMMERCE),

  /**
   * The commerce_account_read_settings permission allows your app to read commerce account settings. Allowed Usage
   * <ul>
   * <li>Read basic commerce account information like connected channels, shipping options, fulfillment locations, etc.
   * </ul>
   */
  COMMERCE_ACCOUNT_READ_SETTINGS("commerce_account_read_settings", Category.COMMERCE),

  /**
   * The commerce_manage_accounts permission allows your app to create and manage commerce accounts, such as an
   * ecommerce app
   * <p>
   * <strong>Allowed Usage</strong>
   * <ul>
   * <li>Associate your app with your commerce account.</li>
   * <li>Tech providers create a commerce account on behalf of their customers.</li>
   * <li>Tech providers enable a new sales channel within their customer’s commerce account.</li>
   * </ul>
   */
  COMMERCE_MANAGE_ACCOUNTS("commerce_manage_accounts", Category.COMMERCE),

  ;

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
    PUBLIC,
    USER_DATA,
    EVENTS_GROUPS_PAGES,
    OTHER, MESSAGING, INSTAGRAM, INSTAGRAM_BASIC_DISPLAY, LIVE_VIDEO, WHATSAPP, COMMERCE, INSTAGRAM_BUSINESS, THREADS
  }
}
