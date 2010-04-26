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
 * TODO: Documentation
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public interface FacebookClient {
  <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters)
      throws FacebookException;

  <T> List<T> fetchObjects(List<String> ids, Class<T> objectType,
      Parameter... parameters) throws FacebookException;

  <T> Connection<T> fetchConnection(String connection, Class<T> connectionType,
      Parameter... parameters) throws FacebookException;

  <T> List<T> executeQuery(String query, Class<T> objectType,
      Parameter... parameters) throws FacebookException;

  <T> T executeMultiquery(MultiqueryParameter queries, Class<T> objectType,
      Parameter... parameters) throws FacebookException;

  void publish(String connection, Parameter... parameters)
      throws FacebookException;

  void deleteObject(String object) throws FacebookException;
}