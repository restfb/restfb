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

package com.restfb.integration;

import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import com.restfb.types.Event;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class FetchEventsIssue252 extends RestFbIntegrationTestBase {

  protected final String SELECTED_FIELDS =
      "id,name,place,owner,description,timezone,ticket_uri,cover,start_time,end_time,updated_time,is_date_only";

  @Test
  public void fetchEvent_1() {
    DefaultFacebookClient client =
        new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.VERSION_2_3);
    Event event = client.fetchObject("1074127502604196", Event.class, Parameter.with("fields", SELECTED_FIELDS));
    assertNotNull(event);
    assertNotNull(event.getPlace());
    assertNotNull(event.getPlace().getLocation());
  }

  @Test
  public void fetchEvent_2() {
    DefaultFacebookClient client =
        new DefaultFacebookClient(getTestSettings().getUserAccessToken(), Version.VERSION_2_3);
    Event event = client.fetchObject("300473363410132", Event.class, Parameter.with("fields", SELECTED_FIELDS));
    assertNotNull(event);
    assertNotNull(event.getPlace());
    assertNotNull(event.getPlace().getLocation());
  }

}
