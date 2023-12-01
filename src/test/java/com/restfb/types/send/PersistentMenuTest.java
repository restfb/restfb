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

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.testutils.AssertJson;

class PersistentMenuTest extends AbstractJsonMapperTests {

  @Test
  void withoutCallToAction() {
    PersistentMenu menu = new PersistentMenu(Locale.CHINA);
    menu.setComposerInputDisabled(true);

    JsonMapper mapper = new DefaultJsonMapper();
    AssertJson.assertEquals("{\"locale\":\"zh_cn\",\"composer_input_disabled\":true}", mapper.toJson(menu, true));
  }

  @Test
  void nestedMenu() {
    PersistentMenu menu = new PersistentMenu();
    menu.setComposerInputDisabled(true);

    WebButton webUrl = new WebButton("Latest News", "http://petershats.parseapp.com/hat-news");
    webUrl.setWebviewHeightRatio(WebviewHeightEnum.full);

    NestedButton nested = new NestedButton("My Account");
    nested.addCallToAction(new PostbackButton("Pay Bill", "PAYBILL_PAYLOAD"));
    nested.addCallToAction(new PostbackButton("History", "HISTORY_PAYLOAD"));
    nested.addCallToAction(new PostbackButton("Contact Info", "CONTACT_INFO_PAYLOAD"));

    menu.addCallToAction(nested);
    menu.addCallToAction(webUrl);

    String expected = jsonFromClasspath("send/persistent-menu");
    JsonMapper mapper = new DefaultJsonMapper();

    String current = mapper.toJson(menu, true);

    AssertJson.assertEquals(expected, current);

  }
}
