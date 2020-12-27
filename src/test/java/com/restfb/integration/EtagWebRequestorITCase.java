/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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
package com.restfb.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.restfb.WebRequestor;
import org.junit.jupiter.api.Test;

import com.restfb.ETagWebRequestor;
import com.restfb.WebRequestor.Response;
import com.restfb.integration.base.RestFbIntegrationTestBase;

class EtagWebRequestorITCase extends RestFbIntegrationTestBase {

  @Test
  void fetchMeWithCache() throws IOException {
    String requestUrl = "https://graph.facebook.com/v2.5/me?format=json&access_token=";
    requestUrl += getTestSettings().getUserAccessToken();
    ETagWebRequestor webRequest = new ETagWebRequestor();
    Response resp1 = webRequest.executeGet(new WebRequestor.Request(requestUrl, null));
    assertNotNull(resp1);
    assertEquals(HttpURLConnection.HTTP_OK, (int) resp1.getStatusCode());
    Response resp2 = webRequest.executeGet(new WebRequestor.Request(requestUrl, null));
    assertNotNull(resp2);
    assertEquals(HttpURLConnection.HTTP_NOT_MODIFIED, (int) resp2.getStatusCode());
    assertEquals(resp1.getBody(), resp2.getBody());
  }

  @Test
  void fetchMeWithoutCache() throws IOException {
    String requestUrl = "https://graph.facebook.com/v2.5/me?format=json&access_token=";
    requestUrl += getTestSettings().getUserAccessToken();
    ETagWebRequestor webRequest = new ETagWebRequestor();
    webRequest.setUseCache(false);
    Response resp1 = webRequest.executeGet(new WebRequestor.Request(requestUrl, null));
    assertNotNull(resp1);
    assertEquals(HttpURLConnection.HTTP_OK, (int) resp1.getStatusCode());
    Response resp2 = webRequest.executeGet(new WebRequestor.Request(requestUrl, null));
    assertNotNull(resp2);
    assertEquals(HttpURLConnection.HTTP_OK, (int) resp2.getStatusCode());
    assertEquals(resp1.getBody(), resp2.getBody());
  }

}
