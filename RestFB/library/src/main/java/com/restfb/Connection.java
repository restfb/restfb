/*
 * Copyright (c) 2010-2011 Mark Allen.
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

import static com.restfb.util.StringUtils.isBlank;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;
import com.restfb.util.ReflectionUtils;

/**
 * Represents a <a href="http://developers.facebook.com/docs/api">Graph API
 * Connection type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Connection<T> {
  private final List<T> data;
  private final String previousPageUrl;
  private final String nextPageUrl;

  /**
   * Creates a connection with the given {@code jsonObject}.
   * 
   * @param json
   *          Raw JSON which must include a {@code data} field that holds a JSON
   *          array and optionally a {@code paging} field that holds a JSON
   *          object with next/previous page URLs.
   * @param jsonMapper
   *          The {@code JsonMapper} used to convert JSON into Java objects.
   * @param connectionType
   *          Connection type token.
   * @throws FacebookJsonMappingException
   *           If the provided {@code json} is invalid.
   */
  @SuppressWarnings("unchecked")
  public Connection(String json, JsonMapper jsonMapper, Class<T> connectionType) {
    List<T> data = new ArrayList<T>();

    if (json == null)
      throw new FacebookJsonMappingException("You must supply non-null connection JSON.");

    JsonObject jsonObject = null;

    try {
      jsonObject = new JsonObject(json);
    } catch (JsonException e) {
      throw new FacebookJsonMappingException("The connection JSON you provided was invalid: " + json, e);
    }

    // Pull out data
    JsonArray jsonData = jsonObject.getJsonArray("data");
    for (int i = 0; i < jsonData.length(); i++)
      data.add(connectionType.equals(JsonObject.class) ? (T) jsonData.get(i) : jsonMapper.toJavaObject(jsonData.get(i)
        .toString(), connectionType));

    // Pull out paging info, if present
    if (jsonObject.has("paging")) {
      JsonObject jsonPaging = jsonObject.getJsonObject("paging");
      previousPageUrl = jsonPaging.has("previous") ? jsonPaging.getString("previous") : null;
      nextPageUrl = jsonPaging.has("next") ? jsonPaging.getString("next") : null;
    } else {
      previousPageUrl = null;
      nextPageUrl = null;
    }

    this.data = unmodifiableList(data);
  }

  /**
   * Creates a connection with the given data and previous/next URLs.
   * 
   * @param data
   *          The connection's data.
   * @param previousPageUrl
   *          The URL for the previous page of data, or {@code null} if there is
   *          none.
   * @param nextPageUrl
   *          The URL for the next page of data, or {@code null} if there is
   *          none.
   */
  public Connection(List<T> data, String previousPageUrl, String nextPageUrl) {
    this.data = unmodifiableList(data == null ? new ArrayList<T>() : data);
    this.previousPageUrl = previousPageUrl;
    this.nextPageUrl = nextPageUrl;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ReflectionUtils.toString(this);
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    return ReflectionUtils.equals(this, object);
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ReflectionUtils.hashCode(this);
  }

  /**
   * Data for this connection.
   * 
   * @return Data for this connection.
   */
  public List<T> getData() {
    return data;
  }

  /**
   * This connection's "previous page of data" URL.
   * 
   * @return This connection's "previous page of data" URL, or {@code null} if
   *         there is no previous page.
   * @since 1.5.3
   */
  public String getPreviousPageUrl() {
    return previousPageUrl;
  }

  /**
   * This connection's "next page of data" URL.
   * 
   * @return This connection's "next page of data" URL, or {@code null} if there
   *         is no next page.
   * @since 1.5.3
   */
  public String getNextPageUrl() {
    return nextPageUrl;
  }

  /**
   * Does this connection have a previous page of data?
   * 
   * @return {@code true} if there is a previous page of data for this
   *         connection, {@code false} otherwise.
   */
  public boolean hasPrevious() {
    return !isBlank(getPreviousPageUrl());
  }

  /**
   * Does this connection have a next page of data?
   * 
   * @return {@code true} if there is a next page of data for this connection,
   *         {@code false} otherwise.
   */
  public boolean hasNext() {
    return !isBlank(getNextPageUrl());
  }
}