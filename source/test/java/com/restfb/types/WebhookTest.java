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

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.Parameter;
import com.restfb.types.send.SenderActionEnum;
import com.restfb.types.webhook.*;
import com.restfb.types.webhook.base.AbstractFeedPostValue;
import com.restfb.types.webhook.base.BaseChangeValue;
import com.restfb.types.webhook.messaging.*;
import com.restfb.types.webhook.messaging.payment.Amount;
import com.restfb.types.webhook.messaging.payment.PaymentCredential;
import com.restfb.types.webhook.messaging.payment.ReuqestedUserInfo;
import com.restfb.types.webhook.messaging.payment.ShippingAddress;

import org.junit.Test;

import java.util.Arrays;

public class WebhookTest extends AbstractJsonMapperTests {

  private static final String ITEM_ALBUM = "album";
  private static final String ITEM_COMMENT = "comment";
  private static final String ITEM_LIKE = "like";
  private static final String ITEM_PHOTO = "photo";
  private static final String ITEM_POST = "post";
  private static final String ITEM_REACTION = "reaction";
  private static final String ITEM_SHARE = "share";
  private static final String ITEM_STATUS = "status";
  private static final String ITEM_RATING = "rating";
  private static final String ITEM_VIDEO = "video";
  private static final String FIELD_FEED = "feed";
  private static final String FIELD_RATINGS = "ratings";
  private static final String FIELD_CONVERSATIONS = "conversations";

  @Test
  public void feedAlbumAdd() {
    FeedAlbumAddValue value =
        openAndCheckFeedPostBasics("feed-album-add", FeedAlbumAddValue.class, ITEM_ALBUM, ChangeValue.Verb.ADD);
    assertEquals("900767076685784", value.getAlbumId());
    assertTrue(value.getPublished());
    assertEquals(1467894325000L, value.getCreatedTime().getTime());
    assertEquals(Arrays.asList("900767076685784", "900767076685785", "900767076685786"), value.getPhotoIds());
  }

