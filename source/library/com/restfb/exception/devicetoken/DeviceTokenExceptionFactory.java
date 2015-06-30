/*
 * Copyright (c) 2010-2015 Norbert Bartels.
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

package com.restfb.exception.devicetoken;

import com.restfb.exception.FacebookOAuthException;

public class DeviceTokenExceptionFactory {

  private DeviceTokenExceptionFactory() {
    // just a utility class
  }

  /**
   * Create a {@link FacebookDeviceTokenException} to a given {@link FacebookOAuthException}.
   * 
   * @param oauthException
   * @throws FacebookDeviceTokenCodeExpiredException
   * @throws FacebookDeviceTokenPendingException
   * @throws FacebookDeviceTokenDeclinedException
   * @throws FacebookDeviceTokenSlowdownException
   */
  public static void createFrom(FacebookOAuthException oauthException) throws FacebookDeviceTokenCodeExpiredException,
      FacebookDeviceTokenPendingException, FacebookDeviceTokenDeclinedException, FacebookDeviceTokenSlowdownException {

    String errorMessage = oauthException.getErrorMessage();
    if (errorMessage.equals("authorization_pending")) {
      throw new FacebookDeviceTokenPendingException(errorMessage, oauthException);
    }
    if (errorMessage.equals("authorization_declined")) {
      throw new FacebookDeviceTokenDeclinedException(errorMessage, oauthException);
    }
    if (errorMessage.equals("slow_down")) {
      throw new FacebookDeviceTokenSlowdownException(errorMessage, oauthException);
    }
    if (errorMessage.equals("code_expired")) {
      throw new FacebookDeviceTokenCodeExpiredException(errorMessage, oauthException);
    }

    throw oauthException;
  }
}
