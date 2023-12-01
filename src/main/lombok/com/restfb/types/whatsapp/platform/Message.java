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
package com.restfb.types.whatsapp.platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;
import com.restfb.types.whatsapp.platform.message.*;
import com.restfb.types.whatsapp.platform.message.Error;
import com.restfb.types.whatsapp.platform.message.System;

import lombok.Getter;
import lombok.Setter;

/**
 * https://developers.facebook.com/docs/whatsapp/cloud-api/webhooks/components#messages-object
 */
public class Message extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook
  private Audio audio;

  @Getter
  @Setter
  @Facebook
  private Button button;

  @Getter
  @Setter
  @Facebook
  private Context context;

  @Getter
  @Setter
  @Facebook
  private Document document;

  @Getter
  @Setter
  @Facebook
  private List<Error> errors = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private String from;

  @Getter
  @Setter
  @Facebook
  private String id;

  @Getter
  @Setter
  @Facebook
  private Identity identity;

  @Getter
  @Setter
  @Facebook
  private Image image;

  @Getter
  @Setter
  @Facebook
  private Interactive interactive;

  @Getter
  @Setter
  @Facebook
  private Location location;

  @Getter
  @Setter
  @Facebook
  private Referral referral;

  @Getter
  @Setter
  @Facebook
  private System system;

  @Getter
  @Setter
  @Facebook
  private Sticker sticker;

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

  @Getter
  @Setter
  @Facebook
  private Video video;

  @Getter
  @Setter
  @Facebook
  private Reaction reaction;

  @Getter
  @Setter
  @Facebook
  private List<MessageContact> contacts = new ArrayList<>();

  /**
   * An arbitrary 256B string, useful for tracking (optional).
   *
   * Cloud API only
   */
  @Getter
  @Setter
  @Facebook("biz_opaque_callback_data")
  private String bizOpaqueCallbackData;

  public boolean isText() {
    return text != null;
  }

  public boolean isImage() {
    return image != null;
  }

  public boolean isVideo() {
    return video != null;
  }

  public boolean isSticker() {
    return sticker != null;
  }

  public boolean isLocation() {
    return location != null;
  }

  public boolean isAudio() {
    return audio != null;
  }

  public boolean isVoice() {
    return isAudio() && getAudio().isVoice();
  }

  public boolean isButton() {
    return button != null;
  }

  public boolean isDocument() {
    return document != null;
  }

  public boolean hasReferral() {
    return referral != null;
  }

  public boolean hasContext() {
    return context != null;
  }

  public boolean isSystem() {
    return system != null;
  }

  public boolean isInteractive() {
    return interactive != null;
  }

  public boolean isReaction() {
    return reaction != null;
  }

  public boolean hasContacts() {
    return !contacts.isEmpty();
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  public boolean hasIdentity() {
    return identity != null;
  }
}
