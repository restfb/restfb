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
package com.restfb.types.ads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

public class TargetingResponseDemographic extends NamedAdsObject {

  @Getter
  @Setter
  @Facebook
  private String type;

  @Facebook
  private List<String> path = new ArrayList<>();

  @Getter
  @Setter
  @Facebook("audience_size")
  private Long audienceSize;

  @Getter
  @Setter
  @Facebook
  private String topic;

  @Getter
  @Setter
  @Facebook
  private String description;

  @Getter
  @Setter
  @Facebook("disambiguation_category")
  private String disambiguationCategory;

  public List<String> getPath() {
    return Collections.unmodifiableList(path);
  }

  public boolean addPath(String pathPart) {
    return path.add(pathPart);
  }

  public boolean removePath(String pathPart) {
    return path.remove(pathPart);
  }
}
