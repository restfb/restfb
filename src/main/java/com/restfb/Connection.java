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
package com.restfb;

import static com.restfb.util.StringUtils.isBlank;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.*;
import com.restfb.util.ReflectionUtils;

/**
 * Represents a <a href="http://developers.facebook.com/docs/api">Graph API Connection type</a>.
 *
 * @param <T>
 *          The Facebook type
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Connection<T> implements Iterable<List<T>> {
  private FacebookClient facebookClient;
  private Class<T> connectionType;
  private List<T> data;
  private String previousPageUrl;
  private String nextPageUrl;
  private Long totalCount;
  private String beforeCursor;
  private String afterCursor;

  /**
   * @see java.lang.Iterable#iterator()
   * @since 1.6.7
   */
  @Override
  public ConnectionIterator<T> iterator() {
    return new Itr<>(this);
  }

  /**
   * Iterator over connection pages.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.7
   */
  protected static class Itr<T> implements ConnectionIterator<T> {
    private Connection<T> connection;
    private boolean initialPage = true;

    /**
     * Creates a new iterator over the given {@code connection}.
     * 
     * @param connection
     *          The connection over which to iterate.
     */
    protected Itr(Connection<T> connection) {
      this.connection = connection;
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
      // Special case: initial page will always have data
      return initialPage || connection.hasNext();
    }

    /**
     * @see java.util.Iterator#next()
     */
    @Override
    public List<T> next() {
      // Special case: initial page will always have data, return it
      // immediately.
      if (initialPage) {
        initialPage = false;
        return connection.getData();
      }

      if (!connection.hasNext()) {
        throw new NoSuchElementException("There are no more pages in the connection.");
      }

      connection = connection.fetchNextPage();
      return connection.getData();
    }

    /**
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {
      throw new UnsupportedOperationException(Itr.class.getSimpleName() + " doesn't support the remove() operation.");
    }

    /**
     * @see ConnectionIterator#snapshot()
     */
    @Override
    public Connection<T> snapshot() {
      return connection;
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

    if (json == null) {
      throw new FacebookJsonMappingException("You must supply non-null connection JSON.");
    }

    JsonObject jsonObject;

    try {
      jsonObject = Json.parse(json).asObject();
    } catch (ParseException e) {
      throw new FacebookJsonMappingException("The connection JSON you provided was invalid: " + json, e);
    }

    // Pull out data
    JsonArray jsonData = jsonObject.get("data").asArray();
    List<T> dataItem = new ArrayList<>(jsonData.size());
    for (JsonValue jsonValue : jsonData) {
      dataItem.add(connectionType.equals(JsonObject.class) ? (T) jsonValue
          : facebookClient.getJsonMapper().toJavaObject(jsonValue.toString(), connectionType));
    }

    // Pull out paging info, if present
    if (jsonObject.get("paging") != null) {
      JsonObject jsonPaging = jsonObject.get("paging").asObject();
      previousPageUrl = fixProtocol(jsonPaging.getString("previous", null));
      nextPageUrl = fixProtocol(jsonPaging.getString("next", null));

      // handle cursors
      if (jsonPaging.get("cursors") != null) {
        JsonObject jsonCursors = jsonPaging.get("cursors").asObject();
        beforeCursor = jsonCursors.getString("before", null);
        afterCursor = jsonCursors.getString("after", null);
      }
    } else {
      previousPageUrl = null;
      nextPageUrl = null;
    }

    if (jsonObject.get("summary") != null) {
      JsonObject jsonSummary = jsonObject.get("summary").asObject();
      totalCount = jsonSummary.get("total_count") != null ? jsonSummary.getLong("total_count", 0L) : null;
    } else {
      totalCount = null;
    }

    this.data = unmodifiableList(dataItem);
    this.facebookClient = facebookClient;
    this.connectionType = connectionType;
  }

  /**
   * Fetches the next page of the connection. Designed to be used by {@link Itr}.
   * 
   * @return The next page of the connection.
   * @since 1.6.7
   */
  protected Connection<T> fetchNextPage() {
    return facebookClient.fetchConnectionPage(getNextPageUrl(), connectionType);
  }

  @Override
  public String toString() {
    return ReflectionUtils.toString(this);
  }

  @Override
  public boolean equals(Object object) {
    return ReflectionUtils.equals(this, object);
  }

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
   * overrides the "previous page" URL with the given value
   * 
   * @param previousPageUrl
   */
  protected void setPreviousPageUrl(String previousPageUrl) {
    this.previousPageUrl = previousPageUrl;
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
   * overrides the "next page" URL with the given value
   * 
   * @param nextPageUrl
   */
  protected void setNextPageUrl(String nextPageUrl) {
    this.nextPageUrl = nextPageUrl;
  }

  /**
   * Does this connection have a previous page of data?
   * 
   * @return {@code true} if there is a previous page of data for this connection, {@code false} otherwise.
   */
  public boolean hasPrevious() {
    return !isBlank(getPreviousPageUrl()) && !isSameCursor();
  }

  /**
   * Does this connection have a next page of data?
   * 
   * @return {@code true} if there is a next page of data for this connection, {@code false} otherwise.
   */
  public boolean hasNext() {
    return !isBlank(getNextPageUrl()) && !getData().isEmpty() && !isSameCursor();
  }

  /**
   * provides the total count of elements, if FB provides them (API >= v2.0)
   * 
   * @return the total count of elements if present
   * @since 1.6.16
   */
  public Long getTotalCount() {
    return totalCount;
  }

  public String getBeforeCursor() {
    return beforeCursor;
  }

  public String getAfterCursor() {
    return afterCursor;
  }

  private String fixProtocol(String pageUrl) {
    if (null != pageUrl && pageUrl.startsWith("http://")) {
      return pageUrl.replaceFirst("http://", "https://");
    } else {
      return pageUrl;
    }
  }

  /**
   * checks the cursors (if present) for equality
   * @return {@code true} if both cursor value are equal, {@code false} otherwise
   */
  private boolean isSameCursor() {
    return getBeforeCursor() != null && getAfterCursor() != null && getBeforeCursor().equals(getAfterCursor());
  }
}