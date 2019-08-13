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
package com.restfb;

import static com.restfb.logging.RestFBLogger.HTTP_LOGGER;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.restfb.util.StringUtils;
import com.restfb.util.UrlUtils;

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

  @Override
  public Response executeGet(String url) throws IOException {
    return execute(url, HttpMethod.GET);
  }

  @Override
  public Response executePost(String url, String parameters) throws IOException {
    return executePost(url, parameters, null);
  }

  @Override
  public Response executePost(String url, String parameters, List<BinaryAttachment> binaryAttachments)
      throws IOException {

    binaryAttachments = Optional.ofNullable(binaryAttachments).orElse(new ArrayList<>());

    if (HTTP_LOGGER.isDebugEnabled()) {
      HTTP_LOGGER.debug("Executing a POST to " + url + " with parameters "
          + (!binaryAttachments.isEmpty() ? "" : "(sent in request body): ") + UrlUtils.urlDecode(parameters)
          + (!binaryAttachments.isEmpty() ? " and " + binaryAttachments.size() + " binary attachment[s]." : ""));
    }

    HttpURLConnection httpUrlConnection = null;
    OutputStream outputStream = null;

    try {
      httpUrlConnection = openConnection(new URL(url + (!binaryAttachments.isEmpty() ? "?" + parameters : "")));
      httpUrlConnection.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);

      // Allow subclasses to customize the connection if they'd like to - set
      // their own headers, timeouts, etc.
      customizeConnection(httpUrlConnection);

      httpUrlConnection.setRequestMethod(HttpMethod.POST.name());
      httpUrlConnection.setDoOutput(true);
      httpUrlConnection.setUseCaches(false);

      if (!binaryAttachments.isEmpty()) {
        httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
        httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + MULTIPART_BOUNDARY);
      }

      httpUrlConnection.connect();
      outputStream = httpUrlConnection.getOutputStream();

      // If we have binary attachments, the body is just the attachments and the
      // other parameters are passed in via the URL.
      // Otherwise the body is the URL parameter string.
      if (!binaryAttachments.isEmpty()) {
        for (BinaryAttachment binaryAttachment : binaryAttachments) {
          StringBuilder stringBuilder = new StringBuilder();

          stringBuilder.append(MULTIPART_TWO_HYPHENS).append(MULTIPART_BOUNDARY)
            .append(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE).append("Content-Disposition: form-data; name=\"")
            .append(createFormFieldName(binaryAttachment)).append("\"; filename=\"")
            .append(binaryAttachment.getFilename()).append("\"");

          stringBuilder.append(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE).append("Content-Type: ")
            .append(binaryAttachment.getContentType());

          stringBuilder.append(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE).append(MULTIPART_CARRIAGE_RETURN_AND_NEWLINE);

          outputStream.write(stringBuilder.toString().getBytes(StringUtils.ENCODING_CHARSET));

          write(binaryAttachment.getData(), outputStream, MULTIPART_DEFAULT_BUFFER_SIZE);

          outputStream.write((MULTIPART_CARRIAGE_RETURN_AND_NEWLINE + MULTIPART_TWO_HYPHENS + MULTIPART_BOUNDARY
              + MULTIPART_TWO_HYPHENS + MULTIPART_CARRIAGE_RETURN_AND_NEWLINE).getBytes(StringUtils.ENCODING_CHARSET));
        }
      } else {
        outputStream.write(parameters.getBytes(StringUtils.ENCODING_CHARSET));
      }

      HTTP_LOGGER.debug("Response headers: {}", httpUrlConnection.getHeaderFields());

      fillHeaderAndDebugInfo(httpUrlConnection);

      Response response = fetchResponse(httpUrlConnection);

      HTTP_LOGGER.debug("Facebook responded with {}", response);
      return response;
    } finally {
      if (autocloseBinaryAttachmentStream && !binaryAttachments.isEmpty()) {
        binaryAttachments.forEach(binaryAttachment -> closeQuietly(binaryAttachment.getData()));
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
  protected void customizeConnection(HttpURLConnection connection) {
    // This implementation is a no-op
  }

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
      HTTP_LOGGER.warn("Unable to close {}: ", closeable, t);
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
      HTTP_LOGGER.warn("Unable to disconnect {}: ", httpUrlConnection, t);
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
      throw new IllegalArgumentException("Must provide non-null source and destination streams.");
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
    if (binaryAttachment.getFieldName() != null) {
      return binaryAttachment.getFieldName();
    }

    String name = binaryAttachment.getFilename();
    int fileExtensionIndex = name.lastIndexOf('.');
    return fileExtensionIndex > 0 ? name.substring(0, fileExtensionIndex) : name;
  }

  /**
   * returns if the binary attachment stream is closed automatically
   * 
   * @since 1.7.0
   * @return {@code true} if the binary stream should be closed automatically, {@code false} otherwise
   */
  public boolean isAutocloseBinaryAttachmentStream() {
    return autocloseBinaryAttachmentStream;
  }

  /**
   * define if the binary attachment stream is closed automatically after sending the content to facebook
   * 
   * @since 1.7.0
   * @param autocloseBinaryAttachmentStream
   *          {@code true} if the {@link BinaryAttachment} stream should be closed automatically, {@code false}
   *          otherwise
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
    HTTP_LOGGER.debug("Making a {} request to {}", httpMethod.name(), url);

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

      HTTP_LOGGER.trace("Response headers: {}", httpUrlConnection.getHeaderFields());

      fillHeaderAndDebugInfo(httpUrlConnection);

      Response response = fetchResponse(httpUrlConnection);

      HTTP_LOGGER.debug("Facebook responded with {}", response);
      return response;
    } finally {
      closeQuietly(httpUrlConnection);
    }
  }

  protected void fillHeaderAndDebugInfo(HttpURLConnection httpUrlConnection) {
    currentHeaders = Collections.unmodifiableMap(httpUrlConnection.getHeaderFields());

    String usedApiVersion = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("facebook-api-version"));
    HTTP_LOGGER.debug("Facebook used the API {} to answer your request", usedApiVersion);

    String fbTraceId = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-fb-trace-id"));
    String fbRev = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-fb-rev"));
    String fbDebug = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-fb-debug"));
    String fbAppUsage = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-app-usage"));
    String fbPageUsage = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-page-usage"));
    String fbAdAccountUsage = StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-ad-account-usage"));
    String fbBusinessUseCaseUsage =
        StringUtils.trimToEmpty(httpUrlConnection.getHeaderField("x-business-use-case-usage"));

    Version usedVersion = Version.getVersionFromString(usedApiVersion);
    debugHeaderInfo = DebugHeaderInfo.DebugHeaderInfoFactory.create().setVersion(usedVersion) // set the version
      .setTraceId(fbTraceId) // set the Trace ID
      .setDebug(fbDebug) // set the debug id
      .setRev(fbRev) // set the rev field
      .setAppUsage(fbAppUsage) // set the app usage
      .setPageUsage(fbPageUsage) // set the page usage
      .setAdAccountUsage(fbAdAccountUsage) // set the ad account usage
      .setBusinessUseCaseUsage(fbBusinessUseCaseUsage) // set the business use case Usage
      .build();
  }

  protected Response fetchResponse(HttpURLConnection httpUrlConnection) throws IOException {
    InputStream inputStream = null;
    try {
      inputStream =
          httpUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK ? httpUrlConnection.getErrorStream()
              : httpUrlConnection.getInputStream();
    } catch (IOException e) {
      HTTP_LOGGER.warn("An error occurred while making a {} request to {}:", httpUrlConnection.getRequestMethod(),
        httpUrlConnection.getURL(), e);
    }

    return new Response(httpUrlConnection.getResponseCode(), StringUtils.fromInputStream(inputStream));
  }

}