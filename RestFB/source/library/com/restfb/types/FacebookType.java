/*
 * Copyright (c) 2010 Mark Allen.
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

package com.restfb.types;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

/**
 * TODO: document
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class FacebookType {
  @Facebook
  private String id;

  // Facebook date format (ISO 8601).
  // Example: 2010-02-28T16:11:08+0000
  private static final String FACEBOOK_DATE_FORMAT = "yyyy-MM-dd'T'kk:mm:ssZ";

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return getId() == null ? super.hashCode() : getId().hashCode();
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder(getClass().getSimpleName());
    buffer.append("[");

    boolean first = true;

    for (Method method : ReflectionUtils.getAccessors(getClass())) {
      if (first)
        first = false;
      else
        buffer.append(" ");

      try {
        String methodName = method.getName();
        int offset = methodName.startsWith("get") ? 3 : 2;
        methodName =
            methodName.substring(offset, offset + 1).toLowerCase()
                + methodName.substring(offset + 1);

        buffer.append(methodName);
        buffer.append("=");

        // Accessors are guaranteed to take no parameters and return a value
        buffer.append(method.invoke(this));
      } catch (Exception e) {
        throw new IllegalStateException("Unable to reflectively invoke "
            + method, e);
      }
    }

    buffer.append("]");
    return buffer.toString();
  }

  public String getId() {
    return id;
  }

  /**
   * Returns a Java representation of a Facebook {@code date} string.
   * 
   * @param date
   *          Facebook {@code date} string.
   * @return Java date representation of the given Facebook {@code date} string.
   * @throws IllegalArgumentException
   *           If the provided {@code date} isn't in the Facebook date format
   *           (ISO 8601).
   */
  protected Date toDate(String date) throws IllegalArgumentException {
    if (date == null)
      return null;

    try {
      return new SimpleDateFormat(FACEBOOK_DATE_FORMAT).parse(date);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Unable to parse date '" + date
          + "' using format string '" + FACEBOOK_DATE_FORMAT + "'", e);
    }
  }

  private static abstract class ReflectionUtils {
    private ReflectionUtils() {}

    private static List<Method> getAccessors(Class<?> clazz) {
      if (clazz == null)
        throw new IllegalArgumentException(
          "The 'clazz' parameter cannot be null.");

      List<Method> methods = new ArrayList<Method>();

      for (Method method : clazz.getMethods()) {
        String methodName = method.getName();
        if (!"getClass".equals(methodName)
            && method.getReturnType() != null
            && method.getParameterTypes().length == 0
            && ((methodName.startsWith("get") && methodName.length() > 3) || (methodName
              .startsWith("is") && methodName.length() > 2)))
          methods.add(method);
      }

      // Order the methods alphabetically by name
      Collections.sort(methods, new Comparator<Method>() {
        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(Method method1, Method method2) {
          return method1.getName().compareTo(method2.getName());
        }
      });

      return Collections.unmodifiableList(methods);
    }
  }
}