/*
 * Copyright (c) 2010-2022 Mark Allen, Norbert Bartels.
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.json.JsonObject;
import com.restfb.types.AbstractFacebookType;
import com.restfb.types.whatsapp.platform.message.MessageType;
import com.restfb.types.whatsapp.platform.message.Text;

import lombok.Getter;
import lombok.Setter;

/**
 * https://developers.facebook.com/docs/whatsapp/cloud-api/webhooks/components#messages-object
 */
public class Message extends AbstractFacebookType {

  private JsonObject audio;

  private JsonObject button;

  private JsonObject context;

  private JsonObject document;

  private List<JsonObject> errors = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private String from;

  @Getter
  @Setter
  @Facebook
  private String id;

  private JsonObject identity;

  private JsonObject image;

  private JsonObject interactive;

  private JsonObject referral;

  private JsonObject system;

  @Getter
  @Setter
  @Facebook
  private Text text;

  @Getter
  @Setter
  @Facebook
  private Date timestamp;

  @Getter
  @Setter
  @Facebook
  private MessageType type;

  private JsonObject video;

  public boolean isText() {
    return text != null;
  }
}
