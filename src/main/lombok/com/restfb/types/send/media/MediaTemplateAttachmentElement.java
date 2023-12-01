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
package com.restfb.types.send.media;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;
import com.restfb.types.send.AbstractButton;
import com.restfb.types.send.MediaAttachment;

import lombok.Getter;

/**
 * Represents the media template element that is used with the attachment id as defined
 * <a href="https://developers.facebook.com/docs/messenger-platform/send-messages/template/media#attachment">here</a>
 */
public class MediaTemplateAttachmentElement extends AbstractFacebookType
    implements MediaAttachment.MediaTemplateElement {

  @Getter
  @Facebook("media_type")
  private String mediaType;

  @Getter
  @Facebook("attachment_id")
  private String attachmentId;

  @Getter
  @Facebook
  private List<AbstractButton> buttons;

  public MediaTemplateAttachmentElement(MediaAttachment.MediaType mediaType, String attachmentId) {
    this.mediaType = mediaType.name().toLowerCase();
    this.attachmentId = attachmentId;
  }

  @Override
  public void addButton(AbstractButton button) {
    if (buttons == null) {
      buttons = new ArrayList<>();
    }
    buttons.add(button);
  }
}
