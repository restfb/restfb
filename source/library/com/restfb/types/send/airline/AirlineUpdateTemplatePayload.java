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
import com.restfb.types.send.TemplatePayload;

import lombok.Setter;

public class AirlineUpdateTemplatePayload extends TemplatePayload {
  @Setter
  @Facebook("intro_message")
  private String introMessage;

  @Setter
  @Facebook("update_type")
  private String updateType;

  @Facebook
  private String locale;

  @Setter
  @Facebook
  private String themeColor;

  @Facebook("pnr_number")
  private String pnrNumber;

  @Facebook("update_flight_info")
  private FlightInfo updateFlightInfo;

  public AirlineUpdateTemplatePayload(String locale, String pnrNumber, FlightInfo updateFlightInfo) {
    setTemplateType("airline_update");
    this.locale = locale;
    this.pnrNumber = pnrNumber;
    this.updateFlightInfo = updateFlightInfo;
  }
}
