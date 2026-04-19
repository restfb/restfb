/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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
package com.restfb.exception;

/**
 * Indicates that the remote server sent a GOAWAY frame and is closing the HTTP/2 connection. This means the server will
 * not accept any new streams on this connection. Existing streams with an ID lower than or equal to the last processed
 * stream ID may have been processed, while streams with a higher ID have been rejected and will not be executed. The
 * GOAWAY frame may include an HTTP/2 error code describing the reason for the shutdown.
 */
public class FacebookGoawayNetworkException extends FacebookNetworkException {

  private static final long serialVersionUID = 1L;

  public FacebookGoawayNetworkException(String message, Throwable cause) {
    super(message, cause, null);
  }
}
