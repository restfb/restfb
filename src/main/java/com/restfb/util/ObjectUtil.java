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
package com.restfb.util;

import static com.restfb.util.StringUtils.isBlank;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class ObjectUtil {

  private ObjectUtil() {
    // prevent instantiation
  }

  /**
   * Ensures that {@code obj} isn't {@code null} or an empty string.
   *
   * @param obj
   *          The parameter to check.
   * @param errorText
   *          The exception message.
   * @throws IllegalArgumentException
   *           If {@code obj} is {@code null} or an empty string.
   */
  public static String requireNotEmpty(String obj, String errorText) {
    if (isBlank(obj)) {
      throw new IllegalArgumentException(errorText);
    }
    return obj;
  }

  public static void requireNotEmpty(Collection<?> collection, String errorText) {
    if (collection == null || collection.isEmpty()) {
      throw new IllegalArgumentException(errorText);
    }
  }

  /**
   * Ensures that {@code obj} isn't {@code null}.
   *
   * @param obj
   *          The parameter to check.
   * @param exceptionSupplier
   *          The supplier for the exception that is thrown if obj is null.
   * @throws T
   *           If {@code obj} is {@code null}.
   */
  public static <T extends Exception> void requireNotNull(Object obj, Supplier<T> exceptionSupplier) throws T {
    if (obj == null) {
      throw exceptionSupplier.get();
    }
  }

  /**
   * Checks is the object is a empty 'collection' or 'map'.
   * 
   * @param obj
   *          the object that is checked
   * @return {@code true} if the given object is a empty collection or an empty map, {@code false} otherwise
   */
  public static boolean isEmptyCollectionOrMap(Object obj) {
    if (obj instanceof Collection) {
      return ((Collection) obj).isEmpty();
    }

    return (obj instanceof Map && ((Map) obj).isEmpty());
  }

  /**
   * Ensures that {@code parameter} isn't {@code null} or an empty string.
   *
   * @param parameterName
   *          The name of the parameter (to be used in exception message).
   * @param parameter
   *          The parameter to check.
   * @throws IllegalArgumentException
   *           If {@code parameter} is {@code null} or an empty string.
   */
  public static void verifyParameterPresence(String parameterName, String parameter) {
    verifyParameterPresence(parameterName, (Object) parameter);
    requireNotEmpty(parameter, "The '" + parameterName + "' parameter cannot be an empty string.");
  }

  /**
   * Ensures that {@code parameter} isn't {@code null}.
   *
   * @param parameterName
   *          The name of the parameter (to be used in exception message).
   * @param parameter
   *          The parameter to check.
   * @throws NullPointerException
   *           If {@code parameter} is {@code null}.
   */
  public static void verifyParameterPresence(String parameterName, Object parameter) {
    Objects.requireNonNull(parameter, "The '" + parameterName + "' parameter cannot be null.");
  }
}
