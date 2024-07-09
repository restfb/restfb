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

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.webhook.ChangeValue;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.whatsapp.*;
import com.restfb.types.whatsapp.platform.Message;
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

  private <T extends ChangeValue> T getWHObjectFromJson(String jsonName, Class<T> clazz) {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("whatsapp/" + jsonName), WebhookObject.class);
    assertThat(webhookObject.isWhatsAppBusinessAccount()).isTrue();
    assertThat(webhookObject.getEntryList()).hasSize(1);
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    return entry.getChanges().get(0).getValue().convertChangeValue(clazz);
  }

}
