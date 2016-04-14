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
package com.restfb.scope;

public enum ExtendedPermissions implements FacebookPermissions {

  ADS_MANAGEMENT("ads_management"), //
  ADS_READ("ads_read"), //

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
  EMAIL("email"), //

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
  @Deprecated MANAGE_NOTIFICATIONS("manage_notifications"), //

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
  MANAGE_PAGES("manage_pages"), //

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
  PAGES_MANAGE_CTA("pages_manage_cta"), //

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
  PAGES_MANAGE_INSTANT_ARTICLES("pages_manage_instant_articles"), //

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
  PAGES_MANAGE_LEADS("pages_manage_leads"), //

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
  PAGES_MESSAGING("pages_messaging"), //

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
  PAGES_MESSAGING_PHONE_NUMBER("pages_messaging_phone_number"), //

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
  PAGES_SHOW_LIST("pages_show_list"), //

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
  PUBLISH_ACTIONS("publish_actions"), //

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
  PUBLISH_PAGES("publish_pages"), //

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
  READ_AUDIENCE_NETWORK_INSIGHTS("read_audience_network_insights"), //

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
  @Deprecated READ_FRIENDLISTS("read_friendlists"), //

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
   */
  READ_CUSTOM_FRIENDLISTS("read_custom_friendlists"), //

  /**
   * Provides read-only access to the Insights data for Pages, Apps and web domains the person owns.
   *
   * <p>
   * <strong>Review</strong>
   *
   * <p>
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  READ_INSIGHTS("read_insights"), //

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
  XMPP_LOGIN("xmpp_login"), //

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
  @Deprecated READ_MAILBOX("read_mailbox"), //

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
  READ_PAGE_MAILBOXES("read_page_mailboxes"), //

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
  @Deprecated READ_STREAM("read_stream"), //

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
  RSVP_EVENT("rsvp_event");

  String permissionString;

  ExtendedPermissions(String facebookPermissionString) {
    this.permissionString = facebookPermissionString;
  }

  @Override
  public String getPermissionString() {
    return this.permissionString;
  }

}
