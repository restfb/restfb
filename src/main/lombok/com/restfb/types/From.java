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

import com.restfb.JsonMapper;
import com.restfb.annotation.OriginalJson;

import lombok.Setter;

public class From extends CategorizedFacebookType {

  @OriginalJson
  private String json;

  @Setter
  private User user;

  @Setter
  private Page page;

  /**
   * returns the from field as {@see com.restfb.types.User} object
   * 
   * @return the from field as User
   */
  public User getAsUser() {
    return user;
  }

  /**
   * returns the from field as {@see com.restfb.types.Page} object
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
