/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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
import com.restfb.Version;

import org.junit.Test;

public class SubscriptionTest extends AbstractJsonMapperTests {

  @Test
  public void check2_8() {
    Subscription subscription =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_8/subscription"), Subscription.class);
    assertNotNull(subscription);
    assertTrue(subscription.getActive().booleanValue());
    assertEquals("https://www.example.org/endpoint/callback", subscription.getCallbackUrl());
    assertEquals("page", subscription.getObject());
    assertEquals(4, subscription.getFields().size());
    for (Subscription.SubscriptionField subscriptionField : subscription.getFields()) {
      assertEquals(Version.UNVERSIONED, subscriptionField.getVersion());
    }
  }

  @Test
  public void check2_9() {
    Subscription subscription =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_9/subscription"), Subscription.class);
    assertNotNull(subscription);
    assertTrue(subscription.getActive().booleanValue());
    assertEquals("https://www.example.org/endpoint/callback", subscription.getCallbackUrl());
    assertEquals("page", subscription.getObject());
    assertEquals(4, subscription.getFields().size());
    for (Subscription.SubscriptionField subscriptionField : subscription.getFields()) {
      assertEquals(Version.VERSION_2_6, subscriptionField.getVersion());
    }
  }
}
