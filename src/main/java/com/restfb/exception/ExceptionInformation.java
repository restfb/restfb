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
package com.restfb.exception;

import com.restfb.json.JsonObject;

/**
 * immutable container to transfer all data used to create the correct facebook exception
 */
public class ExceptionInformation {

  /**
   * Old REST API exception error code field, e.g. 190.
   */
  private final Integer errorCode;

  /**
   * Old REST API exception error subcode field, e.g. 459.
   */
  private final Integer errorSubcode;

  /**
   * The HTTP status code returned by the server, e.g. 500.
   */
  private final Integer httpStatusCode;

  /**
   * Graph API exception type field, e.g. "OAuthException".
   */
  private final String type;

  /**
   * Graph or Old REST API message field, e.g. "Invalid access token signature."
   */
  private final String message;

  /**
   * Graph API error_user_title field.
   */
  private final String userTitle;

  /**
   * Graph API error_user_message field.
   */
  private final String userMessage;

  private final Boolean isTransient;

  /**
   * raw error message as JSON
   */
  private final JsonObject rawError;

  /**
   * basic constructor to build a set of information used by the exception generator
   * 
   * @param errorCode
   *          Old REST API exception error code field, e.g. 190.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @param message
   *          Graph or Old REST API message field, e.g. "Invalid access token signature."
   * @param rawError
   *          raw error message as JSON
   */
  public ExceptionInformation(Integer errorCode, Integer httpStatusCode, String message, JsonObject rawError) {
    this(errorCode, null, httpStatusCode, null, message, null, null, null, rawError);
  }

  /**
   * extended constructor to build a set of information used by the exception generator
   *
   * @param errorCode
   *          Old REST API exception error code field, e.g. 190.
   * @param errorSubcode
   *          Old REST API exception error subcode field, e.g. 459.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @param type
   *          Graph API exception type field, e.g. "OAuthException".
   * @param message
   *          Graph or Old REST API message field, e.g. "Invalid access token signature."
   * @param userTitle
   *          Graph API error_user_title field.
   * @param userMessage
   *          Graph API error_user_message field.
   * @param rawError
   *          raw error message as JSON
   */
  public ExceptionInformation(Integer errorCode, Integer errorSubcode, Integer httpStatusCode, String type,
      String message, String userTitle, String userMessage, Boolean isTransient, JsonObject rawError) {
    this.errorCode = errorCode;
    this.errorSubcode = errorSubcode;
    this.httpStatusCode = httpStatusCode;
    this.type = type;
    this.message = message;
    this.userTitle = userTitle;
    this.userMessage = userMessage;
    this.isTransient = isTransient;
    this.rawError = rawError;
  }

  public Integer getErrorCode() {
    return errorCode;
  }

  public Integer getErrorSubcode() {
    return errorSubcode;
  }

  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }

  public String getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

  public String getUserTitle() {
    return userTitle;
  }

  public String getUserMessage() {
    return userMessage;
  }

  public Boolean getIsTransient() {
    return isTransient;
  }

  public JsonObject getRawError() {
    return rawError;
  }
}
