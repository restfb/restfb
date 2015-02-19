/*
 * Copyright (c) 2010-2015 Mark Allen.
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

package com.restfb.scope;

enum DefaultPermissions implements FacebookPermissions {
  /**
   * Provides access to a subset of items that are part of a person's public profile.
   * 
   * A person's public profile refers to the following properties on the user object by default:
   * <ul>
   * <li>id</li>
   * <li>name</li>
   * <li>first_name</li>
   * <li>last_name</li>
   * <li>link</li>
   * <li>gender</li>
   * <li>locale</li>
   * <li>timezone</li>
   * <li>updated_time</li>
   * <li>verified</li>
   * </ul>
   * 
   * On the web, public_profile is implied with every request and isn't required, although the best practice is to
   * declare it. On iOS and Android, you must manually request it as part of your login flow.<br />
   * <br />
   * 
   * gender & locale can only be accessed if:
   * 
   * <ul>
   * <li>The person queried is the person using the app.</li>
   * <li>The person queried is using the app, and is a friend of the person using the app.</li>
   * <li>The person queried is using the app, is not a friend of the person using the app, but the app includes either
   * an app access token or an appsecret_proof argument with the call.</li>
   * <br />
   * <strong>Review</strong> Your app may use this permission without review from Facebook.
   */
  PUBLIC_PROFILE("public_profile");

  DefaultPermissions(String facebookPermissionString) {
    this.permissionString = facebookPermissionString;
  }

  String permissionString;

  @Override
  public String getPermissionString() {
    return this.permissionString;
  }

}
