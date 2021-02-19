/*
 * Copyright (c) 2010-2021 Mark Allen, Norbert Bartels.
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

import static java.util.Collections.unmodifiableList;

import java.util.*;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.annotation.GraphAPI;
import com.restfb.json.Json;
import com.restfb.json.JsonObject;
import com.restfb.json.JsonValue;
import com.restfb.types.ads.Business;
import com.restfb.types.features.HasProfilePicture;
import com.restfb.types.instagram.IgUser;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/page/">Page Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Page extends CategorizedFacebookType implements HasProfilePicture {

  @Facebook("picture")
  private transient String rawPicture;

  /**
   * The pages's profile picture, if provided.
   * 
   * To force Facebook to fill the <code>picture</code> field you have to fetch the page with the
   * <code>fields=picture</code> parameter, otherwise the picture is <code>null</code>.
   * 
   * @return the page's profile picture as ProfilePictureSource object
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  private ProfilePictureSource picture;

  @Facebook("admin_notes")
  private List<PageAdminNote> adminNotes = new ArrayList<>();

  /**
   * Affiliation of this person. Applicable to Pages representing people
   * 
   * @since 1.10.0
   * @return affiliation of this person
   */
  @Getter
  @Setter
  @Facebook
  private String affiliation;

  /**
   * App ID for app-owned Pages and app Pages.
   *
   * @return App ID for app-owned Pages and app Pages
   */
  @Getter
  @Setter
  @Facebook("app_id")
  private String appId;

  /**
   * Artists the band likes. Applicable to Bands
   * 
   * @since 1.10.0
   * @return artists the band likes
   */
  @Getter
  @Setter
  @Facebook("artists_we_like")
  private String artistsWeLike;

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
   * Dress code of the business. Applicable to Restaurants or Nightlife. Can be one of Casual, Dressy or Unspecified
   * 
   * @since 1.10.0
   * @return dress code of the business
   */
  @Getter
  @Setter
  @Facebook
  private String attire;

  /**
   * The awards information of the film. Applicable to Films
   * 
   * @since 1.10.0
   * @return the awards information of the film
   */
  @Getter
  @Setter
  @Facebook
  private String awards;

  /**
   * Band interests. Applicable to Bands
   * 
   * @since 1.10.0
   * @return band interests
   */
  @Getter
  @Setter
  @Facebook("band_interests")
  private String bandInterests;

  /**
   * Members of the band. Applicable to Bands
   * 
   * @since 1.10.0
   * @return members of the band
   */
  @Getter
  @Setter
  @Facebook("band_members")
  private String bandMembers;

  /**
   * The best available Page on Facebook for the concept represented by this Page. The best available Page takes into
   * account authenticity and the number of likes
   * 
   * @since 1.10.0
   * @return The best available Page on Facebook for the concept represented by this Page
   */
  @Getter
  @Setter
  @Facebook("best_page")
  private Page bestPage;

  /**
   * The mailing or contact address for this page. This field will be blank if the contact address is the same as the
   * physical address
   * 
   * @since 1.10.0
   * @return The mailing or contact address for this page
   */
  @Getter
  @Setter
  @Facebook("contact_address")
  private MailingAddress contactAddress;

  /**
   * If this is a Page in a Global Pages hierarchy, the number of people who are being directed to this Page.
   *
   * @return If this is a Page in a Global Pages hierarchy, the number of people who are being directed to this Page.
   */
  @Getter
  @Setter
  @Facebook("country_page_likes")
  private Integer countryPageLikes;

  /**
   * The Page's category. e.g. Product/Service, Computers/Technology
   *
   * @return The Page's category. e.g. Product/Service, Computers/Technology
   */
  @Getter
  @Setter
  @Facebook
  private String category;

  /**
   * Biography of the band. Applicable to Bands
   * 
   * @since 1.10.0
   * @return Biography of the band
   */
  @Getter
  @Setter
  @Facebook
  private String bio;

  /**
   * Birthday of this person. Applicable to Pages representing people
   * 
   * @since 1.10.0
   * @return birthday of this person
   */
  @Getter
  @Setter
  @Facebook
  private String birthday;

  /**
   * Year vehicle was built. Applicable to Vehicles
   * 
   * @since 1.10.0
   * @return Year vehicle was built
   */
  @Getter
  @Setter
  @Facebook
  private String built;

  /**
   * The Business associated with this Page.
   */
  @Getter
  @Setter
  @Facebook
  private Business business;

  /**
   * Culinary team of the business. Applicable to Restaurants or Nightlife
   * 
   * @since 1.10.0
   * @return culinary team of the business
   */
  @Getter
  @Setter
  @Facebook("culinary_team")
  private String culinaryTeam;

  /**
   * Current location of the Page.
   * 
   * @since 1.10.0
   * @return current location of the Page
   */
  @Getter
  @Setter
  @Facebook("current_location")
  private String currentLocation;

  /**
   * Features of the vehicle. Applicable to Vehicles
   * 
   * @since 1.10.0
   * @return Features of the vehicle
   */
  @Getter
  @Setter
  @Facebook
  private String features;

  /**
   * The name of the Page with country codes appended for Global Brand Pages. Only visible to the Page admin
   * 
   * @since 1.10.0
   * @return The name of the Page with country codes appended for Global Brand Pages
   */
  @Getter
  @Setter
  @Facebook("global_brand_page_name")
  private String globalBrandPageName;

  /**
   * This brand's global Root ID.
   *
   * @return This brand's global Root ID
   */
  @Getter
  @Setter
  @Facebook("global_brand_root_id")
  private String globalBrandRootId;

  /**
   * This brand's global (parent) Page.
   * 
   * @since 1.10.0
   * @return this brand's global (parent) Page
   */
  @Getter
  @Setter
  @Facebook("global_brand_parent_page")
  private Page globalBrandParentPage;

  /**
   * Indicates whether this Page has added the app making the query in a Page tab.
   * 
   * @since 1.10.0
   * @return Indicates whether this Page has added the app making the query in a Page tab
   */
  @Getter
  @Setter
  @Facebook("has_added_app")
  private Boolean hasAddedApp;

  /**
   * has whatsapp number
   *
   * @return has whatsapp number
   */
  @Getter
  @Setter
  @Facebook("has_whatsapp_number")
  private Boolean hasWhatsappNumber;

  /**
   * Indicates whether WhatsApp number connected to this page is a WhatsApp business number
   *
   * @return Whatsapp business number
   */
  @Getter
  @Setter
  @Facebook("has_whatsapp_business_number")
  private Boolean hasWhatsappBusinessNumber;

  /**
   * Hometown of the band. Applicable to Bands
   * 
   * @since 1.10.0
   * @return Hometown of the band
   */
  @Getter
  @Setter
  @Facebook
  private String hometown;

  @Facebook("hours")
  private List<JsonObject> rawHours = new ArrayList<>();

  @Facebook("hours")
  private Map<String, String> rawHoursMap = new HashMap<>();

  /**
   * Opening hours
   *
   * @since 1.10.1
   */
  @Getter
  @Setter
  private Hours hours;

  /**
   * Legal information about the Page publishers.
   * 
   * @since 1.10.0
   * @return Legal information about the Page publishers
   */
  @Getter
  @Setter
  @Facebook
  private String impressum;

  /**
   * Influences on the band. Applicable to Bands
   * 
   * @since 1.10.0
   * @return Influences on the band
   */
  @Getter
  @Setter
  @Facebook
  private String influences;

  /**
   * Whether the business corresponding to this Page is permanently closed.
   * 
   * @since 1.10.0
   * @return Whether the business corresponding to this Page is permanently closed
   */
  @Getter
  @Setter
  @Facebook("is_permanently_closed")
  private Boolean isPermanentlyClosed;

  private List<PageLabel> labels = new ArrayList<>();

  /**
   * owner business of this object
   *
   * @return owner business of this object
   */
  @Getter
  @Setter
  @Facebook("owner_business")
  private Business ownerBusiness;

  /**
   * Overall page rating based on rating survey from users on a scale of 1-5. This value is normalized and is not
   * guaranteed to be a strict average of user ratings. If there are 0 or a small number of ratings, this field will not
   * be returned.
   */
  @Getter
  @Setter
  @Facebook("overall_star_rating")
  private Double overallStarRating;

  /**
   * Number of ratings for the page (limited to ratings that are publicly accessible
   */
  @Getter
  @Setter
  @Facebook("rating_count")
  private Integer ratingCount;

  /**
   * Messenger page scope id associated with page and a user using account_linking_token
   *
   * @return Messenger page scope id associated with page and a user using account_linking_token
   */
  @Getter
  @Setter
  @Facebook
  private String recipient;

  @Facebook("labels")
  private transient String rawLabels;

  /**
   * Personal information. Applicable to Pages representing People
   * 
   * @since 1.10.0
   * @return Personal information
   */
  @Getter
  @Setter
  @Facebook("personal_info")
  private String personalInfo;

  /**
   * Payment options accepted by the business. Applicable to Restaurants or Nightlife
   * 
   * @since 1.10.0
   * @return Payment options accepted by the business
   */
  @Getter
  @Setter
  @Facebook("payment_options")
  private PagePaymentOptions paymentOptions;
  /**
   * Personal interests. Applicable to Pages representing People
   * 
   * @since 1.10.0
   * @return Personal interests
   */
  @Getter
  @Setter
  @Facebook("personal_interests")
  private String personalInterests;

  /**
   * Pharmacy safety information. Applicable to Pharmaceutical companies
   * 
   * @since 1.10.0
   * @return Pharmacy safety information
   */
  @Getter
  @Setter
  @Facebook("pharma_safety_info")
  private String pharmaSafetyInfo;

  /**
   * For places, the category of the place.
   *
   * @return For places, the category of the place
   */
  @Getter
  @Setter
  @Facebook("place_type")
  private String placeType;

  /**
   * The plot outline of the film. Applicable to Films
   * 
   * @since 1.10.0
   * @return The plot outline of the film
   */
  @Getter
  @Setter
  @Facebook("plot_outline")
  private String plotOutline;

  /**
   * Price range of the business. Applicable to Restaurants or Nightlife. Can be one of $ (0-10), $$ (10-30), $$$
   * (30-50), $$$$ (50+) or Unspecified
   * 
   * @since 1.10.0
   * @return Price range of the business
   */
  @Getter
  @Setter
  @Facebook("price_range")
  private String priceRange;

  /**
   * The productor of the film. Applicable to Films
   * 
   * @since 1.10.0
   * @return The productor of the film
   */
  @Getter
  @Setter
  @Facebook("produced_by")
  private String producedBy;

  /**
   * Reason why a post isn't eligible for boosting. Only visible to Page Admins
   * 
   * @since 1.10.0
   * @return Reason why a post isn't eligible for boosting
   */
  @Getter
  @Setter
  @Facebook("promotion_eligible")
  private Boolean promotionEligible;

  /**
   * Reason, for which boosted posts are not eligible. Only visible to a page admin
   * 
   * @since 1.10.0
   * @return Reason, for which boosted posts are not eligible
   */
  @Getter
  @Setter
  @Facebook("promotion_ineligible_reason")
  private String promotionIneligibleReason;

  /**
   * Public transit to the business. Applicable to Restaurants or Nightlife
   * 
   * @since 1.10.0
   * @return Public transit to the business
   */
  @Getter
  @Setter
  @Facebook("public_transit")
  private String publicTransit;

  /**
   * Record label of the band. Applicable to Bands
   * 
   * @since 1.10.0
   * @return Record label of the band
   */
  @Getter
  @Setter
  @Facebook("record_label")
  private String recordLabel;

  /**
   * The film's release date. Applicable to Films
   * 
   * @since 1.10.0
   * @return The film's release date
   */
  @Getter
  @Setter
  @Facebook("release_date")
  private String releaseDate;

  /**
   * Services the restaurant provides. Applicable to Restaurants
   * 
   * @since 1.10.0
   * @return Services the restaurant provides
   */
  @Getter
  @Setter
  @Facebook("restaurant_services")
  private PageRestaurantServices restaurantServices;

  /**
   * The restaurant's specialties. Applicable to Restaurants
   * 
   * @since 1.10.0
   * @return The restaurant's specialties
   */
  @Getter
  @Setter
  @Facebook("restaurant_specialties")
  private PageRestaurantSpecialties restaurantSpecialties;

  /**
   * Unique store code for this location Page
   */
  @Getter
  @Setter
  @Facebook("store_code")
  private String storeCode;

  /**
   * Location Page's store location descriptor
   */
  @Getter
  @Setter
  @Facebook("store_location_descriptor")
  @GraphAPI(since = "2.5")
  private String storeLocationDescriptor;

  /**
   * The genre of the film. Applicable to Films
   * 
   * @since 1.10.0
   * @return The genre of the film
   */
  @Getter
  @Setter
  @Facebook
  private String genre;

  /**
   * MPG of the vehicle. Applicable to Vehicles
   * 
   * @since 1.10.0
   * @return MPG of the vehicle
   */
  @Getter
  @Setter
  @Facebook
  private String mpg;

  /**
   * The TV network for the TV show. Applicable to TV Shows
   * 
   * @since 1.10.0
   * @return The TV network for the TV show
   */
  @Getter
  @Setter
  @Facebook
  private String network;

  /**
   * The number of people who have liked the Page, since the last login. Only visible to a page admin
   * 
   * @since 1.10.0
   * @return The number of people who have liked the Page, since the last login
   */
  @Getter
  @Setter
  @Facebook("new_like_count")
  private Long newLikeCount;

  /**
   * Offer eligibility status. Only visible to a page admin
   * 
   * @since 1.10.0
   * @return Offer eligibility status
   */
  @Getter
  @Setter
  @Facebook("offer_eligible")
  private Boolean offerEligible;

  /**
   * The air schedule of the TV show. Applicable to TV Shows
   * 
   * @since 1.10.0
   * @return The air schedule of the TV show
   */
  @Getter
  @Setter
  @Facebook
  private String schedule;

  /**
   * The screenwriter of the film. Applicable to Films
   * 
   * @since 1.10.0
   * @return The screenwriter of the film
   */
  @Getter
  @Setter
  @Facebook("screenplay_by")
  private String screenplayBy;

  /**
   * The season information of the TV Show. Applicable to TV Shows
   * 
   * @since 1.10.0
   * @return The season information of the TV Show
   */
  @Getter
  @Setter
  @Facebook
  private String season;

  /**
   * The page address, if any, in a simple single line format.
   *
   * @return The page address, if any, in a simple single line format
   */
  @Getter
  @Setter
  @Facebook("single_line_address")
  private String singleLineAddress;

  /**
   * The cast of the film. Applicable to Films
   * 
   * @since 1.10.0
   * @return The cast of the film
   */
  @Getter
  @Setter
  @Facebook
  private String starring;

  /**
   * Information about when the entity represented by the Page was started
   * 
   * @since 1.10.0
   * @return Information about when the entity represented by the Page was started
   */
  @Getter
  @Setter
  @Facebook("start_info")
  private PageStartInfo startInfo;

  /**
   * Unique store number for this location Page.
   * 
   * @since 1.10.0
   * @return Unique store number for this location Page
   */
  @Getter
  @Setter
  @Facebook("store_number")
  private String storeNumber;

  /**
   * The studio for the film production. Applicable to Films
   * 
   * @since 1.10.0
   * @return The studio for the film production
   */
  @Getter
  @Setter
  @Facebook
  private String studio;

  /**
   * The social sentence and like count information for this Page. This is the same info used for the like button
   * 
   * @since 1.10.0
   * @return The social sentence and like count information for this Page
   */
  @Getter
  @Setter
  @Facebook
  private Engagement engagement;

  /**
   * Unread message count for the Page. Only visible to a page admin
   * 
   * @since 1.10.0
   * @return Unread message count for the Page
   */
  @Getter
  @Setter
  @Facebook("unread_message_count")
  private Long unreadMessageCount;

  /**
   * Number of unread notifications. Only visible to a page admin
   * 
   * @since 1.10.0
   * @return Number of unread notifications
   */
  @Getter
  @Setter
  @Facebook("unread_notif_count")
  private Long unreadNotifCount;

  /**
   * Unseen message count for the Page. Only visible to a page admin
   * 
   * @since 1.10.0
   * @return Unseen message count for the Page
   */
  @Getter
  @Setter
  @Facebook("unseen_message_count")
  private Long unseenMessageCount;

  /**
   * Members of this org. Applicable to Pages representing Team Orgs
   * 
   * @since 1.10.0
   * @return Members of this org
   */
  @Getter
  @Setter
  @Facebook
  private String members;

  /**
   * The number of visits to this Page's location. If the Page setting Show map, check-ins and star ratings on the Page
   * (under Page Settings > Page Info > Address) is disabled, then this value will also be disabled
   * 
   * @since 1.10.0
   * @return The number of visits to this Page's location
   */
  @Getter
  @Setter
  @Facebook("were_here_count")
  private Long wereHereCount;

  /**
   * Video Featured by the Page. Only visible to the Page admin
   * 
   * @since 1.10.0
   * @return Video Featured by the Page
   */
  @Getter
  @Setter
  @Facebook("featured_video")
  private Video featuredVideo;

  /**
   * Showing whether this Page is verified and in what color e.g. blue verified, gray verified or not verified
   *
   * @return Showing whether this Page is verified and in what color
   */
  @Getter
  @Setter
  @Facebook("verification_status")
  private String verificationStatus;

  /**
   * Voip info
   * 
   * @since 1.10.0
   * @return Voip info
   */
  @Getter
  @Setter
  @Facebook("voip_info")
  private VoipInfo voipInfo;

  /**
   * The writer of the TV show. Applicable to TV Shows
   * 
   * @since 1.10.0
   * @return The writer of the TV show
   */
  @Getter
  @Setter
  @Facebook("written_by")
  private String writtenBy;

  /**
   * whatsapp number
   *
   * @return whatsapp number
   */
  @Getter
  @Setter
  @Facebook("whatsapp_number")
  private String whatsappNumber;

  /**
   * The page's link.
   * 
   * @return The page's link.
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * The page's username. The alias of the Page. For example, for www.facebook.com/platform the username is 'platform'
   * 
   * @return The page's username.
   */
  @Getter
  @Setter
  @Facebook
  private String username;

  /**
   * When the page was founded.
   * 
   * @return When the page was founded.
   */
  @Getter
  @Setter
  @Facebook
  private String founded;

  /**
   * Overview of the page's company.
   * 
   * @return Overview of the page's company.
   */
  @Getter
  @Setter
  @Facebook("company_overview")
  private String companyOverview;

  /**
   * The company mission. Applicable to Companies
   * 
   * @return The page's mission.
   */
  @Getter
  @Setter
  @Facebook
  private String mission;

  /**
   * The page's products.
   * 
   * @return The page's products.
   */
  @Getter
  @Setter
  @Facebook
  private String products;

  @Getter
  @Setter
  @Facebook("page_token")
  private String pageToken;

  /**
   * Parent Page of this Page.
   *
   * If the Page is part of a Global Root Structure and you have permission to the Global Root,
   * the Global Root Parent Page is returned. If you do not have Global Root permission,
   * the Market Page for your current region is returned as the Parent Page.
   *
   * If your Page is not part of a Global Root Structure, the Parent Page is returned.
   *
   * @return parent page of this page
   */
  @Getter
  @Setter
  @Facebook("parent_page")
  private Page parentPage;

  /**
   * Indicates whether a user has accepted the TOS for running LeadGen Ads on the Page.
   * 
   * @return Indicates whether a user has accepted the TOS for running LeadGen Ads on the Page
   */
  @Getter
  @Setter
  @Facebook("leadgen_tos_accepted")
  private Boolean leadgenTosAccepted;

  /**
   * The number of likes the page has.
   *
   * Since Graph 2.6 you should use {@link Page#fanCount} instead
   *
   * @return The number of likes the page has
   * @since 1.6.5
   */
  @Getter(onMethod_ = {@GraphAPI(until = "2.5")})
  @Setter
  @Facebook("likes")
  @GraphAPI(until = "2.5")
  private Long likesCount;

  /**
   * The Pages that this Page Likes.
   *
   * @return The Pages that this Page Likes.
   */
  @Getter(onMethod_ = {@GraphAPI(since = "2.6")})
  @Setter
  @Facebook
  @GraphAPI(since = "2.6")
  private Likes likes;

  /**
   * The number of likes the page has.
   *
   * @return The number of likes the page has
   */
  @Getter(onMethod_ = {@GraphAPI(since = "2.6")})
  @Setter
  @Facebook("fan_count")
  @GraphAPI(since = "2.6")
  private Long fanCount;

  /**
   * Indicates whether this location is always open.
   *
   * @return Indicates whether this location is always open
   */
  @Getter
  @Setter
  @Facebook("is_always_open")
  private Boolean isAlwaysOpen;

  /**
   * Indicates whether location is part of a chain
   *
   * @return Indicates whether location is part of a chain
   */
  @Getter
  @Setter
  @Facebook("is_chain")
  private Boolean isChain;

  /**
   * Is this a community page?
   * 
   * @return Is this a community page?
   */
  @Getter
  @Setter
  @Facebook("is_community_page")
  private Boolean isCommunityPage;

  /**
   * Indicates whether the page is eligible for the branded content tool
   *
   * @return is eligible for the branded content tool
   */
  @Getter
  @Setter
  @Facebook("is_eligible_for_branded_content")
  private Boolean isEligibleForBrandedContent;

  /**
   * Indicates whether the page is a Messenger Platform Bot with Get Started button enabled
   *
   * @return is a Messenger Platform Bot with Get Started button enabled
   */
  @Getter
  @Setter
  @Facebook("is_messenger_bot_get_started_enabled")
  private Boolean isMessengerBotGetStartedEnabled;

  /**
   * Indicates whether the page is a Messenger Platform Bot
   *
   * @return Indicates whether the page is a Messenger Platform Bot
   */
  @Getter
  @Setter
  @Facebook("is_messenger_platform_bot")
  private Boolean isMessengerPlatformBot;

  /**
   * Indicates whether page is owned
   *
   * @return Indicates whether page is owned
   */
  @Getter
  @Setter
  @Facebook("is_owned")
  private Boolean isOwned;



  /**
   * A description of this page.
   * 
   * @return A description of this page.
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * The description of the Page in raw HTML.
   * 
   * @since 1.10.0
   * @return The description of the Page in raw HTML
   */
  @Getter
  @Setter
  @Facebook("description_html")
  private String descriptionHtml;

  /**
   * The total number of users who have checked in to the Page.
   * 
   * @return The total number of users who have checked in to the Page.
   */
  @Getter
  @Setter
  @Facebook
  private Integer checkins;

  /**
   * The phone number (not always normalized for country code) for the Page.
   * 
   * @return The phone number (not always normalized for country code) for the Page.
   */
  @Getter
  @Setter
  @Facebook
  private String phone;

  /**
   * Parking information. Applicable to Businesses and Places
   *
   * @return Parking information. Applicable to Businesses and Places
   */
  @Getter
  @Setter
  @Facebook
  private PageParking parking;

  /**
   * An admin {@code access_token} for this page.
   * <p>
   * The current user must be an administrator of this page; only returned if specifically requested via the fields URL
   * parameter, e.g. {@code facebookClient.fetchObject("123", Page.class, Parameter.with("fields","access_token"))}
   * 
   * @return The access token specific to this page.
   * @since 1.6.5
   */
  @Getter
  @Setter
  @Facebook("access_token")
  private String accessToken;

  /**
   * General information about this page.
   * 
   * @return General information about this page.
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook
  private String about;

  /**
   * The number of people that are talking about this page (last seven days).
   * 
   * @return The number of people that are talking about this page (last seven days).
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook("talking_about_count")
  private Long talkingAboutCount;

  /**
   * Whether this page has checkin functionality enabled.
   *
   * @return Whether this page has checkin functionality enabled
   */
  @Getter
  @Setter
  @Facebook("can_checkin")
  private Boolean canCheckin;

  /**
   * Indicates whether the current session user can post on this page.
   * 
   * @return Whether the current session user can post on this page.
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook("can_post")
  private Boolean canPost;

  /**
   * Indicates whether the page is published and visible to non-admins.
   * 
   * @return Whether the page is published and visible to non-admins.
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook("is_published")
  private Boolean isPublished;

  /**
   * Indicates whether the Page is unclaimed.
   * 
   * @return Indicates whether the Page is unclaimed.
   * @since 1.7.0
   */
  @Getter
  @Setter
  @Facebook("is_unclaimed")
  private Boolean isUnclaimed;

  /**
   * Indicates whether the application is subscribed for real time updates from this page
   *
   * @return Indicates whether the application is subscribed for real time updates from this page
   */
  @Getter(onMethod_ = {@GraphAPI(since = "2.7")})
  @Setter
  @Facebook("is_webhooks_subscribed")
  @GraphAPI(since = "2.7")
  private Boolean isWebhooksSubscribed;

  /**
   * The director of the film. Applicable to Films.
   * 
   * @return The director of the film
   * @since 1.7.0
   */
  @Getter
  @Setter
  @Facebook("directed_by")
  private String directedBy;

  /**
   * Subtext about the Page being viewed.
   *
   * @return Subtext about the Page being viewed
   */
  @Getter
  @Setter
  @Facebook("display_subtext")
  private String displaySubtext;

  /**
   * Page estimated message response time displayed to user
   *
   * @return Page estimated message response time displayed to user
   */
  @Getter
  @Setter
  @Facebook("displayed_message_response_time")
  private String displayedMessageResponseTime;

  /**
   * General manager of the business. Applicable to Restaurants or Nightlife.
   * 
   * @return General manager of the business.
   * @since 1.7.0
   */
  @Getter
  @Setter
  @Facebook("general_manager")
  private String generalManager;

  /**
   * The general information for a page.
   * 
   * @return The general information for a page.
   * @since 1.6.12
   */
  @Getter
  @Setter
  @Facebook("general_info")
  private String generalInfo;

  /**
   * The location of the place this page represents.
   * 
   * @return The location of the place this page represents.
   */
  @Getter
  @Setter
  @Facebook
  private Location location;

  /**
   * The instant workflow merchant id associated with the Page
   *
   * @return The instant workflow merchant id associated with the Page
   */
  @Getter
  @Setter
  @Facebook("merchant_id")
  private String merchantId;

  /**
   * The cover photo.
   * 
   * @return The cover photo.
   * @since 1.6.10
   */
  @Getter
  @Setter
  @Facebook
  private Cover cover;

  /**
   * The website URL
   * 
   * @return the website url
   * @since 1.6.15
   */
  @Getter
  @Setter
  @Facebook
  private String website;

  /**
   * The name of a Location Page that provides additional location information for that Page beside its name.
   * 
   * @return the name of Location Page
   * @since 1.7.0
   */
  @Getter
  @Setter
  @Facebook("name_with_location_descriptor")
  private String nameWithLocationDescriptor;

  /**
   * Booking agent of the band. Applicable to Bands.
   * 
   * @return Booking agent of the band
   * @since 1.7.0
   */
  @Getter
  @Setter
  @Facebook("booking_agent")
  private String bookingAgent;

  /**
   * Press contact information of the band. Applicable to Bands.
   * 
   * @return Press contact information of the band
   * @since 1.7.0
   */
  @Getter
  @Setter
  @Facebook("press_contact")
  private String pressContact;

  /**
   * Instagram account connected to page via page settings
   */
  @Getter
  @Setter
  @Facebook("connected_instagram_account")
  private IgUser connectedInstagramAccount;

  /**
   * Instagram account linked to page during Instagram business conversion flow
   */
  @Getter
  @Setter
  @Facebook("instagram_business_account")
  private IgUser instagramBusinessAccount;

  @Facebook("instagram_accounts")
  private List<InstagramUser> instagramAccounts = new ArrayList<>();

  /**
   * Indicates the current Instant Articles review status for this page
   *
   * @return Indicates the current Instant Articles review status for this page
   */
  @Getter
  @Setter
  @Facebook("instant_articles_review_status")
  private String instantArticlesReviewStatus;

  /**
   * last used time of this object by the current viewer
   *
   * @return last used time of this object by the current viewer
   */
  @Getter
  @Setter
  @Facebook("last_used_time")
  private Date lastUsedTime;

  @Facebook("category_list")
  private List<Category> categoryList = new ArrayList<>();

  @Facebook
  private List<String> emails = new ArrayList<>();

  @Facebook("food_styles")
  private List<String> foodStyles = new ArrayList<>();

  @Facebook("screennames")
  private List<ScreenName> screenNames = new ArrayList<>();

  private static final long serialVersionUID = 2L;

  public static class ScreenName extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook("service_name")
    private String serviceName;

    @Getter
    @Setter
    @Facebook
    private String value;
  }

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/page">Cover Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.10
   */
  public static class Cover extends AbstractFacebookType {

    /**
     * The ID of the photo.
     * 
     * @return The ID of the photo.
     */
    @Getter
    @Setter
    @Facebook("cover_id")
    private String coverId;

    /**
     * The URL for the cover photo.
     * 
     * @return The URL for the cover photo.
     */
    @Getter
    @Setter
    @Facebook
    private String source;

    /**
     * The percentage offset from top [0-100].
     * 
     * @return The percentage offset from top [0-100].
     */
    @Getter
    @Setter
    @Facebook("offset_y")
    private Integer offsetY;

    private static final long serialVersionUID = 1L;

  }

  public static class MailingAddress extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    /**
     * The mailing address ID.
     */
    @Getter
    @Setter
    @Facebook
    private String id;

    /**
     * Address city name.
     */
    @Getter
    @Setter
    @Facebook
    private String city;

    /**
     * Page representing the address city.
     */
    @Getter
    @Setter
    @Facebook("city_page")
    private Page cityPage;

    /**
     * Country of the address.
     */
    @Getter
    @Setter
    @Facebook
    private String country;

    /**
     * Street address.
     */
    @Getter
    @Setter
    @Facebook
    private String street1;

    /**
     * Second part of the street address - apt, suite, etc.
     */
    @Getter
    @Setter
    @Facebook
    private String street2;

    /**
     * Region or state of the address.
     */
    @Getter
    @Setter
    @Facebook
    private String region;

    /**
     * Postal code of the address.
     */
    @Getter
    @Setter
    @Facebook("postal_code")
    private String postalCode;
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/engagement/">Page Engagement Graph
   * API type</a>.
   * 
   * The social sentence and like count used to render the like plugin
   * 
   * @since 1.10.0
   */
  public static class Engagement extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    /**
     * Number of people who like this.
     * 
     * @return Number of people who like this
     */
    @Getter
    @Setter
    @Facebook
    private Long count;

    /**
     * Abbreviated string representation of count.
     * 
     * @return Abbreviated string representation of count
     */
    @Getter
    @Setter
    @Facebook("count_string")
    private String countString;

    /**
     * Abbreviated string representation of count if the viewer likes the object.
     * 
     * @return Abbreviated string representation of count if the viewer likes the object
     */
    @Getter
    @Setter
    @Facebook("count_string_with_like")
    private String countStringWithLike;

    /**
     * Abbreviated string representation of count if the viewer does not like the object.
     * 
     * @return Abbreviated string representation of count if the viewer does not like the object
     */
    @Getter
    @Setter
    @Facebook("count_string_without_like")
    private String countStringWithoutLike;

    /**
     * Text that the like button would currently display.
     * 
     * @return Text that the like button would currently display
     */
    @Getter
    @Setter
    @Facebook("social_sentence")
    private String socialSentence;

    /**
     * Text that the like button would display if the viewer likes the object.
     * 
     * @return Text that the like button would display if the viewer likes the object
     */
    @Getter
    @Setter
    @Facebook("social_sentence_with_like")
    private String socialSentenceWithLike;

    /**
     * Text that the like button would display if the viewer does not like the object.
     * 
     * @return Text that the like button would display if the viewer does not like the object
     */
    @Getter
    @Setter
    @Facebook("social_sentence_without_like")
    private String socialSentenceWithoutLike;
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/voip-info/">VOIP Info Graph API
   * type</a>.
   * 
   * @since 1.10.0
   */
  public static class VoipInfo extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook("has_permission")
    private Boolean hasPermission;

    @Getter
    @Setter
    @Facebook("has_mobile_app")
    private Boolean hasMobileApp;

    @Getter
    @Setter
    @Facebook("is_pushable")
    private Boolean isPushable;

    @Getter
    @Setter
    @Facebook("is_callable")
    private Boolean isCallable;

    @Getter
    @Setter
    @Facebook("is_callable_webrtc")
    private Boolean isCallableWebrtc;

    @Getter
    @Setter
    @Facebook("reason_code")
    private Long reasonCode;

    @Getter
    @Setter
    @Facebook("reason_description")
    private String reasonDescription;
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/page-start-info/">Page Start Info
   * Graph API type</a>.
   * 
   * @since 1.10.0
   */
  public static class PageStartInfo extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook
    private String type;

    @Getter
    @Setter
    @Facebook
    private PageStartDate date;
  }

  public static class PageStartDate extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook
    private Integer year;

    @Getter
    @Setter
    @Facebook
    private Integer month;

    @Getter
    @Setter
    @Facebook
    private Integer day;
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/page-payment-options/">Page
   * Payment Options Graph API type</a>.
   * 
   * @since 1.10.0
   */
  public static class PagePaymentOptions extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    /**
     * Whether the business accepts American Express as a payment option.
     */
    @Getter
    @Setter
    @Facebook
    private Boolean amex;

    /**
     * Whether the business accepts cash only as a payment option.
     */
    @Getter
    @Setter
    @Facebook("cash_only")
    private Boolean cashOnly;

    /**
     * Whether the business accepts Discover as a payment option.
     */
    @Getter
    @Setter
    @Facebook
    private Boolean discover;

    /**
     * Whether the business accepts MasterCard as a payment option.
     */
    @Getter
    @Setter
    @Facebook
    private Boolean mastercard;

    /**
     * Whether the business accepts Visa as a payment option.
     */
    @Getter
    @Setter
    @Facebook
    private Boolean visa;
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/page-restaurant-services/">Page
   * Restaurant Services Graph API type</a>.
   * 
   * @since 1.10.0
   */
  public static class PageRestaurantServices extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook
    private Boolean catering;

    @Getter
    @Setter
    @Facebook
    private Boolean delivery;

    @Getter
    @Setter
    @Facebook
    private Boolean groups;

    @Getter
    @Setter
    @Facebook
    private Boolean kids;

    @Getter
    @Setter
    @Facebook
    private Boolean outdoor;

    @Getter
    @Setter
    @Facebook
    private Boolean reserve;

    @Getter
    @Setter
    @Facebook
    private Boolean takeout;

    @Getter
    @Setter
    @Facebook
    private Boolean waiter;

    @Getter
    @Setter
    @Facebook
    private Boolean walkins;
  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/page-restaurant-specialties/">Page
   * Restaurant Specialties Graph API type</a>.
   * 
   * @since 1.10.0
   */
  public static class PageRestaurantSpecialties extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook
    private Boolean breakfast;

    @Getter
    @Setter
    @Facebook
    private Boolean coffee;

    @Getter
    @Setter
    @Facebook
    private Boolean dinner;

    @Getter
    @Setter
    @Facebook
    private Boolean drinks;

    @Getter
    @Setter
    @Facebook
    private Boolean lunch;
  }

  /**
   * Parking options for a Page. Useful for Facebook Pages that have a business with parking.
   */
  public static class PageParking extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    /**
     * Whether lot parking is available
     */
    @Getter
    @Setter
    @Facebook
    private Boolean lot;

    /**
     * Whether street parking is available
     */
    @Getter
    @Setter
    @Facebook
    private Boolean street;

    /**
     * Whether valet parking is available
     */
    @Getter
    @Setter
    @Facebook
    private Boolean valet;

  }

  /**
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/page-settings/">Page Settings
   * Graph API type</a>.
   * 
   * @since 1.10.0
   */
  public static class Settings extends AbstractFacebookType {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Facebook
    private String setting;

    @Getter
    @Setter
    @Facebook
    private String value;

    /**
     * convert the value field in a boolean and return it.
     * 
     * Every value that's not the String "true" (case is ignored) is <code>false</code>!
     * 
     * @return the value field as boolean.
     */
    public boolean getValueAsBoolean() {
      return Boolean.parseBoolean(value);
    }

    /**
     * Takes the value and converts it into a json object if possible.
     * 
     * May throw a JsonException
     * 
     * @return
     */
    public JsonObject getValueAsJsonObject() {
      return Json.parse(value).asObject();
    }
  }

  /**
   * External accounts. Applicable to Pages representing people
   *
   * @return the list of screen names
   */
  public List<ScreenName> getScreenNames() {
    return unmodifiableList(screenNames);
  }

  public boolean addScreenName(ScreenName screenName) {
    return screenNames.add(screenName);
  }

  public boolean removeScreenName(ScreenName screenName) {
    return screenNames.remove(screenName);
  }

  /**
   * the sub category list
   * 
   * @return the sub category list
   * @since 1.6.15
   */
  public List<Category> getCategoryList() {
    return unmodifiableList(categoryList);
  }

  public boolean addCategory(Category category) {
    return categoryList.add(category);
  }

  public boolean removeCategory(Category category) {
    return categoryList.remove(category);
  }

  /**
   * The emails listed in the About section of a Page.
   * 
   * @return The emails listed in the About section of a Page
   * @since 1.10.0
   */
  public List<String> getEmails() {
    return unmodifiableList(emails);
  }

  public boolean addEmail(String email) {
    return emails.add(email);
  }

  public boolean removeEmail(String email) {
    return emails.remove(email);
  }

  /**
   * The restaurant's food styles. Applicable to Restaurants
   * 
   * @return The restaurant's food styles
   * @since 1.10.0
   */
  public List<String> getFoodStyles() {
    return unmodifiableList(foodStyles);
  }

  public boolean addFoodStyle(String foodStyle) {
    return foodStyles.add(foodStyle);
  }

  public boolean removeFoodStyle(String foodStyle) {
    return foodStyles.remove(foodStyle);
  }

  /**
   * Notes of this page
   *
   * @return Notes of this page
   */
  @GraphAPI(since = "2.6")
  public List<PageAdminNote> getAdminNotes() {
    return unmodifiableList(adminNotes);
  }

  public boolean addAdminNote(PageAdminNote adminNote) {
    return adminNotes.add(adminNote);
  }

  public boolean removeAdminNote(PageAdminNote adminNote) {
    return adminNotes.remove(adminNote);
  }

  /**
   * Page labels of this page
   *
   * @return labels of this page
   */
  @GraphAPI(since = "2.6")
  public List<PageLabel> getLabels() {
    return unmodifiableList(labels);
  }

  public boolean addLabel(PageLabel label) {
    return labels.add(label);
  }

  public boolean removeLabels(PageLabel label) {
    return labels.remove(label);
  }

  /**
   * Linked Instagram accounts for this Page
   */
  public List<InstagramUser> getInstagramAccounts() {
    return unmodifiableList(instagramAccounts);
  }

  public boolean addInstagramAccount(InstagramUser igUser) {
    return instagramAccounts.add(igUser);
  }

  public boolean removeInstagramAccount(InstagramUser igUser) {
    return instagramAccounts.remove(igUser);
  }

  @JsonMappingCompleted
  protected void convertLabels(JsonMapper jsonMapper) {
    JsonObject rawLabels = null;
    if (this.rawLabels != null) {
      JsonValue jsonValue = Json.parse(this.rawLabels);
      if (jsonValue.isObject()) {
        rawLabels = jsonValue.asObject();
      }
    }

    if (rawLabels != null && rawLabels.isObject()) {
      String innerLabelsString = rawLabels.get("data").toString();
      labels = jsonMapper.toJavaList(innerLabelsString, PageLabel.class);
    }
  }

  @JsonMappingCompleted
  protected void fillProfilePicture(JsonMapper jsonMapper) {
    picture = convertPicture(jsonMapper,rawPicture);
  }

  @JsonMappingCompleted
  protected void graphApi26LikesFallback() {
    if (getFanCount() != null && getLikesCount() == null) {
      likesCount = fanCount;
    }

    if (getFanCount() == null && getLikesCount() != null) {
      fanCount = likesCount;
    }
  }

  @JsonMappingCompleted
  protected void convertHours() {
    if (rawHours != null && !rawHours.isEmpty()) {
      Hours hoursObj = new Hours();
      for (JsonObject entry : rawHours) {
        hoursObj.addHour(entry.getString("key", ""), entry.getString("value", ""));
      }

      hours = hoursObj;
    }
    if (rawHoursMap != null && !rawHoursMap.isEmpty()) {
      Hours hoursObj = new Hours();
      for (Map.Entry<String, String> entry : rawHoursMap.entrySet()) {
        hoursObj.addHour(entry.getKey(), entry.getValue());
      }

      hours = hoursObj;
    }
  }
}