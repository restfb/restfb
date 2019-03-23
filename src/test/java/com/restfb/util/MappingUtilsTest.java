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
package com.restfb.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.restfb.DefaultJsonMapper;
import com.restfb.types.ProfilePictureSource;

class MappingUtilsTest {

  @Test
  void convertRawPicture_complete() {
    MappingUtils utils = new MappingUtils(new DefaultJsonMapper());
    String jsonString = "{\n" + //
        "    \"data\": {\n" + //
        "      \"is_silhouette\": false,\n" + //
        "      \"url\": \"https://fbcdn-profile-a.akamaihd.net/testpicture.jpg\"\n" + //
        "    }\n" + "  }";
    ProfilePictureSource profilePicture = utils.convertPicture(jsonString);
    assertFalse(profilePicture.getIsSilhouette());
    assertEquals("https://fbcdn-profile-a.akamaihd.net/testpicture.jpg", profilePicture.getUrl());
  }

  @Test
  void convertRawPicture_nullInput() {
    MappingUtils utils = new MappingUtils(new DefaultJsonMapper());
    assertNull(utils.convertPicture(null));
  }

  @Test
  void convertRawPicture_brokenJson() {
    MappingUtils utils = new MappingUtils(new DefaultJsonMapper());
    assertNull(utils.convertPicture("\"picture\":{}"));
  }

  @Test
  void convertRawPicture_jsonNoObject() {
    MappingUtils utils = new MappingUtils(new DefaultJsonMapper());
    assertNull(utils.convertPicture("12345"));
  }
}
