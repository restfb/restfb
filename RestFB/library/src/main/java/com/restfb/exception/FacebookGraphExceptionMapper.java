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

package com.restfb.exception;

/**
 * Specifies a method for mapping Graph API exceptions' type and message fields
 * to corresponding instances of {@code FacebookGraphException} (or one of its
 * subclasses).
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6
 */
public interface FacebookGraphExceptionMapper {
  /**
   * Given a Graph API exception type and message, generates an instance of the
   * corresponding {@code FacebookGraphException} or one of its subclasses.
   * 
   * @param type
   *          Graph API exception type field, e.g. "OAuthException".
   * @param message
   *          Graph API exception type field, e.g.
   *          "Invalid access token signature."
   * @return An instance of {@code FacebookGraphException} or one of its
   *         subclasses.
   */
  FacebookGraphException exceptionForTypeAndMessage(String type, String message);
}