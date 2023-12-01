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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.webhook.ChangeValue;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.whatsapp.WhatsappMessagesValue;
import com.restfb.types.whatsapp.platform.Message;
import com.restfb.types.whatsapp.platform.Status;
import com.restfb.types.whatsapp.platform.message.*;
import com.restfb.types.whatsapp.platform.message.Error;
import com.restfb.types.whatsapp.platform.message.Location;
import com.restfb.types.whatsapp.platform.message.System;
import com.restfb.types.whatsapp.platform.message.Video;
import com.restfb.types.whatsapp.platform.status.CategoryType;
import com.restfb.types.whatsapp.platform.status.Conversation;
import com.restfb.types.whatsapp.platform.status.Pricing;

class WABPwebhookTest extends AbstractJsonMapperTests {

  @Test
  void incomingMessageText() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-text", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    // check contact
    checkContact(change);

    // check Metadata
    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    com.restfb.types.whatsapp.platform.Message message = change.getMessages().get(0);
    assertThat(message.getText()).isNotNull();
    assertThat(message.getText().getBody()).isEqualTo("Test");
    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.text);
    assertThat(message.getFrom()).isEqualTo("491234567890");
    assertThat(message.isText()).isTrue();
  }

  @Test
  void incomingMessageImage() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-image", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    // check contact
    checkContact(change);

    // check Metadata
    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getImage()).isNotNull();
    assertThat(message.isImage()).isTrue();
    assertThat(message.isText()).isFalse();

    Image image = message.getImage();
    assertThat(image.getCaption()).isEqualTo("Some awesome image");
    assertThat(image.getId()).isEqualTo("400962571939895");
    assertThat(image.getMimeType()).isEqualTo("image\\/jpeg");
    assertThat(image.getSha256()).isEqualTo("law0CgE277wMMnIJU0XIxhDne6Ptmwaek\\/thVM7mVtg=");

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.image);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageVideo() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-video", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getVideo()).isNotNull();
    assertThat(message.isImage()).isFalse();
    assertThat(message.isText()).isFalse();
    assertThat(message.isVideo()).isTrue();

    Video image = message.getVideo();
    assertThat(image.getCaption()).isEqualTo("Video");
    assertThat(image.getId()).isEqualTo("378911494190301");
    assertThat(image.getMimeType()).isEqualTo("video\\/mp4");
    assertThat(image.getSha256()).isEqualTo("r9TvFx8eGPq\\/nn7l\\/yVMfSmQjBPWlEGOpkBKMVNqDGk=");

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.video);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageSticker() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-sticker", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getSticker()).isNotNull();
    assertThat(message.isImage()).isFalse();
    assertThat(message.isText()).isFalse();
    assertThat(message.isVideo()).isFalse();
    assertThat(message.isSticker()).isTrue();

    Sticker image = message.getSticker();
    assertThat(image.getId()).isEqualTo("1122990445227715");
    assertThat(image.getMimeType()).isEqualTo("image\\/webp");
    assertThat(image.getSha256()).isEqualTo("RZEEl5HVWT4S6C0PoOgjYCQVDWs5esIMJsjcDYI80ZE=");

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.sticker);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageLocation() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-location", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getLocation()).isNotNull();
    assertThat(message.isImage()).isFalse();
    assertThat(message.isText()).isFalse();
    assertThat(message.isVideo()).isFalse();
    assertThat(message.isSticker()).isFalse();
    assertThat(message.isLocation()).isTrue();

    Location image = message.getLocation();
    assertThat(image.getName()).isEqualTo("Saarbr\\u00fccken");
    assertThat(image.getLatitude()).isEqualTo(49.234939575195);
    assertThat(image.getLongitude()).isEqualTo(6.9993596076965);

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.location);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageAudio() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-audio", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getAudio()).isNotNull();
    assertThat(message.isImage()).isFalse();
    assertThat(message.isText()).isFalse();
    assertThat(message.isVideo()).isFalse();
    assertThat(message.isSticker()).isFalse();
    assertThat(message.isLocation()).isFalse();
    assertThat(message.isAudio()).isTrue();
    assertThat(message.isVoice()).isTrue();

    Audio image = message.getAudio();
    assertThat(image.getId()).isEqualTo("1113842206146232");
    assertThat(image.getMimeType()).isEqualTo("audio\\/ogg; codecs=opus");
    assertThat(image.getSha256()).isEqualTo("qGgRrybLhq3DQ2fh7t2IPHP6LnQpFVsZ7zkDwoxDPJs=");
    assertThat(image.isVoice()).isTrue();

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.audio);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageEmoji() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-emoji", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getReaction()).isNotNull();
    assertThat(message.isImage()).isFalse();
    assertThat(message.isText()).isFalse();
    assertThat(message.isVideo()).isFalse();
    assertThat(message.isSticker()).isFalse();
    assertThat(message.isLocation()).isFalse();
    assertThat(message.isAudio()).isFalse();
    assertThat(message.isVoice()).isFalse();
    assertThat(message.isReaction()).isTrue();

    Reaction reaction = message.getReaction();
    assertThat(reaction.getMessageId()).isEqualTo("wamid.xxxxxxxx");
    assertThat(reaction.getEmoji()).isEqualTo("\uD83D\uDC4D\uD83C\uDFFB");
  }

  @Test
  void incomingMessageButton() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-button", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getButton()).isNotNull();
    assertThat(message.isImage()).isFalse();
    assertThat(message.isText()).isFalse();
    assertThat(message.isVideo()).isFalse();
    assertThat(message.isSticker()).isFalse();
    assertThat(message.isLocation()).isFalse();
    assertThat(message.isAudio()).isFalse();
    assertThat(message.isVoice()).isFalse();
    assertThat(message.isButton()).isTrue();

    assertThat(message.hasContext()).isTrue();

    Button image = message.getButton();
    assertThat(image.getText()).isEqualTo("No");
    assertThat(image.getPayload()).isEqualTo("No-Button-Payload");

    Context context = message.getContext();
    assertThat(context.getFrom()).isEqualTo("PHONE_NUMBER");
    assertThat(context.getId()).isEqualTo("wamid.ID");
    assertThat(context.isForwarded()).isFalse();
    assertThat(context.isFrequentlyForwarded()).isFalse();

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.button);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageDocument() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-document", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getDocument()).isNotNull();
    assertThat(message.isImage()).isFalse();
    assertThat(message.isText()).isFalse();
    assertThat(message.isVideo()).isFalse();
    assertThat(message.isSticker()).isFalse();
    assertThat(message.isLocation()).isFalse();
    assertThat(message.isAudio()).isFalse();
    assertThat(message.isVoice()).isFalse();
    assertThat(message.isButton()).isFalse();
    assertThat(message.isDocument()).isTrue();

    Document image = message.getDocument();
    assertThat(image.getId()).isEqualTo("4937353526352");
    assertThat(image.getMimeType()).isEqualTo("application\\/zip");
    assertThat(image.getSha256()).isEqualTo("FzonbuoHILCEbNUvfvddy77Ef\\/vLBuZvfrM8W2kd4srXrk=");
    assertThat(image.getFilename()).isEqualTo("RESTFB ANNIVERSARY.zip");
    assertThat(image.getCaption()).isEqualTo("RESTFB ANNIVERSARY");

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.document);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageAds() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-ads", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getText()).isNotNull();
    assertThat(message.getReferral()).isNotNull();
    assertThat(message.isText()).isTrue();
    assertThat(message.hasReferral()).isTrue();

    Text image = message.getText();
    assertThat(image.getBody()).isEqualTo("Test");

    Referral referral = message.getReferral();
    assertThat(referral.getBody()).isEqualTo("AD_DESCRIPTION");
    assertThat(referral.getHeadline()).isEqualTo("AD_TITLE");
    assertThat(referral.getImageUrl()).isEqualTo("RAW_IMAGE_URL");
    assertThat(referral.getSourceId()).isEqualTo("ADID");
    assertThat(referral.getSourceUrl()).isEqualTo("AD_OR_POST_FB_URL");
    assertThat(referral.getSourceType()).isEqualTo(Referral.SourceType.ad);
    assertThat(referral.getMediaType()).isEqualTo(Referral.MediaType.video);
    assertThat(referral.getThumbnailUrl()).isEqualTo("RAW_THUMBNAIL_URL");
    assertThat(referral.getVideoUrl()).isEqualTo("RAW_VIDEO_URL");

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.text);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageSystem() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-system", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getSystem()).isNotNull();
    assertThat(message.isSystem()).isTrue();

    System image = message.getSystem();
    assertThat(image.getBody()).isEqualTo("NAME changed from PHONE_NUMBER to PHONE_NUMBER");
    assertThat(image.getNewWaId()).isEqualTo("NEW_PHONE_NUMBER");
    assertThat(image.getType()).isEqualTo("user_changed_number");

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.system);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageInteractiveButton() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-interactive-btn", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getInteractive()).isNotNull();
    assertThat(message.isInteractive()).isTrue();

    Interactive image = message.getInteractive();
    assertThat(image.isListReply()).isFalse();
    assertThat(image.isButtonReply()).isTrue();

    assertThat(image.getReply()).isNotNull();
    assertThat(image.getButtonReply()).isNotNull();
    assertThat(image.getListReply()).isNull();

    assertThat(image.getType()).isEqualTo(Interactive.Type.button_reply);

    Interactive.Reply reply = image.getReply();
    assertThat(reply.getDescription()).isNull();
    assertThat(reply.getId()).isEqualTo("unique-button-identifier-here");
    assertThat(reply.getTitle()).isEqualTo("button-text");

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.interactive);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageInteractiveList() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-interactive-list", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    // check Message
    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);
    assertThat(message.getInteractive()).isNotNull();
    assertThat(message.isInteractive()).isTrue();

    Interactive image = message.getInteractive();
    assertThat(image.isListReply()).isTrue();
    assertThat(image.isButtonReply()).isFalse();

    assertThat(image.getReply()).isNotNull();
    assertThat(image.getButtonReply()).isNull();
    assertThat(image.getListReply()).isNotNull();

    assertThat(image.getType()).isEqualTo(Interactive.Type.list_reply);

    Interactive.Reply reply = image.getReply();
    assertThat(reply.getDescription()).isEqualTo("list_reply_description");
    assertThat(reply.getId()).isEqualTo("list_reply_id");
    assertThat(reply.getTitle()).isEqualTo("list_reply_title");

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.interactive);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageUnknown() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-error", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    checkContact(change);

    checkMetaData(change);

    assertThat(change.getMessages()).hasSize(1);
    Message message = change.getMessages().get(0);

    assertThat(message.hasErrors()).isTrue();

    assertThat(message.getErrors()).hasSize(1);

    Error error = message.getErrors().get(0);
    assertThat(error.getCode()).isEqualTo("131051");
    assertThat(error.getTitle()).isEqualTo("Unsupported message type");
    assertThat(error.getDetails()).isEqualTo("Message type is not currently supported");

    assertThat(message.getTimestamp()).isEqualTo(new Date(1653253313000L));
    assertThat(message.getType()).isEqualTo(MessageType.unknown);
    assertThat(message.getFrom()).isEqualTo("491234567890");
  }

  @Test
  void incomingMessageStatusUserInitiated() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-status-ui", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    assertThat(change.getMessages()).isEmpty();
    assertThat(change.getStatuses()).hasSize(1);

    Status status = change.getStatuses().get(0);
    assertThat(status.getId()).isEqualTo("wamid.ID");
    assertThat(status.getRecipientId()).isEqualTo("PHONE_NUMBER");
    assertThat(status.getTimestamp()).isNull();

    Pricing pricing = status.getPricing();
    assertThat(pricing.getPricingModel()).isEqualTo("CBP");
    assertThat(pricing.getCategory()).isEqualTo(CategoryType.user_initiated);
    assertThat(pricing.isBillable()).isTrue();

    Conversation conversation = status.getConversation();
    assertThat(conversation.getId()).isEqualTo("CONVERSATION_ID");
    assertThat(conversation.getOrigin()).isNotNull();
    assertThat(conversation.getOrigin().getType()).isEqualTo(CategoryType.user_initiated);
  }

  @Test
  void incomingMessageStatusWithErrors() {
    WhatsappMessagesValue change = getWHObjectFromJson("webhook-incoming-message-status-errors", WhatsappMessagesValue.class);
    assertThat(change).isInstanceOf(WhatsappMessagesValue.class);

    assertThat(change.getMessages()).isEmpty();
    assertThat(change.getStatuses()).hasSize(1);

    Status status = change.getStatuses().get(0);
    assertThat(status.hasErrors()).isTrue();
    assertThat(status.getErrors()).isNotEmpty();
    assertThat(status.getStatus()).isEqualTo(Status.StatusType.failed);
    com.restfb.types.whatsapp.platform.Error error = status.getErrors().get(0);
    assertThat(error.getCode()).isEqualTo("131026");
    assertThat(error.getErrorData()).isNotNull();
    assertThat(error.getErrorData().getDetails()).isEqualTo("Message Undeliverable.");
    assertThat(error.getHref()).contains("cloud-api");
    assertThat(error.getMessage()).contains("incapable");
    assertThat(error.getTitle()).contains("incapable");
  }

  private void checkMetaData(WhatsappMessagesValue change) {
    // check Metadata
    assertThat(change.getMetadata()).isNotNull();
    assertThat(change.getMetadata().getDisplayPhoneNumber()).isEqualTo("1234567891");
    assertThat(change.getMetadata().getPhoneNumberId()).isEqualTo("10634295353625");
  }

  private void checkContact(WhatsappMessagesValue change) {
    // check contact
    assertThat(change.getContacts()).hasSize(1);
    Contact contact = change.getContacts().get(0);
    assertThat(contact.getWaId()).isEqualTo("491234567890");
    assertThat(contact.getProfile()).isNotNull();
    assertThat(contact.getProfile().getName()).isEqualTo("TestUser");
  }

  private <T extends ChangeValue> T getWHObjectFromJson(String jsonName, Class<T> clazz) {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("whatsapp/" + jsonName), WebhookObject.class);
    assertThat(webhookObject.isWhatsAppBusinessAccount()).isTrue();
    assertThat(webhookObject.getEntryList()).hasSize(1);
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    return entry.getChanges().get(0).getValue().convertChangeValue(clazz);
  }
}
