/*
 * Copyright (c) 2010-2025 Mark Allen, Norbert Bartels.
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
package com.restfb.types.whatsapp;

import com.restfb.Facebook;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.types.AbstractFacebookType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/graph-api/reference/whats-app-business-account-to-number-current-status#fields">
 * WhatsApp Business Phone Number</a>
 */
public class WhatsAppBusinessPhoneNumber extends AbstractFacebookType {

  private static final long serialVersionUID = 1L;

  // WhatsApp business phone number ID
  @Getter
  @Setter
  @Facebook
  private String id;

  // The account mode of the phone number
  @Getter
  @Setter
  @Facebook("account_mode")
  private AccountMode accountMode;

  // The certificate of the WhatsApp business phone number
  @Getter
  @Setter
  @Facebook
  private String certificate;

  // The verification status of the phone number's OTP (One-Time Password) (Default)
  @Getter
  @Setter
  @Facebook("code_verification_status")
  private CodeVerificationStatus codeVerificationStatus;

  // Configuration for WhatsApp Business conversational automation
  @Getter
  @Setter
  @Facebook("conversational_automation")
  private WhatsAppBusinessConversationalComponent conversationAutomation;

  // WhatsApp business phone number in international format (Default)
  @Getter
  @Setter
  @Facebook("display_phone_number")
  private String displayPhoneNumber;

  // Status of eligibility in the API Business Global Search
  @Getter
  @Setter
  @Facebook("eligibility_for_api_business_global_search")
  private String eligibilityForApiBusinessGlobalSearch;

  // Health status of the WhatsApp Business number (used for messaging diagnostics)
  @Getter
  @Setter
  @Facebook("health_status")
  private WhatsAppBusinessHealthStatus healthStatus;

  // Indicates if the phone number is associated with an Official Business Account
  @Getter
  @Setter
  @Facebook("is_official_business_account")
  private Boolean isOfficialBusinessAccount;

  // Indicates if the phone number is used in the WhatsApp Business app
  @Getter
  @Setter
  @Facebook("is_on_biz_app")
  private Boolean isOnBizApp;

  // Indicates if two-step verification is enabled for this phone number
  @Getter
  @Setter
  @Facebook("is_pin_enabled")
  private Boolean isPinEnabled;

  // Indicates if the phone number is pre-verified
  @Getter
  @Setter
  @Facebook("is_preverified_number")
  private Boolean isPreverifiedNumber;

  // The date and time when the phone number was added to the WhatsApp Business Account (Default)
  @Getter
  @Setter
  @Facebook("last_onboarded_time")
  private Date lastOnboardedTime;

  // The messaging limit tier assigned to the phone number
  @Getter
  @Setter
  @Facebook("messaging_limit_tier")
  private MessagingLimitTier messagingLimitTier;

  // The status of the phone number’s display name review
  @Getter
  @Setter
  @Facebook("name_status")
  private NameStatus nameStatus;

  // The certificate of the newly requested name
  @Getter
  @Setter
  @Facebook("new_certificate")
  private String newCertificate;

  // The status of the review of the new requested name
  @Getter
  @Setter
  @Facebook("new_name_status")
  private NameStatus newNameStatus;

  // The platform where the business phone number is registered (Default)
  @Getter
  @Setter
  @Facebook("platform_type")
  private PlatformType platformType;

  // The quality rating of the WhatsApp business phone number
  @Getter
  @Setter
  @Facebook("quality_score")
  private WhatsAppPhoneQualityScoreShape qualityScore;

  // The availability of the phone number in WhatsApp Business Search
  @Getter
  @Setter
  @Facebook("search_visibility")
  private String searchVisibility;

  // The operational status of the phone number
  @Getter
  @Setter
  @Facebook
  private Status status;

  // The throughput level for Cloud API (Default)
  @Getter
  @Setter
  @Facebook
  private Throughput throughput;

  // The verified name that appears in WhatsApp Manager and client chat headers (Default)
  @Getter
  @Setter
  @Facebook("verified_name")
  private String verifiedName;

  @Facebook("quality_rating")
  private String qualityRating;

  @JsonMappingCompleted
  protected void fillQualityScore() {
    if (this.qualityScore != null || this.qualityRating == null) return;

    WhatsAppPhoneQualityScoreShape qualityScore = new WhatsAppPhoneQualityScoreShape();
    qualityScore.setScore(this.qualityRating);

    this.qualityScore = qualityScore;
  }

  public enum AccountMode {
    SANDBOX, LIVE
  }

  public enum CodeVerificationStatus {
    NOT_VERIFIED, VERIFIED, EXPIRED
  }

  public enum Status {
    PENDING, DELETED, MIGRATED, BANNED, RESTRICTED, RATE_LIMITED, FLAGGED, CONNECTED, DISCONNECTED, UNKNOWN, UNVERIFIED
  }

  public enum MessagingLimitTier {
    TIER_50, TIER_250, TIER_1K, TIER_10K, TIER_100K, TIER_UNLIMITED
  }

  public enum NameStatus {
    NONE, NON_EXISTS, PENDING_REVIEW, AVAILABLE_WITHOUT_REVIEW, EXPIRED, APPROVED, DECLINED
  }

  public enum PlatformType {
    CLOUD_API, ON_PREMISE, NOT_APPLICABLE
  }

  public static class Throughput {
    // Error code representing the specific issue
    @Getter
    @Setter
    @Facebook
    private Level level;

    public enum Level {
      STANDARD, HIGH, NOT_APPLICABLE
    }
  }
}

