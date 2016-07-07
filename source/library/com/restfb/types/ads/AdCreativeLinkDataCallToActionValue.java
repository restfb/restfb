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
 * <a href="https://developers.facebook.com/docs/marketing-api/reference/ad-creative-link-data-call-to-action-value/">
 * AdCreativeLinkDataCallToActionValue</a> Marketing API type
 */
public class AdCreativeLinkDataCallToActionValue extends AbstractFacebookType {

  /**
   * Deep link to the app.
   *
   * -- GETTER --
   *
   * @return Deep link to the app.
   */
  @Getter
  @Setter
  @Facebook("app_link")
  private String appLink;

  /**
   * Application related to the action.
   *
   * -- GETTER --
   *
   * @return Application related to the action.
   */
  @Getter
  @Setter
  @Facebook
  private String application;

  /**
   * ID of the Facebook event which the attachment show event info
   *
   * -- GETTER --
   *
   * @return ID of the Facebook event which the attachment show event info
   */
  @Getter
  @Setter
  @Facebook("event_id")
  private String eventId;

  /**
   * The Lead Ad form id.
   *
   * -- GETTER --
   *
   * @return The Lead Ad form id.
   */
  @Getter
  @Setter
  @Facebook("lead_gen_form_id")
  private String leadGenFormId;

  /**
   * The destination link when the CTA button is clicked.
   *
   * -- GETTER --
   *
   * @return The destination link when the CTA button is clicked.
   */
  @Getter
  @Setter
  @Facebook
  private String link;

  /**
   * Caption shown in the attachment.
   *
   * -- GETTER --
   *
   * @return Caption shown in the attachment.
   */
  @Getter
  @Setter
  @Facebook("link_caption")
  private String linkCaption;

  /**
   * Description of the link shown in the attachment.
   *
   * -- GETTER --
   *
   * @return Description of the link shown in the attachment.
   */
  @Getter
  @Setter
  @Facebook("link_description")
  private String linkDescription;

  /**
   * Title of the link shown in the attachment.
   *
   * -- GETTER --
   *
   * @return Title of the link shown in the attachment.
   */
  @Getter
  @Setter
  @Facebook("link_title")
  private String linkTitle;

  /**
   * ID of the Facebook page which the CTA button links to
   *
   * -- GETTER --
   *
   * @return ID of the Facebook page which the CTA button links to
   */
  @Getter
  @Setter
  @Facebook
  private String page;

  /**
   * Open graph object url for canvas virtual good ads.
   *
   * -- GETTER --
   *
   * @return Open graph object url for canvas virtual good ads.
   */
  @Getter
  @Setter
  @Facebook("product_link")
  private String productLink;
}
