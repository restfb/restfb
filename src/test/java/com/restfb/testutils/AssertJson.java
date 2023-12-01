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
package com.restfb.testutils;


import org.junit.jupiter.api.Assertions;

import com.restfb.json.Json;
import com.restfb.json.JsonValue;


/**
 * Assertion utility for JSON as Strings. Based on junit and minimal json.
 */
public class AssertJson {

    /**
     * Tests to Json objects as Strings for equality.
     * <p>
     * All <code>primitive json entities</code> are checked via
     * the provides equals method. Primitive Json entities are boolean, Strings, float, ints and so on.
     * </p>
     * <p>
     * <code>Arrays</code> need to be in the same order because we convert them into ArrayLists and therefore it
     * is important that the order is the same
     * </p>
     * <p>
     * <code>Objects</code> need the same keys, but the order may be different and is not checked.
     * </p>
     *
     * @param expected the expected Json as String
     * @param actual   the actual Json as String
     */
    public static void assertEquals(String expected, String actual) {

        JsonValue expectedJson = null;
        JsonValue actualJson = null;

        if (expected instanceof String) {
            expectedJson = Json.parse((String) expected);
        }

        if (actual instanceof String) {
            actualJson = Json.parse((String) actual);
        }

        failIfNotEquals(null, expectedJson, actualJson);
    }

    private static void failIfNotEquals(String key, JsonValue expectedJson, JsonValue actualJson) {
        try {
            if (actualJson.isObject() && expectedJson.isObject()) {
                int actualFieldSize = actualJson.asObject().names().size();
                int expectedFieldSize = expectedJson.asObject().names().size();
                Assertions.assertEquals(expectedFieldSize, actualFieldSize, "Objects have different fields - expected: " + expectedFieldSize + " actual: " + actualFieldSize);
                for (String keyName : expectedJson.asObject().names()) {
                    failIfNotEquals(keyName, expectedJson.asObject().get(keyName), actualJson.asObject().get(keyName));
                }
            } else if (actualJson.isArray() && expectedJson.isArray()) {
                int actualFieldSize = actualJson.asArray().size();
                int expectedFieldSize = expectedJson.asArray().size();
                Assertions.assertEquals(expectedFieldSize, actualFieldSize, "Arrays have different sizes - expected: " + expectedFieldSize + " actual: " + actualFieldSize);
                for (int i = 0; i < actualJson.asArray().size(); i++) {
                    failIfNotEquals(null, expectedJson.asArray().get(i), actualJson.asArray().get(i));
                }
            } else {
                String assertionString = (key != null) ? "Values with key '" + key + "' are different" : "Values are different";
                Assertions.assertEquals(expectedJson, actualJson, assertionString);
            }
        } catch (NullPointerException npe) {
            Assertions.fail("Objects not equal: expected " + expectedJson + ", actual " + actualJson);
        }
    }
}
