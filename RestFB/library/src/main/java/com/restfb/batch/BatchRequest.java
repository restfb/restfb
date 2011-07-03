/*
 * Copyright (c) 2010-2011 Mark Allen.
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
import static com.restfb.util.StringUtils.urlEncode;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.Parameter;
import com.restfb.util.ReflectionUtils;

/**
 * Encapsulates a discrete part of an entire <a
 * href="https://developers.facebook.com/docs/reference/api/batch/"
 * target="_blank">Facebook Batch API</a> request.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6.5
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

  @Facebook("omit_response_on_success")
  private boolean omitResponseOnSuccess;

  @Facebook
  private List<BatchHeader> headers = new ArrayList<BatchHeader>();

  /**
   * Designed to be invoked by instances of <tt>{@link BatchRequestBuilder}</tt>
   * .
   * 
   * @param relativeUrl
   *          The endpoint to hit, e.g. {@code "me/friends"}.
   * @param parameters
   *          Optional list of URL parameters to be added to the value specified
   *          in {@code relativeUrl}.
   * @param method
   *          The HTTP method to use, e.g. {@code "GET"}.
   * @param body
   *          The request body, e.g. {@code "message=Test status update"}.
   * @param attachedFiles
   *          Names of any attached files for this call, e.g.
   *          {@code "cat1, cat2"}.
   * @param dependsOn
   *          If this call depends on the completion of another call in the
   *          current batch, e.g. {@code "first"}.
   * @param omitResponseOnSuccess
   *          To make sure FB returns JSON in the event that this request
   *          completes successfully, set this to {@code false}.
   * @throws IllegalArgumentException
   *           If {@code relativeUrl} is {@code null} or blank.
   */
  protected BatchRequest(String relativeUrl, List<Parameter> parameters, String method, List<Parameter> bodyParameters,
      String attachedFiles, String dependsOn, boolean omitResponseOnSuccess) {
    if (isBlank(relativeUrl))
      throw new IllegalArgumentException("The 'relativeUrl' parameter is required.");

    this.relativeUrl = relativeUrl;
    this.method = method;
    this.attachedFiles = attachedFiles;
    this.dependsOn = dependsOn;
    this.omitResponseOnSuccess = omitResponseOnSuccess;

    if (parameters.size() > 0)
      this.relativeUrl =
          format(this.relativeUrl.indexOf("?") == -1 ? "%s?%s" : "%s&%s", this.relativeUrl,
            generateParameterString(parameters));

    this.body = generateParameterString(bodyParameters);
  }

  /**
   * Builder pattern implementation to easily construct instances of
   * <tt>{@link BatchRequest}</tt>.
   * <p>
   * See the <a href="https://developers.facebook.com/docs/reference/api/batch/"
   * target="_blank">Facebook Batch API documentation</a> for more details on
   * what a batch request looks like.
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
    private boolean omitResponseOnSuccess;

    /**
     * Creates a batch request builder using the provided FB endpoint.
     * <p>
     * You can explicitly specify URL parameters here, or use
     * {@link #parameters(Parameter...)} instead if you prefer to have the query
     * string constructed programmatically.
     * 
     * @param relativeUrl
     *          The endpoint to hit, e.g. {@code "me/friends"}.
     */
    public BatchRequestBuilder(String relativeUrl) {
      this.relativeUrl = relativeUrl;
    }

    /**
     * Sets The HTTP method for the builder, e.g. {@code "POST"} ({@code GET} is
     * the default value for this builder).
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
     * Sets the list of HTTP headers for this builder.
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
     * Sets the request body parameters for this builder, e.g.
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

    public BatchRequestBuilder attachedFiles(String attachedFiles) {
      this.attachedFiles = attachedFiles;
      return this;
    }

    public BatchRequestBuilder dependsOn(String dependsOn) {
      this.dependsOn = dependsOn;
      return this;
    }

    public BatchRequestBuilder omitResponseOnSuccess(boolean omitResponseOnSuccess) {
      this.omitResponseOnSuccess = omitResponseOnSuccess;
      return this;
    }

    public BatchRequestBuilder parameters(Parameter... parameters) {
      this.parameters.clear();
      this.parameters.addAll(asList(parameters));
      return this;
    }

    public BatchRequest build() {
      return new BatchRequest(relativeUrl, parameters, method, bodyParameters, attachedFiles, dependsOn,
        omitResponseOnSuccess);
    }
  }

  protected String generateParameterString(List<Parameter> parameters) {
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

  public String getMethod() {
    return method;
  }

  public String getRelativeUrl() {
    return relativeUrl;
  }

  public String getBody() {
    return body;
  }

  public String getAttachedFiles() {
    return attachedFiles;
  }

  public String getDependsOn() {
    return dependsOn;
  }

  public boolean isOmitResponseOnSuccess() {
    return omitResponseOnSuccess;
  }

  public List<BatchHeader> getHeaders() {
    return unmodifiableList(headers);
  }
}