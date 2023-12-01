/*
 * Copyright (c) 2010-2024 Mark Allen, Norbert Bartels.
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

import static com.restfb.logging.RestFBLogger.getLoggerInstance;
import static com.restfb.util.StringUtils.fromInputStream;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import com.restfb.exception.FacebookJsonMappingException;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public abstract class AbstractJsonMapperTests {
  protected JsonMapper createJsonMapper() {
    return new DefaultJsonMapper();
  }

  protected JsonMapper createConnectionJsonMapper() {
    JsonMapper mapper = new DefaultJsonMapper();
    FacebookClient facebookClient = mock(FacebookClient.class);
    when(facebookClient.getJsonMapper()).thenReturn(mapper);
    mapper.setFacebookClient(facebookClient);
    return mapper;
  }

  protected JsonMapper createSwallowingJsonMapper() {
    return new DefaultJsonMapper() {

      @Override
      public <T> T toJavaObject(String json, Class<T> type) {
        try {
          return super.toJavaObject(json, type);
        } catch (FacebookJsonMappingException ex) {
          getLoggerInstance("ErrorSwallowingJsonMapper")
            .info("Ignored failed mapping to {}. Bad JSON was '{}' and exception was '{}'", type, json, ex.getMessage());
          return null;
        }
      }

      @Override
      public <T> List<T> toJavaList(String json, Class<T> type) {
        try {
          return super.toJavaList(json, type);
        } catch (FacebookJsonMappingException ex) {
          getLoggerInstance("ErrorSwallowingJsonMapper")
            .info("Ignored failed mapping to {}. Bad JSON was '{}' and exception was '{}'", type, json, ex.getMessage());
          return null;
        }
      }
    };
  }

  protected String jsonFromClasspath(String pathToJson) {
    try {
      return fromInputStream(getClass().getResourceAsStream("/json/" + pathToJson + ".json"));
    } catch (IOException e) {
      throw new IllegalStateException("Unable to load JSON from the classpath", e);
    }
  }
}