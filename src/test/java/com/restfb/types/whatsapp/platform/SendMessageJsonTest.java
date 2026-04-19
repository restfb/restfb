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
package com.restfb.types.whatsapp.platform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class SendMessageJsonTest extends AbstractJsonMapperTests {

  @Test
  void checkSuccessfulResponde() {
    SuccessfulResponse response =
        createJsonMapper().toJavaObject(jsonFromClasspath("whatsapp/message-response"), SuccessfulResponse.class);
    assertNotNull(response);
    assertEquals("whatsapp", response.getMessagingProduct());
    assertEquals(1, response.getContacts().size());
    assertEquals(1, response.getMessages().size());
    assertEquals("wamid.foobar", response.getMessages().get(0).getId());
    assertEquals("123456789", response.getContacts().get(0).getInput());
    assertEquals("123456789", response.getContacts().get(0).getWaId());
  }

  @Test
  void checkSuccessfulResponseWithBsuid() {
    SuccessfulResponse response =
        createJsonMapper().toJavaObject(jsonFromClasspath("whatsapp/message-response-bsuid"), SuccessfulResponse.class);
    assertNotNull(response);
    assertEquals("whatsapp", response.getMessagingProduct());
    assertEquals(1, response.getContacts().size());
    assertEquals(1, response.getMessages().size());
    assertEquals("wamid.foobar", response.getMessages().get(0).getId());
    assertEquals("US.13491208655302741918", response.getContacts().get(0).getInput());
    assertNull(response.getContacts().get(0).getWaId());
    assertEquals("US.13491208655302741918", response.getContacts().get(0).getUserId());
  }

  @Test
  void checkSuccessfulResponseWithPhonePrecedence() {
    SuccessfulResponse response = createJsonMapper().toJavaObject(
        jsonFromClasspath("whatsapp/message-response-phone-precedence"), SuccessfulResponse.class);
    assertNotNull(response);
    assertEquals("whatsapp", response.getMessagingProduct());
    assertEquals(1, response.getContacts().size());
    assertEquals("+16505551234", response.getContacts().get(0).getInput());
    assertEquals("16505551234", response.getContacts().get(0).getWaId());
    assertNull(response.getContacts().get(0).getUserId());
  }

  @Test
  void checkSuccessfulResponseWithGroupId() {
    SuccessfulResponse response =
        createJsonMapper().toJavaObject(jsonFromClasspath("whatsapp/message-response-group"), SuccessfulResponse.class);
    assertNotNull(response);
    assertEquals("whatsapp", response.getMessagingProduct());
    assertEquals(1, response.getContacts().size());
    assertEquals("120363418770915618@g.us", response.getContacts().get(0).getInput());
    assertNull(response.getContacts().get(0).getWaId());
    assertNull(response.getContacts().get(0).getUserId());
  }
}
