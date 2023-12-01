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
package com.restfb.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;
import com.restfb.integration.base.RestFbIntegrationTestBase;

class SimpleBatchITCase extends RestFbIntegrationTestBase {

  @Test
  void check_meAndZuck() {
    DefaultFacebookClient client =
        new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.VERSION_9_0);
    BatchRequest meRequest =
        new BatchRequest.BatchRequestBuilder("me").parameters(Parameter.with("fields", "id,name")).build();
    BatchRequest zuckRequest =
        new BatchRequest.BatchRequestBuilder("4").parameters(Parameter.with("fields", "id,name")).build();

    List<BatchResponse> responseList = client.executeBatch(meRequest, zuckRequest);

    BatchResponse meResponse = responseList.get(0);
    assertNotNull(meResponse);
    assertEquals(200, meResponse.getCode().longValue());
    BatchResponse zuckResponse = responseList.get(1);
    assertNotNull(zuckResponse);
    assertEquals(200, zuckResponse.getCode().longValue());
  }

  @Test
  void check_meAndNotWorking() {
    DefaultFacebookClient client =
        new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.VERSION_9_0);
    BatchRequest meRequest =
        new BatchRequest.BatchRequestBuilder("me").parameters(Parameter.with("fields", "id,name")).build();
    BatchRequest nwRequest =
        new BatchRequest.BatchRequestBuilder("2").parameters(Parameter.with("fields", "id,name")).build();

    List<BatchResponse> responseList = client.executeBatch(meRequest, nwRequest);

    BatchResponse meResponse = responseList.get(0);
    assertNotNull(meResponse);
    assertEquals(200, meResponse.getCode().longValue());
    BatchResponse nwResponse = responseList.get(1);
    assertNotNull(nwResponse);
    assertEquals(400, nwResponse.getCode().longValue());
  }

  @Test
  void check_NotWorkingAndMe() {
    DefaultFacebookClient client =
        new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.VERSION_9_0);
    BatchRequest meRequest =
        new BatchRequest.BatchRequestBuilder("me").parameters(Parameter.with("fields", "id,name")).build();
    BatchRequest nwRequest =
        new BatchRequest.BatchRequestBuilder("2").parameters(Parameter.with("fields", "id,name")).build();

    List<BatchResponse> responseList = client.executeBatch(nwRequest, meRequest);

    BatchResponse nwResponse = responseList.get(0);
    assertNotNull(nwResponse);
    assertEquals(400, nwResponse.getCode().longValue());
    BatchResponse meResponse = responseList.get(1);
    assertNotNull(meResponse);
    assertEquals(200, meResponse.getCode().longValue());
  }
}
