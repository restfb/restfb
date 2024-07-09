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
package com.restfb.exception;

import static com.restfb.exception.devicetoken.DeviceTokenExceptionFactory.createFrom;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restfb.exception.devicetoken.FacebookDeviceTokenCodeExpiredException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenDeclinedException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenPendingException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenSlowdownException;

@ExtendWith(MockitoExtension.class)
class DeviceTokenExceptionFactoryTest {

  @Mock
  FacebookOAuthException mockException;

  @Test
  void authorizationPending() {
    when(mockException.getErrorMessage()).thenReturn("authorization_pending");
    assertThrows(FacebookDeviceTokenPendingException.class, () -> createFrom(mockException));
  }

  @Test
  void authorizationPendingSubCode() {
    when(mockException.getErrorSubcode()).thenReturn(1349174);
    assertThrows(FacebookDeviceTokenPendingException.class, () -> createFrom(mockException));
  }

  @Test
  void authorizationDeclined() {
    when(mockException.getErrorMessage()).thenReturn("authorization_declined");
    assertThrows(FacebookDeviceTokenDeclinedException.class, () -> createFrom(mockException));
  }

  @Test
  void slowDown() {
    when(mockException.getErrorMessage()).thenReturn("slow_down");
    assertThrows(FacebookDeviceTokenSlowdownException.class, () -> createFrom(mockException));
  }

  @Test
  void slowDownSubCode() {
    when(mockException.getErrorSubcode()).thenReturn(1349172);
    assertThrows(FacebookDeviceTokenSlowdownException.class, () -> createFrom(mockException));
  }

  @Test
  void codeExpired() {
    when(mockException.getErrorMessage()).thenReturn("code_expired");
    assertThrows(FacebookDeviceTokenCodeExpiredException.class, () -> createFrom(mockException));
  }

  @Test
  void codeExpiredSubcode() {
    when(mockException.getErrorSubcode()).thenReturn(1349152);
    assertThrows(FacebookDeviceTokenCodeExpiredException.class, () -> createFrom(mockException));
  }

  @Test
  void noDeviceCodeException() {
    assertThrows(FacebookOAuthException.class, () -> createFrom(mockException));
  }

}
