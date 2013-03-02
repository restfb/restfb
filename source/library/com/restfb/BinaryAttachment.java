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

package com.restfb;

import static com.restfb.util.StringUtils.isBlank;
import static java.lang.String.format;

import java.io.InputStream;

import com.restfb.util.ReflectionUtils;

/**
 * Represents a binary file that can be uploaded to Facebook.
 * <p>
 * Normally this would be a photo or video.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.5
 */
public class BinaryAttachment {
  private String filename;
  private InputStream data;

  /**
   * Creates a new binary attachment.
   * 
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   */
  protected BinaryAttachment(String filename, InputStream data) {
    if (isBlank(filename))
      throw new IllegalArgumentException("Binary attachment filename cannot be blank.");
    if (data == null)
      throw new IllegalArgumentException("Binary attachment data cannot be null.");

    this.filename = filename;
    this.data = data;
  }

  /**
   * Creates a binary attachment.
   * 
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   */
  public static BinaryAttachment with(String filename, InputStream data) {
    return new BinaryAttachment(filename, data);
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
    return format("[filename=%s]", getFilename());
  }

  /**
   * The attachment's filename.
   * 
   * @return The attachment's filename.
   */
  public String getFilename() {
    return filename;
  }

  /**
   * The attachment's data.
   * 
   * @return The attachment's data.
   */
  public InputStream getData() {
    return data;
  }
}