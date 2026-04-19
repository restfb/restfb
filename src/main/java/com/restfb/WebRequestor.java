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
package com.restfb;

import static com.restfb.util.StringUtils.isBlank;
import static com.restfb.util.StringUtils.trimToEmpty;
import static java.lang.String.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

import com.restfb.types.FacebookReelAttachment;
import com.restfb.util.StringUtils;

/**
 * Specifies how a class that sends {@code HTTP} requests to the Facebook API endpoint must operate.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public interface WebRequestor {
  /**
   * Encapsulates an HTTP response body and status code.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   */
  class Response {
    /**
     * HTTP response status code (e.g. 200).
     */
    private final Integer statusCode;

    /**
     * HTTP response body as text.
     */
    private final String body;

    /**
     * HTTP headers returned by Facebook.
     */
    private final Map<String, List<String>> headers;

    private final AtomicReference<Optional<DebugHeaderInfo>> debugHeaderInfoRef = new AtomicReference<>();

    /**
     * Creates a response with the given HTTP status code and response body as text.
     * 
     * @param statusCode
     *          The HTTP status code of the response.
     * @param body
     *          The response body as text.
     */
    public Response(Integer statusCode, String body) {
      this(statusCode, body, null, null);
    }

    /**
     * Creates a response with the given HTTP status code, response body and debug header info.
     *
     * @param statusCode
     *          The HTTP status code of the response.
     * @param body
     *          The response body as text.
     * @param debugHeaderInfo
     *          debug info parsed from the headers (may be {@code null})
     */
    public Response(Integer statusCode, String body, DebugHeaderInfo debugHeaderInfo) {
      this(statusCode, body, debugHeaderInfo, null);
    }

    /**
     * Creates a response with status, body, debug info and headers.
     */
    public Response(Integer statusCode, String body, DebugHeaderInfo debugHeaderInfo, Map<String, List<String>> headers) {
      this.statusCode = statusCode;
      this.body = trimToEmpty(body);
      this.headers = headers == null ? null : Collections.unmodifiableMap(headers);
      if (debugHeaderInfo != null) {
        this.debugHeaderInfoRef.set(Optional.of(debugHeaderInfo));
      }
    }

    /**
     * Gets the HTTP status code.
     * 
     * @return The HTTP status code.
     */
    public Integer getStatusCode() {
      return statusCode;
    }

    /**
     * Gets the HTTP response body as text.
     * 
     * @return The HTTP response body as text.
     */
    public String getBody() {
      return body;
    }

    /**
     * Gets the debug header information parsed from the response headers.
     * 
     * @return debug header information, may be {@code null}
     */
    public DebugHeaderInfo getDebugHeaderInfo() {
      Optional<DebugHeaderInfo> cached = debugHeaderInfoRef.get();
      if (cached != null) {
        return cached.orElse(null);
      }

      if (headers == null || headers.isEmpty()) {
        return null;
      }

      DebugHeaderInfo built = buildDebugHeaderInfo(headers);
      Optional<DebugHeaderInfo> optional = Optional.ofNullable(built);
      if (!debugHeaderInfoRef.compareAndSet(null, optional)) {
        return debugHeaderInfoRef.get().orElse(null);
      }
      return optional.orElse(null);
    }

    /**
     * Gets the HTTP headers returned by Facebook.
     * 
     * @return unmodifiable header map or {@code null}
     */
    public Map<String, List<String>> getHeaders() {
      return headers;
    }

    private DebugHeaderInfo buildDebugHeaderInfo(Map<String, List<String>> responseHeaders) {
      String usedApiVersion = StringUtils.trimToEmpty(getHeaderValue(responseHeaders, "facebook-api-version"));
      Version usedVersion = Version.getVersionFromString(usedApiVersion);
      DebugHeaderInfo.DebugHeaderInfoFactory factory =
          DebugHeaderInfo.DebugHeaderInfoFactory.create().setVersion(usedVersion);

      Arrays.stream(FbHeaderField.values()).forEach(f -> f.apply(responseHeaders, factory));
      return factory.build();
    }

    private static String getHeaderValue(Map<String, List<String>> responseHeaders, String fieldName) {
      if (responseHeaders == null) {
        return "";
      }
      for (Map.Entry<String, List<String>> entry : responseHeaders.entrySet()) {
        if (entry.getKey() != null && entry.getKey().equalsIgnoreCase(fieldName)) {
          List<String> values = entry.getValue();
          if (values.isEmpty()) {
            return "";
          }
          return values.get(0);
        }
      }
      return "";
    }

    private enum FbHeaderField {
      X_FB_TRACE_ID("x-fb-trace-id", DebugHeaderInfo.DebugHeaderInfoFactory::setTraceId), //
      X_FB_REV("x-fb-rev", DebugHeaderInfo.DebugHeaderInfoFactory::setRev), //
      X_FB_DEBUG("x-fb-debug", DebugHeaderInfo.DebugHeaderInfoFactory::setDebug), //
      X_APP_USAGE("x-app-usage", DebugHeaderInfo.DebugHeaderInfoFactory::setAppUsage), //
      X_PAGE_USAGE("x-page-usage", DebugHeaderInfo.DebugHeaderInfoFactory::setPageUsage), //
      X_AD_ACCOUNT_USAGE("x-ad-account-usage", DebugHeaderInfo.DebugHeaderInfoFactory::setAdAccountUsage), //
      X_BUSINESS_USE_CASE_USAGE("x-business-use-case-usage",
          DebugHeaderInfo.DebugHeaderInfoFactory::setBusinessUseCaseUsage);

      private final String headerName;
      private final BiConsumer<DebugHeaderInfo.DebugHeaderInfoFactory, String> consumer;

      FbHeaderField(String headerName, BiConsumer<DebugHeaderInfo.DebugHeaderInfoFactory, String> consumer) {
        this.headerName = headerName;
        this.consumer = consumer;
      }

      void apply(Map<String, List<String>> responseHeaders, DebugHeaderInfo.DebugHeaderInfoFactory factory) {
        consumer.accept(factory, StringUtils.trimToEmpty(getHeaderValue(responseHeaders, headerName)));
      }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      if (isBlank(getBody())) {
        return format("HTTP status code %d and an empty response body.", getStatusCode());
      }
      return format("HTTP status code %d and response body: %s", getStatusCode(), getBody());
    }
  }

  /**
   * encapsulates the HTTP Request configuration
   */
  class Request {

    private final String url;

    private final Optional<String> headerAccessToken;

    private String parameters;

    private Body body;

    private List<BinaryAttachment> binaryAttachments;

    /**
     * Simple http request with url and a header access token
     * 
     * @param url
     *          the endpoint the request ist directed to
     * @param headerAccessToken
     *          the HTTP header access token (may be {@code null})
     */
    public Request(String url, String headerAccessToken) {
      this(url, headerAccessToken, null);
    }

    /**
     * Simple http request with url and a header access token
     *
     * @param url
     *          the endpoint the request ist directed to
     * @param headerAccessToken
     *          the HTTP header access token (may be {@code null})
     * @param parameters
     *          the query parameter string
     */
    public Request(String url, String headerAccessToken, String parameters) {
      this(url, headerAccessToken, parameters, null);
    }

    /**
     * Simple http request with url and a header access token
     *
     * @param url
     *          the endpoint the request ist directed to
     * @param headerAccessToken
     *          the HTTP header access token (may be {@code null})
     * @param parameters
     *          the query parameter string
     * @param attachments
     *          list of binary attachments
     */
    public Request(String url, String headerAccessToken, String parameters, List<BinaryAttachment> attachments) {
      this.url = url;
      this.headerAccessToken = Optional.ofNullable(headerAccessToken);
      this.parameters = parameters;
      setBinaryAttachments(attachments);
    }

    public String getUrl() {
      return url;
    }

    public String getHeaderAccessToken() {
      return headerAccessToken.orElse(null);
    }

    public boolean hasHeaderAccessToken() {
      return headerAccessToken.isPresent();
    }

    public String getParameters() {
      return parameters;
    }

    public List<BinaryAttachment> getBinaryAttachments() {
      return Optional.ofNullable(binaryAttachments).orElse(new ArrayList<>());
    }

    public void setBinaryAttachments(List<BinaryAttachment> binaryAttachments) {
      this.binaryAttachments = Optional.ofNullable(binaryAttachments).orElse(new ArrayList<>());
    }

    public String getFullUrl() {
      if (!StringUtils.isBlank(parameters)) {
        if (url != null && url.contains("?")) {
          return url + "&" + parameters;
        }
        return url + "?" + parameters;
      }
      return url;
    }

    @Override
    public String toString() {
      return format("Request to url %s with parameters %s. Header access token: %b", getUrl(), getParameters(),
        hasHeaderAccessToken());
    }

    public void setBody(Body body) {
      this.body = body;
    }

    public Body getBody() {
      return body;
    }

    public boolean hasBody() {
      return body != null;
    }

    public boolean isReelUpload() {
      List<BinaryAttachment> attachments = getBinaryAttachments();
      if (attachments.size() == 1) {
        return attachments.get(0).isFacebookReel();
      }

      return false;
    }

    public Optional<FacebookReelAttachment> getReel() {
      if (isReelUpload()) {
        return Optional.of((FacebookReelAttachment) getBinaryAttachments().get(0));
      }

      return Optional.empty();
    }
  }

  /**
   * Given a Facebook API endpoint URL, execute a {@code GET} against it.
   * 
   * @param request
   *          The request data for the {@code GET} request
   * @return HTTP response data.
   * @throws IOException
   *           If an error occurs while performing the {@code GET} operation.
   * @since 1.5
   */
  Response executeGet(Request request) throws IOException;

  /**
   * Given a Facebook API endpoint URL and parameter string, execute a {@code POST} to the endpoint URL.
   * 
   * @param request
   *          The request data used for the {@code POST} request.
   * @return HTTP response data.
   * @throws IOException
   *           If an error occurs while performing the {@code POST}.
   */
  Response executePost(Request request) throws IOException;

  /**
   * Given a Facebook API endpoint URL and parameter string, execute a {@code DELETE} to the endpoint URL.
   * 
   * @param request
   *          The request data used for the {@code DELETE} request.
   * @return HTTP response data.
   * @throws IOException
   *           If an error occurs while performing the {@code DELETE}.
   */
  Response executeDelete(Request request) throws IOException;

}
