/*
 * Copyright (c) 2010-2015 Mark Allen.
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

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="http://developers.facebook.com/docs/reference/api/page">Page Graph API type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Page extends CategorizedFacebookType {

  /**
   * The page's picture.
   * 
   * @return The page's picture.
   */
  @Getter
  @Setter
  @Facebook
  private String picture;

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
   * 
   * @since 1.10.0
   * @return the Business associated with this Page
   */
  @Getter
  @Setter
  @Facebook
  private String business;

  /**
   * The Page's category. e.g. Product/Service, Computers/Technology
   * 
   * @since 1.10.0
   * @return the Page's category
   */
  @Getter
  @Setter
  @Facebook
  private String category;

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
   * Hometown of the band. Applicable to Bands
   * 
   * @since 1.10.0
   * @return Hometown of the band
   */
  @Getter
  @Setter
  @Facebook
  private String hometown;

  /**
   * Opening hours
   *
   * @since 1.10.1
   */
  @Getter
  @Setter
  @Facebook
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

  /**
   * The number of likes the page has.
   * 
   * @return The number of likes the page has.
   * @since 1.6.5
   */
  @Getter
  @Setter
  @Facebook
  private Long likes;

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
   * Pages with a large number of followers can be manually verified by Facebook as having an authentic identity. This
   * field indicates whether the page is verified by this process
   * 
   * @since 1.10.0
   * @return Indicates whether the Page is verified
   */
  @Getter
  @Setter
  @Facebook("is_verified")
  private Boolean isVerified;

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

  @Facebook("category_list")
  private List<Category> categoryList = new ArrayList<Category>();

  @Facebook
  private List<String> emails = new ArrayList<String>();

  @Facebook("food_styles")
  private List<String> foodStyles = new ArrayList<String>();

  private static final long serialVersionUID = 2L;

  /**
   * Represents the <a href="http://developers.facebook.com/docs/reference/api/page">Cover Graph API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.10
   */
  public static class Cover implements Serializable {

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

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }

  }

  public static class MailingAddress implements Serializable {

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
  public static class Engagement implements Serializable {

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
  public static class VoipInfo implements Serializable {

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
  public static class PageStartInfo implements Serializable {

    @Getter
    @Setter
    @Facebook
    private String type;

    @Getter
    @Setter
    @Facebook
    private PageStartDate date;
  }

  public static class PageStartDate implements Serializable {

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
  public static class PagePaymentOptions implements Serializable {

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
  public static class PageRestaurantServices implements Serializable {

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
  public static class PageRestaurantSpecialties implements Serializable {

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
   * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/page-settings/">Page Settings
   * Graph API type</a>.
   * 
   * @since 1.10.0
   */
  public static class Settings implements Serializable {

    @Getter
    @Setter
    @Facebook
    private String setting;

    @Getter
    @Setter
    @Facebook
    private String value;

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }
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
}