/*
 * Copyright (c) 2010-2015 Norbert Bartels
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

public class DeviceCode implements Serializable {

  /**
   * Code used in the authorization poll
   */
  @Getter
  @Setter
  @Facebook
  private String code;

  /**
   * String to be shown to the user
   */
  @Getter
  @Setter
  @Facebook("user_code")
  private String userCode;

  /**
   * Url the user should call.
   * 
   * Here he should add the user code
   */
  @Getter
  @Setter
  @Facebook("verification_uri")
  private String verificationUri;

  /**
   * The code expires in these seconds.
   * 
   * You should cancel the login flow after that time if you do not receive an access token
   */
  @Getter
  @Setter
  @Facebook("expires_in")
  private Integer expiresIn;

  /**
   * Your device should poll the Device Login API every <code>interval</code> seconds to see if the authorization has
   * been successful
   */
  @Getter
  @Setter
  @Facebook
  private Integer interval;

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
