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
import java.util.AbstractList;
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
   * The page's link.
   * 
   * @return The page's link.
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * The page's username.
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
   * The page's mission.
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
   * The director of the film. 
   * Applicable to Films.
   * 
   * @return The director of the film
   * @since 1.7.0
   */
  @Getter
  @Setter
  @Facebook("directed_by")
  private String directedBy;
  
  /**
   * General manager of the business. 
   * Applicable to Restaurants or Nightlife.
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
   * The name of a Location Page 
   * that provides additional location 
   * information for that Page beside its name. 
   * 
   * @return the name of Location Page
   * @since 1.7.0
   */
  @Getter
  @Setter
  @Facebook("name_with_location_descriptor")
  private String nameWithLocationDescriptor;
  
  /**
   * Booking agent of the band. 
   * Applicable to Bands.
   * 
   * @return Booking agent of the band
   * @since 1.7.0
   */
  @Getter
  @Setter
  @Facebook("booking_agent")
  private String bookingAgent;
  
  /**
   * Press contact information of the band. 
   * Applicable to Bands.
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

}