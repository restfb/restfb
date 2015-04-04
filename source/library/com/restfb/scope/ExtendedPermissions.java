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

public enum ExtendedPermissions implements FacebookPermissions {

  ADS_MANAGEMENT("ads_management"), //
  ADS_READ("ads_read"), //

  /**
   * Provides access to the person's primary email address via the email property on the user object.
   * 
   * Do not spam users. Your use of email must comply with both Facebook policies and with the CAN-SPAM Act.
   * 
   * Note, even if you request the email permission it is not guaranteed you will get an email address. For example, if
   * someone signed up for Facebook with a phone number instead of an email address, the email field may be empty.
   * 
   * <strong>Review</strong>
   * 
   * Your app may use this permission without review from Facebook.
   */
  EMAIL("email"), //

  /**
   * Enables your app to read a person's notifications and mark them as read.
   * 
   * This permission does not let you send notifications to a person.
   * 
   * <strong>Review</strong>
   *
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  MANAGE_NOTIFICATIONS("manage_notifications"), //

  /**
   * Enables your app to retrieve Page Access Tokens for the Pages and Apps that the person administrates.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it. You can grant this
   * permission on behalf of people listed within the Roles section of your App's Dashboard without review by Facebook.
   */
  MANAGE_PAGES("manage_pages"), //

  /**
   * Provides access to publish Posts, Open Graph actions, achievements, scores and other activity on behalf of a person
   * using your app.
   * 
   * Because this permission lets you publish on behalf of a user please read the Platform Policies to ensure you
   * understand how to properly use this permission.
   * 
   * Your app does not need to request the publish_actions permission in order to use the Feed Dialog, the Requests
   * Dialog or the Send Dialog
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  PUBLISH_ACTIONS("publish_actions"), //
  
  /**
   * Gives your app the ability to post, comment and like as any of the Pages managed by a person using your app. 
   * 
   * Publishing as an individual personal account is not possible with this permission. To post 
   * as an individual, please see the <code>publish_actions</code> permission.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   * 
   * since Graph API 2.3
   */
  PUBLISH_PAGES("publish_pages"), //

  /**
   * Provides access to the names of custom lists a person has created to organize their friends. This is useful for
   * rendering an audience selector when someone is publishing stories to Facebook from your app.
   * 
   * This permission does not give access to a list of person's friends. If you want to access a person's friends who
   * also use your app, you should use the user_friends permission.
   * 
   * This permission will also not help you invite a person's friends to use your app.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   * 
   * @deprecated in Graph API 2.3 use READ_CUSTOM_FRIENDLISTS instead. If you use the Graph API before 2.3 ignore the deprecation warning
   */
  @Deprecated
  READ_FRIENDLISTS("read_friendlists"), //
  
  /**
   * Provides access to the names of custom lists a person has created to organize their friends. 
   * 
   * This is useful for rendering an audience selector when someone is publishing stories to Facebook from your app.
   * 
   * This permission does not give access to a list of person's friends. If you want to access a person's friends who
   * also use your app, you should use the user_friends permission.
   * 
   * This permission will also not help you invite a person's friends to use your app.
   * 
   * This permission was called read_friendlists before v2.3.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  READ_CUSTOM_FRIENDLISTS("read_custom_friendlists"), //

  /**
   * Provides read-only access to the Insights data for Pages, Apps and web domains the person owns.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  READ_INSIGHTS("read_insights"), //

  /**
   * Provides the ability to read the messages in a person's Facebook Inbox through the inbox edge and the thread node.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  READ_MAILBOX("read_mailbox"), //

  /**
   * Provides the ability to read from the Page Inboxes of the Pages managed by a person. This permission is often used
   * alongside the manage_pages permission.
   * 
   * This permission does not let your app read the page owner's mailbox. It only applies to the page's mailbox.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  READ_PAGE_MAILBOXES("read_page_mailboxes"), //

  /**
   * Provides access to read the posts in a person's News Feed, or the posts on their Profile.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  READ_STREAM("read_stream"), //

  /**
   * Provides the ability to set a person's attendee status on Facebook Events (eg attending, maybe, or declined).
   * 
   * This permission does not let you invite people to an event.
   * 
   * This permission does not let you update an event's details.
   * 
   * This permission does not let you create an event. There is no way to create an event via the API as of Graph API
   * v2.0.
   * 
   * <strong>Review</strong>
   * 
   * If your app requests this permission Facebook will have to review how your app uses it.
   */
  RSVP_EVENT("rsvp_event");

  ExtendedPermissions(String facebookPermissionString) {
    this.permissionString = facebookPermissionString;
  }

  String permissionString;

  @Override
  public String getPermissionString() {
    return this.permissionString;
  }

}
