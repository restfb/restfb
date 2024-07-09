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

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.webhook.Change;
import com.restfb.types.webhook.ChangeValue;
import com.restfb.types.webhook.PermissionChangeValue;
import com.restfb.types.webhook.WebhookObject;

class WebhookPermissionTest extends AbstractJsonMapperTests {

  @Test
  void manageFundraisers() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/permission-granted.wh-2"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertEquals(1, webhookObject.getEntryList().size());
    assertEquals(1, webhookObject.getEntryList().get(0).getChanges().size());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof PermissionChangeValue);
    PermissionChangeValue changeValue = (PermissionChangeValue) change.getValue();
    assertEquals(ChangeValue.Verb.GRANTED, changeValue.getVerb());
    assertEquals(2, changeValue.getTargetIds().size());
  }

  @Test
  void connected() {
    WebhookObject webhookObject =
        createJsonMapper().toJavaObject(jsonFromClasspath("webhooks/permission-granted.wh-1"), WebhookObject.class);
    assertNotNull(webhookObject);
    assertNotNull(webhookObject);
    assertEquals(1, webhookObject.getEntryList().size());
    assertEquals(1, webhookObject.getEntryList().get(0).getChanges().size());
    Change change = webhookObject.getEntryList().get(0).getChanges().get(0);
    assertTrue(change.getValue() instanceof PermissionChangeValue);
    PermissionChangeValue changeValue = (PermissionChangeValue) change.getValue();
    assertEquals(ChangeValue.Verb.GRANTED, changeValue.getVerb());
    assertEquals(0, changeValue.getTargetIds().size());
  }
}
