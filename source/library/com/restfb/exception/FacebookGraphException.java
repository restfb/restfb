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

import static java.lang.String.format;

/**
 * Indicates that the Facebook Graph API endpoint returned JSON which indicates an error condition.
 * <p>
 * Example:<code>
  {
      "error": {
        "type": "Exception",
        "message": "...",
        "code": 210,
        "error_subcode": 123,
	"error_user_title": "A title",
        "error_user_msg": "A message"
      }
  } </code>
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class FacebookGraphException extends FacebookException {
  private static final long serialVersionUID = 1L;

  /**
   * The Facebook Graph API error type.
   */
  private String errorType;

  /**
   * The Facebook API error message.
   */
  private String errorMessage;

  /**
   * The Facebook API error user title.
   */
  private String errorUserTitle;

  /**
   * The Facebook API error user message.
   */
  private String errorUserMessage;

  /**
   * The Facebook API error code.
   */
  private Integer errorCode;

  /**
   * The Facebook API error subcode.
   */
  private Integer errorSubcode;

  /**
   * The HTTP status code returned by the server.
   */
  private Integer httpStatusCode;

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
   * @param errorUserTitle
   *          Value of the Facebook response attribute {@code error.error_user_title}.
   * @param errorUserMessage
   *          Value of the Facebook response attribute {@code error.error_user_msg}.
   */
  public FacebookGraphException(String errorType, String errorMessage, Integer errorCode, Integer errorSubcode,
      Integer httpStatusCode, String errorUserTitle, String errorUserMessage) {
    super(format("Received Facebook error response of type %s: %s (code %s, subcode %s)", errorType, errorMessage,
      errorCode, errorSubcode));
    this.errorType = errorType;
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
    this.errorSubcode = errorSubcode;
    this.httpStatusCode = httpStatusCode;
    this.errorUserTitle = errorUserTitle;
    this.errorUserMessage = errorUserMessage;
  }

  /**
   * Gets the Facebook Graph API error type.
   * 
   * @return The Facebook Graph API error type.
   */
  public String getErrorType() {
    return errorType;
  }

  /**
   * Gets the Facebook Graph API error message.
   * 
   * @return The Facebook Graph API error message.
   */
  public String getErrorMessage() {
    return errorMessage;
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
   * Gets the Facebook API error subcode.
   * 
   * @return The Facebook API error subcode.
   */
  public Integer getErrorSubcode() {
    return errorSubcode;
  }

  /**
   * Gets the HTTP status code returned by the server.
   * 
   * @return The HTTP status code returned by the server.
   * @since 1.6.10
   */
  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }

  /**
   * Gets the Facebook API error user title.
   * 
   * @return the Facebook API error user title
   * @since 1.7.1
   */
  public String getErrorUserTitle() {
    return errorUserTitle;
  }

  /**
   * Gets the Facebook API error user message.
   * 
   * @return the Facebook API error user message
   * @since 1.7.1
   */
  public String getErrorUserMessage() {
    return errorUserMessage;
  }

}