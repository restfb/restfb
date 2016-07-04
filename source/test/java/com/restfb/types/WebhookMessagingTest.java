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
package com.restfb.types;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.messaging.*;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class WebhookMessagingTest extends AbstractJsonMapperTests {

  @Test
  public void messagingDelivery() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-delivery-basic"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    DeliveryItem item = messagingItem.getDelivery();
    assertNotNull(item);
    assertFalse(item.getMids().isEmpty());
    assertEquals("1458668856253", item.getWatermark());
    assertEquals(37L, item.getSeq().longValue());

  }

  @Test
  public void messagingMessageMediaAttachment() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-media-attachment"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    MessageItem item = messagingItem.getMessage();
    assertNotNull(item);
    assertEquals("mid.1458696618141:b4ef9d19ec21086067", item.getMid());
    assertNull(item.getText());
    assertEquals(51L, item.getSeq().longValue());
    assertFalse(item.getAttachments().isEmpty());
    MessagingAttachment attachment = item.getAttachments().get(0);
    assertEquals("image", attachment.getType());
    assertEquals("IMAGE_URL", attachment.getPayload().getUrl());
  }

  @Test
  public void messagingMessageLocationAttachment() {
    WebhookObject webhookObject = createJsonMapper()
            .toJavaObject(jsonFromClasspath("webhooks/messaging-message-location-attachment"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    MessageItem item = messagingItem.getMessage();
    assertNotNull(item);
    assertEquals("mid.1458696618141:b4ef9d19ec21086067", item.getMid());
    assertNull(item.getText());
    assertEquals(51L, item.getSeq().longValue());
    assertFalse(item.getAttachments().isEmpty());
    MessagingAttachment attachment = item.getAttachments().get(0);
    assertEquals("location", attachment.getType());
    assertEquals(12.34, attachment.getPayload().getCoordinates().getLat(), 0);
    assertEquals(43.21, attachment.getPayload().getCoordinates().getLongVal(), 0);
  }

  @Test
  public void messagingMessageLegacyFallbackAttachment() {
    WebhookObject webhookObject = createJsonMapper()
            .toJavaObject(jsonFromClasspath("webhooks/messaging-message-fallback-attachment"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    MessageItem item = messagingItem.getMessage();
    assertNotNull(item);
    assertEquals("mid.1458696618141:b4ef9d19ec21086067", item.getMid());
    assertNull(item.getText());
    assertEquals(51L, item.getSeq().longValue());
    assertFalse(item.getAttachments().isEmpty());
    MessagingAttachment attachment = item.getAttachments().get(0);
    assertEquals("fallback", attachment.getType());
    assertEquals("Legacy Attachment", attachment.getTitle());
    assertEquals(null, attachment.getPayload());

  }

  @Test
  public void messagingMessageBasic() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-basic"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    MessageItem item = messagingItem.getMessage();
    assertFalse(item.isEcho());
    assertNotNull(item);
    assertEquals("mid.1457764197618:41d102a3e1ae206a38", item.getMid());
    assertEquals("hello, world!", item.getText());
    assertEquals(73L, item.getSeq().longValue());
    assertTrue(item.getAttachments().isEmpty());
  }
  
  @Test
  public void messagingMessageBasicIsEcho() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-basic-echo"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    MessageItem item = messagingItem.getMessage();
    assertTrue(item.isEcho());
    assertNotNull(item);
    assertEquals("mid.1457764197618:41d102a3e1ae206a38", item.getMid());
    assertEquals("hello, world!", item.getText());
    assertEquals("1517776481860111", item.getAppId());
    assertEquals("DEVELOPER_DEFINED_METADATA_STRING", item.getMetadata());
    assertEquals(73L, item.getSeq().longValue());
    assertTrue(item.getAttachments().isEmpty());
  }

  @Test
  public void messagingOptinBasic() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-optin-basic"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    OptinItem item = messagingItem.getOptin();
    assertNotNull(item);
    assertEquals("PASS_THROUGH_PARAM", item.getRef());
  }

  @Test
  public void messagingPostbackBasic() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-postback-basic"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    PostbackItem item = messagingItem.getPostback();
    assertNotNull(item);
    assertEquals("USER_DEFINED_PAYLOAD", item.getPayload());
  }

  @Test
  public void messagingButtonTemplate() {
    WebhookObject webhookObject =
            createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-button-template"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
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
  public void messagingAirlineItineraryTemplate() throws ParseException {
    WebhookObject webhookObject =
            createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-airline-itinerary-template"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    MessageItem item = messagingItem.getMessage();
    assertFalse(item.getAttachments().isEmpty());
    MessagingAttachment attachment = item.getAttachments().get(0);
    assertEquals("template", attachment.getType());
    assertEquals("airline_itinerary", attachment.getPayload().getTemplateType());
    assertEquals("Here's your flight itinerary.", attachment.getPayload().getIntroMessage());
    assertEquals("en_US", attachment.getPayload().getLocale());
    assertEquals("ABCDEF", attachment.getPayload().getPnrNumber());
    assertEquals(12206, attachment.getPayload().getBasePrice(), 0);
    assertEquals(200, attachment.getPayload().getTax(), 0);
    assertEquals(14003, attachment.getPayload().getTotalPrice(), 0);
    assertEquals("Farbound Smith Jr", attachment.getPayload().getPassengerInfoItems().get(0).getName());
    assertEquals("c001", attachment.getPayload().getFlightInfoItems().get(0).getConnectionId());
    assertEquals("SFO", attachment.getPayload().getFlightInfoItems().get(0).getDepartureAirport().getAirportCode());
    assertEquals("2016-01-02T19:45", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(attachment.getPayload().getFlightInfoItems().get(0).getFlightSchedule().getDepartureTime()));
    assertEquals("12A", attachment.getPayload().getPassengerSegmentInfoItems().get(0).getSeat());
    assertEquals("USD", attachment.getPayload().getPriceInfoItems().get(0).getCurrency());
  }
}
