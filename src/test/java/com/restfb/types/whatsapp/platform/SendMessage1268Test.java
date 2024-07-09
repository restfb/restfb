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
package com.restfb.types.whatsapp.platform;

import org.junit.jupiter.api.Test;

import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.testutils.AssertJson;
import com.restfb.types.whatsapp.platform.send.Interactive;
import com.restfb.types.whatsapp.platform.send.Text;
import com.restfb.types.whatsapp.platform.send.interactive.Action;
import com.restfb.types.whatsapp.platform.send.interactive.Button;
import com.restfb.types.whatsapp.platform.send.interactive.Header;

class SendMessage1268Test {

  @Test
  void checkJson() {
    SendMessage sendMessage = new SendMessage("123456789");
    Action action = new Action();
    action.addButton(new Button("Button 1 title", "button-1"));
    action.addButton(new Button("Button 2 title", "button-2"));
    Interactive interactive = new Interactive(action);
    interactive.setHeader(new Header(new Text("This is header text")));
    sendMessage.setInteractive(interactive);

    JsonMapper jsonMapper = new DefaultJsonMapper();
    String json = jsonMapper.toJson(sendMessage, true);
    AssertJson.assertEquals(
      "{\"to\":\"123456789\",\"interactive\":{\"action\":{\"buttons\":[{\"reply\":{\"title\":\"Button 1 title\",\"id\":\"button-1\"},\"type\":\"reply\"},{\"reply\":{\"title\":\"Button 2 title\",\"id\":\"button-2\"},\"type\":\"reply\"}]},\"header\":{\"text\":\"This is header text\",\"type\":\"text\"}},\"type\":\"interactive\",\"messaging_product\":\"whatsapp\"}",
      json);
  }
}
