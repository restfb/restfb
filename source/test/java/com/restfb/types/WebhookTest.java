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
import com.restfb.types.webhook.Change;
import com.restfb.types.webhook.ChangeValue;
import com.restfb.types.webhook.FallBackChangeValue;
import com.restfb.types.webhook.FeedAlbumAddValue;
import com.restfb.types.webhook.FeedCommentValue;
import com.restfb.types.webhook.FeedLikeValue;
import com.restfb.types.webhook.FeedPhotoAddValue;
import com.restfb.types.webhook.FeedPhotoRemoveValue;
import com.restfb.types.webhook.FeedPostValue;
import com.restfb.types.webhook.FeedShareValue;
import com.restfb.types.webhook.FeedStatusValue;
import com.restfb.types.webhook.FeedVideoAddValue;
import com.restfb.types.webhook.PageConversation;
import com.restfb.types.webhook.RatingsCommentValue;
import com.restfb.types.webhook.RatingsLikeValue;
import com.restfb.types.webhook.RatingsRatingValue;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.base.AbstractFeedPostValue;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class WebhookTest extends AbstractJsonMapperTests {

  private static final String FIELD_FEED = "feed";
  private static final String FIELD_RATINGS = "ratings";
  private static final String FIELD_CONVERSATIONS = "conversations";

  @Test
  public void feedAlbumAdd() {
    FeedAlbumAddValue value = openAndCheckFeedPostBasics("feed-album-add", FeedAlbumAddValue.class, ChangeValue.Verb.ADD);
    assertEquals("900767076685784", value.getAlbumId());
    assertTrue(value.getPublished());
    assertEquals(1467894325000L, value.getCreatedTime().getTime());
    assertEquals(Arrays.asList("900767076685784", "900767076685785", "900767076685786"), value.getPhotoIds());
  }

  @Test
  public void feedCommentAdd() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-add-25", FeedCommentValue.class, ChangeValue.Verb.ADD);
    assertFalse(value.isReply());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("901097836652708_903438993085259", value.getCommentId());
    assertEquals(1449135003000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedCommentEdited() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-edited-25", FeedCommentValue.class, ChangeValue.Verb.EDITED);
    assertFalse(value.isReply());
    assertEquals("Let's see the Webhooks in action :D", value.getMessage());
    assertEquals("1234567890321_900728623356296", value.getPostId());
    assertEquals("1234567890321_900728623356296", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1448555737000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedCommentHide() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-hide", FeedCommentValue.class, ChangeValue.Verb.HIDE);
    assertFalse(value.isReply());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1467894562000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedCommentUnhide() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-unhide", FeedCommentValue.class, ChangeValue.Verb.UNHIDE);
    assertFalse(value.isReply());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1467894562000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedCommentRemove() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-remove-25", FeedCommentValue.class, ChangeValue.Verb.REMOVE);
    assertFalse(value.isReply());
    assertEquals("1234567890321_905578656204626", value.getPostId());
    assertEquals("1234567890321_905578656204626", value.getParentId());
    assertEquals("905578656204626_905579239537901", value.getCommentId());
    assertNull(value.getCreatedTime());
  }

  @Test
  public void feedPostAdd() {
    FeedPostValue value = openAndCheckFeedPostBasics("feed-post-add-25", FeedPostValue.class, ChangeValue.Verb.ADD);
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("8423678347823", value.getSenderId());
    assertFalse(value.isHidden());
    assertEquals("Let's check this", value.getMessage());
  }

  @Test
  public void feedPostHide() {
    FeedPostValue value = openAndCheckFeedPostBasics("feed-post-hide", FeedPostValue.class, ChangeValue.Verb.HIDE);
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("8423678347823", value.getSenderId());
    assertEquals("Let's check this", value.getMessage());
  }

  @Test
  public void feedPostRemove() {
    FeedPostValue value = openAndCheckFeedPostBasics("feed-post-remove-25", FeedPostValue.class, ChangeValue.Verb.REMOVE);
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321", value.getSenderId());
    assertEquals(1452098986000L, value.getCreatedTime().getTime());
    assertEquals("1234567890321", value.getRecipientId());
  }

  @Test
  public void feedPostUnhide() {
    FeedPostValue value = openAndCheckFeedPostBasics("feed-post-unhide", FeedPostValue.class, ChangeValue.Verb.UNHIDE);
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("8423678347823", value.getSenderId());
    assertEquals("Let's check this", value.getMessage());
  }

  @Test
  public void feedShareAdd() {
    FeedShareValue value = openAndCheckFeedPostBasics("feed-share-add-25", FeedShareValue.class, ChangeValue.Verb.ADD);
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321", value.getSenderId());
    assertTrue(value.getPublished().booleanValue());
    assertNotNull(value.getMessage());
    assertEquals("http://www.google.com/", value.getLink());
    assertEquals("98735342324352", value.getShareId());
  }

  @Test
  public void feedShareHide() {
    FeedShareValue value = openAndCheckFeedPostBasics("feed-share-hide", FeedShareValue.class, ChangeValue.Verb.HIDE);
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321", value.getSenderId());
    assertEquals("Let me google that for you", value.getMessage());
    assertEquals("http://www.google.com/", value.getLink());
    assertEquals("98735342324352", value.getShareId());
  }

  @Test
  public void feedShareUnhide() {
    FeedShareValue value = openAndCheckFeedPostBasics("feed-share-unhide", FeedShareValue.class, ChangeValue.Verb.UNHIDE);
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321", value.getSenderId());
    assertEquals("Let me google that for you", value.getMessage());
    assertEquals("http://www.google.com/", value.getLink());
    assertEquals("98735342324352", value.getShareId());
  }

  @Test
  public void feedStatusEdited() {
    FeedStatusValue value = openAndCheckFeedPostBasics("feed-status-edited-25", FeedStatusValue.class, ChangeValue.Verb.EDITED);
    assertEquals("edited", value.getVerbAsString());
    assertEquals("Tester", value.getSenderName());
    assertEquals("status", value.getItem());
    assertEquals("This is an edited test message", value.getMessage());
    assertTrue(value.getPublished());
  }

  @Test
  public void feedStatusAdd() {
    FeedStatusValue value = openAndCheckFeedPostBasics("feed-status-add-25", FeedStatusValue.class, ChangeValue.Verb.ADD);
    assertTrue(value.getPublished().booleanValue());
    assertEquals("1234567890321_930145403745849", value.getPostId());
    assertEquals("Tester", value.getSenderName());
    assertEquals(1448633038000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedStatusHide() {
    FeedStatusValue value = openAndCheckFeedPostBasics("feed-status-hide-25", FeedStatusValue.class, ChangeValue.Verb.HIDE);
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("Some Tester", value.getSenderName());
  }

  @Test
  public void feedLikeAdd() {
    FeedLikeValue value = openAndCheckFeedPostBasics("feed-like-add-25", FeedLikeValue.class, ChangeValue.Verb.ADD);
    assertFalse(value.isPageLike());
    assertEquals("909653922461664_940663242694065", value.getParentId());
    assertNull(value.getUserId());
    assertEquals("909653922461664_940663242694065", value.getPostId());
  }

  @Test
  public void feedLikeAddPage() {
    FeedLikeValue value = openAndCheckFeedPostBasics("feed-like-add-page-25", FeedLikeValue.class, ChangeValue.Verb.ADD);
    assertTrue(value.isPageLike());
    assertEquals("10201978818232600", value.getUserId());
  }

  @Test
  public void feedLikeRemove() {
    FeedLikeValue value = openAndCheckFeedPostBasics("feed-like-remove-25", FeedLikeValue.class, ChangeValue.Verb.REMOVE);
    assertEquals("1234567890321_940663242694065", value.getParentId());
  }

  @Test
  public void feedPhotoAdd() {
    FeedPhotoAddValue value = openAndCheckFeedPostBasics("feed-photo-add-25", FeedPhotoAddValue.class, ChangeValue.Verb.ADD);
    assertTrue(value.getPublished());
    assertEquals("https://www.example.org/test.jpg", value.getLink());
    assertEquals("900767076685784", value.getPhotoId());
  }

  @Test
  public void feedPhotoEdited() {
    FeedPhotoAddValue value = openAndCheckFeedPostBasics("feed-photo-edited", FeedPhotoAddValue.class, ChangeValue.Verb.EDITED);
    assertTrue(value.getPublished());
    assertEquals("https://www.example.org/test.jpg", value.getLink());
    assertEquals("900767076685784", value.getPhotoId());
  }

  @Test
  public void feedPhotoHide() {
    FeedPhotoAddValue value = openAndCheckFeedPostBasics("feed-photo-hide", FeedPhotoAddValue.class, ChangeValue.Verb.HIDE);
    assertEquals(ChangeValue.Verb.HIDE, value.getVerb());
    assertEquals("https://www.example.org/test.jpg", value.getLink());
    assertEquals("1063068383729088", value.getPhotoId());
  }

  @Test
  public void feedPhotoRemove() {
    FeedPhotoRemoveValue value = openAndCheckFeedPostBasics("feed-photo-remove-25", FeedPhotoRemoveValue.class, ChangeValue.Verb.REMOVE);
    assertEquals("1234567890321", value.getRecipientId());
  }

  @Test
  public void feedVideoAdd() {
    FeedVideoAddValue value = openAndCheckFeedPostBasics("feed-video-add", FeedVideoAddValue.class, ChangeValue.Verb.ADD);
    assertEquals("https://www.example.org/test.mp4", value.getLink());
    assertEquals("900767076685784", value.getVideoId());
  }

  @Test
  public void ratingsCommentAdd() {
    RatingsCommentValue value = openAndCheckBasics("ratings-comment-add-25", RatingsCommentValue.class, FIELD_RATINGS);
    assertEquals(ChangeValue.Verb.ADD, value.getVerb());
  }

  @Test
  public void ratingsCommentEdited() {
    RatingsCommentValue value = openAndCheckBasics("ratings-comment-edited-25", RatingsCommentValue.class, FIELD_RATINGS);
    assertEquals(ChangeValue.Verb.EDITED, value.getVerb());
    assertEquals("716630661754264_994838177266843", value.getCommentId());
    assertFalse(value.isReply());
  }

  @Test
  public void ratingsCommentRemove() {
    RatingsCommentValue value = openAndCheckBasics("ratings-comment-remove-25", RatingsCommentValue.class, FIELD_RATINGS);
    assertEquals(ChangeValue.Verb.REMOVE, value.getVerb());
    assertEquals("1234567890321", value.getSenderId());
    assertEquals("100002220109526_716630661754264", value.getParentId());

  }

  @Test
  public void ratingsRatingAdd() {
    RatingsRatingValue value = openAndCheckBasics("ratings-rating-add-25", RatingsRatingValue.class, FIELD_RATINGS);
    assertEquals(ChangeValue.Verb.ADD, value.getVerb());
    assertEquals(3L, value.getRating().longValue());
    assertEquals("Tester", value.getReviewerName());
    assertEquals("Ja ziemlich coole Sache", value.getReviewText());
  }

  @Test
  public void ratingsRatingRemove() {
    RatingsRatingValue value = openAndCheckBasics("ratings-rating-remove-25", RatingsRatingValue.class, FIELD_RATINGS);
    assertEquals(ChangeValue.Verb.REMOVE, value.getVerb());
    assertEquals("904801129604590", value.getOpenGraphStoryId());
    assertEquals("705955836155788", value.getReviewerId());
  }

  @Test
  public void ratingsLikeAdd() {
    RatingsLikeValue value = openAndCheckBasics("ratings-like-add-25", RatingsLikeValue.class, FIELD_RATINGS);
    assertEquals(ChangeValue.Verb.ADD, value.getVerb());
    assertEquals(1451775296000L, value.getCreatedTime().getTime());
    assertEquals("Tester", value.getSenderName());
    assertEquals("100002241334695_904801129604590", value.getParentId());
  }

  @Test
  public void ratingsLikeRemove() {
    RatingsLikeValue value = openAndCheckBasics("ratings-like-remove-25", RatingsLikeValue.class, FIELD_RATINGS);
    assertEquals(ChangeValue.Verb.REMOVE, value.getVerb());
  }

  @Test
  public void pageConversations() {
    PageConversation value = openAndCheckBasics("page-conversations-25", PageConversation.class, FIELD_CONVERSATIONS);
    assertNotNull(value.getPageId());
    assertEquals("1234567890321", value.getPageId());
    assertNotNull(value.getThreadId());
    assertEquals("t_id.171899099639713", value.getThreadId());
  }

  @Test
  public void unknownChangeValue() {
    FallBackChangeValue value = openAndCheckBasics("unknown-change-value", FallBackChangeValue.class, null);
    assertNotNull(value.getRawJson());
  }

  @Test
  public void feedTwoEntries() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/feed-two-entries-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertEquals("page", webhookObject.getObject());
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertEquals(2, webhookObject.getEntryList().size());
    boolean parking = false;
    boolean payment = false;
    for (WebhookEntry entry : webhookObject.getEntryList()) {
      assertEquals("1234567890321", entry.getId());
      String field = entry.getChanges().get(0).getField();
      if (field.equals("parking")) {
        parking = true;
      }
      if (field.equals("payment_options")) {
        payment = true;
      }
    }
    assertTrue(parking);
    assertTrue(payment);
  }


  private <T extends AbstractFeedPostValue> T openAndCheckFeedPostBasics(String jsonName, Class<T> changeValueClass, ChangeValue.Verb expectedVerb) {
    T value = openAndCheckBasics(jsonName, changeValueClass, FIELD_FEED);
    assertEquals("change verb", expectedVerb, value.getVerb());
    return value;
  }


  private <T extends ChangeValue> T openAndCheckBasics(String jsonName, Class<T> changeValueClass, String expectedField) {
    WebhookObject webhookObject = createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/" + jsonName), WebhookObject.class);
    assertNotNull("webhookObject not null", webhookObject);
    assertEquals("webhookObject.object", "page", webhookObject.getObject());
    assertFalse("webhookObject.entryList not empty", webhookObject.getEntryList().isEmpty());
    assertFalse("webhookObject.entryList[0].changes not empty", webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals("change value class", change.getValue().getClass(), changeValueClass);
    if(expectedField != null) {
      assertEquals("change field", expectedField, change.getField());
    }
    return (T) change.getValue();
  }
}
