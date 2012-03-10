/*
 * Copyright (c) 2010-2012 Mark Allen.
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

import java.util.List;
import java.util.Map;

import com.restfb.exception.FacebookException;

/**
 * Specifies how a <a
 * href="http://developers.facebook.com/docs/reference/rest/">Legacy Facebook
 * API</a> client must operate.
 * <p>
 * Implementors must support Legacy authentication (API Key, Application Secret,
 * and Session Key) as well as the <a
 * href="http://developers.facebook.com/docs/guides/upgrade#oauth">new OAuth
 * authentication</a> method. Using OAuth authentication is much simpler and
 * strongly recommended.
 * <p>
 * Green-field projects should use the new <a
 * href="http://developers.facebook.com/docs/api">Facebook Graph API</a> via
 * {@link FacebookClient} instead.
 * <p>
 * <ul>
 * <li>The {@link #execute(String, Parameter...)} family of methods should be
 * used when performing API calls that return a single object.</li>
 * <li>The {@link #executeForList(String, Class, Parameter...)} family of
 * methods should be used when performing API calls that return a list of
 * objects.</li>
 * <li>The {@link #executeMultiquery(Map, Class, Parameter...)} family of
 * methods should be used when performing <a
 * href="http://wiki.developers.facebook.com/index.php/Fql.multiquery">
 * {@code fql.multiquery}</a> API calls.</li>
 * </ul>
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public interface LegacyFacebookClient {
  /**
   * Executes a Facebook API method with the given {@code parameters}, ignoring
   * the response.
   * 
   * @param method
   *          The Facebook API method to call, e.g. {@code fql.query}.
   * @param parameters
   *          Parameters to include in the API call.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  void execute(String method, Parameter... parameters);

  /**
   * Executes a Facebook API method with the given {@code parameters}, ignoring
   * the response.
   * 
   * @param method
   *          The Facebook API method to call, e.g. {@code fql.query}.
   * @param sessionKey
   *          A Facebook API session key if you're using the legacy API
   *          key/Secret key authentication scheme. Must be {@code null} if
   *          using OAuth access token authentication.
   * @param parameters
   *          Parameters to include in the API call.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   * @throws IllegalArgumentException
   *           If {@code sessionKey} is provided when using OAuth access token
   *           authentication.
   * @deprecated Use {@link #execute(String, Parameter...)} instead. Facebook is
   *             moving to OAuth and will stop supporting the old session key
   *             authentication scheme soon.
   */
  @Deprecated
  void execute(String method, String sessionKey, Parameter... parameters);

  /**
   * Executes a Facebook API method with the given {@code parameters}, mapping
   * the API response to a single instance of type {@code resultType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param method
   *          The Facebook API method to call, e.g. {@code fql.query}.
   * @param resultType
   *          Result type token.
   * @param parameters
   *          Parameters to include in the API call.
   * @return An instance of type {@code resultType} which contains API response
   *         data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T execute(String method, Class<T> resultType, Parameter... parameters);

  /**
   * Executes a Facebook API method with the given {@code parameters}, mapping
   * the API response to a single instance of type {@code resultType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param method
   *          The Facebook API method to call, e.g. {@code fql.query}.
   * @param sessionKey
   *          A Facebook API session key if you're using the legacy API
   *          key/Secret key authentication scheme. Must be {@code null} if
   *          using OAuth access token authentication.
   * @param resultType
   *          Result type token.
   * @param parameters
   *          Parameters to include in the API call.
   * @return An instance of type {@code resultType} which contains API response
   *         data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   * @throws IllegalArgumentException
   *           If {@code sessionKey} is provided when using OAuth access token
   *           authentication.
   * @deprecated Use {@link #execute(String, Class, Parameter...)} instead.
   *             Facebook is moving to OAuth and will stop supporting the old
   *             session key authentication scheme soon.
   */
  @Deprecated
  <T> T execute(String method, String sessionKey, Class<T> resultType, Parameter... parameters);

  /**
   * Executes a Facebook API method with the given {@code parameters}, mapping
   * the API response to a {@code List} of instances of type {@code resultType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param method
   *          The Facebook API method to call, e.g. {@code fql.query}.
   * @param resultType
   *          Result type token.
   * @param parameters
   *          Parameters to include in the API call.
   * @return A {@code List} of instances of type {@code resultType} which
   *         contain API response data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> List<T> executeForList(String method, Class<T> resultType, Parameter... parameters);

  /**
   * Executes a Facebook API method with the given {@code parameters}, mapping
   * the API response to a {@code List} of instances of type {@code resultType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param method
   *          The Facebook API method to call, e.g. {@code fql.query}.
   * @param sessionKey
   *          A Facebook API session key if you're using the legacy API
   *          key/Secret key authentication scheme. Must be {@code null} if
   *          using OAuth access token authentication.
   * @param resultType
   *          Result type token.
   * @param parameters
   *          Parameters to include in the API call.
   * @return A {@code List} of instances of type {@code resultType} which
   *         contain API response data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   * @throws IllegalArgumentException
   *           If {@code sessionKey} is provided when using OAuth access token
   *           authentication.
   * @deprecated Use {@link #executeForList(String, Class, Parameter...)}
   *             instead. Facebook is moving to OAuth and will stop supporting
   *             the old session key authentication scheme soon.
   */
  @Deprecated
  <T> List<T> executeForList(String method, String sessionKey, Class<T> resultType, Parameter... parameters);

  /**
   * Executes the <a
   * href="http://wiki.developers.facebook.com/index.php/Fql.multiquery">
   * {@code fql.multiquery}</a> API call, mapping the API response to a single
   * instance of type {@code resultType}.
   * <p>
   * This method exists because the standard
   * {@link #execute(String, Parameter...)} and
   * {@link #executeForList(String, Class, Parameter...)} family of methods are
   * not expressive enough to handle {@code fql.multiquery} in a non-verbose
   * way.
   * 
   * @param <T>
   *          Java type to map to.
   * 
   * @param queries
   *          A mapping of query names to queries. This is marshaled to JSON and
   *          sent over the wire to the Facebook API endpoint as the
   *          {@code queries} parameter.
   * @param resultType
   *          Result type token.
   * @param additionalParameters
   *          Additional parameters to include in the API call.
   * @return An instance of type {@code resultType} which contains API response
   *         data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   * @since 1.1
   */
  <T> T executeMultiquery(Map<String, String> queries, Class<T> resultType, Parameter... additionalParameters);

  /**
   * Executes the <a
   * href="http://wiki.developers.facebook.com/index.php/Fql.multiquery">
   * {@code fql.multiquery}</a> API call, mapping the API response to a single
   * instance of type {@code resultType}.
   * <p>
   * This method exists because the standard
   * {@link #execute(String, Parameter...)} and
   * {@link #executeForList(String, Class, Parameter...)} family of methods are
   * not expressive enough to handle {@code fql.multiquery} in a non-verbose
   * way.
   * 
   * @param <T>
   *          Java type to map to.
   * @param queries
   *          A mapping of query names to queries. This is marshaled to JSON and
   *          sent over the wire to the Facebook API endpoint as the
   *          {@code queries} parameter.
   * @param sessionKey
   *          A Facebook API session key if you're using the legacy API
   *          key/Secret key authentication scheme. Must be {@code null} if
   *          using OAuth access token authentication.
   * @param resultType
   *          Result type token.
   * @param additionalParameters
   *          Additional parameters to include in the API call.
   * @return An instance of type {@code resultType} which contains API response
   *         data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   * @throws IllegalArgumentException
   *           If {@code sessionKey} is provided when using OAuth access token
   *           authentication.
   * @since 1.1
   * @deprecated Use {@link #executeMultiquery(Map, Class, Parameter...)}
   *             instead. Facebook is moving to OAuth and will stop supporting
   *             the old session key authentication scheme soon.
   */
  @Deprecated
  <T> T executeMultiquery(Map<String, String> queries, String sessionKey, Class<T> resultType,
      Parameter... additionalParameters);
}