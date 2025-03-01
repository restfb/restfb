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

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/graph-api/reference/whats-app-business-hsm-whats-app-hsm-component-get/">
 * WhatsApp Business HSMWhats App HSMComponent Get</a>, based on
 * <a href="https://developers.facebook.com/docs/whatsapp/business-management-api/message-templates/components">
 * Template Components</a>
 */
public class WhatsAppMessageTemplateComponent extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * Component type.
   */
  @Getter
  @Setter
  @Facebook
  private Type type;

  /**
   * Indicates media asset type for the component type
   */
  @Getter
  @Setter
  @Facebook
  private Format format;

  /**
   * Component text. Required for components with type HEADER, BODY or FOOTER.
   */
  @Getter
  @Setter
  @Facebook
  private String text;

  /**
   * Whether a security recommendation is included in the body text.
   */
  @Getter
  @Setter
  @Facebook("add_security_recommendation")
  private Boolean addSecurityRecommendation;

  /**
   * The number of minutes that the authentication code will expire in. (minimum 1, maximum 90)
   */
  @Getter
  @Setter
  @Facebook("code_expiration_minutes")
  private Integer codeExpirationMinutes;

  /**
   * The illustrative sample text for the parameter.
   * Applies only if the component has parameters.
   */
  @Getter
  @Setter
  @Facebook
  private Example example;

  /**
   * Button components to be used in the template.
   */
  @Getter
  @Setter
  @Facebook
  private List<Button> buttons = new ArrayList<>();

  public enum Type {
    HEADER, BODY, FOOTER, BUTTONS
  }

  public enum Format {
    TEXT, IMAGE, DOCUMENT, VIDEO, LOCATION
  }

  public enum ButtonType {
    QUICK_REPLY, URL, PHONE_NUMBER, COPY_CODE, FLOW, MPM, SPM
  }

  public static class Button {

    /**
     * Button type.
     */
    @Getter
    @Setter
    @Facebook
    private ButtonType type;

    /**
     * Button label text.
     */
    @Getter
    @Setter
    @Facebook
    private String text;

    /**
     * URL of website. Supports 1 variable.
     */
    @Getter
    @Setter
    @Facebook
    private String url;

    @Getter
    @Setter
    @Facebook("phone_number")
    private String phoneNumber;

    /**
     * Single element list with the strings to copy to the device's clipboard if the type is 'COPY_CODE',
     * or sample URL if the type is 'URL' and has a parameter.
     */
    @Getter
    @Setter
    @Facebook
    private List<String> example;

    /**
     * Unique identifier of the Flow provided by WhatsApp.
     * The Flow must be published.
     */
    @Getter
    @Setter
    @Facebook("flow_id")
    private String flowId;

    /**
     * The name of the Flow, supported in Cloud API only.
     * Cannot be used with flow_id or flow_json.
     */
    @Getter
    @Setter
    @Facebook("flow_name")
    private String flowName;

    /**
     * JSON string specifying the layout of the Flow.
     * Cannot be used with flow_id or flow_name.
     */
    @Getter
    @Setter
    @Facebook("flow_json")
    private String flowJson;

    /**
     * Action to define the Flow behavior.
     * Can be 'navigate' for predefined screens or 'data_exchange' for dynamic screens.
     */
    @Getter
    @Setter
    @Facebook("flow_action")
    private String flowAction;

    /**
     * ID of the entry screen in the Flow if flow_action is 'navigate'.
     * Optional. Default is 'FIRST_ENTRY_SCREEN'.
     */
    @Getter
    @Setter
    @Facebook("navigate_screen")
    private String navigateScreen;
  }


  public static class Example {
    @Getter
    @Setter
    @Facebook("header_text")
    private List<String> headerText = new ArrayList<>();

    @Getter
    @Setter
    @Facebook("header_handle")
    private List<String> headerHandle = new ArrayList<>();

    @Getter
    @Setter
    @Facebook("body_text")
    private List<List<String>> bodyText = new ArrayList<>();

    @Getter
    @Setter
    @Facebook("body_text_named_params")
    private List<NamedParam> bodyTextNamedParams = new ArrayList<>();

    @Getter
    @Setter
    @Facebook("header_text_named_params")
    private List<NamedParam> headerTextNamedParams = new ArrayList<>();
  }

  public static class NamedParam {

    @Getter
    @Setter
    @Facebook("param_name")
    private String paramName;

    @Getter
    @Setter
    @Facebook
    private String example;
  }
}
