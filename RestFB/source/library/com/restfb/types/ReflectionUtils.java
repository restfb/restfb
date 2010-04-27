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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO: documentation
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
final class ReflectionUtils {
  private ReflectionUtils() {}

  static List<Method> getAccessors(Class<?> clazz) {
    if (clazz == null)
      throw new IllegalArgumentException(
        "The 'clazz' parameter cannot be null.");

    List<Method> methods = new ArrayList<Method>();

    for (Method method : clazz.getMethods()) {
      String methodName = method.getName();
      if (!"getClass".equals(methodName)
          && !"hashCode".equals(methodName)
          && method.getReturnType() != null
          && !Void.class.equals(method.getReturnType())
          && method.getParameterTypes().length == 0
          && ((methodName.startsWith("get") && methodName.length() > 3)
              || (methodName.startsWith("is") && methodName.length() > 2) || (methodName
            .startsWith("has") && methodName.length() > 3)))
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

  static String toString(Object object) {
    StringBuilder buffer = new StringBuilder(object.getClass().getSimpleName());
    buffer.append("[");

    boolean first = true;

    for (Method method : getAccessors(object.getClass())) {
      if (first)
        first = false;
      else
        buffer.append(" ");

      try {
        String methodName = method.getName();
        int offset = methodName.startsWith("is") ? 2 : 3;
        methodName =
            methodName.substring(offset, offset + 1).toLowerCase()
                + methodName.substring(offset + 1);

        buffer.append(methodName);
        buffer.append("=");

        // Accessors are guaranteed to take no parameters and return a value
        buffer.append(method.invoke(object));
      } catch (Exception e) {
        throw new IllegalStateException("Unable to reflectively invoke "
            + method + " on " + object, e);
      }
    }

    buffer.append("]");
    return buffer.toString();
  }

  static int hashCode(Object object) {
    if (object == null)
      return 0;

    int hashCode = 17;

    for (Method method : getAccessors(object.getClass())) {
      try {
        Object result = method.invoke(object);
        if (result != null)
          hashCode = hashCode * 31 + result.hashCode();
      } catch (Exception e) {
        throw new IllegalStateException("Unable to reflectively invoke "
            + method + " on " + object, e);
      }
    }

    return hashCode;
  }

  static boolean equals(Object object1, Object object2) {
    if (object1 == null && object2 == null)
      return true;
    if (!(object1 != null && object2 != null))
      return false;

    // Bail if the classes aren't at least one-way assignable to each other
    if (!(object1.getClass().isInstance(object2) || object2.getClass()
      .isInstance(object1)))
      return false;

    // Only compare accessors that are present in both classes
    Set<Method> accessorMethodsIntersection =
        new HashSet<Method>(getAccessors(object1.getClass()));
    accessorMethodsIntersection.retainAll(getAccessors(object2.getClass()));

    for (Method method : accessorMethodsIntersection) {
      try {
        Object result1 = method.invoke(object1);
        Object result2 = method.invoke(object2);
        if (result1 == null && result2 == null)
          continue;
        if (!(result1 != null && result2 != null))
          return false;
        if (!result1.equals(result2))
          return false;
      } catch (Exception e) {
        throw new IllegalStateException("Unable to reflectively invoke "
            + method, e);
      }
    }

    return true;
  }
}