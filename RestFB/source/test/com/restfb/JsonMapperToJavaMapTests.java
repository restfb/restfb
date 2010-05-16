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

import static junit.framework.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class JsonMapperToJavaMapTests extends AbstractJsonMapperTests {
  /**
   * Can we handle the empty map?
   */
  @Test
  public void emptyMap() throws FacebookJsonMappingException {
    Map<String, Object> object = createJsonMapper().toJavaObject("{}");
    assertTrue(object.size() == 0);
  }

  /**
   * Can we handle the empty list?
   */
  @Test
  public void emptyList() throws FacebookJsonMappingException {
    List<Object> list = createJsonMapper().toJavaList("[]");
    assertTrue(list.size() == 0);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void userWithPhotos() throws FacebookJsonMappingException {
    Map<String, Object> object =
        createJsonMapper().toJavaObject(jsonFromClasspath("user-with-photos"));

    List<Object> photos = (List<Object>) object.get("photos");
    String name = (String) object.get("name");

    assertTrue(object.size() == 3);
    assertTrue(object.containsKey("uid"));
    assertTrue(object.containsKey("photos"));
    assertTrue(object.containsKey("name"));
    assertTrue(photos.size() == 2);
    assertTrue("Test Person".equals(name));
  }
}