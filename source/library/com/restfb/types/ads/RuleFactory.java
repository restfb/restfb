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

import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

public class RuleFactory {

  public static Rule createRuleFromJson(JsonObject ruleJson) {

    // null check,
    if (ruleJson == null) {
      return null;
    }

    // size check, rules with more than one key are not allowed
    if (ruleJson.length() > 1) {
      throw new IllegalArgumentException("only one key is supported, found " + ruleJson.length());
    }

    String key = (String) ruleJson.keys().next();

    // create data
    if ("url".equals(key)) {
      return createRuleData(ruleJson, key);
    }
    if ("event".equals(key)) {
      return createRuleData(ruleJson, key);
    }
    if ("path".equals(key)) {
      return createRuleData(ruleJson, key);
    }
    if ("domain".equals(key)) {
      return createRuleData(ruleJson, key);
    }
    if ("device_type".equals(key)) {
      return createRuleData(ruleJson, key);
    }

    // create "simple" operator
    if ("i_contains".equals(key)) {
      return createRuleOperator(ruleJson, key);
    }
    if ("contains".equals(key)) {
      return createRuleOperator(ruleJson, key);
    }
    if ("i_not_contains".equals(key)) {
      return createRuleOperator(ruleJson, key);
    }
    if ("not_contains".equals(key)) {
      return createRuleOperator(ruleJson, key);
    }
    if ("gte".equals(key)) {
      return createRuleOperator(ruleJson, key);
    }
    if ("gt".equals(key)) {
      return createRuleOperator(ruleJson, key);
    }
    if ("lte".equals(key)) {
      return createRuleOperator(ruleJson, key);
    }
    if ("lt".equals(key)) {
      return createRuleOperator(ruleJson, key);
    }
    if ("neq".equals(key)) {
      return createRuleOperator(ruleJson, key);
    }
    if ("regex_match".equals(key)) {
      return createRuleOperator(ruleJson, key);
    }

    // create a more complex operator (OR, AND)
    if ("and".equals(key)) {
      JsonArray andList = ruleJson.optJsonArray(key);
      RuleOpAnd rOp = new RuleOpAnd(key);

      for (int i = 0; i < andList.length(); i++) {
        JsonObject item = (JsonObject) andList.get(0);
        Rule r = RuleFactory.createRuleFromJson(item);
        rOp.getRuleList().add(r);
      }

      return rOp;
    }
    if ("or".equals(key)) {
      JsonArray andList = ruleJson.optJsonArray(key);
      RuleOpOr rOp = new RuleOpOr(key);
      for (int i = 0; i < andList.length(); i++) {
        JsonObject item = (JsonObject) andList.get(0);
        Rule r = RuleFactory.createRuleFromJson(item);
        rOp.getRuleList().add(r);
      }
      return rOp;
    }

    // fallback is the custom data object
    return createRuleData(ruleJson, key);
  }

  private static Rule createRuleData(JsonObject ruleJson, String key) {
    RuleData rData = new RuleData(key);
    rData.setOperator((RuleOp) RuleFactory.createRuleFromJson(ruleJson.optJsonObject(key)));
    return rData;
  }

  private static Rule createRuleOperator(JsonObject ruleJson, String key) {
    RuleOp rOp = new RuleOp(key);
    String value = ruleJson.optString(key);

    if (value != null) {
      rOp.setValue(value);
    }
    return rOp;
  }

}
