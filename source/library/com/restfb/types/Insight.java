/*
 * Copyright (c) 2010-2013 Mark Allen.
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

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.json.JsonObject;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/insights" >Insight Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.3
 */
public class Insight extends NamedFacebookType {
  @Facebook
  private String period;

  @Facebook
  private String description;

  @Facebook
  private List<JsonObject> values = new ArrayList<JsonObject>();

  private static final long serialVersionUID = 1L;

  /**
   * Length of the period during which the insights were collected, e.g. 'day', 'week' or 'month'.
   * 
   * @return Length of the period during which the insights were collected.
   */
  public String getPeriod() {
    return period;
  }

  /**
   * The human-readable description of this Insight data.
   * 
   * @return The human-readable description of this Insight data.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Data for this Insight as a list of <tt>{@link com.restfb.json.JsonObject}</tt> because its structure can vary
   * depending on which type of Insight you're looking at.
   * 
   * @return Data for this Insight.
   */
  public List<JsonObject> getValues() {
    return values;
  }
}