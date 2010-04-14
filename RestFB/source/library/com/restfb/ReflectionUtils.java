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

package com.restfb;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Support methods for common reflection tasks.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
abstract class ReflectionUtils {
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
  static boolean isPrimitive(Object object) {
    if (object == null)
      return false;

    Class<?> type = object.getClass();

    return (object instanceof Integer || Integer.TYPE.equals(type))
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
  static <T extends Annotation> List<FieldWithAnnotation<T>> findFieldsWithAnnotation(
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
   * A field/annotation pair.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   */
  static class FieldWithAnnotation<T extends Annotation> {
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
    FieldWithAnnotation(Field field, T annotation) {
      this.field = field;
      this.annotation = annotation;
    }

    /**
     * Gets the field.
     * 
     * @return The field.
     */
    Field getField() {
      return field;
    }

    /**
     * Gets the annotation on the field.
     * 
     * @return The annotation on the field.
     */
    T getAnnotation() {
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
}