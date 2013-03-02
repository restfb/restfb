/*
 * Copyright (c) 2010-2013 Mark Allen.
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

package com.restfb.batch;

import static com.restfb.util.StringUtils.isBlank;
import static com.restfb.util.UrlUtils.urlEncode;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.Parameter;
import com.restfb.util.ReflectionUtils;

/**
 * Encapsulates a discrete part of an entire <a href="https://developers.facebook.com/docs/reference/api/batch/"
 * target="_blank">Facebook Batch API</a> request.
 * <p>
 * Must be constructed by {@link BatchRequestBuilder}.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.5
 * @see BatchRequestBuilder
 */
public class BatchRequest {
  @Facebook
  private String method;

  @Facebook("relative_url")
  private String relativeUrl;

  @Facebook
  private String body;

  @Facebook("attached_files")
  private String attachedFiles;

  @Facebook("depends_on")
  private String dependsOn;

  @Facebook
  private String name;

  @Facebook("omit_response_on_success")
  private boolean omitResponseOnSuccess;

  @Facebook
  private List<BatchHeader> headers = new ArrayList<BatchHeader>();

  /**
   * Designed to be invoked by instances of <tt>{@link BatchRequestBuilder}</tt> .
   * 
   * @param relativeUrl
   *          The endpoint to hit, for example {@code "me/friends"}.
   * @param parameters
   *          Optional list of URL parameters to be added to the value specified in {@code relativeUrl}.
   * @param method
   *          The HTTP method to use, for example {@code "GET"}.
   * @param headers
   *          The list of HTTP headers for the request.
   * @param bodyParameters
   *          The parameters that comprise the request body, for example {@code "message=Test status update"} .
   * @param attachedFiles
   *          Names of any attached files for this call, for example {@code "cat1, cat2"}.
   * @param name
   *          The logical name of this request, for example {@code "get-friends"}.
   * @param dependsOn
   *          If this call depends on the completion of another call in the current batch, for example
   *          {@code "get-friends"}.
   * @param omitResponseOnSuccess
   *          To make sure FB returns JSON in the event that this request completes successfully, set this to
   *          {@code false}.
   * @throws IllegalArgumentException
   *           If {@code relativeUrl} is {@code null} or blank.
   */
  protected BatchRequest(String relativeUrl, List<Parameter> parameters, String method, List<BatchHeader> headers,
      List<Parameter> bodyParameters, String attachedFiles, String dependsOn, String name, boolean omitResponseOnSuccess) {
    if (isBlank(relativeUrl))
      throw new IllegalArgumentException("The 'relativeUrl' parameter is required.");

    this.relativeUrl = relativeUrl;
    this.method = method;
    this.headers = headers;
    this.attachedFiles = attachedFiles;
    this.dependsOn = dependsOn;
    this.name = name;
    this.omitResponseOnSuccess = omitResponseOnSuccess;

    if (parameters.size() > 0)
      this.relativeUrl =
          format(this.relativeUrl.indexOf("?") == -1 ? "%s?%s" : "%s&%s", this.relativeUrl,
            generateParameterString(parameters));

    this.body = generateParameterString(bodyParameters);
  }

  /**
   * Builder pattern implementation used to construct instances of <tt>{@link BatchRequest}</tt>.
   * <p>
   * See the <a href="https://developers.facebook.com/docs/reference/api/batch/" target="_blank">Facebook Batch API
   * documentation</a> for more details on what a batch request looks like.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.5
   */
  public static class BatchRequestBuilder {
    private String method = "GET";
    private String relativeUrl;
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private List<BatchHeader> headers = new ArrayList<BatchHeader>();
    private List<Parameter> bodyParameters = new ArrayList<Parameter>();
    private String attachedFiles;
    private String dependsOn;
    private String name;
    private boolean omitResponseOnSuccess;

    /**
     * Creates a batch request builder using the provided FB endpoint.
     * <p>
     * You can explicitly specify URL parameters here, or use {@link #parameters(Parameter...)} instead if you prefer to
     * have the query string constructed programmatically.
     * 
     * @param relativeUrl
     *          The endpoint to hit, for example {@code "me/friends"}.
     */
    public BatchRequestBuilder(String relativeUrl) {
      this.relativeUrl = relativeUrl;
    }

    /**
     * Sets the HTTP method for the request generated by this builder, for example {@code "POST"} ({@code GET} is the
     * default value for this builder).
     * 
     * @param method
     *          The HTTP method.
     * @return This builder.
     */
    public BatchRequestBuilder method(String method) {
      this.method = method;
      return this;
    }

    /**
     * Sets the logical name for the request generated by this builder. Useful for specifying dependencies between
     * operations - the generated request can be referenced by name.
     * 
     * @param name
     *          The logical name of the request generated by this builder.
     * @return This builder.
     */
    public BatchRequestBuilder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the list of HTTP headers for the request generated by this builder.
     * 
     * @param headers
     *          The HTTP headers.
     * @return This builder.
     */
    public BatchRequestBuilder headers(BatchHeader... headers) {
      this.headers.clear();
      this.headers.addAll(asList(headers));
      return this;
    }

