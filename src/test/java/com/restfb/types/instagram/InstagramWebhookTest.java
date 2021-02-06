/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.webhook.Change;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.instagram.InstagramCommentsValue;
import com.restfb.types.webhook.instagram.InstagramMentionsValue;
import com.restfb.types.webhook.instagram.InstagramStoryInsightsValue;

public class InstagramWebhookTest extends AbstractJsonMapperTests {

  @Test
  public void checkComments() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("instagram/wh-comments"), WebhookObject.class);

    assertEquals("instagram", webhookObject.getObject());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    Change change = entry.getChanges().get(0);
    assertEquals("comments", change.getField());

    assertTrue(change.getValue() instanceof InstagramCommentsValue);
    InstagramCommentsValue value = (InstagramCommentsValue) change.getValue();
    assertEquals("123456789", value.getId());
    assertEquals("This is an example.", value.getText());
  }

  @Test
  public void checkMentions() {
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
  public void checkStoryInsights() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("instagram/wh-story_insights"), WebhookObject.class);
    assertEquals("instagram", webhookObject.getObject());
    WebhookEntry entry = webhookObject.getEntryList().get(0);
    Change change = entry.getChanges().get(0);
    assertEquals("story_insights", change.getField());

    assertTrue(change.getValue() instanceof InstagramStoryInsightsValue);
    InstagramStoryInsightsValue value = (InstagramStoryInsightsValue) change.getValue();

    assertEquals("17887498072083520", value.getMediaId());
    assertEquals(3l, value.getExits().longValue());
    assertEquals(444l, value.getImpressions().longValue());
    assertEquals(44l, value.getReach().longValue());
    assertEquals(0l, value.getReplies().longValue());
    assertEquals(3l, value.getTapsBack().longValue());
    assertEquals(4l, value.getTapsForward().longValue());
  }
}
