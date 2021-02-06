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

import com.restfb.exception.FacebookJsonMappingException;

import static java.lang.String.format;
import static java.util.Collections.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * A collection of reflection-related utility methods.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author Igor Kabiljo
 * @author Scott Hernandez
 * @since 1.6
 */
public final class ReflectionUtils {
  /**
   * In-memory shared cache of reflection data for {@link #findFieldsWithAnnotation(Class, Class)}.
   */
  private static final Map<ClassAnnotationCacheKey, List<?>> FIELDS_WITH_ANNOTATION_CACHE =
      synchronizedMap(new HashMap<ClassAnnotationCacheKey, List<?>>());

  /**
   * In-memory shared cache of reflection data for {@link #findMethodsWithAnnotation(Class, Class)}.
   */
  private static final Map<ClassAnnotationCacheKey, List<Method>> METHODS_WITH_ANNOTATION_CACHE =
      synchronizedMap(new HashMap<ClassAnnotationCacheKey, List<Method>>());

  /**
   * Prevents instantiation.
   */
  private ReflectionUtils() {
    // prevent instantiation
  }

  /**
   * Is the given {@code object} a primitive type or wrapper for a primitive type?
   * 
   * @param object
   *          The object to check for primitive-ness.
   * @return {@code true} if {@code object} is a primitive type or wrapper for a primitive type, {@code false}
   *         otherwise.
   */
  public static boolean isPrimitive(Object object) {
    if (object == null) {
      return false;
    }

    Class<?> type = object.getClass();

    return object instanceof String || (object instanceof Integer || Integer.TYPE.equals(type))
        || (object instanceof Boolean || Boolean.TYPE.equals(type))
        || (object instanceof Long || Long.TYPE.equals(type)) || (object instanceof Double || Double.TYPE.equals(type))
        || (object instanceof Float || Float.TYPE.equals(type)) || (object instanceof Byte || Byte.TYPE.equals(type))
        || (object instanceof Short || Short.TYPE.equals(type))
        || (object instanceof Character || Character.TYPE.equals(type));
  }

  /**
   * Finds fields on the given {@code type} and all of its superclasses annotated with annotations of type
   * {@code annotationType}.
   * 
   * @param <T>
   *          The annotation type.
   * @param type
   *          The target type token.
   * @param annotationType
   *          The annotation type token.
   * @return A list of field/annotation pairs.
   */
  public static <T extends Annotation> List<FieldWithAnnotation<T>> findFieldsWithAnnotation(Class<?> type,
      Class<T> annotationType) {
    ClassAnnotationCacheKey cacheKey = new ClassAnnotationCacheKey(type, annotationType);

    @SuppressWarnings("unchecked")
    List<FieldWithAnnotation<T>> cachedResults =
        (List<FieldWithAnnotation<T>>) FIELDS_WITH_ANNOTATION_CACHE.get(cacheKey);

    if (cachedResults != null) {
      return cachedResults;
    }

    List<FieldWithAnnotation<T>> fieldsWithAnnotation = new ArrayList<>();

    // Walk all superclasses looking for annotated fields until we hit Object
    while (!Object.class.equals(type) && type != null) {
      for (Field field : type.getDeclaredFields()) {
        T annotation = field.getAnnotation(annotationType);
        if (annotation != null) {
          fieldsWithAnnotation.add(new FieldWithAnnotation<>(field, annotation));
        }

      }

      type = type.getSuperclass();
    }

    fieldsWithAnnotation = unmodifiableList(fieldsWithAnnotation);
    FIELDS_WITH_ANNOTATION_CACHE.put(cacheKey, fieldsWithAnnotation);
    return fieldsWithAnnotation;
  }

