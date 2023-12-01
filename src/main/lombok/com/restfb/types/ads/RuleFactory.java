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
package com.restfb.types.ads;

import java.util.List;

import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.json.JsonValue;

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
          jsonValues.add(obj);
        }
      }
      result.add(ruleOpAnd.getType(), jsonValues);
      return result;
    }

    if (rule instanceof RuleOpOr) {
      RuleOpOr ruleOpOr = (RuleOpOr) rule;
      List<Rule> ruleList = ruleOpOr.getRuleList();
      JsonArray jsonValues = new JsonArray();
      for (Rule ruleListItem : ruleList) {
        JsonObject obj = RuleFactory.createJsonFromRule(ruleListItem);
        if (obj != null) {
          jsonValues.add(obj);
        }
      }
      result.add(ruleOpOr.getType(), jsonValues);
      return result;
    }

    if (rule instanceof RuleData) {
      RuleData ruleData = (RuleData) rule;
      result.add(ruleData.getType(), RuleFactory.createJsonFromRule(ruleData.getOperator()));
      return result;
    }

    if (rule instanceof RuleOp) {
      RuleOp ruleOp = (RuleOp) rule;
      result.add(ruleOp.getType(), ruleOp.getValue());
      return result;
    }

    if (result.names().isEmpty()) {
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
    if (ruleJson.names().size() > 1) {
      throw new IllegalArgumentException("only one key is supported, found " + ruleJson.names().size());
    }

    String key = ruleJson.names().get(0);

    // create data
    if (isCreateData(key)) {
      return createRuleData(ruleJson, key);
    }

    // create "simple" operator
    if (isCreateSimpleOperator(key)) {
      return createRuleOperator(ruleJson, key);
    }

    // create a more complex operator (OR, AND)
    if ("and".equals(key)) {
      JsonValue andList = ruleJson.get(key);
      RuleOpAnd rOp = new RuleOpAnd(key);
      if (andList.isArray()) {
        for (JsonValue item : andList.asArray()) {
          Rule r = RuleFactory.createRuleFromJson(item.asObject());
          rOp.getRuleList().add(r);
        }
      }
      return rOp;
    }
    if ("or".equals(key)) {
      JsonValue andList = ruleJson.get(key);
      RuleOpOr rOp = new RuleOpOr(key);
      if (andList.isArray()) {
        for (JsonValue item : andList.asArray()) {
          Rule r = RuleFactory.createRuleFromJson(item.asObject());
          rOp.getRuleList().add(r);
        }
      }
      return rOp;
    }

    // fallback is the custom data object
    return createRuleData(ruleJson, key);
  }

  private static boolean isCreateData(String key) {
    return "url".equals(key) || //
        "event".equals(key) || //
        "path".equals(key) || //
        "domain".equals(key) || //
        "device_type".equals(key);
  }

  private static boolean isCreateSimpleOperator(String key) {
    return "i_contains".equals(key) || //
        "contains".equals(key) || //
        "i_not_contains".equals(key) || //
        "not_contains".equals(key) || //
        "gte".equals(key) || //
        "gt".equals(key) || //
        "lte".equals(key) || //
        "lt".equals(key) || //
        "neq".equals(key) || //
        "eq".equals(key) || //
        "regex_match".equals(key);
  }

  private static Rule createRuleData(JsonObject ruleJson, String key) {
    RuleData rData = new RuleData(key);
    rData.setOperator((RuleOp) RuleFactory.createRuleFromJson(ruleJson.get(key).asObject()));
    return rData;
  }

  private static Rule createRuleOperator(JsonObject ruleJson, String key) {
    RuleOp rOp = new RuleOp(key);
    if (ruleJson.get(key).isString()) {
      rOp.setValue(ruleJson.get(key).asString());
    } else {
      rOp.setValue(ruleJson.get(key).toString());
    }
    return rOp;
  }

}
