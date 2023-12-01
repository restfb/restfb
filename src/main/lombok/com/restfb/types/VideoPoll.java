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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.restfb.Facebook;

import lombok.Getter;
import lombok.Setter;

public class VideoPoll extends FacebookType {

  private static final long serialVersionUID = 1L;

  /**
   * True if this is a Live poll with open voting and the card hides after voting
   *
   * @return True if this is a Live poll with open voting and the card hides after voting
   */
  @Getter
  @Setter
  @Facebook("close_after_voting")
  private Boolean closeAfterVoting;

  /**
   * True if the poll should be open by default, false if it starts in a closed state
   *
   * @return True if the poll should be open by default, false if it starts in a closed state
   */
  @Getter
  @Setter
  @Facebook("default_open")
  private Boolean defaultOpen;

  /**
   * The poll question text
   *
   * @return The poll question text
   */
  @Getter
  @Setter
  @Facebook
  private String question;

  /**
   * True if this is a Live poll and voting is open and there is a background gradient to highlight the voting card
   *
   * @return True if this is a Live poll and voting is open and there is a background gradient to highlight the voting
   *         card
   */
  @Getter
  @Setter
  @Facebook("show_gradient")
  private Boolean showGradient;

  /**
   * True if this is a Live poll and voting is open and the results show after voting
   *
   * @return True if this is a Live poll and voting is open and the results show after voting
   */
  @Getter
  @Setter
  @Facebook("show_results")
  private Boolean showResults;

  /**
   * Live poll status
   *
   * @return Live poll status
   */
  @Getter
  @Setter
  @Facebook
  private String status;

  @Facebook("poll_options")
  private List<VideoPollOption> pollOptions = new ArrayList<>();

  public List<VideoPollOption> getPollOptions() {
    return Collections.unmodifiableList(pollOptions);
  }

  public boolean addPollOption(VideoPollOption option) {
    return pollOptions.add(option);
  }

  public boolean removePollOption(VideoPollOption option) {
    return pollOptions.remove(option);
  }

  public static class VideoPollOption extends FacebookType {

    private static final long serialVersionUID = 1L;

    /**
     * True if this answer is considered correct, otherwise false
     *
     * @return True if this answer is considered correct, otherwise false
     */
    @Getter
    @Setter
    @Facebook("is_correct")
    private Boolean isCorrect;

    /**
     * Options appear in increasing numerical order within a poll
     *
     * @return Options appear in increasing numerical order within a poll
     */
    @Getter
    @Setter
    @Facebook
    private Long order;

    /**
     * Text to display to the user for this option
     *
     * @return Text to display to the user for this option
     */
    @Getter
    @Setter
    @Facebook
    private String text;

    /**
     * Total number of votes for this option
     *
     * @return Total number of votes for this option
     */
    @Getter
    @Setter
    @Facebook("total_votes")
    private Long totalVotes;
  }
}
