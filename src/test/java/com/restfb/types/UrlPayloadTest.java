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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.restfb.types.send.MediaAttachment;

public class UrlPayloadTest {

  @Test
  public void checkToString() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.IMAGE, "exampleUrl");
    String toStringAttachment = "MediaAttachment[payload=UrlPayload[isReusable=null url=exampleUrl] type=image]";
    assertEquals(toStringAttachment, attachment.toString());
  }

  @Test
  public void checkHashcode() {
    MediaAttachment attachment = new MediaAttachment(MediaAttachment.Type.IMAGE, "exampleUrl");
    assertEquals(-1842490888, attachment.hashCode());
  }

  @Test
  public void checkEquals() {
    MediaAttachment attachment1 = new MediaAttachment(MediaAttachment.Type.IMAGE, "exampleUrl");
    MediaAttachment attachment2 = new MediaAttachment(MediaAttachment.Type.IMAGE, "exampleUrl");
    assertTrue(attachment1.equals(attachment2));
    // equals is used here, because this is the test
  }

}
