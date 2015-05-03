/*
 * Copyright (c) 2010-2015 Mark Allen.
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

import static com.restfb.util.StringUtils.ENCODING_CHARSET;
import static com.restfb.util.StringUtils.fromInputStream;
import static com.restfb.util.UrlUtils.urlDecode;
import static java.lang.String.format;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.util.logging.Level.FINE;
import static java.util.logging.Level.FINER;
import static java.util.logging.Level.WARNING;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Default implementation of a service that sends HTTP requests to the Facebook API endpoint.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class DefaultWebRequestor implements WebRequestor {
  /**
   * Arbitrary unique boundary marker for multipart {@code POST}s.
   */
  private static final String MULTIPART_BOUNDARY = "**boundarystringwhichwill**neverbeencounteredinthewild**";

  /**
   * Line separator for multipart {@code POST}s.
   */
  private static final String MULTIPART_CARRIAGE_RETURN_AND_NEWLINE = "\r\n";

  /**
   * Hyphens for multipart {@code POST}s.
   */
  private static final String MULTIPART_TWO_HYPHENS = "--";

  /**
   * Default buffer size for multipart {@code POST}s.
   */
  private static final int MULTIPART_DEFAULT_BUFFER_SIZE = 8192;

  /**
   * By default, how long should we wait for a response (in ms)?
   */
  private static final int DEFAULT_READ_TIMEOUT_IN_MS = 180000;

  /**
   * Logger.
   */
  private static final Logger logger = Logger.getLogger("com.restfb.HTTP");

  /**
   * By default this is true, to prevent breaking existing usage
   */
  private boolean autocloseBinaryAttachmentStream = true;

  protected enum HttpMethod {
    GET, DELETE, POST
  }

  /**
   * @throws java.io.IOException
   * @see com.restfb.WebRequestor#executeGet(java.lang.String)
   */
  @Override
  public Response executeGet(String url) throws IOException {
    return execute(url, HttpMethod.GET);
  }

  /**
   * @throws java.io.IOException
   * @see com.restfb.WebRequestor#executePost(java.lang.String, java.lang.String)
   */
  @Override
  public Response executePost(String url, String parameters) throws IOException {
    return executePost(url, parameters, (BinaryAttachment[]) null);
  }

  /**
   * @throws java.io.IOException
   * @see com.restfb.WebRequestor#executePost(java.lang.String, java.lang.String, com.restfb.BinaryAttachment[])
   */
  @Override
  public Response executePost(String url, String parameters, BinaryAttachment... binaryAttachments) throws IOException {
    if (binaryAttachments == null) {
      binaryAttachments = new BinaryAttachment[0];
    }

    if (logger.isLoggable(FINE)) {
      logger.fine("Executing a POST to " + url + " with parameters "
          + (binaryAttachments.length > 0 ? "" : "(sent in request body): ") + urlDecode(parameters)
          + (binaryAttachments.length > 0 ? " and " + binaryAttachments.length + " binary attachment[s]." : ""));
    }

    HttpURLConnection httpUrlConnection = null;
    OutputStream outputStream = null;
    InputStream inputStream = null;

    try {
      httpUrlConnection = openConnection(new URL(url + (binaryAttachments.length > 0 ? "?" + parameters : "")));
      httpUrlConnection.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);

      // Allow subclasses to customize the connection if they'd like to - set
      // their own headers, timeouts, etc.
      customizeConnection(httpUrlConnection);

      httpUrlConnection.setRequestMethod("POST");
      httpUrlConnection.setDoOutput(true);
      httpUrlConnection.setUseCaches(false);

      if (binaryAttachments.length > 0) {
        httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
        httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + MULTIPART_BOUNDARY);
      }

      httpUrlConnection.connect();
      outputStream = httpUrlConnection.getOutputStream();

      // If we have binary attachments, the body is just the attachments and the
      // other parameters are passed in via the URL.
      // Otherwise the body is the URL parameter string.
      if (binaryAttachments.length > 0) {
        for (BinaryAttachment binaryAttachment : binaryAttachments) {
          StringBuilder stringBuilder = new StringBuilder();

          stringBuilder.append(MULTIPART_TWO_HYPHENS).append(MULTIPART_BOUNDARY)
            .append(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE).append("Content-Disposition: form-data; name=\"")
            .append(createFormFieldName(binaryAttachment)).append("\"; filename=\"")
            .append(binaryAttachment.getFilename()).append("\"");

          if (binaryAttachment.getContentType() != null && binaryAttachment.getContentType().length() > 0) {
            stringBuilder.append(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE).append("Content-Type: ")
              .append(binaryAttachment.getContentType());
          }

          stringBuilder.append(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE).append(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE);

          outputStream.write(stringBuilder.toString().getBytes(ENCODING_CHARSET));

          write(binaryAttachment.getData(), outputStream, MULTIPART_DEFAULT_BUFFER_SIZE);

          outputStream.write((MULTIPART_CARRIAGE_RETURN_AND_NEWLINE + MULTIPART_TWO_HYPHENS + MULTIPART_BOUNDARY
              + MULTIPART_TWO_HYPHENS + MULTIPART_CARRIAGE_RETURN_AND_NEWLINE).getBytes(ENCODING_CHARSET));
        }
      } else {
        outputStream.write(parameters.getBytes(ENCODING_CHARSET));
      }

      if (logger.isLoggable(FINER)) {
        logger.log(FINER, "Response headers: {0}", httpUrlConnection.getHeaderFields());
      }

      try {
        inputStream =
            httpUrlConnection.getResponseCode() != HTTP_OK ? httpUrlConnection.getErrorStream() : httpUrlConnection
              .getInputStream();
      } catch (IOException e) {
        if (logger.isLoggable(WARNING)) {
          logger.log(WARNING, "An error occurred while POSTing to {0}: {1}", new Object[] { url, e });
        }
      }

      return new Response(httpUrlConnection.getResponseCode(), fromInputStream(inputStream));
    } finally {
      if (autocloseBinaryAttachmentStream && binaryAttachments.length > 0) {
        for (BinaryAttachment binaryAttachment : binaryAttachments) {
          closeQuietly(binaryAttachment.getData());
        }
      }

      closeQuietly(outputStream);
      closeQuietly(httpUrlConnection);
    }
  }

  /**
   * Given a {@code url}, opens and returns a connection to it.
   * <p>
   * If you'd like to pipe your connection through a proxy, this is the place to do so.
   * 
   * @param url
   *          The URL to connect to.
   * @return A connection to the URL.
   * @throws IOException
   *           If an error occurs while establishing the connection.
   * @since 1.6.3
   */
  protected HttpURLConnection openConnection(URL url) throws IOException {
    return (HttpURLConnection) url.openConnection();
  }

  /**
   * Hook method which allows subclasses to easily customize the {@code connection}s created by
   * {@link #executeGet(String)} and {@link #executePost(String, String)} - for example, setting a custom read timeout
   * or request header.
   * <p>
   * This implementation is a no-op.
   * 
   * @param connection
   *          The connection to customize.
   */
  protected void customizeConnection(HttpURLConnection connection) {}

  /**
   * Attempts to cleanly close a resource, swallowing any exceptions that might occur since there's no way to recover
   * anyway.
   * <p>
   * It's OK to pass {@code null} in, this method will no-op in that case.
   * 
   * @param closeable
   *          The resource to close.
   */
  protected void closeQuietly(Closeable closeable) {
    if (closeable == null) {
      return;
    }
    try {
      closeable.close();
    } catch (Throwable t) {
      if (logger.isLoggable(WARNING)) {
        logger.log(WARNING, "Unable to close {0}: {1}", new Object[] { closeable, t });
      }
    }
  }

  /**
   * Attempts to cleanly close an {@code HttpURLConnection}, swallowing any exceptions that might occur since there's no
   * way to recover anyway.
   * <p>
   * It's OK to pass {@code null} in, this method will no-op in that case.
   * 
   * @param httpUrlConnection
   *          The connection to close.
   */
  protected void closeQuietly(HttpURLConnection httpUrlConnection) {
    if (httpUrlConnection == null) {
      return;
    }
    try {
      httpUrlConnection.disconnect();
    } catch (Throwable t) {
      if (logger.isLoggable(WARNING)) {
        logger.log(WARNING, "Unable to disconnect {0}: {1}", new Object[] { httpUrlConnection, t });
      }
    }
  }

  /**
   * Writes the contents of the {@code source} stream to the {@code destination} stream using the given
   * {@code bufferSize}.
   * 
   * @param source
   *          The source stream to copy from.
   * @param destination
   *          The destination stream to copy to.
   * @param bufferSize
   *          The size of the buffer to use during the copy operation.
   * @throws IOException
   *           If an error occurs when reading from {@code source} or writing to {@code destination}.
   * @throws NullPointerException
   *           If either {@code source} or @{code destination} is {@code null}.
   */
  protected void write(InputStream source, OutputStream destination, int bufferSize) throws IOException {
    if (source == null || destination == null) {
      throw new NullPointerException("Must provide non-null source and destination streams.");
    }

    int read;
    byte[] chunk = new byte[bufferSize];
    while ((read = source.read(chunk)) > 0)
      destination.write(chunk, 0, read);
  }

  /**
   * Creates the form field name for the binary attachment filename by stripping off the file extension - for example,
   * the filename "test.png" would return "test".
   * 
   * @param binaryAttachment
   *          The binary attachment for which to create the form field name.
   * @return The form field name for the given binary attachment.
   */
  protected String createFormFieldName(BinaryAttachment binaryAttachment) {
    String name = binaryAttachment.getFilename();
    int fileExtensionIndex = name.lastIndexOf(".");
    return fileExtensionIndex > 0 ? name.substring(0, fileExtensionIndex) : name;
  }

  /**
   * returns if the binary attachment stream is closed automatically
   * 
   * @since 1.7.0
   * @return
   */
  public boolean isAutocloseBinaryAttachmentStream() {
    return autocloseBinaryAttachmentStream;
  }

  /**
   * define if the binary attachment stream is closed automatically after sending the content to facebook
   * 
   * @since 1.7.0
   * @param autocloseBinaryAttachmentStream
   */
  public void setAutocloseBinaryAttachmentStream(boolean autocloseBinaryAttachmentStream) {
    this.autocloseBinaryAttachmentStream = autocloseBinaryAttachmentStream;
  }

  @Override
  public Response executeDelete(String url) throws IOException {
    return execute(url, HttpMethod.DELETE);
  }

  private Response execute(String url, HttpMethod httpMethod) throws IOException {
    if (logger.isLoggable(FINE)) {
      logger.log(FINE, "Making a {0} request to {1}", new Object[] { httpMethod.name(), url });
    }

    HttpURLConnection httpUrlConnection = null;
    InputStream inputStream = null;

    try {
      httpUrlConnection = openConnection(new URL(url));
      httpUrlConnection.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);
      httpUrlConnection.setUseCaches(false);
      httpUrlConnection.setRequestMethod(httpMethod.name());

      // Allow subclasses to customize the connection if they'd like to - set
      // their own headers, timeouts, etc.
      customizeConnection(httpUrlConnection);

      httpUrlConnection.connect();

      if (logger.isLoggable(FINER)) {
        logger.log(FINER, "Response headers: {0}", httpUrlConnection.getHeaderFields());
      }

      Response response = fetchResponse(inputStream, httpUrlConnection);

      if (logger.isLoggable(FINE)) {
        logger.log(FINE, "Facebook responded with {0}", response);
      }

      return response;
    } finally {
      closeQuietly(httpUrlConnection);
    }
  }

  protected Response fetchResponse(InputStream inputStream, HttpURLConnection httpUrlConnection) throws IOException {
    try {
      inputStream =
          httpUrlConnection.getResponseCode() != HTTP_OK ? httpUrlConnection.getErrorStream() : httpUrlConnection
            .getInputStream();
    } catch (IOException e) {
      if (logger.isLoggable(WARNING)) {
        logger.warning(format("An error occurred while making a " + httpUrlConnection.getRequestMethod()
            + " request to %s: %s", httpUrlConnection.getURL(), e));
      }
    }

    return new Response(httpUrlConnection.getResponseCode(), fromInputStream(inputStream));
  }

}