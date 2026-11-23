/*
 * Copyright (c) 2010-2026 Mark Allen, Norbert Bartels.
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
package com.restfb.types.threads;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.restfb.AbstractJsonMapperTests;
import com.restfb.JsonMapper;
import com.restfb.json.Json;

@SuppressWarnings("removal")
class ThreadsModelCompatibilityTest extends AbstractJsonMapperTests {

  @ParameterizedTest(name = "{0}")
  @MethodSource("objectFixtures")
  void mapsTheSameGraphFields(String fixture, Class<?> canonical, Class<?> legacy) {
    String json = jsonFromClasspath("threads/" + fixture);
    JsonMapper mapper = createJsonMapper();
    Object th = mapper.toJavaObject(json, canonical);
    Object td = mapper.toJavaObject(json, legacy);

    assertNotNull(th);
    assertNotNull(td);
    assertEquals(Json.parse(mapper.toJson(td)), Json.parse(mapper.toJson(th)));
  }

  static Stream<Arguments> objectFixtures() {
    return Stream.of(
      fixtures(ThMedia.class, TdMedia.class, "basic-media", "media-with-additional-fields", "additional-fields-null",
        "media-with-poll", "media-with-location", "media-with-text-attachment", "media-without-text-attachment",
        "media-with-empty-text-styling-info", "media-with-incomplete-text-attachment"),
      fixtures(ThReply.class, TdReply.class, "basic-reply", "reply-with-additional-fields", "additional-fields-null"),
      fixtures(ThMediaContainer.class, TdMediaContainer.class, "media-container-success", "media-container-error"),
      fixtures(ThProfile.class, TdProfile.class, "basic-profile"),
      fixtures(ThQuota.class, TdQuota.class, "basic-quota")).flatMap(stream -> stream);
  }

  private static Stream<Arguments> fixtures(Class<?> canonical, Class<?> legacy, String... names) {
    return Arrays.stream(names).map(name -> arguments(name, canonical, legacy));
  }

  @Test
  void keepsNestedModelsInTheirOwnFamily() {
    JsonMapper mapper = createJsonMapper();
    String json = "{\"children\":[{\"media_type\":\"IMAGE\"}],\"root_post\":{\"hide_status\":\"NOT_HUSHED\"}}";
    ThMedia th = mapper.toJavaObject(json, ThMedia.class);
    TdMedia td = mapper.toJavaObject(json, TdMedia.class);
    List<ThMedia> thChildren = th.getChildren();
    List<TdMedia> tdChildren = td.getChildren();

    assertEquals(ThMediaType.IMAGE, thChildren.get(0).getMediaType());
    assertEquals(TdMediaType.IMAGE, tdChildren.get(0).getMediaType());
    assertEquals(ThReply.ThHideStatus.NOT_HUSHED, th.getRootPost().getHideStatus());
    assertEquals(TdReply.TdHideStatus.NOT_HUSHED, td.getRootPost().getHideStatus());
    assertNull(th.getIsReply());
    assertNull(td.getIsReply());
  }
}
