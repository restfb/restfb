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
package com.restfb.types.send;

import com.restfb.Facebook;

public class MediaAttachment extends MessageAttachment {

  @Facebook
  private MediaAttachmentPayload payload;

  public MediaAttachment(Type type, String imageUrl) {
    setType(type.toString().toLowerCase());
    if (imageUrl.matches("^\\d+$")) {
      payload = new ReuseIdPayload(imageUrl);
    } else {
      payload = new UrlPayload(imageUrl);
    }
  }

  public void setIsReusable(boolean isReusable) {
    payload.setIsReusable(isReusable);
  }

  private static class UrlPayload implements MediaAttachmentPayload {

    @Facebook
    private String url;

    @Facebook("is_reusable")
    private Boolean isReusable;

    public UrlPayload(String urlString) {
      url = urlString;
    }

    @Override
    public void setIsReusable(boolean isReusable) {
      this.isReusable = isReusable;
    }

  }

  private static class ReuseIdPayload implements MediaAttachmentPayload {

    @Facebook("attachment_id")
    private String attachmentId;

    public ReuseIdPayload(String urlString) {
      attachmentId = urlString;
    }

    @Override
    public void setIsReusable(boolean isReusable) {
      // ignore this here
    }
  }

  private interface MediaAttachmentPayload {
     void setIsReusable(boolean isReusable);
  }

  public enum Type {
    IMAGE, VIDEO, AUDIO, FILE
  }
}
