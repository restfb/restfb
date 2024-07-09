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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.restfb.json.Json;
import com.restfb.json.JsonObject;
import com.restfb.util.StringUtils;

import lombok.Getter;

public class DebugHeaderInfo implements Serializable {

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

  /**
   * x-ad-account-usage
   */
  private final HeaderUsage adAccountUsage;

  private final BusinessUseCaseUsage businessUseCaseUsage;

  private DebugHeaderInfo(String debug, String rev, String traceId, Version version, String appUsage, String pageUsage,
      String adAccountUsage, String businessUsage) {
    this.debug = debug;
    this.rev = rev;
    this.traceId = traceId;
    this.usedVersion = version;
    this.appUsage = createUsage(appUsage);
    this.pageUsage = createUsage(pageUsage);
    this.adAccountUsage = createUsage(adAccountUsage);
    this.businessUseCaseUsage = createBusinessUsage(businessUsage);
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
   * @return the version, facebook used internally to fulfill the request
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

  /**
   * get Facebook response header field <code>x-ad-account-usage</code>
   * 
   * @return the Facebook response header field x-ad-account-usage
   */
  public HeaderUsage getAdAccountUsage() {
    return adAccountUsage;
  }

  public BusinessUseCaseUsage getBusinessUseCaseUsage() {
    return businessUseCaseUsage;
  }

  private BusinessUseCaseUsage createBusinessUsage(String businessUsageInformation) {
    if (StringUtils.isBlank(businessUsageInformation)) {
      return null;
    }

    try {
      DefaultJsonMapper jsonMapper = new DefaultJsonMapper();
      JsonObject jsonObject = Json.parse(businessUsageInformation).asObject();

      BusinessUseCaseUsage usage = new BusinessUseCaseUsage();
      for (String key : jsonObject.names()) {
        usage.put(key, jsonMapper.toJavaList(jsonObject.get(key).toString(), InnerBusinessUseCaseUsage.class));
      }

      return usage;
    } catch (Exception e) {
      return null;
    }
  }

  private HeaderUsage createUsage(String usageInformation) {
    if (StringUtils.isBlank(usageInformation)) {
      return null;
    }

    try {
      JsonObject jsonValue = Json.parse(usageInformation).asObject();
      if (jsonValue.names().size() == 1 && jsonValue.contains("acc_id_util_pct")) {
        return new HeaderUsage(jsonValue.getDouble("acc_id_util_pct", 0.0));
      }

      return new HeaderUsage(jsonValue.getInt("call_count", -1), jsonValue.getInt("total_time", -1),
        jsonValue.getInt("total_cputime", -1));
    } catch (Exception e) {
      return new HeaderUsage(usageInformation);
    }
  }

  /**
   * business use case based usage object.
   * <p>
   * This usage object is used in Graph API 3.3+ and provides access to the complete set of inner objects.
   */
  public static class BusinessUseCaseUsage implements Serializable {

    private Map<String, List<InnerBusinessUseCaseUsage>> usageMap = new HashMap<>();

    /**
     * add a new list of usages that belongs to the business id to the outer object
     *
     * @param key
     *          the business id the list of usages is connected with
     * @param businessUseCaseUsageList
     *          the list of usages
     */
    void put(String key, List<InnerBusinessUseCaseUsage> businessUseCaseUsageList) {
      usageMap.put(key, businessUseCaseUsageList);
    }

    /**
     * returns the usage list that belongs to the given business id
     *
     * @param businessId
     *          the business ids you like to fetch the usage list for
     * @return the list of usages
     */
    public List<InnerBusinessUseCaseUsage> get(String businessId) {
      return usageMap.get(businessId);
    }

    /**
     * returns the list of Business Ids that are provided in the the business use case usage header
     * 
     * @return list of business ids
     */
    public List<String> getBusinessIds() {
      return new ArrayList<>(usageMap.keySet());
    }

  }

  public static class InnerBusinessUseCaseUsage implements Serializable {

    /**
     * Percentage of calls made for this business id/business ad account
     */
    @Getter
    @Facebook("call_count")
    private Integer callCount;

    /**
     * Percentage of the total CPU time that has been used
     */
    @Getter
    @Facebook("total_cputime")
    private Integer totalCputime;

    /**
     * Percentage of the total time that has been used
     */
    @Getter
    @Facebook("total_time")
    private Integer totalTime;

    /**
     * Type of rate limit logic being applied
     */
    @Getter
    @Facebook
    private String type;

    /**
     * Time in minutes to resume calls
     */
    @Getter
    @Facebook("estimated_time_to_regain_access")
    private Integer estimatedTimeToRegainAccess;

  }

  public static class HeaderUsage implements Serializable {

    HeaderUsage(String percentage) {
      this.percentage = percentage;
      this.percentageOnly = true;
      this.adAccountHeader = false;
    }

    HeaderUsage(Integer callCount, Integer totalTime, Integer totalCputime) {
      this.callCount = callCount;
      this.totalTime = totalTime;
      this.totalCputime = totalCputime;
      this.percentageOnly = false;
      this.adAccountHeader = false;
    }

    HeaderUsage(Double percentage) {
      this.accIdUtilPct = percentage;
      this.percentageOnly = false;
      this.adAccountHeader = true;
    }

    @Getter
    private final boolean percentageOnly;

    @Getter
    private final boolean adAccountHeader;

    @Getter
    private String percentage;

    @Getter
    private Integer callCount;

    @Getter
    private Integer totalTime;

    @Getter
    private Integer totalCputime;

    @Getter
    private Double accIdUtilPct;

  }

  public static class DebugHeaderInfoFactory implements Serializable {

    private String debug;
    private String rev;
    private String traceId;
    private Version version;
    private String appUsage;
    private String pageUsage;
    private String adAccountUsage;
    private String businessUseCaseUsage;

    public static DebugHeaderInfoFactory create() {
      return new DebugHeaderInfoFactory();
    }

    public DebugHeaderInfoFactory setVersion(Version version) {
      this.version = version;
      return this;
    }

    public DebugHeaderInfoFactory setDebug(String debug) {
      this.debug = debug;
      return this;
    }

    public DebugHeaderInfoFactory setRev(String rev) {
      this.rev = rev;
      return this;
    }

    public DebugHeaderInfoFactory setTraceId(String traceId) {
      this.traceId = traceId;
      return this;
    }

    public DebugHeaderInfoFactory setAppUsage(String appUsage) {
      this.appUsage = appUsage;
      return this;
    }

    public DebugHeaderInfoFactory setPageUsage(String pageUsage) {
      this.pageUsage = pageUsage;
      return this;
    }

    public DebugHeaderInfoFactory setAdAccountUsage(String adAccountUsage) {
      this.adAccountUsage = adAccountUsage;
      return this;
    }

    public DebugHeaderInfoFactory setBusinessUseCaseUsage(String businessUseCaseUsage) {
      this.businessUseCaseUsage = businessUseCaseUsage;
      return this;
    }

    public DebugHeaderInfo build() {
      return new DebugHeaderInfo(this.debug, this.rev, this.traceId, this.version, this.appUsage, this.pageUsage,
        this.adAccountUsage, this.businessUseCaseUsage);
    }

  }
}
