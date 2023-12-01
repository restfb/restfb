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
package com.restfb.types;

import com.restfb.BinaryAttachment;

import lombok.Getter;

/**
 * Attachment object for uploading a Reel to Facebook.
 *
 * <p>
 * use {@code withByteContent} for sending a binary attachment and {@code withUrl} if the URL is given instead
 */
public class FacebookReelAttachment extends BinaryAttachment {

  @Getter
  private String reelUrl;

  protected FacebookReelAttachment(byte[] byteData) {
    data = byteData;
  }

  protected FacebookReelAttachment(String reelUrl) {
    this.reelUrl = reelUrl;
  }

  public static FacebookReelAttachment withByteContent(byte[] reelData) {
    return new FacebookReelAttachment(reelData);
  }

  public static FacebookReelAttachment withUrl(String reelUrl) {
    return new FacebookReelAttachment(reelUrl);
  }

  @Override
  public boolean isFacebookReel() {
    return true;
  }

  public boolean isBinary() {
    return data != null;
  }

  public int getFileSizeInBytes() {
    if (data != null) {
      return data.length;
    }

    return 0;
  }
}
