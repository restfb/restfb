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

import com.restfb.Facebook;
import com.restfb.annotation.GraphAPI;

import lombok.Getter;

/**
 * implementation of the Open Graph Template
 * (https://developers.facebook.com/docs/messenger-platform/send-messages/template/open-graph)
 *
 * @deprecated with Graph API 4.0 this is no longer supported
 */
@Deprecated
@GraphAPI(until = "4.0")
public class OpenGraphTemplatePayload extends TemplatePayload {

  @Getter
  @Facebook
  private List<Element> elements;

  public OpenGraphTemplatePayload() {
    setTemplateType("open_graph");
  }

  public boolean addElement(Element element) {
    if (elements == null) {
      elements = new ArrayList<>();
    }

    return elements.add(element);
  }

  public static class Element {

    public Element(String url) {
      this.url = url;
    }

    @Getter
    @Facebook
    private String url;

    @Getter
    @Facebook
    private List<AbstractButton> buttons;

    public boolean addButton(WebButton button) {
      if (buttons == null) {
        buttons = new ArrayList<>();
      }

      return buttons.add(button);
    }
  }
}
