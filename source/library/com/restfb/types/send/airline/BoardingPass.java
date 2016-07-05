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
package com.restfb.types.send.airline;

import com.restfb.Facebook;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

public class BoardingPass {
  @Facebook("passenger_name")
  private String passengerName;

  @Facebook("pnr_number")
  private String pnrNumber;

  @Setter
  @Facebook("travel_class")
  private String travelClass;

  @Setter
  @Facebook
  private String seat;

  @Setter
  @Facebook("auxiliary_fields")
  private List<AirlineField> auxiliaryFields;

  @Setter
  @Facebook("secondary_fields")
  private List<AirlineField> secondaryFields;

  @Facebook("logo_image_url")
  private String logoImageUrl;

  @Setter
  @Facebook("header_image_url")
  private String headerImageUrl;

  @Setter
  @Facebook("header_text_field")
  private String headerTextField;

  @Setter
  @Facebook("qr_code")
  private String qrCode;

  @Setter
  @Facebook("barcode_image_url")
  private String barcodeImageUrl;

  @Facebook("above_bar_code_image_url")
  private String aboveBarCodeImageUrl;

  @Facebook("flight_info")
  private FlightInfo flightInfo;

  public BoardingPass(String passengerName, String pnrNumber, String logoImageUrl, String aboveBarCodeImageUrl,
      FlightInfo flightInfo) {
    this.passengerName = passengerName;
    this.pnrNumber = pnrNumber;
    this.logoImageUrl = logoImageUrl;
    this.aboveBarCodeImageUrl = aboveBarCodeImageUrl;
    this.flightInfo = flightInfo;
  }

  public boolean addAuxiliaryField(AirlineField auxiliaryField) {
    if (auxiliaryFields == null) {
      auxiliaryFields = new ArrayList<AirlineField>();
    }

    return auxiliaryFields.add(auxiliaryField);
  }

  public boolean addSecondaryField(AirlineField secondaryField) {
    if (secondaryFields == null) {
      secondaryFields = new ArrayList<AirlineField>();
    }

    return secondaryFields.add(secondaryField);
  }
}
