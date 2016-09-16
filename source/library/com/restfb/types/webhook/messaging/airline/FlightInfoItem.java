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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class FlightInfoItem {
  @Getter
  @Setter
  @Facebook("connection_id")
  private String connectionId;

  @Getter
  @Setter
  @Facebook("segment_id")
  private String segmentId;

  @Getter
  @Setter
  @Facebook("flight_number")
  private String flightNumber;

  @Getter
  @Setter
  @Facebook("aircraft_type")
  private String aircraftType;

  @Getter
  @Setter
  @Facebook("departure_airport")
  private FlightAirportItem departureAirport;

  @Getter
  @Setter
  @Facebook("arrival_airport")
  private FlightAirportItem arrivalAirport;

  @Getter
  @Setter
  @Facebook("flight_schedule")
  private FlightScheduleItem flightSchedule;

  @Getter
  @Setter
  @Facebook("travel_class")
  private String travelClass;
}
