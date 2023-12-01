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
package com.restfb.types.webhook.messaging.nlp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

public class NlpLocation extends BaseNlpEntity {

  @Facebook
  private List<Place> resolved = new ArrayList<>();

  public List<Place> getResolved() {
    return Collections.unmodifiableList(resolved);
  }

  public static class Place extends AbstractFacebookType {

    @Getter
    @Setter
    @Facebook
    private String name;

    @Getter
    @Setter
    @Facebook
    private String grain;

    @Getter
    @Setter
    @Facebook
    private String type;

    @Getter
    @Setter
    @Facebook("timezone")
    private String timezoneAsString;

    @Getter
    @Setter
    private TimeZone timeZone;

    @Getter
    @Setter
    @Facebook
    private Coords coords;

    @Getter
    @Setter
    @Facebook
    private External external;

    @JsonMapper.JsonMappingCompleted
    protected void convertTimezone() {
      if (timezoneAsString != null) {
        try {
          timeZone = TimeZone.getTimeZone(timezoneAsString);
        } catch (Exception e) {
          // exception during timezone detection is ignored here
        }
      }
    }
  }

  public static class Coords extends AbstractFacebookType {

    @Getter
    @Setter
    @Facebook("lat")
    private Double latitude;

    @Getter
    @Setter
    @Facebook("long")
    private Double longitude;
  }

  public static class External extends AbstractFacebookType {

    @Getter
    @Setter
    @Facebook
    private String geonames;

    @Getter
    @Setter
    @Facebook
    private String wikidata;

    @Getter
    @Setter
    @Facebook
    private String wikipedia;
  }

}