  /**
   * Finds methods on the given {@code type} and all of its superclasses annotated with annotations of type
   * {@code annotationType}.
   * <p>
   * These results are cached to mitigate performance overhead.
   * 
   * @param <T>
   *          The annotation type.
   * @param type
   *          The target type token.
   * @param annotationType
   *          The annotation type token.
   * @return A list of methods with the given annotation.
   * @since 1.6.11
   */
  public static <T extends Annotation> List<Method> findMethodsWithAnnotation(Class<?> type, Class<T> annotationType) {
    ClassAnnotationCacheKey cacheKey = new ClassAnnotationCacheKey(type, annotationType);
    List<Method> cachedResults = METHODS_WITH_ANNOTATION_CACHE.get(cacheKey);

    if (cachedResults != null) {
      return cachedResults;
    }

    List<Method> methodsWithAnnotation = new ArrayList<>();

    // Walk all superclasses looking for annotated methods until we hit Object
    while (!Object.class.equals(type)) {
      for (Method method : type.getDeclaredMethods()) {
        T annotation = method.getAnnotation(annotationType);

        if (annotation != null) {
          methodsWithAnnotation.add(method);
        }
      }

      type = type.getSuperclass();
    }

    methodsWithAnnotation = unmodifiableList(methodsWithAnnotation);
    METHODS_WITH_ANNOTATION_CACHE.put(cacheKey, methodsWithAnnotation);
    return methodsWithAnnotation;
  }

  /**
   * For a given {@code field}, get its first parameterized type argument.
   * <p>
   * For example, a field of type {@code List<Long>} would have a first type argument of {@code Long.class}.
   * <p>
   * If the field has no type arguments, {@code null} is returned.
   * 
   * @param field
   *          The field to check.
   * @return The field's first parameterized type argument, or {@code null} if none exists.
   */
  public static Class<?> getFirstParameterizedTypeArgument(Field field) {
    return getParameterizedTypeArgument(field, 0);
  }

  /**
   * For a given {@code field}, get its second parameterized type argument.
   *
   * If the field has no type arguments, {@code null} is returned.
   *
   * @param field
   *          The field to check.
   * @return The field's second parameterized type argument, or {@code null} if none exists.
   */
  public static Class<?> getSecondParameterizedTypeArgument(Field field) {
    return getParameterizedTypeArgument(field, 1);
  }

  private static Class<?> getParameterizedTypeArgument(Field field, int i) {
    Type type = field.getGenericType();
    if (!(type instanceof ParameterizedType)) {
      return null;
    }

    ParameterizedType parameterizedType = (ParameterizedType) type;
    Type firstTypeArgument = parameterizedType.getActualTypeArguments()[i];
    return (firstTypeArgument instanceof Class) ? (Class<?>) firstTypeArgument : null;
  }

