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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.http.HttpRequest.BodyPublisher;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.restfb.BinaryAttachment;
import com.restfb.util.StringUtils;

/**
 * Writes multipart/form-data requests to a temporary file and exposes them as a BodyPublisher.
 */
public class MultipartFormBodyPublisher implements Closeable {

  private static final byte[] MULTIPART_CARRIAGE_RETURN_AND_NEWLINE = "\r\n".getBytes(StandardCharsets.UTF_8);
  private static final byte[] MULTIPART_TWO_HYPHENS = "--".getBytes(StandardCharsets.UTF_8);

  private final TempFileBodyPublisher tempFileBodyPublisher;
  private final OutputStream outputStream;
  private final String boundary;
  private final int bufferSize;

  public MultipartFormBodyPublisher(String boundary, int bufferSize) throws IOException {
    this.boundary = boundary;
    this.bufferSize = bufferSize;
    tempFileBodyPublisher = new TempFileBodyPublisher();
    outputStream = tempFileBodyPublisher.outputStream();
  }

  public void addAttachments(List<BinaryAttachment> attachments) throws IOException {
    for (BinaryAttachment attachment : attachments) {
      addAttachment(attachment);
    }
  }

  private void addAttachment(BinaryAttachment attachment) throws IOException {
    StringBuilder headers = new StringBuilder();
    headers.append(new String(MULTIPART_TWO_HYPHENS, StandardCharsets.UTF_8)).append(boundary)
      .append(new String(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE, StandardCharsets.UTF_8))
      .append("Content-Disposition: form-data; name=\"").append(attachment.getFormFieldName()).append("\"; filename=\"")
      .append(attachment.getFilename()).append("\"")
      .append(new String(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE, StandardCharsets.UTF_8)).append("Content-Type: ")
      .append(attachment.getContentType())
      .append(new String(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE, StandardCharsets.UTF_8))
      .append(new String(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE, StandardCharsets.UTF_8));

    outputStream.write(headers.toString().getBytes(StringUtils.ENCODING_CHARSET));
    try (InputStream data = attachment.getData()) {
      write(data, outputStream);
    }
    outputStream.write((new String(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE, StandardCharsets.UTF_8)
        + new String(MULTIPART_TWO_HYPHENS, StandardCharsets.UTF_8) + boundary
        + new String(MULTIPART_TWO_HYPHENS, StandardCharsets.UTF_8)
        + new String(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE, StandardCharsets.UTF_8))
      .getBytes(StringUtils.ENCODING_CHARSET));
  }

  private void write(InputStream source, OutputStream destination) throws IOException {
    byte[] buffer = new byte[bufferSize];
    int read;
    while ((read = source.read(buffer)) > 0) {
      destination.write(buffer, 0, read);
    }
  }

  public BodyPublisher build() throws IOException {
    outputStream.flush();
    outputStream.close();
    return tempFileBodyPublisher.build();
  }

  @Override
  public void close() throws IOException {
    tempFileBodyPublisher.close();
  }
}
