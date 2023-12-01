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
package com.restfb.types.webhook.messaging;

import java.util.*;

/**
 * Represents the app roles as used here:
 * <a href="https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/app_role">App Roles on
 * Facebook</a>
 */
public class AppRoles implements InnerMessagingItem {

  private Map<String, List<String>> roles = new HashMap<>();

  /**
   * Get a set of app ids
   * 
   * @return set of app ids
   */
  public Set<String> getAppIds() {
    return Collections.unmodifiableSet(roles.keySet());
  }

  /**
   * get the roles to the given app id
   * 
   * @param appId
   * @return List of app roles
   */
  public List<String> getRoles(String appId) {
    if (roles.containsKey(appId)) {
      return Collections.unmodifiableList(roles.get(appId));
    } else {
      return null;
    }
  }

  void addRoles(String appId, List<String> roles) {
    this.roles.put(appId, roles);
  }
}