  @Test
  public void feedAlbumEdited() {
    FeedAlbumEditedValue value = openAndCheckFeedPostBasics("feed-album-edited", FeedAlbumEditedValue.class, ITEM_ALBUM,
      ChangeValue.Verb.EDITED);
    assertEquals("900767076685784", value.getAlbumId());
    assertFalse(value.getPublished());
    assertEquals(1470322909000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedCommentAdd() {
    FeedCommentValue value =
        openAndCheckFeedPostBasics("feed-comment-add-25", FeedCommentValue.class, ITEM_COMMENT, ChangeValue.Verb.ADD);
    assertFalse(value.isReply());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("901097836652708_903438993085259", value.getCommentId());
    assertEquals(1449135003000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedCommentEdited() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-edited-25", FeedCommentValue.class, ITEM_COMMENT,
      ChangeValue.Verb.EDITED);
    assertFalse(value.isReply());
    assertEquals("Let's see the Webhooks in action :D", value.getMessage());
    assertEquals("1234567890321_900728623356296", value.getPostId());
    assertEquals("1234567890321_900728623356296", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1448555737000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedCommentHide() {
    FeedCommentValue value =
        openAndCheckFeedPostBasics("feed-comment-hide", FeedCommentValue.class, ITEM_COMMENT, ChangeValue.Verb.HIDE);
    assertFalse(value.isReply());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1467894562000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedCommentUnhide() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-unhide", FeedCommentValue.class, ITEM_COMMENT,
      ChangeValue.Verb.UNHIDE);
    assertFalse(value.isReply());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1467894562000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedCommentRemove() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-remove-25", FeedCommentValue.class, ITEM_COMMENT,
      ChangeValue.Verb.REMOVE);
    assertFalse(value.isReply());
    assertEquals("1234567890321_905578656204626", value.getPostId());
    assertEquals("1234567890321_905578656204626", value.getParentId());
    assertEquals("905578656204626_905579239537901", value.getCommentId());
    assertNull(value.getCreatedTime());
  }

  @Test
  public void feedPostAdd() {
    FeedPostValue value =
        openAndCheckFeedPostBasics("feed-post-add-25", FeedPostValue.class, ITEM_POST, ChangeValue.Verb.ADD);
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("8423678347823", value.getSenderId());
    assertFalse(value.isHidden());
    assertEquals("Let's check this", value.getMessage());
  }

  @Test
  public void feedPostHide() {
    FeedPostValue value =
        openAndCheckFeedPostBasics("feed-post-hide", FeedPostValue.class, ITEM_POST, ChangeValue.Verb.HIDE);
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("8423678347823", value.getSenderId());
    assertEquals("Let's check this", value.getMessage());
  }

  @Test
  public void feedPostRemove() {
    FeedPostValue value =
        openAndCheckFeedPostBasics("feed-post-remove-25", FeedPostValue.class, ITEM_POST, ChangeValue.Verb.REMOVE);
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321", value.getSenderId());
    assertEquals(1452098986000L, value.getCreatedTime().getTime());
    assertEquals("1234567890321", value.getRecipientId());
  }

  @Test
  public void feedPostUnhide() {
    FeedPostValue value =
        openAndCheckFeedPostBasics("feed-post-unhide", FeedPostValue.class, ITEM_POST, ChangeValue.Verb.UNHIDE);
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("8423678347823", value.getSenderId());
    assertEquals("Let's check this", value.getMessage());
  }

  @Test
  public void feedReactionAdd() {
    FeedReactionValue value =
        openAndCheckFeedPostBasics("feed-reaction-add", FeedReactionValue.class, ITEM_REACTION, ChangeValue.Verb.ADD);
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("1234567890321", value.getSenderId());
  }

  @Test
  public void feedShareAdd() {
    FeedShareValue value =
        openAndCheckFeedPostBasics("feed-share-add-25", FeedShareValue.class, ITEM_SHARE, ChangeValue.Verb.ADD);
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321", value.getSenderId());
    assertTrue(value.getPublished().booleanValue());
    assertNotNull(value.getMessage());
    assertEquals("http://www.google.com/", value.getLink());
    assertEquals("98735342324352", value.getShareId());
  }

  @Test
  public void feedShareHide() {
    FeedShareValue value =
        openAndCheckFeedPostBasics("feed-share-hide", FeedShareValue.class, ITEM_SHARE, ChangeValue.Verb.HIDE);
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321", value.getSenderId());
    assertEquals("Let me google that for you", value.getMessage());
    assertEquals("http://www.google.com/", value.getLink());
    assertEquals("98735342324352", value.getShareId());
  }

  @Test
  public void feedShareUnhide() {
    FeedShareValue value =
        openAndCheckFeedPostBasics("feed-share-unhide", FeedShareValue.class, ITEM_SHARE, ChangeValue.Verb.UNHIDE);
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321", value.getSenderId());
    assertEquals("Let me google that for you", value.getMessage());
    assertEquals("http://www.google.com/", value.getLink());
    assertEquals("98735342324352", value.getShareId());
  }

  @Test
  public void feedStatusEdited() {
    FeedStatusValue value = openAndCheckFeedPostBasics("feed-status-edited-25", FeedStatusValue.class, ITEM_STATUS,
      ChangeValue.Verb.EDITED);
    assertEquals("edited", value.getVerbAsString());
    assertEquals("Tester", value.getSenderName());
    assertEquals("status", value.getItem());
    assertEquals("This is an edited test message", value.getMessage());
    assertTrue(value.getPublished());
  }

  @Test
  public void feedStatusAdd() {
    FeedStatusValue value =
        openAndCheckFeedPostBasics("feed-status-add-25", FeedStatusValue.class, ITEM_STATUS, ChangeValue.Verb.ADD);
    assertTrue(value.getPublished().booleanValue());
    assertEquals("1234567890321_930145403745849", value.getPostId());
    assertEquals("Tester", value.getSenderName());
    assertEquals(1448633038000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedStatusHide() {
    FeedStatusValue value =
        openAndCheckFeedPostBasics("feed-status-hide-25", FeedStatusValue.class, ITEM_STATUS, ChangeValue.Verb.HIDE);
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("Some Tester", value.getSenderName());
  }

  @Test
  public void feedStatusUnhide() {
    FeedStatusValue value =
        openAndCheckFeedPostBasics("feed-status-unhide", FeedStatusValue.class, ITEM_STATUS, ChangeValue.Verb.UNHIDE);
    assertEquals("123456789_64352426", value.getPostId());
    assertEquals("Tester", value.getSenderName());
  }

  @Test
  public void feedLikeAdd() {
    FeedLikeValue value =
        openAndCheckFeedPostBasics("feed-like-add-25", FeedLikeValue.class, ITEM_LIKE, ChangeValue.Verb.ADD);
    assertFalse(value.isPageLike());
    assertEquals("909653922461664_940663242694065", value.getParentId());
    assertNull(value.getUserId());
    assertEquals("909653922461664_940663242694065", value.getPostId());
  }

  @Test
  public void feedLikeAddPage() {
    FeedLikeValue value =
        openAndCheckFeedPostBasics("feed-like-add-page-25", FeedLikeValue.class, ITEM_LIKE, ChangeValue.Verb.ADD);
    assertTrue(value.isPageLike());
    assertEquals("10201978818232600", value.getUserId());
  }

  @Test
  public void feedLikeRemove() {
    FeedLikeValue value =
        openAndCheckFeedPostBasics("feed-like-remove-25", FeedLikeValue.class, ITEM_LIKE, ChangeValue.Verb.REMOVE);
    assertEquals("1234567890321_940663242694065", value.getParentId());
  }

  @Test
  public void feedPhotoAdd() {
    FeedPhotoAddValue value =
        openAndCheckFeedPostBasics("feed-photo-add-25", FeedPhotoAddValue.class, ITEM_PHOTO, ChangeValue.Verb.ADD);
    assertTrue(value.getPublished());
    assertEquals("https://www.example.org/test.jpg", value.getLink());
    assertEquals("900767076685784", value.getPhotoId());
  }

  @Test
  public void feedPhotoEdited() {
    FeedPhotoAddValue value =
        openAndCheckFeedPostBasics("feed-photo-edited", FeedPhotoAddValue.class, ITEM_PHOTO, ChangeValue.Verb.EDITED);
    assertTrue(value.getPublished());
    assertEquals("https://www.example.org/test.jpg", value.getLink());
    assertEquals("900767076685784", value.getPhotoId());
  }

  @Test
  public void feedPhotoHide() {
    FeedPhotoAddValue value =
        openAndCheckFeedPostBasics("feed-photo-hide", FeedPhotoAddValue.class, ITEM_PHOTO, ChangeValue.Verb.HIDE);
    assertEquals(ChangeValue.Verb.HIDE, value.getVerb());
    assertEquals("https://www.example.org/test.jpg", value.getLink());
    assertEquals("1063068383729088", value.getPhotoId());
  }

  @Test
  public void feedPhotoRemove() {
    FeedPhotoRemoveValue value = openAndCheckFeedPostBasics("feed-photo-remove-25", FeedPhotoRemoveValue.class,
      ITEM_PHOTO, ChangeValue.Verb.REMOVE);
    assertEquals("1234567890321", value.getRecipientId());
  }

  @Test
  public void feedVideoAdd() {
    FeedVideoAddValue value =
        openAndCheckFeedPostBasics("feed-video-add", FeedVideoAddValue.class, ITEM_VIDEO, ChangeValue.Verb.ADD);
    assertEquals("https://www.example.org/test.mp4", value.getLink());
    assertEquals("900767076685784", value.getVideoId());
  }

  @Test
  public void ratingsCommentAdd() {
    RatingsCommentValue value = openAndCheckBasics("ratings-comment-add-25", RatingsCommentValue.class, FIELD_RATINGS,
      ITEM_COMMENT, ChangeValue.Verb.ADD);
  }

  @Test
  public void ratingsCommentEdited() {
    RatingsCommentValue value = openAndCheckBasics("ratings-comment-edited-25", RatingsCommentValue.class,
      FIELD_RATINGS, ITEM_COMMENT, ChangeValue.Verb.EDITED);
    assertEquals("716630661754264_994838177266843", value.getCommentId());
    assertFalse(value.isReply());
  }

  @Test
  public void ratingsCommentRemove() {
    RatingsCommentValue value = openAndCheckBasics("ratings-comment-remove-25", RatingsCommentValue.class,
      FIELD_RATINGS, ITEM_COMMENT, ChangeValue.Verb.REMOVE);
    assertEquals("1234567890321", value.getSenderId());
    assertEquals("100002220109526_716630661754264", value.getParentId());

  }

  @Test
  public void ratingsRatingAdd() {
    RatingsRatingValue value = openAndCheckBasics("ratings-rating-add-25", RatingsRatingValue.class, FIELD_RATINGS,
      ITEM_RATING, ChangeValue.Verb.ADD);
    assertEquals(3L, value.getRating().longValue());
    assertEquals("Tester", value.getReviewerName());
    assertEquals("Ja ziemlich coole Sache", value.getReviewText());
  }

  @Test
  public void ratingsRatingRemove() {
    RatingsRatingValue value = openAndCheckBasics("ratings-rating-remove-25", RatingsRatingValue.class, FIELD_RATINGS,
      ITEM_RATING, ChangeValue.Verb.REMOVE);
    assertEquals("904801129604590", value.getOpenGraphStoryId());
    assertEquals("705955836155788", value.getReviewerId());
  }

  @Test
  public void ratingsLikeAdd() {
    RatingsLikeValue value = openAndCheckBasics("ratings-like-add-25", RatingsLikeValue.class, FIELD_RATINGS, ITEM_LIKE,
      ChangeValue.Verb.ADD);
    assertEquals(1451775296000L, value.getCreatedTime().getTime());
    assertEquals("Tester", value.getSenderName());
    assertEquals("100002241334695_904801129604590", value.getParentId());
  }

  @Test
  public void ratingsLikeRemove() {
    RatingsLikeValue value = openAndCheckBasics("ratings-like-remove-25", RatingsLikeValue.class, FIELD_RATINGS,
      ITEM_LIKE, ChangeValue.Verb.REMOVE);
  }

  @Test
  public void pageConversations() {
    WebhookObject webhookObject = openJson("page-conversations-25");
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals("change field", FIELD_CONVERSATIONS, change.getField());
    assertEquals("change value class", change.getValue().getClass(), PageConversation.class);
    PageConversation value = (PageConversation) change.getValue();
    assertEquals("change page id", "1234567890321", value.getPageId());
    assertEquals("change thread id", "t_id.171899099639713", value.getThreadId());
  }

  @Test
  public void unknownChangeValue() {
    WebhookObject webhookObject = openJson("unknown-change-value");
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals("change value class", change.getValue().getClass(), FallBackChangeValue.class);
    assertNotNull(((FallBackChangeValue) change.getValue()).getRawJson());
  }

  @Test
  public void feedTwoEntries() {
    WebhookObject webhookObject = openJson("feed-two-entries-25");
    assertEquals("entry count", 2, webhookObject.getEntryList().size());
    assertEquals("change[0] field", "parking", webhookObject.getEntryList().get(0).getChanges().get(0).getField());
    assertEquals("change[1] field", "payment_options",
      webhookObject.getEntryList().get(1).getChanges().get(0).getField());
  }

  @Test
  public void messagingPayment() {
    WebhookObject webhookObject = openMessagingJson("messaging-payment");
    assertNotNull(webhookObject);
    MessagingItem item = webhookObject.getEntryList().get(0).getMessaging().get(0);
    assertEquals("USER_ID", item.getSender().getId());
    assertEquals("PAGE_ID", item.getRecipient().getId());
    PaymentItem paymentItem = (PaymentItem) item.getItem();
    assertEquals("DEVELOPER_DEFINED_PAYLOAD", paymentItem.getPayload());
    Amount amount = paymentItem.getAmount();
    assertEquals("USD", amount.getCurrency());
    assertEquals(29.62D, amount.getAmount(), 0.01);
    PaymentCredential credential = paymentItem.getPaymentCredential();
    assertEquals("ch_18tmdBEoNIH3FPJHa60ep123", credential.getChargeId());
    assertEquals("stripe", credential.getProviderType());
    ReuqestedUserInfo userInfo = paymentItem.getRequestedUserInfo();
    assertEquals("peter@anemailprovider.com", userInfo.getContactEmail());
    assertEquals("Peter Chang", userInfo.getContactName());
    assertEquals("+15105551234", userInfo.getContactPhone());
    ShippingAddress shippingAddress = userInfo.getShippingAddress();
    assertEquals("MENLO PARK", shippingAddress.getCity());
    assertEquals("US", shippingAddress.getCountry());
    assertEquals("94025", shippingAddress.getPostalCode());
    assertEquals("CA", shippingAddress.getState());
    assertEquals("1 Hacker Way", shippingAddress.getStreet1());
    assertEquals("", shippingAddress.getStreet2());
  }

  @Test
  public void checkoutUpdateShipping() {
    WebhookObject webhookObject = openMessagingJson("messaging-checkoutupdate-shipping");
    assertNotNull(webhookObject);
    MessagingItem item = webhookObject.getEntryList().get(0).getMessaging().get(0);
    assertEquals("USER_ID", item.getSender().getId());
    assertEquals("PAGE_ID", item.getRecipient().getId());
    CheckoutUpdateItem checkoutUpdateItem = (CheckoutUpdateItem) item.getItem();
    assertEquals("DEVELOPER_DEFINED_PAYLOAD", checkoutUpdateItem.getPayload());
    assertNotNull(checkoutUpdateItem.getShippingAddress());
    ShippingAddress shippingAddress = checkoutUpdateItem.getShippingAddress();
    assertEquals("10105655000959552", shippingAddress.getId());
    assertEquals("MENLO PARK", shippingAddress.getCity());
    assertEquals("US", shippingAddress.getCountry());
    assertEquals("94025", shippingAddress.getPostalCode());
    assertEquals("CA", shippingAddress.getState());
    assertEquals("1 Hacker Way", shippingAddress.getStreet1());
    assertEquals("", shippingAddress.getStreet2());
  }

  @Test
  public void quickReply() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-quickreply"), WebhookObject.class);
    assertNotNull(webhookObject);
    MessagingItem messageItem = webhookObject.getEntryList().get(0).getMessaging().get(0);
    QuickReplyItem qrItem = messageItem.getMessage().getQuickReply();
    assertNotNull(qrItem);
    assertEquals("PAYLOAD_QUICK_REPLY", qrItem.getPayload());
  }

  @Test
  public void stickerId() {
    WebhookObject webhookObject = createJsonMapper()
            .toJavaObject(jsonFromClasspath("webhooks/messaging-message-sticker"), WebhookObject.class);
    assertNotNull(webhookObject);
    MessagingItem messageItem = webhookObject.getEntryList().get(0).getMessaging().get(0);
    assertNotNull(messageItem);
    assertNotNull(messageItem.getMessage());
    assertEquals("56789012345678", messageItem.getMessage().getStickerId());
    assertNotNull(messageItem.getMessage().getAttachments());
    assertEquals(1, messageItem.getMessage().getAttachments().size());
    MessagingAttachment attachment = messageItem.getMessage().getAttachments().get(0);
    assertNotNull(attachment.getPayload());
    assertEquals("56789012345678", attachment.getPayload().getStickerId());
  }

  @Test
  public void useEnumAsValue() {
    String val1 = Parameter.with("key", SenderActionEnum.typing_on).value;
    String val2 = Parameter.with("key", SenderActionEnum.typing_off).value;
    String val3 = Parameter.with("key", SenderActionEnum.mark_seen).value;
    assertEquals("typing_on", val1);
    assertEquals("typing_off", val2);
    assertEquals("mark_seen", val3);
  }

  private <T extends AbstractFeedPostValue> T openAndCheckFeedPostBasics(String jsonName, Class<T> changeValueClass,
      String expectedItem, ChangeValue.Verb expectedVerb) {
    return openAndCheckBasics(jsonName, changeValueClass, FIELD_FEED, expectedItem, expectedVerb);
  }

  private <T extends BaseChangeValue> T openAndCheckBasics(String jsonName, Class<T> changeValueClass,
      String expectedField, String expectedItem, ChangeValue.Verb expectedVerb) {
    WebhookObject webhookObject = openJson(jsonName);
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals("change field", expectedField, change.getField());
    assertEquals("change value class", change.getValue().getClass(), changeValueClass);
    assertEquals("change item", expectedItem, ((T) change.getValue()).getItem());
    assertEquals("change verb", expectedVerb, ((T) change.getValue()).getVerb());
    return (T) change.getValue();
  }

  private WebhookObject openJson(String jsonName) {
    WebhookObject webhookObject = getWHObjectFromJson(jsonName);
    assertFalse("webhookObject.entryList[0].changes not empty",
      webhookObject.getEntryList().get(0).getChanges().isEmpty());
    return webhookObject;
  }

  private WebhookObject openMessagingJson(String jsonName) {
    WebhookObject webhookObject = getWHObjectFromJson(jsonName);
    assertFalse("webhookObject.entryList[0].messaging not empty",
            webhookObject.getEntryList().get(0).getMessaging().isEmpty());
    return webhookObject;
  }

  private WebhookObject getWHObjectFromJson(String jsonName) {
    WebhookObject webhookObject =
            createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/" + jsonName), WebhookObject.class);
    assertNotNull("webhookObject not null", webhookObject);
    assertEquals("webhookObject.object", "page", webhookObject.getObject());
    assertFalse("webhookObject.entryList not empty", webhookObject.getEntryList().isEmpty());
    assertEquals("page id", "1234567890321", webhookObject.getEntryList().get(0).getId());
    assertTrue("update time", 1445000000000L < webhookObject.getEntryList().get(0).getTime().getTime());
    return webhookObject;
  }

}
