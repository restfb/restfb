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

import static java.lang.String.format;

/**
 * Indicates that a network error occurred while trying to connect to the Facebook API endpoint.
 */
public class FacebookNetworkException extends FacebookException {

  private final Integer httpStatusCode;
  private static final long serialVersionUID = 1L;
  private static final String MESSAGE = "Facebook request failed";

  public FacebookNetworkException(Throwable cause) {
    this(MESSAGE, cause, null);
  }

  public FacebookNetworkException(Integer httpStatusCode) {
    this(MESSAGE, null, httpStatusCode);
  }

  public FacebookNetworkException(Throwable cause, Integer httpStatusCode) {
    this(MESSAGE, cause, httpStatusCode);
  }

  protected FacebookNetworkException(String message, Throwable cause, Integer httpStatusCode) {
    super(format("A network error occurred while trying to communicate with Facebook: %s%s", message,
      httpStatusCode != null ? format(" (HTTP status code %d)", httpStatusCode) : ""), cause);
    this.httpStatusCode = httpStatusCode;
  }

  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }
}
