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
package com.restfb.types;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

import com.restfb.annotation.GraphAPI;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents information about the place where an event occurred, for example a {@link Checkin} or {@link Photo}.
 * 
 * @author <a href="http://ex-nerd.com">Chris Petersen</a>
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.10
 */
public class Place extends NamedFacebookType {

  /**
   * Location containing geographic information such as latitude, longitude, country, and other fields (fields will vary
   * based on geography and availability of information).
   *
   * It is possible for Facebook to return either this value or {@link #getLocationAsString()}.
   * 
   * @return Location containing geographic information such as latitude, longitude, country, and other fields.
   */
  @Getter
  @Setter
  @Facebook
  private Location location;

  /**
   * Overall Rating of Place, on a 5-star scale. 0 means not enough data to get a combined rating.
   *
   * @return Overall Rating of Place, on a 5-star scale.
   */
  @Getter(onMethod = @__(@GraphAPI(since = "2.5")))
  @Setter
  @Facebook("overall_rating")
  @GraphAPI(since = "2.5")
  private Double overallRating;

  /**
   * Description for this location.
   * <p>
   * It is possible for Facebook to return either this value or {@link #getLocation()}. If {@link #getLocation()}
   * returns {@code null}, then check this method to see if it has data, e.g. {@code "Philadelphia, PA"}.
   * 
   * @return Description for this location.
   * @since 1.6.12
   */
  @Getter
  @Setter
  @Facebook("location")
  private String locationAsString;

  @Facebook("category_list")
  private List<Category> categoryList = new ArrayList<>();

  /**
   * List of other categories for this place.
   * 
   * @return List of other categories for this place.
   * @since 1.6.15
   */
  public List<Category> getCategoryList() {
    return unmodifiableList(categoryList);
  }

  public boolean addCategory(Category category) {
    return categoryList.add(category);
  }

  public boolean removeCategory(Category category) {
    return categoryList.remove(category);
  }

  private static final long serialVersionUID = 1L;

}