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
package com.restfb.types;

import static com.restfb.util.DateUtils.toDateFromLongFormat;

import com.restfb.Facebook;
import com.restfb.JsonMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/application" >Application Graph API
 * type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.6
 */
public class Application extends CategorizedFacebookType {

  /**
   * The app key hash for this app's Android native implementation.
   *
   * @return The app key hash for this app's Android native implementation.
   */
  @Getter
  @Setter
  @Facebook("android_key_hash")
  private String androidKeyHash;

  @Facebook("android_sdk_error_categories")
  private List<MobileSdkErrorCategory> androidSdkErrorCategories = new ArrayList<MobileSdkErrorCategory>();

  /**
   * App ad related information to help debugging.
   *
   * @return App ad related information to help debugging.
   */
  @Getter
  @Setter
  @Facebook
  private ApplicationAppAdDebugInfo appAdDebugInfo;

  @Facebook("app_domains")
  private List<String> appDomains = new ArrayList<String>();

  /**
   * Bitmask of on/off settings for various App Events related features.
   *
   * @return Bitmask of on/off settings for various App Events related features
   */
  @Getter
  @Setter
  @Facebook("app_events_feature_bitmask")
  private Long appEventsFeatureBitmask;

  /**
   * Whether the app install is trackable or not.
   *
   * @return Whether the app install is trackable or not
   */
  @Getter
  @Setter
  @Facebook("app_install_tracked")
  private Boolean appInstallTracked;

  /**
   * App name.
   *
   * @return App name
   */
  @Getter
  @Setter
  @Facebook("app_name")
  private String appName;

  /**
   * App type.
   *
   * @return App type
   */
  @Getter
  @Setter
  @Facebook("app_type")
  private Long appType;

  /**
   * The URL of a special landing page that helps people who are using an app begin publishing Open Graph activity
   *
   * @return The URL of a special landing page that helps people who are using an app begin publishing Open Graph
   *         activity
   */
  @Getter
  @Setter
  @Facebook("auth_dialog_data_help_url")
  private String authDialogDataHelpUrl;

  /**
   * One line description of an app that appears in the Login Dialog
   * 
   * @return One line description of an app that appears in the Login Dialog
   */
  @Getter
  @Setter
  @Facebook("auth_dialog_headline")
  private String authDialogHeadline;

  /**
   * The text to explain why an app needs additional permissions. This appears in the Login Dialog
   * 
   * @return The text to explain why an app needs additional permissions. This appears in the Login Dialog
   */
  @Getter
  @Setter
  @Facebook("auth_dialog_perms_explanation")
  private String authDialogPermsExplanation;

  /**
   * The default privacy setting selected for Open Graph activities in the Auth Dialog.
   *
   * @return The default privacy setting selected for Open Graph activities in the Auth Dialog
   */
  @Getter
  @Setter
  @Facebook("auth_referral_default_activity_privacy")
  private String authReferralDefaultActivityPrivacy;

  /**
   * Indicates whether Authenticated Referrals are enabled.
   *
   * @return Indicates whether Authenticated Referrals are enabled
   */
  @Getter
  @Setter
  @Facebook("auth_referral_enabled")
  private Long authReferralEnabled;

  @Facebook("auth_referral_extended_perms")
  private List<String> authReferralExtendedPerms = new ArrayList<String>();

  @Facebook("auth_referral_friend_perms")
  private List<String> authReferralFriendPerms = new ArrayList<String>();

  /**
   * The format that an app receives for the authentication token from the Login Dialog
   */
  @Getter
  @Setter
  @Facebook("auth_referral_response_type")
  private String authReferralResponseType;

  @Facebook("auth_referral_user_perms")
  private List<String> authReferralUserPerms = new ArrayList<String>();

  /**
   * Indicates whether the app uses fluid or settable height values for Canvas.
   *
   * @return Indicates whether the app uses fluid or settable height values for Canvas
   */
  @Getter
  @Setter
  @Facebook("canvas_fluid_height")
  private Boolean canvasFluidHeight;

