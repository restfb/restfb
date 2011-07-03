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

package com.restfb;

import java.io.InputStream;

/**
 * TODO: Document
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.5
 */
public class BinaryAttachment {
  private String filename;
  private InputStream data;

  private BinaryAttachment(String filename, InputStream data) {
    this.filename = filename;
    this.data = data;
  }

  public static BinaryAttachment with(String filename, InputStream data) {
    return new BinaryAttachment(filename, data);
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