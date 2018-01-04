/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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
package com.restfb;

import static com.restfb.testutils.RestfbAssertions.assertThat;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BinaryAttachmentTest {

  @Test
  public void checkByteArray() {
    String attachmentData = "this is a short string";
    BinaryAttachment att = BinaryAttachment.with("myfile.jpg", attachmentData.getBytes());
    assertThat(att).hasFileName("myfile.jpg");
    assertThat(att.getData()).isInstanceOf(ByteArrayInputStream.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkByteArrayNull() {
    BinaryAttachment.with("filename", (byte[]) null);
  }

  @Test
  public void checkInputStream() {
    InputStream stream = this.getClass().getClassLoader().getResourceAsStream("json/account.json");
    BinaryAttachment att = BinaryAttachment.with("myfile.jpg", stream);
    assertThat(att).hasFileName("myfile.jpg");
    assertThat(att.getData()).isInstanceOf(BufferedInputStream.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkInpuStreamNull() {
    BinaryAttachment.with("filename", (InputStream) null);
  }

  @Test
  public void checkContentTypeStream() {
    InputStream stream = getClass().getResourceAsStream("/binary/fruits.png");
    BinaryAttachment att = BinaryAttachment.with("example.png", stream);
    assertThat(att).hasContentType("image/png");
  }

  @Test
  public void checkContentTypeBytes_imagePng() {
    String attachmentData = "this is a short string";
    BinaryAttachment att = BinaryAttachment.with("example.png", attachmentData.getBytes());
    assertThat(att).hasContentType("image/png");
  }

  @Test
  public void checkContentTypeBytes_html() {
    String attachmentData = "this is a short string";
    BinaryAttachment att = BinaryAttachment.with("example.html", attachmentData.getBytes());
    assertThat(att).hasContentType("text/html");
  }

  @Test
  public void checkContentTypeBytes_fallback() {
    String attachmentData = "this is a short string";
    BinaryAttachment att = BinaryAttachment.with("example.json", attachmentData.getBytes());
    assertThat(att).hasContentType("application/octet-stream");
  }

  @Test
  public void checkContentTypeBytes_manual() {
    String attachmentData = "this is a short string";
    BinaryAttachment att = BinaryAttachment.with("example.json", attachmentData.getBytes(), "application/json");
    assertThat(att).hasContentType("application/json");
  }
}