  /**
   * Indicates whether the app uses fluid or fixed width values for Canvas.
   *
   * @return Indicates whether the app uses fluid or fixed width values for Canvas
   */
  @Getter
  @Setter
  @Facebook("canvas_fluid_width")
  private Long canvasFluidWidth;

  /**
   * The non-secure URL from which Canvas app content is loaded.
   *
   * @return The non-secure URL from which Canvas app content is loaded
   */
  @Getter
  @Setter
  @Facebook("canvas_url")
  private String canvasUrl;

  /**
   * The company the app belongs to.
   *
   * @return The company the app belongs to
   */
  @Getter
  @Setter
  @Facebook
  private String company;

  /**
   * True if the app has configured Single Sign On on iOS.
   *
   * @return True if the app has configured Single Sign On on iOS
   */
  @Getter
  @Setter
  @Facebook("configured_ios_sso")
  private Boolean configuredIosSso;

  /**
   * Email address listed for people using the app to contact developers.
   *
   * @return Email address listed for people using the app to contact developers
   */
  @Getter
  @Setter
  @Facebook("contact_email")
  private String contactEmail;

  /**
   * Social context for the app.
   *
   * @return Social context for the app
   */
  @Getter
  @Setter
  @Facebook
  private ApplicationContext context;

  /**
   * Timestamp that indicates when the app was created.
   *
   * @return Timestamp that indicates when the app was created
   */
  @Getter
  @Setter
  private Date createdTime;

  @Facebook("created_time")
  private String rawCreatedTime;

  /**
   * User ID of the creator of this app
   */
  @Getter
  @Setter
  @Facebook("creator_uid")
  private String creatorUid;

  /**
   * The number of daily active users the app has.
   *
   * @return The number of daily active users the app has
   */
  @Getter
  @Setter
  @Facebook("daily_active_users")
  private Long dailyActiveUsers;

  /**
   * Ranking of this app vs other apps comparing daily active users.
   *
   * @return Ranking of this app vs other apps comparing daily active users
   */
  @Getter
  @Setter
  @Facebook("daily_active_users_rank")
  private Long dailyActiveUsersRank;

  /**
   * URL that is pinged whenever a person removes the app.
   *
   * @return URL that is pinged whenever a person removes the app
   */
  @Getter
  @Setter
  @Facebook("deauth_callback_url")
  private String deauthCallbackUrl;

  /**
   * The platform that should be used to share content.
   *
   * @return The platform that should be used to share content
   */
  @Getter
  @Setter
  @Facebook("default_share_mode")
  private String defaultShareMode;

  /**
   * The description of the app, as provided by the developer.
   * 
   * @return The description of the app, as provided by the developer.
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * Webspace created with one of our hosting partners for this app.
   *
   * @return Webspace created with one of our hosting partners for this app
   */
  @Getter
  @Setter
  @Facebook("hosting_url")
  private String hostingUrl;

  /**
   * The URL of this app's icon.
   *
   * @return The URL of this app's icon
   */
  @Getter
  @Setter
  @Facebook("icon_url")
  private String iconUrl;

  @Facebook("ios_bundle_id")
  private List<String> iosBundleId = new ArrayList<String>();

  @Facebook("ios_sdk_error_categories")
  private List<MobileSdkErrorCategory> iosSdkErrorCategories = new ArrayList<MobileSdkErrorCategory>();

  /**
   * Whether to support the iOS integrated Login Dialog.
   * 
   * @return Whether to support the iOS integrated Login Dialog
   */
  @Getter
  @Setter
  @Facebook("ios_supports_system_auth")
  private Boolean iosSupportsSystemAuth;

  /**
   * Whether to support the native proxy login flow.
   *
   * @return Whether to support the native proxy login flow
   */
  @Getter
  @Setter
  @Facebook("ios_supports_native_proxy_auth_flow")
  private Boolean iosSupportsNativeProxyAuthFlow;

  /**
   * ID of the app in the iPad App Store.
   *
   * @return ID of the app in the iPad App Store
   */
  @Getter
  @Setter
  @Facebook("ipad_app_store_id")
  private String ipadAppStoreId;

