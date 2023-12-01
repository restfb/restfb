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
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/open-graph-action-type/">Open Graph
 * Action Type</a>
 */
public class OpenGraphActionType extends NamedFacebookType {

  /**
   * Whether multiple objects can be referenced by the action
   */
  @Getter
  @Setter
  @Facebook("allow_multiple_references")
  private Boolean allowMultipleReferences;

  /**
   * The preposition used to address the app
   */
  @Getter
  @Setter
  @Facebook("app_preposition")
  private String appPreposition;

  /**
   * Button text
   */
  @Getter
  @Setter
  @Facebook("button_text")
  private String buttonText;

  /**
   * Description
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * Whether the app secret is required for API calls related to actions of this type
   */
  @Getter
  @Setter
  @Facebook("is_app_secret_required")
  private Boolean isAppSecretRequired;

  @Facebook("object_types")
  private List<String> objectTypes = new ArrayList<>();

  /**
   * The plural past form of the action
   */
  @Getter
  @Setter
  @Facebook("plural_past")
  private String pluralPast;

  /**
   * The plural present form of the action
   */
  @Getter
  @Setter
  @Facebook("plural_present")
  private String pluralPresent;

  @Facebook("property_config")
  private Map<String, OpenGraphPropertyConfig> propertyConfig = new HashMap<>();

  /**
   * The singular past form of the action
   */
  @Getter
  @Setter
  @Facebook("singular_past")
  private String singularPast;

  /**
   * The singular present form of the action
   */
  @Getter
  @Setter
  @Facebook("singular_present")
  private String singularPresent;

  /**
   * Bitmap of tenses that are disabled (the least significant bit relates to past tense, the second least significant
   * relates to present tense
   */
  @Getter
  @Setter
  @Facebook("tenses_disabled")
  private Integer tensesDisabled;

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

  /**
   * Object types associated with this action type
   *
   * @return Object types associated with this action type
   */
  public List<String> getObjectTypes() {
    return Collections.unmodifiableList(objectTypes);
  }

  public boolean addObjectType(String objectType) {
    return objectTypes.add(objectType);
  }

  public boolean removeObjectType(String objectType) {
    return objectTypes.remove(objectType);
  }
}
