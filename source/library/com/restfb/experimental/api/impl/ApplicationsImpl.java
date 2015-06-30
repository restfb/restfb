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

package com.restfb.experimental.api.impl;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.experimental.api.Applications;
import com.restfb.json.JsonObject;
import com.restfb.types.Subscription;
import java.util.ArrayList;
import java.util.List;

public class ApplicationsImpl implements Applications {

  private final FacebookClient facebookClient;

  ApplicationsImpl(FacebookClient facebookClient) {
    this.facebookClient = facebookClient;
  }

  @Override
  public List<Subscription> fetchSubscriptions(String appId) {
    Connection<Subscription> subscriptionConn =
        facebookClient.fetchConnection(appId + "/subscriptions", Subscription.class);

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
    List<Parameter> paramList = new ArrayList<Parameter>();

    paramList.add(Parameter.with("verify_token", verifyToken));
    paramList.add(Parameter.with("object", subscription.getObject()));
    paramList.add(Parameter.with("callback_url", subscription.getCallbackUrl()));

    String fieldString = "";
    for (String field : subscription.getFields()) {
      fieldString += "," + field;
    }

    if (!fieldString.isEmpty()) {
      fieldString = fieldString.substring(1);
    }

    paramList.add(Parameter.with("fields", fieldString));
    Parameter paramArray[] = new Parameter[paramList.size()];
    paramArray = paramList.toArray(paramArray);

    JsonObject returnValue = facebookClient.publish(appId + "/subscriptions", JsonObject.class, paramArray);
    return returnValue.optBoolean("success", false);
  }

  @Override
  public boolean removeSubscription(String appId, String object) {
    return facebookClient.deleteObject(appId + "/subscriptions", Parameter.with("object", object));
  }
}
