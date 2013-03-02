/*
 * Copyright (c) 2010-2013 Mark Allen.
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

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import com.restfb.exception.FacebookJsonMappingException;

/**
 * Specifies how a Facebook JSON-to-Java (and vice-versa) mapper must operate.
 * <p>
 * Note that implementors must be able to handle illegal JSON in {@link #toJavaObject(String, Class)} and
 * {@link #toJavaList(String, Class)} in order to correctly process Facebook responses. For example, the
 * {@code Users.getLoggedInUser} Legacy API call returns a value like {@code 1240077}, which is not valid JSON.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public interface JsonMapper {
  /**
   * Given a JSON string, create and return a new instance of a corresponding Java object of type {@code type}.
   * <p>
   * The Java {@code type} must have a no-argument constructor.
   * 
   * @param <T>
   *          Java type to map to.
   * @param json
   *          The JSON to be mapped to a Java type.
   * @param type
   *          Java type token.
   * @return A Java object (of type {@code type}) representation of the JSON input.
   * @throws FacebookJsonMappingException
   *           If an error occurs while mapping JSON to Java.
   */
  <T> T toJavaObject(String json, Class<T> type);

  /**
   * Given a JSON string, create and return a new instance of a corresponding Java {@link java.util.List} which contains
   * elements of type {@code type}.
   * <p>
   * The Java {@code type} must have a no-argument constructor.
   * 
   * @param <T>
   *          Java type to map to for each element of the list.
   * @param json
   *          The JSON to be mapped to a Java type.
   * @param type
   *          Java type token.
   * @return A Java object (of type {@code List} which contains elements of type {@code type}) representation of the
   *         JSON input.
   * @throws FacebookJsonMappingException
   *           If an error occurs while mapping JSON to Java.
   */
  <T> List<T> toJavaList(String json, Class<T> type);

  /**
   * Given a Java {@code object}, create and return a JSON string that represents it.
   * <p>
   * The {@code object}'s properties will be traversed recursively, allowing for arbitrarily complex JSON generation.
   * 
   * @param object
   *          The Java object to map to JSON. Can be a Javabean, {@link java.util.List}, or {@link java.util.Map}.
   * @return A JSON string.
   * @throws FacebookJsonMappingException
   *           If an error occurs while mapping Java to JSON.
   * @since 1.4
   */
  String toJson(Object object);

  /**
   * Given a Java {@code object}, create and return a JSON string that represents it.
   * <p>
   * The {@code object}'s properties will be traversed recursively, allowing for arbitrarily complex JSON generation.
   * 
   * @param object
   *          The Java object to map to JSON. Can be a Javabean, {@link java.util.List}, or {@link java.util.Map}.
   * @param ignoreNullValuedProperties
   *          If {@code true}, no Javabean properties with {@code null} values will be included in the generated JSON.
   * @return A JSON string.
   * @throws FacebookJsonMappingException
   *           If an error occurs while mapping Java to JSON.
   * @since 1.6.5
   */
  String toJson(Object object, boolean ignoreNullValuedProperties);

  /**
   * If you apply this annotation to a method of a type mapped by {@code JsonMapper}, it will be called after the
   * mapping operation has been completed.
   * <p>
   * The method you specify must take 0 parameters or a single {@code JsonMapper} parameter. Any other signature will
   * cause a {@code FacebookJsonMappingException} to be thrown.
   * <p>
   * This is useful if you'd like to perform a custom post-mapping task, like massaging the data Facebook returns or
   * custom mapping of fields {@code JsonMapper} isn't equipped to handle yet.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.11
   */
  @Target({ METHOD })
  @Retention(RUNTIME)
  public @interface JsonMappingCompleted {}
}