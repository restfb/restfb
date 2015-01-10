/*
 * Copyright (c) 2010-2015 Norbert Bartels.
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
package com.restfb.types.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ApiCheckTest extends BaseTestCheck {

  @Test
  public void check() throws IOException, ClassNotFoundException {
    Properties props;
    props = new Properties();
    props.load(getClass().getResourceAsStream("/pre-1.7-api.properties"));
    for (Map.Entry<Object, Object> entrySet : props.entrySet()) {
      String key = (String) entrySet.getKey();
      String value = (String) entrySet.getValue();

      Class c = Class.forName("com.restfb.types." + key.replace(".methods", "").replace(".", "$"));
      Set<String> currentMethods = fetchMethodsFromClass(c);

      Set<String> expectedMethods = new HashSet<String>();
      if (value != null && !value.isEmpty()) {
        String[] methods = value.split(",");
        expectedMethods = new HashSet<String>(Arrays.asList(methods));
      }
      if (expectedMethods.size() > 0) {
        Iterator<String> expIterator = expectedMethods.iterator();
        while (expIterator.hasNext()) {
          String expMethod = expIterator.next();
          assertTrue(key, currentMethods.contains(expMethod));
        }
      }
    }
  }

}
