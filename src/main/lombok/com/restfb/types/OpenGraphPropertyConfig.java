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

import java.util.*;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/open-graph-property-config/">Open
 * Graph Property Config</a> type
 */
public class OpenGraphPropertyConfig {

  @Facebook("allowed_values")
  private List<String> allowedValues = new ArrayList<>();

  /**
   * For string properties, whether the value is always lowercase
   */
  @Getter
  @Setter
  @Facebook("convert_lowercase")
  private Boolean convertLowercase;

  /**
   * For string properties, whether the value is always uppercase
   */
  @Getter
  @Setter
  @Facebook("convert_uppercase")
  private Boolean convertUppercase;

  /**
   * Human-readable name of the property
   */
  @Getter
  @Setter
  @Facebook("display_name")
  private String displayName;

  /**
   * The name of the property to use in markup and API
   */
  @Getter
  @Setter
  @Facebook("formal_name")
  private String formalName;

  /**
   * Whether the property is hidden from News Feed
   */
  @Getter
  @Setter
  @Facebook("hide_from_news_feed")
  private Boolean hideFromNewsFeed;

  /**
   * Whether the property takes multiple values
   */
  @Getter
  @Setter
  @Facebook("is_array")
  private Boolean isArray;

  /**
   * For numeric properties, the upper bound on the value
   */
  @Getter
  @Setter
  @Facebook("max_allowed")
  private Double maxAllowed;

  /**
   * For string properties, the upper bound on the value length
   */
  @Getter
  @Setter
  @Facebook("max_length")
  private Integer maxLength;

  /**
   * For numeric properties, the lower bound on the value
   */
  @Getter
  @Setter
  @Facebook("min_allowed")
  private Double minAllowed;

  /**
   * For string properties, the lower bound on the value length
   */
  @Getter
  @Setter
  @Facebook("min_length")
  private Integer minLength;

  /**
   * For reference properties, the object type associated with the property
   */
  @Getter
  @Setter
  @Facebook("reference_object_type")
  private String referenceObjectType;

  /**
   * Whether the property is required
   */
  @Getter
  @Setter
  @Facebook
  private Boolean required;

  /**
   * If set, at least one property for each group needs to be set
   */
  @Getter
  @Setter
  @Facebook("required_group")
  private String requiredGroup;

  @Facebook("struct_config")
  private Map<String, OpenGraphPropertyConfig> structConfig = new HashMap<>();

  /**
   * Type of the property
   */
  @Getter
  @Setter
  @Facebook
  private String type;

  /**
   * For enum properties, the set of allowed values
   * 
   * @return the set of allowed values
   */
  public List<String> getAllowedValues() {
    return Collections.unmodifiableList(allowedValues);
  }

  public boolean addAllowedValue(String value) {
    return allowedValues.add(value);
  }

  public boolean removeAllowedValue(String value) {
    return allowedValues.remove(value);
  }

  /**
   * For struct properties, the config for the nested properties
   * 
   * @return the config for the nested properties
   */
  public Map<String, OpenGraphPropertyConfig> getStructConfig() {
    return Collections.unmodifiableMap(structConfig);
  }

  public void addStructConfig(String key, OpenGraphPropertyConfig value) {
    structConfig.put(key, value);
  }

  public void removeStructConfig(String key) {
    structConfig.remove(key);
  }
}
