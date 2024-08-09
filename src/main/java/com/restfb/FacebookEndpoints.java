/*
 * Copyright (c) 2010-2024 Mark Allen, Norbert Bartels.
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
 * <p>
 * Facebook provides several endpoints that are used for working with the Graph API and working with Facebook in
 * general. This interface provides methods to access the different urls.
 * <p>
 * The default implementation is {@link FacebookEndpoints}. That class provides the access to Facebook and should be
 * used in productive environments.
 * <p>
 * This interface provides some fields with the default URLs so custom implementation can directly use these or modify
 * the url or provide a completely custom one. This is possible without extending our default implementation.
 * <p>
 * In tests, a custom implementation can be used to mock the Facebook service. The implementing class only needs to
 * provide urls to the mock service and the custom class is set in the {@link DefaultFacebookClient}.
 */
public interface FacebookEndpoints {

  /**
   * returns the Facebook URL
   * 
   * @return the Facebook URL
   */
  default String getFacebookEndpoint() {
    return Endpoint.SERVER.getUrl();
  }

  /**
   * returns the Facebook Graph API endpoint URL
   * 
   * @return the Facebook Graph API endpoint URL
   */
  default String getGraphEndpoint() {
    return Endpoint.GRAPH.getUrl();
  }

  /**
   * returns the Facebook Graph API Video endpoint URL
   * 
   * @return the Facebook Graph API Video endpoint URL
   */
  default String getGraphVideoEndpoint() {
    return Endpoint.GRAPH_VIDEO.getUrl();
  }

  /**
   * returns the Facebook Reel Upload endpoint URL
   *
   * @return the Facebook Reel Upload endpoint URL
   */
  default String getReelUploadEndpoint() {
    return Endpoint.RUPLOAD.getUrl();
  }

  default String getInstagramEndpoint() {
    return Endpoint.INSTAGRAM_GRAPH.getUrl();
  }

  default String getInstagramApiEndpoint() {
    return Endpoint.INSTAGRAM_API.getUrl();
  }

  default String getInstagramOAuthEndpoint() {
    return Endpoint.INSTAGRAM_OAUTH.getUrl();
  }

  default String getThreadsBaseEndpoint() {
    return Endpoint.THREADS_OAUTH.getUrl();
  }

  default String getThreadsApiEndpoint() {
    return Endpoint.THREADS_GRAPH.getUrl();
  }

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
     * Video Upload API endpoint URL.
     */
    GRAPH_VIDEO("https://graph-video.facebook.com"),

    /**
     * Reels Upload endpont URL.
     */
    RUPLOAD("https://rupload.facebook.com/video-upload"),

    /**
     * Instagram Graph API endpoint URL.
     */
    INSTAGRAM_GRAPH("https://graph.instagram.com"),

    /**
     * Instagram API endpoint URL.
     */
    INSTAGRAM_API("https://api.instagram.com"),

    /**
     * Instagram website URL.
     */
    INSTAGRAM_OAUTH("https://www.instagram.com"),

    /**
     * Threads OAuth endpoint URL.
     */
    THREADS_OAUTH("https://www.threads.net"),

    /**
     * Threads Graph API endpoint URL.
     */
    THREADS_GRAPH("https://graph.threads.net");

    private final String url;

    Endpoint(String url) {
      this.url = url;
    }

    public String getUrl() {
      return url;
    }
  }
}
