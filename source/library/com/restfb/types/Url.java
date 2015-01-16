/*
 * Copyright (c) 2010-2015 Norbert Bartels.
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
import com.restfb.JsonMapper.JsonMappingCompleted;
import com.restfb.json.JsonObject;
import static com.restfb.util.DateUtils.toDateFromLongFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an external URL as it relates to the Facebook social graph - shares and comments from the URL on Facebook,
 * and any Open Graph objects associated with the URL.
 * 
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/v2.1/url">URL Graph API type</a>.
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
  @Getter @Setter
  @Facebook("og_object")
  private OGObject ogObject;

  // @Getter
  // @Facebook("app_links")
  // private AppLinks appLinks;

  @Facebook
  private JsonObject share;
  
  /**
   * The number of Facebook comments associated with this URL.
   * 
   * @return The number of Facebook comments associated with this URL
   */
  @Getter @Setter
  private int commentCount = 0;
  
  /**
   * The number of shares of this URL on Facebook.
   * 
   * is set <code>0</code> if the share count is not present
   * 
   * @return The number of shares of this URL on Facebook
   */
  @Getter @Setter
  private int shareCount = 0;

  @JsonMappingCompleted
  void fillCounts() {
    if (share.has("comment_count")) {
      commentCount = share.getInt("comment_count");
    }
    if (share.has("share_count")) {
      shareCount = share.getInt("share_count");
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
    @Getter @Setter
    @Facebook
    private String description;

    /**
     * The title of the object.
     * 
     * @return The title of the object
     */
    @Getter @Setter
    @Facebook
    private String title;

    /**
     * The object type.
     * 
     * @return The object type as String
     */
    @Getter @Setter
    @Facebook
    private String type;

    /**
     * This URL.
     * 
     * @return This URL
     */
    @Getter @Setter
    @Facebook
    private String url;

    @Facebook("updated_time")
    private String rawUpdatedTime;

    /**
     * When the object was last updated.
     * 
     * @return date when the object was last updated.
     */
    @Getter @Setter
    private Date updatedTime;
    
    @JsonMappingCompleted
    void convertTime() {
      updatedTime = toDateFromLongFormat(rawUpdatedTime);
    }
  }
}