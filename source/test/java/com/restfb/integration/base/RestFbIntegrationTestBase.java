/*
 * Copyright (c) 2010-2015 Norbert Bartels
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

package com.restfb.integration.base;

import java.io.InputStream;
import java.util.Properties;
import org.junit.Assume;
import org.junit.Before;

abstract public class RestFbIntegrationTestBase {

  private RestFbIntegrationTestSettings testSettings = null;

  @Before
  public void setup() {
   
      InputStream propertyStream = getClass().getClassLoader().getResourceAsStream("integration-test.properties");
      Assume.assumeNotNull(propertyStream);
      Properties props = new Properties();
      try {
	props.load(propertyStream);
	testSettings = new RestFbIntegrationTestSettings(props);
	if (getClass().getAnnotation(NeedFacebookWriteAccess.class) != null) {
	    Assume.assumeTrue(testSettings.writeAccessAllowed());
	}
      }
      catch (Throwable ioe) {
	  Assume.assumeNoException(ioe);
      }
      
  }

  protected RestFbIntegrationTestSettings getTestSettings() {
    return testSettings;
  }

}
