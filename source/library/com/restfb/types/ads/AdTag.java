/**
 * Copyright (c) 2010-2016 Mark Allen, Norbert Bartels.
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

import com.restfb.Facebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/adtag/">AdGroup Tag type</a>.
 * 
 * @deprecated with Marketing API version 2.4 use {@link AdLabel} instead
 */
public class AdTag extends NamedAdsObject {

  @Facebook("adgroups")
  private List<BaseAdsObject> adGroups = new ArrayList<BaseAdsObject>();

  @Facebook("adcampaigns")
  private List<BaseAdsObject> adCampaigns = new ArrayList<BaseAdsObject>();

  @Facebook("adcampaign_groups")
  private List<BaseAdsObject> adCampaignGroups = new ArrayList<BaseAdsObject>();

  public boolean addAdGroup(BaseAdsObject baseAdsObject) {
    return adGroups.add(baseAdsObject);
  }

  public boolean removeAdGroup(BaseAdsObject baseAdsObject) {
    return adGroups.remove(baseAdsObject);
  }

  public List<BaseAdsObject> getAdGroups() {
    return Collections.unmodifiableList(adGroups);
  }

  public boolean addAdCampaign(BaseAdsObject baseAdsObject) {
    return adCampaigns.add(baseAdsObject);
  }

  public boolean removeAdCampaign(BaseAdsObject baseAdsObject) {
    return adCampaigns.remove(baseAdsObject);
  }

  public List<BaseAdsObject> getAdCampaigns() {
    return Collections.unmodifiableList(adCampaigns);
  }

  public boolean addAdCampaignGroup(BaseAdsObject baseAdsObject) {
    return adCampaignGroups.add(baseAdsObject);
  }

  public boolean removeAdCampaignGroup(BaseAdsObject baseAdsObject) {
    return adCampaignGroups.remove(baseAdsObject);
  }

  public List<BaseAdsObject> getAdCampaignGroups() {
    return Collections.unmodifiableList(adCampaignGroups);
  }
}
