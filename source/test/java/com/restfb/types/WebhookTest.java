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

import static org.junit.Assert.*;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.json.JsonObject;
import com.restfb.types.webhook.*;

import org.junit.Test;

public class WebhookTest extends AbstractJsonMapperTests {

  @Test
  public void feedCommentAdd() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/feed-comment-add-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof FeedCommentValue);
    FeedCommentValue value = (FeedCommentValue) change.getValue();
    assertFalse(value.isReply());
    assertEquals(ChangeValue.Verb.ADD, value.getVerb());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("901097836652708_903438993085259", value.getCommentId());
    assertEquals(1449135003000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedCommentEdited() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/feed-comment-edited-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof FeedCommentValue);
    FeedCommentValue value = (FeedCommentValue) change.getValue();
    assertFalse(value.isReply());
    assertEquals(ChangeValue.Verb.EDITED, value.getVerb());
    assertEquals("Let's see the Webhooks in action :D", value.getMessage());
    assertEquals("1234567890321_900728623356296", value.getPostId());
    assertEquals("1234567890321_900728623356296", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1448555737000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedCommentRemove() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/feed-comment-remove-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof FeedCommentValue);
    FeedCommentValue value = (FeedCommentValue) change.getValue();
    assertFalse(value.isReply());
    assertEquals(ChangeValue.Verb.REMOVE, value.getVerb());
    assertEquals("1234567890321_905578656204626", value.getPostId());
    assertEquals("1234567890321_905578656204626", value.getParentId());
    assertEquals("905578656204626_905579239537901", value.getCommentId());
    assertNull(value.getCreatedTime());
  }

  @Test
  public void feedStatusAdd() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/feed-status-add-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof FeedStatusValue);
    FeedStatusValue value = (FeedStatusValue) change.getValue();
    assertEquals(ChangeValue.Verb.ADD, value.getVerb());
    assertTrue(value.getPublished().booleanValue());
    assertEquals("1234567890321_930145403745849", value.getPostId());
    assertEquals("Tester", value.getSenderName());
    assertEquals(1448633038000L, value.getCreatedTime().getTime());
  }

  @Test
  public void feedStatusEdited() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/feed-status-edited-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof FeedStatusValue);
    FeedStatusValue value = (FeedStatusValue) change.getValue();
    assertEquals(ChangeValue.Verb.EDITED, value.getVerb());
    assertEquals("edited", value.getVerbAsString());
    assertEquals("Tester", value.getSenderName());
    assertEquals("status", value.getItem());
    assertEquals("This is an edited test message", value.getMessage());
    assertTrue(value.getPublished());
  }

  @Test
  public void feedLikeAdd() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/feed-like-add-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof FeedLikeValue);
    FeedLikeValue likeAdd = (FeedLikeValue) change.getValue();
    assertFalse(likeAdd.isPageLike());
    assertEquals("909653922461664_940663242694065", likeAdd.getParentId());
    assertNull(likeAdd.getUserId());
    assertEquals("909653922461664_940663242694065", likeAdd.getPostId());
  }

  @Test
  public void feedLikeAddPage() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/feed-like-add-page-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof FeedLikeValue);
    FeedLikeValue likeAdd = (FeedLikeValue) change.getValue();
    assertTrue(likeAdd.isPageLike());
    assertEquals(ChangeValue.Verb.ADD, likeAdd.getVerb());
    assertEquals("10201978818232600", likeAdd.getUserId());
  }

  @Test
  public void feedLikeRemove() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/feed-like-remove-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof FeedLikeValue);
    FeedLikeValue likeRemove = (FeedLikeValue) change.getValue();
    assertEquals(ChangeValue.Verb.REMOVE, likeRemove.getVerb());
    assertEquals("1234567890321_940663242694065", likeRemove.getParentId());
  }

  @Test
  public void feedPhotoAdd() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/feed-photo-add-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof FeedPhotoAddValue);
    FeedPhotoAddValue photoChange = (FeedPhotoAddValue) change.getValue();
    assertTrue(photoChange.getPublished());
    assertEquals("https://www.example.org/test.jpg", photoChange.getLink());
    assertEquals("900767076685784", photoChange.getPhotoId());

  }

  @Test
  public void feedPhotoRemove() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/feed-photo-remove-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof FeedPhotoRemoveValue);
    FeedPhotoRemoveValue photoChange = (FeedPhotoRemoveValue) change.getValue();
    assertEquals("1234567890321", photoChange.getRecipientId());
  }

  @Test
  public void ratingsCommentAdd() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/ratings-comment-add-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof RatingsCommentValue);
    RatingsCommentValue commentAdd = (RatingsCommentValue) change.getValue();
    assertEquals(ChangeValue.Verb.ADD, commentAdd.getVerb());
  }

  @Test
  public void ratingsCommentEdited() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/ratings-comment-edited-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof RatingsCommentValue);
    RatingsCommentValue commentValue = (RatingsCommentValue) change.getValue();
    assertEquals(ChangeValue.Verb.EDITED, commentValue.getVerb());
    assertEquals("716630661754264_994838177266843", commentValue.getCommentId());
    assertFalse(commentValue.isReply());
  }

  @Test
  public void ratingsCommentRemove() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/ratings-comment-remove-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof RatingsCommentValue);
    RatingsCommentValue commentValue = (RatingsCommentValue) change.getValue();
    assertEquals(ChangeValue.Verb.REMOVE, commentValue.getVerb());
    assertEquals("1234567890321", commentValue.getSenderId());
    assertEquals("100002220109526_716630661754264", commentValue.getParentId());

  }

  @Test
  public void ratingsRatingAdd() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/ratings-rating-add-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof RatingsRatingValue);
    RatingsRatingValue ratingValue = (RatingsRatingValue) change.getValue();
    assertEquals(ChangeValue.Verb.ADD, ratingValue.getVerb());
    assertEquals(3L, ratingValue.getRating().longValue());
    assertEquals("Tester", ratingValue.getReviewerName());
    assertEquals("Ja ziemlich coole Sache", ratingValue.getReviewText());
  }

  @Test
  public void ratingsRatingRemove() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/ratings-rating-remove-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof RatingsRatingValue);
    RatingsRatingValue ratingValue = (RatingsRatingValue) change.getValue();
    assertEquals(ChangeValue.Verb.REMOVE, ratingValue.getVerb());
    assertEquals("904801129604590", ratingValue.getOpenGraphStoryId());
    assertEquals("705955836155788", ratingValue.getReviewerId());
  }

  @Test
  public void ratingsLikeAdd() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/ratings-like-add-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof RatingsLikeValue);
    RatingsLikeValue ratingAdd = (RatingsLikeValue) change.getValue();
    assertEquals(ChangeValue.Verb.ADD, ratingAdd.getVerb());
    assertEquals(1451775296000L, ratingAdd.getCreatedTime().getTime());
    assertEquals("Tester", ratingAdd.getSenderName());
    assertEquals("100002241334695_904801129604590", ratingAdd.getParentId());
  }

  @Test
  public void ratingsLikeRemove() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/ratings-like-remove-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof RatingsLikeValue);
    RatingsLikeValue ratingAdd = (RatingsLikeValue) change.getValue();
    assertEquals(ChangeValue.Verb.REMOVE, ratingAdd.getVerb());
  }

  @Test
  public void pageConversations() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/page-conversations-25"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof PageConversation);
    PageConversation conversation = (PageConversation) change.getValue();
    assertNotNull(conversation.getPageId());
    assertEquals("1234567890321", conversation.getPageId());
    assertNotNull(conversation.getThreadId());
    assertEquals("t_id.171899099639713", conversation.getThreadId());
  }

  @Test
  public void unknownChangeValue() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/unknown-change-value"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertFalse(webhookObject.getEntryList().isEmpty());
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof FallBackChangeValue);
    FallBackChangeValue fallback = (FallBackChangeValue) change.getValue();
    JsonObject object = fallback.getRawJson();
    assertNotNull(object);
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
}
