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

import static org.junit.Assert.*;

import com.restfb.AbstractJsonMapperTests;

import org.hamcrest.core.StringContains;
import org.junit.Test;

public class TargetingProductAudienceSubSpecTest extends AbstractJsonMapperTests {

  @Test
  public void test() {
    TargetingProductAudienceSubSpec subSpec = createJsonMapper().toJavaObject(
      jsonFromClasspath("ads/v2_6/targetingproductaudiencesubspec"), TargetingProductAudienceSubSpec.class);
    assertNotNull(subSpec);
    assertEquals(1234567L, subSpec.getRetentionSeconds().longValue());
    assertThat(subSpec.getRule(), new StringContains("shoes"));
  }
}
