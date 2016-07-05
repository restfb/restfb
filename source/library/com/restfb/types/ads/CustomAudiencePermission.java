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
import com.restfb.types.AbstractFacebookType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the
 * <a href="https://developers.facebook.com/docs/marketing-api/reference/custom-audience-permission">Custom audience
 * permission</a> Marketing API type
 */
public class CustomAudiencePermission extends AbstractFacebookType {

  /**
   * Permission to edit the audience
   *
   * -- GETTER --
   *
   * @return Permission to edit the audience
   */
  @Getter
  @Setter
  @Facebook("can_edit")
  private Boolean canEdit;

  /**
   * Permission to see insight of the audience
   *
   * -- GETTER --
   *
   * @return Permission to see insight of the audience
   */
  @Getter
  @Setter
  @Facebook("can_see_insight")
  private Boolean canSeeInsight;

  /**
   * Capability to share audience based on gatekeeper
   *
   * -- GETTER --
   *
   * @return Capability to share audience based on gatekeeper
   */
  @Getter
  @Setter
  @Facebook("can_share")
  private Boolean canShare;

  /**
   * Capability to be used as seed for lookalike audience
   *
   * -- GETTER --
   *
   * @return Capability to be used as seed for lookalike audience
   */
  @Getter
  @Setter
  @Facebook("subtype_supports_lookalike")
  private Boolean subtypeSupportsLookalike;

  /**
   * Capability to be used as seed for lookalike audience for recipient ad accounts
   *
   * -- GETTER --
   *
   * @return Capability to be used as seed for lookalike audience for recipient ad accounts
   */
  @Getter
  @Setter
  @Facebook("supports_recipient_lookalike")
  private Boolean supportsRecipientLookalike;
}
