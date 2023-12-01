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
package com.restfb.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FakeWebRequestor;
import com.restfb.Version;

class EndpointBuilderTest {

  @Test
  void deleteObjectDELETETest() {
    FakeWebRequestor wr = new FakeWebRequestor();
    DefaultFacebookClient client = new DefaultFacebookClient("12345", wr, new DefaultJsonMapper(), Version.VERSION_9_0);
    client.deleteObject("comment");
    assertThat(wr.getMethod()).isEqualTo("DELETE");
    assertThat(wr.getSavedUrl()).isEqualTo("https://graph.facebook.com/v9.0/comment?access_token=12345&format=json");
  }

  @Test
  void deleteObjectPOSTTest() {
    FakeWebRequestor wr = new FakeWebRequestor();
    DefaultFacebookClient client = new DefaultFacebookClient("12345", wr, new DefaultJsonMapper(), Version.VERSION_9_0);
    client.setHttpDeleteFallback(true);
    client.deleteObject("comment");
    assertThat(wr.getMethod()).isEqualTo("POST");
    assertThat(wr.getSavedUrl()).isEqualTo("https://graph.facebook.com/v9.0/comment");
  }
}