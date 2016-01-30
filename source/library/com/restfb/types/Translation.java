/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/app/translations">Translations Graph
 * API type</a>.
 */
public class Translation extends FacebookType {

  /**
   * The translated string.
   *
   * @return The translated string.
   */
  @Getter
  @Setter
  @Facebook
  private String translation;

  /**
   * The approval status of the string.
   * <p>
   * Possible values: auto-approved, approved, unapproved
   * </p>
   * 
   * @return The approval status of the string.
   */
  @Getter
  @Setter
  @Facebook("approval_status")
  private String approvalStatus;

  /**
   * The original string that was translated.
   *
   * @return The original string that was translated.
   */
  @Getter
  @Setter
  @Facebook("native_string")
  private String nativeString;

  /**
   * The provided description of the string.
   *
   * @return The provided description of the string.
   */
  @Getter
  @Setter
  @Facebook
  private String description;
}
