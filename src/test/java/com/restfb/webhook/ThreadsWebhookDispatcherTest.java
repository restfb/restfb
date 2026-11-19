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
package com.restfb.webhook;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;
import com.restfb.types.webhook.ChangeValue;
import com.restfb.types.webhook.FallBackChangeValue;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.instagram.InstagramMentionsValue;
import com.restfb.types.webhook.threads.ThreadsMediaValue;
import com.restfb.types.webhook.threads.ThreadsWebhookObject;

class ThreadsWebhookDispatcherTest extends AbstractJsonMapperTests {

  @ParameterizedTest
  @ValueSource(strings = { "replies", "mentions", "delete", "publish" })
  void dispatchExactlyOneEventWithNotificationContext(String field) {
    ThreadsWebhookObject notification = notification(field);
    ThreadsMediaValue value = assertInstanceOf(ThreadsMediaValue.class, notification.getValues().getValue());
    ThreadsWebhookChangeListener threads = mock(ThreadsWebhookChangeListener.class);
    WebhookChangeListener legacy = mock(WebhookChangeListener.class);
    WebhookMessagingListener messaging = mock(WebhookMessagingListener.class);
    Webhook webhook = new Webhook();
    webhook.registerListener(threads);
    webhook.registerListener(legacy);
    webhook.registerListener(messaging);

    webhook.process(notification);

    switch (field) {
    case "replies":
      verify(threads).replies(same(notification), same(value));
      break;
    case "mentions":
      verify(threads).mentions(same(notification), same(value));
      break;
    case "delete":
      verify(threads).delete(same(notification), same(value));
      break;
    case "publish":
      verify(threads).publish(same(notification), same(value));
      break;
    default:
      fail("Unexpected test event");
    }
    verifyNoMoreInteractions(threads);
    verifyNoInteractions(legacy, messaging);
  }

  @Test
  void retainInstagramMappingAndDispatchAlongsideThreads() {
    WebhookObject instagram =
        createJsonMapper().toJavaObject(jsonFromClasspath("instagram/wh-mentions"), WebhookObject.class);
    InstagramMentionsValue value =
        assertInstanceOf(InstagramMentionsValue.class, instagram.getEntryList().get(0).getChanges().get(0).getValue());
    ThreadsWebhookChangeListener threads = mock(ThreadsWebhookChangeListener.class);
    WebhookChangeListener legacy = mock(WebhookChangeListener.class);
    Webhook webhook = new Webhook();
    webhook.registerListener(threads);
    webhook.registerListener(legacy);

    webhook.process(instagram);

    assertEquals("17887498072083520", value.getMediaId());
    assertEquals("17887498072083520", value.getCommentId());
    verify(legacy).instagramMentionsValue(same(value));
    verifyNoMoreInteractions(legacy);
    verifyNoInteractions(threads);
  }

  @Test
  void dispatchUnknownEventsToFallbackCallback() {
    JsonObject json = Json.parse(jsonFromClasspath("threads/webhooks/mentions")).asObject();
    json.get("values").asObject().set("field", "future-event");
    ThreadsWebhookObject notification = createJsonMapper().toJavaObject(json.toString(), ThreadsWebhookObject.class);
    FallBackChangeValue value = assertInstanceOf(FallBackChangeValue.class, notification.getValues().getValue());
    ThreadsWebhookChangeListener listener = mock(ThreadsWebhookChangeListener.class);
    Webhook webhook = new Webhook();
    webhook.registerListener(listener);

    webhook.process(notification);

    verify(listener).unknown(same(notification), same(value));
    verifyNoMoreInteractions(listener);
  }

  @Test
  void dispatchNonMediaValuesToFallbackCallback() {
    ThreadsWebhookObject notification = createJsonMapper()
      .toJavaObject("{\"values\":{\"field\":\"mentions\",\"value\":\"future-value\"}}", ThreadsWebhookObject.class);
    ChangeValue value = notification.getValues().getValue();
    ThreadsWebhookChangeListener listener = mock(ThreadsWebhookChangeListener.class);
    Webhook webhook = new Webhook();
    webhook.registerListener(listener);

    webhook.process(notification);

    verify(listener).unknown(same(notification), same(value));
    verifyNoMoreInteractions(listener);
  }

  @ParameterizedTest
  @ValueSource(strings = { "{}", "{\"values\":null}", "{\"values\":{}}",
      "{\"values\":{\"field\":\"replies\",\"value\":null}}" })
  void ignoreMissingChangesOrValues(String json) {
    ThreadsWebhookObject notification = createJsonMapper().toJavaObject(json, ThreadsWebhookObject.class);
    ThreadsWebhookChangeListener listener = mock(ThreadsWebhookChangeListener.class);
    Webhook webhook = new Webhook();
    webhook.registerListener(listener);

    webhook.process(notification);

    verifyNoInteractions(listener);
  }

  @Test
  void ignoreEventsWithoutAThreadsListener() {
    Webhook webhook = new Webhook();
    assertDoesNotThrow(() -> webhook.process(notification("publish")));
  }

  @Test
  void allowPartialListenersThroughDefaultCallbacks() {
    Webhook webhook = new Webhook();
    webhook.registerListener(new ThreadsWebhookChangeListener() {});
    for (String field : new String[] { "replies", "mentions", "delete", "publish" }) {
      assertDoesNotThrow(() -> webhook.process(notification(field)));
    }
  }

  @Test
  void replaceOrDisableTheThreadsListener() {
    ThreadsWebhookChangeListener first = mock(ThreadsWebhookChangeListener.class);
    ThreadsWebhookChangeListener replacement = mock(ThreadsWebhookChangeListener.class);
    Webhook webhook = new Webhook();
    webhook.registerListener(first);
    webhook.registerListener(replacement);
    ThreadsWebhookObject notification = notification("publish");
    ThreadsMediaValue value = (ThreadsMediaValue) notification.getValues().getValue();

    webhook.process(notification);
    webhook.registerListener((ThreadsWebhookChangeListener) null);
    webhook.process(notification);

    verify(replacement).publish(same(notification), same(value));
    verifyNoMoreInteractions(replacement);
    verifyNoInteractions(first);
  }

  private ThreadsWebhookObject notification(String field) {
    return createJsonMapper().toJavaObject(jsonFromClasspath("threads/webhooks/" + field), ThreadsWebhookObject.class);
  }
}
