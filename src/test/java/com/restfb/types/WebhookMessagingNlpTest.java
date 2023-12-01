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

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.messaging.MessageItem;
import com.restfb.types.webhook.messaging.MessagingItem;
import com.restfb.types.webhook.messaging.NlpResult;
import com.restfb.types.webhook.messaging.nlp.*;

class WebhookMessagingNlpTest extends AbstractJsonMapperTests {

  @Test
  void messagingMessageWithNlpField_noNlpContent() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-7"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertNotNull(nlp.getEntities());
    assertEquals(0, nlp.getEntities().size());
  }

  @Test
  void messagingMessageWithNlpField_singleEntityIntend() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-6"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertNotNull(nlp.getEntities());
    assertEquals(1, nlp.getEntities().size());
    NlpCustomWitAi intend = nlp.getEntities().get(0).as(NlpCustomWitAi.class);
    NlpCustomWitAi intendByClass = nlp.getEntities(NlpCustomWitAi.class).get(0);
    assertEquals(intend, intendByClass);
    assertEquals("value", intend.getType());
    assertEquals(0.91431927422157D, intend.getConfidence().doubleValue());
    assertEquals("weather", intend.getValue());
    assertEquals("intend", nlp.getEntities(NlpCustomWitAi.class).get(0).getWitAiKey());
  }

  @Test
  void messagingMessageWithNlpField_twoEntitiesReminderDate() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-5"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertFalse(nlp.hasErrors());
    assertEquals(2, nlp.getEntities().size());
    assertEquals(1, nlp.getEntities(NlpDatetime.class).size());
    assertEquals(1, nlp.getEntities(NlpReminder.class).size());
    NlpDatetime datetime = nlp.getEntities(NlpDatetime.class).get(0);
    assertEquals("day", datetime.getGrain());
    assertEquals("value", datetime.getType());
    assertEquals("2017-08-02T00:00:00.000+02:00", datetime.getValue());
    assertEquals(0.99557711676036D, datetime.getConfidence().doubleValue());
  }

  @Test
  void messagingMessageWithNlpField_singleEntityReminder() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-3"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(1, nlp.getEntities().size());
    NlpReminder reminder = nlp.getEntities().get(0).as(NlpReminder.class);
    assertEquals(true, reminder.getSuggested());
    assertEquals("value", reminder.getType());
    assertEquals("hallo!", reminder.getValue());
    assertEquals(0.95058024208635D, reminder.getConfidence().doubleValue());
  }

  @Test
  void messagingMessageWithNlpField_singleEntityGreetings() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-2"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(1, nlp.getEntities().size());
    NlpGreetings greetings = nlp.getEntities().get(0).as(NlpGreetings.class);
    assertEquals("true", greetings.getValue());
    assertEquals(0.99982774257166D, greetings.getConfidence().doubleValue());
  }

  @Test
  void messagingMessageWithNlpField_unknownEntity() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-8"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(1, nlp.getEntities().size());
    assertEquals("foobar", nlp.getEntities(NlpCustomWitAi.class).get(0).getWitAiKey());
  }

  @Test
  void messagingMessageWithNlpField_amountOfMoney() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-money"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(1, nlp.getEntities().size());
    NlpAmountOfMoney amountOfMoney = nlp.getEntities().get(0).as(NlpAmountOfMoney.class);

    assertEquals("4.56", amountOfMoney.getValue());
    assertEquals(1D, amountOfMoney.getConfidence(), 0.01);
    assertEquals("value", amountOfMoney.getType());
    assertEquals("EUR", amountOfMoney.getUnit());
  }

  @Test
  void messagingMessageWithNlpField_phoneNumber() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-phone"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(1, nlp.getEntities().size());
    NlpPhoneNumber phoneNumber = nlp.getEntities().get(0).as(NlpPhoneNumber.class);

    assertEquals("12345654321", phoneNumber.getValue());
    assertEquals(0.960765D, phoneNumber.getConfidence(), 0.01);
  }

  @Test
  void messagingMessageWithNlpField_email() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-email"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(1, nlp.getEntities().size());
    NlpEmail email = nlp.getEntities().get(0).as(NlpEmail.class);

    assertEquals("test@emaple.org", email.getValue());
    assertEquals(0.960765D, email.getConfidence(), 0.01);
  }

  @Test
  void messagingMessageWithNlpField_url() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-url"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(7, nlp.getEntities().size());
    NlpUrl url = nlp.getEntities().get(0).as(NlpUrl.class);

    assertEquals("https://restfb.com/documentation/#using-webhooks", url.getValue());
    assertEquals(1.0D, url.getConfidence(), 0.01);
    assertEquals("restfb.com", url.getDomain());
    assertFalse(url.isEntityOnly());
    assertEquals("url", url.getEntity());
    assertEquals("https://restfb.com/documentation/#using-webhooks", url.getBody());
    assertEquals(0, url.getStart().intValue());
    assertEquals(48, url.getEnd().intValue());
  }

  @Test
  void messagingMessageWithNlpField_url_plainHost() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-url-2"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(7, nlp.getEntities().size());
    NlpUrl url = nlp.getEntities().get(0).as(NlpUrl.class);

    assertEquals("http://www.google.com", url.getValue());
    assertEquals(0.966434D, url.getConfidence(), 0.01);
    assertEquals("google.com", url.getDomain());
  }

  @Test
  void messagingMessageWithNlpField_sentiment() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-email-2"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(7, nlp.getEntities().size());

    List<NlpSentiment> sentiments = nlp.getEntities() //
      .stream().filter(NlpSentiment.class::isInstance) //
      .map(entity -> entity.as(NlpSentiment.class)).collect(Collectors.toList());

    assertEquals(3, sentiments.size());
    for (NlpSentiment sentiment : sentiments) {
      assertTrue(sentiment.isEntityOnly());
      if ("negative".equals(sentiment.getValue())) {
        continue;
      }
      if ("positive".equals(sentiment.getValue())) {
        continue;
      }
      if ("neutral".equals(sentiment.getValue())) {
        continue;
      }
      fail("unknown sentiment value found" + sentiment.getValue());
    }
  }

  @Test
  void messagingMessageWithNlpField_distance() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-distance"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(1, nlp.getEntities().size());
    NlpDistance distance = nlp.getEntities().get(0).as(NlpDistance.class);

    assertEquals("20", distance.getValue());
    assertEquals(0.960765D, distance.getConfidence(), 0.01);
    assertEquals("value", distance.getType());
    assertEquals("kilometre", distance.getUnit());
  }

  @Test
  void messagingMessageWithNlpField_duration() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-duration"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(6, nlp.getEntities().size());

    for (int i = 0; i < nlp.getEntities().size(); i++) {
      NlpDuration duration = nlp.getEntities().get(i).as(NlpDuration.class);

      assertEquals(String.valueOf(i + 1), duration.getValue());
      String unit = duration.getUnit();

      int multiplier = 1;
      if ("second".equals(unit)) {
        assertEquals(String.valueOf(i + 1), duration.getSecond());
        multiplier = 1;
      }
      if ("minute".equals(unit)) {
        assertEquals(String.valueOf(i + 1), duration.getMinute());
        multiplier = 60;
      }
      if ("hour".equals(unit)) {
        assertEquals(String.valueOf(i + 1), duration.getHour());
        multiplier = 60 * 60;
      }
      if ("day".equals(unit)) {
        assertEquals(String.valueOf(i + 1), duration.getDay());
        multiplier = 60 * 60 * 24;
      }
      if ("month".equals(unit)) {
        assertEquals(String.valueOf(i + 1), duration.getMonth());
        multiplier = 60 * 60 * 24 * 30;
      }
      if ("year".equals(unit)) {
        assertEquals(String.valueOf(i + 1), duration.getYear());
        multiplier = 60 * 60 * 24 * 365;
      }
      assertNotNull(duration.getNormalized());
      assertEquals("second", duration.getNormalized().getUnit());
      assertEquals(String.valueOf((i + 1) * multiplier), duration.getNormalized().getValue());
    }
  }

  @Test
  void messagingMessageWithNlpField_quantity() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-quantity"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(1, nlp.getEntities().size());
    NlpQuantity quantity = nlp.getEntities().get(0).as(NlpQuantity.class);

    assertEquals("3000", quantity.getValue());
    assertEquals(1.0D, quantity.getConfidence(), 0.01);
    assertEquals("value", quantity.getType());
    assertEquals("gram", quantity.getUnit());
  }

  @Test
  void messagingMessageWithNlpField_temperature() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-temperature"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(1, nlp.getEntities().size());
    NlpTemperature temperature = nlp.getEntities().get(0).as(NlpTemperature.class);

    assertEquals("26", temperature.getValue());
    assertEquals(0.96164833333333D, temperature.getConfidence(), 0.01);
    assertEquals("value", temperature.getType());
    assertEquals("celsius", temperature.getUnit());
  }

  @Test
  void messagingMessageWithNlpField_volume() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-volume"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(1, nlp.getEntities().size());
    NlpVolume volume = nlp.getEntities().get(0).as(NlpVolume.class);

    assertEquals("3", volume.getValue());
    assertEquals(1D, volume.getConfidence(), 0.01);
    assertEquals("value", volume.getType());
    assertEquals("litre", volume.getUnit());
  }

  @Test
  void messagingMessageWithNlpField_location() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-location"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);
    assertEquals(7, nlp.getEntities().size());
    NlpLocation nlpLocation = nlp.getEntities().get(0).as(NlpLocation.class);
    assertEquals("Los Angeles", nlpLocation.getValue());
    assertEquals(3, nlpLocation.getResolved().size());
    NlpLocation.Place place = nlpLocation.getResolved().get(0);
    assertNotNull(place);

    assertEquals("locality", place.getGrain());
    assertEquals("Los Angeles", place.getName());
    assertEquals("resolved", place.getType());
    assertEquals("America/Los_Angeles", place.getTimezoneAsString());
    assertEquals("Pacific Standard Time", place.getTimeZone().getDisplayName(Locale.US));

    NlpLocation.Coords coords = place.getCoords();
    assertEquals(34.052230834961, coords.getLatitude().doubleValue());
    assertEquals(-118.24368286133, coords.getLongitude().doubleValue());

    NlpLocation.External external = place.getExternal();
    assertEquals("5368361", external.getGeonames());
    assertEquals("Q65", external.getWikidata());
    assertEquals("Los Angeles", external.getWikipedia());
  }

  @Test
  void messagingMessageWithNlpField_error() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-nlp-error"), WebhookObject.class);
    assertNotNull(webhookObject);
    NlpResult nlp = getNlpResultFromWebhookObject(webhookObject);

    assertTrue(nlp.hasErrors());
    assertEquals(1, nlp.getErrors().size());
    assertEquals("msg-invalid", nlp.getErrors().get(0).getCode());
  }

  private NlpResult getNlpResultFromWebhookObject(WebhookObject webhookObject) {
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    assertFalse(entry.getMessaging().isEmpty());
    MessagingItem messagingItem = entry.getMessaging().get(0);
    assertTrue(messagingItem.isMessage());
    MessageItem messageItem = messagingItem.getMessage();
    assertNotNull(messageItem.getNlp());
    assertTrue(messageItem.hasNlp());
    return messageItem.getNlp();
  }

}
