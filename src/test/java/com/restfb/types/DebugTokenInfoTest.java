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
package com.restfb.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;

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
    assertNotNull(metaData.get("sso"));
    assertEquals("iphone-safari", metaData.get("sso").asString());
  }

  @Test
  public void withExpires() {
    FacebookClient.DebugTokenInfo exampleDebugTokenInfo =
            createJsonMapper().toJavaObject(jsonFromClasspath("debug-token-info-2"), FacebookClient.DebugTokenInfo.class);
    assertNotNull(exampleDebugTokenInfo);
    assertEquals(8, exampleDebugTokenInfo.getScopes().size());
    assertEquals(1563217616000L, exampleDebugTokenInfo.getDataAccessExpiresAt().getTime());
    assertEquals("USER", exampleDebugTokenInfo.getType());
    assertTrue(exampleDebugTokenInfo.isValid());
    assertEquals("1234567890", exampleDebugTokenInfo.getAppId());
    assertEquals(1561330800000l, exampleDebugTokenInfo.getExpiresAt().getTime());
  }
}
