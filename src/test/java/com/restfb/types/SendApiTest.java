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
package com.restfb.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.DefaultJsonMapper;
import com.restfb.exception.FacebookPreconditionException;
import com.restfb.testutils.AssertJson;
import com.restfb.types.send.*;
import com.restfb.types.send.Message;
import com.restfb.types.send.airline.AirlineCheckinTemplatePayload;
import com.restfb.types.send.media.MediaTemplateAttachmentElement;
import com.restfb.types.send.media.MediaTemplateUrlElement;

import static org.junit.jupiter.api.Assertions.*;

class SendApiTest extends AbstractJsonMapperTests {

  @Test
  void recipientWithId() {
    IdMessageRecipient recipient = new IdMessageRecipient("12345");

    assertTrue(recipient instanceof MessageRecipient);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"id\":\"12345\"}", recipientJsonString);
  }

  @Test
  void recipientWithPhone() {
    PhoneMessageRecipient recipient = new PhoneMessageRecipient("12345");

    assertTrue(recipient instanceof MessageRecipient);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"phone_number\":\"12345\"}", recipientJsonString);
  }

  @Test
  void recipientWithPhoneAndName() {
    PhoneMessageRecipient recipient = new PhoneMessageRecipient("12345");
    recipient.setName(new PhoneMessageRecipient.Name("Jane", "Doe"));

    assertTrue(recipient instanceof MessageRecipient);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"phone_number\":\"12345\", \"name\":{\"first_name\":\"Jane\",\"last_name\":\"Doe\"}}",
      recipientJsonString);
  }

  @Test
  void recipientWithUserRef() {
    UserRefMessageRecipient recipient = new UserRefMessageRecipient("UNIQUE_USER_REF");

    assertTrue(recipient instanceof MessageRecipient);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"user_ref\":\"UNIQUE_USER_REF\"}", recipientJsonString);
  }

  @Test
  void messageText() {
    Message recipient = new Message("Just a Test");

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"text\":\"Just a Test\"}", recipientJsonString);
  }

  @Test
  void messageImageAttachment() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.IMAGE, "IMAGE_URL");
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"attachment\":{\"payload\":{\"url\":\"IMAGE_URL\"},\"type\":\"image\"}}",
      recipientJsonString);
  }

  @Test
  void messageVideoAttachment() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.VIDEO, "VIDEO_URL");
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"attachment\":{\"payload\":{\"url\":\"VIDEO_URL\"},\"type\":\"video\"}}",
      recipientJsonString);
  }

  @Test
  void messageMediaAttachment_imageURL() {
    MediaAttachment.MediaTemplateElement mediaTemplateElement =
        new MediaTemplateUrlElement("https://business.facebook.com/<PAGE_NAME>/photos/<NUMERIC_ID>");
    MediaAttachment attachment = new MediaAttachment(Collections.singletonList(mediaTemplateElement));

    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"template_type\":\"media\",\"elements\":[{\"media_type\":\"image\",\"url\":\"https://business.facebook.com/<PAGE_NAME>/photos/<NUMERIC_ID>\"}]},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageMediaAttachment_videoURL() {
    MediaAttachment.MediaTemplateElement mediaTemplateElement =
        new MediaTemplateUrlElement("https://business.facebook.com/<PAGE_NAME>/videos/<NUMERIC_ID>");
    MediaAttachment attachment = new MediaAttachment(Collections.singletonList(mediaTemplateElement));

    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"template_type\":\"media\",\"elements\":[{\"media_type\":\"video\",\"url\":\"https://business.facebook.com/<PAGE_NAME>/videos/<NUMERIC_ID>\"}]},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageMediaAttachment_imageAttachment() {
    MediaAttachment.MediaTemplateElement mediaTemplateElement =
        new MediaTemplateAttachmentElement(MediaAttachment.MediaType.IMAGE, "<ATTACHMENT_ID>");
    MediaAttachment attachment = new MediaAttachment(Collections.singletonList(mediaTemplateElement));

    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"template_type\":\"media\",\"elements\":[{\"media_type\":\"image\",\"attachment_id\":\"<ATTACHMENT_ID>\"}]},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageMediaAttachment_imageAttachmentWithButton() {
    WebButton button = new WebButton("Title", "<WEB_URL>");
    MediaAttachment.MediaTemplateElement mediaTemplateElement =
        new MediaTemplateAttachmentElement(MediaAttachment.MediaType.IMAGE, "<ATTACHMENT_ID>");
    mediaTemplateElement.addButton(button);
    MediaAttachment attachment = new MediaAttachment(Collections.singletonList(mediaTemplateElement));

    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"template_type\":\"media\",\"elements\":[{\"media_type\":\"image\",\"attachment_id\":\"<ATTACHMENT_ID>\",\"buttons\":[{\"url\":\"<WEB_URL>\",\"type\":\"web_url\",\"title\":\"Title\"}]}]},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageMediaAttachment_videoAttachment() {
    MediaAttachment.MediaTemplateElement mediaTemplateElement =
        new MediaTemplateAttachmentElement(MediaAttachment.MediaType.VIDEO, "<ATTACHMENT_ID>");
    MediaAttachment attachment = new MediaAttachment(Collections.singletonList(mediaTemplateElement));

    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"template_type\":\"media\",\"elements\":[{\"media_type\":\"video\",\"attachment_id\":\"<ATTACHMENT_ID>\"}]},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageAudioAttachment() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.AUDIO, "AUDIO_URL");
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"attachment\":{\"payload\":{\"url\":\"AUDIO_URL\"},\"type\":\"audio\"}}",
      recipientJsonString);
  }

  @Test
  void messageAudioAttachmentWithReuse() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.AUDIO, "AUDIO_URL");
    attachment.setIsReusable(true);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"url\":\"AUDIO_URL\",\"is_reusable\":true},\"type\":\"audio\"}}",
      recipientJsonString);
  }

  @Test
  void messageAudioAttachmentReuseId() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.AUDIO, "123456789");
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"attachment\":{\"payload\":{\"attachment_id\":\"123456789\"},\"type\":\"audio\"}}",
      recipientJsonString);
  }

  @Test
  void messageUploadAttachment() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.IMAGE);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"attachment\":{\"payload\":{},\"type\":\"image\"}}",
            recipientJsonString);
  }

  @Test
  void messageUploadAttachmentReusable() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.IMAGE);
    attachment.setIsReusable(true);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"attachment\":{\"payload\":{\"is_reusable\":true},\"type\":\"image\"}}",
            recipientJsonString);
  }

  @Test
  void messageLocationAttachment() {
    LocationAttachment attachment = new LocationAttachment(20, 30);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"coordinates\":{\"lat\":20, \"long\":30}},\"type\":\"location\"}}",
      recipientJsonString);
  }

  @Test
  void messageOpenGraphTemplate() {
    OpenGraphTemplatePayload payload = new OpenGraphTemplatePayload();
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals("{\"attachment\":{\"payload\":{\"template_type\":\"open_graph\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageOpenGraphTemplateWithButton() {
    WebButton button = new WebButton("Check this", "http://www.google.com");
    OpenGraphTemplatePayload payload = new OpenGraphTemplatePayload();
    OpenGraphTemplatePayload.Element element = new OpenGraphTemplatePayload.Element("some url");
    element.addButton(button);
    payload.addElement(element);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"url\":\"some url\",\"buttons\":[{\"url\":\"http://www.google.com\",\"type\":\"web_url\",\"title\":\"Check this\"}]}],\"template_type\":\"open_graph\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageGenericAttachment() {

    ButtonTemplatePayload payload = new ButtonTemplatePayload("TITLE");
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"text\":\"TITLE\", \"template_type\":\"button\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageGenericButtonAttachment() {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    ButtonTemplatePayload payload = new ButtonTemplatePayload("MY TEXT");
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
  void messageGenericButtonsAttachment() {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    WebButton buttonHeight = new WebButton("Check this", "http://www.google.com");
    buttonHeight.setWebviewHeightRatio(WebviewHeightEnum.compact);
    PostbackButton postbackButton = new PostbackButton("My Postback", "POSTBACK");
    CallButton callButton = new CallButton("Call Support", "+1234567890");
    ButtonTemplatePayload payload = new ButtonTemplatePayload("TITLE");
    payload.addButton(button);
    payload.addButton(buttonHeight);
    payload.addButton(postbackButton);
    payload.addButton(callButton);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"text\":\"TITLE\", \"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"},{\"webview_height_ratio\":\"compact\",\"url\":\"http://www.google.com\",\"type\":\"web_url\",\"title\":\"Check this\"},{\"payload\":\"POSTBACK\",\"type\":\"postback\",\"title\":\"My Postback\"},{\"payload\":\"+1234567890\",\"type\":\"phone_number\",\"title\":\"Call Support\"}],\"template_type\":\"button\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageButtonsAttachment() {

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

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"},{\"payload\":\"POSTBACK\",\"type\":\"postback\",\"title\":\"My Postback\"},{\"payload\":\"+1234567890\",\"type\":\"phone_number\",\"title\":\"Call Support\"}],\"title\":\"My Bubble\"}],\"template_type\":\"generic\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageButtonsAttachmentNonSharable() {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    PostbackButton postbackButton = new PostbackButton("My Postback", "POSTBACK");
    CallButton callButton = new CallButton("Call Support", "+1234567890");
    GenericTemplatePayload payload = new GenericTemplatePayload();
    payload.setSharable(false);
    Bubble bubble = new Bubble("My Bubble");
    bubble.addButton(button);
    bubble.addButton(postbackButton);
    bubble.addButton(callButton);
    payload.addBubble(bubble);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"title\":\"My Bubble\",\"buttons\":[{\"url\":\"http://www.google.com\",\"type\":\"web_url\",\"title\":\"Check this\"},{\"payload\":\"POSTBACK\",\"type\":\"postback\",\"title\":\"My Postback\"},{\"payload\":\"+1234567890\",\"type\":\"phone_number\",\"title\":\"Call Support\"}]}],\"sharable\":false,\"template_type\":\"generic\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageListTemplatePayload() {
    ListViewElement element1 = getListViewElement(100, "Classic White T-Shirt");
    ListViewElement element2 = getListViewElement(101, "Classic Blue T-Shirt");

    PostbackButton postbackButton = new PostbackButton("View More", "payload");

    List<ListViewElement> listViewElementList = new ArrayList<>();
    listViewElementList.add(element1);
    listViewElementList.add(element2);
    ListTemplatePayload payload = new ListTemplatePayload(listViewElementList);
    payload.setTopElementStyle(ListTemplatePayload.TopElementStyleEnum.compact);
    payload.addButton(postbackButton);

    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    String expectedJson = "{\"attachment\":{\"payload\":{\"elements\":[{\"title\":\"Classic White T-Shirt\","
        + "\"subtitle\":\"100% Cotton, 200% Comfortable\","
        + "\"image_url\":\"https://peterssendreceiveapp.ngrok.io/img/white-t-shirt.png\"," + "\"default_action\":"
        + "{\"webview_height_ratio\":\"tall\","
        + "\"url\":\"https://peterssendreceiveapp.ngrok.io/view?item=100\",\"messenger_extensions\":true,"
        + "\"fallback_url\":\"https://peterssendreceiveapp.ngrok.io/\",\"type\":\"web_url\"},"
        + "\"buttons\":[{\"webview_height_ratio\":\"tall\","
        + "\"url\":\"https://peterssendreceiveapp.ngrok.io/shop?item=100\",\"messenger_extensions\":true,"
        + "\"fallback_url\":\"https://peterssendreceiveapp.ngrok.io/\",\"type\":\"web_url\","
        + "\"title\":\"Buy\"}]},{\"title\":\"Classic Blue T-Shirt\",\"subtitle\":\"100% Cotton, 200% Comfortable\","
        + "\"image_url\":\"https://peterssendreceiveapp.ngrok.io/img/white-t-shirt.png\",\"default_action\":"
        + "{\"webview_height_ratio\":\"tall\",\"url\":\"https://peterssendreceiveapp.ngrok.io/view?item=101\","
        + "\"messenger_extensions\":true,\"fallback_url\":\"https://peterssendreceiveapp.ngrok.io/\",\"type\":"
        + "\"web_url\"},\"buttons\":[{\"webview_height_ratio\":\"tall\","
        + "\"url\":\"https://peterssendreceiveapp.ngrok.io/shop?item=101\",\"messenger_extensions\":true,"
        + "\"fallback_url\":\"https://peterssendreceiveapp.ngrok.io/\",\"type\":\"web_url\",\"title\":\"Buy\"}]}],"
        + "\"top_element_style\":\"compact\",\"buttons\":[{\"payload\":\"payload\",\"type\":\"postback\","
        + "\"title\":\"View More\"}],\"template_type\":\"list\"},\"type\":\"template\"}}";
    AssertJson.assertEquals(expectedJson, recipientJsonString);
  }

  private ListViewElement getListViewElement(int itemId, String title) {
    DefaultAction defaultAction = new DefaultAction("https://peterssendreceiveapp.ngrok.io/view?item=" + itemId);
    defaultAction.setMessengerExtensions(true, "https://peterssendreceiveapp.ngrok.io/");
    defaultAction.setWebviewHeightRatio(WebviewHeightEnum.tall);

    WebButton webButton = new WebButton("Buy", "https://peterssendreceiveapp.ngrok.io/shop?item=" + itemId);
    webButton.setMessengerExtensions(true, "https://peterssendreceiveapp.ngrok.io/");
    webButton.setWebviewHeightRatio(WebviewHeightEnum.tall);

    ListViewElement element = new ListViewElement(title);
    element.setSubtitle("100% Cotton, 200% Comfortable");
    element.setImageUrl("https://peterssendreceiveapp.ngrok.io/img/white-t-shirt.png");
    element.setDefaultAction(defaultAction);
    element.addButton(webButton);
    return element;
  }

  @Test
  void accountLink() {
    AccountLinkButton button = new AccountLinkButton("https://www.example.com/oauth/authorize");
    GenericTemplatePayload payload = new GenericTemplatePayload();
    Bubble bubble = new Bubble("Link Bubble");
    bubble.addButton(button);
    payload.addBubble(bubble);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message message = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String messageJsonString = mapper.toJson(message, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"buttons\":[{\"type\":\"account_link\",\"url\":\"https://www.example.com/oauth/authorize\"}],\"title\":\"Link Bubble\"}],\"template_type\":\"generic\"},\"type\":\"template\"}}",
      messageJsonString);
  }

  @Test
  void accountUnLink() {
    AccountUnlinkButton button = new AccountUnlinkButton();
    GenericTemplatePayload payload = new GenericTemplatePayload();
    Bubble bubble = new Bubble("Link Bubble");
    bubble.addButton(button);
    payload.addBubble(bubble);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message message = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String messageJsonString = mapper.toJson(message, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"buttons\":[{\"type\":\"account_unlink\"}],\"title\":\"Link Bubble\"}],\"template_type\":\"generic\"},\"type\":\"template\"}}",
      messageJsonString);
  }

  @Test
  void messageTemplateGenericBubbleAttachment() {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    ShareButton share = new ShareButton();
    GenericTemplatePayload payload = new GenericTemplatePayload();
    Bubble bubble = new Bubble("My Bubble");
    bubble.setSubtitle("subtitle");
    bubble.setItemUrl("ITEM_URL");
    bubble.setImageUrl("IMAGE_URL");
    bubble.addButton(button);
    bubble.addButton(share);
    payload.addBubble(bubble);
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"}, {\"type\":\"element_share\"}],\"image_url\":\"IMAGE_URL\",\"item_url\":\"ITEM_URL\",\"subtitle\":\"subtitle\",\"title\":\"My Bubble\"}],\"template_type\":\"generic\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageTemplateAirlineAttachment() {

    AirlineCheckinTemplatePayload payload =
        new AirlineCheckinTemplatePayload("Intro Message", "en_US", "ABCDEF", "http://www.google.com/checkin");
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    assertEquals(Collections.<QuickReply> emptyList(), recipient.getQuickReplies());

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    AssertJson.assertEquals(
      "{\"attachment\":{\"payload\":{\"checkin_url\":\"http://www.google.com/checkin\",\"intro_message\":\"Intro Message\",\"template_type\":\"airline_checkin\",\"locale\":\"en_US\",\"pnr_number\":\"ABCDEF\"},\"type\":\"template\"}}",
      recipientJsonString);
  }

  @Test
  void messageWithQuickRepliesAndMetadata() {
    QuickReply quickReply1 = new QuickReply("title 1", "payload 1");
    QuickReply quickReply2 = new QuickReply("title 2", "payload 2");
    quickReply2.setImageUrl("http://example.org/test.jpg");
    QuickReply quickReply5 = new QuickReply(QuickReply.QuickReplyType.USER_EMAIL);
    QuickReply quickReply6 = new QuickReply(QuickReply.QuickReplyType.USER_PHONE_NUMBER);
    Message message = new Message("message text");
    message.setMetadata("metadata payload");
    message.addQuickReply(quickReply1);
    message.addQuickReply(quickReply2);
    message.addQuickReply(quickReply5);
    message.addQuickReply(quickReply6);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String messageJsonString = mapper.toJson(message, true);

    AssertJson.assertEquals(
      "{\"text\":\"message text\",\"quick_replies\":[{\"content_type\":\"text\",\"title\":\"title 1\",\"payload\":\"payload 1\"},{\"content_type\":\"text\",\"title\":\"title 2\",\"payload\":\"payload 2\",\"image_url\":\"http://example.org/test.jpg\"},{\"content_type\":\"user_email\"},{\"content_type\":\"user_phone_number\"}],\"metadata\":\"metadata payload\"}",
      messageJsonString);
  }

  @Test
  void messageWithMultipleQuickRepliesAddedAtOnce() {
    List<QuickReply> quickReplyList = new ArrayList<>();
    quickReplyList.add(new QuickReply("title1", "payload 1"));
    quickReplyList.add(new QuickReply("title2", "payload 2"));
    Message message = new Message("message text");
    message.addQuickReplies(quickReplyList);

    assertEquals(quickReplyList, message.getQuickReplies());

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String messageJsonString = mapper.toJson(message, true);

    AssertJson.assertEquals(
      "{\"text\":\"message text\",\"quick_replies\":[{\"content_type\":\"text\",\"title\":\"title1\",\"payload\":\"payload 1\"},{\"content_type\":\"text\",\"title\":\"title2\",\"payload\":\"payload 2\"}]}",
      messageJsonString);
  }

  @Test
  void messageWithTooManyReplies_thirdteenPresentOneAdded() {
    Message message = new Message("message text");
    List<QuickReply> quickReplyList = new ArrayList<>();
    quickReplyList.add(new QuickReply("title1", "payload 1"));
    quickReplyList.add(new QuickReply("title2", "payload 2"));
    quickReplyList.add(new QuickReply("title3", "payload 3"));
    quickReplyList.add(new QuickReply("title4", "payload 4"));
    quickReplyList.add(new QuickReply("title5", "payload 5"));
    quickReplyList.add(new QuickReply("title6", "payload 6"));
    quickReplyList.add(new QuickReply("title7", "payload 7"));
    quickReplyList.add(new QuickReply("title8", "payload 8"));
    quickReplyList.add(new QuickReply("title9", "payload 9"));
    quickReplyList.add(new QuickReply("title10", "payload 10"));
    quickReplyList.add(new QuickReply("title11", "payload 11"));
    quickReplyList.add(new QuickReply("title12", "payload 12"));
    quickReplyList.add(new QuickReply("title13", "payload 13"));
    message.addQuickReplies(quickReplyList);
    assertThrows(FacebookPreconditionException.class,
      () -> message.addQuickReply(new QuickReply("last", "payload_last")));
  }

  @Test
  void messageWithTooManyReplies_TenPresentFourAdded() {
    Message message = new Message("message text");
    List<QuickReply> quickReplyList = new ArrayList<>();
    quickReplyList.add(new QuickReply("title1", "payload 1"));
    quickReplyList.add(new QuickReply("title2", "payload 2"));
    quickReplyList.add(new QuickReply("title3", "payload 3"));
    quickReplyList.add(new QuickReply("title4", "payload 4"));
    quickReplyList.add(new QuickReply("title5", "payload 5"));
    quickReplyList.add(new QuickReply("title6", "payload 6"));
    quickReplyList.add(new QuickReply("title7", "payload 7"));
    quickReplyList.add(new QuickReply("title8", "payload 8"));
    quickReplyList.add(new QuickReply("title9", "payload 9"));
    quickReplyList.add(new QuickReply("title10", "payload 10"));

    List<QuickReply> quickReplyListAdded = new ArrayList<>();
    quickReplyListAdded.add(new QuickReply("title1a", "payload 1a"));
    quickReplyListAdded.add(new QuickReply("title2a", "payload 2a"));
    quickReplyListAdded.add(new QuickReply("title3a", "payload 3a"));
    quickReplyListAdded.add(new QuickReply("title3a", "payload 4a"));
    message.addQuickReplies(quickReplyList);
    assertThrows(FacebookPreconditionException.class, () -> message.addQuickReplies(quickReplyListAdded));
  }
}
