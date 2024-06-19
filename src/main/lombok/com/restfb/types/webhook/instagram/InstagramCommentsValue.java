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
package com.restfb.types.webhook.instagram;

import com.restfb.Facebook;
import com.restfb.types.instagram.IgFrom;
import com.restfb.types.webhook.ChangeValue;

import lombok.Getter;
import lombok.Setter;

public class InstagramCommentsValue extends ChangeValue {

  /**
   * The id of the object
   */
  @Getter
  @Setter
  @Facebook
  private String id;

  /**
   * Instagram-scoped ID and username of the Instagram user who created the comment
   */
  @Getter
  @Setter
  @Facebook
  private IgFrom from;

  /**
   * ID and product type of the IG Media the comment was created on
   */
  @Getter
  @Setter
  @Facebook
  private IgCommentMedia media;

  /**
   * Comment text
   */
  @Getter
  @Setter
  @Facebook
  private String text;

  /**
   * ID of parent IG Comment if this comment was created on another IG Comment (i.g. a reply to another comment)
   */
  @Getter
  @Setter
  @Facebook("parent_id")
  private String parentId;

  @Getter
  @Setter
  public static class IgCommentMedia {

    /**
     * ID of the IG Media the comment was created on
     */
    @Facebook
    private String id;

    /**
     * Product type of the IG Media the comment was created on
     */
    @Facebook("media_product_type")
    private String mediaProductType;

    /**
     * ID of the IG Ad the comment was created on
     */
    @Facebook("ad_id")
    private String adId;

    /**
     * Title of the IG Ad the comment was created on
     */
    @Facebook("ad_title")
    private String adTitle;

    /**
     * Original media id of the IG Ad the comment was created on
     */
    @Facebook("original_media_id")
    private String originalMediaId;
  }
}