  /**
   * ID of the app in the iPhone App Store.
   *
   * @return ID of the app in the iPhone App Store
   */
  @Setter
  @Getter
  @Facebook("iphone_app_store_id")
  private String iphoneAppStoreId;

  /**
   * A link to the app on Facebook.
   * 
   * @return A link to the app on Facebook
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * The URL of the app's logo.
   *
   * @return The URL of the app's logo
   */
  @Getter
  @Setter
  @Facebook("logo_url")
  private String logoUrl;

  /**
   * Mobile URL of the app section on a person's profile.
   *
   * @return Mobile URL of the app section on a person's profile
   */
  @Getter
  @Setter
  @Facebook("mobile_profile_section_url")
  private String mobileProfileSectionUrl;

  /**
   * URL to which Mobile users will be directed when using the app.
   *
   * @return URL to which Mobile users will be directed when using the app
   */
  @Getter
  @Setter
  @Facebook("mobile_web_url")
  private String mobileWebUrl;

  /**
   * The number of monthly active users the app has.
   *
   * @return The number of monthly active users the app has
   */
  @Getter
  @Setter
  @Facebook("monthly_active_users")
  private String monthlyActiveUsers;

  /**
   * Ranking of this app vs other apps comparing monthly active users.
   *
   * @return Ranking of this app vs other apps comparing monthly active users
   */
  @Getter
  @Setter
  @Facebook("monthly_active_users_rank")
  private Integer monthlyActiveUsersRank;

  /**
   * The string appended to <code>apps.facebook.com/</code> to navigate to the app's canvas page
   *
   * @return The string appended to <code>apps.facebook.com/</code> to navigate to the app's canvas page
   */
  @Getter
  @Setter
  @Facebook
  private String namespace;

  /**
   * Mobile store URLs for the app.
   *
   * @return Mobile store URLs for the app
   */
  @Getter
  @Setter
  @Facebook("object_store_urls")
  private ApplicationObjectStoreURLs objectStoreUrls;

  /**
   * The title of the app when used in a Page Tab.
   *
   * @return The title of the app when used in a Page Tab
   */
  @Getter
  @Setter
  @Facebook("page_tab_default_name")
  private String pageTabDefaultName;

  /**
   * The non-secure URL from which Page Tab app content is loaded.
   *
   * @return The non-secure URL from which Page Tab app content is loaded
   */
  @Getter
  @Setter
  @Facebook("page_tab_url")
  private String pageTabUrl;

  /**
   * The URL that links to a Privacy Policy for the app.
   *
   * @return The URL that links to a Privacy Policy for the app
   */
  @Getter
  @Setter
  @Facebook("privacy_policy_url")
  private String privacyPolicyUrl;

  /**
   * URL of the app section on a user's profile for the desktop site.
   *
   * @return URL of the app section on a user's profile for the desktop site
   */
  @Getter
  @Setter
  @Facebook("profile_section_url")
  private String profileSectionUrl;

  /**
   * Demographic restrictions for the app.
   *
   * @return Demographic restrictions for the app
   */
  @Getter
  @Setter
  @Facebook
  private ApplicationRestrictionInfo restrictions;

  /**
   * The secure URL from which Canvas app content is loaded.
   *
   * @return The secure URL from which Canvas app content is loaded
   */
  @Getter
  @Setter
  @Facebook("secure_canvas_url")
  private String secureCanvasUrl;

  /**
   * The secure URL from which Page Tab app content is loaded.
   *
   * @return The secure URL from which Page Tab app content is loaded
   */
  @Getter
  @Setter
  @Facebook("secure_page_tab_url")
  private String securePageTabUrl;

  /**
   * App requests must originate from this comma-separated list of IP addresses.
   *
   * @return App requests must originate from this comma-separated list of IP addresses
   */
  @Getter
  @Setter
  @Facebook("server_ip_whitelist")
  private String serverIpWhitelist;

  /**
   * Indicates whether app usage stories show up in the Ticker or News Feed.
   *
   * @return Indicates whether app usage stories show up in the Ticker or News Feed
   */
  @Getter
  @Setter
  @Facebook("social_discovery")
  private Long socialDiscovery;

