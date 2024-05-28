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

import static com.restfb.testutils.RestfbAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.messaging.*;
import com.restfb.types.webhook.messaging.airline.PassengerInfoItem;
import com.restfb.types.webhook.messaging.airline.PassengerSegmentInfoItem;
import com.restfb.webhook.AbstractWebhookMessagingListener;
import com.restfb.webhook.Webhook;

class WebhookMessagingTest extends AbstractJsonMapperTests {

  private final Webhook webhookListener = new Webhook();

  @Test
  void messagingDelivery() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-delivery-basic"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isDelivery());
    DeliveryItem item = messagingItem.getDelivery();
    assertNotNull(item);
    assertFalse(item.getMids().isEmpty());
    assertEquals("1458668856253", item.getWatermark());
    assertEquals(37L, item.getSeq().longValue());
    AtomicBoolean found = new AtomicBoolean(false);

    webhookListener.registerListener(new AbstractWebhookMessagingListener() {
      @Override
      public void delivery(DeliveryItem delivery, MessagingParticipant recipient, MessagingParticipant sender,
          Date timestamp) {
        assertNotNull(delivery);
        assertEquals("1458668856253", delivery.getWatermark());
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void messagingRead() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-read-basic"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertTrue(webhookObject.isPage());
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isRead());
    ReadItem item = messagingItem.getRead();
    assertNotNull(item);
    assertEquals("1458668856253", item.getWatermark());
    assertEquals(38L, item.getSeq().longValue());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void read(ReadItem read, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp) {
        assertNotNull(read);
        assertEquals("1458668856253", read.getWatermark());
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void messagingMessageMediaAttachment() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-media-attachment"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    MessageItem item = messagingItem.getMessage();
    assertNotNull(item);
    assertEquals("mid.1458696618141:b4ef9d19ec21086067", item.getMid());
    assertNull(item.getText());
    assertFalse(item.hasQuickReply());
    assertFalse(item.hasNlp());
    assertTrue(item.hasAttachment());
    assertFalse(item.getAttachments().isEmpty());
    MessagingAttachment attachment = item.getAttachments().get(0);
    assertEquals("image", attachment.getType());
    assertTrue(attachment.isImage());
    assertEquals("IMAGE_URL", attachment.getPayload().getUrl());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void message(MessageItem message, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp) {
        assertNotNull(message);
        assertEquals("mid.1458696618141:b4ef9d19ec21086067", message.getMid());
        assertNull(message.getText());
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void messagingMessageLocationAttachment() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-location-attachment"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    MessageItem item = messagingItem.getMessage();
    assertNotNull(item);
    assertEquals("mid.1458696618141:b4ef9d19ec21086067", item.getMid());
    assertNull(item.getText());
    assertFalse(item.hasQuickReply());
    assertFalse(item.hasNlp());
    assertTrue(item.hasAttachment());
    assertFalse(item.getAttachments().isEmpty());
    MessagingAttachment attachment = item.getAttachments().get(0);
    assertEquals("location", attachment.getType());
    assertTrue(attachment.isLocation());
    assertEquals(12.34, attachment.getPayload().getCoordinates().getLat().doubleValue());
    assertEquals(43.21, attachment.getPayload().getCoordinates().getLongVal().doubleValue());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void message(MessageItem message, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp) {
        assertNotNull(message);
        assertEquals("mid.1458696618141:b4ef9d19ec21086067", message.getMid());
        assertTrue(message.hasAttachment());
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void messagingMessageLegacyFallbackAttachment() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-fallback-attachment"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    MessageItem item = messagingItem.getMessage();
    assertNotNull(item);
    assertEquals("mid.1458696618141:b4ef9d19ec21086067", item.getMid());
    assertNull(item.getText());
    assertFalse(item.hasQuickReply());
    assertFalse(item.hasNlp());
    assertTrue(item.hasAttachment());
    assertFalse(item.getAttachments().isEmpty());
    MessagingAttachment attachment = item.getAttachments().get(0);
    assertEquals("fallback", attachment.getType());
    assertTrue(attachment.isFallback());
    assertEquals("Legacy Attachment", attachment.getTitle());
    assertEquals("BLOB_BLOB_BLOB", attachment.getPayload().getFallback());
  }

  @Test
  void messagingMessageBasic() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-basic"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    assertFalse(messagingItem.hasPriorMessage());
    MessageItem item = messagingItem.getMessage();
    assertEquals(item, messagingItem.getItem());
    assertFalse(item.hasQuickReply());
    assertFalse(item.hasNlp());
    assertFalse(item.hasAttachment());
    assertFalse(item.isEcho());
    assertNotNull(item);
    assertEquals("mid.1457764197618:41d102a3e1ae206a38", item.getMid());
    assertEquals("hello, world!", item.getText());
    assertTrue(item.getAttachments().isEmpty());
  }

  @Test
  void messagingMessageIsDeleted() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-isdeleted"), WebhookObject.class);
    assertNotNull(webhookObject);
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    MessageItem item = messagingItem.getMessage();
    assertTrue(item.getIsDeleted());
  }

  @Test
  void messagingMessageIsUnsupported() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-isunsupported"), WebhookObject.class);
    assertNotNull(webhookObject);
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    MessageItem item = messagingItem.getMessage();
    assertTrue(item.getIsUnsupported());
  }

  @Test
  void messagingMessageReply() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-reply-1"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    MessageItem item = messagingItem.getMessage();
    assertTrue(item.isReply());
    assertEquals("very_long_id_representing_the_original_message", item.getReplyTo().getMid());
  }

