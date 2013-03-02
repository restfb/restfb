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

package com.restfb.util;

import static java.lang.String.format;
import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;
import static java.util.Collections.emptyMap;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.10
 */
public final class UrlUtils {
  /**
   * Default charset to use for encoding/decoding strings.
   */
  public static final String ENCODING_CHARSET = "UTF-8";

  /**
   * Prevents instantiation.
   */
  private UrlUtils() {}

  /**
   * URL-encodes a string.
   * <p>
   * Assumes {@code string} is in {@value #ENCODING_CHARSET} format.
   * 
   * @param string
   *          The string to URL-encode.
   * @return The URL-encoded version of the input string, or {@code null} if {@code string} is {@code null}.
   * @throws IllegalStateException
   *           If unable to URL-encode because the JVM doesn't support {@value #ENCODING_CHARSET}.
   */
  public static String urlEncode(String string) {
    if (string == null)
      return null;
    try {
      return encode(string, ENCODING_CHARSET);
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("Platform doesn't support " + ENCODING_CHARSET, e);
    }
  }

  /**
   * URL-decodes a string.
   * <p>
   * Assumes {@code string} is in {@value #ENCODING_CHARSET} format.
   * 
   * @param string
   *          The string to URL-decode.
   * @return The URL-decoded version of the input string, or {@code null} if {@code string} is {@code null}.
   * @throws IllegalStateException
   *           If unable to URL-decode because the JVM doesn't support {@value #ENCODING_CHARSET}.
   * @since 1.6.5
   */
  public static String urlDecode(String string) {
    if (string == null)
      return null;
    try {
      return decode(string, ENCODING_CHARSET);
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("Platform doesn't support " + ENCODING_CHARSET, e);
    }
  }

  /**
   * For the given {@code queryString}, extract a mapping of query string parameter names to values.
   * <p>
   * Example of a {@code queryString} is {@code accessToken=123&expires=345}.
   * 
   * @param queryString
   *          The URL query string from which parameters are extracted.
   * @return A mapping of query string parameter names to values. If {@code queryString} is {@code null}, an empty
   *         {@code Map} is returned.
   * @throws IllegalStateException
   *           If unable to URL-decode because the JVM doesn't support {@value #ENCODING_CHARSET}.
   */
  public static Map<String, List<String>> extractParametersFromQueryString(String queryString) {
    if (queryString == null)
      return emptyMap();

    // If there is no ? character at the front of the string, append it.
    return extractParametersFromUrl(format("restfb://url%s", queryString.startsWith("?") ? queryString : "?"
        + queryString));
  }

  /**
   * For the given {@code url}, extract a mapping of query string parameter names to values.
   * <p>
   * Adapted from an implementation by BalusC and dfrankow, available at
   * http://stackoverflow.com/questions/1667278/parsing-query-strings-in-java.
   * 
   * @param url
   *          The URL from which parameters are extracted.
   * @return A mapping of query string parameter names to values. If {@code url} is {@code null}, an empty {@code Map}
   *         is returned.
   * @throws IllegalStateException
   *           If unable to URL-decode because the JVM doesn't support {@value #ENCODING_CHARSET}.
   */
  public static Map<String, List<String>> extractParametersFromUrl(String url) {
    if (url == null)
      return emptyMap();

    Map<String, List<String>> parameters = new HashMap<String, List<String>>();

    String[] urlParts = url.split("\\?");

    if (urlParts.length > 1) {
      String query = urlParts[1];

      for (String param : query.split("&")) {
        String pair[] = param.split("=");
        String key = urlDecode(pair[0]);
        String value = "";

        if (pair.length > 1)
          value = urlDecode(pair[1]);

        List<String> values = parameters.get(key);

        if (values == null) {
          values = new ArrayList<String>();
          parameters.put(key, values);
        }

        values.add(value);
      }
    }

    return parameters;
  }
}