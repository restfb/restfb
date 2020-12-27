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

import org.assertj.core.api.AbstractAssert;

import com.restfb.batch.BatchRequest;

import java.util.Objects;

public class BatchRequestAssert extends AbstractAssert<BatchRequestAssert, BatchRequest> {

  private BatchRequestAssert(BatchRequest batchRequest) {
    super(batchRequest, BatchRequestAssert.class);
  }

  public static BatchRequestAssert assertThat(BatchRequest actual) {
    return new BatchRequestAssert(actual);
  }

  public BatchRequestAssert hasMethod(String method) {
    isNotNull();

    if (!Objects.deepEquals(actual.getMethod(), method)) {
      failWithMessage("Expected http method should be <%s> but was <%s>", method, actual.getMethod());
    }

    return this;
  }

  public BatchRequestAssert hasName(String name) {
    isNotNull();

    if (!Objects.deepEquals(actual.getName(), name)) {
      failWithMessage("Expected name should be <%s> but was <%s>", name, actual.getName());
    }

    return this;
  }

  public BatchRequestAssert hasRelativeUrl(String relativeUrl) {
    isNotNull();

    if (!Objects.deepEquals(actual.getRelativeUrl(), relativeUrl)) {
      failWithMessage("Expected relative URL should be <%s> but was <%s>", relativeUrl, actual.getRelativeUrl());
    }

    return this;
  }

  public BatchRequestAssert hasBody(String body) {
    isNotNull();

    if (!Objects.deepEquals(actual.getBody(), body)) {
      failWithMessage("Expected relative URL should be <%s> but was <%s>", body, actual.getBody());
    }

    return this;
  }

  public BatchRequestAssert hasDependsOn(String dependsOn) {
    isNotNull();

    if (!Objects.deepEquals(actual.getDependsOn(), dependsOn)) {
      failWithMessage("Expected 'depends on' should be <%s> but was <%s>", dependsOn, actual.getDependsOn());
    }

    return this;
  }

  public BatchRequestAssert hasAttachedFiles(String attachedFiles) {
    isNotNull();

    if (!Objects.deepEquals(actual.getAttachedFiles(), attachedFiles)) {
      failWithMessage("Expected attached files should be <%s> but was <%s>", attachedFiles, actual.getAttachedFiles());
    }

    return this;
  }

  public BatchRequestAssert isOmitResponseOnSuccess() {
    isNotNull();

    if (!Objects.deepEquals(actual.isOmitResponseOnSuccess(), true)) {
      failWithMessage("Expected omitResponseOnSuccess should be <%s> but was <%s>", true,
        actual.isOmitResponseOnSuccess());
    }

    return this;
  }

}