  /**
   * The subcategory the app can be found under.
   *
   * @return The subcategory the app can be found under
   */
  @Getter
  @Setter
  @Facebook
  private String subcategory;

  /**
   * Indicates whether the app should do a fast-app-switch to the Facebook app to show the app requests dialog.
   *
   * @return Indicates whether the app should do a fast-app-switch to the Facebook app to show the app requests dialog
   */
  @Getter
  @Setter
  @Facebook("supports_apprequests_fast_app_switch")
  private ApplicationFastAppSwitch supportsApprequestsFastAppSwitch;

  /**
   * Indicates whether the app has not opted out of app install tracking.
   *
   * @return Indicates whether the app has not opted out of app install tracking
   */
  @Getter
  @Setter
  @Facebook("supports_attribution")
  private Boolean supportsAttribution;

  /**
   * Indicates whether the app has not opted out of the mobile SDKs sending data on SDK interactions
   *
   * @return Indicates whether the app has not opted out of the mobile SDKs sending data on SDK interactions
   */
  @Getter
  @Setter
  @Facebook("supports_implicit_sdk_logging")
  private Boolean supportsImplicitSdkLogging;

  /**
   * Whether to suppress the native iOS Login Dialog
   *
   * @return Whether to suppress the native iOS Login Dialog
   */
  @Getter
  @Setter
  @Facebook("suppress_native_ios_gdp")
  private Boolean suppressNativeIosGdp;

  /**
   * URL to Terms of Service that appears in the Login Dialog
   *
   * @return URL to Terms of Service that appears in the Login Dialog
   */
  @Getter
  @Setter
  @Facebook("terms_of_service_url")
  private String termsOfServiceUrl;

  /**
   * URL scheme suffix
   *
   * @return URL scheme suffix
   */
  @Getter
  @Setter
  @Facebook("url_scheme_suffix")
  private String urlSchemeSuffix;

  /**
   * Does the app use the legacy auth method?
   *
   * @return Does the app use the legacy auth method?
   */
  @Getter
  @Setter
  @Facebook("use_legacy_auth")
  private Boolean useLegacyAuth;

  /**
   * Main contact email for this app where people can receive support.
   *
   * @return Main contact email for this app where people can receive support
   */
  @Getter
  @Setter
  @Facebook("user_support_email")
  private String userSupportEmail;

  /**
   * URL shown in the Canvas footer that people can visit to get support for the app.
   *
   * @return URL shown in the Canvas footer that people can visit to get support for the app
   */
  @Getter
  @Setter
  @Facebook("user_support_url")
  private String userSupportUrl;

  /**
   * URL of a website that integrates with this app.
   *
   * @return URL of a website that integrates with this app
   */
  @Getter
  @Setter
  @Facebook("website_url")
  private String websiteUrl;

  /**
   * The number of weekly active users the app has.
   *
   * @return The number of weekly active users the app has
   */
  @Getter
  @Setter
  @Facebook("weekly_active_users")
  private String weeklyActiveUsers;

  /**
   * Indicates whether Login Version 4 is enabled for this app.
   *
   * @return Indicates whether Login Version 4 is enabled for this app
   */
  @Getter
  @Setter
  @Facebook("gdpv4_enabled")
  private Boolean gdpv4Enabled;

  /**
   * Indicates whether the New User Experience for login button must be shown or not.
   *
   * @return Indicates whether the New User Experience for login button must be shown or not
   */
  @Getter
  @Setter
  @Facebook("gdpv4_nux_enabled")
  private Boolean gdpv4NuxEnabled;

  /**
   * Localized content for the login new user experience.
   *
   * @return Localized content for the login new user experience
   */
  @Getter
  @Setter
  @Facebook("gdpv4_nux_content")
  private String gdpv4NuxContent;

  /**
   * last used time of this object by the current viewer
   *
   * @return last used time of this object by the current viewer
   */
  @Getter
  @Setter
  private Date lastUsedTime;

  @Facebook("last_used_time")
  private String rawLastUsedTime;

  /**
   * relevance score of an asset.
   *
   * @return relevance score of an asset
   */
  @Getter
  @Setter
  @Facebook("asset_score")
  private Double assetScore;

