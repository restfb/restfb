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
package com.restfb.types.webhook.messaging.nlp;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

public abstract class BaseNlpEntity {

  @Getter
  @Setter
  @Facebook
  private Double confidence;

  @Getter
  @Setter
  @Facebook
  private String value;

  @Getter
  @Setter
  @Facebook("_entity")
  private String entity;

  /**
   * body of this entity.
   */
  @Getter
  @Setter
  @Facebook("_body")
  private String body;

  /**
   * start position of the entity in the message
   */
  @Getter
  @Setter
  @Facebook("_start")
  private Integer start;

  /**
   * end position of the entity in the message
   */
  @Getter
  @Setter
  @Facebook("_end")
  private Integer end;

  /**
   * returns if the object contains only a nlp entity or if the nlp object contains body, start and end fields, too.
   * @return {@code true} if the nlp object contains also body, start and end fields, {@code false} otherwise
   */
  public boolean isEntityOnly() {
    return body == null;
  }



  public <T extends BaseNlpEntity> T as(Class<T> clazz) {
    return (T) this;
  }
}
