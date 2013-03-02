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

package com.restfb.batch;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;

/**
 * Encapsulates a discrete part of an entire <a href="https://developers.facebook.com/docs/reference/api/batch/"
 * target="_blank">Facebook Batch API</a> response.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.5
 */
public class BatchResponse {
  @Facebook
  private Integer code;

  @Facebook
  private String body;

  @Facebook
  private List<BatchHeader> headers = new ArrayList<BatchHeader>();

  /**
   * "Magic" no-argument constructor so we can reflectively make instances of this class with DefaultJsonMapper, but
   * normal client code cannot.
   */
  protected BatchResponse() {}

  /**
   * Creates a batch response with the given HTTP response status code, headers, and JSON body.
   * 
   * @param code
   *          HTTP status code.
   * @param headers
   *          HTTP headers.
   * @param body
   *          JSON body.
   */
  public BatchResponse(Integer code, List<BatchHeader> headers, String body) {
    this.code = code;
    this.body = body;

    if (headers != null)
      this.headers.addAll(headers);
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ReflectionUtils.hashCode(this);
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object that) {
    return ReflectionUtils.equals(this, that);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ReflectionUtils.toString(this);
  }

  /**
   * The HTTP status code for this response.
   * 
   * @return The HTTP status code for this response.
   */
  public Integer getCode() {
    return code;
  }

  /**
   * The HTTP response body JSON.
   * 
   * @return The HTTP response body JSON.
   */
  public String getBody() {
    return body;
  }

  /**
   * The HTTP response headers.
   * 
   * @return The HTTP response headers.
   */
  public List<BatchHeader> getHeaders() {
    return unmodifiableList(headers);
  }
}