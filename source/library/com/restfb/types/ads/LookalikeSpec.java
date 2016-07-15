/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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
import com.restfb.types.AbstractFacebookType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class LookalikeSpec extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook
  private String country;

  @Getter
  @Setter
  @Facebook("is_financial_service")
  private Boolean isFinancialService;

  @Facebook("origin")
  private List<Origin> origin = new ArrayList<Origin>();

  @Getter
  @Setter
  @Facebook
  private Double ratio;

  @Getter
  @Setter
  @Facebook("starting_ratio")
  private Double startingRatio;

  @Getter
  @Setter
  @Facebook
  private String type;

  public boolean addOrigin(Origin object) {
    return origin.add(object);
  }

  public boolean removeOrigin(Origin object) {
    return origin.remove(object);
  }

  public List<Origin> getOrigin() {
    return Collections.unmodifiableList(origin);
  }

  public static class Origin extends NamedAdsObject {

    /**
     * {@code true}, returned only when the origin is deleted
     *
     * -- GETTER --
     *
     * @return {@code true}, returned only when the origin is deleted
     */
    @Getter
    @Setter
    @Facebook
    private Boolean deleted;

    /**
     * {@code custom_audience} or {@code conversion_pixel} or {@code page} or {@code app}
     *
     * -- GETTER --
     *
     * @return {@code custom_audience} or {@code conversion_pixel} or {@code page} or {@code app}
     */
    @Getter
    @Setter
    @Facebook
    private String type;

  }

}
