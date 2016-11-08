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
package com.restfb.types.webhook.messaging;

import com.restfb.Facebook;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the
 * <a href=" https://developers.facebook.com/docs/messenger-platform/webhook-reference/message-received">Message</a>
 * Callback
 */
@ToString
public class MessageItem implements InnerMessagingItem {

  /**
   * Message ID
   */
  @Getter
  @Setter
  @Facebook
  private String mid;

  /**
   * Message sequence number
   *
   * @Deprecated https://messengerblog.com/platform-updates/introducing-messenger-platform-v1-3-and-new-ways-to-drive-the-conversation/
   *             "Developers who previously relied on using seq ID for deduping can use mid instead, and those who were
   *             using it for ordering purposes can use timestamp instead."
   */
  @Deprecated
  @Getter
  @Setter
  @Facebook
  private Long seq;

  /**
   * Text of message
   */
  @Getter
  @Setter
  @Facebook
  private String text;

  /**
   * Indicates the message sent from the page itself
   */
  @Getter
  @Setter
  @Facebook("is_echo")
  private boolean isEcho;

  /**
   * ID of the app from which the message was sent
   */
  @Getter
  @Setter
  @Facebook("app_id")
  private String appId;

  /**
   * Custom string passed to the Send API as the metadata field
   */
  @Getter
  @Setter
  @Facebook
  private String metadata;

  @Getter
  @Setter
  @Facebook("quick_reply")
  private QuickReplyItem quickReply;

  @Getter
  @Setter
  @Facebook("sticker_id")
  private String stickerId;

  /**
   * Array containing attachment data
   */
  @Getter
  @Setter
  @Facebook
  private List<MessagingAttachment> attachments = new ArrayList<MessagingAttachment>();
}
