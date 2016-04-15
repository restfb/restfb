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
package com.restfb.types;

import static org.junit.Assert.*;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.types.User.Picture;

import org.junit.Test;

import java.util.List;

public class UserTest extends AbstractJsonMapperTests {

  @Test
  public void checkUserPicture() {
    User exampleUser = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/user-picture"), User.class);
    assertEquals("Tester", exampleUser.getName());
    assertEquals("123456789", exampleUser.getId());
    Picture pic = exampleUser.getPicture();
    assertFalse(pic.getIsSilhouette());
    assertNotNull(pic.getUrl());
    assertEquals("https://fbcdn-profile-a.akamaihd.net/profilepic", pic.getUrl());
  }

  @Test
  public void checkUserNoPicture() {
    User exampleUser = createJsonMapper().toJavaObject(jsonFromClasspath("v1_0/user-no-picture"), User.class);
    assertEquals("Tester", exampleUser.getName());
    assertEquals("123456789", exampleUser.getId());
    assertTrue(null == exampleUser.getPicture());
  }

  @Test
  public void checkAgeRangeNoUpper() {
    User exampleUser = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/age_range_no_upper"), User.class);
    assertEquals("Test Benutzer", exampleUser.getName());
    assertEquals("123456789", exampleUser.getId());
    assertEquals(21, exampleUser.getAgeRange().getMin().intValue());
    assertNull(exampleUser.getAgeRange().getMax());
  }

  @Test
  public void checkAgeRange() {
    User exampleUser = createJsonMapper().toJavaObject(jsonFromClasspath("v2_2/age_range"), User.class);
    assertEquals("Test Benutzer", exampleUser.getName());
    assertEquals("123456789", exampleUser.getId());
    assertEquals(13, exampleUser.getAgeRange().getMin().intValue());
    assertEquals(17, exampleUser.getAgeRange().getMax().intValue());
  }

  @Test
  public void checkCoverPhoto() {
    User exampleUser = createJsonMapper().toJavaObject(jsonFromClasspath("v2_4/cover_photo"), User.class);
    assertEquals("Test Benutzer", exampleUser.getName());
    assertEquals("123456789", exampleUser.getId());
    assertNotNull(exampleUser.getCover());
    assertEquals("987654321", exampleUser.getCover().getId());
    assertEquals(0, exampleUser.getCover().getOffsetY().intValue());
    assertEquals("https://scontent.xx.fbcdn.net/hphotos-xaf1/v/l/t1.0-9/s720x720/34352425342534.jpg",
      exampleUser.getCover().getSource());
  }

  @Test
  public void userWorkDates() {
    User exampleUser = createJsonMapper().toJavaObject(jsonFromClasspath("v2_5/user-work"), User.class);
    assertNotNull(exampleUser);
    List<User.Work> workList = exampleUser.getWork();
    assertEquals(1, workList.size());
    User.Work work = workList.get(0);
    assertNotNull(work.getStartDate());
    assertEquals(1193875200000L, work.getStartDate().getTime());
    assertNotNull(work.getEndDate());
    assertEquals(1348963200000L, work.getEndDate().getTime());
  }
  @Test
  public void checkV2_6_label() {
    User exampleUser = createJsonMapper().toJavaObject(jsonFromClasspath("v2_6/user-with-labels"), User.class);
    assertNotNull(exampleUser);
    assertNotNull(exampleUser.getLabels());
    assertEquals(1L, exampleUser.getLabels().size());
  }

}
