/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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

import java.util.Date;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/video-list">Video List Graph API type</a>.
 * 
 * Facebook APi Version 2.3+
 * 
 * @since 1.12.0
 */
public class VideoList extends FacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * Description of the playlist
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * Title of the playlist
   */
  @Getter
  @Setter
  @Facebook
  private String title;

  /**
   * Owner of the playlist
   */
  @Getter
  @Setter
  @Facebook
  private CategorizedFacebookType owner;

  /**
   * The time when the playlist was created
   */
  @Getter
  @Setter
  @Facebook("creation_time")
  private Date creationTime;

  /**
   * The time when the contents of the playlist was last changed
   */
  @Getter
  @Setter
  @Facebook("last_modified")
  private Date lastModified;

}
