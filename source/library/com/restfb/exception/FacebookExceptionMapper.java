/*
 * Copyright (c) 2010-2013 Mark Allen.
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
 * Specifies a method for mapping Graph and Old REST API exceptions to corresponding instances of
 * {@code FacebookException}.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6
 */
public interface FacebookExceptionMapper {
  /**
   * Given a Facebook API exception type and message, generates an instance of the corresponding
   * {@code FacebookGraphException} or one of its subclasses.
   * 
   * @param errorCode
   *          Old REST API exception error code field, e.g. 190.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @param type
   *          Graph API exception type field, e.g. "OAuthException".
   * @param message
   *          Graph or Old REST API message field, e.g. "Invalid access token signature."
   * @return An appropriate {@code FacebookException} subclass.
   */
  FacebookException exceptionForTypeAndMessage(Integer errorCode, Integer httpStatusCode, String type, String message);
}