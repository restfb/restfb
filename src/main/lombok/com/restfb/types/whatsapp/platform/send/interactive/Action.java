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
package com.restfb.types.whatsapp.platform.send.interactive;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class Action extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook
  private String button;

  @Getter
  @Facebook
  private List<Button> buttons;

  @Getter
  @Setter
  @Facebook("catalog_id")
  private String catalogId;

  @Getter
  @Setter
  @Facebook("product_retailer_id")
  private String productRetailerId;

  @Facebook
  private List<Section> sections;

  public Action addButton(Button button) {
    if (buttons == null) {
      buttons = new ArrayList<>();
    }

    if (buttons.size() <= 3) {
      buttons.add(button);
    } else {
      throw new IllegalArgumentException("Action may contain only up to 3 buttons");
    }

    return this;
  }

  public Action addSection(Section section) {
    if (sections == null) {
      sections = new ArrayList<>();
    }

    sections.add(section);
    return this;
  }
}