  /**
   * Error configuration for Android SDK.
   * 
   * @return Error configuration for Android SDK.
   */
  public List<MobileSdkErrorCategory> getAndroidSdkErrorCategories() {
    return Collections.unmodifiableList(androidSdkErrorCategories);
  }

  public boolean addAndroidSdkErrorCategory(MobileSdkErrorCategory mobileSdkErrorCategory) {
    return androidSdkErrorCategories.add(mobileSdkErrorCategory);
  }

  public boolean removeAndroidSdkErrorCategory(MobileSdkErrorCategory mobileSdkErrorCategory) {
    return androidSdkErrorCategories.remove(mobileSdkErrorCategory);
  }

  /**
   * Error configuration for iOS SDK.
   *
   * @return Error configuration for Android SDK.
   */
  public List<MobileSdkErrorCategory> getIosSdkErrorCategories() {
    return Collections.unmodifiableList(iosSdkErrorCategories);
  }

  public boolean addIosSdkErrorCategory(MobileSdkErrorCategory mobileSdkErrorCategory) {
    return iosSdkErrorCategories.add(mobileSdkErrorCategory);
  }

  public boolean removeIosSdkErrorCategory(MobileSdkErrorCategory mobileSdkErrorCategory) {
    return iosSdkErrorCategories.remove(mobileSdkErrorCategory);
  }

  /**
   * Domains and subdomains this app can use.
   * 
   * @return Domains and subdomains this app can use
   */
  public List<String> getAppDomains() {
    return Collections.unmodifiableList(appDomains);
  }

  public boolean addAppDomain(String appDomain) {
    return appDomains.add(appDomain);
  }

  public boolean removeAppDomain(String appDomain) {
    return appDomains.remove(appDomain);
  }

  /**
   * Extended permissions that a person can choose to grant when Authenticated Referrals are enabled.
   *
   * @return Extended permissions that a person can choose to grant when Authenticated Referrals are enabled
   */
  public List<String> getAuthReferralExtendedPerms() {
    return Collections.unmodifiableList(authReferralExtendedPerms);
  }

  public boolean addAuthReferralExtendedPerm(String authReferralExtendedPerm) {
    return authReferralExtendedPerms.add(authReferralExtendedPerm);
  }

  public boolean removeAuthReferralExtendedPerm(String authReferralExtendedPerm) {
    return authReferralExtendedPerms.remove(authReferralExtendedPerm);
  }

  /**
   * Basic friends permissions that a user must grant when Authenticated Referrals are enabled.
   *
   * @return Basic friends permissions that a user must grant when Authenticated Referrals are enabled
   */
  public List<String> getAuthReferralFriendPerms() {
    return Collections.unmodifiableList(authReferralFriendPerms);
  }

  public boolean addAuthReferralFriendPerm(String authReferralFriendPerm) {
    return authReferralFriendPerms.add(authReferralFriendPerm);
  }

  public boolean removeAuthReferralFriendPerm(String authReferralFriendPerm) {
    return authReferralFriendPerms.remove(authReferralFriendPerm);
  }

  /**
   * Basic user permissions that a user must grant when Authenticated Referrals are enabled.
   *
   * @return Basic user permissions that a user must grant when Authenticated Referrals are enabled
   */
  public List<String> getAuthReferralUserPerms() {
    return Collections.unmodifiableList(authReferralUserPerms);
  }

  public boolean addAuthReferralUserPerm(String authReferralUserPerm) {
    return authReferralUserPerms.add(authReferralUserPerm);
  }

  public boolean removeAuthReferralUserPerm(String authReferralUserPerm) {
    return authReferralUserPerms.remove(authReferralUserPerm);
  }

  /**
   * Bundle ID of the associated iOS app.
   *
   * @return Bundle ID of the associated iOS app
   */
  public List<String> getIosBundleId() {
    return Collections.unmodifiableList(iosBundleId);
  }

  public boolean addIosBundleId(String iosBundleId) {
    return this.iosBundleId.add(iosBundleId);
  }

