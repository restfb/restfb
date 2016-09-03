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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class WebRequestorTest {

  @Test
  public void enumChecker() {
    assertEquals("GET", DefaultWebRequestor.HttpMethod.GET.name());
    assertEquals("DELETE", DefaultWebRequestor.HttpMethod.DELETE.name());
  }

  @Test
  public void testFillHeaderAndDebugInfo() {

    String appUsage = "{ 'call_count': 10, 'total_time': 20, 'total_cputime': 30 }";
    String pageUsage = "{ 'call_count': 20, 'total_time': 40, 'total_cputime': 60 }";

    final Map<String, String> headerFields = new HashMap<String, String>();
    headerFields.put("x-app-usage", appUsage);
    headerFields.put("x-page-usage", pageUsage);

    HttpURLConnection connection = new HttpURLConnection(null) {
      @Override
      public void disconnect() {
      }

      @Override
      public boolean usingProxy() {
        return false;
      }

      @Override
      public void connect() throws IOException {
      }

      @Override
      public String getHeaderField(String name) {
        return headerFields.get(name);
      }
    };

    DefaultWebRequestor defaultWebRequestor = new DefaultWebRequestor();
    defaultWebRequestor.fillHeaderAndDebugInfo(connection);

    DebugHeaderInfo debugHeaderInfo = defaultWebRequestor.getDebugHeaderInfo();

    assertEquals(appUsage, debugHeaderInfo.getAppUsage());
    assertEquals(pageUsage, debugHeaderInfo.getPageUsage());
  }
}
