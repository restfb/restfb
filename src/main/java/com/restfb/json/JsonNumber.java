/*******************************************************************************
 * Copyright (c) 2013, 2015 EclipseSource.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.restfb.json;

import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("serial") // use default serial UID
class JsonNumber extends JsonValue {

  private final String numberStr;

  JsonNumber(String numberStr) {
    Objects.requireNonNull(numberStr, STRING_IS_NULL);
    this.numberStr = numberStr;
  }

  @Override
  public String toString() {
    return numberStr;
  }

  @Override
  void write(JsonWriter writer) throws IOException {
    writer.writeNumber(numberStr);
  }

  @Override
  public boolean isNumber() {
    return true;
  }

  @Override
  public int asInt() {
    return Integer.parseInt(numberStr, 10);
  }

  @Override
  public long asLong() {
    return Long.parseLong(numberStr, 10);
  }

  @Override
  public float asFloat() {
    return Float.parseFloat(numberStr);
  }

  @Override
  public double asDouble() {
    return Double.parseDouble(numberStr);
  }

  @Override
  public int hashCode() {
    return numberStr.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (getClass() != object.getClass()) {
      return false;
    }
    JsonNumber other = (JsonNumber) object;
    return numberStr.equals(other.numberStr);
  }

}