  public boolean removeIosBundleId(String iosBundleId) {
    return this.iosBundleId.remove(iosBundleId);
  }

  private static final long serialVersionUID = 1L;

  @JsonMapper.JsonMappingCompleted
  void convertTime() {
    createdTime = toDateFromLongFormat(rawCreatedTime);
    lastUsedTime = toDateFromLongFormat(rawLastUsedTime);
  }

  /**
   * Represents the
   * <a href="https://developers.facebook.com/docs/graph-api/reference/mobile-sdk-error-category/" >Application Mobile
   * SDK Error Category Graph API type</a>.
   */
  public static class MobileSdkErrorCategory extends AbstractFacebookType {

    /**
     * The name for the category.
     *
     * @return The name for the category.
     */
    @Getter
    @Setter
    @Facebook
    private String name;

    /**
     * The user facing message that can be shown before attempting recovery.
     *
     * @return The user facing message that can be shown before attempting recovery.
     */
    @Getter
    @Setter
    @Facebook("recovery_message")
    private String recoveryMessage;

    /**
     * The vector of user facing labels for recovery options.
     */
    @Facebook("recovery_options")
    private List<String> recoveryOptions = new ArrayList<String>();

    /**
     * @return The vector of user facing labels for recovery options.
     */
    public List<String> getRecoveryOptions() {
      return Collections.unmodifiableList(recoveryOptions);
    }

    public boolean addRecoveryOption(String recoveryOption) {
      return recoveryOptions.add(recoveryOption);
    }

    public boolean removeRecoveryOption(String recoveryOption) {
      return recoveryOptions.remove(recoveryOption);
    }

  }

  /**
   * Represents the
   * <a href="https://developers.facebook.com/docs/graph-api/reference/application-app-ad-debug-info/" >Application App
   * Ad Debug Info Graph API type</a>.
   */
  public static class ApplicationAppAdDebugInfo extends AbstractFacebookType {

    /**
     * Timestamp of most recent install event reported by iOS SDK.
     *
     * @return Timestamp of most recent install event reported by iOS SDK
     */
    @Getter
    @Setter
    private Date lastIosInstall;

    @Facebook("last_ios_install")
    private String rawLastIosInstall;

    /**
     * Timestamp of most recent install event reported by Android SDK.
     *
     * @return Timestamp of most recent install event reported by Android SDK
     */
    @Getter
    @Setter
    private Date lastAndroidInstall;

    @Facebook("last_android_install")
    private String rawLastAndroidInstall;

    /**
     * Status of iOS app ad support for this application.
     *
     * @return Status of iOS app ad support for this application
     */
    @Getter
    @Setter
    @Facebook("ios_support_status")
    private String iosSupportStatus;

    /**
     * Status of iOS app ad support for this application.
     *
     * @return Status of iOS app ad support for this application
     */
    @Getter
    @Setter
    @Facebook("android_support_status")
    private String androidSupportStatus;

    /**
     * Number of reported iOS installs over the last seven days.
     *
     * @return Number of reported iOS installs over the last seven days
     */
    @Getter
    @Setter
    @Facebook("ios_installs_over_last_seven_days")
    private Long iosInstallsOverLastSevenDays;

    /**
     * Number of reported Android installs over the last seven days.
     *
     * @return Number of reported Android installs over the last seven days
     */
    @Getter
    @Setter
    @Facebook("android_installs_over_last_seven_days")
    private Long androidInstallsOverLastSevenDays;

    /**
     * Timestamp of most recent iOS deferred deep link request.
     *
     * @return Timestamp of most recent iOS deferred deep link request
     */
    @Getter
    @Setter
    private Date lastIosDeferredDeepLinkCall;

    @Facebook("last_ios_deferred_deep_link_call")
    private String rawLastIosDeferredDeepLinkCall;

    /**
     * Timestamp of most recent Android deferred deep link request.
     *
     * @return Timestamp of most recent Android deferred deep link request
     */
    @Getter
    @Setter
    private Date lastAndroidDeferredDeepLinkCall;

    @Facebook("last_android_deferred_deep_link_call")
    private String rawLastAndroidDeferredDeepLinkCall;

