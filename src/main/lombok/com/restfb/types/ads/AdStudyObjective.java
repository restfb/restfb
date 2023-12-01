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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

public class AdStudyObjective extends NamedAdsObject {

  /**
   * Detected custom attributes for this objective
   */
  @Facebook("custom_attributes")
  private List<String> customAttributes = new ArrayList<>();

  /**
   * Is this the primary objective of the study
   */
  @Getter
  @Setter
  @Facebook("is_primary")
  private Boolean isPrimary;

  /**
   * Results of the study for this objective
   */
  @Facebook
  private List<String> results = new ArrayList<>();

  /**
   * The type of the objective
   */
  @Getter
  @Setter
  @Facebook
  private String type;

  public boolean addCustomAttribute(String attribute) {
    return customAttributes.add(attribute);
  }

  public boolean removeCustomAttribute(String attribute) {
    return customAttributes.remove(attribute);
  }

  public List<String> getCustomAttributes() {
    return Collections.unmodifiableList(customAttributes);
  }

  public boolean addResult(String result) {
    return results.add(result);
  }

  public boolean removeResult(String result) {
    return results.remove(result);
  }

  public List<String> getResults() {
    return Collections.unmodifiableList(results);
  }
}
