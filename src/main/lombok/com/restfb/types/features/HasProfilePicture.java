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
package com.restfb.types.features;

import com.restfb.JsonMapper;
import com.restfb.json.Json;
import com.restfb.json.JsonValue;
import com.restfb.json.ParseException;
import com.restfb.types.ProfilePictureSource;

public interface HasProfilePicture {

  ProfilePictureSource getPicture();

  default ProfilePictureSource convertPicture(JsonMapper mapper, String rawPicture) {
    if (rawPicture == null) {
      return null;
    }

    try {
      JsonValue jsonValue = Json.parse(rawPicture);

      if (!jsonValue.isObject()) {
        return null;
      }

      String picJson = jsonValue.asObject().get("data").toString();
      return mapper.toJavaObject(picJson, ProfilePictureSource.class);
    } catch (ParseException pe) {
      return null;
    }
  }
}
