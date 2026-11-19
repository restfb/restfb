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
package com.restfb.webhook;

import com.restfb.types.webhook.ChangeValue;
import com.restfb.types.webhook.threads.ThreadsMediaValue;
import com.restfb.types.webhook.threads.ThreadsWebhookObject;

/**
 * Optional callbacks for <a href="https://developers.facebook.com/documentation/threads/webhooks/">Threads webhook
 * notifications</a>, dispatched by {@link Webhook#process(ThreadsWebhookObject)}. Register an implementation with
 * {@link Webhook#registerListener(ThreadsWebhookChangeListener)} and override only the events of interest. Each
 * callback receives the original notification so that topic, target ID, subscription ID, and notification time remain
 * available. Default implementations ignore the event.
 */
public interface ThreadsWebhookChangeListener {

  /**
   * A reply to a post owned by the authenticated user.
   *
   * @param notification
   *          the complete notification, including its single change
   * @param value
   *          reply media and webhook references
   */
  default void replies(ThreadsWebhookObject notification, ThreadsMediaValue value) {}

  /**
   * A public post mentioning the authenticated user. Reply metadata can be absent without {@code threads_read_replies}
   * permission.
   *
   * @param notification
   *          the complete notification
   * @param value
   *          mentioning media and optional webhook references
   */
  default void mentions(ThreadsWebhookObject notification, ThreadsMediaValue value) {}

  /**
   * A post deleted by the authenticated user.
   *
   * @param notification
   *          the complete notification
   * @param value
   *          deleted media, its owner, and deletion timestamp
   */
  default void delete(ThreadsWebhookObject notification, ThreadsMediaValue value) {}

  /**
   * A post or reply published by the authenticated user.
   *
   * @param notification
   *          the complete notification
   * @param value
   *          published media
   */
  default void publish(ThreadsWebhookObject notification, ThreadsMediaValue value) {}

  /**
   * An unrecognized event field or a value that does not have the known media shape. Unknown object values retain their
   * JSON in {@link com.restfb.types.webhook.FallBackChangeValue}.
   *
   * @param notification
   *          the complete notification, including the unrecognized field
   * @param value
   *          the mapped fallback, string, or list value
   */
  default void unknown(ThreadsWebhookObject notification, ChangeValue value) {}
}
