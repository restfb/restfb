/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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
 * <p>
 * Examples: No network adapter available, API endpoint is down.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class FacebookNetworkException extends FacebookException {
  /**
   * The HTTP response status code.
   */
  private final Integer httpStatusCode;

  private static final long serialVersionUID = 1L;

  private static final String MESSAGE = "Facebook request failed";

  /**
   * Creates an exception with the given message and cause.
   * 
   * @param cause
   *          The exception that caused this exception to be thrown.
   */
  public FacebookNetworkException(Throwable cause) {
    this(cause, null);
  }

  /**
   * Creates an exception with the given message and HTTP status code.
   * 
   * @param httpStatusCode
   *          The HTTP response status code.
   */
  public FacebookNetworkException(Integer httpStatusCode) {
    this(null, httpStatusCode);
  }

  /**
   * Creates an exception with the given message, cause, and HTTP status code.
   * 
   * @param cause
   *          The exception that caused this exception to be thrown.
   * @param httpStatusCode
   *          The HTTP response status code.
   */
  public FacebookNetworkException(Throwable cause, Integer httpStatusCode) {
    super(format("A network error occurred while trying to communicate with Facebook: %s (HTTP status code %d)",
      MESSAGE, httpStatusCode), cause);
    this.httpStatusCode = httpStatusCode;
  }

  /**
   * Gets the HTTP response status code.
   * 
   * @return The HTTP response status code.
   */
  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }
}