  @Test
  void messagingMessageReplyStory() {
    WebhookObject webhookObject =
            createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-reply-2"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    MessageItem item = messagingItem.getMessage();
    assertTrue(item.isReply());
    assertNotNull(item.getReplyTo().getStory());
    ReplyTo.Story story = item.getReplyTo().getStory();
    assertEquals("EXAMPLE_URL", story.getUrl());
    assertEquals("STORY_ID", story.getId());
  }

  @Test
  void messagingReactionREACT() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-reaction-1"), WebhookObject.class);
    assertNotNull(webhookObject);
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isReaction());
    MessageReaction reaction = (MessageReaction) messagingItem.getItem();
    assertEquals("original_message_id", reaction.getMid());
    assertEquals(MessageReaction.Action.REACT, reaction.getAction());
    assertEquals("\uD83D\uDE22", reaction.getEmoji());
    assertEquals("sad", reaction.getReaction());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void reaction(MessageReaction reaction, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp) {
        assertNotNull(reaction);
        assertEquals("original_message_id", reaction.getMid());
        assertEquals("sad", reaction.getReaction());
        found.set(true);
      }

    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void messagingReactionUNREACT() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-reaction-2"), WebhookObject.class);
    assertNotNull(webhookObject);
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isReaction());
    MessageReaction reaction = (MessageReaction) messagingItem.getItem();
    assertEquals("original_message_id", reaction.getMid());
    assertEquals(MessageReaction.Action.UNREACT, reaction.getAction());
  }

  @Test
  void messagingReactionPrior() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-prior"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    assertNotNull(messagingItem.getPriorMessage());
    assertTrue(messagingItem.hasPriorMessage());
    assertEquals("checkbox_plugin", messagingItem.getPriorMessage().getSource());
    assertEquals("<USER_REF>", messagingItem.getPriorMessage().getIdentifier());
    MessageItem item = messagingItem.getMessage();
    assertEquals(item, messagingItem.getItem());
    assertFalse(item.hasQuickReply());
    assertFalse(item.hasNlp());
    assertFalse(item.hasAttachment());
    assertFalse(item.isEcho());
    assertNotNull(item);
    assertEquals("mid.1457744567618:41d102a3e1ae206a38", item.getMid());
    assertEquals("Thanks for messaging me!", item.getText());
    assertTrue(item.getAttachments().isEmpty());

  }

  @Test
  void messagingAppRoles() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-approles"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertNotNull(messagingItem.getAppRoles());
    assertTrue(messagingItem.isAppRoles());
    AppRoles roles = messagingItem.getAppRoles();
    assertFalse(roles.getAppIds().isEmpty());
    assertEquals("123456789", roles.getAppIds().iterator().next());
    assertFalse(roles.getRoles("123456789").isEmpty());
    assertEquals("primary_receiver", roles.getRoles("123456789").get(0));
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void appRoles(AppRoles appRoles, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp) {
        assertNotNull(appRoles);
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void messagingReferralBasic() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-referral-basic"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isReferral());
    ReferralItem referral = messagingItem.getReferral();
    assertEquals(referral, messagingItem.getItem());
    assertNotNull(referral);
    assertEquals("ref_data_in_m_dot_me_param", referral.getRef());
    assertEquals("SHORTLINK", referral.getSource());
    assertEquals("OPEN_THREAD", referral.getType());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void referral(ReferralItem referral, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp) {
        assertNotNull(referral);
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void messagingMessageBasicIsEcho() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-basic-echo"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    assertFalse(entry.hasStandby());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    MessageItem item = messagingItem.getMessage();
    assertTrue(item.isEcho());
    assertNotNull(item);
    assertEquals("mid.1457764197618:41d102a3e1ae206a38", item.getMid());
    assertEquals("hello, world!", item.getText());
    assertEquals("1517776481860111", item.getAppId());
    assertEquals("DEVELOPER_DEFINED_METADATA_STRING", item.getMetadata());
    assertTrue(item.getAttachments().isEmpty());
  }

  @Test
  void messagingMessageEdits() {
    WebhookObject webhookObject = createJsonMapper()
            .toJavaObject(jsonFromClasspath("webhooks/messaging-message-edits"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    assertFalse(entry.hasStandby());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    MessageEditItem item = messagingItem.getMessageEdit();
    assertNotNull(item);
    assertEquals("mid.1457764197618:41d102a3e1ae206a38", item.getMid());
    assertEquals("hello, world!", item.getText());
    assertEquals(37, item.getNumEdit());
  }

  @Test
  void standbyMessageBasicIsEcho() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/standby-message-basic-echo"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertTrue(entry.getMessaging().isEmpty());
    assertFalse(entry.getStandby().isEmpty());
    assertTrue(entry.hasStandby());
    MessagingItem messagingItem = entry.getStandby().get(0);
    assertTrue(messagingItem.isMessage());
    MessageItem item = messagingItem.getMessage();
    assertTrue(item.isEcho());
    assertNotNull(item);
    assertEquals("mid.1457764197618:41d102a3e1ae206a38", item.getMid());
    assertEquals("hello, world!", item.getText());
    assertEquals("1517776481860111", item.getAppId());
    assertEquals("DEVELOPER_DEFINED_METADATA_STRING", item.getMetadata());
    assertTrue(item.getAttachments().isEmpty());
  }

  @Test
  void messagingOptinBasic() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-optin-basic"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isOptin());
    OptinItem item = messagingItem.getOptin();
    assertEquals(item, messagingItem.getItem());
    assertNotNull(item);
    assertEquals("PASS_THROUGH_PARAM", item.getRef());
    assertNull(item.getUserRef());
    assertNull(item.getUserRefMessageRecipient());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void optin(OptinItem optin, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp) {
        assertNotNull(optin);
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void messagingOptinUserRef() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-optin-userref"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isOptin());
    OptinItem item = messagingItem.getOptin();
    assertEquals(item, messagingItem.getItem());
    assertNotNull(item);
    assertEquals("PASS_THROUGH_PARAM", item.getRef());
    assertEquals("UNIQUE_REF_PARAM", item.getUserRef());
    assertNotNull(item.getUserRefMessageRecipient());
  }

  @Test
  void messagingPolicyCallbackBlock() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-policy-callback-block"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isPolicyEnforcement());
    PolicyEnforcementItem item = messagingItem.getPolicyEnforcement();
    assertEquals(item, messagingItem.getItem());
    assertNotNull(item);
    assertEquals("block", item.getAction());
    assertTrue(item.isBlock());
    assertFalse(item.isUnblock());
    assertNotNull(item.getReason());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void policyEnforcement(PolicyEnforcementItem policyEnforcement, MessagingParticipant recipient,
          MessagingParticipant sender, Date timestamp) {
        assertNotNull(policyEnforcement);
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void messagingPolicyCallbackUnblock() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-policy-callback-unblock"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    PolicyEnforcementItem item = messagingItem.getPolicyEnforcement();
    assertEquals(item, messagingItem.getItem());
    assertNotNull(item);
    assertEquals("unblock", item.getAction());
    assertFalse(item.isBlock());
    assertTrue(item.isUnblock());
    assertNotNull(item.getReason());
  }

  @Test
  void messagingPostbackBasic() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-postback-basic"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isPostback());
    PostbackItem item = messagingItem.getPostback();
    assertEquals(item, messagingItem.getItem());
    assertNotNull(item);
    assertEquals("USER_DEFINED_PAYLOAD", item.getPayload());
    assertNull(item.getReferral());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void postback(PostbackItem postback, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp) {
        assertNotNull(postback);
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void messagingPostbackWithReferral() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-postback-referral"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isPostback());
    PostbackItem item = messagingItem.getPostback();
    assertEquals(item, messagingItem.getItem());
    assertNotNull(item);
    assertEquals("USER_DEFINED_PAYLOAD", item.getPayload());
    PostbackReferral referral = item.getReferral();
    assertEquals("ref_data_in_m_dot_me_param", referral.getRef());
    assertEquals("SHORTLINK", referral.getSource());
    assertEquals("OPEN_THREAD", referral.getType());
  }

  @Test
  void messagingPostbackFromChat() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-postback-chat"), WebhookObject.class);
    assertThat(webhookObject).isNotNull();
    assertThat(webhookObject.getEntryList()).isNotEmpty();
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertThat(entry.getMessaging()).isNotEmpty();
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertThat(messagingItem.getSender().isUserRef()).isTrue();
    assertThat(messagingItem.getSender().getUserRef()).isEqualTo("987654321432");
    assertThat(messagingItem.getRecipient().isUserRef()).isFalse();
    assertThat(messagingItem.isPostback()).isTrue();
    PostbackItem item = messagingItem.getPostback();
    assertThat(item).isNotNull();
    PostbackReferral referral = item.getReferral();
    assertThat(referral.getSource()).isEqualTo("CUSTOMER_CHAT_PLUGIN");
    assertThat(referral.getType()).isEqualTo("OPEN_THREAD");
    assertThat(referral.getRefererUri()).isEqualTo("https://www.xyz.com/abcd");
    assertThat(referral.getIsGuestUser()).isFalse();
  }

  @Test
  void messagingAccountLinkingLinked() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-accountlinking-linked"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isAccountLinking());
    AccountLinkingItem item = messagingItem.getAccountLinking();
    assertEquals(item, messagingItem.getItem());
    assertNotNull(item);
    assertTrue(item.isLinked());
    assertFalse(item.isUnlinked());
    assertEquals("linked", item.getStatus());
    assertEquals("PASS_THROUGH_AUTHORIZATION_CODE", item.getAuthorizationCode());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void accountLinking(AccountLinkingItem item, MessagingParticipant recipient, MessagingParticipant sender, Date timestamp) {
        assertNotNull(item);
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void messagingAccountLinkingUnlinked() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-accountlinking-unlinked"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isAccountLinking());
    AccountLinkingItem item = messagingItem.getAccountLinking();
    assertEquals(item, messagingItem.getItem());
    assertNotNull(item);
    assertFalse(item.isLinked());
    assertTrue(item.isUnlinked());
    assertEquals("unlinked", item.getStatus());
  }

  @Test
  void messagingButtonTemplate() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-button-template"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    MessageItem item = messagingItem.getMessage();
    assertFalse(item.getAttachments().isEmpty());
    MessagingAttachment attachment = item.getAttachments().get(0);
    assertEquals("template", attachment.getType());
    assertEquals("button", attachment.getPayload().getTemplateType());
    ButtonItem button = attachment.getPayload().getButtons().get(0);
    assertEquals("web_url", button.getType());
    assertEquals("BUTTON_URL", button.getUrl());
    assertEquals("Visit Messenger", button.getTitle());
  }

  @Test
  void messagingAirlineItineraryTemplate() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-airline-itinerary-template"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    assertNotNull(messagingItem.getTimestamp());
    assertEquals(1458692752478L, messagingItem.getTimestamp().getTime());
    MessageItem item = messagingItem.getMessage();
    assertFalse(item.getAttachments().isEmpty());
    MessagingAttachment attachment = item.getAttachments().get(0);
    assertEquals("template", attachment.getType());
    assertEquals("airline_itinerary", attachment.getPayload().getTemplateType());
    assertEquals("Here's your flight itinerary.", attachment.getPayload().getIntroMessage());
    assertEquals("en_US", attachment.getPayload().getLocale());
    assertNull(attachment.getPayload().getThemeColor());
    assertEquals("ABCDEF", attachment.getPayload().getPnrNumber());
    assertEquals(12206d, attachment.getPayload().getBasePrice().doubleValue());
    assertEquals(200d, attachment.getPayload().getTax().doubleValue());
    assertEquals(14003d, attachment.getPayload().getTotalPrice().doubleValue());

    PassengerInfoItem passengerInfoItem = attachment.getPayload().getPassengerInfoItems().get(0);
    assertEquals("Farbound Smith Jr", passengerInfoItem.getName());
    assertEquals("p001", passengerInfoItem.getPassengerId());
    assertEquals("0741234567890", passengerInfoItem.getTicketNumber());

    assertEquals("c001", attachment.getPayload().getFlightInfoItems().get(0).getConnectionId());
    assertEquals("SFO", attachment.getPayload().getFlightInfoItems().get(0).getDepartureAirport().getAirportCode());
    assertEquals(1451763900000L,
      attachment.getPayload().getFlightInfoItems().get(0).getFlightSchedule().getDepartureTime().getTime());
    PassengerSegmentInfoItem passengerSegmentInfoItem = attachment.getPayload().getPassengerSegmentInfoItems().get(0);
    assertEquals("12A", passengerSegmentInfoItem.getSeat());
    assertEquals("Business", passengerSegmentInfoItem.getSeatType());
    assertEquals("USD", attachment.getPayload().getPriceInfoItems().get(0).getCurrency());
  }

  @Test
  void passThreadControl() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-pass-thread-control"), WebhookObject.class);
    assertNotNull(webhookObject);
    MessagingItem item = webhookObject.getEntryList().get(0).getMessaging().get(0);
    assertNotNull(item);
    assertFalse(item.isTakeThreadControl());
    assertTrue(item.isPassThreadControl());
    assertEquals("USER_ID", item.getSender().getId());
    assertEquals("PAGE_ID", item.getRecipient().getId());
    PassThreadControlItem passThreadControlItem = (PassThreadControlItem) item.getItem();
    assertNotNull(passThreadControlItem);
    assertEquals("NEW_OWNER_APP_ID", passThreadControlItem.getNewOwnerAppId());
    assertEquals("METADATA", passThreadControlItem.getMetadata());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void passThreadControl(PassThreadControlItem passThreadControl, MessagingParticipant recipient,
          MessagingParticipant sender, Date timestamp) {
        assertNotNull(passThreadControl);
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void takeThreadControl() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-take-thread-control"), WebhookObject.class);
    assertNotNull(webhookObject);
    MessagingItem item = webhookObject.getEntryList().get(0).getMessaging().get(0);
    assertNotNull(item);
    assertTrue(item.isTakeThreadControl());
    assertFalse(item.isPassThreadControl());
    assertEquals("USER_ID", item.getSender().getId());
    assertEquals("PAGE_ID", item.getRecipient().getId());
    TakeThreadControlItem takeThreadControlItem = (TakeThreadControlItem) item.getItem();
    assertNotNull(takeThreadControlItem);
    assertEquals("PREVIOUS_OWNER_APP_ID", takeThreadControlItem.getPreviousOwnerAppId());
    assertEquals("METADATA", takeThreadControlItem.getMetadata());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void takeThreadControl(TakeThreadControlItem takeThreadControl, MessagingParticipant recipient,
          MessagingParticipant sender, Date timestamp) {
        assertNotNull(takeThreadControl);
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void requestThreadControl() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-request-thread-control"), WebhookObject.class);
    assertNotNull(webhookObject);
    MessagingItem item = webhookObject.getEntryList().get(0).getMessaging().get(0);
    assertNotNull(item);
    assertFalse(item.isTakeThreadControl());
    assertFalse(item.isPassThreadControl());
    assertTrue(item.isRequestThreadControl());
    assertEquals("USER_ID", item.getSender().getId());
    assertEquals("PAGE_ID", item.getRecipient().getId());
    RequestThreadControlItem requestThreadControlItem = (RequestThreadControlItem) item.getItem();
    assertNotNull(requestThreadControlItem);
    assertEquals("REQUESTED_OWNER_APP_ID", requestThreadControlItem.getRequestedOwnerAppId());
    assertEquals("METADATA", requestThreadControlItem.getMetadata());
    AtomicBoolean found = new AtomicBoolean(false);
    webhookListener.registerListener(new AbstractWebhookMessagingListener() {

      @Override
      public void requestThreadControl(RequestThreadControlItem requestThreadControl, MessagingParticipant recipient,
          MessagingParticipant sender, Date timestamp) {
        assertNotNull(requestThreadControl);
        found.set(true);
      }
    });
    webhookListener.process(webhookObject);
    assertTrue(found.get());
  }

  @Test
  void productTemplate() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-product-template"), WebhookObject.class);
    assertNotNull(webhookObject);
    ProductItem productItem = extractProductItem(webhookObject);
    assertThat(productItem.getId()).isEqualTo("<PRODUCT_ID>");
    assertThat(productItem.getImageUrl()).endsWith("sdsd");
    assertThat(productItem.getTitle()).isEqualTo("Some product title");
    assertThat(productItem.getRetailerId()).isEqualTo("<EXTERNAL_ID>");
    assertThat(productItem.getSubtitle()).isEqualTo("$40");
  }

  @Test
  void productTemplateLive() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-product-template-2"), WebhookObject.class);
    assertNotNull(webhookObject);
    ProductItem productItem = extractProductItem(webhookObject);
    assertThat(productItem.getId()).isEqualTo("12345678910");
    assertThat(productItem.getImageUrl()).contains("andSoOn.png");
    assertThat(productItem.getTitle()).isEqualTo("product name");
    assertThat(productItem.getRetailerId()).isEqualTo("123456");
    assertThat(productItem.getSubtitle()).isEqualTo("product price as a string");
  }

  private ProductItem extractProductItem(WebhookObject webhookObject) {
    MessagingItem item = webhookObject.getEntryList().get(0).getMessaging().get(0);
    assertNotNull(item);
    assertTrue(item.getMessage().hasAttachment());
    MessagingAttachment messagingAttachment = item.getMessage().getAttachments().get(0);
    assertNotNull(messagingAttachment);
    assertTrue(messagingAttachment.isTemplate());
    final ProductTemplateItem product = messagingAttachment.getPayload().getProduct();
    assertFalse(product.getElements().isEmpty());
    ProductItem productItem = product.getElements().get(0);
    assertNotNull(productItem);
    return productItem;
  }
}