    @Facebook("ios_install_invalidation_schemes")
    private List<String> iosInstallInvalidationSchemes = new ArrayList<String>();

    /**
     * Is the application allowed to use optimized CPM bidding.
     *
     * @return Is the application allowed to use optimized CPM bidding
     */
    @Getter
    @Setter
    @Facebook("is_ocpm_enabled")
    private Boolean isOcpmEnabled;

    /**
     * Is the application allowed to use CPA bidding for iOS
     *
     * @return Is the application allowed to use CPA bidding for iOS
     */
    @Getter
    @Setter
    @Facebook("is_cpa_enabled_for_ios")
    private Boolean isCpaEnabledForIos;

    /**
     * Is the application allowed to use CPA bidding for Android.
     *
     * @return Is the application allowed to use CPA bidding for Android
     */
    @Getter
    @Setter
    @Facebook("is_cpa_enabled_for_android")
    private Boolean isCpaEnabledForAndroid;

    /**
     * Is the application in development mode.
     *
     * @return Is the application in development mode
     */
    @Getter
    @Setter
    @Facebook("is_app_in_dev_mode")
    private Boolean isAppInDevMode;

    /**
     * Is the application childred of aonther application, i.e. is it a test application
     *
     * @return Is the application childred of aonther application, i.e. is it a test application
     */
    @Getter
    @Setter
    @Facebook("is_app_child_app")
    private Boolean isAppChildApp;

    @Facebook("ios_missing_settings")
    private List<String> iosMissingSettings = new ArrayList<String>();

    @Facebook("android_missing_settings")
    private List<String> androidMissingSettings = new ArrayList<String>();

    /**
     * Settings that need to be fixed before serving app ads for iOS.
     *
     * @return Settings that need to be fixed before serving app ads for iOS
     */
    public List<String> getIosMissingSettings() {
      return Collections.unmodifiableList(iosMissingSettings);
    }

    public boolean addIosMissingSetting(String iosMissingSetting) {
      return iosMissingSettings.add(iosMissingSetting);
    }

    public boolean removeIosMissingSetting(String iosMissingSetting) {
      return iosMissingSettings.remove(iosMissingSetting);
    }

    /**
     * Settings that need to be fixed before serving app ads for Android.
     *
     * @return Settings that need to be fixed before serving app ads for Android
     */
    public List<String> getAndroidMissingSettings() {
      return Collections.unmodifiableList(androidMissingSettings);
    }

    public boolean addAndroidMissingSetting(String androidMissingSetting) {
      return androidMissingSettings.add(androidMissingSetting);
    }

    public boolean removeAndroidMissingSetting(String androidMissingSetting) {
      return androidMissingSettings.remove(androidMissingSetting);
    }

    /**
     * Schemes used for iOS install invalidation
     *
     * @return Schemes used for iOS install invalidation
     */
    public List<String> getIosInstallInvalidationSchemes() {
      return Collections.unmodifiableList(iosInstallInvalidationSchemes);
    }

    public boolean addIosInstallInvalidationScheme(String iosInstallInvalidationScheme) {
      return iosInstallInvalidationSchemes.add(iosInstallInvalidationScheme);
    }

    public boolean removeIosInstallInvalidationScheme(String iosInstallInvalidationScheme) {
      return iosInstallInvalidationSchemes.remove(iosInstallInvalidationScheme);
    }

    @JsonMapper.JsonMappingCompleted
    void convertTime() {
      lastIosInstall = toDateFromLongFormat(rawLastIosInstall);
      lastAndroidInstall = toDateFromLongFormat(rawLastAndroidInstall);
      lastIosDeferredDeepLinkCall = toDateFromLongFormat(rawLastIosDeferredDeepLinkCall);
      lastAndroidDeferredDeepLinkCall = toDateFromLongFormat(rawLastAndroidDeferredDeepLinkCall);
    }
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/application-context/" >Application
   * Context Graph API type</a>.
   */
  public static class ApplicationContext extends FacebookType {

  }

