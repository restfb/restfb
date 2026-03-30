/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.webhook.ChangeValue;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.whatsapp.*;
import com.restfb.types.whatsapp.platform.Message;
import com.restfb.types.whatsapp.platform.Status;
import com.restfb.types.whatsapp.platform.message.Contact;
import com.restfb.types.whatsapp.platform.message.Error;
import com.restfb.types.whatsapp.platform.message.MessageContact;
import com.restfb.types.whatsapp.platform.message.MessageType;

class WebhookWhatsappTest extends AbstractJsonMapperTests {

  @Test
  void messageTemplateStatusUpdate_approved() {
    MessageTemplateStatusUpdateValue change =
        getWHObjectFromJson("webhook-messageTemplateStatusUpdate-approved", MessageTemplateStatusUpdateValue.class);
    assertThat(change.getMessageTemplateId()).isEqualTo("1234567");
    assertThat(change.getEvent()).isEqualTo("APPROVED");
    assertThat(change.getMessageTemplateName()).isEqualTo("My message template");
    assertThat(change.getMessageTemplateLanguage()).isEqualTo("en-US");
    assertThat(change.getMessageTemplateId()).isEqualTo("1234567");
    assertThat(change.getDisableInfo()).isNull();
  }

  @Test
  void messageTemplateStatusUpdate_disabling() {
    MessageTemplateStatusUpdateValue change =
        getWHObjectFromJson("webhook-messageTemplateStatusUpdate-disabling", MessageTemplateStatusUpdateValue.class);
    assertThat(change.getMessageTemplateId()).isEqualTo("1234567");
    assertThat(change.getEvent()).isEqualTo("FLAGGED");
    assertThat(change.getMessageTemplateName()).isEqualTo("My message template");
    assertThat(change.getMessageTemplateLanguage()).isEqualTo("en-US");
    assertThat(change.getMessageTemplateId()).isEqualTo("1234567");
    assertThat(change.getDisableInfo()).isNotNull();
  }

  @Test
  void phoneNumberNameUpdate() {
    PhoneNumberNameUpdateValue change =
        getWHObjectFromJson("webhook-phoneNumberNameUpdate", PhoneNumberNameUpdateValue.class);
    assertThat(change.getDisplayPhoneNumber()).isEqualTo("16505551111");
    assertThat(change.getDecision()).isEqualTo("APPROVED");
    assertThat(change.getRequestedVerifiedName()).isEqualTo("WhatsApp");
    assertThat(change.getRejectionReason()).isNull();
  }

  @Test
  void phoneNumberQualityUpdate() {
    PhoneNumberQualityUpdateValue change =
        getWHObjectFromJson("webhook-phoneNumberQualityUpdate", PhoneNumberQualityUpdateValue.class);
    assertThat(change.getDisplayPhoneNumber()).isEqualTo("16505551111");
    assertThat(change.getCurrentLimit()).isEqualTo("TIER_10K");
    assertThat(change.getEvent()).isEqualTo("FLAGGED");
  }

  @Test
  void accountUpdateVerified() {
    AccountUpdateValue change = getWHObjectFromJson("webhook-accountUpdate-verified", AccountUpdateValue.class);
    assertThat(change.getPhoneNumber()).isEqualTo("16505551111");
    assertThat(change.getEvent()).isEqualTo("VERIFIED_ACCOUNT");
    assertThat(change.getBanInfo()).isNull();
  }

  @Test
  void accountUpdateBanned() {
    AccountUpdateValue change = getWHObjectFromJson("webhook-accountUpdate-banned", AccountUpdateValue.class);
    assertThat(change.getPhoneNumber()).isNull();
    assertThat(change.getEvent()).isEqualTo("DISABLED_UPDATE");
    assertThat(change.getBanInfo()).isNotNull();
    assertThat(change.getBanInfo().getWabaBanDate()).isEqualTo("January 31, 2021");
    assertThat(change.getBanInfo().getWabaBanState()).isEqualTo("SCHEDULE_FOR_DISABLE");
  }

  @Test
  void accountReviewUpdate() {
    AccountReviewUpdateValue change =
        getWHObjectFromJson("webhook-accountReviewUpdate", AccountReviewUpdateValue.class);
    assertThat(change.getDecision()).isEqualTo("APPROVED");
  }

  @Test
  void businessUsernameUpdateApproved() {
    BusinessUsernameUpdateValue change =
        getWHObjectFromJson("webhook-businessUsernameUpdate-approved", BusinessUsernameUpdateValue.class);
    assertThat(change.getDisplayPhoneNumber()).isEqualTo("15550783881");
    assertThat(change.getUsername()).isEqualTo("acme_support");
    assertThat(change.getStatus()).isEqualTo("approved");
  }

