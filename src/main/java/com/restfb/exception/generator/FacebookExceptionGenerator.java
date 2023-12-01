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
package com.restfb.exception.generator;

import com.restfb.exception.FacebookGraphException;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.exception.FacebookResponseStatusException;

/**
 * Generator to convert Facebook error JSON into RestFB Exceptions.
 *
 * <p>
 * Provides methods to convert graph api errors and batch errors.
 */
public interface FacebookExceptionGenerator {

  /**
   * API error response 'error' attribute name.
   */
  String ERROR_ATTRIBUTE_NAME = "error";

  /**
   * API error response 'type' attribute name.
   */
  String ERROR_TYPE_ATTRIBUTE_NAME = "type";

  /**
   * API error response 'error_user_title' attribute name.
   */
  String ERROR_USER_TITLE_ATTRIBUTE_NAME = "error_user_title";

  /**
   * API error response 'error_user_msg' attribute name.
   */
  String ERROR_USER_MSG_ATTRIBUTE_NAME = "error_user_msg";

  /**
   * API error response 'message' attribute name.
   */
  String ERROR_MESSAGE_ATTRIBUTE_NAME = "message";

  /**
   * API error response 'code' attribute name.
   */
  String ERROR_CODE_ATTRIBUTE_NAME = "code";

  String ERROR_IS_TRANSIENT_NAME = "is_transient";

  /**
   * API error response 'error_subcode' attribute name.
   */
  String ERROR_SUBCODE_ATTRIBUTE_NAME = "error_subcode";

  /**
   * Batch API error response 'error' attribute name.
   */
  String BATCH_ERROR_ATTRIBUTE_NAME = "error";

  /**
   * Batch API error response 'error_description' attribute name.
   */
  String BATCH_ERROR_DESCRIPTION_ATTRIBUTE_NAME = "error_description";

  /**
   * Throws an exception if Facebook returned an error response. Using the Graph API, it's possible to see both the new
   * Graph API-style errors as well as Legacy API-style errors, so we have to handle both here. This method extracts
   * relevant information from the error JSON and throws an exception which encapsulates it for end-user consumption.
   * <p>
   * For Graph API errors:
   * <p>
   * If the {@code error} JSON field is present, we've got a response status error for this API call.
   * <p>
   * For Legacy errors (e.g. FQL):
   * <p>
   * If the {@code error_code} JSON field is present, we've got a response status error for this API call.
   *
   * @param json
   *          The JSON returned by Facebook in response to an API call.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @throws FacebookGraphException
   *           If the JSON contains a Graph API error response.
   * @throws FacebookResponseStatusException
   *           If the JSON contains an Legacy API error response.
   * @throws FacebookJsonMappingException
   *           If an error occurs while processing the JSON.
   */
  void throwFacebookResponseStatusExceptionIfNecessary(String json, Integer httpStatusCode);

  /**
   * If the {@code error} and {@code error_description} JSON fields are present, we've got a response status error for
   * this batch API call. Extracts relevant information from the JSON and throws an exception which encapsulates it for
   * end-user consumption.
   *
   * @param json
   *          The JSON returned by Facebook in response to a batch API call.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @throws FacebookResponseStatusException
   *           If the JSON contains an error code.
   * @throws FacebookJsonMappingException
   *           If an error occurs while processing the JSON.
   * @since 1.6.5
   */
  void throwBatchFacebookResponseStatusExceptionIfNecessary(String json, Integer httpStatusCode);
}
