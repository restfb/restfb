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
import com.restfb.types.whatsapp.platform.send.Interactive;
import com.restfb.types.whatsapp.platform.send.Reaction;
import com.restfb.types.whatsapp.platform.send.Text;
import com.restfb.types.whatsapp.platform.send.interactive.*;
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

  @Test
  void checkInteractive1() {

    Action action = new Action();
    action.setCatalogId("CATALOG_ID");
    action.setProductRetailerId("ID_TEST_ITEM_1");
    Interactive interactive = new Interactive(action);
    interactive.setType(Interactive.Type.product);
    interactive.setBody(new Body("optional body text"));
    interactive.setFooter(new Footer("optional footer text"));
    SendMessage message = new SendMessage("PHONE_NUMBER");
    message.setInteractive(interactive);

    JsonMapper mapper = new DefaultJsonMapper();
    String mappedMessage = mapper.toJson(message, true);

    AssertJson.assertEquals(
      "{\"to\":\"PHONE_NUMBER\",\"interactive\":{\"action\":{\"catalog_id\":\"CATALOG_ID\",\"product_retailer_id\":\"ID_TEST_ITEM_1\"},\"body\":{\"text\":\"optional body text\"},\"footer\":{\"text\":\"optional footer text\"},\"type\":\"product\"},\"type\":\"interactive\",\"messaging_product\":\"whatsapp\"}",
      mappedMessage);
  }

  @Test
  void checkInteractive2() {

    Action action = new Action();
    action.setCatalogId("CATALOG_ID");

    Section section1 = new Section();
    section1.addProductItem(new Section.Product("product-SKU-in-catalog"))
      .addProductItem(new Section.Product("product-SKU-in-catalog"));
    section1.setTitle("section-title");
    Section section2 = new Section();
    section2.addProductItem(new Section.Product("product-SKU-in-catalog"))
      .addProductItem(new Section.Product("product-SKU-in-catalog"));
    section2.setTitle("section-title");
    action.addSection(section1).addSection(section2);
    Interactive interactive = new Interactive(action);
    interactive.setType(Interactive.Type.product);
    interactive.setBody(new Body("body-content"));
    interactive.setFooter(new Footer("footer-content"));
    interactive.setHeader(new Header(new Text("header-content")));
    SendMessage message = new SendMessage("PHONE_NUMBER");
    message.setInteractive(interactive);

    JsonMapper mapper = new DefaultJsonMapper();
    String mappedMessage = mapper.toJson(message, true);

    AssertJson.assertEquals("{\"to\":\"PHONE_NUMBER\"," + "\"interactive\":{\"action\":"
        + "{\"catalog_id\":\"CATALOG_ID\","
        + "\"sections\":[{\"product_items\":[{\"product_retailer_id\":\"product-SKU-in-catalog\"},{\"product_retailer_id\":\"product-SKU-in-catalog\"}],\"title\":\"section-title\"},"
        + "{\"product_items\":[{\"product_retailer_id\":\"product-SKU-in-catalog\"},{\"product_retailer_id\":\"product-SKU-in-catalog\"}],\"title\":\"section-title\"}]},"
        + "\"body\":{\"text\":\"body-content\"},"
        + "\"header\":{\"text\":{\"body\":\"header-content\"},\"type\":\"text\"},"
        + "\"footer\":{\"text\":\"footer-content\"},\"type\":\"product\"},"
        + "\"type\":\"interactive\",\"messaging_product\":\"whatsapp\"}",
      mappedMessage);
  }
}
