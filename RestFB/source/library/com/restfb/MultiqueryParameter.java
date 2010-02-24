/*
 * Copyright (c) 2010 Mark Allen.
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

package com.restfb;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import com.restfb.json.JSONException;
import com.restfb.json.JSONObject;

/**
 * Special parameter type used to represent the {@code queries} parameter in the
 * <a href="http://wiki.developers.facebook.com/index.php/Fql.multiquery">
 * {@code fql.multiquery}</a> API call.
 * <p>
 * Please see the
 * {@link FacebookClient#executeMultiquery(Class, MultiqueryParameter, Parameter...)}
 * family of methods for more details on how this is used.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.1
 */
public final class MultiqueryParameter {
  /**
   * Queries provided in {@link #with(Map)}.
   */
  private Map<String, String> queries;

  /**
   * Queries provided in {@link #with(Map)} in JSON format.
   */
  private String queriesAsJson;

  /**
   * Prevents external instantiation.
   * 
   * @param queries
   *          Mapping of query names to queries.
   * @return A {@code MultiqueryParameter} instance with the given {@code
   *         queries}.
   * @throws IllegalArgumentException
   *           If {@code queries} contains {@code null} or empty keys or values.
   */
  private MultiqueryParameter(Map<String, String> queries) {
    if (queries == null)
      throw new IllegalArgumentException("Provided queries cannot be null.");

    JSONObject jsonObject = new JSONObject();

    for (Entry<String, String> entry : queries.entrySet()) {
      if (StringUtils.isBlank(entry.getKey())
          || StringUtils.isBlank(entry.getValue()))
        throw new IllegalArgumentException(
          "Provided queries must have non-blank keys and values. "
              + "Queries are: " + queries);

      try {
        jsonObject.put(StringUtils.trimToEmpty(entry.getKey()), StringUtils
          .trimToEmpty(entry.getValue()));
      } catch (JSONException e) {
        // Shouldn't happen unless bizarre input is provided
        throw new IllegalArgumentException("Unable to convert " + queries
            + " to JSON.", e);
      }
    }

    queriesAsJson = jsonObject.toString();
    this.queries = Collections.unmodifiableMap(queries);
  }

  /**
   * Gets the queries provided in {@link #with(Map)} in JSON format.
   * 
   * @return Queries provided in {@link #with(Map)} in JSON format.
   */
  String getQueriesAsJson() {
    return queriesAsJson;
  }

  /**
   * Factory method which provides an instance with the given mapping of query
   * names to queries.
   * 
   * @param queries
   *          Mapping of query names to queries.
   * @return A {@code MultiqueryParameter} instance with the given {@code
   *         queries}.
   * @throws IllegalArgumentException
   *           If {@code queries} is {@code null} or {@code queries} contains
   *           {@code null} or empty keys or values.
   */
  public static MultiqueryParameter with(Map<String, String> queries) {
    return new MultiqueryParameter(queries);
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!getClass().equals(obj.getClass()))
      return false;

    MultiqueryParameter other = (MultiqueryParameter) obj;

    if (this.queries != other.queries && (!this.queries.equals(other.queries)))
      return false;
    if (this.queriesAsJson != other.queriesAsJson
        && (!this.queriesAsJson.equals(other.queriesAsJson)))
      return false;

    return true;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + this.queries.hashCode();
    hash = 41 * hash + this.queriesAsJson.hashCode();
    return hash;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return queries.toString();
  }
}