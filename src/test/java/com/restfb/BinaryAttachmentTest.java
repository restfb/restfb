/*
 * Copyright (c) 2010-2024 Mark Allen, Norbert Bartels.
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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BinaryAttachmentTest {

  @Test
  void checkByteArray() {
    String attachmentData = "this is a short string";
    BinaryAttachment att = BinaryAttachment.with("myfile.jpg", attachmentData.getBytes());
    assertThat(att).hasFileName("myfile.jpg");
    assertThat(att.getData()).isInstanceOf(ByteArrayInputStream.class);
  }

  @Test
  void checkByteArrayNull() {
    assertThrows(NullPointerException.class, () -> BinaryAttachment.with("filename", (byte[]) null));
  }

  @Test
  void checkInputStream() {
    InputStream stream = this.getClass().getClassLoader().getResourceAsStream("json/account.json");
    BinaryAttachment att = BinaryAttachment.with("myfile.jpg", stream);
    assertThat(att).hasFileName("myfile.jpg");
    assertThat(att.getData()).isInstanceOf(BufferedInputStream.class);
  }

  @Test
  void checkInputStreamNull() {
    assertThrows(NullPointerException.class, () -> BinaryAttachment.with("filename", (InputStream) null));
  }

  @Test
  void checkContentTypeStream() {
    InputStream stream = getClass().getResourceAsStream("/binary/fruits.png");
    BinaryAttachment att = BinaryAttachment.with("example.png", stream);
    assertThat(att).hasContentType("image/png");
  }

  @ParameterizedTest
  @MethodSource("fileAndContentProvider")
  void checkContentTypeBytes(String filename, String contentType) {
    String attachmentData = "this is a short string";
    BinaryAttachment att = BinaryAttachment.with(filename, attachmentData.getBytes());
    assertThat(att).hasContentType(contentType);
  }

  static Stream<Arguments> fileAndContentProvider() {
    return Stream.of( //
      Arguments.arguments("example.png", "image/png"), // png check
      Arguments.arguments("example.html", "text/html"), // html check
      Arguments.arguments("example.json", "application/json") // json check
    );
  }

  @Test
  void checkContentTypeBytes_manual() {
    String attachmentData = "this is a short string";
    BinaryAttachment att = BinaryAttachment.with("example.json", attachmentData.getBytes(), "application/json");
    assertThat(att).hasContentType("application/json");
  }
}
