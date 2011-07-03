/*
 * Copyright (c) 2010-2011 Mark Allen.
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

import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

/**
 * TODO: document
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
  List<BatchHeader> headers = new ArrayList<BatchHeader>();

  public Integer getCode() {
    return code;
  }

  public String getBody() {
    return body;
  }

  public List<BatchHeader> getHeaders() {
    return unmodifiableList(headers);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return format("%s[\n  Code=%d\n  Headers=%s\n  Body=%s\n]", BatchResponse.class.getSimpleName(), getCode(),
      getHeaders(), getBody());
  }
}