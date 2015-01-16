/*
 * Copyright (c) 2010-2015 Norbert Bartels.
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
package com.restfb.util;

import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * a DateFormat strategy that returns a cached SimpleDateFormat instance.
 * 
 * For every format string an instance of the SimpleDateFormat is saved on a per thread base, so the SimpleDateFormat
 * instance is reused and you get an major speedup.
 * 
 * Attention: to prevent a possible memory leak while using this strategy you have to clean up the inner ThreadLocal
 * with the {@code clearThreadLocal} method.
 * 
 * @since 1.7.0
 */
public class CachedDateFormatStrategy implements DateFormatStrategy {

  @Override
  public DateFormat formatFor(String format) {
    return SimpleDateFormatHolder.formatFor(format);
  }

  public void clearThreadLocal() {
    SimpleDateFormatHolder.clearThreadLocal();
  }

  final static class SimpleDateFormatHolder {

    private static final ThreadLocal<SoftReference> THREADLOCAL_FORMATTER_MAP = new ThreadLocal<SoftReference>() {

      @Override
      protected SoftReference<Map> initialValue() {
        return new SoftReference<Map>(new HashMap<String, SimpleDateFormat>());
      }

    };

    public static SimpleDateFormat formatFor(String pattern) {
      SoftReference<Map> ref = THREADLOCAL_FORMATTER_MAP.get();
      Map<String, SimpleDateFormat> formatterMap = ref.get();
      if (formatterMap == null) {
        formatterMap = new HashMap<String, SimpleDateFormat>();
        THREADLOCAL_FORMATTER_MAP.set(new SoftReference<Map>(formatterMap));
      }

      SimpleDateFormat formatter = formatterMap.get(pattern);
      if (formatter == null) {
        formatter = new SimpleDateFormat(pattern);
        formatterMap.put(pattern, formatter);
      }

      return formatter;
    }

    public static void clearThreadLocal() {
      THREADLOCAL_FORMATTER_MAP.remove();
    }
  }

}
