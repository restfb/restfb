/**
 * Copyright (c) 2010-2019 Mark Allen, Norbert Bartels.
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
package com.restfb.types;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;

import com.restfb.AbstractJsonMapperTests;

import org.junit.Test;

public class EventTest extends AbstractJsonMapperTests {

  @Test
  public void issue252_eventWithLocation_1() {
    Event event = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/issue252-event-1"), Event.class);
    assertNotNull(event);
    assertNotNull(event.getPlace());
    assertNotNull(event.getPlace().getLocation());
  }

  @Test
  public void issue252_eventWithLocation_2() {
    Event event = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/issue252-event-2"), Event.class);
    assertNotNull(event);
    assertNotNull(event.getPlace());
    assertNotNull(event.getPlace().getLocation());
    assertTrue(event.getIsCanceled());
  }

  @Test
  public void issue312_eventPictureAndCover() {
    Event event = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/issue312-event"), Event.class);
    assertNotNull(event);
    assertNotNull(event.getPicture());
    assertEquals(
      "https://fbcdn-photos-h-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-0/c43.0.50.50/p50x50/12006310_1078877852124437_9067576785883091133_n.png?oh=252b25a37adf3e52b98b2377daead960&oe=56A03EC4&__gda__=1456603383_a12d1727201e97640e41c74e72e8a03a",
      event.getPicture().getUrl());
    assertFalse(event.getPicture().getIsSilhouette().booleanValue());

    assertNotNull(event.getCover());
    assertEquals(
      "https://scontent.xx.fbcdn.net/hphotos-xfp1/v/t1.0-9/s720x720/12006310_1078877852124437_9067576785883091133_n.png?oh=561599ccf7e8247dbe550c6e1759aa39&oe=56D0F885",
      event.getCover().getSource());
  }

  @Test
  public void exampleEventWithVenueList() {
    Event event = createJsonMapper().toJavaObject(jsonFromClasspath("v2_3/event-with-venuelist"), Event.class);
    assertNotNull(event);
    assertNotNull(event.getVenueList());
    assertEquals(2, event.getVenueList().size());
  }

  @Test
  public void exampleEventWithLocation() {
    Event event = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/event"), Event.class);
    assertNotNull(event);
    assertNotNull(event.getName());
    assertTrue(event.getName().contains("New York"));
    assertEquals(52, event.getAttendingCount().intValue());
    assertNotNull(event.getPlace());
    assertEquals(3.8, event.getPlace().getOverallRating().doubleValue(), 0.1);
    assertNotNull(event.getPlace().getLocation());
    assertTrue(event.getPlace().getName().contains("Ratskeller"));
    assertEquals("Germany", event.getPlace().getLocation().getCountry());
    assertEquals("66111", event.getPlace().getLocation().getZip());
    assertEquals(49.2349091, event.getPlace().getLocation().getLatitude().doubleValue(), 0.1);
  }

  @Test
  public void checkV2_0() {
    Event event = createJsonMapper().toJavaObject(jsonFromClasspath("v2_0/event"), Event.class);
    assertNotNull(event);
    assumeThat(event.getDescription(), containsString("chocolate event"));
    assumeThat(event.getName(), containsString("Chocolate Festival"));
    assertEquals("Africa/Johannesburg", event.getTimezone());
    assertEquals("300473363410132", event.getId());
    assertEquals("OPEN", event.getPrivacy());
    assertNotNull(event.getLocation());
    assertEquals("Lourensford Estate", event.getLocation());
    assertNotNull(event.getOwner());
    assertNotNull(event.getVenue());
    assertEquals("181719998521108", event.getVenue().getId());
    assertEquals(-34.071977375471, event.getVenue().getLatitude(), 0.1);
    assertEquals(18.888539577026, event.getVenue().getLongitude(), 0.1);
    assertEquals("Somerset West", event.getVenue().getCity());

  }

  @Test
  public void checkV2_5() {
    Event event = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/event-location"), Event.class);
    assertNotNull(event);
    assumeThat(event.getDescription(), containsString("chocolate event"));
    assumeThat(event.getName(), containsString("Chocolate Festival"));
    assertEquals("Africa/Johannesburg", event.getTimezone());
    assertEquals("300473363410132", event.getId());
    assertNotNull(event.getCover());
    assertNotNull(event.getPlace());
    assertEquals("Lourensford Estate", event.getPlace().getName());
    assertNotNull(event.getPlace().getLocation());
    assertEquals("Somerset West", event.getPlace().getLocation().getCity());
    assertEquals(-34.071977375471, event.getPlace().getLocation().getLatitude(), 0.1);
    assertEquals(18.888539577026, event.getPlace().getLocation().getLongitude(), 0.1);
    assertNotNull(event.getOwner());
  }

  @Test
  public void checkV2_5_issue380() {
    Event event = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/event-fields"), Event.class);
    assertNotNull(event);
    assertTrue(event.getIsPageOwned());
    assertTrue(event.getIsViewerAdmin());
    assertEquals(0L, event.getDeclinedCount().longValue());
    assertTrue(event.getCanGuestsInvite());
    assertEquals(1455840000000L, event.getStartTime().getTime());
    assertEquals("Europe/Berlin", event.getTimezone());
    assertEquals(0L, event.getMaybeCount().longValue());
    assertEquals(0L, event.getNoreplyCount().longValue());
    assertEquals(0L, event.getInterestedCount().longValue());
  }

}
