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

package com.restfb.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A collection of reflection-related utility methods.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author ScottHernandez
 * @since 1.6
 */
public final class ReflectionUtils {
  /**
   * Prevents instantiation.
   */
  private ReflectionUtils() {}

  /**
   * Is the given {@code object} a primitive type or wrapper for a primitive
   * type?
   * 
   * @param object
   *          The object to check for primitive-ness.
   * @return {@code true} if {@code object} is a primitive type or wrapper for a
   *         primitive type, {@code false} otherwise.
   */
  public static boolean isPrimitive(Object object) {
    if (object == null)
      return false;

    Class<?> type = object.getClass();

    return object instanceof String
        || (object instanceof Integer || Integer.TYPE.equals(type))
        || (object instanceof Boolean || Boolean.TYPE.equals(type))
        || (object instanceof Long || Long.TYPE.equals(type))
        || (object instanceof Double || Double.TYPE.equals(type))
        || (object instanceof Float || Float.TYPE.equals(type))
        || (object instanceof Byte || Byte.TYPE.equals(type))
        || (object instanceof Short || Short.TYPE.equals(type))
        || (object instanceof Character || Character.TYPE.equals(type));
  }

  /**
   * Finds fields on the given {@code type} and all of its superclasses
   * annotated with annotations of type {@code annotationType}.
   * 
   * @param <T>
   *          The annotation type.
   * @param type
   *          The target type token.
   * @param annotationType
   *          The annotation type token.
   * @return A list of field/annotation pairs.
   */
  public static <T extends Annotation> List<FieldWithAnnotation<T>> findFieldsWithAnnotation(
      Class<?> type, Class<T> annotationType) {
    // TODO: cache off results per type instead of reflecting every time

    List<FieldWithAnnotation<T>> fieldsWithAnnotation =
        new ArrayList<FieldWithAnnotation<T>>();

    // Walk all superclasses looking for annotated fields until we hit
    // Object
    while (!Object.class.equals(type)) {
      for (Field field : type.getDeclaredFields()) {
        T annotation = field.getAnnotation(annotationType);

        if (annotation != null)
          fieldsWithAnnotation
            .add(new FieldWithAnnotation<T>(field, annotation));
      }

      type = type.getSuperclass();
    }

    return Collections.unmodifiableList(fieldsWithAnnotation);
  }

