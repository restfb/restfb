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

import java.util.List;

public class RuleFactory {

  public static JsonObject createJsonFromRule(Rule rule) {
    if (rule == null) {
      return null;
    }

    JsonObject result = new JsonObject();

    if (rule instanceof RuleOpAnd) {
      RuleOpAnd ruleOpAnd = (RuleOpAnd) rule;
      List<Rule> ruleList = ruleOpAnd.getRuleList();
      JsonArray jsonValues = new JsonArray();
      for (Rule ruleListItem : ruleList) {
        JsonObject obj = RuleFactory.createJsonFromRule(ruleListItem);
        if (obj != null) {
          jsonValues.put(obj);
        }
      }
      result.put(ruleOpAnd.getType(), jsonValues);
      return result;
    }

    if (rule instanceof RuleOpOr) {
      RuleOpOr ruleOpOr = (RuleOpOr) rule;
      List<Rule> ruleList = ruleOpOr.getRuleList();
      JsonArray jsonValues = new JsonArray();
      for (Rule ruleListItem : ruleList) {
        JsonObject obj = RuleFactory.createJsonFromRule(ruleListItem);
        if (obj != null) {
          jsonValues.put(obj);
        }
      }
      result.put(ruleOpOr.getType(), jsonValues);
      return result;
    }

    if (rule instanceof RuleData) {
      RuleData ruleData = (RuleData) rule;
      result.put(ruleData.getType(), RuleFactory.createJsonFromRule(ruleData.getOperator()));
      return result;
    }

    if (rule instanceof RuleOp) {
      RuleOp ruleOp = (RuleOp) rule;
      result.put(ruleOp.getType(), ruleOp.getValue());
      return result;
    }

    if (result.length() == 0) {
      throw new IllegalArgumentException("unknown rule found");
    } else {
      return result;
    }

  }

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
    if ("eq".equals(key)) {
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
        JsonObject item = (JsonObject) andList.get(i);
        Rule r = RuleFactory.createRuleFromJson(item);
        rOp.getRuleList().add(r);
      }

      return rOp;
    }
    if ("or".equals(key)) {
      JsonArray orList = ruleJson.optJsonArray(key);
      RuleOpOr rOp = new RuleOpOr(key);

      for (int i = 0; i < orList.length(); i++) {
        JsonObject item = (JsonObject) orList.get(i);
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
