/**
 * Copyright (c) 2010-2017 Mark Allen, Norbert Bartels.
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
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.JsonObject;
import com.restfb.types.ads.AppLinks;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an external URL as it relates to the Facebook social graph - shares and comments from the URL on Facebook,
 * and any Open Graph objects associated with the URL.
 * 
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/url">URL Graph API type</a>.
 * 
 * Facebook APi Version 2.1+
 * 
 * @since 1.7.0
 */
public class Url extends FacebookType {

  /**
   * The Open Graph object that is canonically associated with this URL.
   * 
   * @return The Open Graph object that is canonically associated with this URL
   */
  @Getter
  @Setter
  @Facebook("og_object")
  private OGObject ogObject;

  /**
   * AppLinks data associated with this URL.
   *
   * @return AppLinks data associated with this URL.
   */
  @Getter
  @Setter
  @Facebook("app_links")
  private AppLinks appLinks;

  @Facebook
  private JsonObject share;

  @Facebook
  private JsonObject engagement;

  /**
   * The sum of comments on posts containing this URL on Facebook.
   * 
   * @return The sum of comments on posts containing this URL on Facebook.
   */
  @Getter
  @Setter
  private int commentCount = 0;

  /**
   * The total shares of this URL all over Facebook.
   * 
   * is set <code>0</code> if the share count is not present
   * 
   * @return The total shares of this URL all over Facebook.
   */
  @Getter
  @Setter
  private int shareCount = 0;

  /**
   * The sum of reactions across all posts containing the URL on Facebook.
   *
   * @return The sum of reactions across all posts containing the URL on Facebook.
   */
  @Getter
  @Setter
  private int reactionCount;

  /**
   * The number shown in the comments plugin associated with the URL. This number does not include comments made on
   * posts on Facebook.
   *
   * @return The number shown in the comments plugin associated with the URL. This number does not include comments made
   *         on posts on Facebook.
   */
  @Getter
  @Setter
  private int commentPluginCount;

  @JsonMappingCompleted
  void fillCounts() {
    if (share != null) {
      if (share.has("comment_count")) {
        commentCount = share.getInt("comment_count");
      }
      if (share.has("share_count")) {
        shareCount = share.getInt("share_count");
      }
    }
    if (engagement != null) {
      if (engagement.has("comment_count")) {
        commentCount = engagement.getInt("comment_count");
      }
      if (engagement.has("share_count")) {
        shareCount = engagement.getInt("share_count");
      }
      if (engagement.has("reaction_count")) {
        reactionCount = engagement.getInt("reaction_count");
      }
      if (engagement.has("comment_plugin_count")) {
        commentPluginCount = engagement.getInt("comment_plugin_count");
      }
    }
  }

  private static final long serialVersionUID = 1L;

  /**
   * The Open Graph object that is canonically associated with this URL.
   */
  public static class OGObject extends FacebookType {

    /**
     * The description of the object.
     * 
     * @return The description of the object
     */
    @Getter
    @Setter
    @Facebook
    private String description;

    /**
     * The title of the object.
     * 
     * @return The title of the object
     */
    @Getter
    @Setter
    @Facebook
    private String title;

    /**
     * The object type.
     * 
     * @return The object type as String
     */
    @Getter
    @Setter
    @Facebook
    private String type;

    /**
     * This URL.
     * 
     * @return This URL
     */
    @Getter
    @Setter
    @Facebook
    private String url;

    @Facebook("updated_time")
    private String rawUpdatedTime;

    /**
     * When the object was last updated.
     * 
     * @return date when the object was last updated.
     */
    @Getter
    @Setter
    private Date updatedTime;

    /**
     * The image url
     *
     * @return image url
     */
    @Getter
    @Setter
    @Facebook
    private String image;

    @JsonMappingCompleted
    void convertTime() {
      updatedTime = toDateFromLongFormat(rawUpdatedTime);
    }
  }
}