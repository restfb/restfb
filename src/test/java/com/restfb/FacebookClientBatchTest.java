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
package com.restfb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.restfb.batch.BatchHeader;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;

class FacebookClientBatchTest extends AbstractJsonMapperTests {

  @Test
  void batchtest() {
    FacebookClient client = createFacebookClient("batch-simple");

    BatchRequest exampleRequest = new BatchRequest.BatchRequestBuilder("").build();

    List<BatchResponse> responseList = client.executeBatch(exampleRequest);
    assertEquals(2, responseList.size());

    checkResponseItem(responseList, 1, 200);
    List<BatchHeader> headerList = checkResponseItem(responseList, 0, 200);

    assertEquals("Access-Control-Allow-Origin", headerList.get(0).getName());
    assertEquals("*", headerList.get(0).getValue());
    assertTrue(responseList.get(0).getBody().contains("Tester"));
  }

  @Test
  void batchFirst() {
    FacebookClient client = createFacebookClient("batch-first-error");

    BatchRequest exampleRequest = new BatchRequest.BatchRequestBuilder("").build();

    List<BatchResponse> responseList = client.executeBatch(exampleRequest);
    assertEquals(2, responseList.size());

    checkResponseItem(responseList, 0, 400);
    checkResponseItem(responseList, 1, 200);
  }

  @Test
  void batchSecond() {
    FacebookClient client = createFacebookClient("batch-second-error");

    BatchRequest exampleRequest = new BatchRequest.BatchRequestBuilder("").build();

    List<BatchResponse> responseList = client.executeBatch(exampleRequest);
    assertEquals(2, responseList.size());

    checkResponseItem(responseList, 0, 200);
    checkResponseItem(responseList, 1, 400);
  }

  private List<BatchHeader> checkResponseItem(List<BatchResponse> responseList, int item, int status) {
    assertEquals(status, responseList.get(item).getCode().longValue());
    List<BatchHeader> headerList = responseList.get(item).getHeaders();
    assertEquals(9, headerList.size());
    return headerList;
  }

  private FacebookClient createFacebookClient(final String batchJson) {
    WebRequestor.Response response = new WebRequestor.Response(200, jsonFromClasspath("batch/" + batchJson));
    return new DefaultFacebookClient("accesstoken", new FakeWebRequestor(response), new DefaultJsonMapper(),
      Version.LATEST);
  }

}
