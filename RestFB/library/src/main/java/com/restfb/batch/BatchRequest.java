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

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

/**
 * TODO: document
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
   * Designed to be invoked by instances of <tt>BatchRequestBuilder</tt>.
   * 
   * @param relativeUrl
   * @param method
   * @param body
   * @param attachedFiles
   * @param dependsOn
   * @param omitResponseOnSuccess
   */
  protected BatchRequest(String relativeUrl, String method, String body, String attachedFiles, String dependsOn,
      boolean omitResponseOnSuccess) {
    this.relativeUrl = relativeUrl;
    this.method = method;
    this.body = body;
    this.attachedFiles = attachedFiles;
    this.dependsOn = dependsOn;
    this.omitResponseOnSuccess = omitResponseOnSuccess;
  }

  public static class BatchRequestBuilder {
    private String method = "GET";
    private List<BatchHeader> headers = new ArrayList<BatchHeader>();
    private String relativeUrl;
    private String body;
    private String attachedFiles;
    private String dependsOn;
    private boolean omitResponseOnSuccess;

    public BatchRequestBuilder(String relativeUrl) {
      this.relativeUrl = relativeUrl;
    }

    public BatchRequestBuilder method(String method) {
      this.method = method;
      return this;
    }

    public BatchRequestBuilder headers(List<BatchHeader> headers) {
      this.headers.clear();
      this.headers.addAll(headers);
      return this;
    }

    public BatchRequestBuilder body(String body) {
      this.body = body;
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

    public BatchRequest build() {
      return new BatchRequest(relativeUrl, method, body, attachedFiles, dependsOn, omitResponseOnSuccess);
    }
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