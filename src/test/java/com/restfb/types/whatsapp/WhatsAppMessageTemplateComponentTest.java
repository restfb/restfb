/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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
package com.restfb.types.whatsapp;

import com.restfb.AbstractJsonMapperTests;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WhatsAppMessageTemplateComponentTest extends AbstractJsonMapperTests {

  @Test
  void checkWhatsAppMessageTemplateComponents() {

    List<WhatsAppMessageTemplateComponent> components = createJsonMapper().toJavaList(
            jsonFromClasspath("whatsapp/message-template-components"),
            WhatsAppMessageTemplateComponent.class);

    assertNotNull(components);

    // Test HEADER component with TEXT format
    WhatsAppMessageTemplateComponent headerText = components.get(0);
    assertEquals(WhatsAppMessageTemplateComponent.Type.HEADER, headerText.getType());
    assertEquals(WhatsAppMessageTemplateComponent.Format.TEXT, headerText.getFormat());
    assertEquals("Header text", headerText.getText());

    // Test HEADER component with IMAGE format
    WhatsAppMessageTemplateComponent headerImage = components.get(1);
    assertEquals(WhatsAppMessageTemplateComponent.Type.HEADER, headerImage.getType());
    assertEquals(WhatsAppMessageTemplateComponent.Format.IMAGE, headerImage.getFormat());
    assertNotNull(headerImage.getExample());
    assertEquals("https://example.com", headerImage.getExample().getHeaderHandle().get(0));

    // Test HEADER component with numeric type parameters
    WhatsAppMessageTemplateComponent headerNumericParams = components.get(2);
    assertEquals(WhatsAppMessageTemplateComponent.Type.HEADER, headerNumericParams.getType());
    assertEquals(WhatsAppMessageTemplateComponent.Format.TEXT, headerNumericParams.getFormat());
    assertTrue(headerNumericParams.getText().contains("{{1}}"));
    assertNotNull(headerNumericParams.getExample());
    assertEquals("Albert", headerNumericParams.getExample().getHeaderText().get(0));

    // Test HEADER component with named type parameters
    WhatsAppMessageTemplateComponent headerNamedParams = components.get(3);
    assertEquals(WhatsAppMessageTemplateComponent.Type.HEADER, headerNamedParams.getType());
    assertEquals(WhatsAppMessageTemplateComponent.Format.TEXT, headerNamedParams.getFormat());
    assertTrue(headerNamedParams.getText().contains("{{name}}"));
    assertNotNull(headerNamedParams.getExample());
    assertEquals("name", headerNamedParams.getExample().getHeaderTextNamedParams().get(0).getParamName());
    assertEquals("Albert", headerNamedParams.getExample().getHeaderTextNamedParams().get(0).getExample());


    // Test BODY component with named type parameters
    WhatsAppMessageTemplateComponent bodyNamedParams = components.get(4);
    assertEquals(WhatsAppMessageTemplateComponent.Type.BODY, bodyNamedParams.getType());
    assertTrue(bodyNamedParams.getText().contains("{{name}}"));
    assertNotNull(bodyNamedParams.getExample());
    assertEquals("name", bodyNamedParams.getExample().getBodyTextNamedParams().get(0).getParamName());
    assertEquals("Albert", bodyNamedParams.getExample().getBodyTextNamedParams().get(0).getExample());


    // Test FOOTER component (with code expiration)
    WhatsAppMessageTemplateComponent footer = components.get(5);
    assertEquals(WhatsAppMessageTemplateComponent.Type.FOOTER, footer.getType());
    assertEquals("This code expires in 10 minutes.", footer.getText());
    assertEquals(10, footer.getCodeExpirationMinutes());


    // Test BUTTONS component with URL button and one parameter
    WhatsAppMessageTemplateComponent buttonUrl = components.get(6);
    assertEquals(WhatsAppMessageTemplateComponent.Type.BUTTONS, buttonUrl.getType());
    WhatsAppMessageTemplateComponent.Button urlButton = buttonUrl.getButtons().get(0);
    assertEquals(WhatsAppMessageTemplateComponent.ButtonType.URL, urlButton.getType());
    assertEquals("Copy code", urlButton.getText());
    assertTrue(urlButton.getUrl().contains("{{1}}"));
    assertNotNull(urlButton.getExample());
    assertEquals("https://example.com/item", urlButton.getExample().get(0));

    // Test BUTTONS component with COPY_CODE button
    WhatsAppMessageTemplateComponent buttonCopyCode = components.get(7);
    assertEquals(WhatsAppMessageTemplateComponent.Type.BUTTONS, buttonCopyCode.getType());
    WhatsAppMessageTemplateComponent.Button copyCodeButton = buttonCopyCode.getButtons().get(0);
    assertEquals(WhatsAppMessageTemplateComponent.ButtonType.COPY_CODE, copyCodeButton.getType());
    assertEquals("Copy offer code", copyCodeButton.getText());
    assertNotNull(copyCodeButton.getExample());
    assertEquals("SALE2025", copyCodeButton.getExample().get(0));

    // Test BUTTONS component with FLOW button
    WhatsAppMessageTemplateComponent buttonFlow = components.get(8);
    assertEquals(WhatsAppMessageTemplateComponent.Type.BUTTONS, buttonFlow.getType());
    WhatsAppMessageTemplateComponent.Button flowButton = buttonFlow.getButtons().get(0);
    assertEquals(WhatsAppMessageTemplateComponent.ButtonType.FLOW, flowButton.getType());
    assertEquals("Flow button", flowButton.getText());
    assertEquals("9999999999999999", flowButton.getFlowId());
    assertEquals("NAVIGATE", flowButton.getFlowAction());
    assertEquals("QUESTION_ONE", flowButton.getNavigateScreen());

    // Test BUTTONS component with PHONE_NUMBER button
    WhatsAppMessageTemplateComponent buttonPhone = components.get(9);
    assertEquals(WhatsAppMessageTemplateComponent.Type.BUTTONS, buttonPhone.getType());
    WhatsAppMessageTemplateComponent.Button phoneButton = buttonPhone.getButtons().get(0);
    assertEquals(WhatsAppMessageTemplateComponent.ButtonType.PHONE_NUMBER, phoneButton.getType());
    assertEquals("Call", phoneButton.getText());
    assertEquals("+593999999999", phoneButton.getPhoneNumber());

    // Test BUTTONS component with QUICK_REPLY button
    WhatsAppMessageTemplateComponent buttonQuickReply = components.get(10);
    assertEquals(WhatsAppMessageTemplateComponent.Type.BUTTONS, buttonQuickReply.getType());
    WhatsAppMessageTemplateComponent.Button quickReplyButton = buttonQuickReply.getButtons().get(0);
    assertEquals(WhatsAppMessageTemplateComponent.ButtonType.QUICK_REPLY, quickReplyButton.getType());
    assertEquals("Quick reply text", quickReplyButton.getText());

    // Test BUTTONS component with MPM button
    WhatsAppMessageTemplateComponent buttonMpm = components.get(11);
    assertEquals(WhatsAppMessageTemplateComponent.Type.BUTTONS, buttonMpm.getType());
    WhatsAppMessageTemplateComponent.Button mpmButton = buttonMpm.getButtons().get(0);
    assertEquals(WhatsAppMessageTemplateComponent.ButtonType.MPM, mpmButton.getType());
    assertEquals("View items", mpmButton.getText());

    // Test BUTTONS component with SPM button
    WhatsAppMessageTemplateComponent buttonSpm = components.get(12);
    assertEquals(WhatsAppMessageTemplateComponent.Type.BUTTONS, buttonSpm.getType());
    WhatsAppMessageTemplateComponent.Button spmButton = buttonSpm.getButtons().get(0);
    assertEquals(WhatsAppMessageTemplateComponent.ButtonType.SPM, spmButton.getType());
    assertEquals("View", spmButton.getText());


    // Test BODY component with positional type parameters
    WhatsAppMessageTemplateComponent bodyPositionalParams = components.get(13);
    assertEquals(WhatsAppMessageTemplateComponent.Type.BODY, bodyPositionalParams.getType());
    assertTrue(bodyPositionalParams.getText().contains("{{1}}"));
    assertEquals(1, bodyPositionalParams.getExample().getBodyText().size());
    assertEquals(1, bodyPositionalParams.getExample().getBodyText().get(0).size());
    assertEquals("Albert", bodyPositionalParams.getExample().getBodyText().get(0).get(0));
  }
}
