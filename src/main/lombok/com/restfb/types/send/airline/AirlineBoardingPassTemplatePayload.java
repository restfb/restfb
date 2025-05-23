/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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
 * "https://developers.facebook.com/docs/messenger-platform/send-api-reference/airline-boardingpass-template">Airline
 * Boardingpass Template Payload</a> type
 */
public class AirlineBoardingPassTemplatePayload extends TemplatePayload {

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
  @Facebook("boarding_pass")
  private List<BoardingPass> boardingPassList;

  public AirlineBoardingPassTemplatePayload(String introMessage, String locale, List<BoardingPass> boardingPassList) {
    setTemplateType("airline_boardingpass");
    this.introMessage = introMessage;
    this.locale = locale;
    this.boardingPassList = boardingPassList;
  }

  public boolean addBoardingPass(BoardingPass boardingPass) {
    if (boardingPassList == null) {
      boardingPassList = new ArrayList<>();
    }

    return boardingPassList.add(boardingPass);
  }
}
