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
package com.restfb.batch;

import static com.restfb.testutils.RestfbAssertions.assertThat;

import org.junit.jupiter.api.Test;

import com.restfb.Parameter;

class BatchRequestTest {

  @Test
  void checkBatchRequest_me() {
    BatchRequest meRequest = new BatchRequest.BatchRequestBuilder("me").build();
    assertThat(meRequest).hasMethod("GET").hasRelativeUrl("me");
  }

  @Test
  void checkBatchRequest_withHeader() {
    BatchHeader header = new BatchHeader("If-None-Match", "478fb2358f700");
    BatchRequest meRequest = new BatchRequest.BatchRequestBuilder("me").headers(header).build();
    assertThat(meRequest).hasMethod("GET").hasRelativeUrl("me");
    assertThat(meRequest.getHeaders()).containsExactly(header);
  }

  @Test
  void checkBatchRequest_withName() {
    BatchRequest meRequest = new BatchRequest.BatchRequestBuilder("me").name("req1").build();
    assertThat(meRequest).hasMethod("GET").hasRelativeUrl("me").hasName("req1");
  }

  @Test
  void checkBatchRequest_withParams() {
    BatchRequest m83musicRequest =
        new BatchRequest.BatchRequestBuilder("m83music/feed").parameters(Parameter.with("limit", 5)).build();
    assertThat(m83musicRequest).hasMethod("GET").hasRelativeUrl("m83music/feed?limit=5");
  }

  @Test
  void checkBatchRequest_withFiles() {
    BatchRequest withFiles = new BatchRequest.BatchRequestBuilder("me/photos").attachedFiles("cat-pic").build();
    assertThat(withFiles).hasMethod("GET").hasRelativeUrl("me/photos").hasAttachedFiles("cat-pic");
  }

  @Test
  void checkBatchRequest_post() {
    BatchRequest postRequest = new BatchRequest.BatchRequestBuilder("me/feed").method("POST")
      .body(Parameter.with("message", "Testing!")).build();
    assertThat(postRequest).hasMethod("POST").hasRelativeUrl("me/feed").hasBody("message=Testing%21");
  }

  @Test
  void checkBatchRequest_post_omitResponse() {
    BatchRequest postRequest = new BatchRequest.BatchRequestBuilder("me/feed").method("POST")
      .body(Parameter.with("message", "Testing!")).omitResponseOnSuccess(true).build();
    assertThat(postRequest).hasMethod("POST").hasRelativeUrl("me/feed").hasBody("message=Testing%21")
      .isOmitResponseOnSuccess();
  }

}
