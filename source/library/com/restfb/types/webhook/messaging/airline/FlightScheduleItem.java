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
import com.restfb.JsonMapper;
import com.restfb.util.DateUtils;

import java.text.ParseException;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class FlightScheduleItem {

  @Facebook("boarding_time")
  transient private String rawBoardingTime;

  /**
   * Timestamp for boarding time.
   *
   * @return Timestamp for boarding time.
   */
  @Getter
  @Setter
  private Date boardingTime;

  @Facebook("departure_time")
  transient private String rawDepartureTime;

  /**
   * Timestamp for departure time.
   *
   * @return Timestamp for departure time.
   */
  @Getter
  @Setter
  private Date departureTime;

  @Facebook("arrival_time")
  transient private String rawArrivalTime;

  /**
   * Timestamp for arrival time.
   *
   * @return Timestamp for arrival time.
   */
  @Getter
  @Setter
  private Date arrivalTime;

  @JsonMapper.JsonMappingCompleted
  void convertTimes() throws ParseException {
    if (rawBoardingTime != null) {
      boardingTime = DateUtils.toDateFromLongFormat(rawBoardingTime);
    }
    if (rawDepartureTime != null) {
      departureTime = DateUtils.toDateFromLongFormat(rawDepartureTime);
    }
    if (rawArrivalTime != null) {
      arrivalTime = DateUtils.toDateFromLongFormat(rawArrivalTime);
    }
  }
}
