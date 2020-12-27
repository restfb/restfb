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
package com.restfb.testutils;

import com.restfb.Parameter;

import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

public class ParameterAssert extends AbstractAssert<ParameterAssert, Parameter> {

  public ParameterAssert(Parameter actual) {
    super(actual, ParameterAssert.class);
  }

  public static ParameterAssert assertThat(Parameter actual) {
    return new ParameterAssert(actual);
  }

  public ParameterAssert hasName(String name) {
    isNotNull();

    if (!Objects.deepEquals(actual.name, name)) {
      failWithMessage("Expected name should be <%s> but was <%s>", name, actual.name);
    }

    return this;
  }

  public ParameterAssert hasValue(Object value) {
    isNotNull();

    if (!Objects.deepEquals(actual.value, value)) {
      failWithMessage("Expected value should be <%s> but was <%s>", value, actual.value);
    }

    return this;
  }
}
