/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * A collection of string-handling utility methods.
 *
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6
 */
public final class StringUtils {

  /**
   * Default charset to use for encoding/decoding strings.
   */
  public static final Charset ENCODING_CHARSET = StandardCharsets.UTF_8;

  /**
   * Prevents instantiation.
   */
  private StringUtils() {
    // Prevents instantiation
  }

  /**
   * Is {@code string} blank (null or only whitespace)?
   *
   * @param string
   *          The string to check.
   * @return {@code true} if {@code string} is blank, {@code false} otherwise.
   */
  public static boolean isBlank(String string) {
    return string == null || "".equals(string.trim());
  }

  /**
   * Returns a trimmed version of {@code string}, or {@code null} if {@code string} is {@code null} or the trimmed
   * version is a blank string.
   *
   * @param string
   *          The string to trim.
   * @return A trimmed version of {@code string}, or {@code null} if {@code string} is {@code null} or the trimmed
   *         version is a blank string.
   */
  public static String trimToNull(String string) {
    if (isBlank(string)) {
      return null;
    }
    return string.trim();
  }

  /**
   * Returns a trimmed version of {@code string}, or an empty string if {@code string} is {@code null} or the trimmed
   * version is a blank string.
   *
   * @param string
   *          The string to trim.
   * @return A trimmed version of {@code string}, or an empty string if {@code string} is {@code null} or the trimmed
   *         version is a blank string.
   */
  public static String trimToEmpty(String string) {
    if (isBlank(string)) {
      return "";
    }
    return string.trim();
  }

  /**
   * Converts {@code string} to a byte array.
   * <p>
   * Assumes {@code string} is in {@link StringUtils#ENCODING_CHARSET} format.
   *
   * @param string
   *          The string to convert to a byte array.
   * @return A byte array representation of {@code string}.
   * @throws NullPointerException
   *           If {@code string} is {@code null}.
   * @throws IllegalStateException
   *           If unable to convert because the JVM doesn't support {@link StringUtils#ENCODING_CHARSET}.
   */
  public static byte[] toBytes(String string) {
    ObjectUtil.verifyParameterPresence("string", string);
    return string.getBytes(ENCODING_CHARSET);
  }

  /**
   * Converts {@code data} to a string in {@link #ENCODING_CHARSET} format.
   *
   * @param data
   *          The data to convert to a string.
   * @return A string representation of {@code data}.
   *
   * @throws NullPointerException
   *           If {@code data} is {@code null}.
   * @throws IllegalStateException
   *           If unable to convert because the JVM doesn't support {@link StringUtils#ENCODING_CHARSET}.
   * @since 1.6.13
   */
  public static String toString(byte[] data) {
    ObjectUtil.verifyParameterPresence("data", data);
    return new String(data, ENCODING_CHARSET);
  }

  /**
   * Builds and returns a string representation of the given {@code inputStream} .
   *
   * @param inputStream
   *          The stream from which a string representation is built.
   *
   * @return A string representation of the given {@code inputStream}.
   * @throws IOException
   *           If an error occurs while processing the {@code inputStream}.
   */
  public static String fromInputStream(InputStream inputStream) throws IOException {
    if (inputStream == null) {
      return null;
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, ENCODING_CHARSET))) {
      StringBuilder response = new StringBuilder();

      String line;
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }

      return response.toString();
    }
  }

  /**
   * Joins the given {@code array} into a comma-separated string.
   *
   * @param array
   *          The array to join.
   * @return A comma-separated string representation of the given {@code array}.
   */
  public static String join(String[] array) {
    return array == null ? null : join(asList(array));
  }

  /**
   * Joins the given {@code list} into a comma-separated string.
   *
   * @param list
   *          The list to join.
   * @return A comma-separated string representation of the given {@code list}.
   */
  public static String join(List<String> list) {
    if (list == null) {
      return null;
    }

    StringBuilder joined = new StringBuilder();
    boolean first = true;

    for (String element : list) {
      if (first) {
        first = false;
      } else {
        joined.append(",");
      }
      joined.append(element);
    }

    return joined.toString();
  }

  /**
   * Returns an {@code Integer} representation of the given {@code string}, or {@code null} if it's not a valid
   * {@code Integer}.
   *
   * @param string
   *          The string to process.
   * @return The {@code Integer} representation of {@code string}, or {@code null} if {@code string} is {@code null} or
   *         not a valid {@code Integer}.
   */
  public static Integer toInteger(String string) {
    if (string == null) {
      return null;
    }

    try {
      return parseInt(string);
    } catch (Exception e) {
      return null;
    }
  }
}