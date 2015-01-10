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
package com.restfb.integration.experimental;

import com.restfb.DefaultFacebookClient;
import com.restfb.Version;
import com.restfb.experimental.api.Facebook;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import com.restfb.types.NamedFacebookType;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class FetchGroupMemberITCase extends RestFbIntegrationTestBase {

  @Test
  public void fetchGroupMembers() {
    DefaultFacebookClient client =
        new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.VERSION_2_2);
    Facebook fb = new Facebook(client);
    List<NamedFacebookType> memberList = fb.groups().memberList(getTestSettings().getGroupId());
    assertNotNull(memberList);
    assertTrue(memberList.size() > 0);
  }

}
