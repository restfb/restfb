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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a <a href="http://developers.facebook.com/docs/api">Graph API
 * Connection type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class Connection<T> {
  private final List<T> data;
  private final boolean hasPrevious;
  private final boolean hasNext;

  /**
   * Creates a connection with the given data and previous/next flags.
   * 
   * @param data
   *          The connection's data.
   * @param hasPrevious
   *          Is there a previous page of data?
   * @param hasNext
   *          Is there a next page of data?
   */
  Connection(List<T> data, boolean hasPrevious, boolean hasNext) {
    this.data =
        Collections.unmodifiableList(data == null ? new ArrayList<T>() : data);
    this.hasPrevious = hasPrevious;
    this.hasNext = hasNext;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("%s[data=%s hasPrevious=%s hasNext=%s]", getClass()
      .getSimpleName(), getData(), hasPrevious(), hasNext());
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    if (object == null)
      return false;
    if (!(object instanceof Connection<?>))
      return false;

    Connection<?> otherConnection = (Connection<?>) object;

    return otherConnection.hasNext() == hasNext()
        && otherConnection.hasPrevious == hasPrevious()
        && otherConnection.getData().equals(getData());
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = super.hashCode();
    hashCode += 17 * getData().hashCode();
    hashCode += 43 * Boolean.valueOf(hasPrevious()).hashCode();
    hashCode += 71 * Boolean.valueOf(hasNext()).hashCode();
    return hashCode;
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
   * Does this connection have a previous page of data?
   * 
   * @return {@code true} if there is a previous page of data for this
   *         connection, {@code false} otherwise.
   */
  public boolean hasPrevious() {
    return hasPrevious;
  }

  /**
   * Does this connection have a next page of data?
   * 
   * @return {@code true} if there is a next page of data for this connection,
   *         {@code false} otherwise.
   */
  public boolean hasNext() {
    return hasNext;
  }
}