/**
 * Copyright (c) 2010-2017 Mark Allen, Norbert Bartels.
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

import com.restfb.json.JsonObject;
import com.restfb.util.StringUtils;

import lombok.Getter;

public class DebugHeaderInfo {

  /**
   * x-fb-debug
   */
  private final String debug;

  /**
   * x-fb-rev
   */
  private final String rev;

  /**
   * x-fb-trace-id
   */
  private final String traceId;

  /**
   * the use version
   */
  private final Version usedVersion;

  /**
   * x-app-usage
   */
  private final HeaderUsage appUsage;

  /**
   * x-page-usage
   */
  private final HeaderUsage pageUsage;

  public DebugHeaderInfo(String debug, String rev, String traceId, Version version, String appUsage, String pageUsage) {
    this.debug = debug;
    this.rev = rev;
    this.traceId = traceId;
    this.usedVersion = version;
    this.appUsage = createUsage(appUsage);
    this.pageUsage = createUsage(pageUsage);
  }

  /**
   * get Facebook response header field <code>x-fb-debug</code>
   * 
   * @return the Facebook response header field <code>x-fb-debug</code>
   */
  public String getDebug() {
    return debug;
  }

  /**
   * get Facebook response header field <code>x-fb-rev</code>
   * 
   * @return the Facebook response header field <code>x-fb-rev</code>
   */
  public String getRev() {
    return rev;
  }

  /**
   * get Facebook response header field <code>x-fb-trace-id</code>
   * 
   * @return the Facebook response header field x-fb-trace-id
   */
  public String getTraceId() {
    return traceId;
  }

  /**
   * get the version, facebook used internally to fulfill the request
   * 
   * @return
   */
  public Version getUsedVersion() {
    return usedVersion;
  }

  /**
   * get Facebook response header field <code>x-app-usage</code>
   *
   * @return the Facebook response header field x-app-usage
   */
  public HeaderUsage getAppUsage() {
    return appUsage;
  }

  /**
   * get Facebook response header field <code>x-page-usage</code>
   *
   * @return the Facebook response header field x-page-usage
   */
  public HeaderUsage getPageUsage() {
    return pageUsage;
  }

  private HeaderUsage createUsage(String usageInformation) {
    if (StringUtils.isBlank(usageInformation)) {
      return null;
    }

    try {
      JsonObject jsonValue = new JsonObject(usageInformation);
      return new HeaderUsage(jsonValue.getInt("call_count"), jsonValue.getInt("total_time"),
        jsonValue.getInt("total_cputime"));
    } catch (Exception e) {
      return new HeaderUsage(usageInformation);
    }
  }

  public static class HeaderUsage {

    HeaderUsage(String percentage) {
      this.percentage = percentage;
      this.percentageOnly = true;
    }

    HeaderUsage(Integer callCount, Integer totalTime, Integer totalCputime) {
      this.callCount = callCount;
      this.totalTime = totalTime;
      this.totalCputime = totalCputime;
      this.percentageOnly = false;
    }

    @Getter
    private final boolean percentageOnly;

    @Getter
    private String percentage;

    @Getter
    private Integer callCount;

    @Getter
    private Integer totalTime;

    @Getter
    private Integer totalCputime;

  }
}
