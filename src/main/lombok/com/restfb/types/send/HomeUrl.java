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
package com.restfb.types.send;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/messenger-platform/reference/messenger-profile-api/home-url">Messenger
 * Profile Home Url</a>
 */
public class HomeUrl extends AbstractFacebookType {

  /**
   * The URL to be invoked from drawer. Must be whitelisted. Must use https.
   */
  @Getter
  @Setter
  @Facebook
  private String url;

  /**
   * Controls the height of webview. Only allowed value is tall.
   */
  @Getter
  @Setter
  @Facebook("webview_height_ratio")
  private String webviewHeightRatio;

  /**
   * Controls whether the share button in the webview is enabled. Either show or hide, defaults to "hide"
   */
  @Getter
  @Setter
  @Facebook("webview_share_button")
  private String webviewShareButton;

  /**
   * Controls whether users not assigned a role for your bot or its Facebook page can see the Chat Extension.
   * 
   * This should be set to true until the Chat Extension is ready to be used by others.
   */
  @Getter
  @Setter
  @Facebook("in_test")
  private Boolean inTest;

}
