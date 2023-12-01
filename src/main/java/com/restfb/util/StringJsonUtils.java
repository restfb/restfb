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
package com.restfb.util;

/**
 * Helper class to encapsulate simple checks used in the DefaultJsonMapper.
 */
public class StringJsonUtils {

  private StringJsonUtils() {
    throw new IllegalStateException("StringJsonUtils must not be instantiated");
  }

  public static final String EMPTY_OBJECT = "{}";

  /**
   * Is the given JSON equivalent to the empty list (<code>[]</code>)?
   *
   * @param jsonString
   *          The JSON to check.
   * @return {@code true} if the JSON is equivalent to the empty list, {@code false} otherwise.
   */
  public static boolean isEmptyList(String jsonString) {
    return "[]".equals(jsonString);
  }

  /**
   * Checks if the given String start with a <code>[</code> character, so it may be a JsonArray
   *
   * @param jsonString
   *          the JSON to check.
   * @return {@code true} if the String may be a JSON Array
   */
  public static boolean isList(String jsonString) {
    return jsonString != null && jsonString.startsWith("[");
  }

  /**
   * Checks if the given String is equals to the String with the content {@code "null"}
   * 
   * @param jsonString
   *          the JSON to check.
   * @return {@code true} if the String is {@code "null"}
   */
  public static boolean isNull(String jsonString) {
    return "null".equals(jsonString);
  }

  /**
   * Checks if the given String is equals to the String with the content {@code "false"}
   * 
   * @param jsonString
   *          the JSON to check.
   * @return {@code true} if the String is {@code "false"}
   */
  public static boolean isFalse(String jsonString) {
    return "false".equals(jsonString);
  }

  /**
   * Checks if the given String start with a <code>{</code> character, so it may be a JsonObject
   *
   * @param jsonString
   *          the JSON to check.
   * @return {@code true} if the String may be a JSON object
   */
  public static boolean isObject(String jsonString) {
    return jsonString != null && jsonString.startsWith("{");
  }

  /**
   * Is the given JSON equivalent to the empty object (<code>{}</code>)?
   *
   * @param jsonString
   *          The JSON to check.
   * @return {@code true} if the JSON is equivalent to the empty object, {@code false} otherwise.
   */
  public static boolean isEmptyObject(String jsonString) {
    return EMPTY_OBJECT.equals(jsonString);
  }
}
