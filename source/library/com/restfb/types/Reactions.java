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
package com.restfb.types;

import static java.util.Collections.unmodifiableList;

import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the <a href="https://developers.facebook.com/docs/graph-api/reference/post/reactions">Reactions Type</a>
 */
public class Reactions extends AbstractFacebookType {

  /**
   * The number of reactions.
   *
   * @return The number of reactions.
   */
  @Getter
  @Setter
  @Facebook
  private Long totalCount = 0L;

  /**
   * returns the user reaction to the object
   *
   * @return the user reaction to the object
   */
  @Getter
  @Setter
  private String viewerReaction;

  @Facebook
  private JsonObject summary;

  @Facebook
  private List<ReactionItem> data = new ArrayList<ReactionItem>();

  private static final long serialVersionUID = 1L;

  /**
   * The reaction list.
   *
   * @return The reaction list.
   */
  public List<ReactionItem> getData() {
    return unmodifiableList(data);
  }

  public boolean addData(ReactionItem reaction) {
    return data.add(reaction);
  }

  public boolean removeData(ReactionItem reaction) {
    return data.remove(reaction);
  }

  @JsonMapper.JsonMappingCompleted
  private void fillViewerReaction() {
    if (summary != null && summary.has("viewer_reaction")) {
      viewerReaction = summary.getString("viewer_reaction");
    }
  }

  /**
   * add change count value, if summary is set and count is empty
   */
  @JsonMapper.JsonMappingCompleted
  private void fillTotalCount() {
    if (totalCount == 0 && summary != null && summary.has("total_count")) {
      totalCount = summary.getLong("total_count");
    }
  }

  /**
   * the reaction type
   */
  public static class ReactionItem extends AbstractFacebookType {

    /**
     * the name of the user who sent the reaction
     */
    @Getter
    @Setter
    @Facebook
    private String name;

    /**
     * the id of the user who sent the reaction
     */
    @Getter
    @Setter
    @Facebook
    private String id;

    /**
     * type of the reaction
     *
     * may be {NONE, LIKE, LOVE, WOW, HAHA, SAD, ANGRY}
     *
     * @return type of the reaction
     */
    @Getter
    @Setter
    @Facebook
    private String type;

  }

}
