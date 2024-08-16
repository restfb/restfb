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
package com.restfb.types.threads;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/threads/troubleshooting">Ihreads Media Container</a>
 */
@Setter
@Getter
public class TdMediaContainer extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  @Facebook
  private String id;

  @Facebook
  private Status status;

  @Facebook("error_message")
  private ErrorMessage errorMessage;

  public enum Status {
    EXPIRED, // The container was not published within 24 hours and has expired.
    ERROR, // The container failed to complete the publishing process.
    FINISHED, // The container and its media object are ready to be published.
    IN_PROGRESS, // The container is still in the publishing process.
    PUBLISHED // The container's media object has been published.
  }

  public enum ErrorMessage {
    FAILED_DOWNLOADING_VIDEO, //
    FAILED_PROCESSING_AUDIO, //
    FAILED_PROCESSING_VIDEO, //
    INVALID_ASPEC_RATIO, //
    INVALID_BIT_RATE, //
    INVALID_DURATION, //
    INVALID_FRAME_RATE, //
    INVALID_AUDIO_CHANNELS, //
    INVALID_AUDIO_CHANNEL_LAYOUT, //
    UNKNOWN
  }
}
