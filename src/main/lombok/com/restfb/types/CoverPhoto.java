/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/cover-photo/">Cover Photo Graph API
 * type</a>.
 */
public class CoverPhoto extends FacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * When non-zero, the cover image overflows horizontally. The value indicates the offset percentage of the total image
   * width from the left [0-100]
   *
   * @return offsetX
   */
  @Getter
  @Setter
  @Facebook("offset_x")
  Integer offsetX;

  /**
   * When non-zero, the cover photo overflows vertically. The value indicates the offset percentage of the total image
   * height from the top [0-100]
   *
   * @return offsetY
   */
  @Getter
  @Setter
  @Facebook("offset_y")
  Integer offsetY;

  /**
   * Direct URL for the person's cover photo image
   *
   * @return Direct URL for the person's cover photo image
   */
  @Getter
  @Setter
  @Facebook
  String source;
}
