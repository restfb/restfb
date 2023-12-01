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
package com.restfb.types.send;

import java.io.Serializable;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;

public class MediaAttachment extends MessageAttachment {

  @Getter
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

  public MediaAttachment(Type type) {
    setType(type.toString().toLowerCase());
    payload = new UploadPayload();
  }

  public MediaAttachment(List<MediaTemplateElement> elements) {
    setType(Type.TEMPLATE.toString().toLowerCase());
    payload = new MediaTemplatePayload(elements);
  }

  public void setIsReusable(boolean isReusable) {
    payload.setIsReusable(isReusable);
  }

  private static class UrlPayload extends AbstractFacebookType implements MediaAttachmentPayload {

    @Getter
    @Facebook
    private String url;

    @Getter
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

  private static class UploadPayload extends AbstractFacebookType implements MediaAttachmentPayload {

    @Getter
    @Facebook("is_reusable")
    private Boolean isReusable;

    public UploadPayload() {
      // empty constructor
    }

    @Override
    public void setIsReusable(boolean isReusable) {
      this.isReusable = isReusable;
    }

  }

  private static class ReuseIdPayload extends AbstractFacebookType implements MediaAttachmentPayload {

    @Getter
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

  private static class MediaTemplatePayload extends AbstractFacebookType implements MediaAttachmentPayload {

    @Getter
    @Facebook("template_type")
    private String templateType = "media";

    @Facebook
    private List<MediaTemplateElement> elements;

    public MediaTemplatePayload(List<MediaTemplateElement> elements) {
      this.elements = elements;
    }

    @Override
    public void setIsReusable(boolean isReusable) {
      // ignore this here
    }
  }

  private interface MediaAttachmentPayload extends Serializable {
    void setIsReusable(boolean isReusable);
  }

  public interface MediaTemplateElement extends Serializable {

    void addButton(AbstractButton button);

  }

  public enum Type {
    IMAGE, VIDEO, AUDIO, FILE, TEMPLATE
  }

  public enum MediaType {
    IMAGE, VIDEO;
  }
}
