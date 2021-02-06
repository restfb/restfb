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
package com.restfb.integration;

import static org.junit.Assert.assertNotNull;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.DeviceCode;

import org.junit.Test;

public class DeviceCodeITCase extends RestFbIntegrationTestBase {

  @Test
  public void fetchDeviceCode() {
    DefaultFacebookClient client = new DefaultFacebookClient(getTestSettings().getAppId(), Version.VERSION_2_8);
    ScopeBuilder scope = new ScopeBuilder();
    DeviceCode deviceCode = client.fetchDeviceCode(scope);
    assertNotNull(deviceCode);
    FacebookClient.AccessToken token = null;
    System.out.println("UserCode: " + deviceCode.getUserCode());

    int count = 10;
    do {
      System.out.print(count + ":");
      try {
        token = client.obtainDeviceAccessToken(deviceCode.getCode());
      } catch (FacebookOAuthException e) {
        System.out.println("Subcode: " + e.getErrorSubcode());
        System.out.println("Subcode: " + e.getErrorCode());
        System.out.println(e.getErrorMessage());
      } catch (Exception e) {
        System.out.println(e);
      }
      try {
        Thread.sleep(10000l);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      count--;
    } while (count > 0 && token == null);
    assertNotNull(token);

  }
}
