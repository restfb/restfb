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
package com.restfb.types.whatsapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href=
 * "https://developers.facebook.com/docs/graph-api/reference/whats-app-business-account/conversation_analytics/">WhatsApp
 * Business Account Conversation Analytics</a> type
 */
public class WABAConversationAnalytics {

  @Facebook("conversation_directions")
  private List<WABAConversationDirection> conversationDirections;

  @Facebook("conversation_types")
  private List<WABAConversationType> conversationTypes;

  @Facebook("country_codes")
  private List<String> countryCodes;

  @Facebook
  private List<WABADimension> dimensions;

  @Getter
  @Setter
  @Facebook
  private Integer end;

  @Getter
  @Setter
  @Facebook
  private WABAGranularity granularity;

  @Facebook("metric_types")
  private List<WABAMetricType> metricTypes;

  @Facebook("phone_numbers")
  private List<String> phoneNumbers;

  @Getter
  @Setter
  @Facebook
  private Integer start;


  public List<WABAMetricType> getMetricTypes() {
    return Collections.unmodifiableList(getMetricTypesList());
  }

  public boolean addMetricType(WABAMetricType metricType) {
    return getMetricTypesList().add(metricType);
  }

  public boolean removeMetricType(WABAMetricType metricType) {
    return getMetricTypesList().remove(metricType);
  }

  private List<WABAMetricType> getMetricTypesList() {
    if (metricTypes == null) {
      metricTypes = new ArrayList<>();
    }
    return metricTypes;
  }

  public List<WABAConversationType> getConversationTypes() {
    return Collections.unmodifiableList(getConversationTypesList());
  }

  public boolean addConversationType(WABAConversationType conversationType) {
    return getConversationTypesList().add(conversationType);
  }

  public boolean removeConversationType(WABAConversationType conversationType) {
    return getConversationTypesList().remove(conversationType);
  }

  private List<WABAConversationType> getConversationTypesList() {
    if (conversationTypes == null) {
      conversationTypes = new ArrayList<>();
    }
    return conversationTypes;
  }

  public List<WABAConversationDirection> getConversationDirections() {
    return Collections.unmodifiableList(getConversationDirectionsList());
  }

  public boolean addConversationDirection(WABAConversationDirection conversationDirection) {
    return getConversationDirectionsList().add(conversationDirection);
  }

  public boolean removeConversationDirection(WABAConversationDirection conversationDirection) {
    return getConversationDirectionsList().remove(conversationDirection);
  }

  private List<WABAConversationDirection> getConversationDirectionsList() {
    if (conversationDirections == null) {
      conversationDirections = new ArrayList<>();
    }
    return conversationDirections;
  }

  public List<WABADimension> getDimensions() {
    return Collections.unmodifiableList(getDimensionsList());
  }

  public boolean addDimension(WABADimension dimension) {
    return getDimensionsList().add(dimension);
  }

  public boolean removeDimension(WABADimension dimension) {
    return getDimensionsList().remove(dimension);
  }

  private List<WABADimension> getDimensionsList() {
    if (dimensions == null) {
      dimensions = new ArrayList<>();
    }
    return dimensions;
  }

  public List<String> getPhoneNumbers() {
    return Collections.unmodifiableList(getPhoneNumbersList());
  }

  public boolean addPhoneNumber(String phoneNumber) {
    return getPhoneNumbersList().add(phoneNumber);
  }

  public boolean removePhoneNumber(String phoneNumber) {
    return getPhoneNumbersList().remove(phoneNumber);
  }

  private List<String> getPhoneNumbersList() {
    if (phoneNumbers == null) {
      phoneNumbers = new ArrayList<>();
    }
    return phoneNumbers;
  }

  /**
   * List of ISO 3166 country codes (e.g. US, IN)
   */
  public List<String> getCountryCodes() {
    return Collections.unmodifiableList(getCountryCodesList());
  }

  public boolean addCountryCode(String countryCode) {
    return getCountryCodesList().add(countryCode);
  }

  public boolean removeCountryCode(String countryCode) {
    return getCountryCodesList().remove(countryCode);
  }

  private List<String> getCountryCodesList() {
    if (countryCodes == null) {
      countryCodes = new ArrayList<>();
    }
    return countryCodes;
  }
}
