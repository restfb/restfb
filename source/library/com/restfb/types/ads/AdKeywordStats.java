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
package com.restfb.types.ads;

import com.restfb.Facebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */
public class AdKeywordStats extends NamedAdsObject {

  /**
   * The number of times your ad was served. On our mobile apps an ad is counted as served the first time it's viewed.
   * On all other Facebook interfaces, an ad is served the first time it's placed in a person's News Feed or each time
   * it's placed in the right column.
   */
  @Getter
  @Setter
  @Facebook
  private Integer impressions;

  /**
   * The number of people your ad was served to.
   */
  @Getter
  @Setter
  @Facebook("unique_impressions")
  private Integer uniqueImpressions;

  /**
   * The number of people your ad was served to.
   */
  @Getter
  @Setter
  @Facebook
  private Integer reach;

  /**
   * The total number of clicks on your ad. Depending on what you're promoting, this can include Page likes, event
   * responses or app installs.
   */
  @Getter
  @Setter
  @Facebook
  private Integer clicks;

  /**
   * The total number of unique people who have clicked on your ad. For example, if 3 people click on the same ad 5
   * times, it will count as 3 unique clicks.
   */
  @Getter
  @Setter
  @Facebook("unique_clicks")
  private Integer uniqueClicks;

  /**
   * The number of actions taken on your ad, Page, app or event after your ad was served to someone, even if they didn't
   * click on it. Actions include Page likes, app installs, conversions, event responses and more. For example, 2 Page
   * likes and 2 comments would be counted as 4 actions.
   */
  @Getter
  @Setter
  @Facebook("total_actions")
  private Integer totalActions;

  /**
   * The number of unique people who took an action such as liking your Page or installing your app as a result of your
   * ad. For example, if the same person likes and comments on a post, they will be counted as 1 unique person.
   */
  @Getter
  @Setter
  @Facebook("total_unique_actions")
  private Integer totalUniqueActions;

  /**
   * The number of actions taken on your ad, Page, app or event after your ad was served to someone, even if they didn't
   * click on it. Actions include Page likes, app installs, conversions, event responses and more. For example, 2 Page
   * likes and 2 comments would be counted as 4 actions.
   */
  @Facebook
  private List<AdsActionStats> actions = new ArrayList<AdsActionStats>();

  public List<AdsActionStats> getActions() {
    return Collections.unmodifiableList(actions);
  }

  public boolean addAction(AdsActionStats action) {
    return actions.add(action);
  }

  public boolean removeAction(AdsActionStats action) {
    return actions.remove(action);
  }

  /**
   * The number of unique people who took an action such as liking your Page or installing your app as a result of your
   * ad. For example, if the same person likes and comments on a post, they will be counted as 1 unique person.
   */
  @Facebook("unique_actions")
  private List<AdsActionStats> uniqueActions = new ArrayList<AdsActionStats>();

  public List<AdsActionStats> getUniqueActions() {
    return Collections.unmodifiableList(uniqueActions);
  }

  public boolean addUniqueAction(AdsActionStats uniqueAction) {
    return uniqueActions.add(uniqueAction);
  }

  public boolean removeUniqueAction(AdsActionStats uniqueAction) {
    return uniqueActions.remove(uniqueAction);
  }

  /**
   * The total amount you've spent so far.
   */
  @Getter
  @Setter
  @Facebook
  private Double spend;

  /**
   * The number of clicks you received divided by the number of impressions.
   */
  @Getter
  @Setter
  @Facebook
  private Double ctr;

  /**
   * The number of people who clicked on your ad divided by the number of people you reached. For example, if you
   * received 20 unique clicks and your ad was served to 1,000 unique people, your unique click-through rate would be
   * 2%.
   */
  @Getter
  @Setter
  @Facebook("unique_ctr")
  private Double uniqueCtr;

  /**
   * The average cost you've paid to have 1,000 impressions on your ad.
   */
  @Getter
  @Setter
  @Facebook
  private Double cpm;

  /**
   * The average cost you've paid to have your ad served to 1,000 unique people.
   */
  @Getter
  @Setter
  @Facebook
  private Double cpp;

  /**
   * The average cost per click for these ads, calculated as the amount spent divided by the number of clicks received.
   */
  @Getter
  @Setter
  @Facebook
  private Double cpc;

  /**
   * The average you've spent on actions. For example, if you spent $20 and you got 10 Page likes, each one cost an
   * average of $2.
   */
  @Getter
  @Setter
  @Facebook("cost_per_total_action")
  private Double costPerTotalAction;

  /**
   * The average cost per unique click for these ads, calculated as the amount spent divided by the number of unique
   * clicks received.
   */
  @Getter
  @Setter
  @Facebook("cost_per_unique_click")
  private Double costPerUniqueClick;

  /**
   * The average number of times your ad was served to each person.
   */
  @Getter
  @Setter
  @Facebook
  private Double frequency;

}
