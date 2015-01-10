/*
 * Copyright (c) 2010-2015 Mark Allen.
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
 * Indicates that the Facebook Graph API endpoint returned JSON which indicates an error condition related to the OAuth
 * token included in the request.
 * <p>
 * Example:<code>
  {
      "error": {
        "type": "OAuthException",
        "message": "(#210) User not visible",
        "code": 210,
        "error_subcode": 123,
	"error_user_title": "A title",
        "error_user_msg": "A message"
      }
  } </code>
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6
 */
public class FacebookOAuthException extends FacebookGraphException {
  private static final long serialVersionUID = 2L;

  /**
   * Creates an exception with the given error type and message.
   * 
   * @param errorType
   *          Value of the Facebook response attribute {@code error.type}.
   * @param errorMessage
   *          Value of the Facebook response attribute {@code error.message}.
   * @param errorCode
   *          Value of the Facebook response attribute {@code error.code}.
   * @param errorSubcode
   *          Value of the Facebook response attribute {@code error.error_subcode}.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @param userTitle
   *          Value of the Facebook response attribute {@code error.error_user_title}.
   * @param userMessage
   *          Value of the Facebook response attribute {@code error.error_user_message}.
   */
  public FacebookOAuthException(String errorType, String errorMessage, Integer errorCode, Integer errorSubcode,
      Integer httpStatusCode, String userTitle, String userMessage) {
    super(errorType, errorMessage, errorCode, errorSubcode, httpStatusCode, userTitle, userMessage);
  }
}