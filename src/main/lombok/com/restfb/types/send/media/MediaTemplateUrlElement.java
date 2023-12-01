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
 * Represents the media template element that is used with the url as defined
 * <a href="https://developers.facebook.com/docs/messenger-platform/send-messages/template/media#url">here</a>
 *
 * Allowed urls are:
 * <ul>
 * <li>https://business.facebook.com/<PAGE_NAME>/videos/<NUMERIC_ID></li>
 * <li>https://www.facebook.com/<USERNAME>/videos/<NUMERIC_ID>/</li>
 * <li>https://business.facebook.com/<PAGE_NAME>/photos/<NUMERIC_ID></li>
 * <li>https://www.facebook.com/photo.php?fbid=<NUMERIC_ID></li>
 * </ul>
 */
public class MediaTemplateUrlElement extends AbstractFacebookType implements MediaAttachment.MediaTemplateElement {

  @Getter
  @Facebook("media_type")
  private String mediaType;

  @Getter
  @Facebook
  private String url;

  @Getter
  @Facebook
  private List<AbstractButton> buttons;

  public MediaTemplateUrlElement(String url) {
    if (!validUrl(url)) {
      throw new IllegalArgumentException("The given URL is not valid");
    }
    this.url = url;
    if (url.contains("/videos/")) {
      this.mediaType = MediaAttachment.MediaType.VIDEO.name().toLowerCase();
    } else {
      this.mediaType = MediaAttachment.MediaType.IMAGE.name().toLowerCase();
    }
  }

  @Override
  public void addButton(AbstractButton button) {
    if (buttons == null) {
      buttons = new ArrayList<>();
    }
    buttons.add(button);
  }

  private boolean validUrl(String url) {
    if (url == null) {
      throw new IllegalArgumentException("The media URL may not be null");
    }

    if (url.startsWith("https://business.facebook.com/")) {
      return true;
    }

    return url.startsWith("https://www.facebook.com/");
  }
}
