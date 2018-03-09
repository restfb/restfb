/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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

/**
 * Interface for accessing the Facebook Endpoints.
 *
 * Facebook provides several endpoints that are used for working with the Graph API and working with Facebook in
 * general. This interface provides methods to access the different urls.
 *
 * The default implementation is {@link DefaultFacebookEndpoints}. That class provides the access to Facebook and should
 * be used in productive environments.
 *
 * This interface provides some fields with the default URLs so custom implementation can diretly use these or modify
 * the url or provide a completly custom one. This is possible without extending our default implementation.
 *
 * In tests, a custom implementation can be used to mock the Facebook service. The implementing class only needs to
 * provide urls to the mock service and the custom class is set in the {@link DefaultFacebookClient}.
 */
public interface FacebookEndpoints {

  /**
   * returns the Facebook URL
   * 
   * @return the Facebook URL
   */
  String getFacebookEndpoint();

  /**
   * returns the Facebook Graph API endpoint URL
   * 
   * @return the Facebook Graph API endpoint URL
   */
  String getGraphEndpoint();

  /**
   * returns the Facebook read only API endpoint URL
   * 
   * @return the Facebook read only API endpoint URL
   */
  String getReadOnlyEndpoint();

  /**
   * returns the Facebook Graph API Video endpoint URL
   * 
   * @return the Facebook Graph API Video endpoint URL
   */
  String getGraphVideoEndpoint();

  enum Endpoint {
    /**
     * General Facebook endpoint URL.
     */
    SERVER("https://www.facebook.com"),

    /**
     * Graph API endpoint URL.
     */
    GRAPH("https://graph.facebook.com"),

    /**
     * Read-only API endpoint URL.
     */
    READ_ONLY("https://api-read.facebook.com/method"),

    /**
     * Video Upload API endpoint URL.
     */
    GRAPH_VIDEO("https://graph-video.facebook.com");

    private String url;

    Endpoint(String url) {
      this.url = url;
    }

    public String getUrl() {
      return url;
    }
  }
}
