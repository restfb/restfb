/*
 * Copyright (c) 2010-2022 Mark Allen, Norbert Bartels.
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

import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.testutils.AssertJson;
import com.restfb.types.whatsapp.platform.send.Image;
import com.restfb.types.whatsapp.platform.send.Reaction;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

class SendMessageTest {

  @Test
  void checkReaction() {
    Reaction reaction = new Reaction("my-message-id");
    reaction.setEmoji("😇");
    SendMessage message = new SendMessage("12345678");
    message.setReaction(reaction);
    JsonMapper mapper = new DefaultJsonMapper();
    String mappedMessage = mapper.toJson(message, true);

    AssertJson.assertEquals(
      "{\"to\":\"12345678\",\"reaction\":{\"message_id\":\"my-message-id\",\"emoji\":\"\\ud83d\\ude07\"},\"type\":\"reaction\",\"messaging_product\":\"whatsapp\"}",
      mappedMessage);
  }

  @Test
  void checkMedia() throws MalformedURLException {
    Image image = new Image(new URL("https://restfb.com/img/favicon.png"));
    SendMessage message = new SendMessage("12345678");
    message.setMedia(image);
    JsonMapper mapper = new DefaultJsonMapper();
    String mappedMessage = mapper.toJson(message, true);

    AssertJson.assertEquals(
      "{\"to\":\"12345678\",\"image\":{\"link\":\"https://restfb.com/img/favicon.png\"},\"type\":\"image\",\"messaging_product\":\"whatsapp\"}",
      mappedMessage);
  }
}
