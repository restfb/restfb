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
package com.restfb.types.instagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.restfb.Connection;
import com.restfb.Facebook;
import com.restfb.types.Insight;

import com.restfb.types.ads.IgBoostMediaAd;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/instagram-api/reference/media">instagram media</a> type
 */
public class IgMedia extends IgMediaChild {

  private static final long serialVersionUID = 1L;

  /**
   * The field provides information about boosting eligibility of a IG instagram media as an ad and additional details
   * if not eligible.
   */
  @Getter
  @Setter
  @Facebook("boost_eligibility_info")
  private IgBoostEligibilityInfo boostEligibilityInfo;

  @Getter
  @Setter
  @Facebook("boost_ads_list")
  private List<IgBoostMediaAd> boostAdsList = new ArrayList<>();

  /**
   * Caption. Excludes album children. The @ symbol is excluded, unless the app user can perform admin-equivalent tasks
   * on the Facebook Page connected to the Instagram account used to create the caption.
   */
  @Getter
  @Setter
  @Facebook
  private String caption;

  /**
   * Count of comments on the media.
   * <p>
   * Excludes comments on album child media and the media's caption. Includes replies on comments.
   */
  @Getter
  @Setter
  @Facebook("comments_count")
  private Long commentsCount;

  /**
   * Indicates if comments are enabled or disabled. Excludes album children.
   */
  @Getter
  @Setter
  @Facebook("is_comment_enabled")
  private Boolean isCommentEnabled;

  /**
   * Reels only. If {@code true}, indicates the reel can appear in both the Feed and Reels tabs.
   * <p>
   * If {@code false}, indicates the reel can only appear in the Reels tab.
   */
  @Getter
  @Setter
  @Facebook("is_shared_to_feed")
  private Boolean isSharedToFeed;

  /**
   * Count of likes on the media.
   * <p>
   * Excludes likes on album child media and likes on promoted posts created from the media. Includes replies on
   * comments.
   * 
   * <ul>
   * <li><strong>v10.0 and older calls</strong>: value will be 0 if the media owner has hidden like counts it.</li>
   * <li><strong>v11.0+ calls</strong>: field will be omitted if media owner has hidden like counts on it.</li>
   * </ul>
   */
  @Getter
  @Setter
  @Facebook("like_count")
  private Long likeCount;

  /**
   * Media thumbnail URL. Only available on VIDEO media.
   */
  @Getter
  @Setter
  @Facebook("thumbnail_url")
  private String thumbnailUrl;

  /**
   * IGTV media title.
   * 
   * @deprecated Omitted from response.
   */
  @Deprecated
  @Getter
  @Setter
  @Facebook("video_title")
  private String videoTitle;

  @Getter
  @Setter
  @Facebook("copyright_check_information")
  private CopyrightCheckInformation copyrightCheckInformation;

  @Facebook
  private List<IgMediaChild> children = new ArrayList<>();

  @Facebook
  private List<Insight> insights = new ArrayList<>();

  @Facebook
  private List<IgComment> comments = new ArrayList<>();

  /**
   * returns the list of child media objects, only available if the media_type is a carousal
   * 
   * @return the list of children as unmodifiable list
   */
  public List<IgMediaChild> getChildren() {
    return Collections.unmodifiableList(children);
  }

  /**
   * adds a new child to the children list
   * 
   * @param child
   *          the child that is added
   * @return true if adding the child works
   */
  public boolean addChild(IgMediaChild child) {
    return children.add(child);
  }

  /**
   * removes a new child from the children list
   * 
   * @param child
   *          the child that is removed
   * @return true if removing the child works
   */
  public boolean removeChild(IgMediaChild child) {
    return children.remove(child);
  }

  public List<Insight> getInsights() {
    return Collections.unmodifiableList(insights);
  }

  public boolean addChild(Insight insight) {
    return insights.add(insight);
  }

  public boolean removeChild(Insight insight) {
    return insights.remove(insight);
  }

  /**
   * returns the comments of this media
   * 
   * @return the comments of this media
   */
  public List<IgComment> getComments() {
    return Collections.unmodifiableList(comments);
  }

  public boolean addComment(IgComment comment) {
    return comments.add(comment);
  }

  public boolean removeComment(IgComment comment) {
    return comments.remove(comment);
  }
}
