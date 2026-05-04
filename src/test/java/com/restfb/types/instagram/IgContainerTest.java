/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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
package com.restfb.types.instagram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class IgContainerTest extends AbstractJsonMapperTests {

  @Test
  void checkJson() {
    IgContainer igContainer = createJsonMapper().toJavaObject(jsonFromClasspath("instagram/container"), IgContainer.class);

    assertNotNull(igContainer);
    assertEquals("17889615691921648", igContainer.getId());
    assertEquals("FINISHED", igContainer.getStatusCode());
    assertEquals("completed", igContainer.getStatus());
    assertNotNull(igContainer.getCopyrightCheckStatus());
    assertEquals("completed", igContainer.getCopyrightCheckStatus().getStatus());
    assertTrue(igContainer.getCopyrightCheckStatus().getMatchesFound());
  }

  @Test
  void checkJson_missingCopyrightCheckStatus() {
    IgContainer igContainer = createJsonMapper().toJavaObject(jsonFromClasspath("instagram/container_no_copyright"),
      IgContainer.class);

    assertNotNull(igContainer);
    assertEquals("17889615691921648", igContainer.getId());
    assertEquals("in_progress", igContainer.getStatus());
    assertEquals("IN_PROGRESS", igContainer.getStatusCode());
    assertNull(igContainer.getCopyrightCheckStatus());
  }
}
