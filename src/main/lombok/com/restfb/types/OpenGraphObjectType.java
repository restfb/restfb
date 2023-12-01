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

import java.util.Collections;
import java.util.Map;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/open-graph-object-type/">Open Graph
 * Object Type</a>
 */
public class OpenGraphObjectType extends NamedFacebookType {

  /**
   * An article for the type name
   */
  @Getter
  @Setter
  @Facebook
  private String article;

  /**
   * The plural form of the object
   */
  @Getter
  @Setter
  @Facebook
  private String plural;

  /**
   * The singular form of the object
   */
  @Getter
  @Setter
  @Facebook
  private String singular;

  @Facebook("property_config")
  private Map<String, OpenGraphPropertyConfig> propertyConfig;

  /**
   * Per-property config
   *
   * @return Per-property config
   */
  public Map<String, OpenGraphPropertyConfig> getPropertyConfig() {
    return Collections.unmodifiableMap(propertyConfig);
  }

  public void addPropertyConfig(String key, OpenGraphPropertyConfig value) {
    propertyConfig.put(key, value);
  }

  public void removePropertyConfig(String key) {
    propertyConfig.remove(key);
  }
}
