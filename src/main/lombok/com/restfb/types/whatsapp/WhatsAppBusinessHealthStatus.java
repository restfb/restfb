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
 * <a href="https://developers.facebook.com/docs/whatsapp/cloud-api/health-status/#response-contents">
 * Messaging Health Status</a>
 */
public class WhatsAppBusinessHealthStatus extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  // Indicates the overall messaging capability of the business phone number
  @Getter
  @Setter
  @Facebook("can_send_message")
  private CanSendMessageStatus canSendMessage;

  // List of entities and their messaging status
  @Getter
  @Setter
  @Facebook
  private List<EntityStatus> entities = new ArrayList<>();

  public enum CanSendMessageStatus {
    AVAILABLE, LIMITED, BLOCKED
  }

  public enum EntityType {
    PHONE_NUMBER, WABA, BUSINESS, APP, MESSAGE_TEMPLATE
  }

  public static class EntityStatus {
    // Type of entity (PHONE_NUMBER, WABA, BUSINESS, APP, MESSAGE_TEMPLATE)
    @Getter
    @Setter
    @Facebook("entity_type")
    private EntityType entityType;

    // ID of the entity
    @Getter
    @Setter
    @Facebook
    private String id;

    // Messaging capability status of the entity
    @Getter
    @Setter
    @Facebook("can_send_message")
    private CanSendMessageStatus canSendMessage;

    // Additional information about messaging limitations
    @Getter
    @Setter
    @Facebook("additional_info")
    private List<String> additionalInfo = new ArrayList<>();

    // List of errors if the entity is blocked from sending messages
    @Getter
    @Setter
    @Facebook
    private List<ErrorDetail> errors = new ArrayList<>();
  }

  public static class ErrorDetail {
    // Error code representing the specific issue
    @Getter
    @Setter
    @Facebook("error_code")
    private int errorCode;

    // Description of the error
    @Getter
    @Setter
    @Facebook("error_description")
    private String errorDescription;

    // Suggested solution for resolving the error
    @Getter
    @Setter
    @Facebook("possible_solution")
    private String possibleSolution;
  }
}