    /**
     * Sets the request body parameters for the request generated by this builder, for example
     * {@code Parameter.with("message", "Test status update")}.
     * 
     * @param parameters
     *          The request body parameters.
     * @return This builder.
     */
    public BatchRequestBuilder body(Parameter... parameters) {
      this.bodyParameters.clear();
      this.bodyParameters.addAll(asList(parameters));
      return this;
    }

    /**
     * Sets the comma-delimited names of any attached files for this builder, for example {@code "cat1, cat2"}.
     * 
     * @param attachedFiles
     *          The names of any attached files for this builder.
     * @return This builder.
     */
    public BatchRequestBuilder attachedFiles(String attachedFiles) {
      this.attachedFiles = attachedFiles;
      return this;
    }

    /**
     * Specifies if the request generated by this builder depends on the completion of another call in the current
     * batch, for example {@code "first"}.
     * 
     * @param dependsOn
     *          A reference to another request in the batch that this builder's request depends on.
     * @return This builder.
     */
    public BatchRequestBuilder dependsOn(String dependsOn) {
      this.dependsOn = dependsOn;
      return this;
    }

    /**
     * To make sure FB returns JSON in the event that this builder's request completes successfully, set this to
     * {@code false}.
     * 
     * @param omitResponseOnSuccess
     *          Set this to {@code false} to make sure FB returns JSON in the event that this builder's request
     *          completes successfully,
     * @return This builder.
     */
    public BatchRequestBuilder omitResponseOnSuccess(boolean omitResponseOnSuccess) {
      this.omitResponseOnSuccess = omitResponseOnSuccess;
      return this;
    }

    /**
     * Specifies URL parameters for the request generated by this builder.
     * 
     * @param parameters
     *          The URL parameters.
     * @return This builder.
     */
    public BatchRequestBuilder parameters(Parameter... parameters) {
      this.parameters.clear();
      this.parameters.addAll(asList(parameters));
      return this;
    }

    /**
     * Generates an instance of {@link BatchRequest}.
     * 
     * @return An instance of {@link BatchRequest}.
     */
    public BatchRequest build() {
      return new BatchRequest(relativeUrl, parameters, method, headers, bodyParameters, attachedFiles, dependsOn, name,
        omitResponseOnSuccess);
    }
  }

  /**
   * For a list of parameters, generate a URL query string.
   * <p>
   * Does not include a leading "?" character.
   * 
   * @param parameters
   *          The parameters to stringify.
   * @return A URL query string representation of the given {@code parameters}.
   */
  protected String generateParameterString(List<Parameter> parameters) {
    if (parameters == null)
      return "";

    StringBuilder parameterStringBuilder = new StringBuilder();
    boolean first = true;

    for (Parameter parameter : parameters) {
      if (first)
        first = false;
      else
        parameterStringBuilder.append("&");

      parameterStringBuilder.append(urlEncode(parameter.name));
      parameterStringBuilder.append("=");
      parameterStringBuilder.append(urlEncode(parameter.value));
    }

    return parameterStringBuilder.toString();
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ReflectionUtils.hashCode(this);
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object that) {
    return ReflectionUtils.equals(this, that);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ReflectionUtils.toString(this);
  }

  /**
   * The HTTP method to use, for example {@code "GET"}.
   * 
   * @return The HTTP method to use.
   */
  public String getMethod() {
    return method;
  }

  /**
   * The endpoint to hit, for example {@code "me/friends?limit=10"}.
   * 
   * @return The endpoint to hit.
   */
  public String getRelativeUrl() {
    return relativeUrl;
  }

  /**
   * The request body, for example {@code "message=Test status update"}.
   * 
   * @return The request body.
   */
  public String getBody() {
    return body;
  }

  /**
   * Names of any attached files for this call, for example {@code "cat1, cat2"} .
   * 
   * @return Names of any attached files for this call.
   */
  public String getAttachedFiles() {
    return attachedFiles;
  }

  /**
   * The logical name for this request, for example {@code "get-friends"}.
   * 
   * @return The logical name for this request.
   */
  public String getName() {
    return name;
  }

  /**
   * Another call in the current batch upon which this call depends, for example {@code "get-friends"}.
   * 
   * @return Another call in the current batch upon which this call depends.
   */
  public String getDependsOn() {
    return dependsOn;
  }

  /**
   * Will the batch response for this request be {@code null}?
   * 
   * @return {@code true} if the batch response for this request will be {@code null}, {@code false} otherwise.
   */
  public boolean isOmitResponseOnSuccess() {
    return omitResponseOnSuccess;
  }

  /**
   * HTTP Headers to be sent as part of this request.
   * 
   * @return HTTP Headers to be sent as part of this request.
   */
  public List<BatchHeader> getHeaders() {
    return unmodifiableList(headers);
  }
}