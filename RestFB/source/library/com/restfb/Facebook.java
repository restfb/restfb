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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies how a Facebook JSON response attribute maps to a Java field.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Facebook {
  /**
   * Name of the Facebook API result attribute to map to - {@code affiliation},
   * for example.
   * 
   * @return Name of the Facebook API result attribute to map to.
   */
  String value() default "";

  /**
   * If this annotation is placed on a {@code List} type, this attribute
   * specifies what type of object is contained in the list (necessary due to
   * type erasure).
   * <p>
   * This attribute is required when this annotation is applied to a {@code
   * List} type when mapping from JSON to Java and is ignored otherwise.
   * 
   * @return If the annotated field is of type {@code List}, the type of object
   *         contained in the list. Defaults to a dummy class if none is
   *         explicitly specified.
   */
  Class<?> contains() default DefaultEnumClass.class;
}