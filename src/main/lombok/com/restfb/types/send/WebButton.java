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

import lombok.Getter;
import lombok.Setter;

public class WebButton extends AbstractButton implements MenuItem {

  @Getter
  @Setter
  @Facebook("webview_height_ratio")
  private WebviewHeightEnum webviewHeightRatio;

  @Getter
  @Facebook
  private String url;

  @Getter
  @Facebook("messenger_extensions")
  private Boolean messengerExtensions;

  @Getter
  @Facebook("fallback_url")
  private String fallbackUrl;

  @Facebook("webview_share_button")
  private String webviewShareButton;

  public WebButton(String title, String webUrl) {
    super(title);
    setType("web_url");
    this.url = webUrl;
  }

  public void setMessengerExtensions(boolean withMessengerExtensions, String fallbackUrl) {
    if (withMessengerExtensions) {
      this.messengerExtensions = true;
      this.fallbackUrl = fallbackUrl;
    } else {
      messengerExtensions = null;
      this.fallbackUrl = null;
    }
  }

  /**
   * Set to {@code true} to disable sharing in the webview (for sensitive info).
   * 
   * @param disable
   */
  public void setDisableSharing(boolean disable) {
    if (disable) {
      webviewShareButton = "hide";
    } else {
      webviewShareButton = null;
    }
  }
}
