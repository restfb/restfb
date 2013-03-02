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

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;

/**
 * Represents an HTTP header name/value pair used by {@link BatchRequest} and {@link BatchResponse}.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.5
 */
public class BatchHeader {
  @Facebook
  private String name;

  @Facebook
  private String value;

  /**
   * "Magic" no-argument constructor so we can reflectively make instances of this class with DefaultJsonMapper, but
   * normal client code cannot.
   */
  protected BatchHeader() {}

  /**
   * Creates a {@code BatchHeader} with the given name/value pair.
   * 
   * @param name
   *          The name of the header.
   * @param value
   *          The value of the header.
   */
  public BatchHeader(String name, String value) {
    this.name = name;
    this.value = value;
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
   * The name of the HTTP header.
   * 
   * @return The name of the HTTP header.
   */
  public String getName() {
    return name;
  }

  /**
   * The value of the HTTP header.
   * 
   * @return The value of the HTTP header.
   */
  public String getValue() {
    return value;
  }
}