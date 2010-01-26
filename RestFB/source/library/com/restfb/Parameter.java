/*
 * Copyright (c) 2010 Mark Allen.
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

/**
 * Representation of a Facebook API request parameter.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Parameter {
  /**
   * Parameter name.
   */
  public final String name;

  /**
   * Parameter value.
   */
  public final String value;

  /**
   * Creates a new parameter with the given {@code name} and {@code value}.
   * 
   * @param name
   *          The parameter name.
   * @param value
   *          The parameter value.
   * @throws IllegalArgumentException
   *           If {@code name} or {@code value} is {@code null} or a blank
   *           string.
   */
  private Parameter(String name, String value) {
    if (StringUtils.isBlank(name) || StringUtils.isBlank(value))
      throw new IllegalArgumentException(Parameter.class
          + " instances must have a non-blank name and value.");

    this.name = StringUtils.trimToEmpty(name).toLowerCase();
    this.value = value;
  }

  /**
   * Factory method which provides an instance with the given {@code name} and
   * {@code value}.
   * 
   * @param name
   *          The parameter name.
   * @param value
   *          The parameter value.
   * @return A {@code Parameter} instance with the given {@code name} and
   *         {@code value}.
   * @throws IllegalArgumentException
   *           If {@code name} or {@code value} is {@code null} or a blank
   *           string.
   */
  public static Parameter with(String name, String value) {
    return new Parameter(name, value);
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!getClass().equals(obj.getClass()))
      return false;

    Parameter other = (Parameter) obj;

    if (this.name != other.name && (!this.name.equals(other.name)))
      return false;
    if (this.value != other.value && (!this.value.equals(other.value)))
      return false;

    return true;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + this.name.hashCode();
    hash = 41 * hash + this.value.hashCode();
    return hash;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("Parameter[%s=%s]", name, value);
  }
}