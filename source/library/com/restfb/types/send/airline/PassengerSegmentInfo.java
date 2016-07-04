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

public class PassengerSegmentInfo {
  @Facebook("segment_id")
  private String segmentId;

  @Facebook("passenger_id")
  private String passengerId;

  @Facebook
  private String seat;

  @Facebook("seat_type")
  private String seatType;

  @Facebook("product_info")
  private List<ProductInfo> productInfoList;

  public PassengerSegmentInfo(String segmentId, String passengerId, String seat, String seatType) {
    this.segmentId = segmentId;
    this.passengerId = passengerId;
    this.seat = seat;
    this.seatType = seatType;
  }

  public boolean addProductInfo(ProductInfo productInfo) {
    if (productInfoList == null) {
      productInfoList = new ArrayList<ProductInfo>();
    }

    return productInfoList.add(productInfo);
  }
}
