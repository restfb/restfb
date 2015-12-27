/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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
 * Abstract class to provide access to the JSON Facebook provides in case of an error
 *
 * Sometime a developer needs to access the plain error to get a more in depth view to the error.
 */
abstract public class FacebookErrorMessageException extends FacebookException {

  private JsonObject rawErrorJson;

  public FacebookErrorMessageException(String message) {
    super(message);
  }

  public FacebookErrorMessageException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * return the raw error as JSON, may be <code>null</code>
   * 
   * @return raw error
   */
  public JsonObject getRawErrorJson() {
    return rawErrorJson;
  }

  protected void setRawErrorJson(JsonObject rawError) {
    rawErrorJson = rawError;
  }
}
