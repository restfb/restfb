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

import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;

import com.restfb.util.SoftHashMap;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.Map;

/**
 * WebRequestor with ETag-support.
 *
 * <p>
 * The {@see ETagWebRequestor} caches all <tt>GET</tt>-requests with an ETag header field in a {@see SoftHashMap} and
 * uses the ETag on the next request as <code>If-None-Match</code> header field if the same URL is requested.
 * </p>
 *
 * <p>
 * Is the response status code 304 (NOT MODIFIED) the old response from cache is used.
 * </p>
 *
 * <p>
 * <strong>Attention:</strong> even 304 responses count as request at Facebook and so they count against the throttling
 * limits. Facebook suggests to use them for data that change only frequently
 * </p>
 *
 * <p>
 * Further information regarding ETag at Facebook can be found here:
 * <a href="https://developers.facebook.com/blog/post/627/">https://developers.facebook.com/blog/post/627/</a>
 * </p>
 *
 * <p>
 * <strong>Attention 2</strong>: If excessively used with a lot of URLs, the {@see SoftHashMap} can lead to a
 * performance degradation
 * </p>
 */
public class ETagWebRequestor extends DefaultWebRequestor {

  private final Map<String, ETagResponse> etagCache =
      Collections.synchronizedMap(new SoftHashMap<String, ETagResponse>());
  private final ThreadLocal<ETagResponse> currentETagRespThreadLocal = new ThreadLocal<ETagResponse>();
  private volatile boolean useCache = true;

  @Override
  protected void customizeConnection(HttpURLConnection connection) {
    if (isUseCache() && connection.getRequestMethod().equals(HttpMethod.GET.name())) {
      ETagResponse resp = etagCache.get(connection.getURL().toString());
      if (resp != null) {
        currentETagRespThreadLocal.set(resp);
        connection.addRequestProperty("If-None-Match", resp.getEtag());
      }
    }
  }

  @Override
  protected Response fetchResponse(HttpURLConnection httpUrlConnection) throws IOException {
    try {
      if (httpUrlConnection.getRequestMethod().equals(HttpMethod.GET.name())) {
        if (httpUrlConnection.getResponseCode() == HTTP_NOT_MODIFIED && currentETagRespThreadLocal.get() != null) {
          ETagResponse etagResp = currentETagRespThreadLocal.get();
          return new Response(httpUrlConnection.getResponseCode(), etagResp.getBody());
        } else {
          Response resp = super.fetchResponse(httpUrlConnection);
          if (httpUrlConnection.getHeaderField("ETag") != null) {
            etagCache.put(httpUrlConnection.getURL().toString(),
              new ETagResponse(httpUrlConnection.getHeaderField("ETag"), resp.getBody()));
          }
          return resp;
        }
      } else {
        return super.fetchResponse(httpUrlConnection);
      }
    } finally {
      currentETagRespThreadLocal.remove();
    }
  }

  /**
   * return if cache is used.
   * 
   * @return <code>true</code> if ETag-Cache is used, <code>false</code> if not
   */
  public boolean isUseCache() {
    return this.useCache;
  }

  /**
   * activate/deactivate the ETag-Cache for the next request.
   *
   * <p>
   * when deactivated, the ETag-Cache is *not* deleted
   * </p>
   *
   * @param useCache
   */
  public void setUseCache(boolean useCache) {
    this.useCache = useCache;
  }

  private class ETagResponse {

    public ETagResponse(String etag, String body) {
      this.etag = etag;
      this.body = body;
    }

    private final String etag;
    private final String body;

    public String getEtag() {
      return etag;
    }

    public String getBody() {
      return body;
    }
  }

}
