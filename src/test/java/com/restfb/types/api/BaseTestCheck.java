/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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
package com.restfb.types.api;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseTestCheck {

  protected Set<String> fetchMethodsFromClass(Class clazz) {
    Method[] methods = clazz.getMethods();
    Set<String> methodList = new HashSet<>();
    for (Method method : methods) {
      methodList.add(method.getName());
    }

    Method[] inheritedMethods = clazz.getMethods();
    for (Method method : inheritedMethods) {
      if (method.getName().equals("equals")) {
        methodList.add(method.getName());
      }
      if (method.getName().equals("hashCode")) {
        methodList.add(method.getName());
      }
      if (method.getName().equals("toString")) {
        methodList.add(method.getName());
      }
    }

    return methodList;
  }

  protected String joinMethods(Set<String> methodList) {
    return methodList.stream().collect(Collectors.joining(","));
  }
}
