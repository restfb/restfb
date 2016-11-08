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
package com.restfb.types.send;

import com.restfb.Facebook;
import com.restfb.exception.FacebookPreconditionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Setter;

public class ListTemplatePayload extends TemplatePayload {

  @Facebook
  private List<ListViewElement> elements;

  /**
   * Value must be large or compact. Default to large if not specified
   */
  @Setter
  @Facebook("top_element_style")
  private TopElementStyleEnum topElementStyle;

  @Facebook
  private List<AbstractButton> buttons;

  public ListTemplatePayload(List<ListViewElement> listViewElements) {
    setTemplateType("list");
    if (listViewElements == null) {
      throw new FacebookPreconditionException("List of elements may not be 'null'");
    }
    if (listViewElements.isEmpty() || listViewElements.size() < 2) {
      throw new FacebookPreconditionException("List of elements needs to contain a minimum of 2");
    }
    if (listViewElements.size() > 4) {
      throw new FacebookPreconditionException("List of elements can contain a maximum of 4 elements");
    }
    elements = Collections.unmodifiableList(listViewElements);
  }

  public boolean addButton(AbstractButton button) {
    if (buttons == null) {
      buttons = new ArrayList<AbstractButton>();
    }

    if (buttons.size() == 1) {
      throw new FacebookPreconditionException("maximum of associated buttons is 1");
    }

    return buttons.add(button);
  }

  public enum TopElementStyleEnum {
    large, compact;
  }
}
