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

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.send.SenderActionEnum;
import com.restfb.types.webhook.*;
import com.restfb.types.webhook.base.AbstractFeedPostValue;
import com.restfb.types.webhook.base.BaseChangeValue;
import com.restfb.types.webhook.messaging.*;
import com.restfb.types.webhook.messaging.payment.Amount;
import com.restfb.types.webhook.messaging.payment.PaymentCredential;
import com.restfb.types.webhook.messaging.payment.ReuqestedUserInfo;
import com.restfb.types.webhook.messaging.payment.ShippingAddress;
import com.restfb.webhook.AbstractWebhookChangeListener;
import com.restfb.webhook.Webhook;
import com.restfb.webhook.WebhookChangeListener;

class WebhookTest extends AbstractJsonMapperTests {

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

  private final Webhook webhookListener = new Webhook();

  private AtomicBoolean listenerTriggered = new AtomicBoolean();

  @BeforeEach
  void cleanEnv() {
    listenerTriggered.set(false);
  }

  @Test
  void feedAlbumAdd() {
    FeedAlbumAddValue value = openAndCheckFeedPostBasics("feed-album-add", FeedAlbumAddValue.class, ITEM_ALBUM,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedAlbumAddValue(FeedAlbumAddValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("900767076685784", value.getAlbumId());
    assertTrue(value.getPublished());
    assertEquals(1467894325000L, value.getCreatedTime().getTime());
    assertEquals(Arrays.asList("900767076685784", "900767076685785", "900767076685786"), value.getPhotoIds());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedAlbumEdited() {
    FeedAlbumEditedValue value = openAndCheckFeedPostBasics("feed-album-edited", FeedAlbumEditedValue.class, ITEM_ALBUM,
      ChangeValue.Verb.EDITED, new AbstractWebhookChangeListener() {
        @Override
        public void feedAlbumEditedValue(FeedAlbumEditedValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("900767076685784", value.getAlbumId());
    assertFalse(value.getPublished());
    assertEquals(1470322909000L, value.getCreatedTime().getTime());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedCommentAdd() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-add-211", FeedCommentValue.class, ITEM_COMMENT,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedCommentValue(FeedCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertFalse(value.isReply());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("901097836652708_903438993085259", value.getCommentId());
    assertEquals(1449135003000L, value.getCreatedTime().getTime());

    assertEquals("Tester", value.getFrom().getName());
    assertEquals("1234567890321", value.getFrom().getId());

    assertNull(value.getPhoto());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedCommentAddWithPhoto() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-add-with-photo", FeedCommentValue.class,
      ITEM_COMMENT, ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedCommentValue(FeedCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertFalse(value.isReply());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("901097836652708_903438993085259", value.getCommentId());
    assertEquals(1449135003000L, value.getCreatedTime().getTime());
    assertNotNull(value.getPhoto());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedCommentAddWithVideo() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-add-with-video", FeedCommentValue.class,
      ITEM_COMMENT, ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedCommentValue(FeedCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertFalse(value.isReply());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("901097836652708_903438993085259", value.getCommentId());
    assertEquals(1449135003000L, value.getCreatedTime().getTime());
    assertNotNull(value.getVideo());
    assertNull(value.getPhoto());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedCommentEdited() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-edited-25", FeedCommentValue.class, ITEM_COMMENT,
      ChangeValue.Verb.EDITED, new AbstractWebhookChangeListener() {
        @Override
        public void feedCommentValue(FeedCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertFalse(value.isReply());
    assertEquals("Let's see the Webhooks in action :D", value.getMessage());
    assertEquals("1234567890321_900728623356296", value.getPostId());
    assertEquals("1234567890321_900728623356296", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1448555737000L, value.getCreatedTime().getTime());
    assertNull(value.getPhoto());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedCommentEditedWithPhoto() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-edited-with-photo", FeedCommentValue.class,
      ITEM_COMMENT, ChangeValue.Verb.EDITED, new AbstractWebhookChangeListener() {
        @Override
        public void feedCommentValue(FeedCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertFalse(value.isReply());
    assertEquals("Let's see the Webhooks in action :D", value.getMessage());
    assertEquals("1234567890321_900728623356296", value.getPostId());
    assertEquals("1234567890321_900728623356296", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1448555737000L, value.getCreatedTime().getTime());
    assertNotNull(value.getPhoto());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedCommentEditedWithVideo() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-edited-with-video", FeedCommentValue.class,
      ITEM_COMMENT, ChangeValue.Verb.EDITED, new AbstractWebhookChangeListener() {
        @Override
        public void feedCommentValue(FeedCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertFalse(value.isReply());
    assertEquals("Let's see the Webhooks in action :D", value.getMessage());
    assertEquals("1234567890321_900728623356296", value.getPostId());
    assertEquals("1234567890321_900728623356296", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1448555737000L, value.getCreatedTime().getTime());
    assertNotNull(value.getVideo());
    assertNull(value.getPhoto());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedCommentHide() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-hide", FeedCommentValue.class, ITEM_COMMENT,
      ChangeValue.Verb.HIDE, new AbstractWebhookChangeListener() {
        @Override
        public void feedCommentValue(FeedCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertFalse(value.isReply());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1467894562000L, value.getCreatedTime().getTime());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedCommentUnhide() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-unhide", FeedCommentValue.class, ITEM_COMMENT,
      ChangeValue.Verb.UNHIDE, new AbstractWebhookChangeListener() {
        @Override
        public void feedCommentValue(FeedCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertFalse(value.isReply());
    assertEquals("and the next one", value.getMessage());
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("900728623356296_900744590021366", value.getCommentId());
    assertEquals(1467894562000L, value.getCreatedTime().getTime());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedCommentRemove() {
    FeedCommentValue value = openAndCheckFeedPostBasics("feed-comment-remove-25", FeedCommentValue.class, ITEM_COMMENT,
      ChangeValue.Verb.REMOVE, new AbstractWebhookChangeListener() {
        @Override
        public void feedCommentValue(FeedCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertFalse(value.isReply());
    assertEquals("1234567890321_905578656204626", value.getPostId());
    assertEquals("1234567890321_905578656204626", value.getParentId());
    assertEquals("905578656204626_905579239537901", value.getCommentId());
    assertNull(value.getCreatedTime());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedPostAdd() {
    FeedPostValue value = openAndCheckFeedPostBasics("feed-post-add-25", FeedPostValue.class, ITEM_POST,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedPostValue(FeedPostValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("8423678347823", value.getFrom().getId());
    assertFalse(value.getIsHidden());
    assertEquals("Let's check this", value.getMessage());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedPostHide() {
    FeedPostValue value = openAndCheckFeedPostBasics("feed-post-hide", FeedPostValue.class, ITEM_POST,
      ChangeValue.Verb.HIDE, new AbstractWebhookChangeListener() {
        @Override
        public void feedPostValue(FeedPostValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("8423678347823", value.getFrom().getId());
    assertEquals("Let's check this", value.getMessage());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedPostEdit() {
    FeedPostValue value = openAndCheckFeedPostBasics("feed-post-edit", FeedPostValue.class, ITEM_POST,
      ChangeValue.Verb.EDIT, new AbstractWebhookChangeListener() {
        @Override
        public void feedPostValue(FeedPostValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("8423678347823", value.getFrom().getId());
    assertEquals("Let's check this", value.getMessage());
    assertEquals("753215778164799", value.getRecipientId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedPostRemove() {
    FeedPostValue value = openAndCheckFeedPostBasics("feed-post-remove-25", FeedPostValue.class, ITEM_POST,
      ChangeValue.Verb.REMOVE, new AbstractWebhookChangeListener() {
        @Override
        public void feedPostValue(FeedPostValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_901097836652708", value.getPostId());
    assertEquals("1234567890321", value.getFrom().getId());
    assertEquals(1452098986000L, value.getCreatedTime().getTime());
    assertEquals("1234567890321", value.getRecipientId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedPostUnhide() {
    FeedPostValue value = openAndCheckFeedPostBasics("feed-post-unhide", FeedPostValue.class, ITEM_POST,
      ChangeValue.Verb.UNHIDE, new AbstractWebhookChangeListener() {
        @Override
        public void feedPostValue(FeedPostValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("8423678347823", value.getFrom().getId());
    assertEquals("Let's check this", value.getMessage());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedReactionAdd() {
    FeedReactionValue value = openAndCheckFeedPostBasics("feed-reaction-add", FeedReactionValue.class, ITEM_REACTION,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedReactionValue(FeedReactionValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("1234567890321", value.getFrom().getId());
    assertEquals("love", value.getReactionType());
    assertTrue(value.isPostReaction());
    assertFalse(value.isCommentReaction());
    assertFalse(value.isReplyReaction());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedReactionCommentAdd() {
    FeedReactionValue value = openAndCheckFeedPostBasics("feed-reaction-add-comment", FeedReactionValue.class,
      ITEM_REACTION, ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedReactionValue(FeedReactionValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321_98735342324352", value.getParentId());
    assertEquals("1234567890321_1264545546974600", value.getCommentId());
    assertEquals("1234567890321", value.getFrom().getId());
    assertEquals("love", value.getReactionType());
    assertFalse(value.isPostReaction());
    assertTrue(value.isCommentReaction());
    assertFalse(value.isReplyReaction());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedReactionReplyAdd() {
    FeedReactionValue value = openAndCheckFeedPostBasics("feed-reaction-add-reply", FeedReactionValue.class,
      ITEM_REACTION, ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedReactionValue(FeedReactionValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321_1264545546974600", value.getParentId());
    assertEquals("1234567890321_1357555177673636", value.getCommentId());
    assertEquals("1234567890321", value.getFrom().getId());
    assertEquals("like", value.getReactionType());
    assertFalse(value.isPostReaction());
    assertTrue(value.isCommentReaction());
    assertTrue(value.isReplyReaction());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedReactionEdit() {
    FeedReactionValue value = openAndCheckFeedPostBasics("feed-reaction-edit", FeedReactionValue.class, ITEM_REACTION,
      ChangeValue.Verb.EDIT, new AbstractWebhookChangeListener() {
        @Override
        public void feedReactionValue(FeedReactionValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("1234567890321", value.getFrom().getId());
    assertEquals("wow", value.getReactionType());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedReactionRemove() {
    FeedReactionValue value = openAndCheckFeedPostBasics("feed-reaction-remove", FeedReactionValue.class, ITEM_REACTION,
      ChangeValue.Verb.REMOVE, new AbstractWebhookChangeListener() {
        @Override
        public void feedReactionValue(FeedReactionValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321_901097836652708", value.getParentId());
    assertEquals("1234567890321", value.getFrom().getId());
    assertEquals("wow", value.getReactionType());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedShareAdd() {
    FeedShareValue value = openAndCheckFeedPostBasics("feed-share-add-25", FeedShareValue.class, ITEM_SHARE,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedShareValue(FeedShareValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321", value.getFrom().getId());
    assertTrue(value.getPublished());
    assertNotNull(value.getMessage());
    assertEquals("http://www.google.com/", value.getLink());
    assertEquals("98735342324352", value.getShareId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedShareHide() {
    FeedShareValue value = openAndCheckFeedPostBasics("feed-share-hide", FeedShareValue.class, ITEM_SHARE,
      ChangeValue.Verb.HIDE, new AbstractWebhookChangeListener() {
        @Override
        public void feedShareValue(FeedShareValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321", value.getFrom().getId());
    assertEquals("Let me google that for you", value.getMessage());
    assertEquals("http://www.google.com/", value.getLink());
    assertEquals("98735342324352", value.getShareId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedShareUnhide() {
    FeedShareValue value = openAndCheckFeedPostBasics("feed-share-unhide", FeedShareValue.class, ITEM_SHARE,
      ChangeValue.Verb.UNHIDE, new AbstractWebhookChangeListener() {
        @Override
        public void feedShareValue(FeedShareValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_98735342324352", value.getPostId());
    assertEquals("1234567890321", value.getFrom().getId());
    assertEquals("Let me google that for you", value.getMessage());
    assertEquals("http://www.google.com/", value.getLink());
    assertEquals("98735342324352", value.getShareId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedStatusEdited() {
    FeedStatusValue value = openAndCheckFeedPostBasics("feed-status-edited-25", FeedStatusValue.class, ITEM_STATUS,
      ChangeValue.Verb.EDITED, new AbstractWebhookChangeListener() {
        @Override
        public void feedStatusValue(FeedStatusValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("edited", value.getVerbAsString());
    assertEquals("Tester", value.getFrom().getName());
    assertEquals("status", value.getItem());
    assertEquals("This is an edited test message", value.getMessage());
    assertTrue(value.getPublished());
    assertEquals(0, value.getPhotos().size());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedStatusEdited_withPhotos() {
    FeedStatusValue value = openAndCheckFeedPostBasics("feed-status-edited-photos", FeedStatusValue.class, ITEM_STATUS,
      ChangeValue.Verb.EDITED, new AbstractWebhookChangeListener() {
        @Override
        public void feedStatusValue(FeedStatusValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("edited", value.getVerbAsString());
    assertEquals("Tester", value.getFrom().getName());
    assertEquals("status", value.getItem());
    assertEquals("This is an edited test message", value.getMessage());
    assertTrue(value.getPublished());
    assertEquals(3, value.getPhotos().size());
    assertTrue(value.getPhotos().get(0).contains("exampleimage1.png"));
    assertTrue(value.getPhotos().get(1).contains("exampleimage2.png"));
    assertTrue(value.getPhotos().get(2).contains("exampleimage3.png"));
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedStatusAdd() {
    FeedStatusValue value = openAndCheckFeedPostBasics("feed-status-add-25", FeedStatusValue.class, ITEM_STATUS,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedStatusValue(FeedStatusValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertTrue(value.getPublished());
    assertEquals("1234567890321_930145403745849", value.getPostId());
    assertEquals("Tester", value.getFrom().getName());
    assertEquals(1448633038000L, value.getCreatedTime().getTime());
    assertEquals(0, value.getPhotos().size());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedStatusAdd_withPhotos() {
    FeedStatusValue value = openAndCheckFeedPostBasics("feed-status-add-photos", FeedStatusValue.class, ITEM_STATUS,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedStatusValue(FeedStatusValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertTrue(value.getPublished());
    assertEquals("1234567890321_930145403745849", value.getPostId());
    assertEquals("Tester", value.getFrom().getName());
    assertEquals(1448633038000L, value.getCreatedTime().getTime());
    assertEquals(3, value.getPhotos().size());
    assertTrue(value.getPhotos().get(0).contains("exampleimage1.png"));
    assertTrue(value.getPhotos().get(1).contains("exampleimage2.png"));
    assertTrue(value.getPhotos().get(2).contains("exampleimage3.png"));
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedStatusHide() {
    FeedStatusValue value = openAndCheckFeedPostBasics("feed-status-hide-25", FeedStatusValue.class, ITEM_STATUS,
      ChangeValue.Verb.HIDE, new AbstractWebhookChangeListener() {
        @Override
        public void feedStatusValue(FeedStatusValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_7293787835232", value.getPostId());
    assertEquals("Some Tester", value.getFrom().getName());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedStatusUnhide() {
    FeedStatusValue value = openAndCheckFeedPostBasics("feed-status-unhide", FeedStatusValue.class, ITEM_STATUS,
      ChangeValue.Verb.UNHIDE, new AbstractWebhookChangeListener() {
        @Override
        public void feedStatusValue(FeedStatusValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("123456789_64352426", value.getPostId());
    assertEquals("Tester", value.getFrom().getName());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedLikeAdd() {
    FeedLikeValue value = openAndCheckFeedPostBasics("feed-like-add-25", FeedLikeValue.class, ITEM_LIKE,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedLikeValue(FeedLikeValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertFalse(value.isPageLike());
    assertEquals("909653922461664_940663242694065", value.getParentId());
    assertNull(value.getUserId());
    assertEquals("909653922461664_940663242694065", value.getPostId());
    assertTrue(value.isPostLike());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedLikeAddComment() {
    FeedLikeValue value = openAndCheckFeedPostBasics("feed-like-add-comment-25", FeedLikeValue.class, ITEM_LIKE,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedLikeValue(FeedLikeValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertFalse(value.isPageLike());
    assertNull(value.getUserId());
    assertEquals("940663242694065_659751347534292", value.getCommentId());
    assertFalse(value.isPostLike());
    assertTrue(value.isCommentLike());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedLikeAddPage() {
    FeedLikeValue value = openAndCheckFeedPostBasics("feed-like-add-page-25", FeedLikeValue.class, ITEM_LIKE,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedLikeValue(FeedLikeValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertTrue(value.isPageLike());
    assertEquals("10201978818232600", value.getUserId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedLikeRemove() {
    FeedLikeValue value = openAndCheckFeedPostBasics("feed-like-remove-25", FeedLikeValue.class, ITEM_LIKE,
      ChangeValue.Verb.REMOVE, new AbstractWebhookChangeListener() {
        @Override
        public void feedLikeValue(FeedLikeValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321_940663242694065", value.getParentId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedPhotoAdd() {
    FeedPhotoAddValue value = openAndCheckFeedPostBasics("feed-photo-add-25", FeedPhotoAddValue.class, ITEM_PHOTO,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedPhotoAddValue(FeedPhotoAddValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertTrue(value.getPublished());
    assertEquals("https://www.example.org/test.jpg", value.getLink());
    assertEquals("900767076685784", value.getPhotoId());
    assertNull(value.getMessage());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedPhotoAddWithMessage() {
    FeedPhotoAddValue value = openAndCheckFeedPostBasics("feed-photo-add-message", FeedPhotoAddValue.class, ITEM_PHOTO,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedPhotoAddValue(FeedPhotoAddValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertTrue(value.getPublished());
    assertEquals("https://www.example.org/test.jpg", value.getLink());
    assertEquals("900767076685784", value.getPhotoId());
    assertNotNull(value.getMessage());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedPhotoEdited() {
    FeedPhotoAddValue value = openAndCheckFeedPostBasics("feed-photo-edited", FeedPhotoAddValue.class, ITEM_PHOTO,
      ChangeValue.Verb.EDITED, new AbstractWebhookChangeListener() {
        @Override
        public void feedPhotoAddValue(FeedPhotoAddValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertTrue(value.getPublished());
    assertEquals("https://www.example.org/test.jpg", value.getLink());
    assertEquals("900767076685784", value.getPhotoId());
    assertNull(value.getMessage());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedPhotoEditedWithMessage() {
    FeedPhotoAddValue value = openAndCheckFeedPostBasics("feed-photo-edited-message", FeedPhotoAddValue.class,
      ITEM_PHOTO, ChangeValue.Verb.EDITED, new AbstractWebhookChangeListener() {
        @Override
        public void feedPhotoAddValue(FeedPhotoAddValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertTrue(value.getPublished());
    assertEquals("https://www.example.org/test.jpg", value.getLink());
    assertEquals("900767076685784", value.getPhotoId());
    assertNotNull(value.getMessage());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedPhotoHide() {
    FeedPhotoAddValue value = openAndCheckFeedPostBasics("feed-photo-hide", FeedPhotoAddValue.class, ITEM_PHOTO,
      ChangeValue.Verb.HIDE, new AbstractWebhookChangeListener() {
        @Override
        public void feedPhotoAddValue(FeedPhotoAddValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals(ChangeValue.Verb.HIDE, value.getVerb());
    assertEquals("https://www.example.org/test.jpg", value.getLink());
    assertEquals("1063068383729088", value.getPhotoId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedPhotoRemove() {
    FeedPhotoRemoveValue value = openAndCheckFeedPostBasics("feed-photo-remove-25", FeedPhotoRemoveValue.class,
      ITEM_PHOTO, ChangeValue.Verb.REMOVE, new AbstractWebhookChangeListener() {
        @Override
        public void feedPhotoRemoveValue(FeedPhotoRemoveValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321", value.getRecipientId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedVideoAdd() {
    FeedVideoValue value = openAndCheckFeedPostBasics("feed-video-add", FeedVideoValue.class, ITEM_VIDEO,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void feedVideoValue(FeedVideoValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("https://www.example.org/test.mp4", value.getLink());
    assertEquals("900767076685784", value.getVideoId());
    assertNotNull(value.getMessage());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedVideoUnblock() {
    FeedVideoBlockMute value = openAndCheckFeedPostBasics("feed-video-unblock", FeedVideoBlockMute.class, ITEM_VIDEO,
      ChangeValue.Verb.UNBLOCK, new AbstractWebhookChangeListener() {
        @Override
        public void feedVideoBlockMute(FeedVideoBlockMute convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("https://www.example.org/test.mp4", value.getLink());
    assertEquals("900767076685784", value.getVideoId());
    assertNotNull(value.getMessage());
    assertEquals(1, value.getVideoFlagReason().intValue());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedVideoBlock() {
    FeedVideoBlockMute value = openAndCheckFeedPostBasics("feed-video-block", FeedVideoBlockMute.class, ITEM_VIDEO,
      ChangeValue.Verb.BLOCK, new AbstractWebhookChangeListener() {
        @Override
        public void feedVideoBlockMute(FeedVideoBlockMute convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("https://www.example.org/test.mp4", value.getLink());
    assertEquals("900767076685784", value.getVideoId());
    assertNotNull(value.getMessage());
    assertEquals(1, value.getVideoFlagReason().intValue());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedVideoMute() {
    FeedVideoBlockMute value = openAndCheckFeedPostBasics("feed-video-mute", FeedVideoBlockMute.class, ITEM_VIDEO,
      ChangeValue.Verb.MUTE, new AbstractWebhookChangeListener() {
        @Override
        public void feedVideoBlockMute(FeedVideoBlockMute convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("https://www.example.org/test.mp4", value.getLink());
    assertEquals("900767076685784", value.getVideoId());
    assertNotNull(value.getMessage());
    assertEquals(1, value.getVideoFlagReason().intValue());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedVideoEdited() {
    FeedVideoValue value = openAndCheckFeedPostBasics("feed-video-edited", FeedVideoValue.class, ITEM_VIDEO,
      ChangeValue.Verb.EDITED, new AbstractWebhookChangeListener() {
        @Override
        public void feedVideoValue(FeedVideoValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("https://www.example.org/test.mp4", value.getLink());
    assertEquals("900767076685784", value.getVideoId());
    assertTrue(value.getPublished());
    assertNotNull(value.getMessage());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedVideoHide() {
    FeedVideoValue value = openAndCheckFeedPostBasics("feed-video-hide", FeedVideoValue.class, ITEM_VIDEO,
      ChangeValue.Verb.HIDE, new AbstractWebhookChangeListener() {
        @Override
        public void feedVideoValue(FeedVideoValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("https://www.example.org/test.mp4", value.getLink());
    assertEquals("900767076685784", value.getVideoId());
    assertTrue(value.getPublished());
    assertNull(value.getMessage());
    assertEquals(1549019010000L, value.getCreatedTime().getTime());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedVideoRemove() {
    FeedVideoRemoveValue value = openAndCheckFeedPostBasics("feed-video-remove-25", FeedVideoRemoveValue.class,
      ITEM_VIDEO, ChangeValue.Verb.REMOVE, new AbstractWebhookChangeListener() {
        @Override
        public void feedVideoRemoveValue(FeedVideoRemoveValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890321", value.getRecipientId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsCommentAdd() {
    RatingsCommentValue value = openAndCheckBasics("ratings-comment-add-25", RatingsCommentValue.class, FIELD_RATINGS,
      ITEM_COMMENT, ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsCommentValue(RatingsCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("6767676767", value.getOpenGraphStoryId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsCommentEdited() {
    RatingsCommentValue value = openAndCheckBasics("ratings-comment-edited-25", RatingsCommentValue.class,
      FIELD_RATINGS, ITEM_COMMENT, ChangeValue.Verb.EDITED, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsCommentValue(RatingsCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("716630661754264_994838177266843", value.getCommentId());
    assertFalse(value.isReply());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsCommentRemove() {
    RatingsCommentValue value = openAndCheckBasics("ratings-comment-remove-25", RatingsCommentValue.class,
      FIELD_RATINGS, ITEM_COMMENT, ChangeValue.Verb.REMOVE, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsCommentValue(RatingsCommentValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("1234567890123", value.getFrom().getId());
    assertEquals("100002220109526_716630661754264", value.getParentId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsRatingAdd() {
    RatingsRatingValue value = openAndCheckBasics("ratings-rating-add-25", RatingsRatingValue.class, FIELD_RATINGS,
      ITEM_RATING, ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsRatingValue(RatingsRatingValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals(3L, value.getRating().longValue());
    assertEquals("Tester", value.getReviewerName());
    assertEquals("Ja ziemlich coole Sache", value.getReviewText());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsRatingAddWithRecommendation() {
    RatingsRatingValue value = openAndCheckBasics("ratings-rating-add-31", RatingsRatingValue.class, FIELD_RATINGS,
      ITEM_RATING, ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsRatingValue(RatingsRatingValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals(3L, value.getRating().longValue());
    assertEquals("Tester", value.getReviewerName());
    assertEquals("Ja ziemlich coole Sache", value.getReviewText());
    assertEquals(RecommendationType.POSITIVE, value.getRecommendationType());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsRatingEdit() {
    RatingsRatingValue value = openAndCheckBasics("ratings-rating-edit-25", RatingsRatingValue.class, FIELD_RATINGS,
      ITEM_RATING, ChangeValue.Verb.EDIT, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsRatingValue(RatingsRatingValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals(3L, value.getRating().longValue());
    assertEquals("Tester", value.getReviewerName());
    assertEquals("Ja ziemlich coole Sache", value.getReviewText());
    assertNull(value.getCommentId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsRatingRemove() {
    RatingsRatingValue value = openAndCheckBasics("ratings-rating-remove-25", RatingsRatingValue.class, FIELD_RATINGS,
      ITEM_RATING, ChangeValue.Verb.REMOVE, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsRatingValue(RatingsRatingValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals("904801129604590", value.getOpenGraphStoryId());
    assertEquals("705955836155788", value.getReviewerId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsLikeAdd() {
    RatingsLikeValue value = openAndCheckBasics("ratings-like-add-25", RatingsLikeValue.class, FIELD_RATINGS, ITEM_LIKE,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsLikeValue(RatingsLikeValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals(1451775296000L, value.getCreatedTime().getTime());
    assertEquals("Tester", value.getFrom().getName());
    assertEquals("100002241334695_904801129604590", value.getParentId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsLikeAdd40() {
    RatingsLikeValue value = openAndCheckBasics("ratings-like-add-40", RatingsLikeValue.class, FIELD_RATINGS, ITEM_LIKE,
      ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsLikeValue(RatingsLikeValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertEquals(1451775296000L, value.getCreatedTime().getTime());
    assertEquals("Tester", value.getFrom().getName());
    assertEquals("100002241334695_904801129604590", value.getParentId());
    assertNull(value.getReviewText());
    assertEquals("2782258465134925", value.getOpenGraphStoryId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsLikeRemove() {
    RatingsLikeValue value = openAndCheckBasics("ratings-like-remove-25", RatingsLikeValue.class, FIELD_RATINGS,
      ITEM_LIKE, ChangeValue.Verb.REMOVE, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsLikeValue(RatingsLikeValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertNotNull(value);
    assertEquals(1451775365000L, value.getCreatedTime().getTime());
    assertEquals("100002241334695_904801129604590", value.getParentId());
    assertEquals("705955836155788", value.getFrom().getId());
    assertNull(value.getReviewText());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsLikeRemove40() {
    RatingsLikeValue value = openAndCheckBasics("ratings-like-remove-40", RatingsLikeValue.class, FIELD_RATINGS,
      ITEM_LIKE, ChangeValue.Verb.REMOVE, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsLikeValue(RatingsLikeValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertNotNull(value);
    assertEquals(1451775296000L, value.getCreatedTime().getTime());
    assertEquals("Tester", value.getFrom().getName());
    assertEquals("100002241334695_904801129604590", value.getParentId());
    assertNull(value.getReviewText());
    assertEquals("2782258465134925", value.getOpenGraphStoryId());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsReactionAdd() {
    RatingsReactionValue value = openAndCheckBasics("ratings-reaction-add", RatingsReactionValue.class, FIELD_RATINGS,
      ITEM_REACTION, ChangeValue.Verb.ADD, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsReactionValue(RatingsReactionValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertNull(value.getReviewText());
    assertNull(value.getCommentId());
    assertEquals("716630661754264", value.getOpenGraphStoryId());
    assertEquals("haha", value.getReactionType());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsReactionEdit() {
    RatingsReactionValue value = openAndCheckBasics("ratings-reaction-edit", RatingsReactionValue.class, FIELD_RATINGS,
      ITEM_REACTION, ChangeValue.Verb.EDIT, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsReactionValue(RatingsReactionValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertNull(value.getReviewText());
    assertNull(value.getCommentId());
    assertEquals("716630661754264", value.getOpenGraphStoryId());
    assertEquals("angry", value.getReactionType());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void ratingsReactionRemove() {
    RatingsReactionValue value = openAndCheckBasics("ratings-reaction-remove", RatingsReactionValue.class,
      FIELD_RATINGS, ITEM_REACTION, ChangeValue.Verb.REMOVE, new AbstractWebhookChangeListener() {
        @Override
        public void ratingsReactionValue(RatingsReactionValue convertChangeValue) {
          assertNotNull(convertChangeValue);
          listenerTriggered.set(true);
        }
      });
    assertNull(value.getReviewText());
    assertNull(value.getCommentId());
    assertEquals("716630661754264", value.getOpenGraphStoryId());
    assertEquals("angry", value.getReactionType());
    assertTrue(listenerTriggered.get());
  }

  @Test
  void feedEventAdd() {
    WebhookObject webhookObject = openJson("feed-event-add");
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals("feed", change.getField(), "change field");
    assertEquals(change.getValue().getClass(), FeedEventValue.class, "change value class");
    FeedEventValue value = (FeedEventValue) change.getValue();
    assertEquals("1944041199140689", value.getEventId(), "change event id");
    assertEquals("1234567890321_1944041199140689", value.getPostId(), "change post id");
    assertEquals("Page added an event.", value.getStory(), "change story");
  }

  @Test
  void pageLeadgen() {
    WebhookObject webhookObject = openJson("leadgen");
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals(change.getValue().getClass(), PageLeadgen.class, "change value class");
    PageLeadgen pageLeadgen = (PageLeadgen) change.getValue();
    assertEquals("0", pageLeadgen.getAdgroupId(), "leadgen adgroup id");
    assertEquals("0", pageLeadgen.getAdId(), "leadgen ad id");
    assertEquals("12345", pageLeadgen.getFormId(), "leadgen form id");
    assertEquals("1636129430026801", pageLeadgen.getLeadgenId(), "leadgen leadgen id");
    assertEquals("12345", pageLeadgen.getPageId(), "leadgen page id");
    assertEquals(1485964825000L, pageLeadgen.getCreatedTime().getTime(), "leadgen created time");
  }

  @Test
  void unknownChangeValue() {
    WebhookObject webhookObject = openJson("unknown-change-value");
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals(change.getValue().getClass(), FallBackChangeValue.class, "change value class");
    assertNotNull(((FallBackChangeValue) change.getValue()).getRawJson());
  }

  @Test
  void userWorkHistoryChange() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/user-workhistory"), WebhookObject.class);
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertNotNull(change.getValue());
    assertEquals(ListJsonChangeValue.class, change.getValue().getClass());
    ListJsonChangeValue changeValue = (ListJsonChangeValue) change.getValue();
    assertEquals(1, changeValue.getValue().size());
    assertEquals(JsonObject.class, changeValue.getValue().get(0).getClass());
  }

  @Test
  void userEmailChange() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/user-email"), WebhookObject.class);
    assertTrue(webhookObject.isUser());
    assertFalse(webhookObject.isPage());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertNotNull(change.getValue());
    assertEquals(SimpleStringChangeValue.class, change.getValue().getClass());
    SimpleStringChangeValue changeValue = (SimpleStringChangeValue) change.getValue();
    assertEquals("example_email@facebook.com", changeValue.getValue());
  }

  @Test
  void mentionPostAdd() {
    WebhookObject webhookObject = openJson("mention-post-add");
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals(change.getValue().getClass(), MentionPostAddValue.class, "change value class");
    MentionPostAddValue mentionPostAddValue = (MentionPostAddValue) change.getValue();
    assertNotNull(mentionPostAddValue);
    assertEquals("44444444_444444444", mentionPostAddValue.getPostId());
    assertEquals("44444444", mentionPostAddValue.getSenderId());
    assertEquals("Example Name", mentionPostAddValue.getSenderName());
    assertEquals("post", mentionPostAddValue.getItem());
    assertEquals(ChangeValue.Verb.ADD, mentionPostAddValue.getVerb());
  }

  @Test
  void mentionPostAdd_realData() {
    WebhookObject webhookObject = openJson("mention-post-add-real");
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals(change.getValue().getClass(), MentionPostAddValue.class, "change value class");
    MentionPostAddValue mentionPostAddValue = (MentionPostAddValue) change.getValue();
    assertNotNull(mentionPostAddValue);
    assertEquals("1234567890321_98722536423179", mentionPostAddValue.getPostId());
    assertEquals("post", mentionPostAddValue.getItem());
    assertEquals(ChangeValue.Verb.ADD, mentionPostAddValue.getVerb());
  }

  @Test
  void mentionCommentAdd() {
    WebhookObject webhookObject = openJson("mention-comment-add");
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals(change.getValue().getClass(), MentionCommentAddValue.class, "change value class");
    MentionCommentAddValue mentionCommentAddValue = (MentionCommentAddValue) change.getValue();
    assertNotNull(mentionCommentAddValue);
    assertEquals("1234567890321_7363534231324", mentionCommentAddValue.getPostId());
    assertEquals("7363534231324_3754798971271444", mentionCommentAddValue.getCommentId());
    assertEquals("comment", mentionCommentAddValue.getItem());
    assertEquals(ChangeValue.Verb.ADD, mentionCommentAddValue.getVerb());
  }

  @Test
  void feedTwoEntries() {
    WebhookObject webhookObject = openJson("feed-two-entries-25");
    assertEquals(2, webhookObject.getEntryList().size(), "entry count");
    assertEquals("parking", webhookObject.getEntryList().get(0).getChanges().get(0).getField(), "change[0] field");
    assertEquals("payment_options", webhookObject.getEntryList().get(1).getChanges().get(0).getField(),
      "change[1] field");
  }

  @Test
  void messagingPayment() {
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
  void checkoutUpdateShipping() {
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
  void quickReply() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-quickreply"), WebhookObject.class);
    assertNotNull(webhookObject);
    MessagingItem item = webhookObject.getEntryList().get(0).getMessaging().get(0);
    MessageItem messageItem = item.getMessage();
    assertTrue(messageItem.hasQuickReply());
    QuickReplyItem qrItem = messageItem.getQuickReply();
    assertNotNull(qrItem);
    assertEquals("PAYLOAD_QUICK_REPLY", qrItem.getPayload());
  }

  @Test
  void stickerId() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/messaging-message-sticker"), WebhookObject.class);
    assertNotNull(webhookObject);
    MessagingItem messageItem = webhookObject.getEntryList().get(0).getMessaging().get(0);
    assertNotNull(messageItem);
    assertNotNull(messageItem.getMessage());
    assertEquals("56789012345678", messageItem.getMessage().getStickerId());
    assertFalse(messageItem.getMessage().isLike());
    assertNotNull(messageItem.getMessage().getAttachments());
    assertEquals(1, messageItem.getMessage().getAttachments().size());
    MessagingAttachment attachment = messageItem.getMessage().getAttachments().get(0);
    assertNotNull(attachment.getPayload());
    assertEquals("56789012345678", attachment.getPayload().getStickerId());
    assertFalse(attachment.getPayload().isLike());
  }

  @Test
  void unsupportedAttachment() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("instagram/messaging-attachment-unsupported"), WebhookObject.class);
    assertNotNull(webhookObject);
    MessagingItem messageItem = webhookObject.getEntryList().get(0).getMessaging().get(0);
    assertNotNull(messageItem);
    assertTrue(messageItem.getMessage().hasAttachment());
    MessagingAttachment attachment = messageItem.getMessage().getAttachments().get(0);
    assertTrue(attachment.isUnsupportedType());
    assertNotNull(attachment.getPayload());
    MessagingPayload payload = attachment.getPayload();
    assertNotNull(payload.getUrl());
  }

  @Test
  void instagramMessageRead() {
    WebhookObject webhookObject = createJsonMapper()
            .toJavaObject(jsonFromClasspath("instagram/messaging-seen"), WebhookObject.class);
    assertNotNull(webhookObject);
    MessagingItem messageItem = webhookObject.getEntryList().get(0).getMessaging().get(0);
    assertNotNull(messageItem);
    ReadItem readItem = messageItem.getRead();
    assertTrue(readItem.hasMid());
    assertFalse(readItem.hasWatermark());
    assertNotNull(readItem);
    assertNotNull(readItem.getMid());
    assertEquals(readItem.getMid(), "MESSAGE-ID");
  }

  @Test
  void stickerId_isLike() {
    WebhookObject webhookObject = createJsonMapper()
      .toJavaObject(jsonFromClasspath("webhooks/messaging-message-sticker-thumbup"), WebhookObject.class);
    assertNotNull(webhookObject);
    MessagingItem messageItem = webhookObject.getEntryList().get(0).getMessaging().get(0);
    assertNotNull(messageItem);
    assertNotNull(messageItem.getMessage());
    assertEquals("369239263222822", messageItem.getMessage().getStickerId());
    assertTrue(messageItem.getMessage().isLike());
    assertNotNull(messageItem.getMessage().getAttachments());
    assertEquals(1, messageItem.getMessage().getAttachments().size());
    MessagingAttachment attachment = messageItem.getMessage().getAttachments().get(0);
    assertNotNull(attachment.getPayload());
    assertEquals("369239263222822", attachment.getPayload().getStickerId());
    assertTrue(attachment.getPayload().isLike());
  }

  @Test
  void useEnumAsValue() {
    String val1 = Parameter.with("key", SenderActionEnum.typing_on).value;
    String val2 = Parameter.with("key", SenderActionEnum.typing_off).value;
    String val3 = Parameter.with("key", SenderActionEnum.mark_seen).value;
    assertEquals("typing_on", val1);
    assertEquals("typing_off", val2);
    assertEquals("mark_seen", val3);
  }

  @Test
  void userAddsLocation() {
    checkUserPageValueWithField("webhooks/user-location-add", "location");
  }

  @Test
  void userAddsHometown() {
    checkUserPageValueWithField("webhooks/user-hometown-add", "hometown");
  }

  @Test
  void userAddsLike() {
    checkUserPageValueWithField("webhooks/user-likes-add", "likes");
  }

  @Test
  void userAddsTelevision() {
    checkUserPageValueWithField("webhooks/user-television-add", "television");
  }

  @Test
  void userAddsMovies() {
    checkUserPageValueWithField("webhooks/user-movies-add", "movies");
  }

  private void checkUserPageValueWithField(String s, String movies) {
    WebhookObject webhookObject = createJsonMapper().toJavaObject(jsonFromClasspath(s), WebhookObject.class);
    assertNotNull(webhookObject);
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals(movies, change.getField());
    assertEquals(UserPageValue.class, change.getValue().getClass());
    UserPageValue userPageValue = (UserPageValue) change.getValue();
    assertEquals("9988776655443322", userPageValue.getPage());
    assertEquals(ChangeValue.Verb.ADD, userPageValue.getVerb());
  }

  private <T extends AbstractFeedPostValue> T openAndCheckFeedPostBasics(String jsonName, Class<T> changeValueClass,
      String expectedItem, ChangeValue.Verb expectedVerb, WebhookChangeListener listener) {
    return openAndCheckBasics(jsonName, changeValueClass, FIELD_FEED, expectedItem, expectedVerb, listener);
  }

  private <T extends BaseChangeValue> T openAndCheckBasics(String jsonName, Class<T> changeValueClass,
      String expectedField, String expectedItem, ChangeValue.Verb expectedVerb, WebhookChangeListener listener) {
    WebhookObject webhookObject = openJson(jsonName);
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertEquals(expectedField, change.getField(), "change field");
    assertEquals(change.getValue().getClass(), changeValueClass, "change value class");
    assertEquals(expectedItem, ((T) change.getValue()).getItem(), "change item");
    assertEquals(expectedVerb, ((T) change.getValue()).getVerb(), "change verb");
    webhookListener.registerListener(listener);
    webhookListener.process(webhookObject);
    return (T) change.getValue();
  }

  private WebhookObject openJson(String jsonName) {
    WebhookObject webhookObject = getWHObjectFromJson(jsonName);
    assertFalse(webhookObject.getEntryList().get(0).getChanges().isEmpty(),
      "webhookObject.entryList[0].changes not empty");
    return webhookObject;
  }

  private WebhookObject openMessagingJson(String jsonName) {
    WebhookObject webhookObject = getWHObjectFromJson(jsonName);
    assertFalse(webhookObject.getEntryList().get(0).getMessaging().isEmpty(),
      "webhookObject.entryList[0].messaging not empty");
    return webhookObject;
  }

  private WebhookObject getWHObjectFromJson(String jsonName) {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/" + jsonName), WebhookObject.class);
    assertNotNull(webhookObject, "webhookObject not null");
    assertEquals("page", webhookObject.getObject(), "webhookObject.object");
    assertFalse(webhookObject.getEntryList().isEmpty(), "webhookObject.entryList not empty");
    assertEquals("1234567890321", webhookObject.getEntryList().get(0).getId(), "page id");
    assertTrue(1445000000000L < webhookObject.getEntryList().get(0).getTime().getTime(), "update time");
    return webhookObject;
  }

}
