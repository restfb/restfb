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
import com.restfb.util.ReflectionUtils;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/privacy/">Privacy Graph API type</a>
 * .
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Privacy implements Serializable {

  /**
   * The description of the privacy value.
   *
   * @return The description of the privacy value.
   */
  @Getter
  @Setter
  @Facebook
  private String value;

  /**
   * The privacy description.
   *
   * @return The privacy description.
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * The privacy friends restriction.
   *
   * @return The privacy friends restriction.
   */
  @Getter
  @Setter
  @Facebook
  private String friends;

  /**
   * The privacy networks restriction.
   *
   * @return The privacy networks restriction.
   */
  @Getter
  @Setter
  @Facebook
  private String networks;

  /**
   * For CUSTOM settings, a comma-separated list of user IDs and friend list IDs that "cannot" see the post.
   *
   * @return The privacy "deny" restriction.
   */
  @Getter
  @Setter
  @Facebook
  private String deny;

  /**
   * For CUSTOM settings, a comma-separated list of user IDs and friend list IDs that "can" see the post. This can also
   * be ALL_FRIENDS or FRIENDS_OF_FRIENDS to include all members of those sets.
   *
   * @return The privacy "allow" restriction.
   */
  @Getter
  @Setter
  @Facebook
  private String allow;

  private static final long serialVersionUID = 1L;

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ReflectionUtils.hashCode(this);
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object that) {
    return ReflectionUtils.equals(this, that);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ReflectionUtils.toString(this);
  }

}
