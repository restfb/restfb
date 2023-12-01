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
package com.restfb.types.webhook.messaging;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a receiving postback object as defined here:
 * https://developers.facebook.com/docs/messenger-platform/webhook-reference/postback
 */
@ToString
public class PostbackItem implements InnerMessagingItem {

  /**
   * Message ID
   */
  @Getter
  @Setter
  @Facebook
  private String mid;

  /**
   * Title for the CTA that was clicked on. This is sent to all apps subscribed to the page.
   *
   * For apps other than the original CTA sender, the postback event will be delivered via the
   * <a href="https://developers.facebook.com/docs/messenger-platform/webhook-reference/standby-channel"
   * target="_self">standby channel</a>.
   */
  @Getter
  @Setter
  @Facebook
  private String title;

  /**
   * payload parameter that was defined with the button
   */
  @Getter
  @Setter
  @Facebook
  private String payload;

  /**
   * Comes only with Get Started postback and if an optional ref param was passed from the entry point, such as m.me
   * link.
   */
  @Getter
  @Setter
  @Facebook
  private PostbackReferral referral;
}
