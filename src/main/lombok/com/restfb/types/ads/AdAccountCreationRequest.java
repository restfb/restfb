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
import com.restfb.json.JsonObject;
import com.restfb.types.User;

import lombok.Getter;
import lombok.Setter;

public class AdAccountCreationRequest extends BaseAdsObject {

  @Getter
  @Setter
  @Facebook("ad_accounts_currency")
  private String adAccountsCurrency;

  @Getter
  @Setter
  @Facebook("ad_accounts_info")
  private List<JsonObject> adAccountsInfo;

  @Getter
  @Setter
  @Facebook("additional_comment")
  private String additionalComment;

  @Getter
  @Setter
  @Facebook("address_in_chinese")
  private String addressInChinese;

  @Getter
  @Setter
  @Facebook("address_in_english")
  private JsonObject addressInEnglish;

  @Getter
  @Setter
  @Facebook("address_in_local_language")
  private String addressInLocalLanguage;

  @Getter
  @Setter
  @Facebook("advertiser_business")
  private Business advertiserBusiness;
  @Getter
  @Setter
  @Facebook("appeal_reason")
  private JsonObject appealReason;

  @Getter
  @Setter
  @Facebook
  private Business business;

  @Getter
  @Setter
  @Facebook("business_registration_id")
  private String businessRegistrationId;

  @Getter
  @Setter
  @Facebook("chinese_legal_entity_name")
  private String chineseLegalEntityName;

  @Getter
  @Setter
  @Facebook
  private JsonObject contact;

  @Getter
  @Setter
  @Facebook
  private User creator;

  @Getter
  @Setter
  @Facebook("credit_card_id")
  private String creditCardId;

  @Getter
  @Setter
  @Facebook("disapproval_reasons")
  private List<JsonObject> mDisapprovalReasons;

  @Getter
  @Setter
  @Facebook("english_legal_entity_name")
  private String englishLegalEntityName;

  @Getter
  @Setter
  @Facebook("extended_credit_id")
  private String extendedCreditId;

  @Getter
  @Setter
  @Facebook("is_smb")
  private Boolean isSmb;

  @Getter
  @Setter
  @Facebook("is_test")
  private Boolean isTest;

  @Getter
  @Setter
  @Facebook("is_under_authorization")
  private Boolean isUnderAuthorization;

  @Getter
  @Setter
  @Facebook("legal_entity_name_in_local_language")
  private String legalEntityNameInLocalLanguage;

  @Getter
  @Setter
  @Facebook("oe_request_id")
  private String oeRequestId;

  @Getter
  @Setter
  @Facebook("official_website_url")
  private String officialWebsiteUrl;

  @Getter
  @Setter
  @Facebook("planning_agency_business")
  private Business planningAgencyBusiness;

  @Getter
  @Setter
  @Facebook("planning_agency_business_id")
  private String planningAgencyBusinessId;

  @Getter
  @Setter
  @Facebook("promotable_app_ids")
  private List<String> promotableAppIds;

  @Getter
  @Setter
  @Facebook("promotable_page_ids")
  private List<String> promotablePageIds;

  @Getter
  @Setter
  @Facebook("promotable_urls")
  private List<String> promotableUrls;

  @Getter
  @Setter
  @Facebook("request_change_reasons")
  private List<JsonObject> requestChangeReasons;

  @Getter
  @Setter
  @Facebook
  private String status;

  @Getter
  @Setter
  @Facebook
  private String subvertical;

  @Getter
  @Setter
  @Facebook("time_created")
  private String timeCreated;

  @Getter
  @Setter
  @Facebook
  private String vertical;
}
