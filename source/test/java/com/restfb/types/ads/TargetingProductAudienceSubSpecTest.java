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
package com.restfb.types.ads;

import static org.junit.Assert.*;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.json.JsonObject;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class TargetingProductAudienceSubSpecTest extends AbstractJsonMapperTests {

  @Test
  public void test() {
    TargetingProductAudienceSubSpec subSpec = createJsonMapper().toJavaObject(
      jsonFromClasspath("ads/v2_6/targetingproductaudiencesubspec"), TargetingProductAudienceSubSpec.class);
    assertNotNull(subSpec);
    assertEquals(1234567L, subSpec.getRetentionSeconds().longValue());
    assertTrue(subSpec.getRule() instanceof RuleData);
    RuleData ruleData = (RuleData) subSpec.getRule();
    assertEquals("url", ruleData.getType());
    assertEquals("i_contains", ruleData.getOperator().getType());
    assertEquals("shoes", ruleData.getOperator().getValue());
  }

  @Test
  public void testReverse() throws JSONException {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example1"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);

    TargetingProductAudienceSubSpec spec = new TargetingProductAudienceSubSpec();
    spec.setRule(rule);

    String json = createJsonMapper().toJson(spec, true);
    JSONAssert.assertEquals("{\"rule\":" + ruleJson.toString() + "}", json, false);
  }

  @Test
  public void testStringExample() {
    TargetingProductAudienceSubSpec ruleJson =
            createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_7/subspec_example"), TargetingProductAudienceSubSpec.class);
    assertNotNull(ruleJson);
    Rule rule = ruleJson.getRule();
    assertNotNull(rule);
    RuleData ruleData = (RuleData) rule;
    assertEquals("event", ruleData.getType());
    assertEquals("eq", ruleData.getOperator().getType());
    assertEquals("BOUGHT", ruleData.getOperator().getValue());
  }
}
