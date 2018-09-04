/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
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

import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/marketing-api/adaccountgroup/">AdGroup Campaign Group
 * type</a>.
 */
public class AdCampaignGroup extends NamedAdsObject {

  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @Facebook("account_id")
  private String accountId;

  @Getter
  @Setter
  @Facebook
  private String objective;

  @Getter
  @Setter
  @Facebook("campaign_group_status")
  private String campaignGroupStatus;

  @Getter
  @Setter
  @Facebook("is_completed")
  private String isCompleted;

  @Getter
  @Setter
  @Facebook("buying_type")
  private String buyingType;

  @Getter
  @Setter
  @Facebook("promoted_object")
  private AdPromotedObject promotedObject;

  @Getter
  @Setter
  @Facebook("spend_cap")
  private String spendCap;

  @Getter
  @Setter
  @Facebook
  private List<AdLabel> adlabels;

  @Getter
  @Setter
  @Facebook("created_time")
  private Date createdTime;

  @Getter
  @Setter
  @Facebook("start_time")
  private Date startTime;

  @Getter
  @Setter
  @Facebook("stop_time")
  private Date stopTime;

  @Getter
  @Setter
  @Facebook("updated_time")
  private Date updatedTime;

}
