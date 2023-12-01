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
package com.restfb.types.send.airline;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.send.TemplatePayload;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href=
 * "https://developers.facebook.com/docs/messenger-platform/send-api-reference/airline-itinerary-template">Airline
 * Itinerary Template Payload</a> type
 */
public class AirlineItineraryTemplatePayload extends TemplatePayload {

  @Getter
  @Facebook("intro_message")
  private String introMessage;

  @Getter
  @Facebook
  private String locale;

  @Getter
  @Setter
  @Facebook("theme_color")
  private String themeColor;

  @Getter
  @Facebook("pnr_number")
  private String pnrNumber;

  @Getter
  @Facebook("passenger_info")
  private List<PassengerInfo> passengerInfoList;

  @Getter
  @Facebook("flight_info")
  private List<FlightInfo> flightInfoList;

  @Getter
  @Facebook("passenger_segment_info")
  private List<PassengerSegmentInfo> passengerSegmentInfoList;

  @Getter
  @Facebook("price_info")
  private List<PriceInfo> priceInfoList;

  @Getter
  @Setter
  @Facebook("base_price")
  private double basePrice;

  @Getter
  @Setter
  @Facebook
  private double tax;

  @Getter
  @Facebook("total_price")
  private double totalPrice;

  @Getter
  @Facebook
  private String currency;

  public AirlineItineraryTemplatePayload(String introMessage, String locale, String pnrNumber, double totalPrice) {
    setTemplateType("airline_itinerary");
    this.introMessage = introMessage;
    this.locale = locale;
    this.pnrNumber = pnrNumber;
    this.totalPrice = totalPrice;
  }

  public boolean addPassengerInfo(PassengerInfo passengerInfo) {
    if (passengerInfoList == null) {
      passengerInfoList = new ArrayList<>();
    }

    return passengerInfoList.add(passengerInfo);
  }

  public boolean addFlightInfo(FlightInfo flightInfo) {
    if (flightInfoList == null) {
      flightInfoList = new ArrayList<>();
    }

    return flightInfoList.add(flightInfo);
  }

  public boolean addPassengerSegmentInfo(PassengerSegmentInfo passengerSegmentInfo) {
    if (passengerSegmentInfoList == null) {
      passengerSegmentInfoList = new ArrayList<>();
    }

    return passengerSegmentInfoList.add(passengerSegmentInfo);
  }

  public boolean addPriceInfo(PriceInfo priceInfo) {
    if (priceInfoList == null) {
      priceInfoList = new ArrayList<>();
    }

    return priceInfoList.add(priceInfo);
  }
}
