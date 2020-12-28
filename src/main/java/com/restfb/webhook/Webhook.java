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
package com.restfb.webhook;

import com.restfb.types.webhook.*;
import com.restfb.types.webhook.instagram.InstagramCommentsValue;
import com.restfb.types.webhook.instagram.InstagramMentionsValue;
import com.restfb.types.webhook.instagram.InstagramStoryInsightsValue;

public class Webhook {

  private WebhookMessagingListener messagingListener;

  private WebhookChangeListener eventListener;

  /**
   * registers an listener that handles the messaging Items of an incoming webhook request
   * 
   * @param listener
   */
  public void registerListener(WebhookMessagingListener listener) {
    this.messagingListener = listener;
  }

  /**
   * registers a listener that handles the change values of an incoming webhook request
   * 
   * @param listener
   */
  public void registerListener(WebhookChangeListener listener) {
    this.eventListener = listener;
  }

  public void process(WebhookObject webhookObject) {
    if (messagingListener != null) {
      handleMessaging(webhookObject);
    }
    if (eventListener != null) {
      handleEvents(webhookObject);
    }
  }

  private void handleEvents(WebhookObject webhookObject) {
    webhookObject.getEntryList().stream().flatMap(entry -> entry.getChanges().stream()).forEach(change -> {
      if (change.getValue() instanceof FeedCommentValue) {
        eventListener.feedCommentValue(change.getValue().convertChangeValue(FeedCommentValue.class));
      }

      if (change.getValue() instanceof FeedPhotoAddValue) {
        eventListener.feedPhotoAddValue(change.getValue().convertChangeValue(FeedPhotoAddValue.class));
      }

      if (change.getValue() instanceof FeedPhotoRemoveValue) {
        eventListener.feedPhotoRemoveValue(change.getValue().convertChangeValue(FeedPhotoRemoveValue.class));
      }

      if (change.getValue() instanceof FeedVideoValue) {
        eventListener.feedVideoValue(change.getValue().convertChangeValue(FeedVideoValue.class));
      }

      if (change.getValue() instanceof FeedVideoRemoveValue) {
        eventListener.feedVideoRemoveValue(change.getValue().convertChangeValue(FeedVideoRemoveValue.class));
      }

      if (change.getValue() instanceof FeedStatusValue) {
        eventListener.feedStatusValue(change.getValue().convertChangeValue(FeedStatusValue.class));
      }

      if (change.getValue() instanceof FeedPostValue) {
        eventListener.feedPostValue(change.getValue().convertChangeValue(FeedPostValue.class));
      }

      if (change.getValue() instanceof FeedVideoBlockMute) {
        eventListener.feedVideoBlockMute(change.getValue().convertChangeValue(FeedVideoBlockMute.class));
      }

      if (change.getValue() instanceof FeedReactionValue) {
        eventListener.feedReactionValue(change.getValue().convertChangeValue(FeedReactionValue.class));
      }

      if (change.getValue() instanceof FeedShareValue) {
        eventListener.feedShareValue(change.getValue().convertChangeValue(FeedShareValue.class));
      }

      if (change.getValue() instanceof FeedAlbumEditedValue) {
        eventListener.feedAlbumEditedValue(change.getValue().convertChangeValue(FeedAlbumEditedValue.class));
      }

      if (change.getValue() instanceof FeedEventValue) {
        eventListener.feedEventValue(change.getValue().convertChangeValue(FeedEventValue.class));
      }

      if (change.getValue() instanceof FeedLikeValue) {
        eventListener.feedLikeValue(change.getValue().convertChangeValue(FeedLikeValue.class));
      }

      if (change.getValue() instanceof FeedAlbumAddValue) {
        eventListener.feedAlbumAddValue(change.getValue().convertChangeValue(FeedAlbumAddValue.class));
      }

      if (change.getValue() instanceof RatingsRatingValue) {
        eventListener.ratingsRatingValue(change.getValue().convertChangeValue(RatingsRatingValue.class));
      }
      if (change.getValue() instanceof MentionPostAddValue) {
        eventListener.mentionPostAddValue(change.getValue().convertChangeValue(MentionPostAddValue.class));
      }

      if (change.getValue() instanceof RatingsCommentValue) {
        eventListener.ratingsCommentValue(change.getValue().convertChangeValue(RatingsCommentValue.class));
      }

      if (change.getValue() instanceof RatingsLikeValue) {
        eventListener.ratingsLikeValue(change.getValue().convertChangeValue(RatingsLikeValue.class));
      }

      if (change.getValue() instanceof RatingsReactionValue) {
        eventListener.ratingsReactionValue(change.getValue().convertChangeValue(RatingsReactionValue.class));
      }

      if (change.getValue() instanceof UserPageValue) {
        eventListener.userPageValue(change.getValue().convertChangeValue(UserPageValue.class));
      }

      if (change.getValue() instanceof PermissionChangeValue) {
        eventListener.permissionChangeValue(change.getValue().convertChangeValue(PermissionChangeValue.class));
      }

      if (change.getValue() instanceof PageLeadgen) {
        eventListener.pageLeadgen(change.getValue().convertChangeValue(PageLeadgen.class));
      }

      if (change.getValue() instanceof InstagramStoryInsightsValue) {
        eventListener
          .instagramStoryInsightsValue(change.getValue().convertChangeValue(InstagramStoryInsightsValue.class));
      }

      if (change.getValue() instanceof InstagramMentionsValue) {
        eventListener.instagramMentionsValue(change.getValue().convertChangeValue(InstagramMentionsValue.class));
      }

      if (change.getValue() instanceof InstagramCommentsValue) {
        eventListener.instagramCommentsValue(change.getValue().convertChangeValue(InstagramCommentsValue.class));
      }
    });
  }

