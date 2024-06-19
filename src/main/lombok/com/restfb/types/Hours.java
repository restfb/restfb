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
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class Hours extends AbstractFacebookType {

  private Map<DayOfWeek, Map<Integer, Hour>> hours = new HashMap<>();

  /**
   * Returns a map of indices and {@link Hour} objects with the open and close time
   * 
   * @param day
   *          the day the map should be returned for
   * @return the map containing the index and the {@link Hour} object
   */
  public Map<Integer, Hour> getHours(DayOfWeek day) {
    if (hours.get(day) == null) {
      return null;
    }

    return Collections.unmodifiableMap(hours.get(day));
  }

  /**
   * returns the complete overview, with the day as key and a map as value
   * 
   * @return the complete overview
   */
  public Map<DayOfWeek, Map<Integer, Hour>> getHours() {
    return hours;
  }

  public boolean addHour(String key, String value) {
    String dayStr = key.substring(0, 3);

    DayOfWeek day = DayOfWeek.valueOf(dayStr.toUpperCase());

    if (!hours.containsKey(day)) {
      hours.put(day, new HashMap<>());
    }

    if (key.endsWith("open")) {
      String indexStr = key.substring(4).replace("_open", "");
      hours.get(day).get(createIndex(day, indexStr)).setOpen(value);
      return true;
    }

    if (key.endsWith("close")) {
      String indexStr = key.substring(4).replace("_close", "");
      hours.get(day).get(createIndex(day, indexStr)).setClose(value);
      return true;
    }

    return false;
  }

  private Integer createIndex(DayOfWeek day, String indexStr) {
    Integer index = Integer.valueOf(indexStr);
    if (hours.get(day).get(index) == null) {
      hours.get(day).put(index, new Hour());
    }
    return index;
  }

  public static class Hour extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String open;

    @Getter
    @Setter
    private String close;
  }

  public enum DayOfWeek {
    MON, TUE, WED, THU, FRI, SAT, SUN
  }

  private static final long serialVersionUID = 1L;
}
