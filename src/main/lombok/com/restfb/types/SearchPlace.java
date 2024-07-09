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
package com.restfb.types;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.types.ads.AppLinks;
import com.restfb.types.features.HasCover;
import com.restfb.types.features.HasProfilePicture;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/places/fields">SearchPlace</a>
 */
public class SearchPlace extends NamedFacebookType implements HasProfilePicture, HasCover {

  /**
   * Information about the Place provided by the Page administrator.
   *
   * @return Information about the Place provided by the Page administrator.
   */
  @Getter
  @Setter
  @Facebook
  private String about;

  /**
   * AppLinks to the Place on various devices.
   *
   * @return AppLinks to the Place on various devices.
   */
  @Getter
  @Setter
  @Facebook("app_links")
  private AppLinks appLinks;

  @Facebook("category_list")
  private List<Category> categoryList = new ArrayList<>();

  /**
   * The number of checkins at this Place.
   *
   * @return The number of checkins at this Place.
   */
  @Getter
  @Setter
  @Facebook
  private Long checkins;

  /**
   * Information about the cover photo.
   *
   * @return Information about the cover photo.
   */
  @Getter(onMethod_ = {@Override})
  @Setter
  @Facebook
  private CoverPhoto cover;

  /**
   * The description of the Place provided by the Page administrator.
   *
   * @return The description of the Place provided by the Page administrator.
   */
  @Getter
  @Setter
  @Facebook
  private String description;

  /**
   * The social sentence and the like count information for this Place. This is the same information used for the like
   * button.
   *
   * @return The social sentence and the like count information for this Place.
   */
  @Getter
  @Setter
  @Facebook
  private Page.Engagement engagement;

  /**
   *
   */
  @Getter
  @Setter
  @Facebook
  private String hours;

  /**
   * Indicates whether this Place is always open.
   *
   * @return Indicates whether this Place is always open.
   */
  @Getter
  @Setter
  @Facebook("is_always_open")
  private Boolean isAlwaysOpen;

  /**
   * Indicates whether this Place is permanently closed.
   *
   * @return Indicates whether this Place is permanently closed.
   */
  @Getter
  @Setter
  @Facebook("is_permanently_closed")
  private Boolean isPermanentlyClosed;

  /**
   * Pages with a large number of followers can be manually verified by Facebook as having an authentic identity. This
   * field indicates whether the Page has been verified by this process.
   */
  @Getter
  @Setter
  @Facebook("is_verified")
  private Boolean isVerified;

  /**
   * The URL of the Facebook Page.
   *
   * @return The URL of the Facebook Page.
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * Location information about the Place. E.g., latitude and longitude, and address.
   *
   * @return Location information about the Place. E.g., latitude and longitude, and address.
   */
  @Getter
  @Setter
  @Facebook
  private Location location;

  /**
   * The overall page rating, based on rating survey from users, on a scale of 1-5.
   *
   * This value is normalized, and is not guaranteed to be a strict average of user ratings.
   *
   * @return The overall page rating, based on rating survey from users, on a scale of 1-5.
   */
  @Getter
  @Setter
  @Facebook("overall_star_rating")
  private Double overallStarRating;

  /**
   * The Page node corresponding to the Place.
   *
   * @return The Page node corresponding to the Place.
   */
  @Getter
  @Setter
  @Facebook
  private Page page;

  /**
   * Parking information about the Place.
   *
   * @return Parking information about the Place.
   */
  @Getter
  @Setter
  @Facebook
  private Page.PageParking parking;

  /**
   * Payment options accepted by the Place. Applicable to restaurants and nightlife.
   *
   * @return Payment options accepted by the Place.
   */
  @Getter
  @Setter
  @Facebook("payment_options")
  private Page.PagePaymentOptions paymentOptions;

  /**
   * The Place's telephone number.
   *
   * @return The Place's telephone number.
   */
  @Getter
  @Setter
  @Facebook
  private String phone;

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

  @Facebook("picture")
  private transient String rawPicture;

  /**
   * Price range of the business.
   *
   * Applicable to Restaurants or Nightlife. Can be one of $ (0-10), $$ (10-30), $$$ (30-50), $$$$ (50+), or Unspecified
   *
   * @return Price range of the business.
   */
  @Getter
  @Setter
  @Facebook("price_range")
  private String priceRange;

  /**
   * Number of ratings for the Place
   *
   * @return Number of ratings for the Place
   */
  @Getter
  @Setter
  @Facebook("rating_count")
  private Long ratingCount;

  /**
   * The services that the restaurant provides.
   *
   * @return The services that the restaurant provides.
   */
  @Getter
  @Setter
  @Facebook("restaurant_services")
  private Page.PageRestaurantServices restaurantServices;

  /**
   * The restaurant's specialties.
   *
   * @return The restaurant's specialties.
   */
  @Getter
  @Setter
  @Facebook("restaurant_specialties")
  private Page.PageRestaurantSpecialties restaurantSpecialties;

  /**
   * The Place's complete postal address, on a single line.
   *
   * @return The Place's complete postal address, on a single line.
   */
  @Getter
  @Setter
  @Facebook("single_line_address")
  private String singleLineAddress;

  /**
   * The URL of the Place's website.
   *
   * @return The URL of the Place's website.
   */
  @Getter
  @Setter
  @Facebook
  private String website;

  /**
   * Photos for the Place.
   *
   * By default, this edge returns the profile photos for the Place. You can also use an optional type parameter with
   * the value uploaded to get the photos uploaded by the Place.
   */
  @Getter
  @Setter
  @Facebook
  private List<Photo> photos = new ArrayList<>();

  /**
   * The native component workflows for this Place.
   *
   * Workflows allow people to perform actions like requesting appointments, buying tickets, and ordering from
   * restaurants on Facebook
   */
  @Getter
  @Setter
  @Facebook
  private List<PagesPlatformComponentFlowServiceConfig> workflows = new ArrayList<>();

  @JsonMapper.JsonMappingCompleted
  protected void fillProfilePicture(JsonMapper jsonMapper) {
    picture = convertPicture(jsonMapper, rawPicture);
  }

  /**
   * The Place's categories.
   *
   * @return The Place's categories.
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

  public static class PagesPlatformComponentFlowServiceConfig extends FacebookType {

    @Getter
    @Setter
    @Facebook
    private String deeplink;

    @Getter
    @Setter
    @Facebook
    private String label;

    @Getter
    @Setter
    @Facebook("flow_category")
    private String flowCategory;

  }
}
