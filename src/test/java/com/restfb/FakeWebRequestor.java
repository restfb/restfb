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

import static java.net.HttpURLConnection.HTTP_OK;

import com.restfb.DefaultWebRequestor.HttpMethod;

/**
 * Fake Web Requestor for unit tests.
 */
public class FakeWebRequestor implements WebRequestor {

  private String savedUrl;

  private HttpMethod method;

  private String parameters;

  private final Response predefinedResponse;

  private String accessToken;
  private Body body;

  public FakeWebRequestor() {
    this(null);
  }

  public FakeWebRequestor(Response predefinedResponse) {
    this.predefinedResponse = predefinedResponse;
  }

  @Override
  public Response executeGet(Request request) {
    this.savedUrl = request.getFullUrl();
    this.method = HttpMethod.GET;
    this.accessToken = request.getHeaderAccessToken();
    return createInternalResponse();
  }

  @Override
  public Response executePost(Request request) {
    this.savedUrl = request.getUrl();
    this.method = HttpMethod.POST;
    this.parameters = request.getParameters();
    this.accessToken = request.getHeaderAccessToken();
    this.body = request.getBody();
    return createInternalResponse();
  }

  @Override
  public Response executeDelete(Request request) {
    this.savedUrl = request.getFullUrl();
    this.method = HttpMethod.DELETE;
    this.accessToken = request.getHeaderAccessToken();
    return createInternalResponse();
  }

  private Response createInternalResponse() {
    if (predefinedResponse != null) {
      return predefinedResponse;
    } else {
      return new Response(HTTP_OK, getSavedUrl());
    }
  }

  @Override
  public DebugHeaderInfo getDebugHeaderInfo() {
    return null;
  }

  /**
   * get the used HTTP Method.
   * 
   * @return HTTP Method as String
   */
  public String getMethod() {
    return method.name();
  }

  /**
   * get the called url.
   * 
   * @return the called url as String
   */
  public String getSavedUrl() {
    return savedUrl;
  }

  /**
   * get the used parameters (query string)
   * 
   * @return the used parameter
   */
  public String getParameters() {
    return parameters;
  }

  public Body getBody() {
    return body;
  }
}
