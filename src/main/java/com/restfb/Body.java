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
package com.restfb;

import java.util.Optional;

/**
 * The POST body class. It uses the RestFB JsonMapper to convert an object to json String.
 * <p>
 * You can use your custom mapper to modify the mapping.
 */
public class Body {

  private final String data;

  private Body(Object data) {
    this(data, new DefaultJsonMapper());
  }

  private Body(Object data, JsonMapper jsonMapper) {
    if (data == null) {
      throw new IllegalArgumentException(Body.class + " instances" + " must have a non-null data.");
    }
    this.data = Optional.ofNullable(jsonMapper)
      .orElseThrow(() -> new IllegalArgumentException("Provided " + JsonMapper.class + " must not be null."))
      .toJson(data, true);
  }

  /**
   * returns the Body data as String
   * 
   * @return the body data
   */
  public String getData() {
    return data;
  }

  /**
   * build a new body object instance with the given data as immutable inner data
   * 
   * @param data
   *          the data is internally converted into a String using the JsonMapper
   * @return the Body instance
   */
  public static Body withData(Object data) {
    return new Body(data);
  }

  /**
   * build a new body object instance with the given data as immutable inner data
   *
   * @param data
   *          the data is internally converted into a String using the provided JsonMapper
   * @param jsonMapper
   *          the custom JsonMapper used for the Object to String conversion
   * 
   * @return the Body instance
   */
  public static Body withData(Object data, JsonMapper jsonMapper) {
    return new Body(data, jsonMapper);
  }
}
