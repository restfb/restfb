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
package com.restfb.types;

import java.util.Date;

import com.restfb.Facebook;
import com.restfb.types.features.HasCreatedTime;
import com.restfb.types.features.HasFrom;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/notification">Notification Graph API
 * type</a>.
 */
public class Notification extends FacebookType implements HasCreatedTime, HasFrom {

  /**
   * The entity (user, page, app, etc.) that 'sent', or is the source of the notification.
   *
   * @return The entity that 'sent', or is the source of the notification.
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook
  private NamedFacebookType from;

  /**
   * The entity that received the notification.
   *
   * @return The entity that received the notification.
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType to;

  /**
   * The URL that clicking on the notification would take someone.
   *
   * @return The URL that clicking on the notification would take someone.
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * The message text in the notification.
   *
   * @return The message text in the notification.
   */
  @Getter
  @Setter
  @Facebook
  private String title;

  /**
   * The app responsible for generating the notification. Some of the core Facebook features have their own app that
   * shows up here, such as likes when someone likes another person's content.
   *
   * @return The app responsible for generating the notification.
   */
  @Getter
  @Setter
  @Facebook
  private Application application;

  /**
   * Indicates that the notification is unread.
   * <p>
   * Note that 'read' notifications will not be accessible.
   *
   * @return Indicates that the notification is unread.
   */
  @Getter
  @Setter
  @Facebook
  private Integer unread;

  /**
   * The object (this can be a post, a photo, a comment, etc.) that was the subject of the notification.
   *
   * @return the object that was the subject of the notification.
   */
  @Getter
  @Setter
  @Facebook
  private NamedFacebookType object;

  /**
   * When the notification was last updated. When the notification was created.
   *
   * @return When the notification was last updated.
   */
  @Getter
  @Setter
  @Facebook("updated_time")
  private Date updatedTime;

  /**
   * When the notification was created.
   *
   * @return When the notification was created.
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook("created_time")
  private Date createdTime;

}
