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
package com.restfb.experimental.api.impl;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.experimental.api.Applications;
import com.restfb.json.JsonObject;
import com.restfb.types.Subscription;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ApplicationsImpl implements Applications {

  private static final String SUBSCRIPTIONS_ENDPOINT = "/subscriptions";

  private final FacebookClient facebookClient;

  ApplicationsImpl(FacebookClient facebookClient) {
    this.facebookClient = facebookClient;
  }

  @Override
  public List<Subscription> fetchSubscriptions(String appId) {
    Connection<Subscription> subscriptionConn =
        facebookClient.fetchConnection(appId + SUBSCRIPTIONS_ENDPOINT, Subscription.class);

    List<Subscription> subList = new ArrayList<Subscription>();

    for (List<Subscription> subscriptionList : subscriptionConn) {
      for (Subscription subscription : subscriptionList) {
        subList.add(subscription);
      }
    }

    return subList;
  }

  @Override
  public boolean createSubscription(String appId, String verifyToken, Subscription subscription) {
    Parameter verifyTokenParam = Parameter.with("verify_token", verifyToken);
    Parameter objectParam = Parameter.with("object", subscription.getObject());
    Parameter callbackParam = Parameter.with("callback_url", subscription.getCallbackUrl());

    String fieldString = "";
    for (String field : subscription.getFields()) {
      fieldString += "," + field;
    }

    if (!fieldString.isEmpty()) {
      fieldString = fieldString.substring(1);
    }

    Parameter fieldsParam = Parameter.with("fields", fieldString);

    JsonObject returnValue = facebookClient.publish(appId + SUBSCRIPTIONS_ENDPOINT, JsonObject.class, verifyTokenParam,
      objectParam, callbackParam, fieldsParam);
    return returnValue.optBoolean("success", false);
  }

  @Override
  public boolean removeSubscription(String appId, String object) {
    return facebookClient.deleteObject(appId + SUBSCRIPTIONS_ENDPOINT, Parameter.with("object", object));
  }
}
