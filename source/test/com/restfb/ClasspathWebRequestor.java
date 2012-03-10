/*
 * Copyright (c) 2010-2012 Mark Allen.
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

import static com.restfb.util.StringUtils.fromInputStream;
import static java.net.HttpURLConnection.HTTP_OK;

import java.io.IOException;

/**
 * {@link WebRequestor} implementation that loads a file from the classpath
 * instead of hitting the web. Useful for running unit tests against local JSON
 * textfiles.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class ClasspathWebRequestor implements WebRequestor {
  /**
   * Cached response.
   */
  private Response response;

  /**
   * Creates a web requestor that loads a file from the classpath instead of
   * hitting the web.
   * 
   * @param pathToJson
   *          The classpath location of the JSON file to load.
   * @throws IOException
   *           If an error occurs while processing the classpath JSON file.
   */
  public ClasspathWebRequestor(String pathToJson) throws IOException {
    // Cache off the response immediately instead of recreating it every time in
    // executePost().
    response = new Response(HTTP_OK, fromInputStream(ClasspathWebRequestor.class.getResourceAsStream(pathToJson)));
  }

  /**
   * @see com.restfb.WebRequestor#executePost(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public Response executePost(String url, String parameters) throws IOException {
    return response;
  }

  /**
   * @see com.restfb.WebRequestor#executeGet(java.lang.String)
   */
  @Override
  public Response executeGet(String url) throws IOException {
    return response;
  }

  /**
   * @see com.restfb.WebRequestor#executePost(java.lang.String,
   *      java.lang.String, com.restfb.BinaryAttachment[])
   */
  @Override
  public Response executePost(String url, String parameters, BinaryAttachment... binaryAttachments) throws IOException {
    return response;
  }
}