/*
 * Copyright (c) 2010-2015 Norbert Bartels.
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

import com.restfb.DefaultWebRequestor.HttpMethod;
import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Fake Web Requestor for unit tests.
 */
public class FakeWebRequestor implements WebRequestor {

  private String savedUrl;

  private HttpMethod method;

  @Override
  public Response executeGet(String url) throws IOException {
    this.savedUrl = url;
    this.method = HttpMethod.GET;
    return new Response(HTTP_OK, url);
  }

  @Override
  public Response executePost(String url, String parameters) throws IOException {
    this.savedUrl = url;
    this.method = HttpMethod.POST;
    return new Response(HTTP_OK, url);
  }

  @Override
  public Response executePost(String url, String parameters, BinaryAttachment... binaryAttachments) throws IOException {
    this.savedUrl = url;
    this.method = HttpMethod.POST;
    return new Response(HTTP_OK, url);
  }

  @Override
  public Response executeDelete(String url) throws IOException {
    this.savedUrl = url;
    this.method = HttpMethod.DELETE;
    return new Response(HTTP_OK, url);
  }

  /**
   * get the used HTTP Method.
   * @return HTTP Method as String
   */
  public String getMethod() {
    return method.name();
  }

  /**
   * get the called url.
   * @return the called url as String
   */
  public String getSavedUrl() {
    return savedUrl;
  }
}
