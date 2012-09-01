/*
 * Copyright (c) 2010-2012 Mark Allen.
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

import java.io.Serializable;

import com.restfb.Facebook;
import com.restfb.util.ReflectionUtils;

/**
 * Represents the <a
 * href="http://developers.facebook.com/docs/reference/api/page">Page Graph API
 * type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Page extends CategorizedFacebookType {
  @Facebook
  private String picture;

  @Facebook
  private String link;

  @Facebook
  private String username;

  @Facebook
  private String founded;

  @Facebook("company_overview")
  private String companyOverview;

  @Facebook
  private String mission;

  @Facebook
  private String products;

  @Facebook
  private Long likes;

  @Facebook("is_community_page")
  private Boolean isCommunityPage;

  @Facebook
  private String description;

  @Facebook
  private Integer checkins;

  @Facebook
  private String phone;

  @Facebook("access_token")
  private String accessToken;

  @Facebook
  private String about;

  @Facebook("talking_about_count")
  private Long talkingAboutCount;

  @Facebook("can_post")
  private Boolean canPost;

  @Facebook("is_published")
  private Boolean isPublished;

  @Facebook
  private Location location;

  @Facebook
  private Cover cover;

  private static final long serialVersionUID = 2L;

  /**
   * Represents the <a
   * href="http://developers.facebook.com/docs/reference/api/page">Cover Graph
   * API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.10
   */
  public static class Cover implements Serializable {
    @Facebook("cover_id")
    private String coverId;

    @Facebook
    private String source;

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

    /**
     * The ID of the photo.
     * 
     * @return The ID of the photo.
     */
    public String getCoverId() {
      return coverId;
    }

    /**
     * The URL for the cover photo.
     * 
     * @return The URL for the cover photo.
     */
    public String getSource() {
      return source;
    }

    /**
     * The percentage offset from top [0-100].
     * 
     * @return The percentage offset from top [0-100].
     */
    public Integer getOffsetY() {
      return offsetY;
    }
  }

  /**
   * The page's picture.
   * 
   * @return The page's picture.
   */
  public String getPicture() {
    return picture;
  }

  /**
   * The page's link.
   * 
   * @return The page's link.
   */
  public String getLink() {
    return link;
  }

  /**
   * The page's username.
   * 
   * @return The page's username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * When the page was founded.
   * 
   * @return When the page was founded.
   */
  public String getFounded() {
    return founded;
  }

  /**
   * Overview of the page's company.
   * 
   * @return Overview of the page's company.
   */
  public String getCompanyOverview() {
    return companyOverview;
  }

  /**
   * The page's mission.
   * 
   * @return The page's mission.
   */
  public String getMission() {
    return mission;
  }

  /**
   * The page's products.
   * 
   * @return The page's products.
   */
  public String getProducts() {
    return products;
  }

  /**
   * The number of likes the page has.
   * 
   * @return The number of likes the page has.
   * @since 1.6.5
   */
  public Long getLikes() {
    return likes;
  }

  /**
   * Is this a community page?
   * 
   * @return Is this a community page?
   */
  public Boolean getIsCommunityPage() {
    return isCommunityPage;
  }

  /**
   * A description of this page.
   * 
   * @return A description of this page.
   */
  public String getDescription() {
    return description;
  }

  /**
   * The total number of users who have checked in to the Page.
   * 
   * @return The total number of users who have checked in to the Page.
   */
  public Integer getCheckins() {
    return checkins;
  }

  /**
   * The phone number (not always normalized for country code) for the Page.
   * 
   * @return The phone number (not always normalized for country code) for the
   *         Page.
   */
  public String getPhone() {
    return phone;
  }

  /**
   * The access token specific to this page.
   * <p>
   * This value will only be available for pages if the application accessing
   * this page has been given {@code manage_page} permissions.
   * 
   * @return The access token specific to this page.
   * @since 1.6.5
   */
  public String getAccessToken() {
    return accessToken;
  }

  /**
   * General information about this page.
   * 
   * @return General information about this page.
   * @since 1.6.10
   */
  public String getAbout() {
    return about;
  }

  /**
   * The number of people that are talking about this page (last seven days).
   * 
   * @return The number of people that are talking about this page (last seven
   *         days).
   * @since 1.6.10
   */
  public Long getTalkingAboutCount() {
    return talkingAboutCount;
  }

  /**
   * The location of the place this page represents.
   * 
   * @return The location of the place this page represents.
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Indicates whether the current session user can post on this page.
   * 
   * @return Whether the current session user can post on this page.
   * @since 1.6.10
   */
  public Boolean getCanPost() {
    return canPost;
  }

  /**
   * Indicates whether the page is published and visible to non-admins.
   * 
   * @return Whether the page is published and visible to non-admins.
   * @since 1.6.10
   */
  public Boolean getIsPublished() {
    return isPublished;
  }

  /**
   * The cover photo.
   * 
   * @return The cover photo.
   * @since 1.6.10
   */
  public Cover getCover() {
    return cover;
  }
}