  private void handleMessaging(WebhookObject webhookObject) {
    webhookObject.getEntryList().stream().flatMap(entry -> entry.getMessaging().stream()).forEach(messagingItem -> {
      if (messagingItem.isAccountLinking()) {
        messagingListener.accountLinking(messagingItem.getAccountLinking(), messagingItem.getRecipient(),
          messagingItem.getSender(), messagingItem.getTimestamp());
      }
      if (messagingItem.isAppRoles()) {
        messagingListener.appRoles(messagingItem.getAppRoles(), messagingItem.getRecipient(), messagingItem.getSender(),
          messagingItem.getTimestamp());
      }
      if (messagingItem.isCheckoutUpdate()) {
        messagingListener.checkoutUpdate(messagingItem.getCheckoutUpdate(), messagingItem.getRecipient(),
          messagingItem.getSender(), messagingItem.getTimestamp());
      }
      if (messagingItem.isDelivery()) {
        messagingListener.delivery(messagingItem.getDelivery(), messagingItem.getRecipient(), messagingItem.getSender(),
          messagingItem.getTimestamp());
      }
      if (messagingItem.isMessage()) {
        messagingListener.message(messagingItem.getMessage(), messagingItem.getRecipient(), messagingItem.getSender(),
          messagingItem.getTimestamp());
      }
      if (messagingItem.isOptin()) {
        messagingListener.optin(messagingItem.getOptin(), messagingItem.getRecipient(), messagingItem.getSender(),
          messagingItem.getTimestamp());
      }
      if (messagingItem.isPassThreadControl()) {
        messagingListener.passThreadControl(messagingItem.getPassThreadControl(), messagingItem.getRecipient(),
          messagingItem.getSender(), messagingItem.getTimestamp());
      }
      if (messagingItem.isPayment()) {
        messagingListener.payment(messagingItem.getPayment(), messagingItem.getRecipient(), messagingItem.getSender(),
          messagingItem.getTimestamp());
      }
      if (messagingItem.isPolicyEnforcement()) {
        messagingListener.policyEnforcement(messagingItem.getPolicyEnforcement(), messagingItem.getRecipient(),
          messagingItem.getSender(), messagingItem.getTimestamp());
      }
      if (messagingItem.isPostback()) {
        messagingListener.postback(messagingItem.getPostback(), messagingItem.getRecipient(), messagingItem.getSender(),
          messagingItem.getTimestamp());
      }
      if (messagingItem.isReaction()) {
        messagingListener.reaction(messagingItem.getReaction(), messagingItem.getRecipient(), messagingItem.getSender(),
          messagingItem.getTimestamp());
      }
      if (messagingItem.isRead()) {
        messagingListener.read(messagingItem.getRead(), messagingItem.getRecipient(), messagingItem.getSender(),
          messagingItem.getTimestamp());
      }
      if (messagingItem.isReferral()) {
        messagingListener.referral(messagingItem.getReferral(), messagingItem.getRecipient(), messagingItem.getSender(),
          messagingItem.getTimestamp());
      }
      if (messagingItem.isRequestThreadControl()) {
        messagingListener.requestThreadControl(messagingItem.getRequestThreadControl(), messagingItem.getRecipient(),
          messagingItem.getSender(), messagingItem.getTimestamp());
      }
      if (messagingItem.isTakeThreadControl()) {
        messagingListener.takeThreadControl(messagingItem.getTakeThreadControl(), messagingItem.getRecipient(),
          messagingItem.getSender(), messagingItem.getTimestamp());
      }
    });
  }
}
