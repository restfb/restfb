/*
 * Copyright (c) 2010-2014 Mark Allen.
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

package com.restfb.types;

import static com.restfb.util.DateUtils.toDateFromLongFormat;

import java.util.Date;

import com.restfb.Facebook;
import lombok.Getter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/reference/api/user/#apprequests" >App Request Graph API
 * type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.10
 */
public class AppRequest extends FacebookType {

  /**
   * App associated with the request.
   * 
   * @return App associated with the request.
   */
  @Getter
  @Facebook
  private Application application;

  /**
   * The recipient user associated with the request.
   * 
   * @return The recipient user associated with the request.
   */
  @Getter
  @Facebook
  private NamedFacebookType to;

  /**
   * The sender user associated with the request.
   * 
   * @return The sender user associated with the request.
   */
  @Getter
  @Facebook
  private NamedFacebookType from;

  /**
   * A string describing the request.
   * 
   * @return A string describing the request.
   */
  @Getter
  @Facebook
  private String message;

  @Facebook("created_time")
  private String createdTime;

  private static final long serialVersionUID = 1L;

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/page">Cover Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.10
   */
  public static class Application extends NamedFacebookType {

    /**
     * The application's canvas name.
     * 
     * @return The application's canvas name.
     */
    @Getter
    @Facebook("canvas_name")
    private String canvasName;

    /**
     * The application's namespace.
     * 
     * @return The application's namespace.
     */
    @Getter
    @Facebook
    private String namespace;

    private static final long serialVersionUID = 1L;
  }

  /**
   * Timestamp when the request was created.
   * 
   * @return Timestamp when the request was created.
   */
  public Date getCreatedTime() {
    return toDateFromLongFormat(createdTime);
  }
}