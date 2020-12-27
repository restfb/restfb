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

import com.restfb.BinaryAttachment;

import java.util.Objects;

public class BinaryAttachmentAssert extends AbstractAssert<BinaryAttachmentAssert, BinaryAttachment> {

  public BinaryAttachmentAssert(BinaryAttachment actual) {
    super(actual, BinaryAttachmentAssert.class);
  }

  public static BinaryAttachmentAssert assertThat(BinaryAttachment actual) {
    return new BinaryAttachmentAssert(actual);
  }

  public BinaryAttachmentAssert hasFileName(String filename) {
    isNotNull();

    if (!Objects.deepEquals(actual.getFilename(), filename)) {
      failWithMessage("Expected filename should be <%s> but was <%s>", filename, actual.getFilename());
    }

    return this;
  }

  public BinaryAttachmentAssert hasContentType(String contentType) {
    isNotNull();

    if (!Objects.deepEquals(actual.getContentType(), contentType)) {
      failWithMessage("Expected content type should be <%s> but was <%s>", contentType, actual.getContentType());
    }

    return this;
  }
}