  @Test
  void userIdUpdate() {
    UserIdUpdateValue change = getWHObjectFromJson("webhook-userIdUpdate", UserIdUpdateValue.class);
    assertThat(change.getMessagingProduct()).isEqualTo("whatsapp");
    assertThat(change.getContacts()).hasSize(1);
    assertThat(change.getContacts().get(0).getWaId()).isEqualTo("16505551234");
    assertThat(change.getUserIdUpdate()).hasSize(1);
    UserIdUpdateValue.UserIdUpdateItem update = change.getUserIdUpdate().get(0);
    assertThat(update.getWaId()).isEqualTo("16505551234");
    assertThat(update.getDetail()).isEqualTo("User id for Sheena Nelson has been updated.");
    assertThat(update.getUserId()).isNotNull();
    assertThat(update.getUserId().getPrevious()).isEqualTo("US.10101010101010101010");
    assertThat(update.getUserId().getCurrent()).isEqualTo("US.13491208655302741918");
    assertThat(update.getParentUserId()).isNotNull();
    assertThat(update.getParentUserId().getPrevious()).isEqualTo("US.ENT.10101010101010101010");
    assertThat(update.getParentUserId().getCurrent()).isEqualTo("US.ENT.11815799212886844830");
    assertThat(update.getTimestamp()).isNotNull();
  }

  @Test
  void messageContacts() {
    WhatsappMessagesValue messagesValue =
        getWHObjectFromJson("webhook-incoming-message-contacts", WhatsappMessagesValue.class);
    assertThat(messagesValue.getContacts()).hasSize(1);
    assertThat(messagesValue.getMessages()).hasSize(1);
    Message message = messagesValue.getMessages().get(0);
    assertThat(message.hasContacts()).isTrue();
    assertThat(message.getType()).isEqualTo(MessageType.contacts);
    assertThat(message.getContacts()).hasSize(1);
    MessageContact contact = message.getContacts().get(0);
    assertThat(contact.getName()).isNotNull();
    assertThat(contact.getName().getFirstName()).isEqualTo("j_doe");
    assertThat(contact.getName().getFormattedName()).isEqualTo("j_doe");
  }

  @Test
  void messageContacts2() {
    WhatsappMessagesValue messagesValue =
        getWHObjectFromJson("webhook-incoming-message-contacts2", WhatsappMessagesValue.class);
    assertThat(messagesValue.getContacts()).hasSize(1);
    assertThat(messagesValue.getMessages()).hasSize(1);
    Message message = messagesValue.getMessages().get(0);
    assertThat(message.hasContacts()).isTrue();
    assertThat(message.getType()).isEqualTo(MessageType.contacts);
    assertThat(message.getContacts()).hasSize(2);
    MessageContact contact1 = message.getContacts().get(0);
    assertThat(contact1.getName()).isNotNull();
    assertThat(contact1.getName().getFirstName()).isEqualTo("REDACTED");
    assertThat(contact1.getName().getLastName()).isEqualTo("REDACTED");
    assertThat(contact1.getName().getFormattedName()).isEqualTo("REDACTED");
    assertThat(contact1.getPhones()).hasSize(1);
    MessageContact.ContactPhone phone = contact1.getPhones().get(0);
    assertThat(phone.getPhone()).isEqualTo("+39 393 REDACTED");
    assertThat(phone.getType()).isEqualTo("Cellulare");
    assertThat(phone.getWaId()).isNull();
    MessageContact contact2 = message.getContacts().get(1);
    assertThat(contact2.getName()).isNotNull();
    assertThat(contact2.getName().getFirstName()).isEqualTo("REDACTED");
    assertThat(contact2.getName().getLastName()).isEqualTo("REDACTED");
    assertThat(contact2.getName().getFormattedName()).isEqualTo("REDACTED");
    assertThat(contact2.getPhones()).hasSize(1);
    MessageContact.ContactPhone phone2 = contact2.getPhones().get(0);
    assertThat(phone2.getPhone()).isEqualTo("+39 349 REDACTED");
    assertThat(phone2.getWaId()).isEqualTo("39349REDACTED");
    assertThat(phone2.getType()).isEqualTo("Cellulare");
  }

  @Test
  void messageUnsupported() {
    WhatsappMessagesValue messagesValue =
        getWHObjectFromJson("webhook-incoming-message-unsupported", WhatsappMessagesValue.class);
    assertThat(messagesValue.getMessages()).hasSize(1);
    Message message = messagesValue.getMessages().get(0);
    assertThat(message.getType()).isEqualTo(MessageType.unsupported);
    assertThat(message.getErrors()).hasSize(1);
    Error error = message.getErrors().get(0);
    assertThat(error.getErrorData()).isNotNull();
  }

