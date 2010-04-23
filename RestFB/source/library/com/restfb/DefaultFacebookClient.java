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

import static java.net.HttpURLConnection.HTTP_OK;

import org.apache.log4j.Logger;

import com.restfb.WebRequestor.Response;

/**
 * TODO: Documentation
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class DefaultFacebookClient implements FacebookClient {
  /**
   * Handles {@code POST}s to the Facebook API endpoint.
   */
  private WebRequestor webRequestor;

  /**
   * Handles mapping Facebook response JSON to Java objects.
   */
  private JsonMapper jsonMapper;

  // API endpoint URL
  private static final String FACEBOOK_GRAPH_ENDPOINT_URL =
      "https://graph.facebook.com";

  private static final Logger logger =
      Logger.getLogger(DefaultFacebookClient.class);

  /**
   * TODO: Documentation
   */
  public DefaultFacebookClient() {
    webRequestor = new DefaultWebRequestor();
    jsonMapper = new DefaultJsonMapper();
  }

  /**
   * @see com.restfb.FacebookClient#fetch(java.lang.String, java.lang.Class,
   *      com.restfb.Parameter[])
   */
  @Override
  public <T> T fetch(String endpoint, Class<T> resultType,
      Parameter... parameters) throws FacebookException {
    if (StringUtils.isBlank(endpoint))
      throw new IllegalArgumentException(
        "The endpoint parameter must be a non-blank string.");

    endpoint = StringUtils.trimToEmpty(endpoint).toLowerCase();
    if (!endpoint.startsWith("/"))
      endpoint = "/" + endpoint;

    // TODO: more validation

    return jsonMapper.toJavaObject(makeRequest(endpoint, parameters),
      resultType);
  }

  /**
   * Coordinates the process of executing the API GET request and processing the
   * response we receive from the endpoint.
   * 
   * @param endpoint
   *          Facebook Graph API endpoint.
   * @param parameters
   *          Arbitrary number of parameters to send along to Facebook as part
   *          of the API call.
   * @return The JSON returned by Facebook for the API call.
   * @throws FacebookException
   *           If an error occurs while making the Facebook API POST or
   *           processing the response.
   */
  protected String makeRequest(String endpoint, Parameter... parameters)
      throws FacebookException {
    Response response = null;

    // TODO: support POSTs

    // Perform a GET to the API endpoint
    try {
      response =
          webRequestor.executeGet(FACEBOOK_GRAPH_ENDPOINT_URL + endpoint
              + toParameterString(parameters));
    } catch (Throwable t) {
      throw new FacebookNetworkException("Facebook GET failed", t);
    }

    if (logger.isInfoEnabled())
      logger.info("Facebook responded with " + response);

    // If we get any HTTP response code other than a 200 OK, throw an exception
    if (HTTP_OK != response.getStatusCode())
      throw new FacebookNetworkException("Facebook GET failed", response
        .getStatusCode());

    String json = response.getBody();

    // If the response contained an error code, throw an exception
    throwFacebookResponseStatusExceptionIfNecessary(json);

    return json;
  }

  /**
   * TODO: Documentation
   * 
   * @param json
   *          The JSON returned by Facebook in response to an API call.
   * @throws FacebookResponseStatusException
   *           If the JSON contains an error code.
   * @throws FacebookJsonMappingException
   *           If an error occurs while processing the JSON.
   */
  protected void throwFacebookResponseStatusExceptionIfNecessary(String json)
      throws FacebookResponseStatusException, FacebookJsonMappingException {
  // TODO: implement - no-op for now

  // This is what an error object looks like:

  /*
   * { "error": { "type": "Exception", "message":
   * "You can only access the \"home\" connection for the current user: " } }
   */

  }

  /**
   * Generate the parameter string to be included in the Facebook API request.
   * 
   * @param parameters
   *          Arbitrary number of extra parameters to include in the request.
   * @return The parameter string to include in the Facebook API request.
   */
  protected String toParameterString(Parameter... parameters) {
    StringBuilder parameterStringBuilder = new StringBuilder();
    boolean first = true;

    for (Parameter parameter : parameters) {
      if (first)
        first = false;
      else
        parameterStringBuilder.append("&");

      parameterStringBuilder.append(StringUtils.urlEncode(parameter.name));
      parameterStringBuilder.append("=");
      parameterStringBuilder.append(StringUtils.urlEncode(parameter.value));
    }

    return parameters.length == 0 ? "" : "?" + parameterStringBuilder;
  }
}