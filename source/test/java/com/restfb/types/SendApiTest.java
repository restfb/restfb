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
import com.restfb.testutils.AssertJson;
import com.restfb.types.send.*;
import com.restfb.types.send.Message;

import org.junit.Test;

public class SendApiTest extends AbstractJsonMapperTests {

  @Test
  public void recipientWithId() {
    IdMessageRecipient recipient = new IdMessageRecipient("12345");

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"id\":\"12345\"}", recipientJsonString);
  }

  @Test
  public void recipientWithPhone() {
    PhoneMessageRecipient recipient = new PhoneMessageRecipient("12345");

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"phone_number\":\"12345\"}", recipientJsonString);
  }

  @Test
  public void messageText() {
    Message recipient = new Message("Just a Test");

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"text\":\"Just a Test\"}", recipientJsonString);
  }

  @Test
  public void messageImageAttachment() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.IMAGE, "IMAGE_URL");
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"attachment\":{\"payload\":{\"url\":\"IMAGE_URL\"},\"type\":\"image\"}}",
      recipientJsonString);
  }

  @Test
  public void messageVideoAttachment() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.VIDEO, "VIDEO_URL");
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"attachment\":{\"payload\":{\"url\":\"VIDEO_URL\"},\"type\":\"video\"}}",
            recipientJsonString);
  }

  @Test
  public void messageAudioAttachment() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.AUDIO, "AUDIO_URL");
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"attachment\":{\"payload\":{\"url\":\"AUDIO_URL\"},\"type\":\"audio\"}}",
            recipientJsonString);
  }

  @Test
  public void messageGenericAttachment() {

    ButtonTemplatePayload payload = new ButtonTemplatePayload();
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"attachment\":{\"payload\":{\"template_type\":\"button\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  public void messageGenericButtonAttachment() {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    ButtonTemplatePayload payload = new ButtonTemplatePayload();
    payload.setText("MY TEXT");
    payload.addButton(button);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"}],\"template_type\":\"button\",\"text\":\"MY TEXT\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  public void messageGenericButtonsAttachment() {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    PostbackButton postbackButton = new PostbackButton("My Postback", "POSTBACK");
    ButtonTemplatePayload payload = new ButtonTemplatePayload();
    payload.addButton(button);
    payload.addButton(postbackButton);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"},{\"payload\":\"POSTBACK\",\"type\":\"postback\",\"title\":\"My Postback\"}],\"template_type\":\"button\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  public void messageButtonsAttachment() {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    PostbackButton postbackButton = new PostbackButton("My Postback", "POSTBACK");
    GenericTemplatePayload payload = new GenericTemplatePayload();
    Bubble bubble = new Bubble("My Bubble");
    bubble.addButton(button);
    bubble.addButton(postbackButton);
    payload.addBubble(bubble);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"},{\"payload\":\"POSTBACK\",\"type\":\"postback\",\"title\":\"My Postback\"}],\"title\":\"My Bubble\"}],\"template_type\":\"generic\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  public void messageTemplateGenericBubbleAttachment() {

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

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"}],\"image_url\":\"IMAGE_URL\",\"item_url\":\"ITEM_URL\",\"subtitle\":\"subtitle\",\"title\":\"My Bubble\"}],\"template_type\":\"generic\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

}
