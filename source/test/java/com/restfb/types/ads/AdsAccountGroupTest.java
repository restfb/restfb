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
package com.restfb.types.ads;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

public class AdsAccountGroupTest extends AbstractJsonMapperTests {

  @Test
  public void checkV2_3() {
    AdAccountGroup accGroup =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_3/adsAccountGroup"), AdAccountGroup.class);
    assertNotNull(accGroup);
    assertEquals("368121234", accGroup.getId());
    assertEquals("Ad account group 1", accGroup.getName());
    assertEquals(1, accGroup.getStatus().intValue());

    assertNotNull(accGroup.getAccounts().get(0));
    AdAccountGroupAccount account = accGroup.getAccounts().get(0);
    assertEquals("333444555", account.getAccountId());
    assertEquals("2", account.getStatus());

    assertNotNull(accGroup.getUsers().get(0));
    AdAccountGroupUser user = accGroup.getUsers().get(0);
    assertEquals("1001", user.getRole());
    assertEquals("9876554", user.getUid());
  }

}
