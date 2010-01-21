/*
 * Copyright (c) 2010 Mark Allen.
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

package com.restfb;

/**
 * Indicates that the Facebook endpoint returned JSON which contains an error
 * code.
 * <p>
 * Example: <code>{"error_code": 2,
 * "error_msg": "The service is not available at this time.", ...}</code>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class FacebookResponseStatusException extends FacebookException {
  /**
   * The Facebook API error code.
   */
  private Integer errorCode;

  /**
   * Creates an exception with the given message and error code.
   * 
   * @param message
   *          Value of the Facebook response attribute {@code error_msg}.
   * @param errorCode
   *          Value of the Facebook response attribute {@code error_code}. Must
   *          not be {@code null}.
   * @throws NullPointerException
   *           If {@code errorCode} is {@code null}.
   */
  public FacebookResponseStatusException(String message, Integer errorCode) {
    super(StringUtils.trimToEmpty(message));
    if (errorCode == null)
      throw new NullPointerException(
        "The 'errorCode' parameter must not be null.");
    this.errorCode = errorCode;
  }

  /**
   * Gets the Facebook API error code.
   * 
   * @return The Facebook API error code.
   */
  public Integer getErrorCode() {
    return errorCode;
  }

  /**
   * @see java.lang.Throwable#toString()
   */
  @Override
  public String toString() {
    if (getErrorCode() == null)
      return getMessage();

    return "Error code " + getErrorCode() + ": " + getMessage();
  }
}
