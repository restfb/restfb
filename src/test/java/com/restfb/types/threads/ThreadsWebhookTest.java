/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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
package com.restfb.types.threads;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;
import com.restfb.types.webhook.Change;
import com.restfb.types.webhook.ChangeValueFactory;
import com.restfb.types.webhook.FallBackChangeValue;
import com.restfb.types.webhook.ListJsonChangeValue;
import com.restfb.types.webhook.SimpleStringChangeValue;
import com.restfb.types.webhook.instagram.InstagramMentionsValue;
import com.restfb.types.webhook.threads.ThreadsMediaValue;
import com.restfb.types.webhook.threads.ThreadsReference;
import com.restfb.types.webhook.threads.ThreadsWebhookObject;

/**
 * The four base fixtures reproduce Meta's examples at https://developers.facebook.com/documentation/threads/webhooks/.
 * Additional fixtures and mutations are synthetic mapping tests, not captured API responses or guarantees about which
 * optional fields occur together.
 */
@SuppressWarnings("removal")
class ThreadsWebhookTest extends AbstractJsonMapperTests {

  @ParameterizedTest
  @CsvSource({ "replies, moderate", "mentions, interaction", "delete, moderate", "publish, interaction" })
  void mapOfficialExamples(String event, String topic) {
    ThreadsWebhookObject notification = notification(event);

    assertEquals("123456", notification.getAppId());
    assertEquals(topic, notification.getTopic());
    assertEquals("78901", notification.getTargetId());
    assertEquals(1723226877L, notification.getTime());
    assertEquals("234567", notification.getSubscriptionId());
    assertEquals(Boolean.FALSE, notification.getHasUidField());
    assertNotNull(notification.getValues());
    assertEquals(event, notification.getValues().getField());

    ThreadsMediaValue value = mediaValue(notification);
    assertNotNull(value.getMedia());
    assertEquals("8901234", value.getMedia().getId());
    assertEquals("test_username", value.getMedia().getUsername());
    assertEquals(Instant.parse("2024-08-07T10:33:16Z"), value.getMedia().getTimestamp().toInstant());
    ThMedia snapshot = value.toThMedia();
    assertEquals(value.getMedia().getId(), snapshot.getId());
    assertEquals(value.getMedia().getUsername(), snapshot.getUsername());
    assertEquals(value.getMedia().getTimestamp(), snapshot.getTimestamp());
  }

  @Test
  void thMediaUsesIncomingJsonRatherThanLegacyMutations() {
    ThreadsMediaValue value = mediaValue(notification("mentions"));
    TdMedia legacyMedia = value.getMedia();
    legacyMedia.setText("changed legacy text");
    assertSame(legacyMedia, value.getMedia());

    ThMedia snapshot = value.toThMedia();
    assertEquals("Reply", snapshot.getText());
    assertEquals(ThMediaType.TEXT_POST, snapshot.getMediaType());
    snapshot.setText("changed snapshot text");

    value.setMedia(null);
    assertNull(value.getMedia());
    assertEquals("Reply", value.toThMedia().getText());
    assertNull(new ThreadsMediaValue().toThMedia());
  }

  @Test
  void mapReplyReferences() {
    ThreadsMediaValue value = mediaValue(notification("replies"));
    TdMedia media = value.getMedia();

    assertEquals("Reply", media.getText());
    assertEquals(TdMediaType.TEXT_POST, media.getMediaType());
    assertEquals("https://www.threads.com/@test_username/post/Pp", media.getPermalink());
    assertEquals("Pp", media.getShortcode());
    assertEquals("567890", value.getRepliedTo().getId());
    assertEquals("123456", value.getRootPost().getId());
    assertEquals("123456", value.getRootPost().getOwnerId());
    assertEquals("test_username_2", value.getRootPost().getUsername());
    assertNull(media.getIsVerified());
    assertNull(media.getProfilePictureUrl());
    assertNull(value.getDeletedAt());
    assertNull(value.getOwner());
  }

