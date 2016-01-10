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
package com.restfb.types;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/page/call_to_actions/">Page Call To
 * Action type</a>
 */
public class PageCallToAction extends FacebookType {

  /**
   * Destination type for the call-to-action on desktop.
   * <p>
   * Default value: NONE
   * </p>
   * <p>
   * may be: WEBSITE, MESSENGER, NONE
   * </p>
   */
  @Getter
  @Setter
  @Facebook("web_destination_type")
  private String webDestinationType;

  /**
   * Destination type for the call-to-action on Android.
   * <p>
   * Default value: NONE
   * </p>
   * <p>
   * may be: WEBSITE, APP_DEEPLINK, FACEBOOK_APP, PHONE_CALL, MESSENGER, NONE
   * </p>
   */
  @Getter
  @Setter
  @Facebook("android_destination_type")
  private String androidDestinationType;

  /**
   * Destination type for the call-to-action on iPhone.
   * <p>
   * Default value: NONE
   * </p>
   * <p>
   * may be: WEBSITE, APP_DEEPLINK, FACEBOOK_APP, PHONE_CALL, MESSENGER, NONE
   * </p>
   */
  @Getter
  @Setter
  @Facebook("iphone_destination_type")
  private String iphoneDestinationType;

  /**
   * International phone number with plus that can be called through a phone
   */
  @Getter
  @Setter
  @Facebook("intl_number_with_plus")
  private String intlNumberWithPlus;

  /**
   * Destination url for the call-to-action on desktop
   */
  @Getter
  @Setter
  @Facebook("web_url")
  private String webUrl;

  /**
   * ID of the App that stores the destination info on Android
   */
  @Getter
  @Setter
  @Facebook("android_app_id")
  private String androidAppId;

  /**
   * Destination deeplink for the call-to-action on Android
   */
  @Getter
  @Setter
  @Facebook("android_deeplink")
  private String androidDeeplink;

  /**
   * Destination app for the call-to-action on Android
   */
  @Getter
  @Setter
  @Facebook("android_package_name")
  private String androidPackageName;

  /**
   * Destination url for the call-to-action on Android
   */
  @Getter
  @Setter
  @Facebook("android_url")
  private String androidUrl;

  /**
   * ID fo the App that stores the destination info on iPhone
   */
  @Getter
  @Setter
  @Facebook("iphone_app_id")
  private String iphoneAppId;

  /**
   * Destination deeplink for the call-to-action on iPhone
   */
  @Getter
  @Setter
  @Facebook("iphoneDeeplink")
  private String iphone_deeplink;

  /**
   * Destination url for the call-to-action on iPhone
   */
  @Getter
  @Setter
  @Facebook("iphoneUrl")
  private String iphone_url;
}
