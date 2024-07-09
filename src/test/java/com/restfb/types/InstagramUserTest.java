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
package com.restfb.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.restfb.AbstractJsonMapperTests;

class InstagramUserTest extends AbstractJsonMapperTests {

  @Test
  void checkV2_8_instagram() {
    InstagramUser instagramUser =
        createJsonMapper().toJavaObject(jsonFromClasspath("v2_8/instagram-user"), InstagramUser.class);
    assertNotNull(instagramUser);
    assertEquals(164L, instagramUser.getFollowCount().longValue());
    assertEquals(118L, instagramUser.getFollowedByCount().longValue());
    assertEquals(52L, instagramUser.getMediaCount().longValue());
    assertTrue(instagramUser.getHasProfilePicture());
    assertFalse(instagramUser.getIsPrivate());
    assertTrue(instagramUser.getIsPublished());
    assertEquals("https://scontent.cdninstagram.com/t51.2885-19/s150x150/abcabc.jpg", instagramUser.getProfilePic());
    assertEquals("abcabc", instagramUser.getUsername());
  }

}