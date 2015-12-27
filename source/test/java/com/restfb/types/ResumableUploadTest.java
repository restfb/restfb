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

import static org.junit.Assert.*;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

public class ResumableUploadTest extends AbstractJsonMapperTests {

  @Test
  public void checkTransfer() {
    ResumableUploadTransferResponse exampleResponse = createJsonMapper()
      .toJavaObject(jsonFromClasspath("v2_5/resumable-transfer"), ResumableUploadTransferResponse.class);
    assertNotNull(exampleResponse);
    assertEquals(6291456L, exampleResponse.getEndOffset().longValue());
    assertEquals(1048576L, exampleResponse.getStartOffset().longValue());
    assertFalse(exampleResponse.isFinished());
  }

  @Test
  public void checkTransferFinished() {
    ResumableUploadTransferResponse exampleResponse = createJsonMapper()
      .toJavaObject(jsonFromClasspath("v2_5/resumable-transfer-finished"), ResumableUploadTransferResponse.class);
    assertNotNull(exampleResponse);
    assertEquals(243546575846L, exampleResponse.getEndOffset().longValue());
    assertEquals(243546575846L, exampleResponse.getStartOffset().longValue());
    assertTrue(exampleResponse.isFinished());
  }

  @Test
  public void checkStart() {
    ResumableUploadStartResponse exampleResponse =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/resumable-start"), ResumableUploadStartResponse.class);
    assertNotNull(exampleResponse);
    assertEquals(1048576L, exampleResponse.getEndOffset().longValue());
    assertEquals(0L, exampleResponse.getStartOffset().longValue());
    assertEquals("885457714883387", exampleResponse.getVideoId());
    assertEquals("1234567890", exampleResponse.getUploadSessionId());
    assertFalse(exampleResponse.isFinished());
  }
}
