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
import com.restfb.JsonMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * Type that can be used as return value for publishing new objects to Facebook.
 * 
 * Id, post_id and success field are accessible. Have a look at the fields and methods javadoc
 */
public class GraphResponse {

  /**
   * <code>true</code> if publishing of the object was successful, <code>false</code> otherwise.
   *
   * if the success field is not provided by Facebook, we check if the id field is present
   */
  @Getter
  @Setter
  @Facebook
  private boolean success;

  @Getter
  @Setter
  @Facebook
  private String id;

  @Getter
  @Setter
  @Facebook("post_id")
  private String postId;

  /**
   * returns the id that is used for the post or comment.
   * 
   * Normally the <code>id</code> of the newly object is returned, but after publishing a photo the <code>post id</code>
   * is returned. So you get the id of the corresponding post without put the logic in your application.
   * 
   * Attention: if you publish a photo without story you get the photo id here
   *
   * @return id of the new created post / comment
   */
  public String getTimelineId() {
    return postId != null ? postId : id;
  }

  @JsonMapper.JsonMappingCompleted
  protected void check() {
    if (id != null) {
      success = true;
    }
  }

}
