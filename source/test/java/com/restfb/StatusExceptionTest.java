/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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

import static org.junit.Assert.*;

import com.restfb.exception.FacebookGraphException;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class StatusExceptionTest extends AbstractJsonMapperTests {

  @Test
  public void statusException() throws JSONException {
    String jsonErrorString = jsonFromClasspath("exampleError");
    DefaultFacebookClient client = new DefaultFacebookClient(Version.LATEST);
    try {
      client.throwFacebookResponseStatusExceptionIfNecessary(jsonErrorString, 400);
      fail();
    } catch (FacebookGraphException fe) {
      assertEquals("A title", fe.getErrorUserTitle());
      assertEquals("A message", fe.getErrorUserMessage());
      assertEquals(190, fe.getErrorCode().intValue());
      assertEquals(460, fe.getErrorSubcode().intValue());
      assertEquals("Message describing the error", fe.getErrorMessage());
      assertNotNull(fe.getRawErrorJson());
      JSONAssert.assertEquals(jsonErrorString, fe.getRawErrorJson().toString(), false);
      assertEquals("", fe.getFbtraceId());
    }
  }

  @Test
  public void statusExceptionWithFbtraceId() throws JSONException {
    String jsonErrorString = jsonFromClasspath("exampleErrorNew");
    DefaultFacebookClient client = new DefaultFacebookClient(Version.LATEST);
    try {
      client.throwFacebookResponseStatusExceptionIfNecessary(jsonErrorString, 400);
      fail();
    } catch (FacebookGraphException fe) {
      assertEquals("A title", fe.getErrorUserTitle());
      assertEquals("A message", fe.getErrorUserMessage());
      assertEquals(190, fe.getErrorCode().intValue());
      assertEquals(460, fe.getErrorSubcode().intValue());
      assertEquals("Message describing the error", fe.getErrorMessage());
      assertNotNull(fe.getRawErrorJson());
      JSONAssert.assertEquals(jsonErrorString, fe.getRawErrorJson().toString(), false);
      assertEquals("EJplcsCHuLu", fe.getFbtraceId());
    }
  }

}