  /**
   * Gets all accessor methods for the given {@code clazz}.
   * 
   * @param clazz
   *          The class for which accessors are extracted.
   * @return All accessor methods for the given {@code clazz}.
   */
  public static List<Method> getAccessors(Class<?> clazz) {
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

  /**
   * TODO: document and test
   * 
   * @param <T>
   * @param type
   * @param annotationType
   * @return
   */
  public static <T extends Annotation> List<MethodWithAnnotation<T>> findMethodsWithAnnotation(
      Class<?> type, Class<T> annotationType) {
    // TODO: cache off results per type instead of reflecting every time

    List<MethodWithAnnotation<T>> methodsWithAnnotation =
        new ArrayList<MethodWithAnnotation<T>>();

    // Walk all superclasses looking for annotated methods until we hit
    // Object
    while (!Object.class.equals(type)) {
      if (type == null)
        break;

      for (Method method : type.getDeclaredMethods()) {
        T annotation = method.getAnnotation(annotationType);
        if (annotation != null)
          methodsWithAnnotation.add(new MethodWithAnnotation<T>(method,
            annotation));
      }

      type = type.getSuperclass();
    }

    return Collections.unmodifiableList(methodsWithAnnotation);
  }

  /**
   * Get the (first) class that parameterizes the Field supplied.
   * 
   * TODO: finish docs, test
   * 
   * @param field
   *          the field
   * @return the class that parameterizes the field, or null if field is not
   *         parameterized
   */
  public static Class<?> getParameterizedClass(final Field field) {
    return getParameterizedClass(field, 0);
  }

  /**
   * Get the class that parameterizes the Field supplied, at the index supplied
   * (field can be parameterized with multiple param classes).
   * 
   * TODO: finish docs, test
   * 
   * @param field
   *          the field
   * @param index
   *          the index of the parameterizing class
   * @return the class that parameterizes the field, or null if field is not
   *         parameterized
   */
  public static Class<?> getParameterizedClass(final Field field,
      final int index) {
    if (field.getGenericType() instanceof ParameterizedType) {
      ParameterizedType ptype = (ParameterizedType) field.getGenericType();
      if ((ptype.getActualTypeArguments() != null)
          && (ptype.getActualTypeArguments().length <= index)) {
        return null;
      }
      Type paramType = ptype.getActualTypeArguments()[index];
      if (paramType instanceof GenericArrayType) {
        Class<?> arrayType =
            (Class<?>) ((GenericArrayType) paramType).getGenericComponentType();
        return Array.newInstance(arrayType, 0).getClass();
      } else {
        if (paramType instanceof ParameterizedType) {
          ParameterizedType paramPType = (ParameterizedType) paramType;
          return (Class<?>) paramPType.getRawType();
        } else {
          if (paramType instanceof TypeVariable<?>) {
            // TODO: Figure out what to do... Walk back up the to
            // the parent class and try to get the variable type
            // from the T/V/X
            throw new RuntimeException("Generic Typed Class not supported:  <"
                + ((TypeVariable<?>) paramType).getName() + "> = "
                + ((TypeVariable<?>) paramType).getBounds()[0]);
          } else if (paramType instanceof Class<?>) {
            return (Class<?>) paramType;
          } else {
            throw new RuntimeException(
              "Unknown type... pretty bad... call for help, wave your hands... yeah!");
          }
        }
      }
    }
    return null;
  }

  /**
   * Reflection-based implementation of {@link Object#toString()}.
   * 
   * @param object
   *          The object to convert to a string representation.
   * @return A string representation of {@code object}.
   * @throws IllegalStateException
   *           If an error occurs while performing reflection operations.
   */
  public static String toString(Object object) {
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
            + method + " on " + object.getClass(), e);
      }
    }

    buffer.append("]");
    return buffer.toString();
  }

  /**
   * Reflection-based implementation of {@link Object#hashCode()}.
   * 
   * @param object
   *          The object to hash.
   * @return A hashcode for {@code object}.
   * @throws IllegalStateException
   *           If an error occurs while performing reflection operations.
   */
  public static int hashCode(Object object) {
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

  /**
   * Reflection-based implementation of {@link Object#equals(Object)}.
   * 
   * @param object1
   *          One object to compare.
   * @param object2
   *          Another object to compare.
   * @return {@code true} if the objects are equal, {@code false} otherwise.
   * @throws IllegalStateException
   *           If an error occurs while performing reflection operations.
   */
  public static boolean equals(Object object1, Object object2) {
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

  /**
   * A field/annotation pair.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   */
  public static class FieldWithAnnotation<T extends Annotation> {
    /**
     * A field.
     */
    private Field field;

    /**
     * An annotation on the field.
     */
    private T annotation;

    /**
     * Creates a field/annotation pair.
     * 
     * @param field
     *          A field.
     * @param annotation
     *          An annotation on the field.
     */
    public FieldWithAnnotation(Field field, T annotation) {
      this.field = field;
      this.annotation = annotation;
    }

    /**
     * Gets the field.
     * 
     * @return The field.
     */
    public Field getField() {
      return field;
    }

    /**
     * Gets the annotation on the field.
     * 
     * @return The annotation on the field.
     */
    public T getAnnotation() {
      return annotation;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return String.format("Field %s.%s (%s): %s", field.getDeclaringClass()
        .getName(), field.getName(), field.getType(), annotation);
    }
  }

  /**
   * A method/annotation pair.
   * 
   * @author ScottHernandez
   */
  public static class MethodWithAnnotation<T extends Annotation> {
    /**
     * A method.
     */
    private Method method;

    /**
     * An annotation on the method.
     */
    private T annotation;

    /**
     * Creates a method/annotation pair.
     * 
     * @param method
     *          A method.
     * @param annotation
     *          An annotation on the method.
     */
    public MethodWithAnnotation(Method method, T annotation) {
      this.method = method;
      this.annotation = annotation;
    }

    /**
     * Gets the method.
     * 
     * @return The method.
     */
    public Method getMethod() {
      return method;
    }

    /**
     * Gets the annotation on the method.
     * 
     * @return The annotation on the method.
     */
    public T getAnnotation() {
      return annotation;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return String.format("Method %s %s.%s : %s", method.getReturnType(),
        method.getDeclaringClass().getName(), method.getName(), annotation);
    }
  }
}