/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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
package com.restfb.types.webhook.messaging.airline;

import com.restfb.Facebook;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class BoardingPassItem {

  @Getter
  @Setter
  @Facebook("passenger_name")
  private String passengerName;

  @Getter
  @Setter
  @Facebook("pnr_number")
  private String pnrNumber;

  @Getter
  @Setter
  @Facebook("travel_class")
  private String travelClass;

  @Getter
  @Setter
  @Facebook
  private String seat;

  @Getter
  @Setter
  @Facebook("auxiliary_fields")
  private List<AirlineFieldItem> auxiliaryFields;

  @Getter
  @Setter
  @Facebook("secondary_fields")
  private List<AirlineFieldItem> secondaryFields;

  @Getter
  @Setter
  @Facebook("logo_image_url")
  private String logoImageUrl;

  @Getter
  @Setter
  @Facebook("header_image_url")
  private String headerImageUrl;

  @Getter
  @Setter
  @Facebook("header_text_field")
  private String headerTextField;

  @Getter
  @Setter
  @Facebook("qr_code")
  private String qrCode;

  @Getter
  @Setter
  @Facebook("barcode_image_url")
  private String barcodeImageUrl;

  @Getter
  @Setter
  @Facebook("above_bar_code_image_url")
  private String aboveBarCodeImageUrl;

  @Getter
  @Setter
  @Facebook("flight_info")
  private FlightInfoItem flightInfo;
}