  @Test
  void incomingMessageWithBsuidFields() {
    WhatsappMessagesValue messagesValue =
        getWHObjectFromJson("webhook-incoming-message-text-bsuid", WhatsappMessagesValue.class);
    assertThat(messagesValue.getContacts()).hasSize(1);
    Contact contact = messagesValue.getContacts().get(0);
    assertThat(contact.getWaId()).isNull();
    assertThat(contact.getUserId()).isEqualTo("US.13491208655302741918");
    assertThat(contact.getParentUserId()).isEqualTo("US.ENT.11815799212886844830");
    assertThat(contact.getProfile()).isNotNull();
    assertThat(contact.getProfile().getName()).isEqualTo("Sheena Nelson");
    assertThat(contact.getProfile().getUsername()).isEqualTo("@realsheenanelson");

    assertThat(messagesValue.getMessages()).hasSize(1);
    Message message = messagesValue.getMessages().get(0);
    assertThat(message.getFrom()).isNull();
    assertThat(message.getFromUserId()).isEqualTo("US.13491208655302741918");
    assertThat(message.getFromParentUserId()).isEqualTo("US.ENT.11815799212886844830");
    assertThat(message.getGroupId()).isEqualTo("120363418770915618@g.us");
    assertThat(message.getType()).isEqualTo(MessageType.text);
    assertThat(message.getText()).isNotNull();
    assertThat(message.getText().getBody()).isEqualTo("Does it come in another color?");
  }

  @Test
  void statusMessageWithBsuidFields() {
    WhatsappMessagesValue messagesValue =
        getWHObjectFromJson("webhook-incoming-message-status-bsuid", WhatsappMessagesValue.class);
    assertThat(messagesValue.getContacts()).hasSize(1);
    Contact contact = messagesValue.getContacts().get(0);
    assertThat(contact.getWaId()).isNull();
    assertThat(contact.getUserId()).isEqualTo("US.13491208655302741918");
    assertThat(contact.getParentUserId()).isEqualTo("US.ENT.11815799212886844830");
    assertThat(contact.getProfile()).isNotNull();
    assertThat(contact.getProfile().getName()).isEqualTo("Pablo M.");
    assertThat(contact.getProfile().getUsername()).isEqualTo("@pablomorales");

    assertThat(messagesValue.getStatuses()).hasSize(1);
    Status status = messagesValue.getStatuses().get(0);
    assertThat(status.getRecipientId()).isNull();
    assertThat(status.getRecipientUserId()).isEqualTo("US.13491208655302741918");
    assertThat(status.getParentRecipientUserId()).isEqualTo("US.ENT.11815799212886844830");
    assertThat(status.getPricing()).isNotNull();
    assertThat(status.getPricing().getType()).isEqualTo("regular");
  }

  @Test
  void groupStatusMessageWithBsuidFields() {
    WhatsappMessagesValue messagesValue =
        getWHObjectFromJson("webhook-incoming-message-status-group-bsuid", WhatsappMessagesValue.class);
    assertThat(messagesValue.getStatuses()).hasSize(1);
    Status status = messagesValue.getStatuses().get(0);
    assertThat(status.getRecipientId()).isEqualTo("120363418770915618@g.us");
    assertThat(status.getRecipientParticipantId()).isEqualTo("16505551234");
    assertThat(status.getRecipientParticipantUserId()).isEqualTo("US.13491208655302741918");
    assertThat(status.getRecipientParticipantParentUserId()).isEqualTo("US.ENT.11815799212886844830");
  }

  @Test
  void systemMessageWithBsuidFields() {
    WhatsappMessagesValue messagesValue =
        getWHObjectFromJson("webhook-incoming-message-system-bsuid", WhatsappMessagesValue.class);
    assertThat(messagesValue.getMessages()).hasSize(1);
    Message message = messagesValue.getMessages().get(0);
    assertThat(message.isSystem()).isTrue();
    assertThat(message.getSystem().getWaId()).isNull();
    assertThat(message.getSystem().getUserId()).isEqualTo("US.13491208655302741918");
    assertThat(message.getSystem().getParentUserId()).isEqualTo("US.ENT.11815799212886844830");
    assertThat(message.getSystem().getType()).isEqualTo("user_changed_user_id");
    assertThat(message.getSystem().getBody())
        .isEqualTo("User Sheena Nelson changed from US.10101010101010101010 to US.13491208655302741918");
  }

  @Test
  void contactsRequestMessageWithBsuidFields() {
    WhatsappMessagesValue messagesValue =
        getWHObjectFromJson("webhook-incoming-message-contacts-request-bsuid", WhatsappMessagesValue.class);
    assertThat(messagesValue.getContacts()).hasSize(1);
    Contact contact = messagesValue.getContacts().get(0);
    assertThat(contact.getUserId()).isEqualTo("US.13491208655302741918");
    assertThat(contact.getParentUserId()).isEqualTo("US.ENT.11815799212886844830");
    assertThat(contact.getProfile()).isNotNull();
    assertThat(contact.getProfile().getUsername()).isEqualTo("@realsheenanelson");

    assertThat(messagesValue.getMessages()).hasSize(1);
    Message message = messagesValue.getMessages().get(0);
    assertThat(message.getType()).isEqualTo(MessageType.contacts);
    assertThat(message.getOrigin()).isEqualTo("contact_request/other");
    assertThat(message.getContacts()).hasSize(1);
    MessageContact messageContact = message.getContacts().get(0);
    assertThat(messageContact.getVcard()).contains("BEGIN:VCARD");
    assertThat(messageContact.getPhones()).hasSize(1);
    assertThat(messageContact.getPhones().get(0).getPhone()).isEqualTo("15551234567");
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
