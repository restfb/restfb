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

import java.util.List;

/**
 * Specifies how a Facebook API client must operate.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public interface FacebookClient {
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
  void execute(String method, Parameter... parameters) throws FacebookException;

  /**
   * Executes a Facebook API method with the given {@code parameters}, ignoring
   * the response.
   * 
   * @param method
   *          The Facebook API method to call, e.g. {@code fql.query}.
   * @param sessionKey
   *          A Facebook API session key.
   * @param parameters
   *          Parameters to include in the API call.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  void execute(String method, String sessionKey, Parameter... parameters)
      throws FacebookException;

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
  <T> T execute(String method, Class<T> resultType, Parameter... parameters)
      throws FacebookException;

  /**
   * Executes a Facebook API method with the given {@code parameters}, mapping
   * the API response to a single instance of type {@code resultType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param method
   *          The Facebook API method to call, e.g. {@code fql.query}.
   * @param sessionKey
   *          A Facebook API session key.
   * @param resultType
   *          Result type token.
   * @param parameters
   *          Parameters to include in the API call.
   * @return An instance of type {@code resultType} which contains API response
   *         data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T execute(String method, String sessionKey, Class<T> resultType,
      Parameter... parameters) throws FacebookException;

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
  <T> List<T> executeForList(String method, Class<T> resultType,
      Parameter... parameters) throws FacebookException;

  /**
   * Executes a Facebook API method with the given {@code parameters}, mapping
   * the API response to a {@code List} of instances of type {@code resultType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param method
   *          The Facebook API method to call, e.g. {@code fql.query}.
   * @param sessionKey
   *          A Facebook API session key.
   * @param resultType
   *          Result type token.
   * @param parameters
   *          Parameters to include in the API call.
   * @return A {@code List} of instances of type {@code resultType} which
   *         contain API response data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> List<T> executeForList(String method, String sessionKey,
      Class<T> resultType, Parameter... parameters) throws FacebookException;
}