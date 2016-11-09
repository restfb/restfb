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

import static org.junit.Assert.assertTrue;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.DefaultJsonMapper;
import com.restfb.exception.FacebookPreconditionException;
import com.restfb.types.send.*;
import com.restfb.types.send.Message;
import com.restfb.types.send.airline.AirlineCheckinTemplatePayload;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.ArrayList;
import java.util.List;

public class SendApiTest extends AbstractJsonMapperTests {

  @Test
  public void recipientWithId() throws JSONException {
    IdMessageRecipient recipient = new IdMessageRecipient("12345");

    assertTrue(recipient instanceof MessageRecipient);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"id\":\"12345\"}", recipientJsonString, false);
  }

  @Test
  public void recipientWithPhone() throws JSONException {
    PhoneMessageRecipient recipient = new PhoneMessageRecipient("12345");

    assertTrue(recipient instanceof MessageRecipient);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"phone_number\":\"12345\"}", recipientJsonString, false);
  }

  @Test
  public void recipientWithUserRef() throws JSONException {
    UserRefMessageRecipient recipient = new UserRefMessageRecipient("UNIQUE_USER_REF");

    assertTrue(recipient instanceof MessageRecipient);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"user_ref\":\"UNIQUE_USER_REF\"}", recipientJsonString, false);
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
  public void messageAudioAttachmentWithReuse() throws JSONException {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.AUDIO, "AUDIO_URL");
    attachment.setIsReusable(true);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals(
      "{\"attachment\":{\"payload\":{\"url\":\"AUDIO_URL\",\"is_reusable\":true},\"type\":\"audio\"}}",
      recipientJsonString, false);
  }

  @Test
  public void messageAudioAttachmentReuseId() throws JSONException {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.AUDIO, "123456789");
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"attachment\":{\"payload\":{\"attachment_id\":\"123456789\"},\"type\":\"audio\"}}",
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

    ButtonTemplatePayload payload = new ButtonTemplatePayload("TITLE");
    TemplateAttachment attachment = new TemplateAttachment(payload);
    Message recipient = new Message(attachment);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String recipientJsonString = mapper.toJson(recipient, true);

    JSONAssert.assertEquals("{\"attachment\":{\"payload\":{\"text\":\"TITLE\", \"template_type\":\"button\"},\"type\":\"template\"}}",
      recipientJsonString, false);
  }

  @Test
  public void messageGenericButtonAttachment() throws JSONException {

    WebButton button = new WebButton("Check this", "http://www.google.com");
    ButtonTemplatePayload payload = new ButtonTemplatePayload("MY TEXT");
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

    JSONAssert.assertEquals(
      "{\"attachment\":{\"payload\":{\"text\":\"TITLE\", \"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"},{\"webview_height_ratio\":\"compact\",\"url\":\"http://www.google.com\",\"type\":\"web_url\",\"title\":\"Check this\"},{\"payload\":\"POSTBACK\",\"type\":\"postback\",\"title\":\"My Postback\"},{\"payload\":\"+1234567890\",\"type\":\"phone_number\",\"title\":\"Call Support\"}],\"template_type\":\"button\"},\"type\":\"template\"}}",
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
  public void messageListTemplatePayload() throws JSONException {
    ListViewElement element1 = getListViewElement(100, "Classic White T-Shirt");
    ListViewElement element2 = getListViewElement(101, "Classic Blue T-Shirt");

    PostbackButton postbackButton = new PostbackButton("View More", "payload");

    List<ListViewElement> listViewElementList = new ArrayList<ListViewElement>();
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
    JSONAssert.assertEquals(expectedJson, recipientJsonString, false);
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

    JSONAssert.assertEquals(
      "{\"attachment\":{\"payload\":{\"elements\":[{\"buttons\":[{\"type\":\"web_url\",\"title\":\"Check this\",\"url\":\"http://www.google.com\"}, {\"type\":\"element_share\"}],\"image_url\":\"IMAGE_URL\",\"item_url\":\"ITEM_URL\",\"subtitle\":\"subtitle\",\"title\":\"My Bubble\"}],\"template_type\":\"generic\"},\"type\":\"template\"}}",
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
    QuickReply quickReply1 = new QuickReply("title 1", "payload 1");
    QuickReply quickReply2 = new QuickReply("title 2", "payload 2");
    quickReply2.setImageUrl("http://example.org/test.jpg");
    QuickReply quickReply3 = new QuickReply();
    QuickReply quickReply4 = new QuickReply();
    quickReply4.setImageUrl("http://example.org/test2.jpg");
    Message message = new Message("message text");
    message.setMetadata("metadata payload");
    message.addQuickReply(quickReply1);
    message.addQuickReply(quickReply2);
    message.addQuickReply(quickReply3);
    message.addQuickReply(quickReply4);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String messageJsonString = mapper.toJson(message, true);

    JSONAssert.assertEquals(
      "{\"quick_replies\":[{\"payload\":\"payload 1\",\"title\":\"title 1\",\"content_type\":\"text\"},{\"content_type\":\"text\",\"title\":\"title 2\",\"payload\":\"payload 2\",\"image_url\":\"http://example.org/test.jpg\"},{\"content_type\":\"location\"},{\"content_type\":\"location\",\"image_url\":\"http://example.org/test2.jpg\"}],\"metadata\":\"metadata payload\",\"text\":\"message text\"}",
      messageJsonString, false);
  }

  @Test
  public void messageWithMultipleQuickRepliesAddedAtOnce() throws JSONException {
    List<QuickReply> quickReplyList = new ArrayList<QuickReply>();
    quickReplyList.add(new QuickReply("title1", "payload 1"));
    quickReplyList.add(new QuickReply("title2", "payload 2"));
    Message message = new Message("message text");
    message.addQuickReplies(quickReplyList);

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String messageJsonString = mapper.toJson(message, true);

    JSONAssert.assertEquals(
      "{\"text\":\"message text\",\"quick_replies\":[{\"content_type\":\"text\",\"title\":\"title1\",\"payload\":\"payload 1\"},{\"content_type\":\"text\",\"title\":\"title2\",\"payload\":\"payload 2\"}]}",
      messageJsonString, false);
  }

  @Test(expected = FacebookPreconditionException.class)
  public void messageWithTooManyReplies_tenPresentOneAdded() {
    Message message = new Message("message text");
    List<QuickReply> quickReplyList = new ArrayList<QuickReply>();
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
    message.addQuickReplies(quickReplyList);
    message.addQuickReply(new QuickReply("last", "payload_last"));
  }

  @Test(expected = FacebookPreconditionException.class)
  public void messageWithTooManyReplies_EightPresentThreeAdded() {
    Message message = new Message("message text");
    List<QuickReply> quickReplyList = new ArrayList<QuickReply>();
    quickReplyList.add(new QuickReply("title1", "payload 1"));
    quickReplyList.add(new QuickReply("title2", "payload 2"));
    quickReplyList.add(new QuickReply("title3", "payload 3"));
    quickReplyList.add(new QuickReply("title4", "payload 4"));
    quickReplyList.add(new QuickReply("title5", "payload 5"));
    quickReplyList.add(new QuickReply("title6", "payload 6"));
    quickReplyList.add(new QuickReply("title7", "payload 7"));
    quickReplyList.add(new QuickReply("title8", "payload 8"));

    List<QuickReply> quickReplyListAdded = new ArrayList<QuickReply>();
    quickReplyListAdded.add(new QuickReply("title1a", "payload 1a"));
    quickReplyListAdded.add(new QuickReply("title2a", "payload 2a"));
    quickReplyListAdded.add(new QuickReply("title3a", "payload 3a"));
    message.addQuickReplies(quickReplyList);
    message.addQuickReplies(quickReplyListAdded);
  }
}
