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

public class RuleTest extends AbstractJsonMapperTests {

  @Test
  public void example1test() {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example1"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    assertNotNull(rule);
    assertTrue(rule instanceof RuleData);
    RuleData ruleData = (RuleData) rule;
    assertEquals("url", ruleData.getType());
    RuleOp op = (RuleOp) ruleData.getOperator();
    assertEquals("i_contains", op.getType());
    assertEquals("shoes", op.getValue());
  }

  @Test
  public void example2test() {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example2"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    assertNotNull(rule);
    assertTrue(rule instanceof RuleData);
    RuleData ruleData = (RuleData) rule;
    assertEquals("url", ruleData.getType());
    RuleOp op = (RuleOp) ruleData.getOperator();
    assertEquals("i_not_contains", op.getType());
    assertEquals("shoes", op.getValue());
  }

  @Test
  public void example3test() {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example3"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    assertNotNull(rule);
    assertTrue(rule instanceof RuleOpAnd);
    RuleOpAnd ruleOpAnd = (RuleOpAnd) rule;
    assertEquals(2, ruleOpAnd.getRuleList().size());
  }

  @Test
  public void example4test() {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example4"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    assertNotNull(rule);
    assertTrue(rule instanceof RuleOpOr);
    RuleOpOr ruleOpAnd = (RuleOpOr) rule;
    assertEquals(2, ruleOpAnd.getRuleList().size());
  }

  @Test
  public void example5test() {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example5"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    assertNotNull(rule);
    assertTrue(rule instanceof RuleOpAnd);
    RuleOpAnd ruleOpAnd = (RuleOpAnd) rule;
    assertEquals(2, ruleOpAnd.getRuleList().size());
  }

  @Test
  public void example6test() {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example6"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    assertNotNull(rule);
    assertTrue(rule instanceof RuleOpOr);
    RuleOpOr ruleOpAnd = (RuleOpOr) rule;
    assertEquals(2, ruleOpAnd.getRuleList().size());
    RuleData ruleOpOr1 = (RuleData) ruleOpAnd.getRuleList().get(0);
    assertEquals("url", ruleOpOr1.getType());
    RuleData ruleOpOr2 = (RuleData) ruleOpAnd.getRuleList().get(1);
    assertEquals("url", ruleOpOr2.getType());
  }

  @Test
  public void example7test() {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example7"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    assertNotNull(rule);
    assertTrue(rule instanceof RuleOpAnd);
    RuleOpAnd ruleOpAnd = (RuleOpAnd) rule;
    assertEquals(2, ruleOpAnd.getRuleList().size());
    RuleOpOr ruleOpOr1 = (RuleOpOr) ruleOpAnd.getRuleList().get(0);
    assertEquals(2, ruleOpOr1.getRuleList().size());
    RuleOpOr ruleOpOr2 = (RuleOpOr) ruleOpAnd.getRuleList().get(1);
    assertEquals(2, ruleOpOr2.getRuleList().size());
  }

  @Test
  public void checkNull() {
    assertNull(RuleFactory.createRuleFromJson(null));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkBadJson() {
    JsonObject ruleJson = createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_bad"), JsonObject.class);
    RuleFactory.createRuleFromJson(ruleJson);
  }

  @Test
  public void example1testReverse() throws JSONException {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example1"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    JsonObject ruleJsonReverse = RuleFactory.createJsonFromRule(rule);
    JSONAssert.assertEquals(ruleJson.toString(), ruleJsonReverse.toString(), false);
  }

  @Test
  public void example2testReverse() throws JSONException {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example2"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    JsonObject ruleJsonReverse = RuleFactory.createJsonFromRule(rule);
    JSONAssert.assertEquals(ruleJson.toString(), ruleJsonReverse.toString(), false);
  }

  @Test
  public void example4testReverse() throws JSONException {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example4"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    JsonObject ruleJsonReverse = RuleFactory.createJsonFromRule(rule);
    JSONAssert.assertEquals(ruleJson.toString(), ruleJsonReverse.toString(), false);
  }

  @Test
  public void example5testReverse() throws JSONException {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example5"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    JsonObject ruleJsonReverse = RuleFactory.createJsonFromRule(rule);
    JSONAssert.assertEquals(ruleJson.toString(), ruleJsonReverse.toString(), false);
  }

  @Test
  public void example6testReverse() throws JSONException {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example6"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    JsonObject ruleJsonReverse = RuleFactory.createJsonFromRule(rule);
    JSONAssert.assertEquals(ruleJson.toString(), ruleJsonReverse.toString(), false);
  }

  @Test
  public void example7testReverse() throws JSONException {
    JsonObject ruleJson =
        createJsonMapper().toJavaObject(jsonFromClasspath("ads/v2_6/rule_example7"), JsonObject.class);
    Rule rule = RuleFactory.createRuleFromJson(ruleJson);
    JsonObject ruleJsonReverse = RuleFactory.createJsonFromRule(rule);
    JSONAssert.assertEquals(ruleJson.toString(), ruleJsonReverse.toString(), false);
  }

}
