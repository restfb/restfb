/*
 * Copyright (c) 2010-2012 Mark Allen.
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

import static com.restfb.util.StringUtils.fromInputStream;
import static java.lang.String.format;
import static java.util.logging.Logger.getLogger;

import java.io.IOException;

import com.restfb.DefaultJsonMapper.JsonMappingErrorHandler;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public abstract class AbstractJsonMapperTests {
  protected JsonMapper createJsonMapper() {
    return new DefaultJsonMapper();
  }

  protected JsonMapper createErrorSwallowingJsonMapper() {
    return new DefaultJsonMapper(new JsonMappingErrorHandler() {
      public boolean handleMappingError(String unmappableJson, Class<?> targetType, Exception e) {
        getLogger("ErrorSwallowingJsonMapper").info(
          format("Ignored failed mapping to %s. " + "Bad JSON was '%s' and exception was %s", targetType,
            unmappableJson, e));
        return true;
      }
    });
  }

  protected String jsonFromClasspath(String pathToJson) {
    try {
      return fromInputStream(getClass().getResourceAsStream("/json/" + pathToJson + ".json"));
    } catch (IOException e) {
      throw new IllegalStateException("Unable to load JSON from the classpath", e);
    }
  }
}