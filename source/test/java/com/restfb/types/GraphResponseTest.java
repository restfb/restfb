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

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

public class GraphResponseTest extends AbstractJsonMapperTests {

  @Test
  public void successExample() {
    GraphResponse result =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/graphresponse_success"), GraphResponse.class);
    assertTrue(result.isSuccess());
  }

  @Test
  public void successNotExample() {
    GraphResponse result =
            createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/graphresponse_success_not"), GraphResponse.class);
    assertFalse(result.isSuccess());
  }

  @Test
  public void idExample() {
    GraphResponse result =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/graphresponse_id"), GraphResponse.class);
    assertTrue(result.isSuccess());
    assertEquals(result.getId(), result.getTimelineId());
    assertNull(result.getPostId());
  }

  @Test
  public void imageExample() {
    GraphResponse result =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/graphresponse_image"), GraphResponse.class);
    assertTrue(result.isSuccess());
    assertEquals(result.getPostId(), result.getTimelineId());
    assertNotNull(result.getPostId());
    assertNotNull(result.getId());
  }

}
