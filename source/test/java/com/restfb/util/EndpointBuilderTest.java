/*
 * Copyright (c) 2010-2015 Norbert Bartels.
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

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FakeWebRequestor;
import com.restfb.Version;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EndpointBuilderTest {

  @Test
  public void version2Test() {
    FakeWebRequestor wr = new FakeWebRequestor();
    DefaultFacebookClient client = new DefaultFacebookClient("12345", wr, new DefaultJsonMapper(), Version.VERSION_2_0);
    String respstring = client.fetchObject("/me", String.class);
    assertEquals("https://graph.facebook.com/v2.0/me?access_token=12345&format=json", respstring);
  }

  @Test
  public void version21Test() {
    FakeWebRequestor wr = new FakeWebRequestor();
    DefaultFacebookClient client = new DefaultFacebookClient("12345", wr, new DefaultJsonMapper(), Version.VERSION_2_1);
    String respstring = client.fetchObject("/me", String.class);
    assertEquals("https://graph.facebook.com/v2.1/me?access_token=12345&format=json", respstring);
  }

  @Test
  public void version22Test() {
    FakeWebRequestor wr = new FakeWebRequestor();
    DefaultFacebookClient client = new DefaultFacebookClient("12345", wr, new DefaultJsonMapper(), Version.VERSION_2_2);
    String respstring = client.fetchObject("/me", String.class);
    assertEquals("https://graph.facebook.com/v2.2/me?access_token=12345&format=json", respstring);
  }

  @Test
  public void version23Test() {
    FakeWebRequestor wr = new FakeWebRequestor();
    DefaultFacebookClient client = new DefaultFacebookClient("12345", wr, new DefaultJsonMapper(), Version.VERSION_2_3);
    String respstring = client.fetchObject("/me", String.class);
    assertEquals("https://graph.facebook.com/v2.3/me?access_token=12345&format=json", respstring);
  }

  @Test
  public void unversionedTest() {
    FakeWebRequestor wr = new FakeWebRequestor();
    DefaultFacebookClient client = new DefaultFacebookClient("12345", wr, new DefaultJsonMapper(), Version.UNVERSIONED);
    String respstring = client.fetchObject("/me", String.class);
    assertEquals("https://graph.facebook.com/me?access_token=12345&format=json", respstring);
  }

  @Test
  public void unversionedNoVersionTest() {
    FakeWebRequestor wr = new FakeWebRequestor();
    DefaultFacebookClient client = new DefaultFacebookClient("12345", wr, new DefaultJsonMapper());
    String respstring = client.fetchObject("/me", String.class);
    assertEquals("https://graph.facebook.com/me?access_token=12345&format=json", respstring);
  }

  @Test
  public void deleteObjectDELETETest() {
    FakeWebRequestor wr = new FakeWebRequestor();
    DefaultFacebookClient client = new DefaultFacebookClient("12345", wr, new DefaultJsonMapper(), Version.VERSION_2_2);
    client.deleteObject("comment");
    assertEquals("DELETE", wr.getMethod());
    assertEquals("https://graph.facebook.com/v2.2/comment?access_token=12345&format=json", wr.getSavedUrl());
  }

  @Test
  public void deleteObjectPOSTTest() {
    FakeWebRequestor wr = new FakeWebRequestor();
    DefaultFacebookClient client = new DefaultFacebookClient("12345", wr, new DefaultJsonMapper(), Version.VERSION_2_2);
    client.setHttpDeleteFallback(true);
    client.deleteObject("comment");
    assertEquals("POST", wr.getMethod());
    assertEquals("https://graph.facebook.com/v2.2/comment", wr.getSavedUrl());
  }
}