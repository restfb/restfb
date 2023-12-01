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
package com.restfb.types.send;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.exception.FacebookPreconditionException;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class Message extends AbstractFacebookType {

  private static final int QUICK_REPLY_ITEM_LIMIT = 13;

  @Getter
  @Facebook
  private String text;

  @Facebook("quick_replies")
  private List<QuickReply> quickReplies;

  @Getter
  @Setter
  @Facebook
  private String metadata;

  @Getter
  @Facebook
  private MessageAttachment attachment;

  public Message(String text) {
    this.text = text;
  }

  public Message(MessageAttachment attachment) {
    this.attachment = attachment;
  }

  /**
   * add single {@code QuickReply} to the list of QuickReplies
   * 
   * @param quickReply
   *          the QuickReply you like to add
   * @return
   */
  public boolean addQuickReply(QuickReply quickReply) {
    if (quickReplies == null) {
      quickReplies = new ArrayList<>();
    }

    checkPrecondition(1);

    return quickReplies.add(quickReply);
  }

  /**
   * add list of {@code QuickReply} objects to the list of QuickReplies
   * 
   * @param quickReplyList
   *          the list QuickReplies you like to add
   * @return
   */
  public boolean addQuickReplies(List<QuickReply> quickReplyList) {
    if (quickReplies == null) {
      quickReplies = new ArrayList<>();
    }

    checkPrecondition(quickReplyList.size());

    return quickReplies.addAll(quickReplyList);
  }

  public List<QuickReply> getQuickReplies() {
    if (quickReplies == null) {
      return Collections.emptyList();
    }
    return Collections.unmodifiableList(quickReplies);
  }

  private void checkPrecondition(int addingRepliesSize) {
    if (quickReplies.size() + addingRepliesSize > QUICK_REPLY_ITEM_LIMIT) {
      String message = "You cannot have more than " + QUICK_REPLY_ITEM_LIMIT
          + " replies in one message, current size/try adding: " + quickReplies.size() + "/" + addingRepliesSize;
      throw new FacebookPreconditionException(message);
    }
  }
}
