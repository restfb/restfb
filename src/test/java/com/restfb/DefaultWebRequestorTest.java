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
package com.restfb;

import static com.restfb.testutils.RestfbAssertions.assertThat;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultWebRequestorTest {

  @Mock
  private HttpURLConnection mockUrlConnection;

  @Mock
  private OutputStream mockOutputStream;

  @Spy
  private DefaultWebRequestor requestor;

  private static final String exampleUrl = "http://www.example.org";

  @BeforeEach
  void setup() throws IOException {
    doReturn(mockUrlConnection).when(requestor).openConnection(any(URL.class));
  }

  @Test
  void checkGet_withAccessToken() throws IOException {
    String resultString = "This is just a simple Test";
    when(mockUrlConnection.getResponseCode()).thenReturn(200);
    InputStream stream = new ByteArrayInputStream(resultString.getBytes(StandardCharsets.UTF_8));
    when(mockUrlConnection.getInputStream()).thenReturn(stream);
    WebRequestor.Response response = requestor.executeGet(new WebRequestor.Request("http://www.example.org", "accesstoken"));

    verify(mockUrlConnection).setRequestProperty("Authorization", "Bearer accesstoken");
  }

  @Test
  void checkGet() throws IOException {
    String resultString = "This is just a simple Test";
    when(mockUrlConnection.getResponseCode()).thenReturn(200);
    InputStream stream = new ByteArrayInputStream(resultString.getBytes(StandardCharsets.UTF_8));
    when(mockUrlConnection.getInputStream()).thenReturn(stream);
    WebRequestor.Response response = requestor.executeGet(new WebRequestor.Request("http://www.example.org", null));

    // check the result
    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(resultString);

    // verify the calls
    verify(mockUrlConnection).setReadTimeout(180000);
    verify(mockUrlConnection).setUseCaches(false);
    verify(mockUrlConnection).setRequestMethod(DefaultWebRequestor.HttpMethod.GET.name());
    verify(mockUrlConnection, never()).setRequestProperty(eq("Authorization"), anyString());
    verify(mockUrlConnection).connect();
    verify(requestor).executeGet(any(WebRequestor.Request.class));
    verify(requestor, never()).executePost(any(WebRequestor.Request.class));
    verify(requestor).customizeConnection(mockUrlConnection);
    verify(requestor).fillHeaderAndDebugInfo(mockUrlConnection);
    verify(requestor).fetchResponse(mockUrlConnection);
  }

  @Test
  void checkPost_withAccessToken() throws IOException {
    when(mockUrlConnection.getOutputStream()).thenReturn(mockOutputStream);
    String resultString = "This is just a simple Test";
    when(mockUrlConnection.getResponseCode()).thenReturn(200);
    InputStream stream = new ByteArrayInputStream(resultString.getBytes(StandardCharsets.UTF_8));
    when(mockUrlConnection.getInputStream()).thenReturn(stream);
    WebRequestor.Response response = requestor.executePost(new WebRequestor.Request(exampleUrl, "accesstoken", ""));

    verify(mockUrlConnection).setRequestProperty("Authorization", "Bearer accesstoken");
  }

  @Test
  void checkPost_NoBinary() throws IOException {
    when(mockUrlConnection.getOutputStream()).thenReturn(mockOutputStream);
    String resultString = "This is just a simple Test";
    when(mockUrlConnection.getResponseCode()).thenReturn(200);
    InputStream stream = new ByteArrayInputStream(resultString.getBytes(StandardCharsets.UTF_8));
    when(mockUrlConnection.getInputStream()).thenReturn(stream);
    WebRequestor.Request request = new WebRequestor.Request(exampleUrl, null, "");
    WebRequestor.Response response = requestor.executePost(request);

    // check the result
    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(resultString);

    // verify the calls
    verify(mockUrlConnection).setReadTimeout(180000);
    verify(mockUrlConnection).setUseCaches(false);
    verify(mockUrlConnection).setDoOutput(true);
    verify(mockUrlConnection).setRequestMethod(DefaultWebRequestor.HttpMethod.POST.name());
    verify(mockUrlConnection, never()).setRequestProperty(eq("Authorization"), anyString());
    verify(mockUrlConnection).connect();
    verify(requestor).executePost(eq(request));
    verify(requestor, never()).executeGet(any(WebRequestor.Request.class));
    verify(requestor).customizeConnection(mockUrlConnection);
    verify(requestor).fillHeaderAndDebugInfo(mockUrlConnection);
    verify(requestor).fetchResponse(mockUrlConnection);
  }

  @Test
  void checkPost_WithBinary() throws IOException {
    when(mockUrlConnection.getOutputStream()).thenReturn(mockOutputStream);
    BinaryAttachment mockAttachment = mock(BinaryAttachment.class);
    InputStream mockBinaryInputStream = mock(InputStream.class);
    when(mockAttachment.getFilename()).thenReturn("exampleFile.png");
    when(mockAttachment.getData()).thenReturn(mockBinaryInputStream);

    String resultString = "This is just a simple Test";
    when(mockUrlConnection.getResponseCode()).thenReturn(200);
    InputStream stream = new ByteArrayInputStream(resultString.getBytes(StandardCharsets.UTF_8));
    when(mockUrlConnection.getInputStream()).thenReturn(stream);
    WebRequestor.Request request = new WebRequestor.Request(exampleUrl, null, "", Collections.singletonList(mockAttachment));
    WebRequestor.Response response = requestor.executePost(request);

    // check the result
    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(resultString);

    // verify the calls
    verify(mockUrlConnection).setReadTimeout(180000);
    verify(mockUrlConnection).setUseCaches(false);
    verify(mockUrlConnection).setDoOutput(true);
    verify(mockUrlConnection).setRequestProperty("Connection", "Keep-Alive");
    verify(mockUrlConnection, never()).setRequestProperty(eq("Authorization"), anyString());
    verify(mockUrlConnection).setRequestMethod(DefaultWebRequestor.HttpMethod.POST.name());
    verify(mockUrlConnection).connect();
    verify(requestor).executePost(eq(request));
    verify(requestor, never()).executeGet(any(WebRequestor.Request.class));
    verify(requestor).customizeConnection(mockUrlConnection);
    verify(requestor).fillHeaderAndDebugInfo(mockUrlConnection);
    verify(requestor).fetchResponse(mockUrlConnection);
    verify(requestor).write(mockBinaryInputStream, mockOutputStream, 8192);
    verify(requestor).closeQuietly(mockUrlConnection);
    verify(requestor).closeQuietly(mockOutputStream);
    verify(requestor).closeQuietly(mockBinaryInputStream);
  }
}
