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

import static com.restfb.util.UrlUtils.urlEncode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Base class that contains data and functionality from {@link DefaultFacebookClient}
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
abstract class BaseFacebookClient {
  /**
   * Handles {@code GET}s and {@code POST}s to the Facebook API endpoint.
   */
  protected WebRequestor webRequestor;

  /**
   * Handles mapping Facebook response JSON to Java objects.
   */
  protected JsonMapper jsonMapper;

  /**
   * Reserved method override parameter name.
   */
  protected static final String METHOD_PARAM_NAME = "method";

  /**
   * Reserved "result format" parameter name.
   */
  protected static final String FORMAT_PARAM_NAME = "format";

  /**
   * Set of parameter names that user must not specify themselves, since we use these parameters internally.
   */
  private final Set<String> illegalParamNames;

  /**
   * Reserved access token parameter name.
   */
  protected static final String ACCESS_TOKEN_PARAM_NAME = "access_token";

  /**
   * Reserved application secret proof parameter name.
   */
  protected static final String APP_SECRET_PROOF_PARAM_NAME = "appsecret_proof";

  BaseFacebookClient() {
    illegalParamNames = new HashSet<>();
    illegalParamNames.add(ACCESS_TOKEN_PARAM_NAME);
    illegalParamNames.add(METHOD_PARAM_NAME);
    illegalParamNames.add(FORMAT_PARAM_NAME);
  }

  /**
   * Appends the given {@code parameter} to the given {@code parameters} array.
   * 
   * @param parameter
   *          The parameter value to append.
   * @param parameters
   *          The parameters to which the given {@code parameter} is appended.
   * @return A new array which contains both {@code parameter} and {@code parameters}.
   */
  protected Parameter[] parametersWithAdditionalParameter(Parameter parameter, Parameter... parameters) {
    Parameter[] parameterArray = Arrays.copyOf(parameters, parameters.length+1);
    parameterArray[parameters.length] = parameter;
    return parameterArray;
  }

  /**
   * Gets the URL-encoded version of the given {@code value} for the parameter named {@code name}.
   * <p>
   * Includes special-case handling for access token parameters where we check if the token is already URL-encoded - if
   * so, we don't encode again. All other parameter types are always URL-encoded.
   * 
   * @param name
   *          The name of the parameter whose value should be URL-encoded and returned.
   * @param value
   *          The value of the parameter which should be URL-encoded and returned.
   * @return The URL-encoded version of the given {@code value}.
   */
  protected String urlEncodedValueForParameterName(String name, String value) {
    // Special handling for access_token -
    // '%7C' is the pipe character and will be present in any access_token
    // parameter that's already URL-encoded. If we see this combination, don't
    // URL-encode. Otherwise, URL-encode as normal.
    return ACCESS_TOKEN_PARAM_NAME.equals(name) && value.contains("%7C") ? value : urlEncode(value);
  }

  /**
   * Given an api call (e.g. "me" or "fql.query"), returns the correct FB API endpoint to use.
   * <p>
   * Useful for returning the read-only API endpoint where possible.
   * 
   * @param apiCall
   *          The FB API call (Graph or Old REST API) for which we'd like an endpoint.
   * @param hasAttachment
   *          Are we including a multipart file when making this API call?
   * @param hasReel
   *          Is the binary attachment used as Facebook reel?
   * @return An absolute endpoint URL to communicate with.
   * @since 1.6.3
   */
  protected abstract String createEndpointForApiCall(String apiCall, boolean hasAttachment, boolean hasReel);

  /**
   * Verifies that the provided parameter names don't collide with the ones we internally pass along to Facebook.
   *
   * @param parameters
   *          The parameters to check.
   * @throws IllegalArgumentException
   *           If there's a parameter name collision.
   */
  protected void verifyParameterLegality(Parameter... parameters) {
    Stream.of(parameters).map(parameter -> parameter.name).filter(illegalParamNames::contains).findAny()
      .ifPresent(this::throwIAE);
  }

  private void throwIAE(String name) {
    throw new IllegalArgumentException(
      "Parameter '" + name + "' is reserved for RestFB use - you cannot specify it yourself.");
  }
}