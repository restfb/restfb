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
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/waba-analytics/">WABA (WhatsApp
 * Business Account) Analytics type </a>
 */
public class WABAAnalytics extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  @Facebook("country_codes")
  private List<String> countryCodes = new ArrayList<>();

  @Facebook("data_points")
  private List<WABAAnalyticsDataPoint> dataPoints = new ArrayList<>();

  @Getter
  @Setter
  @Facebook
  private String granularity;

  @Facebook("phone_numbers")
  private List<String> phoneNumbers = new ArrayList<>();

  /**
   * List of ISO 3166 country codes (e.g. US, IN)
   */
  public List<String> getCountryCodes() {
    return Collections.unmodifiableList(countryCodes);
  }

  public boolean addCountryCode(String countryCode) {
    return countryCodes.add(countryCode);
  }

  public boolean removeCountryCode(String countryCode) {
    return countryCodes.remove(countryCode);
  }

  /**
   * List of analytics data points (e.g. {start: 0, end: 10000, sent: 10, delivered: 9})
   */
  public List<WABAAnalyticsDataPoint> getDataPoints() {
    return Collections.unmodifiableList(dataPoints);
  }

  public boolean addDataPoint(WABAAnalyticsDataPoint dataPoint) {
    return dataPoints.add(dataPoint);
  }

  public boolean removeDataPoint(WABAAnalyticsDataPoint dataPoint) {
    return dataPoints.remove(dataPoint);
  }

  /**
   * List of WhatsApp normalized phone numbers (e.g. [16315551000])
   */
  public List<String> getPhoneNumbers() {
    return Collections.unmodifiableList(phoneNumbers);
  }

  public boolean addPhoneNumber(String phoneNumber) {
    return phoneNumbers.add(phoneNumber);
  }

  public boolean removePhoneNumber(String phoneNumber) {
    return phoneNumbers.remove(phoneNumber);
  }
}
