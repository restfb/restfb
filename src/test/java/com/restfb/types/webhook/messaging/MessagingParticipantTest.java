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
package com.restfb.types.webhook.messaging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.UserRefMessageRecipient;

class MessagingParticipantTest {

  @Test
  void toMessageRecipientUsesId() {
    MessagingParticipant participant = new MessagingParticipant();
    participant.setId("12345");

    IdMessageRecipient recipient = assertInstanceOf(IdMessageRecipient.class, participant.toMessageRecipient());
    assertEquals("12345", recipient.getId());
  }

  @Test
  void toMessageRecipientUsesUserRef() {
    MessagingParticipant participant = new MessagingParticipant();
    participant.setUserRef("UNIQUE_USER_REF");

    UserRefMessageRecipient recipient =
        assertInstanceOf(UserRefMessageRecipient.class, participant.toMessageRecipient());
    assertEquals("UNIQUE_USER_REF", recipient.getUserRef());
  }

  @Test
  void toMessageRecipientPrefersUserRef() {
    MessagingParticipant participant = new MessagingParticipant();
    participant.setId("12345");
    participant.setUserRef("UNIQUE_USER_REF");

    UserRefMessageRecipient recipient =
        assertInstanceOf(UserRefMessageRecipient.class, participant.toMessageRecipient());
    assertEquals("UNIQUE_USER_REF", recipient.getUserRef());
  }
}
