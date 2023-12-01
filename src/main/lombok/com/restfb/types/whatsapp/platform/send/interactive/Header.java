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
package com.restfb.types.whatsapp.platform.send.interactive;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;
import com.restfb.types.whatsapp.platform.send.Document;
import com.restfb.types.whatsapp.platform.send.Image;
import com.restfb.types.whatsapp.platform.send.Text;
import com.restfb.types.whatsapp.platform.send.Video;

public class Header extends AbstractFacebookType {
  @Facebook
  private Document document;

  @Facebook
  private Video video;

  @Facebook
  private Image image;

  @Facebook
  private String text;

  @Facebook
  private Type type = Type.text;

  @Deprecated
  public Header(Text text) {
    this.text = text.getBody();
    this.type = Type.text;
  }

  public Header(String text) {
    this.text = text;
    this.type = Type.text;
  }

  public Header(Video video) {
    this.video = video;
    this.type = Type.video;
  }

  public Header(Image image) {
    this.image = image;
    this.type = Type.image;
  }

  public Header(Document document) {
    this.document = document;
    this.type = Type.document;
  }

  public enum Type {
    document, video, image, text;
  }
}