  @Test
  void mapOptionalReplyAuthorProfileAndDistinctOwnerId() {
    ThreadsMediaValue value = mediaValue(notification("replies-with-author-profile"));

    assertTrue(value.getMedia().getIsVerified());
    assertEquals("https://example.org/author.jpg", value.getMedia().getProfilePictureUrl());
    assertEquals("123456", value.getRootPost().getId());
    assertEquals("654321", value.getRootPost().getOwnerId());
    assertEquals("567890", value.getRepliedTo().getId());
  }

  @Test
  void mapMentionFields() {
    TdMedia media = mediaValue(notification("mentions")).getMedia();

    assertEquals("test alt text", media.getAltText());
    assertTrue(media.getGifUrl().startsWith("https://media2.giphy.com/"));
    assertTrue(media.getHasReplies());
    assertFalse(media.getIsReply());
    assertFalse(media.getIsQuotePost());
    assertEquals("THREADS", media.getMediaProductType());
    assertEquals(TdMediaType.TEXT_POST, media.getMediaType());
    assertEquals("https://www.threads.com/@test_username/post/Pp", media.getPermalink());
    assertEquals("Pp", media.getShortcode());
    assertEquals("Reply", media.getText());
    assertNull(media.getIsVerified());
    assertNull(media.getMediaUrl());
    assertNull(media.getPollAttachment());
    assertNull(media.getThumbnailUrl());
  }

  @Test
  void mapMentionsWithoutReplyPermission() {
    ThreadsMediaValue value = mediaValue(notification("mentions-without-reply-permission"));
    TdMedia media = value.getMedia();

    assertNull(media.getHasReplies());
    assertNull(media.getIsReply());
    assertNull(value.toThMedia().getHasReplies());
    assertNull(value.toThMedia().getIsReply());
    assertNull(value.getRepliedTo());
    assertNull(value.getRootPost());
    assertFalse(media.getIsQuotePost());
    assertEquals("8901234", media.getId());
  }

  @Test
  void mapOptionalMentionFields() {
    ThreadsMediaValue value = mediaValue(notification("mentions-with-optional-fields"));
    TdMedia media = value.getMedia();

    assertEquals("https://example.org/video.mp4", media.getMediaUrl());
    assertEquals("https://example.org/thumbnail.jpg", media.getThumbnailUrl());
    assertEquals("https://example.org/author.jpg", media.getProfilePictureUrl());
    assertFalse(media.getIsVerified());
    assertFalse(media.getHasReplies());
    assertTrue(media.getIsReply());
    assertTrue(media.getIsQuotePost());
    assertEquals("123456", value.getRootPost().getId());
    assertEquals("654321", value.getRootPost().getOwnerId());
    assertEquals("test_username_2", value.getRootPost().getUsername());
    assertEquals("567890", value.getRepliedTo().getId());
    assertEquals("456789", value.getQuotedPost().getId());
    assertEquals("345678", value.getRepostedPost().getId());
    assertNotNull(media.getPollAttachment());
    assertEquals("First", media.getPollAttachment().getOptionA());
    assertEquals("Second", media.getPollAttachment().getOptionB());
    assertEquals(0.25, media.getPollAttachment().getOptionAVotesPercentage(), 0.001);
    assertEquals(0.75, media.getPollAttachment().getOptionBVotesPercentage(), 0.001);
    assertEquals(Instant.parse("2024-08-08T10:33:16Z"), media.getPollAttachment().getExpirationTimestamp().toInstant());
  }

  @Test
  void mapDeletionOwnerAndTimestamp() {
    ThreadsMediaValue value = mediaValue(notification("delete"));

    assertEquals("78901", value.getOwner().getOwnerId());
    assertNull(value.getOwner().getId());
    assertEquals(Instant.parse("2024-08-07T10:33:16Z"), value.getDeletedAt().toInstant());
    assertNull(value.getMedia().getMediaType());
    assertNull(value.getRootPost());
    assertNull(value.getRepliedTo());
  }

