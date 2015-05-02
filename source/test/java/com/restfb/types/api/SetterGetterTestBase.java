/*
 * Copyright (c) 2010-2015 Norbert Bartels.
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

import com.restfb.types.Message;
import com.restfb.types.NamedFacebookType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SetterGetterTestBase {

  private final List<String> ignoredFields = new ArrayList<String>();
  private final HashMap<Class, Object> defaultInstances;

  public void addIgnoredField(String fieldName) {
    ignoredFields.add(fieldName);
  }

  public SetterGetterTestBase() {
    defaultInstances = new HashMap<Class, Object>();
    defaultInstances.put(boolean.class, Boolean.TRUE);
    defaultInstances.put(byte.class, Byte.valueOf((byte) 0));
    defaultInstances.put(char.class, Character.valueOf('0'));
    defaultInstances.put(short.class, Short.valueOf((short) 0));
    defaultInstances.put(int.class, Integer.valueOf(0));
    defaultInstances.put(long.class, Long.valueOf(0));
    defaultInstances.put(float.class, Float.valueOf(0f));
    defaultInstances.put(double.class, Double.valueOf(0));
    defaultInstances.put(List.class, new ArrayList());
    defaultInstances.put(String.class, "test");
    defaultInstances.put(NamedFacebookType.class, new NamedFacebookType());
    defaultInstances.put(Message.class, new Message());
    ignoredFields.add("serialVersionUID");
  }

  /**
   * test the given instance.
   * 
   * check all fields (except the ignored fields), try to set and get
   * 
   * @param instance
   */
  public void testInstance(final Object instance) {
    List<Field> fields = Arrays.asList(instance.getClass().getDeclaredFields());
    for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext();) {
      Field field = iterator.next();

      if (ignoredFields.contains(field.getName())) {
        continue;
      }

      if (fieldIsList(field)) {
        testListGetterAndAddRemove(field, instance);
      } else {
        testGetterAndSetter(field, instance);
      }
    }
  }

  private boolean fieldIsList(Field field) {
    Class<?> clazz = field.getType();
    return (clazz.getName().equals(List.class.getName()));
  }

  private void testGetterAndSetter(Field field, Object instance) {

    Class<?> theClass = field.getDeclaringClass();
    String testText =
        "Problem checking setter and getter for field " + field.getName() + " on "
            + field.getDeclaringClass().getName();
    try {
      Method getter = null;

      String capName = capitalizedName(field);
      // if (capName.matches("^Is[A-Z].*")) {
      // capName = capName.substring(2);
      // }

      if (field.getType().equals(boolean.class)) {
        getter = theClass.getMethod("is" + capName);
      } else {
        getter = theClass.getMethod("get" + capName);
      }
      Method setter = theClass.getMethod("set" + capName, field.getType());

      Object o = getExampleValueByType(field.getType());
      setter.invoke(instance, o);

      assertEquals(testText, o, getter.invoke(instance));

    } catch (NoSuchMethodException ex) {
      fail("NoSuchMethodException: " + testText);
    } catch (SecurityException ex) {
      fail("SecurityException: " + testText);
    } catch (IllegalAccessException ex) {
      fail("IllegalAccessException: " + testText);
    } catch (InvocationTargetException ex) {
      fail("InvocationTargetException: " + testText);
    }
  }

  private void testListGetterAndAddRemove(Field field, Object instance) {
    Class<?> theClass = field.getDeclaringClass();
    String testText =
        "Problem checking add,remove and getter for field " + field.getName() + " on "
            + field.getDeclaringClass().getName();
    try {
      Method[] methods = theClass.getMethods();
      Method adder = null;
      Method remover = null;
      Method getter = null;
      for (int i = 0; i < methods.length; i++) {
        Method method = methods[i];
        Method a = findAdder(method, field);
        if (null != a) {
          adder = a;
        }
        Method r = findRemover(method, field);
        if (null != r) {
          remover = r;
        }
        if (method.getName().equals("get" + capitalizedName(field))) {
          getter = method;
        }
      }

      if (getter == null || adder == null || remover == null) {
        fail("method not found" + testText);
      }

      Object o = getExampleValueByType(adder.getParameterTypes()[0]);
      assertEquals(testText, 0, ((List) getter.invoke(instance)).size());
      adder.invoke(instance, o);
      assertEquals(testText, 1, ((List) getter.invoke(instance)).size());
      remover.invoke(instance, o);
      assertEquals(testText, 0, ((List) getter.invoke(instance)).size());
      try {
        ((List) getter.invoke(instance)).add(o);
        fail("should not be possible");
      } catch (UnsupportedOperationException e) {
        // catch non modifiable exception
        assertTrue(true);
      }

    } catch (SecurityException ex) {
      fail("SecurityException: " + testText);
    } catch (IllegalAccessException ex) {
      fail("IllegalAccessException: " + testText);
    } catch (InvocationTargetException ex) {
      fail("InvocationTargetException: " + testText);
    }
  }

  private String capitalizedName(final Field field) {
    return capitalizedName(field, false);
  }

  private String capitalizedName(final Field field, boolean removePlural) {
    String result = field.getName();
    result = result.replaceFirst("" + result.charAt(0), "" + Character.toUpperCase(result.charAt(0)));
    if (!removePlural) {
      return result;
    } else {
      return result.substring(0, result.length() - 1);
    }
  }

  private Object getExampleValueByType(Class<?> type) {
    return defaultInstances.get(type);
  }

  private Class<?> fetchActualTypeFromList(Field field) {
    Type type = field.getGenericType();
    if (type instanceof ParameterizedType) {
      ParameterizedType aType = (ParameterizedType) type;
      Type t = aType.getActualTypeArguments()[0];
      return t.getClass();
    }
    return null;
  }

  private Method findAdder(Method method, Field field) {
    Method adder = null;
    // property is very special
    if (method.getName().equals("addProperty") && capitalizedName(field).equals("Properties")) {
      adder = method;
    }
    // non plural name
    if (method.getName().equals("add" + capitalizedName(field))) {
      adder = method;
    }
    // remove plural 's' from field
    if (method.getName().equals("add" + capitalizedName(field, true))) {
      adder = method;
    }

    // change -ies to -y
    if (method.getName().equals("add" + capitalizedName(field).replace("ies", "y"))) {
      adder = method;
    }

    // change -ses to -s
    if (method.getName().equals("add" + capitalizedName(field).replace("ses", "s"))) {
      adder = method;
    }

    // remove 'List' from field
    if (method.getName().equals("add" + capitalizedName(field).replace("List", ""))) {
      adder = method;
    }
    return adder;
  }

  private Method findRemover(Method method, Field field) {
    Method remover = null;
    // property is very special
    if (method.getName().equals("removeProperty") && capitalizedName(field).equals("Properties")) {
      remover = method;
    }
    // non plural name
    if (method.getName().equals("remove" + capitalizedName(field))) {
      remover = method;
    }
    // remove plural 's' from field
    if (method.getName().equals("remove" + capitalizedName(field, true))) {
      remover = method;
    }

    // change -ies to -y
    if (method.getName().equals("remove" + capitalizedName(field).replace("ies", "y"))) {
      remover = method;
    }

    // change -ses to -s
    if (method.getName().equals("remove" + capitalizedName(field).replace("ses", "s"))) {
      remover = method;
    }

    // remove 'List' from field
    if (method.getName().equals("remove" + capitalizedName(field).replace("List", ""))) {
      remover = method;
    }
    return remover;
  }
}
