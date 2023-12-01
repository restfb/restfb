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
package com.restfb.util;

import static java.lang.String.format;
import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toList;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.10
 */
public final class UrlUtils {

  /**
   * Prevents instantiation.
   */
  private UrlUtils() {
    throw new IllegalStateException("UrlUtils must not be instantiated");
  }

  /**
   * URL-encodes a string.
   * <p>
   * Assumes {@code string} is in {@link StandardCharsets#UTF_8} format.
   * 
   * @param string
   *          The string to URL-encode.
   * @return The URL-encoded version of the input string, or {@code null} if {@code string} is {@code null}.
   * @throws IllegalStateException
   *           If unable to URL-encode because the JVM doesn't support {@link StandardCharsets#UTF_8}.
   */
  public static String urlEncode(String string) {
    if (string == null) {
      return null;
    }
    try {
      return encode(string, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("Platform doesn't support " + StandardCharsets.UTF_8.name(), e);
    }
  }

  /**
   * URL-decodes a string.
   * <p>
   * Assumes {@code string} is in {@link StandardCharsets#UTF_8} format.
   * 
   * @param string
   *          The string to URL-decode.
   * @return The URL-decoded version of the input string, or {@code null} if {@code string} is {@code null}.
   * @throws IllegalStateException
   *           If unable to URL-decode because the JVM doesn't support {@link StandardCharsets#UTF_8}.
   * @since 1.6.5
   */
  public static String urlDecode(String string) {
    if (string == null) {
      return null;
    }
    try {
      return decode(string, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("Platform doesn't support " + StandardCharsets.UTF_8.name(), e);
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
   *           If unable to URL-decode because the JVM doesn't support {@link StandardCharsets#UTF_8}.
   */
  public static Map<String, List<String>> extractParametersFromQueryString(String queryString) {
    if (queryString == null) {
      return emptyMap();
    }

    // If there is no ? character at the front of the string, append it.
    return extractParametersFromUrl(
      format("restfb://url%s", queryString.startsWith("?") ? queryString : "?" + queryString));
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
   *           If unable to URL-decode because the JVM doesn't support {@link StandardCharsets#UTF_8}.
   */
  public static Map<String, List<String>> extractParametersFromUrl(String url) {
    if (url == null) {
      return emptyMap();
    }

    Map<String, List<String>> parameters = new HashMap<>();
    String[] urlParts = url.split("\\?");

    if (urlParts.length > 1) {
      String query = urlParts[1];
      parameters = Pattern.compile("&").splitAsStream(query) //
        .map(s -> Arrays.copyOf(s.split("="), 2))
        .collect(Collectors.groupingBy(s -> urlDecode(s[0]), Collectors.mapping(s -> urlDecode(s[1]), toList())));
    }

    return parameters;
  }

  /**
   * Modify the query string in the given {@code url} and return the new url as String.
   * <p>
   * The given key/value pair is added to the url. If the key is already present, it is replaced with the new value.
   *
   * @param url
   *          The URL which parameters should be modified.
   * @param key
   *          the key, that should be modified or added
   * @param value
   *          the value of the key/value pair
   * @return the modified URL as String
   */
  public static String replaceOrAddQueryParameter(String url, String key, String value) {
    String[] urlParts = url.split("\\?");
    String qParameter = key + "=" + value;

    if (urlParts.length == 2) {
      Map<String, List<String>> paramMap = extractParametersFromQueryString(urlParts[1]);
      if (paramMap.containsKey(key)) {
        String queryValue = paramMap.get(key).get(0);
        return url.replace(key + "=" + queryValue, qParameter);
      } else {
        return url + "&" + qParameter;
      }

    } else {
      return url + "?" + qParameter;
    }
  }

  /**
   * Remove the given key from the url query string and return the new URL as String.
   *
   * @param url
   *          The URL from which parameters are extracted.
   * @param key
   *          the key, that should be removed
   * @return the modified URL as String
   */
  public static String removeQueryParameter(String url, String key) {
    String[] urlParts = url.split("\\?");

    if (urlParts.length == 2) {
      Map<String, List<String>> paramMap = extractParametersFromQueryString(urlParts[1]);
      if (paramMap.containsKey(key)) {
        String queryValue = paramMap.get(key).get(0);
        String result = url.replace(key + "=" + queryValue, "");
        // improper separators have to be fixed
        // @TODO find a better way to solve this
        result = result.replace("?&", "?").replace("&&", "&");
        if (result.endsWith("&")) {
          return result.substring(0, result.length() - 1);
        } else {
          return result;
        }
      }
    }
    return url;
  }
}