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

import java.io.Serializable;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Carries the result of a Graph API call plus response metadata such as debug headers.
 *
 * @param <T>
 *          type of the mapped result
 */
public final class ApiResult<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private final T result;

  private final ResponseMetadata responseMetadata;

  private ApiResult(T result, ResponseMetadata responseMetadata) {
    this.result = result;
    this.responseMetadata = responseMetadata;
  }

  public static <T> ApiResult<T> withMetadata(T result, DebugHeaderInfo debugHeaderInfo,
      Map<String, List<String>> responseHeaders, Duration duration, String httpMethod, String requestUrl) {
    return withMetadata(result, ResponseMetadata.of(debugHeaderInfo, responseHeaders, duration, httpMethod, requestUrl));
  }

  public static <T> ApiResult<T> withMetadata(T result, ResponseMetadata responseMetadata) {
    return new ApiResult<>(result, responseMetadata);
  }

  public static <T> ApiResult<T> withoutMetadata(T result) {
    return new ApiResult<>(result, null);
  }

  public T getResult() {
    return result;
  }

  public ResponseMetadata getResponseMetadata() {
    return responseMetadata;
  }

  public DebugHeaderInfo getDebugHeaderInfo() {
    return Optional.ofNullable(responseMetadata).map(ResponseMetadata::getDebugHeaderInfo).orElse(null);
  }

  public Map<String, List<String>> getResponseHeaders() {
    return Optional.ofNullable(responseMetadata).map(ResponseMetadata::getResponseHeaders).orElse(null);
  }

  public Duration getDuration() {
    return Optional.ofNullable(responseMetadata).map(ResponseMetadata::getDuration).orElse(null);
  }

  public String getHttpMethod() {
    return Optional.ofNullable(responseMetadata).map(ResponseMetadata::getHttpMethod).orElse(null);
  }

  public String getRequestUrl() {
    return Optional.ofNullable(responseMetadata).map(ResponseMetadata::getRequestUrl).orElse(null);
  }
}
