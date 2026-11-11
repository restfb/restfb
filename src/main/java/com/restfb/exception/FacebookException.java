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
package com.restfb.exception;

import java.io.Serializable;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

import com.restfb.util.UrlUtils;

/**
 * Root of the RestFB exception hierarchy.
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author <a href="Http://restfb.com">Norbert Bartels</a>
 */
public abstract class FacebookException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private InfoData infoData;

  /**
   * Creates an exception with the given message.
   *
   * @param message
   *          A message describing this exception.
   */
  protected FacebookException(String message) {
    super(message);
  }

  /**
   * Creates an exception with the given message and cause.
   *
   * @param message
   *          A message describing this exception.
   * @param cause
   *          The exception that caused this exception to be thrown.
   */
  protected FacebookException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Adds optional request metadata to the exception for diagnostics.
   *
   * @param infoData
   *          encapsulated request metadata (may be {@code null})
   * @return this exception for fluent usage.
   */
  public FacebookException withInfoData(InfoData infoData) {
    this.infoData = infoData;
    return this;
  }

  /**
   * Convenience helper to attach request metadata.
   *
   * @return this exception for fluent usage.
   */
  public FacebookException withInfoData(String httpMethod, String fullEndpoint, String parameterString,
      String headerAccessToken, Long startTime) {
    return withInfoData(new InfoData(httpMethod, fullEndpoint, parameterString, headerAccessToken, startTime));
  }

  @Override
  public String getMessage() {
    return super.getMessage() + getInfoData().map(s -> ", " + s).orElse("");
  }

  public String getBasicMessage() {
    return super.getMessage();
  }

  /**
   * Request metadata associated with this exception, if available.
   *
   * @return optional request metadata
   */
  public Optional<InfoData> getInfoData() {
    return Optional.ofNullable(infoData);
  }

  /**
   * Aggregates optional metadata that may help diagnose a failing Facebook request. Authentication parameters are
   * redacted before storage, including in getter and serialized output. The request itself is not modified. Arbitrary
   * exception messages, causes and application data are not sanitized here.
   */
  public static final class InfoData implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String httpMethod;
    private final String fullEndpoint;
    private final String parameterString;
    private final String headerAccessToken;
    private final Long startTime;
    private final long endTime;

    public InfoData(String httpMethod, String fullEndpoint, String parameterString, String headerAccessToken,
        Long startTime) {
      this.httpMethod = httpMethod;
      this.fullEndpoint = UrlUtils.redactSensitiveUrl(fullEndpoint);
      this.parameterString = UrlUtils.redactSensitiveParameters(parameterString);
      this.headerAccessToken = headerAccessToken == null ? null : UrlUtils.REDACTED_VALUE;
      this.startTime = startTime;
      this.endTime = System.currentTimeMillis();
    }

    public String getHttpMethod() {
      return httpMethod;
    }

    /**
     * @return the endpoint with authentication query parameters redacted
     */
    public String getFullEndpoint() {
      return fullEndpoint;
    }

    /**
     * @return URL-encoded diagnostic parameters with authentication values redacted, or {@code null}
     */
    public String getParameterString() {
      return parameterString;
    }

    /**
     * @return {@code [REDACTED]} if a header token was supplied, otherwise {@code null}; never the original token
     */
    public String getHeaderAccessToken() {
      return headerAccessToken;
    }

    public Duration getDuration() {
      if (startTime == null) {
        return Duration.ZERO;
      }
      return Duration.ofMillis(Math.max(0, endTime - startTime));
    }

    public String getDurationAsString() {
      return startTime == null ? "unknown" : getDuration().toMillis() + "ms";
    }

    public String getUrl() {
      String endpoint = String.valueOf(fullEndpoint);
      if (parameterString != null && !parameterString.isEmpty()) {
        int fragmentIndex = endpoint.indexOf('#');
        String fragment = fragmentIndex < 0 ? "" : endpoint.substring(fragmentIndex);
        endpoint = fragmentIndex < 0 ? endpoint : endpoint.substring(0, fragmentIndex);
        String separator = "?";
        if (endpoint.contains("?")) {
          separator = endpoint.endsWith("?") || endpoint.endsWith("&") ? "" : "&";
        }
        endpoint += separator + parameterString + fragment;
      }
      try {
        return URLDecoder.decode(endpoint, StandardCharsets.UTF_8);
      } catch (IllegalArgumentException e) {
        // The stored URL is already redacted, even if its path cannot be decoded.
        return endpoint;
      }
    }

    @Override
    public String toString() {
      return "URL: " + httpMethod + ": " + getUrl()
          + (headerAccessToken != null ? " headerAccessToken: " + headerAccessToken : "") + ", response-time: "
          + getDurationAsString();
    }

  }
}