  /**
   * Represents the
   * <a href="https://developers.facebook.com/docs/graph-api/reference/application-fast-app-switch/" >Application Fast
   * App Switch Graph API type</a>.
   */
  private class ApplicationFastAppSwitch extends AbstractFacebookType {

    @Facebook("blocked_device_models")
    private List<String> blockedDeviceModels = new ArrayList<String>();

    /**
     * Is landscape orientation allowed?
     *
     * @return Is landscape orientation allowed?
     */
    @Getter
    @Setter
    @Facebook("is_landscape_allowed")
    private Boolean isLandscapeAllowed;

    /**
     * Minimum iOS version for fast app switches
     *
     * @return Minimum iOS version for fast app switches
     */
    @Getter
    @Setter
    @Facebook("min_device_ios")
    private String minDeviceIos;

    /**
     * Does the app support fast app switching?
     *
     * @return Does the app support fast app switching?
     */
    @Getter
    @Setter
    @Facebook("supports_fast_app_switch")
    private Boolean supportsFastAppWwitch;

    /**
     * Configuration version.
     *
     * @return Configuration version
     */
    @Getter
    @Setter
    @Facebook
    private Long version;

    /**
     * List of device models that may not do fast app switches.
     * 
     * @return List of device models that may not do fast app switches
     */
    public List<String> getBlockedDeviceModels() {
      return Collections.unmodifiableList(blockedDeviceModels);
    }

    public boolean addBlockedDeviceModel(String blockedDeviceModel) {
      return blockedDeviceModels.add(blockedDeviceModel);
    }

    public boolean removeBlockedDeviceModel(String blockedDeviceModel) {
      return blockedDeviceModels.remove(blockedDeviceModel);
    }
  }

  /**
   * Represents the
   * <a href="https://developers.facebook.com/docs/graph-api/reference/application-restriction-info/" >Application
   * Restriction Info Graph API type</a>.
   */
  private class ApplicationRestrictionInfo extends AbstractFacebookType {

    /**
     * Age restrictions for the app.
     *
     * @return Age restrictions for the app
     */
    @Getter
    @Setter
    @Facebook
    private String age;

    /**
     * Age restrictions by location.
     *
     * @return Age restrictions by location
     */
    @Getter
    @Setter
    @Facebook("age_distribution")
    private String ageDistribution;

    /**
     * Location restrictions for the app.
     *
     * @return Location restrictions for the app
     */
    @Getter
    @Setter
    @Facebook
    private String location;

    /**
     * Custom made category restrictions for the app, such as alcohol restriction.
     * 
     * @return Custom made category restrictions for the app, such as alcohol restriction
     */
    @Getter
    @Setter
    @Facebook
    private String type;
  }

  /**
   * Represents the
   * <a href="https://developers.facebook.com/docs/graph-api/reference/application-object-store-urls/" >Application
   * Object Store URLs Info Graph API type</a>.
   */
  private class ApplicationObjectStoreURLs extends AbstractFacebookType {

    /**
     * URL for the app in the Google Play store.
     *
     * @return URL for the app in the Google Play store
     */
    @Getter
    @Setter
    @Facebook("google_play")
    private String googlePlay;

    /**
     * URL for the app in the iTunes store.
     *
     * @return URL for the app in the iTunes store
     */
    @Getter
    @Setter
    @Facebook
    private String itunes;

    /**
     * URL for the iPad app in the iTunes store.
     *
     * @return URL for the iPad app in the iTunes store
     */
    @Getter
    @Setter
    @Facebook("itunes_ipad")
    private String itunesIpad;

    /**
     * URL for the Facebook Canvas app.
     *
     * @return URL for the Facebook Canvas app
     */
    @Getter
    @Setter
    @Facebook("fb_canvas")
    private String fbCanvas;

    /**
     * URL for the app in the Windows 10 store.
     *
     * @return URL for the app in the Windows 10 store
     */
    @Getter
    @Setter
    @Facebook("windows_10_store")
    private String windows10Store;

    /**
     * URL for the app in the Amazon App store.
     *
     * @return URL for the app in the Amazon App store
     */
    @Getter
    @Setter
    @Facebook("amazon_app_store")
    private String amazonAppStore;
  }
}