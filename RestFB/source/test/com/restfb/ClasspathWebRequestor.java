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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class ClasspathWebRequestor implements WebRequestor {
  private String pathToJson;

  private static final Logger logger =
      Logger.getLogger(ClasspathWebRequestor.class);

  public ClasspathWebRequestor(String pathToJson) {
    this.pathToJson = pathToJson;
  }

  /**
   * @see com.restfb.WebRequestor#executePost(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public Response executePost(String url, String parameters) throws IOException {
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(ClasspathWebRequestor.class
          .getResourceAsStream(pathToJson)));
    StringBuilder json = new StringBuilder();
    String line;

    try {
      while ((line = reader.readLine()) != null)
        json.append(line);
      return new Response(200, json.toString());
    } finally {
      if (reader != null)
        try {
          reader.close();
        } catch (IOException e) {
          logger.warn("Unable to close file '" + pathToJson
              + "', continuing...");
        }
    }
  }
}