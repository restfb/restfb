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
package com.restfb.types.webhook.messaging;

import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.webhook.messaging.airline.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MessagingPayload {

  @Getter
  @Setter
  @Facebook
  private String url;

  @Getter
  @Setter
  @Facebook("sticker_id")
  private String stickerId;

  @Getter
  @Setter
  @Facebook
  private CoordinatesItem coordinates;

  @Getter
  @Setter
  @Facebook("template_type")
  private String templateType;

  @Getter
  @Setter
  @Facebook
  private List<ButtonItem> buttons;

  @Getter
  @Setter
  @Facebook
  private List<ElementItem> elements;

  @Getter
  @Setter
  @Facebook("recipient_name")
  private String recipientName;

  @Getter
  @Setter
  @Facebook("order_number")
  private String orderNumber;

  @Getter
  @Setter
  @Facebook
  private String currency;

  @Getter
  @Setter
  @Facebook("payment_method")
  private String paymentMethod;

  @Getter
  @Setter
  @Facebook("order_url")
  private String orderUrl;

  @Getter
  @Setter
  @Facebook
  private Long timestamp;

  @Getter
  @Setter
  @Facebook
  private AddressItem address;

  @Getter
  @Setter
  @Facebook
  private SummaryItem summary;

  @Getter
  @Setter
  @Facebook
  private List<AdjustmentItem> adjustmentItems;

  @Getter
  @Setter
  @Facebook("intro_message")
  private String introMessage;

  @Getter
  @Setter
  @Facebook
  private String locale;

  @Getter
  @Setter
  @Facebook
  private String themeColor;

  @Getter
  @Setter
  @Facebook("update_type")
  private String updateType;

  @Getter
  @Setter
  @Facebook("pnr_number")
  private String pnrNumber;

  @Getter
  @Setter
  @Facebook("passenger_info")
  private List<PassengerInfoItem> passengerInfoItems;

  @Getter
  @Setter
  @Facebook("flight_info")
  private List<FlightInfoItem> flightInfoItems;

  @Getter
  @Setter
  @Facebook("passenger_segment_info")
  private List<PassengerSegmentInfoItem> passengerSegmentInfoItems;

  @Getter
  @Setter
  @Facebook("price_info")
  private List<PriceInfoItem> priceInfoItems;

  @Getter
  @Setter
  @Facebook("base_price")
  private Double basePrice;

  @Getter
  @Setter
  @Facebook
  private Double tax;

  @Getter
  @Setter
  @Facebook("total_price")
  private Double totalPrice;

  @Getter
  @Setter
  @Facebook("update_flight_info")
  private FlightInfoItem updateFlightInfo;

  @Getter
  @Setter
  @Facebook("checkin_url")
  private String checkinUrl;

  @Getter
  @Setter
  @Facebook("boarding_pass")
  private List<BoardingPassItem> boardingPassItems;

  @Getter
  @Setter
  @Facebook
  private ProductTemplateItem product;

  @Getter
  @Setter
  private String fallback;

  /**
   * Title of the attachment. Applicable to attachment type: fallback
   */
  @Getter
  @Setter
  @Facebook
  private String title;

  /**
   * The user may send a like and this method can be used to discover the three know versions of the sticker
   *
   * @return {@code true} if the user sent a like (thumb up sticker), {@code false} otherwise
   */
  public boolean isLike() {
    return "369239263222822".equals(stickerId) // small like (thumb up) sticker
        || "369239343222814".equals(stickerId) // medium size sticker
        || "369239383222810".equals(stickerId); // large size sticker
  }
}