  @Test
  void preserveDistinctPublicationDeletionAndNotificationTimes() {
    JsonObject json = fixture("delete");
    json.set("time", 1723226878L);
    json.get("values").asObject().get("value").asObject().set("deleted_at", "2024-08-08T11:33:16+0100");
    ThreadsWebhookObject notification = map(json);
    ThreadsMediaValue value = mediaValue(notification);

    assertEquals(1723226878L, notification.getTime());
    assertEquals(Instant.parse("2024-08-07T10:33:16Z"), value.getMedia().getTimestamp().toInstant());
    assertEquals(Instant.parse("2024-08-08T10:33:16Z"), value.getDeletedAt().toInstant());
  }

  @Test
  void mapPublishFields() {
    TdMedia media = mediaValue(notification("publish")).getMedia();

    assertEquals(TdMediaType.TEXT_POST, media.getMediaType());
    assertEquals("https://www.threads.com/@test_username/post/Pp", media.getPermalink());
    assertNull(media.getText());
    assertNull(media.getHasReplies());
  }

  @ParameterizedTest
  @ValueSource(strings = { "true", "false", "null" })
  void preserveOptionalBooleanValues(String rawValue) {
    JsonObject json = fixture("mentions");
    json.set("has_uid_field", Json.parse(rawValue));
    JsonObject value = json.get("values").asObject().get("value").asObject();
    value.set("has_replies", Json.parse(rawValue));
    value.set("is_reply", Json.parse(rawValue));
    value.set("is_quote_post", Json.parse(rawValue));
    value.set("is_verified", Json.parse(rawValue));
    ThreadsWebhookObject notification = map(json);
    TdMedia media = mediaValue(notification).getMedia();
    Boolean expected = "null".equals(rawValue) ? null : Boolean.valueOf(rawValue);

    assertEquals(expected, notification.getHasUidField());
    assertEquals(expected, media.getHasReplies());
    assertEquals(expected, media.getIsReply());
    assertEquals(expected, media.getIsQuotePost());
    assertEquals(expected, media.getIsVerified());
  }

  @Test
  void mapMissingAndNullEnvelopeFields() {
    ThreadsWebhookObject empty = createJsonMapper().toJavaObject("{}", ThreadsWebhookObject.class);
    ThreadsWebhookObject explicitNull = createJsonMapper()
      .toJavaObject("{\"time\":null,\"has_uid_field\":null,\"values\":null}", ThreadsWebhookObject.class);

    for (ThreadsWebhookObject notification : new ThreadsWebhookObject[] { empty, explicitNull }) {
      assertNull(notification.getAppId());
      assertNull(notification.getTopic());
      assertNull(notification.getTargetId());
      assertNull(notification.getSubscriptionId());
      assertNull(notification.getTime());
      assertNull(notification.getHasUidField());
      assertNull(notification.getValues());
    }
  }

  @Test
  void retainLongNotificationTimestampAndUnknownTopic() {
    JsonObject json = fixture("publish").set("time", 4102444800L).set("topic", "future-topic");
    ThreadsWebhookObject notification = map(json);

    assertEquals(4102444800L, notification.getTime());
    assertEquals("future-topic", notification.getTopic());
    assertInstanceOf(ThreadsMediaValue.class, notification.getValues().getValue());
  }

  @ParameterizedTest
  @ValueSource(strings = { "root_post", "replied_to", "owner", "quoted_post", "reposted_post" })
  void acceptScalarAndExpandedReferences(String field) {
    JsonObject json = fixture("mentions");
    JsonObject change = json.get("values").asObject();
    JsonObject value = change.get("value").asObject();
    value.set(field, "987654");
    ThreadsMediaValue scalar = mediaValue(map(json));
    value.set(field, new JsonObject().add("id", "987654").add("owner_id", "654321").add("username", "author"));
    ThreadsMediaValue expanded = mediaValue(map(json));

    assertReference(field, scalar, "987654", null);
    assertReference(field, expanded, "987654", "654321");
    value.set(field, Json.NULL);
    ThreadsMediaValue explicitNull = mediaValue(map(json));
    assertNull(explicitNull.getRootPost());
    assertNull(explicitNull.getRepliedTo());
    assertNull(explicitNull.getOwner());
    assertNull(explicitNull.getQuotedPost());
    assertNull(explicitNull.getRepostedPost());
  }

