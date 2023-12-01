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
package com.restfb.types.ads;

import java.util.List;

import com.restfb.Facebook;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/reachandfrequency/">Reach Frequency Spec
 * type</a>.
 */
public class ReachFrequencySpec extends AbstractFacebookType {

  @Getter
  @Setter
  @Facebook
  private List<String> countries;

  @Facebook("min_campaign_duration")
  private String minCampaignDuration;

  @Facebook("max_campaign_duration")
  private String maxCampaignDuration;

  @Facebook("max_days_to_finish")
  private String maxDaysToFinish;

  @Facebook("min_reach_limits")
  private String minReachLimits;

  public JsonObject getMinCampaignDuration() {
    if (minCampaignDuration != null) {
      return Json.parse(minCampaignDuration).asObject();
    } else {
      return null;
    }
  }

  public void setMinCampaignDuration(JsonObject minCampaignDuration) {
    if (minCampaignDuration != null) {
      this.minCampaignDuration = minCampaignDuration.toString();
    } else {
      this.minCampaignDuration = null;
    }
  }

  public JsonObject getMaxCampaignDuration() {
    if (maxCampaignDuration != null) {
      return Json.parse(maxCampaignDuration).asObject();
    } else {
      return null;
    }
  }

  public void setMaxCampaignDuration(JsonObject maxCampaignDuration) {
    if (maxCampaignDuration != null) {
      this.maxCampaignDuration = maxCampaignDuration.toString();
    } else {
      this.maxCampaignDuration = null;
    }
  }

  public JsonObject getMaxDaysToFinish() {
    if (maxDaysToFinish != null) {
      return Json.parse(maxDaysToFinish).asObject();
    } else {
      return null;
    }
  }

  public void setMaxDaysToFinish(JsonObject maxDaysToFinish) {
    if (maxDaysToFinish != null) {
      this.maxDaysToFinish = maxDaysToFinish.toString();
    } else {
      this.maxDaysToFinish = null;
    }
  }

  public JsonObject getMinReachLimits() {
    if (minReachLimits != null) {
      return Json.parse(minReachLimits).asObject();
    } else {
      return null;
    }
  }

  public void setMinReachLimits(JsonObject minReachLimits) {
    if (minReachLimits != null) {
      this.minReachLimits = minReachLimits.toString();
    } else {
      this.minReachLimits = null;
    }
  }
}
