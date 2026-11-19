/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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
package com.restfb.types.webhook.threads;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * A <a href="https://developers.facebook.com/documentation/threads/webhooks/">Threads webhook notification</a>. Unlike
 * Facebook and Instagram notifications, a Threads notification contains one {@code values} object, not
 * {@code entry[].changes[]}. Deserialize the received JSON with
 * {@code mapper.toJavaObject(payload, ThreadsWebhookObject.class)}; no API request or access token is needed. Receiving
 * and verifying the HTTP request remains the application's responsibility.
 */
public class ThreadsWebhookObject extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * Threads app ID configured in the App Dashboard.
   */
  @Getter
  @Setter
  @Facebook("app_id")
  private String appId;

  /**
   * Subscription topic, currently {@code moderate} or {@code interaction}.
   */
  @Getter
  @Setter
  @Facebook
  private String topic;

  /**
   * Media ID for replies and deletion events, or the mentioned user's app-scoped ID for mentions. This is not
   * universally an account ID; Meta does not explicitly define its meaning for publish events.
   */
  @Getter
  @Setter
  @Facebook("target_id")
  private String targetId;

  /**
   * Notification send time as Unix epoch seconds, retained without converting it to milliseconds.
   */
  @Getter
  @Setter
  @Facebook
  private Long time;

  /**
   * Subscription ID for the user in the notification.
   */
  @Getter
  @Setter
  @Facebook("subscription_id")
  private String subscriptionId;

  /**
   * Flag supplied in Meta's notification examples. {@code null} means that the field was absent or explicitly null.
   */
  @Getter
  @Setter
  @Facebook("has_uid_field")
  private Boolean hasUidField;

  /**
   * The single change in this notification. Its event field determines the value type.
   */
  @Getter
  @Setter
  @Facebook
  private ThreadsWebhookChange values;
}
