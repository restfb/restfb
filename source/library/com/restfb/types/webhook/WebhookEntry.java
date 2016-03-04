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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class WebhookEntry {
  @Facebook
  @Getter
  @Setter
  private String uid;

  @Facebook
  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  private Date date = new Date();

  @Facebook("time")
  private Long rawDate;

  @Facebook("changed_fields")
  @Getter
  @Setter
  private List<String> changedFields = new ArrayList<String>();

  @Facebook
  @Getter
  @Setter
  private List<Change> changes = new ArrayList<Change>();

  @JsonMappingCompleted
  private void convertDate() {
    if (rawDate != null) {
      date.setTime(rawDate * 1000);
    }
  }
}
