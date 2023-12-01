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

import java.util.Locale;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;

public class Greeting extends AbstractFacebookType {

  /**
   * The greeting text for the specific locale.
   */
  @Getter
  @Facebook
  private String text;

  /**
   * Locale of the greeting text.
   *
   * Facebook will show this greeting text when user locale matches the provided locale. You must at least specify
   * greeting text for the default locale. This is the text Facebook will fall back to if they don't find another
   * matching the user's locale. See the list of
   * <a href="https://developers.facebook.com/docs/messenger-platform/messenger-profile/supported-locales">supported
   * locales</a>.
   */
  @Getter
  @Facebook
  private String locale;

  private Greeting() {
    // used for json mapping only
  }

  public Greeting(String text) {
    this("default", text);
  }

  public Greeting(String locale, String text) {
    this.text = text;
    this.locale = locale;
  }

  public Greeting(Locale locale, String text) {
    this(locale.toString().toLowerCase(), text);
  }
}