  /**
   * Gets all accessor methods for the given {@code clazz}.
   * 
   * @param clazz
   *          The class for which accessors are extracted.
   * @return All accessor methods for the given {@code clazz}.
   */
  public static List<Method> getAccessors(Class<?> clazz) {
    if (clazz == null) {
      throw new IllegalArgumentException("The 'clazz' parameter cannot be null.");
    }

    List<Method> methods = new ArrayList<>();
    for (Method method : clazz.getMethods()) {
      String methodName = method.getName();
      if (!"getClass".equals(methodName) && !"hashCode".equals(methodName) && method.getReturnType() != null
          && !Void.class.equals(method.getReturnType()) && method.getParameterTypes().length == 0
          && ((methodName.startsWith("get") && methodName.length() > 3)
              || (methodName.startsWith("is") && methodName.length() > 2)
              || (methodName.startsWith("has") && methodName.length() > 3))) {
        methods.add(method);
      }
    }

    // Order the methods alphabetically by name
    sort(methods, new Comparator<Method>() {
      /**
       * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
       */
      @Override
      public int compare(Method method1, Method method2) {
        return method1.getName().compareTo(method2.getName());
      }
    });

    return unmodifiableList(methods);
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
      if (first) {
        first = false;
      } else {
        buffer.append(" ");
      }

      try {
        String methodName = method.getName();
        int offset = methodName.startsWith("is") ? 2 : 3;
        methodName = methodName.substring(offset, offset + 1).toLowerCase() + methodName.substring(offset + 1);

        buffer.append(methodName);
        buffer.append("=");

        if (!method.isAccessible()) {
          method.setAccessible(true);
        }

        // Accessors are guaranteed to take no parameters and return a value
        buffer.append(method.invoke(object));
      } catch (Exception e) {
        throw new IllegalStateException("Unable to reflectively invoke " + method + " on " + object.getClass(), e);
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
    if (object == null) {
      return 0;
    }

    int hashCode = 17;

    for (Method method : getAccessors(object.getClass())) {
      try {
        if (!method.isAccessible()) {
          method.setAccessible(true);
        }

        Object result = method.invoke(object);
        if (result != null) {
          hashCode = hashCode * 31 + result.hashCode();
        }
      } catch (Exception e) {
        throw new IllegalStateException("Unable to reflectively invoke " + method + " on " + object, e);
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
    if (object1 == null && object2 == null) {
      return true;
    }
    if (!(object1 != null && object2 != null)) {
      return false;
    }

    // Bail if the classes aren't at least one-way assignable to each other
    if (!(object1.getClass().isInstance(object2) || object2.getClass().isInstance(object1))) {
      return false;
    }

    // Only compare accessors that are present in both classes
    Set<Method> accessorMethodsIntersection = new HashSet<>(getAccessors(object1.getClass()));
    accessorMethodsIntersection.retainAll(getAccessors(object2.getClass()));

    for (Method method : accessorMethodsIntersection) {
      try {
        if (!method.isAccessible()) {
          method.setAccessible(true);
        }

        Object result1 = method.invoke(object1);
        Object result2 = method.invoke(object2);
        if (result1 == null && result2 == null) {
          continue;
        }
        if (!(result1 != null && result2 != null)) {
          return false;
        }
        if (!result1.equals(result2)) {
          return false;
        }
      } catch (Exception e) {
        throw new IllegalStateException("Unable to reflectively invoke " + method, e);
      }
    }

    return true;
  }

  /**
   * Creates a new instance of the given {@code type}.
   * <p>
   *
   *
   * @param <T>
   *          Java type to map to.
   * @param type
   *          Type token.
   * @return A new instance of {@code type}.
   * @throws FacebookJsonMappingException
   *           If an error occurs when creating a new instance ({@code type} is inaccessible, doesn't have a no-arg
   *           constructor, etc.)
   */
  public static <T> T createInstance(Class<T> type) {
    String errorMessage = "Unable to create an instance of " + type
            + ". Please make sure that if it's a nested class, is marked 'static'. "
            + "It should have a no-argument constructor.";

    try {
      Constructor<T> defaultConstructor = type.getDeclaredConstructor();

      if (defaultConstructor == null) {
        throw new FacebookJsonMappingException("Unable to find a default constructor for " + type);
      }

      // Allows protected, private, and package-private constructors to be
      // invoked
      defaultConstructor.setAccessible(true);
      return defaultConstructor.newInstance();
    } catch (Exception e) {
      throw new FacebookJsonMappingException(errorMessage, e);
    }
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

    @Override
    public String toString() {
      return format("Field %s.%s (%s): %s", field.getDeclaringClass().getName(), field.getName(), field.getType(),
        annotation);
    }
  }

  /**
   * Cache key composed of a class and annotation pair. Used by {@link ReflectionUtils#FIELDS_WITH_ANNOTATION_CACHE}.
   * 
   * @author Igor Kabiljo
   */
  private static final class ClassAnnotationCacheKey {
    /**
     * Class component of this cache key.
     */
    private final Class<?> clazz;

    /**
     * Annotation component of this cache key.
     */
    private final Class<? extends Annotation> annotation;

    /**
     * Creates a cache key with the given {@code clazz}/@{code annotation} pair.
     * 
     * @param clazz
     *          Class component of this cache key.
     * @param annotation
     *          Annotation component of this cache key.
     */
    private ClassAnnotationCacheKey(Class<?> clazz, Class<? extends Annotation> annotation) {
      this.clazz = clazz;
      this.annotation = annotation;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (annotation == null ? 0 : annotation.hashCode());
      result = prime * result + (clazz == null ? 0 : clazz.hashCode());
      return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }

      ClassAnnotationCacheKey other = (ClassAnnotationCacheKey) obj;

      if (annotation == null) {
        if (other.annotation != null) {
          return false;
        }
      } else if (!annotation.equals(other.annotation)) {
        return false;
      }

      if (clazz == null) {
        if (other.clazz != null) {
          return false;
        }
      } else if (!clazz.equals(other.clazz)) {
        return false;
      }

      return true;
    }
  }
}