/**
 * Copyright (c) 2010-2015 Mark Allen, Norbert Bartels.
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
package com.restfb.exception.generator;

import static org.junit.Assert.*;

import com.restfb.exception.ResponseErrorJsonParsingException;

import com.restfb.exception.generator.DefaultFacebookExceptionGenerator;
import org.junit.Test;

public class FacebookExceptionGeneratorTest {

  @Test
  public void checkSkipErrorResponseParsing_NoJsonObject() {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    String json = "testString";
    try {
      generator.skipResponseStatusExceptionParsing(json);
      fail("Exception not thrown");
    } catch (ResponseErrorJsonParsingException e) {
      // every thing fine;
    }
  }

  @Test
  public void checkSkipErrorResponseParsing_JsonObjectWithoutError() {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    String json = "{ \"testString\": \"example\" }";
    try {
      generator.skipResponseStatusExceptionParsing(json);
      fail("Exception not thrown");
    } catch (ResponseErrorJsonParsingException e) {
      // every thing fine;
    }
  }

  @Test
  public void checkSkipErrorResponseParsing_JsonObjectWithError() {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    String json = "{ \"error\": \"exampleError\" }";
    try {
      generator.skipResponseStatusExceptionParsing(json);
      // every thing fine;
    } catch (ResponseErrorJsonParsingException e) {
      fail("Exception not thrown");
    }
  }

  @Test
  public void checkSilentJsonObjectGeneration_WithValidJson() {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    String json = "{ \"error\": \"exampleError\" }";
    assertNotNull(generator.silentlyCreateObjectFromString(json));
  }

  @Test
  public void checkSilentJsonObjectGeneration_WithInvalidJson() {
    DefaultFacebookExceptionGenerator generator = new DefaultFacebookExceptionGenerator();
    String json = "{ \"error\"}";
    assertNull(generator.silentlyCreateObjectFromString(json));
  }

}
