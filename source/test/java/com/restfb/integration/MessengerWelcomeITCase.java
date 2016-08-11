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
package com.restfb.integration;

import static org.junit.Assert.assertEquals;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.integration.base.RestFbIntegrationTestBase;
import com.restfb.json.JsonObject;
import com.restfb.types.send.CallToAction;
import com.restfb.types.send.Greeting;
import com.restfb.types.send.Message;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MessengerWelcomeITCase extends RestFbIntegrationTestBase {

  @Test
  public void setGreeting() {
    String pageAccessToken = getTestSettings().getPageAccessToken();

    FacebookClient client = new DefaultFacebookClient(pageAccessToken, Version.VERSION_2_6);

    Greeting greeting = new Greeting("Welcome and chat with the Testbot");

    JsonObject response = client.publish(getTestSettings().getPageId() + "/thread_settings", JsonObject.class,
      Parameter.with("setting_type", "greeting"), Parameter.with("greeting", greeting));

    assertEquals("Successfully updated greeting", response.getString("result"));
  }
  
  @Test
  public void setWelcomeMessage() {
    String pageAccessToken = getTestSettings().getPageAccessToken();

    FacebookClient client = new DefaultFacebookClient(pageAccessToken, Version.VERSION_2_6);

    Message simpleTextMessage = new Message("Welcome and chat with the Testbot");
    CallToAction welcome = new CallToAction(simpleTextMessage);

    List<CallToAction> actionList = new ArrayList<CallToAction>();
    actionList.add(welcome);

    JsonObject response = client.publish(getTestSettings().getPageId() + "/thread_settings", JsonObject.class,
      Parameter.with("setting_type", "call_to_actions"), Parameter.with("thread_state", "new_thread"),
      Parameter.with("call_to_actions", actionList));

    assertEquals("Successfully added new_thread's CTAs", response.getString("result"));
  }
}
