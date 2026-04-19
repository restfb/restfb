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
package com.restfb.request;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Streams request bodies via a temporary file so Java 11 HttpClient can re-read them.
 */
public class TempFileBodyPublisher implements Closeable {

  private final Path tempFile;
  private final OutputStream outputStream;
  private boolean closed;

  public TempFileBodyPublisher() throws IOException {
    tempFile = Files.createTempFile("restfb-request", ".bin");
    outputStream = Files.newOutputStream(tempFile, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
  }

  public OutputStream outputStream() {
    return outputStream;
  }

  public BodyPublisher build() throws IOException {
    outputStream.flush();
    outputStream.close();
    closed = true;
    return BodyPublishers.ofFile(tempFile);
  }

  @Override
  public void close() throws IOException {
    if (!closed) {
      outputStream.close();
      closed = true;
    }
    Files.deleteIfExists(tempFile);
  }
}
