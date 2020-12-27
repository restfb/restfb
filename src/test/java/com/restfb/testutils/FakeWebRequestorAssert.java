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

import com.restfb.FakeWebRequestor;

import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

public class FakeWebRequestorAssert extends AbstractAssert<FakeWebRequestorAssert, FakeWebRequestor> {

  public FakeWebRequestorAssert(FakeWebRequestor actual) {
    super(actual, FakeWebRequestorAssert.class);
  }

  public static FakeWebRequestorAssert assertThat(FakeWebRequestor actual) {
    return new FakeWebRequestorAssert(actual);
  }

  public FakeWebRequestorAssert isSavedUrlEqualTo(String url) {
    isNotNull();

    if (!Objects.deepEquals(actual.getSavedUrl(), url)) {
      failWithMessage("Expected saved url should be <%s> but was <%s>", url, actual.getSavedUrl());
    }

    return this;
  }

  public FakeWebRequestorAssert isParametersEqualTo(String parameters) {
    isNotNull();

    if (!Objects.deepEquals(actual.getParameters(), parameters)) {
      failWithMessage("Expected parameters should be <%s> but was <%s>", parameters, actual.getParameters());
    }

    return this;
  }
}
