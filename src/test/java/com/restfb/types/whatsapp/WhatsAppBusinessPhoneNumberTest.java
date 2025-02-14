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
package com.restfb.types.whatsapp;

import com.restfb.AbstractJsonMapperTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhatsAppBusinessPhoneNumberTest extends AbstractJsonMapperTests {

  @Test
  void checkDeserializeDefaultResponse() {
    WhatsAppBusinessPhoneNumber phoneNumber = createJsonMapper().toJavaObject(
            jsonFromClasspath("whatsapp/phone-number-default-response"),
            WhatsAppBusinessPhoneNumber.class);

    assertNotNull(phoneNumber);
    assertEquals("Business Phone Numbers Name", phoneNumber.getVerifiedName());
    assertEquals(WhatsAppBusinessPhoneNumber.CodeVerificationStatus.EXPIRED, phoneNumber.getCodeVerificationStatus());
    assertEquals("+593 99 999 9999", phoneNumber.getDisplayPhoneNumber());
    assertEquals("GREEN", phoneNumber.getQualityScore().getScore());
    assertEquals(WhatsAppBusinessPhoneNumber.PlatformType.CLOUD_API, phoneNumber.getPlatformType());
    assertEquals(WhatsAppBusinessPhoneNumber.Throughput.Level.STANDARD, phoneNumber.getThroughput().getLevel());
    assertEquals("999999999999999", phoneNumber.getId());
    assertNotNull(phoneNumber.getLastOnboardedTime());
  }

  @Test
  void checkDeserializeFullResponse() {
    WhatsAppBusinessPhoneNumber phoneNumber = createJsonMapper().toJavaObject(
            jsonFromClasspath("whatsapp/phone-number-full-response"),
            WhatsAppBusinessPhoneNumber.class);

    // Validate main fields
    assertNotNull(phoneNumber);
    assertEquals("certificate", phoneNumber.getCertificate());
    assertEquals("new_certificate", phoneNumber.getNewCertificate());
    assertEquals(WhatsAppBusinessPhoneNumber.AccountMode.LIVE, phoneNumber.getAccountMode());
    assertEquals(WhatsAppBusinessPhoneNumber.CodeVerificationStatus.EXPIRED, phoneNumber.getCodeVerificationStatus());
    assertEquals("+593 99 999 9999", phoneNumber.getDisplayPhoneNumber());
    assertEquals("NON_ELIGIBLE_NUMBER_OUTSIDE_ROLLOUT_COUNTRIES", phoneNumber.getEligibilityForApiBusinessGlobalSearch());
    assertEquals(WhatsAppBusinessPhoneNumber.MessagingLimitTier.TIER_1K, phoneNumber.getMessagingLimitTier());
    assertEquals(WhatsAppBusinessPhoneNumber.NameStatus.APPROVED, phoneNumber.getNameStatus());
    assertEquals(WhatsAppBusinessPhoneNumber.PlatformType.CLOUD_API, phoneNumber.getPlatformType());
    assertEquals(WhatsAppBusinessPhoneNumber.Status.CONNECTED, phoneNumber.getStatus());
    assertEquals("Business Phone Numbers Name", phoneNumber.getVerifiedName());

    // Validate boolean fields
    assertFalse(phoneNumber.getIsOfficialBusinessAccount());
    assertFalse(phoneNumber.getIsOnBizApp());
    assertTrue(phoneNumber.getIsPinEnabled());
    assertFalse(phoneNumber.getIsPreverifiedNumber());

    // Validate throughput field
    assertEquals(WhatsAppBusinessPhoneNumber.Throughput.Level.STANDARD, phoneNumber.getThroughput().getLevel());

    // Validate HealthStatus
    assertNotNull(phoneNumber.getHealthStatus());
    assertEquals(WhatsAppBusinessHealthStatus.CanSendMessageStatus.BLOCKED, phoneNumber.getHealthStatus().getCanSendMessage());
    assertEquals(4, phoneNumber.getHealthStatus().getEntities().size());

    // Validate first HealthStatus entity
    WhatsAppBusinessHealthStatus.EntityStatus entity = phoneNumber.getHealthStatus().getEntities().get(0);
    assertEquals(WhatsAppBusinessHealthStatus.EntityType.PHONE_NUMBER, entity.getEntityType());
    assertEquals("999999999999999", entity.getId());
    assertEquals(WhatsAppBusinessHealthStatus.CanSendMessageStatus.BLOCKED, entity.getCanSendMessage());
    assertFalse(entity.getErrors().isEmpty());

    assertEquals(141000, entity.getErrors().get(0).getErrorCode());
    assertEquals("The phone number you are trying to send messages from is not linked to your WhatsApp account.", entity.getErrors().get(0).getErrorDescription());
    assertEquals("Register and finish the OTP authentication process for your phone number.", entity.getErrors().get(0).getPossibleSolution());

    assertFalse(entity.getAdditionalInfo().isEmpty());
    assertEquals("Your display name has not been approved yet. Your message limit will increase after the display name is approved.", entity.getAdditionalInfo().get(0));

    // Validate Conversation Automation
    WhatsAppBusinessConversationalComponent conversationalComponent = phoneNumber.getConversationAutomation();
    assertNotNull(conversationalComponent);
    assertTrue(conversationalComponent.isEnableWelcomeMessage());

    assertNotNull(conversationalComponent.getPrompts());
    assertEquals(2, conversationalComponent.getPrompts().size());
    assertEquals("Find the best hotels in the area", conversationalComponent.getPrompts().get(0));
    assertEquals("Find deals on rental cars", conversationalComponent.getPrompts().get(1));

    assertNotNull(conversationalComponent.getCommands());
    assertEquals(2, conversationalComponent.getCommands().size());
    assertEquals("tickets", conversationalComponent.getCommands().get(0).getCommandName());
    assertEquals("Book flight tickets", conversationalComponent.getCommands().get(0).getCommandDescription());
    assertEquals("hotel", conversationalComponent.getCommands().get(1).getCommandName());
    assertEquals("Book hotel", conversationalComponent.getCommands().get(1).getCommandDescription());

    // Validate Quality Score
    assertNotNull(phoneNumber.getQualityScore());
    assertEquals("GREEN", phoneNumber.getQualityScore().getScore());
    assertEquals(1739513802, phoneNumber.getQualityScore().getDate());
    assertEquals("reason", phoneNumber.getQualityScore().getReasons().get(0));
  }

  @Test
  void checkListsNotNullWhenMissingInJson() {
    WhatsAppBusinessPhoneNumber phoneNumber = createJsonMapper().toJavaObject(
            jsonFromClasspath("whatsapp/phone-number-missing-lists"),
            WhatsAppBusinessPhoneNumber.class);

    assertNotNull(phoneNumber);

    assertNotNull(phoneNumber.getConversationAutomation());
    assertNotNull(phoneNumber.getConversationAutomation().getPrompts());
    assertNotNull(phoneNumber.getConversationAutomation().getCommands());

    assertNotNull(phoneNumber.getHealthStatus());
    assertNotNull(phoneNumber.getHealthStatus().getEntities());
    assertNotNull(phoneNumber.getHealthStatus().getEntities().get(0).getAdditionalInfo());
    assertNotNull(phoneNumber.getHealthStatus().getEntities().get(0).getErrors());

    assertNotNull(phoneNumber.getQualityScore());
    assertNotNull(phoneNumber.getQualityScore().getReasons());
  }
}
