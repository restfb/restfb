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

import com.restfb.AbstractJsonMapperTests;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DebugTokenInfoTest extends AbstractJsonMapperTests {

  @Test
  public void testJsonWithScopes() {
    FacebookClient.DebugTokenInfo exampleDebugTokenInfo =
        createJsonMapper().toJavaObject(jsonFromClasspath("debug-token-info"), FacebookClient.DebugTokenInfo.class);
    assertNotNull(exampleDebugTokenInfo);
    assertTrue(exampleDebugTokenInfo.getScopes().contains("email"));
    assertTrue(exampleDebugTokenInfo.getScopes().contains("publish_actions"));
  }

  @Test
  public void testJsonWithMetadata() {
    FacebookClient.DebugTokenInfo exampleDebugTokenInfo =
        createJsonMapper().toJavaObject(jsonFromClasspath("debug-token-info"), FacebookClient.DebugTokenInfo.class);
    assertNotNull(exampleDebugTokenInfo);
    assertNotNull(exampleDebugTokenInfo.getMetaData());
    JsonObject metaData = exampleDebugTokenInfo.getMetaData();
    assertTrue(metaData.has("sso"));
    assertEquals("iphone-safari", metaData.getString("sso"));
  }
}
