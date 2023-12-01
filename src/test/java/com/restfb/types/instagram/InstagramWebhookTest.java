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
package com.restfb.types.instagram;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.webhook.Change;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.instagram.InstagramCommentsValue;
import com.restfb.types.webhook.instagram.InstagramMentionsValue;
import com.restfb.types.webhook.instagram.InstagramStoryInsightsValue;

import static org.junit.jupiter.api.Assertions.*;

class InstagramWebhookTest extends AbstractJsonMapperTests {

  @Test
  void checkComments() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("instagram/wh-comments"), WebhookObject.class);
    checkInstagramCommentWebhook(webhookObject, "comments");
  }

  @Test
  void checkLiveComments() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("instagram/wh-livecomments"), WebhookObject.class);
    checkInstagramCommentWebhook(webhookObject, "live_comments");
  }

  @Test
  void checkComments2021() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("instagram/wh-comments-2021"), WebhookObject.class);
    InstagramCommentsValue value = checkInstagramCommentWebhook(webhookObject, "comments");

    assertNotNull(value.getFrom());
    assertEquals("1234536447", value.getFrom().getId());
    assertEquals("testuser", value.getFrom().getUsername());
    assertEquals("987654321", value.getParentId());
    assertNotNull(value.getMedia());
    assertEquals("76543987", value.getMedia().getId());
    assertEquals("FEED", value.getMedia().getMediaProductType());
  }

  private InstagramCommentsValue checkInstagramCommentWebhook(WebhookObject webhookObject, String field) {
    assertEquals("instagram", webhookObject.getObject());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    Change change = entry.getChanges().get(0);
    assertEquals(field, change.getField());

    assertTrue(change.getValue() instanceof InstagramCommentsValue);
    InstagramCommentsValue value = (InstagramCommentsValue) change.getValue();
    assertEquals("123456789", value.getId());
    assertEquals("This is an example.", value.getText());
    return value;
  }

  @Test
  void checkMentions() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("instagram/wh-mentions"), WebhookObject.class);
    assertEquals("instagram", webhookObject.getObject());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    Change change = entry.getChanges().get(0);
    assertEquals("mentions", change.getField());

    assertTrue(change.getValue() instanceof InstagramMentionsValue);
    InstagramMentionsValue value = (InstagramMentionsValue) change.getValue();

    assertEquals("17887498072083520", value.getCommentId());
    assertEquals("17887498072083520", value.getMediaId());
  }

  @Test
  void checkStoryInsights() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("instagram/wh-story_insights"), WebhookObject.class);
    assertEquals("instagram", webhookObject.getObject());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    Change change = entry.getChanges().get(0);
    assertEquals("story_insights", change.getField());

    assertTrue(change.getValue() instanceof InstagramStoryInsightsValue);
    InstagramStoryInsightsValue value = (InstagramStoryInsightsValue) change.getValue();

    assertEquals("17887498072083520", value.getMediaId());
    assertEquals(3L, value.getExits().longValue());
    assertEquals(444L, value.getImpressions().longValue());
    assertEquals(44L, value.getReach().longValue());
    assertEquals(0L, value.getReplies().longValue());
    assertEquals(3L, value.getTapsBack().longValue());
    assertEquals(4L, value.getTapsForward().longValue());
  }
}
