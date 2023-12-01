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
package com.restfb.types.ads;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.json.Json;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonValue;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/marketing-api/reference/ads-image-crops/">AdsImageCrops</a> Marketing
 * API type
 *
 * Every field is following the crops specification, in the format of [[{left},{top}],[{right},{bottom}]].
 */
public class AdsImageCrops extends AbstractFacebookType {

  @Getter
  @Setter
  private AdsImageCropsSpec thumb100x100;

  @Facebook("100x100")
  private String thumb100x100String;

  @Getter
  @Setter
  private AdsImageCropsSpec thumb100x72;

  @Facebook("100x72")
  private String thumb100x72String;

  @Getter
  @Setter
  private AdsImageCropsSpec thumb191x100;

  @Facebook("191x100")
  private String thumb191x100String;

  @Getter
  @Setter
  private AdsImageCropsSpec thumb400x150;

  @Facebook("400x150")
  private String thumb400x150String;

  @Getter
  @Setter
  private AdsImageCropsSpec thumb600x360;

  @Facebook("600x360")
  private String thumb600x360String;

  public static class AdsImageCropsSpec extends AbstractFacebookType {

    @Getter
    @Setter
    private Long left;

    @Getter
    @Setter
    private Long top;

    @Getter
    @Setter
    private Long right;

    @Getter
    @Setter
    private Long bottom;

  }

  @JsonMapper.JsonMappingCompleted
  private void convert() {
    thumb100x100 = createSpecFromString(thumb100x100String);
    thumb100x72 = createSpecFromString(thumb100x72String);
    thumb191x100 = createSpecFromString(thumb191x100String);
    thumb400x150 = createSpecFromString(thumb400x150String);
    thumb600x360 = createSpecFromString(thumb600x360String);
  }

  private AdsImageCropsSpec createSpecFromString(String input) {
    AdsImageCropsSpec spec = null;
    if (input != null) {
      spec = new AdsImageCropsSpec();
      JsonValue ar = Json.parse(input);
      JsonArray outerArray = ar.asArray();
      JsonArray leftTop = outerArray.get(0).asArray();
      spec.setLeft(leftTop.get(0).asLong());
      spec.setTop(leftTop.get(1).asLong());
      JsonArray rightBottom = outerArray.get(1).asArray();
      spec.setRight(rightBottom.get(0).asLong());
      spec.setBottom(rightBottom.get(1).asLong());
    }
    return spec;
  }
}
