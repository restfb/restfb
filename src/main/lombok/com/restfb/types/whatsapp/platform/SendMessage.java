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
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;
import com.restfb.types.whatsapp.platform.send.*;

import lombok.Getter;
import lombok.Setter;

public class SendMessage extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook
  private String to;

  @Getter
  @Setter
  @Facebook
  private Context context;

  @Getter
  @Facebook
  private Text text;

  @Getter
  @Facebook
  private Reaction reaction;

  @Facebook
  private Video video;

  @Facebook
  private Audio audio;

  @Facebook
  private Image image;

  @Facebook
  private Document document;

  @Facebook
  private Sticker sticker;

  @Facebook
  private Interactive interactive;

  @Facebook
  private Location location;

  @Facebook
  private List<Contact> contacts;

  @Facebook
  @Setter
  private Type type = Type.text;

  @Facebook("messaging_product")
  private final String messagingProduct = "whatsapp";

  public SendMessage(String to) {
    setTo(to);
  }

  public void setReaction(Reaction reaction) {
    this.reaction = reaction;
    this.type = Type.reaction;
  }

  public void setText(Text text) {
    this.text = text;
    this.type = Type.text;
  }

  public void setMedia(Media media) {
    if (media instanceof Audio) {
      this.audio = media.as(Audio.class);
      this.type = Type.audio;
    }

    if (media instanceof Video) {
      this.video = media.as(Video.class);
      this.type = Type.video;
    }

    if (media instanceof Image) {
      this.image = media.as(Image.class);
      this.type = Type.image;
    }

    if (media instanceof Document) {
      this.document = media.as(Document.class);
      this.type = Type.document;
    }

    if (media instanceof Sticker) {
      this.sticker = media.as(Sticker.class);
      this.type = Type.sticker;
    }
  }

  public void setInteractive(Interactive interactive) {
    this.interactive = interactive;
    this.type = Type.interactive;
  }

  public void setLocation(Location location) {
    this.location = location;
    this.type = Type.location;
  }

  public void addContact(Contact contact) {
    if (contacts == null) {
      contacts = new ArrayList<>();
    }

    contacts.add(contact);
    this.type = Type.contacts;
  }

  public enum Type {
    text, reaction, audio, video, document, sticker, image, interactive, location, contacts;
  }
}