  @Test
  void retainUnknownEventJsonWithoutUsingInstagramMappings() {
    JsonObject json = fixture("mentions");
    json.get("values").asObject().set("field", "comments");
    ThreadsWebhookObject notification = map(json);

    FallBackChangeValue value = assertInstanceOf(FallBackChangeValue.class, notification.getValues().getValue());
    assertEquals("8901234", value.getRawJson().getString("id", null));
    assertEquals("Reply", value.getRawJson().getString("text", null));
  }

  @Test
  void keepDefaultAndThreadsFactoryNamespacesIndependent() {
    ChangeValueFactory factory = new ChangeValueFactory().setField("mentions").setValue("{\"id\":\"123\"}");

    assertInstanceOf(InstagramMentionsValue.class, factory.buildWithMapper(createJsonMapper()));
    assertInstanceOf(ThreadsMediaValue.class,
      factory.setPlatform(ChangeValueFactory.Platform.THREADS).buildWithMapper(createJsonMapper()));
    assertInstanceOf(InstagramMentionsValue.class,
      factory.setPlatform(ChangeValueFactory.Platform.DEFAULT).buildWithMapper(createJsonMapper()));
  }

  @Test
  void resolveThreadsFieldsIndependentlyOfDefaultLocale() {
    Locale original = Locale.getDefault();
    try {
      Locale.setDefault(Locale.forLanguageTag("tr-TR"));
      assertInstanceOf(ThreadsMediaValue.class, notification("publish").getValues().getValue());
    } finally {
      Locale.setDefault(original);
    }
  }

  @ParameterizedTest
  @CsvSource({ "'\"future-value\"', string", "'[{\"id\":\"future\"}]', list" })
  void retainNonObjectValues(String rawValue, String expectedType) {
    JsonObject json = fixture("mentions");
    json.get("values").asObject().set("value", Json.parse(rawValue));

    Change change = map(json).getValues();
    if ("string".equals(expectedType)) {
      assertInstanceOf(SimpleStringChangeValue.class, change.getValue());
    } else {
      assertInstanceOf(ListJsonChangeValue.class, change.getValue());
    }
  }

  private void assertReference(String field, ThreadsMediaValue value, String id, String ownerId) {
    ThreadsReference reference;
    switch (field) {
    case "root_post":
      reference = value.getRootPost();
      break;
    case "replied_to":
      reference = value.getRepliedTo();
      break;
    case "owner":
      reference = value.getOwner();
      break;
    case "quoted_post":
      reference = value.getQuotedPost();
      break;
    default:
      reference = value.getRepostedPost();
    }
    assertNotNull(reference);
    assertEquals(id, reference.getId());
    assertEquals(ownerId, reference.getOwnerId());
    if (ownerId != null) {
      assertEquals("author", reference.getUsername());
    }
  }

  private ThreadsMediaValue mediaValue(ThreadsWebhookObject notification) {
    return assertInstanceOf(ThreadsMediaValue.class, notification.getValues().getValue());
  }

  private ThreadsWebhookObject notification(String fixture) {
    return map(fixture(fixture));
  }

  private JsonObject fixture(String fixture) {
    return Json.parse(jsonFromClasspath("threads/webhooks/" + fixture)).asObject();
  }

  private ThreadsWebhookObject map(JsonObject json) {
    return createJsonMapper().toJavaObject(json.toString(), ThreadsWebhookObject.class);
  }
}
