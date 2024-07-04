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

import java.math.BigDecimal;
import java.math.BigInteger;

import com.restfb.json.JsonValue;

/**
 * Helper class to convert {@link JsonValue} to a given type
 */
class JsonHelper {

  /**
   * convert {@link JsonValue} into Boolean.
   * <p>
   * if the given type is a boolean the json internal conversion is used. 0 or 1 are converted to False or True
   * <p>
   * The Strings false and true are converted to the corresponding types
   *
   * @param json a json value
   * @return a boolean generated from the json value
   */
  public boolean getBooleanFrom(JsonValue json) {

    if (json.isBoolean()) {
      return json.asBoolean();
    }

    if (json.isNumber()) {
      if (json.asInt() == 0) {
        return Boolean.FALSE;
      }
      if (json.asInt() == 1) {
        return Boolean.TRUE;
      }
    }

    if (json.isString()) {
      String value = json.asString();
      if (value.equalsIgnoreCase("false")) {
        return Boolean.FALSE;
      }
      if (value.equalsIgnoreCase("true")) {
        return Boolean.TRUE;
      }
    }

    throw new UnsupportedOperationException("Json is not a boolean: " + json);
  }

  /**
   * convert jsonvalue to a Double
   * 
   * @param json a json value
   * @return a double generated from the jsonvalue
   */
  public Double getDoubleFrom(JsonValue json) {
    if (json.isNumber()) {
      return json.asDouble();
    } else {
      return Double.valueOf(json.asString());
    }
  }

  /**
   * convert jsonvalue to a Integer
   *
   * @param json a json value
   * @return a integer generated from the jsonvalue
   */
  public Integer getIntegerFrom(JsonValue json) {
    if (json.isNumber()) {
      return json.asInt();
    } else {
      return Integer.valueOf(json.asString());
    }
  }

  /**
   * convert jsonvalue to a String
   *
   * @param json a json value
   * @return a String generated from the json value
   */
  public String getStringFrom(JsonValue json) {
    if (json.isString()) {
      return json.asString();
    } else {
      return json.toString();
    }
  }

  /**
   * convert jsonvalue to a Float
   *
   * @param json a json value
   * @return a Float generated from the json value
   */
  public Float getFloatFrom(JsonValue json) {
    if (json.isNumber()) {
      return json.asFloat();
    } else {
      return new BigDecimal(json.asString()).floatValue();
    }
  }

  /**
   * convert jsonvalue to a BigInteger
   *
   * @param json a json value
   * @return a big integer generated from the json value
   */
  public BigInteger getBigIntegerFrom(JsonValue json) {
    if (json.isString()) {
      return new BigInteger(json.asString());
    } else {
      return new BigInteger(json.toString());
    }
  }

  /**
   * convert jsonvalue to a Long
   *
   * @param json a json value
   * @return a long generated from the jsonvalue
   */
  public Long getLongFrom(JsonValue json) {
    if (json.isNumber()) {
      return json.asLong();
    } else {
      return Long.valueOf(json.asString());
    }
  }

  /**
   * convert jsonvalue to a BigDecimal
   *
   * @param json a json value
   * @return a big decimal generated from the json value
   */
  public BigDecimal getBigDecimalFrom(JsonValue json) {
    if (json.isString()) {
      return new BigDecimal(json.asString());
    } else {
      return new BigDecimal(json.toString());
    }
  }

  /**
   * removes starting and ending double quote from an input String
   * 
   * @param jsonInput
   *          input JSON string
   * @return the cleaned input string without leading and ending double quote
   */
  public String cleanString(String jsonInput) {
    if (jsonInput.length() > 1 && jsonInput.startsWith("\"") && jsonInput.endsWith("\"")) {
      return jsonInput.substring(1, jsonInput.length() - 1);
    }

    return jsonInput;
  }

  boolean isEmptyArray(JsonValue jsonValue) {
    return jsonValue.isArray() && jsonValue.asArray().isEmpty();
  }

  boolean isNull(JsonValue jsonValue) {
    return jsonValue == null || jsonValue.isNull();
  }
}
