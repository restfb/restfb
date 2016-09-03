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
package com.restfb.types;

import com.restfb.Facebook;

import lombok.Getter;

/**
 * Response object that should be used as Response type for the chunked upload (transfer phase) of a resumable upload.
 * To get more information for example about the request parameter you should look
 * <a href="https://developers.facebook.com/docs/graph-api/video-uploads#transfer">here</a>
 */
public class ResumableUploadTransferResponse extends AbstractFacebookType {

  @Getter
  @Facebook("start_offset")
  private Long startOffset;

  @Getter
  @Facebook("end_offset")
  private Long endOffset;

  /**
   * Is <code>true</code> if Facebook don't want any other bytes and the video is transfered.
   * 
   * @return if the transfer is the last one and the file upload is finished
   */
  public boolean isFinished() {
    if (startOffset != null && endOffset != null) {
      if (startOffset.longValue() == endOffset.longValue()) {
        return true;
      }
    }
    return false;
  }

}
