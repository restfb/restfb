/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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
package com.restfb.types.webhook;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.types.webhook.messaging.MessagingItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class WebhookEntry {

  @Getter
  @Setter
  @Facebook
  private String uid;

  @Getter
  @Setter
  @Facebook
  private String id;

  @Getter
  @Setter
  private Date time = new Date();

  @Facebook("time")
  private Long rawTime;

  @Getter
  @Setter
  @Facebook("changed_fields")
  private List<String> changedFields = new ArrayList<String>();

  @Getter
  @Setter
  @Facebook
  private List<Change> changes = new ArrayList<Change>();

  @Getter
  @Setter
  @Facebook
  private List<MessagingItem> messaging = new ArrayList<MessagingItem>();

  @JsonMappingCompleted
  private void convertDate() {
    if (rawTime != null) {
      time.setTime(rawTime * 1000);
    }
  }

}
