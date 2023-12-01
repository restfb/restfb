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

import com.restfb.JsonMapper;
import com.restfb.annotation.OriginalJson;

import lombok.Setter;

/**
 * {@code From} type is used as replacement for the case we need a user or a page. Because these
 * both types have different fields we have this special handling.
 *
 * Because the minimal intersection of page and user are id and name, so the {@code From} object extends the
 * {@link NamedFacebookType} and as fast access name and id are available.
 */
public class From extends NamedFacebookType {

  @OriginalJson
  private String json;

  @Setter
  private User user;

  @Setter
  private Page page;

  /**
   * returns the from field as {@link com.restfb.types.User} object
   * 
   * @return the from field as User
   */
  public User getAsUser() {
    return user;
  }

  /**
   * returns the from field as {@link com.restfb.types.Page} object
   * 
   * @return the from field as Page
   */
  public Page getAsPage() {
    return page;
  }

  @JsonMapper.JsonMappingCompleted
  protected void convert(JsonMapper jsonMapper) {
    if (json != null) {
      user = jsonMapper.toJavaObject(json, User.class);
      page = jsonMapper.toJavaObject(json, Page.class);
    }
  }

}
