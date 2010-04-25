/*
 * Copyright (c) 2010 Mark Allen.
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

import static com.restfb.StringUtils.ENCODING_CHARSET;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * Default implementation of a service that sends HTTP {@code POST}s to the
 * Facebook API endpoint.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class DefaultWebRequestor implements WebRequestor {
  /**
   * By default, how long should we wait for a response (in ms)?
   */
  private static final int DEFAULT_READ_TIMEOUT_IN_MS = 20000;

  private static final Logger logger =
      Logger.getLogger(DefaultWebRequestor.class);

  /**
   * @see com.restfb.WebRequestor#executeGet(java.lang.String)
   */
  @Override
  public Response executeGet(String url) throws IOException {
    if (logger.isInfoEnabled())
      logger.info("Making a GET request to " + url);

    HttpURLConnection httpUrlConnection = null;
    InputStream inputStream = null;

    try {
      httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
      httpUrlConnection.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);

      // Allow subclasses to customize the connection if they'd like to - set
      // their own headers, timeouts, etc.
      customizeConnection(httpUrlConnection);

      httpUrlConnection.setRequestMethod("GET");
      httpUrlConnection.connect();

      try {
        inputStream = httpUrlConnection.getInputStream();
      } catch (IOException e) {
        logger
          .warn("An error occurred while making a GET request to " + url, e);
      }

      return new Response(httpUrlConnection.getResponseCode(), StringUtils
        .fromInputStream(inputStream));
    } finally {
      closeQuietly(httpUrlConnection);
    }
  }

  /**
   * @see com.restfb.WebRequestor#executePost(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public Response executePost(String url, String parameters) throws IOException {
    if (logger.isInfoEnabled())
      logger.info("Executing a POST to " + url
          + " with parameters (sent in request body): " + parameters);

    HttpURLConnection httpUrlConnection = null;
    OutputStream outputStream = null;
    InputStream inputStream = null;

    try {
      httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
      httpUrlConnection.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);

      // Allow subclasses to customize the connection if they'd like to - set
      // their own headers, timeouts, etc.
      customizeConnection(httpUrlConnection);

      httpUrlConnection.setRequestMethod("POST");
      httpUrlConnection.setDoOutput(true);
      httpUrlConnection.connect();
      outputStream = httpUrlConnection.getOutputStream();
      outputStream.write(parameters.getBytes(ENCODING_CHARSET));

      try {
        inputStream = httpUrlConnection.getInputStream();
      } catch (IOException e) {
        logger.warn("An error occurred while POSTing to " + url, e);
      }

      return new Response(httpUrlConnection.getResponseCode(), StringUtils
        .fromInputStream(inputStream));
    } finally {
      closeQuietly(outputStream);
      closeQuietly(httpUrlConnection);
    }
  }

  /**
   * Hook method which allows subclasses to easily customize the {@code
   * connection}s created by {@link #executeGet(String)} and
   * {@link #executePost(String, String)} - for example, setting a custom read
   * timeout or request header.
   * 
   * This implementation is a no-op.
   * 
   * @param connection
   *          The connection to customize.
   */
  protected void customizeConnection(HttpURLConnection connection) {}

  /**
   * Attempts to cleanly close a resource, swallowing any exceptions that might
   * occur since there's no way to recover anyway.
   * <p>
   * It's OK to pass {@code null} in, this method will no-op in that case.
   * 
   * @param closeable
   *          The resource to close.
   */
  protected void closeQuietly(Closeable closeable) {
    if (closeable == null)
      return;
    try {
      closeable.close();
    } catch (Throwable t) {
      logger.warn("Unable to close " + closeable + ": ", t);
    }
  }

  /**
   * Attempts to cleanly close an {@code HttpURLConnection}, swallowing any
   * exceptions that might occur since there's no way to recover anyway.
   * <p>
   * It's OK to pass {@code null} in, this method will no-op in that case.
   * 
   * @param httpUrlConnection
   *          The connection to close.
   */
  protected void closeQuietly(HttpURLConnection httpUrlConnection) {
    if (httpUrlConnection == null)
      return;
    try {
      httpUrlConnection.disconnect();
    } catch (Throwable t) {
      logger.warn("Unable to disconnect " + httpUrlConnection + ": ", t);
    }
  }
}