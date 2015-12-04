/**
 * Copyright (c) 2010-2015 Mark Allen, Norbert Bartels.
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
package com.restfb.integration.experimental;

import static org.junit.Assert.*;

import com.restfb.DefaultFacebookClient;
import com.restfb.Version;
import com.restfb.experimental.api.Facebook;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import com.restfb.types.Account;
import com.restfb.types.User;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserApiITCase extends RestFbIntegrationTestBase {

  Facebook fb;

  @Before
  @Override
  public void setup() {
    super.setup();
    DefaultFacebookClient client =
        new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.VERSION_2_2);
    fb = new Facebook(client);
  }

  @Test
  public void fetchAllAccounts() {
    List<Account> accList = fb.users().getAccounts();
    assertNotNull(accList);
    assertFalse(accList.isEmpty());
  }

  @Test
  public void fetchMe() {
    User me = fb.users().getMe();
    assertNotNull(me);
    assertEquals(getTestSettings().getUserId(), me.getId());
  }

  @Test
  public void fetchUser() {
    String userId = "4";
    User zuck = fb.users().get(userId);
    assertNotNull(zuck);
    assertEquals(userId, zuck.getId());
  }
}
