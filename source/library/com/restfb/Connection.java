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

package com.restfb;

import static com.restfb.util.StringUtils.isBlank;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;
import com.restfb.util.ReflectionUtils;

/**
 * Represents a <a href="http://developers.facebook.com/docs/api">Graph API Connection type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Connection<T> implements Iterable<List<T>> {
  private FacebookClient facebookClient;
  private Class<T> connectionType;
  private List<T> data;
  private String previousPageUrl;
  private String nextPageUrl;

  /**
   * @see java.lang.Iterable#iterator()
   * @since 1.6.7
   */
  public Iterator<List<T>> iterator() {
    return new ConnectionIterator<T>(this);
  }

  /**
   * Iterator over connection pages.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.7
   */
  protected static class ConnectionIterator<T> implements Iterator<List<T>> {
    private Connection<T> connection;
    private boolean initialPage = true;

    /**
     * Creates a new iterator over the given {@code connection}.
     * 
     * @param connection
     *          The connection over which to iterate.
     */
    protected ConnectionIterator(Connection<T> connection) {
      this.connection = connection;
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
      // Special case: initial page will always have data
      if (initialPage)
        return true;

      return connection.hasNext();
    }

    /**
     * @see java.util.Iterator#next()
     */
    public List<T> next() {
      // Special case: initial page will always have data, return it
      // immediately.
      if (initialPage) {
        initialPage = false;
        return connection.getData();
      }

      if (!connection.hasNext())
        throw new NoSuchElementException("There are no more pages in the connection.");

      connection = connection.fetchNextPage();
      return connection.getData();
    }

    /**
     * @see java.util.Iterator#remove()
     */
    public void remove() {
      throw new UnsupportedOperationException(ConnectionIterator.class.getSimpleName()
          + " doesn't support the remove() operation.");
    }
  }

  /**
   * Creates a connection with the given {@code jsonObject}.
   * 
   * @param facebookClient
   *          The {@code FacebookClient} used to fetch additional pages and map data to JSON objects.
   * @param json
   *          Raw JSON which must include a {@code data} field that holds a JSON array and optionally a {@code paging}
   *          field that holds a JSON object with next/previous page URLs.
   * @param connectionType
   *          Connection type token.
   * @throws FacebookJsonMappingException
   *           If the provided {@code json} is invalid.
   * @since 1.6.7
   */
  @SuppressWarnings("unchecked")
  public Connection(FacebookClient facebookClient, String json, Class<T> connectionType) {
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
      data.add(connectionType.equals(JsonObject.class) ? (T) jsonData.get(i) : facebookClient.getJsonMapper()
        .toJavaObject(jsonData.get(i).toString(), connectionType));

    // Pull out paging info, if present
    if (jsonObject.has("paging")) {
      JsonObject jsonPaging = jsonObject.getJsonObject("paging");
      previousPageUrl = jsonPaging.has("previous") ? jsonPaging.getString("previous") : null;
      nextPageUrl = jsonPaging.has("next") ? jsonPaging.getString("next") : null;
      if (null != previousPageUrl && previousPageUrl.startsWith("http://")) {
        previousPageUrl = previousPageUrl.replaceFirst("http://", "https://");
      }
      if (null != nextPageUrl && nextPageUrl.startsWith("http://")) {
        nextPageUrl = nextPageUrl.replaceFirst("http://", "https://");
      }
    } else {
      previousPageUrl = null;
      nextPageUrl = null;
    }

    this.data = unmodifiableList(data);
    this.facebookClient = facebookClient;
    this.connectionType = connectionType;
  }

  /**
   * Fetches the next page of the connection. Designed to be used by {@link ConnectionIterator}.
   * 
   * @return The next page of the connection.
   * @since 1.6.7
   */
  protected Connection<T> fetchNextPage() {
    return facebookClient.fetchConnectionPage(getNextPageUrl(), connectionType);
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
   * @return This connection's "previous page of data" URL, or {@code null} if there is no previous page.
   * @since 1.5.3
   */
  public String getPreviousPageUrl() {
    return previousPageUrl;
  }

  /**
   * This connection's "next page of data" URL.
   * 
   * @return This connection's "next page of data" URL, or {@code null} if there is no next page.
   * @since 1.5.3
   */
  public String getNextPageUrl() {
    return nextPageUrl;
  }

  /**
   * Does this connection have a previous page of data?
   * 
   * @return {@code true} if there is a previous page of data for this connection, {@code false} otherwise.
   */
  public boolean hasPrevious() {
    return !isBlank(getPreviousPageUrl());
  }

  /**
   * Does this connection have a next page of data?
   * 
   * @return {@code true} if there is a next page of data for this connection, {@code false} otherwise.
   */
  public boolean hasNext() {
    return !isBlank(getNextPageUrl());
  }
}