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

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Specifies how a <a href="http://developers.facebook.com/docs/api">Facebook
 * Graph API</a> client must operate.
 * <p>
 * Projects that need to access the <a
 * href="http://developers.facebook.com/docs/reference/rest/">old REST API</a>
 * should use {@link LegacyFacebookClient} instead. You might choose to do this
 * because you have a legacy codebase or you need functionality that is not yet
 * available in the Graph API.
 * <p>
 * If you'd like to...
 * 
 * <ul>
 * <li>Fetch an object: use {@link #fetchObject(String, Class, Parameter...)} or
 * {@link #fetchObjects(List, Class, Parameter...)}</li>
 * <li>Fetch a connection: use
 * {@link #fetchConnection(String, Class, Parameter...)}</li>
 * <li>Execute an FQL query: use
 * {@link #executeQuery(String, Class, Parameter...)} or
 * {@link #executeMultiquery(Map, Class, Parameter...)}</li>
 * <li>Publish data: use {@link #publish(String, Class, Parameter...)} or
 * {@link #publish(String, Class, InputStream, Parameter...)}</li>
 * <li>Delete an object: use {@link #deleteObject(String)}</li>
 * </ul>
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public interface FacebookClient {
  /**
   * Fetches a single <a
   * href="http://developers.facebook.com/docs/reference/api/">Graph API
   * object</a>.
   * 
   * @param <T>
   *          Java type to map to.
   * @param object
   *          ID of the object to fetch, e.g. {@code "me"}.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code objectType} which contains the requested
   *         object's data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters)
      throws FacebookException;

  /**
   * Fetches multiple <a
   * href="http://developers.facebook.com/docs/reference/api/">Graph API
   * objects</a> in a single call.
   * <p>
   * You'll need to write your own container type ({@code objectType}) to hold
   * the results. See <a href="http://restfb.com">http://restfb.com</a> for an
   * example of how to do this.
   * 
   * @param <T>
   *          Java type to map to.
   * @param ids
   *          IDs of the objects to fetch, e.g. {@code "me", "arjun"}.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code objectType} which contains the requested
   *         objects' data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T fetchObjects(List<String> ids, Class<T> objectType,
      Parameter... parameters) throws FacebookException;

  /**
   * Fetches a Graph API {@code Connection} type.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The name of the connection, e.g. {@code "me/feed"}.
   * @param connectionType
   *          Connection type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code connectionType} which contains the
   *         requested Connection's data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> Connection<T> fetchConnection(String connection, Class<T> connectionType,
      Parameter... parameters) throws FacebookException;

  /**
   * Executes an <a
   * href="http://developers.facebook.com/docs/reference/fql/">FQL query</a>.
   * 
   * @param <T>
   *          Java type to map to.
   * @param query
   *          The FQL query to execute, e.g. {@code
   *          "SELECT name FROM user WHERE uid=220439 or uid=7901103"}.
   * @param objectType
   *          Resultset object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return A list of instances of {@code objectType} which map to the query
   *         results.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> List<T> executeQuery(String query, Class<T> objectType,
      Parameter... parameters) throws FacebookException;

  /**
   * Executes an <a
   * href="http://developers.facebook.com/docs/reference/fql/">FQL
   * multiquery</a>, which allows you to batch multiple queries into a single
   * request.
   * <p>
   * You'll need to write your own container type ({@code objectType}) to hold
   * the results. See <a href="http://restfb.com">http://restfb.com</a> for an
   * example of how to do this.
   * 
   * @param <T>
   *          Java type to map to.
   * @param queries
   *          A mapping of query names to queries. This is marshaled to JSON and
   *          sent over the wire to the Facebook API endpoint as the {@code
   *          queries} parameter.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code objectType} which contains the requested
   *         objects' data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T executeMultiquery(Map<String, String> queries, Class<T> objectType,
      Parameter... parameters) throws FacebookException;

  /**
   * Performs a <a
   * href="http://developers.facebook.com/docs/api#publishing">Graph API
   * publish</a> operation on the given {@code connection}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The Connection to publish to.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return An instance of type {@code objectType} which contains the Facebook
   *         response to your publish request.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T publish(String connection, Class<T> objectType, Parameter... parameters)
      throws FacebookException;

  /**
   * Performs a <a
   * href="http://developers.facebook.com/docs/api#publishing">Graph API
   * publish</a> operation on the given {@code connection} and include a file -
   * a photo, for example - in the publish request.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The Connection to publish to.
   * @param objectType
   *          Object type token.
   * @param binaryAttachment
   *          The file to include in the publish request - a photo, for example.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return An instance of type {@code objectType} which contains the Facebook
   *         response to your publish request.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T publish(String connection, Class<T> objectType,
      InputStream binaryAttachment, Parameter... parameters)
      throws FacebookException;

  /**
   * Performs a <a href="http://developers.facebook.com/docs/api#deleting">Graph
   * API delete</a> operation on the given {@code object}.
   * 
   * @param object
   *          The ID of the object to delete.
   * @return {@code true} if Facebook indicated that the object was successfully
   *         deleted, {@code false} otherwise.
   * @throws FacebookException
   *           If an error occurred while attempting to delete the object.
   */
  boolean deleteObject(String object) throws FacebookException;
}