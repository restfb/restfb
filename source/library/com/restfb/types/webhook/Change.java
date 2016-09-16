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
package com.restfb.types.webhook;

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The transferred change containing the field and the value
 */
@ToString
public class Change {

  /**
   * the subscribed field.
   */
  @Facebook
  @Getter
  @Setter
  private String field;

  /**
   * value of this change.
   *
   * You have to check the current class implementation because the value depends on the change Facebook sends to you.
   *
   * @return the current changed value, may be {@code null}
   */
  @Getter
  @Setter
  private ChangeValue value = null;

  @Facebook("value")
  private String rawValue;

  @JsonMapper.JsonMappingCompleted
  private void convertChangeValue(JsonMapper mapper) {

    if (rawValue != null) {
      ChangeValueFactory factory = new ChangeValueFactory().setField(field).setValue(rawValue);
      value = factory.buildWithMapper(mapper);
    }
  }
}
