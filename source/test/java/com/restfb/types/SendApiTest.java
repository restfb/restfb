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
import com.restfb.DefaultJsonMapper;
import com.restfb.types.send.*;
import com.restfb.types.send.Message;
import com.restfb.types.send.airline.AirlineCheckinTemplatePayload;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class SendApiTest extends AbstractJsonMapperTests {

  @Test
  public void recipientWithId() throws JSONException {
    IdMessageRecipient recipient = new IdMessageRecipient("12345");

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"id\":\"12345\"}", recipientJsonString, false);
  }

  @Test
  public void recipientWithPhone() throws JSONException {
    PhoneMessageRecipient recipient = new PhoneMessageRecipient("12345");

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"phone_number\":\"12345\"}", recipientJsonString, false);
  }

  @Test
  public void messageText() throws JSONException {
    Message recipient = new Message("Just a Test");

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"text\":\"Just a Test\"}", recipientJsonString, false);
  }

  @Test
  public void messageImageAttachment() throws JSONException {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.IMAGE, "IMAGE_URL");
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"attachment\":{\"payload\":{\"url\":\"IMAGE_URL\"},\"type\":\"image\"}}",
      recipientJsonString, false);
  }

  @Test
  public void messageVideoAttachment() throws JSONException {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.VIDEO, "VIDEO_URL");
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"attachment\":{\"payload\":{\"url\":\"VIDEO_URL\"},\"type\":\"video\"}}",
      recipientJsonString, false);
  }

  @Test
  public void messageAudioAttachment() throws JSONException {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.AUDIO, "AUDIO_URL");
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"attachment\":{\"payload\":{\"url\":\"AUDIO_URL\"},\"type\":\"audio\"}}",
      recipientJsonString, false);
  }

  @Test
  public void messageLocationAttachment() throws JSONException {
    LocationAttachment attachment = new LocationAttachment(20, 30);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals(
      "{\"attachment\":{\"payload\":{\"coordinates\":{\"lat\":20, \"long\":30}},\"type\":\"location\"}}",
      recipientJsonString, false);
  }

  @Test
  public void messageGenericAttachment() throws JSONException {

    ButtonTemplatePayload payload = new ButtonTemplatePayload();
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"attachment\":{\"payload\":{\"template_type\":\"button\"},\"type\":\"template\"}}",
      recipientJsonString, false);
  }

  @Test
  public void messageGenericButtonAttachment() throws JSONException {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    ButtonTemplatePayload payload = new ButtonTemplatePayload();
    payload.setText("MY TEXT");
    payload.addButton(button);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals(
      "{\"attachment\":{\"payload\":{\"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"}],\"template_type\":\"button\",\"text\":\"MY TEXT\"},\"type\":\"template\"}}",
      recipientJsonString, false);
  }

  @Test
  public void messageGenericButtonsAttachment() throws JSONException {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    PostbackButton postbackButton = new PostbackButton("My Postback", "POSTBACK");
    CallButton callButton = new CallButton("Call Support","+1234567890");
    ButtonTemplatePayload payload = new ButtonTemplatePayload();
    payload.addButton(button);
    payload.addButton(postbackButton);
    payload.addButton(callButton);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals(
      "{\"attachment\":{\"payload\":{\"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"},{\"payload\":\"POSTBACK\",\"type\":\"postback\",\"title\":\"My Postback\"},{\"payload\":\"+1234567890\",\"type\":\"phone_number\",\"title\":\"Call Support\"}],\"template_type\":\"button\"},\"type\":\"template\"}}",
      recipientJsonString, false);
  }

  @Test
  public void messageButtonsAttachment() throws JSONException {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    PostbackButton postbackButton = new PostbackButton("My Postback", "POSTBACK");
    CallButton callButton = new CallButton("Call Support", "+1234567890");
    GenericTemplatePayload payload = new GenericTemplatePayload();
    Bubble bubble = new Bubble("My Bubble");
    bubble.addButton(button);
    bubble.addButton(postbackButton);
    bubble.addButton(callButton);
    payload.addBubble(bubble);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"},{\"payload\":\"POSTBACK\",\"type\":\"postback\",\"title\":\"My Postback\"},{\"payload\":\"+1234567890\",\"type\":\"phone_number\",\"title\":\"Call Support\"}],\"title\":\"My Bubble\"}],\"template_type\":\"generic\"},\"type\":\"template\"}}",
      recipientJsonString, false);
  }

  @Test
  public void accountLink() throws JSONException {
    AccountLinkButton button = new AccountLinkButton("https://www.example.com/oauth/authorize");
    GenericTemplatePayload payload = new GenericTemplatePayload();
    Bubble bubble = new Bubble("Link Bubble");
    bubble.addButton(button);
    payload.addBubble(bubble);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message message = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String messageJsonString = mapper.toJson(message, true);

    JSONAssert.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"buttons\":[{\"type\":\"account_link\",\"url\":\"https://www.example.com/oauth/authorize\"}],\"title\":\"Link Bubble\"}],\"template_type\":\"generic\"},\"type\":\"template\"}}",
      messageJsonString, false);
  }

  @Test
  public void accountUnLink() throws JSONException {
    AccountUnlinkButton button = new AccountUnlinkButton();
    GenericTemplatePayload payload = new GenericTemplatePayload();
    Bubble bubble = new Bubble("Link Bubble");
    bubble.addButton(button);
    payload.addBubble(bubble);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message message = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String messageJsonString = mapper.toJson(message, true);

    JSONAssert.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"buttons\":[{\"type\":\"account_unlink\"}],\"title\":\"Link Bubble\"}],\"template_type\":\"generic\"},\"type\":\"template\"}}",
      messageJsonString, false);
  }

  @Test
  public void messageTemplateGenericBubbleAttachment() throws JSONException {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    GenericTemplatePayload payload = new GenericTemplatePayload();
    Bubble bubble = new Bubble("My Bubble");
    bubble.setSubtitle("subtitle");
    bubble.setItemUrl("ITEM_URL");
    bubble.setImageUrl("IMAGE_URL");
    bubble.addButton(button);
    payload.addBubble(bubble);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"}],\"image_url\":\"IMAGE_URL\",\"item_url\":\"ITEM_URL\",\"subtitle\":\"subtitle\",\"title\":\"My Bubble\"}],\"template_type\":\"generic\"},\"type\":\"template\"}}",
      recipientJsonString, false);
  }

  @Test
  public void messageTemplateAirlineAttachment() throws JSONException {

    AirlineCheckinTemplatePayload payload =
        new AirlineCheckinTemplatePayload("Intro Message", "en_US", "ABCDEF", "http://www.google.com/checkin");
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals(
      "{\"attachment\":{\"payload\":{\"checkin_url\":\"http://www.google.com/checkin\",\"intro_message\":\"Intro Message\",\"template_type\":\"airline_checkin\",\"locale\":\"en_US\",\"pnr_number\":\"ABCDEF\"},\"type\":\"template\"}}",
      recipientJsonString, false);
  }

  @Test
  public void messageWithQuickRepliesAndMetadata() throws JSONException {
    QuickReply quickReply1 = new QuickReply("text", "title 1", "payload 1");
    QuickReply quickReply2 = new QuickReply("text", "title 2", "payload 2");
    Message message = new Message("message text");
    message.setMetadata("metadata payload");
    message.addQuickReply(quickReply1);
    message.addQuickReply(quickReply2);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String messageJsonString = mapper.toJson(message, true);
    System.out.println(messageJsonString);

    JSONAssert.assertEquals(
      "{\"quick_replies\":[{\"payload\":\"payload 1\",\"title\":\"title 1\",\"content_type\":\"text\"},{\"payload\":\"payload 2\",\"title\":\"title 2\",\"content_type\":\"text\"}],\"metadata\":\"metadata payload\",\"text\":\"message text\"}",
      messageJsonString, false);
  }
}
