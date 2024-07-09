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
package com.restfb.types;

import com.restfb.Facebook;
import com.restfb.types.features.HasMessage;

import lombok.Getter;
import lombok.Setter;

/**
 * Respresents the <a href="https://developers.facebook.com/docs/graph-api/reference/saved-message-response/">Saved
 * Message Response</a> type
 */
public class SavedMessageResponse extends FacebookType implements HasMessage {

  /**
   * The message category of the saved response
   */
  @Getter
  @Setter
  @Facebook
  private String category;

  /**
   * The image attached to this save response
   */
  @Getter
  @Setter
  @Facebook
  private String image;

  /**
   * Toggle whether to enable the message
   */
  @Getter
  @Setter
  @Facebook("is_enabled")
  private Boolean isEnabled;

  /**
   * The message body of the saved response
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook
  private String message;

  /**
   * The title of the saved response
   */
  @Getter
  @Setter
  @Facebook
  private String title;
}
