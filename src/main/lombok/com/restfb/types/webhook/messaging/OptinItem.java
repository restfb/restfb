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
package com.restfb.types.webhook.messaging;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.types.send.UserRefMessageRecipient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class OptinItem implements InnerMessagingItem {

  @Getter
  @Setter
  @Facebook
  private String ref;

  @Getter
  @Setter
  @Facebook("user_ref")
  private String userRef;

  @Getter
  @Setter
  @Facebook
  private String type;

  @Getter
  @Setter
  @Facebook
  private String payload;

  @Getter
  @Setter
  @Facebook("one_time_notif_token")
  private String oneTimeNotifToken;

  @Getter
  private UserRefMessageRecipient userRefMessageRecipient;

  @JsonMapper.JsonMappingCompleted
  private void provideRecipient() {
    if (userRef != null) {
      userRefMessageRecipient = new UserRefMessageRecipient(userRef);
    }
  }

  public boolean isOneTimeNotif() {
    return oneTimeNotifToken != null;
  }
}
