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
package com.restfb;

import static com.restfb.logging.RestFBLogger.HTTP_LOGGER;
import static java.lang.String.format;

import com.restfb.util.StringUtils;
import com.restfb.util.UrlUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

  private Map<String, List<String>> currentHeaders;

  private DebugHeaderInfo debugHeaderInfo;

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

    if (HTTP_LOGGER.isDebugEnabled()) {
      HTTP_LOGGER.debug("Executing a POST to " + url + " with parameters "
          + (binaryAttachments.length > 0 ? "" : "(sent in request body): ") + UrlUtils.urlDecode(parameters)
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

          outputStream.write(stringBuilder.toString().getBytes(StringUtils.ENCODING_CHARSET));

          write(binaryAttachment.getData(), outputStream, MULTIPART_DEFAULT_BUFFER_SIZE);

          outputStream.write((MULTIPART_CARRIAGE_RETURN_AND_NEWLINE + MULTIPART_TWO_HYPHENS + MULTIPART_BOUNDARY
              + MULTIPART_TWO_HYPHENS + MULTIPART_CARRIAGE_RETURN_AND_NEWLINE).getBytes(StringUtils.ENCODING_CHARSET));
        }
      } else {
        outputStream.write(parameters.getBytes(StringUtils.ENCODING_CHARSET));
      }

      if (HTTP_LOGGER.isDebugEnabled()) {
        HTTP_LOGGER.debug(format("Response headers: %s", httpUrlConnection.getHeaderFields()));
      }

      fillHeaderAndDebugInfo(httpUrlConnection);

      try {
        inputStream = httpUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK
            ? httpUrlConnection.getErrorStream() : httpUrlConnection.getInputStream();
      } catch (IOException e) {
        HTTP_LOGGER.warn(format("An error occurred while POSTing to %s:", url), e);
      }

      return new Response(httpUrlConnection.getResponseCode(), StringUtils.fromInputStream(inputStream));
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
    } catch (Exception t) {
      HTTP_LOGGER.warn(format("Unable to close %s: ", closeable), t);
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
    } catch (Exception t) {
      HTTP_LOGGER.warn(format("Unable to disconnect %s: ", httpUrlConnection), t);
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
    int fileExtensionIndex = name.lastIndexOf('.');
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

  /**
   * access to the current response headers
   * 
   * @return the current reponse header map
   */
  public Map<String, List<String>> getCurrentHeaders() {
    return currentHeaders;
  }

  @Override
  public Response executeDelete(String url) throws IOException {
    return execute(url, HttpMethod.DELETE);
  }

  @Override
  public DebugHeaderInfo getDebugHeaderInfo() {
    return debugHeaderInfo;
  }

  private Response execute(String url, HttpMethod httpMethod) throws IOException {
    if (HTTP_LOGGER.isDebugEnabled()) {
      HTTP_LOGGER.debug(format("Making a %s request to %s", httpMethod.name(), url));
    }

    HttpURLConnection httpUrlConnection = null;

    try {
      httpUrlConnection = openConnection(new URL(url));
      httpUrlConnection.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);
      httpUrlConnection.setUseCaches(false);
      httpUrlConnection.setRequestMethod(httpMethod.name());

      // Allow subclasses to customize the connection if they'd like to - set
      // their own headers, timeouts, etc.
      customizeConnection(httpUrlConnection);

      httpUrlConnection.connect();

      if (HTTP_LOGGER.isTraceEnabled()) {
        HTTP_LOGGER.trace(format("Response headers: %s", httpUrlConnection.getHeaderFields()));
      }

      fillHeaderAndDebugInfo(httpUrlConnection);

      Response response = fetchResponse(httpUrlConnection);

      if (HTTP_LOGGER.isDebugEnabled()) {
        HTTP_LOGGER.debug(format("Facebook responded with %s", response));
      }

      return response;
    } finally {
      closeQuietly(httpUrlConnection);
    }
  }

  protected void fillHeaderAndDebugInfo(HttpURLConnection httpUrlConnection) {
    currentHeaders = Collections.unmodifiableMap(httpUrlConnection.getHeaderFields());

    String usedApiVersion = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("facebook-api-version"));
    if (HTTP_LOGGER.isDebugEnabled()) {
      HTTP_LOGGER.debug(format("Facebook used the API %s to answer your request", usedApiVersion));
    }

    String fbTraceId = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-fb-trace-id"));
    String fbRev = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-fb-rev"));
    String fbDebug = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-fb-debug"));
    String fbAppUsage = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-app-usage"));
    String fbPageUsage = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-page-usage"));
    Version usedVersion = Version.getVersionFromString(usedApiVersion);
    debugHeaderInfo = new DebugHeaderInfo(fbDebug, fbRev, fbTraceId, usedVersion, fbAppUsage, fbPageUsage);
  }

  protected Response fetchResponse(HttpURLConnection httpUrlConnection) throws IOException {
    InputStream inputStream = null;
    try {
      inputStream = httpUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK
          ? httpUrlConnection.getErrorStream() : httpUrlConnection.getInputStream();
    } catch (IOException e) {
      HTTP_LOGGER.warn(format("An error occurred while making a %s request to %s:",
        httpUrlConnection.getRequestMethod(), httpUrlConnection.getURL()), e);
    }

    return new Response(httpUrlConnection.getResponseCode(), StringUtils.fromInputStream(inputStream));
  }

}