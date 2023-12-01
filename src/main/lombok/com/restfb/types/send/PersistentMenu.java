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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class PersistentMenu extends AbstractFacebookType {

  /**
   * Locale of the menu.
   *
   * We will show this menu when user locale matches the provided locale. You must at least specify a menu for the
   * default locale. This is the menu we will fall back to if we don't find another matching the user's locale. See the
   * list of supported locales.
   */
  @Getter
  @Facebook
  private String locale;

  /**
   * Set to true to disable user input in the menu. This means users will only be able to interact with your bot via the
   * menu, postbacks, buttons, and webviews.
   */
  @Setter
  @Getter
  @Facebook("composer_input_disabled")
  private Boolean composerInputDisabled;

  /**
   * Top-level menu items that user can interact with.
   */
  @Getter
  @Facebook("call_to_actions")
  private List<MenuItem> callToActions;

  public PersistentMenu() {
    this("default");
  }

  public PersistentMenu(String locale) {
    this.locale = locale;
  }

  public PersistentMenu(Locale locale) {
    this(locale.toString().toLowerCase());
  }

  public void addCallToAction(MenuItem item) {
    if (callToActions == null) {
      callToActions = new ArrayList<>();
    }

    callToActions.add(item);
  }
}
