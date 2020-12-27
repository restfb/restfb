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
package com.restfb.types;

import static com.restfb.testutils.RestfbAssertions.assertThat;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import com.restfb.*;
import com.restfb.json.JsonObject;

class InsightTest extends AbstractJsonMapperTests {

  @Test
  void checkV2_4_insight() {
    Insight exampleInsight = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/insight"), Insight.class);
    assertEquals("Lifetime: The number of stories created about your Page post, by action type. (Total Count)",
      exampleInsight.getDescription());
    assertEquals("Lifetime Post Stories by action type", exampleInsight.getTitle());
    assertEquals("lifetime", exampleInsight.getPeriod());
    assertEquals("post_stories_by_action_type", exampleInsight.getName());
    assertEquals("123452253635426_25173125254/insights/post_stories_by_action_type/lifetime", exampleInsight.getId());
    assertNotNull(exampleInsight.getValues());
  }

  @Test
  void checkV3_3_insight_1() {
    Insight exampleInsight = createJsonMapper().toJavaObject(jsonFromClasspath("v3_3/insight-1"), Insight.class);
    assertNotNull(exampleInsight);
    JsonObject obj = exampleInsight.getValues().get(0);
    assertNotNull(obj);
  }

  @Test
  void checkV3_3_connection() {
    TreeMap<String, Integer> vals = new TreeMap<>();
    Connection<Insight> conn = create3PageInsightConnection();
    for (List<Insight> insightList : conn) {
      for (Insight insight : insightList) {
        assertNotNull(insight);
        JsonObject object = insight.getValues().get(0).get("value").asObject();
        for (String name : object.names()) {
          vals.put(name, object.get(name).asInt());
        }
      }
    }

    while (!vals.isEmpty()) {
      Map.Entry<String, Integer> entry = vals.pollFirstEntry();
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }

  @Test
  void checkIssue1082() {
    List<Insight> exampleInsight = createJsonMapper().toJavaList(jsonFromClasspath("v7_0/issue1082"), Insight.class);
    assertNotNull(exampleInsight);
    exampleInsight.stream().forEach(insight -> {
      assertThat(insight.getValues()).isNotNull();
      assertThat(insight.getValues()).hasSize(1);
      assertThat(insight.getValues().get(0).get("value").asInt()).isPositive();
    });
  }

  private Connection<Insight> create3PageInsightConnection() {
    FakeWebRequestor fakeWebRequestor = new FakeWebRequestor() {
      @Override
      public Response executeGet(Request request) {

        String url = request.getFullUrl();

        if (url.equals("https://graph.facebook.com/v3.3/page1?access_token=token&format=json")) {
          return new Response(HTTP_OK, jsonFromClasspath("v3_3/insight/page-1"));
        }

        if (url.equals(
          "https://graph.facebook.com/v3.3/<page-id>/insights?access_token=<access_token>&pretty=0&metric=page_fans_city&since=1560236400&until=1560409200")) {
          return new Response(HTTP_OK, jsonFromClasspath("v3_3/insight/page-2"));
        }

        if (url.equals(
          "https://graph.facebook.com/v3.3/<page-id>/insights?access_token=<access_token>&pretty=0&since=1560409200&until=1560582000&metric=page_fans_city")) {
          return new Response(HTTP_OK, jsonFromClasspath("v3_3/insight/page-3"));
        }

        return new Response(HTTP_OK, url);
      }
    };
    DefaultFacebookClient facebookClient =
        new DefaultFacebookClient("token", fakeWebRequestor, new DefaultJsonMapper(), Version.VERSION_3_3);
    return facebookClient.fetchConnection("/page1", Insight.class);
  }
}
