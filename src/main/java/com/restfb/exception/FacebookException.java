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
import java.util.StringJoiner;

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
   * Aggregates optional metadata that may help diagnose a failing Facebook request.
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
      this.fullEndpoint = fullEndpoint;
      this.parameterString = parameterString;
      this.headerAccessToken = headerAccessToken;
      this.startTime = startTime;
      this.endTime = System.currentTimeMillis();
    }

    public String getHttpMethod() {
      return httpMethod;
    }

    public String getFullEndpoint() {
      return fullEndpoint;
    }

    public String getParameterString() {
      return parameterString;
    }

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
      String parameterStringToReturn;
      try {
        parameterStringToReturn = URLDecoder.decode(parameterString, StandardCharsets.UTF_8);
      } catch (Exception e) {
        parameterStringToReturn = parameterString;
      }
      String endpointToReturn;
      try {
        endpointToReturn = URLDecoder.decode(fullEndpoint, StandardCharsets.UTF_8);
      } catch (Exception e) {
        endpointToReturn = fullEndpoint;
      }
      return endpointToReturn + (parameterString != null ? "?" + parameterStringToReturn : "");
    }

    @Override
    public String toString() {
      return "URL: " + httpMethod + ": " + getUrl() +
              (headerAccessToken != null ? " headerAccessToken: " + headerAccessToken : "")
              + ", response-time: " + getDurationAsString();
    }

  }
}
