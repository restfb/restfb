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
package com.restfb.types.ads;

import java.util.List;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

public class AdAccountTargetingInsights extends NamedAdsObject {

  @Getter
  @Setter
  @Facebook("audience_size")
  private Long audienceSize;

  @Getter
  @Setter
  @Facebook("exploration_ratio")
  private Double explorationRatio;

  @Getter
  @Setter
  @Facebook("path")
  private List<String> path;

  @Getter
  @Setter
  @Facebook("performance_rating")
  private Long performanceRating;

  @Getter
  @Setter
  @Facebook("recommendation_rating")
  private Long recommendationRating;

  @Getter
  @Setter
  @Facebook("tags")
  private List<String> tags;

  @Getter
  @Setter
  @Facebook("type")
  private String type;
}
